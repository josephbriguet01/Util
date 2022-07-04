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
 * Cette classe sert d'exception lorsqu'une adresse de réseau ne se combine pas avec son masque de sous réseau (exemple: 192.168.201.0/25). En effet le 1 de 201 entre en conflit avec le début du masque.
 * @author Briguet
 * @version 1.0
 */
public class InvalidCombinaisonAddressException extends RuntimeException {
    
    
    
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
    public InvalidCombinaisonAddressException(String string) {
        super(string);
    }
    // </editor-fold>
    
    
    
}