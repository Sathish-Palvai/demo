package com.palvai.hash;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class TwoSum {

	public ArrayList<Integer> twoSum(final List<Integer> A, int B) {

		HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();

		ArrayList<Integer> ans = new ArrayList<Integer>();

		for (int i = 0; i < A.size(); i++) {
			int curr = A.get(i);

			if (map.containsKey(B - curr)) {
				int index = map.get(B - curr);
				ans.add(index);
				ans.add(i + 1);
				return ans;
			} else if (!map.containsKey(curr)) {
				map.put(curr, i + 1);
			}
		}

		return ans;

	}

	public List<List<Integer>> calc() {
		List<List<Integer>> result = new ArrayList<List<Integer>>();
		return result;

	}

	public static void main(String args[]) {
		TwoSum obj = new TwoSum();
		obj.twoSum(Arrays.asList(2, 7, 11, 15), 9);
		List<List<Integer>> retVal = new ArrayList<List<Integer>>();

	}

}
