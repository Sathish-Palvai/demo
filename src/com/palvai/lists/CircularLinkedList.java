package com.palvai.lists;

public class CircularLinkedList {

	public int listSize = 0;
	public CircularNode headNode = null;
	public CircularNode tailNode = null;

	public void insertAtHead(int data) {
		CircularNode n = new CircularNode(data);
		if (listSize == 0) {
			headNode = n;
			tailNode = n;
			n.nextNode = headNode;
		} else {
			CircularNode temp = headNode;
			n.nextNode = temp;
			headNode = n;
			tailNode.nextNode = headNode;
		}
		listSize++;
	}

	public void insertAtEnd(int data) {
		if (listSize == 0) {
			insertAtHead(data);
		} else {
			CircularNode n = new CircularNode(data);
			tailNode.nextNode = n;
			tailNode = n;
			tailNode.nextNode = headNode;
			listSize++;
		}
	}

	public void deleteAtHead() {
		if (listSize == 0) {
			System.out.println("\nList is Empty");
		} else {
			headNode = headNode.nextNode;
			tailNode.nextNode = headNode;
			listSize--;
		}
	}

	public int elementAt(int index) {
		if (index > listSize) {
			return -1;
		}
		CircularNode n = headNode;
		while (index - 1 != 0) {
			n = n.nextNode;
			index--;
		}
		return n.data;
	}

	// print the linked list
	public void print() {
		System.out.print("Circular Linked List:");
		CircularNode temp = headNode;
		if (listSize <= 0) {
			System.out.print("List is empty");
		} else {
			do {
				System.out.print(" " + temp.data);
				temp = temp.nextNode;
			} while (temp != headNode);
		}
		System.out.println();
	}

	public int getSize() {
		return listSize;
	}

	public static void main(String[] args) {
		CircularLinkedList c = new CircularLinkedList();
		c.insertAtHead(4);
		c.insertAtHead(5);
		c.insertAtHead(6);
		c.print();
		c.deleteAtHead();
		c.print();
		c.insertAtEnd(4);
		c.print();
		System.out.println("Size of linked list: " + c.getSize());
		System.out.println("Element at 2nd position: " + c.elementAt(2));
	}

}

class CircularNode {
	int data;
	CircularNode nextNode;

	public CircularNode(int data) {
		this.data = data;
	}
}