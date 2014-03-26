/**
 * Date:        22 февр. 2014 г.
 * File:        ListGraph.java
 *
 * Author:      Zajcev V.
 */

package com.unit7.pathfinder.graphs;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.Set;

/**
 * @author unit7
 * 
 */
public class ListGraph<T extends Node> implements Graph<T>, Serializable {
    private static final long serialVersionUID = -1470389398513225245L;

    @Override
    public void add(T node) {
        if (!nodes.contains(node))
            nodes.add(node);
    }

    /**
     * Связать два узла ребром
     * @param f
     * @param s
     * @param name
     * @param weight
     */
    @Override
    public void connect(T f, T s, String name, int weight) {
        if (weight < 0)
            throw new IllegalArgumentException("weight < 0");

        if (!(nodes.contains(f) && nodes.contains(s)))
            throw new NoSuchElementException();
        
        Edge e1 = new Edge(name, weight, s);
        Edge e2 = new Edge(name, weight, f);
        
        f.addEdge(e1);
        s.addEdge(e2);
    }

    /**
     * Устанавливает вес ребра в обе стороны
     * @param f
     * @param s
     * @param name
     * @param oldWeight
     * @param weight
     */
    public void setConnectionWeight(Node f, Node s, String name, int oldWeight, int weight) {
        if (!(nodes.contains(f) && nodes.contains(s)))
            throw new NoSuchElementException();
        
        Edge e1 = f.getEdge(new Edge(name, oldWeight, s));
        Edge e2 = s.getEdge(new Edge(name, oldWeight, f));
        if (e1 == null || e2 == null)
            throw new NoSuchElementException();
        
        e1.setWeight(weight);
        e2.setWeight(weight);
    }
    
    public List<T> getNodes() {
        return Collections.unmodifiableList(nodes);
    }
    
    /**
     * Возвращает список ребер из указнного узла
     * @param node
     * @return
     */
    public List<Edge> getEdgesFrom(Node node) {
        if (!nodes.contains(node))
            throw new NoSuchElementException();
        
        return Collections.unmodifiableList(node.getEdges());
    }
    
    /**
     * Возвращает список ребер между указанными узлами
     * @param f
     * @param s
     * @return
     */
    public List<Edge> getEdgesBetween(Node f, Node s) {
        if (!(nodes.contains(f) && nodes.contains(s)))
            throw new NoSuchElementException();
        
        List<Edge> res = new ArrayList<Edge>();
        List<Edge> edges = f.getEdges();
        for (Iterator<Edge> it = edges.iterator(); it.hasNext(); ) {
            Edge e = it.next();
            if (e.getDestination().equals(s))
                res.add(e);
        }
        
        return res;
    }
    
    /**
     * Проверяте наличие пути между узлами
     * @param f
     * @param s
     * @return
     */
    public boolean pathExists(Node f, Node s) {
        return GraphMethods.pathExists(f, s);
    }
    
    /**
     * Наискорейший путь
     * @param f
     * @param s
     * @return
     */
    public List<Edge> getPath(Node f, Node s) {
        return GraphMethods.getPath(f, s, nodes);
    }
    
    @Override
    public String toString() {
        return "ListGraph [nodes=" + nodes + "]";
    }

    private List<T> nodes = new ArrayList<T>();
}