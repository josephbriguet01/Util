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
 * Cette classe permet de faire le lien direct entre la position de la souris dans un composant et les {@link Zone zones} détectables, et ce grace au carbone négatif
 * @see Zone
 * @see ModeArea
 * @author JasonPercus
 * @version 1.0
 */
public class ZoneAdapter {
    
    
    
//ATTRIBUTS
    /**
     * Correspond à la zone de détection
     */
    private Zone zone;
    
    /**
     * Correspond au composant qui est lié à la zone de détection
     */
    private final java.awt.Component component;

    
    
//CONSTRUCTOR
    /**
     * Crée un Adaptateur de {@link Zone}
     * @param zone Correspond à la zone de détection qui doit être lié au composant (ATTENTION: La zone doit faire la même taille que le composant)
     * @param component Correspond au composant qui doit être lié à la détection de zone (ATTENTION: La zone doit faire la même taille que le composant)
     */
    public ZoneAdapter(Zone zone, java.awt.Component component) {
        this.zone       = zone;
        this.component  = component;
    }

    
    
//METHODES PUBLICS
    /**
     * Renvoie la zone de détection liée au composant
     * @return Retourne la zone de détection
     */
    public Zone getZone() {
        return zone;
    }

    /**
     * Renvoie le composant lié à la zone de détection
     * @return Retourne le composant
     */
    public java.awt.Component getComponent() {
        return component;
    }

    /**
     * Modifie la zone de détection
     * @param zone Correspond à la nouvelle zone de détection qui sera liée au composant
     */
    public void setZone(Zone zone) {
        this.zone = zone;
    }
    
    /**
     * Détermine si la souris se trouve sur une zone détectable du composant
     * @see Zone#isColored(int, int)
     * @return Retourne true si la souris se trouve sur une zone détectée, sinon false
     */
    public boolean inZone(){
        return inZone(ModeArea.ADDITIVE);
    }
    
    /**
     * Détermine si la souris se trouve sur une zone détectable du composant
     * @see Zone#isColored(int, int)
     * @param mode Correspond au mode de recherche. S'il s'agit du {@link ModeArea#ADDITIVE}, la logique de détection suivra la logique de dessin des zones. En revanche, s'il s'agit du {@link ModeArea#SUBSTRACTIVE}, la logique de détection sera inversée
     * @return Retourne true si la souris se trouve sur une zone détectée, sinon false
     */
    public boolean inZone(ModeArea mode){
        java.awt.Point position = component.getMousePosition();
        boolean result;
        
        if (position.x < 0) {
            result = false;
        } else if (position.x >= zone.getWidth()) {
            result = false;
        } else {
            result = zone.isColored(position.x, position.y);
        }
        
        if(mode == ModeArea.ADDITIVE){
            return result;
        }else{
            return !result;
        }
    }
    
    
    
}