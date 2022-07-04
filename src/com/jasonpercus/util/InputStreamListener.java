/*
 * Copyright (C) JasonPercus Systems, Inc - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by JasonPercus, 05/2021
 */
package com.jasonpercus.util;



/**
 * Cette interface permet à un listener d'être au courant des changements d'états d'un InputStream
 * @see InputStream
 * @see java.io.InputStream
 * @author JasonPercus
 * @version 1.0
 */
public interface InputStreamListener {
    
    
    
    /**
     * Lorsque la lecture des données a commancé
     */
    public void started();
    
    /**
     * Lorsqu'il n'y a plus de données à lire
     */
    public void finished();
    
    
    
}