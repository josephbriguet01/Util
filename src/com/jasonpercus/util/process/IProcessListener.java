/*
 * Copyright (C) JasonPercus Systems, Inc - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by JasonPercus, 05/2021
 */
package com.jasonpercus.util.process;



/**
 * Cette interface permet de communiquer à un listener les status d'un process
 * @see Process
 * @author JasonPercus
 * @version 1.0
 */
public interface IProcessListener {
    
    
    
    /**
     * Lorsque le Process a été lancé
     * @param <R> Correspond au type de résultat renvoyé
     * @param process Correspond au Process qui vient d'être lancé
     * @param args Correspond aux arguments de lancement du process
     * @param pid Correspond au pid du process
     * @param messageStream Correspond à l'entrée standard du Process
     * @param errorStream Correspond à l'entrée erreur du Process
     * @return Retourne éventuellement un résultat
     */
    public <R> R started(Process process, String[] args, long pid, java.io.InputStream messageStream, java.io.InputStream errorStream);
    
    /**
     * Lorsque le process a été stoppé
     * @param process Correspond au process qui a été stoppé
     * @param args Correspond aux arguments de lancement du process
     */
    public void stopped(Process process, String[] args);
    
    /**
     * Lorsque le process a été tué
     * @param process Correspond au process qui a été tué
     * @param args Correspond aux arguments de lancement du process
     */
    public void killed(Process process, String[] args);
    
    
    
}