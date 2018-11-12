package com.palvai.arays;

import java.util.ArrayList;

public class RotateArray {

	public void rotate(ArrayList<ArrayList<Integer>> a) {

		int S = a.size() - 1;

		for (int i = 0; i < a.size() / 2; i++) {

			for (int j = i; j < S - i; ++j) {

				int temp1 = a.get(S - j).get(i);
				int temp2 = a.get(S - i).get(S - j);
				int temp3 = a.get(j).get(S - i);
				int temp4 = a.get(i).get(j);

				// swap
				a.get(i).set(j, temp1);
				a.get(S - j).set(i, temp2);
				a.get(S - i).set(S - j, temp3);
				a.get(j).set(S - i, temp4);
			}
		}
	}

	public void rotate2(ArrayList<ArrayList<Integer>> a) {
		int n = a.size();
		int q1, q2, q3, q4;
		for (int j = 0; j < n / 2; j++) {
			for (int i = j; i < n - j - 1; i++) {
				
				q1 = a.get(j).get(i);
				q2 = a.get(i).get(n - j - 1);
				q3 = a.get(n - 1 - j).get(n - 1 - i);
				q4 = a.get(n - 1 - i).get(j);

				a.get(j).set(i, q4);
				a.get(i).set(n - j - 1, q1);
				a.get(n - 1 - j).set(n - 1 - i, q2);
				a.get(n - 1 - i).set(j, q3);
			}
		}
	}

}
