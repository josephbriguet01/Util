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
 * Cette classe représente l'élément javadoc @serialField
 * @author JasonPercus
 * @version 1.0
 */
public class SerialField_javadoc extends ElementTyped_javadoc {
    
    
    
//CONSTRUCTORS
    /**
     * Crée un paramètre serialField de javadoc par défaut
     */
    public SerialField_javadoc() {
        this("", "");
    }
    
    /**
     * Crée un paramètre serialField de javadoc
     * @param field Correspond au nom du paramètre (ex: @serialField [myVar] La description)
     * @param description Correspond à la description du paramètre (ex: @serialField myVar [La description])
     */
    public SerialField_javadoc(String field, String description) {
        this(null, field, description, null);
    }
    
    /**
     * Crée un paramètre serialField de javadoc
     * @param creator Correspond au créateur de bout de code
     * @param field Correspond au nom du paramètre (ex: @serialField [myVar] La description)
     * @param description Correspond à la description du paramètre (ex: @serialField myVar [La description])
     * @param parent Correspond au code parent de celui-ci
     */
    protected SerialField_javadoc(CodeCreator creator, String field, String description, Code parent) {
        super(creator, "serialField", field, description, parent);
    }

    
    
//GETTER & SETTER
    /**
     * Renvoie le nom du paramètre (ex: @serialField [myVar] La description)
     * @return Retourne le nom du paramètre
     */
    public synchronized final String getField() {
        return this.getKey();
    }

    /**
     * Modifie le nom du paramètre (ex: @serialField [myVar] La description)
     * @param field Correspond au nouveau nom du paramètre
     */
    public synchronized final void setField(String field) {
        this.setKey(field);
    }

    
    
//METHODE PUBLIC
    /**
     * Renvoie la priorité de l'élément par rapport aux autres dans un bloc javadoc. Plus la valeur est petite, plus la priorité est grande, et inversement plus la valeur est grande, plus la priorité d'affichage est petite
     * @return Retourne 10
     */
    @Override
    public int priority() {
        return 10;
    }
    
    
    
}