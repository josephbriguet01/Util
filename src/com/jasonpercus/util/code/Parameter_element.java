/*
 * Copyright (C) JasonPercus Systems, Inc - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by JasonPercus, 06/2021
 */
package com.jasonpercus.util.code;



import com.github.javaparser.ast.body.Parameter;



/**
 * Cette classe représente un paramètre de méthode
 * @author JasonPercus
 * @version 1.0
 */
@SuppressWarnings({"NestedSynchronizedStatement", "SynchronizeOnNonFinalField"})
public class Parameter_element extends Element_element {

    
    
//ATTRIBUTS
    /**
     * Détermine si la méthode est final ou pas
     */
    private boolean _final;
    
    /**
     * Correspond au type de paramètre
     */
    private String  _type;
    
    /**
     * Détermine s'il s'agit d'un tableau de paramètre avec les [...], genre String...var
     */
    private boolean _varArgs;

    
    
//CONSTRUCTORS
    /**
     * Crée un paramètre
     */
    public Parameter_element() {
        super();
    }
    
    /**
     * Crée un paramètre
     * @param creator Correspond au creator qui crée les sous-blocs de code
     * @param p Correspond au bloc représentant le paramètre de JavaParser
     * @param parent Correspond au constructeur ou méthode parent de ce paramètre
     * @deprecated Ne pas utiliser
     */
    protected Parameter_element(CodeCreator creator, Parameter p, Element_element parent) {
        super(creator, p.getNameAsString(), false, false, false, false, parent);
        this._final     = p.isFinal();
        this._type      = p.getType().asString();
        this._varArgs   = p.isVarArgs();
    }
    
    
    
//GETTERS & SETTERS
    /**
     * Détermine si le paramètre est final ou pas
     * @return True s'il l'est, sinon false
     */
    public synchronized final boolean isFinal() {
        return _final;
    }

    /**
     * Modifie si oui ou non le paramètre doit être final ou pas
     * @param _final True s'il doit l'être, sinon false
     */
    public synchronized final void setFinal(boolean _final) {
        this._final = _final;
    }

    /**
     * Renvoie le type de paramètre
     * @return Retourne le type de paramètre
     */
    public synchronized final String getType() {
        return _type;
    }

    /**
     * Modifie le type de paramètre
     * @param type Correspond au type de paramètre
     */
    public synchronized final void setType(String type) {
        this._type = type;
    }

    /**
     * Détermine si l'argument est un tableau d'argument (ex: String...var)
     * @return Retourne true si c'est le cas, sinon false
     */
    public synchronized final boolean isVarArgs() {
        return _varArgs;
    }

    /**
     * Modifie si oui ou non l'argument est un tableau d'arguments (ex: String...var)
     * @param varArgs Détermine s'il s'agira d'un tableau d'arguments
     */
    public synchronized final void setVarArgs(boolean varArgs) {
        this._varArgs = varArgs;
    }
    
    
    
//METHODES PUBLICS
    /**
     * Renvoie la signature du paramètre
     * @return Retourne la signature du paramètre
     */
    @Override
    public String getSignature(){
        StringBuilder chain = new StringBuilder();
        if(_final)
            chain.append("final ");
        chain.append(_type);
        if(_varArgs)
            chain.append("...");
        else
            chain.append(" ");
        chain.append(getName());
        return chain.toString();
    }

    /**
     * Renvoie le paramètre sous la forme d'une chaîne de caractères
     * @return Retourne le paramètre sous la forme d'une chaîne de caractères
     */
    @Override
    public String toString() {
        String c = getSignature();
        if(asComment())
            return "/*" + c + "*/";
        else
            return c;
    }
    
    
    
}