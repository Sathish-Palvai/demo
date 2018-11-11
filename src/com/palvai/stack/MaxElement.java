package com.palvai.stack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MaxElement {

	private int[] nums;

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

	public ArrayList<Integer> slidingMaximum(final List<Integer> A, int B) {
		int listSize = A.size();
		ArrayList<Integer> ret = new ArrayList<Integer>();
		Integer[] array = A.toArray(new Integer[listSize]);
		int[] temp = new int[listSize];

		this.nums = temp;

		if (B > listSize) {
			ret.add(this.select(0, listSize - 1, listSize - 1));
			return ret;
		}

		for (int i = 0; i < listSize - B + 1; i++) {
			for (int j = 0; j < array.length; j++) {
				temp[j] = array[j];
			}
			ret.add(this.select(0 + i, B + i - 1, B + i - 1));
		}
		return ret;
	}

	public static void main(String args[]) {
		MaxElement obj = new MaxElement();
		System.out.println(obj.slidingMaximum(Arrays.asList(10, 9, 8, 7, 6, 5, 4, 3, 2, 1), 2));
	}

}
