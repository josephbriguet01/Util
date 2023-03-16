/*
 * Copyright (C) JasonPercus Systems, Inc - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by JasonPercus, 06/2021
 */
package com.jasonpercus.util.code;



import com.github.javaparser.ast.comments.JavadocComment;
import com.github.javaparser.javadoc.Javadoc;
import com.github.javaparser.javadoc.JavadocBlockTag;



/**
 * Cette classe représente un bloc javadoc
 * @author JasonPercus
 * @version 1.0
 */
@SuppressWarnings({"NestedSynchronizedStatement", "SynchronizeOnNonFinalField", "LeakingThisInConstructor"})
public class Javadoc_code extends Code {
    
    
    
//ATTRIBUT
    /**
     * Correspond à la liste des éléments dans un bloc javadoc (ex: @author..., @param... @deprecated ...)
     */
    private final java.util.List<Element_javadoc> elements;

    
    
//CONSTRUCTORS
    /**
     * Crée un bloc javadoc par défaut
     */
    public Javadoc_code() {
        super(null, "", null);
        this.elements = new java.util.ArrayList<>();
    }
    
    /**
     * Crée un bloc javadoc
     * @param creator Correspond au créateur de bout de code
     * @param jc Correspond au bloc représentant la javadoc de JavaParser
     * @param parent Correspond au code parent de celui-ci
     * @deprecated Ne pas utiliser
     */
    @Deprecated
    protected Javadoc_code(CodeCreator creator, JavadocComment jc, Code parent) {
        super(creator, "", parent);
        this.elements = new java.util.ArrayList<>();
        
        Javadoc doc = jc.asJavadocComment().parse();
        this.elements.add(creator.createDescriptionJavadoc(creator, doc.getDescription().toText(), this));
        
        for(JavadocBlockTag str : doc.getBlockTags()){
            switch(str.getTagName()){
                case "author":
                    this.elements.add(creator.createAuthorJavadoc(creator, str.getContent().toText(), this));
                    break;
                case "deprecated":
                    this.elements.add(creator.createDeprecatedJavadoc(creator, str.getContent().toText(), this));
                    break;
                case "param":
                    this.elements.add(creator.createParamJavadoc(creator, str.getName().get(), str.getContent().toText(), this));
                    break;
                case "see":
                    this.elements.add(creator.createSeeJavadoc(creator, str.getContent().toText(), this));
                    break;
                case "serial":
                    this.elements.add(creator.createSerialJavadoc(creator, str.getName().get(), str.getContent().toText(), this));
                    break;
                case "serialField":
                    this.elements.add(creator.createSerialFieldJavadoc(creator, str.getName().get(), str.getContent().toText(), this));
                    break;
                case "serialData":
                    this.elements.add(creator.createSerialDataJavadoc(creator, str.getContent().toText(), this));
                    break;
                case "since":
                    this.elements.add(creator.createSinceJavadoc(creator, str.getContent().toText(), this));
                    break;
                case "version":
                    this.elements.add(creator.createVersionJavadoc(creator, str.getContent().toText(), this));
                    break;
                case "exception":
                    this.elements.add(creator.createExceptionJavadoc(creator, str.getName().get(), str.getContent().toText(), this));
                    break;
                case "throws":
                    this.elements.add(creator.createThrowsJavadoc(creator, str.getName().get(), str.getContent().toText(), this));
                    break;
                case "return":
                    this.elements.add(creator.createReturnJavadoc(creator, str.getContent().toText(), this));
                    break;
                default:
                    this.elements.add(creator.createUnknownJavadoc(creator, str.getTagName(), str.getContent().toText(), this));
                    break;
            }
        }
        java.util.Collections.sort(this.elements);
    }

    
    
//METHODES PUBLICS
    /**
     * Renvoie une copie de la liste des éléments javadoc (attention: il s'agit d'une copie de liste. Donc les modifications réalisées dessus n'auront aucun impact sur l'objet)
     * @return Retourne une copie de la liste des éléments javadoc
     */
    public synchronized final java.util.List<Element_javadoc> getElements() {
        synchronized(this.elements){
            java.util.List<Element_javadoc> copy = new java.util.ArrayList<>();
            for (Element_javadoc tc : this.elements) {
                copy.add(tc);
            }
            return copy;
        }
    }
    
    /**
     * Ajoute un/des élément(s) javadoc à l'objet
     * @param element Correspond au(x) élément(s) javadoc à ajouter à l'objet
     */
    public synchronized final void addElements(Element_javadoc...element){
        synchronized(this.elements){
            if(element != null){
                for(Element_javadoc code : element){
                    if(!this.elements.contains(code)){
                        code.setParent(this);
                        this.elements.add(code);
                    }
                }
                java.util.Collections.sort(this.elements);
            }
        }
    }
    
    /**
     * Enlève un/des élément(s) javadoc de l'objet
     * @param element Correspond au(x) élément(s) javadoc à enlever de l'objet
     */
    public synchronized final void removeElements(Element_javadoc...element){
        synchronized(this.elements){
            if(element != null){
                for(Element_javadoc code : element){
                    if(this.elements.contains(code)){
                        code.setParent(null);
                        this.elements.remove(code);
                    }
                }
            }
        }
    }
    
    /**
     * Enlève un élément javadoc de l'objet à l'indice donné
     * @param index Correspond à l'indice de l'élément javadoc à enlever
     */
    public synchronized final void removeElement(int index){
        synchronized(this.elements){
            Element_javadoc code = this.elements.get(index);
            code.setParent(null);
            this.elements.remove(index);
        }
    }
    
    /**
     * Enlève toutes les éléments javadoc de l'objet
     */
    public synchronized final void clearElements(){
        synchronized(this.elements){
            for(Element_javadoc code : this.elements)
                code.setParent(null);
            this.elements.clear();
        }
    }
    
    /**
     * Renvoie le nombre d'éléments javadoc de l'objet
     * @return Retourne le nombre d'éléments javadoc de l'objet
     */
    public synchronized final int countElements(){
        synchronized(this.elements){
            return this.elements.size();
        }
    }
    
    /**
     * Détermine si l'objet a des éléments javadoc
     * @return Retourne true, s'il en a, sinon false
     */
    public synchronized final boolean hasElements(){
        return countElements() > 0;
    }
    
    /**
     * Renvoie l'élément javadoc à l'indice index, compris dans l'objet
     * @param index Correspond à l'indice de l'élément javadoc à renvoyer
     * @return Retourne l'élément javadoc à l'indice index, compris dans l'objet
     */
    public synchronized final Element_javadoc getElement(int index){
        synchronized(this.elements){
            return this.elements.get(index);
        }
    }
    
    /**
     * Renvoie la signature du bloc javadoc
     * @return Retourne la signature du bloc javadoc
     * @deprecated <div style="color: #D45B5B; font-style: italic">Utiliser la méthode {@link #toString()}</div>
     */
    @Override
    @Deprecated
    public String getSignature() {
        StringBuilder chain = new StringBuilder("/**\n");
        
        
        
        for(Element_javadoc element : this.elements)
            if(element != null && !element.toString().isEmpty())
                chain.append(" * ").append(element.toString().replace("\r", "").replace("\n", "\n * ")).append("\n");
        
        
        
        chain.append(" */");
        
        
        
        return chain.toString();
    }

    /**
     * Renvoie le bloc javadoc sous la forme d'une chaîne de caractères
     * @return Retourne le bloc javadoc sous la forme d'une chaîne de caractères
     */
    @Override
    public String toString() {
        return getSignature();
    }
    
    
    
}