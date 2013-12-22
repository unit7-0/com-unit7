/**
 * Date:	21 дек. 2013 г.
 * File:	Resources.java
 *
 * Author:	Zajcev V.
 */

package com.unit7.services.pokerservice.client.resources;

import java.awt.Image;

import com.unit7.services.pokerservice.client.exceptions.ResourceNotFoundException;

/**
 * @author unit7
 *
 */
public class Resources {
    public static Image getImageByName(String name) throws ResourceNotFoundException {
        // stub
        return null;
    }
    
    public static final Object STUB = new Object();
    
    public static final String REQUEST_SMALL_BLIND_TITLE = "Введите значение малого блайнда";
    public static final String REQUEST_BIG_BLIND_TITLE = "Введите значение большого блайнда";
    public static final String REQUEST_BET_TITLE = "Выберите ставку";
    public static final String REGEX_DOUBLE = "\\d(\\.(([1-9])|([0-9][1-9]?)))?\\s*";
}
