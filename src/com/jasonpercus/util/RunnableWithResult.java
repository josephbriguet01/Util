/*
 * Copyright (C) JasonPercus Systems, Inc - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by JasonPercus, August 2021
 */
package com.jasonpercus.util;



/**
 * The <code>Runnable</code> interface should be implemented by any
 * class whose instances are intended to be executed by a thread. The
 * class must define a method of no arguments called <code>run</code>.
 * <p>
 * This interface is designed to provide a common protocol for objects that
 * wish to execute code while they are active. For example,
 * <code>Runnable</code> is implemented by class <code>Thread</code>.
 * Being active simply means that a thread has been started and has not
 * yet been stopped.
 * <p>
 * In addition, <code>Runnable</code> provides the means for a class to be
 * active while not subclassing <code>Thread</code>. A class that implements
 * <code>Runnable</code> can run without subclassing <code>Thread</code>
 * by instantiating a <code>Thread</code> instance and passing itself in
 * as the target.  In most cases, the <code>Runnable</code> interface should
 * be used if you are only planning to override the <code>run()</code>
 * method and no other <code>Thread</code> methods.
 * This is important because classes should not be subclassed
 * unless the programmer intends on modifying or enhancing the fundamental
 * behavior of the class.
 *
 * @author JasonPercus
 * @version 1.0
 * @see     java.lang.Thread
 * @see     java.util.concurrent.Callable
 * @since   JDK1.0
 */
public abstract class RunnableWithResult implements Runnable {
    
    
    
//ATTRIBUTS
    /**
     * Correspond à la liste des paramètres à donner à la méthode {@link #run()}
     */
    protected Object[] params;
    
    /**
     * Correspond au résultat de la méthode {@link #run()}
     */
    private Object result;

    
    
//CONSTRUCTORS
    /**
     * Crée un objet Runnable qui aura pour fonction de retourner un résultat
     */
    public RunnableWithResult() {
        this.params = new Object[0];
    }

    /**
     * Crée un objet Runnable qui aura pour fonction de retourner un résultat
     * @param params Correspond à la liste des paramètres à donner à la méthode {@link #run()}
     */
    public RunnableWithResult(Object... params) {
        if (params == null) {
            this.params = new Object[0];
        } else {
            this.params = params;
        }
    }

    
    
//METHODES PUBLICS
    /**
     * Renvoie la liste des paramètres à donner à la méthode {@link #run()}
     * @return Retourne la liste des paramètres à donner à la méthode {@link #run()}
     */
    public final Object[] getParams() {
        return params;
    }

    /**
     * Renvoie le résultat que doit fournir la méthode {@link #run()} à la fin de son exécution grâce à la méthode {@link #returnResult(java.lang.Object)}
     * @return Retourne le résultat que doit fournir la méthode {@link #run()}
     */
    public final Object getResult() {
        return result;
    }

    /**
     * Définit le résultat de l'exécution de la méthode {@link #run()}
     * @param result Correspond au résultat que renvoie la méthode {@link #run()}
     */
    protected final void returnResult(Object result){
        this.result = result;
    }
    
    
    
}