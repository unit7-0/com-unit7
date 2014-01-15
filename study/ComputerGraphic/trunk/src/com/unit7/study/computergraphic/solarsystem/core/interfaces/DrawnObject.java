/**
 * Date:	07 янв. 2014 г.
 * File:	DrawObject.java
 *
 * Author:	Zajcev V.
 */

package com.unit7.study.computergraphic.solarsystem.core.interfaces;

import com.unit7.study.computergraphic.solarsystem.core.SpaceObject;

/**
 * @author unit7
 * 
 */
public interface DrawnObject {
    public SpaceObject getTarget();

    public void setTarget(SpaceObject target);
    
    public void setTimeAround(long timeAround);
    
    public long getTimeAround();
    
    public void setDrawnRadius(double radius);
    
    public double getDrawnRadius();
}
