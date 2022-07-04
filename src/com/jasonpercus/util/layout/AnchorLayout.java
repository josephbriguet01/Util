/*
 * Copyright (C) JasonPercus Systems, Inc - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by JasonPercus, 06/2021
 */
package com.jasonpercus.util.layout;



/**
 * Un AnchorLayout permet de disposer 9 composants dans un layout selon les 8 points cardinaux et le centre. On pourrait imaginer qu'il s'agit donc d'une grille de 3x3. Mais il n'en ai rien ! Car le layout positionne les composants selons leur taille et leur ancre
 * @author JasonPercus
 * @version 1.0
 */
public class AnchorLayout implements java.awt.LayoutManager2, java.io.Serializable {

    
    
//CONSTANTES
    /**
     * Ancre au centre du layout
     */
    public static final String CENTER      = "Center";
    
    /**
     * Ancre à l'ouest du layout
     */
    public static final String WEST        = "West";
    
    /**
     * Ancre à l'est du layout
     */
    public static final String EAST        = "East";
    
    /**
     * Ancre au nord du layout
     */
    public static final String NORTH       = "North";
    
    /**
     * Ancre au nord-est du layout
     */
    public static final String NORTHEAST   = "NorthEast";
    
    /**
     * Ancre au nord-ouest du layout
     */
    public static final String NORTHWEST   = "NorthWest";
    
    /**
     * Ancre au sud du layout
     */
    public static final String SOUTH       = "South";
    
    /**
     * Ancre au sud-est du layout
     */
    public static final String SOUTHEAST   = "SouthEast";
    
    /**
     * Ancre au sud-ouest du layout
     */
    public static final String SOUTHWEST   = "SouthWest";
    
    
    
//ATTRIBUTS
    /**
     * Correspond au composant ancré au centre du layout
     */
    private java.awt.Component center;
    
    /**
     * Correspond au composant ancré au nord du layout
     */
    private java.awt.Component north;
    
    /**
     * Correspond au composant ancré au nord-est du layout
     */
    private java.awt.Component north_east;
    
    /**
     * Correspond au composant ancré l'est du layout
     */
    private java.awt.Component east;
    
    /**
     * Correspond au composant ancré au sud-est du layout
     */
    private java.awt.Component south_east;
    
    /**
     * Correspond au composant ancré au sud du layout
     */
    private java.awt.Component south;
    
    /**
     * Correspond au composant ancré au sud-ouest du layout
     */
    private java.awt.Component south_west;
    
    /**
     * Correspond au composant ancré à l'ouest du layout
     */
    private java.awt.Component west;
    
    /**
     * Correspond au composant ancré au nord-ouest du layout
     */
    private java.awt.Component north_west;

    
    
//CONSTRUCTOR
    /**
     * Construit un AnchorLayout
     */
    public AnchorLayout() {
        super();
    }
    
    
    
//METHODES PUBLICS
    /**
     * Renvoie la contrainte liée à un composant enfant du layout
     * @param comp Correspond au composant enfant du layout dont on chercher à récupérer la contrainte
     * @return Retourne la contrainte liée à un composant enfant du layout
     */
    public Object getConstraints(java.awt.Component comp) {
        if (comp == null)
            return null;
        
        if (comp == center)
            return CENTER;
        
        else if (comp == north)
            return NORTH;
        
        else if (comp == north_east)
            return NORTHEAST;
        
        else if (comp == east)
            return EAST;
        
        else if (comp == south_east)
            return SOUTHEAST;
        
        else if (comp == south)
            return SOUTH;
        
        else if (comp == south_west)
            return SOUTHWEST;
        
        else if (comp == west)
            return WEST;
        
        else if (comp == north_west)
            return NORTHWEST;
        
        return null;
    }
    
    /**
     * Ajoute un composant au layout selon une ancre
     * @param comp Correspond au composant à ajouter au layout
     * @param constraints Correspond à l'ancre
     */
    @Override
    public void addLayoutComponent(java.awt.Component comp, Object constraints) {
        synchronized (comp.getTreeLock()) {
            if ((constraints == null) || (constraints instanceof String)) {
                addLayoutComponent((String) constraints, comp);
            } else {
                throw new IllegalArgumentException("cannot add to layout: constraint must be a string (or null)");
            }
        }
    }

    /**
     * Ajoute un composant au layout selon une ancre
     * @param name Correspond au nom de l'ancre
     * @param comp Correspond au composant à ajouter au layout
     * @deprecated <div style="color: #D45B5B; font-style: italic">Remplacé par {@link #addLayoutComponent(java.awt.Component, java.lang.Object)}</div>
     */
    @Override
    public void addLayoutComponent(String name, java.awt.Component comp) {
        synchronized (comp.getTreeLock()) {
            if (name == null)
                name = CENTER;

            switch (name) {
                case CENTER:
                    center = comp;
                    break;
                case NORTH:
                    north = comp;
                    break;
                case NORTHEAST:
                    north_east = comp;
                    break;
                case EAST:
                    east = comp;
                    break;
                case SOUTHEAST:
                    south_east = comp;
                    break;
                case SOUTH:
                    south = comp;
                    break;
                case SOUTHWEST:
                    south_west = comp;
                    break;
                case WEST:
                    west = comp;
                    break;
                case NORTHWEST:
                    north_west = comp;
                    break;
                default:
                    throw new IllegalArgumentException("cannot add to layout: unknown constraint: " + name);
            }
        }
    }

    /**
     * Enlève un composant du layout
     * @param comp Correspond au composant à enlever du layout
     */
    @Override
    public void removeLayoutComponent(java.awt.Component comp) {
        synchronized (comp.getTreeLock()) {
            if (comp == center) {
                center = null;
            } else if (comp == north) {
                north = null;
            } else if (comp == north_east) {
                north_east = null;
            } else if (comp == east) {
                east = null;
            } else if (comp == south_east) {
                south_east = null;
            } else if (comp == south) {
                south = null;
            } else if (comp == south_west) {
                south_west = null;
            } else if (comp == west) {
                west = null;
            } else if (comp == north_west) {
                north_west = null;
            }
        }
    }

    /**
     * Renvoie l'alignement le long de l'axe x. Ceci spécifie comment le composant aimerait être aligné par rapport aux autres composants. La valeur doit être un nombre compris entre 0 et 1 où 0 représente l'alignement le long de l'origine, 1 est aligné le plus loin de l'origine, 0,5 est centré, etc
     * @param parent Correspond au container qui possède ce layout
     * @return Retourne l'alignement le long de l'axe x
     */
    @Override
    public float getLayoutAlignmentX(java.awt.Container parent) {
        return 0;
    }

    /**
     * Renvoie l'alignement le long de l'axe y. Ceci spécifie comment le composant aimerait être aligné par rapport aux autres composants. La valeur doit être un nombre compris entre 0 et 1 où 0 représente l'alignement le long de l'origine, 1 est aligné le plus loin de l'origine, 0,5 est centré, etc
     * @param parent Correspond au container qui possède ce layout
     * @return Retourne l'alignement le long de l'axe y
     */
    @Override
    public float getLayoutAlignmentY(java.awt.Container parent) {
        return 0;
    }

    /**
     * Invalide la mise en page, indiquant que si le gestionnaire de mise en page a des informations mises en cache, elles doivent être supprimées
     * @param parent Correspond au container qui possède ce layout
     */
    @Override
    public void invalidateLayout(java.awt.Container parent) {
        
    }

    /**
     * Renvoie la taille minimale que peut avoir le layout
     * @param parent Correspond au container qui possède ce layout
     * @return Retourne la taille minimale que peut avoir le layout
     */
    @Override
    public java.awt.Dimension minimumLayoutSize(java.awt.Container parent) {
        return new java.awt.Dimension(0, 0);
    }

    /**
     * Renvoie la taille maximale que peut avoir le layout
     * @param parent Correspond au container qui possède ce layout
     * @return Retourne la taille maximale que peut avoir le layout
     */
    @Override
    public java.awt.Dimension maximumLayoutSize(java.awt.Container parent) {
        return new java.awt.Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE);
    }

    /**
     * Renvoie la taille préférée que peut avoir le layout
     * @param parent Correspond au container qui possède ce layout
     * @return Retourne la taille préférée que peut avoir le layout
     */
    @Override
    public java.awt.Dimension preferredLayoutSize(java.awt.Container parent) {
        synchronized (parent.getTreeLock()) {
            int line0 = 0;
            int line1 = 0;
            int line2 = 0;
            int column0 = 0;
            int column1 = 0;
            int column2 = 0;
            if(north_west != null){
                line0 += north_west.getPreferredSize().width;
                column0 += north_west.getPreferredSize().height;
            }
            if(north != null){
                line0 += north.getPreferredSize().width;
                column1 += north.getPreferredSize().height;
            }
            if(north_east != null){
                line0 += north_east.getPreferredSize().width;
                column2 += north_east.getPreferredSize().height;
            }
            if(west != null){
                line1 += west.getPreferredSize().width;
                column0 += west.getPreferredSize().height;
            }
            if(center != null){
                line1 += center.getPreferredSize().width;
                column1 += center.getPreferredSize().height;
            }
            if(east != null){
                line1 += east.getPreferredSize().width;
                column2 += east.getPreferredSize().height;
            }
            if(south_west != null){
                line2 += south_west.getPreferredSize().width;
                column0 += south_west.getPreferredSize().height;
            }
            if(south != null){
                line2 += south.getPreferredSize().width;
                column1 += south.getPreferredSize().height;
            }
            if(south_east != null){
                line2 += south_east.getPreferredSize().width;
                column2 += south_east.getPreferredSize().height;
            }
            
            int width = Math.max(line0, Math.max(line1, line2));
            int height = Math.max(column0, Math.max(column1, column2));
            
            return new java.awt.Dimension(width, height);
        }
    }

    /**
     * Dispose le conteneur spécifié
     * @param parent Correspond au container qui possède ce layout
     */
    @Override
    public void layoutContainer(java.awt.Container parent) {
        synchronized (parent.getTreeLock()) {
            int width = parent.getWidth();
            int height = parent.getHeight();
            int wChild;
            int hChild;
            if(north_west != null){
                wChild = north_west.getPreferredSize().width;
                hChild = north_west.getPreferredSize().height;
                north_west.setBounds(0, 0, wChild, hChild);
            }
            if(north != null){
                wChild = north.getPreferredSize().width;
                hChild = north.getPreferredSize().height;
                north.setBounds((width / 2) - (wChild / 2), 0, wChild, hChild);
            }
            if(north_east != null){
                wChild = north_east.getPreferredSize().width;
                hChild = north_east.getPreferredSize().height;
                north_east.setBounds(width - wChild, 0, wChild, hChild);
            }
            if(west != null){
                wChild = west.getPreferredSize().width;
                hChild = west.getPreferredSize().height;
                west.setBounds(0, (height / 2) - (hChild / 2), wChild, hChild);
            }
            if(center != null){
                wChild = center.getPreferredSize().width;
                hChild = center.getPreferredSize().height;
                center.setBounds((width / 2) - (wChild / 2), (height / 2) - (hChild / 2), wChild, hChild);
            }
            if(east != null){
                wChild = east.getPreferredSize().width;
                hChild = east.getPreferredSize().height;
                east.setBounds(width - wChild, (height / 2) - (hChild / 2), wChild, hChild);
            }
            if(south_west != null){
                wChild = south_west.getPreferredSize().width;
                hChild = south_west.getPreferredSize().height;
                south_west.setBounds(0, height - hChild, wChild, hChild);
            }
            if(south != null){
                wChild = south.getPreferredSize().width;
                hChild = south.getPreferredSize().height;
                south.setBounds((width / 2) - (wChild / 2), height - hChild, wChild, hChild);
            }
            if(south_east != null){
                wChild = south_east.getPreferredSize().width;
                hChild = south_east.getPreferredSize().height;
                south_east.setBounds(width - wChild, height - hChild, wChild, hChild);
            }
        }
    }
    
    
    
}