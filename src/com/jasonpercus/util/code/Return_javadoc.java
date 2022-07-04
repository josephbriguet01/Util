/*
 * Copyright (C) JasonPercus Systems, Inc - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by JasonPercus, 06/2021
 */
package com.jasonpercus.util.code;



/**
 * Cette classe représente l'élément javadoc @return
 * @author JasonPercus
 * @version 1.0
 */
public class Return_javadoc extends Element_javadoc {

    
    
//CONSTRUCTORS
    /**
     * Crée un retour de javadoc par défaut
     */
    public Return_javadoc() {
        this("");
    }
    
    /**
     * Crée un retour de javadoc
     * @param _return Correspond au retour du bout de code. Ce que le bout de code doit renvoyer (ex: @retour [Retourne true si..., sinon false])
     */
    public Return_javadoc(String _return) {
        this(null, _return, null);
    }
    
    /**
     * Crée un retour de javadoc
     * @param creator Correspond au créateur de bout de code
     * @param _return Correspond au retour du bout de code. Ce que le bout de code doit renvoyer (ex: @retour [Retourne true si..., sinon false])
     * @param parent Correspond au code parent de celui-ci
     */
    protected Return_javadoc(CodeCreator creator, String _return, Code parent) {
        super(creator, "return", _return, parent);
    }


    
    
//GETTER & SETTER
    /**
     * Renvoie le retour de javadoc (ex: @retour [Retourne true si..., sinon false])
     * @return Retourne le retour de javadoc
     */
    public synchronized final String getReturn() {
        return this.getDescription();
    }

    /**
     * Modifie le retour de javadoc (ex: @retour [Retourne true si..., sinon false])
     * @param _return Correspond au nouveau retour de javadoc
     */
    public synchronized final void setReturn(String _return) {
        this.setDescription(_return);
    }

    
    
//METHODE PUBLIC
    /**
     * Renvoie la priorité de l'élément par rapport aux autres dans un bloc javadoc. Plus la valeur est petite, plus la priorité est grande, et inversement plus la valeur est grande, plus la priorité d'affichage est petite
     * @return Retourne 13
     */
    @Override
    public int priority() {
        return 13;
    }
    
    
    
}