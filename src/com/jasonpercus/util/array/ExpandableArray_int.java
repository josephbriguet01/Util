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
 * Cette classe réprésente un tableau extensible d'entier. Au départ sa taille est de 0.
 * @author JasonPercus
 * @version 1.1
 */
public class ExpandableArray_int extends Array implements Iterable<Integer>{
    
    
    
    // <editor-fold defaultstate="collapsed" desc="SERIAL_VERSION_UID">
    /**
     * Correspond au numéro de série qui identifie le type de dé/sérialization utilisé pour l'objet
     */
    private static final long serialVersionUID = 1L;
    // </editor-fold>
    
    
    
//CONSTRUCTORS
    /**
     * Crée un tableau extensible d'entier
     */
    public ExpandableArray_int() {
        super();
    }
    
    /**
     * Crée un tableau extensible d'entier avec pour valeurs de départ les entiers du tableau array
     * @param array Correspond au tableau qui fourni les valeurs de départ
     */
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public ExpandableArray_int(int... array){
        super();
        if(array != null)
            this.put(array);
    }
    
    /**
     * Crée un tableau extensible d'entier avec pour valeurs de départ les entiers du tableau array
     * @param array Correspond au tableau qui fourni les valeurs de départ
     */
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public ExpandableArray_int(ExpandableArray_int array){
        super();
        if(array != null)
            this.put(array);
    }
    
    
    
//METHODES PUBLICS
    /**
     * Renvoie un int à l'index donné
     * @param index Correspond à l'index de l'int à récupérer
     * @return Retourne un int
     */
    public synchronized int get(int index) {
        if (index < 0 || index >= size) {
            throw new java.lang.ArrayIndexOutOfBoundsException("" + index);
        }
        int div = index / sizeSubArray;
        int mod = index % sizeSubArray;
        int[] array = (int[]) listArrays.get(div);
        return array[mod];
    }

    /**
     * Place un int dans le tableau. Si le tableau est trop petit, celui-ci est étendu automatiquement
     * @param index Correspond à l'index où placer l'int
     * @param i Correspond à l'int à placer
     */
    public synchronized void set(int index, int i) {
        if (index < 0) {
            throw new java.lang.ArrayIndexOutOfBoundsException("" + index);
        }
        if (index >= maxSizeTheory) {
            int diff = index - maxSizeTheory;
            int div = diff / sizeSubArray;
            int mod = diff % sizeSubArray;
            int arrayToAdd = div + 1;

            addA(arrayToAdd);
            addE(lastArray(), mod, i);
            size = ((listArrays.size() - 1) * sizeSubArray) + mod + 1;
        } else {
            int div = index / sizeSubArray;
            int mod = index % sizeSubArray;
            int[] array = (int[]) listArrays.get(div);
            addE(array, mod, i);
            size = Math.max(index+1, size);
        }
    }
    
    /**
     * Place un ou plusieurs int à la suite du dernier int placé
     * @param array Correspond au tableau d'int à placer à la suite
     */
    public synchronized void put(int... array){
        for(int i : array){
            set(size, i);
        }
    }
    
    /**
     * Ajoute un tableau extensible d'int à la suite du dernier int placé
     * @param array Correspond au tableau extensible d'int à placer à la suite
     */
    public synchronized void put(ExpandableArray_int array){
        for(int e : array){
            put(e);
        }
    }
    
    /**
     * Renvoie l'index d'un int dans le tableau
     * @param i Correspond à l'int à trouver
     * @return Retourne l'index de l'int trouvé dans le tableau ou -1 si l'int n'a pas été trouvé
     */
    public synchronized int indexOf(int i){
        for(int j=0;j<size;j++){
            if(get(j) == i)
                return j;
        }
        return -1;
    }
    
    /**
     * Renvoie le dernier index d'un int dans le tableau
     * @param i Correspond à l'int à trouver
     * @return Retourne le dernier index de l'int trouvé dans le tableau ou -1 si l'int n'a pas été trouvé
     */
    public synchronized int lastIndexOf(int i){
        for(int j=size-1;j>-1;j--){
            if(get(j) == i)
                return j;
        }
        return -1;
    }
    
    /**
     * Renvoie si oui ou non un int existe dans le tableau
     * @param i Correspond à l'int à trouver
     * @return Retourne true si l'int existe, sinon false
     */
    public synchronized boolean contains(int i){
        return indexOf(i) > -1;
    }
    
    /**
     * Efface une cellule du tableau. Le tableau conserve sa taille actuelle, mais la valeur de la cellule passe à 0
     * @param index Correspond à la position de la cellule à effacer
     */
    public synchronized void remove(int index){
        set(index, 0);
    }
    
    /**
     * Efface le tableau. Le tableau conserve sa taille actuelle, mais toute les cellules du tableau passent à 0
     */
    public synchronized void removeAll(){
        for(int i=0;i<size;i++){
            set(i, 0);
        }
    }
    
    /**
     * Efface toutes les cellules du tableau contenant l'int en paramètre. Le tableau conserve sa taille actuelle, mais la valeur des cellules passent à 0
     * @param i Correspond au int à supprimer
     */
    public synchronized void removeAll(int i){
        int index = indexOf(i);
        while (index > -1)
            remove(index);
    }
    
    /**
     * Transforme ce tableau extensible en tableau de taille fixe
     * @return Retourne un tableau de taille fixe
     */
    public synchronized int[] toArray(){
        int[] out = new int[size];
        if(size == 0) return out;
        int added = 0;
        for(int i=0;i<countArray;i++){
            int[] toAdd = (int[]) listArrays.get(i);
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
        int[] array = toArray();
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
    public synchronized boolean objEquals(ExpandableArray_int other){
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
    public synchronized void arraycopy(ExpandableArray_int src, int srcPos, int destPos, int length){
        for(int i=srcPos;i<(srcPos+length);i++){
            int element = src.get(i);
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
    public synchronized void arraycopy(int[] src, int srcPos, int destPos, int length){
        for(int i=srcPos;i<(srcPos+length);i++){
            int element = src[i];
            set(destPos++, element);
        }
    }
    
    /**
     * Renvoie un itérateur sur les éléments de cette liste dans un ordre approprié
     * @return Retourne un itérateur sur les éléments de cette liste dans le bon ordre
     */
    @Override
    public java.util.Iterator<Integer> iterator() {
        ExpandableArrayIterator_int<Integer> iterator = new ExpandableArrayIterator_int<>(this);
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
    private synchronized int[] lastArray() {
        if (listArrays.isEmpty()) {
            return null;
        } else {
            return (int[]) listArrays.get(listArrays.size() - 1);
        }
    }

    /**
     * Ajoute un int dans un sous tableau
     * @param array Correspond au sous tableau
     * @param index Correspond à l'index du sous tableau
     * @param i Correspond à l'int à ajouter
     */
    private synchronized void addE(int[] array, int index, int i) {
        array[index] = i;
    }

    /**
     * Ajoute un/des sous tableaux
     * @param count Correspond au nombre de sous tableaux à créer
     */
    private synchronized void addA(int count) {
        for (int i = 0; i < count; i++) {
            listArrays.add(new int[sizeSubArray]);
            countArray++;
        }
        maxSizeTheory = countArray * sizeSubArray;
    }
    
    
    
}



/**
 * Cette classe représente les itérators qui peuvent parcourir un tableau extensible
 * @see ExpandableArray_int
 * @author JasonPercus
 * @param <E> Correspond au type d'objet contenu dans le tableau extensible
 */
class ExpandableArrayIterator_int<E> implements java.util.Iterator<E> {

    
    
//ATTRIBUTS
    /**
     * Correspond à la position de l'itérator dans le tableau extensible
     */
    private int position;
    
    /**
     * Correspond au tableau extensible à parcourir
     */
    private final ExpandableArray_int array;

    
    
//CONSTRUCTOR
    /**
     * Crée un objet ExpandableArrayIterator_int
     * @param array Correspond au tableau extensible qui sera parcouru
     */
    protected ExpandableArrayIterator_int(ExpandableArray_int array) {
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
        E e = (E) Integer.valueOf(array.get(position));
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