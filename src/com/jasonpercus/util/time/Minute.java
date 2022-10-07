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
 * Cette classe représente un nombre de minutes
 * @see Millisecond
 * @see TimeChangeListener
 * @author JasonPercus
 * @version 1.0
 */
public class Minute extends Second {
    
    
    
    // <editor-fold defaultstate="collapsed" desc="SERIAL_VERSION_UID">
    /**
     * Correspond au numéro de série qui identifie le type de dé/sérialization utilisé pour l'objet
     */
    private static final long serialVersionUID = 1L;
    // </editor-fold>

    
    
//CONSTRUCTORS
    /**
     * Crée un objet 0 minute (par défaut les nombres négatifs sont autorisés)
     */
    public Minute() {
    }
    
    /**
     * Crée un objet n minutes (ce nombre n est calculé en fonction de la date fournie)
     * @param date Correspond à la date dont on cherche à récupérer le nombre de minutes
     */
    public Minute(java.util.Date date){
        super(date);
    }

    /**
     * Crée un objet n minutes (par défaut les nombres négatifs sont autorisés)
     * @param minutes Correspond au n minutes
     */
    public Minute(int minutes) {
        super(minutes * 60);
    }

    /**
     * Crée un objet n minutes
     * @param minutes Correspond au n minutes
     * @param authorizationNegativeNumber Détermine si l'objet doit accepter les nombres négatifs ou pas (si non, à chaque fois qu'un nombre négatif sera donné, il sera ramené à 0)
     */
    public Minute(int minutes, boolean authorizationNegativeNumber) {
        super(minutes * 60, authorizationNegativeNumber);
    }
    
    
    
//GETTER & SETTER
    /**
     * Renvoie le nombre de minutes
     * @return Retourne le nombre de minutes
     */
    public synchronized int getMinutes() {
        return (int) (getSeconds() / 60);
    }

    /**
     * Modifie le nombre de minutes (pensez à la valeur du paramètre authorizationNegativeNumber)
     * @param minutes Correspond au nombre de minutes
     */
    public synchronized void setMinutes(long minutes) {
        setSeconds(minutes * 60);
    }
    
    
    
    
//METHODES PUBLICS
    /**
     * Ajoute n minutes
     * @param minutes Correspond au n minutes à ajouter
     */
    public synchronized void addMinutes(long minutes){
        addSeconds(minutes * 60);
    }
    
    /**
     * Supprime n minutes (pensez à la valeur du paramètre authorizationNegativeNumber)
     * @param minutes Correspond au n minutes à supprimer
     */
    public synchronized void removeMinutes(long minutes){
        removeSeconds(minutes * 60);
    }
    
    
    
}