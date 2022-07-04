/*
 * Copyright (C) JasonPercus Systems, Inc - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by JasonPercus, 06/2018
 */
package com.jasonpercus.network;



/**
 * Cette classe sert d'exception lorsqu'un caractère est invalide.
 * @author Briguet
 * @version 1.0
 */
public class InvalidCharacterException extends RuntimeException {
    
    
    
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
    public InvalidCharacterException(String string) {
        super(string);
    }
    // </editor-fold>
    
    
    
}