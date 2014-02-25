/**
 * Date:	25 февр. 2014 г.
 * File:	Holder.java
 *
 * Author:	Zajcev V.
 */

package com.unit7.pathfinder.engine;

import java.io.Serializable;

/**
 * @author unit7
 *
 */
public class Holder<T> implements Serializable {
    
    private static final long serialVersionUID = 5986395744406757584L;

    public Holder(T obj) {
        this.obj = obj;
    }
    
    public Holder() {
    }
    
    public T getObj() {
        return obj;
    }

    public void setObj(T obj) {
        this.obj = obj;
    }

    private T obj;
}
