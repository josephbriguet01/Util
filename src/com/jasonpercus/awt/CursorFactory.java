/*
 * Copyright (C) JasonPercus Systems, Inc - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by JasonPercus, 07/2022
 */
package com.jasonpercus.awt;



import com.jasonpercus.util.Strings;



/**
 * Cette classe permet de fabriquer des cursors AWT personnalisés
 * @author JasonPercus
 * @version 1.0
 */
public class CursorFactory {

    
    
//CONSTANTS
    /**
     * Correspond à un curseur vide. Ainsi la souris disparait
     */
    public final static java.awt.Cursor BLANK_CURSOR = make("blank cursor", new java.awt.image.BufferedImage(16, 16, java.awt.image.BufferedImage.TYPE_INT_ARGB));

    
    
//CONSTRUCTOR
    /**
     * Constructeur par défaut
     * @deprecated <div style="color: #D45B5B; font-style: italic">NE PAS UTILISER</div>
     */
    @Deprecated
    private CursorFactory() {
    }
    
    
    
//METHODES PUBLICS STATICS
    /**
     * Crée un curseur personnalisé
     * @param img Correspond à l'image du curseur personnalisé
     * @return Retourne le curseur fabriqué
     */
    public static java.awt.Cursor make(java.awt.Image img){
        return make(Strings.generateLower(15), img, new java.awt.Point(0, 0));
    }
    
    /**
     * Crée un curseur personnalisé
     * @param img Correspond à l'image du curseur personnalisé
     * @param hotSpot Détermine le point de clic du curseur
     * @return Retourne le curseur fabriqué
     */
    public static java.awt.Cursor make(java.awt.Image img, java.awt.Point hotSpot){
        return make(Strings.generateLower(15), img, hotSpot);
    }
    
    /**
     * Crée un curseur personnalisé
     * @param nameCursor Correspond au nom du curseur personnalisé
     * @param img Correspond à l'image du curseur personnalisé
     * @return Retourne le curseur fabriqué
     */
    public static java.awt.Cursor make(String nameCursor, java.awt.Image img){
        return make(nameCursor, img, new java.awt.Point(0, 0));
    }
    
    /**
     * Crée un curseur personnalisé
     * @param nameCursor Correspond au nom du curseur personnalisé
     * @param img Correspond à l'image du curseur personnalisé
     * @param hotSpot Détermine le point de clic du curseur
     * @return Retourne le curseur fabriqué
     */
    public static java.awt.Cursor make(String nameCursor, java.awt.Image img, java.awt.Point hotSpot){
        return java.awt.Toolkit.getDefaultToolkit().createCustomCursor(img, hotSpot, nameCursor);
    }
    
    
    
}