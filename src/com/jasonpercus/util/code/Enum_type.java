/*
 * Copyright (C) JasonPercus Systems, Inc - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by JasonPercus, 06/2021
 */
package com.jasonpercus.util.code;



import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.AnnotationDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.EnumConstantDeclaration;
import com.github.javaparser.ast.body.EnumDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.type.ClassOrInterfaceType;



/**
 * Cette classe représente une classe java d'énumération (en bout de code)
 * @author JasonPercus
 * @version 1.0
 */
@SuppressWarnings({"NestedSynchronizedStatement", "SynchronizeOnNonFinalField", "LeakingThisInConstructor"})
public class Enum_type extends Type_code {

    
    
//ATTRIBUTS
    /**
     * Correspond à la liste des interfaces implémentées
     */
    private final java.util.List<String> _implements;
    
    /**
     * Correspond à la liste des constantes énumérées dans la classe d'énumération
     */
    private final java.util.List<EnumConst_element> _enumconst;
    
    
    
//CONSTRUCTORS
    /**
     * Crée une classe d'énumération de code par défaut
     */
    public Enum_type() {
        this._implements = new java.util.ArrayList<>();
        this._enumconst  = new java.util.ArrayList<>();
    }
    
    /**
     * Crée une classe d'énumération de code
     * @param creator Correspond au créateur de bout de code
     * @param parent Correspond à l'éventuel parent de cette classe
     * @param ed Correspond au bloc représentant la classe d'énumération de JavaParser
     * @deprecated Ne pas utiliser
     */
    protected Enum_type(CodeCreator creator, Type_code parent, EnumDeclaration ed) {
        super(creator, ed.getNameAsString(), ed.isPublic(), ed.isProtected(), ed.isPrivate(), ed.isStatic(), ed.isStrictfp(), parent);
        NodeList<ClassOrInterfaceType> _implementsB = ed.getImplementedTypes();
        this._implements = new java.util.ArrayList<>();
        this._enumconst  = new java.util.ArrayList<>();

        for (ClassOrInterfaceType coit : _implementsB)
            this._implements.add(coit.toString());
        
        java.util.List<Node> children = ed.getChildNodes();
        for(Node child : children){
            if(child instanceof FieldDeclaration){
                FieldDeclaration fd = (FieldDeclaration) child;
                for(VariableDeclarator vd : fd.getVariables()){
                    addFields(creator.createFieldElement(creator, fd, vd, this));
                }
            }
            if(child instanceof MethodDeclaration)
                addMethods(creator.createMethodElement(creator, (MethodDeclaration) child, this));
            if(child instanceof ConstructorDeclaration)
                addConstructors(creator.createConstructorElement(creator, (ConstructorDeclaration) child, this));
            
            if(child instanceof ClassOrInterfaceDeclaration){
                ClassOrInterfaceDeclaration coid2 = (ClassOrInterfaceDeclaration) child;
                if(coid2.isInterface()){
                    addSubInterfaces(creator.createInterface(creator, this, coid2));
                }else{
                    addSubClass(creator.createClass(creator, this, coid2));
                }
            }else if(child instanceof EnumDeclaration){
                addSubEnums(creator.createEnum(creator, this, (EnumDeclaration) child));
            }else if(child instanceof AnnotationDeclaration){
                addSubAnnotations(creator.createAnnotation(creator, this, (AnnotationDeclaration) child));
            }
        }
        
        if(ed.hasJavaDocComment())
            setJavadoc(creator.createJavadoc(creator, ed.getJavadocComment().get(), this));
        
        for(AnnotationExpr ae : ed.getAnnotations())
            addAnnotations(creator.createAnnotationElement(creator, ae, this));
        
        for(EnumConstantDeclaration ecd : ed.getEntries())
            this._enumconst.add(creator.createConstEnumElement(creator, ecd, this));
    }
    
    
    
//METHODES PUBLICS
    /**
     * Renvoie une copie de la liste des interfaces implémentées (attention: il s'agit d'une copie de liste. Donc les modifications réalisées dessus n'auront aucun impact sur la classe d'énumération)
     * @return Retourne une copie de la liste des interfaces implémentées
     */
    public synchronized final java.util.List<String> getImplements() {
        synchronized(this._implements){
            java.util.List<String> copy = new java.util.ArrayList<>();
            for (String tc : this._implements) {
                copy.add(tc);
            }
            return copy;
        }
    }
    
    /**
     * Ajoute une/des interface(s) implémentée(s) à la classe d'énumération
     * @param implement Correspond au(x) interface(s) implémentée(s) à ajouter à la classe d'énumération
     */
    public synchronized final void addImplements(String...implement){
        synchronized(this._implements){
            if(implement != null){
                for(String code : implement){
                    if(!this._implements.contains(code))
                        this._implements.add(code);
                }
            }
        }
    }
    
    /**
     * Enlève une/des interface(s) implémentée(s) de la classe d'énumération
     * @param implement Correspond au(x) interface(s) implémentée(s) à enlever de la classe d'énumération
     */
    public synchronized final void removeImplements(String...implement){
        synchronized(this._implements){
            if(implement != null){
                for(String code : implement){
                    if(this._implements.contains(code))
                        this._implements.remove(code);
                }
            }
        }
    }
    
    /**
     * Enlève une interface implémentée de la classe d'énumération à l'indice donné
     * @param index Correspond à l'indice de l'interface implémentée à enlever
     */
    public synchronized final void removeImplement(int index){
        synchronized(this._implements){
            this._implements.remove(index);
        }
    }
    
    /**
     * Enlève toutes les interfaces implémentées de la classe d'énumération
     */
    public synchronized final void clearImplements(){
        synchronized(this._implements){
            this._implements.clear();
        }
    }
    
    /**
     * Renvoie le nombre d'interfaces implémentées de la classe d'énumération
     * @return Retourne le nombre d'interfaces implémentées de la classe d'énumération
     */
    public synchronized final int countImplements(){
        synchronized(this._implements){
            return this._implements.size();
        }
    }
    
    /**
     * Détermine si la classe d'énumération a des interfaces implémentées
     * @return Retourne true, si elle en a, sinon false
     */
    public synchronized final boolean hasImplements(){
        return countImplements() > 0;
    }
    
    /**
     * Renvoie l'interface implémentée à l'indice index, compris dans la classe d'énumération
     * @param index Correspond à l'indice de l'interface implémentée à renvoyer
     * @return Retourne l'interface implémentée à l'indice index, compris dans la classe d'énumération
     */
    public synchronized final String getImplement(int index){
        synchronized(this._implements){
            return this._implements.get(index);
        }
    }
    
    /**
     * Renvoie une copie de la liste des constantes (attention: il s'agit d'une copie de liste. Donc les modifications réalisées dessus n'auront aucun impact sur la classe d'énumération)
     * @return Retourne une copie de la liste des constantes
     */
    public synchronized final java.util.List<EnumConst_element> getConsts() {
        synchronized(this._enumconst){
            java.util.List<EnumConst_element> copy = new java.util.ArrayList<>();
            for (EnumConst_element tc : this._enumconst) {
                copy.add(tc);
            }
            return copy;
        }
    }
    
    /**
     * Ajoute une/des constante(s) implémentée(s) à la classe d'énumération
     * @param _const Correspond au(x) constante(s) implémentée(s) à ajouter à la classe d'énumération
     */
    public synchronized final void addConsts(EnumConst_element..._const){
        synchronized(this._enumconst){
            if(_const != null){
                for(EnumConst_element code : _const){
                    if(!this._enumconst.contains(code)){
                        code.setParent(this);
                        this._enumconst.add(code);
                    }
                }
            }
        }
    }
    
    /**
     * Enlève une/des constante(s) implémentée(s) de la classe d'énumération
     * @param _const Correspond au(x) constante(s) implémentée(s) à enlever de la classe d'énumération
     */
    public synchronized final void removeConsts(EnumConst_element..._const){
        synchronized(this._enumconst){
            if(_const != null){
                for(EnumConst_element code : _const){
                    if(this._enumconst.contains(code)){
                        code.setParent(null);
                        this._enumconst.remove(code);
                    }
                }
            }
        }
    }
    
    /**
     * Enlève une constante de la classe d'énumération à l'indice donné
     * @param index Correspond à l'indice de la constante à enlever
     */
    public synchronized final void removeConst(int index){
        synchronized(this._enumconst){
            EnumConst_element code = this._enumconst.get(index);
            code.setParent(null);
            this._enumconst.remove(index);
        }
    }
    
    /**
     * Enlève toutes les constantes de la classe d'énumération
     */
    public synchronized final void clearConsts(){
        synchronized(this._enumconst){
            for(EnumConst_element code : this._enumconst){
                code.setParent(null);
            }
            this._enumconst.clear();
        }
    }
    
    /**
     * Renvoie le nombre de constantes de la classe d'énumération
     * @return Retourne le nombre de constantes de la classe d'énumération
     */
    public synchronized final int countConsts(){
        synchronized(this._enumconst){
            return this._enumconst.size();
        }
    }
    
    /**
     * Détermine si la classe d'énumération a des constantes
     * @return Retourne true, si elle en a, sinon false
     */
    public synchronized final boolean hasConsts(){
        return countConsts() > 0;
    }
    
    /**
     * Renvoie la constante à l'indice index, compris dans la classe d'énumération
     * @param index Correspond à l'indice de la constante à renvoyer
     * @return Retourne la constante à l'indice index, compris dans la classe d'énumération
     */
    public synchronized final EnumConst_element getConst(int index){
        synchronized(this._enumconst){
            return this._enumconst.get(index);
        }
    }
    
    /**
     * Renvoie la signature de la classe d'énumération
     * @return Retourne la signature de la classe d'énumération
     */
    @Override
    public String getSignature(){
        StringBuilder chain = new StringBuilder(getVisibility().toString());
        
        if(isStatic())
            chain.append("static ");
        
        if(isStrictfp())
            chain.append("strictfp ");
        
        chain.append("enum ").append(getName());
        
        if(!_implements.isEmpty()){
            chain.append(" implements ");
            for(int i=0;i<_implements.size();i++){
                if(i>0) chain.append(", ");
                chain.append(_implements.get(i));
            }
        }
        
        return chain.toString();
    }
    
    /**
     * Renvoie la classe d'énumération sous la forme d'une chaîne de caractères. Elle contient la javadoc du bout de la classe (s'il y en a une), les annotations de la classe (s'il y en a), la signature de la classe avec le code associé (s'il y en a un)
     * @return Retourne la classe d'énumération en entier sous la forme d'une chaîne de caractères
     */
    @Override
    public String toString(){
        StringBuilder chain = new StringBuilder();
        
        
        
        if(hasJavadoc())
            chain.append(getJavadoc().toString()).append("\n");
        
        
        
        if(hasAnnotations())
            for(com.jasonpercus.util.code.Annotation_element a : getAnnotations())
                chain.append(a.toString()).append("\n");
        
        
        
        chain.append(getSignature()).append(" {\n");
        
        
        
        if(!_enumconst.isEmpty()){
            for(int i=0;i<_enumconst.size();i++){
                EnumConst_element enumconst = _enumconst.get(i);
                if(i>0) chain.append(",\n");
                chain.append("\n    ").append(enumconst.toString().replace("\n", "\n    "));
            }
            chain.append(";\n");
        }
        
        
        
        if(hasFields())
            for(Field_element field : getFields())
                chain.append("\n    ").append(field.toString().replace("\n", "\n    ")).append("\n");
        
        
        
        if(hasConstructors())
            for(Constructor_element constructor : getConstructors())
                chain.append("\n    ").append(constructor.toString().replace("\n", "\n    ")).append("\n");
        
        
        
        if(hasMethods())
            for(Method_element method : getMethods())
                chain.append("\n    ").append(method.toString().replace("\n", "\n    ")).append("\n");
        
        
        
        if(hasSubClass())
            for(Class_type classe : getSubClass())
                chain.append("\n    ").append(classe.toString().replace("\n", "\n    ")).append("\n");
        
        
        
        if(hasSubInterfaces())
            for(Interface_type inter : getSubInterfaces())
                chain.append("\n    ").append(inter.toString().replace("\n", "\n    ")).append("\n");
        
        
        
        if(hasSubEnums())
            for(Enum_type enumeration : getSubEnums())
                chain.append("\n    ").append(enumeration.toString().replace("\n", "\n    ")).append("\n");
        
        
        
        if(hasSubAnnotations())
            for(Annotation_type annotation : getSubAnnotations())
                chain.append("\n    ").append(annotation.toString().replace("\n", "\n    ")).append("\n");
        
        
        
        chain.append("}");
        
        
        
        String c = chain.toString();
        if(asComment())
            return "//" + c.replace("\n", "\n//");
        else
            return c;
    }
    
    
    
}