package com.otto.spacetravel.logic;

import java.util.LinkedList;

/*
 * Class holding inter-connected nodes as a graph
 */
public class Graph {
	private Integer vertices;
	private LinkedList<Node>[] adjacencyList;

	public Graph(int vertices) {
		this.vertices = vertices;
		adjacencyList = new LinkedList[vertices];

		for (int i = 0; i < vertices; i++) {
			adjacencyList[i] = new LinkedList<Node>();
		}
	}

	public Integer getVertices() {
		return vertices;
	}

	public LinkedList<Node>[] getAdjacencyList() {
		return adjacencyList;
	}

	/*
	 * Add an edge/connection to a graph
	 */
	public void addEdge(int source, int destination, int hour) {
		Node node = new Node(source, destination, hour);
		adjacencyList[source].addLast(node);
	}
}