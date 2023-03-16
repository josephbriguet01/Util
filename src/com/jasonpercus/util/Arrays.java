/*
 * Copyright (C) JasonPercus Systems, Inc - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by JasonPercus, 05/2021
 */
package com.jasonpercus.util;



/**
 * Cette classe contient quelques méthodes utiles aux tableaux de taille fixe
 * @see java.util.Arrays
 * @author JasonPercus
 * @version 1.1
 */
public class Arrays {

    
    
//CONSTRUCTOR
    /**
     * Constructeur par défaut
     * @deprecated Ne pas utiliser
     */
    @Deprecated
    private Arrays() {
    }
    
    
    
//METHODES PUBLICS STATICS
    /**
     * Concatène 2 tableaux
     * @param <T> Correspond au type d'objet dans les deux tableaux
     * @param first Correspond au premier tableau à concaténer au second
     * @param second Correspond au second tableau à concaténer au premier
     * @return Retourne le résultat de la concaténation
     */
    public static <T> T[] concat(T[] first, T[] second) {
        T[] result = java.util.Arrays.copyOf(first, first.length + second.length);
        System.arraycopy(second, 0, result, first.length, second.length);
        return result;
    }
    
    /**
     * Inverse les élements d'un tableau. Le dernier devant le premier, le premier le dernier...
     * @param <T> Correspond au type d'objet dans le tableau à inverser
     * @param array Correspond au tableau dont on cherche à inverser les éléments
     */
    public static <T> void invert(T[] array){
        for (int i = 0; i < array.length / 2; i++) {
            T temp = array[i];
            array[i] = array[array.length - 1 - i];
            array[array.length - 1 - i] = temp;
        }
    }
    
    /**
     * Trie un tableau d'objet
     * @param <T> Correspond au type d'objets contenus dans le tableau
     * @param array Correspond au tableau qui sera trié
     */
    public static <T> void sort(T[] array){
        java.util.Arrays.sort(array);
    }
    
    
    
}