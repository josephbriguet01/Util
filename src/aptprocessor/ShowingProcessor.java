/*
 * Copyright (C) JasonPercus Systems, Inc - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by JasonPercus, 07/2021
 */
package aptprocessor;



import com.jasonpercus.util.code.AnnotationMember_element;
import com.jasonpercus.util.code.Annotation_element;
import com.jasonpercus.util.code.Annotation_type;
import com.jasonpercus.util.code.Class_type;
import com.jasonpercus.util.code.CodeCreator;
import com.jasonpercus.util.code.Constructor_element;
import com.jasonpercus.util.code.Description_javadoc;
import com.jasonpercus.util.code.Element_javadoc;
import com.jasonpercus.util.code.EnumConst_element;
import com.jasonpercus.util.code.Enum_type;
import com.jasonpercus.util.code.Field_element;
import com.jasonpercus.util.FileCode;
import com.jasonpercus.util.code.Import_element;
import com.jasonpercus.util.code.Interface_type;
import com.jasonpercus.util.code.Javadoc_code;
import com.jasonpercus.util.code.Method_element;
import com.jasonpercus.util.code.Return_javadoc;
import com.jasonpercus.util.code.SyntaxCodeException;
import com.jasonpercus.util.code.Type_code;
import com.jasonpercus.util.code.Version_javadoc;
import com.github.javaparser.ast.body.AnnotationDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.EnumDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.jasonpercus.util.Showing;
import com.jasonpercus.util.code.Code;
import com.jasonpercus.util.code.Exception_javadoc;
import com.jasonpercus.util.code.Param_javadoc;
import com.jasonpercus.util.code.Parameter_element;
import com.jasonpercus.util.code.Throws_javadoc;



/**
 * Cette classe permet au compilateur de restructurer une classe. {@link com.jasonpercus.util.Showing} est utilisé pour annoté cette classe
 * @see com.jasonpercus.util.Builder
 * @author JasonPercus
 * @version 1.0
 */
@javax.annotation.processing.SupportedAnnotationTypes(value = {"com.jasonpercus.util.Showing"})
@javax.annotation.processing.SupportedSourceVersion(javax.lang.model.SourceVersion.RELEASE_17)
public class ShowingProcessor extends SimplifiedAbstractProcessor {

    
    
//ATTRIBUTS
    /**
     * Détermine s'il faut créer des blocs collapses autour des sections (constantes, attributs, constructeurs, méthodes...)
     */
    private Showing.Bloc  blocs;
    
    /**
     * Détermine s'il faut que les blocs collapses puissent se rétracter à l'ouverture du fichier ou rester ouvert
     */
    private Showing.State state;
    
    /**
     * Détermine s'il faut afficher les erreurs javadoc lors de la compilation
     */
    private Showing.Javadoc javadoc;
    
    /**
     * Correspond à la liste des fichiers déjà analysée. Elle permet entre-autre d'empêcher à plusieurs classe dans un même fichier de regénérer le fichier alors qu'il vient d'être fait
     */
    private final java.util.List<String> paths = new java.util.ArrayList<>();
    
    /**
     * Correspond à la liste des imports à supprimer
     */
    private final java.util.List<Import_element> toRemove = new java.util.ArrayList<>();
    
    
    
//METHODES PUBLICS
    /**
     * Traite un ensemble de types d'annotations sur des éléments de type provenant du tour précédent et renvoie si ces annotations sont revendiquées ou non par ce processeur
     * @param annotations Correspond aux types d'annotations à traiter
     * @param roundEnv Correspond à l'environnement d'information sur le tour en cours et le tour précédent
     * @return Retourne si oui (true) ou non (false) l'ensemble d'annotations est revendiqué par ce processeur
     */
    @Override
    public boolean process(java.util.Set<? extends javax.lang.model.element.TypeElement> annotations, javax.annotation.processing.RoundEnvironment roundEnv) {
        for (javax.lang.model.element.TypeElement annotation : annotations) {
            java.util.Set<? extends javax.lang.model.element.Element> annotatedElements = roundEnv.getElementsAnnotatedWith(annotation);
            for(javax.lang.model.element.Element classe : annotatedElements){
                this.blocs = classe.getAnnotation(Showing.class).blocs();
                this.state = classe.getAnnotation(Showing.class).state();
                this.javadoc = classe.getAnnotation(Showing.class).javadoc();
                try {
                    FileCode code = importCode(classe);
                    if(!paths.contains(code.getAbsolutePath())){
                        for(Import_element i : code.getImports()){
                            if(i.getName().equals("com.jasonpercus.util.Showing")){
                                toRemove.add(i);
                            }else if(!i.isStatic() && !i.isAll()){
                                if(i.getName().indexOf("javax.") == 0){
                                    toRemove.add(i);
                                } else if (i.getName().indexOf("java.") == 0){
                                    toRemove.add(i);
                                } else if (i.getName().indexOf("androidx.") == 0){
                                    toRemove.add(i);
                                } else if (i.getName().indexOf("android.") == 0){
                                    toRemove.add(i);
                                }
                            }
                        }
                        for(Import_element toDelete : toRemove){
                            code.removeImports(toDelete);
                        }
                        for(Type_code doc : code.getDocuments()){
                            replaceRefImport(doc);
                        }
                        
                        try {
                            code.write();
                            paths.add(code.getAbsolutePath());
                        } catch (java.io.IOException ex) {
                            printMessage(javax.tools.Diagnostic.Kind.ERROR, ex.getMessage());
                        }
                    }
                } catch (java.io.FileNotFoundException ex) {
                    printMessage(javax.tools.Diagnostic.Kind.ERROR, getPathClass(classe)+" not exists !");
                } catch (SyntaxCodeException ex) {
                    printMessage(javax.tools.Diagnostic.Kind.ERROR, getPathClass(classe)+" is badly written !");
                }
            }
        }
        return false;
    }

    /**
     * Renvoie un FileCode créé (Cette méthode redéfini permet entre-autre de créer des classes filles de FileCode
     * @param javaFile Correspond au fichier à créer
     * @return Retourne le FileCode créé
     * @throws java.io.FileNotFoundException Si le fichier n'existe pas
     * @throws SyntaxCodeException Si le fichier contient des erreurs de syntax
     */
    @Override
    public FileCode createFileCode(java.io.File javaFile) throws java.io.FileNotFoundException, SyntaxCodeException {
        return new NewFileCode(javaFile);
    }
    
    
    
//METHODES PRIVATES
    /**
     * Renvoie le collapse de démarrage d'un bloc
     * @param name Correspond au nom du collapse
     * @return Retourne le collapse de démarrage
     */
    private String getEditorFoldStart(String name){
        return "// <editor-fold defaultstate=\"" + state + "\" desc=\"" + name + "\">";
    }
    
    /**
     * Renvoie le collapse de fin d'un bloc
     * @return Retourne le collapse de fin
     */
    private String getEditorFoldEnd(){
        return "// </editor-fold>";
    }
    
    /**
     * Remplace toutes les occurences des imports à supprimer dans le code
     * @param doc Correspond au code à analyser et modifier
     */
    private void replaceRefImport(Code doc){
        if(doc != null){
            if(doc instanceof AnnotationMember_element){
                AnnotationMember_element n = (AnnotationMember_element) doc;
                    
                replaceRefImport(n.getAnnotations());
                n.setDefaultValue(replaceRefImport(n.getDefaultValue()));
                replaceRefImport(n.getJavadoc());
                n.setReturn(replaceRefImport(n.getReturn()));
            }
            if(doc instanceof Annotation_element){
                Annotation_element n = (Annotation_element) doc;
                    
                n.setName(replaceRefImport(n.getName()));
                n.setValue(replaceRefImport(n.getValue()));
            }
            if(doc instanceof Annotation_type){
                Annotation_type n = (Annotation_type) doc;
                    
                replaceRefImport(n.getAnnotations());
                replaceRefImport(n.getAnnotationMembers());
                replaceRefImport(n.getConstructors());
                replaceRefImport(n.getFields());
                replaceRefImport(n.getJavadoc());
                replaceRefImport(n.getMethods());
                replaceRefImport(n.getSubAnnotations());
                replaceRefImport(n.getSubClass());
                replaceRefImport(n.getSubEnums());
                replaceRefImport(n.getSubInterfaces());
            }
            if(doc instanceof Class_type){
                Class_type n = (Class_type) doc;
                    
                replaceRefImport(n.getAnnotations());
                replaceRefImport(n.getConstructors());
                
                java.util.List list2 = replaceRefImport(n.getExtends());
                n.clearExtends();
                for(Object o : list2) n.addExtends((String) o);
                
                replaceRefImport(n.getFields());
                
                java.util.List list1 = replaceRefImport(n.getGenericsParams());
                n.clearGenericsParams();
                for(Object o : list1) n.addGenericsParams((String) o);
                
                java.util.List list3 = replaceRefImport(n.getImplements());
                n.clearImplements();
                for(Object o : list3) n.addImplements((String) o);
                
                replaceRefImport(n.getJavadoc());
                replaceRefImport(n.getMethods());
                replaceRefImport(n.getSubAnnotations());
                replaceRefImport(n.getSubClass());
                replaceRefImport(n.getSubEnums());
                replaceRefImport(n.getSubInterfaces());
            }
            if(doc instanceof Constructor_element){
                Constructor_element n = (Constructor_element) doc;
                    
                replaceRefImport(n.getAnnotations());
                n.setCode(replaceRefImport(n.getCode()));
                
                java.util.List list1 = replaceRefImport(n.getGenericsParams());
                n.clearGenericsParams();
                for(Object o : list1) n.addGenericsParams((String) o);
                
                replaceRefImport(n.getJavadoc());
                replaceRefImport(n.getParams());
                
                java.util.List list3 = replaceRefImport(n.getThrows());
                n.clearThrows();
                for(Object o : list3) n.addThrows((String) o);
            }
            if(doc instanceof Element_javadoc){
                Element_javadoc n = (Element_javadoc) doc;
                    
                n.setDescription(replaceRefImport(n.getDescription()));
            }
            if(doc instanceof Exception_javadoc){
                Exception_javadoc n = (Exception_javadoc) doc;
                    
                n.setKey(replaceRefImport(n.getKey()));
            }
            if(doc instanceof Throws_javadoc){
                Throws_javadoc n = (Throws_javadoc) doc;
                    
                n.setKey(replaceRefImport(n.getKey()));
            }
            if(doc instanceof EnumConst_element){
                EnumConst_element n = (EnumConst_element) doc;
                    
                replaceRefImport(n.getAnnotations());
                replaceRefImport(n.getArgs());
                
                java.util.List list4 = replaceRefImport(n.getArgs());
                n.clearArgs();
                for(Object o : list4) n.addArgs((String) o);
                
                replaceRefImport(n.getJavadoc());
            }
            if(doc instanceof Enum_type){
                Enum_type n = (Enum_type) doc;
                    
                replaceRefImport(n.getAnnotations());
                replaceRefImport(n.getConstructors());
                replaceRefImport(n.getConsts());
                replaceRefImport(n.getFields());
                
                java.util.List list3 = replaceRefImport(n.getImplements());
                n.clearImplements();
                for(Object o : list3) n.addImplements((String) o);
                
                replaceRefImport(n.getJavadoc());
                replaceRefImport(n.getMethods());
                replaceRefImport(n.getSubAnnotations());
                replaceRefImport(n.getSubClass());
                replaceRefImport(n.getSubEnums());
                replaceRefImport(n.getSubInterfaces());
            }
            if(doc instanceof Field_element){
                Field_element n = (Field_element) doc;
                    
                replaceRefImport(n.getAnnotations());
                replaceRefImport(n.getJavadoc());
                n.setType(replaceRefImport(n.getType()));
                n.setValue(replaceRefImport(n.getValue()));
            }
            if(doc instanceof Interface_type){
                Interface_type n = (Interface_type) doc;
                    
                replaceRefImport(n.getAnnotations());
                replaceRefImport(n.getConstructors());
                
                java.util.List list2 = replaceRefImport(n.getExtends());
                n.clearExtends();
                for(Object o : list2) n.addExtends((String) o);
                
                replaceRefImport(n.getFields());
                
                java.util.List list1 = replaceRefImport(n.getGenericsParams());
                n.clearGenericsParams();
                for(Object o : list1) n.addGenericsParams((String) o);
                
                java.util.List list3 = replaceRefImport(n.getImplements());
                n.clearImplements();
                for(Object o : list3) n.addImplements((String) o);
                
                replaceRefImport(n.getJavadoc());
                replaceRefImport(n.getMethods());
                replaceRefImport(n.getSubAnnotations());
                replaceRefImport(n.getSubClass());
                replaceRefImport(n.getSubEnums());
                replaceRefImport(n.getSubInterfaces());
            }
            if(doc instanceof Javadoc_code){
                Javadoc_code n = (Javadoc_code) doc;
                    
                replaceRefImport(n.getElements());
            }
            if(doc instanceof Method_element){
                Method_element n = (Method_element) doc;
                    
                replaceRefImport(n.getAnnotations());
                n.setCode(replaceRefImport(n.getCode()));
                
                java.util.List list1 = replaceRefImport(n.getGenericsParams());
                n.clearGenericsParams();
                for(Object o : list1) n.addGenericsParams((String) o);
                
                replaceRefImport(n.getJavadoc());
                replaceRefImport(n.getParams());
                n.setReturn(replaceRefImport(n.getReturn()));
                
                java.util.List list3 = replaceRefImport(n.getThrows());
                n.clearThrows();
                for(Object o : list3) n.addThrows((String) o);
            }
            if(doc instanceof Parameter_element){
                Parameter_element n = (Parameter_element) doc;
                
                replaceRefImport(n.getAnnotations());
                replaceRefImport(n.getJavadoc());
                n.setType(replaceRefImport(n.getType()));
            }
        }
    }
    
    /**
     * Remplace toutes les occurences des imports à supprimer dans la chaîne de caractère
     * @param chain Correspond à la chaîne de caractère à analyser et modifier
     * @return Renvoie toutes les occurences des imports à supprimer
     */
    private String replaceRefImport(String chain){
        if(chain != null){
            for(Import_element toDelete : toRemove){
                chain = chain.replace(toDelete.getName(), "bugirnhgeirgyefz21gz45e");
                chain = chain.replace(toDelete.getSimpleName(), toDelete.getName());
                chain = chain.replace("bugirnhgeirgyefz21gz45e", toDelete.getName());
            }
        }
        return chain;
    }
    
    /**
     * Remplace toutes les occurences des imports à supprimer dans la liste
     * @param list Correspond à la liste à analyser et modifier
     * @return Retourne la liste modifiée (si les éléments sont des String) sinon null, car les références sont automatiquement mise à jour
     */
    private java.util.List replaceRefImport(java.util.List list){
        if(list != null){
            for(int i=0;i<list.size();i++){
                Object o = list.get(i);
                if(o instanceof Code){
                    replaceRefImport((Code) o);
                }else if(o instanceof String){
                    list.set(i, replaceRefImport((String) o));
                }
            }
        }
        return list;
    }
    
    
    
//CLASS
    /**
     * Cette classe représente les fichiers de code *.java. A partir d'un objet {@link FileCode} il est possible d'analyser toute la structure du fichier de code grâce à la méthode {@link #analyse() FileCode.Analyse()}, puis de faire des modifications, puis réenregistrer, écraser un fichier de code...
     * @author JasonPercus
     * @version 1.0
     */
    private class NewFileCode extends FileCode {

        
        
    //CONSTRUCTOR
        /**
         * Crée une nouvelle instance de File en convertissant le fichier donné en un chemin d'accès abstrait
         * @param file Correspond au fichier à convertir
         */
        public NewFileCode(java.io.File file) {
            super(file);
            CodeCreator creator = new CodeCreator(){
                
                private String _getFields(java.util.List<Field_element> fes){
                    StringBuilder chain = new StringBuilder();
                    
                    
                    
                    //SORT
                    java.util.Collections.sort(fes, (Field_element o1, Field_element o2) -> {
                        return o1.getSignature().compareTo(o2.getSignature());
                    });
                    
                    
                    
                    //COUNT & NOTE JAVADOC MISSING
                    int countConst = 0;
                    int countStatic = 0;
                    int countOnly = 0;
                    for(Field_element field : fes){
                        if(field.isConstField())        countConst++;
                        else if(field.isStaticField())  countStatic++;
                        else if(field.isOnlyField())    countOnly++;
                                    
                        if(!field.hasJavadoc() && javadoc == Showing.Javadoc.WARNING)
                            printMessage(javax.tools.Diagnostic.Kind.NOTE, ">Javadoc missing for field \"" + field.getName() + "\" !");
                    }
                    
                    
                    
                    //CONST
                    if (countConst > 0) {
                        if (blocs == Showing.Bloc.NO_COLLAPSE) 
                            chain.append("\n//CONSTANTE").append((countConst > 1) ? "S" : "");
                        
                        if (blocs == Showing.Bloc.COLLAPSE)
                            chain.append("\n    ").append(getEditorFoldStart("CONSTANTE" + ((countConst > 1) ? "S" : "")));

                        for (Field_element field : fes)
                            if (field.isConstField())
                                chain.append("\n    ").append(field.toString().replace("\n", "\n    ")).append("\n");
                        
                        if (blocs == Showing.Bloc.COLLAPSE)
                            chain.append("    ").append(getEditorFoldEnd()).append("\n");
                        
                        chain.append("\n\n");
                    }
                                
                    
                    
                    //STATIC
                    if (countStatic > 0) {
                        if (blocs == Showing.Bloc.NO_COLLAPSE) 
                            chain.append("\n//ATTRIBUT").append((countStatic > 1) ? "S" : "").append(" STATIC").append((countStatic > 1) ? "S" : "");
                        
                        if (blocs == Showing.Bloc.COLLAPSE)
                            chain.append("\n    ").append(getEditorFoldStart("ATTRIBUT" + ((countStatic > 1) ? "S" : "") + " STATIC" + ((countStatic > 1) ? "S" : "")));

                        for (Field_element field : fes)
                            if (field.isStaticField())
                                chain.append("\n    ").append(field.toString().replace("\n", "\n    ")).append("\n");
                        
                        if (blocs == Showing.Bloc.COLLAPSE)
                            chain.append("    ").append(getEditorFoldEnd()).append("\n");
                        
                        chain.append("\n\n");
                    }

                    
                    
                    //ONLY
                    if (countOnly > 0) {
                        if (blocs == Showing.Bloc.NO_COLLAPSE)
                            chain.append("\n//ATTRIBUT").append((countOnly > 1) ? "S" : "");
                        
                        if (blocs == Showing.Bloc.COLLAPSE)
                            chain.append("\n    ").append(getEditorFoldStart("ATTRIBUT" + ((countOnly > 1) ? "S" : "")));

                        for (Field_element field : fes)
                            if (field.isOnlyField())
                                chain.append("\n    ").append(field.toString().replace("\n", "\n    ")).append("\n");
                        
                        if (blocs == Showing.Bloc.COLLAPSE)
                            chain.append("    ").append(getEditorFoldEnd()).append("\n");
                        
                        chain.append("\n\n");
                    }
                    return chain.toString();
                }
                
                private String _getConstructors(java.util.List<Constructor_element> ces){
                    StringBuilder chain = new StringBuilder();
                    java.util.Collections.sort(ces, (Constructor_element o1, Constructor_element o2) -> {
                        if (!o1.hasNoVisibility() && !o2.hasNoVisibility()) {
                            if (o1.isPublic() && !o2.isPublic()) {
                                return -1;
                            } else if (!o1.isPublic() && o2.isPublic()) {
                                return 1;
                            } else if (o1.isProtected() && !o2.isProtected()) {
                                return -1;
                            } else if (!o1.isProtected() && o2.isProtected()) {
                                return 1;
                            } else {
                                int p1 = o1.countParams();
                                int p2 = o2.countParams();
                                if (p1 < p2) {
                                    return -1;
                                } else if (p1 > p2) {
                                    return 1;
                                } else {
                                    return o1.getSignature().compareTo(o2.getSignature());
                                }
                            }
                        } else if (!o1.hasNoVisibility() && o2.hasNoVisibility()) {
                            return 1;
                        } else if (o1.hasNoVisibility() && !o2.hasNoVisibility()) {
                            return -1;
                        } else {
                            int p1 = o1.countParams();
                            int p2 = o2.countParams();
                            if (p1 < p2) {
                                return -1;
                            } else if (p1 > p2) {
                                return 1;
                            } else {
                                return o1.getSignature().compareTo(o2.getSignature());
                            }
                        }
                    });

                    
                    
                    for(Constructor_element constructor : ces){
                        if(!constructor.hasJavadoc() && javadoc == Showing.Javadoc.WARNING)
                            printMessage(javax.tools.Diagnostic.Kind.NOTE, ">Javadoc missing for constructor \"" + constructor.getName() + "\" !");
                    }
                    
                    
                    
                    if (blocs == Showing.Bloc.NO_COLLAPSE) 
                        chain.append("\n//CONSTRUCTOR").append((ces.size() > 1) ? "S" : "");
                        
                    if (blocs == Showing.Bloc.COLLAPSE)
                        chain.append("\n    ").append(getEditorFoldStart("CONSTRUCTOR" + ((ces.size() > 1) ? "S" : "")));

                    for (Constructor_element constructor : ces)
                        chain.append("\n    ").append(constructor.toString().replace("\n", "\n    ")).append("\n");
                        
                    if (blocs == Showing.Bloc.COLLAPSE)
                        chain.append("    ").append(getEditorFoldEnd()).append("\n");
                        
                    chain.append("\n\n");
                    
                        
                        
                    return chain.toString();
                }
                
                private String _getMethods(java.util.List<Method_element> mes){
                    StringBuilder chain = new StringBuilder();
                    
                    boolean oneBlocRealized = false;
                    java.util.List<Method_element> _abstracts   = new java.util.ArrayList<>();
                    java.util.List<Method_element> _getsets     = new java.util.ArrayList<>();
                    java.util.List<Method_element> _statics      = new java.util.ArrayList<>();
                    java.util.List<Method_element> _others      = new java.util.ArrayList<>();
                    
                    for(Method_element method : mes){
                        if(method.isAbstract())
                            _abstracts.add(method);
                        else if(method.isGetter() || method.isSetter())
                            _getsets.add(method);
                        else if(method.isStatic())
                            _statics.add(method);
                        else
                            _others.add(method);
                        
                        if(!method.hasJavadoc()){
                            if(method.getNameAndParameters().equals("toString()") && method.getReturn().equals("String")){
                                Javadoc_code doc = new Javadoc_code();
                                doc.addElements(new Description_javadoc("Renvoie l'objet " + method.getParent().getName() + " sous la forme d'une chaîne de caractères"));
                                doc.addElements(new Return_javadoc("Retourne l'objet " + method.getParent().getName() + " sous la forme d'une chaîne de caractères"));
                                method.setJavadoc(doc);
                            }
                            else if(method.getNameAndParameters().equals("hashCode()") && method.getReturn().equals("int")){
                                Javadoc_code doc = new Javadoc_code();
                                doc.addElements(new Description_javadoc("Renvoie l'hashCode de l'objet " + method.getParent().getName()));
                                doc.addElements(new Return_javadoc("Retourne l'hashCode de l'objet " + method.getParent().getName()));
                                method.setJavadoc(doc);
                            }
                            else if(method.getName().equals("equals") && method.getReturn().equals("boolean") && method.countParams() == 1 && method.getParam(0).getType().equals("Object")){
                                Javadoc_code doc = new Javadoc_code();
                                doc.addElements(new Description_javadoc("Détermine si deux objets " + method.getParent().getName() + " sont identiques ou pas"));
                                doc.addElements(new Param_javadoc(method.getParam(0).getName(), "Correspond au second objet " + method.getParent().getName() + " à comparer au courant"));
                                doc.addElements(new Return_javadoc("Retourne true s'ils sont identiques, sinon false"));
                                method.setJavadoc(doc);
                            }
                            else if(method.getName().equals("compareTo") && method.getReturn().equals("int") && method.countParams() == 1){
                                Javadoc_code doc = new Javadoc_code();
                                doc.addElements(new Description_javadoc("Compare deux objets " + method.getParam(0).getType()));
                                doc.addElements(new Param_javadoc(method.getParam(0).getName(), "Correspond au second objet " + method.getParam(0).getType() + " à comparer au courant"));
                                doc.addElements(new Return_javadoc("Retourne le résultat de la comparaison"));
                                method.setJavadoc(doc);
                            }
                            else if(method.getNameAndParameters().equals("clone()")){
                                Javadoc_code doc = new Javadoc_code();
                                doc.addElements(new Description_javadoc("Clone cet objet " + method.getParent().getName()));
                                doc.addElements(new Return_javadoc("Retourne la copie de cet objet"));
                                doc.addElements(new Throws_javadoc("CloneNotSupportedException", "Si le clone n'est pas supporté"));
                                method.setJavadoc(doc);
                            }
                            else if(javadoc == Showing.Javadoc.WARNING)
                                printMessage(javax.tools.Diagnostic.Kind.NOTE, ">Javadoc missing for method \"" + method.getNameAndParameters() + ")" + "\" !");
                        }
                    }
                    
                    if(!_getsets.isEmpty()){
                        java.util.List<CoupleGetSet> cgs = new java.util.ArrayList<>();
                        while(!_getsets.isEmpty()){
                            Method_element first = _getsets.get(0);
                            Method_element second;
                            boolean found = false;
                            for(int i=_getsets.size()-1;i>-1;i--){
                                second = _getsets.get(i);
                                if(first.isCoupleGetterSetter(second)){
                                    cgs.add(new CoupleGetSet(first, second));
                                    _getsets.remove(i);
                                    found = true;
                                }
                            }
                            if(!found){
                                cgs.add(new CoupleGetSet(first, null));
                            }
                            _getsets.remove(0);
                        }
                        java.util.Collections.sort(cgs);
                        int countGET = 0;
                        int countSET = 0;
                        for(CoupleGetSet cg :cgs){
                            if(cg.get != null)
                                countGET++;
                            if(cg.set != null)
                                countSET++;
                        }
                            
                        int countCGS = cgs.size();
                        if(countCGS > 0){
                            String sb = "GETTER & SETTER";
                            if(countGET == 0 && countSET == 1)
                                sb = "SETTER";
                            else if(countGET == 0 && countSET > 1)
                                sb = "SETTERS";
                            else if(countGET == 1 && countSET == 0)
                                sb = "GETTER";
                            else if(countGET == 1 && countSET > 1)
                                sb = "GETTER & SETTERS";
                            else if(countGET > 1 && countSET == 0)
                                sb = "GETTERS";
                            else if(countGET > 1 && countSET == 1)
                                sb = "GETTERS & SETTER";
                            else if(countGET > 1 && countSET > 1)
                                sb = "GETTERS & SETTERS";
                            
                        if(oneBlocRealized) chain.append("\n\n");
                        
                            if (blocs == Showing.Bloc.NO_COLLAPSE) 
                                chain.append("\n//").append(sb);

                            if (blocs == Showing.Bloc.COLLAPSE)
                                chain.append("\n    ").append(getEditorFoldStart(sb));
                            
                            for(CoupleGetSet couple : cgs){
                                if(couple.get != null)
                                    chain.append("\n    ").append(couple.get.toString().replace("\n", "\n    ")).append("\n");
                                if(couple.set != null)
                                    chain.append("\n    ").append(couple.set.toString().replace("\n", "\n    ")).append("\n");
                            }
                        
                            if (blocs == Showing.Bloc.COLLAPSE)
                                chain.append("    ").append(getEditorFoldEnd()).append("\n");
                            
                            oneBlocRealized = true;
                        }
                    }
                    
                    
                    if(!_abstracts.isEmpty()){
                        java.util.Collections.sort(_abstracts, (Method_element o1, Method_element o2) -> {
                            if (!o1.hasNoVisibility() && !o2.hasNoVisibility()) {
                                if (o1.isPublic() && !o2.isPublic()) {
                                    return -1;
                                } else if (!o1.isPublic() && o2.isPublic()) {
                                    return 1;
                                } else if (o1.isProtected() && !o2.isProtected()) {
                                    return -1;
                                } else if (!o1.isProtected() && o2.isProtected()) {
                                    return 1;
                                } else {
                                    int cpte = o1.getName().compareTo(o2.getName());
                                    if(cpte == 0){
                                        int p1 = o1.countParams();
                                        int p2 = o2.countParams();
                                        if (p1 < p2) {
                                            return -1;
                                        } else if (p1 > p2) {
                                            return 1;
                                        } else {
                                            return o1.getSignature().compareTo(o2.getSignature());
                                        }
                                    }else
                                        return cpte;
                                }
                            } else if (!o1.hasNoVisibility() && o2.hasNoVisibility()) {
                                return 1;
                            } else if (o1.hasNoVisibility() && !o2.hasNoVisibility()) {
                                return -1;
                            } else {
                                int cpte = o1.getName().compareTo(o2.getName());
                                if(cpte == 0){
                                    int p1 = o1.countParams();
                                    int p2 = o2.countParams();
                                    if (p1 < p2) {
                                        return -1;
                                    } else if (p1 > p2) {
                                        return 1;
                                    } else {
                                        return o1.getSignature().compareTo(o2.getSignature());
                                    }
                                }else
                                    return cpte;
                            }
                        });
                        
                        if(oneBlocRealized) chain.append("\n\n");
                        
                        if (blocs == Showing.Bloc.NO_COLLAPSE) 
                            chain.append("\n//").append("METHOD").append((_abstracts.size()>1) ? "S" : "").append(" ABSTRACT").append((_abstracts.size()>1) ? "S" : "");

                        if (blocs == Showing.Bloc.COLLAPSE)
                            chain.append("\n    ").append(getEditorFoldStart("METHOD" + ((_abstracts.size()>1) ? "S" : "") + " ABSTRACT" + ((_abstracts.size()>1) ? "S" : "")));
                        
                        for(Method_element method : _abstracts)
                            chain.append("\n    ").append(method.toString().replace("\n", "\n    ")).append("\n");
                        
                        if (blocs == Showing.Bloc.COLLAPSE)
                            chain.append("    ").append(getEditorFoldEnd()).append("\n");
                        
                        oneBlocRealized = true;
                    }
                    
                    if(!_statics.isEmpty()){
                        if(oneBlocRealized) chain.append("\n\n");
                        
                        if (blocs == Showing.Bloc.NO_COLLAPSE) 
                            chain.append("\n//").append("METHOD").append((_statics.size()>1) ? "S" : "").append(" STATIC").append((_statics.size()>1) ? "S" : "");

                        if (blocs == Showing.Bloc.COLLAPSE)
                            chain.append("\n    ").append(getEditorFoldStart("METHOD" + ((_statics.size()>1) ? "S" : "") + " STATIC" + ((_statics.size()>1) ? "S" : "")));
                        
                        for(Method_element method : _statics)
                            chain.append("\n    ").append(method.toString().replace("\n", "\n    ")).append("\n");
                        
                        if (blocs == Showing.Bloc.COLLAPSE)
                            chain.append("    ").append(getEditorFoldEnd()).append("\n");
                        
                        oneBlocRealized = true;
                    }
                    
                    if(!_others.isEmpty()){
                        if(oneBlocRealized) chain.append("\n\n");
                        
                        if (blocs == Showing.Bloc.NO_COLLAPSE) 
                            chain.append("\n//").append("METHOD").append((_others.size()>1) ? "S" : "");

                        if (blocs == Showing.Bloc.COLLAPSE)
                            chain.append("\n    ").append(getEditorFoldStart("METHOD" + ((_others.size()>1) ? "S" : "")));
                        
                        for(Method_element method : _others)
                            chain.append("\n    ").append(method.toString().replace("\n", "\n    ")).append("\n");
                        
                        if (blocs == Showing.Bloc.COLLAPSE)
                            chain.append("    ").append(getEditorFoldEnd()).append("\n");
                        
                    }
                    
                    return chain.toString();
                }
                
                @Override
                public Annotation_type createAnnotation(CodeCreator creator, Type_code parent, AnnotationDeclaration ad) {
                    Annotation_type at = new Annotation_type(creator, parent, ad){
                        @Override
                        public String toString() {
                            StringBuilder chain = new StringBuilder();



                            if(hasJavadoc())
                                chain.append(getJavadoc().toString()).append("\n");



                            if(hasAnnotations())
                                for(com.jasonpercus.util.code.Annotation_element a : getAnnotations()){
                                    if(a.getName().replace("com.jasonpercus.util.", "").equals(Showing.class.getSimpleName()))
                                        a.asComment(true);
                                    chain.append(a.toString()).append("\n");
                                }



                            chain.append(getSignature()).append(" {\n\n\n");



                            if(!getAnnotationMembers().isEmpty())
                                for(AnnotationMember_element annotation : getAnnotationMembers()){
                                    if(!annotation.hasJavadoc() && javadoc == Showing.Javadoc.WARNING)
                                        printMessage(javax.tools.Diagnostic.Kind.NOTE, ">Javadoc missing for annotation \"" + annotation.getName() + "\" !");
                                    chain.append("\n    ").append(annotation.toString().replace("\n", "\n    ")).append("\n");
                                }


                            
                            boolean hasSubAnalysed = true;
                            
                            

                            if(hasFields()){
                                if(hasSubAnalysed){
                                    chain.append("\n\n");
                                    hasSubAnalysed = false;
                                }
                                chain.append(_getFields(getFields()));
                            }



                            if(hasConstructors()){
                                if(hasSubAnalysed){
                                    chain.append("\n\n");
                                    hasSubAnalysed = false;
                                }
                                chain.append(_getConstructors(getConstructors()));
                            }



                            if(hasMethods()){
                                if(hasSubAnalysed){
                                    chain.append("\n\n");
                                    hasSubAnalysed = false;
                                }
                                chain.append(_getMethods(getMethods()));
                            }


                            
                            if(hasSubClass()){
                                                                
                                if (blocs == Showing.Bloc.NO_COLLAPSE) 
                                    chain.append("\n\n\n//CLASS");

                                if (blocs == Showing.Bloc.COLLAPSE)
                                    chain.append("\n\n\n    ").append(getEditorFoldStart("CLASS"));
                                
                                for(Class_type classe : getSubClass()){
                                    if(!classe.hasJavadoc() && javadoc == Showing.Javadoc.WARNING)
                                        printMessage(javax.tools.Diagnostic.Kind.NOTE, ">Javadoc missing for class \"" + classe.getName() + "\" !");
                                    chain.append("\n    ").append(classe.toString().replace("\n", "\n    ")).append("\n");
                                }
                                
                                if (blocs == Showing.Bloc.COLLAPSE)
                                    chain.append("    ").append(getEditorFoldEnd()).append("\n");
                                
                            }



                            if(hasSubInterfaces()){
                                                                
                                if (blocs == Showing.Bloc.NO_COLLAPSE) 
                                    chain.append("\n\n\n//INTERFACE").append(countSubInterfaces() > 1 ? "S" : "");

                                if (blocs == Showing.Bloc.COLLAPSE)
                                    chain.append("\n\n\n    ").append(getEditorFoldStart("INTERFACE" + (countSubInterfaces() > 1 ? "S" : "")));
                                
                                for(Interface_type inter : getSubInterfaces()){
                                    if(!inter.hasJavadoc() && javadoc == Showing.Javadoc.WARNING)
                                        printMessage(javax.tools.Diagnostic.Kind.NOTE, ">Javadoc missing for interface \"" + inter.getName() + "\" !");
                                    chain.append("\n    ").append(inter.toString().replace("\n", "\n    ")).append("\n");
                                }
                                
                                if (blocs == Showing.Bloc.COLLAPSE)
                                    chain.append("    ").append(getEditorFoldEnd()).append("\n");
                                
                            }



                            if(hasSubEnums()){
                                                                
                                if (blocs == Showing.Bloc.NO_COLLAPSE) 
                                    chain.append("\n\n\n//ENUM").append(countSubEnums() > 1 ? "S" : "");

                                if (blocs == Showing.Bloc.COLLAPSE)
                                    chain.append("\n\n\n    ").append(getEditorFoldStart("ENUM" + (countSubEnums()> 1 ? "S" : "")));
                                
                                for(Enum_type enumeration : getSubEnums()){
                                    if(!enumeration.hasJavadoc() && javadoc == Showing.Javadoc.WARNING)
                                        printMessage(javax.tools.Diagnostic.Kind.NOTE, ">Javadoc missing for enumeration \"" + enumeration.getName() + "\" !");
                                    chain.append("\n    ").append(enumeration.toString().replace("\n", "\n    ")).append("\n");
                                }
                                
                                if (blocs == Showing.Bloc.COLLAPSE)
                                    chain.append("    ").append(getEditorFoldEnd()).append("\n");
                                
                            }



                            if(hasSubAnnotations()){
                                if(hasSubAnalysed){
                                    chain.append("\n\n");
                                }
                                                                
                                if (blocs == Showing.Bloc.NO_COLLAPSE) 
                                    chain.append("\n\n\n//ANNOTATION").append(countSubAnnotations()> 1 ? "S" : "");

                                if (blocs == Showing.Bloc.COLLAPSE)
                                    chain.append("\n\n\n    ").append(getEditorFoldStart("ANNOTATION" + (countSubAnnotations() > 1 ? "S" : "")));
                                
                                for(Annotation_type annotation : getSubAnnotations()){
                                    if(!annotation.hasJavadoc() && javadoc == Showing.Javadoc.WARNING)
                                        printMessage(javax.tools.Diagnostic.Kind.NOTE, ">Javadoc missing for annotation \"" + annotation.getName() + "\" !");
                                    chain.append("\n    ").append(annotation.toString().replace("\n", "\n    ")).append("\n");
                                }
                                
                                if (blocs == Showing.Bloc.COLLAPSE)
                                    chain.append("    ").append(getEditorFoldEnd()).append("\n");
                                
                            }



                            chain.append("\n\n\n}");

                            return chain.toString();
                        }
                    };
                    return at;
                }

                @Override
                public Enum_type createEnum(CodeCreator creator, Type_code parent, EnumDeclaration ed) {
                    Enum_type et = new Enum_type(creator, parent, ed){
                        @Override
                        public String toString() {
                            StringBuilder chain = new StringBuilder();
        
        
        
                            if(hasJavadoc())
                                chain.append(getJavadoc().toString()).append("\n");



                            if(hasAnnotations())
                                for(Annotation_element a : getAnnotations()){
                                    if(a.getName().replace("com.jasonpercus.util.", "").equals(Showing.class.getSimpleName()) && a.getParent() == null)
                                        a.asComment(true);
                                    chain.append(a.toString()).append("\n");
                                }



                            chain.append(getSignature()).append(" {\n\n\n");



                            if(!getConsts().isEmpty()){
                                for(int i=0;i<getConsts().size();i++){
                                    EnumConst_element enumconst = getConst(i);
                                    if(!enumconst.hasJavadoc() && javadoc == Showing.Javadoc.WARNING)
                                        printMessage(javax.tools.Diagnostic.Kind.NOTE, ">Javadoc missing for const \"" + enumconst.getName() + "\" !");
                                    if(i>0) chain.append(",\n");
                                    chain.append("\n    ").append(enumconst.toString().replace("\n", "\n    "));
                                }
                                chain.append(";\n");
                            }


                            
                            boolean hasSubAnalysed = true;
                            
                            

                            if(hasFields()){
                                if(hasSubAnalysed){
                                    chain.append("\n\n");
                                    hasSubAnalysed = false;
                                }
                                chain.append(_getFields(getFields()));
                            }



                            if(hasConstructors()){
                                if(hasSubAnalysed){
                                    chain.append("\n\n");
                                    hasSubAnalysed = false;
                                }
                                chain.append(_getConstructors(getConstructors()));
                            }



                            if(hasMethods()){
                                if(hasSubAnalysed){
                                    chain.append("\n\n");
                                }
                                chain.append(_getMethods(getMethods()));
                            }


                            
                            if(hasSubClass()){
                                                                
                                if (blocs == Showing.Bloc.NO_COLLAPSE) 
                                    chain.append("\n\n\n//CLASS");

                                if (blocs == Showing.Bloc.COLLAPSE)
                                    chain.append("\n\n\n    ").append(getEditorFoldStart("CLASS"));
                                
                                for(Class_type classe : getSubClass()){
                                    if(!classe.hasJavadoc() && javadoc == Showing.Javadoc.WARNING)
                                        printMessage(javax.tools.Diagnostic.Kind.NOTE, ">Javadoc missing for class \"" + classe.getName() + "\" !");
                                    chain.append("\n    ").append(classe.toString().replace("\n", "\n    ")).append("\n");
                                }
                                
                                if (blocs == Showing.Bloc.COLLAPSE)
                                    chain.append("    ").append(getEditorFoldEnd()).append("\n");
                                
                            }



                            if(hasSubInterfaces()){
                                                                
                                if (blocs == Showing.Bloc.NO_COLLAPSE) 
                                    chain.append("\n\n\n//INTERFACE").append(countSubInterfaces() > 1 ? "S" : "");

                                if (blocs == Showing.Bloc.COLLAPSE)
                                    chain.append("\n\n\n    ").append(getEditorFoldStart("INTERFACE" + (countSubInterfaces() > 1 ? "S" : "")));
                                
                                for(Interface_type inter : getSubInterfaces()){
                                    if(!inter.hasJavadoc() && javadoc == Showing.Javadoc.WARNING)
                                        printMessage(javax.tools.Diagnostic.Kind.NOTE, ">Javadoc missing for interface \"" + inter.getName() + "\" !");
                                    chain.append("\n    ").append(inter.toString().replace("\n", "\n    ")).append("\n");
                                }
                                
                                if (blocs == Showing.Bloc.COLLAPSE)
                                    chain.append("    ").append(getEditorFoldEnd()).append("\n");
                                
                            }



                            if(hasSubEnums()){
                                                                
                                if (blocs == Showing.Bloc.NO_COLLAPSE) 
                                    chain.append("\n\n\n//ENUM").append(countSubEnums() > 1 ? "S" : "");

                                if (blocs == Showing.Bloc.COLLAPSE)
                                    chain.append("\n\n\n    ").append(getEditorFoldStart("ENUM" + (countSubEnums()> 1 ? "S" : "")));
                                
                                for(Enum_type enumeration : getSubEnums()){
                                    if(!enumeration.hasJavadoc() && javadoc == Showing.Javadoc.WARNING)
                                        printMessage(javax.tools.Diagnostic.Kind.NOTE, ">Javadoc missing for enumeration \"" + enumeration.getName() + "\" !");
                                    chain.append("\n    ").append(enumeration.toString().replace("\n", "\n    ")).append("\n");
                                }
                                
                                if (blocs == Showing.Bloc.COLLAPSE)
                                    chain.append("    ").append(getEditorFoldEnd()).append("\n");
                                
                            }



                            if(hasSubAnnotations()){
                                                                
                                if (blocs == Showing.Bloc.NO_COLLAPSE) 
                                    chain.append("\n\n\n//ANNOTATION").append(countSubAnnotations()> 1 ? "S" : "");

                                if (blocs == Showing.Bloc.COLLAPSE)
                                    chain.append("\n\n\n    ").append(getEditorFoldStart("ANNOTATION" + (countSubAnnotations() > 1 ? "S" : "")));
                                
                                for(Annotation_type annotation : getSubAnnotations()){
                                    if(!annotation.hasJavadoc() && javadoc == Showing.Javadoc.WARNING)
                                        printMessage(javax.tools.Diagnostic.Kind.NOTE, ">Javadoc missing for annotation \"" + annotation.getName() + "\" !");
                                    chain.append("\n    ").append(annotation.toString().replace("\n", "\n    ")).append("\n");
                                }
                                
                                if (blocs == Showing.Bloc.COLLAPSE)
                                    chain.append("    ").append(getEditorFoldEnd()).append("\n");
                                
                            }



                            chain.append("\n\n\n}");



                            return chain.toString();
                        }
                    };
                    return et;
                }

                @Override
                public Class_type createClass(CodeCreator creator, Type_code parent, ClassOrInterfaceDeclaration coid) {
                    Class_type ct = new Class_type(creator, parent, coid){
                        @Override
                        public String toString() {
                            StringBuilder chain = new StringBuilder();
        
        
        
                            if(hasJavadoc())
                                chain.append(getJavadoc().toString()).append("\n");



                            if(hasAnnotations())
                                for(com.jasonpercus.util.code.Annotation_element a : getAnnotations()){
                                    if(a.getName().replace("com.jasonpercus.util.", "").equals(Showing.class.getSimpleName()))
                                        a.asComment(true);
                                    chain.append(a.toString()).append("\n");
                                }



                            chain.append(getSignature()).append(" {\n\n\n");



                            if(hasFields())
                                chain.append(_getFields(getFields()));



                            if(hasConstructors())
                                chain.append(_getConstructors(getConstructors()));



                            if(hasMethods())
                                chain.append(_getMethods(getMethods()));



                            if(hasSubClass()){
                                                                
                                if (blocs == Showing.Bloc.NO_COLLAPSE) 
                                    chain.append("\n\n\n//CLASS");

                                if (blocs == Showing.Bloc.COLLAPSE)
                                    chain.append("\n\n\n    ").append(getEditorFoldStart("CLASS"));
                                
                                for(Class_type classe : getSubClass()){
                                    if(!classe.hasJavadoc() && javadoc == Showing.Javadoc.WARNING)
                                        printMessage(javax.tools.Diagnostic.Kind.NOTE, ">Javadoc missing for class \"" + classe.getName() + "\" !");
                                    chain.append("\n    ").append(classe.toString().replace("\n", "\n    ")).append("\n");
                                }
                                
                                if (blocs == Showing.Bloc.COLLAPSE)
                                    chain.append("    ").append(getEditorFoldEnd()).append("\n");
                                
                            }



                            if(hasSubInterfaces()){
                                                                
                                if (blocs == Showing.Bloc.NO_COLLAPSE) 
                                    chain.append("\n\n\n//INTERFACE").append(countSubInterfaces() > 1 ? "S" : "");

                                if (blocs == Showing.Bloc.COLLAPSE)
                                    chain.append("\n\n\n    ").append(getEditorFoldStart("INTERFACE" + (countSubInterfaces() > 1 ? "S" : "")));
                                
                                for(Interface_type inter : getSubInterfaces()){
                                    if(!inter.hasJavadoc() && javadoc == Showing.Javadoc.WARNING)
                                        printMessage(javax.tools.Diagnostic.Kind.NOTE, ">Javadoc missing for interface \"" + inter.getName() + "\" !");
                                    chain.append("\n    ").append(inter.toString().replace("\n", "\n    ")).append("\n");
                                }
                                
                                if (blocs == Showing.Bloc.COLLAPSE)
                                    chain.append("    ").append(getEditorFoldEnd()).append("\n");
                                
                            }



                            if(hasSubEnums()){
                                                                
                                if (blocs == Showing.Bloc.NO_COLLAPSE) 
                                    chain.append("\n\n\n//ENUM").append(countSubEnums() > 1 ? "S" : "");

                                if (blocs == Showing.Bloc.COLLAPSE)
                                    chain.append("\n\n\n    ").append(getEditorFoldStart("ENUM" + (countSubEnums()> 1 ? "S" : "")));
                                
                                for(Enum_type enumeration : getSubEnums()){
                                    if(!enumeration.hasJavadoc() && javadoc == Showing.Javadoc.WARNING)
                                        printMessage(javax.tools.Diagnostic.Kind.NOTE, ">Javadoc missing for enumeration \"" + enumeration.getName() + "\" !");
                                    chain.append("\n    ").append(enumeration.toString().replace("\n", "\n    ")).append("\n");
                                }
                                
                                if (blocs == Showing.Bloc.COLLAPSE)
                                    chain.append("    ").append(getEditorFoldEnd()).append("\n");
                                
                            }



                            if(hasSubAnnotations()){
                                                                
                                if (blocs == Showing.Bloc.NO_COLLAPSE) 
                                    chain.append("\n\n\n//ANNOTATION").append(countSubAnnotations()> 1 ? "S" : "");

                                if (blocs == Showing.Bloc.COLLAPSE)
                                    chain.append("\n\n\n    ").append(getEditorFoldStart("ANNOTATION" + (countSubAnnotations() > 1 ? "S" : "")));
                                
                                for(Annotation_type annotation : getSubAnnotations()){
                                    if(!annotation.hasJavadoc() && javadoc == Showing.Javadoc.WARNING)
                                        printMessage(javax.tools.Diagnostic.Kind.NOTE, ">Javadoc missing for annotation \"" + annotation.getName() + "\" !");
                                    chain.append("\n    ").append(annotation.toString().replace("\n", "\n    ")).append("\n");
                                }
                                
                                if (blocs == Showing.Bloc.COLLAPSE)
                                    chain.append("    ").append(getEditorFoldEnd()).append("\n");
                                
                            }



                            chain.append("\n\n\n}");



                            return chain.toString();
                        }
                    };
                    return ct;
                }

                @Override
                public Interface_type createInterface(CodeCreator creator, Type_code parent, ClassOrInterfaceDeclaration coid) {
                    Interface_type it = new Interface_type(creator, parent, coid){
                        @Override
                        public String toString() {
                            StringBuilder chain = new StringBuilder();



                            if(hasJavadoc())
                                chain.append(getJavadoc().toString()).append("\n");



                            if(hasAnnotations())
                                for(Annotation_element a : getAnnotations()){
                                    if(a.getName().replace("com.jasonpercus.util.", "").equals(Showing.class.getSimpleName()))
                                        a.asComment(true);
                                    chain.append(a.toString()).append("\n");
                                }



                            chain.append(getSignature()).append(" {\n\n\n");



                            if(hasFields())
                                chain.append(_getFields(getFields()));



                            if(hasConstructors())
                                chain.append(_getConstructors(getConstructors()));



                            if(hasMethods())
                                chain.append(_getMethods(getMethods()));


                            
                            if(hasSubClass()){
                                                                
                                if (blocs == Showing.Bloc.NO_COLLAPSE) 
                                    chain.append("\n\n\n//CLASS");

                                if (blocs == Showing.Bloc.COLLAPSE)
                                    chain.append("\n\n\n    ").append(getEditorFoldStart("CLASS"));
                                
                                for(Class_type classe : getSubClass()){
                                    if(!classe.hasJavadoc() && javadoc == Showing.Javadoc.WARNING)
                                        printMessage(javax.tools.Diagnostic.Kind.NOTE, ">Javadoc missing for class \"" + classe.getName() + "\" !");
                                    chain.append("\n    ").append(classe.toString().replace("\n", "\n    ")).append("\n");
                                }
                                
                                if (blocs == Showing.Bloc.COLLAPSE)
                                    chain.append("    ").append(getEditorFoldEnd()).append("\n");
                                
                            }



                            if(hasSubInterfaces()){
                                                                
                                if (blocs == Showing.Bloc.NO_COLLAPSE) 
                                    chain.append("\n\n\n//INTERFACE").append(countSubInterfaces() > 1 ? "S" : "");

                                if (blocs == Showing.Bloc.COLLAPSE)
                                    chain.append("\n\n\n    ").append(getEditorFoldStart("INTERFACE" + (countSubInterfaces() > 1 ? "S" : "")));
                                
                                for(Interface_type inter : getSubInterfaces()){
                                    if(!inter.hasJavadoc() && javadoc == Showing.Javadoc.WARNING)
                                        printMessage(javax.tools.Diagnostic.Kind.NOTE, ">Javadoc missing for interface \"" + inter.getName() + "\" !");
                                    chain.append("\n    ").append(inter.toString().replace("\n", "\n    ")).append("\n");
                                }
                                
                                if (blocs == Showing.Bloc.COLLAPSE)
                                    chain.append("    ").append(getEditorFoldEnd()).append("\n");
                                
                            }



                            if(hasSubEnums()){
                                                                
                                if (blocs == Showing.Bloc.NO_COLLAPSE) 
                                    chain.append("\n\n\n//ENUM").append(countSubEnums() > 1 ? "S" : "");

                                if (blocs == Showing.Bloc.COLLAPSE)
                                    chain.append("\n\n\n    ").append(getEditorFoldStart("ENUM" + (countSubEnums()> 1 ? "S" : "")));
                                
                                for(Enum_type enumeration : getSubEnums()){
                                    if(!enumeration.hasJavadoc() && javadoc == Showing.Javadoc.WARNING)
                                        printMessage(javax.tools.Diagnostic.Kind.NOTE, ">Javadoc missing for enumeration \"" + enumeration.getName() + "\" !");
                                    chain.append("\n    ").append(enumeration.toString().replace("\n", "\n    ")).append("\n");
                                }
                                
                                if (blocs == Showing.Bloc.COLLAPSE)
                                    chain.append("    ").append(getEditorFoldEnd()).append("\n");
                                
                            }



                            if(hasSubAnnotations()){
                                                                
                                if (blocs == Showing.Bloc.NO_COLLAPSE) 
                                    chain.append("\n\n\n//ANNOTATION").append(countSubAnnotations()> 1 ? "S" : "");

                                if (blocs == Showing.Bloc.COLLAPSE)
                                    chain.append("\n\n\n    ").append(getEditorFoldStart("ANNOTATION" + (countSubAnnotations() > 1 ? "S" : "")));
                                
                                for(Annotation_type annotation : getSubAnnotations()){
                                    if(!annotation.hasJavadoc() && javadoc == Showing.Javadoc.WARNING)
                                        printMessage(javax.tools.Diagnostic.Kind.NOTE, ">Javadoc missing for annotation \"" + annotation.getName() + "\" !");
                                    chain.append("\n    ").append(annotation.toString().replace("\n", "\n    ")).append("\n");
                                }
                                
                                if (blocs == Showing.Bloc.COLLAPSE)
                                    chain.append("    ").append(getEditorFoldEnd()).append("\n");
                                
                            }



                            chain.append("\n\n\n}");



                            return chain.toString();
                        }
                    };
                    return it;
                }

                @Override
                public Method_element createMethodElement(CodeCreator creator, MethodDeclaration md, Type_code parent) {
                    Method_element me = new Method_element(creator, md, parent){
                        
                        @Override
                        public synchronized boolean isSetter() {
                            return (!isDefault() && !isAbstract() && !isNative() && (isPublic() || isProtected()) && !isStatic() && !isStrictfp() && getName().indexOf("set") == 0 && countParams() == 1);
                        }
                        
                    };
                    return me;
                }
                
            };
            setCreator(creator);
        }

        
        
    //METHODE PUBLIC
        /**
         * Renvoie le code de ce fichier de code
         * @return Retourne le code de ce fichier de code
         */
        @Override
        public synchronized String getCode() {
            StringBuilder sb = new StringBuilder();
                if (getLicence() != null) {
                    sb.append(getLicence()).append("\n");
                }
                sb.append(getPackage()).append("\n\n\n\n");
                if (!getImports().isEmpty()) {
                    for (Import_element _import : getImports()) {
                        sb.append(_import).append("\n");
                    }
                    sb.append("\n\n\n");
                }
                if (!getDocuments().isEmpty()) {
                    for (int i = 0; i < getDocuments().size(); i++) {
                        Type_code doc = getDocuments(i);
                        if(i == 0 && doc.hasJavadoc()){
                            Javadoc_code documentation = doc.getJavadoc();
                            Version_javadoc version = null;
                            for(Element_javadoc e : documentation.getElements()){
                                if(e instanceof Version_javadoc){
                                    version = (Version_javadoc) e;
                                    break;
                                }
                            }
                            if(version != null){
                                String description = version.getDescription();
                                if(description.isEmpty())
                                    version.setDescription("1.0");
                                else{
                                    java.util.regex.Pattern p = java.util.regex.Pattern.compile("((\\d*\\.)*\\d)");
                                    java.util.regex.Matcher m = p.matcher(description);
                                    while(m.find()) {
                                        String v = m.group();
                                        String[] split;
                                        if(v.contains(".")){
                                            split = v.split("\\.");
                                            split[split.length-1] = "" + (Integer.parseInt(split[split.length-1]) + 1);
                                        }else{
                                            split = new String[]{"" + (Integer.parseInt(m.group()) + 1)};
                                        }
                                        v = java.util.Arrays.toString(split).replace("[", "").replace("]", "").replace(",", ".").replace(" ", "");
                                        
                                        if(m.start() == 0 && m.end() == description.length())
                                            version.setDescription(v);
                                        else if(m.start() == 0 && m.end() < description.length())
                                            version.setDescription(v + description.substring(m.end()));
                                        else if(m.start() > 0 && m.end() == description.length())
                                            version.setDescription(description.substring(0, m.start()) + v);
                                        else
                                            version.setDescription(description.substring(0, m.start()) + v + description.substring(m.end()));
                                        break;
                                    }
                                }
                            }
                        }
                        if (i > 0) {
                            sb.append("\n\n\n\n");
                        }
                        sb.append(doc);
                    }
                }
                return sb.toString();
        }
        
        
        
    //CLASS
        /**
         * Cette classe représente un coule d'un getter et d'un setter pointant le même attribut
         * @author JasonPercus
         * @version 1.0
         */
        private class CoupleGetSet implements Comparable<CoupleGetSet> {
            
            
            
        //ATTRIBUTS
            /**
             * Correspond au getter
             */
            private Method_element get;
            
            /**
             * Correspond au setter
             */
            private Method_element set;

            
            
        //CONSTRUCTOR
            /**
             * Crée un couple
             * @param first correspond au getter ou au setter
             * @param second Correspond au setter ou au getter
             */
            public CoupleGetSet(Method_element first, Method_element second) {
                if(first.isGetter()){
                    this.get = first;
                    this.set = second;
                }else{
                    this.get = second;
                    this.set = first;
                }
            }
            
            
            
        //METHODES PUBLICS
            /**
             * Renvoie le nom du couple (ex: si un getter s'appelle isValid() ou qu'un setter s'appelle setValid(). Il s'agit bien alors d'un couple de getter setter. Le nom du couple sera Valid)
             * @return Retourne le nom du couple
             */
            public String name(){
                if(set != null)
                    return set.getName().replaceFirst("set", "");
                else{
                    if(get.getName().indexOf("get") == 0)
                        return get.getName().replaceFirst("get", "");
                    else if(get.getName().indexOf("is") == 0)
                        return get.getName().replaceFirst("is", "");
                    else 
                        return null;
                }
            }

            /**
             * Compare deux couples de getter setter
             * @param o Correspond au second couple à comparer au couple courant
             * @return Retourne le résultat de la comparaison
             */
            @Override
            public int compareTo(CoupleGetSet o) {
                if(get == null && set != null && o.get == null && o.set != null)
                    return name().compareTo(o.name());
                else if(get == null && set != null && o.get != null && o.set == null)
                    return 1;
                else if(get == null && set != null && o.get != null && o.set != null)
                    return 1;
                else if(get != null && set == null && o.get == null && o.set != null)
                    return -1;
                else if(get != null && set == null && o.get != null && o.set == null)
                    return name().compareTo(o.name());
                else if(get != null && set == null && o.get != null && o.set != null)
                    return 1;
                else if(get != null && set != null && o.get == null && o.set != null)
                    return -1;
                else if(get != null && set != null && o.get != null && o.set == null)
                    return -1;
                else if(get != null && set != null && o.get != null && o.set != null)
                    return name().compareTo(o.name());
                return 0;
            }
            
            
            
        }
        
        
        
    }
    
    
    
}