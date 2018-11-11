package com.palvai.graph;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;

public class DFS {
	private boolean[] marked; // marked[v] = is there an s-v path?
	private int count; // number of vertices connected to s
	private int[] edgeTo;        // edgeTo[v] = last edge on s-v path
  private final int s;         // source vertex

	/**
	 * Computes the vertices in graph {@code G} that are connected to the source
	 * vertex {@code s}.
	 * 
	 * @param G
	 *          the graph
	 * @param s
	 *          the source vertex
	 * @throws IllegalArgumentException
	 *           unless {@code 0 <= s < V}
	 */
	public DFS(Graph G, int s) {
		this.s = s;
    edgeTo = new int[G.V()];
    marked = new boolean[G.V()];
    validateVertex(s);
    dfs(G, s);
    dfsPath(G, s);
	}

	// depth first search from v
	private void dfs(Graph G, int v) {
		count++;
		marked[v] = true;
		for (int w : G.adj(v)) {
			if (!marked[w]) {
				dfs(G, w);
			}
		}
	}
	
	private void dfsPath(Graph G, int v) {
    marked[v] = true;
    for (int w : G.adj(v)) {
        if (!marked[w]) {
            edgeTo[w] = v;
            dfs(G, w);
        }
    }
}

	
	public void NonrecursiveDFS(Graph G, int s) {
    marked = new boolean[G.V()];

    validateVertex(s);

    // to be able to iterate over each adjacency list, keeping track of which
    // vertex in each adjacency list needs to be explored next
    Iterator<Integer>[] adj = (Iterator<Integer>[]) new Iterator[G.V()];
    for (int v = 0; v < G.V(); v++)
        adj[v] = G.adj(v).iterator();

    // depth-first search using an explicit stack
    Stack<Integer> stack = new Stack<Integer>();
    marked[s] = true;
    stack.push(s);
    while (!stack.isEmpty()) {
        int v = stack.peek();
        if (adj[v].hasNext()) {
            int w = adj[v].next();
            // StdOut.printf("check %d\n", w);
            if (!marked[w]) {
                // discovered vertex w for the first time
                marked[w] = true;
                // edgeTo[w] = v;
                stack.push(w);
                // StdOut.printf("dfs(%d)\n", w);
            }
        }
        else {
            // StdOut.printf("%d done\n", v);
            stack.pop();
        }
    }
}

	/**
	 * Is there a path between the source vertex {@code s} and vertex {@code v}?
	 * 
	 * @param v
	 *          the vertex
	 * @return {@code true} if there is a path, {@code false} otherwise
	 * @throws IllegalArgumentException
	 *           unless {@code 0 <= v < V}
	 */
	public boolean marked(int v) {
		validateVertex(v);
		return marked[v];
	}

	/**
	 * Returns the number of vertices connected to the source vertex {@code s}.
	 * 
	 * @return the number of vertices connected to the source vertex {@code s}
	 */
	public int count() {
		return count;
	}

	// throw an IllegalArgumentException unless {@code 0 <= v < V}
	private void validateVertex(int v) {
		int V = marked.length;
		if (v < 0 || v >= V)
			throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
	}
	
	 /**
   * Is there a path between the source vertex {@code s} and vertex {@code v}?
   * @param v the vertex
   * @return {@code true} if there is a path, {@code false} otherwise
   * @throws IllegalArgumentException unless {@code 0 <= v < V}
   */
  public boolean hasPathTo(int v) {
      validateVertex(v);
      return marked[v];
  }

  /**
   * Returns a path between the source vertex {@code s} and vertex {@code v}, or
   * {@code null} if no such path.
   * @param  v the vertex
   * @return the sequence of vertices on a path between the source vertex
   *         {@code s} and vertex {@code v}, as an Iterable
   * @throws IllegalArgumentException unless {@code 0 <= v < V}
   */
  public Iterable<Integer> pathTo(int v) {
      validateVertex(v);
      if (!hasPathTo(v)) return null;
      Stack<Integer> path = new Stack<Integer>();
      for (int x = v; x != s; x = edgeTo[x])
          path.push(x);
      path.push(s);
      return path;
  }

	/**
	 * Unit tests the {@code DepthFirstSearch} data type.
	 *
	 * @param args
	 *          the command-line arguments
	 */
	public static void main(String[] args) {
		ArrayList<ArrayList<Integer>> input = new ArrayList<ArrayList<Integer>>();
		ArrayList<Integer> list1 = new ArrayList<Integer>();
		list1.add(0);
		list1.add(1);
		ArrayList<Integer> list2 = new ArrayList<Integer>();
		list2.add(1);
		list2.add(2);
		ArrayList<Integer> list3 = new ArrayList<Integer>();
		list3.add(2);
		list3.add(3);
		ArrayList<Integer> list4 = new ArrayList<Integer>();
		list4.add(1);
		list4.add(3);
		ArrayList<Integer> list5 = new ArrayList<Integer>();
		list5.add(0);
		list5.add(4);
		input.add(list1);
		input.add(list2);
		input.add(list3);
		input.add(list4);
		input.add(list5);
		Graph G = new Graph(5, 5, input);
		int s = 0;
		DFS dfs = new DFS(G, s);
		for (int v = 0; v < G.V(); v++) {
			if (dfs.marked(v))
				System.out.println(v + " ");
		}

		System.out.println();
		if (dfs.count() != G.V())
			System.out.println("NOT connected");
		else
			System.out.println("connected");
		
		
		for (int v = 0; v < G.V(); v++) {
      if (dfs.hasPathTo(v)) {
          System.out.printf("%d to %d:  ", s, v);
          for (int x : dfs.pathTo(v)) {
              if (x == s) System.out.print(x);
              else        System.out.print("-" + x);
          }
          System.out.println();
      }

      else {
          System.out.printf("%d to %d:  not connected\n", s, v);
      }

  }
	}

}
