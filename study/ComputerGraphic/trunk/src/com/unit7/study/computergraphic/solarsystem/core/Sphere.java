/**
 * Date:	04 янв. 2014 г.
 * File:	Sphere.java
 *
 * Author:	Zajcev V.
 */

package com.unit7.study.computergraphic.solarsystem.core;

import java.math.BigInteger;

import com.unit7.study.computergraphic.solarsystem.core.DrawnSphere.Builder;

/**
 * @author unit7
 * 
 */
public class Sphere extends SpaceObject {

    /**
     * @return радиус в км
     */
    public double getRadius() {
        return radius;
    }

    /**
     * @param radius радиус в км
     */
    public void setRadius(double radius) {
        this.radius = radius;
    }

    @Override
    public void action() {
        // nothing to do
    }

    public static class Builder {
        public Sphere build() {
            Sphere sphere = new Sphere();
            sphere.setAge(age);
            sphere.setName(name);
            sphere.setRadius(radius);
            sphere.setWeight(weight);
            sphere.setX(x);
            sphere.setY(y);
            sphere.setZ(z);

            return sphere;
        }

        public double getWeight() {
            return weight;
        }

        public double getAge() {
            return age;
        }

        public String getName() {
            return name;
        }

        public double getX() {
            return x;
        }

        public double getY() {
            return y;
        }

        public double getZ() {
            return z;
        }

        public double getRadius() {
            return radius;
        }

        public Builder setWeight(double weight) {
            this.weight = weight;
            return this;
        }

        public Builder setAge(double age) {
            this.age = age;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setX(double x) {
            this.x = x;
            return this;
        }

        public Builder setY(double y) {
            this.y = y;
            return this;
        }

        public Builder setZ(double z) {
            this.z = z;
            return this;
        }

        public Builder setRadius(double radius) {
            this.radius = radius;
            return this;
        }

        private double weight;
        private double age;
        private String name;
        private double x, y, z;
        private double radius;
    }

    private double radius; // радиус в км
}
