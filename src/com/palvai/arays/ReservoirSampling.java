package com.palvai.arays;

import java.util.Random;

public class ReservoirSampling {

	private Random random;

	public ReservoirSampling() {
		this.random = new Random();
	}

	// O(N) algorithm to get k items at random from array nums
	public void solve(int[] nums, int k) {

		// create a new array for k items
		int[] reservoir = new int[k];

		// copy the first k items from the original array
		for (int i = 0; i < reservoir.length; i++)
			reservoir[i] = nums[i];

		// we consider the items from the original array
		// k+1 because we have already copied k items
		// the i-th item is chosen to be included in the reservoir with probability k/i
		for (int i = k + 1; i < nums.length; i++) {
			int j = random.nextInt(i) + 1;
			if (j < k)
				reservoir[j] = nums[i];
		}

		// show the k random items
		for (int i = 0; i < reservoir.length; i++)
			System.out.print(reservoir[i] + " ");
	}

	public static void main(String[] args) {

		int[] nums = { 13, 65, 46, 11, 71, 50, 12, 5, 81, 97, 28, 74, 87, 68, 42, 31, 40, 37, 43, 25 };
		int k = 5;

		ReservoirSampling reservoirSampling = new ReservoirSampling();
		reservoirSampling.solve(nums, k);
	}

}
