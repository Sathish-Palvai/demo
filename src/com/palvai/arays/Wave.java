package com.palvai.arays;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * 
 * Yes there is an O(n) solution available. Basically you make two pass on the array.
In the first pass, pick two index per iteration. If input[i] > input[i+1] then swap it.
Now in the second pass, starting index is 1 and we check if input[i]<input[i+1] then we swap.
Here is the algorithm
for (i = 0; i+1<arr.size; i+=2) {
if (arr[i]>arr[i+1) { swap(i, i+1) } } for (i = 1; i+1<arr.size; i+=2) { if(input[i]<input[i+1]) {
swap(i, i+1)
}
}

Why this algorithm works?
Lets say we have input{a, b, c, d}
After first pass, we know that a< b and c < d
In the second pass, if b > c then we already have a wave array now and we need not do anything. If however, b < c, then we swap them so array becomes {a, c, b, d}
Now, since a < b and b < c, therefore a < c and similarly since d > c and c > b therefore d > b
 * @author USSAPAL
 *
 */

public class Wave {
	public ArrayList<Integer> wave(ArrayList<Integer> A) {

		Collections.sort(A);
		int n = A.size();
		ArrayList<Integer> temp = new ArrayList<>();
		int i, j;
		for (i = 0, j = i + 1; i < n - 1 && j < n - 1; i = i + 2, j = j + 2) {
			temp.add(A.get(j));
			temp.add(A.get(i));
		}

		System.out.println(i + " , " + j);
		if (n == 1) {
			temp.add(A.get(0));
		} else if (n == 2) {
			temp.add(A.get(1));
			temp.add(A.get(0));
			
		} else {
			if (n % 2 == 0) {
				temp.add(A.get(j));
				temp.add(A.get(i));
			} else {
				temp.add(A.get(i));
			}
		}

		return temp;
	}

	public static void main(String args[]) {
		ArrayList<Integer> temp = new ArrayList<Integer>(Arrays.asList(1, 3, 2, 4));

		Wave object = new Wave();
		System.out.println(object.wave(temp));

	}
}
