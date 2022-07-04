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
 * Cette classe représente l'élément javadoc @author
 * @author JasonPercus
 * @version 1.0
 */
public class Author_javadoc extends Element_javadoc {

    
    
//CONSTRUCTORS
    /**
     * Crée un auteur de javadoc par défaut
     */
    public Author_javadoc() {
        this("");
    }
    
    /**
     * Crée un auteur de javadoc
     * @param author Correspond à l'auteur du bout de code
     */
    public Author_javadoc(String author) {
        this(null, author, null);
    }
    
    /**
     * Crée un auteur de javadoc
     * @param creator Correspond au créateur de bout de code
     * @param author Correspond à l'auteur du bout de code
     * @param parent Correspond au code parent de celui-ci
     */
    protected Author_javadoc(CodeCreator creator, String author, Code parent) {
        super(creator, "author", author, parent);
    }
    
    
    
//GETTER & SETTER
    /**
     * Renvoie l'auteur du bloc javadoc
     * @return Retourne l'auteur du bloc javadoc
     */
    public synchronized final String getAuthor() {
        return this.getDescription();
    }

    /**
     * Modifie l'auteur du bloc javadoc
     * @param author Correspond au nouvel auteur du bout de code
     */
    public synchronized final void setAuthor(String author) {
        this.setDescription(author);
    }

    
    
//METHODE PUBLIC
    /**
     * Renvoie la priorité de l'élément par rapport aux autres dans un bloc javadoc. Plus la valeur est petite, plus la priorité est grande, et inversement plus la valeur est grande, plus la priorité d'affichage est petite
     * @return Retourne 16
     */
    @Override
    public final int priority() {
        return 16;
    }
    
    
    
}