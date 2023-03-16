/*
 * Copyright (C) JasonPercus Systems, Inc - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by JasonPercus, 06/2021
 */
package com.jasonpercus.util.code;



import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.AnnotationExpr;



/**
 * Cette classe représente une constante d'énumération
 * @author JasonPercus
 * @version 1.0
 */
@SuppressWarnings({"NestedSynchronizedStatement", "SynchronizeOnNonFinalField"})
public class Field_element extends Element_element {

    
    
//ATTRIBUTS
    /**
     * Détermine si l'attribut est final ou pas
     */
    private boolean      _final;
    
    /**
     * Détermine si l'attribut est transient ou pas
     */
    private boolean      _transient;
    
    /**
     * Détermine si l'attribut est volatile ou pas
     */
    private boolean      _volatile;
    
    /**
     * Correspond au type de l'attribut
     */
    private String       _type;
    
    /**
     * Correspond à la valeur de l'attribut
     */
    private String       _value;

    
    
//CONSTRUCTORS
    /**
     * Crée un attribut
     */
    public Field_element() {
        super();
    }
    
    /**
     * Crée un attribut
     * @param creator Correspond au creator qui crée les sous-blocs de code
     * @param fd Correspond au bloc représentant l'attribut de JavaParser
     * @param vd Correspond au bloc représentant la variable de l'attribut de JavaParser
     * @param parent Correspond à la [classe | interface | énumération] parente de cet attribut
     * @deprecated Ne pas utiliser
     */
    @Deprecated
    protected Field_element(CodeCreator creator, FieldDeclaration fd, VariableDeclarator vd, Type_code parent) {
        super(null, vd.getNameAsString(), fd.isPublic(), fd.isProtected(), fd.isPrivate(), fd.isStatic(), parent);
        this._final     = fd.isFinal();
        this._transient = fd.isTransient();
        this._volatile  = fd.isVolatile();
        this._type      = fd.getElementType().asString();
        if(vd.getInitializer().isPresent())
            this._value = vd.getInitializer().get().toString();
        
        if(fd.hasJavaDocComment())
            setJavadoc(creator.createJavadoc(creator, fd.getJavadocComment().get(), this));
        
        for(AnnotationExpr ae : fd.getAnnotations())
            addAnnotations(creator.createAnnotationElement(creator, ae, this));
    }

    
    
//GETTERS & SETTERS
    /**
     * Détermine si l'attribut est final ou pas
     * @return True si elle l'est, sinon false
     */
    public synchronized final boolean isFinal() {
        return _final;
    }

    /**
     * Modifie si oui ou non l'attribut doit être final ou pas
     * @param _final True si elle doit l'être, sinon false
     */
    public synchronized final void setFinal(boolean _final) {
        this._final = _final;
    }
    
    /**
     * Détermine si l'attribut est transient ou pas
     * @return True si elle l'est, sinon false
     */
    public synchronized final boolean isTransient() {
        return _transient;
    }

    /**
     * Modifie si oui ou non l'attribut doit être transient ou pas
     * @param _transient True si elle doit l'être, sinon false
     */
    public synchronized final void setTransient(boolean _transient) {
        this._transient = _transient;
    }
    
    /**
     * Détermine si l'attribut est volatile ou pas
     * @return True si elle l'est, sinon false
     */
    public synchronized final boolean isVolatile() {
        return _volatile;
    }

    /**
     * Modifie si oui ou non l'attribut doit être volatile ou pas
     * @param _volatile True si elle doit l'être, sinon false
     */
    public synchronized final void setVolatile(boolean _volatile) {
        this._volatile = _volatile;
    }

    /**
     * Renvoie le type d'un attribut
     * @return Retourne le type d'un attribut
     */
    public synchronized final String getType() {
        return _type;
    }

    /**
     * Modifie le type d'un attribut
     * @param type Correspond au type d'un attribut
     */
    public synchronized final void setType(String type) {
        this._type = type;
    }

    /**
     * Renvoie la valeur d'un attribut
     * @return Retourne la valeur d'un attribut
     */
    public synchronized final String getValue() {
        return _value;
    }

    /**
     * Modifie la valeur d'un attribut
     * @param value Correspond à la valeur d'un attribut
     */
    public synchronized final void setValue(String value) {
        this._value = value;
    }
    
    
    
//METHODES PUBLICS
    /**
     * Si l'attribut est final et static
     * @return Retourne true si c'est le cas, sinon false
     */
    public boolean isConstField(){
        return isFinal() && isStatic();
    }
    
    /**
     * Si l'attribut est seulement static et non final
     * @return Retourne true s'il l'est, sinon false
     */
    public boolean isStaticField(){
        return !isFinal() && isStatic();
    }
    
    /**
     * Si l'attribut n'est pas static
     * @return Retourne true s'il l'est, sinon false
     */
    public boolean isOnlyField(){
        return !isStatic();
    }
    
    /**
     * Renvoie la signature d'un attribut
     * @return Retourne la signature d'un attribut
     */
    @Override
    public String getSignature(){
        StringBuilder chain = new StringBuilder(getVisibility().toString());
        
        if(_final)
            chain.append("final ");
        
        if(isStatic())
            chain.append("static ");
        
        if(_transient)
            chain.append("transient ");
        
        if(_volatile)
            chain.append("volatile ");
        
        chain.append(_type).append(" ").append(getName());
        
        return chain.toString();
    }

    /**
     * Renvoie un attribut sous la forme d'une chaîne de caractères
     * @return Retourne un attribut sous la forme d'une chaîne de caractères
     */
    @Override
    public String toString() {
        StringBuilder chain = new StringBuilder();
        
        
        
        if(hasJavadoc())
            chain.append(getJavadoc().toString()).append("\n");
        
        
        
        if(hasAnnotations())
            for(com.jasonpercus.util.code.Annotation_element a : getAnnotations())
                chain.append(a.toString()).append("\n");
        
        
        
        chain.append(getSignature());
        
        
        
        if(_value == null)
            chain.append(";");
        else
            chain.append(" = ").append(_value).append(";");
        
        
        
        String c = chain.toString();
        if(asComment())
            return "//" + c.replace("\n", "\n//");
        else
            return c;
    }
    
    
    
}