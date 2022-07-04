/*
 * Copyright (C) JasonPercus Systems, Inc - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by JasonPercus, 05/2021
 */
package com.jasonpercus.util.mousearea;



/**
 * Cette classe permet d'énumérer les modes possibles de dessin pour la classe {@link Zone}
 * @see Zone
 * @author JasonPercus
 * @version 1.0
 */
public enum ModeArea implements java.io.Serializable {
    
    
    
//ATTRIBUTS STATICS
    /**
     * Lorsque l'on souhaite ajouter une zone contrôlée
     */
    ADDITIVE("Adds an area that will be controlled and determined !"),
    
    /**
     * Lorsque l'on souhaite supprimer une zone contrôlée
     */
    SUBSTRACTIVE("Removes an area that will be controlled and determined");
    
    
    
//SERIAL VERSION UID
    /**
     * Correspond au numéro de série qui identifie le type de dé/sérialization utilisé pour l'objet
     */
    private static final long serialVersionUID = 1L;
    
    
    
//ATTRIBUT
    /**
     * Correspond à la description du mode
     */
    private String description = "";

    
    
//CONSTRUCTOR
    /**
     * Crée un mode additif ou soustractif de zone contrôlée
     * @param description Correspond à la description du mode
     */
    ModeArea(String description) {
        this.description = description;
    }

    
    
//METHODE PUBLIC
    /**
     * Renvoie le mode sous la forme d'une chaîne de caractères
     * @return Retourne le mode sous la forme d'une chaîne de caractères
     */
    @Override
    public String toString() {
        return description;
    }
    
    
    
}