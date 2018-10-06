
package com.palvai.strings;

import java.util.regex.Mathcher;
import java.util.regex.Pattern;

public class RunLengthEncoding {
 
  public static String encode(String inputStr) {
    StringBuffer enodedString = new StringBuffer();
    int inputLen  = input.length();
    for(int i=0;i<inputLen;i++) {
        int runLen = 1;
        while(i+1 < inputLen && inputStr.charAt(i) == inputStr.charAt(i+1)) {
          i++;
          runLen++;
        }
      encodedString.append(runLen);
      encodedString.append(source.charAt(i));
      
    }
    return encodedString.toString();
  }
}
