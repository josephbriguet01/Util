/*
 * Copyright (C) JasonPercus Systems, Inc - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by JasonPercus, 09/2022
 */
package com.jasonpercus.util.array;



/**
 * Cette classe permet de créer une file (de taille fixe) de données. La donnée la plus ancienne est consommé dès qu'une nouvelle donnée essaye de rentrer et agrandir la taille de l'{@linkplain ArrayQueue}. Cette classe permet de créer une fenêtre de taille fixe de détection de données
 * @author JasonPercus
 * @param <O> Correspond au type d'objet contenu dans l'{@linkplain ArrayQueue}
 * @version 1.0
 */
public class ArrayQueueDetector<O> extends ArrayQueue<O> {
    
    
    
//ATTRIBUTS
    /**
     * Correspond à la liste des modèles des données à détecter
     */
    private final java.util.List<O[]> toDetects;
    
    /**
     * Correspond au listener qui sera prévenue de chaque détection
     */
    private final IDetector listener;

    
    
//CONSTRUCTOR
    /**
     * Crée un objet {@link ArrayQueueDetector}
     * @param size Correspond à la taille de l'objet
     * @param listener Correspond au listener qui sera prévenue de chaque détection dans les données
     */
    public ArrayQueueDetector(int size, IDetector<O> listener) {
        super(size);
        this.toDetects = new java.util.ArrayList<>();
        this.listener  = listener;
    }
    
    
    
//METHODES PUBLICS
    /**
     * Ajoute un modèle des données à détecter
     * @param toDetect Correspond au modèle des données à ajouter
     */
    public void addDetector(O[] toDetect){
        if(toDetect == null)
            throw new NullPointerException("toDetect is null !");
        if(toDetect.length != buffer.length)
            throw new RuntimeException("The size of the array to detect is different from the size of the " + ArrayQueue.class.getSimpleName() + " !");
        if(!toDetects.contains(toDetect))
            toDetects.add(toDetect);
    }
    
    /**
     * Supprime un modèle des données à détecter
     * @param toDetect Correspond au modèle des données à supprimer
     */
    public void removeDetector(O[] toDetect){
        if(toDetect != null)
            toDetects.remove(toDetect);
    }
    
    /**
     * Ajoute une donnée à la fin de la file. Ce qui a pour effet de détruire la donnée la plus ancienne de manière à permettre le décallage de toutes les données et pouvoir rentrer la nouvelle donnée
     * @param o Correspond à la nouvelle donnée à ajouter
     * @return Retourne la valeur détruite avant le décallage puis l'ajout de la nouvelle valeur
     */
    @Override
    public O put(O o){
        O old = super.put(o);
        if(listener != null){
            O[] detected = detect();
            if(detected != null){
                listener.sequenceDetected(detected);
            }
        }
        return old;
    }
    
    
    
//METHODES PRIVATES
    /**
     * Détecte si oui ou non il existe un modèle de détecté dans l'{@link ArrayQueueDetector}
     * @return Retourne le modèle détecté ou null, si aucun n'a été trouvé
     */
    private O[] detect(){
        if(toDetects != null){
            for(O[] array : toDetects){
                if(detect(array))
                    return array;
            }
        }
        return null;
    }
    
    /**
     * Détermine si ce modèle apparait dans l'{@link ArrayQueueDetector}
     * @param toDetect Correspond au modèle à tester
     * @return Retourne true s'il s'agit bien de ce modèle d'affiché dans l'{@link ArrayQueueDetector}, sinon false
     */
    private boolean detect(O[] toDetect){
        for(int i=0;i<toDetect.length;i++){
            O o1 = toDetect[i];
            O o2 = get(i);
            if(!o1.equals(o2)){
                return false;
            }
        }
        return true;
    }
    
    
    
//INTERFACE
    /**
     * Cette interface permet de tenir au courant un objet des détections réalisées sur un objet
     * @author JasonPercus
     * @param <O> Correspond au type d'objet contenu dans l'{@linkplain ArrayQueue}
     * @version 1.0
     */
    public interface IDetector<O> {
        
        public void sequenceDetected(O[] sequence);
        
    }
    
    
    
}