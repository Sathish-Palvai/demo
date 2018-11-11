package com.palvai.hash;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class LargestContinuosSequence {
	// Input: {1 ,2 ,-2 ,4 ,-4}
	// Output: {2 ,-2 ,4 ,-4}
	public static int lszero(List<Integer> A) {

		int max_len = 0;

		int size = A.size();
		// Pick a starting point
		for (int i = 0; i < size; i++) {
			// Initialize curr_sum for every
			// starting point
			int curr_sum = 0;

			// try all subarrays starting with 'i'
			for (int j = i; j < size; j++) {
				curr_sum += A.get(j);

				// If curr_sum becomes 0, then update
				// max_len
				if (curr_sum == 0)
					max_len = Math.max(max_len, j - i + 1);
			}

		}
		return max_len;
	}

	public static int maxLen(List<Integer> A) {
		HashMap<Integer, Integer> hM = new HashMap<Integer, Integer>();

		int sum = 0; // Initialize sum of elements
		int max_len = 0; // Initialize result
		int maxIndex = 0;
		int beginIndex = 0;
		// Traverse through the given array
		for (int i = 0; i < A.size(); i++) {
			// Add current element to sum
			sum += A.get(i);

			if (A.get(i) == 0 && max_len == 0)
				max_len = 1;

			if (sum == 0)
				max_len = i + 1;

			// Look this sum in hash table
			Integer prev_i = hM.get(sum);

			// If this sum is seen before, then update max_len
			// if required
			if (prev_i != null) {
				if (max_len == 0 && beginIndex == 0) {
					beginIndex = i - 1;
				}
				max_len = Math.max(max_len, i - prev_i);
				maxIndex = i - 1;

			} else // Else put this sum in hash table
				hM.put(sum, i);
		}
		System.out.println("beginIndex" + beginIndex);
		System.out.println("maxIndex " + maxIndex);
		return max_len;
	}

	public static ArrayList<Integer> lszero3(List<Integer> a) {

		ArrayList<Integer> ans = new ArrayList<Integer>();

		int sum = 0;

		HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
		map.put(0, -1);
		int start = 0;
		// int end = 0;
		int length = 0;
		for (int i = 0; i < a.size(); i++) {
			sum += a.get(i);
			if (map.containsKey(sum)) {
				int last = map.get(sum);
				if (i - last > length) {
					start = last;
					length = i - last;
				}
			} else {
				map.put(sum, i);
			}
		}

		for (int i = 0; i < length; i++) {
			ans.add(a.get(start + 1 + i));
		}

		return ans;

	}

	public static void main(String args[]) {
		// System.out.println(LargestContinuosSequence.maxLen(Arrays.asList(1, 3, 2, -2,
		// 4, -4)));
		System.out.println(LargestContinuosSequence.lszero3(Arrays.asList(1, 2, -2, 4, -4)));
	}

}
