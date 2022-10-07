/*
 * Copyright (C) JasonPercus Systems, Inc - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by JasonPercus, 07/2021
 */
package com.jasonpercus.util.thread;



/**
 * Cette classe permet de créer un pool de thread. Ainsi, le système gère automatiquement les demandes sur des threads libres. C'est comme une liste de guichet. Dès qu'un guichet se libère, une nouvelle personne arrive et le guichetier gère son problème. Ici il s'agit de bouts de code qui doivent être exécutés sur des threads
 * @author JasonPercus
 * @version 1.0
 */
public final class ThreadPool implements AutoCloseable {
    
    
    
//ATTRIBUTS
    /**
     * Correspond au pool de thread qui s'occupera d'appeler au bon momemnt les bons threads...
     */
    private final java.util.concurrent.ThreadPoolExecutor executor;
    
    /**
     * True si le pool est démarré (plus précisément lorsque le premier {@link Runnable} s'exécute)
     */
    private boolean started;
    
    /**
     * True si le pool est stoppé (plus précisément lorsque le dernier {@link Runnable} en cours d'exécution se termine)
     */
    private boolean finished;
    
    /**
     * Correspond au listener qui sera mis au courant des évênements sur le ThreadPool
     */
    private Monitor listener;
    
    /**
     * Correspond au nombre de {@link Runnable} qui doivent être exécutés ou qui sont en cours d'exécution
     */
    private int cptToExecute = 0;
    
    /**
     * Correspond au nombre de {@link Runnable} qui sont terminés
     */
    private int cptFinished = 0;

    
    
//CONSTRUCTORS
    /**
     * Crée un pool de threads dont la taille n'est pas définit. Le pool augmente sa taille en fonction des besoins
     */
    public ThreadPool(){
        this.executor = (java.util.concurrent.ThreadPoolExecutor) java.util.concurrent.Executors.newCachedThreadPool();
    }
    
    /**
     * Crée un pool de threads avec une taille définie
     * @param count Correspond au nombre de threads qui pourront être au maximum exécutés en même temps
     */
    public ThreadPool(int count) {
        if(count>0)
            this.executor = (java.util.concurrent.ThreadPoolExecutor) java.util.concurrent.Executors.newFixedThreadPool(count);
        else
            this.executor = (java.util.concurrent.ThreadPoolExecutor) java.util.concurrent.Executors.newCachedThreadPool();
    }
    
    
    
//GETTERS & SETTER
    /**
     * Renvoie le listener qui sera mis au courant des évênements sur le ThreadPool
     * @return Retourne le listener qui sera mis au courant des évênements sur le ThreadPool
     */
    public Monitor getMonitor(){
        return this.listener;
    }
    
    /**
     * Modifie le listener qui sera mis au courant des évênements sur le ThreadPool
     * @param monitor Correspond au nouveau listener qui sera mis au courant des évênements sur le ThreadPool
     */
    public void setMonitor(Monitor monitor){
        this.listener = monitor;
    }

    /**
     * Détermine si le pool est démarré (plus précisément lorsque le premier {@link Runnable} s'exécute)
     * @return Retourne true si c'est le cas, sinon false
     */
    public boolean isStarted() {
        return started;
    }

    /**
     * Détermine si le pool est stoppé (plus précisément lorsque le dernier {@link Runnable} en cours d'exécution se termine)
     * @return Retourne true si c'est le cas, sinon false
     */
    public boolean isFinished() {
        return finished;
    }
    
    
    
//METHODES PUBLICS
    /**
     * Ajoute à la liste d'attente un bout de code devant être exécuté par un thread. Dès qu'une thread est disponible et que ce bout de code est tête de file, alors celui-ci est exécuté
     * @param runnable Correspond au bout de code à exécuter par un thread
     */
    public void execute(Runnable runnable){
        if(!started){
            started = true;
            if(listener != null) listener.started();
        }
        this.executor.execute(new Task(runnable));
    }

    /**
     * Ferme la file d'exécution de nouveaux bouts de code avec la méthode {@link #execute(java.lang.Runnable)}. En revanche, le pool termine sa liste de bouts de code déjà dans la file
     * @throws Exception S'il y a la moindre exception
     */
    @Override
    public void close() throws Exception {
        this.executor.shutdown();
    }

    /**
     * Ferme de force la file d'exécution de nouveaux bouts de code avec la méthode {@link #execute(java.lang.Runnable)}. Et stoppe les threads en cours. Donc tout ceux qui sont encore dans la file d'attente ne seront pas exécutés
     * @return Retourne la liste des bouts de codes qui n'auront pas été exécutés
     * @throws Exception S'il y a la moindre exception
     */
    public java.util.List<Runnable> closeNow() throws Exception {
        this.finished = true;
        return this.executor.shutdownNow();
    }
    
    
    
//INTERFACE
    /**
     * Cette interface permet à un objet d'être mis au courant des évênements sur un {@link ThreadPool}
     * @author JasonPercus
     * @version 1.0
     */
    public interface Monitor {
        
        /**
         * Lorsque le {@link ThreadPool} vient de démarrer
         */
        public void started();
        
        /**
         * A chaque lancement ou fermeture de thread
         * @param runnable Correspond au bout de code qui vient de se lancer ou de se terminer
         * @param status Détermine si le code est maintenant en cours d'exécution ou s'il vient de se terminer
         * @param poolSize Correspond au nombre actuel de threads dans le pool
         * @param corePoolSize Correspond au nombre de threads de base
         * @param activeCount Correspond au nombre approximatif de threads qui exécutent activement des tâches
         * @param completedTaskCount Correspond au nombre total approximatif de tâches dont l'exécution est terminée
         * @param taskCount Correspond au nombre total approximatif de tâches dont l'exécution a déjà été planifiée
         */
        public void update(Runnable runnable, StatusThread status, int poolSize, int corePoolSize, int activeCount, long completedTaskCount, long taskCount);
        
        /**
         * Lorsque le {@link ThreadPool} vient de se terminer
         */
        public void stopped();
        
    }
    
    
    
//ENUM
    /**
     * Cette énumération permet d'énumérer les états d'un bout de code lorsqu'il est en cours d'exécution par un thread, ou lorsque celui-ci est terminé
     * @author JasonPercus
     * @version 1.0
     */
    public enum StatusThread {
        
        /**
         * Lorsque le bout de code de l'utilisateur est en cours d'exécution
         */
        PLAYING,
        
        /**
         * Lorsque le bout de code de l'utilisateur vient de se terminer normalement ou de force
         */
        STOPPED;
        
    }
    
    
    
//CLASS
    /**
     * Cette classe représente une tâche à exécuter. C'est elle qui contient un bout de code à exécuter par un thread
     * @author JasonPercus
     * @version 1.0
     */
    private class Task implements Runnable {

        
        
    //ATTRIBUT
        /**
         * Correspond au bout de code que l'utilisateur veut exécuter sur un thread du pool
         */
        private final Runnable toRun;

        
        
    //CONSTRUCTOR
        /**
         * Crée une tâche
         * @param toRun Correspond au bout de code que l'utilisateur veut exécuter sur un thread du pool
         */
        public Task(Runnable toRun) {
            this.toRun = toRun;
            cptToExecute++;
        }
        
        
        
    //METHODE PUBLIC
        /**
         * Execute la tâche
         */
        @Override
        public void run() {
            //Avant d'exécuter le bout de code de l'utilisateur
            if(listener != null) listener.update(toRun, StatusThread.PLAYING, executor.getPoolSize(), executor.getCorePoolSize(), executor.getActiveCount(), executor.getCompletedTaskCount(), executor.getTaskCount());
            
            //Exécute le bout de code de l'utilisateur
            this.toRun.run();
            
            //Après l'exécution du bout de code de l'utilisateur
            if(listener != null) listener.update(toRun, StatusThread.STOPPED, executor.getPoolSize(), executor.getCorePoolSize(), executor.getActiveCount(), executor.getCompletedTaskCount(), executor.getTaskCount());
            cptFinished++;
            if((cptToExecute <= cptFinished || (finished && executor.getActiveCount() <= 1)) && listener != null){
                finished = true;
                listener.stopped();
            } 
        }
        
        
        
    }
    
    
    
}