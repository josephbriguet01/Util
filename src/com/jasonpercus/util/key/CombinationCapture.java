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
 * Cette classe à pour fonction de capturer sur le clavier une combinaison de touches
 * @see Combination
 * @author JasonPercus
 * @version 1.0
 */
public final class CombinationCapture {
    
    
    
//ATTRIBUTS PRIVATES
    /**
     * Correspond au composant dont on va capturer une combinaison lorsqu'il a le focus
     */
    private final java.awt.Component component;
    
    /**
     * Détermine s'il faut ajouter la touche windows aux combinaisons capturées
     */
    private boolean addWindowsKey;
    
    /**
     * Détermine s'il faut ajouter la touche meta aux combinaisons capturées
     */
    private boolean addMetaKey;
    
    /**
     * Correspond au listener des touches pressées sur le composant
     */
    private java.awt.event.KeyAdapter adapter;
    
    /**
     * Correspond au listener de combinaison
     */
    private CombinationCaptureListener listener;

    
    
//CONSTRUCTORS
    /**
     * Crée un captureur de combinaison de touche(s)
     * @deprecated Ne pas utiliser
     */
    private CombinationCapture() {
        this(null, null);
    }

    /**
     * Crée un captureur de combinaison de touche(s)
     * @param component Correspond au composant qui récupérera les évênements clavier
     * @param listener Correspond au listener qui recevra la combinaison pressée
     */
    public CombinationCapture(java.awt.Component component, CombinationCaptureListener listener) {
        this(component, false, false, listener);
    }

    /**
     * Crée un captureur de combinaison de touche(s)
     * @param component Correspond au composant qui récupérera les évênements clavier
     * @param addWindowsKey Détermine s'il faut ajouter par défaut la touche windows à la combinaison
     * @param addMetaKey Détermine s'il faut ajouter par défaut la touche meta à la combinaison (MAC seulement)
     * @param listener Correspond au listener qui recevra la combinaison pressée
     */
    public CombinationCapture(java.awt.Component component, boolean addWindowsKey, boolean addMetaKey, CombinationCaptureListener listener) {
        this.listener = listener;
        this.component = component;
        this.addWindowsKey = addWindowsKey;
        this.addMetaKey = addMetaKey;
        this.component.addKeyListener(this.adapter = new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent evt) {
                if(evt.getKeyCode() != 0 && evt.getKeyCode() != java.awt.event.KeyEvent.VK_CONTROL && evt.getKeyCode() != java.awt.event.KeyEvent.VK_ALT && evt.getKeyCode() != java.awt.event.KeyEvent.VK_ALT_GRAPH && evt.getKeyCode() != java.awt.event.KeyEvent.VK_SHIFT && evt.getKeyCode() != java.awt.event.KeyEvent.VK_WINDOWS && evt.getKeyCode() != java.awt.event.KeyEvent.VK_META){
                    Combination combination = new Combination(evt.getKeyCode(), CombinationCapture.this.addWindowsKey, CombinationCapture.this.addMetaKey || evt.isMetaDown(), evt.isControlDown(), evt.isShiftDown(), evt.isAltDown());
                    if(CombinationCapture.this.listener != null)
                        CombinationCapture.this.listener.combinationPressed(combination, evt);
                }
            }
        });
    }

    
    
//METHODES PUBLICS
    /**
     * Détermine si la touche windows est ajoutée à la (futur) combinaison
     * @return Retourne true si elle l'est, sinon false
     */
    public boolean addWindowsKey() {
        return addWindowsKey;
    }

    /**
     * Détermine s'il faut ajouter par défaut la touche windows à la (futur) combinaison
     * @param addWindowsKey True, s'il faut l'ajouter, sinon false
     */
    public void addWindowsKey(boolean addWindowsKey) {
        this.addWindowsKey = addWindowsKey;
    }

    /**
     * Détermine si la touche meta est ajoutée à la (futur) combinaison
     * @return Retourne true si elle l'est, sinon false
     */
    public boolean addMetaKey() {
        return addMetaKey;
    }

    /**
     * Détermine s'il faut ajouter par défaut la touche meta à la (futur) combinaison
     * @param addMetaKey True, s'il faut l'ajouter, sinon false
     */
    public void addMetaKey(boolean addMetaKey) {
        this.addMetaKey = addMetaKey;
    }

    /**
     * Renvoie le listener des combinaisons de touches sur le composant donné en constructeur
     * @return Retourne le listener des combinaisons de touches sur le composant donné en constructeur
     */
    public CombinationCaptureListener getListener() {
        return listener;
    }

    /**
     * Modifie le listener des combinaisons de touches sur le composant donné en constructeur
     * @param listener Correspond au nouveau listener de touches sur le composant donné en constructeur
     */
    public void setListener(CombinationCaptureListener listener) {
        this.listener = listener;
    }

    /**
     * Détruit l'objet
     * @throws Throwable S'il y a la moindre exception
     */
    @Override
    @SuppressWarnings("FinalizeDeclaration")
    protected void finalize() throws Throwable {
        try{
            this.component.removeKeyListener(adapter);
        }catch(Exception e){}
        super.finalize();
    }
    
    
    
}