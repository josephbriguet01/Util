/*
 * Copyright (C) JasonPercus Systems, Inc - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by JasonPercus, 06/2021
 */
package com.jasonpercus.util.code;



import com.github.javaparser.ast.expr.AnnotationExpr;



/**
 * Cette classe représente une annotation (en bout de code) (ex: @SuppressWarnings)
 * @author JasonPercus
 * @version 1.0
 */
@SuppressWarnings({"NestedSynchronizedStatement", "SynchronizeOnNonFinalField"})
public class Annotation_element extends Code {
    
    
    
//ATTRIBUT
    /**
     * Correspond à la valeur d'une annotation (ex: @SuppressWarnings({"NestedSynchronizedStatement", "SynchronizeOnNonFinalField"}), SuppressWarning correspond au nom et {"NestedSynchronizedStatement", "SynchronizeOnNonFinalField"} correspond à la valeur)
     */
    private String value;

    
    
//CONSTRUCTORS
    /**
     * Crée une annotation par défaut
     */
    public Annotation_element() {
        super();
    }
    
    /**
     * Crée une annotation (ex: @SuppressWarnings({"NestedSynchronizedStatement", "SynchronizeOnNonFinalField"})
     * @param creator Correspond au creator qui crée les sous-blocs de code
     * @param ae Correspond au bloc représentant l'annotation de JavaParser
     * @param parent Correspond au code parent de celui-ci
     * @deprecated Ne pas utiliser
     */
    @Deprecated
    protected Annotation_element(CodeCreator creator, AnnotationExpr ae, Code parent) {
        super(creator, ae.getNameAsString(), parent);
        String v = ae.toString()
                .replace("@" + ae.getNameAsString(), "")
                .replace("\\(\\s+\\)", "()");
        
        if(v.isEmpty() || v.equals("()"))
            value = "";
        else
            value = v.substring(1, v.length()-1);
    }

    
    
//GETTER & SETTER
    /**
     * Renvoie la valeur de l'annotation (ex: @SuppressWarnings({"NestedSynchronizedStatement", "SynchronizeOnNonFinalField"}), SuppressWarning correspond au nom et {"NestedSynchronizedStatement", "SynchronizeOnNonFinalField"} correspond à la valeur)
     * @return Retourne la valeur de l'annotation
     */
    public synchronized final String getValue() {
        return value;
    }

    /**
     * Modifie la valeur de l'annotation (ex: @SuppressWarnings({"NestedSynchronizedStatement", "SynchronizeOnNonFinalField"}), SuppressWarning correspond au nom et {"NestedSynchronizedStatement", "SynchronizeOnNonFinalField"} correspond à la valeur)
     * @param value Correspond à la nouvelle valeur de l'annotation
     */
    public synchronized final void setValue(String value) {
        this.value = value;
    }
    
    
    
//METHODES PUBLICS
    /**
     * Renvoie la signature de l'annotation (ex: @SuppressWarnings({"NestedSynchronizedStatement", "SynchronizeOnNonFinalField"}))
     * @return Retourne la signature de l'annotation
     */
    @Override
    public String getSignature(){
        StringBuilder sb = new StringBuilder("@" + getName());
        if(!value.isEmpty())
            sb.append("(").append(value).append(")");
        return sb.toString();
    }

    /**
     * Renvoie l'annotation sous la forme d'une chaîne de caractères
     * @return Retourne l'annotation sous la forme d'une chaîne de caractères
     */
    @Override
    public String toString() {
        String c = getSignature();
        if(asComment())
            return "//" + c.replace("\n", "\n//");
        else
            return c;
    }
    
    
    
}