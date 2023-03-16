/*
 * Copyright (C) JasonPercus Systems, Inc - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by JasonPercus, 06/2021
 */
package com.jasonpercus.util.code;



import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.type.ReferenceType;
import com.github.javaparser.ast.type.TypeParameter;



/**
 * Cette classe représente un constructeur
 * @author JasonPercus
 * @version 1.0
 */
@SuppressWarnings({"NestedSynchronizedStatement", "SynchronizeOnNonFinalField", "LeakingThisInConstructor"})
public class Constructor_element extends Element_element {
    
    
    
//ATTRIBUTS
    /**
     * Correspond à l'attribut strictfp du constructeur
     */
    private boolean _strictfp;

    /**
     * Détermine si le constructeur est final ou pas
     */
    private boolean _final;
    
    /**
     * Détermine si le constructeur est abstrait ou pas
     */
    private boolean _abstract;
    
    /**
     * Correspond au code associé au constructeur
     */
    private String _code;
    
    /**
     * Correspond à la liste des paramètres génériques
     */
    private final java.util.List<String> _genericsParameters;
    
    /**
     * Correspond au(x) paramètre(s) du constructeur
     */
    private final java.util.List<Parameter_element> _parameters;
    
    /**
     * Correspond à la liste des exceptions levées par le constructeur
     */
    private final java.util.List<String> _throws;

    
    
//CONSTRUCTORS
    /**
     * Crée un constructeur
     */
    public Constructor_element() {
        super();
        this._genericsParameters = new java.util.ArrayList<>();
        this._parameters         = new java.util.ArrayList<>();
        this._throws             = new java.util.ArrayList<>();
    }
    
    /**
     * Crée un constructeur
     * @param creator Correspond au creator qui crée les sous-blocs de code
     * @param cd Correspond au bloc représentant le constructeur de JavaParser
     * @param parent Correspond à la [classe | énumération] parente de ce constructeur
     * @deprecated Ne pas utiliser
     */
    @Deprecated
    protected Constructor_element(CodeCreator creator, ConstructorDeclaration cd, Type_code parent) {
        super(null, cd.getNameAsString(), cd.isPublic(), cd.isProtected(), cd.isPrivate(), cd.isStatic(), parent);
        this._final              = cd.isFinal();
        this._abstract           = cd.isAbstract();
        this._strictfp           = cd.isStrictfp();
        this._genericsParameters = new java.util.ArrayList<>();
        this._parameters         = new java.util.ArrayList<>();
        this._throws             = new java.util.ArrayList<>();
        
        NodeList<TypeParameter> _parametersB = cd.getTypeParameters();
        for (TypeParameter tp : _parametersB)
            this._genericsParameters.add(tp.asString());
        
        for(Parameter p : cd.getParameters())
            this._parameters.add(creator.createParameterElement(creator, p, this));
        
        String code = cd.getBody().toString();
        if(code.length() > 4){
            code = code.substring(3, code.length()-2);
            String[] split = code.split("\\n");
            StringBuilder chain = new StringBuilder();
            for(String s : split){
                if(chain.length() > 0) chain.append("\n");
                chain.append(s.replaceAll("(^\\s{4})", ""));
            }
            this._code = chain.toString();
        }else{
            this._code = "";
        }
        
        for(ReferenceType rt : cd.getThrownExceptions())
            this._throws.add(rt.asString());
        
        if(cd.hasJavaDocComment())
            setJavadoc(creator.createJavadoc(creator, cd.getJavadocComment().get(), this));
        
        for(AnnotationExpr ae : cd.getAnnotations())
            addAnnotations(creator.createAnnotationElement(creator, ae, this));
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

    /**
     * Détermine si la méthode est final ou pas
     * @return True si elle l'est, sinon false
     */
    public synchronized final boolean isFinal() {
        return _final;
    }

    /**
     * Modifie si oui ou non la méthode doit être final ou pas
     * @param _final True si elle doit l'être, sinon false
     */
    public synchronized final void setFinal(boolean _final) {
        this._final = _final;
    }

    /**
     * Détermine si la méthode est abstraite ou pas
     * @return True si elle l'est, sinon false
     */
    public synchronized final boolean isAbstract() {
        return _abstract;
    }

    /**
     * Modifie si oui ou non la méthode doit être abstraite ou pas
     * @param _abstract True si elle doit l'être, sinon false
     */
    public synchronized final void setAbstract(boolean _abstract) {
        this._abstract = _abstract;
    }
    
    /**
     * Renvoie le nom du constructeur avec sa liste des paramètres
     * @return Retourne le nom du constructeur avec sa liste des paramètres
     */
    public synchronized final String getNameAndParameters(){
        StringBuilder chain = new StringBuilder(getName());
        chain.append("(");
        for(int i=0;i<getParams().size();i++){
            if(i>0) chain.append(", ");
            chain.append(getParam(i));
        }
        chain.append(")");
        return chain.toString();
    }

    /**
     * Renvoie le code du constructeur
     * @return Retourne le code du constructeur
     */
    public synchronized String getCode() {
        return _code;
    }

    /**
     * Modifie le code du constructeur
     * @param code Correspond au code du constructeur
     */
    public synchronized void setCode(String code) {
        this._code = code;
    }
    
    
    
//METHODES PUBLICS
    /**
     * Renvoie une copie de la liste des paramètres génériques (attention: il s'agit d'une copie de liste. Donc les modifications réalisées dessus n'auront aucun impact sur le constructeur)
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
     * Ajoute un/des paramètre(s) générique(s) au constructeur
     * @param parameter Correspond au(x) paramètre(s) générique(s) à ajouter au constructeur
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
     * Enlève un/des paramètre(s) générique(s) au constructeur
     * @param parameter Correspond au(x) paramètre(s) générique(s) à enlever au constructeur
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
     * Enlève un paramètre générique au constructeur à l'indice donné
     * @param index Correspond à l'indice du paramètre générique à enlever
     */
    public synchronized final void removeGenericParam(int index){
        synchronized(this._genericsParameters){
            this._genericsParameters.remove(index);
        }
    }
    
    /**
     * Enlève toutes les paramètres génériques au constructeur
     */
    public synchronized final void clearGenericsParams(){
        synchronized(this._genericsParameters){
            this._genericsParameters.clear();
        }
    }
    
    /**
     * Renvoie le nombre de paramètres génériques au constructeur
     * @return Retourne le nombre de paramètres génériques au constructeur
     */
    public synchronized final int countGenericsParams(){
        synchronized(this._genericsParameters){
            return this._genericsParameters.size();
        }
    }
    
    /**
     * Détermine si le constructeur a des paramètres génériques
     * @return Retourne true, si elle en a, sinon false
     */
    public synchronized final boolean hasGenericsParams(){
        return countGenericsParams() > 0;
    }
    
    /**
     * Renvoie le paramètre générique à l'indice index, compris dans le constructeur
     * @param index Correspond à l'indice du paramètre générique à renvoyer
     * @return Retourne le paramètre générique à l'indice index, compris dans le constructeur
     */
    public synchronized final String getGenericParam(int index){
        synchronized(this._genericsParameters){
            return this._genericsParameters.get(index);
        }
    }
    
    /**
     * Renvoie une copie de la liste des paramètres (attention: il s'agit d'une copie de liste. Donc les modifications réalisées dessus n'auront aucun impact sur le constructeur)
     * @return Retourne une copie de la liste des paramètres
     */
    public synchronized final java.util.List<Parameter_element> getParams() {
        synchronized(this._parameters){
            java.util.List<Parameter_element> copy = new java.util.ArrayList<>();
            for (Parameter_element tc : this._parameters) {
                copy.add(tc);
            }
            return copy;
        }
    }
    
    /**
     * Ajoute un/des paramètre(s) au constructeur
     * @param parameter Correspond au(x) paramètre(s) à ajouter au constructeur
     */
    public synchronized final void addParams(Parameter_element...parameter){
        synchronized(this._parameters){
            if(parameter != null){
                for(Parameter_element code : parameter){
                    if(!this._parameters.contains(code)){
                        code.setParent(this);
                        this._parameters.add(code);
                    }
                }
            }
        }
    }
    
    /**
     * Enlève un/des paramètre(s) au constructeur
     * @param parameter Correspond au(x) paramètre(s) à enlever au constructeur
     */
    public synchronized final void removeParams(Parameter_element...parameter){
        synchronized(this._parameters){
            if(parameter != null){
                for(Parameter_element code : parameter){
                    if(this._parameters.contains(code)){
                        code.setParent(null);
                        this._parameters.remove(code);
                    }
                }
            }
        }
    }
    
    /**
     * Enlève un paramètre du constructeur à l'indice donné
     * @param index Correspond à l'indice du paramètre à enlever
     */
    public synchronized final void removeParam(int index){
        synchronized(this._parameters){
            Parameter_element code = this._parameters.get(index);
            code.setParent(null);
            this._parameters.remove(index);
        }
    }
    
    /**
     * Enlève toutes les paramètres du constructeur
     */
    public synchronized final void clearParams(){
        synchronized(this._parameters){
            for(Parameter_element code : this._parameters){
                code.setParent(null);
            }
            this._parameters.clear();
        }
    }
    
    /**
     * Renvoie le nombre de paramètres au constructeur
     * @return Retourne le nombre de paramètres du constructeur
     */
    public synchronized final int countParams(){
        synchronized(this._parameters){
            return this._parameters.size();
        }
    }
    
    /**
     * Détermine si le constructeur a des paramètres
     * @return Retourne true, si elle en a, sinon false
     */
    public synchronized final boolean hasParams(){
        return countParams() > 0;
    }
    
    /**
     * Renvoie le paramètre à l'indice index, compris dans le constructeur
     * @param index Correspond à l'indice du paramètre à renvoyer
     * @return Retourne le paramètre à l'indice index, compris dans le constructeur
     */
    public synchronized final Parameter_element getParam(int index){
        synchronized(this._parameters){
            return this._parameters.get(index);
        }
    }
    
    /**
     * Renvoie une copie de la liste des exceptions (attention: il s'agit d'une copie de liste. Donc les modifications réalisées dessus n'auront aucun impact sur la méthode)
     * @return Retourne une copie de la liste des exceptions
     */
    public synchronized final java.util.List<String> getThrows() {
        synchronized(this._throws){
            java.util.List<String> copy = new java.util.ArrayList<>();
            for (String tc : this._throws) {
                copy.add(tc);
            }
            return copy;
        }
    }
    
    /**
     * Ajoute une/des exception(s) au constructeur
     * @param _throw Correspond au(x) exception(s) à ajouter au constructeur
     */
    public synchronized final void addThrows(String..._throw){
        synchronized(this._throws){
            if(_throw != null){
                for(String code : _throw){
                    if(!this._throws.contains(code))
                        this._throws.add(code);
                }
            }
        }
    }
    
    /**
     * Enlève une/des exception(s) au constructeur
     * @param _throw Correspond au(x) exception(s) à enlever au constructeur
     */
    public synchronized final void removeThrows(String..._throw){
        synchronized(this._throws){
            if(_throw != null){
                for(String code : _throw){
                    if(this._throws.contains(code))
                        this._throws.remove(code);
                }
            }
        }
    }
    
    /**
     * Enlève une exception du constructeur à l'indice donné
     * @param index Correspond à l'indice de l'exception à enlever
     */
    public synchronized final void removeThrow(int index){
        synchronized(this._throws){
            this._throws.remove(index);
        }
    }
    
    /**
     * Enlève toutes les exceptions du constructeur
     */
    public synchronized final void clearThrows(){
        synchronized(this._throws){
            this._throws.clear();
        }
    }
    
    /**
     * Renvoie le nombre d'exceptions du constructeur
     * @return Retourne le nombre d'exceptions du constructeur
     */
    public synchronized final int countThrows(){
        synchronized(this._throws){
            return this._throws.size();
        }
    }
    
    /**
     * Détermine si le constructeur a des exceptions
     * @return Retourne true, si elle en a, sinon false
     */
    public synchronized final boolean hasThrows(){
        return countThrows() > 0;
    }
    
    /**
     * Renvoie l'exception à l'indice index, compris dans le constructeur
     * @param index Correspond à l'indice de l'exception à renvoyer
     * @return Retourne l'exception à l'indice index, compris dans le constructeur
     */
    public synchronized final String getThrow(int index){
        synchronized(this._throws){
            return this._throws.get(index);
        }
    }

    /**
     * Renvoie le hashCode du constructeur
     * @return Retourne le hashCode du constructeur
     */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + java.util.Objects.hashCode(this._parameters);
        hash = 97 * hash + java.util.Objects.hashCode(this.getName());
        return hash;
    }

    /**
     * Détermine si deux constructeurs sont égaux
     * @param obj Correspond au second constructeur à comparer au courant
     * @return Retourne true s'ils sont égaux, sinon false
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Constructor_element other = (Constructor_element) obj;
        if (!java.util.Objects.equals(this.getName(), other.getName())) {
            return false;
        }
        return java.util.Objects.equals(this._parameters, other._parameters);
    }
    
    /**
     * Renvoie la signature du constructeur
     * @return Retourne la signature du constructeur
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
        
        if(_strictfp)
            chain.append("strictfp ");
        
        if(!_genericsParameters.isEmpty()){
            chain.append("<");
            for(int i=0;i<_genericsParameters.size();i++){
                if(i>0) chain.append(", ");
                chain.append(_genericsParameters.get(i));
            }
            chain.append("> ");
        }
        
        chain.append(getName()).append("(");
        
        if(!_parameters.isEmpty()){
            for(int i=0;i<_parameters.size();i++){
                if(i>0) chain.append(", ");
                chain.append(_parameters.get(i).toString());
            }
        }
        
        chain.append(")");
        
        if(!_throws.isEmpty()){
            chain.append(" throws ");
            for(int i=0;i<_throws.size();i++){
                if(i>0) chain.append(", ");
                chain.append(_throws.get(i));
            }
        }
        
        return chain.toString();
    }

    /**
     * Renvoie le constructeur sous la forme d'une chaîne de caractères
     * @return Retourne le constructeur sous la forme d'une chaîne de caractères
     */
    @Override
    public String toString() {
        StringBuilder chain = new StringBuilder();
        
        
        
        if(hasJavadoc())
            chain.append(getJavadoc().toString()).append("\n");
        
        
        
        if(hasAnnotations())
            for(com.jasonpercus.util.code.Annotation_element a : getAnnotations())
                chain.append(a.toString()).append("\n");
        
        
        
        chain.append(getSignature());
        
        
        
        if(_code == null) chain.append(";");
        else{
            chain.append(" {\n");
            if(_code.isEmpty()){
                chain.append("    \n}");
            }else{
                String[] split = _code.split("\\n");
                for(String line : split){
                    chain.append("    ").append(line).append("\n");
                }
                chain.append("}");
            }
        }
        
        
        
        String c = chain.toString();
        if(asComment())
            return "//" + c.replace("\n", "\n//");
        else
            return c;
    }
    
    
    
}