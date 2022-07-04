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
 * Cette classe réprésente un tableau extensible de float. Au départ sa taille est de 0.
 * @author JasonPercus
 * @version 1.1
 */
public class ExpandableArray_float extends Array implements Iterable<Float>{
    
    
    
    // <editor-fold defaultstate="collapsed" desc="SERIAL_VERSION_UID">
    /**
     * Correspond au numéro de série qui identifie le type de dé/sérialization utilisé pour l'objet
     */
    private static final long serialVersionUID = 1L;
    // </editor-fold>
    
    
    
//CONSTRUCTORS
    /**
     * Crée un tableau extensible de float
     */
    public ExpandableArray_float() {
        super();
    }
    
    /**
     * Crée un tableau extensible de float avec pour valeurs de départ les floats du tableau array
     * @param array Correspond au tableau qui fourni les valeurs de départ
     */
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public ExpandableArray_float(float... array){
        super();
        if(array != null)
            this.put(array);
    }
    
    /**
     * Crée un tableau extensible de float avec pour valeurs de départ les float du tableau array
     * @param array Correspond au tableau qui fourni les valeurs de départ
     */
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public ExpandableArray_float(ExpandableArray_float array){
        super();
        if(array != null)
            this.put(array);
    }
    
    
    
//METHODES PUBLICS
    /**
     * Renvoie un float à l'index donné
     * @param index Correspond à l'index du float à récupérer
     * @return Retourne un float
     */
    public synchronized float get(int index) {
        if (index < 0 || index >= size) {
            throw new java.lang.ArrayIndexOutOfBoundsException("" + index);
        }
        int div = index / sizeSubArray;
        int mod = index % sizeSubArray;
        float[] array = (float[]) listArrays.get(div);
        return array[mod];
    }

    /**
     * Place un float dans le tableau. Si le tableau est trop petit, celui-ci est étendu automatiquement
     * @param index Correspond à l'index où placer le float
     * @param f Correspond au float à placer
     */
    public synchronized void set(int index, float f) {
        if (index < 0) {
            throw new java.lang.ArrayIndexOutOfBoundsException("" + index);
        }
        if (index >= maxSizeTheory) {
            int diff = index - maxSizeTheory;
            int div = diff / sizeSubArray;
            int mod = diff % sizeSubArray;
            int arrayToAdd = div + 1;

            addA(arrayToAdd);
            addE(lastArray(), mod, f);
            size = ((listArrays.size() - 1) * sizeSubArray) + mod + 1;
        } else {
            int div = index / sizeSubArray;
            int mod = index % sizeSubArray;
            float[] array = (float[]) listArrays.get(div);
            addE(array, mod, f);
            size = Math.max(index+1, size);
        }
    }
    
    /**
     * Place un ou plusieurs float à la suite du dernier float placé
     * @param array Correspond au tableau de floats à placer à la suite
     */
    public synchronized void put(float... array){
        for(float b : array){
            set(size, b);
        }
    }
    
    /**
     * Ajoute un tableau extensible de floats à la suite du dernier float placé
     * @param array Correspond au tableau extensible de floats à placer à la suite
     */
    public synchronized void put(ExpandableArray_float array){
        for(float b : array){
            put(b);
        }
    }
    
    /**
     * Renvoie l'index d'un float dans le tableau
     * @param f Correspond au float à trouver
     * @return Retourne l'index du float trouvé dans le tableau ou -1 si le float n'a pas été trouvé
     */
    public synchronized int indexOf(float f){
        for(int j=0;j<size;j++){
            if(get(j) == f)
                return j;
        }
        return -1;
    }
    
    /**
     * Renvoie le dernier index d'un float dans le tableau
     * @param f Correspond au float à trouver
     * @return Retourne le dernier index du float trouvé dans le tableau ou -1 si le float n'a pas été trouvé
     */
    public synchronized int lastIndexOf(float f){
        for(int j=size-1;j>-1;j--){
            if(get(j) == f)
                return j;
        }
        return -1;
    }
    
    /**
     * Renvoie si oui ou non un float existe dans le tableau
     * @param f Correspond au float à trouver
     * @return Retourne true si le float existe, sinon false
     */
    public synchronized boolean contains(float f){
        return indexOf(f) > -1;
    }
    
    /**
     * Efface une cellule du tableau. Le tableau conserve sa taille actuelle, mais la valeur de la cellule passe à 0
     * @param index Correspond à la position de la cellule à effacer
     */
    public synchronized void remove(int index){
        set(index, (float)0f);
    }
    
    /**
     * Efface le tableau. Le tableau conserve sa taille actuelle, mais toute les cellules du tableau passent à 0
     */
    public synchronized void removeAll(){
        for(int i=0;i<size;i++){
            set(i, (float)0f);
        }
    }
    
    /**
     * Efface toutes les cellules du tableau contenant le float en paramètre. Le tableau conserve sa taille actuelle, mais la valeur des cellules passent à 0
     * @param f Correspond au float à supprimer
     */
    public synchronized void removeAll(float f){
        int index = indexOf(f);
        while (index > -1)
            remove(index);
    }
    
    /**
     * Transforme ce tableau extensible en tableau de taille fixe
     * @return Retourne un tableau de taille fixe
     */
    public synchronized float[] toArray(){
        float[] out = new float[size];
        if(size == 0) return out;
        int added = 0;
        for(int i=0;i<countArray;i++){
            float[] toAdd = (float[]) listArrays.get(i);
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
        float[] array = toArray();
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
    public synchronized boolean objEquals(ExpandableArray_float other){
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
    public synchronized void arraycopy(ExpandableArray_float src, int srcPos, int destPos, int length){
        for(int i=srcPos;i<(srcPos+length);i++){
            float element = src.get(i);
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
    public synchronized void arraycopy(float[] src, int srcPos, int destPos, int length){
        for(int i=srcPos;i<(srcPos+length);i++){
            float element = src[i];
            set(destPos++, element);
        }
    }
    
    /**
     * Renvoie un itérateur sur les éléments de cette liste dans un ordre approprié
     * @return Retourne un itérateur sur les éléments de cette liste dans le bon ordre
     */
    @Override
    public java.util.Iterator<Float> iterator() {
        ExpandableArrayIterator_float<Float> iterator = new ExpandableArrayIterator_float<>(this);
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
    private synchronized float[] lastArray() {
        if (listArrays.isEmpty()) {
            return null;
        } else {
            return (float[]) listArrays.get(listArrays.size() - 1);
        }
    }

    /**
     * Ajoute un float dans un sous tableau
     * @param array Correspond au sous tableau
     * @param index Correspond à l'index du sous tableau
     * @param f Correspond au float à ajouter
     */
    private synchronized void addE(float[] array, int index, float f) {
        array[index] = f;
    }

    /**
     * Ajoute un/des sous tableaux
     * @param count Correspond au nombre de sous tableaux à créer
     */
    private synchronized void addA(int count) {
        for (int i = 0; i < count; i++) {
            listArrays.add(new float[sizeSubArray]);
            countArray++;
        }
        maxSizeTheory = countArray * sizeSubArray;
    }
    
    
    
}



/**
 * Cette classe représente les itérators qui peuvent parcourir un tableau extensible
 * @see ExpandableArray_float
 * @author JasonPercus
 * @param <E> Correspond au type d'objet contenu dans le tableau extensible
 */
class ExpandableArrayIterator_float<E> implements java.util.Iterator<E> {

    
    
//ATTRIBUTS
    /**
     * Correspond à la position de l'itérator dans le tableau extensible
     */
    private int position;
    
    /**
     * Correspond au tableau extensible à parcourir
     */
    private final ExpandableArray_float array;

    
    
//CONSTRUCTOR
    /**
     * Crée un objet ExpandableArrayIterator_float
     * @param array Correspond au tableau extensible qui sera parcouru
     */
    protected ExpandableArrayIterator_float(ExpandableArray_float array) {
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
        E e = (E) Float.valueOf(array.get(position));
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