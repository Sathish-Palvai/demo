package com.palvai.stack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

public class ReversePolishNotation {

	/*
	 * ["2", "1", "+", "3", "*"] -> ((2 + 1) * 3) -> 9 ["4", "13", "5", "/", "+"] ->
	 * (4 + (13 / 5)) -> 6
	 */

	// Get Infix for a given postfix
	// expression
	public static int evalRPN(ArrayList<String> A) {
		{
			Stack<String> s = new Stack<String>();
			Integer op1, op2;
			for (String element : A) {

				switch (element) {

				case "*":
					op1 = Integer.parseInt(s.pop());
					op2 = Integer.parseInt(s.pop());
					s.push("" + (op1 * op2));
					break;
				case "+":
					op1 = Integer.parseInt(s.pop());
					op2 = Integer.parseInt(s.pop());
					s.push("" + (op1 + op2));
					break;
				case "/":
					op1 = Integer.parseInt(s.pop());
					op2 = Integer.parseInt(s.pop());
					s.push("" + (op2 / op1));
					break;
				case "-":
					op1 = Integer.parseInt(s.pop());
					op2 = Integer.parseInt(s.pop());
					s.push("" + (op2 - op1));
					break;

				default:
					System.out.println("pushing element " + element);
					s.push(element);

				}
			}

			return Integer.parseInt(s.peek());
		}
	}

	// Driver code
	public static void main(String args[]) {
		ArrayList<String> obj = new ArrayList<String>(Arrays.asList("5", "1", "2", "+", "4", "*", "+", "3", "-"));
		System.out.println(ReversePolishNotation.evalRPN(obj));
	}

}
