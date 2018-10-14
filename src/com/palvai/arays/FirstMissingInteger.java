package com.palvai.arays;

import java.util.ArrayList;
import java.util.Arrays;

public class FirstMissingInteger {

	public int firstMissingPositive(ArrayList<Integer> A) {
		/*
		 * def firstMissingPositive(self, nums): """ :type nums: List[int] :rtype: int
		 * Basic idea: 1. for any array whose length is l, the first missing positive
		 * must be in range [1,...,l+1], so we only have to care about those elements in
		 * this range and remove the rest. 2. we can use the array index as the hash to
		 * restore the frequency of each number within the range [1,...,l+1] """
		 * nums.append(0) n = len(nums) for i in range(len(nums)): #delete those useless
		 * elements if nums[i]<0 or nums[i]>=n: nums[i]=0 for i in range(len(nums)):
		 * #use the index as the hash to record the frequency of each number
		 * nums[nums[i]%n]+=n for i in range(1,len(nums)): if nums[i]/n==0: return i
		 * return n
		 * 
		 */
		int n = A.size();
		for (int i = 0; i < n; i++) {

			// #delete those useless elements if
			int currValue = A.get(i);
			if (currValue < 0 || currValue > n) {
				A.set(i, 0);
				currValue = 0;
			}

			int remainder = currValue % n;
			if (A.get(remainder) < 0 || A.get(remainder) > n) {
				A.set(remainder, 0);
			}
			int result = A.get(remainder) + n;
			// #use the index as the hash to record the frequency of each number
			A.set(remainder, result);
			if (i == 0) {
				continue;
			}

			if (A.get(i) / n == 0) {
				return i;
			}

		}
		return n;
	}

	public int firstMissingPositive(int A[]) {
		/*
		 * def firstMissingPositive(self, nums): """ :type nums: List[int] :rtype: int
		 * Basic idea: 1. for any array whose length is l, the first missing positive
		 * must be in range [1,...,l+1], so we only have to care about those elements in
		 * this range and remove the rest. 2. we can use the array index as the hash to
		 * restore the frequency of each number within the range [1,...,l+1] """
		 * nums.append(0) n = len(nums) for i in range(len(nums)): #delete those useless
		 * elements if nums[i]<0 or nums[i]>=n: nums[i]=0 for i in range(len(nums)):
		 * #use the index as the hash to record the frequency of each number
		 * nums[nums[i]%n]+=n for i in range(1,len(nums)): if nums[i]/n==0: return i
		 * return n
		 * 
		 */
		int n = A.length;
		for (int i = 0; i < n; i++) {
			int currValue = A[i];
			if (currValue < 0 || currValue >= n) {
				System.out.println("This is a test");
				A[i] = 0;
			}
		}

		for (int i = 0; i < n; i++) {
			A[A[i] % n] += n;
			System.out.println(i + " A[A[i] % n] " + A[A[i] % n]);
			if (i == 0)
				continue;
			if (A[i] / n == 0)
				return i;
		}
		return n;
	}

	public int firstMissingPositive2(int A[]) {

		int n = A.length;
		for (int i = 0; i < n; i++) {
			int currValue = A[i];
			if (currValue < 0 || currValue >= n) {
				A[i] = 0;
			}
			currValue = A[A[i] % n];
			if (currValue < 0 || currValue >= n) {
				System.out.println("safda ");
				A[A[i] % n] = 0;
			}

			A[A[i] % n] += n;
			System.out.println(i + " A[A[i] % n] " + A[A[i] % n]);
			
			if (i == 0)
				continue;
			if (A[i] / n == 0)
				return i;
		}
		return n;
	}

	public static void main(String args[]) {
		ArrayList<Integer> input = new ArrayList<>(Arrays.asList(1, 2, 0));
		FirstMissingInteger obj = new FirstMissingInteger();

		int a[] = { 1, 2, 0 };
		System.out.println(obj.firstMissingPositive(input));
		System.out.println(obj.firstMissingPositive(a));
	}

}
