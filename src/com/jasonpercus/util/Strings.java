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
 * Cette classe contient quelques méthodes susceptibles à la transformations des chaînes de caractères {@link String}
 * @see String
 * @author JasonPercus
 * @version 1.0
 */
public class Strings {
    
    
    
//CONSTRUCTOR
    /**
     * Constructeur par défaut
     * @deprecated Ne pas utiliser
     */
    private Strings() {
    }
    
    
    
//METHODES PUBLICS STATICS
    /**
     * Cette méthode génère une chaine aléatoire de length caractère [a-zA-Z0-9]
     * @param length Correspond à la taille de la chaine voulue
     * @param charsAuthorized Correspond à la liste des caractères authorisés
     * @return Retourne la chaine de caractère
     */
    public static String generate(int length, String charsAuthorized) {
        String pass = "";
        for (int x = 0; x < length; x++) {
            int i = (int) Math.floor(Math.random() * charsAuthorized.length());
            pass += charsAuthorized.charAt(i);
        }
        return pass;
    }
    
    /**
     * Cette méthode génère une chaine aléatoire de length caractère [a-zA-Z0-9]
     * @param length Correspond à la taille de la chaine voulue
     * @return Retourne la chaine de caractère
     */
    public static String generate(int length) {
        return generate(length, "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890");
    }
    
    /**
     * Cette méthode génère une chaine aléatoire de length caractère [0-9]
     * @param length Correspond à la taille de la chaine voulue
     * @return Retourne la chaine de caractère
     */
    public static String generateDigit(int length) {
        return generate(length, "1234567890");
    }
    
    /**
     * Cette méthode génère une chaine aléatoire de length caractère [a-z]
     * @param length Correspond à la taille de la chaine voulue
     * @return Retourne la chaine de caractère
     */
    public static String generateLower(int length) {
        return generate(length, "abcdefghijklmnopqrstuvwxyz");
    }
    
    /**
     * Cette méthode génère une chaine aléatoire de length caractère [a-z0-9]
     * @param length Correspond à la taille de la chaine voulue
     * @return Retourne la chaine de caractère
     */
    public static String generateLowerDigit(int length) {
        return generate(length, "abcdefghijklmnopqrstuvwxyz1234567890");
    }
    
    /**
     * Cette méthode génère une chaine aléatoire de length caractère [A-Z]
     * @param length Correspond à la taille de la chaine voulue
     * @return Retourne la chaine de caractère
     */
    public static String generateUpper(int length) {
        return generate(length, "ABCDEFGHIJKLMNOPQRSTUVWXYZ");
    }
    
    /**
     * Cette méthode génère une chaine aléatoire de length caractère [A-Z0-9]
     * @param length Correspond à la taille de la chaine voulue
     * @return Retourne la chaine de caractère
     */
    public static String generateUpperDigit(int length) {
        return generate(length, "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890");
    }
    
    
    
}