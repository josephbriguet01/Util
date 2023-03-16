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
 * Cette classe représente un élément typé d'un bloc javadoc (en bout de code)
 * @author JasonPercus
 * @version 1.0
 */
public abstract class ElementTyped_javadoc extends Element_javadoc {
    
    
    
//ATTRIBUT
    /**
     * Correspond au nom de la clef associée à l'élément de la javadoc. Exemple, prenons pour exemple l'élément de javadoc suivant: @param myVar Correspond à ma variable... param correspond au nom de l'élément, myVar à la key et "Correspond à ma variable" à la description
     */
    private String key;

    
    
//CONSTRUCTORS
    /**
     * Crée un élément typé de javadoc par défaut
     */
    public ElementTyped_javadoc() {
        this(null, "", "", "", null);
    }

    /**
     * Crée un élément typé de javadoc
     * @param name Correspond au nom du tag. Vide, s'il s'agit de la description du bloc javadoc
     */
    public ElementTyped_javadoc(String name) {
        this(null, name, "", "", null);
    }

    /**
     * Crée un élément typé de javadoc
     * @param name Correspond au nom du tag. Vide, s'il s'agit de la description du bloc javadoc
     * @param description Correspond à la description de l'élément javadoc. Exemple, prenons pour exemple l'élément de javadoc suivant: @param myVar Correspond à ma variable... param correspond au nom de l'élément, myVar à la key et "Correspond à ma variable" à la description
     */
    public ElementTyped_javadoc(String name, String description) {
        this(null, name, "", description, null);
    }

    /**
     * Crée un élément typé de javadoc
     * @param name Correspond au nom du tag. Vide, s'il s'agit de la description du bloc javadoc
     * @param key Correspond au nom de la clef associée à l'élément de la javadoc. Exemple, prenons pour exemple l'élément de javadoc suivant: @param myVar Correspond à ma variable... param correspond au nom de l'élément, myVar à la key et "Correspond à ma variable" à la description
     * @param description Correspond à la description de l'élément javadoc. Exemple, prenons pour exemple l'élément de javadoc suivant: @param myVar Correspond à ma variable... param correspond au nom de l'élément, myVar à la key et "Correspond à ma variable" à la description
     */
    public ElementTyped_javadoc(String name, String key, String description) {
        this(null, name, key, description, null);
    }

    /**
     * Crée un élément typé de javadoc
     * @param creator Correspond au créateur de bout de code
     * @param name Correspond au nom du tag. Vide, s'il s'agit de la description du bloc javadoc
     * @param key Correspond au nom de la clef associée à l'élément de la javadoc. Exemple, prenons pour exemple l'élément de javadoc suivant: @param myVar Correspond à ma variable... param correspond au nom de l'élément, myVar à la key et "Correspond à ma variable" à la description
     * @param description Correspond à la description de l'élément javadoc. Exemple, prenons pour exemple l'élément de javadoc suivant: @param myVar Correspond à ma variable... param correspond au nom de l'élément, myVar à la key et "Correspond à ma variable" à la description
     * @param parent Correspond au code parent de celui-ci
     */
    protected ElementTyped_javadoc(CodeCreator creator, String name, String key, String description, Code parent) {
        super(creator, name, description, parent);
        this.key = key;
    }
    
    

//GETTER & SETTER
    /**
     * Renvoie le nom de la clef associée à l'élément de la javadoc. Exemple, prenons pour exemple l'élément de javadoc suivant: @param myVar Correspond à ma variable... param correspond au nom de l'élément, myVar à la key et "Correspond à ma variable" à la description
     * @return Retourne le nom de la clef associée à l'élément de la javadoc
     */
    public synchronized final String getKey(){    
        return key;
    }

    /**
     * Modifie le nom de la clef associée à l'élément de la javadoc. Exemple, prenons pour exemple l'élément de javadoc suivant: @param myVar Correspond à ma variable... param correspond au nom de l'élément, myVar à la key et "Correspond à ma variable" à la description
     * @param key Correspond au nouveau nom de la clef associée à l'élément de la javadoc
     */
    public synchronized final void setKey(String key) {
        this.key = key;
    }

    
    
//METHODES PUBLICS
    /**
     * Renvoie la signature du bout de code
     * @return Retourne la signature du bout de code
     * @deprecated <div style="color: #D45B5B; font-style: italic">Utiliser la méthode {@link #toString()}</div>
     */
    @Override
    @Deprecated
    public String getSignature() {
        String _name_        = getName() != null ? getName() : "";
        String _key_         = key != null ? key : "";
        String _description_ = getDescription() != null ? getDescription() : "";
        
        boolean _name        = _name_.isEmpty();
        boolean _key         = _key_.isEmpty();
        boolean _description = _description_.isEmpty();
        
        if(_name && _key && _description)
            return "";
        else if(_name && _key && !_description)
            return _description_;
        else if(_name && !_key && _description)
            return "";
        else if(_name && !_key && !_description)
            return "";
        else if(!_name && _key && _description)
            return "";
        else if(!_name && _key && !_description)
            return "@" + _name_ + " " + _description_;
        else if(!_name && !_key && _description)
            return "@" + _name_ + " " + _key_ + " ";
        else
            return "@" + _name_ + " " + _key_ + " " + _description_;
    }

    /**
     * Renvoie l'élément sous la forme d'une chaîne de caractères (@name key description)
     * @return Retourne l'élément sous la forme d'une chaîne de caractères (@name key description)
     */
    @Override
    public String toString(){
        return getSignature();
    }
    
    
    
}