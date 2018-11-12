package com.palvai.tree;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Preorder (Root, Left, Right) : 1 2 4 5 3
 * 
 * @author USSAPAL
 *
 */
public class PreOrder {

	void printPreorder(TreeNode node) {
		if (node == null)
			return;

		System.out.print(node.val + " ");

		// first recur on left subtree
		printPreorder(node.left);

		// then recur on right subtree
		printPreorder(node.right);

		// now deal with the node

	}

	// An iterative process to print preorder traversal of Binary tree
	public ArrayList<Integer> preorderTraversal(TreeNode A) {

		ArrayList<Integer> returnList = new ArrayList<Integer>();
		if (A == null) {
			return returnList;
		}
		Stack<TreeNode> stack = new Stack<TreeNode>();
		stack.push(A);
		while (!stack.isEmpty()) {
			TreeNode curr = stack.peek();
			returnList.add(curr.val);
			stack.pop();

			if (curr.right != null) {
				stack.push(curr.right);
			}
			if (curr.left != null) {
				stack.push(curr.left);
			}
		}

		return returnList;
	}

}
