package com.palvai.search;

import java.util.Arrays;

public class QuickSelect {

	private int[] nums;

	public QuickSelect(int[] nums) {
		this.nums = nums;
	}

	public void showArray() {
		for (int i = 0; i < nums.length; ++i) {
			System.out.print(nums[i] + "  ");
		}
	}

	private int partition(int low, int high) {

		int pivotIndex = (low + high) / 2; // random index maybe better
		swap(pivotIndex, high); // set the pivot to the end of list

		int i = low;

		for (int j = low; j < high; ++j) {
			if (nums[j] <= nums[high]) {
				swap(i, j);
				i++;
			}
		}

		swap(i, high); // swap back the pivot

		return i;
	}

	private void swap(int i, int j) {
		int temp = nums[i];
		nums[i] = nums[j];
		nums[j] = temp;
	}

	public int select(int left, int right, int k) {

		if (left == right) {
			return nums[left];
		}
		int pivotIndex = partition(left, right);
		if (k == pivotIndex) {
			return nums[k];
		} else if (k < pivotIndex) {
			right = pivotIndex - 1;
			return select(left, right, k);
		} else {
			left = pivotIndex + 1;
			return select(left, right, k);
		}
	}

	public static void main(String args[]) {
		int a[] = { 23, 45, 21, 55, 22, 56, 76, 89, 33 };

		// 21 22 23 33 45 55
		QuickSelect obj = new QuickSelect(a);
		System.out.println("4 th largest element " + obj.select(0, 8, 8));
		Arrays.sort(a);
		System.out.println(Arrays.toString(a));
	}

}
