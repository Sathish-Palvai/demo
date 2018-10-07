package com.palvai.strings;

public class StringToInteger {

	public static void main(String args[]) {
		String input = "j123";
		// String input = "-123";
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

		System.out.println(startIndex == 1 ? -(finalNumber) : finalNumber);

	}

}
