package com.palvai.arays;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Stack;

public class MergeIntervalInterviewBit {
	public ArrayList<Interval> insert(ArrayList<Interval> intervals, Interval newInterval) {

		ArrayList<Interval> mergedIntervals = new ArrayList<Interval>();
		intervals.add(newInterval);
		Stack<Interval> stack = new Stack<Interval>();
		Collections.sort(intervals, new Comparator<Interval>() {
			public int compare(Interval i1, Interval i2) {
				return i1.start - i2.start;
			}
		});

		stack.push(intervals.get(0));

		for (int i = 1; i < intervals.size(); i++) {

			if (!stack.isEmpty()) {
				Interval top = stack.peek();
				if (top.end < intervals.get(i).start) {
					stack.push(intervals.get(i));
				} else if (top.end < intervals.get(i).end) {
					top.end = intervals.get(i).end;
				}

			}
		}

		while (!stack.empty()) {
			Interval t = stack.peek();
			mergedIntervals.add(t);

			stack.pop();
		}
		Collections.reverse(mergedIntervals);

		return mergedIntervals;

	}

	class Interval {
		int start;
		int end;

		Interval() {
			start = 0;
			end = 0;
		}

		Interval(int s, int e) {
			start = s;
			end = e;
		}
	}
}