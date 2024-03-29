/**
 * Date:	06 янв. 2014 г.
 * File:	ObjectHolder.java
 *
 * Author:	Zajcev V.
 */

package com.unit7.study.computergraphic.solarsystem.core.interfaces;

import java.util.List;

/**
 * @author unit7
 * 
 */
public interface ObjectHolder<T> {
    ObjectHolder<T> addObject(T obj);

    boolean removeObject(T obj);

    void setObjects(List<T> objects);

    List<T> getObjects();
}
