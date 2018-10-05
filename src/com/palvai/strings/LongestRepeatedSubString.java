package com.palvai.strings;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LongestRepeatedSubString {

	public static List<String> getSuffix(String input) {

		int len = input.length();
		List<String> suffixList = new ArrayList<>();
		for (int i = 0; i < len; i++) {
			suffixList.add(input.substring(i, len));
		}
		return suffixList;
	}

	public static List<String> getPrefix(String input) {

		int len = input.length();
		List<String> prefixList = new ArrayList<>();
		for (int i = 0; i < len; i++) {
			prefixList.add(input.substring(0, i));
		}
		return prefixList;
	}

	public static String reverseString(String input) {
		int len = input.length();
		StringBuilder reverseString = new StringBuilder();
		for (int i = len - 1; i >= 0; i++) {
			reverseString.append(input.charAt(i));
		}
		return reverseString.toString();
	}

	public static String longestCommonPrefix(String inputOne, String inputTwo) {
		int commonLen = Math.min(inputOne.length(), inputTwo.length());

		for (int i = 0; i < commonLen; i++) {
			if (inputOne.charAt(i) != inputTwo.charAt(i)) {
				return inputOne.substring(0, i);
			}
		}
		return inputOne.substring(0, commonLen);

	}

	public static String longestRepeatedSubString(String input) {
		int len = input.length();
		List<String> suffixList = getSuffix(input);
		Collections.sort(suffixList);
		String longestSubString = "";
		for (int i = 0; i < len - 1; i++) {
			String str = longestCommonPrefix(suffixList.get(i), suffixList.get(i + 1));
			if (str.length() > longestSubString.length()) {
				longestSubString = str;
			}
		}
		return longestSubString;
	}

	public static void main(String[] args) {

		System.out.println(LongestRepeatedSubString.longestRepeatedSubString("helloehelloejdjehello"));

	}

}
