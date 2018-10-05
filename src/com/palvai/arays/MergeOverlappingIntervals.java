package com.palvai.arays;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Stack;

public class MergeOverlappingIntervals {

	public static void mergeIntervals(Interval arr[], int n) {
		// Test if the given set has at least one interval
		if (n <= 0)
			return;

		Stack<Interval> s = new Stack<Interval>();
		// Create an empty stack of intervals

		Arrays.sort(arr, new Comparator<Interval>() {
			public int compare(Interval i1, Interval i2) {
				return i1.getStart() - i2.getStart();
			}
		});
		
		for(int index = 0; index < arr.length; index++) {
		  System.out.println(arr[index].toString());
		}

		// push the first interval to stack
		s.push(arr[0]);

		// Start from the next interval and merge if necessary
		for (int i = 1; i < n; i++) {
			// get interval from stack top
			System.out.println("i = " + i);
			if (!s.isEmpty()) {
				Interval top = s.peek();

				// if current interval is not overlapping with stack top,
				// push it to the stack
				if (top.getEnd() < arr[i].getStart())
					s.push(arr[i]);

				// Otherwise update the ending time of top if ending of current
				// interval is more
				else if (top.getEnd() < arr[i].getEnd()) {
					top.setEnd(arr[i].getEnd());
				}
			}
		}

		// Print contents of stack
		System.out.println("\n The Merged Intervals are: ");
		while (!s.empty()) {
			Interval t = s.peek();
			System.out.println(t);
			s.pop();
		}
		return;
	}

	public static void main(String args[]) {
		List<Interval> intervals = new ArrayList<Interval>();
		intervals.add(new Interval(9, 12));
		intervals.add(new Interval(13, 14));
		intervals.add(new Interval(6, 8));
		intervals.add(new Interval(1, 9));
		intervals.add(new Interval(2, 4));
		intervals.add(new Interval(4, 7));
		mergeIntervals(intervals.toArray(new Interval[intervals.size()]), intervals.size());

	}

}
