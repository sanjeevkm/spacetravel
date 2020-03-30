package com.otto.spacetravel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.junit.BeforeClass;
import org.junit.Test;

import com.otto.spacetravel.data.DataLoader;
import com.otto.spacetravel.data.DummyDataLoader;
import com.otto.spacetravel.exception.RouteNotFoundException;
import com.otto.spacetravel.logic.Graph;
import com.otto.spacetravel.logic.OttoRouteFinder;
import com.otto.spacetravel.model.NodeDetail;
import com.otto.spacetravel.model.Route;

/*
 * Test cases for OttoRouteFinder
 */
public class OttoRouteFinderTest {
	private static Graph graph = null;
	private static RouteFinder routeFinder = null;

	@BeforeClass
	public static void setup() throws Exception {
		DataLoader<Graph, Void> dataLoader = new DummyDataLoader();

		graph = dataLoader.loadData(null);
		if (graph == null) {
			throw new Exception("Graph instance required for test cases.");
		}

		routeFinder = new OttoRouteFinder(graph);
		if (routeFinder == null) {
			throw new Exception("routeFinder instance required for test cases.");
		}
	}

	@Test(expected = Exception.class)
	public void test_getTravelTime_nullAllInput() throws Exception {
		routeFinder.getTravelTime(null);
	}

	@Test(expected = RouteNotFoundException.class)
	public void test_getTravelTime_invalidRoute() throws Exception {
		List<NodeDetail> routeNodeList = new LinkedList<>();
		routeNodeList.add(NodeDetail.BETELGEUSE);
		routeNodeList.add(NodeDetail.SOLAR_SYSTEM);

		routeFinder.getTravelTime(routeNodeList);
	}

	// Exercise 1: The distance of route Solar System -> Alpha Centauri -> Sirius
	@Test
	public void test_getTravelTime_exercise1() throws Exception {
		List<NodeDetail> routeNodeList = new LinkedList<>();
		routeNodeList.add(NodeDetail.SOLAR_SYSTEM);
		routeNodeList.add(NodeDetail.ALPHA_CENTAURI);
		routeNodeList.add(NodeDetail.SIRIUS);
		Integer hours = routeFinder.getTravelTime(routeNodeList);

		assertNotNull(hours);
		assertEquals(Integer.valueOf(9), hours);
	}

	// Exercise 2: The distance of route Solar System -> Betelgeuse
	@Test
	public void test_getTravelTime_exercise2() throws Exception {
		List<NodeDetail> routeNodeList = new LinkedList<>();
		routeNodeList.add(NodeDetail.SOLAR_SYSTEM);
		routeNodeList.add(NodeDetail.BETELGEUSE);
		Integer hours = routeFinder.getTravelTime(routeNodeList);

		assertNotNull(hours);
		assertEquals(Integer.valueOf(5), hours);
	}

	// Exercise 3: The distance of route Solar System -> Betelgeuse -> Sirius
	@Test
	public void test_getTravelTime_exercise3() throws Exception {
		List<NodeDetail> routeNodeList = new LinkedList<>();
		routeNodeList.add(NodeDetail.SOLAR_SYSTEM);
		routeNodeList.add(NodeDetail.BETELGEUSE);
		routeNodeList.add(NodeDetail.SIRIUS);

		Integer hours = routeFinder.getTravelTime(routeNodeList);

		assertNotNull(hours);
		assertEquals(Integer.valueOf(13), hours);
	}

	// Exercise 4: The distance of route Solar System -> Vega -> Alpha Centauri ->
	// Sirius -> Betelgeuse
	@Test
	public void test_getTravelTime_exercise4() throws Exception {
		List<NodeDetail> routeNodeList = new LinkedList<>();
		routeNodeList.add(NodeDetail.SOLAR_SYSTEM);
		routeNodeList.add(NodeDetail.VEGA);
		routeNodeList.add(NodeDetail.ALPHA_CENTAURI);
		routeNodeList.add(NodeDetail.SIRIUS);
		routeNodeList.add(NodeDetail.BETELGEUSE);

		Integer hours = routeFinder.getTravelTime(routeNodeList);

		assertNotNull(hours);
		assertEquals(Integer.valueOf(22), hours);
	}

	// Exercise 5: The distance of route Solar System -> Vega -> Betelgeuse
	@Test(expected = RouteNotFoundException.class)
	public void test_getTravelTime_exercise5() throws Exception {
		List<NodeDetail> routeNodeList = new LinkedList<>();
		routeNodeList.add(NodeDetail.SOLAR_SYSTEM);
		routeNodeList.add(NodeDetail.VEGA);
		routeNodeList.add(NodeDetail.BETELGEUSE);

		routeFinder.getTravelTime(routeNodeList);
	}

	@Test(expected = Exception.class)
	public void test_findRoutes_nullAllInput() throws Exception {
		routeFinder.findRoutes(null, null, null);
	}

	@Test(expected = Exception.class)
	public void test_findRoutes_nullSourceInput() throws Exception {
		routeFinder.findRoutes(null, NodeDetail.SOLAR_SYSTEM, null);
	}

	@Test(expected = Exception.class)
	public void test_findRoutes_nullDestinationInput() throws Exception {
		routeFinder.findRoutes(NodeDetail.SOLAR_SYSTEM, null, null);
	}

	@Test
	public void test_findRoutes_nullNullFilterInput() throws Exception {
		List<Route> routeList = routeFinder.findRoutes(NodeDetail.SOLAR_SYSTEM, NodeDetail.BETELGEUSE, null);

		assertNotNull(routeList);
	}

	// Exercise 6: Determine all routes starting at Sirius and ending at Sirius with
	// maximum of 3 stops
	@Test
	public void test_findRoutes_exercise6() throws Exception {
		List<Function<List<Route>, List<Route>>> filters = new ArrayList<>();
		// Added +1 for source
		filters.add(x -> x.stream().filter(y -> (y.getNodeList().size() <= 3 + 1)).collect(Collectors.toList()));

		List<Route> routeList = routeFinder.findRoutes(NodeDetail.SIRIUS, NodeDetail.SIRIUS, filters);

		assertNotNull(routeList);
		assertEquals(2, routeList.size());
		// TODO: Add assert to ensure that routes are correct
	}

	// Exercise 7: Determine the number of routes starting at the solar system and
	// ending at Sirius with exactly 3 stops inbetween
	@Test
	public void test_findRoutes_exercise7() throws Exception {
		List<Function<List<Route>, List<Route>>> filters = new ArrayList<>();
		// Added +1 for source
		filters.add(x -> x.stream().filter(y -> (y.getNodeList().size() == 3 + 1)).collect(Collectors.toList()));

		List<Route> routeList = routeFinder.findRoutes(NodeDetail.SOLAR_SYSTEM, NodeDetail.SIRIUS, filters);

		assertNotNull(routeList);
		assertEquals(1, routeList.size());
		// TODO: Add assert to ensure that routes are correct
	}

	// Exercise 10: Determine all different routes starting at Sirius and ending at
	// Sirius with an over traveltime less than 30.
	@Test
	public void test_findRoutes_exercise10() throws Exception {
		List<Function<List<Route>, List<Route>>> filters = new ArrayList<>();
		// Added +1 for source
		filters.add(x -> x.stream().filter(y -> (y.getHours() <= 30)).collect(Collectors.toList()));

		List<Route> routeList = routeFinder.findRoutes(NodeDetail.SIRIUS, NodeDetail.SIRIUS, filters);

		assertNotNull(routeList);
		assertEquals(7, routeList.size());
		// TODO: Add assert to ensure that routes are correct
	}

	@Test(expected = Exception.class)
	public void test_getShortestTravelTime_nullAllInput() throws Exception {
		routeFinder.getShortestTravelTime(null, null);
	}

	// Excercise 8: Determine the duration of the shortest routes (in traveltime)
	// between solar system and Sirius
	@Test
	public void test_getShortestTravelTime_exercise8() throws Exception {
		Integer hours = routeFinder.getShortestTravelTime(NodeDetail.SOLAR_SYSTEM, NodeDetail.SIRIUS);

		assertNotNull(hours);
		assertEquals(Integer.valueOf(9), hours);
	}

	// Excercise 9: Determine the duration of the shortest routes (in traveltime)
	// starting at Alpha Centauri and ending at Alpha Centauri
	@Test
	public void test_getShortestTravelTime_exercise9() throws Exception {
		Integer hours = routeFinder.getShortestTravelTime(NodeDetail.ALPHA_CENTAURI, NodeDetail.ALPHA_CENTAURI);

		assertNotNull(hours);
		assertEquals(Integer.valueOf(9), hours);
	}
}
