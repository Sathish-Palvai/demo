package com.palvai.stack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

public class PrevSmaller {

	public ArrayList<Integer> prevSmaller(ArrayList<Integer> A) {

		ArrayList<Integer> result = new ArrayList<Integer>();

		Stack<Integer> s = new Stack<Integer>();

		for (int i = 0; i < A.size(); i++) {

			while (!s.empty() && s.peek() >= A.get(i)) {
				s.pop();
			}

			if (s.empty()) {
				result.add(-1);
			} else {
				result.add(s.peek());
			}
			s.push(A.get(i));
		}

		return result;

	}

	public ArrayList<Integer> prevSmaller2(ArrayList<Integer> arr) {
		ArrayList<Integer> retval = new ArrayList<>();
		Stack<Integer> st = new Stack<Integer>();

		for (int i = 0; i < arr.size(); ++i) {
			while (!st.isEmpty() && arr.get(i) <= st.peek()) {
				st.pop();
			}
			if (st.isEmpty()) {
				retval.add(-1);
			} else {
				retval.add(st.peek());
			}
			st.add(arr.get(i));
		}

		return retval;
	}

	public ArrayList<Integer> prevSmaller3(ArrayList<Integer> arr) {
		Stack<Integer> s = new Stack<Integer>();

		int ar[] = new int[arr.size()];
		for (int i = arr.size() - 1; i >= 0; i--) {
			if (!s.empty()) {
				while (arr.get(s.peek()) > arr.get(i)) {
					ar[s.pop()] = arr.get(i);
					if (s.empty())
						break;
				}
				s.push(i);
			} else {
				s.push(i);
			}
		}
		while (!s.empty()) {
			ar[s.pop()] = -1;
		}
		ArrayList<Integer> rs = new ArrayList<Integer>();
		for (int j = 0; j < ar.length; j++)
			rs.add(ar[j]);
		return rs;
	}

	public static void main(String args[]) {
		PrevSmaller obj = new PrevSmaller();
		ArrayList<Integer> list = new ArrayList<>(Arrays.asList(34, 35, 27, 42, 5, 28, 39, 20, 28));
		// [-1, 4, -1, 2, 2]
		System.out.println(obj.prevSmaller(list));

	}

}
