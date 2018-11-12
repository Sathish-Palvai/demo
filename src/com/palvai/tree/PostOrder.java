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

	/* function to print level order traversal of tree */
	void printLevelOrder(TreeNode root) {
		int h = height(root);
		int i;
		for (i = 1; i <= h; i++)
			printGivenLevel(root, i);
	}

	/*
	 * Compute the "height" of a tree -- the number of nodes along the longest path
	 * from the root node down to the farthest leaf node.
	 */
	int height(TreeNode root) {
		if (root == null)
			return 0;
		else {
			/* compute height of each subtree */
			int lheight = height(root.left);
			int rheight = height(root.right);

			/* use the larger one */
			if (lheight > rheight)
				return (lheight + 1);
			else
				return (rheight + 1);
		}
	}

	/* Print nodes at the given level */
	void printGivenLevel(TreeNode root, int level) {
		if (root == null)
			return;
		if (level == 1)
			System.out.print(root.val + " ");
		else if (level > 1) {
			printGivenLevel(root.left, level - 1);
			printGivenLevel(root.right, level - 1);
		}
	}

}
