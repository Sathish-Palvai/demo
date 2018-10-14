package com.palvai.arays;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MaxSubArray {

	public ArrayList<Integer> maxset(ArrayList<Integer> A) {

		int listSize = A.size();
		long total = 0;
		long prevTotal = -1;
		ArrayList<Integer> prevMaxSubArrayList = new ArrayList<Integer>();
		ArrayList<Integer> maxSubArrayList = new ArrayList<Integer>();

		for (int i = 0; i < listSize; i++) {
			int currValue = A.get(i);
			if (currValue > 0) {
				maxSubArrayList.add(currValue);
				total = total + currValue;
			} else {
				if (total > prevTotal) {
					prevTotal = total;
					prevMaxSubArrayList = maxSubArrayList;
				}
				total = 0;
				maxSubArrayList = new ArrayList<Integer>();
			}

		}

		if (total > prevTotal) {
			prevTotal = total;
			prevMaxSubArrayList = maxSubArrayList;
		}

		return prevMaxSubArrayList;

	}
	
	
	 public int maximumGap(final List<Integer> A) {
     int maxDist = -1;
     int[] largestAfter = new int[A.size()];
     int max = A.get(A.size()-1);
     for (int i = A.size(); --i >= 0;) {
         if (max < A.get(i)) {
             max = A.get(i);
         }
         largestAfter[i] = max;
     }
     int min = A.get(0);
     int maxi = 0;
     for (int i = 0; i < A.size(); i++) {
         if (min < A.get(i))
             continue;
         min = A.get(i);
         while (maxi < largestAfter.length && largestAfter[maxi] >= min)
             maxi++;
         if (maxDist < maxi - i - 1)
             maxDist = maxi - i - 1;
     }
     return maxDist;
 }

	public static void main(String args[]) {
		ArrayList<Integer> input = new ArrayList<>(Arrays.asList(1967513926, 1540383426, -1303455736, -521595368));
		//ArrayList<Integer> input = new ArrayList<>(Arrays.asList(1, 2, 5, -7, 2, 3, 2));
		MaxSubArray obj = new MaxSubArray();
		ArrayList<Integer> output = obj.maxset(input);
		System.out.println(output);
	}
}
