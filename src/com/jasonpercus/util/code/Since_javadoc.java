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
 * Cette classe représente l'élément javadoc @since
 * @author JasonPercus
 * @version 1.0
 */
public class Since_javadoc extends Element_javadoc {

    
    
//CONSTRUCTORS
    /**
     * Crée un since de javadoc par défaut
     */
    public Since_javadoc() {
        this("");
    }
    
    /**
     * Crée un since de javadoc
     * @param since Correspond à la version antérieure du bout de code
     */
    public Since_javadoc(String since) {
        this(null, since, null);
    }
    
    /**
     * Crée un since de javadoc
     * @param creator Correspond au créateur de bout de code
     * @param since Correspond à la version antérieure du bout de code
     * @param parent Correspond au code parent de celui-ci
     */
    protected Since_javadoc(CodeCreator creator, String since, Code parent) {
        super(creator, "since", since, parent);
    }
    
    
    
//GETTER & SETTER
    /**
     * Renvoie le version antérieure du bloc javadoc
     * @return Retourne la version antérieure du bloc javadoc
     */
    public synchronized final String getSince() {
        return this.getDescription();
    }

    /**
     * Modifie la version antérieure du bloc javadoc
     * @param author Correspond à la nouvelle version antérieure du bout de code
     */
    public synchronized final void setSince(String author) {
        this.setDescription(author);
    }

    
    
//METHODE PUBLIC
    /**
     * Renvoie la priorité de l'élément par rapport aux autres dans un bloc javadoc. Plus la valeur est petite, plus la priorité est grande, et inversement plus la valeur est grande, plus la priorité d'affichage est petite
     * @return Retourne 18
     */
    @Override
    public final int priority() {
        return 18;
    }
    
    
    
}