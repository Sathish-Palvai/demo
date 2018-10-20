package com.palvai.strings;

public class IsNumber {

	public int isNumber(final String a) {
		return (a.matches("\\s*[\\-\\+]?([0-9]*\\.?)?[0-9]+(e[\\-\\+]?[0-9]+)?\\s*")) ? 1 : 0;
	}

	public int isNumber2(final String s) {
		int digits = 0, dots = 0, i = 0, n = s.length(), j = s.length() - 1;
		while (i < n && s.charAt(i) == ' ')
			i++;
		if (i == n)
			return 0;
		String a = s.substring(i);
		i = 0;
		n = a.length();
		if (i < n && s.charAt(i) == '-' || s.charAt(i) == '+')
			i++;
		if (a.length() == 0 || a == null)
			return 0;
		while ((a.charAt(i) >= '0' && a.charAt(i) <= '9') || a.charAt(i) == '.') {
			if (a.charAt(i) >= '0' && a.charAt(i) <= '9')
				digits++;
			else
				dots++;
			i++;
			if (i >= n)
				break;
		}
		if (digits < 1 || dots > 1 || a.charAt(i - 1) == '.') {
			return 0;
		}
		if (i < n && a.charAt(i) == 'e') {
			i++;
			if (i < n && a.charAt(i) == '-' || a.charAt(i) == '+')
				i++;
			if (i >= n)
				return 0;
			digits = 0;
			while ((a.charAt(i) >= '0' && a.charAt(i) <= '9')) {
				digits++;
				i++;
				if (i >= n)
					break;
			}
			if (digits < 1)
				return 0;
		}
		while (i < n && a.charAt(i) == ' ')
			i++;
		return i == n ? 1 : 0;
	}

	public int isNumber3(final String s) {
		String str;
		str = s.trim(); // trims the white spaces.

		if (str.length() == 0)
			return 0;

		// if string is of length 1 and the only
		// character is not a digit
		if (str.length() == 1 && !Character.isDigit(str.charAt(0)))
			return 0;

		// If the 1st char is not '+', '-', '.' or digit
		if (str.charAt(0) != '+' && str.charAt(0) != '-' && !Character.isDigit(str.charAt(0)) && str.charAt(0) != '.')
			return 0;

		// To check if a '.' or 'e' is found in given
		// string. We use this flag to make sure that
		// either of them appear only once.
		boolean flagDotOrE = false;

		for (int i = 1; i < str.length(); i++) {
			// If any of the char does not belong to
			// {digit, +, -, ., e}
			if (!Character.isDigit(str.charAt(i)) && str.charAt(i) != 'e' && str.charAt(i) != '.' && str.charAt(i) != '+'
			    && str.charAt(i) != '-')
				return 0;

			if (str.charAt(i) == '.') {
				// checks if the char 'e' has already
				// occurred before '.' If yes, return 0.
				if (flagDotOrE == true)
					return 0;

				// If '.' is the last character.
				if (i + 1 >= str.length())
					return 0;

				// if '.' is not followed by a digit.
				if (!Character.isDigit(str.charAt(i + 1)))
					return 0;
			}

			else if (str.charAt(i) == 'e') {
				// set flagDotOrE = 1 when e is encountered.
				flagDotOrE = true;

				// if there is no digit before 'e'.
				if (!Character.isDigit(str.charAt(i - 1)))
					return 0;

				// If 'e' is the last Character
				if (i + 1 >= str.length())
					return 0;

				// if e is not followed either by '.',
				// '+', '-' or a digit
				if (!Character.isDigit(str.charAt(i + 1)) && str.charAt(i + 1) != '+' && str.charAt(i + 1) != '-')
					return 0;
			}
		}

		/*
		 * If the string skips all above cases, then it is numeric
		 */
		return 1;
	}

}
