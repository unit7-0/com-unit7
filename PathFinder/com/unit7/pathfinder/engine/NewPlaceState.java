/**
 * Date:	22 февр. 2014 г.
 * File:	NewPlaceState.java
 *
 * Author:	Zajcev V.
 */

package com.unit7.pathfinder.engine;


/**
 * @author unit7
 * 
 */
public class NewPlaceState implements State {

    private static final long serialVersionUID = 5068083883602342722L;

    /*
     * @see
     * com.unit7.pathfinder.gui.State#addPlace(com.unit7.pathfinder.gui.ImageMap
     * )
     */
    @Override
    public void addPlace(ImageMap map) {
        // TODO Auto-generated method stub

    }

    /*
     * @see
     * com.unit7.pathfinder.gui.State#addConnection(com.unit7.pathfinder.gui
     * .ImageMap)
     */
    @Override
    public void addConnection(ImageMap map) {
        throw new UnsupportedOperationException("Operation does not supported");
    }

    /*
     * @see
     * com.unit7.pathfinder.gui.State#changeConnection(com.unit7.pathfinder.
     * gui.ImageMap)
     */
    @Override
    public void changeConnection(ImageMap map) {
        throw new UnsupportedOperationException("Operation does not supported");
    }

    /*
     * @see
     * com.unit7.pathfinder.gui.State#findPath(com.unit7.pathfinder.gui.ImageMap
     * )
     */
    @Override
    public void findPath(ImageMap map) {
        throw new UnsupportedOperationException("Operation does not supported");
    }

    @Override
    public void showConnections(ImageMap map) {
        throw new UnsupportedOperationException("Operation does not supported");
    }

    /* 
     * @see com.unit7.pathfinder.gui.State#afterClick(com.unit7.pathfinder.gui.ImageMap)
     */
    @Override
    public void afterClick(ImageMap map) {
        map.addPoint();
        map.setState(ImageMap.SELECT_PLACE_STATE);
    }
}
