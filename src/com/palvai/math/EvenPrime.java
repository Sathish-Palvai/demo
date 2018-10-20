package com.palvai.math;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EvenPrime {

	public static ArrayList<Integer> primesum(int A) {
		ArrayList<Integer> temp = new ArrayList<Integer>();

		Map<Integer, Integer> primes = new HashMap<Integer, Integer>();
		boolean isprime = true;
		for (int i = 2; i <= A;) {
			isprime = isPrimeCheck(i);
			if (isprime) {
				//System.out.println("is Prime " + i);
				primes.put(i, i);
				if (primes.get(A - i) != null || isPrimeCheck(A - i)) {

					temp.add(i);
					temp.add(A - i);
					return temp;
				}
				i++;
			} else
				i++;
		}
		return temp;
	}

	public static boolean isPrimeCheck(int num) {
		boolean isPrime = true;
		for (int loop = 2; loop <= num; loop++) {
			if ((num % loop) == 0 && loop != num) {
				isPrime = false;
				break;
			}
		}
		return isPrime;
	}

	public static void main(String args[]) {
		System.out.println(EvenPrime.primesum(10));
	}

}
