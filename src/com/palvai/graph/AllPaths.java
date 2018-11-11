package com.palvai.graph;

import java.util.Stack;

import com.palvai.graph.path.dks.Edge;
import com.palvai.graph.path.dks.Vertex;

/******************************************************************************
 *  Compilation:  javac AllPaths.java
 *  Execution:    java AllPaths
 *  Depedencies:  Graph.java
 *  
 *  Enumerate all simple paths (of length >= 1) in a graph between s and t.
 *  This implementation uses depth-first search and backtracking.
 *
 *  Warning: there can be exponentially many simple paths in a graph,
 *           so no algorithm is suitable for large graphs.
 *  
 *  7 vertices, 9 edges 
 *  0: 2 1 
 *  1: 5 0 
 *  2: 5 3 0 
 *  3: 6 4 2 
 *  4: 6 5 3 
 *  5: 4 1 2 
 *  6: 4 3 
 *
 *
 *  all simple paths between 0 and 6:
 *  0-2-5-4-6
 *  0-2-5-4-3-6
 *  0-2-3-6
 *  0-2-3-4-6
 *  0-1-5-4-6
 *  0-1-5-4-3-6
 *  0-1-5-2-3-6
 *  0-1-5-2-3-4-6
 *  # paths = 8
 *
 *  all simple paths between 1 and 5:
 *  1-5
 *  1-0-2-5
 *  1-0-2-3-6-4-5
 *  1-0-2-3-4-5
 *  # paths = 4
 *
 ******************************************************************************/

public class AllPaths {
    private boolean[] onPath;        // vertices in current path
    private Stack<Integer> path;     // the current path
    private int numberOfPaths;       // number of simple path

    // show all simple paths from s to t - use DFS
    public AllPaths(Graph G, int s, int t) {
        onPath = new boolean[G.V()];
        path   = new Stack<Integer>();
        dfs(G, s, t);
    }


    // use DFS
    private void dfs(Graph G, int v, int t) {

        // add v to current path
        path.push(v);
        onPath[v] = true;

        // found path from s to t
        if (v == t) {
            processCurrentPath();
            numberOfPaths++;
        }

        // consider all neighbors that would continue path with repeating a node
        else {
            for (int w : G.adj(v)) {
                if (!onPath[w])
                    dfs(G, w, t);
            }
        }

        // done exploring from v, so remove from path
        path.pop();
        onPath[v] = false;
    }

    // this implementation just prints the path to standard output
    private void processCurrentPath() {
        Stack<Integer> reverse = new Stack<Integer>();
        for (int v : path)
            reverse.push(v);
        if (reverse.size() >= 1)
            System.out.print(reverse.pop());
        while (!reverse.isEmpty())
            System.out.print("-" + reverse.pop());
        System.out.println();
    }

    // return number of simple paths between s and t
    public int numberOfPaths() {
        return numberOfPaths;
    }


    // test client
    public static void main(String[] args) {
    	
        Graph G = new Graph(7);
        G.addEdge(0, 1);
        G.addEdge(0, 2);
        G.addEdge(2, 3);
        G.addEdge(3, 4);
        G.addEdge(2, 5);
        G.addEdge(1, 5);
        G.addEdge(5, 4);
        G.addEdge(3, 6);
        G.addEdge(4, 6);
        System.out.println(G);
        
        /*
         * Vertex vertex0 = new Vertex("A");
		Vertex vertex1 = new Vertex("B");
		Vertex vertex2 = new Vertex("C");
		Vertex vertex3 = new Vertex("D");

		vertex0.addNeighbour(new Edge(1, vertex0, vertex1));
		vertex0.addNeighbour(new Edge(1, vertex0, vertex2));
		vertex0.addNeighbour(new Edge(1, vertex0, vertex3));
		vertex1.addNeighbour(new Edge(1, vertex1, vertex0));
		vertex1.addNeighbour(new Edge(1, vertex1, vertex3));
		vertex2.addNeighbour(new Edge(1, vertex2, vertex0));
		vertex3.addNeighbour(new Edge(1, vertex3, vertex0));
		vertex3.addNeighbour(new Edge(1, vertex3, vertex1));
		vertex3.addNeighbour(new Edge(9, vertex3, vertex2));
         */
        
        Graph G2 = new Graph(4);
        G2.addEdge(0, 1);
        G2.addEdge(0, 2);
        G2.addEdge(0, 3);
        G2.addEdge(1, 0);
        G2.addEdge(1, 3);
        G2.addEdge(2, 0);
        G2.addEdge(3, 0);
        G2.addEdge(3, 1);
        G2.addEdge(3, 2);

        System.out.println();
        System.out.println("all simple paths between 0 and 6:");
        AllPaths allpaths1 = new AllPaths(G, 0, 6);
        System.out.println("# paths = " + allpaths1.numberOfPaths());

        System.out.println();
        System.out.println("all simple paths between 0 and 2:");
        AllPaths allpaths2 = new AllPaths(G2, 0, 3);
        System.out.println("# paths = " + allpaths2.numberOfPaths());
    }


}

