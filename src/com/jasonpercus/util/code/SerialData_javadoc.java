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
 * Cette classe représente l'élément javadoc @serialData
 * @author JasonPercus
 * @version 1.0
 */
public class SerialData_javadoc extends Element_javadoc {

    
    
//CONSTRUCTORS
    /**
     * Crée un serialData de javadoc par défaut
     */
    public SerialData_javadoc() {
        this("");
    }
    
    /**
     * Crée un serialData de javadoc
     * @param serialData Correspond au serialData du bout de code
     */
    public SerialData_javadoc(String serialData) {
        this(null, serialData, null);
    }
    
    /**
     * Crée un serialData de javadoc
     * @param creator Correspond au créateur de bout de code
     * @param serialData Correspond au serialData du bout de code
     * @param parent COrrespond au code parent de celui-ci
     */
    protected SerialData_javadoc(CodeCreator creator, String serialData, Code parent) {
        super(creator, "serialData", serialData, parent);
    }
    
    
    
//GETTER & SETTER
    /**
     * Renvoie le serialData du bloc javadoc
     * @return Retourne le serialData du bloc javadoc
     */
    public synchronized final String getSerialData() {
        return this.getDescription();
    }

    /**
     * Modifie le serialData du bloc javadoc
     * @param serialData Correspond au nouvel serialData du bout de code
     */
    public synchronized final void setSerialData(String serialData) {
        this.setDescription(serialData);
    }

    
    
//METHODE PUBLIC
    /**
     * Renvoie la priorité de l'élément par rapport aux autres dans un bloc javadoc. Plus la valeur est petite, plus la priorité est grande, et inversement plus la valeur est grande, plus la priorité d'affichage est petite
     * @return Retourne 11
     */
    @Override
    public final int priority() {
        return 11;
    }
    
    
    
}