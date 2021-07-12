/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jasonpercus.mysql;



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