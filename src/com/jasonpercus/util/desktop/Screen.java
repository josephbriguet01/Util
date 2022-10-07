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
 * Cette classe représente la taille et la position d'un moniteur
 * @author JasonPercus
 * @version 1.0
 */
public final class Screen extends java.awt.Rectangle implements Comparable<Screen> {

    
    
//ATTRIBUT
    /**
     * Correspond au moniteur pointé
     */
    private final java.awt.GraphicsDevice device;
    
    
    
//CONSTRUCTOR
    /**
     * Crée un objet {@link Screen}. Cet objet {@link Screen} pointera forcément le moniteur principal
     */
    public Screen() {
        super(java.awt.Toolkit.getDefaultToolkit().getScreenSize());
        this.device = null;
    }
    
    /**
     * Crée un objet {@link Screen}
     * @param device Correspond au moniteur pointé
     */
    public Screen(java.awt.GraphicsDevice device) {
        super(device.getDefaultConfiguration().getBounds());
        this.device = device;
    }

    
    
//METHODES PUBLICS
    /**
     * Détermine s'il s'agit de l'écran principal ou pas
     * @return Retourne true s'il s'agit de l'écran principal, sinon false
     */
    public boolean isPrimary() {
        return this.contains(1, 1);
    }
    
    /**
     * Renvoie la taille et la position d'un {@link Desktop} sur un moniteur
     * @return Retourne la taille et la position d'un {@link Desktop} sur un moniteur
     */
    public Desktop getDesktop(){
        if(this.device == null)
            return new Desktop();
        else
            return new Desktop(this.device);
    }

    /**
     * Renvoie la position relative et la taille du moniteur en partant du principe qu'il s'agisse du moniteur principal. Ce qui veut dire que les coordonnées de base sont [0;0]
     * @return Retourne la position relative et la taille du moniteur en partant du principe qu'il s'agisse du moniteur principal
     */
    public java.awt.Rectangle getRelativeScreen() {
        return new java.awt.Rectangle(new java.awt.Point(0, 0), getSize());
    }
    
    /**
     * Renvoie la surface en px d'une {@link java.awt.Window} sur ce {@link Screen}. Attention si la fenêtre est à cheval sur plusieurs écrans, seule la surface sur cet écran sera prise en compte.
     * @param window Correspond à la fenêtre dont on cherche à connaitre sa surface en px sur ce {@link Screen}
     * @return Retourne la surface en px de cette fenêtre
     */
    public int getIntersectionArea(java.awt.Window window){
        java.awt.Rectangle zone = intersection(window.getBounds());
        return zone.width * zone.height;
    }

    /**
     * Compare deux objets {@link Screen}
     * @param o Correspond au second objet à comparer au courant
     * @return Retourne le résultat de la comparaison
     */
    @Override
    public int compareTo(Screen o) {
        if (o == null) {
            return 1;
        }
        if (this.isPrimary() && !o.isPrimary()) {
            return -1;
        } else if (!this.isPrimary() && o.isPrimary()) {
            return 1;
        } else {
            return 0;
        }
    }
    
    
    
//METHODES PUBLICS STATICS
    /**
     * Renvoie un tableau de {@link Screen} connecté
     * @return Retourne un tableau de {@link Screen} connecté
     */
    public static Screen[] getScreens() {
        java.awt.GraphicsEnvironment graphicsEnvironment = java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment();
        final java.awt.GraphicsDevice[] screenDevices = graphicsEnvironment.getScreenDevices();
        Screen[] screenBounds = new Screen[screenDevices.length];
        
        for (int i = 0; i < screenDevices.length; i++) 
            screenBounds[i] = new Screen(screenDevices[i]);
        
        java.util.Arrays.sort(screenBounds);
        return screenBounds;
    }
    
    /**
     * Renvoie l'objet {@link Screen} sur lequel la fenêtre est plus présente (en terme de surface). 
     * Si plusieurs écrans se partagent la même surface, alors ce sera l'écran principal qui sera renvoyé. 
     * Maintenant si plusieurs écrans se partagent la même surface, mais qu'aucun d'eux n'est l'écran principal, alors seul le premier écran détecté comme ayant la plus grande surface avec cette fenêtre sera renvoyé.
     * @param window Correspond à la fenêtre que l'on souhaite tester
     * @return Retourne l'objet {@link Screen} sur lequel la fenêtre est plus présente (en terme de surface)
     */
    public static Screen isMoreOn(java.awt.Window window){
        Screen[] screens = getScreens();
        int area         = Integer.MIN_VALUE;
        Screen screen    = null;
        
        for(Screen s : screens){
            int intersection = s.getIntersectionArea(window);
            if(intersection >= area){
                area = intersection;
                
                if(intersection == area && !s.isPrimary())
                    continue;
                
                screen = s;
            }
        }
        
        return (area == Integer.MIN_VALUE) ? null : screen;
    }
    
    /**
     * Renvoie l'objet {@link Screen} sur lequel la fenêtre est moins présente (en terme de surface). Remarque une fenêtre possédant une surface de 0px sur un écran n'est pas considéré. 
     * Si plusieurs écrans se partagent la même surface, alors ce sera l'écran principal qui sera renvoyé. 
     * Maintenant si plusieurs écrans se partagent la même surface, mais qu'aucun d'eux n'est l'écran principal, alors seul le premier écran détecté comme ayant la plus petite surface avec cette fenêtre sera renvoyé.
     * @param window Correspond à la fenêtre que l'on souhaite tester
     * @return Retourne l'objet {@link Screen} sur lequel la fenêtre est moins présente (en terme de surface)
     */
    public static Screen isLessOn(java.awt.Window window){
        Screen[] screens = getScreens();
        int area         = Integer.MAX_VALUE;
        Screen screen    = null;
        
        for(Screen s : screens){
            int intersection = s.getIntersectionArea(window);
            if(intersection > 0 && intersection <= area){
                area = intersection;
                
                if(intersection == area && !s.isPrimary())
                    continue;
                
                screen = s;
            }
        }
        
        return (area == Integer.MAX_VALUE) ? null : screen;
    }
    
    
    
}