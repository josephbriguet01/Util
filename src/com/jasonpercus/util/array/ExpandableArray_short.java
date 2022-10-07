/*
 * Copyright (C) JasonPercus Systems, Inc - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by JasonPercus, 05/2021
 */
package com.jasonpercus.util.array;



/**
 * Cette classe réprésente un tableau extensible de short. Au départ sa taille est de 0.
 * @author JasonPercus
 * @version 1.1
 */
public class ExpandableArray_short extends Array implements Iterable<Short>{
    
    
    
    // <editor-fold defaultstate="collapsed" desc="SERIAL_VERSION_UID">
    /**
     * Correspond au numéro de série qui identifie le type de dé/sérialization utilisé pour l'objet
     */
    private static final long serialVersionUID = 1L;
    // </editor-fold>
    
    
    
//CONSTRUCTORS
    /**
     * Crée un tableau extensible de short
     */
    public ExpandableArray_short() {
        super();
    }
    
    /**
     * Crée un tableau extensible de short avec pour valeurs de départ les shorts du tableau array
     * @param array Correspond au tableau qui fourni les valeurs de départ
     */
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public ExpandableArray_short(short... array){
        super();
        if(array != null)
            this.put(array);
    }
    
    /**
     * Crée un tableau extensible de short avec pour valeurs de départ les short du tableau array
     * @param array Correspond au tableau qui fourni les valeurs de départ
     */
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public ExpandableArray_short(ExpandableArray_short array){
        super();
        if(array != null)
            this.put(array);
    }
    
    
    
//METHODES PUBLICS
    /**
     * Renvoie un short à l'index donné
     * @param index Correspond à l'index du short à récupérer
     * @return Retourne un short
     */
    public synchronized short get(int index) {
        if (index < 0 || index >= size) {
            throw new java.lang.ArrayIndexOutOfBoundsException("" + index);
        }
        int div = index / sizeSubArray;
        int mod = index % sizeSubArray;
        short[] array = (short[]) listArrays.get(div);
        return array[mod];
    }

    /**
     * Place un short dans le tableau. Si le tableau est trop petit, celui-ci est étendu automatiquement
     * @param index Correspond à l'index où placer le short
     * @param s Correspond au short à placer
     */
    public synchronized void set(int index, short s) {
        if (index < 0) {
            throw new java.lang.ArrayIndexOutOfBoundsException("" + index);
        }
        if (index >= maxSizeTheory) {
            int diff = index - maxSizeTheory;
            int div = diff / sizeSubArray;
            int mod = diff % sizeSubArray;
            int arrayToAdd = div + 1;

            addA(arrayToAdd);
            addE(lastArray(), mod, s);
            size = ((listArrays.size() - 1) * sizeSubArray) + mod + 1;
        } else {
            int div = index / sizeSubArray;
            int mod = index % sizeSubArray;
            short[] array = (short[]) listArrays.get(div);
            addE(array, mod, s);
            size = Math.max(index+1, size);
        }
    }
    
    /**
     * Place un ou plusieurs short à la suite du dernier short placé
     * @param array Correspond au tableau de shorts à placer à la suite
     */
    public synchronized void put(short... array){
        for(short b : array){
            set(size, b);
        }
    }
    
    /**
     * Ajoute un tableau extensible de shorts à la suite du dernier short placé
     * @param array Correspond au tableau extensible de shorts à placer à la suite
     */
    public synchronized void put(ExpandableArray_short array){
        for(short b : array){
            put(b);
        }
    }
    
    /**
     * Renvoie l'index d'un short dans le tableau
     * @param s Correspond au short à trouver
     * @return Retourne l'index du short trouvé dans le tableau ou -1 si le short n'a pas été trouvé
     */
    public synchronized int indexOf(short s){
        for(int j=0;j<size;j++){
            if(get(j) == s)
                return j;
        }
        return -1;
    }
    
    /**
     * Renvoie le dernier index d'un short dans le tableau
     * @param s Correspond au short à trouver
     * @return Retourne le dernier index du short trouvé dans le tableau ou -1 si le short n'a pas été trouvé
     */
    public synchronized int lastIndexOf(short s){
        for(int j=size-1;j>-1;j--){
            if(get(j) == s)
                return j;
        }
        return -1;
    }
    
    /**
     * Renvoie si oui ou non un short existe dans le tableau
     * @param s Correspond au short à trouver
     * @return Retourne true si le short existe, sinon false
     */
    public synchronized boolean contains(short s){
        return indexOf(s) > -1;
    }
    
    /**
     * Efface une cellule du tableau. Le tableau conserve sa taille actuelle, mais la valeur de la cellule passe à 0
     * @param index Correspond à la position de la cellule à effacer
     */
    public synchronized void remove(int index){
        set(index, (short)0);
    }
    
    /**
     * Efface le tableau. Le tableau conserve sa taille actuelle, mais toute les cellules du tableau passent à 0
     */
    public synchronized void removeAll(){
        for(int i=0;i<size;i++){
            set(i, (short)0);
        }
    }
    
    /**
     * Efface toutes les cellules du tableau contenant le short en paramètre. Le tableau conserve sa taille actuelle, mais la valeur des cellules passent à 0
     * @param s Correspond au short à supprimer
     */
    public synchronized void removeAll(short s){
        int index = indexOf(s);
        while (index > -1)
            remove(index);
    }
    
    /**
     * Transforme ce tableau extensible en tableau de taille fixe
     * @return Retourne un tableau de taille fixe
     */
    public synchronized short[] toArray(){
        short[] out = new short[size];
        if(size == 0) return out;
        int added = 0;
        for(int i=0;i<countArray;i++){
            short[] toAdd = (short[]) listArrays.get(i);
            if(i+1 == countArray){
                System.arraycopy(toAdd, 0, out, added, sizeSubArray - (maxSizeTheory - size));
            }else{
                System.arraycopy(toAdd, 0, out, added, toAdd.length);
                added += toAdd.length;
            }
        }
        return out;
    }
    
    /**
     * Trie le tableau extensible
     */
    @SuppressWarnings("Convert2Lambda")
    public synchronized void sort(){
        short[] array = toArray();
        java.util.Arrays.sort(array);
        for (int i = 0; i < array.length; i++) {
            set(i, array[i]);
        }
    }
    
    /**
     * Détermine si le contenu de deux tableaux extensibles est identiques
     * @param other Correspond au second tableau extensible à comparer au courant
     * @return Retourne true s'ils ont le même contenu, sinon false
     */
    public synchronized boolean objEquals(ExpandableArray_short other){
        if(other.length() != size) return false;
        else{
            for(int i=0;i<size;i++){
                if(other.get(i) != get(i))
                    return false;
            }
            return true;
        }
    }
    
    /**
     * Copie un tableau extensible dans ce tableau
     * @param src Correspond au tableau extensible à copier dans le courant
     * @param srcPos Correspond à l'index de départ du tableau à copier
     * @param destPos Correspond à l'index de départ de copie du tableau de destination
     * @param length Correspond au nombre d'objet à copier dans le tableau courant
     */
    public synchronized void arraycopy(ExpandableArray_short src, int srcPos, int destPos, int length){
        for(int i=srcPos;i<(srcPos+length);i++){
            short element = src.get(i);
            set(destPos++, element);
        }
    }
    
    /**
     * Copie un tableau dans ce tableau extensible
     * @param src Correspond au tableau à copier dans le courant
     * @param srcPos Correspond à l'index de départ du tableau à copier
     * @param destPos Correspond à l'index de départ de copie du tableau de destination
     * @param length Correspond au nombre d'objet à copier dans le tableau courant
     */
    public synchronized void arraycopy(short[] src, int srcPos, int destPos, int length){
        for(int i=srcPos;i<(srcPos+length);i++){
            short element = src[i];
            set(destPos++, element);
        }
    }
    
    /**
     * Renvoie un itérateur sur les éléments de cette liste dans un ordre approprié
     * @return Retourne un itérateur sur les éléments de cette liste dans le bon ordre
     */
    @Override
    public java.util.Iterator<Short> iterator() {
        ExpandableArrayIterator_short<Short> iterator = new ExpandableArrayIterator_short<>(this);
        return iterator;
    }
    
    /**
     * Renvoie le tableau extensible sous la forme d'une chaîne de caractère
     * @return Retourne le tableau extensible sous la forme d'une chaîne de caractère
     */
    @Override
    public synchronized String toString() {
        return java.util.Arrays.toString(toArray());
    }
    
    
    
//METHODES PRIVATES
    /**
     * Renvoie le dernier sous-tableau créé
     * @return Retourne le dernier sous-tableau créé
     */
    private synchronized short[] lastArray() {
        if (listArrays.isEmpty()) {
            return null;
        } else {
            return (short[]) listArrays.get(listArrays.size() - 1);
        }
    }

    /**
     * Ajoute un short dans un sous tableau
     * @param array Correspond au sous tableau
     * @param index Correspond à l'index du sous tableau
     * @param s Correspond au short à ajouter
     */
    private synchronized void addE(short[] array, int index, short s) {
        array[index] = s;
    }

    /**
     * Ajoute un/des sous tableaux
     * @param count Correspond au nombre de sous tableaux à créer
     */
    private synchronized void addA(int count) {
        for (int i = 0; i < count; i++) {
            listArrays.add(new short[sizeSubArray]);
            countArray++;
        }
        maxSizeTheory = countArray * sizeSubArray;
    }
    
    
    
}



/**
 * Cette classe représente les itérators qui peuvent parcourir un tableau extensible
 * @see ExpandableArray_short
 * @author JasonPercus
 * @param <E> Correspond au type d'objet contenu dans le tableau extensible
 */
class ExpandableArrayIterator_short<E> implements java.util.Iterator<E> {

    
    
//ATTRIBUTS
    /**
     * Correspond à la position de l'itérator dans le tableau extensible
     */
    private int position;
    
    /**
     * Correspond au tableau extensible à parcourir
     */
    private final ExpandableArray_short array;

    
    
//CONSTRUCTOR
    /**
     * Crée un objet ExpandableArrayIterator_short
     * @param array Correspond au tableau extensible qui sera parcouru
     */
    protected ExpandableArrayIterator_short(ExpandableArray_short array) {
        this.array      = array;
        this.position   = 0;
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
    public synchronized E next() {
        @SuppressWarnings("UnnecessaryBoxing")
        E e = (E) Short.valueOf(array.get(position));
        position++;
        return e;
    }
    
    /**
     * Supprime de la collection sous-jacente le dernier élément renvoyé par cet itérateur
     */
    @Override
    public synchronized void remove() {
        if(position - 1 > -1)
            array.remove(position - 1);
    }
    
    
    
}