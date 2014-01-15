/**
 * Date:	06 янв. 2014 г.
 * File:	Scene.java
 *
 * Author:	Zajcev V.
 */

package com.unit7.study.computergraphic.solarsystem.core.processors;

import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.unit7.study.computergraphic.solarsystem.core.ObjectHolderImpl;
import com.unit7.study.computergraphic.solarsystem.core.interfaces.ActionObject;

/**
 * @author unit7
 * 
 */
public class Scene extends ObjectHolderImpl<ActionObject> implements Runnable {
	@Override
    public void run() {
        while (!quite) {
            Collection<ActionObject> objs = getObjects();
            Iterator<ActionObject> it = objs.iterator();
            if (log.isDebugEnabled()) {
                log.debug(String.format("Objects to show [ %d ]", objs.size()));
            }
            
            while (it.hasNext()) {
                ActionObject obj = it.next();
                if (log.isDebugEnabled()) {
                    log.debug(String.format("Objects [ %s ] starting steping", obj));
                }
                
                obj.action();
                
                if (log.isDebugEnabled()) {
                    log.debug(String.format("Objects [ %s ] finished steping", obj));
                }
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

    public boolean isQuite() {
        return quite;
    }

    public void setQuite(boolean quite) {
        this.quite = quite;
    }

    private long delay = 1; // 200 milliseconds
    private boolean quite;
    
    private static final Logger log = Logger.getLogger(Scene.class);
}
