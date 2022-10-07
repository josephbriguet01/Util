/*
 * Copyright (C) JasonPercus Systems, Inc - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by JasonPercus, 07/2022
 */
package com.jasonpercus.util.desktop;



/**
 * Cette classe représente la taille et la position d'un bureau (sans la barre des tâches,...) sur un moniteur
 * @author JasonPercus
 * @version 1.0
 */
public final class Desktop extends java.awt.Rectangle implements Comparable<Desktop> {

    
    
//ATTRIBUTS
    /**
     * Correspond à la taille et la position d'un moniteur
     */
    private Screen screen;
    
    /**
     * Correspond à la position relative et la taille du bureau en partant du principe qu'il s'agisse du bureau principal. Ce qui veut dire que les coordonnées de base sont [0;0]
     */
    private java.awt.Rectangle relativeDesktop;
    
    
    
//CONSTRUCTORS
    /**
     * Crée un objet {@link Desktop}. Cet objet {@link Desktop} sera forcément sur le moniteur principal
     */
    public Desktop() {
        super(java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds());
    }
    
    /**
     * Crée un objet {@link Desktop} d'un moniteur
     * @param device Correspond au moniteur pointé
     */
    public Desktop(java.awt.GraphicsDevice device) {
        super();
        java.awt.Insets insets = java.awt.Toolkit.getDefaultToolkit().getScreenInsets(device.getDefaultConfiguration());
        this.screen            = new Screen(device);
        this.x                 = screen.x + insets.left;
        this.y                 = screen.y + insets.top;
        this.width             = screen.width  - insets.left - insets.right;
        this.height            = screen.height - insets.top  - insets.bottom;
        this.relativeDesktop  = new java.awt.Rectangle(new java.awt.Point(insets.left, insets.top), new java.awt.Dimension(this.width, this.height));
    }

    
    
//METHODES PUBLICS
    /**
     * Détermine s'il s'agit du bureau principal ou pas
     * @return Retourne true s'il s'agit du bureau principal, sinon false
     */
    public boolean isPrimary() {
        return this.screen.isPrimary();
    }
    
    /**
     * Renvoie la taille et la position d'un moniteur
     * @return Retourne la taille et la position d'un moniteur
     */
    public Screen getScreen() {
        return screen;
    }

    /**
     * Renvoie la position relative et la taille du bureau en partant du principe qu'il s'agisse du bureau principal. Ce qui veut dire que les coordonnées de base sont [0;0]
     * @return Retourne la position relative et la taille du bureau en partant du principe qu'il s'agisse du bureau principal
     */
    public java.awt.Rectangle getRelativeDesktop() {
        return relativeDesktop;
    }

    /**
     * Compare deux objets {@link Desktop}
     * @param o Correspond au second objet à comparer au courant
     * @return Retourne le résultat de la comparaison
     */
    @Override
    public int compareTo(Desktop o) {
        return this.screen.compareTo(o.screen);
    }
    
    
    
//METHODE PUBLIC STATIC
    /**
     * Renvoie un tableau de {@link Desktop}
     * @return Retourne un tableau de {@link Desktop}
     */
    public static Desktop[] getDesktops() {
        java.awt.GraphicsEnvironment graphicsEnvironment = java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment();
        final java.awt.GraphicsDevice[] screenDevices = graphicsEnvironment.getScreenDevices();
        Desktop[] screenBounds = new Desktop[screenDevices.length];
        
        for (int i = 0; i < screenDevices.length; i++) 
            screenBounds[i] = new Desktop(screenDevices[i]);
        
        java.util.Arrays.sort(screenBounds);
        return screenBounds;
    }
    
    
    
}