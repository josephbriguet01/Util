/*
 * Copyright (C) JasonPercus Systems, Inc - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by JasonPercus, 09/2022
 */
package com.jasonpercus.util.array;



/**
 * Cette classe permet de créer une file (de taille fixe) de données. La donnée la plus ancienne est consommé dès qu'une nouvelle donnée essaye de rentrer et agrandir la taille de l'{@linkplain ArrayQueue}
 * @author JasonPercus
 * @param <O> Correspond au type d'objet contenu dans l'{@linkplain ArrayQueue}
 * @version 1.0
 */
public class ArrayQueue<O> implements Iterable<O> {

    
    
//ATTRIBUTS
    /**
     * Correspond à la file de données
     */
    protected final O[] buffer;
    
    /**
     * Correspond à la position du curseur sur le premier élément des données (car la file étant de taille fixe les données sont ajouté de manière circulaire, donc le premier élément change à chaque ajout
     */
    private int index;
    
    /**
     * Correspond à au nombre de donnée dans la file de données
     */
    private int size;
    
    /**
     * Correspond au numéro de la prochaine donnée à ajouter dans la file
     */
    private int cpt;

    
    
//CONSTRUCTOR
    /**
     * Crée un objet {@link ArrayQueue}
     * @param size Correspond à la taille de l'objet
     */
    public ArrayQueue(int size) {
        if(size <= 0)
            throw new NegativeArraySizeException("size must be > 0 !");
        this.buffer = (O[]) new Object[size];
        this.index  = 0;
        this.size   = 0;
    }
    
    
    
//METHODES PUBLICS
    /**
     * Détermine si l'{@link ArrayQueue} est pleine
     * @return Retourne true si elle l'est, sinon false
     */
    public final boolean isFull(){
        return length() == buffer.length;
    }
    
    /**
     * Détermine si l'{@link ArrayQueue} est vide
     * @return Retourne true si elle l'est, sinon false
     */
    public final boolean isEmpty(){
        return length() == 0;
    }
    
    /**
     * Renvoie le nombre de données dans l'{@link ArrayQueue}
     * @return Retourne le nombre de données dans l'{@link ArrayQueue}
     */
    public final int length(){
        return Math.min(size, buffer.length);
    }
    
    /**
     * Renvoie une donnée prise à l'index donné de l'{@link ArrayQueue}
     * @param index Correspond à l'index de l'{@link ArrayQueue}
     * @return Retourne la donnée trouvé à l'index
     */
    public final O get(int index){
        if(index < length())
            return buffer[(this.index + index) % buffer.length];
        else
            throw new IndexOutOfBoundsException(index + "[index] > " + length() + "[length] !");
    }

    /**
     * Ajoute une donnée à la fin de la file.Ce qui a pour effet de détruire la donnée la plus ancienne de manière à permettre le décallage de toutes les données et pouvoir rentrer la nouvelle donnée
     * @param o Correspond à la nouvelle donnée à ajouter
     * @return Retourne la valeur détruite avant le décallage puis l'ajout de la nouvelle valeur
     */
    public O put(O o) {
        O old = buffer[cpt % buffer.length];
        buffer[cpt % buffer.length] = o;
        cpt++;
        size = Math.min(++size, buffer.length);
        if(cpt <= buffer.length)
            index = 0;
        else
            index = cpt % buffer.length;
        return old;
    }

    /**
     * Renvoie l'itérateur de l'{@link ArrayQueue}
     * @return Retourne l'itérateur de l'{@link ArrayQueue}
     */
    @Override
    public final java.util.Iterator<O> iterator() {
        return new ArrayQueueIterator<>(this);
    }

    /**
     * Renvoie une {@link ArrayQueue} sous la forme d'une chaîne de caractères
     * @return Retourne une {@link ArrayQueue} sous la forme d'une chaîne de caractères
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for(int i=0;i<length();i++){
            if(i>0)
                sb.append(", ");
            sb.append(get(i));
        }
        sb.append("]");
        return sb.toString();
    }
    
    
    
}



/**
 * Cette classe représente les itérators qui peuvent parcourir un {@link ArrayQueue}
 * @author JasonPercus
 * @param <O> Correspond au type d'objet contenu dans le {@link ArrayQueue}
 * @version 1.0
 */
class ArrayQueueIterator<O> implements java.util.Iterator<O> {

    
    
//ATTRIBUTS
    /**
     * Correspond à la position de l'itérator dans le {@link ArrayQueue}
     */
    private int position;
    
    /**
     * Correspond au {@link ArrayQueue} à parcourir
     */
    private final ArrayQueue<O> array;

    
    
//CONSTRUCTOR
    /**
     * Crée un objet {@link ArrayQueueIterator}
     * @param array Correspond au {@link ArrayQueue} qui sera parcouru
     */
    protected ArrayQueueIterator(ArrayQueue<O> array) {
        this.array    = array;
        this.position = 0;
    }
    
    
    
//METHODES PUBLICS
    /**
     * Détermine si le suivant élément existe ou pas
     * @return Retourne true, s'il existe sinon false
     */
    @Override
    public synchronized boolean hasNext() {
        return position < array.length();
    }
    
    /**
     * Renvoie le suivant élément
     * @return Retourne le suivant élément
     */
    @Override
    public synchronized O next() {
        O e = array.get(position);
        position++;
        return e;
    }
    
    
    
}