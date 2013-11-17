package com.unit7.study.translationmethods.labs.chains;

import java.util.ArrayList;
import java.util.List;

public class Vertex {
    public Vertex(String name) {
        this.name = name;
        this.childs = new ArrayList<Vertex>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Vertex> getChilds() {
        return childs;
    }

    public void setChilds(List<Vertex> childs) {
        this.childs = childs;
    }
    
    public void addChild(Vertex child) {
        childs.add(child);
    }

    private String name;
    private List<Vertex> childs;
}
