package com.palvai.stack;

import java.util.Stack;

public class MinStack {

	private Stack<Integer> mainStack;
	private Stack<Integer> minStack;

	public MinStack() {
		this.mainStack = new Stack<>();
		this.minStack = new Stack<>();
	}

	public void push(int x) {

		// push the new item onto the stack
		mainStack.push(x);

		// first item should be same in both stacks
		if (mainStack.size() == 1) {
			minStack.push(x);
			return;
		}

		// if the item is the smallest one so far: we insert it onto the stack

		if (x < minStack.peek()) {
			minStack.push(x);
		} else {
			// if not the smallest one then we duplicate the smallest one on the maxStack
			minStack.push(minStack.peek());
		}
		// System.out.println("After Pushing");
	}

	public void pop() {
		if (minStack.size() != 0) {
			minStack.pop();
			mainStack.pop();
		}
	}

	public int top() {
		if (minStack.size() == 0)
			return -1;
		else
			return mainStack.peek();

	}

	public int getMin() {
		if (minStack.size() == 0)
			return -1;
		else
			return minStack.peek();
	}
}
