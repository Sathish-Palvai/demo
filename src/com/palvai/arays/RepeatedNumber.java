package com.palvai.arays;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RepeatedNumber {
	// DO NOT MODIFY THE LIST
	public ArrayList<Integer> repeatedNumber(final List<Integer> a) {
		int arraySize = a.size();
		long sumOfNumbers = getSumOfNumbers(arraySize);
		System.out.println("sumOfNumbers " + sumOfNumbers);
		long sumOfSquares = getSumOfSquares(arraySize);
		System.out.println("sumOfSquares " + sumOfSquares);
		long differenceNumber = getDifferenceofNumbers(a, sumOfNumbers);
		System.out.println("differenceNumber " + differenceNumber);
		long differenceSquare = getDifferenceofSquares(a, sumOfSquares);
		System.out.println("differenceSquare " + differenceSquare);

		long sumNumber = differenceSquare / differenceNumber;
		System.out.println("sumNumber " + sumNumber);
		int repeatedNumber = (int) ((sumNumber + differenceNumber) / 2);
		System.out.println("repeatedNumber " + repeatedNumber);

		int missingNumber = (int) (sumNumber - repeatedNumber);
		System.out.println("missingNumber " + missingNumber);

		ArrayList<Integer> result = new ArrayList<>();
		result.add(repeatedNumber);
		result.add(missingNumber);
		return result;
	}

	private long getDifferenceofNumbers(List<Integer> a, long sumOfNumbers) {
		long sum = sumOfNumbers * -1;
		for (Integer number : a) {
			long num = (long) number;
			sum += num;
			;
		}
		return sum;
	}

	private long getDifferenceofSquares(List<Integer> a, long sumOfSquares) {
		long sumSquares = sumOfSquares * -1;
		for (Integer number : a) {
			long num = (long) number;
			sumSquares += (num * num);
		}
		return sumSquares;
	}

	private long getSumOfNumbers(double n) {
		return (long) (n * (n - 1) / 2 + n);
	}

	private long getSumOfSquares(double n) {
		return (long) (n * (n + 1) * (2 * n + 1) / 6);
	}
	
	public static void main(String args[]) {
		ArrayList<Integer> list = new ArrayList<>(Arrays.asList(3, 1, 2, 5, 3));
		RepeatedNumber object = new RepeatedNumber();
		object.repeatedNumber(list);
	}
}

/**
 * Input:[3 1 2 5 3]
 * 
 * Output:[3, 4]
 * 
 * A = 3, B = 4
 */
