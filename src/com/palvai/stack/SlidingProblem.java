package com.palvai.stack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class SlidingProblem {


	public static ArrayList<Integer> slidingMaximum(final List<Integer> A, int w) {
		ArrayList<Integer> result = new ArrayList<>();
		// Doubly Ended Queue
		LinkedList<Integer> dq = new LinkedList<>();

		for (int i = 0; i < A.size(); i++) {
			// Remove the first element if there are already w elements present in the queue
			// the left most (first in this case) will not be valid anymore
			if (!dq.isEmpty() && dq.peekFirst() + w == i) {
				dq.removeFirst();
				System.out.println("Remove Called " + i);
			}

			// need to remove the elements from back as long as the current element is
			// bigger because now current element will be part of the window and elements
			// smaller than that won't matter
			while (!dq.isEmpty() && A.get(dq.peekLast()) < A.get(i)) {
				dq.removeLast();
			}
			dq.addLast(i);
			System.out.println(dq.toString());
			// only if the current index is more than the window size, result will start
			// getting filled
			if (i >= w - 1) {
				result.add(A.get(dq.peekFirst()));
				System.out.println("result " + result.toString());
				
			}
			
		}

		return result;
	}
	
	public static void main(String args[]) {
		System.out.println(SlidingProblem.slidingMaximum(Arrays.asList(1, 3, -1, -3, 5, 3, 6, 7), 3));
	}
}
