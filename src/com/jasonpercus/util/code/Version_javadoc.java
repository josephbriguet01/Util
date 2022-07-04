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
 * Cette classe représente l'élément javadoc @version
 * @author JasonPercus
 * @version 1.0
 */
public class Version_javadoc extends Element_javadoc {

    
    
//CONSTRUCTORS
    /**
     * Crée une version de javadoc par défaut
     */
    public Version_javadoc() {
        this("");
    }
    
    /**
     * Crée une version de javadoc
     * @param version Correspond à la version du bout de code
     */
    public Version_javadoc(String version) {
        this(null, version, null);
    }
    
    /**
     * Crée une version de javadoc
     * @param creator Correspond au créateur de bout de code
     * @param version Correspond à la version du bout de code
     * @param parent Correspond au code parent de celui-ci
     */
    protected Version_javadoc(CodeCreator creator, String version, Code parent) {
        super(creator, "version", version, parent);
    }
    
    
    
//GETTER & SETTER
    /**
     * Renvoie le version du bloc javadoc
     * @return Retourne la version du bloc javadoc
     */
    public synchronized final String getVersion() {
        return this.getDescription();
    }

    /**
     * Modifie la version du bloc javadoc
     * @param author Correspond à la nouvelle version du bout de code
     */
    public synchronized final void setVersion(String author) {
        this.setDescription(author);
    }

    
    
//METHODE PUBLIC
    /**
     * Renvoie la priorité de l'élément par rapport aux autres dans un bloc javadoc. Plus la valeur est petite, plus la priorité est grande, et inversement plus la valeur est grande, plus la priorité d'affichage est petite
     * @return Retourne 17
     */
    @Override
    public final int priority() {
        return 17;
    }
    
    
    
}