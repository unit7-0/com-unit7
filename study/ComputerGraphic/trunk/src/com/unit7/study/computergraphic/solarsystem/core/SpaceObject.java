/**
 * Date:	04 янв. 2014 г.
 * File:	SpaceObject.java
 *
 * Author:	Zajcev V.
 */

package com.unit7.study.computergraphic.solarsystem.core;

import com.unit7.study.computergraphic.solarsystem.core.interfaces.ActionObject;
import com.unit7.study.computergraphic.solarsystem.core.interfaces.Coordinable;
import com.unit7.study.computergraphic.solarsystem.core.interfaces.Nameable;

/**
 * @author unit7
 * 
 */
public abstract class SpaceObject implements ActionObject, Nameable, Coordinable<Double> {
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

    @Override
    public Double getX() {
        return x;
    }

    @Override
    public void setX(Double x) {
        this.x = x;
    }

    @Override
    public Double getY() {
        return y;
    }

    @Override
    public void setY(Double y) {
        this.y = y;
    }

    @Override
    public Double getZ() {
        return z;
    }

    @Override
    public void setZ(Double z) {
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

    public double getWeightDegree() {
        return weightDegree;
    }

    public void setWeightDegree(double weightDegree) {
        this.weightDegree = weightDegree;
    }

    public boolean isShowInfo() {
        return showInfo;
    }

    public void setShowInfo(boolean showInfo) {
        this.showInfo = showInfo;
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
    
    private boolean showInfo;
    
    private SpaceObjectStatus status;
}
