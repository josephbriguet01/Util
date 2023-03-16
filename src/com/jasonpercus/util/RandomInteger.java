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
 * Cette classe permet de générer des nombres entiers
 * @author JasonPercus
 * @version 1.0
 */
public class RandomInteger {

    
    
//CONSTRUCTOR
    /**
     * Constructeur par défaut
     */
    private RandomInteger() {
    }
    
    
    
//METHODE
    /**
     * Renvoie un nombre entier dans l'intervalle [min, max]
     * @param min Correspond au nombre minimum qui peut être renvoyé
     * @param max Correspond au nombre maximum qui peut être renvoyé
     * @return Retourne un nombre entier dans l'intervalle [min, max]
     */
    public static int next(int min, int max){
        return min + (int)(Math.random() * ((max - min) + 1));
    }
    
    
    
}