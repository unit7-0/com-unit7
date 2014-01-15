/**
 * Date:	07 янв. 2014 г.
 * File:	DrawnSphere.java
 *
 * Author:	Zajcev V.
 */

package com.unit7.study.computergraphic.solarsystem.core;

import org.apache.log4j.Logger;

import com.unit7.study.computergraphic.solarsystem.core.interfaces.DrawnObject;

/**
 * @author unit7
 * 
 */
public class DrawnSphere extends Sphere implements DrawnObject {
	@Override
	public void action() {
		long t1 = Time.getInstance().getTime();
		long t2 = lastStep;
		long t = t1 - t2;
		if (t <= 0)
			return;

		angle = (angle + dAngle);
		if (angle >= 360) {
			dAngle = dAngle;
			angle = 0;
		}
		
		double dx = 0;
		if (target != null) {
			dx = -lastTargetX + target.getX();
			lastTargetX = target.getX();
		}

		lastStep = t1;
		double s = t * delta;
		double x = getX() + s + dx;

		if (log.isDebugEnabled()) {
			log.debug(String
					.format("curTime: %d, lastTime: %d, deltaT: %d, s: %.2f, x: %.2f, newX: %.2f, y: %.2f, delta: %.2f",
							t1, t2, t, s, getX(), x, getY(), delta));
		}

		// вышли за пределы окружности вращения
		double right = target.getX() + drawnRadius;
		double left = target.getX() - drawnRadius;
		if (x > right || x < left) {
			delta = -delta;
			if (x > right) {
				x = right;
			} else {
				x = left;
			}
		}

		setX(x);
		updateY();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.unit7.study.computergraphic.solarsystem.engine.DrawnObject#getTarget
	 * ()
	 */
	@Override
	public SpaceObject getTarget() {
		return target;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.unit7.study.computergraphic.solarsystem.engine.DrawnObject#setTarget
	 * (com.unit7.study.computergraphic.solarsystem.engine.SpaceObject)
	 */
	@Override
	public void setTarget(SpaceObject target) {
		update();
		lastTargetX = target.getX();
		lastTargetY = target.getY();
		this.target = target;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.unit7.study.computergraphic.solarsystem.engine.DrawnObject#setTimeAround
	 * (long)
	 */
	@Override
	public void setTimeAround(long timeAround) {
		this.timeAround = timeAround;
		update();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.unit7.study.computergraphic.solarsystem.engine.DrawnObject#getTimeAround
	 * ()
	 */
	@Override
	public long getTimeAround() {
		return timeAround;
	}

	@Override
	public void setDrawnRadius(double radius) {
		this.drawnRadius = radius;
		update();
	}

	@Override
	public double getDrawnRadius() {
		return drawnRadius;
	}

	protected void update() {
		double s = drawnRadius * 4;
		delta = s / timeAround;

		if (log.isDebugEnabled()) {
			log.debug(String.format(
					"drawRadius: %.2f, timeAroud: %d, s: %.2f, delta: %.2f",
					drawnRadius, timeAround, s, delta));
		}

		updateY();
	}

	protected void updateY() {
		if (target == null)
			return;

		// движение против часовой. TODO сделать параметром
		double y = target.getY()
				+ (delta < 0 ? -1 : 1)
				* Math.sqrt(Utils.sqr(drawnRadius)
						- Utils.sqr(getX() - target.getX()));

		if (log.isDebugEnabled()) {
			log.debug(String
					.format("this [ %s ] [ %.2f, +-%.2f, %.2f ] target [ %s ] [ %.2f, %.2f, %.2f ]",
							getName(), getX(), y, getZ(), target.getName(),
							target.getX(), target.getY(), target.getZ()));
		}

		if (log.isDebugEnabled()) {
			log.debug("x: " + getX() + " newY: " + y);
		}

		setY(y);
	}

	public double getAngle() {
		return angle;
	}

	public void setAngle(double angle) {
		this.angle = angle;
	}

	public static class Builder extends Sphere.Builder {
		public DrawnSphere build() {
			DrawnSphere sphere = new DrawnSphere();
			sphere.setDrawnRadius(drawnRadius);
			sphere.setTarget(target);
			sphere.setTimeAround(timeAround);
			sphere.setAge(getAge());
			sphere.setName(getName());
			sphere.setRadius(getRadius());
			sphere.setWeight(getWeight());
			sphere.setX(getX());
			sphere.setY(getY());
			sphere.setZ(getZ());

			return sphere;
		}

		public Builder setTarget(SpaceObject target) {
			this.target = target;
			return this;
		}

		public Builder setTimeAround(long timeAround) {
			this.timeAround = timeAround;
			return this;
		}

		public Builder setDrawnRadius(double drawnRadius) {
			this.drawnRadius = drawnRadius;
			return this;
		}

		private SpaceObject target;
		private long timeAround;
		private double drawnRadius;
	}

	private SpaceObject target;
	private long timeAround; // время обращения вокруг объекта в мс
	private double drawnRadius; // радиус обращения / расстояние от солнца до
								// плутона * 1000
	private double delta; // время обращения за 1 мс
	private long lastStep = Time.getInstance().getTime(); // время последнего
															// шага
	private double lastTargetX;
	private double lastTargetY;
	
	private double angle;
	private double dAngle = 0.1;

	private static final Logger log = Logger.getLogger(DrawnSphere.class);
}
