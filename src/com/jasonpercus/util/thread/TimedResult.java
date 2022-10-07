/*
 * Copyright (C) JasonPercus Systems, Inc - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by JasonPercus, 05/2021
 */
package com.jasonpercus.util.thread;

import com.jasonpercus.util.time.Millisecond;



/**
 * Cette classe contient quelques méthodes statiques pratiques. À savoir pouvoir exécuter un bout de code, et si celui-ci dépasse un temps maximum indiqué, une valeur par défaut est renvoyée
 * 
 * Imaginons deux exemples simples.
 * Définissons tout d'abord les variables ci-dessous. La première aura pour but de définir le nombre de fois que le programme devra dire bonjour dans le temps imparti et la seconde aura pour but de définir à quelle personne le programme devra dire bonjour.
 * <pre>
 * <code>
 * final int nombre = 2000;
 * final String personnage = "Lucas";
 * </code>
 * </pre>
 * Et voici maintenant un petit code qui aura pour but de dire nombre fois "Bonjour" à une personne
 * <pre>
 * <code>
 * CodeTimeResult&lsaquo;String, String&rsaquo; code = (String in) -&rsaquo; {
 *     for(int i=0;i&lsaquo;nombre;i++){
 *         System.out.println(i + ": Bonjour " + in);
 *         Thread.sleep(1);
 *     }
 *     return "J'ai réussi à dire bonjour " + nombre + "x à Lucas !";
 * };
 * 
 * //5000 = Temps maximum que doit mettre le code à s'exécuter
 * System.out.println(TimedResult.&lsaquo;String,String&rsaquo;run(code, personnage, 5000, "Je ne suis pas assez rapide pour dire " + nombre + "x bonjour autant de fois dans le temps imparti !"));
 * </code>
 * </pre>
 * Si tout se passe bien le programme devrait afficher:
 * <pre>
 * <code>
 * 0: Bonjour Lucas
 * 1: Bonjour Lucas
 * 2: Bonjour Lucas
 * 3: Bonjour Lucas
 * ...
 * 1999: Bonjour Lucas
 * J'ai réussi à dire bonjour 2000x à Lucas !
 * </code>
 * </pre>
 * Si maintenant ont fait varier la variable nombre et que l'on lui assigne la valeur 1000000, le programme devrait afficher:
 * <pre>
 * <code>
 * 0: Bonjour Lucas
 * 1: Bonjour Lucas
 * 2: Bonjour Lucas
 * 3: Bonjour Lucas
 * ...
 * 3139: Bonjour Lucas
 * Je ne suis pas assez rapide pour dire 1000000x bonjour autant de fois dans le temps imparti !
 * </code>
 * </pre>
 * 
 * @see CodeTimeResult
 * @author JasonPercus
 * @version 1.0
 */
public class TimedResult {

    
    
//CONSTRUCTOR
    /**
     * Crée un objet TimedResult
     */
    public TimedResult() {
    }
    
    
    
//METHODES PUBLICS
    /**
     * Execute la méthode {@link CodeTimeResult#run(java.lang.Object) CodeTimeResult.run()}.
     * Son exécution ne dépassera pas maxTime millisecondes. Si la méthode devait mettre plus de temps, alors celle-ci serait killé et un résultat par défaut serait renvoyé
     * @param <In> Correspond au type de valeur qui peut être fournie à la méthode {@link CodeTimeResult#run(java.lang.Object) CodeTimeResult.run()}
     * @param <Out> Correspond au type de valeur retournée
     * @param code Correspond au code à exécuter (cf: {@link CodeTimeResult})
     * @param in Correspond à une éventuelle valeur à fournir à la méthode {@link CodeTimeResult#run(java.lang.Object) CodeTimeResult.run()}
     * @param maxTime Correspond au temps maximum d'exécution autorisé au code
     * @param defaultResult Correspond à la valeur par défaut qui sera renvoyée si la méthode {@link CodeTimeResult#run(java.lang.Object) CodeTimeResult.run()} met trop de temps à s'exécuter
     * @return Retourne la valeur de la méthode {@link CodeTimeResult#run(java.lang.Object) CodeTimeResult.run()} ou la valeur par défaut
     */
    @SuppressWarnings("SleepWhileInLoop")
    public static <In, Out> Out run(CodeTimeResult<In, Out> code, In in, Millisecond maxTime, Out defaultResult){
        Object[] outs = new Object[1];
        boolean[] found = new boolean[]{false};
        Thread t = new Thread(() -> {
            try {
                Out out = (Out) code.run(in);
                outs[0] = out;
                found[0] = true;
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        });
        t.start();
        long start = new java.util.Date().getTime();
        try {
            while(((new java.util.Date().getTime() - start) < maxTime.getMilliseconds()) && !found[0]){
                Thread.sleep(1);
            }
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        if(found[0])
            return (Out) outs[0];
        else{
            t.interrupt();
            return defaultResult;
        }
    }
    
    /**
     * Execute la méthode {@link CodeTimeResult#run(java.lang.Object) CodeTimeResult.run()}.
     * Son exécution ne dépassera pas maxTime millisecondes. Si la méthode devait mettre plus de temps, alors celle-ci serait killé
     * @param <In> Correspond au type de valeur qui peut être fournie à la méthode {@link CodeTimeResult#run(java.lang.Object) CodeTimeResult.run()}
     * @param code Correspond au code à exécuter (cf: {@link CodeTimeResult})
     * @param in Correspond à une éventuelle valeur à fournir à la méthode {@link CodeTimeResult#run(java.lang.Object) CodeTimeResult.run()}
     * @param maxTime Correspond au temps maximum d'exécution autorisé au code
     */
    public static <In> void run(CodeTimeResult<In, Void> code, In in, Millisecond maxTime){
        TimedResult.<In, Void>run(code, in, maxTime, null);
    }
    
    /**
     * Execute la méthode {@link CodeTimeResult#run(java.lang.Object) CodeTimeResult.run()}.
     * Son exécution ne dépassera pas maxTime millisecondes. Si la méthode devait mettre plus de temps, alors celle-ci serait killé et un résultat par défaut serait renvoyé
     * @param <Out> Correspond au type de valeur retournée
     * @param code Correspond au code à exécuter (cf: {@link CodeTimeResult})
     * @param maxTime Correspond au temps maximum d'exécution autorisé au code
     * @param defaultResult Correspond à la valeur par défaut qui sera renvoyée si la méthode {@link CodeTimeResult#run(java.lang.Object) CodeTimeResult.run()} met trop de temps à s'exécuter
     * @return Retourne la valeur de la méthode {@link CodeTimeResult#run(java.lang.Object) CodeTimeResult.run()} ou la valeur par défaut
     */
    public static <Out> Out run(CodeTimeResult<Void, Out> code, Millisecond maxTime, Out defaultResult){
        return TimedResult.<Void, Out>run(code, null, maxTime, defaultResult);
    }
    
    /**
     * Execute la méthode {@link CodeTimeResult#run(java.lang.Object) CodeTimeResult.run()}.
     * Son exécution ne dépassera pas maxTime millisecondes. Si la méthode devait mettre plus de temps, alors celle-ci serait killé
     * @param code Correspond au code à exécuter (cf: {@link CodeTimeResult})
     * @param maxTime Correspond au temps maximum d'exécution autorisé au code
     */
    public static void run(CodeTimeResult<Void, Void> code, Millisecond maxTime){
        TimedResult.<Void, Void>run(code, null, maxTime, null);
    }
    
    
    
}