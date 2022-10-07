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
 * Cette classe réprésente un tableau extensible d'objets. Au départ sa taille est de 0.
 * @author JasonPercus
 * @version 1.1
 * @param <E> Correspond au type d'objet contenu dans un tableau extensible
 */
public class ExpandableArray<E> extends Array implements Iterable<E> {
    
    
    
    // <editor-fold defaultstate="collapsed" desc="SERIAL_VERSION_UID">
    /**
     * Correspond au numéro de série qui identifie le type de dé/sérialization utilisé pour l'objet
     */
    private static final long serialVersionUID = 1L;
    // </editor-fold>
    
    
    
//ATTRIBUTS
    /**
     * Correspond au type d'objet contenu dans le tableau
     */
    private final Class<E> classObject;
    
    
    
//CONSTRUCTORS
    /**
     * Crée un tableau extensible d'objets
     * @deprecated <div style="color: #D45B5B; font-style: italic">Ne pas utiliser ! Ce constructeur sert uniquement pour la dé/sérialisation. Utiliser {@link #ExpandableArray(java.lang.Class)}</div>
     */
    public ExpandableArray() {
        this(null);
    }

    /**
     * Crée un tableau extensible d'objets déterminés
     * @param c Correspond au type d'objet contenu dans le tableau (penser également à utiliser la généricité avec les chevrons)
     */
    public ExpandableArray(Class<E> c) {
        this(c, (E[])null);
    }

    /**
     * Crée un tableau extensible d'objets déterminés
     * @param c Correspond au type d'objet contenu dans le tableau (penser également à utiliser la généricité avec les chevrons)
     * @param collection Correspond à la collections d'objets qui seront ajoutés dans le tableau
     */
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public ExpandableArray(Class<E> c, E[] collection) {
        super();
        if(c.getSimpleName().equals("boolean") || 
                c.getSimpleName().equals("byte") || 
                c.getSimpleName().equals("short") || 
                c.getSimpleName().equals("int") || 
                c.getSimpleName().equals("long") || 
                c.getSimpleName().equals("float") || 
                c.getSimpleName().equals("double") || 
                c.getSimpleName().equals("char")){
            throw new ClassCastException("Please use class ExpandableArray_"+c.getSimpleName()+" !");
        }
        this.classObject = c;
        if(collection != null)
            this.put(collection);
    }

    /**
     * Crée un tableau extensible d'objets déterminés
     * @param c Correspond au type d'objet contenu dans le tableau (penser également à utiliser la généricité avec les chevrons)
     * @param collection Correspond à la collections d'objets qui seront ajoutés dans le tableau
     */
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public ExpandableArray(Class<E> c, ExpandableArray<E> collection) {
        super();
        if(c.getSimpleName().equals("boolean") || 
                c.getSimpleName().equals("byte") || 
                c.getSimpleName().equals("short") || 
                c.getSimpleName().equals("int") || 
                c.getSimpleName().equals("long") || 
                c.getSimpleName().equals("float") || 
                c.getSimpleName().equals("double") || 
                c.getSimpleName().equals("char")){
            throw new ClassCastException("Please use class ExpandableArray_"+c.getSimpleName()+" !");
        }
        this.classObject = c;
        if(collection != null)
            this.put(collection);
    }

    
    
//METHODES PUBLICS
    /**
     * Renvoie un objet à l'index donné
     * @param index Correspond à l'index de l'objet à récupérer
     * @return Retourne un objet
     */
    public synchronized E get(int index) {
        if (index < 0 || index >= size) {
            throw new java.lang.ArrayIndexOutOfBoundsException("" + index);
        }
        int div = index / sizeSubArray;
        int mod = index % sizeSubArray;
        E[] array = (E[]) listArrays.get(div);
        return array[mod];
    }

    /**
     * Place un objet dans le tableau. Si le tableau est trop petit, celui-ci est étendu automatiquement
     * @param index Correspond à l'index où placer l'objet
     * @param e Correspond à l'objet à placer
     */
    public synchronized void set(int index, E e) {
        if (index < 0) {
            throw new java.lang.ArrayIndexOutOfBoundsException("" + index);
        }
        if (index >= maxSizeTheory) {
            int diff = index - maxSizeTheory;
            int div = diff / sizeSubArray;
            int mod = diff % sizeSubArray;
            int arrayToAdd = div + 1;

            addA(arrayToAdd);
            addE(lastArray(), mod, e);
            size = ((listArrays.size() - 1) * sizeSubArray) + mod + 1;
        } else {
            int div = index / sizeSubArray;
            int mod = index % sizeSubArray;
            E[] array = (E[]) listArrays.get(div);
            addE(array, mod, e);
            size = Math.max(index+1, size);
        }
    }

    /**
     * Place un objet à la suite du dernier objet placé
     * @param e Correspond à l'objet à placer à la suite
     */
    public synchronized void put(E e) {
        set(size, e);
    }
    
    /**
     * Ajoute un tableau d'objets à la suite du dernier objet placé
     * @param collection Correspond au tableau d'objets à placer à la suite
     */
    public synchronized void put(E[] collection){
        for(E e : collection){
            put(e);
        }
    }
    
    /**
     * Ajoute un tableau extensible d'objets à la suite du dernier objet placé
     * @param collection Correspond au tableau extensible d'objets à placer à la suite
     */
    public synchronized void put(ExpandableArray<E> collection){
        for(E e : collection){
            put(e);
        }
    }
    
    /**
     * Renvoie l'index d'un objet dans le tableau
     * @param e Correspond à l'objet à trouver
     * @return Retourne l'index de l'objet trouvé dans le tableau ou -1 si l'objet n'a pas été trouvé
     */
    public synchronized int indexOf(E e){
        for(int i=0;i<size;i++){
            if(get(i).equals(e))
                return i;
        }
        return -1;
    }
    
    /**
     * Renvoie le dernier index d'un objet dans le tableau
     * @param e Correspond à l'objet à trouver
     * @return Retourne le dernier index de l'objet trouvé dans le tableau ou -1 si l'objet n'a pas été trouvé
     */
    public synchronized int lastIndexOf(E e){
        for(int i=size-1;i>-1;i--){
            if(get(i).equals(e))
                return i;
        }
        return -1;
    }
    
    /**
     * Renvoie si oui ou non un objet existe dans le tableau
     * @param e Correspond à l'objet à trouver
     * @return Retourne true si l'objet existe, sinon false
     */
    public synchronized boolean contains(E e){
        return indexOf(e) > -1;
    }
    
    /**
     * Efface une cellule du tableau. Le tableau conserve sa taille actuelle, mais la valeur de la cellule passe à null
     * @param index Correspond à la position de la cellule à effacer
     */
    public synchronized void remove(int index){
        set(index, null);
    }
    
    /**
     * Efface un tableau. Le tableau conserve sa taille actuelle, mais toute les cellules du tableau passent à null
     */
    public synchronized void removeAll(){
        for(int i=0;i<size;i++){
            set(i, null);
        }
    }
    
    /**
     * Efface toutes les cellules du tableau contenant l'objet en paramètre. Le tableau conserve sa taille actuelle, mais la valeur des cellules passent à null
     * @param e Correspond à l'objet à supprimer
     */
    public synchronized void removeAll(E e){
        int index = indexOf(e);
        while (index > -1)
            remove(index);
    }
    
    /**
     * Transforme ce tableau extensible en tableau de taille fixe
     * @return Retourne un tableau de taille fixe
     */
    public synchronized E[] toArray(){
        E[] out;
        if (classObject != null) {
            out = (E[]) java.lang.reflect.Array.newInstance(classObject, size);
        } else {
            out = (E[]) new Object[size];
        }
        if(size == 0) return out;
        int added = 0;
        for(int i=0;i<countArray;i++){
            E[] toAdd = (E[]) listArrays.get(i);
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
     * Trie le tableau extensible (s'il y a des valeurs null, elles seront placés à la fin)
     * @throws java.lang.UnsupportedOperationException Si aucun objet générique a été défini pour ce tableau extensible ou si les objets génériques n'implémente pas l'interface {@link java.lang.Comparable}
     */
    @SuppressWarnings("Convert2Lambda")
    public synchronized void sort(){
        if(classObject != null){
            if(instanceofComparable()){
                E[] array = toArray();
                java.util.Arrays.sort(array, new java.util.Comparator<E>() {
                    @Override
                    public int compare(E o1, E o2) {
                        if (o1 == null && o2 == null) {
                            return 0;
                        }
                        if (o1 == null) {
                            return 1;
                        }
                        if (o2 == null) {
                            return -1;
                        }
                        return ((Comparable)o1).compareTo(o2);
                    }
                });
                for(int i=0;i<array.length;i++){
                    set(i, array[i]);
                }
            }else{
                throw new java.lang.UnsupportedOperationException("Generic objects do not implement Comparable !");
            }
        }else{
            throw new java.lang.UnsupportedOperationException("Generic object has not been defined !");
        }
    }
    
    /**
     * Détermine si le contenu de deux tableaux extensibles est identiques
     * @param other Correspond au second tableau extensible à comparer au courant
     * @return Retourne true s'ils ont le même contenu, sinon false
     */
    public synchronized boolean objEquals(ExpandableArray<E> other){
        if(other.length() != size) return false;
        else{
            for(int i=0;i<size;i++){
                Object o1 = other.get(i);
                Object o2 = get(i);
                if(o1 == null && o2 != null) return false;
                else if(o1 != null && o2 == null) return false;
                else if(o1 != null && o2 != null && !o1.equals(o2)) return false;
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
    public synchronized void arraycopy(ExpandableArray<E> src, int srcPos, int destPos, int length){
        for(int i=srcPos;i<(srcPos+length);i++){
            E element = src.get(i);
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
    public synchronized void arraycopy(E[] src, int srcPos, int destPos, int length){
        for(int i=srcPos;i<(srcPos+length);i++){
            E element = src[i];
            set(destPos++, element);
        }
    }

    /**
     * Renvoie un itérateur sur les éléments de cette liste dans un ordre approprié
     * @return Retourne un itérateur sur les éléments de cette liste dans le bon ordre
     */
    @Override
    public synchronized java.util.Iterator<E> iterator() {
        return new ExpandableArrayIterator(this);
    }
    
    /**
     * Renvoie un tableau extensible sous la forme d'une chaîne de caractère
     * @return Retourne un tableau extensible sous la forme d'une chaîne de caractère
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
    private synchronized E[] lastArray() {
        if (listArrays.isEmpty()) {
            return null;
        } else {
            return (E[]) listArrays.get(listArrays.size() - 1);
        }
    }

    /**
     * Ajoute un élément dans un sous tableau
     * @param array Correspond au sous tableau
     * @param index Correspond à l'index du sous tableau
     * @param e Correspond à l'objet à ajouter
     */
    private synchronized void addE(E[] array, int index, E e) {
        array[index] = e;
    }

    /**
     * Ajoute un/des sous tableaux
     * @param count Correspond au nombre de sous tableaux à créer
     */
    private synchronized void addA(int count) {
        for (int i = 0; i < count; i++) {
            E[] toAdd;
            if (classObject != null) {
                toAdd = (E[]) java.lang.reflect.Array.newInstance(classObject, sizeSubArray);
            } else {
                toAdd = (E[]) new Object[sizeSubArray];
            }
            listArrays.add(toAdd);
            countArray++;
        }
        maxSizeTheory = countArray * sizeSubArray;
    }
    
    /**
     * Vérifie si la classe générique implémente Comparable
     * @return Retourne true, si c'est le cas, sinon false
     */
    private synchronized boolean instanceofComparable(){
        if(classObject == null)
            return false;
        Class[] intfs = classObject.getInterfaces();
        for(Class c : intfs){
            if(c.getCanonicalName().equals("java.lang.Comparable")){
                return true;
            }
        }
        return false;
    }

    
    
}



/**
 * Cette classe représente les itérators qui peuvent parcourir un tableau extensible
 * @see ExpandableArrayOld
 * @author JasonPercus
 * @param <E> Correspond au type d'objet contenu dans le tableau extensible
 */
class ExpandableArrayIterator<E> implements java.util.Iterator<E> {

    
    
//ATTRIBUTS
    /**
     * Correspond à la position de l'itérator dans le tableau extensible
     */
    private int position;
    
    /**
     * Correspond au tableau extensible à parcourir
     */
    private final ExpandableArray<E> array;

    
    
//CONSTRUCTOR
    /**
     * Crée un objet ExpandableArrayIterator
     * @param array Correspond au tableau extensible qui sera parcouru
     */
    protected ExpandableArrayIterator(ExpandableArray<E> array) {
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
        return position < array.length();
    }
    
    /**
     * Renvoie le suivant élément
     * @return Retourne le suivant élément
     */
    @Override
    public synchronized E next() {
        E e = array.get(position);
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