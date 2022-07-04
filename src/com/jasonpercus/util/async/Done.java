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
 * Cette classe représente un bout de code qui sera exécuté à la fin d'une tâche asynchrone
 * @author JasonPercus
 * @version 1.0
 * @param <R> Correspond au résultat de la tâche asynchrone terminée
 */
public interface Done<R> {
    
    
    
    /**
     * Bout de code exécuté à la fin d'une tâche asynchrone
     * @param result Correspond au résultat renvoyé par la tâche asynchrone
     */
    public void run(R result);
    
    
    
}