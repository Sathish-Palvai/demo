package com.palvai.lists;

public class SingleLinkedList {

	private Node root;
	private Node reverse;

	public SingleLinkedList() {
		this.root = null;
	}

	Node reverse() {
		Node prev = null;
		Node current = this.root;
		Node next = null;
		while (current != null) {
			next = current.getNextNode();
			current.setNextNode(prev);
			prev = current;
			current = next;
		}
		this.root = prev;
		return this.root;
	}

	Node newReverseList() {

		Node current = this.root;
		Node prev = null;
		while (current != null) {
			reverse = new Node();
			reverse.setValue(current.getValue());
			reverse.setNextNode(prev);
			prev = reverse;
			current = current.getNextNode();
		}
		reverse = prev;
		return this.reverse;
	}

	/**
	 * The new node is always inserted before the root. Make new node as root node.
	 * 
	 * @param value
	 */
	public void insert(int value) {

		// Create a new Node with given value
		Node newNode = new Node(value);
		// Set next node of new node as root
		newNode.setNextNode(root);
		// Point the root to new node.
		root = newNode;
	}

	/**
	 * Insert new node with given value after the previous node.
	 * 
	 * @param previousNode
	 * @param value
	 */
	public void insertAfter(Node previousNode, int value) {

		// If the given previous node is null return with error message
		if (previousNode == null) {
			System.out.println("The given previous node cannot be null");
			return;
		}

		// create a new node with given value
		Node newNode = new Node(value);
		// set new nodes next node as previous node next node
		newNode.setNextNode(previousNode.getNextNode());
		// set the previous node next node as new node.
		previousNode.setNextNode(newNode);

	}

	/**
	 * Add new node at the end of list.
	 * 
	 * @param value
	 */
	public void appendNode(int value) {

		// If root is null, make new node as root node and return.
		if (root == null) {
			root = new Node(value);
			return;
		}
		// Point iterator to root
		Node iterator = root;
		// Traverse the linked list while not iterator is not null
		while (iterator.getNextNode() != null) {
			iterator = iterator.getNextNode();
		}
		// When iterator is at the last node make the new node
		// as last node next node
		iterator.setNextNode(new Node(value));
	}

	/**
	 * Delete a node in linked list with given key
	 * 
	 * @param key
	 */
	public void deleteNode(int key) {

		Node currentNode = root, previousNode = null;

		// Handle the case where node to delete is the root node
		if (currentNode != null && currentNode.getValue() == key) {
			currentNode = currentNode.getNextNode();
			return;
		}

		// Traverse the linked list maintaining the current node and previous node
		while (currentNode != null && currentNode.getValue() != key) {
			previousNode = currentNode;
			currentNode = currentNode.getNextNode();
		}

		// Current node is not null means key found
		if (currentNode != null) {
			previousNode.setNextNode(currentNode.getNextNode());
		}
	}

	/**
	 * Delete node at given position
	 * 
	 * @param position
	 */
	void deleteNodeAtPosition(int position) {

		// If linked list is empty
		if (root == null)
			return;

		Node currentNode = root;

		// If root node has to be removed
		if (position == 0) {
			root = currentNode.getNextNode();
			return;
		}

		// Find previous node of the node to be deleted
		for (int i = 0; currentNode != null && i < position - 1; i++)
			currentNode = currentNode.getNextNode();

		// If position is more than number of nodes
		if (currentNode == null || currentNode.getNextNode() == null)
			return;

		// Node temp->next is the node to be deleted
		// Store pointer to the next of node to be deleted
		Node next = currentNode.getNextNode().getNextNode();

		currentNode.setNextNode(next); // Unlink the deleted node from list
	}

	/**
	 * 
	 * @param value
	 * @return
	 */
	public boolean search(int value) {
		Node current = root;
		while (current != null) {
			if (current.getValue() == value) {
				// data found
				return true;
			}
			current = current.getNextNode();
		}
		// data not found
		return false;
	}

	/**
	 * 
	 * @return
	 */
	public int getCount() {
		Node temp = root;
		int count = 0;
		while (temp != null) {
			count++;
			temp = temp.getNextNode();
		}
		return count;
	}

	/**
	 * 
	 * @param node
	 * @return
	 */
	public int getCountRec(Node node) {
		// Base case
		if (node == null)
			return 0;

		// Count is this node plus rest of the list
		return 1 + getCountRec(node.getNextNode());
	}

	/* Wrapper over getCountRec() */
	public int getCountWrapper() {
		return getCountRec(root);
	}

	/**
	 * Floyd’s Cycle-Finding Algorithm
	 * 
	 * @return
	 */
	int detectLoop() {
		Node slowPointer = root, fastPointer = root;
		while (slowPointer != null && fastPointer != null && fastPointer.getNextNode() != null) {
			slowPointer = slowPointer.getNextNode();
			fastPointer = fastPointer.getNextNode().getNextNode();
			if (slowPointer == fastPointer) {
				System.out.println("Found loop");
				return 1;
			}
		}
		return 0;
	}

	public Node rotateRight(Node A, int B) {

		Node temp = A;
		
		int count = 1;
		while (temp.getNextNode() != null) {
			count++;
			temp = temp.getNextNode();
		}
		int itr = count - B % count;

		temp.setNextNode(A);
		Node breakLink = A;
		while (--itr > 0) {
			breakLink = breakLink.getNextNode();
		}

		A = breakLink.getNextNode();
		breakLink.setNextNode(null);

		return A;

	}

	// Function that detects loop in the list
	int detectAndRemoveLoop(Node node) {
		Node slow = node, fast = node;
		while (slow != null && fast != null && fast.getNextNode() != null) {
			slow = slow.getNextNode();
			fast = fast.getNextNode().getNextNode();

			// If slow and fast meet at same point then loop is present
			if (slow == fast) {
				removeLoop(slow, node);
				return 1;
			}
		}
		return 0;
	}

	// Function to remove loop
	void removeLoop(Node loop, Node curr) {
		Node ptr1 = null, ptr2 = null;

		/*
		 * Set a pointer to the beging of the Linked List and move it one by one to find
		 * the first node which is part of the Linked List
		 */
		ptr1 = curr;
		while (true) {

			/*
			 * Now start a pointer from loop_node and check if it ever reaches ptr2
			 */
			ptr2 = loop;
			while (ptr2.getNextNode() != loop && ptr2.getNextNode() != ptr1) {
				ptr2 = ptr2.getNextNode();
			}

			/*
			 * If ptr2 reahced ptr1 then there is a loop. So break the loop
			 */
			if (ptr2.getNextNode() == ptr1) {
				break;
			}

			/* If ptr2 did't reach ptr1 then try the next node after ptr1 */
			ptr1 = ptr1.getNextNode();
		}

		/*
		 * After the end of loop ptr2 is the last node of the loop. So make next of ptr2
		 * as NULL
		 */
		ptr2.setNextNode(null);
	}

	// Function to remove loop
	void removeLoop2(Node loop, Node head) {
		Node ptr1 = loop;
		Node ptr2 = loop;

		// Count the number of nodes in loop
		int k = 1, i;
		while (ptr1.getNextNode() != ptr2) {
			ptr1 = ptr1.getNextNode();
			k++;
		}

		// Fix one pointer to head
		ptr1 = head;

		// And the other pointer to k nodes after head
		ptr2 = head;
		for (i = 0; i < k; i++) {
			ptr2 = ptr2.getNextNode();
		}

		/*
		 * Move both pointers at the same pace, they will meet at loop starting node
		 */
		while (ptr2 != ptr1) {
			ptr1 = ptr1.getNextNode();
			ptr2 = ptr2.getNextNode();
		}

		// Get pointer to the last node
		ptr2 = ptr2.getNextNode();
		while (ptr2.getNextNode() != ptr1) {
			ptr2 = ptr2.getNextNode();
		}

		/*
		 * Set the next node of the loop ending node to fix the loop
		 */
		ptr2.setNextNode(null);
	}

	// Function that detects loop in the list
	void detectAndRemoveLoop3(Node node) {

		// If list is empty or has only one node
		// without loop
		if (node == null || node.getNextNode() == null)
			return;

		Node slow = node, fast = node;

		// Move slow and fast 1 and 2 steps
		// ahead respectively.
		slow = slow.getNextNode();
		fast = fast.getNextNode().getNextNode();

		// Search for loop using slow and fast pointers
		while (fast != null && fast.getNextNode() != null) {
			if (slow == fast)
				break;

			slow = slow.getNextNode();
			fast = fast.getNextNode().getNextNode();
		}

		/* If loop exists */
		if (slow == fast) {
			slow = node;
			while (slow.getNextNode() != fast.getNextNode()) {
				slow = slow.getNextNode();
				fast = fast.getNextNode();
			}

			/* since fast->next is the looping point */
			fast.setNextNode(null); /* remove loop */
		}
	}

	public void printList() {
		System.out.println("****Linked List Values ****");
		Node temp = root;
		while (temp != null) {
			System.out.println(temp.getValue());
			temp = temp.getNextNode();
		}

	}

	public void printReverseList() {
		System.out.println("****Reversed Linked List Values ****");
		Node temp = reverse;
		while (temp != null) {
			System.out.println(temp.getValue());
			temp = temp.getNextNode();
		}

	}

	public Node reverseBetween(int B, int C) {

		Node start = root;
		Node prestart = null;
		int count = 1;

		// reach till startpoint
		while (count < B) {
			prestart = start;
			start = start.getNextNode();
			count++;
		}

		// start reversing
		Node current = start;
		Node prev = prestart;
		Node next = prestart;

		while (count <= C) {
			next = current.getNextNode();
			current.setNextNode(prev);
			prev = current;
			current = next;
			count++;
		}

		System.out.println("Start " + start.getValue());
		System.out.println("next " + next.getValue());
		System.out.println("prev " + prev.getValue());
		// System.out.println("prestart " + prestart.getValue());

		// manage endpoint pointers and head
		start.setNextNode(next);
		if (prestart == null) {
			root = prev;
		} else {
			prestart.setNextNode(prev);
		}

		return root;
	}

	public Node addTwoNumbers(Node A, Node B) {
		int carry = 0;
		Node res = null, temp = null, prev = null;
		while (A != null || B != null) {
			int sum = carry + (A != null ? A.getValue() : 0) + (B != null ? B.getValue() : 0);
			if (sum >= 10) {
				carry = 1;
				sum = sum - 10;
			} else {
				carry = 0;
			}

			// Create a new node with sum as data
			temp = new Node(sum);

			// if this is the first node then set it as head of
			// the resultant list
			if (res == null) {
				res = temp;
			} else // If this is not the first node then connect it to the rest.
			{
				prev.setNextNode(temp);
			}

			// Set prev for next insertion
			prev = temp;

			A = (A != null) ? A.getNextNode() : A;
			B = (B != null) ? B.getNextNode() : B;

		}

		if (carry > 0) {
			temp.setNextNode(new Node(carry));
		}
		return res;
	}

	public Node detectCycle(Node a) {

		Node slowPointer = root;
		Node fastPointer = root;

		while (slowPointer != null && fastPointer != null && fastPointer.getNextNode() != null) {
			slowPointer = slowPointer.getNextNode();
			fastPointer = fastPointer.getNextNode().getNextNode();
			if (slowPointer == fastPointer) {
				return slowPointer;
			}
		}

		return null;
	}

	public static void main(String args[]) {

		// sl.insert(4);
		// sl.insert(5);
		// sl.insert(6);
		// sl.printList();
		// sl.deleteNode(5);

		// sl.printList();

		// sl.reverse();

		// sl.printList();

		// sl.newReverseList();

		// sl.printReverseList();

		// sl.printList();

		// sl.printReverseList();
	}

}
