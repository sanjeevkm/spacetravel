package com.otto.spacetravel;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import com.otto.spacetravel.data.DataLoader;
import com.otto.spacetravel.data.DummyDataLoader;
import com.otto.spacetravel.exception.RouteNotFoundException;
import com.otto.spacetravel.filter.RouteFilter;
import com.otto.spacetravel.logic.Graph;
import com.otto.spacetravel.logic.OttoRouteFinder;
import com.otto.spacetravel.model.NodeDetail;
import com.otto.spacetravel.model.Route;

/*
 * Helper class to run route related APIs
 */
public class App {
	public static void main(String[] args) throws RouteNotFoundException {
		DataLoader<Graph, Void> dataLoader = new DummyDataLoader();
		Graph graph = dataLoader.loadData(null);
		RouteFinder routeFinder = new OttoRouteFinder(graph);

//		printGraph(graph);

		List<NodeDetail> routeNodeList = null;
		// Exercise 1: The distance of route Solar System -> Alpha Centauri -> Sirius
		System.out.println("Exercise 1");
		routeNodeList = new LinkedList<>();
		routeNodeList.add(NodeDetail.SOLAR_SYSTEM);
		routeNodeList.add(NodeDetail.ALPHA_CENTAURI);
		routeNodeList.add(NodeDetail.SIRIUS);
		System.out.println("Solar System -> Alpha Centauri -> Sirius takes " + routeFinder.getTravelTime(routeNodeList)
				+ " hours");

		// Exercise 2: The distance of route Solar System -> Betelgeuse
		System.out.println("Exercise 2");
		routeNodeList = new LinkedList<>();
		routeNodeList.add(NodeDetail.SOLAR_SYSTEM);
		routeNodeList.add(NodeDetail.BETELGEUSE);
		System.out.println("Solar System -> Betelgeuse takes " + routeFinder.getTravelTime(routeNodeList) + " hours");

		// Exercise 3: The distance of route Solar System -> Betelgeuse -> Sirius
		System.out.println("Exercise 3");
		routeNodeList = new LinkedList<>();
		routeNodeList.add(NodeDetail.SOLAR_SYSTEM);
		routeNodeList.add(NodeDetail.BETELGEUSE);
		routeNodeList.add(NodeDetail.SIRIUS);
		System.out.println(
				"Solar System -> Betelgeuse -> Sirius takes " + routeFinder.getTravelTime(routeNodeList) + " hours");

		// Exercise 4: The distance of route Solar System -> Vega -> Alpha Centauri ->
		// Sirius -> Betelgeuse
		System.out.println("Exercise 4");
		routeNodeList = new LinkedList<>();
		routeNodeList.add(NodeDetail.SOLAR_SYSTEM);
		routeNodeList.add(NodeDetail.VEGA);
		routeNodeList.add(NodeDetail.ALPHA_CENTAURI);
		routeNodeList.add(NodeDetail.SIRIUS);
		routeNodeList.add(NodeDetail.BETELGEUSE);
		System.out.println("Solar System -> Vega -> Alpha Centauri -> Sirius -> Betelgeuse takes "
				+ routeFinder.getTravelTime(routeNodeList) + " hours");

		// Exercise 5: The distance of route Solar System -> Vega -> Betelgeuse
		System.out.println("Exercise 5");
		routeNodeList = new LinkedList<>();
		routeNodeList.add(NodeDetail.SOLAR_SYSTEM);
		routeNodeList.add(NodeDetail.VEGA);
		routeNodeList.add(NodeDetail.BETELGEUSE);

		try {
			System.out.println(
					"Solar System -> Vega -> Betelgeuse takes " + routeFinder.getTravelTime(routeNodeList) + " hours");
		} catch (Exception e) {
			System.out.println("Solar System -> Vega -> Betelgeuse : NO SUCH ROUTE");
		}

		List<Route> routeList = null;
		RouteFilter filter = null;
		// Exercise 6: Determine all routes starting at Sirius and ending at Sirius with
		// maximum of 3 stops
		System.out.println("Exercise 6");
		filter = new RouteFilter();
		filter.setMaxNumberOfStops(3);
		routeList = routeFinder.findRoutes(NodeDetail.SIRIUS, NodeDetail.SIRIUS, filter);
		routeList.stream().forEach(System.out::println);

		// Exercise 7: Determine the number of routes starting at the solar system and
		// ending at Sirius with exactly 3 stops inbetween
		System.out.println("Exercise 7");
		filter = new RouteFilter();
		filter.setExactNumberOfStops(3);
		routeList = routeFinder.findRoutes(NodeDetail.SOLAR_SYSTEM, NodeDetail.SIRIUS, filter);
		routeList.stream().forEach(System.out::println);

		// Excercise 8: Determine the duration of the shortest routes (in traveltime)
		// between solar system and Sirius
		System.out.println("Exercise 8");
		System.out.println("Shortest time to travel between Solar system and Sirius is "
				+ routeFinder.getShortestTravelTime(NodeDetail.SOLAR_SYSTEM, NodeDetail.SIRIUS));

		// Excercise 9: Determine the duration of the shortest routes (in traveltime)
		// starting at Alpha Centauri and ending at Alpha Centauri
		System.out.println("Exercise 9");
		System.out.println("Shortest time to travel between Alpha Centauri and Alpha Centauri is "
				+ routeFinder.getShortestTravelTime(NodeDetail.ALPHA_CENTAURI, NodeDetail.ALPHA_CENTAURI));

		// Exercise 10: Determine all different routes starting at Sirius and ending at
		// Sirius with an over traveltime less than 30.
		System.out.println("Exercise 10");
		filter = new RouteFilter();
		filter.setMaxHoursAllowed(30);
		routeList = routeFinder.findRoutes(NodeDetail.SIRIUS, NodeDetail.SIRIUS, filter);
		routeList.stream().forEach(System.out::println);
	}

	/*
	 * Print graph nodes
	 */
	private static void printGraph(Graph graph) {
		Arrays.stream(graph.getAdjacencyList()).forEach(System.out::println);
	}
}
