/*
 * Copyright (C) JasonPercus Systems, Inc - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by JasonPercus, 06/2021
 */
package com.jasonpercus.util.code;



import com.github.javaparser.ast.body.AnnotationMemberDeclaration;
import com.github.javaparser.ast.expr.AnnotationExpr;



/**
 * Cette classe représente un membre d'annotation d'une interface d'annotation (en bout de code) (ex: Mode mode() default Mode.IF_NOT_EXISTS)
 * @author JasonPercus
 * @version 1.0
 */
@SuppressWarnings({"NestedSynchronizedStatement", "SynchronizeOnNonFinalField", "LeakingThisInConstructor"})
public class AnnotationMember_element extends Element_element {

    
    
//ATTRIBUTS
    /**
     * Détermine si le membre d'annotation est abstrait ou pas
     */
    private boolean _abstract;
    
    /**
     * Correspond au type de retour du membre d'annotation
     */
    private String  _return;
    
    /**
     * Correspond à la valeur par défaut du membre d'annotation
     */
    private String  _defaultValue;
    
    /**
     * Détermine s'il y a une valeur par défaut pour ce membre d'annotation
     */
    private boolean _defaultValueExists;

    

//CONSTRUCTORS
    /**
     * Crée un membre d'annotation (ex: Mode mode() default Mode.IF_NOT_EXISTS)
     */
    public AnnotationMember_element() {
        super();
    }
    
    /**
     * Crée un membre d'annotation (ex: Mode mode() default Mode.IF_NOT_EXISTS)
     * @param creator Correspond au créateur de bout de code
     * @param amd Correspond au bloc représentant le membre d'annotation de JavaParser
     * @param parent Correspond à l'interface d'annotation parent de ce membre
     * @deprecated Ne pas utiliser
     */
    @Deprecated
    protected AnnotationMember_element(CodeCreator creator, AnnotationMemberDeclaration amd, Type_code parent) {
        super(null, amd.getName().toString(), amd.isPublic(), false, false, false, parent);
        this._abstract  = amd.isAbstract();
        this._return    = amd.getTypeAsString();
        
        if(amd.getDefaultValue().isPresent()){
            this._defaultValue = amd.getDefaultValue().get().toString();
            this._defaultValueExists = true;
        }
        
        if(amd.hasJavaDocComment())
            setJavadoc(creator.createJavadoc(creator, amd.getJavadocComment().get(), this));
        
        for(AnnotationExpr ae : amd.getAnnotations())
            addAnnotations(creator.createAnnotationElement(creator, ae, this));
        
    }

    
    
//GETTERS & SETTERS
    /**
     * Détermine si le membre d'annotation est abstrait ou pas (ex: [abstract] Mode mode() default Mode.IF_NOT_EXISTS)
     * @return True s'il l'est, sinon false
     */
    public synchronized final boolean isAbstract() {
        return _abstract;
    }

    /**
     * Modifie si oui ou non le membre d'annotation est abstrait ou pas (ex: [abstract] Mode mode() default Mode.IF_NOT_EXISTS)
     * @param _abstract True s'il doit l'être, sinon false
     */
    public synchronized final void setAbstract(boolean _abstract) {
        this._abstract = _abstract;
    }

    /**
     * Renvoie le type de retour du membre de l'annotation (ex: abstract [Mode] mode() default Mode.IF_NOT_EXISTS)
     * @return Retourne le type de retour du membre de l'annotation
     */
    public synchronized final String getReturn() {
        return _return;
    }

    /**
     * Modifie le type de retour du membre de l'annotation (ex: abstract [Mode] mode() default Mode.IF_NOT_EXISTS)
     * @param _return Correspond au nouveau type de retour
     */
    public synchronized final void setReturn(String _return) {
        this._return = _return;
    }

    /**
     * Renvoie la valeur par défaut du membre de l'annotation (ex: abstract Mode mode() default [Mode.IF_NOT_EXISTS])
     * @return Retourne la valeur par défaut du membre de l'annotation
     */
    public synchronized final String getDefaultValue() {
        return _defaultValue;
    }

    /**
     * Modifie la valeur par défaut du membre de l'annotation (ex: abstract Mode mode() default [Mode.IF_NOT_EXISTS])
     * @param _defaultValue Correspond à la nouvelle valeur par défaut du membre de l'annotation
     */
    public synchronized final void setDefaultValue(String _defaultValue) {
        this._defaultValue = _defaultValue;
    }

    /**
     * Détermine s'il existe une valeur par défaut du membre de l'annotation (ex: abstract Mode mode() [default Mode.IF_NOT_EXISTS])
     * @return Retourne true si c'est le cas, sinon false
     */
    public synchronized final boolean defaultValueExists() {
        return _defaultValueExists;
    }

    /**
     * Modifie si oui ou non il doit exister une valeur par défaut du membre de l'annotation (ex: abstract Mode mode() [default Mode.IF_NOT_EXISTS])
     * @param exists True si l'on veut une valeur par défaut, sinon false
     */
    public synchronized final void defaultValueExists(boolean exists) {
        this._defaultValueExists = exists;
    }
    
    
    
//METHODES PUBLICS
    /**
     * Renvoie la signature d'un membre d'annotation (ex: Mode mode() default Mode.IF_NOT_EXISTS)
     * @return Retourne la signature d'un membre d'annotation
     */
    @Override
    public String getSignature(){
        Element_element.Visibility visibility = getVisibility();
        if(visibility == Element_element.Visibility.PROTECTED || visibility == Element_element.Visibility.PRIVATE)
            visibility = Element_element.Visibility.DEFAULT;
        
        
        StringBuilder chain = new StringBuilder(visibility.toString());
        
        
        if(_abstract)
            chain.append("abstract ");
            
        
        chain.append(_return).append(" ").append(getName()).append("()");
        
        
        if(_defaultValueExists)
            chain.append(" default ").append(_defaultValue);
        
        
        return chain.toString();
    }
    
    /**
     * Renvoie le membre d'annotation sous la forme d'une chaîne de caractères
     * @return Retourne le membre d'annotation sous la forme d'une chaîne de caractères
     */
    @Override
    public String toString(){
        StringBuilder chain = new StringBuilder();
        
        
        
        if(hasJavadoc())
            chain.append(getJavadoc().toString()).append("\n");
        
        
        
        if(hasAnnotations())
            for(com.jasonpercus.util.code.Annotation_element a : getAnnotations())
                chain.append(a.toString()).append("\n");
        
        
        
        chain.append(getSignature()).append(";");
        
        
        String c = chain.toString();
        if(asComment())
            return "//" + c.replace("\n", "\n//");
        else
            return c;
    }
    
    
    
}