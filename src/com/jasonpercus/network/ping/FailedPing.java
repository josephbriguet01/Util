/*
 * Copyright (C) JasonPercus Systems, Inc - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by JasonPercus, 05/2021
 */
package com.jasonpercus.network.ping;



import com.jasonpercus.network.IPv4;



/**
 * Cette classe représente une réponse négative de ping
 * @author JasonPercus
 * @version 1.0
 */
public class FailedPing extends ResultPing {


    
//CONSTRUCTOR
    /**
     * Crée une réponse négative de ping
     * @param from Correspond à l'adresse IP qui a répondu au ping
     */
    protected FailedPing(IPv4 from) {
        super(from);
    }

    
    
//METHODE PUBLIC
    /**
     * Renvoie la réponse négative du ping sous la forme d'une chaîne de caractères
     * @return Retourne la réponse négative du ping sous la forme d'une chaîne de caractères
     */
    @Override
    public String toString() {
        return "FailedPing{" + "from=" + from + '}';
    }
    
    
    
}