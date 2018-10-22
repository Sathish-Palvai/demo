package com.palvai.stack;

import java.util.Stack;

public class MatchingParanthesis {

	boolean isMatchingPair(char character1, char character2) {
		if (character1 == '(' && character2 == ')')
			return true;
		else if (character1 == '{' && character2 == '}')
			return true;
		else if (character1 == '[' && character2 == ']')
			return true;
		else
			return false;
	}

	public int isValid(String B) {
		char[] A = B.toCharArray();
		Stack<Character> st = new Stack<>();

		for (int i = 0; i < A.length; i++) {

			if (A[i] == '{' || A[i] == '(' || A[i] == '[')
				st.push(A[i]);

			if (A[i] == '}' || A[i] == ')' || A[i] == ']') {

				if (st.isEmpty()) {
					return 0;
				}

				else if (!isMatchingPair((char) st.pop(), A[i])) {
					return 0;
				}
			}

		}

		if (st.isEmpty())
			return 1;
		else {
			return 0;
		}
	}
}