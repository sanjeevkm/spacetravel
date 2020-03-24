package com.otto.spacetravel.data;

/*
 * Interface to load data into the system
 */
public interface DataLoader<S, T> {
	public S loadData(T t);
}
