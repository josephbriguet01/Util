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
 * Cette classe représente un nombre de millisecondes
 * @see TimeChangeListener
 * @author JasonPercus
 * @version 1.0
 */
public class Millisecond extends Number implements java.io.Serializable, Comparable<Millisecond>, Cloneable {
    
    
    
    // <editor-fold defaultstate="collapsed" desc="SERIAL_VERSION_UID">
    /**
     * Correspond au numéro de série qui identifie le type de dé/sérialization utilisé pour l'objet
     */
    private static final long serialVersionUID = 1L;
    // </editor-fold>
    
    
    
//ATTRIBUTS
    /**
     * Correspond au nombre de millisecondes
     */
    private long milliseconds;
    
    /**
     * Détermine si les nombres négatifs sont autorisés
     */
    private boolean authorizationNegativeNumber;
    
    /**
     * Correspond au listener qui écouteront les changements de valeur sur l'objet
     */
    private transient java.util.List<TimeChangeListener> changeListeners;

    
    
//CONSTRUCTORS
    /**
     * Crée un objet 0 Milliseconde (par défaut les nombres négatifs sont autorisés)
     */
    public Millisecond() {
        this(0l, true);
    }
    
    /**
     * Crée un objet n millisecondes (ce nombre n est calculé en fonction de la date fournie)
     * @param date Correspond à la date dont on cherche à récupérer le nombre de millisecondes
     */
    public Millisecond(java.util.Date date){
        this(date.getTime(), false);
    }

    /**
     * Crée un objet n millisecondes (par défaut les nombres négatifs sont autorisés)
     * @param milliseconds Correspond au n millisecondes
     */
    public Millisecond(long milliseconds) {
        this(milliseconds, true);
    }

    /**
     * Crée un objet n millisecondes
     * @param milliseconds Correspond au n millisecondes
     * @param authorizationNegativeNumber Détermine si l'objet doit accepter les nombres négatifs ou pas (si non, à chaque fois qu'un nombre négatif sera donné, il sera ramené à 0)
     */
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public Millisecond(long milliseconds, boolean authorizationNegativeNumber) {
        this.changeListeners = new java.util.ArrayList<>();
        this.authorizationNegativeNumber = authorizationNegativeNumber;
        this.setMilliseconds(milliseconds);
    }
    
    

//GETTERS & SETTERS
    /**
     * Renvoie le nombre de millisecondes
     * @return Retourne le nombre de millisecondes
     */
    public synchronized long getMilliseconds() {
        return milliseconds;
    }

    /**
     * Modifie le nombre de millisecondes (pensez à la valeur du paramètre authorizationNegativeNumber)
     * @param milliseconds Correspond au nombre de millisecondes
     */
    public synchronized void setMilliseconds(long milliseconds) {
        long oldMilliseconds = this.milliseconds;
        
        if(milliseconds < 0 && !this.authorizationNegativeNumber)
            this.milliseconds = 0;
        else
            this.milliseconds = milliseconds;
        
        if(this.milliseconds != oldMilliseconds)
            sendEvt();
    }

    /**
     * Renvoie si oui ou non les valeurs négatives sont acceptées
     * @return Retourne true (par défaut dans le constructeur) si elles le sont, sinon false
     */
    public synchronized boolean isAuthorizationNegativeNumber() {
        return authorizationNegativeNumber;
    }

    /**
     * Détermine si oui ou non les valeurs négatives doivent-être acceptées
     * @param authorizationNegativeNumber True si elles doivent être acceptées (par défaut dans le constructeur), false sinon
     */
    public synchronized void setAuthorizationNegativeNumber(boolean authorizationNegativeNumber) {
        boolean oldAuthorizationNegativeNumber = this.authorizationNegativeNumber;
        
        this.authorizationNegativeNumber = authorizationNegativeNumber;
        if(!authorizationNegativeNumber && milliseconds<0)
            this.milliseconds = 0;
        
        if(this.authorizationNegativeNumber != oldAuthorizationNegativeNumber)
            sendEvt();
    }

    /**
     * Renvoie la liste des listeners
     * @return Retourne la liste des listeners
     */
    public synchronized java.util.List<TimeChangeListener> getChangeListeners() {
        return changeListeners;
    }

    /**
     * Modifie la liste des listeners
     * @param changeListeners Correspond à la nouvelle liste des listeners
     */
    public synchronized void setChangeListeners(java.util.List<TimeChangeListener> changeListeners) {
        this.changeListeners = changeListeners;
    }
    
    
    
    
//METHODES PUBLICS
    /**
     * Ajoute n millisecondes
     * @param milliseconds Correspond au n millisecondes à ajouter
     */
    public synchronized void addMilliseconds(long milliseconds){
        setMilliseconds(getMilliseconds() + milliseconds);
    }
    
    /**
     * Supprime n millisecondes (pensez à la valeur du paramètre authorizationNegativeNumber)
     * @param milliseconds Correspond au n millisecondes à supprimer
     */
    public synchronized void removeMilliseconds(long milliseconds){
        setMilliseconds(getMilliseconds() - milliseconds);
    }
    
    /**
     * Vide le nombre de millisecondes à 0
     */
    public synchronized void drain(){
        setMilliseconds(0l);
    }
    
    /**
     * Endors le thread pendant les n millisecondes de cet objet
     * @throws InterruptedException S'il y a une interruption pendant que le thread courant est endormi
     */
    public void sleep() throws InterruptedException {
        if(this.milliseconds>0)
            Thread.sleep(this.milliseconds);
    }
    
    /**
     * Ajoute un listener
     * @param listener Correspond au nouveau listener à ajouter
     */
    @SuppressWarnings({"SynchronizeOnNonFinalField", "NestedSynchronizedStatement"})
    public synchronized void addChangeListener(TimeChangeListener listener){
        if(listener != null){
            synchronized(changeListeners){
                if(!changeListeners.contains(listener)){
                    changeListeners.add(listener);
                }
            }
        }
    }
    
    /**
     * Supprime un listener
     * @param listener Correspond au listener à supprimer
     */
    @SuppressWarnings({"SynchronizeOnNonFinalField", "NestedSynchronizedStatement"})
    public synchronized void removeChangeListener(TimeChangeListener listener){
        if(listener != null){
            synchronized(changeListeners){
                if(changeListeners.contains(listener)){
                    changeListeners.remove(listener);
                }
            }
        }
    }
    
    /**
     * Supprime tous les listeners
     */
    @SuppressWarnings({"SynchronizeOnNonFinalField", "NestedSynchronizedStatement"})
    public synchronized void removeAllChangeListener(){
        synchronized (changeListeners) {
            changeListeners.clear();
        }
    }

    /**
     * Renvoie le nombre de millisecondes sous la forme d'un entier
     * @return Retourne le nombre de millisecondes sous la forme d'un entier
     */
    @Override
    public int intValue() {
        return (int) milliseconds;
    }

    /**
     * Renvoie le nombre de millisecondes sous la forme d'un long
     * @return Retourne le nombre de millisecondes sous la forme d'un long
     */
    @Override
    public long longValue() {
        return milliseconds;
    }

    /**
     * Renvoie le nombre de millisecondes sous la forme d'un float
     * @return Retourne le nombre de millisecondes sous la forme d'un float
     */
    @Override
    public float floatValue() {
        return milliseconds;
    }

    /**
     * Renvoie le nombre de millisecondes sous la forme d'un double
     * @return Retourne le nombre de millisecondes sous la forme d'un double
     */
    @Override
    public double doubleValue() {
        return milliseconds;
    }

    
    
//OVERRIDED
    /**
     * Renvoie le hashCode de l'objet Millisecond
     * @return Retourne le hashCode de l'objet Millisecond
     */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + (int) (this.milliseconds ^ (this.milliseconds >>> 32));
        return hash;
    }

    /**
     * Détermine si deux objets Millisecond sont identiques
     * @param obj Correspond au second objet à comparer au courant
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
        final Millisecond other = (Millisecond) obj;
        return this.milliseconds == other.milliseconds;
    }

    /**
     * Renvoie le nombre de millisecondes sous la forme d'une chaîne de caractères
     * @return Retourne le nombre de millisecondes sous la forme d'une chaîne de caractères
     */
    @Override
    public String toString() {
        return ""+milliseconds;
    }

    /**
     * Compare deux objets Millisecond
     * @param o Correspond au second objet Millisecond à comparer au courant
     * @return Retourne le résultat de la comparaison
     */
    @Override
    public int compareTo(Millisecond o) {
        if(milliseconds < o.milliseconds) return -1;
        if(milliseconds > o.milliseconds) return 1;
        return 0;
    }

    /**
     * Clone l'objet Millisecond
     * @return Retourne un clone de Millisecond
     * @throws CloneNotSupportedException Si le clone n'est pas supporté
     */
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
    
    
    
//METHODE PRIVATE
    /**
     * Envoie un évènement de changement
     */
    @SuppressWarnings({"SynchronizeOnNonFinalField", "NestedSynchronizedStatement"})
    private synchronized void sendEvt(){
        synchronized(changeListeners){
            if(changeListeners != null){
                for(TimeChangeListener listener : changeListeners){
                    if(listener != null){
                        listener.changed(this);
                    }
                }
            }
        }
    }
    
    
    
}