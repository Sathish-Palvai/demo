package com.palvai.graph.path.euler;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import com.palvai.graph.Graph;

public class EulerPath {
  private Stack<Integer> path = null;   // Euler path; null if no suh path

  // an undirected edge, with a field to indicate whether the edge has already been used
  private static class Edge {
      private final int v;
      private final int w;
      private boolean isUsed;

      public Edge(int v, int w) {
          this.v = v;
          this.w = w;
          isUsed = false;
      }

      // returns the other vertex of the edge
      public int other(int vertex) {
          if      (vertex == v) return w;
          else if (vertex == w) return v;
          else throw new IllegalArgumentException("Illegal endpoint");
      }
  }

  /**
   * Computes an Euler path in the specified graph, if one exists.
   * 
   * @param G the graph
   */
  public EulerPath(Graph G) {

      // find vertex from which to start potential Euler path:
      // a vertex v with odd degree(v) if it exits;
      // otherwise a vertex with degree(v) > 0
      int oddDegreeVertices = 0;
      int s = nonIsolatedVertex(G);
      for (int v = 0; v < G.V(); v++) {
          if (G.degree(v) % 2 != 0) {
              oddDegreeVertices++;
              s = v;
          }
      }

      // graph can't have an Euler path
      // (this condition is needed for correctness)
      if (oddDegreeVertices > 2) return;

      // special case for graph with zero edges (has a degenerate Euler path)
      if (s == -1) s = 0;

      // create local view of adjacency lists, to iterate one vertex at a time
      // the helper Edge data type is used to avoid exploring both copies of an edge v-w
      Queue<Edge>[] adj = (Queue<Edge>[]) new Queue[G.V()];
      for (int v = 0; v < G.V(); v++)
          adj[v] = new LinkedList<Edge>();

      for (int v = 0; v < G.V(); v++) {
          int selfLoops = 0;
          for (int w : G.adj(v)) {
              // careful with self loops
              if (v == w) {
                  if (selfLoops % 2 == 0) {
                      Edge e = new Edge(v, w);
                      adj[v].add(e);
                      adj[w].add(e);
                  }
                  selfLoops++;
              }
              else if (v < w) {
                  Edge e = new Edge(v, w);
                  adj[v].add(e);
                  adj[w].add(e);
              }
          }
      }

      // initialize stack with any non-isolated vertex
      Stack<Integer> stack = new Stack<Integer>();
      stack.push(s);

      // greedily search through edges in iterative DFS style
      path = new Stack<Integer>();
      while (!stack.isEmpty()) {
          int v = stack.pop();
          while (!adj[v].isEmpty()) {
              Edge edge = adj[v].poll();
              if (edge.isUsed) continue;
              edge.isUsed = true;
              stack.push(v);
              v = edge.other(v);
          }
          // push vertex with no more leaving edges to path
          path.push(v);
      }

      // check if all edges are used
      if (path.size() != G.E() + 1)
          path = null;

      assert certifySolution(G);
  }

  /**
   * Returns the sequence of vertices on an Euler path.
   * 
   * @return the sequence of vertices on an Euler path;
   *         {@code null} if no such path
   */
  public Iterable<Integer> path() {
      return path;
  }

  /**
   * Returns true if the graph has an Euler path.
   * 
   * @return {@code true} if the graph has an Euler path;
   *         {@code false} otherwise
   */
  public boolean hasEulerPath() {
      return path != null;
  }


  // returns any non-isolated vertex; -1 if no such vertex
  private static int nonIsolatedVertex(Graph G) {
      for (int v = 0; v < G.V(); v++)
          if (G.degree(v) > 0)
              return v;
      return -1;
  }


  /**************************************************************************
   *
   *  The code below is solely for testing correctness of the data type.
   *
   **************************************************************************/

  // Determines whether a graph has an Euler path using necessary
  // and sufficient conditions (without computing the path itself):
  //    - degree(v) is even for every vertex, except for possibly two
  //    - the graph is connected (ignoring isolated vertices)
  // This method is solely for unit testing.
  private static boolean satisfiesNecessaryAndSufficientConditions(Graph G) {
      if (G.E() == 0) return true;

      // Condition 1: degree(v) is even except for possibly two
      int oddDegreeVertices = 0;
      for (int v = 0; v < G.V(); v++)
          if (G.degree(v) % 2 != 0)
              oddDegreeVertices++;
      if (oddDegreeVertices > 2) return false;

      // Condition 2: graph is connected, ignoring isolated vertices
      int s = nonIsolatedVertex(G);
      BreadthFirstPaths bfs = new BreadthFirstPaths(G, s);
      for (int v = 0; v < G.V(); v++)
          if (G.degree(v) > 0 && !bfs.hasPathTo(v))
              return false;

      return true;
  }

  // check that solution is correct
  private boolean certifySolution(Graph G) {

      // internal consistency check
      if (hasEulerPath() == (path() == null)) return false;

      // hashEulerPath() returns correct value
      if (hasEulerPath() != satisfiesNecessaryAndSufficientConditions(G)) return false;

      // nothing else to check if no Euler path
      if (path == null) return true;

      // check that path() uses correct number of edges
      if (path.size() != G.E() + 1) return false;

      // check that path() is a path in G
      // TODO

      return true;
  }


  private static void unitTest(Graph G, String description) {
      System.out.println(description);
      System.out.println("-------------------------------------");
      System.out.print(G);

      EulerPath euler = new EulerPath(G);

      System.out.print("Euler path:  ");
      if (euler.hasEulerPath()) {
          for (int v : euler.path()) {
              System.out.print(v + " ");
          }
          System.out.println();
      }
      else {
          System.out.println("none");
      }
      System.out.println();
  }


  /**
   * Unit tests the {@code EulerPath} data type.
   *
   * @param args the command-line arguments
   */
  public static void main(String[] args) {
      int V = 4;
      int E = 6;


      // Euler cycle
      Graph G1 = GraphGenerator.eulerianCycle(V, E);
      unitTest(G1, "Euler cycle");

      // Euler path
      Graph G2 = GraphGenerator.eulerianPath(V, E);
      
      unitTest(G2, "Euler path");

      // add one random edge
      Graph G3 = new Graph(G2);
      G3.addEdge(StdRandom.uniform(V), StdRandom.uniform(V));
      unitTest(G3, "one random edge added to Euler path");

      // self loop
      Graph G4 = new Graph(V);
      int v4 = StdRandom.uniform(V);
      G4.addEdge(v4, v4);
      unitTest(G4, "single self loop");

      // single edge
      Graph G5 = new Graph(V);
      G5.addEdge(StdRandom.uniform(V), StdRandom.uniform(V));
      unitTest(G5, "single edge");

      // empty graph
      Graph G6 = new Graph(V);
      unitTest(G6, "empty graph");

      // random graph
      Graph G7 = GraphGenerator.simple(V, E);
      unitTest(G7, "simple graph");
  }
}