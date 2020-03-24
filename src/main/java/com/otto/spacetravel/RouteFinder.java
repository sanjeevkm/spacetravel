package com.otto.spacetravel;

import java.util.List;

import com.otto.spacetravel.exception.RouteNotFoundException;
import com.otto.spacetravel.filter.RouteFilter;
import com.otto.spacetravel.model.NodeDetail;
import com.otto.spacetravel.model.Route;

/*
 * Interface to define route related APIs.
 */
public interface RouteFinder {
	public String MSG_NO_SUCH_ROUTE = "NO SUCH ROUTE";

	/*
	 * For a route calculate travel time.
	 */
	Integer getTravelTime(List<NodeDetail> nodeList) throws RouteNotFoundException;

	/*
	 * Find routes between source and destination based on filter conditions.
	 */
	List<Route> findRoutes(NodeDetail source, NodeDetail destination, RouteFilter filter) throws RouteNotFoundException;

	/*
	 * Get shortest travel time between source and destination.
	 */
	Integer getShortestTravelTime(NodeDetail source, NodeDetail destination) throws RouteNotFoundException;
}
