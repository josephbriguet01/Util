/*
 * Copyright (C) JasonPercus Systems, Inc - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by JasonPercus, 05/2021
 */
package com.jasonpercus.util.process.java;



/**
 * Cette interface permet à un processus ({@link InterPipe}) fils de récupérer les objets envoyés d'un processus père
 * @see InterPipe
 * @author JasonPercus
 * @version 1.0
 */
public interface IReceivedForChildren {
    
    
    
    /**
     * Récupère un objet envoyé du processus père
     * @param object Correspond à l'objet récupéré
     */
    public void received(Object object);
    
    /**
     * Récupère de manière synchrone un objet envoyé d'un processus père et lui renvoie un résultat
     * @param object Correspond à l'objet récupéré
     * @return Retourne la réponse à la demande
     */
    public java.io.Serializable receivedSync(Object object);
    
    
    
}