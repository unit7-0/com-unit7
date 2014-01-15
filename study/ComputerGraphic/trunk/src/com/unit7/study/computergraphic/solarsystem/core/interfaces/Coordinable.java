/**
 * Date:	15 янв. 2014 г.:18:12:34
 * File:	Coordinable.java
 * 
 * Author:	Zajcev V.
 */

package com.unit7.study.computergraphic.solarsystem.core.interfaces;

public interface Coordinable<T extends Number> {
	T getX();

	T getY();

	T getZ();

	void setX(T x);

	void setY(T y);

	void setZ(T z);
}
