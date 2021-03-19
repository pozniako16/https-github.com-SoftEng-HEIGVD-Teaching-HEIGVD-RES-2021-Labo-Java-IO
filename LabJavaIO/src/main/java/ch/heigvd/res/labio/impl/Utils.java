package ch.heigvd.res.labio.impl;

import java.util.logging.Logger;

/**
 *
 * @author Olivier Liechti
 * Modified by: Lev Pozniakoff
 */
public class Utils {

  private static final Logger LOG = Logger.getLogger(Utils.class.getName());

  /**
   * This method looks for the next new line separators (\r, \n, \r\n) to extract
   * the next line in the string passed in arguments. 
   * 
   * @param lines a string that may contain 0, 1 or more lines
   * @return an array with 2 elements; the first element is the next line with
   * the line separator, the second element is the remaining text. If the argument does not
   * contain any line separator, then the first element is an empty string.
   */
  public static String[] getNextLine(String lines) {
    boolean foundSeparator = false;
    int limitIndex = 0;
    for (int i = 0; i < lines.length() && !foundSeparator; ++i) {
      if (lines.charAt(i) == '\r') {
        if (i <= lines.length() - 2 && lines.charAt(i + 1) == '\n')
          ++i;
        else {
          foundSeparator = true;
          limitIndex = i;
        }
      }
      if (lines.charAt(i) == '\n') {
        foundSeparator = true;
        limitIndex = i;
      }
    }

    String[] output = {"", ""};
    if (limitIndex == lines.length() - 1)
      output[0] = lines;
    else if (!foundSeparator)
      output[1] = lines;
    else {
      output[0] = lines.substring(0, limitIndex+1);
      output[1] = lines.substring(limitIndex+1);
    }
    return output;
  }
}
