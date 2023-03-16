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
 * Cette classe représente les imports
 * @author JasonPercus
 * @version 1.0
 */
public class Import_element extends Code implements Comparable<Code> {
    
    
    
//ATTRIBUTS
    /**
     * Détermine s'il s'agit d'un import static ou pas
     */
    private boolean _static;
    
    /**
     * Détermine s'il s'agit d'un import global (ex: java.io.*)
     */
    private boolean _all;

    
    
//CONSTRUCTORS
    /**
     * Crée un import
     */
    public Import_element() {
        super();
    }
    
    /**
     * Crée un import (ex: import java.io.*;)
     * @param name Correspond au nom de l'import (ex: java.io.*)
     * @param _static Détermine si l'import est static ou pas
     * @param all Détermine si l'import est un import global (ex: java.io.* au lieu de java.io.File)
     */
    public Import_element(String name, boolean _static, boolean all) {
        this(null, name, _static, all, null);
    }
    
    /**
     * Crée un import (ex: import java.io.*;)
     * @param creator Correspond au créateur de bout de code
     * @param name Correspond au nom de l'import (ex: java.io.*)
     * @param _static Détermine si l'import est static ou pas
     * @param all Détermine si l'import est un import global (ex: java.io.* au lieu de java.io.File)
     * @param parent Correspond au code parent de celui-ci
     * @deprecated Ne pas utiliser
     */
    @Deprecated
    protected Import_element(CodeCreator creator, String name, boolean _static, boolean all, Code parent) {
        super(creator, name, parent);
        this._static = _static;
        this._all    = all;
    }

    
    
//GETTERS & SETTERS
    /**
     * Détermine si l'import est static ou pas
     * @return Retourne true s'il l'est sinon false
     */
    public synchronized final boolean isStatic() {
        return _static;
    }

    /**
     * Modifie si oui ou nom l'import doit être static ou pas
     * @param _static Correspond à true s'il doit l'être, sinon false
     */
    public synchronized final void setStatic(boolean _static) {
        this._static = _static;
    }

    /**
     * Détermine si l'import est un import global (ex: java.io.* au lieu de java.io.File)
     * @return Retourne true s'il l'est, sinon false
     */
    public synchronized final boolean isAll() {
        return _all;
    }

    /**
     * Modifie si oui ou non l'import doit être global ou pas (ex: java.io.* au lieu de java.io.File)
     * @param _all Correspond à true, s'il doit l'être, sinon false
     */
    public synchronized final void setAll(boolean _all) {
        this._all = _all;
    }
    
    /**
     * Renvoie le nom simple de l'import, sans son package (ex: s'il y a l'import import java.io.File; File est le SimpleName)
     * @return Retourne le nom simple de l'import
     */
    public synchronized final String getSimpleName(){
        int index = getName().lastIndexOf(".");
        if(index>-1){
            if(index == 0)
                return getName();
            else
                return getName().substring(index+1);
        }
        return getName();
    }
    
    /**
     * Renvoie le package de l'import, sans son nom simple (ex: s'il y a l'import import java.io.File; java.io est le package)
     * @return Retourne le package de l'import
     */
    public synchronized final String getPackage(){
        int index = getName().lastIndexOf(".");
        if(index>-1){
            if(index == 0)
                return "";
            else
                return getName().substring(0, index);
        }
        return "";
    }
    
    
    
//METHODES PUBLICS
    /**
     * Renvoie la signature de l'import
     * @return Retourne la signature de l'import (ex: import java.io.*)
     */
    @Override
    public String getSignature(){
        String chain = "import ";
        if(_static)
            chain += "static ";
        chain += getName();
        if(_all)
            chain += ".*";
        return chain;
    }

    /**
     * Renvoie le hashCode de l'import
     * @return Retourne le hashCode de l'import
     */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + (this._static ? 1 : 0);
        hash = 67 * hash + (this._all ? 1 : 0);
        hash = 67 * hash + java.util.Objects.hashCode(this.getName());
        return hash;
    }

    /**
     * Détermine si deux imports sont identiques ou pas
     * @param obj Correspond au second objet à comparer au courant
     * @return Retourne true, s'ils sont identiques, sinon false
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Import_element other = (Import_element) obj;
        if (this._static != other._static) {
            return false;
        }
        if (this._all != other._all) {
            return false;
        }
        return java.util.Objects.equals(this.getName(), other.getName());
    }

    /**
     * Renvoie l'import sous la forme d'une chaîne de caractères
     * @return Retourne l'import sous la forme d'une chaîne de caractères (ex: import java.io.*;)
     */
    @Override
    public String toString() {
        if(asComment())
            return "//" + getSignature() + ";";
        else
            return getSignature() + ";";
    }

    /**
     * Compare deux imports
     * @param o Correspond au second objet à comparer au courant
     * @return Retourne le résultat de la comparaison
     */
    @Override
    public int compareTo(Code o) {
        return this.toString().compareTo(o.toString());
    }
    
    
    
}