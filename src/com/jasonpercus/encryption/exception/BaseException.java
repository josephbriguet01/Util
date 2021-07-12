/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jasonpercus.encryption.exception;



import com.jasonpercus.encryption.base.Base;



/**
 * Cette classe représente les exceptions liées à un problème de changement de base
 * @see Base
 * @author Briguet
 * @version 1.0
 */
public class BaseException extends java.lang.RuntimeException {
    
    
    
    // <editor-fold defaultstate="collapsed" desc="SERIAL_VERSION_UID">
    /**
     * Correspond au numéro de série qui identifie le type de dé/sérialization utilisé pour l'objet
     */
    private static final long serialVersionUID = 1L;
    // </editor-fold>

    
    
//CONSTRUCTOR
    /**
     * Crée une exception
     * @param message Correspond au message de l'exception
     */
    public BaseException(String message) {
        super(message);
    }
    
    
    
}