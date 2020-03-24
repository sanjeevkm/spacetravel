package com.otto.spacetravel.logic;

import lombok.Getter;
import lombok.ToString;

/*
 * Model defining source (NodeDetail-id), destination (NodeDetail-id) 
 * and time required to move from source to destination in hours.
 */
@Getter
@ToString
public class Node {
	private Integer source;
	private Integer destination;
	private Integer hours;

	public Node(Integer source, Integer destination, Integer hours) {
		this.source = source;
		this.destination = destination;
		this.hours = hours;
	}
}