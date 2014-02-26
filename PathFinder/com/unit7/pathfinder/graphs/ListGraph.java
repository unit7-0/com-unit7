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
public class ListGraph implements Serializable {
    private static final long serialVersionUID = -1470389398513225245L;

    public void add(Node node) {
        if (!nodes.contains(node))
            nodes.add(node);
    }

    public void connect(Node f, Node s, String name, int weight) {
        if (weight < 0)
            throw new IllegalArgumentException("weight < 0");

        if (!(nodes.contains(f) && nodes.contains(s)))
            throw new NoSuchElementException();
        
        Edge e1 = new Edge(name, weight, s);
        Edge e2 = new Edge(name, weight, f);
        
        f.addEdge(e1);
        s.addEdge(e2);
    }

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
    
    public List<Node> getNodes() {
        return Collections.unmodifiableList(nodes);
    }
    
    public List<Edge> getEdgesFrom(Node node) {
        if (!nodes.contains(node))
            throw new NoSuchElementException();
        
        return Collections.unmodifiableList(node.getEdges());
    }
    
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
    
    public boolean pathExists(Node f, Node s) {
        return dfs(f, s, new HashSet<Node>());
    }
    
    public boolean dfs(Node root, Node to, Set<Node> map) {
        if (map.contains(root))
            return false;
        
        map.add(root);
        List<Edge> edges = root.getEdges();
        for (Iterator<Edge> it = edges.iterator(); it.hasNext(); ) {
            Edge e = it.next();
            Node node = e.getDestination();
            if (node.equals(to) || dfs(node, to, map))
                return true;
        }
        
        return false;
    }
    
    /**
     * Алгоритм Дейкстры
     * @param f
     * @param s
     * @return
     */
    public List<Edge> getPath(Node f, Node s) {
        List<Edge> res = new ArrayList<Edge>();
        // узел - расстояние до него и предок + вес ребра
        Map<Node, Pair<Integer, Pair<Integer, Node>>> dist = new HashMap<Node, Pair<Integer, Pair<Integer, Node>>>();
        for (Iterator<Node> it = nodes.iterator(); it.hasNext(); )  {
            Node n = it.next();
            dist.put(n, new Pair<Integer, Pair<Integer, Node>>(Integer.MAX_VALUE / 2, new Pair<Integer, Node>(-1, n)));
        }
        
        dist.put(f, new Pair<Integer, Pair<Integer, Node>>(0, new Pair<Integer, Node>(-1, f)));
        Queue<Node> q = new LinkedList<Node>();
        q.add(f);
        while (!q.isEmpty()) {
            Node n = q.poll();
            for (Iterator<Edge> it = n.getEdges().iterator(); it.hasNext(); ) {
                Edge e = it.next();
                int d = dist.get(n).getFirst() + e.getWeight();
                Node to = e.getDestination();
                if (d < dist.get(to).getFirst()) {
                    Pair<Integer, Pair<Integer, Node>> p = dist.get(to);
                    p.setFirst(d);
                    Pair<Integer, Node> anc = p.getSecond();
                    anc.setSecond(n);
                    anc.setFirst(e.getWeight());
                    q.add(to);
                }
            }
        }
        
        // пути нет
        if (dist.get(s).getFirst() >= Integer.MAX_VALUE / 2) {
            return res;
        }
        
        Node n = s;
        while (!n.equals(f)) {
            Pair<Integer, Pair<Integer, Node>> cur = dist.get(n);
            Node anc = cur.getSecond().getSecond();
            int weight = cur.getSecond().getFirst();
            for (Iterator<Edge> it = anc.getEdges().iterator(); it.hasNext(); ) {
                Edge e = it.next();
                if (e.getWeight() == weight && e.getDestination().equals(n)) {
                    res.add(e);
                    n = anc;
                    break;
                }
            }
        }
        
        Collections.reverse(res);
        return res;
    }
    
    @Override
    public String toString() {
        return "ListGraph [nodes=" + nodes + "]";
    }

    private List<Node> nodes = new ArrayList<Node>();
}