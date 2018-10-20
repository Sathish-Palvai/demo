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

		Math.sqrt(3);

		return -1;
	}

	public static int sqrt(int a) {

		if (a == 0 || a == 1) {
			return a;
		}

		int result = 0;
		long start = 1, end = a;
		long mid = 0;
		while (start < end) {
			mid = (start + end) / 2;

			if (mid * mid == a)
				return (int) mid;

			if (mid * mid < a) {
				start = mid + 1;
				result = (int) mid;
			} else {
				end = mid;
			}

		}
		return result;

	}

	public static int pow(int x, int n, int d) {
		if (n == 0)
			;

		long a = x;
		long out = 1L;

		while (n > 0) {

			if (n % 2 == 1) {
				out *= a;
				out %= d;
			}

			a *= a;
			a %= d;
			n = n >> 1;

		}

		out = (out + d) % d;

		return (int) out;

	}

	public static void main(String args[]) {

		int a[] = { 1, 3, 5, 7, 8, 9, 11, 13 };

		// System.out.println(BinarySearch.binarySearch(a, 1));

		System.out.println(BinarySearch.sqrt(5));
	}

}
