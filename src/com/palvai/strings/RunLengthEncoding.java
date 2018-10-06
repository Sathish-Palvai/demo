
package com.palvai.strings;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RunLengthEncoding {

	public static String encode(String inputStr) {
		StringBuffer encodedString = new StringBuffer();
		int inputLen = inputStr.length();
		for (int i = 0; i < inputLen; i++) {
			int runLen = 1;
			while (i + 1 < inputLen && inputStr.charAt(i) == inputStr.charAt(i + 1)) {
				i++;
				runLen++;
			}
			encodedString.append(runLen == 1 ? "" : runLen);
			encodedString.append(inputStr.charAt(i));

		}
		return encodedString.toString();
	}

	public static String encodeWithSingleCharacter(String inputStr) {
		StringBuffer encodedString = new StringBuffer();
		int inputLen = inputStr.length();
		for (int i = 0; i < inputLen; i++) {
			int runLen = 1;
			while (i + 1 < inputLen && inputStr.charAt(i) == inputStr.charAt(i + 1)) {
				i++;
				runLen++;
			}
			encodedString.append(runLen);
			encodedString.append(inputStr.charAt(i));

		}
		return encodedString.toString();
	}

	public static String decode(String source) {
		StringBuffer finalString = new StringBuffer();
		Pattern pattern = Pattern.compile("[0-9]+|[a-zA-Z]");
		Matcher matcher = pattern.matcher(source);
		while (matcher.find()) {
			try {
				int num = Integer.parseInt(matcher.group());
				matcher.find();
				while (num-- != 0) {
					finalString.append(matcher.group());
				}
			} catch (NumberFormatException ex) {
				finalString.append(matcher.group());
			}
		}
		return finalString.toString();
	}

	public static void main(String args[]) {

		String input = "AABBCCDDEEFGH";
		String encodedSingleString = RunLengthEncoding.encodeWithSingleCharacter(input);
		String encodedString = RunLengthEncoding.encode(input);
		System.out.println(encodedSingleString);
		System.out.println(RunLengthEncoding.encode(input));
		System.out.println("Decoded String" + RunLengthEncoding.decode(encodedString));

	}

}
