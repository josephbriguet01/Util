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
 * Cette classe représente un tag inconnu d'un bloc javadoc
 * @author JasonPercus
 * @version 1.0
 */
public class Unknown_javadoc extends Element_javadoc {

    
    
//CONSTRUCTORS
    /**
     * Crée un tag inconnu de javadoc par défaut
     */
    public Unknown_javadoc() {
        this("", "");
    }
    
    /**
     * Crée un tag inconnu de javadoc
     * @param nameTag Correspond au nom du tag créé (ex: @MyTag)
     * @param description Correspond à la description du tag
     */
    public Unknown_javadoc(String nameTag, String description) {
        this(null, nameTag, description, null);
    }
    
    /**
     * Crée un tag inconnu de javadoc
     * @param creator Correspond au créateur de bout de code
     * @param nameTag Correspond au nom du tag créé (ex: @MyTag)
     * @param description Correspond à la description du tag
     * @param parent Correspond au code parent de celui-ci
     */
    protected Unknown_javadoc(CodeCreator creator, String nameTag, String description, Code parent) {
        super(creator, nameTag, description, parent);
    }

    
    
//METHODE PUBLIC
    /**
     * Renvoie la priorité de l'élément par rapport aux autres dans un bloc javadoc. Plus la valeur est petite, plus la priorité est grande, et inversement plus la valeur est grande, plus la priorité d'affichage est petite
     * @return Retourne 19
     */
    @Override
    public final int priority() {
        return 19;
    }
    
    
    
}