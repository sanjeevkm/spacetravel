package com.otto.spacetravel.data;

import com.otto.spacetravel.logic.Graph;

/*
 * Dummy data loader class to load test data into the system
 */
public class DummyDataLoader implements DataLoader<Graph, Void> {
	@Override
	public Graph loadData(Void source) {
		int vertices = 5;

		Graph graph = new Graph(vertices);
		// Load nodes reachable from Solar System
		graph.addEdge(0, 1, 5);
		graph.addEdge(0, 2, 5);
		graph.addEdge(0, 3, 7);
		// Load nodes reachable from Betelgeuse
		graph.addEdge(1, 3, 6);
		graph.addEdge(1, 4, 8);
		// Load nodes reachable from Alpha Centauri
		graph.addEdge(2, 4, 4);
		// Load nodes reachable from Vega
		graph.addEdge(3, 2, 3);
		// Load nodes reachable from Sirius
		graph.addEdge(4, 1, 8);
		graph.addEdge(4, 3, 2);

		return graph;
	}
}
