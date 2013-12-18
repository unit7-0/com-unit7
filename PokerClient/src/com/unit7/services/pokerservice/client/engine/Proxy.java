/**
 * Date:	17 дек. 2013 г.
 * File:	Proxy.java
 *
 * Author:	Zajcev V.
 */

package com.unit7.services.pokerservice.client.engine;

/**
 * @author unit7
 *
 */
public interface Proxy<A, R> {
    R request(A data);
}
