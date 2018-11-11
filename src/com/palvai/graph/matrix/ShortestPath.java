package com.palvai.graph.matrix;

import java.util.LinkedList;
import java.util.Queue;

public class ShortestPath {

	private static int sRow = 0, sCol = 0, dRow, dCol;
	private static int rowNum[] = { -1, 0, 0, 1 };
	private static int colNum[] = { 0, -1, 1, 0 };
	private static int noOfRows = 0;
	private static int noOfColumns = 0;

	public static int BFS(int mat[][]) {
		noOfRows = mat.length;
		noOfColumns = mat[0].length;
		// check source and destination cell
		// of the matrix have value 1
		if (!(mat[sRow][sCol] == 1) || !(mat[dRow][dCol] == 1))
			return Integer.MAX_VALUE;

		boolean visited[][] = new boolean[noOfRows][noOfColumns];

		// Mark the source cell as visited
		visited[sRow][sCol] = true;

		// Create a queue for BFS
		Queue<MatNode> q = new LinkedList<MatNode>();

		// distance of source cell is 0
		MatNode s = new MatNode(0, 0, 1);
		q.add(s); // Enqueue source cell

		// Do a BFS starting from source cell
		while (!q.isEmpty()) {
			MatNode curr = q.peek();
			int x = curr.r;
			int y = curr.c;

			// If we have reached the destination cell,
			// we are done
			if (x == dRow && y == dCol)
				return curr.v;

			// Otherwise dequeue the front cell in the queue
			// and enqueue its adjacent cells
			q.poll();

			for (int i = 0; i < 4; i++) {
				int row = x + rowNum[i];
				int col = y + colNum[i];

				// if adjacent cell is valid, has path and
				// not visited yet, enqueue it.
				if (isValid(row, col) && mat[row][col] == 1 && !visited[row][col]) {
					// mark cell as visited and enqueue it
					visited[row][col] = true;
					MatNode adjCell = new MatNode(row, col, curr.v + 1);
					q.add(adjCell);
				}
			}
		}

		// return -1 if destination cannot be reached
		return Integer.MAX_VALUE;
	}

	private static boolean isValid(int row, int col) {
		// return true if row number and column number
		// is in range
		return (row >= 0) && (row < noOfRows) && (col >= 0) && (col < noOfColumns);
	}

	public static void main(String args[]) {
		/*
		 * int mat[][] = { { 1, 0, 1, 1, 1, 1, 0, 1, 1, 1 }, { 1, 0, 1, 0, 1, 1, 1, 0,
		 * 1, 1 }, { 1, 1, 1, 0, 1, 1, 0, 1, 0, 1 }, { 0, 0, 0, 0, 1, 0, 0, 0, 0, 1 }, {
		 * 1, 1, 1, 0, 1, 1, 1, 0, 1, 0 }, { 1, 0, 1, 1, 1, 1, 0, 1, 0, 0 }, { 1, 0, 0,
		 * 0, 0, 0, 0, 0, 0, 1 }, { 1, 0, 1, 1, 1, 1, 0, 1, 1, 1 }, { 1, 1, 0, 0, 0, 0,
		 * 1, 0, 0, 1 } };
		 */

		int mat[][] = { 
				{ 1, 1, 1, 1 }, 
				{ 1, 0, 0, 1 }, 
				{ 1, 1, 0, 1 },
				{ 1, 1, 1, 1 } };

		dRow = 3;
		dCol = 2;

		int dist = BFS(mat);

		if (dist != Integer.MAX_VALUE)
			System.out.println("Shortest Path is " + dist);
		else
			System.out.println("Shortest Path doesn't exist");

	}

	public static class MatNode {
		private int r;
		private int c;
		private int v;

		public MatNode(int r, int c, int v) {
			this.r = r;
			this.c = c;
			this.v = v;
		}
	}

}
