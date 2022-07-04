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
 * Cette classe représente un bout de code avec une certaine visibilité
 * @author JasonPercus
 * @version 1.0
 */
@SuppressWarnings({"NestedSynchronizedStatement", "SynchronizeOnNonFinalField"})
public abstract class Element_element extends Code {
    
    
    
//ATTRIBUTS
    /**
     * Détermine si le bout de code est public ou pas
     */
    private boolean _public;
    
    /**
     * Détermine si le bout de code est protected ou pas
     */
    private boolean _protected;
    
    /**
     * Détermine si le bout de code est private ou pas
     */
    private boolean _private;
    
    /**
     * Détermine si le bout de code est static ou pas
     */
    private boolean _static;
    
    /**
     * Correspond à la javadoc du bout de code
     */
    private Javadoc_code javadoc;
    
    /**
     * Correspond à la liste des annotations du bout de code
     */
    private final java.util.List<Annotation_element> annotations;

    
    
//CONSTRUCTORS
    /**
     * Crée un bout de code par défaut
     */
    public Element_element() {
        this.annotations = new java.util.ArrayList<>();
    }

    /**
     * Crée un bout de code avec une certaine visibilité
     * @param creator Correspond au créateur de bout de code
     * @param name Correspond au nom du bout de code
     * @param _public Détermine si le bout de code est public
     * @param _protected Détermine si le bout de code est protected
     * @param _private Détermine si le bout de code est private
     * @param _static Détermine si le bout de code est static
     * @param parent Correspond au parent de cet élément
     */
    protected Element_element(CodeCreator creator, String name, boolean _public, boolean _protected, boolean _private, boolean _static, Element_element parent) {
        super(creator, name, parent);
        this._public     = _public;
        this._protected  = _protected;
        this._private    = _private;
        this._static     = _static;
        this.annotations = new java.util.ArrayList<>();
    }

    
    
//METHODES PUBLICS
    /**
     * Détermine si le bout de code est public ou pas
     * @return Retourne true, s'il l'est, sinon false
     */
    public synchronized final boolean isPublic() {
        return _public;
    }

    /**
     * Modifie si oui ou non le bout de code doit être public ou pas
     * @param _public Détermine si le bout de code doit être public ou pas
     */
    public synchronized final void setPublic(boolean _public) {
        if(_public){
            setProtected(false);
            setPrivate(false);
        }
        this._public = _public;
    }

    /**
     * Détermine si le bout de code est protected ou pas
     * @return Retourne true, s'il l'est, sinon false
     */
    public synchronized final boolean isProtected() {
        return _protected;
    }

    /**
     * Modifie si oui ou non le bout de code doit être protected ou pas
     * @param _protected Détermine si le bout de code doit être protected ou pas
     */
    public synchronized final void setProtected(boolean _protected) {
        if(_protected){
            setPublic(false);
            setPrivate(false);
        }
        this._protected = _protected;
    }

    /**
     * Détermine si le bout de code est private ou pas
     * @return Retourne true, s'il l'est, sinon false
     */
    public synchronized final boolean isPrivate() {
        return _private;
    }

    /**
     * Modifie si oui ou non le bout de code doit être private ou pas
     * @param _private Détermine si le bout de code doit être private ou pas
     */
    public synchronized final void setPrivate(boolean _private) {
        if(_private){
            setPublic(false);
            setProtected(false);
        }
        this._private = _private;
    }

    /**
     * Détermine si le bout de code est static ou pas
     * @return Retourne true, s'il l'est, sinon false
     */
    public synchronized final boolean isStatic() {
        return _static;
    }

    /**
     * Modifie si oui ou non le bout de code doit être static ou pas
     * @param _static Détermine si le bout de code doit être static ou pas
     */
    public synchronized final void setStatic(boolean _static) {
        this._static = _static;
    }
    
    /**
     * Renvoie la visibilité du bout de code
     * @return Retourne la visibilité du bout de code
     */
    public synchronized final Visibility getVisibility(){
        if(_public) 
            return Visibility.PUBLIC;
        else if(_protected) 
            return Visibility.PROTECTED;
        else if(_private) 
            return Visibility.PRIVATE;
        else 
            return Visibility.DEFAULT;
    }
    
    /**
     * Modifie la visibilité du bout de code
     * @param visibility Correspond à la nouvelle visibilité du bout de code
     */
    public synchronized final void setVisibility(Visibility visibility){
        if(null != visibility) {
            switch (visibility) {
                case PUBLIC:
                    _public     = true;
                    _protected  = false;
                    _private    = false;
                    break;
                case PROTECTED:
                    _public     = false;
                    _protected  = true;
                    _private    = false;
                    break;
                case PRIVATE:
                    _public     = false;
                    _protected  = false;
                    _private    = true;
                    break;
                default:
                    _public     = false;
                    _protected  = false;
                    _private    = false;
                    break;
            }
        }
    }
    
    /**
     * Détermine si le bout de code à une javadoc
     * @return Retourne true, s'il en a une, sinon false
     */
    public synchronized final boolean hasJavadoc(){
        return javadoc != null;
    }

    /**
     * Renvoie la javadoc du bout de code
     * @return Retourne la javadoc du bout de code
     */
    public synchronized final Javadoc_code getJavadoc() {
        return javadoc;
    }

    /**
     * Modifie la javadoc du bout de code
     * @param javadoc Correspond à la nouvelle javadoc du bout de code
     */
    public synchronized final void setJavadoc(Javadoc_code javadoc) {
        this.javadoc = javadoc;
    }
    
    /**
     * Renvoie une copie de la liste des annotations (attention: il s'agit d'une copie de liste. Donc les modifications réalisées dessus n'auront aucun impact sur le bout de code)
     * @return Retourne une copie de la liste des annotations
     */
    public synchronized final java.util.List<Annotation_element> getAnnotations() {
        synchronized(annotations){
            java.util.List<Annotation_element> copy = new java.util.ArrayList<>();
            for (Annotation_element tc : annotations) {
                copy.add(tc);
            }
            return copy;
        }
    }
    
    /**
     * Ajoute une/des annotation(s) au bout de code
     * @param annotations Correspond au(x) annotation(s) à ajouter au bout de code
     */
    public synchronized final void addAnnotations(Annotation_element...annotations){
        synchronized(this.annotations){
            if(annotations != null){
                for(Annotation_element code : annotations){
                    if(!this.annotations.contains(code)){
                        code.setParent(this);
                        this.annotations.add(code);
                    }
                }
            }
        }
    }
    
    /**
     * Enlève une/des annotation(s) au bout de code
     * @param annotations Correspond au(x) annotation(s) à enlever du bout de code
     */
    public synchronized final void removeAnnotations(Annotation_element...annotations){
        synchronized(this.annotations){
            if(annotations != null){
                for(Annotation_element code : annotations){
                    if(this.annotations.contains(code)){
                        code.setParent(null);
                        this.annotations.remove(code);
                    }
                }
            }
        }
    }
    
    /**
     * Enlève une annotation du bout de code à l'indice donné
     * @param index Correspond à l'indice de l'annotation à enlever
     */
    public synchronized final void removeAnnotation(int index){
        synchronized(this.annotations){
            Annotation_element code = this.annotations.get(index);
            code.setParent(null);
            this.annotations.remove(index);
        }
    }
    
    /**
     * Enlève toutes les annotations du bout de code
     */
    public synchronized final void clearAnnotations(){
        synchronized(this.annotations){
            for(Annotation_element code : this.annotations){
                code.setParent(null);
            }
            this.annotations.clear();
        }
    }
    
    /**
     * Renvoie le nombre d'annotation du bout de code
     * @return Retourne le nombre d'annotation du bout de code
     */
    public synchronized final int countAnnotations(){
        synchronized(this.annotations){
            return this.annotations.size();
        }
    }
    
    /**
     * Détermine si le bout de code a des annotations
     * @return Retourne true, s'il en a, sinon false
     */
    public synchronized final boolean hasAnnotations(){
        return countAnnotations() > 0;
    }
    
    /**
     * Renvoie l'annotation à l'indice index, compris dans le bout de code
     * @param index Correspond à l'indice de l'annotation à renvoyer
     * @return Retourne l'annotation à l'indice index, compris dans le bout de code
     */
    public synchronized final Annotation_element getAnnotations(int index){
        synchronized(this.annotations){
            return this.annotations.get(index);
        }
    }
    
    /**
     * Renvoie true si l'élément n'est ni public, ni protected, ni private, sinon false
     * @return Retourne true si l'élément n'est ni public, ni protected, ni private, sinon false
     */
    public synchronized final boolean hasNoVisibility(){
        return !isPrivate() && !isProtected() && !isPublic();
    }
    
    
    
//ENUMERATION
    /**
     * Cette énumération énumère les Modifiers de visibilité (public | protected | private)
     * @author JasonPercus
     * @version 1.0
     */
    public enum Visibility {
    
        
        
    //CONSTANTES
        /**
         * Lorsqu'il n'y a pas de Modifier de visibilité
         */
        DEFAULT(""),
        
        /**
         * Correspond au modifier de visibilité public
         */
        PUBLIC("public "),
        
        /**
         * Correspond au modifier de visibilité protected
         */
        PROTECTED("protected "),
        
        /**
         * Correspond au modifier de visibilité private
         */
        PRIVATE("private ");

        
        
    //ATTRIBUT
        /**
         * Correspond à la visibilité d'un modifier sous la forme d'une chaîne de caractères
         */
        private final String type;

        
        
    //CONSTRUCTOR
        /**
         * Crée un modifier de visibilité
         * @param type Correspond à la visibilité d'un modifier sous la forme d'une chaîne de caractères
         */
        private Visibility(String type) {
            this.type = type;
        }

        
        
    //METHODE PUBLIC
        /**
         * Renvoie le modifier de visibilité sous la forme d'une chaîne de caractères
         * @return Retourne le modifier de visibilité sous la forme d'une chaîne de caractères
         */
        @Override
        public String toString() {
            return type;
        }

        
        
    }
    
    
    
}