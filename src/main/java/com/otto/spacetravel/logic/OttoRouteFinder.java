package com.otto.spacetravel.logic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.otto.spacetravel.RouteFinder;
import com.otto.spacetravel.exception.RouteNotFoundException;
import com.otto.spacetravel.model.NodeDetail;
import com.otto.spacetravel.model.Route;

/*
 * Implementation of RouteFinder.
 * Implements APIs and logic to find routes, time travel.
 * Logic is borrowed from 
 * https://algorithms.tutorialhorizon.com/graph-print-all-paths-between-source-and-destination/
 * 
 * TODO:
 * Add cache support.
 */
public class OttoRouteFinder implements RouteFinder {
	private final String separator = "->";
	private Graph graph = null;

	public OttoRouteFinder(Graph graph) {
		this.graph = graph;
	}

	/*
	 * For a route calculate travel time. Compares input route with graph data. If
	 * route from one stop to another exists then extract hours required for that
	 * jump and add to total hours required to cover the route.
	 */
	@Override
	public Integer getTravelTime(List<NodeDetail> nodeList) throws RouteNotFoundException {
		if (nodeList == null || nodeList.isEmpty()) {
			throw new IllegalArgumentException("Empty route data");
		}

		Boolean nodeFound = Boolean.FALSE;
		Integer hours = 0;
		Integer numberOfStops = nodeList.size();
		NodeDetail current = null, next = null;
		LinkedList<Node> list = null;

		for (int i = 0; i < numberOfStops; i++) {
			if (i + 1 >= numberOfStops) {
				return hours;
			}

			nodeFound = Boolean.FALSE;
			current = nodeList.get(i);
			next = nodeList.get(i + 1);
			list = graph.getAdjacencyList()[current.getId()];

			for (Node node : list) {
				if (next.getId().equals(node.getDestination())) {
					hours += node.getHours();
					nodeFound = Boolean.TRUE;
					break;
				}
			}

			if (!nodeFound) {
				throw new RouteNotFoundException(RouteFinder.MSG_NO_SUCH_ROUTE);
			}
		}

		return hours;
	}

	/*
	 * Find routes between source and destination based on filter conditions. First
	 * it gets all routes then applies filter on that.
	 * 
	 * TODO: Can integrate filter in route finding algorithm to avoid calculating
	 * routes discard later based on filter.
	 */
	@Override
	public List<Route> findRoutes(NodeDetail source, NodeDetail destination, List<Function<List<Route>, List<Route>>> filters)
			throws RouteNotFoundException {
		validateNode(source, destination);

		List<Route> routeList = findAllRoutes(source.getId(), destination.getId());
		// If there are no routes available then throw exception.
		if (routeList.isEmpty()) {
			throw new RouteNotFoundException(RouteFinder.MSG_NO_SUCH_ROUTE);
		}

		// If filter is null then no further processing is required.
		if (filters != null) {
			for(Function<List<Route>, List<Route>> filter: filters) {
				routeList = filter.apply(routeList);
			}
		}

		return routeList;
	}

	/*
	 * Get shortest travel time between source and destination. First finds all
	 * routes and return one with least travel time.
	 * 
	 * TODO: Can integrate filter in route finding algorithm to avoid discarding
	 * slow routes.
	 */
	@Override
	public Integer getShortestTravelTime(NodeDetail source, NodeDetail destination) throws RouteNotFoundException {
		validateNode(source, destination);

		List<Route> routeList = findAllRoutes(source.getId(), destination.getId());
		Optional<Route> routeOptional = routeList.stream().sorted(Comparator.comparingInt(Route::getHours)).findFirst();
		Route route = routeOptional.orElseThrow(() -> new RouteNotFoundException(RouteFinder.MSG_NO_SUCH_ROUTE));

		return route.getHours();
	}

	/*
	 * Recursively go through each nodes until destination nodes is found.
	 */
	private void find(Integer start, Integer end, String path, boolean[] visited, List<Route> routeList,
			Integer hours) {
		String newPath = path + separator + start;
		LinkedList<Node> list = graph.getAdjacencyList()[start];

		visited[start] = true;

		for (int i = 0; i < list.size(); i++) {
			Node node = list.get(i);

			if (!node.getDestination().equals(end) && visited[node.getDestination()] == false) {
				find(node.getDestination(), end, newPath, visited, routeList, new Integer(hours + node.getHours()));
			} else if (node.getDestination().equals(end)) {
				routeList.add(new Route(hours + node.getHours(), getNodeList(newPath + "->" + node.getDestination())));
			}
		}

		// remove from path
		visited[start] = false;
	}

	/*
	 * Logic start point to calculate routes
	 */
	private List<Route> findAllRoutes(Integer start, Integer end) {
		List<Route> routeList = new ArrayList<>();
		boolean[] visited = new boolean[graph.getVertices()];

		visited[start] = true;
		find(start, end, "", visited, routeList, 0);

		return routeList;
	}

	/*
	 * Converts ->0->1->2->3->4->0 into list of NodeDetail
	 */
	private List<NodeDetail> getNodeList(String string) {
		List<NodeDetail> nodeDetailList = Arrays.stream(string.split(separator)).filter(x -> x.length() > 0)
				.map(Integer::valueOf).map(NodeDetail::getNodeDetailById).collect(Collectors.toList());

		return nodeDetailList;
	}

	/*
	 * Validate input NodeDetails
	 */
	private void validateNode(NodeDetail source, NodeDetail destination) {
		if (source == null) {
			throw new IllegalArgumentException("Source can't be null");
		}

		if (destination == null) {
			throw new IllegalArgumentException("Destination can't be null");
		}
	}
}
