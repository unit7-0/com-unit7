/**
 * Date:	21 дек. 2013 г.
 * File:	ErrorException.java
 *
 * Author:	Zajcev V.
 */

package com.unit7.services.pokerservice.client.exceptions;

/**
 * @author unit7
 *
 */
public class ErrorException extends Exception {
    public ErrorException() {
        super();
        // TODO Auto-generated constructor stub
    }

    public ErrorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        // TODO Auto-generated constructor stub
    }

    public ErrorException(String message, Throwable cause) {
        super(message, cause);
        // TODO Auto-generated constructor stub
    }

    public ErrorException(String message) {
        super(message);
        // TODO Auto-generated constructor stub
    }

    public ErrorException(Throwable cause) {
        super(cause);
        // TODO Auto-generated constructor stub
    }

    private static final long serialVersionUID = 6791090196620121042L;

}
