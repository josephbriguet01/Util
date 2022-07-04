/*
 * Copyright (C) JasonPercus Systems, Inc - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by JasonPercus, 06/2021
 */
package com.jasonpercus.util.code;



import com.github.javaparser.ast.body.AnnotationDeclaration;
import com.github.javaparser.ast.body.AnnotationMemberDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.EnumConstantDeclaration;
import com.github.javaparser.ast.body.EnumDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.comments.JavadocComment;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.jasonpercus.util.FileCode;



/**
 * Cette classe permet de créer du code
 * @see FileCode
 * @author JasonPercus
 * @version 1.0
 */
public class CodeCreator {

    
    
//CONSTRUCTOR
    /**
     * Crée un objet de création de code
     */
    public CodeCreator() {
        
    }
    
    
    
//METHODES PUBLICS
    /**
     * Crée un Package_element
     * @param creator Correspond au creator qui crée les sous-blocs de code
     * @param name Correspond au nom du package (ex: java.io)
     * @param parent Correspond au code parent de celui-ci
     * @return Retourne un Package_element
     */
    public Package_element createPackage(CodeCreator creator, String name, Code parent){
        return new Package_element(creator, name, parent);
    }
    
    /**
     * Crée un Import_element
     * @param creator Correspond au creator qui crée les sous-blocs de code
     * @param name Correspond au nom de l'import (ex: java.io.File)
     * @param isStatic Si l'import est static
     * @param isAll Si l'import désigne toutes les classes d'un package (ex: java.io.*)
     * @param parent Correspond au code parent de celui-ci
     * @return Retourne un Import_element
     */
    public Import_element createImport(CodeCreator creator, String name, boolean isStatic, boolean isAll, Code parent){
        return new Import_element(creator, name, isStatic, isAll, parent);
    }
    
    /**
     * Crée une Interface_type
     * @param creator Correspond au creator qui crée les sous-blocs de code
     * @param parent Correspond à l'éventuelle [classe | interface | énumération | @annotation] parente de cette interface
     * @param coid Correspond au bloc représentant l'interface de JavaParser
     * @return Retourne une Interface_type
     */
    public Interface_type createInterface(CodeCreator creator, Type_code parent, ClassOrInterfaceDeclaration coid){
        return new Interface_type(creator, parent, coid);
    }
    
    /**
     * Crée une Class_type
     * @param creator Correspond au creator qui crée les sous-blocs de code
     * @param parent Correspond à l'éventuelle [classe | interface | énumération | @annotation] parente de cette classe
     * @param coid Correspond au bloc représentant la classe de JavaParser
     * @return Retourne une Class_type
     */
    public Class_type createClass(CodeCreator creator, Type_code parent, ClassOrInterfaceDeclaration coid){
        return new Class_type(creator, parent, coid);
    }
    
    /**
     * Crée une Enum_type
     * @param creator Correspond au creator qui crée les sous-blocs de code
     * @param parent Correspond à l'éventuelle [classe | interface | énumération | @annotation] parente de cette énumération
     * @param ed Correspond au bloc représentant l'énumération de JavaParser
     * @return Retourne une Enum_type
     */
    public Enum_type createEnum(CodeCreator creator, Type_code parent, EnumDeclaration ed){
        return new Enum_type(creator, parent, ed);
    }
    
    /**
     * Crée une Annotation_type
     * @param creator Correspond au creator qui crée les sous-blocs de code
     * @param parent Correspond à l'éventuelle [classe | interface | énumération | @annotation] parente de cette annotation
     * @param ad Correspond au bloc représentant l'annotation de JavaParser
     * @return Retourne une Annotation_type
     */
    public Annotation_type createAnnotation(CodeCreator creator, Type_code parent, AnnotationDeclaration ad){
        return new Annotation_type(creator, parent, ad);
    }
    
    /**
     * Crée une Javadoc_code
     * @param creator Correspond au creator qui crée les sous-blocs de code
     * @param jc Correspond au bloc représentant un bloc de commentaire de JavaParser
     * @param parent Correspond au code parent de celui-ci
     * @return Retourne une Javadoc_code
     */
    public Javadoc_code createJavadoc(CodeCreator creator, JavadocComment jc, Code parent){
        return new Javadoc_code(creator, jc, parent);
    }
    
    /**
     * Crée un description pour la javadoc
     * @param creator Correspond au creator qui crée les sous-blocs de code
     * @param description Correspond à la description de la javadoc
     * @param parent Correspond au code parent de celui-ci
     * @return Retourne la description pour la javadoc
     */
    public Description_javadoc createDescriptionJavadoc(CodeCreator creator, String description, Code parent){
        return new Description_javadoc(creator, description, parent);
    }
    
    /**
     * Crée un tag @author pour la javadoc
     * @param creator Correspond au creator qui crée les sous-blocs de code
     * @param author Correspond à l'auteur du code pour la documentation
     * @param parent Correspond au code parent de celui-ci
     * @return Retourne un tag pour la javadoc
     */
    public Author_javadoc createAuthorJavadoc(CodeCreator creator, String author, Code parent){
        return new Author_javadoc(creator, author, parent);
    }
    
    /**
     * Crée un tag @deprecated pour la javadoc
     * @param creator Correspond au creator qui crée les sous-blocs de code
     * @param description Correspond à la description du tag
     * @param parent Correspond au code parent de celui-ci
     * @return Retourne un tag pour la javadoc
     */
    public Deprecated_javadoc createDeprecatedJavadoc(CodeCreator creator, String description, Code parent){
        return new Deprecated_javadoc(creator, description, parent);
    }
    
    /**
     * Crée un tag @param pour la javadoc
     * @param creator Correspond au creator qui crée les sous-blocs de code
     * @param name Correspond au nom du paramètre
     * @param description Correspond à la description du tag
     * @param parent Correspond au code parent de celui-ci
     * @return Retourne un tag pour la javadoc
     */
    public Param_javadoc createParamJavadoc(CodeCreator creator, String name, String description, Code parent){
        return new Param_javadoc(creator, name, description, parent);
    }
    
    /**
     * Crée un tag @see pour la javadoc
     * @param creator Correspond au creator qui crée les sous-blocs de code
     * @param description Correspond à la description du tag
     * @param parent Correspond au code parent de celui-ci
     * @return Retourne un tag pour la javadoc
     */
    public See_javadoc createSeeJavadoc(CodeCreator creator, String description, Code parent){
        return new See_javadoc(creator, description, parent);
    }
    
    /**
     * Crée un tag @serial pour la javadoc
     * @param creator Correspond au creator qui crée les sous-blocs de code
     * @param name Correspond au nom du paramètre
     * @param description Correspond à la description du tag
     * @param parent Correspond au code parent de celui-ci
     * @return Retourne un tag pour la javadoc
     */
    public Serial_javadoc createSerialJavadoc(CodeCreator creator, String name, String description, Code parent){
        return new Serial_javadoc(creator, name, description, parent);
    }
    
    /**
     * Crée un tag @serialField pour la javadoc
     * @param creator Correspond au creator qui crée les sous-blocs de code
     * @param name Correspond au nom du paramètre
     * @param description Correspond à la description du tag
     * @param parent Correspond au code parent de celui-ci
     * @return Retourne un tag pour la javadoc
     */
    public SerialField_javadoc createSerialFieldJavadoc(CodeCreator creator, String name, String description, Code parent){
        return new SerialField_javadoc(creator, name, description, parent);
    }
    
    /**
     * Crée un tag @serialData pour la javadoc
     * @param creator Correspond au creator qui crée les sous-blocs de code
     * @param description Correspond à la description du tag
     * @param parent Correspond au code parent de celui-ci
     * @return Retourne un tag pour la javadoc
     */
    public SerialData_javadoc createSerialDataJavadoc(CodeCreator creator, String description, Code parent){
        return new SerialData_javadoc(creator, description, parent);
    }
    
    /**
     * Crée un tag @since pour la javadoc
     * @param creator Correspond au creator qui crée les sous-blocs de code
     * @param since Correspond à la description du tag
     * @param parent Correspond au code parent de celui-ci
     * @return Retourne un tag pour la javadoc
     */
    public Since_javadoc createSinceJavadoc(CodeCreator creator, String since, Code parent){
        return new Since_javadoc(creator, since, parent);
    }
    
    /**
     * Crée un tag @version pour la javadoc
     * @param creator Correspond au creator qui crée les sous-blocs de code
     * @param version Correspond à la description du tag
     * @param parent Correspond au code parent de celui-ci
     * @return Retourne un tag pour la javadoc
     */
    public Version_javadoc createVersionJavadoc(CodeCreator creator, String version, Code parent){
        return new Version_javadoc(creator, version, parent);
    }
    
    /**
     * Crée un tag @exception pour la javadoc
     * @param creator Correspond au creator qui crée les sous-blocs de code
     * @param type Correspond au type de paramètre
     * @param description Correspond à la description du tag
     * @param parent Correspond au code parent de celui-ci
     * @return Retourne un tag pour la javadoc
     */
    public Exception_javadoc createExceptionJavadoc(CodeCreator creator, String type, String description, Code parent){
        return new Exception_javadoc(creator, type, description, parent);
    }
    
    /**
     * Crée un tag @throws pour la javadoc
     * @param creator Correspond au creator qui crée les sous-blocs de code
     * @param type Correspond au type de paramètre
     * @param description Correspond à la description du tag
     * @param parent Correspond au code parent de celui-ci
     * @return Retourne un tag pour la javadoc
     */
    public Throws_javadoc createThrowsJavadoc(CodeCreator creator, String type, String description, Code parent){
        return new Throws_javadoc(creator, type, description, parent);
    }
    
    /**
     * Crée un tag @return pour la javadoc
     * @param creator Correspond au creator qui crée les sous-blocs de code
     * @param description Correspond à la description du tag
     * @param parent Correspond au code parent de celui-ci
     * @return Retourne un tag pour la javadoc
     */
    public Return_javadoc createReturnJavadoc(CodeCreator creator, String description, Code parent){
        return new Return_javadoc(creator, description, parent);
    }
    
    /**
     * Crée un tag inconnu pour la javadoc
     * @param creator Correspond au creator qui crée les sous-blocs de code
     * @param type Correspond au type de paramètre
     * @param description Correspond à la description du tag
     * @param parent Correspond au code parent de celui-ci
     * @return Retourne un tag inconnu pour la javadoc
     */
    public Unknown_javadoc createUnknownJavadoc(CodeCreator creator, String type, String description, Code parent){
        return new Unknown_javadoc(creator, type, description, parent);
    }
    
    /**
     * Crée une annotation (ex @SupressWarning...)
     * @param creator Correspond au creator qui crée les sous-blocs de code
     * @param ae Correspond au bloc représentant une annotation de JavaParser
     * @param parent Correspond au code parent de celui-ci
     * @return Retourne une annotation (qui pourra se poser sur une classe, une méthode...
     */
    public Annotation_element createAnnotationElement(CodeCreator creator, AnnotationExpr ae, Code parent){
        return new Annotation_element(creator, ae, parent);
    }
    
    /**
     * Crée un membre d'annotation (ex: Mode mode() default Mode.IF_NOT_EXISTS)
     * @param creator Correspond au créateur de bout de code
     * @param amd Correspond au bloc représentant le membre d'annotation de JavaParser
     * @param parent Correspond à l'interface d'annotation parent de ce membre
     * @return Retourne un membre d'annotation
     */
    public AnnotationMember_element createAnnotationMemberElement(CodeCreator creator, AnnotationMemberDeclaration amd, Annotation_type parent){
        return new AnnotationMember_element(creator, amd, parent);
    }
    
    /**
     * Crée un paramètre
     * @param creator Correspond au creator qui crée les sous-blocs de code
     * @param parameter Correspond au bloc représentant un paramètre de JavaParser
     * @param parent Correspond au [constructeur | méthode] parent de ce paramètre
     * @return Retourne un paramètre
     */
    public Parameter_element createParameterElement(CodeCreator creator, Parameter parameter, Element_element parent){
        return new Parameter_element(creator, parameter, parent);
    }
    
    /**
     * Crée une constante d'énumération
     * @param creator Correspond au creator qui crée les sous-blocs de code
     * @param ecd Correspond au bloc représentant la constante d'énumération de JavaParser
     * @param parent Correspond au code parent de celui-ci
     * @return Retourne une constante d'énumération
     */
    public EnumConst_element createConstEnumElement(CodeCreator creator, EnumConstantDeclaration ecd, Code parent){
        return new EnumConst_element(creator, ecd, parent);
    }
    
    /**
     * Crée un attribut
     * @param creator Correspond au creator qui crée les sous-blocs de code
     * @param fd Correspond au bloc représentant l'attribut de JavaParser
     * @param vd Correspond au bloc représentant la variable de l'attribut de JavaParser
     * @param parent Correspond à la [classe | interface | énumération] parente de cet attribut
     * @return Retourne un attribut
     */
    public Field_element createFieldElement(CodeCreator creator, FieldDeclaration fd, VariableDeclarator vd, Type_code parent){
        return new Field_element(creator, fd, vd, parent);
    }
    
    /**
     * Crée un constructeur
     * @param creator Correspond au creator qui crée les sous-blocs de code
     * @param cd Correspond au bloc représentant le constructeur de JavaParser
     * @param parent Correspond à la [classe | énumération] parente de ce constructeur
     * @return Retourne un constructeur
     */
    public Constructor_element createConstructorElement(CodeCreator creator, ConstructorDeclaration cd, Type_code parent){
        return new Constructor_element(creator, cd, parent);
    }
    
    /**
     * Crée une méthode
     * @param creator Correspond au creator qui crée les sous-blocs de code
     * @param md Correspond au bloc représentant la méthode de JavaParser
     * @param parent Correspond à la [classe | interface | énumération | annotation] parente de cette méthode
     * @return Retourne une méthode
     */
    public Method_element createMethodElement(CodeCreator creator, MethodDeclaration md, Type_code parent){
        return new Method_element(creator, md, parent);
    }
    
    
    
}