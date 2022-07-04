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
 * Cette classe permet d'exécuter du code asynchrone
 * @author JasonPercus
 * @version 1.0
 */
public class Async {
    
    
    
//ATTRIBUT
    /**
     * Correspond à l'exécuteur de pool de thread qui va gérer les appels en asynchrone
     */
    private static AsyncObj ASYNC;

    
    
//CONSTRUCTOR
    /**
     * Crée un objet Async
     */
    private Async() {
    }
    
    
    
//METHODES PUBLICS STATICS
    /**
     * Crée un exécuteur de pool de threads. Par la suite, il s'occupera d'exécuter les appels asynchrones
     */
    public synchronized static void create() {
        if(ASYNC == null)
            ASYNC = new AsyncObj();
    }
    
    /**
     * Crée un exécuteur de pool de threads. Par la suite, il s'occupera d'exécuter les appels asynchrones
     * @param executor Correspond au type de pool de thread qui sera utilisé
     */
    public synchronized static void create(java.util.concurrent.ExecutorService executor) {
        if(ASYNC == null)
            ASYNC = new AsyncObj(executor);
    }
    
    /**
     * Renvoie {@code true} si l'exécuteur a été arrêté
     * @return Retourne {@code true} si l'exécuteur a été arrêté
     */
    public synchronized static boolean isShutdown() {
        if(ASYNC != null)
            return ASYNC.isShutdown();
        else
            return true;
    }
    
    /**
     * Renvoie {@code true} si toutes les tâches sont terminées après l'arrêt. Notez que {@code isTerminated} n'est jamais {@code true} à moins que {@code shutdown} ou {@code shutdownNow} n'ait été appelé en premier
     * @return Retourne {@code true} si toutes les tâches sont terminées après l'arrêt
     */
    public synchronized static boolean isTerminated() {
        if(ASYNC != null)
            return ASYNC.isTerminated();
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
    public synchronized static boolean awaitTermination(long timeout, java.util.concurrent.TimeUnit unit) throws InterruptedException {
        if(ASYNC != null)
            return ASYNC.awaitTermination(timeout, unit);
        else
            return true;
    }
    
    /**
     * Lance un arrêt ordonné dans lequel les tâches précédemment soumises sont exécutées, mais aucune nouvelle tâche ne sera acceptée. L'appel n'a aucun effet supplémentaire s'il est déjà arrêté. Cette méthode n'attend pas la fin de l'exécution des tâches soumises précédemment. Utilisez {@link #awaitTermination waitTermination} pour le faire
     * @throws SecurityException Si un gestionnaire de sécurité existe et que l'arrêt de cet ExecutorService peut manipuler des threads que l'appelant n'est pas autorisé à modifier car il ne contient pas {@link java.lang.RuntimePermission}{@code ("modifyThread")}, ou le gestionnaire de sécurité {@code checkAccess} refuse l'accès
     */
    public synchronized static void shutdown() throws SecurityException {
        if(ASYNC != null)
            ASYNC.shutdown();
    }
    
    /**
     * Tente d'arrêter toutes les tâches en cours d'exécution, arrête le traitement des tâches en attente et renvoie une liste des tâches en attente d'exécution. Cette méthode n'attend pas la fin des tâches en cours d'exécution. Utilisez {@link #awaitTermination waitTermination} pour le faire. Il n'y a aucune garantie au-delà des meilleurs efforts pour arrêter le traitement des tâches en cours d'exécution. Par exemple, les implémentations typiques seront annulées via {@link Thread#interrupt}, de sorte que toute tâche qui ne répond pas aux interruptions peut ne jamais se terminer.
     * @return Retourne une liste des tâches qui n'ont jamais commencé à être exécutées
     * @throws SecurityException Si un gestionnaire de sécurité existe et que l'arrêt de cet ExecutorService peut manipuler des threads que l'appelant n'est pas autorisé à modifier car il ne contient pas {@link java.lang.RuntimePermission}{@code ("modifyThread")}, ou le gestionnaire de sécurité {@code checkAccess} refuse l'accès
     */
    public synchronized static java.util.List<Runnable> shutdownNow() throws SecurityException {
        if(ASYNC != null)
            return ASYNC.shutdownNow();
        else
            return null;
    }
    
    /**
     * Soumet une tâche exécutable pour exécution et renvoie un futur représentant cette tâche. La méthode {@code get} de Future renverra le résultat donné en cas de réussite
     * @param task Correspond à la tâche à soumettre
     * @return Retourne un futur représentant l'attente de l'achèvement de la tâche
     */
    public synchronized static java.util.concurrent.Future execute(Runnable task) {
        if(ASYNC != null)
            return ASYNC.execute(task);
        else
            return null;
    }
    
    /**
     * Soumet une tâche de retour de valeur pour exécution et renvoie un Future représentant les résultats en attente de la tâche. La méthode {@code get} de Future renverra le résultat de la tâche en cas de réussite
     * @param <R> Correspond au type de résultat
     * @param task Correspond à la tâche à soumettre
     * @return Retourne un futur représentant l'attente de l'achèvement de la tâche
     */
    public synchronized static <R> java.util.concurrent.Future<R> execute(java.util.concurrent.Callable<R> task) {
        if(ASYNC != null)
            return ASYNC.execute(task);
        else
            return null;
    }
    
    /**
     * Soumet une tâche exécutable pour exécution. Une fois la tâche terminé le bout de code {@link Done} est appelé
     * @param task Correspond à la tâche à soumettre
     * @param done Correspond au bout de code à exécuté une fois la tâche terminée
     */
    public synchronized static void execute(Runnable task, Done done) {
        if(ASYNC != null){
            Runnable runnable = () -> {
                task.run();
                done.run(null);
            };
            ASYNC.execute(runnable);
        }
    }
    
    /**
     * Soumet une tâche de retour de valeur pour exécution. Une fois la tâche terminé le bout de code {@link Done} est appelé
     * @param <R> Correspond au type de résultat
     * @param task Correspond à la tâche à soumettre
     * @param done Correspond au bout de code à exécuté une fois la tâche terminée
     */
    public synchronized static <R> void execute(java.util.concurrent.Callable<R> task, Done<R> done) {
        if(ASYNC != null){
            Runnable runnable = () -> {
                try {
                    done.run(task.call());
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                } catch (Exception ex) {
                    java.util.logging.Logger.getLogger(Async.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                }
            };
            ASYNC.execute(runnable);
        }
    }
    
    
    
}