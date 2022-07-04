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
 * Cette classe permet de connaitre les adresses utilisables d'un réseau ainsi que leur nombre...
 * @author Briguet
 * @version 1.0
 */
public class PoolIPv4 implements java.io.Serializable, Comparable<PoolIPv4>, Cloneable, Iterable<IPv4> {
    
    
    
    // <editor-fold defaultstate="collapsed" desc="SERIAL_VERSION_UID">
    /**
     * Correspond au numéro de série qui identifie le type de dé/sérialization utilisé pour l'objet
     */
    private static final long serialVersionUID = 1L;
    // </editor-fold>
    
    
    
    // <editor-fold defaultstate="collapsed" desc="ATTRIBUTS">
    /**
     * Correspond à l'adresse IP du réseau
     */
    IPv4 ipn;
    /**
     * Correspond au masque du réseau
     */
    MaskIPv4 ipm;
    /**
     * Correspond à l'adresse ip du réseau sous la forme d'un entier
     */
    int ipNetwork;
    // </editor-fold>

    
    
    // <editor-fold defaultstate="collapsed" desc="CONSTRUCTORS">
    /**
     * Crée par défaut un pool d'adresse
     */
    private PoolIPv4() {
    }
    
    /**
     * Crée un pool d'adresse
     * @param ipNetwork Correspond à l'adresse IP du réseau
     * @param mask Correspond au masque du réseau
     */
    protected PoolIPv4(IPv4 ipNetwork, MaskIPv4 mask) {
        this.ipn = ipNetwork;
        this.ipm = mask;
        this.ipNetwork = ipNetwork.toInt();
    }
    // </editor-fold>
    
    
    
    // <editor-fold defaultstate="collapsed" desc="METHODES PUBLICS">
    /**
     * Renvoie le nombre d'adresses utilisables dans le réseau (sans l'adresse IP du réseau, ni l'adresse IP de broadcast)
     * @return Retourne le nombre d'adresses utilisables
     */
    public long count(){
        return this.ipm.countHosts();
    }
    
    /**
     * Renvoie un itérateur sur les éléments de ce pool dans un ordre approprié
     * @return Retourne un itérateur sur les éléments de ce pool dans le bon ordre
     */
    @Override
    public java.util.Iterator<IPv4> iterator() {
        return new PoolIPv4Iterator(this);
    }

    /**
     * Renvoie le hashCode du pool d'ips
     * @return Retourne le hashCode du pool d'ips
     */
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + java.util.Objects.hashCode(this.ipn);
        hash = 89 * hash + java.util.Objects.hashCode(this.ipm);
        return hash;
    }

    /**
     * Renvoie si deux pools d'ips sont identiques
     * @param obj Correspond au second pool d'ips à comparer au courant
     * @return Retourne true s'ils sont identiques, sinon false
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
        final PoolIPv4 other = (PoolIPv4) obj;
        if (!java.util.Objects.equals(this.ipn, other.ipn)) {
            return false;
        }
        return java.util.Objects.equals(this.ipm, other.ipm);
    }

    /**
     * Renvoie le pool sous la forme d'une chaîne de caractères
     * @return Retourne le pool sous la forme d'une chaîne de caractères
     */
    @Override
    public String toString() {
        return "PoolIPv4{" + "IP Network=" + ipn + ", Mask Network=" + ipm + '}';
    }

    /**
     * Renvoie un objet clone de celui-ci
     * @return Retourne un objet clone de celui-ci
     * @throws java.lang.CloneNotSupportedException Correspond à l'exception levée si le clonnage se déroule mal
     */
    @Override
    @SuppressWarnings("CloneDoesntCallSuperClone")
    public Object clone() throws CloneNotSupportedException {
        return new PoolIPv4((IPv4)ipn.clone(), (MaskIPv4)ipm.clone());
    }
    
    /**
     * Renvoie le résultat de la méthode compareTo()
     * @param pool Correspond à l'objet à comparer
     * @return Retourne le résultat de la méthode compareTo()
     */
    @Override
    public int compareTo(PoolIPv4 pool) {
        int code = ipn.compareTo(pool.ipn);
        if(code == 0)
            return ipm.compareTo(pool.ipm);
        else
            return code;
    }
    // </editor-fold>
    
    
    
    // <editor-fold defaultstate="collapsed" desc="METHODE PRIVATE">
    /**
     * Renvoie la prochaine ou la précédante adresse IPv4 du pool d'adresse du réseau
     * @return Retourne un objet IPv4
     */
    @SuppressWarnings("ManualArrayToCollectionCopy")
    IPv4 getIPv4Iterator(int position){
        long valueMaxMask = ipm.countHosts();
        if(position<=valueMaxMask){
            return new IPv4(ipNetwork + position);
        }else{
            return null;
        }
    }
    // </editor-fold>
    
    
    
}



/**
 * Cette classe représente les itérators qui peuvent parcourir un Pool d'IPv4
 * @see PoolIPv4
 * @see IPv4
 * @author Briguet
 * @version 1.0
 * @param <ip> Correspond au type IPv4
 */
class PoolIPv4Iterator implements java.util.Iterator {

    
    
//ATTRIBUTS
    /**
     * Correspond à la position de l'itérator dans le Pool IPv4
     */
    private int position;
    
    /**
     * Correspond au PoolIPv4 à parcourir
     */
    private final PoolIPv4 array;

    
    
//CONSTRUCTOR
    /**
     * Crée un objet ExpandableArrayIterator
     * @param array Correspond au tableau extensible qui sera parcouru
     */
    protected PoolIPv4Iterator(PoolIPv4 array) {
        this.array = array;
        position = 0;
    }
    
    
    
//METHODES PUBLICS
    /**
     * Détermine si le suivant élément existe ou pas
     * @return Retourne true, s'il existe sinon false
     */
    @Override
    public synchronized boolean hasNext() {
        return position < array.count();
    }
    
    /**
     * Renvoie le suivant élément
     * @return Retourne le suivant élément
     */
    @Override
    public synchronized Object next() {
        long valueMaxMask = array.ipm.countHosts();
        Object o = null;
        position++;
        if(position<=valueMaxMask){
            o = new IPv4(array.ipNetwork + position);
        }
        return o;
    }
    
    /**
     * Supprime de la collection sous-jacente le dernier élément renvoyé par cet itérateur
     */
    @Override
    public synchronized void remove() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        /*if(position - 1 > -1)
            array.remove(position - 1);*/
    }
    
    
    
}