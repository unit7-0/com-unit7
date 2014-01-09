/**
 * Date:	09 янв. 2014 г.:10:05:28
 * File:	GravitationSystem.java
 * 
 * Author:	Zajcev V.
 */

package com.unit7.study.computergraphic.solarsystem.engine;

import java.util.Iterator;
import java.util.concurrent.TimeUnit;

public class GravitationSystem extends ObjectHolderImpl<SpaceObject> implements
		Runnable {
	@Override
	public void run() {
		while (true) {
			Iterator<SpaceObject> it = getObjects().iterator();
			while (it.hasNext()) {
				SpaceObject obj0 = it.next();
				Iterator<SpaceObject> it1 = getObjects().iterator();
				while (it1.hasNext()) {
					SpaceObject obj = it1.next();
					if (obj0.equals(obj))
						continue;

					double d = Math.sqrt(Utils.sqr(obj.getX() - obj0.getX())
							+ Utils.sqr(obj.getY() - obj0.getY())
							+ Utils.sqr(obj.getZ() - obj0.getZ()));
					if (d > 3) {
						obj0.addVx(0.00007 * obj.getWeight() / d / d * (obj.getX() - obj0.getX() / d));
						obj0.addVy(0.00007 * obj.getWeight() / d / d * (obj.getY() - obj0.getY() / d));
						obj0.addVz(0.00007 * obj.getWeight() / d / d * (obj.getZ() - obj0.getZ() / d));
					}
				}
				
				obj0.setX(obj0.getX() + obj0.getVx());
				obj0.setY(obj0.getY() + obj0.getVy());
				obj0.setZ(obj0.getZ() + obj0.getVz());
			}

			try {
				TimeUnit.MILLISECONDS.sleep(delay);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public long getDelay() {
		return delay;
	}

	public void setDelay(long delay) {
		this.delay = delay;
	}

	private long delay = 200;
}
