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
 * Cette classe permet de créer des chaînes de caractère hexadécimale.
 * @author Briguet
 * @version 1.0
 */
public class ChainHexadecimal implements java.io.Serializable, Comparable<ChainHexadecimal>, Cloneable, CharSequence {
    
    
    
    // <editor-fold defaultstate="collapsed" desc="SERIAL_VERSION_UID">
    /**
     * Correspond au numéro de série qui identifie le type de dé/sérialization utilisé pour l'objet
     */
    private static final long serialVersionUID = 1L;
    // </editor-fold>
    
    
    
    // <editor-fold defaultstate="collapsed" desc="ATTRIBUT">
    /**
     * Correspond à la chaîne de caractères hexadécimaux
     */
    private String chain;
    // </editor-fold>

    

    // <editor-fold defaultstate="collapsed" desc="CONSTRUCTORS">
    /**
     * Crée une chaîne hexadécimale de caractères
     */
    public ChainHexadecimal() {
        this.chain = "";
    }
    
    /**
     * Crée une chaîne hexadécimale de caractères
     * @param chainHexadecimal Correspond à une chaine de caractères
     * @throws InvalidCharacterException Correspond à l'exception levée si la chaine comprend un caractère qui n'est pas compris entre 0 et 9 compris ou a et f compris.
     */
    public ChainHexadecimal(String chainHexadecimal) throws InvalidCharacterException{
        this.chain = "";
        if(chainHexadecimal == null) chain = "";
        else{
            String tempo = "";
            for(int i=0;i<chainHexadecimal.length();i++){
                CharHexadecimal ch = new CharHexadecimal(chainHexadecimal.charAt(i));
                tempo += ch;
            }
            this.chain = tempo;
        }
    }
    
    /**
     * Crée une chaîne hexadécimale de caractères
     * @param listCharHexadecimal Correspond à la liste de caractères hexadécimaux.
     */
    public ChainHexadecimal(java.util.List<CharHexadecimal> listCharHexadecimal){
        this.chain = "";
        if(listCharHexadecimal == null) throw new java.lang.NullPointerException();
        for(int i=0;i<listCharHexadecimal.size();i++){
            CharHexadecimal ch = listCharHexadecimal.get(i);
            this.chain += ch;
        }
    }
    // </editor-fold>
    
    
    
    // <editor-fold defaultstate="collapsed" desc="METHODES PUBLICS">
    /**
     * Renvoie la chaîne hexadécimale
     * @return Retourne la chaîne hexadécimale
     */
    public String getChain() {
        return chain;
    }

    /**
     * Modifie la chaîne de caractères hexadécimaux
     * @param chain Correspond à la nouvelle chaîne
     * @throws InvalidCharacterException Correspond à l'exception levée si la chaine comprend un caractère qui n'est pas compris entre 0 et 9 compris ou a et f compris.
     */
    public void setChain(String chain) throws InvalidCharacterException {
        if(chain == null) this.chain = "";
        else{
            String tempo = "";
            for(int i=0;i<chain.length();i++){
                CharHexadecimal ch = new CharHexadecimal(chain.charAt(i));
                tempo += ch;
            }
            this.chain = tempo;
        }
    }

    /**
     * Renvoie la liste des caractères hexadécimaux
     * @return Retourne la liste des caractères hexadécimaux
     */
    public java.util.List<CharHexadecimal> getListCharHexadecimal(){
        java.util.List<CharHexadecimal> chs = new java.util.ArrayList<>();
        for(int i=0;i<chain.length();i++){
            chs.add(new CharHexadecimal(chain.charAt(i)));
        }
        return chs;
    }
    
    /**
     * Modifie la chaîne de caractères hexadécimaux
     * @param listCharHexadecimal Correspond à une liste de caractère hexadécimaux
     */
    public void setListCharHexadecimal(java.util.List<CharHexadecimal> listCharHexadecimal){
        if(listCharHexadecimal == null) throw new java.lang.NullPointerException();
        for(int i=0;i<listCharHexadecimal.size();i++){
            CharHexadecimal ch = listCharHexadecimal.get(i);
            this.chain += ch;
        }
    }
    
    /**
     * Renvoie la chaîne de caractères hexadécimaux
     * @return Retourne la chaîne de caractères hexadécimaux
     */
    @Override
    public String toString() {
        return chain;
    }
    
    /**
     * Renvoie le résultat de la méthode compareTo()
     * @param ch Correspond à l'objet à comparer
     * @return Retourne le résultat de la méthode compareTo()
     */
    @Override
    public int compareTo(ChainHexadecimal ch) {
        return chain.compareTo(ch.toString());
    }

    /**
     * Renvoie le résultat de la méthode hashCode()
     * @return Retourne le résultat de la méthode hashCode()
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + java.util.Objects.hashCode(this.chain);
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
        final ChainHexadecimal other = (ChainHexadecimal) obj;
        return java.util.Objects.equals(this.chain, other.chain);
    }

    /**
     * Renvoie un objet clone de celui-ci
     * @return Retourne un objet clone de celui-ci
     * @throws java.lang.CloneNotSupportedException Correspond à l'exception levée si le clonnage se déroule mal
     */
    @Override
    @SuppressWarnings("CloneDoesntCallSuperClone")
    public Object clone() throws CloneNotSupportedException {
        return new ChainHexadecimal(chain);
    }

    /**
     * Renvoie la longueur de la chaîne de caractères
     * @return Retourne la longueur de la chaîne de caractères
     */
    @Override
    public int length() {
        return chain.length();
    }

    /**
     * Renvoie le nième caractère de la chaîne
     * @param i Correspond à l'indice du caractère à récupérer
     * @return Retourne le caractère à la position i
     */
    @Override
    public char charAt(int i) {
        return chain.charAt(i);
    }

    /**
     * Renvoie une sous-chaîne de caractères compris entre i et i1
     * @param i Correspond à l'indice de départ de la sous-chaîne
     * @param i1 Correspond à l'indice de fin de la sous-chaîne
     * @return Retourne une chaîne de caractères
     */
    @Override
    public CharSequence subSequence(int i, int i1) {
        return chain.subSequence(i, i1);
    }
    // </editor-fold>



}