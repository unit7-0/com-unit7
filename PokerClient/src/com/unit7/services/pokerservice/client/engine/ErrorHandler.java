/**
 * Date:	21 дек. 2013 г.
 * File:	ErrorHandler.java
 *
 * Author:	Zajcev V.
 */

package com.unit7.services.pokerservice.client.engine;

/**
 * @author unit7
 *
 */
public interface ErrorHandler {
    void handleException(Exception e);
    void handleError(Object e);
}
