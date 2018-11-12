package com.palvai.tree;

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
	void iterativePreorder(TreeNode node) {

		if (node == null) {
			return;
		}

		Stack<TreeNode> nodeStack = new Stack<TreeNode>();
		nodeStack.push(node);

		while (nodeStack.empty() == false) {

			// Pop the top item from stack and print it
			TreeNode mynode = nodeStack.peek();
			System.out.print(mynode.val + " ");
			nodeStack.pop();

			// Push right and left children of the popped node to stack
			if (mynode.right != null) {
				nodeStack.push(mynode.right);
			}
			if (mynode.left != null) {
				nodeStack.push(mynode.left);
			}
		}
	}

}
