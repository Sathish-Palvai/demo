package com.palvai.backtrack;

public class SubSet3 {

	Integer[] givenArray = new Integer[] { 1, 2 };

	public void allSubsets() {
		Integer[] subSet = new Integer[givenArray.length];
		helper(subSet, 0);
	}

	public void helper(Integer[] subSet, int i) {
		if (i == givenArray.length) {
			printSet(subSet);
		} else {
			subSet[i] = null;
			int idx = i + 1;
			helper(subSet, idx);
			subSet[i] = givenArray[i];
			int idx2 = i + 1;
			helper(subSet, idx2);
		}
	}

	public void printSet(Integer[] subset) {
		System.out.print("[");
		for (Integer i : subset) {
			if (i != null && subset.length >= 1) {
				System.out.print(i + ",");
			}
		}
		System.out.print(" ]");
	}

	public static void main(String args[]) {
		SubSet3 obj = new SubSet3();
		obj.allSubsets();
	}

}
