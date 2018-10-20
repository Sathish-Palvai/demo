package com.palvai.strings;

public class Palindrome {

	public static String countAndSay(int n) {

		String str = "1";

		for (int i = 0; i < n; i++) {
			char prev = str.charAt(0);
			int count = 1;
			StringBuilder temp = new StringBuilder();
			for (int j = 1; j < str.length(); j++) {
				if (prev == str.charAt(j)) {
					count++;
				} else {
					temp.append(count);
					temp.append(prev);
					count = 1;
					prev = str.charAt(j);
				}
			}
			temp.append(count);
			temp.append(prev);

			// System.out.println(temp.toString());
			str = new String(temp.toString());
		}

		return str;
	}

	public int isPalindrome(String A) {
		int n = A.length();
		// A man, a plan, a canal: Panama
		StringBuilder newString = new StringBuilder();
		for (int i = 0; i < n; i++) {
			int ch = A.charAt(i);
			if (ch >= 65 && ch <= 90) {
				newString.append((char) ch);
			} else if (ch >= 97 && ch <= 122) {
				newString.append((char) (ch - 32));
			} else if (ch >= 48 && ch <= 57) {
				newString.append(A.charAt(i));
			}
		}
		String str = newString.toString();
		n = str.length();
		// System.out.println("New String " + newString.toString());
		for (int i = 0; i < n; i++) {
			if (!(str.charAt(i) == str.charAt(n - i - 1))) {
				return 0;
			}
		}
		return 1;
	}

	public int lengthOfLastWord(final String A) {

		StringBuffer prevWord = new StringBuffer();
		StringBuffer newWord = new StringBuffer();
		for (int i = 0; i < A.length(); i++) {
			char next = A.charAt(i);
			if (next != ' ') {
				newWord.append(next);
			} else {
				if (newWord.toString().length() != 0) {
					prevWord = new StringBuffer();
					prevWord.append(newWord.toString());
				}
				newWord = new StringBuffer();
			}
		}

		String returnVal = newWord.toString();
		// System.out.println(returnVal);
		if (returnVal.length() == 0) {
			returnVal = prevWord.toString();
			// System.out.println("xxx " + returnVal);
		}

		return returnVal.length();
	}

	public int lengthOfLastWordFastest(final String a) {
		if (a.length() == 0) {
			return 0;
		}
		int ans = 0;
		boolean wordFound = false;
		for (int i = a.length() - 1; i >= 0; i--) {
			if (a.charAt(i) == ' ' && wordFound) {
				break;
			}
			if (a.charAt(i) != ' ') {
				ans++;
				wordFound = true;
			}
		}
		return ans;
	}

	public static String reverseWord(final String a) {
		if (a.length() == 0 || a.length() == 1) {
			return a;
		}
		StringBuilder reversedSentence = new StringBuilder();
		StringBuilder word = new StringBuilder();
		boolean wordFound = false;

		for (int i = a.length() - 1; i >= 0; i--) {
			char currChar = a.charAt(i);
			if (i == 0 && currChar != ' ') {
				if (wordFound) {
					word.append(currChar);
					currChar = ' ';
				} else {
					word = new StringBuilder();
					word.append(currChar);
					wordFound = true;
					currChar = ' ';
				}

			}
			if ((currChar == ' ') & wordFound) {

				reversedSentence.append(word.reverse().toString());
				reversedSentence.append(i != 0 ? " " : "");
				wordFound = false;
				word = new StringBuilder();
			}
			if (currChar != ' ') {
				word.append(currChar);
				wordFound = true;
			}
		}
		return reversedSentence.toString();
	}

	/**
	 * How many characters are required to make a string palindrome.
	 * 
	 * @param A
	 * @return
	 */
	public int solve(String A) {
		int n = A.length();
		int ans = n;
		while (n > 1 && !isPalindrome(A, n)) {
			n--;
		}
		return ans - n;
	}

	// Method to find if a given string is palindrome
	public boolean isPalindrome(String A, int len) {
		int i = 0, j = len - 1;
		while (i <= j && (A.charAt(i) == A.charAt(j))) {
			i++;
			j--;
		}
		if (i > j) {
			return true;
		}
		return false;
	}

	void computeLPS2(String s, int[] lps) {
		int m = s.length();
		int len = 0;
		lps[0] = 0;
		int i = 1;
		while (i < m) {
			if (s.charAt(i) == s.charAt(len)) {
				len++;
				lps[i] = len;
				i++;
			} else {
				if (len != 0) {
					len = lps[len - 1];
				} else {
					lps[i] = 0;
					i++;
				}
			}
		}
	}

	public int solve2(String a) {
		StringBuilder s = new StringBuilder(a);
		s = s.reverse();
		String str = a + "$" + s.toString();
		int[] lps = new int[str.length()];
		computeLPS2(str, lps);
		return (a.length() - lps[str.length() - 1]);
	}

	public static String addBinary(String A, String B) {
		String result = "";

		int s = 0;

		int i = A.length() - 1, j = B.length() - 1;
		while (i >= 0 || j >= 0 || s == 1) {

			s += ((i >= 0) ? A.charAt(i) - '0' : 0);
			s += ((j >= 0) ? B.charAt(j) - '0' : 0);

			result = (char) (s % 2 + '0') + result;

			s /= 2;

			i--;
			j--;
		}

		return result;
	}

	public static void multiplyTwoString(String A, String B) {

		char[] n1 = A.toCharArray();
		char[] n2 = B.toCharArray();

		int result = 0;

		for (int i = 0; i < n1.length; i++) {
			for (int j = 0; j < n2.length; j++) {
				result += (n1[i] - '0') * (n2[j] - '0') * (int) Math.pow(10, n1.length + n2.length - (i + j + 2));
			}
		}
		System.out.println(result);
	}

	public static void main(String args[]) {
		// System.out.println(Palindrome.countAndSay(3));

		multiplyTwoString("4154", "51454");
	}

}
