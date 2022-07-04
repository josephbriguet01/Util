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
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.type.ReferenceType;
import com.github.javaparser.ast.type.TypeParameter;



/**
 * Cette classe représente une méthode
 * @author JasonPercus
 * @version 1.0
 */
@SuppressWarnings({"NestedSynchronizedStatement", "SynchronizeOnNonFinalField", "LeakingThisInConstructor"})
public class Method_element extends Element_element {

    
    
//ATTRIBUTS
    /**
     * Détermine si la méthode est final ou pas
     */
    private boolean _final;
    
    /**
     * Détermine si la méthode est abstrait ou pas
     */
    private boolean _abstract;
    
    /**
     * Détermine si la méthode est strictfp ou pas
     */
    private boolean _strictfp;
    
    /**
     * Détermine si la méthode est default ou pas
     */
    private boolean _default;
    
    /**
     * Détermine si la méthode est native ou pas
     */
    private boolean _native;
    
    /**
     * Détermine si la méthode est synchronized ou pas
     */
    private boolean _synchronized;
    
    /**
     * Correspond au type de retour de la méthode
     */
    private String  _return;
    
    /**
     * Correspond au code associé à la méthode
     */
    private String _code;
    
    /**
     * Correspond à la liste des paramètres génériques
     */
    private final java.util.List<String> _genericsParameters;
    
    /**
     * Correspond au(x) paramètre(s) de la méthode
     */
    private final java.util.List<Parameter_element> _parameters;
    
    /**
     * Correspond à la liste des exceptions levées par la méthode
     */
    private final java.util.List<String> _throws;

    
    
//CONSTRUCTORS
    /**
     * Crée une méthode
     */
    public Method_element() {
        super();
        this._genericsParameters = new java.util.ArrayList<>();
        this._parameters         = new java.util.ArrayList<>();
        this._throws             = new java.util.ArrayList<>();
    }
    
    /**
     * Crée une méthode
     * @param creator Correspond au creator qui crée les sous-blocs de code
     * @param md Correspond au bloc représentant la méthode de JavaParser
     * @param parent Correspond à la [classe | interface | énumération | annotation] parente de cette méthode
     * @deprecated Ne pas utiliser
     */
    protected Method_element(CodeCreator creator, MethodDeclaration md, Type_code parent) {
        super(null, md.getNameAsString(), md.isPublic(), md.isProtected(), md.isPrivate(), md.isStatic(), parent);
        this._final              = md.isFinal();
        this._abstract           = md.isAbstract();
        this._strictfp           = md.isStrictfp();
        this._default            = md.isDefault();
        this._native             = md.isNative();
        this._synchronized       = md.isSynchronized();
        this._return             = md.getTypeAsString();
        this._genericsParameters = new java.util.ArrayList<>();
        this._parameters         = new java.util.ArrayList<>();
        this._throws             = new java.util.ArrayList<>();
        
        NodeList<TypeParameter> _parametersB = md.getTypeParameters();
        for (TypeParameter tp : _parametersB)
            this._genericsParameters.add(tp.asString());
        
        for(Parameter p : md.getParameters())
            this._parameters.add(creator.createParameterElement(creator, p, this));
        
        if(md.getBody().isPresent()){
            String code = md.getBody().get().toString();
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
        }
        
        for(ReferenceType rt : md.getThrownExceptions())
            this._throws.add(rt.asString());
        
        if(md.hasJavaDocComment())
            setJavadoc(creator.createJavadoc(creator, md.getJavadocComment().get(), this));
        
        for(AnnotationExpr ae : md.getAnnotations())
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
     * Détermine si la méthode est default ou pas
     * @return True si elle l'est, sinon false
     */
    public synchronized final boolean isDefault() {
        return _default;
    }

    /**
     * Modifie si oui ou non la méthode doit être default ou pas
     * @param _default True si elle doit l'être, sinon false
     */
    public synchronized final void setDefault(boolean _default) {
        this._default = _default;
    }

    /**
     * Détermine si la méthode est native ou pas
     * @return True si elle l'est, sinon false
     */
    public synchronized final boolean isNative() {
        return _default;
    }

    /**
     * Modifie si oui ou non la méthode doit être native ou pas
     * @param _native True si elle doit l'être, sinon false
     */
    public synchronized final void setNative(boolean _native) {
        this._native = _native;
    }

    /**
     * Détermine si la méthode est synchronized ou pas
     * @return True si elle l'est, sinon false
     */
    public synchronized final boolean isSynchronized() {
        return _synchronized;
    }

    /**
     * Modifie si oui ou non la méthode doit être synchronized ou pas
     * @param _synchronized True si elle doit l'être, sinon false
     */
    public synchronized final void setSynchronized(boolean _synchronized) {
        this._synchronized = _synchronized;
    }

    /**
     * Renvoie le type de retour de la méthode
     * @return Retourne le type de retour de la méthode
     */
    public synchronized final String getReturn() {
        return _return;
    }

    /**
     * Modifie le type de retour de la méthode
     * @param _return Correspond au type de retour de la méthode
     */
    public synchronized final void setReturn(String _return) {
        this._return = _return;
    }
    
    /**
     * Renvoie le nom de la méthode avec sa liste des paramètres
     * @return Retourne le nom de la méthode avec sa liste des paramètres
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
     * Renvoie le code de la méthode
     * @return Retourne le code de la méthode
     */
    public synchronized String getCode() {
        return _code;
    }

    /**
     * Modifie le code de la méthode
     * @param code Correspond au code de la méthode
     */
    public synchronized void setCode(String code) {
        this._code = code;
    }
    
    /**
     * Renvoie si oui ou non la méthode est un getter
     * @return Retourne true si c'est le cas, sinon false
     */
    public synchronized boolean isGetter() {
        return (!isDefault() && !isAbstract() && !isNative() && (isPublic() || isProtected()) && !isStatic() && !isStrictfp() && (getName().indexOf("get") == 0 || getName().indexOf("is") == 0) && countParams() == 0);
    }
    
    /**
     * Renvoie si oui ou non la méthode est un setter
     * @return Retourne true si c'est le cas, sinon false
     */
    public synchronized boolean isSetter() {
        return (!isDefault() && !isAbstract() && !isNative() && (isPublic() || isProtected()) && !isStatic() && !isStrictfp() && getName().indexOf("set") == 0 && countParams() == 1 && getReturn().equals("void"));
    }
    
    /**
     * Renvoie si oui ou non le getter | setter est lié au setter | getter de l'autre méthode (ex: getName et setName sont un couple correct, getAge et setName n'est pas un couple correct)
     * @param method Correspond à l'autre méthode qui doit être couplée ou pas à la méthode courante
     * @return Retourne true si les deux méthodes sont liées, sinon false
     */
    public synchronized boolean isCoupleGetterSetter(Method_element method){
        if((isGetter() && method.isSetter()) || (isSetter() && method.isGetter())){
            String name  = getName();
            String oName = method.getName();
            
            if(name.indexOf("get") == 0)
                name = name.replaceFirst("get", "");
            else if(name.indexOf("is") == 0)
                name = name.replaceFirst("is", "");
            else if(name.indexOf("set") == 0)
                name = name.replaceFirst("set", "");
            
            if(oName.indexOf("get") == 0)
                oName = oName.replaceFirst("get", "");
            else if(oName.indexOf("is") == 0)
                oName = oName.replaceFirst("is", "");
            else if(oName.indexOf("set") == 0)
                oName = oName.replaceFirst("set", "");
            
            return name.equals(oName);
        }
        return false;
    }
    
    
    
//METHODES PUBLICS
    /**
     * Renvoie une copie de la liste des paramètres génériques (attention: il s'agit d'une copie de liste. Donc les modifications réalisées dessus n'auront aucun impact sur la méthode)
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
     * Ajoute un/des paramètre(s) générique(s) à la méthode
     * @param parameter Correspond au(x) paramètre(s) générique(s) à ajouter à la méthode
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
     * Enlève un/des paramètre(s) générique(s) à la méthode
     * @param parameter Correspond au(x) paramètre(s) générique(s) à enlever à la méthode
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
     * Enlève un paramètre générique de la méthode à l'indice donné
     * @param index Correspond à l'indice du paramètre générique à enlever
     */
    public synchronized final void removeGenericParam(int index){
        synchronized(this._genericsParameters){
            this._genericsParameters.remove(index);
        }
    }
    
    /**
     * Enlève toutes les paramètres génériques de la méthode
     */
    public synchronized final void clearGenericsParams(){
        synchronized(this._genericsParameters){
            this._genericsParameters.clear();
        }
    }
    
    /**
     * Renvoie le nombre de paramètres génériques de la méthode
     * @return Retourne le nombre de paramètres génériques de la méthode
     */
    public synchronized final int countGenericsParams(){
        synchronized(this._genericsParameters){
            return this._genericsParameters.size();
        }
    }
    
    /**
     * Détermine si la méthode a des paramètres génériques
     * @return Retourne true, si elle en a, sinon false
     */
    public synchronized final boolean hasGenericsParams(){
        return countGenericsParams() > 0;
    }
    
    /**
     * Renvoie le paramètre générique à l'indice index, compris dans la méthode
     * @param index Correspond à l'indice du paramètre générique à renvoyer
     * @return Retourne le paramètre générique à l'indice index, compris dans la méthode
     */
    public synchronized final String getGenericParam(int index){
        synchronized(this._genericsParameters){
            return this._genericsParameters.get(index);
        }
    }
    
    /**
     * Renvoie une copie de la liste des paramètres (attention: il s'agit d'une copie de liste. Donc les modifications réalisées dessus n'auront aucun impact sur la méthode)
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
     * Ajoute un/des paramètre(s) à la méthode
     * @param parameter Correspond au(x) paramètre(s) à ajouter à la méthode
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
     * Enlève un/des paramètre(s) à la méthode
     * @param parameter Correspond au(x) paramètre(s) à enlever à la méthode
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
     * Enlève un paramètre de la méthode à l'indice donné
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
     * Enlève toutes les paramètres de la méthode
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
     * Renvoie le nombre de paramètres de la méthode
     * @return Retourne le nombre de paramètres de la méthode
     */
    public synchronized final int countParams(){
        synchronized(this._parameters){
            return this._parameters.size();
        }
    }
    
    /**
     * Détermine si la méthode a des paramètres
     * @return Retourne true, si elle en a, sinon false
     */
    public synchronized final boolean hasParams(){
        return countParams() > 0;
    }
    
    /**
     * Renvoie le paramètre à l'indice index, compris dans la méthode
     * @param index Correspond à l'indice du paramètre à renvoyer
     * @return Retourne le paramètre à l'indice index, compris dans la méthode
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
     * Ajoute une/des exception(s) à la méthode
     * @param _throw Correspond au(x) exception(s) à ajouter à la méthode
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
     * Enlève une/des exception(s) à la méthode
     * @param _throw Correspond au(x) exception(s) à enlever à la méthode
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
     * Enlève une exception de la méthode à l'indice donné
     * @param index Correspond à l'indice de l'exception à enlever
     */
    public synchronized final void removeThrow(int index){
        synchronized(this._throws){
            this._throws.remove(index);
        }
    }
    
    /**
     * Enlève toutes les exceptions de la méthode
     */
    public synchronized final void clearThrows(){
        synchronized(this._throws){
            this._throws.clear();
        }
    }
    
    /**
     * Renvoie le nombre d'exceptions de la méthode
     * @return Retourne le nombre d'exceptions de la méthode
     */
    public synchronized final int countThrows(){
        synchronized(this._throws){
            return this._throws.size();
        }
    }
    
    /**
     * Détermine si la méthode a des exceptions
     * @return Retourne true, si elle en a, sinon false
     */
    public synchronized final boolean hasThrows(){
        return countThrows() > 0;
    }
    
    /**
     * Renvoie l'exception à l'indice index, compris dans la méthode
     * @param index Correspond à l'indice de l'exception à renvoyer
     * @return Retourne l'exception à l'indice index, compris dans la méthode
     */
    public synchronized final String getThrow(int index){
        synchronized(this._throws){
            return this._throws.get(index);
        }
    }

    /**
     * Renvoie le hashCode de la méthode
     * @return Retourne le hashCode de la méthode
     */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 83 * hash + java.util.Objects.hashCode(this._parameters);
        hash = 83 * hash + java.util.Objects.hashCode(this.getName());
        return hash;
    }

    /**
     * Détermine si deux méthodes sont identiques
     * @param obj Correspond à la secondes méthode à comparer à la courante
     * @return Retourne true si elles sont identiques, sinon false
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
        final Method_element other = (Method_element) obj;
        if (!java.util.Objects.equals(this.getName(), other.getName())) {
            return false;
        }
        return java.util.Objects.equals(this._parameters, other._parameters);
    }
    
    /**
     * Renvoie la signature de la méthode
     * @return Retourne la signature de la méthode
     */
    @Override
    public String getSignature(){
        StringBuilder chain = new StringBuilder(getVisibility().toString());
        
        if(_native)
            chain.append("native ");
        
        if(_synchronized)
            chain.append("synchronized ");
        
        if(_final)
            chain.append("final ");
        
        if(_abstract)
            chain.append("abstract ");
        
        if(isStatic())
            chain.append("static ");
        
        if(_strictfp)
            chain.append("strictfp ");
        
        if(_default)
            chain.append("default ");
        
        if(!_genericsParameters.isEmpty()){
            chain.append("<");
            for(int i=0;i<_genericsParameters.size();i++){
                if(i>0) chain.append(", ");
                chain.append(_genericsParameters.get(i));
            }
            chain.append("> ");
        }
            
        chain.append(_return).append(" ").append(getName()).append("(");
        
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
     * Renvoie la méthode sous la forme d'une chaîne de caractères
     * @return Retourne la méthode sous la forme d'une chaîne de caractères
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