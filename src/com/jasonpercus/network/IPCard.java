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
 * Cette classe représente une adresse IP avec son masque et sa passerelle
 * @author Briguet
 * @version 1.0
 */
public class IPCard implements java.io.Serializable, Comparable<IPCard>, Cloneable {
    
    
    
    // <editor-fold defaultstate="collapsed" desc="SERIAL_VERSION_UID">
    /**
     * Correspond au numéro de série qui identifie le type de dé/sérialization utilisé pour l'objet
     */
    private static final long serialVersionUID = 1L;
    // </editor-fold>

    
    
    // <editor-fold defaultstate="collapsed" desc="ATTRIBUT">
    /**
     * Correspond à une adresse ip d'une carte réseau
     */
    private IPv4        ip;
    /**
     * Correspond au masque de l'adresse ip
     */
    private MaskIPv4    mask;
    /**
     * Correspond à l'adresse broadcast de l'adresse ip
     */
    private BroadcastIPv4 broadcast;
    // </editor-fold>

    
    
    // <editor-fold defaultstate="collapsed" desc="CONSTRUCTORS">
    /**
     * Crée un objet IPCard par défaut
     */
    public IPCard() {
        this.ip = new IPv4();
        this.mask = new MaskIPv4();
    }
    
    /**
     * Crée un objet IPCard
     * @param ip Correspond à une adresse IP
     * @param mask Correspond au masque de l'adresse IP
     */
    public IPCard(IPv4 ip, MaskIPv4 mask) {
        this.ip = ip;
        this.mask = mask;
    }

    /**
     * Crée un objet IPCard
     * @param ip Correspond à une adresse IP
     * @param mask Correspond au masque de l'adresse IP
     * @param broadcast Correspond à l'adresse broadcast de l'adresse IP
     */
    public IPCard(IPv4 ip, MaskIPv4 mask, BroadcastIPv4 broadcast) {
        this.ip = ip;
        this.mask = mask;
        this.broadcast = broadcast;
    }
    // </editor-fold>

    
    
    // <editor-fold defaultstate="collapsed" desc="METHODES PUBLICS">
    /**
     * Renvoie l'adresse IP
     * @return Retourne l'adresse IP
     */
    public IPv4 getIp() {
        return ip;
    }

    /**
     * Modifie l'adresse IP
     * @param ip Correspond à la nouvelle adresse IP
     */
    public void setIp(IPv4 ip) {
        this.ip = ip;
    }

    /**
     * Renvoie le masque de l'adresse IP
     * @return Retourne le masque de l'adresse IP
     */
    public MaskIPv4 getMask() {
        return mask;
    }

    /**
     * Modifie le masque de l'adresse IP
     * @param mask Correspond au nouveau masque de l'adresse IP
     */
    public void setMask(MaskIPv4 mask) {
        this.mask = mask;
    }

    /**
     * Renvoie l'adresse broadcast de l'adresse IP
     * @return Retourne l'adresse broadcast de l'adresse IP
     */
    public BroadcastIPv4 getBroadcast() {
        return broadcast;
    }

    /**
     * Modifie l'adresse broadcast de l'adresse IP
     * @param broadcast Correspond à la nouvelle adresse broadcast de l'adresse IP
     */
    public void setBroadcast(BroadcastIPv4 broadcast) {
        this.broadcast = broadcast;
    }

    /**
     * Clone cette IPCard
     * @return Retourne le clone de l'adresse IP
     * @throws CloneNotSupportedException Correspond à l'adresse levée s'il y a une erreur de clonage
     */
    @Override
    @SuppressWarnings("CloneDoesntCallSuperClone")
    public Object clone() throws CloneNotSupportedException {
        return new IPCard((IPv4)ip.clone(), (MaskIPv4)mask.clone(), (BroadcastIPv4)broadcast.clone());
    }
    
    /**
     * Renvoie le résultat de la méthode hashCode()
     * @return Retourne le résultat de la méthode hashCode()
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 73 * hash + java.util.Objects.hashCode(this.ip);
        hash = 73 * hash + java.util.Objects.hashCode(this.mask);
        hash = 73 * hash + java.util.Objects.hashCode(this.broadcast);
        return hash;
    }

    /**
     * Renvoie le résultat de la méthode equals()
     * @param obj Correspond à l'objet à comparer
     * @return Retourne le résultat de la méthode equals()
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
        final IPCard other = (IPCard) obj;
        if (!java.util.Objects.equals(this.ip, other.ip)) {
            return false;
        }
        if (!java.util.Objects.equals(this.mask, other.mask)) {
            return false;
        }
        if(this.broadcast == null && other.broadcast == null) return true;
        else{
            if(this.broadcast == null && other.broadcast != null || this.broadcast != null && other.broadcast == null) return false;
            else return java.util.Objects.equals(this.broadcast, other.broadcast);
        }
    }

    /**
     * Renvoie sous forme de chaîne de caractère l'objet IPCard
     * @return Retourne sous forme de chaîne de caractère l'objet IPCard
     */
    @Override
    public String toString() {
        return "IPCard(ip: " + ip + ", mask: " + mask + ", broadcast: " + broadcast + ")";
    }
    
    /**
     * Renvoie le résultat de la méthode compareTo()
     * @param ipc Correspond à l'objet à comparer
     * @return Retourne le résultat de la méthode compareTo()
     */
    @Override
    public int compareTo(IPCard ipc) {
        return this.ip.toString().compareTo(((IPCard)ipc).ip.toString());
    }
    // </editor-fold>
    
    
    
}