package com.palvai.bit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SingleNumber {
	public static int singleNumber2(final List<Integer> A) {

		int res = 0;
		for (int i = 0; i < A.size(); i++) {
			res ^= A.get(i);
		}

		return res;
	}

	public static int singleNumber3(final List<Integer> A) {
		int ones = 0, twos = 0, three = 0;
		int n = A.size();
		for (int a : A) {
			twos = twos | (ones & a);
			ones = ones ^ a;
			three = ~(twos & ones);
			ones = ones & three;
			twos = twos & three;
		}
		return ones;
	}

	public static long reverse(long a) {

		long rev = 0;

		for (int i = 0; i < 32; i++) {
			rev <<= 1;
			if ((a & (1 << i)) != 0)
				rev |= 1;
		}

		return rev;
	}

	public int divide(int dividend, int divisor) {
		if (divisor == 0)
			return Integer.MAX_VALUE;
		long ans = ((long) dividend) / divisor, num = Integer.MAX_VALUE;
		if (ans > num)
			return (int) num;
		return (int) ans;
	}

	public static void main(String args[]) {
		List<Integer> arrayList = new ArrayList<Integer>(Arrays.asList(890, 992, 172, 479, 973, 901, 417, 215, 901, 283,
		    788, 102, 726, 609, 379, 587, 630, 283, 10, 707, 203, 417, 382, 601, 713, 290, 489, 374, 203, 680, 108, 463,
		    290, 290, 382, 886, 584, 406, 809, 601, 176, 11, 554, 801, 166, 303, 308, 319, 172, 619, 400, 885, 203, 463,
		    303, 303, 885, 308, 460, 283, 406, 64, 584, 973, 572, 194, 383, 630, 395, 901, 992, 973, 938, 609, 938, 382,
		    169, 707, 680, 965, 726, 726, 890, 383, 172, 102, 10, 308, 10, 102, 587, 809, 460, 379, 713, 890, 463, 108, 108,
		    811, 176, 169, 313, 886, 400, 319, 22, 885, 572, 64, 120, 619, 313, 3, 460, 713, 811, 965, 479, 3, 247, 886,
		    120, 707, 120, 176, 374, 609, 395, 811, 406, 809, 801, 554, 3, 194, 11, 587, 169, 215, 313, 319, 554, 379, 788,
		    194, 630, 601, 965, 417, 788, 479, 64, 22, 22, 489, 166, 938, 66, 801, 374, 66, 619, 489, 215, 584, 383, 66,
		    680, 395, 400, 166, 572, 11, 992));
		System.out.println(SingleNumber.singleNumber3(arrayList));
		System.out.println(SingleNumber.reverse(3));
	}
}
