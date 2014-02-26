/**
 * Date:	22 февр. 2014 г.
 * File:	Edge.java
 *
 * Author:	Zajcev V.
 */

package com.unit7.pathfinder.graphs;

import java.io.Serializable;

/**
 * @author unit7
 * 
 */
public class Edge implements Serializable {
    private static final long serialVersionUID = -3778780094844906477L;
    
    Edge(String name, int weight, Node destination) {
        setWeight(weight); // для проверки отрицательного веса
        this.name = name;
        this.destination = destination;
    }
    
    public void setWeight(int weight) {
        if (weight < 0)
            throw new IllegalArgumentException();
        
        this.weight = weight;
    }

    public int getWeight() {
        return weight;
    }
    
    public Node getDestination() {
        return destination;
    }
    
    public String getName() {
        return name;
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((destination == null) ? 0 : destination.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + weight;
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
        Edge other = (Edge) obj;
        if (destination == null) {
            if (other.destination != null)
                return false;
        } else if (!destination.equals(other.destination))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (weight != other.weight)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return String.format("Edge [name=%s, weight=%s, destination=%s]", name, weight, destination.getName());
    }

    private int weight;
    private String name;
    private Node destination;
}
