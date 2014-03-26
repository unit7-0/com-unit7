/**
 * Date:	26 марта 2014 г.:7:34:07
 * File:	Graph.java
 * 
 * Author:	Zajcev V.
 */

package com.unit7.pathfinder.graphs;

import java.util.List;

/**
 * Общий интерфейс для представления графа. Работает с узлами, реализующими
 * интерфейс Node.
 * 
 * @param <T>
 */
public interface Graph<T extends Node> {
	void add(T node);

	void connect(T f, T second, String name, int weight);

	void setConnectionWeight(T f, T s, String name, int oldWeight, int weight);

	List<Edge> getEdgesFrom(T node);

	List<Edge> getEdgesBetween(T f, T s);

	boolean pathExists(T f, T s);

	List<Edge> getPath(T f, T s);
}
