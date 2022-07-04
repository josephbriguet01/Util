/*
 * Copyright (C) JasonPercus Systems, Inc - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by JasonPercus, 05/2021
 */
package com.jasonpercus.util.time;



/**
 * Cette classe représente un nombre de secondes
 * @see Millisecond
 * @see TimeChangeListener
 * @author JasonPercus
 * @version 1.0
 */
public class Second extends Millisecond {
    
    
    
    // <editor-fold defaultstate="collapsed" desc="SERIAL_VERSION_UID">
    /**
     * Correspond au numéro de série qui identifie le type de dé/sérialization utilisé pour l'objet
     */
    private static final long serialVersionUID = 1L;
    // </editor-fold>

    
    
//CONSTRUCTORS
    /**
     * Crée un objet 0 Seconde (par défaut les nombres négatifs sont autorisés)
     */
    public Second() {
    }
    
    /**
     * Crée un objet n secondes (ce nombre n est calculé en fonction de la date fournie)
     * @param date Correspond à la date dont on cherche à récupérer le nombre de secondes
     */
    public Second(java.util.Date date){
        super(date);
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
    
    
    
    
//METHODES PUBLICS
    /**
     * Ajoute n secondes
     * @param seconds Correspond au n secondes à ajouter
     */
    public synchronized void addSeconds(long seconds){
        addMilliseconds(seconds * 1000);
    }
    
    /**
     * Supprime n secondes (pensez à la valeur du paramètre authorizationNegativeNumber)
     * @param seconds Correspond au n secondes à supprimer
     */
    public synchronized void removeSeconds(long seconds){
        removeMilliseconds(seconds * 1000);
    }
    
    
    
}