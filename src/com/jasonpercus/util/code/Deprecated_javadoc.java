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
 * Cette classe représente l'élément javadoc @deprecated
 * @author JasonPercus
 * @version 1.0
 */
public class Deprecated_javadoc extends Element_javadoc {

    
    
//CONSTRUCTORS
    /**
     * Crée un bout de javadoc déprécié par défaut
     */
    public Deprecated_javadoc() {
        this("");
    }

    /**
     * Crée un bout de javadoc déprécié
     * @param description Correspond à la raison du bout de code déprécié
     */
    public Deprecated_javadoc(String description) {
        super(null, description);
    }

    /**
     * Crée un bout de javadoc déprécié
     * @param creator Correspond au créateur de bout de code
     * @param description Correspond à la raison du bout de code déprécié
     * @param parent Correspond au code parent de celui-ci
     */
    protected Deprecated_javadoc(CodeCreator creator, String description, Code parent) {
        super(creator, "deprecated", description, parent);
    }

    
    
//METHODE PUBLIC
    /**
     * Renvoie la priorité de l'élément par rapport aux autres dans un bloc javadoc. Plus la valeur est petite, plus la priorité est grande, et inversement plus la valeur est grande, plus la priorité d'affichage est petite
     * @return Retourne 20
     */
    @Override
    public int priority() {
        return 20;
    }
    
    
    
}