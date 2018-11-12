package com.palvai.graph;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;

public class MultipleOfN {
	// Return the smallest multiple of the number (as a string) consisting only of
	// digits 0 and 1
	// All possible digits that can be constructed using the digits 0 and 1 can be
	// traversed
	// as a tree, where at each level, appending a 0 is one branch and appending a 1
	// is another
	//
	// If we perform BFS on this tree, the first number we see which is an exact
	// multiple of the input
	// number will be the result
	//
	// The 2 paths we take at each level when the current number is num:
	// (num * 10)
	// (num * 10) + 1
	//
	// Since the number can grow huge quite easily, it might not be possible to
	// store the result in a
	// 32 or even a 64 bit integer/long.
	//
	// One alternative is to use BigNumber, but a simpler alternative exists using
	// modulo
	//
	// The operation we perform above (i.e. multiplications and additions) still
	// work when using modulo
	// Since we use the given number itself as the modulo, when we see a modulo
	// result of 0, it will imply
	// we've hit the result during BFS
	//
	// To reconstruct the number, we need to store the parents when adding the node
	// in the queue (similar to
	// using BFS for computing shortest path)
	//
	// We will also need to know if we appended a 0 or a 1 at each step, and so we
	// store this information
	// as well in the node
	//
	// Also we need not re-visit nodes when we have seen the (value % num) already.
	// The reason being any
	// additional digits we add from now will only make the number longer and we
	// already are tracking
	// the path for this same modulo result we've seen earlier.
	//
	public static String multiple(int num) {
		if (num < 0) {
			throw new IllegalArgumentException("Invalid args");
		}

		String result = "0";

		if (num > 0) {
			// A set to store all the visited nodes
			boolean[] isVisited = new boolean[num];
			Arrays.fill(isVisited, false);

			Set<Integer> visitedSet = new HashSet<>();
			// The queue used by BFS
			Queue<Node> queue = new ArrayDeque<>();

			// Add the first number 1 and mark it visited
			queue.add(new Node(true, 1 % num, null));
			isVisited[1 % num] = true;

			// The final destination node which represents the answer
			Node destNode = null;

			while (!queue.isEmpty()) {
				// Get the next node from the queue
				Node currNode = queue.remove();

				if (currNode.val == 0) {
					// We have reached a valid multiple of num
					destNode = currNode;
					break;
				} else {
					// Visit the next 2 neighbors
					// Append 0 - (currNode.val * 10)
					// Append 1 - (currNode.val * 10) + 1

					// Append a '0'
					int val1 = (currNode.val * 10) % num;
					if (!isVisited[val1]) {
						queue.add(new Node(false, val1, currNode));
						isVisited[val1] = true;
					}

					// Append a '1'
					int val2 = (val1 + 1);
					if (val2 == num) {
						val2 = 0;
					}
					if (!isVisited[val2]) {
						queue.add(new Node(true, val2, currNode));
						isVisited[val2] = true;
					}
				}
			}

			// Trace the path from destination to source
			if (destNode == null) {
				throw new IllegalStateException("Result should not be null");
			} else {
				StringBuilder reverseResultBuilder = new StringBuilder();
				Node currNode = destNode;
				while (currNode != null) {
					reverseResultBuilder.append(currNode.isDigitOne ? '1' : '0');
					currNode = currNode.prev;
				}
				result = reverseResultBuilder.reverse().toString();
			}
		}

		return result;
	}

	private static class Node {
		public final boolean isDigitOne;
		public final int val;
		public final Node prev;

		public Node(boolean isDigitOne, int val, Node prev) {
			this.isDigitOne = isDigitOne;
			this.val = val;
			this.prev = prev;
		}
	}

	public static void main(String[] args) {
		int num = Integer.valueOf(args[0]);
		System.out.println("Input number: " + num);
		System.out.println("Smallest multiple using only 0s and 1s as digits: " + MultipleOfN.multiple(num));
	}

}
