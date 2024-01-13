/*
 * Copyright (C) JasonPercus Systems, Inc - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by JasonPercus, January 2024
 */
package com.jasonpercus.util;



/**
 * Cette classe représente une exception sur un objet de type {@link CategorizedCollection}
 * @author JasonPercus
 * @version 1.0
 */
public class CategorizedCollectionException extends RuntimeException {

    
    
//CONSTRUCTORS
    /**
     * Crée un objet {@link CategorizedCollectionException}
     * @param message Correspond au message de l'exception
     */
    public CategorizedCollectionException(String message) {
        super(message);
    }

    /**
     * Crée un objet {@link CategorizedCollectionException}
     * @param message Correspond au message de l'exception
     * @param cause Correspond à la cause de l'exception
     */
    public CategorizedCollectionException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Crée un objet {@link CategorizedCollectionException}
     * @param cause Correspond à la cause de l'exception
     */
    public CategorizedCollectionException(Throwable cause) {
        super(cause);
    }
    
    
    
}