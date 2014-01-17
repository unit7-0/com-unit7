/**
 * Date:	17 янв. 2014 г.:6:20:39
 * File:	Resources.java
 * 
 * Author:	Zajcev V.
 */

package com.unit7.study.computergraphic.solarsystem.resources;

import java.io.InputStream;

public class Resources {
	public static InputStream getResourceAsStream(String name) {
		return Resources.class.getResourceAsStream(name);
	}
}
