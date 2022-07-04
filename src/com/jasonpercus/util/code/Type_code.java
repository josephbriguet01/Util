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
 * Cette classe représente les [classes | interfaces | énumérations | @annotations]
 * @author JasonPercus
 * @version 1.0
 */
@SuppressWarnings({"NestedSynchronizedStatement", "SynchronizeOnNonFinalField"})
public abstract class Type_code extends Element_element {
    
    
    
//ATTRIBUTS
    /**
     * Correspond à l'attribut strictfp de la [classe | interface | énumération | @annotation]
     */
    private boolean _strictfp;
    
    /**
     * Correspond à la liste des attributs
     */
    private final java.util.List<Field_element>        _fields;
    
    /**
     * Correspond à la liste des méthodes
     */
    private final java.util.List<Method_element>       _methods;
    
    /**
     * Correspond à la liste des constructeurs
     */
    private final java.util.List<Constructor_element>  _constructors;
    
    /**
     * Correspond à la liste des sous-classes
     */
    private final java.util.List<Class_type>        _subclass;
    
    /**
     * Correspond à la liste des sous-interfaces
     */
    private final java.util.List<Interface_type>    _subinterface;
    
    /**
     * Correspond à la liste des sous-énumérations
     */
    private final java.util.List<Enum_type>         _subenum;
    
    /**
     * Correspond à la liste des sous-@annotations
     */
    private final java.util.List<Annotation_type>   _subannotation;

    
    
//CONSTRUCTORS
    /**
     * Crée un bout de code par défaut
     */
    public Type_code() {
        this._fields        = new java.util.ArrayList<>();
        this._methods       = new java.util.ArrayList<>();
        this._constructors  = new java.util.ArrayList<>();
        this._subclass      = new java.util.ArrayList<>();
        this._subinterface  = new java.util.ArrayList<>();
        this._subenum       = new java.util.ArrayList<>();
        this._subannotation = new java.util.ArrayList<>();
    }
    
    /**
     * Crée une [classe | interface | énumération | @annotation] par défaut
     * @param creator Correspond au créateur de bout de code
     * @param name Correspond au nom de la [classe | interface | énumération | @annotation]
     * @param _public Détermine si le bout de code est public
     * @param _protected Détermine si le bout de code est protected
     * @param _private Détermine si le bout de code est private
     * @param _static Détermine si le bout de code est static
     * @param _strictfp Détermine si le bout de code est strictfp
     * @param parent Correspond à l'éventuel parent de bout de code
     * @deprecated <div style="color: #D45B5B; font-style: italic">NE PAS UTILISER</div>
     */
    public Type_code(CodeCreator creator, String name, boolean _public, boolean _protected, boolean _private, boolean _static, boolean _strictfp, Type_code parent) {
        super(creator, name, _public, _protected, _private, _static, parent);
        this._strictfp      = _strictfp;
        this._fields        = new java.util.ArrayList<>();
        this._methods       = new java.util.ArrayList<>();
        this._constructors  = new java.util.ArrayList<>();
        this._subclass      = new java.util.ArrayList<>();
        this._subinterface  = new java.util.ArrayList<>();
        this._subenum       = new java.util.ArrayList<>();
        this._subannotation = new java.util.ArrayList<>();
    }

    
    
//GETTERS & SETTERS
    /**
     * Renvoie si oui ou non le bout de code est strictfp
     * @return Retourne true s'il l'est, sinon false
     */
    public synchronized boolean isStrictfp() {
        return _strictfp;
    }

    /**
     * Modifie si oui ou non le bout de code doit être strictfp
     * @param _strictfp Correspond à true, s'il soit l'être, sinon false
     */
    public synchronized void setStrictfp(boolean _strictfp) {
        this._strictfp = _strictfp;
    }
    
    
    
//METHODES PUBLICS
    /**
     * Renvoie une copie de la liste des attributs (attention: il s'agit d'une copie de liste. Donc les modifications réalisées dessus n'auront aucun impact sur le bout de code)
     * @return Retourne une copie de la liste des attributs
     */
    public synchronized final java.util.List<Field_element> getFields() {
        synchronized(this._fields){
            java.util.List<Field_element> copy = new java.util.ArrayList<>();
            for (Field_element tc : this._fields) {
                copy.add(tc);
            }
            return copy;
        }
    }
    
    /**
     * Ajoute une/des attribut(s) au bout de code
     * @param fields Correspond au(x) attribut(s) à ajouter au bout de code
     */
    public synchronized final void addFields(Field_element...fields){
        synchronized(this._fields){
            if(fields != null){
                for(Field_element code : fields){
                    if(!this._fields.contains(code)){
                        code.setParent(this);
                        this._fields.add(code);
                    }
                }
            }
        }
    }
    
    /**
     * Enlève une/des attribut(s) au bout de code
     * @param fields Correspond au(x) attribut(s) à enlever du bout de code
     */
    public synchronized final void removeFields(Field_element...fields){
        synchronized(this._fields){
            if(fields != null){
                for(Field_element code : fields){
                    if(this._fields.contains(code)){
                        code.setParent(null);
                        this._fields.remove(code);
                    }
                }
            }
        }
    }
    
    /**
     * Enlève un attribut du bout de code à l'indice donné
     * @param index Correspond à l'indice de l'attribut à enlever
     */
    public synchronized final void removeField(int index){
        synchronized(this._fields){
            Field_element code = this._fields.get(index);
            code.setParent(null);
            this._fields.remove(index);
        }
    }
    
    /**
     * Enlève toutes les attributs du bout de code
     */
    public synchronized final void clearFields(){
        synchronized(this._fields){
            for(Field_element code : this._fields){
                code.setParent(null);
            }
            this._fields.clear();
        }
    }
    
    /**
     * Renvoie le nombre d'attributs du bout de code
     * @return Retourne le nombre d'attributs du bout de code
     */
    public synchronized final int countFields(){
        synchronized(this._fields){
            return this._fields.size();
        }
    }
    
    /**
     * Détermine si le bout de code a des attributs
     * @return Retourne true, s'il en a, sinon false
     */
    public synchronized final boolean hasFields(){
        return countFields() > 0;
    }
    
    /**
     * Renvoie l'attribut à l'indice index, compris dans le bout de code
     * @param index Correspond à l'indice de l'attribut à renvoyer
     * @return Retourne l'attribut à l'indice index, compris dans le bout de code
     */
    public synchronized final Field_element getFields(int index){
        synchronized(this._fields){
            return this._fields.get(index);
        }
    }
    
    /**
     * Renvoie une copie de la liste des méthodes (attention: il s'agit d'une copie de liste. Donc les modifications réalisées dessus n'auront aucun impact sur le bout de code)
     * @return Retourne une copie de la liste des méthodes
     */
    public synchronized final java.util.List<Method_element> getMethods() {
        synchronized(this._methods){
            java.util.List<Method_element> copy = new java.util.ArrayList<>();
            for (Method_element tc : this._methods) {
                copy.add(tc);
            }
            return copy;
        }
    }
    
    /**
     * Ajoute une/des méthode(s) au bout de code
     * @param methods Correspond au(x) méthode(s) à ajouter au bout de code
     */
    public synchronized final void addMethods(Method_element...methods){
        synchronized(this._methods){
            if(methods != null){
                for(Method_element code : methods){
                    if(!this._methods.contains(code)){
                        code.setParent(this);
                        this._methods.add(code);
                    }
                }
            }
        }
    }
    
    /**
     * Enlève une/des méthode(s) au bout de code
     * @param methods Correspond au(x) méthode(s) à enlever du bout de code
     */
    public synchronized final void removeMethods(Method_element...methods){
        synchronized(this._methods){
            if(methods != null){
                for(Method_element code : methods){
                    if(this._methods.contains(code)){
                        code.setParent(null);
                        this._methods.remove(code);
                    }
                }
            }
        }
    }
    
    /**
     * Enlève une méthode du bout de code à l'indice donné
     * @param index Correspond à l'indice de la méthode à enlever
     */
    public synchronized final void removeMethod(int index){
        synchronized(this._methods){
            Method_element code = this._methods.get(index);
            code.setParent(null);
            this._methods.remove(index);
        }
    }
    
    /**
     * Enlève toutes les méthodes du bout de code
     */
    public synchronized final void clearMethods(){
        synchronized(this._methods){
            for(Method_element code : this._methods){
                code.setParent(null);
            }
            this._methods.clear();
        }
    }
    
    /**
     * Renvoie le nombre de méthodes du bout de code
     * @return Retourne le nombre de méthodes du bout de code
     */
    public synchronized final int countMethods(){
        synchronized(this._methods){
            return this._methods.size();
        }
    }
    
    /**
     * Détermine si le bout de code a des méthodes
     * @return Retourne true, s'il en a, sinon false
     */
    public synchronized final boolean hasMethods(){
        return countMethods() > 0;
    }
    
    /**
     * Renvoie la méthode à l'indice index, compris dans le bout de code
     * @param index Correspond à l'indice de la méthode à renvoyer
     * @return Retourne la méthode à l'indice index, compris dans le bout de code
     */
    public synchronized final Method_element getMethods(int index){
        synchronized(this._methods){
            return this._methods.get(index);
        }
    }
    
    /**
     * Renvoie une copie de la liste des constructeurs (attention: il s'agit d'une copie de liste. Donc les modifications réalisées dessus n'auront aucun impact sur le bout de code)
     * @return Retourne une copie de la liste des constructeurs
     */
    public synchronized final java.util.List<Constructor_element> getConstructors() {
        synchronized(this._constructors){
            java.util.List<Constructor_element> copy = new java.util.ArrayList<>();
            for (Constructor_element tc : this._constructors) {
                copy.add(tc);
            }
            return copy;
        }
    }
    
    /**
     * Ajoute un/des constructeur(s) au bout de code
     * @param constructors Correspond au(x) constructeur(s) à ajouter au bout de code
     */
    public synchronized final void addConstructors(Constructor_element...constructors){
        synchronized(this._constructors){
            if(constructors != null){
                for(Constructor_element code : constructors){
                    if(!this._constructors.contains(code)){
                        code.setParent(this);
                        this._constructors.add(code);
                    }
                }
            }
        }
    }
    
    /**
     * Enlève un/des constructeur(s) au bout de code
     * @param constructors Correspond au(x) constructeur(s) à enlever du bout de code
     */
    public synchronized final void removeConstructors(Constructor_element...constructors){
        synchronized(this._constructors){
            if(constructors != null){
                for(Constructor_element code : constructors){
                    if(this._constructors.contains(code)){
                        code.setParent(null);
                        this._constructors.remove(code);
                    }
                }
            }
        }
    }
    
    /**
     * Enlève une constructeur du bout de code à l'indice donné
     * @param index Correspond à l'indice du constructeur à enlever
     */
    public synchronized final void removeConstructor(int index){
        synchronized(this._constructors){
            Constructor_element code = this._constructors.get(index);
            code.setParent(null);
            this._constructors.remove(index);
        }
    }
    
    /**
     * Enlève toutes les constructeurs du bout de code
     */
    public synchronized final void clearConstructors(){
        synchronized(this._constructors){
            for(Constructor_element code : this._constructors){
                code.setParent(null);
            }
            this._constructors.clear();
        }
    }
    
    /**
     * Renvoie le nombre de constructeurs du bout de code
     * @return Retourne le nombre de constructeurs du bout de code
     */
    public synchronized final int countConstructors(){
        synchronized(this._constructors){
            return this._constructors.size();
        }
    }
    
    /**
     * Détermine si le bout de code a des constructeurs
     * @return Retourne true, s'il en a, sinon false
     */
    public synchronized final boolean hasConstructors(){
        return countConstructors() > 0;
    }
    
    /**
     * Renvoie le constructeur à l'indice index, compris dans le bout de code
     * @param index Correspond à l'indice du constructeur à renvoyer
     * @return Retourne le constructeur à l'indice index, compris dans le bout de code
     */
    public synchronized final Constructor_element getConstructors(int index){
        synchronized(this._constructors){
            return this._constructors.get(index);
        }
    }
    
    /**
     * Renvoie une copie de la liste des sous-classes (attention: il s'agit d'une copie de liste. Donc les modifications réalisées dessus n'auront aucun impact sur le bout de code)
     * @return Retourne une copie de la liste des sous-classes
     */
    public synchronized final java.util.List<Class_type> getSubClass() {
        synchronized(this._subclass){
            java.util.List<Class_type> copy = new java.util.ArrayList<>();
            for (Class_type tc : this._subclass) {
                copy.add(tc);
            }
            return copy;
        }
    }
    
    /**
     * Ajoute une/des sous-classe(s) au bout de code
     * @param subclass Correspond au(x) sous-classe(s) à ajouter au bout de code
     */
    public synchronized final void addSubClass(Class_type...subclass){
        synchronized(this._subclass){
            if(subclass != null){
                for(Class_type code : subclass){
                    if(!this._subclass.contains(code)){
                        code.setParent(this);
                        this._subclass.add(code);
                    }
                }
            }
        }
    }
    
    /**
     * Enlève une/des sous-classe(s) au bout de code
     * @param subclass Correspond au(x) sous-classe(s) à enlever du bout de code
     */
    public synchronized final void removeSubClass(Class_type...subclass){
        synchronized(this._subclass){
            if(subclass != null){
                for(Class_type code : subclass){
                    if(this._subclass.contains(code)){
                        code.setParent(null);
                        this._subclass.remove(code);
                    }
                }
            }
        }
    }
    
    /**
     * Enlève une sous-classe du bout de code à l'indice donné
     * @param index Correspond à l'indice de la sous-classe à enlever
     */
    public synchronized final void removeSubClass(int index){
        synchronized(this._subclass){
            Class_type code = this._subclass.get(index);
            code.setParent(null);
            this._subclass.remove(index);
        }
    }
    
    /**
     * Enlève toutes les sous-classes du bout de code
     */
    public synchronized final void clearSubClass(){
        synchronized(this._subclass){
            for(Class_type code : this._subclass){
                code.setParent(null);
            }
            this._subclass.clear();
        }
    }
    
    /**
     * Renvoie le nombre de sous-classes du bout de code
     * @return Retourne le nombre de sous-classes du bout de code
     */
    public synchronized final int countSubClass(){
        synchronized(this._subclass){
            return this._subclass.size();
        }
    }
    
    /**
     * Détermine si le bout de code a des sous-classes
     * @return Retourne true, s'il en a, sinon false
     */
    public synchronized final boolean hasSubClass(){
        return countSubClass() > 0;
    }
    
    /**
     * Renvoie la sous-classe à l'indice index, compris dans le bout de code
     * @param index Correspond à l'indice de la sous-classe à renvoyer
     * @return Retourne la sous-classe à l'indice index, compris dans le bout de code
     */
    public synchronized final Class_type getSubClass(int index){
        synchronized(this._subclass){
            return this._subclass.get(index);
        }
    }
    
    /**
     * Renvoie une copie de la liste des sous-interfaces (attention: il s'agit d'une copie de liste. Donc les modifications réalisées dessus n'auront aucun impact sur le bout de code)
     * @return Retourne une copie de la liste des sous-interfaces
     */
    public synchronized final java.util.List<Interface_type> getSubInterfaces() {
        synchronized(this._subinterface){
            java.util.List<Interface_type> copy = new java.util.ArrayList<>();
            for (Interface_type tc : this._subinterface) {
                copy.add(tc);
            }
            return copy;
        }
    }
    
    /**
     * Ajoute une/des sous-interface(s) au bout de code
     * @param subinterface Correspond au(x) sous-interface(s) à ajouter au bout de code
     */
    public synchronized final void addSubInterfaces(Interface_type...subinterface){
        synchronized(this._subinterface){
            if(subinterface != null){
                for(Interface_type code : subinterface){
                    if(!this._subinterface.contains(code)){
                        code.setParent(this);
                        this._subinterface.add(code);
                    }
                }
            }
        }
    }
    
    /**
     * Enlève une/des sous-interface(s) au bout de code
     * @param subinterface Correspond au(x) sous-interface(s) à enlever du bout de code
     */
    public synchronized final void removeSubInterfaces(Interface_type...subinterface){
        synchronized(this._subinterface){
            if(subinterface != null){
                for(Interface_type code : subinterface){
                    if(this._subinterface.contains(code)){
                        code.setParent(null);
                        this._subinterface.remove(code);
                    }
                }
            }
        }
    }
    
    /**
     * Enlève une sous-interface du bout de code à l'indice donné
     * @param index Correspond à l'indice de la sous-interface à enlever
     */
    public synchronized final void removeSubInterface(int index){
        synchronized(this._subinterface){
            Interface_type code = this._subinterface.get(index);
            code.setParent(null);
            this._subinterface.remove(index);
        }
    }
    
    /**
     * Enlève toutes les sous-interfaces du bout de code
     */
    public synchronized final void clearSubInterfaces(){
        synchronized(this._subinterface){
            for(Interface_type code : this._subinterface){
                code.setParent(null);
            }
            this._subinterface.clear();
        }
    }
    
    /**
     * Renvoie le nombre de sous-interfaces du bout de code
     * @return Retourne le nombre de sous-interfaces du bout de code
     */
    public synchronized final int countSubInterfaces(){
        synchronized(this._subinterface){
            return this._subinterface.size();
        }
    }
    
    /**
     * Détermine si le bout de code a des sous-interfaces
     * @return Retourne true, s'il en a, sinon false
     */
    public synchronized final boolean hasSubInterfaces(){
        return countSubInterfaces() > 0;
    }
    
    /**
     * Renvoie la sous-interface à l'indice index, compris dans le bout de code
     * @param index Correspond à l'indice de la sous-interface à renvoyer
     * @return Retourne la sous-interface à l'indice index, compris dans le bout de code
     */
    public synchronized final Interface_type getSubInterface(int index){
        synchronized(this._subinterface){
            return this._subinterface.get(index);
        }
    }
    
    /**
     * Renvoie une copie de la liste des sous-énumérations (attention: il s'agit d'une copie de liste. Donc les modifications réalisées dessus n'auront aucun impact sur le bout de code)
     * @return Retourne une copie de la liste des sous-énumérations
     */
    public synchronized final java.util.List<Enum_type> getSubEnums() {
        synchronized(this._subenum){
            java.util.List<Enum_type> copy = new java.util.ArrayList<>();
            for (Enum_type tc : this._subenum) {
                copy.add(tc);
            }
            return copy;
        }
    }
    
    /**
     * Ajoute une/des sous-énumération(s) au bout de code
     * @param subenum Correspond au(x) sous-énumération(s) à ajouter au bout de code
     */
    public synchronized final void addSubEnums(Enum_type...subenum){
        synchronized(this._subenum){
            if(subenum != null){
                for(Enum_type code : subenum){
                    if(!this._subenum.contains(code)){
                        code.setParent(this);
                        this._subenum.add(code);
                    }
                }
            }
        }
    }
    
    /**
     * Enlève une/des sous-énumération(s) au bout de code
     * @param subenum Correspond au(x) sous-énumération(s) à enlever du bout de code
     */
    public synchronized final void removeSubEnums(Enum_type...subenum){
        synchronized(this._subenum){
            if(subenum != null){
                for(Enum_type code : subenum){
                    if(this._subenum.contains(code)){
                        code.setParent(null);
                        this._subenum.remove(code);
                    }
                }
            }
        }
    }
    
    /**
     * Enlève une sous-énumération du bout de code à l'indice donné
     * @param index Correspond à l'indice de la sous-énumération à enlever
     */
    public synchronized final void removeSubEnum(int index){
        synchronized(this._subenum){
            Enum_type code = this._subenum.get(index);
            code.setParent(null);
            this._subenum.remove(index);
        }
    }
    
    /**
     * Enlève toutes les sous-énumérations du bout de code
     */
    public synchronized final void clearSubEnums(){
        synchronized(this._subenum){
            for(Enum_type code : this._subenum){
                code.setParent(null);
            }
            this._subenum.clear();
        }
    }
    
    /**
     * Renvoie le nombre de sous-énumérations du bout de code
     * @return Retourne le nombre de sous-énumérations du bout de code
     */
    public synchronized final int countSubEnums(){
        synchronized(this._subenum){
            return this._subenum.size();
        }
    }
    
    /**
     * Détermine si le bout de code a des sous-énumérations
     * @return Retourne true, s'il en a, sinon false
     */
    public synchronized final boolean hasSubEnums(){
        return countSubEnums() > 0;
    }
    
    /**
     * Renvoie la sous-énumération à l'indice index, compris dans le bout de code
     * @param index Correspond à l'indice de la sous-énumération à renvoyer
     * @return Retourne la sous-énumération à l'indice index, compris dans le bout de code
     */
    public synchronized final Enum_type getSubEnum(int index){
        synchronized(this._subenum){
            return this._subenum.get(index);
        }
    }
    
    /**
     * Renvoie une copie de la liste des sous-annotations (attention: il s'agit d'une copie de liste. Donc les modifications réalisées dessus n'auront aucun impact sur le bout de code)
     * @return Retourne une copie de la liste des sous-annotations
     */
    public synchronized final java.util.List<Annotation_type> getSubAnnotations() {
        synchronized(this._subannotation){
            java.util.List<Annotation_type> copy = new java.util.ArrayList<>();
            for (Annotation_type tc : this._subannotation) {
                copy.add(tc);
            }
            return copy;
        }
    }
    
    /**
     * Ajoute une/des sous-annotation(s) au bout de code
     * @param subannotation Correspond au(x) sous-annotation(s) à ajouter au bout de code
     */
    public synchronized final void addSubAnnotations(Annotation_type...subannotation){
        synchronized(this._subannotation){
            if(subannotation != null){
                for(Annotation_type code : subannotation){
                    if(!this._subannotation.contains(code)){
                        code.setParent(this);
                        this._subannotation.add(code);
                    }
                }
            }
        }
    }
    
    /**
     * Enlève une/des sous-annotation(s) au bout de code
     * @param subannotation Correspond au(x) sous-annotation(s) à enlever du bout de code
     */
    public synchronized final void removeSubAnnotations(Annotation_type...subannotation){
        synchronized(this._subannotation){
            if(subannotation != null){
                for(Annotation_type code : subannotation){
                    if(this._subannotation.contains(code)){
                        code.setParent(null);
                        this._subannotation.remove(code);
                    }
                }
            }
        }
    }
    
    /**
     * Enlève une sous-annotation du bout de code à l'indice donné
     * @param index Correspond à l'indice de la sous-annotation à enlever
     */
    public synchronized final void removeSubAnnotation(int index){
        synchronized(this._subannotation){
            Annotation_type code = this._subannotation.get(index);
            code.setParent(null);
            this._subannotation.remove(index);
        }
    }
    
    /**
     * Enlève toutes les sous-annotations du bout de code
     */
    public synchronized final void clearSubAnnotations(){
        synchronized(this._subannotation){
            for(Annotation_type code : this._subannotation){
                code.setParent(null);
            }
            this._subannotation.clear();
        }
    }
    
    /**
     * Renvoie le nombre de sous-annotations du bout de code
     * @return Retourne le nombre de sous-annotations du bout de code
     */
    public synchronized final int countSubAnnotations(){
        synchronized(this._subannotation){
            return this._subannotation.size();
        }
    }
    
    /**
     * Détermine si le bout de code a des sous-annotations
     * @return Retourne true, s'il en a, sinon false
     */
    public synchronized final boolean hasSubAnnotations(){
        return countSubAnnotations() > 0;
    }
    
    /**
     * Renvoie la sous-annotation à l'indice index, compris dans le bout de code
     * @param index Correspond à l'indice de la sous-annotation à renvoyer
     * @return Retourne la sous-annotation à l'indice index, compris dans le bout de code
     */
    public synchronized final Annotation_type getSubAnnotation(int index){
        synchronized(this._subannotation){
            return this._subannotation.get(index);
        }
    }
    
    
    
}