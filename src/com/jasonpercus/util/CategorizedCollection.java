/*
 * Copyright (C) JasonPercus Systems, Inc - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by JasonPercus, January 2024
 */
package com.jasonpercus.util;



/**
 * Cette classe permet de créer une collection de catégories d'éléments
 * @author JasonPercus
 * @version 1.0
 * @param <E> Correspond au type d'éléments contenus dans la collection
 * @param <C> Correspond au type de catégories (attention, il s'agit d'une énumération)
 */
public class CategorizedCollection <E, C extends Enum<C>> implements Iterable<E> {

    
    
//ATTRIBUTS
    /**
     * Correspond au type de collections utilisé pour chaque catégorie. Attention: il doit y avoir un constructeur par défaut
     */
    private final Class<? extends java.util.Collection> clazz;
    
    /**
     * Correspond aux associations entres les catégories et leur collection
     */
    private final java.util.HashMap<C, java.util.Collection> map;
    
    /**
     * Correspond à la liste des collections ordonnées pour les itérateurs
     */
    private       java.util.LinkedList<java.util.Collection> collections;
    
    /**
     * Correspond au nombre d'élément dans la collection
     */
    private       int size;
    
    
    
//CONSTRUCTORS
    /**
     * Crée une collection de catégories
     * @param categories Correspond aux catégories ordonnées créées à la base
     */
    @SafeVarargs
    public CategorizedCollection(C... categories) {
        this(java.util.ArrayList.class, categories);
    }
    
    /**
     * Crée une collection de catégories
     * @param clazz Correspond au type de collections utilisé pour chaque catégorie. Attention: il doit y avoir un constructeur par défaut
     * @param categories Correspond aux catégories ordonnées créées à la base
     * @throws NullPointerException Si la classe ou la catégorie est null
     * @throws CategorizedCollectionException Si la catégorie ne peut être créé car la classe définie en paramètre du constructeur {@link #CategorizedCollection(java.lang.Class, java.lang.Enum...)} n'a pas de constructeur par défaut
     */
    @SafeVarargs
    @SuppressWarnings("UnusedAssignment")
    public CategorizedCollection(Class<? extends java.util.Collection> clazz, C... categories) throws NullPointerException, CategorizedCollectionException {
        if(clazz == null)
            throw new NullPointerException("clazz is null !");
        if(categories == null)
            throw new NullPointerException("categories is null !");
        this.clazz = clazz;
        java.util.List<C> cs = new java.util.LinkedList<>();
        for(C category : categories) {
            if(category != null){
                if(!cs.contains(category))
                    cs.add(category);
                else
                    throw new CategorizedCollectionException("Cannot generate categories because one category (" + category.name() + ") appears multiple times");
            }
        }
        cs.clear();
        cs = null;
        this.map = new java.util.HashMap<>();
        this.collections = new java.util.LinkedList<>();
        for(C category : categories) {
            if(category != null){
                try {
                    java.util.Collection collection = clazz.getConstructor().newInstance();
                    map.put(category, collection);
                    collections.add(collection);
                } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | java.lang.reflect.InvocationTargetException ex) {
                    ex.printStackTrace();
//                    throw new CategorizedCollectionException("Cannot generate categories because collection (" + clazz.getCanonicalName() + ") does not have a default constructor !", ex);
                }
            }
        }
    }
    
    
    
//METHODES PUBLICS
    /**
     * Ajoute un élément à une catégorie de la collection. Si la catégorie n'existe pas, elle est créée au passage
     * @param element Correspond à l'élément à ajouter à la catégorie
     * @param category Correspond à la catégorie où sera ajouté l'élément
     * @return Retourne true si l'élément a bien été ajouté, sinon false
     * @throws NullPointerException Si la catégorie est null
     * @throws CategorizedCollectionException Si la catégorie ne peut être créé car la classe définie en paramètre du constructeur {@link #CategorizedCollection(java.lang.Class, java.lang.Enum...)} n'a pas de constructeur par défaut
     */
    @SuppressWarnings("unchecked")
    public boolean add(E element, C category) throws NullPointerException, CategorizedCollectionException {
        if(category == null)
            throw new NullPointerException("category is null !");
        java.util.Collection collection = map.get(category);
        if(collection != null){
            boolean result = collection.add(element);
            if(result)
                size++;
            return result;
        }else{
            try {
                collection = clazz.getConstructor().newInstance();
                map.put(category, collection);
                collections.add(collection);
                boolean result = collection.add(element);
                if(result)
                    size++;
                return result;
            } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | java.lang.reflect.InvocationTargetException ex) {
                throw new CategorizedCollectionException("Cannot generate categories because collection (" + clazz.getCanonicalName() + ") does not have a default constructor !", ex);
            }
        }
    }
    
    /**
     * Ajoute un élément dans chaque catégorie déjà existantes
     * @param element Correspond à l'élément à ajouter à chaque catégorie
     * @return Retourne true s'il a bien été ajouté dans toutes les catégories, sinon false
     */
    @SuppressWarnings("unchecked")
    public boolean add(E element) {
        java.util.List<C> categories = listCategories();
        boolean added = true;
        for(C category : categories){
            if(!map.get(category).add(element))
                added = false;
            else
                size++;
        }
        return added;
    }
    
    /**
     * Supprime un seul élément d'une catégorie
     * @param element Correspond à l'élément à supprimer
     * @param category Correspond à la catégorie où sera supprimé l'élément
     * @return Retourne true si l'élément a bien été supprimé ou ps
     * @throws NullPointerException Si la catégorie est null
     */
    public boolean remove(E element, C category) throws NullPointerException {
        if(category == null)
            throw new NullPointerException("category is null !");
        java.util.Collection collection = map.get(category);
        if(collection == null)
            return false;
        boolean result = collection.remove(element);
        if(result)
            size--;
        return result;
    }
    
    /**
     * Supprime un seul élément de la collection (dans chaque catégorie)
     * @param element Correspond à l'élément recherché et supprimé
     * @return Retourne true s'il a bien été supprimé, sinon false
     */
    public boolean remove(E element) {
        java.util.Iterator<java.util.Collection> iterator = this.collections.iterator();
        boolean result = false;
        while(iterator.hasNext()){
            java.util.Collection collection = iterator.next();
            boolean deleted = collection.remove(element);
            if(deleted){
                result = true;
                size--;
            }
        }
        return result;
    }
    
    /**
     * Vide une catégorie (mais ne la supprime pas à contrario de {@link #removeCategory(java.lang.Enum)})
     * @param category Correspond à la catégorie à vider
     * @throws NullPointerException Si la catégorie est null
     */
    public void clear(C category) throws NullPointerException {
        if(category == null)
            throw new NullPointerException("category is null !");
        java.util.Collection collection = map.get(category);
        if (collection != null) {
            int count = collection.size();
            collection.clear();
            size -= count;
        }
    }
    
    /**
     * Vide toutes les catégories (mais ne supprime pas les catégories à contrario de {@link #removeCategories()})
     */
    public void clear() {
        java.util.Iterator<java.util.Collection> iterator = this.collections.iterator();
        while(iterator.hasNext()){
            java.util.Collection collection = iterator.next();
            collection.clear();
        }
        size = 0;
    }
    
    /**
     * Supprime une catégorie et ses éléments de la collection
     * @param category Correspond à la catégorie à supprimer
     * @return Retourne la collection que représentait la catégorie supprimée. Si la catégorie n'a pas été trouvée, alors null est renvoyé
     */
    public java.util.Collection removeCategory(C category) throws NullPointerException {
        if(category == null)
            throw new NullPointerException("category is null !");
        java.util.Collection collection = map.get(category);
        if (collection != null) {
            size -= collection.size();
            collections.remove(collection);
            map.remove(category);
            return collection;
        } else
            return null;
    }
    
    /**
     * Supprime toutes les catégories et leurs éléments de la collection
     * @return Retourne les collections que représentaient les catégories supprimées. Si la collection était vide alors un tableau vide est renvoyé
     */
    public java.util.Collection[] removeCategories(){
        map.clear();
        java.util.Collection[] array = new java.util.Collection[collections.size()];
        java.util.Iterator<java.util.Collection> iterator = this.collections.iterator();
        int i = 0;
        while(iterator.hasNext()){
            array[i] = iterator.next();
            i++;
        }
        this.collections.clear();
        size = 0;
        return array;
    }
    
    /**
     * Renvoie la liste des catégories de la collection
     * @return Retourne la liste des catégories de la collection
     */
    public java.util.List<C> listCategories(){
        java.util.Set<C> keys = map.keySet();
        java.util.List<C> categories = new java.util.LinkedList<>();
        for(C category : keys)
            categories.add(category);
        return categories;
    }
    
    /**
     * Réordonne la séquence des catégories. Indispensable lors d'un parcours de la collection pour afficher les éléments d'une catégories avant ceux d'une autre catégorie
     * @param categories Correspond aux catégories ordonnées. Si des catégories n'apparaissent pas dans cette liste mais sont bien présente dans la collection, alors celles-ci seront ajouté à la fin dans le même ordre dans lequel elles se trouvent
     * @throws NullPointerException Si la catégorie est null
     * @throws CategorizedCollectionException Si une catégorie a été ajouté plusieurs fois dans la séquence categories
     */
    @SafeVarargs
    @SuppressWarnings("UnusedAssignment")
    public final void reorderCategory(C... categories) throws NullPointerException, CategorizedCollectionException {
        if(categories == null)
            throw new NullPointerException("categories is null !");
        if(categories.length > 0){
            java.util.List<C> cs = new java.util.LinkedList<>();
            for(C category : categories) {
                if(category != null){
                    if(!cs.contains(category))
                        cs.add(category);
                    else
                        throw new CategorizedCollectionException("Cannot reorder categories because one category (" + category.name() + ") appears multiple times");
                }
            }
            cs.clear();
            cs = null;
            
            @SuppressWarnings("LocalVariableHidesMemberVariable")
            java.util.LinkedList<java.util.Collection> collections = new java.util.LinkedList<>();
            for(C category : categories){
                if(category != null){
                    java.util.Collection collection = map.get(category);
                    if(collection != null){
                        collections.add(collection);
                        this.collections.remove(collection);
                    }
                }
            }
            for(java.util.Collection collection : this.collections)
                collections.add(collection);
            this.collections.clear();
            this.collections = collections;
        }
    }
    
    /**
     * Renvoie si oui ou non une catégorie possède un élément
     * @param element Correspond à l'élément recherché
     * @param category Correspond à la catégorie dans laquelle se fait la recherche
     * @return Retourne true si l'élément a été trouvé ou null sinon
     * @throws NullPointerException Si la catégorie est null
     */
    public boolean contains(E element, C category) throws NullPointerException {
        if(category == null)
            throw new NullPointerException("category is null !");
        java.util.Collection collection = map.get(category);
        if (collection != null) {
            return collection.contains(element);
        } else 
            return false;
    }
    
    /**
     * Renvoie si oui ou non une catégorie possède un élément
     * @param element Correspond à l'élément recherché
     * @return Retourne true si l'élément a été trouvé dans au moins une catégorie ou null sinon
     */
    public boolean contains(E element) {
        java.util.Iterator<java.util.Collection> iterator = this.collections.iterator();
        while(iterator.hasNext()){
            if(iterator.next().contains(element))
                return true;
        }
        return false;
    }
    
    /**
     * Renvoie si oui ou non une catégorie de la collection est vide ou pas
     * @param category Correspond à la catégorie recherchée
     * @return Retourne true si c'est le cas, sinon false
     * @throws NullPointerException Si la catégorie est null
     */
    public boolean isEmpty(C category) throws NullPointerException {
        if(category == null)
            throw new NullPointerException("category is null !");
        java.util.Collection collection = map.get(category);
        if (collection != null) {
            return collection.isEmpty();
        } else 
            return true;
    }
    
    /**
     * Renvoie si la collection est vide ou pas
     * @return Retourne true si c'est le cas, sinon false
     */
    public boolean isEmpty() {
        return this.size <= 0;
    }
    
    /**
     * Renvoie le nombre d'élements dans une catégorie de la collection
     * @param category Correspond à la catégorie recherchée
     * @return Retourne le nombre d'élements dans la catégorie
     * @throws NullPointerException Si la catégorie est null
     */
    public int size(C category) throws NullPointerException {
        if(category == null)
            throw new NullPointerException("category is null !");
        java.util.Collection collection = map.get(category);
        if (collection != null) {
            return collection.size();
        } else 
            return 0;
    }
    
    /**
     * Renvoie le nombre d'éléments dans la collection (toutes les catégories confondues)
     * @return Retourne le nombre d'éléments dans la collection
     */
    public int size() {
        return this.size;
    }
    
    /**
     * Renvoie une collection (non-modifiable)
     * @param category Correspond à la catégorie de la collection à retourner
     * @return Retourne la collection trouvée, sinon null
     */
    @SuppressWarnings("unchecked")
    public java.util.Collection<E> getCollection(C category) {
        java.util.Collection collection = map.get(category);
        if(collection == null)
            return null;
        else
            return java.util.Collections.unmodifiableCollection(collection);
    }
    
    /**
     * Renvoie les collections catégorisées (non-modifiables)
     * @return Retourne les collections catégorisées
     */
    @SuppressWarnings("unchecked")
    public java.util.Collection<E>[] getCollections() {
        @SuppressWarnings("unchecked")
        java.util.Collection<E>[] array = new java.util.Collection[collections.size()];
        for(int i = 0; i < array.length; i++){
            array[i] = java.util.Collections.unmodifiableCollection(collections.get(i));
        }
        return array;
    }

    /**
     * Renvoie l'itérateur de cette collection
     * @return Retourne l'itérateur de cette collection
     */
    @Override
    public java.util.Iterator<E> iterator() {
        return new CategorizedCollectionIterator<>();
    }
    
    
    
//CLASS
    /**
     * Cette classe représente un objet itérateur d'un objet {@link CategorizedCollection}
     * @param <E> Correspond au type d'élément contenu dans l'objet {@link CategorizedCollection}
     * @author JasonPercus
     * @version 1.0
     */
    private class CategorizedCollectionIterator <E> implements java.util.Iterator<E>{
        
        
        
    //ATTRIBUTS
        /**
         * Correspond à la liste de tous les itérateurs de toutes les sous-liste
         */
        private final java.util.List<java.util.Iterator<E>> iterators;
        
        /**
         * Correspond à l'index du tableau d'itérateurs
         */
        private int index;
        
        /**
         * Correspond à la taille de l'objet {@link CategorizedCollection}
         */
        private final int size;
        
        /**
         * Correspond au nombre d'objet lu dans l'objet {@link CategorizedCollection} grâce à cet itérateur
         */
        private int position;

        
        
    //CONSTRUCTOR
        /**
         * Crée un itérateur {@link CategorizedCollectionIterator}
         */
        @SuppressWarnings("unchecked")
        public CategorizedCollectionIterator() {
            this.index     = 0;
            this.position  = 0;
            this.size      = size();
            this.iterators = new java.util.ArrayList<>();
            for(java.util.Collection collection : collections)
                this.iterators.add(collection.iterator());
        }

        
        
    //METHODES PUBLICS
        /**
         * Returns {@code true} if the iteration has more elements.
         * (In other words, returns {@code true} if {@link #next} would
         * return an element rather than throwing an exception.)
         *
         * @return {@code true} if the iteration has more elements
         */
        @Override
        public boolean hasNext() {
            return this.iterators.isEmpty() ? false : this.position < this.size;
        }

        /**
         * Returns the next element in the iteration.
         *
         * @return the next element in the iteration
         * @throws NoSuchElementException if the iteration has no more elements
         */
        @Override
        public E next() {
            java.util.Iterator<E> iterator = this.iterators.get(this.index);
            if(iterator.hasNext()) {
                this.position++;
                return iterator.next();
            } else {
                this.index++;
                return next();
            }
        }
        
        
        
    }
    
    
    
}