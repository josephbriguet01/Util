/*
 * Copyright (C) JasonPercus Systems, Inc - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by JasonPercus, 06/2021
 */
package com.jasonpercus.util.code;



/**
 * Cette classe représente une exception de syntax de code. Par exemple lorsqu'il manque un ; ou une parenthèse...
 * @author JasonPercus
 * @version 1.0
 */
public class SyntaxCodeException extends java.lang.Exception {

    
    
//CONSTRUCTOR
    /**
     * Crée une exception de syntax de code
     * @param message Correspond au message de l'exception
     */
    public SyntaxCodeException(String message) {
        super(message);
    }
    
    
    
}