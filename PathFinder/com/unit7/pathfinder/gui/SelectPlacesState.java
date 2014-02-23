/**
 * Date:	22 февр. 2014 г.
 * File:	SelectPlacesState.java
 *
 * Author:	Zajcev V.
 */

package com.unit7.pathfinder.gui;

/**
 * @author unit7
 *
 */
public class SelectPlacesState implements State {

    /* 
     * @see com.unit7.pathfinder.gui.State#addPlace(com.unit7.pathfinder.gui.ImageMap)
     */
    @Override
    public void addPlace(ImageMap map) {
        throw new UnsupportedOperationException("Operation does not supported");
    }

    /* 
     * @see com.unit7.pathfinder.gui.State#addConnection(com.unit7.pathfinder.gui.ImageMap)
     */
    @Override
    public void addConnection(ImageMap map) {
        map.createConnection();
    }

    /* 
     * @see com.unit7.pathfinder.gui.State#changeConnection(com.unit7.pathfinder.gui.ImageMap)
     */
    @Override
    public void changeConnection(ImageMap map) {
       map.changeConnection();
    }

    /* 
     * @see com.unit7.pathfinder.gui.State#findPath(com.unit7.pathfinder.gui.ImageMap)
     */
    @Override
    public void findPath(ImageMap map) {
        map.findPath();
    }

    @Override
    public void showConnections(ImageMap map) {
        map.showConnections();
    }

    /* 
     * @see com.unit7.pathfinder.gui.State#afterClick(com.unit7.pathfinder.gui.ImageMap)
     */
    @Override
    public void afterClick(ImageMap map) {
        map.selectPoint();
    }

}
