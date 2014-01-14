/**
 * Date:	04 янв. 2014 г.
 * File:	SpaceObject.java
 *
 * Author:	Zajcev V.
 */

package com.unit7.study.computergraphic.solarsystem.engine;

import java.math.BigInteger;

/**
 * @author unit7
 * 
 */
public abstract class SpaceObject implements ActionObject, Nameable {
    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAge() {
        return age;
    }

    public void setAge(double age) {
        this.age = age;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public double getVx() {
		return vx;
	}

	public void setVx(double vx) {
		this.vx = vx;
	}

	public double getVy() {
		return vy;
	}

	public void setVy(double vy) {
		this.vy = vy;
	}

	public double getVz() {
		return vz;
	}

	public void setVz(double vz) {
		this.vz = vz;
	}

	public void addVx(double v) {
		vx += v;
	}
	
	public void addVy(double v) {
		vy += v;
	}
	
	public void addVz(double v) {
		vz += v;
	}
	
	public SpaceObjectStatus getStatus() {
        return status;
    }

    public void setStatus(SpaceObjectStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return String.format("SpaceObject [weight=%s, age=%s, name=%s]", weight, age, name);
    }

    private double weight; // масса * 10^23
    private double weightDegree = 23;
    private double age; // возраст в млрд лет
    private String name;
    private double x, y, z;
    private double vx, vy, vz;
    
    private SpaceObjectStatus status;
}
