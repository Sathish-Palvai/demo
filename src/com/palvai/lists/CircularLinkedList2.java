package com.palvai.lists;

public class CircularLinkedList2 {

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

	public void sortedInsert(int data) {
		CircularNode newNode = new CircularNode(data);
		CircularNode tempHead = headNode;
		CircularNode tempTail = tailNode;

		if (tempHead.data > newNode.data) {
			tailNode.nextNode = newNode;
			newNode.nextNode = headNode;
			headNode = newNode;
		} else if (tempTail.data < newNode.data) {
			tailNode.nextNode = newNode;
			newNode.nextNode = headNode;
			tailNode = newNode;
		} else {
			CircularNode prevNode = null;
			while (newNode.data > tempHead.data) {
				prevNode = tempHead;
				tempHead = tempHead.nextNode;
			}
			prevNode.nextNode = newNode;
			newNode.nextNode = tempHead;
			System.out.println(tempHead.data);
		}

	}

	public int getSize() {
		return listSize;
	}

	public static void main(String[] args) {
		CircularLinkedList2 c = new CircularLinkedList2();
		c.insertAtHead(1);
		c.insertAtEnd(2);
		c.insertAtEnd(3);
		c.insertAtEnd(4);
		c.sortedInsert(0);
		c.sortedInsert(5);
		c.sortedInsert(3);
		c.print();
		System.out.println("Size of linked list: " + c.getSize());
		System.out.println("Element at 2nd position: " + c.elementAt(6));
	}

}
