/*
 * Copyright (C) JasonPercus Systems, Inc - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by JasonPercus, 03/2023
 */
package com.jasonpercus.util;



/**
 * Cette classe permet de boucler sur une liste ou un tableau. Le code exécuté lors d'une nouvelle boucle est défini par l'interface {@link Code}
 * @author JasonPercus
 * @version 1.0
 */
public class Foreach {

    
    
//CONSTRUCTOR
    /**
     * Correspond au constructeur par défaut
     */
    private Foreach() {
    }
    
    
    
//METHODES PUBLICS
    /**
     * Boucle une liste
     * @param <O> Correspond au type d'objets contenus dans la liste
     * @param code Correspond au code à exécuter à chaque boucle
     * @param list Correspond à la liste d'objets que l'on cherche à boucler
     */
    public static <O> void execute(Code<O> code, java.util.List<O> list){
        int size = list.size();
        for(int i=0;i<size;i++){
            boolean result = code.loop(i, size, list.get(i));
            size = list.size();
            if(!result)
                break;
        }
    }
    
    /**
     * Boucle un tableau
     * @param <O> Correspond au type d'objets contenus dans le tableau
     * @param code Correspond au code à exécuter à chaque boucle
     * @param array Correspond au tableau d'objets que l'on cherche à boucler
     */
    public static <O> void execute(Code<O> code, O...array){
        int size = array.length;
        for(int i=0;i<size;i++){
            boolean result = code.loop(i, size, array[i]);
            size = array.length;
            if(!result)
                break;
        }
    }
    
    /**
     * Boucle une liste (mais dans le sens inverse)
     * @param <O> Correspond au type d'objets contenus dans la liste
     * @param code Correspond au code à exécuter à chaque boucle
     * @param list Correspond à la liste d'objets que l'on cherche à boucler (mais dans le sens inverse)
     */
    public static <O> void reverse(Code<O> code, java.util.List<O> list){
        int size = list.size();
        for(int i=size-1;i>-1;i--){
            boolean result = code.loop(i, size, list.get(i));
            size = list.size();
            if(!result)
                break;
        }
    }
    
    /**
     * Boucle un tableau (mais dans le sens inverse)
     * @param <O> Correspond au type d'objets contenus dans le tableau
     * @param code Correspond au code à exécuter à chaque boucle
     * @param array Correspond au tableau d'objets que l'on cherche à boucler (mais dans le sens inverse)
     */
    public static <O> void reverse(Code<O> code, O...array){
        int size = array.length;
        for(int i=size-1;i>-1;i--){
            boolean result = code.loop(i, size, array[i]);
            size = array.length;
            if(!result)
                break;
        }
    }
    
    /**
     * Boucle une liste (mais dans le sens inverse) et supprime les objets au fur et à mesure
     * @param <O> Correspond au type d'objets contenus dans la liste
     * @param code Correspond au code à exécuter à chaque boucle
     * @param list Correspond à la liste d'objets que l'on cherche à boucler (mais dans le sens inverse)
     */
    public static <O> void reverseAndRemove(Code<O> code, java.util.List<O> list){
        int size = list.size();
        for(int i=size-1;i>-1;i--){
            O o = list.get(i);
            boolean result = code.loop(i, size, o);
            list.remove(i);
            size = list.size();
            if(!result)
                break;
        }
    }
    
    
    
//INTERFACE
    /**
     * Cette interface permet à un objet {@link Foreach} de boucler sur une liste ou un tableau
     * @param <O> Correspond au type d'objet contenus dans la liste ou le tableau
     * @author JasonPercus
     * @version 1.0
     */
    public interface Code<O> {



    //METHODE
        /**
         * A chaque nouvelle boucle cette méthode est appelée
         * @param i Correspond à l'indice de la boucle
         * @param length Correspond à la taille de la liste ou du tableau
         * @param o Correspond à l'objet situé dans la liste ou le tableau à l'indice i
         * @return Détermine si l'on souhaite obtenir une prochaine boucle
         */
        public boolean loop(int i, int length, O o);



    }
    
    
    
}