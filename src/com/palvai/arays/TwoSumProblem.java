package com.palvai.arays;

import java.util.HashMap;
import java.util.Map;

public class TwoSumProblem {

	// the one-dimensional array in which we store the integers
	private int[] nums;
	// the S target we are after
	private int S;
	private Map<Integer, Integer> hashTable;

	public TwoSumProblem(int[] nums, int S) {
		this.hashTable = new HashMap<>();
		this.nums = nums;
		this.S = S;
	}

	// because of the nested for loop it has O(N^2) quadratic running time
	// complexity
	public void solve() {

		// we consider all the items in the array
		for (int i = 0; i < nums.length; ++i) {
			// for all items we check all other items
			for (int j = 0; j < nums.length; ++j) {

				// and check if two items sum up to S or not
				if (nums[i] + nums[j] == S) {
					System.out.println("Solution: " + nums[i] + "+" + nums[j] + "=" + S);
				}

			}
		}
	}

	// we can eliminate the second for loop with a hash table (running time memory
	// complexity tradeoff)
	public void solveBetter() {

		// consider all the items once so it has O(N) linear running time complexity
		for (int i = 0; i < nums.length; ++i) {

			// check the remainder (actual value - target value)
			int remainder = S - nums[i];

			// check if the remainder exists in the hashtable: it means we have found a pair
			// O(1)
			if (hashTable.containsValue(remainder))
				System.out.println("Solution: " + nums[i] + "+" + remainder + "=" + S);

			// add the current number to the hashtable
			hashTable.put(i, nums[i]);
		}
	}

}
