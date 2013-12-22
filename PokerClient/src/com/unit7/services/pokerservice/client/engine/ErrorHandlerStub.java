/**
 * Date:	21 дек. 2013 г.
 * File:	ErrorHandlerStub.java
 *
 * Author:	Zajcev V.
 */

package com.unit7.services.pokerservice.client.engine;

/**
 * @author unit7
 *
 */
public class ErrorHandlerStub implements ErrorHandler {

    /* (non-Javadoc)
     * @see com.unit7.services.pokerservice.client.engine.ErrorHandler#handleException(java.lang.Exception)
     */
    @Override
    public void handleException(Exception e) {
        throw new RuntimeException(e);
    }

    /* (non-Javadoc)
     * @see com.unit7.services.pokerservice.client.engine.ErrorHandler#handleError(java.lang.Object)
     */
    @Override
    public void handleError(Object e) {
        throw new RuntimeException(e.toString());
    }

}
