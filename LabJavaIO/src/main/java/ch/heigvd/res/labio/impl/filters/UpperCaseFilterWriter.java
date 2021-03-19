package ch.heigvd.res.labio.impl.filters;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;

/**
 *
 * @author Olivier Liechti
 * Modified by Lev POZNIAKOFF
 * This class allows its user to convert a string into an uppercase string
 * DATE: 05.03.2021
 */
public class UpperCaseFilterWriter extends FilterWriter {

  /**
   * Constructeur
   * @param wrappedWriter
   */
  public UpperCaseFilterWriter(Writer wrappedWriter) {
    super(wrappedWriter);
  }

  /**
   * Converti une substring en majuscule et l'écrit dans le writer
   * @param str la string
   * @param off l'offset
   * @param len la taille de la substring
   * @throws IOException si la substring comprise dans off et off+len est hors de str
   */
  @Override
  public void write(String str, int off, int len) throws IOException {
    if(off < 0 || off+len > str.length()-1)
      throw new IOException("Boundaries are off limit");
    this.write(str.substring(off, off+len));
  }


  /**
   * Converti un sous tableau en majuscule et l'écrit dans le writer
   * @param cbuf le sous tableau
   * @param off l'offset
   * @param len la taille de la substring
   * @throws IOException si le sous-tableau compris dans off et off+len est hors de str
   */
  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
    if(off < 0 || off+len > cbuf.length)
      throw new IOException("Boundaries are off limit");
    for(int i = off; i<off+len; ++i)
      this.write(cbuf[i]);
  }

  /**
   * Converti un char en majuscule si c'est une lettre et l'ajoute dans le writer
   * l'ajoute seulement dans le writer si ce n'est pas une lettre
   * @param c le char à convertir
   * @throws IOException
   */
  @Override
  public void write(int c) throws IOException {
    if (Character.isAlphabetic(c) && c >= (int)'a')
      super.write(Character.toUpperCase(c));
    else
      super.write(c);
  }

  /**
   * Converti une string en majuscule et l'ajoute au writer
   * @param s la string a convertir
   * @throws IOException
   */
  public void write(String s) throws IOException{
    for(int i = 0; i<s.length(); ++i)
      this.write(s.charAt(i));
  }
}
