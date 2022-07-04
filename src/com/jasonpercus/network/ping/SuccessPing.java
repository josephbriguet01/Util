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
 * Cette classe représente une réponse positive de ping
 * @author JasonPercus
 * @version 1.0
 */
public class SuccessPing extends ResultPing {
    
    
    
//ATTRIBUTS
    /**
     * Correspond au nombre d'octets envoyés pour le ping
     */
    private final int bytes;
    
    /**
     * Correspond au temps de réponse de l'équipement visé par le ping
     */
    private final int time;
    
    /**
     * Correspond au ttl du ping
     */
    private final int ttl;

    
    
//CONSTRUCTOR
    /**
     * Crée une réponse positive de ping
     * @param from Correspond à l'adresse IP qui a répondu au ping
     * @param bytes Correspond au nombre d'octets envoyés pour le ping
     * @param time Correspond au temps de réponse de l'équipement visé par le ping
     * @param ttl Correspond au ttl du ping
     */
    protected SuccessPing(IPv4 from, int bytes, int time, int ttl) {
        super(from);
        this.bytes = bytes;
        this.time = time;
        this.ttl = ttl;
    }

    
    
//METHODES PUBLICS
    /**
     * Renvoie le nombre d'octets envoyés pour le ping
     * @return Retourne le nombre d'octets envoyés pour le ping
     */
    public int getBytes() {
        return bytes;
    }

    /**
     * Renvoie le temps de réponse de l'équipement visé par le ping
     * @return Retourne le temps de réponse de l'équipement visé par le ping
     */
    public int getTime() {
        return time;
    }

    /**
     * Renvoie le ttl du ping
     * @return Retourne le ttl du ping
     */
    public int getTtl() {
        return ttl;
    }

    /**
     * Renvoie la réponse positive du ping sous la forme d'une chaîne de caractères
     * @return Retourne la réponse positive du ping sous la forme d'une chaîne de caractères
     */
    @Override
    public String toString() {
        return "SuccessPing{" + "from=" + from + ", bytes=" + bytes + ", time=" + time + ", ttl=" + ttl + '}';
    }
    
    
    
}