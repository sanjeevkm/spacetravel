package com.otto.spacetravel.filter;

import lombok.Data;

/*
 * Defines various filters to be used to limit possible routes. 
 */
@Data
public class RouteFilter {
	private Integer maxNumberOfStops;
	private Integer exactNumberOfStops;
	private Integer maxHoursAllowed;
}
