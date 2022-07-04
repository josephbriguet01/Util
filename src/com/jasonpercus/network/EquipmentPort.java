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
 * Cette classe permet de représenter un port d'un équipement réseau
 * @author Briguet
 * @version 1.0
 */
public class EquipmentPort extends Number implements Comparable<EquipmentPort>, Cloneable {
    
    
    
    // <editor-fold defaultstate="collapsed" desc="SERIAL_VERSION_UID">
    /**
     * Correspond au numéro de série qui identifie le type de dé/sérialization utilisé pour l'objet
     */
    private static final long serialVersionUID = 1L;
    // </editor-fold>

    
    
    // <editor-fold defaultstate="collapsed" desc="ATTRIBUTS">
    /**
     * Ce type représente le protocol TCP
     */
    public static int TYPE_TCP = 6;
    /**
     * Ce type représente le protocol UDP
     */
    public static int TYPE_UDP = 17;
    /**
     * Ce type représente le protocol TCP et UDP
     */
    public static int TYPE_TCP_UDP = 23;
    /**
     * Correspond au type de protocol qui vise le port
     */
    private int type_protocol;
    /**
     * Correspond au port d'un équipement réseau
     */
    private int port;
    /**
     * Détermine si oui ou non celui-ci est ouvert ou pas
     */
    private boolean alive;
    /**
     * Correspond au nom du port
     */
    private String name;
    // </editor-fold>

    
    
    // <editor-fold defaultstate="collapsed" desc="CONSTRUTORS">
    /**
     * Crée un objet EquipmentPort par défaut
     */
    private EquipmentPort() {
        
    }
    
    /**
     * Crée un objet EquipmentPort
     * @param port Correspond au port d'un équipement réseau
     * @param alive Détermine si oui ou non le port est ouvert
     * @param name Correspond au nom du port
     * @param type_protocol Correspond au type de protocol associé au port
     */
    protected EquipmentPort(int port, boolean alive, String name, int type_protocol) {
        this.port = port;
        this.alive = alive;
        this.name = name;
        this.type_protocol = type_protocol;
    }
    // </editor-fold>

    
    
    // <editor-fold defaultstate="collapsed" desc="METHODES PUBLICS">
    /**
     * Renvoie le numéro du port
     * @return Retourne le numéro du port
     */
    public int getPort() {
        return port;
    }

    /**
     * Modifie le numéro de port
     * @param port Correspond au nouveau numéro de port
     */
    public void setPort(int port) {
        this.port = port;
    }

    /**
     * Renvoie si oui ou non le port est ouvert
     * @return Retourne true s'il est ouvert, sinon false
     */
    public boolean isAlive() {
        return alive;
    }

    /**
     * Modifie si oui ou non le port est ouvert
     * @param alive Détermine si oui ou non le port est ouvert
     */
    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    /**
     * Renvoie le nom du port
     * @return Retourne le nom du port
     */
    public String getName() {
        return name;
    }

    /**
     * Modifie le nom du port
     * @param name Correspond au nouveau nom du port
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Renvoie le type de protocol
     * @return Retourne le type de protocol
     */
    public int getType_protocol() {
        return type_protocol;
    }

    /**
     * Modifie le type de protocol
     * @param type_protocol Correspond au nouveau type de protocol
     */
    public void setType_protocol(int type_protocol) {
        this.type_protocol = type_protocol;
    }
    
    /**
     * Renvoie le port sous la forme d'un entier
     * @return Retourne le port
     */
    @Override
    public int intValue() {
        return port;
    }

    /**
     * Renvoie le port sous la forme d'un long
     * @return Retourne le port
     */
    @Override
    public long longValue() {
        return port;
    }

    /**
     * Renvoie le port sous la forme d'un float
     * @return Retourne le port
     */
    @Override
    public float floatValue() {
        return port;
    }

    /**
     * Renvoie le port sous la forme d'un double
     * @return Retourne le port
     */
    @Override
    public double doubleValue() {
        return port;
    }
    
    /**
     * Renvoie un objet clone de celui-ci
     * @return Retourne un objet clone de celui-ci
     * @throws java.lang.CloneNotSupportedException Correspond à l'exception levée si le clonnage se déroule mal
     */
    @Override
    @SuppressWarnings("CloneDoesntCallSuperClone")
    public Object clone() throws CloneNotSupportedException {
        return new EquipmentPort(port, alive, name, type_protocol);
    }

    /**
     * Renvoie l'objet sous la forme d'une chaîne de caractères
     * @return Retourne l'objet sous la forme d'une chaîne de caractères
     */
    @Override
    public String toString() {
        String protocol = "TCP/UDP";
        if(type_protocol == EquipmentPort.TYPE_TCP)
            protocol = "TCP";
        else if(type_protocol == EquipmentPort.TYPE_UDP)
            protocol = "UDP";
        
        
        return "Port "+protocol+" (" + port + ") alive: " + alive;
    }

    /**
     * Renvoie le résultat de la méthode compareTo()
     * @param ep Correspond à l'objet à comparer
     * @return Retourne le résultat de la méthode compareTo()
     */
    @Override
    public int compareTo(EquipmentPort ep) {
        if(port < ep.port) return -1;
        else if(port > ep.port) return 1;
        else{
            if(type_protocol < ep.type_protocol) return -1;
            else if(type_protocol > ep.type_protocol) return 1;
            else{
                if(alive == !ep.alive) return -1;
                else if(!alive == ep.alive) return 1;
                else{
                    return name.compareTo(ep.name);
                }
            }
        }
    }

    /**
     * Renvoie le hashCode du port de l'équipement
     * @return Retourne le hashCode du port de l'équipement
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + this.type_protocol;
        hash = 17 * hash + this.port;
        hash = 17 * hash + java.util.Objects.hashCode(this.name);
        return hash;
    }

    /**
     * Détermine si deux ports d'équipements sont identiques
     * @param obj Correspond au second port à comparer au courant
     * @return Retourne true, sils sont identiques, sinon false
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
        final EquipmentPort other = (EquipmentPort) obj;
        if (this.type_protocol != other.type_protocol) {
            return false;
        }
        if (this.port != other.port) {
            return false;
        }
        return java.util.Objects.equals(this.name, other.name);
    }
    // </editor-fold>
    
    
    
}