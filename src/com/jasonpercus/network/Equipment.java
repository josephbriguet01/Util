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
 * Cette classe représente un équipement d'un réseau LAN
 * @author Briguet
 * @version 1.0
 */
public class Equipment implements java.io.Serializable, Comparable<Equipment>, Cloneable {
    
    
    
    // <editor-fold defaultstate="collapsed" desc="SERIAL_VERSION_UID">
    /**
     * Correspond au numéro de série qui identifie le type de dé/sérialization utilisé pour l'objet
     */
    private static final long serialVersionUID = 1L;
    // </editor-fold>
    
    
    
    // <editor-fold defaultstate="collapsed" desc="ATTRIBUTS">
    /**
     * Correspond à l'adresse IP de l'équipement
     */
    private IPv4 ip;
    /**
     * Correspond au nom de l'équipement
     */
    private String hostname;
    /**
     * Correspond à true si l'équipement existe, sinon false
     */
    private boolean alive;
    // </editor-fold>

    
    
    // <editor-fold defaultstate="collapsed" desc="CONSTRUCTORS">
    /**
     * Crée un objet Equipment par défaut
     */
    private Equipment() {
    }

    /**
     * Crée un objet Equipment
     * @param ip Correspond à l'adresse IP de l'équipement
     * @param hostname Correspond au nom de l'équipement
     * @param alive Correspond à true si l'équipement existe, sinon false
     */
    protected Equipment(IPv4 ip, String hostname, boolean alive) {
        this.ip = ip;
        this.hostname = hostname;
        this.alive = alive;
    }
    // </editor-fold>

    
    
    // <editor-fold defaultstate="collapsed" desc="METHODES PUBLICS">
    /**
     * Renvoie l'adresse IP de l'équipement
     * @return Retourne l'adresse IP de l'équipement
     */
    public IPv4 getIp() {
        return ip;
    }

    /**
     * Renvoie si oui ou non l'équipement existe
     * @return Retourne si oui ou non l'équipement existe
     */
    public boolean isAlive() {
        return alive;
    }

    /**
     * Renvoie le nom de l'équipement
     * @return Retourne le nom de l'équipement
     */
    public String getHostname() {
        return hostname;
    }

    /**
     * Modifie l'adresse IP de l'équipement
     * @param ip Correspond à la nouvelle adresse IP
     */
    public void setIp(IPv4 ip) {
        this.ip = ip;
    }

    /**
     * Modifie si oui ou non l'équipement existe
     * @param alive Correspond à true si l'équipement existe
     */
    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    /**
     * Modifie le nom de l'équipement
     * @param hostname Correspond au nom de l'équipement
     */
    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    /**
     * Renvoie un objet clone de celui-ci
     * @return Retourne un objet clone de celui-ci
     * @throws java.lang.CloneNotSupportedException Correspond à l'exception levée si le clonnage se déroule mal
     */
    @Override
    @SuppressWarnings("CloneDoesntCallSuperClone")
    public Object clone() throws CloneNotSupportedException {
        return new Equipment((IPv4) ip.clone(), hostname, alive);
    }
    
    /**
     * Renvoie le résultat de la méthode compareTo()
     * @param e Correspond à l'objet à comparer
     * @return Retourne le résultat de la méthode compareTo()
     */
    @Override
    public int compareTo(Equipment e) {
        int code = hostname.compareTo(e.hostname);
        if(code == 0){
            if(alive && !e.alive) return -1;
            else if(!alive && e.alive) return 1;
            else return 0;
        }else return code;
    }

    /**
     * Renvoie l'objet sous la forme d'une chaîne de caractères
     * @return Retourne l'objet sous la forme d'une chaîne de caractères
     */
    @Override
    public String toString() {
        return "Equipment{" + "ip=" + ip + ", hostname=" + hostname + ", alive=" + alive + '}';
    }

    /**
     * Renvoie le hashCode de l'équipement
     * @return Retourne le hashCode de l'équipement
     */
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 47 * hash + java.util.Objects.hashCode(this.ip);
        hash = 47 * hash + java.util.Objects.hashCode(this.hostname);
        return hash;
    }

    /**
     * Détermine si deux équipements sont identiques
     * @param obj Correspond au second équipement à comparer au courant
     * @return Retourne true, s'ils sont identiques, sinon false
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Equipment other = (Equipment) obj;
        if (!java.util.Objects.equals(this.hostname, other.hostname)) {
            return false;
        }
        return java.util.Objects.equals(this.ip, other.ip);
    }
    // </editor-fold>
    
    
    
}