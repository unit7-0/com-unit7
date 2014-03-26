/**
 * Date:	26 марта 2014 г.:7:38:57
 * File:	Node.java
 * 
 * Author:	Zajcev V.
 */

package com.unit7.pathfinder.graphs;

import java.util.List;

/**
 * Общий интерфейс узла графа.
 */
public interface Node {
	void addEdge(Edge edge);

	Edge getEdge(Edge e);

	List<Edge> getEdges();

	boolean containEdge(Edge edge);
	
	String getName();
}
