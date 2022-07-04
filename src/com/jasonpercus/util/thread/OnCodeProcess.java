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
 * Cette interface permet d'implémenter un objet ayant les caractéristiques d'écoute d'un objet {@link CodeProcessing}.
 * <pre>
 * {@code
 * Ordre d'appel des méthodes:
 *  1: start()
 *  2: before()
 *   #3: During.run() [exécuté dans un autre thread ce qui fait que le point 4 est appelé quasiment instantanément sur le thread courant]
 *  4: after() [qui ne connait pas le/les résultat(s) du point 3 puisqu'ils s'exécutent sur deux threads différents]
 *  5: whileWaiting() [qui boucle tant que le point 3 n'est pas terminé. Ce point bloque le thread courant]
 *  6: end() [le point 5 est débloqué et tous les résultats sont connus et transmis]
 * }</pre>
 * @param <In> Correspond au type d'entrée fournit au départ (voir la méthode: {@link CodeProcessing#start(com.jasonpercus.util.thread.OnCodeProcess, java.lang.Object, com.jasonpercus.util.thread.During...) CodeProcessing.start()})
 * @param <StartOut> Correspond au type de donnée renvoyé par la méthode {@link #start(java.lang.Object) start()}
 * @param <BeforeOut> Correspond au type de donnée renvoyé par la méthode {@link #before(java.lang.Object, java.lang.Object) before()}
 * @param <DuringOut> Correspond au type de donnée renvoyé par la méthode {@link During#run(java.lang.Object, java.lang.Object, java.lang.Object) During.run()}
 * @param <AfterOut> Correspond au type de donnée renvoyé par la méthode {@link #after(java.lang.Object, java.lang.Object, java.lang.Object) after()}
 * @see CodeProcessing
 * @author JasonPercus
 * @version 1.0
 */
public interface OnCodeProcess<In, StartOut, BeforeOut, DuringOut, AfterOut> {
    
    
    
    /**
     * Est appelée une fois que {@link CodeProcessing#start(com.jasonpercus.util.thread.OnCodeProcess, java.lang.Object, com.jasonpercus.util.thread.During...) CodeProcessing.start()} est appelée.
     * Ainsi on peut dire que cette méthode sert essentiellement à montrer que la méthode {@link CodeProcessing#start(com.jasonpercus.util.thread.OnCodeProcess, java.lang.Object, com.jasonpercus.util.thread.During...) CodeProcessing.start()} est lancée
     * @param in Correspond au type d'entrée fournit au départ (voir la méthode: {@link CodeProcessing#start(com.jasonpercus.util.thread.OnCodeProcess, java.lang.Object, com.jasonpercus.util.thread.During...) CodeProcessing.start()})
     * @return Retourne un éventuel résultat
     */
    public StartOut start(In in);
    
    /**
     * Est appelée après la méthode {@link #start(java.lang.Object) start()}, mais juste avant de lancer la/les méthode(s) {@link During#run(java.lang.Object, java.lang.Object, java.lang.Object) During.run()}
     * Ainsi on peut dire que cette méthode sert essentiellement à initialiser certaines variables...
     * @param in Correspond au type d'entrée fournit au départ (voir la méthode: {@link CodeProcessing#start(com.jasonpercus.util.thread.OnCodeProcess, java.lang.Object, com.jasonpercus.util.thread.During...) CodeProcessing.start()})
     * @param resultStart Correspond à l'éventuel retour de la méthode {@link #start(java.lang.Object) start()}
     * @return Retourne un éventuel résultat
     */
    public BeforeOut before(In in, StartOut resultStart);
    
    /**
     * Est appelée après ou pendant le lancement de la/les méthode(s) {@link During#run(java.lang.Object, java.lang.Object, java.lang.Object) During.run()}
     * Ainsi on peut dire que cette méthode sert essentiellement à initialiser / récupérer certaines variables post lancement du processus
     * @param in Correspond au type d'entrée fournit au départ (voir la méthode: {@link CodeProcessing#start(com.jasonpercus.util.thread.OnCodeProcess, java.lang.Object, com.jasonpercus.util.thread.During...) CodeProcessing.start()})
     * @param resultStart Correspond à l'éventuel retour de la méthode {@link #start(java.lang.Object) start()}
     * @param resultBefore Correspond à l'éventuel retour de la méthode {@link #before(java.lang.Object, java.lang.Object) before()}
     * @return Retourne un éventuel résultat
     */
    public AfterOut after(In in, StartOut resultStart, BeforeOut resultBefore);
    
    /**
     * Est appelé en boucle pendant l'éxécution de la méthode {@link During#run(java.lang.Object, java.lang.Object, java.lang.Object) During.run()}
     * Ainsi on peut dire que cette méthode sert essentiellement à contrôler la durée d'exécution du processus
     * @param process Correspond à l'objet process {@link CodeProcessing}
     * @param timeSpent Correspond au temps passé à exécuter la méthode {@link CodeProcessing#start(com.jasonpercus.util.thread.OnCodeProcess, java.lang.Object, com.jasonpercus.util.thread.During...) CodeProcessing.start()}
     */
    public void whileWaiting(CodeProcessing process, long timeSpent);
    
    /**
     * Est appelé une fois que la/les méthode(s) {@link During#run(java.lang.Object, java.lang.Object, java.lang.Object) During.run()} sont terminées
     * @param in Correspond au type d'entrée fournit au départ (voir la méthode: {@link CodeProcessing#start(com.jasonpercus.util.thread.OnCodeProcess, java.lang.Object, com.jasonpercus.util.thread.During...) CodeProcessing.start()})
     * @param resultStart Correspond à l'éventuel retour de la méthode {@link #start(java.lang.Object) start()}
     * @param resultBefore Correspond à l'éventuel retour de la méthode {@link #before(java.lang.Object, java.lang.Object) before()}
     * @param resultDuring Correspond à la liste des résultats de/des méthode(s) {@link During#run(java.lang.Object, java.lang.Object, java.lang.Object) During.run()} exécutée(s). Si deux objets {@link During} ont été fourni, alors la liste contiendra deux résultats. Ces résultats sont placés dans la liste dans l'ordre d'arrivé des objets {@link During} dans la méthode {@link CodeProcessing#start(com.jasonpercus.util.thread.OnCodeProcess, java.lang.Object, com.jasonpercus.util.thread.During...) CodeProcessing.start()}
     * @param resultAfter Correspond à l'éventuel retour de la méthode {@link #after(java.lang.Object, java.lang.Object, java.lang.Object) after()}
     * @param durationProcess Correspond au temps total (en ms) qu'à passé la méthode {@link CodeProcessing#start(com.jasonpercus.util.thread.OnCodeProcess, java.lang.Object, com.jasonpercus.util.thread.During...) CodeProcessing.start()} à être exécuté
     */
    public void end(In in, StartOut resultStart, BeforeOut resultBefore, java.util.List<DuringOut> resultDuring, AfterOut resultAfter, long durationProcess);
    
    
    
}