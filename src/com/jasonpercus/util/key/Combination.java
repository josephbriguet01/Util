/*
 * Copyright (C) JasonPercus Systems, Inc - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by JasonPercus, 06/2021
 */
package com.jasonpercus.util.key;



/**
 * Cette classe représente une combinaison de touche(s) ou raccourci
 * @author JasonPercus
 * @version 1.0
 */
public class Combination implements java.io.Serializable, Cloneable, Comparable<Combination> {
    
    
    
//CONSTANTE
    /**
     * Représente la touche null. Autrement dit lorsqu'il n'y a pas de touche combinée avec une touche de contrôle
     */
    public final static int NO_KEY = -1;
    
    
    
//ATTRIBUT PRIVATE STATIC
    /**
     * Correspond au robot qui va réaliser virtuellement une combinaison de touche(s)
     */
    private transient static java.awt.Robot executor;
    
    
    
//ATTRIBUTS
    /**
     * Lorsque la combinaison de touche(s) contient la touche Windows
     */
    private boolean windows;
    
    /**
     * Lorsque la combinaison de touche(s) contient la touche Meta (MAC seulement)
     */
    private boolean meta;
    
    /**
     * Lorsque la combinaison de touche(s) contient la touche Ctrl
     */
    private boolean control;
    
    /**
     * Lorsque la combinaison de touche(s) contient la touche Shift
     */
    private boolean shift;
    
    /**
     * Lorsque la combinaison de touche(s) contient la touche Alt
     */
    private boolean alt;
    
    /**
     * Correspond au code de la touche combiné avec une touche de contrôle
     */
    private int key;

    
    
//CONSTRUCTORS
    /**
     * Crée une combinaison de touche(s) par défaut. À savoir, aucune touche
     * @deprecated <div style="color: #D45B5B; font-style: italic">Ne pas utiliser - Il n'a de l'intérêt que pour la dé/sérialization</div>
     */
    public Combination() {
    }
    
    /**
     * Crée une combinaison de touche(s)
     * @param key Correspond au code de la touche combiné avec une touche de contrôle
     * @param windows Lorsque la combinaison de touche(s) contient la touche Windows
     * @param meta Lorsque la combinaison de touche(s) contient la touche Meta (MAC seulement)
     * @param control Lorsque la combinaison de touche(s) contient la touche Ctrl
     * @param shift Lorsque la combinaison de touche(s) contient la touche Shift
     * @param alt Lorsque la combinaison de touche(s) contient la touche Alt
     */
    public Combination(int key, boolean windows, boolean meta, boolean control, boolean shift, boolean alt) {
        this.windows = windows;
        this.meta    = meta;
        this.control = control;
        this.shift   = shift;
        this.alt     = alt;
        this.key     = key;
    }

    
    
//GETTERS & SETTERS
    /**
     * Détermine si la touche Windows est comprise dans la combinaison
     * @return Retourne true si elle l'est, sinon false
     */
    public boolean isWindows() {
        return windows;
    }

    /**
     * Détermine si la touche Meta (MAC seulement) est comprise dans la combinaison
     * @return Retourne true si elle l'est, sinon false
     */
    public boolean isMeta() {
        return meta;
    }

    /**
     * Détermine si la touche Ctrl est comprise dans la combinaison
     * @return Retourne true si elle l'est, sinon false
     */
    public boolean isControl() {
        return control;
    }

    /**
     * Détermine si la touche Shift est comprise dans la combinaison
     * @return Retourne true si elle l'est, sinon false
     */
    public boolean isShift() {
        return shift;
    }

    /**
     * Détermine si la touche Alt est comprise dans la combinaison
     * @return Retourne true si elle l'est, sinon false
     */
    public boolean isAlt() {
        return alt;
    }

    /**
     * Renvoie le code de la touche combiné avec une touche de contrôle
     * @return Retourne le code de la touche combiné avec une touche de contrôle
     */
    public int getKey() {
        return key;
    }

    /**
     * Détermine si la touche Windows doit être comprise dans la combinaison
     * @param windows True, si elle doit être comprise, sinon false
     */
    public void setWindows(boolean windows) {
        this.windows = windows;
    }

    /**
     * Détermine si la touche Meta doit être comprise dans la combinaison
     * @param meta True, si elle doit être comprise, sinon false
     */
    public void setMeta(boolean meta) {
        this.meta = meta;
    }

    /**
     * Détermine si la touche Ctrl doit être comprise dans la combinaison
     * @param control True, si elle doit être comprise, sinon false
     */
    public void setControl(boolean control) {
        this.control = control;
    }

    /**
     * Détermine si la touche Shift doit être comprise dans la combinaison
     * @param shift True, si elle doit être comprise, sinon false
     */
    public void setShift(boolean shift) {
        this.shift = shift;
    }

    /**
     * Détermine si la touche Alt doit être comprise dans la combinaison
     * @param alt True, si elle doit être comprise, sinon false
     */
    public void setAlt(boolean alt) {
        this.alt = alt;
    }

    /**
     * Modifie le code de la touche combiné avec une touche de contrôle
     * @param key Correspond au nouveau code de touche
     */
    public void setKey(int key) {
        this.key = key;
    }

    
    
//METHODES PUBLICS
    /**
     * Execute la combinaison de touche(s). Attention: certaines combinaisons telles que Ctrl+Alt+DELETE ne peuvent pas être exécutées sous windows
     * @see java.awt.Robot
     * @throws java.awt.AWTException Si le robot qui doit simuler la combinaison de touche ne peut être créé
     */
    @SuppressWarnings("SleepWhileInLoop")
    public void execute() throws java.awt.AWTException {
        if(Combination.executor == null){
            Combination.executor = new java.awt.Robot();
        }
        if(Combination.executor != null){
            java.util.List<Integer> codes = new java.util.ArrayList<>();
            if(windows)
                codes.add(java.awt.event.KeyEvent.VK_WINDOWS);
            
            if(meta)
                codes.add(java.awt.event.KeyEvent.VK_META);
            
            if(control)
                codes.add(java.awt.event.KeyEvent.VK_CONTROL);
            
            if(alt)
                codes.add(java.awt.event.KeyEvent.VK_ALT);
            
            if(shift)
                codes.add(java.awt.event.KeyEvent.VK_SHIFT);
            
            if(key != -1)
                codes.add(key);
            
            for(Integer code : codes){
                Combination.executor.keyPress(code);
                try {
                    Thread.sleep(10);
                } catch (InterruptedException ex) {
                    java.util.logging.Logger.getLogger(Combination.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                }
            }
            
            for(Integer code : codes){
                Combination.executor.keyRelease(code);
            }
        }
    }
    
    /**
     * Renvoie le hashCode de la combinaison de touche(s)
     * @return Retourne le hashCode de la combinaison de touche(s)
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + (this.windows ? 1 : 0);
        hash = 37 * hash + (this.meta ? 1 : 0);
        hash = 37 * hash + (this.control ? 1 : 0);
        hash = 37 * hash + (this.shift ? 1 : 0);
        hash = 37 * hash + (this.alt ? 1 : 0);
        hash = 37 * hash + this.key;
        return hash;
    }

    /**
     * Détermine si deux combinaisons de touche(s) sont égales
     * @param obj Correspond à la seconde combinaison de touche(s) à comparer à la courante
     * @return Retourne true si les deux combinaisons sont égales, sinon false
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Combination other = (Combination) obj;
        if (this.windows != other.windows) {
            return false;
        }
        if (this.meta != other.meta) {
            return false;
        }
        if (this.control != other.control) {
            return false;
        }
        if (this.shift != other.shift) {
            return false;
        }
        if (this.alt != other.alt) {
            return false;
        }
        return this.key == other.key;
    }

    /**
     * Renvoie la combinaison de touche(s) sous la forme d'une chaîne de caractères
     * @return Retourne la combinaison de touche(s) sous la forme d'une chaîne de caractères
     */
    @Override
    public String toString() {
        String chain = "";
        
        if(windows)
            chain += "Windows";
        
        if(meta)
            chain += "Meta";
        
        if(control){
            if(!chain.isEmpty()) chain += " + ";
            chain += "Ctrl";
        }
        
        if(alt){
            if(!chain.isEmpty()) chain += " + ";
            chain += "Alt";
        }
        
        if(shift){
            if(!chain.isEmpty()) chain += " + ";
            chain += "Shift";
        }
        
        if(key != -1){
            if(!chain.isEmpty()) chain += " + ";
            chain += Code.name(key);
        }
        
        return chain;
    }

    /**
     * Renvoie le résultat de la comparaison de deux combinaisons de touche(s)
     * @param c Correspond à la seconde combinaison à comparer à la courante
     * @return Retourne le résultat de la comparaison
     */
    @Override
    public int compareTo(Combination c) {
        return this.toString().compareTo(c.toString());
    }

    /**
     * Clone une combinaison de touche(s)
     * @return Retourne le clone créé
     * @throws CloneNotSupportedException Si le clone ne peut être créé
     */
    @Override
    @SuppressWarnings("CloneDoesntCallSuperClone")
    public Object clone() throws CloneNotSupportedException {
        return new Combination(key, windows, meta, control, shift, alt);
    }
    
    
    
}