/*
 * Copyright (C) JasonPercus Systems, Inc - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by JasonPercus, 05/2021
 */
package com.jasonpercus.util.thread;



/**
 * Cette classe s'apparente à la classe {@link Runnable} avec pour différence qu'elle prend des paramètres en entrée et en sortie. La méthode {@link #run(java.lang.Object, java.lang.Object, java.lang.Object) run()} s'exécute tout pareillement, en parallèle du thread courant.
 * @param <In> Correspond au type d'entrée fournit au départ (voir la méthode: {@link CodeProcessing#start(com.jasonpercus.util.thread.OnCodeProcess, java.lang.Object, com.jasonpercus.util.thread.During...) CodeProcessing.start()})
 * @param <StartOut> Correspond au type de donnée renvoyé par la méthode {@link OnCodeProcess#start(java.lang.Object) OnCodeProcess.start()}
 * @param <BeforeOut> Correspond au type de donnée renvoyé par la méthode {@link OnCodeProcess#before(java.lang.Object, java.lang.Object) OnCodeProcess.before()}
 * @param <DuringOut> Correspond au type de donnée renvoyé par la méthode {@link #run(java.lang.Object, java.lang.Object, java.lang.Object) run()}
 * @see CodeProcessing
 * @author JasonPercus
 * @version 1.0
 */
public abstract class During<In, StartOut, BeforeOut, DuringOut> {

    
    
//CONSTRUCTOR
    /**
     * Crée un objet During
     */
    public During() {
    }
    
    
    
//METHODE PUBLIC
    /**
     * Est appelé à être exécuté en parallèle de la méthode {@link CodeProcessing#start(com.jasonpercus.util.thread.OnCodeProcess, java.lang.Object, com.jasonpercus.util.thread.During...) CodeProcessing.start()}. Cette dernière va d'ailleur attendre le résultat de cette méthode avant de terminer son exécution.
     * Dans l'ordre des appels, cette méthode est appelée en troisième position (voir la documentation de la classe {@link OnCodeProcess}) derrière {@link OnCodeProcess#start(java.lang.Object) OnCodeProcess.start()} et {@link OnCodeProcess#before(java.lang.Object, java.lang.Object) OnCodeProcess.before()}
     * @param in Correspond au type d'entrée fournit au départ (voir la méthode: {@link CodeProcessing#start(com.jasonpercus.util.thread.OnCodeProcess, java.lang.Object, com.jasonpercus.util.thread.During...) CodeProcessing.start()})
     * @param resultStart Correspond à l'éventuel retour de la méthode {@link OnCodeProcess#start(java.lang.Object) OnCodeProcess.start()}
     * @param resultBefore Correspond à l'éventuel retour de la méthode {@link OnCodeProcess#before(java.lang.Object, java.lang.Object) OnCodeProcess.before()}
     * @return Retourne un éventuel résultat
     */
    public abstract DuringOut run(In in, StartOut resultStart, BeforeOut resultBefore);
    
    
    
}