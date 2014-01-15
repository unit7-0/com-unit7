/**
 * Date:	07 янв. 2014 г.
 * File:	Utils.java
 *
 * Author:	Zajcev V.
 */

package com.unit7.study.computergraphic.solarsystem.core;

/**
 * @author unit7
 *
 */
public class Utils {
    public static double sqr(double a) {
        return a * a;
    }
    
    public static long daysToMilliseconds(double days) {
        long time = (long) (days * 24 * 60 * 60 * 1000);
        return time;
    }
}
