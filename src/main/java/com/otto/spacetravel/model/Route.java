package com.otto.spacetravel.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

/*
 * Route holds nodes forming a route as well 
 * time required to cover the route in hours.
 */
@Data
@AllArgsConstructor
public class Route {
	private Integer hours;
	private List<NodeDetail> nodeList;
}
