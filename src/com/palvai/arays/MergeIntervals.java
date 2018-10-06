package com.palvai.arays;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MergeIntervals {

	/**
	 * Definition for an interval. public class Interval { int start; int end;
	 * Interval() { start = 0; end = 0; } Interval(int s, int e) { start = s; end =
	 * e; } }
	 */
	public static ArrayList<Interval> insert(ArrayList<Interval> intervals, Interval newInterval) {

		Interval firstInterval = intervals.get(0);
		Interval lastInterval = intervals.get(intervals.size() - 1);
		ArrayList<Interval> result = new ArrayList<Interval>();

		if (newInterval.getEnd() < firstInterval.getStart()) {
			result.add(newInterval);
			result.addAll(intervals);
			return result;
		}

		if (lastInterval.getEnd() < newInterval.getStart()) {
			intervals.add(newInterval);
			return intervals;
		}

		if (newInterval.getStart() <= firstInterval.getStart() && newInterval.getEnd() >= lastInterval.getEnd()) {
			result.add(newInterval);
			return result;
		}

		// Case 4 and Case 5
		// These two cases need to check whether
		// intervals overlap or not. For this we
		// can use a subroutine that will perform
		// this function.
		boolean overlap = true;
		int size = intervals.size();
		for (int i = 0; i < intervals.size(); i++) {

			Interval interval = intervals.get(i);

			overlap = doesOverlap(interval, newInterval);
			if (!overlap) {
				result.add(interval);

				// Case 4 : To check if given interval
				// lies between two intervals.
				if (i < size - 1) {
					Interval nextInterval = intervals.get(i + 1);
					if (newInterval.getStart() > interval.getEnd() && newInterval.getEnd() < nextInterval.getStart()) {
						result.add(newInterval);
					}
				}

				continue;

			}

			// Case 5 : Merge Overlapping Intervals.
			// Starting time of new merged interval is
			// minimum of starting time of both
			// overlapping intervals.
			Interval temp = new Interval();
			temp.setStart(Math.min(newInterval.getStart(), intervals.get(i).getStart()));

			// Traverse the set until intervals are
			// overlapping
			while ((i < size - 1) && overlap) {

				// Ending time of new merged interval
				// is maximum of ending time both
				// overlapping intervals.
				temp.setEnd(Math.max(newInterval.getEnd(), intervals.get(i).getEnd()));
				if (i == size - 1)
					overlap = false;
				else
					overlap = doesOverlap(intervals.get(i + 1), newInterval);
				i++;
			}

			i--;
			result.add(temp);

		}

		return result;
	}

	private static boolean doesOverlap(Interval a, Interval b) {
		return (Math.min(a.getEnd(), b.getEnd()) >= Math.max(a.getStart(), b.getStart()));
	}

	public static List<Interval> merge(List<Interval> intervals) {
		List<Interval> result = new ArrayList<Interval>();

		if (intervals == null || intervals.size() == 0)
			return result;

		Collections.sort(intervals, new Comparator<Interval>() {
			public int compare(Interval i1, Interval i2) {
				if (i1.getStart() != i2.getStart())
					return i1.getStart() - i2.getStart();
				else
					return i1.getEnd() - i2.getEnd();
			}
		});

		Interval pre = intervals.get(0);
		for (int i = 0; i < intervals.size(); i++) {
			Interval curr = intervals.get(i);
			if (curr.getStart() > pre.getEnd()) {
				result.add(pre);
				pre = curr;
			} else {
				Interval merged = new Interval(pre.getStart(), Math.max(pre.getEnd(), curr.getEnd()));
				pre = merged;
			}
		}
		result.add(pre);

		return result;
	}

	public static void main(String args[]) {
		/*
		 * [1,3],[6,9]
		 * 
		 * [2,5] would result in [1,5],[6,9].
		 * 
		 * Given [1,2],[3,5],[6,7],[8,10],[12,16], insert and merge [4,9] would result
		 * in [1,2],[3,10],[12,16].
		 */
		Interval int1 = new Interval(1, 3);
		Interval int2 = new Interval(6, 9);
		Interval int3 = new Interval(2, 5);
		ArrayList<Interval> list = new ArrayList<Interval>();
		list.add(int1);
		list.add(int2);
		// list.add(int3);

		// System.out.println(MergeIntervals.merge(list));
		System.out.println(MergeIntervals.insert(list, int3));

	}

}
