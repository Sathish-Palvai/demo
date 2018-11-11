package com.palvai.hash;

import java.util.HashSet;

public class ColorFulNumber {

	public int colorful(int A) {

		String numStr = String.valueOf(A);

		int n = numStr.length();

		HashSet<Integer> set = new HashSet<>();

		for (int i = 0; i < n; i++) {
			for (int j = i; j < n; j++) {
				int res = 1;

				for (int k = i; k <= j; k++) {
					res *= Integer.parseInt(numStr.substring(k, k + 1));
					System.out.println(res);
				}

				if (set.contains(res))
					return 0;
				else
					set.add(res);
			}
		}

		return 1;
	}
	
	public static void main(String args[]) {
		ColorFulNumber obj = new ColorFulNumber();
		System.out.println(obj.colorful(23));
	}

}
