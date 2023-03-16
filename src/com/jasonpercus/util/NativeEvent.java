/*
 * Copyright (C) JasonPercus Systems, Inc - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by JasonPercus, 03/2023
 */
package com.jasonpercus.util;



import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;
import com.github.kwhat.jnativehook.mouse.NativeMouseInputListener;
import com.github.kwhat.jnativehook.mouse.NativeMouseMotionListener;
import com.github.kwhat.jnativehook.mouse.NativeMouseWheelListener;



/**
 * Cette classe est basée sur le travail d'Alex Barker. Il permet grâce à son projet de récupérer les évènements souris et clavier en dehors de l'application java
 * @author JasonPercus
 * @version 1.0
 */
public class NativeEvent implements java.io.Serializable {
    
    
    
//ATTRIBUT STATIC
    /**
     * Détermine si le projet d'Alex Barker a été initialisé ou pas
     */
    private transient static boolean inited;
    
    
    
//ATTRIBUTS
    /**
     * Correspond à la liste des MouseListener
     */
    private transient final java.util.List<NativeMouseInputListener> mouseListeners = new java.util.ArrayList<>();
    
    /**
     * Correspond à la liste des MouseMotionListener
     */
    private transient final java.util.List<NativeMouseMotionListener> mouseMotionListeners = new java.util.ArrayList<>();
    
    /**
     * Correspond à la liste des MouseWheelListener
     */
    private transient final java.util.List<NativeMouseWheelListener> mouseWheelListeners = new java.util.ArrayList<>();
    
    /**
     * Correspond à la liste des KeyListener
     */
    private transient final java.util.List<NativeKeyListener> keyListeners = new java.util.ArrayList<>();

    
    
//CONSTRUCTOR
    /**
     * Crée un objet {@linkplain NativeEvent}
     * @throws com.jasonpercus.util.NativeEvent.NativeEventException Si l'écoute des évènements de l'OS ne peuvent être accroché à un écouteur
     */
    public NativeEvent() throws NativeEventException {
        if(!inited){
            try {
                GlobalScreen.registerNativeHook();
            } catch (NativeHookException ex) {
                throw new NativeEventException("There was a problem registering the native hook.");
            }
        }
    }
    
    
    
//METHODES PUBLICS
    /**
     * Ajoute un objet {@link NativeKeyListener} au gestionnaire d'évènements
     * @param listener Correspond au listener à ajouter au gestionnaire d'évènements
     */
    public synchronized void addKeyListener(NativeKeyListener listener) {
        if(listener != null){
            keyListeners.add(listener);
            GlobalScreen.addNativeKeyListener(listener);
        }
    }
    
    /**
     * Enlève un objet {@link NativeKeyListener} du gestionnaire d'évènements
     * @param listener Correspond au listener à enlever au gestionnaire d'évènements
     */
    public synchronized void removeKeyListener(NativeKeyListener listener) {
        if(listener != null){
            keyListeners.remove(listener);
            GlobalScreen.addNativeKeyListener(listener);
        }
    }
    
    /**
     * Renvoie la liste des {@link NativeKeyListener} contenus dans le gestionnaire d'évènements
     * @return Retourne la liste des {@link NativeKeyListener} contenus dans le gestionnaire d'évènements
     */
    public synchronized NativeKeyListener[] getKeyListeners() {
        NativeKeyListener[] array = new NativeKeyListener[keyListeners.size()];
        for(int i=0;i<keyListeners.size();i++)
            array[i] = keyListeners.get(i);
        return array;
    }
    
    /**
     * Ajoute un objet {@link NativeMouseInputListener} au gestionnaire d'évènements
     * @param listener Correspond au listener à ajouter au gestionnaire d'évènements
     */
    public synchronized void addMouseListener(NativeMouseInputListener listener) {
        if(listener != null){
            mouseListeners.add(listener);
            GlobalScreen.addNativeMouseListener(listener);
        }
    }
    
    /**
     * Enlève un objet {@link NativeMouseInputListener} du gestionnaire d'évènements
     * @param listener Correspond au listener à enlever au gestionnaire d'évènements
     */
    public synchronized void removeMouseListener(NativeMouseInputListener listener) {
        if(listener != null){
            mouseListeners.remove(listener);
            GlobalScreen.removeNativeMouseListener(listener);
        }
    }
    
    /**
     * Renvoie la liste des {@link NativeMouseInputListener} contenus dans le gestionnaire d'évènements
     * @return Retourne la liste des {@link NativeMouseInputListener} contenus dans le gestionnaire d'évènements
     */
    public synchronized NativeMouseInputListener[] getMouseListeners() {
        NativeMouseInputListener[] array = new NativeMouseInputListener[mouseListeners.size()];
        for(int i=0;i<mouseListeners.size();i++)
            array[i] = mouseListeners.get(i);
        return array;
    }
    
    /**
     * Ajoute un objet {@link NativeMouseMotionListener} au gestionnaire d'évènements
     * @param listener Correspond au listener à ajouter au gestionnaire d'évènements
     */
    public synchronized void addMouseMotionListener(NativeMouseMotionListener listener) {
        if(listener != null){
            mouseMotionListeners.add(listener);
            GlobalScreen.addNativeMouseMotionListener(listener);
        }
    }

    /**
     * Enlève un objet {@link NativeMouseMotionListener} du gestionnaire d'évènements
     * @param listener Correspond au listener à enlever au gestionnaire d'évènements
     */
    public synchronized void removeMouseMotionListener(NativeMouseMotionListener listener) {
        if(listener != null){
            mouseMotionListeners.remove(listener);
            GlobalScreen.removeNativeMouseMotionListener(listener);
        }
    }

    /**
     * Renvoie la liste des {@link NativeMouseMotionListener} contenus dans le gestionnaire d'évènements
     * @return Retourne la liste des {@link NativeMouseMotionListener} contenus dans le gestionnaire d'évènements
     */
    public synchronized NativeMouseMotionListener[] getMouseMotionListeners() {
        NativeMouseMotionListener[] array = new NativeMouseMotionListener[mouseMotionListeners.size()];
        for(int i=0;i<mouseMotionListeners.size();i++)
            array[i] = mouseMotionListeners.get(i);
        return array;
    }

    /**
     * Ajoute un objet {@link NativeMouseWheelListener} au gestionnaire d'évènements
     * @param listener Correspond au listener à ajouter au gestionnaire d'évènements
     */
    public synchronized void addMouseWheelListener(NativeMouseWheelListener listener) {
        if(listener != null){
            mouseWheelListeners.add(listener);
            GlobalScreen.addNativeMouseWheelListener(listener);
        }
    }

    /**
     * Enlève un objet {@link NativeMouseWheelListener} du gestionnaire d'évènements
     * @param listener Correspond au listener à enlever au gestionnaire d'évènements
     */
    public synchronized void removeMouseWheelListener(NativeMouseWheelListener listener) {
        if(listener != null){
            mouseWheelListeners.remove(listener);
            GlobalScreen.removeNativeMouseWheelListener(listener);
        }
    }

    /**
     * Renvoie la liste des {@link NativeMouseWheelListener} contenus dans le gestionnaire d'évènements
     * @return Retourne la liste des {@link NativeMouseWheelListener} contenus dans le gestionnaire d'évènements
     */
    public synchronized NativeMouseWheelListener[] getMouseWheelListeners() {
        NativeMouseWheelListener[] array = new NativeMouseWheelListener[mouseWheelListeners.size()];
        for(int i=0;i<mouseWheelListeners.size();i++)
            array[i] = mouseWheelListeners.get(i);
        return array;
    }
    
    
    
//CLASS
    /**
     * Cette classe représente les exceptions de type {@link NativeEvent}
     * @author JasonPercus
     * @version 1.0
     */
    public static class NativeEventException extends Exception {

        
        
    //CONSTRUCTOR
        /**
         * Crée une exception de type {@linkplain NativeEventException}
         * @param message Correspond au message de l'exception
         */
        public NativeEventException(String message) {
            super(message);
        }
        
        
        
    }
    
    
    
}