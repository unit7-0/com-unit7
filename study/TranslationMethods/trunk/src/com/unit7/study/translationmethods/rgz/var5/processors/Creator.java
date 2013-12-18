/**
 * Date:	15 дек. 2013 г.
 * File:	Creator.java
 *
 * Author:	Zajcev V.
 */

package com.unit7.study.translationmethods.rgz.var5.processors;

import com.unit7.study.translationmethods.labs.lab3.exceptions.InformationException;

/**
 * @author unit7
 *
 */
public interface Creator<T> {
    T create() throws InformationException;
    T getCreated() throws Exception;
    
    static final String EMPTY_PARAMETER = "Параметер не создан. Сначала необоходимо вызвать метод create";
}
