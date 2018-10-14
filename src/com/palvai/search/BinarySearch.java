package com.palvai.search;

public class BinarySearch {

	public static int binarySearch(int a[], int searchVal) {
		int size = a.length;
		int start = 0;
		int end = size - 1;

		while (start <= end) {
			int mid = (start + end) / 2;

			if (a[mid] == searchVal) {
				return mid;
			}

			if (searchVal > a[mid]) {
				start = mid + 1;
			} else {
				end = mid - 1;
			}

		}

		return -1;
	}

	public static void main(String args[]) {

		int a[] = { 1, 3, 5, 7, 8, 9, 11, 13 };
		
		System.out.println(BinarySearch.binarySearch(a, 1));
	}

}
