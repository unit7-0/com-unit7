/**
 * Date:	25 февр. 2014 г.
 * File:	NamedHolder.java
 *
 * Author:	Zajcev V.
 */

package com.unit7.pathfinder.engine;

/**
 * @author unit7
 *
 */
public class NamedHolder<T> extends Holder<T> {
    private static final long serialVersionUID = 5311210781240621458L;

    public NamedHolder() {
    }

    public NamedHolder(String name) {
        this.name = name;
    }
    
    public NamedHolder(T obj) {
        super(obj);
        // TODO Auto-generated constructor stub
    }

    public NamedHolder(T obj, String name) {
        super(obj);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;
}
