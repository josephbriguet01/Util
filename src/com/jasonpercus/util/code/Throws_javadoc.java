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
 * Cette classe représente l'élément javadoc @throws
 * @author JasonPercus
 * @version 1.0
 */
public class Throws_javadoc extends ElementTyped_javadoc {

    
    
//CONSTRUCTORS
    /**
     * Crée une exception de javadoc par défaut
     */
    public Throws_javadoc() {
        this("", "");
    }
    
    /**
     * Crée une exception de javadoc
     * @param type Correspond au type d'exception (ex: @throws [java.io.FileNotFoundException] Le fichier n'a pas été trouvé)
     * @param description Correspond à la description de l'exception levée (ex: @throws java.io.FileNotFoundException [Le fichier n'a pas été trouvé])
     */
    public Throws_javadoc(String type, String description) {
        this(null, type, description, null);
    }
    
    /**
     * Crée une exception de javadoc
     * @param creator Correspond au créateur de bout de code
     * @param type Correspond au type d'exception (ex: @throws [java.io.FileNotFoundException] Le fichier n'a pas été trouvé)
     * @param description Correspond à la description de l'exception levée (ex: @throws java.io.FileNotFoundException [Le fichier n'a pas été trouvé])
     * @param parent Correspond au code parent de celui-ci
     */
    protected Throws_javadoc(CodeCreator creator, String type, String description, Code parent) {
        super(creator, "throws", type, description, parent);
    }

    
    
//GETTER & SETTER
    /**
     * Renvoie le type d'exception (ex: @throws [java.io.FileNotFoundException] Le fichier n'a pas été trouvé)
     * @return Retourne le type d'exception
     */
    public synchronized final String getType() {
        return this.getKey();
    }

    /**
     * Modifie le type d'exception (ex: @throws [java.io.FileNotFoundException] Le fichier n'a pas été trouvé)
     * @param type Correspond au nouveau type d'exception
     */
    public synchronized final void setType(String type) {
        this.setKey(type);
    }

    
    
//METHODE PUBLIC
    /**
     * Renvoie la priorité de l'élément par rapport aux autres dans un bloc javadoc. Plus la valeur est petite, plus la priorité est grande, et inversement plus la valeur est grande, plus la priorité d'affichage est petite
     * @return Retourne 15
     */
    @Override
    public int priority() {
        return 15;
    }
    
    
    
}