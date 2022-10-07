/*
 * Copyright (C) BRIGUET Systems, Inc - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Briguet, 05/2020
 */
package com.jasonpercus.util.mysql;



/**
 * Cette classe permet d'annoter un attribut (PrimaryKey) d'une classe d'objet.
 * 
 * En attribuant cette annotation à l'attribut d'un objet, la recherche d'un élément se fera sur l'attribut contenant cette annotation
 * 
 * @author JasonPercus
 * @version 1.0
 */
@java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@java.lang.annotation.Target(java.lang.annotation.ElementType.FIELD)
public @interface PrimaryKey {
    
    
    
}