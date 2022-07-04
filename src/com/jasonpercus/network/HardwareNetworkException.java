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
 * Crée une exception lorsqu'une erreur survient lors de la récupération des cartes réseaux.
 * @author Briguet
 * @version 1.0
 */
public class HardwareNetworkException extends RuntimeException {
    
    
    
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
    public HardwareNetworkException(String string) {
        super(string);
    }
    // </editor-fold>
    
    
    
}