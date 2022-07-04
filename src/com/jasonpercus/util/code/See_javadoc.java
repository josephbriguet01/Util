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
 * Cette classe représente l'élément javadoc @see
 * @author JasonPercus
 * @version 1.0
 */
public class See_javadoc extends Element_javadoc {



//CONSTRUCTORS
    /**
     * Crée un paramètre see de javadoc par défaut
     */
    public See_javadoc() {
        this("");
    }
    
    /**
     * Crée un paramètre see de javadoc
     * @param see Correspond à ce qu'il faut consulter (ex: @see [MyOtherClass#MyOtherMethod])
     */
    public See_javadoc(String see) {
        this(null, see, null);
    }
    
    /**
     * Crée un paramètre see de javadoc
     * @param creator Correspond au créateur de bout de code
     * @param see Correspond à ce qu'il faut consulter (ex: @see [MyOtherClass#MyOtherMethod])
     * @param parent Correspond au code parent de celui-ci
     */
    protected See_javadoc(CodeCreator creator, String see, Code parent) {
        super(creator, "see", see, parent);
    }

    
    
//GETTER & SETTER
    /**
     * Renvoie ce qu'il faut consulter (ex: @see [MyOtherClass#MyOtherMethod])
     * @return Retourne ce qu'il faut consulter
     */
    public synchronized final String getSee() {
        return this.getDescription();
    }

    /**
     * Modifie ce qu'il faut consulter (ex: @see [MyOtherClass#MyOtherMethod])
     * @param see Correspond à la nouvelle [classe | méthode | ...] à consulter
     */
    public synchronized final void setSee(String see) {
        this.setDescription(see);
    }

    
    
//METHODE PUBLIC
    /**
     * Renvoie la priorité de l'élément par rapport aux autres dans un bloc javadoc. Plus la valeur est petite, plus la priorité est grande, et inversement plus la valeur est grande, plus la priorité d'affichage est petite
     * @return Retourne 8
     */
    @Override
    public int priority() {
        return 8;
    }
    
    
    
}