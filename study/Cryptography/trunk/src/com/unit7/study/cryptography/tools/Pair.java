package com.unit7.study.cryptography.tools;

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

    @Override
    public int hashCode() {
        int hash = 31 * first.hashCode();
        hash = hash * 31 + second.hashCode();
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Pair))
            return false;
        
        Pair other = (Pair) obj;
        return first.equals(other.first) && second.equals(other.second);
    }

    private K first;
    private V second;
}
