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
 * Cette classe représente un élément d'un bloc javadoc (en bout de code)
 * @author JasonPercus
 * @version 1.0
 */
public abstract class Element_javadoc extends Code {
    
    
    
//ATTRIBUT
    /**
     * Correspond à la description de l'élément javadoc. Exemple, prenons pour exemple l'élément de javadoc suivant: @author Moi... author correspond au nom de l'élément et "Moi" à la description
     */
    private String description;

    
    
//CONSTRUCTORS
    /**
     * Crée un élément de javadoc par défaut
     */
    public Element_javadoc() {
        this(null, "", "", null);
    }

    /**
     * Crée un élément de javadoc
     * @param name Correspond au nom du tag. Vide, s'il s'agit de la description du bloc javadoc
     */
    public Element_javadoc(String name) {
        this(null, name, "", null);
    }

    /**
     * Crée un élément de javadoc
     * @param name Correspond au nom du tag. Vide, s'il s'agit de la description du bloc javadoc
     * @param description Correspond à la description de l'élément javadoc. Exemple, prenons pour exemple l'élément de javadoc suivant: @author Moi... author correspond au nom de l'élément et "Moi" à la description
     */
    public Element_javadoc(String name, String description) {
        this(null, name, description, null);
    }

    /**
     * Crée un élément de javadoc
     * @param creator Correspond au créateur de bout de code
     * @param name Correspond au nom du tag. Vide, s'il s'agit de la description du bloc javadoc
     * @param description Correspond à la description de l'élément javadoc. Exemple, prenons pour exemple l'élément de javadoc suivant: @author Moi... author correspond au nom de l'élément et "Moi" à la description
     * @param parent Correspond au code parent de celui-ci
     */
    protected Element_javadoc(CodeCreator creator, String name, String description, Code parent) {
        super(creator, name, parent);
        this.description = description;
    }
    
    

//GETTER & SETTER
    /**
     * Renvoie la description de l'élément javadoc. Exemple, prenons pour exemple l'élément de javadoc suivant: @author Moi... author correspond au nom de l'élément et "Moi" à la description
     * @return Retourne la description de l'élément javadoc
     */
    public synchronized final String getDescription() {
        return description;
    }
    
    /**
     * Modifie la description de l'élément javadoc. Exemple, prenons pour exemple l'élément de javadoc suivant: @author Moi... author correspond au nom de l'élément et "Moi" à la description
     * @param description Correspond à la nouvelle description de l'élément javadoc
     */
    public synchronized final void setDescription(String description) {
        this.description = description;
    }
    
    
    
//METHODE ABSTRACT
    /**
     * Renvoie la priorité de l'élément par rapport aux autres dans un bloc javadoc. Plus la valeur est petite, plus la priorité est grande, et inversement plus la valeur est grande, plus la priorité d'affichage est petite (À définir)
     * @return Retourne la priorité de l'élément par rapport aux autres dans le bloc javadoc
     */
    public abstract int priority();

    
    
//METHODES PUBLICS
    /**
     * Renvoie la signature du bout de code
     * @return Retourne la signature du bout de code
     * @deprecated <div style="color: #D45B5B; font-style: italic">Utiliser la méthode {@link #toString()}</div>
     */
    @Override
    public String getSignature() {
        String _name_        = getName() != null ? getName() : "";
        String _description_ = description != null ? description : "";
        
        boolean _name        = _name_.isEmpty();
        boolean _description = _description_.isEmpty();
        
        if(_name && _description)
            return "";
        else if(_name && !_description)
            return _description_;
        else if(!_name && _description)
            return "@" + _name_;
        else
            return "@" + _name_ + " " + _description_;
    }

    /**
     * Renvoie l'élément sous la forme d'une chaîne de caractères (@name description... Ex: @author Moi)
     * @return Retourne l'élément sous la forme d'une chaîne de caractères (@name description... Ex: @author Moi)
     */
    @Override
    public String toString(){
        return getSignature();
    }

    /**
     * Compare deux bouts de code
     * @param o Correspond au second objet à comparer au courant
     * @return Retourne le résultat de la comparaison
     */
    @Override
    public final int compareTo(Code o){
        if(o instanceof Element_javadoc){
            Element_javadoc e = (Element_javadoc) o;
            if(this.priority() < e.priority()) return -1;
            else if(this.priority() > e.priority()) return 1;
            else return 0;
        }else{
            return super.compareTo(o);
        }
    }
    
    
    
}