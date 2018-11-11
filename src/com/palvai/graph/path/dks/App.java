package com.palvai.graph.path.dks;

public class App {

	public static void main(String[] args) {

		Vertex vertex0 = new Vertex("A");
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

		ShortestPath shortestPath = new ShortestPath();
		shortestPath.computePaths(vertex0);

		System.out.println(shortestPath.getShortestPathTo(vertex2));

	}
}
