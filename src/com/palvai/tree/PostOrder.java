package com.palvai.tree;

import java.util.ArrayList;
import java.util.Stack;

public class PostOrder {

	void printPostorder(TreeNode node) {
		if (node == null)
			return;

		// first recur on left subtree
		printPostorder(node.left);

		// then recur on right subtree
		printPostorder(node.right);

		// now deal with the node
		System.out.print(node.val + " ");
	}

	public ArrayList<Integer> postorderTraversal(TreeNode A) {

		ArrayList<Integer> list = new ArrayList<Integer>();
		Stack<TreeNode> S = new Stack<TreeNode>();
		if (A == null)
			return list;
		S.push(A);
		TreeNode prev = null;
		while (!S.isEmpty()) {
			TreeNode current = S.peek();

			/*
			 * go down the tree in search of a leaf an if so process it and pop stack
			 * otherwise move down
			 */
			if (prev == null || prev.left == current || prev.right == current) {
				if (current.left != null)
					S.push(current.left);
				else if (current.right != null)
					S.push(current.right);
				else {
					S.pop();
					list.add(current.val);
				}

				/*
				 * go up the tree from left node, if the child is right push it onto stack
				 * otherwise process parent and pop stack
				 */
			} else if (current.left == prev) {
				if (current.right != null)
					S.push(current.right);
				else {
					S.pop();
					list.add(current.val);
				}

				/*
				 * go up the tree from right node and after coming back from right node process
				 * parent and pop stack
				 */
			} else if (current.right == prev) {
				S.pop();
				list.add(current.val);
			}

			prev = current;
		}

		return list;

	}

}
