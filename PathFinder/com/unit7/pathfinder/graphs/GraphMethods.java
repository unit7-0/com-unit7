/**
 * Date:	26 марта 2014 г.:7:57:38
 * File:	GraphMethods.java
 * 
 * Author:	Zajcev V.
 */

package com.unit7.pathfinder.graphs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class GraphMethods {
	/**
     * Алгоритм Дейкстры
     * @param f
     * @param s
     * @return
     */
    public static <T extends Node> List<Edge> getPath(T f, T s, Collection nodes) {
        List<Edge> res = new ArrayList<Edge>();
        // узел - расстояние до него и предок + вес ребра
        Map<Node, Pair<Integer, Pair<Integer, Node>>> dist = new HashMap<Node, Pair<Integer, Pair<Integer, Node>>>();
        for (Iterator<T> it = nodes.iterator(); it.hasNext(); )  {
            Node n = it.next();
            dist.put(n, new Pair<Integer, Pair<Integer, Node>>(Integer.MAX_VALUE / 2, new Pair<Integer, Node>(-1, n)));
        }
        
        dist.put(f, new Pair<Integer, Pair<Integer, Node>>(0, new Pair<Integer, Node>(-1, f)));
        // вершины для обработки
        Queue<Node> q = new LinkedList<Node>();
        q.add(f);
        while (!q.isEmpty()) {
            Node n = q.poll();
            for (Iterator<Edge> it = n.getEdges().iterator(); it.hasNext(); ) {
                Edge e = it.next();
                int d = dist.get(n).getFirst() + e.getWeight();
                Node to = e.getDestination();
                // обновим расстояние до вершины
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
        // формируем путь из ребер. Идем от последней вершины
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
    
    /**
     * Поиск в глубину
     * @param root
     * @param to
     * @param map
     * @return
     */
    public static <T extends Node> boolean dfs(T root, T to, Set map) {
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
     * Проверяте наличие пути между узлами
     * @param f
     * @param s
     * @return
     */
    public static <T extends Node> boolean pathExists(T f, T s) {
        return dfs(f, s, new HashSet<Node>());
    }
}
