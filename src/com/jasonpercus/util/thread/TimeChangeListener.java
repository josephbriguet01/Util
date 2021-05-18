/*
 * Copyright (C) BRIGUET Systems, Inc - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Briguet, May 2021
 */
package com.jasonpercus.util.thread;



/**
 * Cette interface a pour but d'avertir les objets des changements d'une valeur d'un objet Millisecond
 * @see Millisecond
 * @author JasonPercus
 * @version 1.0
 */
public interface TimeChangeListener {
    
    
    
    /**
     * Lorsque l'objet Millisecond à changé de valeur
     * @param millisecond Correspond à l'objet Millisecond qui a changé de valeur
     */
    public void changed(Millisecond millisecond);
    
    
    
}