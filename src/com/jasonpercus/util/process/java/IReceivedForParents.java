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
 * Cette interface permet à un processus ({@link InterPipe}) père de récupérer les objets envoyés d'un processus fils
 * @see InterPipe
 * @author JasonPercus
 * @version 1.0
 */
public interface IReceivedForParents {
    
    
    
    /**
     * Récupère un objet envoyé d'un processus fils
     * @param id Correspond à l'id du processus fils qui a envoyé l'objet
     * @param object Correspond à l'objet récupéré
     * @param error Détermine si l'objet est une erreur ou pas
     */
    public void received(String id, Object object, boolean error);
    
    /**
     * Récupère de manière synchrone un objet envoyé d'un processus fils et lui renvoie un résultat
     * @param id Correspond à l'id du processus fils qui a envoyé l'objet
     * @param object Correspond à l'objet récupéré
     * @param error Détermine si l'objet est une erreur ou pas
     * @return Retourne la réponse à la demande
     */
    public java.io.Serializable receivedSync(String id, Object object, boolean error);
    
    
    
}