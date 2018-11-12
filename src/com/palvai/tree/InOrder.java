package com.palvai.tree;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Depth First Traversals: (a) Inorder (Left, Root, Right) : 4 2 5 1 3 (b)
 * Preorder (Root, Left, Right) : 1 2 4 5 3 (c) Postorder (Left, Right, Root) :
 * 4 5 2 3 1
 * 
 * Breadth First or Level Order Traversal : 1 2 3 4 5 Please see this post for
 * Breadth First Traversal.
 * 
 * @author USSAPAL
 *
 */

class TreeNode {
	int val;
	TreeNode left;
	TreeNode right;

	TreeNode(int x) {
		val = x;
		left = null;
		right = null;
	}
}

public class InOrder {

	public ArrayList<Integer> inorderTraversal(TreeNode A) {
		Stack<TreeNode> s = new Stack<TreeNode>();
		TreeNode curr = A;
		ArrayList<Integer> list = new ArrayList<Integer>();

		// traverse the tree
		while (curr != null || s.size() > 0) {

			/*
			 * Reach the left most TreeNode of the curr TreeNode
			 */
			while (curr != null) {
				/*
				 * place pointer to a tree TreeNode on the stack before traversing the
				 * TreeNode's left subtree
				 */
				s.push(curr);
				curr = curr.left;
			}

			/* Current must be NULL at this point */
			curr = s.pop();

			list.add(curr.val);

			/*
			 * we have visited the TreeNode and its left subtree. Now, it's right subtree's
			 * turn
			 */
			curr = curr.right;
		}

		return list;
	}

	void printInorder(TreeNode node) {
		if (node == null)
			return;

		/* first recur on left child */
		printInorder(node.left);

		/* then print the data of node */
		System.out.print(node.val + " ");

		/* now recur on right child */
		printInorder(node.right);
	}

}
