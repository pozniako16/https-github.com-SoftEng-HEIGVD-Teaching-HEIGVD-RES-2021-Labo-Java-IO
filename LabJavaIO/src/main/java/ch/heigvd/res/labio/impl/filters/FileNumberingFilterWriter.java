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
  private int lineCount;
  private boolean isFirstChar;
  private char lastChar;

  public FileNumberingFilterWriter(Writer out) {
    super(out);
    lineCount = 1;
    isFirstChar = true;
    lastChar = '\0';
  }

  @Override
  public void write(String str, int off, int len) throws IOException {
    this.write(str.substring(off, off+len));
  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
    for(int i = off; i<off+len; i++)
      this.write(cbuf[i]);
  }

  @Override
  public void write(int c) throws IOException {
    if(isFirstChar) {
      this.writeMultipleDigitsNumber(lineCount++);
      super.write('\t');
      isFirstChar = false; 
    }

    if(c == '\n' || lastChar == '\r') {
      if(lastChar == '\r')
        super.write(lastChar);
      if(c == '\n')
        super.write(c);
      this.writeMultipleDigitsNumber(lineCount++);
      super.write('\t');
      if(lastChar == '\r' && c != '\n')
        super.write(c);
    }
    else if(c != '\r')
      super.write(c);
    lastChar = (char) c;
  }

  public void write(String s) throws IOException {
    for (int i = 0; i < s.length(); ++i) {
      this.write(s.charAt(i));

    }

  }

  private void writeMultipleDigitsNumber(int i) throws IOException{
    if(i>=100)
      super.write('0'+i/100);
    if(i>=10)
      super.write('0'+((i%100)/10));
    super.write('0'+i%10);
  }
}