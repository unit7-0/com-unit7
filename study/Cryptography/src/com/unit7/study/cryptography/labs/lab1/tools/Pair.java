package com.unit7.study.cryptography.labs.lab1.tools;

public class Pair<K, V> {
    public Pair() {
    }
    
    public Pair(K first, V second) {
        this.first = first;
        this.second = second;
    }
    
    public K getFirst() {
        return first;
    }

    public void setFirst(K first) {
        this.first = first;
    }

    public V getSecond() {
        return second;
    }

    public void setSecond(V second) {
        this.second = second;
    }

    private K first;
    private V second;
}
