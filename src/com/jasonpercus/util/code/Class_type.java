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
import com.github.javaparser.ast.body.EnumDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.type.TypeParameter;



/**
 * Cette classe représente une classe java (en bout de code)
 * @author JasonPercus
 * @version 1.0
 */
@SuppressWarnings({"NestedSynchronizedStatement", "SynchronizeOnNonFinalField", "LeakingThisInConstructor"})
public class Class_type extends Type_code {

    
    
//ATTRIBUTS
    /**
     * Détermine si la classe est final ou pas
     */
    private boolean _final;
    
    /**
     * Détermine si la classe est abstraite ou pas
     */
    private boolean _abstract;
    
    /**
     * Correspond à la liste des classes mères
     */
    private final java.util.List<String> _extends;
    
    /**
     * Correspond à la liste des interfaces implémentées
     */
    private final java.util.List<String> _implements;
    
    /**
     * Correspond à la liste des paramètres génériques
     */
    private final java.util.List<String> _genericsParameters;
    
    
    
//CONSTRUCTORS
    /**
     * Crée une classe de code par défaut
     */
    public Class_type() {
        this._extends               = new java.util.ArrayList<>();
        this._implements            = new java.util.ArrayList<>();
        this._genericsParameters    = new java.util.ArrayList<>();
    }
    
    /**
     * Crée une classe de code
     * @param creator Correspond au créateur de bout de code
     * @param parent Correspond à l'éventuel parent de cette classe
     * @param coid Correspond au bloc représentant la classe de JavaParser
     * @deprecated Ne pas utiliser
     */
    protected Class_type(CodeCreator creator, Type_code parent, ClassOrInterfaceDeclaration coid) {
        super(creator, coid.getNameAsString(), coid.isPublic(), coid.isProtected(), coid.isPrivate(), coid.isStatic(), coid.isStrictfp(), parent);
        NodeList<ClassOrInterfaceType> _extendsB    = coid.getExtendedTypes();
        NodeList<ClassOrInterfaceType> _implementsB = coid.getImplementedTypes();
        NodeList<TypeParameter> _parametersB        = coid.getTypeParameters();
        this._final                 = coid.isFinal();
        this._abstract              = coid.isAbstract();
        this._extends               = new java.util.ArrayList<>();
        this._implements            = new java.util.ArrayList<>();
        this._genericsParameters    = new java.util.ArrayList<>();
        
        for (ClassOrInterfaceType coit : _extendsB)
            this._extends.add(coit.toString());

        for (ClassOrInterfaceType coit : _implementsB)
            this._implements.add(coit.toString());

        for (TypeParameter tp : _parametersB)
            this._genericsParameters.add(tp.asString());
        
        java.util.List<Node> children = coid.getChildNodes();
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
        
        if(coid.hasJavaDocComment())
            setJavadoc(creator.createJavadoc(creator, coid.getJavadocComment().get(), this));
        
        for(AnnotationExpr ae : coid.getAnnotations())
            addAnnotations(creator.createAnnotationElement(creator, ae, this));
        
    }

    
    
//GETTERS & SETTERS
    /**
     * Détermine si la classe est final ou pas
     * @return True si elle l'est, sinon false
     */
    public synchronized final boolean isFinal() {
        return _final;
    }

    /**
     * Modifie si oui ou non la classe doit être final ou pas
     * @param _final True si elle doit l'être, sinon false
     */
    public synchronized final void setFinal(boolean _final) {
        this._final = _final;
    }

    /**
     * Détermine si la classe est abstraite ou pas
     * @return True si elle l'est, sinon false
     */
    public synchronized final boolean isAbstract() {
        return _abstract;
    }

    /**
     * Modifie si oui ou non la classe doit être abstraite ou pas
     * @param _abstract True si elle doit l'être, sinon false
     */
    public synchronized final void setAbstract(boolean _abstract) {
        this._abstract = _abstract;
    }
    
    
    
//METHODES PUBLICS
    /**
     * Renvoie une copie de la liste des classes mères (attention: il s'agit d'une copie de liste. Donc les modifications réalisées dessus n'auront aucun impact sur la classe)
     * @return Retourne une copie de la liste des classes mères
     */
    public synchronized final java.util.List<String> getExtends() {
        synchronized(this._extends){
            java.util.List<String> copy = new java.util.ArrayList<>();
            for (String tc : this._extends) {
                copy.add(tc);
            }
            return copy;
        }
    }
    
    /**
     * Ajoute une/des classe(s) mère(s) à la classe
     * @param extend Correspond au(x) classe(s) mère(s) à ajouter à la classe
     */
    public synchronized final void addExtends(String...extend){
        synchronized(this._extends){
            if(extend != null){
                for(String code : extend){
                    if(!this._extends.contains(code))
                        this._extends.add(code);
                }
            }
        }
    }
    
    /**
     * Enlève une/des classe(s) mère(s) de la classe
     * @param extend Correspond au(x) classe(s) mère(s) à enlever de la classe
     */
    public synchronized final void removeExtends(String...extend){
        synchronized(this._extends){
            if(extend != null){
                for(String code : extend){
                    if(this._extends.contains(code))
                        this._extends.remove(code);
                }
            }
        }
    }
    
    /**
     * Enlève une classe mère de la classe à l'indice donné
     * @param index Correspond à l'indice de la classe mère à enlever
     */
    public synchronized final void removeExtend(int index){
        synchronized(this._extends){
            this._extends.remove(index);
        }
    }
    
    /**
     * Enlève toutes les classes mères de la classe
     */
    public synchronized final void clearExtends(){
        synchronized(this._extends){
            this._extends.clear();
        }
    }
    
    /**
     * Renvoie le nombre de classes mères de la classe
     * @return Retourne le nombre de classes mères de la classe
     */
    public synchronized final int countExtends(){
        synchronized(this._extends){
            return this._extends.size();
        }
    }
    
    /**
     * Détermine si la classe a des classes mères
     * @return Retourne true, si elle en a, sinon false
     */
    public synchronized final boolean hasExtends(){
        return countExtends() > 0;
    }
    
    /**
     * Renvoie la classe mère à l'indice index, compris dans la classe
     * @param index Correspond à l'indice de la classe mère à renvoyer
     * @return Retourne la classe mère à l'indice index, compris dans la classe
     */
    public synchronized final String getExtend(int index){
        synchronized(this._extends){
            return this._extends.get(index);
        }
    }
    
    /**
     * Renvoie une copie de la liste des interfaces implémentées (attention: il s'agit d'une copie de liste. Donc les modifications réalisées dessus n'auront aucun impact sur la classe)
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
     * Ajoute une/des interface(s) implémentée(s) à la classe
     * @param implement Correspond au(x) interface(s) implémentée(s) à ajouter à la classe
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
     * Enlève une/des interface(s) implémentée(s) de la classe
     * @param implement Correspond au(x) interface(s) implémentée(s) à enlever de la classe
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
     * Enlève une interface implémentée de la classe à l'indice donné
     * @param index Correspond à l'indice de l'interface implémentée à enlever
     */
    public synchronized final void removeImplement(int index){
        synchronized(this._implements){
            this._implements.remove(index);
        }
    }
    
    /**
     * Enlève toutes les interfaces implémentées de la classe
     */
    public synchronized final void clearImplements(){
        synchronized(this._implements){
            this._implements.clear();
        }
    }
    
    /**
     * Renvoie le nombre d'interfaces implémentées de la classe
     * @return Retourne le nombre d'interfaces implémentées de la classe
     */
    public synchronized final int countImplements(){
        synchronized(this._implements){
            return this._implements.size();
        }
    }
    
    /**
     * Détermine si la classe a des interfaces implémentées
     * @return Retourne true, si elle en a, sinon false
     */
    public synchronized final boolean hasImplements(){
        return countImplements() > 0;
    }
    
    /**
     * Renvoie l'interface implémentée à l'indice index, compris dans la classe
     * @param index Correspond à l'indice de l'interface implémentée à renvoyer
     * @return Retourne l'interface implémentée à l'indice index, compris dans la classe
     */
    public synchronized final String getImplement(int index){
        synchronized(this._implements){
            return this._implements.get(index);
        }
    }
    
    /**
     * Renvoie une copie de la liste des paramètres génériques (attention: il s'agit d'une copie de liste. Donc les modifications réalisées dessus n'auront aucun impact sur la classe)
     * @return Retourne une copie de la liste des paramètres génériques
     */
    public synchronized final java.util.List<String> getGenericsParams() {
        synchronized(this._genericsParameters){
            java.util.List<String> copy = new java.util.ArrayList<>();
            for (String tc : this._genericsParameters) {
                copy.add(tc);
            }
            return copy;
        }
    }
    
    /**
     * Ajoute un/des paramètre(s) générique(s) à la classe
     * @param parameter Correspond au(x) paramètre(s) générique(s) à ajouter à la classe
     */
    public synchronized final void addGenericsParams(String...parameter){
        synchronized(this._genericsParameters){
            if(parameter != null){
                for(String code : parameter){
                    if(!this._genericsParameters.contains(code))
                        this._genericsParameters.add(code);
                }
            }
        }
    }
    
    /**
     * Enlève un/des paramètre(s) générique(s) de la classe
     * @param parameter Correspond au(x) paramètre(s) générique(s) à enlever de la classe
     */
    public synchronized final void removeGenericsParams(String...parameter){
        synchronized(this._genericsParameters){
            if(parameter != null){
                for(String code : parameter){
                    if(this._genericsParameters.contains(code))
                        this._genericsParameters.remove(code);
                }
            }
        }
    }
    
    /**
     * Enlève un paramètre générique de la classe à l'indice donné
     * @param index Correspond à l'indice du paramètre générique à enlever
     */
    public synchronized final void removeGenericParam(int index){
        synchronized(this._genericsParameters){
            this._genericsParameters.remove(index);
        }
    }
    
    /**
     * Enlève toutes les paramètres génériques de la classe
     */
    public synchronized final void clearGenericsParams(){
        synchronized(this._genericsParameters){
            this._genericsParameters.clear();
        }
    }
    
    /**
     * Renvoie le nombre de paramètres génériques de la classe
     * @return Retourne le nombre de paramètres génériques de la classe
     */
    public synchronized final int countGenericsParams(){
        synchronized(this._genericsParameters){
            return this._genericsParameters.size();
        }
    }
    
    /**
     * Détermine si la classe a des paramètres génériques
     * @return Retourne true, si elle en a, sinon false
     */
    public synchronized final boolean hasGenericsParams(){
        return countGenericsParams() > 0;
    }
    
    /**
     * Renvoie le paramètre générique à l'indice index, compris dans la classe
     * @param index Correspond à l'indice du paramètre générique à renvoyer
     * @return Retourne le paramètre générique à l'indice index, compris dans la classe
     */
    public synchronized final String getGenericParam(int index){
        synchronized(this._genericsParameters){
            return this._genericsParameters.get(index);
        }
    }
    
    /**
     * Renvoie la signature de la classe
     * @return Retourne la signature de la classe
     */
    @Override
    public String getSignature(){
        StringBuilder chain = new StringBuilder(getVisibility().toString());
        
        if(_final)
            chain.append("final ");
        
        if(_abstract)
            chain.append("abstract ");
        
        if(isStatic())
            chain.append("static ");
        
        if(isStrictfp())
            chain.append("strictfp ");
        
        chain.append("class ").append(getName());
        
        if(!_genericsParameters.isEmpty()){
            chain.append(" <");
            for(int i=0;i<_genericsParameters.size();i++){
                if(i>0) chain.append(", ");
                chain.append(_genericsParameters.get(i));
            }
            chain.append(">");
        }
        
        if(!_extends.isEmpty()){
            chain.append(" extends ");
            for(int i=0;i<_extends.size();i++){
                if(i>0) chain.append(", ");
                chain.append(_extends.get(i));
            }
        }
        
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
     * Renvoie la classe sous la forme d'une chaîne de caractères. Elle contient la javadoc du bout de la classe (s'il y en a une), les annotations de la classe (s'il y en a), la signature de la classe avec le code associé (s'il y en a un)
     * @return Retourne la classe en entier sous la forme d'une chaîne de caractères
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