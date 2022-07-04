/*
 * Copyright (C) JasonPercus Systems, Inc - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by JasonPercus, 06/2021
 */
package com.jasonpercus.util.code;



import com.github.javaparser.ast.body.EnumConstantDeclaration;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.Expression;



/**
 * Cette classe représente une constante d'énumération
 * @author JasonPercus
 * @version 1.0
 */
@SuppressWarnings({"NestedSynchronizedStatement", "SynchronizeOnNonFinalField", "LeakingThisInConstructor"})
public class EnumConst_element extends Code {
    
    
    
//ATTRIBUTS
    /**
     * Correspond à la javadoc du bout de code
     */
    private Javadoc_code javadoc;
    
    /**
     * Correspond à la liste des annotations du bout de code
     */
    private final java.util.List<Annotation_element> annotations;
    
    /**
     * Correspond à la liste des arguments de la constante
     */
    private final java.util.List<String> arguments;

    
    
//CONSTRUCTORS
    /**
     * Crée une constante d'énumération
     */
    public EnumConst_element() {
        super();
        this.annotations = new java.util.ArrayList<>();
        this.arguments   = new java.util.ArrayList<>();
    }
    
    /**
     * Crée une constante d'énumération
     * @param creator Correspond au creator qui crée les sous-blocs de code
     * @param ecd Correspond au bloc représentant la constante d'énumération de JavaParser
     * @param parent Correspond au code parent de celui-ci
     * @deprecated Ne pas utiliser
     */
    protected EnumConst_element(CodeCreator creator, EnumConstantDeclaration ecd, Code parent) {
        super(null, ecd.getNameAsString(), parent);
        this.annotations = new java.util.ArrayList<>();
        this.arguments   = new java.util.ArrayList<>();
        
        if(ecd.hasJavaDocComment())
            setJavadoc(creator.createJavadoc(creator, ecd.getJavadocComment().get(), this));
        
        for(AnnotationExpr ae : ecd.getAnnotations())
            addAnnotations(creator.createAnnotationElement(creator, ae, this));
        
        for(Expression s : ecd.getArguments())
            this.arguments.add(s.toString());
    }
    
    
    
//METHODES PUBLICS
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
     * Renvoie une copie de la liste des arguments (attention: il s'agit d'une copie de liste. Donc les modifications réalisées dessus n'auront aucun impact sur le bout de code)
     * @return Retourne une copie de la liste des arguments
     */
    public synchronized final java.util.List<String> getArgs() {
        synchronized(arguments){
            java.util.List<String> copy = new java.util.ArrayList<>();
            for (String tc : arguments) {
                copy.add(tc);
            }
            return copy;
        }
    }
    
    /**
     * Ajoute un/des argument(s) au bout de code
     * @param arguments Correspond au(x) argument(s) à ajouter au bout de code
     */
    public synchronized final void addArgs(String...arguments){
        synchronized(this.arguments){
            if(arguments != null){
                for(String code : arguments){
                    if(!this.arguments.contains(code))
                        this.arguments.add(code);
                }
            }
        }
    }
    
    /**
     * Enlève un/des argument(s) au bout de code
     * @param arguments Correspond au(x) argument(s) à enlever du bout de code
     */
    public synchronized final void removeArgs(String...arguments){
        synchronized(this.arguments){
            if(arguments != null){
                for(String code : arguments){
                    if(this.arguments.contains(code))
                        this.arguments.remove(code);
                }
            }
        }
    }
    
    /**
     * Enlève un argument du bout de code à l'indice donné
     * @param index Correspond à l'indice de l'argument à enlever
     */
    public synchronized final void removeArg(int index){
        synchronized(this.arguments){
            this.arguments.remove(index);
        }
    }
    
    /**
     * Enlève toutes les arguments du bout de code
     */
    public synchronized final void clearArgs(){
        synchronized(this.arguments){
            this.arguments.clear();
        }
    }
    
    /**
     * Renvoie le nombre d'argument du bout de code
     * @return Retourne le nombre d'argument du bout de code
     */
    public synchronized final int countArgs(){
        synchronized(this.arguments){
            return this.arguments.size();
        }
    }
    
    /**
     * Détermine si le bout de code a des arguments
     * @return Retourne true, s'il en a, sinon false
     */
    public synchronized final boolean hasArgs(){
        return countArgs() > 0;
    }
    
    /**
     * Renvoie l'argument à l'indice index, compris dans le bout de code
     * @param index Correspond à l'indice de l'argument à renvoyer
     * @return Retourne l'argument à l'indice index, compris dans le bout de code
     */
    public synchronized final String getArgs(int index){
        synchronized(this.arguments){
            return this.arguments.get(index);
        }
    }
    
    /**
     * Renvoie la signature de la constante d'énumération
     * @return Retourne la signature de la constante d'énumération
     */
    @Override
    public String getSignature(){
        StringBuilder chain = new StringBuilder(getName());
        if(!arguments.isEmpty()){
            chain.append("(");
            for(int i=0;i<arguments.size();i++){
                if(i>0) chain.append(", ");
                chain.append(arguments.get(i));
            }
            chain.append(")");
        }
        return chain.toString();
    }
    
    /**
     * Renvoie la constante d'énumération sous la forme de chaîne de caractères
     * @return Retourne la constante d'énumération sous la forme de chaîne de caractères
     */
    @Override
    public String toString(){
        StringBuilder chain = new StringBuilder();
        
        
        
        if(hasJavadoc())
            chain.append(getJavadoc().toString()).append("\n");
        
        
        
        if(hasAnnotations())
            for(com.jasonpercus.util.code.Annotation_element a : getAnnotations())
                chain.append(a.toString()).append("\n");
        
        
        
        chain.append(getSignature());
        
        
        
        String c = chain.toString();
        if(asComment())
            return "//" + c.replace("\n", "\n//");
        else
            return c;
    }
    
    
    
}