package com.palvai.strings;

import java.util.HashMap;
import java.util.Map;

public class StringToInteger {

	public static void main(String args[]) {
		System.out.println(atoi("-5433287 4 54"));

	}

	public static int atoiSimple(String input) {
		char[] inputArray = input.toCharArray();
		int inputLength = inputArray.length;
		int startIndex = inputArray[0] == '-' ? 1 : 0;
		int multiplier = inputLength - startIndex;
		int finalNumber = 0;
		for (int i = startIndex; i < inputLength; i++) {
			int currentNumber = inputArray[i] - 48;
			if (currentNumber > 9) {
				throw new NumberFormatException("Wrong input format");
			}
			finalNumber = finalNumber + currentNumber * (int) Math.pow(10, --multiplier);
		}

		int returnVal = startIndex == 1 ? -(finalNumber) : finalNumber;
		return returnVal;
	}

	public static int atoiComplex(String input) {

		String firtNumber = findFirstIntegerString2(input);
		System.out.println("firtNumber " + firtNumber);
		if (firtNumber.length() == 0) {
			return 0;
		}
		char[] inputArray = firtNumber.toCharArray();
		int inputLength = inputArray.length;
		int startIndex = inputArray[0] == '-' || inputArray[0] == '+' ? 1 : 0;
		int multiplier = inputLength - startIndex;
		long finalNumber = 0;
		for (int i = startIndex; i < inputLength; i++) {
			int currentNumber = inputArray[i] - 48;
			finalNumber = finalNumber + currentNumber * (long) Math.pow(10, --multiplier);
		}

		long returnVal = startIndex == 1 ? -(finalNumber) : finalNumber;
		System.out.println(returnVal > Integer.MAX_VALUE);
		if (returnVal > 0 && returnVal > Integer.MAX_VALUE) {
			return Integer.MAX_VALUE;
		} else if (returnVal < 0 && returnVal > Integer.MIN_VALUE) {
			return Integer.MIN_VALUE;
		} else {
			return (int) returnVal;
		}
	}

	public static String findFirstIntegerString(final String a) {
		if (a.length() == 0) {
			return "";
		}

		Map<Character, Integer> charMap = new HashMap<Character, Integer>();
		charMap.put('-', 45);
		charMap.put('+', 43);
		charMap.put('0', 48);
		charMap.put('1', 49);
		charMap.put('2', 50);
		charMap.put('3', 51);
		charMap.put('4', 52);
		charMap.put('5', 53);
		charMap.put('6', 54);
		charMap.put('7', 55);
		charMap.put('8', 56);
		charMap.put('9', 57);

		boolean intFound = false;
		char ch = ' ';
		StringBuilder firstInt = new StringBuilder();
		for (int i = 0; i < a.length(); i++) {
			ch = a.charAt(i);
			int currentNumber = ch - 48;
			if ((ch == '-' || ch == '+') && firstInt.length() < 1 && i + 1 < a.length()) {
				int nextNumber = a.charAt(i + 1) - 48;
				// System.out.println("Next Number : " + nextNumber);
				if ((nextNumber >= 0 && nextNumber <= 9)) {
					firstInt.append(ch);
					if (nextNumber == 0) {
						i++;
						continue;
					} else {
						continue;
					}
				} else {
					return "";
				}
			}
			if (!(currentNumber > 0 && currentNumber <= 9) && intFound) {
				break;
			}

			if (currentNumber > 0 && currentNumber <= 9) {
				firstInt.append(currentNumber);
				intFound = true;
			}
			System.out.println("currentNumber  : " + currentNumber);

			if (currentNumber < 0 || currentNumber > 9) {
				break;
			}
		}
		return firstInt.toString();
	}

	public static String findFirstIntegerString2(final String a) {

		if (a.length() == 0) {
			return "";
		}

		Map<Character, Integer> charMap = new HashMap<Character, Integer>();
		charMap.put('-', 45);
		charMap.put('+', 43);
		charMap.put('0', 48);
		charMap.put('1', 49);
		charMap.put('2', 50);
		charMap.put('3', 51);
		charMap.put('4', 52);
		charMap.put('5', 53);
		charMap.put('6', 54);
		charMap.put('7', 55);
		charMap.put('8', 56);
		charMap.put('9', 57);
		charMap.put(' ', 32);

		boolean numStarted = false;
		StringBuilder str = new StringBuilder();
		int len = a.length();
		int i = 0;
		while (i < len) {
			char tCh = a.charAt(i);
			Integer tInt = charMap.get(tCh);

			if (charMap.get(tCh) != null) {

				if ((tCh == '-' || tCh == '+') && !numStarted) {
					if (tCh == '-') {
						str.append(tCh);
					}
				} else if ((tCh == '-' || tCh == '+') && numStarted) {
					return "";
				}

				if (numStarted && tCh == ' ') {
					return str.toString();
				}

				if (tInt >= 48 && tInt <= 57) {
					str.append(tCh);
					numStarted = true;
				}

			} else {
				return "";
			}

			i++;

		}

		return str.toString();
	}

	public static int atoi(final String A) {
		int idx;
		long num;
		int n = A.length();
		boolean sign = true;

		idx = 0;

		while (idx < n && A.charAt(idx) == ' ')
			idx++;

		if (idx == n)
			return 0;

		if (A.charAt(idx) == '-') {
			sign = false;
			idx++;
		} else if (A.charAt(idx) == '+') {
			idx++;
		}

		num = 0;

		while (idx < n && A.charAt(idx) >= '0' && A.charAt(idx) <= '9') {

			num = Math.abs(num);
			num = num * 10 + A.charAt(idx) - '0';

			if (!sign)
				num = -num;

			if (num > Integer.MAX_VALUE)
				return Integer.MAX_VALUE;

			else if (num < Integer.MIN_VALUE)
				return Integer.MIN_VALUE;

			idx++;

		}

		return (int) num;

	}

}
