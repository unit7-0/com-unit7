/**
 * Date:	17 дек. 2013 г.
 * File:	PokerServiceTests.java
 *
 * Author:	Zajcev V.
 */

package com.unit7.services.pokerservice.test;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import com.unit7.services.pokerservice.client.app.App;

/**
 * @author unit7
 *
 */
public class PokerServiceTests {
    @Test
    public void upApp() {
        App.main(null);
    }
    
    @Before
    public void up() {
        Logger.getRootLogger().setLevel(Level.DEBUG);
    }
}
