/**
 * Date:	21 дек. 2013 г.
 * File:	GUIErrorHandler.java
 *
 * Author:	Zajcev V.
 */

package com.unit7.services.pokerservice.client.engine;

import javax.swing.JOptionPane;

import org.apache.log4j.Logger;

import com.unit7.services.pokerservice.client.commands.containers.ErrorCommandContainer;

/**
 * @author unit7
 *
 */
public class GUIErrorHandler implements ErrorHandler {

    /* (non-Javadoc)
     * @see com.unit7.services.pokerservice.client.engine.ErrorHandler#handleException(java.lang.Exception)
     */
    @Override
    public void handleException(Exception e) {
        JOptionPane.showMessageDialog(null, e.getLocalizedMessage());
        log.error(e.getLocalizedMessage());
        throw new RuntimeException(e);
    }

    /* (non-Javadoc)
     * @see com.unit7.services.pokerservice.client.engine.ErrorHandler#handleError(java.lang.Object)
     */
    @Override
    public void handleError(Object e) {
        if (e instanceof ErrorCommandContainer) {
            ErrorCommandContainer container = (ErrorCommandContainer) e;
            JOptionPane.showMessageDialog(null, container.getMessage());
            log.error(container.getMessage());
            throw new RuntimeException(container.getMessage());
        }
        throw new RuntimeException(e.toString());
    }

    private Logger log = Logger.getLogger(GUIErrorHandler.class);
}
