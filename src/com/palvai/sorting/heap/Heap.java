package com.palvai.sorting.heap;

public class Heap {

	private Integer[] heap;
	private int currentPosition = -1;

	public Heap(int size) {
		this.heap = new Integer[size];
	}

	public void insert(int item) {

		if (isFull()) {
			throw new RuntimeException("Heap is full...");
		}

		this.heap[++currentPosition] = item;
		fixUp(currentPosition);
	}

	private void fixUp(int index) {
		int parentIndex = (index - 1) / 2;

		while (parentIndex >= 0 && this.heap[parentIndex] < this.heap[index]) {
			int temp = this.heap[index];
			this.heap[index] = this.heap[parentIndex];
			this.heap[parentIndex] = temp;
			index = parentIndex;
			parentIndex = (index - 1) / 2;
		}
	}

	// get root method
	public int getMax() {
		int result = this.heap[0];
		this.heap[0] = this.heap[currentPosition--];
		this.heap[currentPosition + 1] = null; // avoid obsolete references
		fixDown(0, -1);
		return result;
	}

	public void heapsort() {
		for (int i = 0; i <= currentPosition; i++) {
			int temp = this.heap[0]; //
			System.out.print(temp + " ");
			this.heap[0] = this.heap[currentPosition - i];
			this.heap[currentPosition - i] = temp;
			fixDown(0, currentPosition - i - 1);
		}
	}

	private void fixDown(int index, int upto) {
		if (upto < 0)
			upto = currentPosition;

		while (index <= upto) {
			int leftChild = 2 * index + 1;
			int rightChild = 2 * index + 2;

			if (leftChild <= upto) {
				int childToSwap;

				if (rightChild > upto) {
					childToSwap = leftChild;
				} else {
					childToSwap = (this.heap[leftChild] > this.heap[rightChild]) ? leftChild : rightChild;
				}

				if (this.heap[index] < this.heap[childToSwap]) {
					int temp = this.heap[index];
					this.heap[index] = this.heap[childToSwap];
					this.heap[childToSwap] = temp;
				} else {
					break;
				}

				index = childToSwap;
			} else {
				break;
			}
		}
	}

	private boolean isFull() {
		return this.currentPosition == this.heap.length;
	}

	public boolean isMinHeap(int[] heap) {

		// leaf nodes do not have children (those are NULL values)
		// so it means that if node with index i -> 2*i+2>n where N is the size of the
		// array then
		// we know that it is a leaf node
		// rearrange the equation: (size of the array without leaf nodes) = (N-2)/2 this
		// is the effective
		// length so the items we have to consider
		for (int i = 0; i <= (heap.length - 2) / 2; i++) {

			// node with index i has left child 2*i+1 and right child 2*i+2 in the array
			// representation
			// we just have to check one by one whether the heap property is violated or not
			if (heap[i] > heap[2 * i + 1] || heap[i] > heap[2 * i + 2])
				return false;
		}

		// the array represents a valid minimum heap (so all the child nodes are greater
		// than the parents)
		return true;
	}
}
