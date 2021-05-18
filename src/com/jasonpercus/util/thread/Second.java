/*
 * Copyright (C) BRIGUET Systems, Inc - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Briguet, May 2021
 */
package com.jasonpercus.util.thread;



/**
 * Cette classe représente un nombre de secondes
 * @see Millisecond
 * @see TimeChangeListener
 * @author JasonPercus
 * @version 1.0
 */
public class Second extends Millisecond {

    
    
//CONSTRUCTORS
    /**
     * Crée un objet 0 Seconde (par défaut les nombres négatifs sont autorisés)
     */
    public Second() {
    }

    /**
     * Crée un objet n secondes (par défaut les nombres négatifs sont autorisés)
     * @param seconds Correspond au n secondes
     */
    public Second(int seconds) {
        super(seconds * 1000);
    }

    /**
     * Crée un objet n secondes
     * @param seconds Correspond au n secondes
     * @param authorizationNegativeNumber Détermine si l'objet doit accepter les nombres négatifs ou pas (si non, à chaque fois qu'un nombre négatif sera donné, il sera ramené à 0)
     */
    public Second(int seconds, boolean authorizationNegativeNumber) {
        super(seconds * 1000, authorizationNegativeNumber);
    }
    
    
    
//GETTER & SETTER
    /**
     * Renvoie le nombre de secondes
     * @return Retourne le nombre de secondes
     */
    public synchronized int getSeconds() {
        return (int) (getMilliseconds() / 1000);
    }

    /**
     * Modifie le nombre de secondes (pensez à la valeur du paramètre authorizationNegativeNumber)
     * @param seconds Correspond au nombre de secondes
     */
    public synchronized void setSeconds(long seconds) {
        setMilliseconds(seconds * 1000);
    }
    
    
    
}