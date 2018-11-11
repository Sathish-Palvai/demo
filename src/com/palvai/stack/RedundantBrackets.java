package com.palvai.stack;

import java.util.Stack;

public class RedundantBrackets {
	public static int braces(String A) {
		Stack<Character> stack = new Stack<Character>();

		for (char ch : A.toCharArray()) {

			if (ch == ')') {
				char top = stack.pop();
				System.out.println("1 " + stack.toString());
				boolean flag = true;

				while (top != '(') {

					if (top == '+' || top == '-' || top == '*' || top == '/') {
						flag = false;
					}

					top = stack.pop();
				  //System.out.println("2 " + stack.toString());

				}
				
				if (flag == true) {
					return 1;
				}
			}

			else {
				stack.push(ch);
				//System.out.println("3 " + stack.toString());
				 
			}
			
		}
		return 0;
	}

	public static void main(String args[]) {
		System.out.println(RedundantBrackets.braces("(a + ((a + b)))"));
	}

}
