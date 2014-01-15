/**
 * Date:	11 янв. 2014 г.
 * File:	SpaceObjectStatus.java
 *
 * Author:	Zajcev V.
 */

package com.unit7.study.computergraphic.solarsystem.core;

/**
 * @author unit7
 * 
 */
public enum SpaceObjectStatus {
    DWARF_PLANET("Dwarf planet"), PLANET("Planet"), SATELLITE("Satellite"), STAR("Star");

    private SpaceObjectStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    private String value;
}
