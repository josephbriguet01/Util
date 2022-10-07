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
 * Cette interface est un peu similaire à l'interface {@link Runnable} avec pour différence qu'elle prend un paramètre et renvoie un résultat. Cependant, elle n'est seulement utilisable qu'avec les méthodes de la classe {@link TimedResult}
 * @param <In> Correspond au type de valeur qui peut être fournie à la méthode {@link #run(java.lang.Object) run()}
 * @param <Out> Correspond au type de valeur retournée par la méthode {@link #run(java.lang.Object) run()}
 * @see TimedResult
 * @author JasonPercus
 * @version 1.0
 */
public interface CodeTimeResult<In, Out> {
    
    
    
    /**
     * Est appelée à être exécutée dans un temps imparti 
     * @param in Correspond à une éventuelle valeur à fournir
     * @return Retourne la valeur qui doit être renvoyé dans le temps imparti
     * @throws InterruptedException Si l'exécution de cette méthode n'est pas réalisé dans le temps imparti, cette exception est levée. Il ne faut surtout pas l'intercepter ou vous risquez de figer le programme. Laissez-là remonter !
     */
    public Out run(In in) throws InterruptedException;
    
    
    
}