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
 * Crée une exception lorsqu'une erreur survient lors d'un appel de méthode qui n'est pas supporté pas l'OS
 * @author Briguet
 * @version 1.0
 */
public class OSNotSupportedException extends RuntimeException {
    
    
    
    // <editor-fold defaultstate="collapsed" desc="SERIAL_VERSION_UID">
    /**
     * Correspond au numéro de série qui identifie le type de dé/sérialization utilisé pour l'objet
     */
    private static final long serialVersionUID = 1L;
    // </editor-fold>
    
    
    
    // <editor-fold defaultstate="collapsed" desc="CONSTRUCTOR">
    /**
     * Crée une exception
     * @param string Correspond au message de l'exception
     */
    public OSNotSupportedException(String string) {
        super(string);
    }
    // </editor-fold>
    
    
    
}