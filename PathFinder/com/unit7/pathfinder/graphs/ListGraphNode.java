/**
 * Date:	22 февр. 2014 г.
 * File:	Node.java
 *
 * Author:	Zajcev V.
 */

package com.unit7.pathfinder.graphs;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author unit7
 * 
 */
public class ListGraphNode implements Node, Serializable {
	private static final long serialVersionUID = -8771958341365469519L;

	public ListGraphNode() {
	}

	public ListGraphNode(String name) {
		this.name = name;
	}

	@Override
	public void addEdge(Edge edge) {
		if (!edges.contains(edge))
			edges.add(edge);
	}

	@Override
	public boolean containEdge(Edge edge) {
		return edges.contains(edge);
	}

	@Override
	public Edge getEdge(Edge e) {
		for (Iterator<Edge> it = edges.iterator(); it.hasNext();) {
			Edge edge = it.next();
			if (edge.equals(e))
				return edge;
		}

		return null;
	}

	@Override
	public List<Edge> getEdges() {
		return edges;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ListGraphNode other = (ListGraphNode) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return String.format("Node [name=%s, edges=%s]", name, edges);
	}

	private String name;
	private List<Edge> edges = new ArrayList<Edge>();
}
