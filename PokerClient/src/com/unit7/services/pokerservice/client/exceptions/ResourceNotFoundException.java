/**
 * Date:	21 дек. 2013 г.
 * File:	ResourceNotFoundException.java
 *
 * Author:	Zajcev V.
 */

package com.unit7.services.pokerservice.client.exceptions;

/**
 * @author unit7
 *
 */
public class ResourceNotFoundException extends Exception {

    public ResourceNotFoundException() {
        super();
        // TODO Auto-generated constructor stub
    }

    public ResourceNotFoundException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        // TODO Auto-generated constructor stub
    }

    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
        // TODO Auto-generated constructor stub
    }

    public ResourceNotFoundException(String message) {
        super(message);
        // TODO Auto-generated constructor stub
    }

    public ResourceNotFoundException(Throwable cause) {
        super(cause);
        // TODO Auto-generated constructor stub
    }

}
