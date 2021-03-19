package ch.heigvd.res.labio.impl.filters;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.logging.Logger;

/**
 * This class transforms the streams of character sent to the decorated writer.
 * When filter encounters a line separator, it sends it to the decorated writer.
 * It then sends the line number and a tab character, before resuming the write
 * process.gg
 *
 * Hello\n\World -> 1\Hello\n2\tWorld
 *
 * @author Olivier Liechti
 */
public class FileNumberingFilterWriter extends FilterWriter {

  private static final Logger LOG = Logger.getLogger(FileNumberingFilterWriter.class.getName());
  private int lineCount; //compteur de ligne
  private boolean isFirstChar; //indique si c'est le premier char écrit dans le writer
  private char lastChar; //le dernier char écrit dans le writer

  /**
   * Constructeur
   * @param out le string writer
   */
  public FileNumberingFilterWriter(Writer out) {
    super(out);
    lineCount = 1;
    isFirstChar = true;
    lastChar = '\0';
  }

  /**
   * Ecrit dans le writer la substring entre off et off+len en ajoutant la mise en forme définie plus haut
   * @param str la string à convertir
   * @param off l'offset
   * @param len la longueur de la substring
   * @throws IOException si la substring limitée par les paramètres est hors de str
   */
  @Override
  public void write(String str, int off, int len) throws IOException {
    if(off < 0 || off+len > str.length())
      throw new IOException("Boundaries are off limit");
    this.write(str.substring(off, off+len));
  }

  /**
   * Ecrit dans le writer le sous tableau de cbuf compris entre off et off +len en ajoutant la mise en forme
   * définie plus haut
   * @param cbuf le tableau de char à convertir
   * @param off l'offset
   * @param len la taille du sous tableau
   * @throws IOException si le sous tableau limitée par les paramètres est hors de cbuf
   */
  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
    if(off < 0 || off+len > cbuf.length)
      throw new IOException("Boundaries are off limit");
    for(int i = off; i<off+len; i++)
      this.write(cbuf[i]);
  }

  /**
   * Ajoute un char au writer en respectant la mise en forme
   * @param c le char à ajouter
   * @throws IOException
   */
  @Override
  public void write(int c) throws IOException {
    //cas du premier caractère
    if(isFirstChar) {
      this.writeMultipleDigitsNumber(lineCount++);
      super.write('\t');
      isFirstChar = false; 
    }

    //cas du retour à la ligne
    if(c == '\n' || lastChar == '\r') {
      if(lastChar == '\r')
        super.write(lastChar);
      if(c == '\n')
        super.write(c);
      this.writeMultipleDigitsNumber(lineCount++); //pour ajouter les nombres (<1000)
      super.write('\t');
      if(lastChar == '\r' && c != '\n')
        super.write(c);
    }

    //cas d'un char normal
    else if(c != '\r')
      super.write(c);
    lastChar = (char) c;
  }

  /**
   * Ecrit une string dans le writer en respectant la mise en forme
   * @param s, la string à écrire
   * @throws IOException
   */
  public void write(String s) throws IOException {
    for (int i = 0; i < s.length(); ++i) {
      this.write(s.charAt(i));
    }
  }

  /**
   * Ecrit un entier dans >10 et <1000 dans le string writer
   * @param i l'entier à écrire
   * @throws IOException
   */
  private void writeMultipleDigitsNumber(int i) throws IOException{
    if(i>=100)
      super.write('0'+i/100);
    if(i>=10)
      super.write('0'+((i%100)/10));
    super.write('0'+i%10);
  }
}