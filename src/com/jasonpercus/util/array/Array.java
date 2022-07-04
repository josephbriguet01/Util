/*
 * Copyright (C) JasonPercus Systems, Inc - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by JasonPercus, 05/2021
 */
package com.jasonpercus.util.array;



import com.jasonpercus.util.Strings;



/**
 * Cette classe abstraite représente un tableau
 * @author JasonPercus
 * @version 1.0
 */
public abstract class Array implements java.io.Serializable, Cloneable {
    
    
    
    // <editor-fold defaultstate="collapsed" desc="SERIAL_VERSION_UID">
    /**
     * Correspond au numéro de série qui identifie le type de dé/sérialization utilisé pour l'objet
     */
    private static final long serialVersionUID = 1L;
    // </editor-fold>
    
    
    
//CONSTANTE
    /**
     * Correspond à la taille par défaut des blocs du tableau
     */
    private final static int DEFAULT_SIZE_SUBARRAY = 8;
    
    
    
//ATTRIBUTS
    /**
     * Correspond à la liste des sous tableaux
     */
    protected final java.util.List listArrays;
    
    /**
     * Correspond à l'id du tableau
     */
    protected String id;
    
    /**
     * Correspond à la taille des blocs du tableau
     */
    protected int sizeSubArray = 8;
    
    /**
     * Correspond à la taille du tableau
     */
    protected int size;
    
    /**
     * Correspond à la taille théorique du tableau
     */
    protected int maxSizeTheory;
    
    /**
     * Correspond au nombre de sous-tableaus compris dans le tableau
     */
    protected int countArray;

    
    
//CONSTRUCTOR
    /**
     * Crée un tableau
     */
    public Array() {
        this.id             = Strings.generate(30);
        this.listArrays     = new java.util.ArrayList<>();
        this.size           = 0;
        this.maxSizeTheory  = 0;
        this.countArray     = 0;
        this.sizeSubArray   = DEFAULT_SIZE_SUBARRAY;
    }

    
    
//METHODES PUBLICS
    /**
     * Renvoie la taille des séquences. Un Array est composé de plusieurs tableau de taille fixe (Object[]) appelé aussi dans ce projet Séquence. Ainsi plusieurs séquences forment le tableau extensible
     * @return Retourne la taille des séquences
     */
    public synchronized int getSizeSequences(){
        return this.sizeSubArray;
    }
    
    /**
     * Modifie la taille des séquences. Un Array est composé de plusieurs tableau de taille fixe (Object[]) appelé aussi dans ce projet Séquence. Ainsi plusieurs séquences forment le tableau extensible. Pour pouvoir modifier la taille des séquences, il faut avoir un tableau vide. Une fois qu'il existe un élément dans le tableau, il n'est plus possible de changer la taille des séquences
     * @see #clear() Permet de vider le tableau dans son intégralité ce qui permet par la suite de redéfinir une nouvelle taille de séquence
     * @param size Correspond à la nouvelle taille des séquences
     * @return Retourne true si la valeur a bien changé, sinon false
     * @throws InstantiationException Si la taille est inférieure à 0 ou si le tableau contient déjà un élément
     */
    public synchronized boolean setSizeSequences(int size) throws InstantiationException {
        int oldSize = getSizeSequences();
        if(size < 2) {
            throw new IllegalArgumentException("Your size too small ! The size must be at least 2.");
        } else if(this.size>0) {
            throw new InstantiationException("Cannot change the size of sequences if the array contains an element ! This operation requires having an empty array.");
        } else {
            clear();
            this.sizeSubArray = size;
            return oldSize != size;
        }
    }
    
    /**
     * Efface un tableau. Attention la taille de ce tableau repasse à 0;
     */
    public final synchronized void clear() {
        this.listArrays.clear();
        this.countArray     = 0;
        this.size           = 0;
        this.maxSizeTheory  = 0;
    }

    /**
     * Renvoie la taille du tableau extensible
     * @return Retourne la taille
     */
    public final synchronized int length() {
        return size;
    }
    
    /**
     * Renvoie si oui ou non le tableau extensible est vide ou pas
     * @return Retourne true s'il l'est sinon false
     */
    public final synchronized boolean isEmpty(){
        return size <= 0;
    }
    
    /**
     * Renvoie le hashCode du tableau
     * @return Retourne le hashCode du tableau
     */
    @Override
    public final int hashCode() {
        int hash = 7;
        hash = 23 * hash + java.util.Objects.hashCode(this.id);
        return hash;
    }

    /**
     * Détermine si deux tableaux ont la même adresse en mémoire (Attention on ne compare pas leur contenu)
     * @param obj Correspond au second tableau à comparer au courant
     * @return Retourne true, s'il sont identiques, sinon false
     */
    @Override
    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Array other = (Array) obj;
        return java.util.Objects.equals(this.id, other.id);
    }

    /**
     * Clone ce tableau
     * @return Retourne une copie de ce tableau
     * @throws CloneNotSupportedException S'il y a une erreur de clonage
     */
    @Override
    protected final Object clone() throws CloneNotSupportedException {
        Array array = (Array) super.clone();
        array.id = Strings.generate(30);
        return array;
    }
    
    
    
}