package com.palvai.lists;

public class SingleLinkedList {

	private Node root;

	public SingleLinkedList() {
		this.root = null;
	}

	/**
	 * The new node is always added before the head of the given LinkedList. And new
	 * node becomes the new root of the LinkedList.
	 * 
	 * @param value
	 */
	public void insert(int value) {

		Node newNode = new Node(value);
		newNode.setNextNode(root);
		root = newNode;
	}

	public void insertAfter(Node prevNode, int value) {

		if (prevNode == null) {
			System.out.println("The given previous node cannot be null");
			return;
		}

		Node newNode = new Node(value);
		newNode.setNextNode(prevNode.getNextNode());
		prevNode.setNextNode(newNode);

	}

	public void appendNode(int value) {
		if (root == null) {
			root = new Node(value);
			return;
		}
		Node iterator = root;
		while (iterator.getNextNode() != null) {
			iterator = iterator.getNextNode();
		}
		iterator.setNextNode(new Node(value));
		return;
	}

	public void deleteNode(int key) {
		Node curr = root, prev = null;
		if (curr != null && curr.getValue() == key) {
			curr = curr.getNextNode();
			return;
		}

		while (curr != null && curr.getValue() != key) {
			prev = curr;
			curr = curr.getNextNode();
		}

		if (curr == null) {
			return;
		}

		prev.setNextNode(curr.getNextNode());
	}

	void deleteNodeAtPosition(int position) {
		// If linked list is empty
		if (root == null)
			return;

		// Store head node
		Node curr = root;

		// If head needs to be removed
		if (position == 0) {
			root = curr.getNextNode(); // Change head
			return;
		}

		// Find previous node of the node to be deleted
		for (int i = 0; curr != null && i < position - 1; i++)
			curr = curr.getNextNode();

		// If position is more than number of ndoes
		if (curr == null || curr.getNextNode() == null)
			return;

		// Node temp->next is the node to be deleted
		// Store pointer to the next of node to be deleted
		Node next = curr.getNextNode().getNextNode();

		curr.setNextNode(next); // Unlink the deleted node from list
	}

	public void printList() {
		System.out.println("****Linked List Values ****");
		Node temp = root;
		while (temp != null) {
			System.out.println(temp.getValue());
			temp = temp.getNextNode();
		}

	}

	public static void main(String args[]) {
		SingleLinkedList sl = new SingleLinkedList();
		sl.insert(4);
		sl.insert(5);
		sl.insert(6);
		sl.printList();
		sl.deleteNode(5);

		sl.printList();
	}

}
