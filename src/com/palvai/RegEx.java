package com.palvai;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegEx {

	public static void main(String args[]) {
		String regEx = "^[0-9]\\d{0,9}(\\.\\d{1,3})?%?$";
		String input = "1.445";
		
		//^\s*(?=.*[1-9])\d*(?:\.\d{1,2})?\s*$

		Pattern pattern = Pattern.compile(regEx);
		// in case you would like to ignore case sensitivity,
		// you could use this statement:
		// Pattern pattern = Pattern.compile("\\s+", Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(input);
		
		
		// check all occurance
		while (matcher.find()) {
			System.out.print("Start index: " + matcher.start());
			System.out.print(" End index: " + matcher.end() + " ");
			System.out.println(matcher.group());
		}
	}

}
