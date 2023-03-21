/*
 * Copyright (C) JasonPercus Systems, Inc - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by JasonPercus, 03/2023
 */
package com.jasonpercus.util;

import java.io.IOException;



/**
 * Cette classe permet de créer une file d'attente de bytes sous la forme d'un flux
 * @author JasonPercus
 * @version 1.0
 */
public class QueueDataInputStream extends java.io.InputStream {

    
    
//ATTRIBUTS
    /**
     * Correspond à la file d'attente en interne
     */
    private final Queue<Integer> queue;
    
    /**
     * Cette variable détermine si la file d'attente est considérée comme fermé ou pas en entrée
     */
    private boolean closeOutput;
    
    /**
     * Cette variable détermine si la file d'attente est considérée comme fermé ou pas en sortie
     */
    private boolean closeInput;

    
    
//CONSTRUCTOR
    /**
     * Crée un objet {@linkplain QueueDataInputStream}
     */
    public QueueDataInputStream() {
        this.queue = new Queue<>();
        this.closeOutput = false;
        this.closeInput  = false;
    }
    
    
    
//METHODES
    /**
     * Détermine s'il existe ou pas des données dans le flux (le fait qu'il n'y en a pas ne justefie en aucun cas que le flux est fermée
     * @return Retourne true s'il n'y a aucune donnée, sinon false
     */
    public boolean isEmpty(){
            return this.queue.isEmpty();
    }

    /**
     * Extrait un byte de la file d'attente. Cette méthode reste bloquante tant qu'il n'y a pas de byte dans la file d'attente
     * @return Retourne un byte de la file d'attente ou -1 si le flux est fermé
     * @throws java.io.IOException S'il y a une erreur de lecture d'un byte
     */
    @Override
    public int read() throws java.io.IOException {
        try {
            if(!closeInput){
                int value = queue.get();
                if(value < 0)
                    closeInput = true;
                return value;
            }else
                return -1;
        } catch (InterruptedException ex) {
            throw new java.io.IOException("Cannot read byte", ex);
        }
    }
    
    /**
     * Ecrit un byte dans la file d'attente
     * @param value Correspond au byte à ajouter dans la file d'attente. Ne pas ajouter -1.
     * @throws java.io.IOException Si le byte ne peut être ajouté dans la file d'attente
     */
    public void write(int value) throws java.io.IOException {
        try {
            if(!closeOutput)
                queue.add(value);
            else
                throw new java.io.IOException(QueueDataInputStream.class.getSimpleName() + " is closed !");
        } catch (InterruptedException ex) {
            throw new java.io.IOException("Cannot write byte", ex);
        }
    }
    
    /**
     * Ecrit un tableau de byte dans la file d'attente
     * @param b Correspond au tableau de bytes à ajouter dans la file d'attente. Ne pas ajouter -1 au tableau.
     * @throws java.io.IOException Si un/des byte(s) ne peuvent être ajoutés dans la file d'attente
     */
    public void write(byte[] b) throws java.io.IOException {
        write(b, 0, b.length);
    }
    
    /**
     * Ecrit une partie du tableau de byte dans la file d'attente
     * @param b Correspond au tableau de bytes à ajouter dans la file d'attente. Ne pas ajouter -1 au tableau.
     * @param off Correspond à l'indice du premier byte à ajouter dans la file d'attente
     * @param len Correspond au nombre de bytes à ajouter dans la file d'attente
     * @throws java.io.IOException Si un/des byte(s) ne peuvent être ajoutés dans la file d'attente
     */
    public void write(byte[] b, int off, int len) throws java.io.IOException {
        for(int i = off; i < off + len; i++){
            write(b[i] & 0xFF);
        }
    }
    
    /**
     * Ferme le flux en entrée. Si aucune donnée ne rentre, alors la file d'attente est sûre d'être vidée et renverra forcémment -1
     * @throws java.io.IOException Si une erreur d'entrée/sortie survient
     */
    @Override
    public synchronized void close() throws java.io.IOException {
        if(!closeOutput){
            write(-1);
            closeOutput = true;
        }
    }
    
    
    
}