/*
 * Copyright (C) JasonPercus Systems, Inc - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by JasonPercus, 06/2018
 */
package com.jasonpercus.network;



/**
 * Cette classe représente le type hexadécimal.
 * @author Briguet
 * @version 1.0
 */
public final class CharHexadecimal implements java.io.Serializable, Comparable<CharHexadecimal>, Cloneable {
    
    
    
    // <editor-fold defaultstate="collapsed" desc="SERIAL_VERSION_UID">
    /**
     * Correspond au numéro de série qui identifie le type de dé/sérialization utilisé pour l'objet
     */
    private static final long serialVersionUID = 1L;
    // </editor-fold>
    
    
    
    // <editor-fold defaultstate="collapsed" desc="ATTRIBUT">
    /**
     * Correspond à la chaîne de caractères qui représente l'objet hexadécimal
     */
    private String hexadecimal;
    // </editor-fold>

    
    
    // <editor-fold defaultstate="collapsed" desc="CONSTRUCTORS">
    /**
     * Crée un objet CharHexadecimal à 0
     */
    public CharHexadecimal() {
        this.hexadecimal = "0";
    }

    /**
     * Crée un objet CharHexadecimal
     * @param dec Correspond à l'entier décimal qui représente le nombre hexadécimal
     * @throws InvalidNumberException Correspond à l'exception levée si le nombre n'est pas compris entre 0 et 15 compris.
     */
    public CharHexadecimal(int dec) throws InvalidNumberException {
        this.hexadecimal = "0";
        this.hexadecimal = ""+convert(dec);
    }
    
    /**
     * Crée un objet CharHexadecimal
     * @param hex Correspond au caractère hexadécimal qui représente cet objet.
     * @throws InvalidCharacterException Correspond à l'exception levée si le caractère n'est pas compris entre 0 et 9 compris ou a et f compris.
     * @throws InvalidNumberException Correspond à l'exception levée si le nombre n'est pas compris entre 0 et 15 compris.
     */
    public CharHexadecimal(char hex) throws InvalidCharacterException, InvalidNumberException {
        this.hexadecimal = "0";
        this.hexadecimal = ""+convert(convert(hex));
    }
    // </editor-fold>

    
    
    // <editor-fold defaultstate="collapsed" desc="METHODES PUBLICS">
    /**
     * Renvoie en chaine de caractère la valeur hexadécimale
     * @return Retourne en chaîne de caractères la valeur hexadécimale
     */
    public String getHexadecimal() {
        return hexadecimal;
    }

    /**
     * Modifie la valeur de l'objet hexadécimal
     * @param hexadecimal Correspond au caractère hexadécimal qui représente cet objet.
     * @throws InvalidCharacterException Correspond à l'exception levée si le caractère n'est pas compris entre 0 et 9 compris ou a et f compris.
     * @throws InvalidNumberException Correspond à l'exception levée si le nombre n'est pas compris entre 0 et 15 compris.
     */
    public void setHexadecimal(String hexadecimal) throws InvalidCharacterException, InvalidNumberException {
        this.hexadecimal = "0";
        if(hexadecimal == null) hexadecimal = "0";
        if(hexadecimal.length() == 0 || hexadecimal.length() > 1) throw new InvalidCharacterException("The string must be 1 character");
        this.hexadecimal = ""+convert(convert(hexadecimal.charAt(0)));
    }
    
    /**
     * Renvoie en chaine de caractère la valeur décimale
     * @return Retourne en chaine de caractère la valeur hexadécimale
     */
    public int getDecimal(){
        return convert(hexadecimal.charAt(0));
    }
    
    /**
     * Modifie la valeur de l'objet hexadécimal
     * @param decimal Correspond à l'entier décimal qui représente le nombre hexadécimal
     * @throws InvalidNumberException Correspond à l'exception levée si le nombre n'est pas compris entre 0 et 15 compris.
     */
    public void setDecimal(int decimal) throws InvalidNumberException {
        this.hexadecimal = "0";
        this.hexadecimal = ""+convert(decimal);
    }
    
    /**
     * Clone l'objet hexadécimal
     * @return Retourne le clone
     * @throws java.lang.CloneNotSupportedException Correspond à l'exception levée si le clonnage se déroule mal
     */
    @Override
    @SuppressWarnings({"CloneDoesntCallSuperClone", "CloneDeclaresCloneNotSupported"})
    public Object clone() throws CloneNotSupportedException {
        return new CharHexadecimal(hexadecimal.charAt(0));
    }
    
    /**
     * Renvoie sous forme de chaîne de caractères l'objet hexadécimal
     * @return Retourne sous forme de chaîne de caractères l'objet hexadécimal
     */
    @Override
    public String toString() {
        return hexadecimal;
    }

    /**
     * Renvoie le résultat de la méthode hashCode()
     * @return Retourne le résultat de la méthode hashCode()
     */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 47 * hash + java.util.Objects.hashCode(this.hexadecimal);
        return hash;
    }

    /**
     * Renvoie le résultat de la méthode equals()
     * @param obj Correspond à l'objet à comparer
     * @return Retourne le résultat de la méthode equals()
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CharHexadecimal other = (CharHexadecimal) obj;
        return java.util.Objects.equals(this.hexadecimal, other.hexadecimal);
    }

    /**
     * Renvoie le résultat de la méthode compareTo()
     * @param ch Correspond à l'objet à comparer
     * @return Retourne le résultat de la méthode compareTo()
     */
    @Override
    public int compareTo(CharHexadecimal ch) {
        return toString().compareTo(ch.toString());
    }
    // </editor-fold>
    
    
    
    // <editor-fold defaultstate="collapsed" desc="METHODES PRIVATES">
    /**
     * Converti un nombre décimal en caractère hexadécimal
     * @param dec Correspond au nombre décimal
     * @return Retourne le caractère hexadécimal
     * @throws InvalidNumberException Correspond à l'exception levée si le nombre n'est pas compris entre 0 et 15 compris.
     */
    private static char convert(int dec) throws InvalidNumberException {
        if(dec<0 || 15<dec) throw new InvalidNumberException("Number ("+dec+") not authorized");
        else{
            if(0<=dec && dec<=9) return (""+dec).charAt(0);
            else{
                switch(dec){
                    case 10: return 'a';
                    case 11: return 'b';
                    case 12: return 'c';
                    case 13: return 'd';
                    case 14: return 'e';
                    case 15: return 'f';
                    default: return '0';
                }
            }
        }
    }
    
    /**
     * Converti un caractère hexadécimal en nombre décimal
     * @param hex Correspond au caractère hexadécimal
     * @return Retourne le nombre décimal
     * @throws InvalidCharacterException Correspond à l'exception levée si le caractère n'est pas compris entre 0 et 9 compris ou a et f compris.
     */
    private static int convert(char hex) throws InvalidCharacterException {
        hex = (""+hex).toLowerCase().charAt(0);
        String str = ""+hex;
        java.util.regex.Pattern p = java.util.regex.Pattern.compile("[0-9a-fA-F]{1}");
        java.util.regex.Matcher matcher = p.matcher(str);
        if(!matcher.find()) throw new InvalidCharacterException("Character ("+hex+") not authorized");
        else{
            switch(hex){
                case '0':
                    return 0;
                case '1':
                    return 1;
                case '2':
                    return 2;
                case '3':
                    return 3;
                case '4':
                    return 4;
                case '5':
                    return 5;
                case '6':
                    return 6;
                case '7':
                    return 7;
                case '8':
                    return 8;
                case '9':
                    return 9;
                case 'a':
                    return 10;
                case 'b':
                    return 11;
                case 'c':
                    return 12;
                case 'd':
                    return 13;
                case 'e':
                    return 14;
                case 'f':
                    return 15;
                default:
                    return 0;
            }
        }
    }
    // </editor-fold>
    
    
    
}