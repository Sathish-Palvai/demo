package com.palvai.strings;

import java.util.Arrays;

public class LongestCommonSubSequence {

	// private static String a = "bd", b = "abcd";
	private static String a = "stone", b = "longest";

	public static int LCSTest(int i, int j) {
		if (i == a.length() || j == b.length())
			return 0;
		else if (a.charAt(i) == b.charAt(j)) {
			return 1 + LCSTest(i + 1, j + 1);

		} else {
			return Math.max(LCSTest(i + 1, j), LCSTest(i, j + 1));
		}
	}

	public static void LCSDynamic() {
		int LCS[][] = new int[a.length() + 1][b.length() + 1];
		int i, j;

		/*
		 * Following steps build L[m+1][n+1] in bottom up fashion. Note that L[i][j]
		 * contains length of LCS of X[0..i-1] and Y[0..j-1]
		 */
		for (i = 0; i <= a.length(); i++) {
			for (j = 0; j <= b.length(); j++) {
				if (i == 0 || j == 0)
					LCS[i][j] = 0;

				else if (a.charAt(i - 1) == b.charAt(j - 1))
					LCS[i][j] = LCS[i - 1][j - 1] + 1;

				else
					LCS[i][j] = Math.max(LCS[i - 1][j], LCS[i][j - 1]);
			}
		}

		for (int[] row : LCS) {
			// converting each row as string
			// and then printing in a separate line
			System.out.println(Arrays.toString(row));
		}
		printLCS(LCS, a.length(), b.length());

	}

	public static void printLCS(int[][] input, int i, int j) {
		StringBuffer path = new StringBuffer();
		while (i > 0 && j > 0) {
			if (a.charAt(i - 1) == b.charAt(j - 1)) {
				// Put current character in result
				path.append(a.charAt(i - 1));

				// reduce values of i, j and index
				i--;
				j--;
			}

			// If not same, then find the larger of two and
			// go in the direction of larger value
			else if (input[i - 1][j] > input[i][j - 1])
				i--;
			else
				j--;
		}

		// Print the lcs
		System.out.println(path.reverse().toString());
	}

	public static void main(String args[]) {
		System.out.println(LongestCommonSubSequence.LCSTest(0, 0));
		LongestCommonSubSequence.LCSDynamic();

	}

}
