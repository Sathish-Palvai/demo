package com.palvai.stack;

import java.util.Stack;

public class RectArea {
	// The main function to find the maximum rectangular area under given
	// histogram with n bars
	static int getMaxArea(int hist[], int n) {
		Stack<Integer> s = new Stack<>();

		int maxArea = 0; // Initialize max area
		int top; // To store top of stack
		int topArea; // To store area with top bar as the smallest bar

		// Run through all bars of given histogram
		int i = 0;
		while (i < n) {
			if (s.empty() || hist[s.peek()] <= hist[i]) {
				s.push(i++);
				System.out.println(s.toString());
			} else {
				top = s.peek(); // store the top index
				s.pop(); // pop the top

				topArea = hist[top] * (s.empty() ? i : i - s.peek() - 1);

				if (maxArea < topArea)
					maxArea = topArea;

				System.out.println("Max Area " + maxArea);
			}
		}

		while (s.empty() == false) {
			top = s.peek();
			s.pop();
			topArea = hist[top] * (s.empty() ? i : i - s.peek() - 1);

			if (maxArea < topArea)
				maxArea = topArea;
		}

		return maxArea;

	}

	// Driver program to test above function
	public static void main(String[] args) {
		int hist[] = { 6, 2, 5, 4, 6, 1, 4, 7, 8, 9, 1 };
		System.out.println("Maximum area is " + getMaxArea(hist, hist.length));
	}
}
