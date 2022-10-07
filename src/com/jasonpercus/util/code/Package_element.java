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
 * Cette classe représente les packages
 * @author JasonPercus
 * @version 1.0
 */
public class Package_element extends Code {

    
    
//CONSTRUCTORS
    /**
     * Crée un package
     */
    public Package_element() {
    }
    
    /**
     * Crée un package (ex: package java.io)
     * @param name Correspond au nom du package (ex: java.io)
     */
    protected Package_element(String name) {
        this(null, name, null);
    }
    
    /**
     * Crée un package (ex: package java.io)
     * @param creator Correspond au créateur de bout de code
     * @param name Correspond au nom du package (ex: java.io)
     * @param parent Correspond au code parent de celui-ci
     */
    protected Package_element(CodeCreator creator, String name, Code parent) {
        super(creator, name, parent);
    }

    
    
//METHODES PUBLICS
    /**
     * Renvoie la signature du package
     * @return Retourne la signature du package (ex: package java.io)
     */
    @Override
    public String getSignature() {
        return String.format("package %s", getName());
    }

    /**
     * Renvoie le package sous la forme d'une chaîne de caractères
     * @return Retourne le package sous la forme d'une chaîne de caractères (ex: package java.io;)
     */
    @Override
    public String toString() {
        if(asComment())
            return "//" + String.format("%s;", getSignature());
        else
            return String.format("%s;", getSignature());
    }
    
    
    
}