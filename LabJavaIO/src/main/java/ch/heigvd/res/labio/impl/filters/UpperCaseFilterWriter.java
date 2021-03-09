package ch.heigvd.res.labio.impl.filters;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;

/**
 *
 * @author Olivier Liechti

 * Modified by Lev POZNIAKOFF
 *
 * DATE: 05.03.2021
=======
 */
public class UpperCaseFilterWriter extends FilterWriter {
  
  public UpperCaseFilterWriter(Writer wrappedWriter) {
    super(wrappedWriter);
  }

  @Override
  public void write(String str, int off, int len) throws IOException {
    this.write(str.substring(off, off+len));
  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
    for(int i = off; i<off+len; ++i)
      this.write(cbuf[i]);
  }

  @Override
  public void write(int c) throws IOException {
    if (Character.isAlphabetic(c) && c >= (int)'a')
      super.write(Character.toUpperCase(c));
    else
      super.write(c);
  }

  public void write(String s) throws IOException{
    for(int i = 0; i<s.length(); ++i)
      this.write(s.charAt(i));
  }
}
