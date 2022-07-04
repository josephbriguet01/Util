/*
 * Copyright (C) JasonPercus Systems, Inc - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by JasonPercus, 10/2021
 */
package com.jasonpercus.util.async;



/**
 * Cette classe permet de créer un objet permettant d'exécuter du code asynchrone
 * @author JasonPercus
 * @version 1.0
 */
public class AsyncObj {
    
    
    
//ATTRIBUT
    /**
     * Correspond à l'exécuteur de pool de thread qui va gérer les appels en asynchrone
     */
    private final java.util.concurrent.ExecutorService EXECUTOR;

    
    
//CONSTRUCTORS
    /**
     * Crée un exécuteur de pool de threads. Par la suite, il s'occupera d'exécuter les appels asynchrones
     */
    public AsyncObj() {
        this.EXECUTOR = java.util.concurrent.Executors.newCachedThreadPool();
    }
    
    /**
     * Crée un exécuteur de pool de threads. Par la suite, il s'occupera d'exécuter les appels asynchrones
     * @param executor Correspond au type de pool de thread qui sera utilisé
     */
    public AsyncObj(java.util.concurrent.ExecutorService executor) {
        this.EXECUTOR = executor;
    }
    
    
    
//METHODES PUBLICS STATICS
    /**
     * Renvoie {@code true} si l'exécuteur a été arrêté
     * @return Retourne {@code true} si l'exécuteur a été arrêté
     */
    public synchronized boolean isShutdown() {
        if(EXECUTOR != null)
            return EXECUTOR.isShutdown();
        else
            return true;
    }
    
    /**
     * Renvoie {@code true} si toutes les tâches sont terminées après l'arrêt. Notez que {@code isTerminated} n'est jamais {@code true} à moins que {@code shutdown} ou {@code shutdownNow} n'ait été appelé en premier
     * @return Retourne {@code true} si toutes les tâches sont terminées après l'arrêt
     */
    public synchronized boolean isTerminated() {
        if(EXECUTOR != null)
            return EXECUTOR.isTerminated();
        else
            return true;
    }
    
    /**
     * Bloque jusqu'à ce que toutes les tâches aient terminé leur exécution après une demande d'arrêt, ou que le délai d'expiration se produise ou que le thread en cours soit interrompu, selon la première éventualité.
     * @param timeout Correspond au temps d'attente maximum autorisé
     * @param unit Correspond à l'unité de temps de l'argument timeout
     * @return Retourne {@code true} si cet exécuteur s'est terminé et {@code false} si le délai d'attente s'est écoulé avant la résiliation
     * @throws java.lang.InterruptedException Si interrompu pendant l'attente
     */
    public synchronized boolean awaitTermination(long timeout, java.util.concurrent.TimeUnit unit) throws InterruptedException {
        if(EXECUTOR != null)
            return EXECUTOR.awaitTermination(timeout, unit);
        else
            return true;
    }
    
    /**
     * Lance un arrêt ordonné dans lequel les tâches précédemment soumises sont exécutées, mais aucune nouvelle tâche ne sera acceptée. L'appel n'a aucun effet supplémentaire s'il est déjà arrêté. Cette méthode n'attend pas la fin de l'exécution des tâches soumises précédemment. Utilisez {@link #awaitTermination waitTermination} pour le faire
     * @throws SecurityException Si un gestionnaire de sécurité existe et que l'arrêt de cet ExecutorService peut manipuler des threads que l'appelant n'est pas autorisé à modifier car il ne contient pas {@link java.lang.RuntimePermission}{@code ("modifyThread")}, ou le gestionnaire de sécurité {@code checkAccess} refuse l'accès
     */
    public synchronized void shutdown() throws SecurityException {
        if(EXECUTOR != null)
            EXECUTOR.shutdown();
    }
    
    /**
     * Tente d'arrêter toutes les tâches en cours d'exécution, arrête le traitement des tâches en attente et renvoie une liste des tâches en attente d'exécution. Cette méthode n'attend pas la fin des tâches en cours d'exécution. Utilisez {@link #awaitTermination waitTermination} pour le faire. Il n'y a aucune garantie au-delà des meilleurs efforts pour arrêter le traitement des tâches en cours d'exécution. Par exemple, les implémentations typiques seront annulées via {@link Thread#interrupt}, de sorte que toute tâche qui ne répond pas aux interruptions peut ne jamais se terminer.
     * @return Retourne une liste des tâches qui n'ont jamais commencé à être exécutées
     * @throws SecurityException Si un gestionnaire de sécurité existe et que l'arrêt de cet ExecutorService peut manipuler des threads que l'appelant n'est pas autorisé à modifier car il ne contient pas {@link java.lang.RuntimePermission}{@code ("modifyThread")}, ou le gestionnaire de sécurité {@code checkAccess} refuse l'accès
     */
    public synchronized java.util.List<Runnable> shutdownNow() throws SecurityException {
        if(EXECUTOR != null)
            return EXECUTOR.shutdownNow();
        else
            return null;
    }
    
    /**
     * Soumet une tâche exécutable pour exécution et renvoie un futur représentant cette tâche. La méthode {@code get} de Future renverra le résultat donné en cas de réussite
     * @param task Correspond à la tâche à soumettre
     * @return Retourne un futur représentant l'attente de l'achèvement de la tâche
     */
    public synchronized java.util.concurrent.Future execute(Runnable task) {
        if(EXECUTOR != null)
            return EXECUTOR.submit(task);
        else
            return null;
    }
    
    /**
     * Soumet une tâche de retour de valeur pour exécution et renvoie un Future représentant les résultats en attente de la tâche. La méthode {@code get} de Future renverra le résultat de la tâche en cas de réussite
     * @param <R> Correspond au type de résultat
     * @param task Correspond à la tâche à soumettre
     * @return Retourne un futur représentant l'attente de l'achèvement de la tâche
     */
    public synchronized <R> java.util.concurrent.Future<R> execute(java.util.concurrent.Callable<R> task) {
        if(EXECUTOR != null)
            return EXECUTOR.submit(task);
        else
            return null;
    }
    
    /**
     * Soumet une tâche exécutable pour exécution. Une fois la tâche terminé le bout de code {@link Done} est appelé
     * @param task Correspond à la tâche à soumettre
     * @param done Correspond au bout de code à exécuté une fois la tâche terminée
     */
    public synchronized void execute(Runnable task, Done done) {
        if(EXECUTOR != null){
            Runnable runnable = () -> {
                task.run();
                done.run(null);
            };
            EXECUTOR.submit(runnable);
        }
    }
    
    /**
     * Soumet une tâche de retour de valeur pour exécution. Une fois la tâche terminé le bout de code {@link Done} est appelé
     * @param <R> Correspond au type de résultat
     * @param task Correspond à la tâche à soumettre
     * @param done Correspond au bout de code à exécuté une fois la tâche terminée
     */
    public synchronized <R> void execute(java.util.concurrent.Callable<R> task, Done<R> done) {
        if(EXECUTOR != null){
            Runnable runnable = () -> {
                try {
                    done.run(task.call());
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                } catch (Exception ex) {
                    java.util.logging.Logger.getLogger(AsyncObj.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                }
            };
            EXECUTOR.submit(runnable);
        }
    }
    
    
    
}