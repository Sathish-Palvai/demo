package com.palvai.arays;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/**
 * 
 * Given [3, 30, 34, 5, 9], the largest formed number is 9534330.
 * 
 * @author USSAPAL
 *
 */
public class LargestNumber {

	public String largestNumber(final List<Integer> A) {

		Collections.sort(A, new Comparator<Integer>() {
			@Override
			public int compare(Integer a, Integer b) {
				String X = a.toString();
				String Y = b.toString();
				String XY = X + Y;
				String YX = Y + X;
				return XY.compareTo(YX) > 0 ? -1 : 1;
			}
		});

		StringBuilder temp = new StringBuilder();

		Iterator<Integer> it = A.iterator();

		while (it.hasNext()) {
			temp.append(it.next());
		}

		return temp.toString().startsWith("0") ? "0" : temp.toString();
	}

	public static void main(String args[]) {
		ArrayList<Integer> input = new ArrayList<>(Arrays.asList(3, 30, 34, 5, 9));
		LargestNumber obj = new LargestNumber();
		System.out.println(obj.largestNumber(input));
	}
}
