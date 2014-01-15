/**
 * Date:	06 янв. 2014 г.
 * File:	ObjectHolderImpl.java
 *
 * Author:	Zajcev V.
 */

package com.unit7.study.computergraphic.solarsystem.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.unit7.study.computergraphic.solarsystem.core.interfaces.ObjectHolder;

/**
 * @author unit7
 *
 */
public class ObjectHolderImpl<T> implements ObjectHolder<T> {

    /* (non-Javadoc)
     * @see com.unit7.study.computergraphic.solarsystem.engine.ObjectHolder#addObject(java.lang.Object)
     */
    @Override
    public ObjectHolder<T> addObject(T obj) {
        objects.add(obj);
        return this;
    }

    /* (non-Javadoc)
     * @see com.unit7.study.computergraphic.solarsystem.engine.ObjectHolder#removeObject(java.lang.Object)
     */
    @Override
    public boolean removeObject(T obj) {
        return objects.remove(obj);
    }

    /* (non-Javadoc)
     * @see com.unit7.study.computergraphic.solarsystem.engine.ObjectHolder#setObjects(java.util.List)
     */
    @Override
    public void setObjects(List<T> objects) {
        this.objects = objects;
    }

    /* (non-Javadoc)
     * @see com.unit7.study.computergraphic.solarsystem.engine.ObjectHolder#getObjects()
     */
    @Override
    public List<T> getObjects() {
        return Collections.unmodifiableList(objects);
    }

    private List<T> objects = new ArrayList<T>();
}
