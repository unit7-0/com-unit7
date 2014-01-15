/**
 * Date:	07 янв. 2014 г.
 * File:	Time.java
 *
 * Author:	Zajcev V.
 */

package com.unit7.study.computergraphic.solarsystem.core;

import org.apache.log4j.Logger;

/**
 * Реализация программного времени для системы
 * @author unit7
 *
 */
public class Time {
    private Time() {
    }
    
    public static Time getInstance() {
        return instance;
    }
    
    public void changeSpeed(double newRatio) {
        update();
        if (ratio <= 0)
            throw new IllegalArgumentException("ratio == 0");
        
        this.ratio = newRatio;
        if (log.isDebugEnabled()) {
            log.debug(String.format("this.ratio: %.2f, newRatio: %.2f", ratio, newRatio));
        }
    }
    
    public void faster() {
        update();
        changeSpeed(ratio + 1);
    }
    
    public void slow() {
        update();
        double delta = 1;
        if (ratio <= 1)
            delta = 0.1;
        else if (ratio < 0.2)
            return;
        
        changeSpeed(ratio - delta);
    }
    
    public long getTime() {
        update();
        return time;
    }
    
    private void update() {
        if (log.isDebugEnabled()) {
            log.debug(String.format("time: %d, lastAccessed: %d", time, lastAccessed));
        }
        
        long diff = System.currentTimeMillis() - lastAccessed;
        if (log.isDebugEnabled()) {
            log.debug(String.format("diff: %d, ratio: %.2f", diff, ratio));
        }
        
        lastAccessed = System.currentTimeMillis();
        diff = (long) (diff * ratio);
        
        if (log.isDebugEnabled()) {
            log.debug(String.format("recalc diff: %d", diff));
        }
        time += diff;
    }
    
    private double ratio = 1;
    private long time = System.currentTimeMillis();
    private long lastAccessed = System.currentTimeMillis(); // мировое время последнего доступа
    
    private static final Time instance = new Time();
    
    private static final Logger log = Logger.getLogger(Time.class);
}
