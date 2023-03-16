/*
 * Copyright (C) JasonPercus Systems, Inc - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by JasonPercus, 03/2023
 */
package com.jasonpercus.util;



/**
 * Cette classe permet de créer une file d'attente thread-safe.Elle sert entre autre dans le cas Producer/Consumer
 * @param <O> Correspond au type d'objets contenus dans la file d'attente
 * @author JasonPercus
 * @version 1.0
 */
public class Queue<O> {
    
    
    
//ATTRIBUT
    /**
     * Correspond au buffer
     */
    private final java.util.concurrent.BlockingQueue<O> buffer;
    private final Object obj1 = new Object();
    private final Object obj2 = new Object();

    
    
//CONSTRUCTOR
    /**
     * Crée un objet {@linkplain Queue}
     */
    public Queue() {
        this.buffer = new java.util.concurrent.LinkedBlockingQueue<>();
    }

    
    
//METHODES
    /**
     * Ajoute un objet dans la file d'attente
     * @param obj Correspond à l'objet à ajouter dans la file d'attente
     * @throws InterruptedException Lorsqu'une exception d'interruption survient
     */
    public void add(O obj) throws InterruptedException {
        synchronized(obj1){
            this.buffer.put(obj);
        }
    }

    /**
     * Renvoie la première personne de la file d'attente
     * @return Retourne la première personne de la file d'attente
     * @throws InterruptedException Lorsqu'une exception d'interruption survient
     */
    public O get() throws InterruptedException {
        synchronized(obj2){
            return this.buffer.take();
        }
    }
    
    
    
}