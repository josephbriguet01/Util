/*
 * Copyright (C) JasonPercus Systems, Inc - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by JasonPercus, 06/2021
 */
package com.jasonpercus.util.code;



/**
 * Cette classe représente l'élément javadoc @serial
 * @author JasonPercus
 * @version 1.0
 */
public class Serial_javadoc extends ElementTyped_javadoc {
    
    
    
//CONSTRUCTORS
    /**
     * Crée un paramètre serial de javadoc par défaut
     */
    public Serial_javadoc() {
        this("", "");
    }
    
    /**
     * Crée un paramètre serial de javadoc
     * @param field Correspond au nom du paramètre (ex: @serial [myVar] La description)
     * @param description Correspond à la description du paramètre (ex: @serial myVar [La description])
     */
    public Serial_javadoc(String field, String description) {
        this(null, field, description, null);
    }
    
    /**
     * Crée un paramètre serial de javadoc
     * @param creator Correspond au créateur de bout de code
     * @param field Correspond au nom du paramètre (ex: @serial [myVar] La description)
     * @param description Correspond à la description du paramètre (ex: @serial myVar [La description])
     * @param parent Correspond au code parent de celui-ci
     */
    protected Serial_javadoc(CodeCreator creator, String field, String description, Code parent) {
        super(creator, "serial", field, description, parent);
    }

    
    
//GETTER & SETTER
    /**
     * Renvoie le nom du paramètre (ex: @serial [myVar] La description)
     * @return Retourne le nom du paramètre
     */
    public synchronized final String getField() {
        return this.getKey();
    }

    /**
     * Modifie le nom du paramètre (ex: @serial [myVar] La description)
     * @param field Correspond au nouveau nom du paramètre
     */
    public synchronized final void setField(String field) {
        this.setKey(field);
    }

    
    
//METHODE PUBLIC
    /**
     * Renvoie la priorité de l'élément par rapport aux autres dans un bloc javadoc. Plus la valeur est petite, plus la priorité est grande, et inversement plus la valeur est grande, plus la priorité d'affichage est petite
     * @return Retourne 9
     */
    @Override
    public int priority() {
        return 9;
    }
    
    
    
}