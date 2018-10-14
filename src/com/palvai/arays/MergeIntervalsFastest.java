package com.palvai.arays;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class Interval1 {
	int start;
	int end;

	Interval1() {
		start = 0;
		end = 0;
	}

	Interval1(int s, int e) {
		start = s;
		end = e;
	}
}

public class MergeIntervalsFastest {
	public ArrayList<Interval1> insert(ArrayList<Interval1> intervals, Interval1 newInterval) {
		List<Interval1> overlappingIntervals = new ArrayList<>();

		overlappingIntervals = intervals.stream().filter(interval -> isOverLappingInterval(interval, newInterval))
		    .collect(java.util.stream.Collectors.toList());

		Interval1 mergedInterval = merge(overlappingIntervals, newInterval);
		intervals.removeAll(overlappingIntervals);
		intervals.add(mergedInterval);

		Collections.sort(intervals, (interval1, interval2) -> {
			if (interval1.start > interval2.start)
				return 1;
			else if (interval1.start < interval2.start)
				return -1;
			else
				return 0;
		});
		return new ArrayList<>(intervals);
	}

	private boolean isOverLappingInterval(Interval1 interval, Interval1 newInterval) {
		if (newInterval.end < newInterval.start) {
			int tempVar = newInterval.start;
			newInterval.start = newInterval.end;
			newInterval.end = tempVar;
		}
		if (Math.max(interval.start, newInterval.start) < Math.min(interval.end, newInterval.end)) {
			return true;
		}
		return false;
	}

	private Interval1 merge(List<Interval1> overlappingIntervals, Interval1 newInterval) {
		int start, end;
		if (!overlappingIntervals.isEmpty()) {
			start = Math.min(overlappingIntervals.get(0).start, Math.min(newInterval.start, newInterval.end));
			end = Math.max(overlappingIntervals.get(overlappingIntervals.size() - 1).end,
			    Math.max(newInterval.start, newInterval.end));
		} else {
			start = Math.min(newInterval.start, newInterval.end);
			end = Math.max(newInterval.start, newInterval.end);
		}
		return new Interval1(start, end);

	}
}
