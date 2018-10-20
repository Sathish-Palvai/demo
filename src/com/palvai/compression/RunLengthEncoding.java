package com.palvai.compression;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Program to encode the strings like AABBCCDDEEFGH to 2A2B2C2D2EFGH
 * 
 * @author USSAPAL
 *
 */
public class RunLengthEncoding {

	/**
	 * Method to encode the input string.
	 * 
	 * @param input
	 * @return
	 */
	public static String encode(String input) {

		// To store the result
		StringBuffer encodedString = new StringBuffer();

		// Input string length
		int inputLen = input.length();

		// Start from 0 and iterate over all the characters
		for (int i = 0; i < inputLen; i++) {
			// Initialize the run length of the select char as 1
			int runLen = 1;
			// Inner loop to increment i and verify if next character
			// is equal to selected character. Make sure i+1 not crosses
			// the string length which will throw IndexOutOfBoundsException.
			while (i + 1 < inputLen && input.charAt(i) == input.charAt(i + 1)) {
				// If current and next character are equal increment runLength
				// and also increment i to start checking next character.
				runLen++;
				i++;
			}
			// If the are repeated characters append run length to encodedString
			encodedString.append(runLen == 1 ? "" : runLen);
			// Append the current character.
			encodedString.append(input.charAt(i));

		}
		// Return the encoded string.
		return encodedString.toString();
	}

	/**
	 * 
	 * @param input
	 * @return
	 */
	public static String encodeWithSingleCharacter(String input) {
		// To store the result
		StringBuffer encodedString = new StringBuffer();

		// Input string length
		int inputLen = input.length();

		// Start from 0 and iterate over all the characters
		for (int i = 0; i < inputLen; i++) {
			// Initialize the run length of the select char as 1
			int runLen = 1;
			// Inner loop to increment i and verify if next character
			// is equal to selected character. Make sure i+1 not crosses
			// the string length which will throw IndexOutOfBoundsException.
			while (i + 1 < inputLen && input.charAt(i) == input.charAt(i + 1)) {
				// If current and next character are equal increment runLength
				// and also increment i to start checking next character.
				runLen++;
				i++;
			}
			// If the are repeated characters append run length to encodedString
			encodedString.append(runLen);
			// Append the current character.
			encodedString.append(input.charAt(i));

		}
		// Return the encoded string.
		return encodedString.toString();
	}

	/**
	 * Method to decode the run length encoded string.
	 * 
	 * @param input
	 * @return
	 */
	public static String decode(String input) {
		// Initialize a string buffer to store the decoded string
		StringBuffer decodedString = new StringBuffer();

		// REGEX pattern to find tokens like Number or Alphabets
		Pattern pattern = Pattern.compile("[0-9]+|[a-zA-Z]");
		// Match the pattern on input string.
		Matcher matcher = pattern.matcher(input);
		// While the pattern matches
		while (matcher.find()) {
			try {
				// If the first group is of type integer parse and find the next group to
				// find the character
				int count = Integer.parseInt(matcher.group());
				matcher.find();
				// For the count times append the next character
				while (count-- != 0) {
					decodedString.append(matcher.group());
				}
			} catch (NumberFormatException ex) {
				// No count associated with this group of characters
				decodedString.append(matcher.group());
			}
		}
		// return decoded string.
		return decodedString.toString();
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
