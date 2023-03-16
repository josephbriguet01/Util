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
import com.github.javaparser.ast.body.AnnotationDeclaration;
import com.github.javaparser.ast.body.AnnotationMemberDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.EnumDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.AnnotationExpr;



/**
 * Cette classe représente une interface java d'annotation (en bout de code)
 * @author JasonPercus
 * @version 1.0
 */
@SuppressWarnings({"NestedSynchronizedStatement", "SynchronizeOnNonFinalField", "LeakingThisInConstructor"})
public class Annotation_type extends Type_code {

    
    
//ATTRIBUTS
    /**
     * Détermine si l'interface d'annotation est final ou pas
     */
    private boolean _abstract;
    
    /**
     * Correspond à la liste des membres d'annotations
     */
    private final java.util.List<AnnotationMember_element> annotationMember;
    
    
    
//CONSTRUCTORS
    /**
     * Crée une interface d'annotation de code par défaut
     */
    public Annotation_type() {
        this.annotationMember = new java.util.ArrayList<>();
    }
    
    /**
     * Crée une interface d'annotation de code par défaut
     * @param creator Correspond au créateur de bout de code
     * @param parent Correspond à l'éventuel parent de cette classe
     * @param ad Correspond au bloc représentant l'interface d'annotation de JavaParser
     * @deprecated Ne pas utiliser
     */
    @Deprecated
    protected Annotation_type(CodeCreator creator, Type_code parent, AnnotationDeclaration ad) {
        super(creator, ad.getNameAsString(), ad.isPublic(), ad.isProtected(), ad.isPrivate(), ad.isStatic(), ad.isStrictfp(), parent);
        this._abstract        = ad.isAbstract();
        this.annotationMember = new java.util.ArrayList<>();
        
        java.util.List<Node> children = ad.getChildNodes();
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
            }else if(child instanceof AnnotationMemberDeclaration){
                this.annotationMember.add(creator.createAnnotationMemberElement(creator, (AnnotationMemberDeclaration) child, this));
            }
        }
        
        if(ad.hasJavaDocComment())
            setJavadoc(creator.createJavadoc(creator, ad.getJavadocComment().get(), this));
        
        for(AnnotationExpr ae : ad.getAnnotations())
            addAnnotations(creator.createAnnotationElement(creator, ae, this));
        
    }

    
    
//GETTER & SETTER
    /**
     * Détermine si l'interface d'annotation est abstraite ou pas
     * @return True si elle l'est, sinon false
     */
    public synchronized final boolean isAbstract() {
        return _abstract;
    }

    /**
     * Modifie si oui ou non l'interface d'annotation doit être abstraite ou pas
     * @param _abstract True si elle doit l'être, sinon false
     */
    public synchronized final void setAbstract(boolean _abstract) {
        this._abstract = _abstract;
    }
    
    /**
     * Renvoie une copie de la liste des membres d'annotations (attention: il s'agit d'une copie de liste. Donc les modifications réalisées dessus n'auront aucun impact sur l'interface d'annotation)
     * @return Retourne une copie de la liste des membres d'annotations
     */
    public synchronized final java.util.List<AnnotationMember_element> getAnnotationMembers() {
        synchronized(this.annotationMember){
            java.util.List<AnnotationMember_element> copy = new java.util.ArrayList<>();
            for (AnnotationMember_element tc : this.annotationMember) {
                copy.add(tc);
            }
            return copy;
        }
    }
    
    /**
     * Ajoute un/des membre(s) d'annotations à l'interface d'annotation
     * @param implement Correspond au(x) membre(s) d'annotations à ajouter à la classe
     */
    public synchronized final void addAnnotationMembers(AnnotationMember_element...implement){
        synchronized(this.annotationMember){
            if(implement != null){
                for(AnnotationMember_element code : implement){
                    if(!this.annotationMember.contains(code)){
                        code.setParent(this);
                        this.annotationMember.add(code);
                    }
                }
            }
        }
    }
    
    /**
     * Enlève un/des membre(s) d'annotations de la classe
     * @param implement Correspond au(x) membre(s) d'annotations à enlever de la classe
     */
    public synchronized final void removeAnnotationMembers(AnnotationMember_element...implement){
        synchronized(this.annotationMember){
            if(implement != null){
                for(AnnotationMember_element code : implement){
                    if(this.annotationMember.contains(code)){
                        code.setParent(null);
                        this.annotationMember.remove(code);
                    }
                }
            }
        }
    }
    
    /**
     * Enlève un membre d'annotations de la classe à l'indice donné
     * @param index Correspond à l'indice du membre d'annotations à enlever
     */
    public synchronized final void removeAnnotationMember(int index){
        synchronized(this.annotationMember){
            AnnotationMember_element code = this.annotationMember.get(index);
            code.setParent(null);
            this.annotationMember.remove(index);
        }
    }
    
    /**
     * Enlève toutes les membres d'annotations de l'interface d'annotation
     */
    public synchronized final void clearAnnotationMembers(){
        synchronized(this.annotationMember){
            for(AnnotationMember_element code : this.annotationMember){
                code.setParent(null);
            }
            this.annotationMember.clear();
        }
    }
    
    /**
     * Renvoie le nombre de membres d'annotations de l'interface d'annotation
     * @return Retourne le nombre de membres d'annotations de l'interface d'annotation
     */
    public synchronized final int countAnnotationMembers(){
        synchronized(this.annotationMember){
            return this.annotationMember.size();
        }
    }
    
    /**
     * Détermine si l'interface d'annotation a des membres d'annotations
     * @return Retourne true, si elle en a, sinon false
     */
    public synchronized final boolean hasAnnotationMembers(){
        return countAnnotationMembers() > 0;
    }
    
    /**
     * Renvoie le membre d'annotations à l'indice index, compris dans l'interface d'annotation
     * @param index Correspond à l'indice du membre d'annotations à renvoyer
     * @return Retourne le membre d'annotations à l'indice index, compris dans l'interface d'annotation
     */
    public synchronized final AnnotationMember_element getAnnotationMember(int index){
        synchronized(this.annotationMember){
            return this.annotationMember.get(index);
        }
    }
    
    /**
     * Renvoie la signature de l'interface d'annotation
     * @return Retourne la signature de l'interface d'annotation
     */
    @Override
    public String getSignature(){
        StringBuilder chain = new StringBuilder(getVisibility().toString());
        
        if(_abstract)
            chain.append("abstract ");
        
        if(isStatic())
            chain.append("static ");
        
        if(isStrictfp())
            chain.append("strictfp ");
        
        chain.append("@interface ").append(getName());
        
        return chain.toString();
    }
    
    /**
     * Renvoie l'interface d'annotation sous la forme d'une chaîne de caractères. Elle contient la javadoc du bout de la classe (s'il y en a une), les annotations de la classe (s'il y en a), la signature de la classe avec le code associé (s'il y en a un)
     * @return Retourne l'interface d'annotation en entier sous la forme d'une chaîne de caractères
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
        
        
        
        if(!annotationMember.isEmpty())
            for(AnnotationMember_element annotation : annotationMember)
                chain.append("\n    ").append(annotation.toString().replace("\n", "\n    ")).append("\n");
        
        
        
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