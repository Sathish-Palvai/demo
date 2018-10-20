package com.palvai.strings;

import java.util.ArrayList;

public class RestoreIpAddress {

	/**
	 * Make sure s is of length of 4 and 12
	 * 
	 * @param s
	 * @return
	 */
	public static ArrayList<String> restoreIpAddresses(String s) {
		ArrayList<String> res = new ArrayList<String>();
		if (s.length() < 4 || s.length() > 12)
			return res;
		dfs(s, "", res, 0);
		return res;
	}

	public static void dfs(String s, String tmp, ArrayList<String> res, int count) {
		if (count == 3 && isValid(s)) {
			res.add(tmp + s);
			return;
		}
		for (int i = 1; i < 4 && i < s.length(); i++) {
			System.out.println("i = " + i);
			String substr = s.substring(0, i);

			if (isValid(substr)) {
				System.out.println(tmp + substr + '.');
				dfs(s.substring(i), tmp + substr + '.', res, count + 1);
			}
		}
	}

	public static boolean isValid(String s) {
		if (s.charAt(0) == '0')
			return s.equals("0");
		int num = Integer.parseInt(s);
		return num <= 255 && num > 0;
	}

	public static void main(String args[]) {
		System.out.println(RestoreIpAddress.restoreIpAddresses("1234"));
	}

	public ArrayList<String> restoreIpAddresses2(String A) {
		ArrayList<String> res = new ArrayList<>();
		helper(A, 4, res, "");
		return res;
	}

	private void helper(String s, int expectedSize, ArrayList<String> res, String curr) {
		if (expectedSize == 0 && s.isEmpty()) {
			res.add(curr);
			return;
		}
		for (int end = 1; end <= 3; end++) {
			if (s.length() >= end) {
				int val = Integer.parseInt(s.substring(0, end));
				if (val >= 0 && val < 256 && String.valueOf(val).length() == end) {
					helper(s.substring(end), expectedSize - 1, res,
					    !curr.isEmpty() ? curr + '.' + s.substring(0, end) : s.substring(0, end));
				}
			}

		}
	}

}
