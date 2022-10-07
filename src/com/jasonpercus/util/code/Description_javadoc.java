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
 * Cette classe représente l'élément de description de la javadoc
 * @author JasonPercus
 * @version 1.0
 */
public class Description_javadoc extends Element_javadoc {

    
    
//CONSTRUCTORS
    /**
     * Crée une description par défaut d'un bloc javadoc
     */
    public Description_javadoc() {
        this("");
    }

    /**
     * Crée une description d'un bloc javadoc
     * @param description Correspond à la description d'un bloc javadoc
     */
    public Description_javadoc(String description) {
        this(null, description, null);
    }

    /**
     * Crée une description d'un bloc javadoc
     * @param creator Correspond au créateur de bout de code
     * @param description Correspond à la description d'un bloc javadoc
     * @param parent Correspond au code parent de celui-ci
     */
    protected Description_javadoc(CodeCreator creator, String description, Code parent) {
        super(creator, "", description, null);
    }

    
    
//METHODE PUBLIC
    /**
     * Renvoie la priorité de l'élément par rapport aux autres dans un bloc javadoc. Plus la valeur est petite, plus la priorité est grande, et inversement plus la valeur est grande, plus la priorité d'affichage est petite
     * @return Retourne 0
     */
    @Override
    public int priority() {
        return 0;
    }
    
    
    
}