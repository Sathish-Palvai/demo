package com.palvai.sorting.complex.merge;

public class MergeSort {

	private int[] nums;
	private int[] tempArray;

	public MergeSort(int[] nums) {
		this.nums = nums;
		tempArray = new int[nums.length];
	}

	public void mergeSort(int low, int high) {

		if (low >= high) {
			return;
		}

		int middle = (low + high) / 2;

		// Divide left part
		mergeSort(low, middle);
		// Divide right part
		mergeSort(middle + 1, high);
		// Conquer Part
		merge(low, middle, high);
	}

	public void showResult() {
		for (int i = 0; i < nums.length; ++i) {
			System.out.print(nums[i] + " ");
		}
	}

	private void merge(int low, int middle, int high) {

		// Copy nums[i] -> temp[i]
		for (int i = low; i <= high; i++) {
			tempArray[i] = nums[i];
		}

		// Index i for left sub array, Index j fro right sub array
		// Index k for tempArray
		int i = low;
		int j = middle + 1;
		int k = low;

		// Copy the smallest values from either the left or the right side back
		// to the original array comparing the current value of left and right sub array

		// loop while the left or right sub array index reaches their length
		while ((i <= middle) && (j <= high)) {
			if (tempArray[i] <= tempArray[j]) {
				nums[k] = tempArray[i];
				i++;
			} else {
				nums[k] = tempArray[j];
				j++;
			}

			k++;
		}

		// Copy the rest of the left side of the array into the target array
		while (i <= middle) {
			nums[k] = tempArray[i];
			k++;
			i++;
		}
		while (j <= middle) {
			nums[k] = tempArray[j];
			k++;
			j++;
		}
	}

	public static void main(String args[]) {
		MergeSort obj = new MergeSort(new int[] { 1, 2, 3, 5, 4, 7, 8 });
		obj.mergeSort(0, 6);
		obj.showResult();
	}
}
