/*
 * Copyright (C) JasonPercus Systems, Inc - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by JasonPercus, 08/2021
 */
package com.jasonpercus.util;



/**
 * Cette classe permet de créer un bloqueur de programme, cela permet ainsi d'attendre un évènement précis avant de redébloquer le programme. C'est donc un objet de synchronisation
 * @author JasonPercus
 * @version 1.0
 */
@SuppressWarnings("NestedSynchronizedStatement")
public class Locker implements Comparable<Locker>, Cloneable {
    
    
    
//ATTRIBUTS STATICS
    /**
     * Correspond au compteur d'id permetant ainsi de générer des id uniques
     */
    private static int cpt = 0;
    
    /**
     * Correspond à la collection faisant le lien entre des Lockers et des id
     */
    private final static java.util.HashMap<String, Locker> MAP = new java.util.HashMap<>();
    
    
    
//ATTRIBUTS
    /**
     * Correspond à l'id de l'objet
     */
    private int id;
    
    /**
     * Correspond à l'objet qui va servir de bloqueur de programme
     */
    private final Object locker;
    
    /**
     * Détermine si l'objet est dans l'état bloqué ou pas
     */
    private boolean locked;

    
    
//CONSTRUCTOR
    /**
     * Crée un objet bloqueur de programme
     */
    public Locker() {
        this.id     = cpt++;
        this.locked = false;
        this.locker = new Object();
    }
    
    
    
//METHODES PUBLICS
    /**
     * Détermine si l'objet est actuellement bloqué
     * @return Retourne true, si c'est le cas, sinon false
     */
    public boolean isLocked(){
        return locked;
    }

    /**
     * Bloque l'exécution du programme jusqu'à ce qu'il y ait un débloquage avec la méthode {@link #unlock()}
     */
    public void lock() {
        synchronized(locker){
            try {
                locked = true;
                locker.wait();
                locked = false;
            } catch (InterruptedException ex) {
                //Logger.getLogger(Locker.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    /**
     * Débloque l'éxécution du programme
     */
    public void unlock(){
        synchronized(locker){
            locker.notify();
        }
    }

    /**
     * Renvoie le hashCode de l'objet
     * @return Retourne le hashCode de l'objet
     */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + this.id;
        return hash;
    }

    /**
     * Détermine si deux objets Locker sont identiques ou pas
     * @param obj Correspond au second objet Locker dont on cherche à vérifier s'il est identique au courant
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
        final Locker other = (Locker) obj;
        return this.id == other.id;
    }

    /**
     * Compare deux objets Locker
     * @param o Correspond au second objet Locker à comparer au courant
     * @return Retourne le résultat de la comparaison
     */
    @Override
    public int compareTo(Locker o) {
        if(o == null) return 1;
        else{
            if(id < o.id) return -1;
            else if(id > o.id) return 1;
            else return 0;
        }
    }

    /**
     * Renvoie l'objet Locker sous la forme d'une chaîne de caractères
     * @return Retourne l'objet Locker sous la forme d'une chaîne de caractères
     */
    @Override
    public String toString() {
        if(locked)
            return "Locker(" + id + "){locked}";
        else
            return "Locker(" + id + "){unlocked}";
    }

    /**
     * Clone un objet Locker (Attention l'objet cloné aura le même id)
     * @return Retourne un clone de l'objet Locker
     * @throws CloneNotSupportedException Si le clone n'est pas supporté
     */
    @Override
    @SuppressWarnings("CloneDoesntCallSuperClone")
    protected Object clone() throws CloneNotSupportedException {
        Locker clone = new Locker();
        clone.id = this.id;
        return clone;
    }
    
    
    
//METHODES PUBLICS STATICS
    /**
     * Détermine si un bloqueur est actuellement bloqué
     * @param id Correspond à l'id du bloqueur dont on cherche à déterminer s'il est bloqué
     * @return Retourne true, si c'est le cas, sinon false
     */
    public static boolean isLocked(String id){
        Locker locker = null;
        synchronized(MAP){
            if(MAP.containsKey(id))
                locker = MAP.get(id);
        }
        if(locker != null)
            return locker.isLocked();
        else
            return false;
    }
    
    /**
     * Crée et bloque l'exécution du programme jusqu'à ce qu'il y ait un débloquage avec la méthode {@link #unlockAndDestroy(java.lang.String) }
     * @param id Correspond à l'id du bloqueur
     * @throws java.lang.IllegalArgumentException Si un bloqueur contient le même id
     */
    public static void createAndLock(String id){
        Locker locker = null;
        synchronized(MAP){
            if(!MAP.containsKey(id))
                MAP.put(id, locker = new Locker());
        }
        if(locker != null)
            locker.lock();
        else
            throw new java.lang.IllegalArgumentException("The id " + id + " already exists !");
    }
    
    /**
     * Débloque l'éxécution du programme pour un id donné
     * @param id Correspond à l'id du bloqueur à débloquer
     * @throws java.lang.IllegalArgumentException Si aucun bloqueur ne contient cet id
     */
    public static void unlockAndDestroy(String id){
        Locker locker = null;
        synchronized(MAP){
            if(MAP.containsKey(id)){
                locker = MAP.get(id);
                MAP.remove(id);
            }
        }
        if(locker != null)
            locker.unlock();
        else
            throw new java.lang.IllegalArgumentException("The id " + id + " not exists !");
    }
    
    
    
}