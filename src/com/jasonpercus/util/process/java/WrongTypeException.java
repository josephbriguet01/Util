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
 * Cette classe d'exception permet de prévenir le développeur que celui-ci s'est trompé entre les utilisations des méthodes du process père et des méthodes des process fils
 * @see InterPipe
 * @author JasonPercus
 * @version 1.0
 */
public class WrongTypeException extends RuntimeException {
    
    
    
    // <editor-fold defaultstate="collapsed" desc="SERIAL_VERSION_UID">
    /**
     * Correspond au numéro de série qui identifie le type de dé/sérialization utilisé pour l'objet
     */
    private static final long serialVersionUID = 1L;
    // </editor-fold>

    
    
//CONSTRUCTOR
    /**
     * Crée une exception
     * @param message Correspond au message de l'exception
     */
    protected WrongTypeException(String message) {
        super(message);
    }
    
    
    
//METHODES PROTECTEDS
    /**
     * S'il s'agit d'un processus père et non d'un processus fils
     * @return Retourne l'exception créée
     */
    protected static WrongTypeException isParentType(){
        return new WrongTypeException("This process is a parent process and not a child process !");
    }
    
    /**
     * S'il s'agit d'un processus fils et non d'un processus père
     * @return Retourne l'exception créée
     */
    protected static WrongTypeException isChildType(){
        return new WrongTypeException("This process is a child process and not a parent process !");
    }
    
    /**
     * S'il s'agit d'un processus père et non d'un processus fils (synchrone)
     * @return Retourne l'exception créée
     */
    protected static WrongTypeException isParentTypeSync(){
        return new WrongTypeException("This process is a parent process and not a child process ! Use the method "+InterPipe.class.getSimpleName()+".getSync(String id, java.io.Serializable obj)");
    }
    
    /**
     * S'il s'agit d'un processus fils et non d'un processus père (synchrone)
     * @return Retourne l'exception créée
     */
    protected static WrongTypeException isChildTypeSync(){
        return new WrongTypeException("This process is a child process and not a parent process ! Use the method "+InterPipe.class.getSimpleName()+".getSync(java.io.Serializable obj, boolean error)");
    }
    
    /**
     * S'il s'agit d'un processus père à stopper et non d'un processus fils
     * @return Retourne l'exception créée
     */
    protected static WrongTypeException isParentTypeStop(){
        return new WrongTypeException("This process is a parent process and not a child process ! Use the method "+InterPipe.class.getSimpleName()+".stop(String id)");
    }
    
    /**
     * S'il s'agit d'un processus sils à stopper et non d'un processus père
     * @return Retourne l'exception créée
     */
    protected static WrongTypeException isChildTypeStop(){
        return new WrongTypeException("This process is a child process and not a parent process ! Use the method "+InterPipe.class.getSimpleName()+".stop()");
    }
    
    /**
     * Si la méthode d'envoie d'un objet ne correpond pas au processus père
     * @return Retourne l'exception créée
     */
    protected static WrongTypeException isParentTypeOut(){
        return new WrongTypeException("This process is a parent process and not a child process ! Use the method "+InterPipe.class.getSimpleName()+".out.print(String processId, Serializable obj)");
    }
    
    /**
     * Si la méthode d'envoie d'un objet ne correpond pas au processus fils
     * @return Retourne l'exception créée
     */
    protected static WrongTypeException isChildTypeOut(){
        return new WrongTypeException("This process is a child process and not a parent process ! Use the method "+InterPipe.class.getSimpleName()+".out.print(Serializable obj)");
    }
    
    /**
     * Si la méthode de réception d'un objet ne correpond pas au processus père
     * @return Retourne l'exception créée
     */
    protected static WrongTypeException isParentTypeIn(){
        return new WrongTypeException("This process is a parent process and not a child process ! Use the method "+InterPipe.class.getSimpleName()+".in.read(IReceivedForParents received)");
    }
    
    /**
     * Si la méthode de réception d'un objet ne correpond pas au processus fils
     * @return Retourne l'exception créée
     */
    protected static WrongTypeException isChildTypeIn(){
        return new WrongTypeException("This process is a child process and not a parent process ! Use the method "+InterPipe.class.getSimpleName()+".in.read(IReceivedForChildren received)");
    }
    
    
    
}