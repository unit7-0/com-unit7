/**
 * Date:	22 февр. 2014 г.
 * File:	State.java
 *
 * Author:	Zajcev V.
 */

package com.unit7.pathfinder.gui;

/**
 * @author unit7
 *
 */
public interface State {
    void addPlace(ImageMap map);
    void addConnection(ImageMap map);
    void changeConnection(ImageMap map);
    void findPath(ImageMap map);
    void showConnections(ImageMap map);
    void afterClick(ImageMap map);
}
