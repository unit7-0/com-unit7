/**
 * Date:	22 дек. 2013 г.
 * File:	PokerClientTests.java
 *
 * Author:	Zajcev V.
 */

package com.unit7.services.pokerservice.client.tests;

import com.unit7.services.pokerservice.client.resources.Resources;

/**
 * @author unit7
 *
 */
public class PokerClientTests {
    public void loadResources() {
        try {
            Class.forName(Resources.class.getName());
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        PokerClientTests tests = new PokerClientTests();
        tests.loadResources();
    }
}
