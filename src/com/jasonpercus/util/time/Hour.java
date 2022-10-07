/*
 * Copyright (C) JasonPercus Systems, Inc - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by JasonPercus, 07/2022
 */
package com.jasonpercus.util.time;



/**
 * Cette classe représente un nombre d'heures
 * @see Millisecond
 * @see TimeChangeListener
 * @author JasonPercus
 * @version 1.0
 */
public class Hour extends Minute {
    
    
    
    // <editor-fold defaultstate="collapsed" desc="SERIAL_VERSION_UID">
    /**
     * Correspond au numéro de série qui identifie le type de dé/sérialization utilisé pour l'objet
     */
    private static final long serialVersionUID = 1L;
    // </editor-fold>

    
    
//CONSTRUCTORS
    /**
     * Crée un objet 0 heure (par défaut les nombres négatifs sont autorisés)
     */
    public Hour() {
    }
    
    /**
     * Crée un objet n heures (ce nombre n est calculé en fonction de la date fournie)
     * @param date Correspond à la date dont on cherche à récupérer le nombre d'heures
     */
    public Hour(java.util.Date date){
        super(date);
    }

    /**
     * Crée un objet n heures (par défaut les nombres négatifs sont autorisés)
     * @param hours Correspond au n heures
     */
    public Hour(int hours) {
        super(hours * 60);
    }

    /**
     * Crée un objet n heures
     * @param hours Correspond au n heures
     * @param authorizationNegativeNumber Détermine si l'objet doit accepter les nombres négatifs ou pas (si non, à chaque fois qu'un nombre négatif sera donné, il sera ramené à 0)
     */
    public Hour(int hours, boolean authorizationNegativeNumber) {
        super(hours * 60, authorizationNegativeNumber);
    }
    
    
    
//GETTER & SETTER
    /**
     * Renvoie le nombre d'heures
     * @return Retourne le nombre d'heures
     */
    public synchronized int getHours() {
        return (int) (getMinutes() / 60);
    }

    /**
     * Modifie le nombre d'heures (pensez à la valeur du paramètre authorizationNegativeNumber)
     * @param minutes Correspond au nombre d'heures
     */
    public synchronized void setHours(long minutes) {
        setMinutes(minutes * 60);
    }
    
    
    
    
//METHODES PUBLICS
    /**
     * Ajoute n heures
     * @param hours Correspond au n heures à ajouter
     */
    public synchronized void addHours(long hours){
        addMinutes(hours * 60);
    }
    
    /**
     * Supprime n heures (pensez à la valeur du paramètre authorizationNegativeNumber)
     * @param hours Correspond au n heures à supprimer
     */
    public synchronized void removeHours(long hours){
        removeMinutes(hours * 60);
    }
    
    
    
}