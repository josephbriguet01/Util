/*
 * Copyright (C) JasonPercus Systems, Inc - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by JasonPercus, 06/2021
 */
package com.jasonpercus.util;



import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseResult;
import com.github.javaparser.ParserConfiguration;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.AnnotationDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.EnumDeclaration;
import com.jasonpercus.util.code.CodeCreator;
import com.jasonpercus.util.code.Import_element;
import com.jasonpercus.util.code.Package_element;
import com.jasonpercus.util.code.SyntaxCodeException;
import com.jasonpercus.util.code.Type_code;



/**
 * Cette classe représente les fichiers de code *.java. A partir d'un objet {@link FileCode} il est possible d'analyser toute la structure du fichier de code grâce à la méthode {@link #analyse() FileCode.Analyse()}, puis de faire des modifications, puis réenregistrer, écraser un fichier de code...
 * @author JasonPercus
 * @version 1.0
 */
@SuppressWarnings({"NestedSynchronizedStatement", "SynchronizeOnNonFinalField"})
public class FileCode extends File {

    
    
//ATTRIBUTS
    /**
     * Correspond à la licence du fichier de code
     */
    private String _licence;
    
    /**
     * Correspond au package du fichier de code
     */
    private Package_element _package;
    
    /**
     * Correspond à la liste des imports du fichier de code
     */
    private java.util.List<Import_element> _imports;
    
    /**
     * Correspond à la liste des classes, interfaces, enumérations et @annotations du fichier de code
     */
    private java.util.List<Type_code> _docs;
    
    /**
     * Correspond à la classe qui gère les créations des éléments de code
     */
    private CodeCreator creator;
    
    /**
     * Initialise les listes
     */
    {
        _imports = new java.util.ArrayList<>();
        _docs    = new java.util.ArrayList<>();
        creator  = new CodeCreator();
    }
    
    
    
//CONSTRUCTORS
    /**
     * Crée un FileCode par défaut
     * @deprecated <div style="color: #D45B5B; font-style: italic">Ne pas utiliser - Il n'a de l'intérêt que pour la dé/sérialization</div>
     */
    public FileCode() {
        super("");
    }

    /**
     * Crée une nouvelle instance de File en convertissant la chaîne de chemin d'accès donnée en un chemin d'accès abstrait
     * @param pathname Correspond à une chaîne de chemin
     */
    public FileCode(String pathname) {
        super(pathname);
    }
    
    /**
     * Crée une nouvelle instance de File en convertissant le fichier donné en un chemin d'accès abstrait
     * @param file Correspond au fichier à convertir
     */
    public FileCode(java.io.File file){
        super(file);
    }

    /**
     * Crée une nouvelle instance de File à partir d'une chaîne de chemin parent et d'une chaîne de chemin enfant
     * @param parent Correspond à la chaîne de chemin d'accès parent
     * @param child Correspond à la chaîne de chemin d'accès enfant
     */
    public FileCode(String parent, String child) {
        super(parent, child);
    }

    /**
     * Crée une nouvelle instance de fichier à partir d'un chemin d'accès abstrait parent et d'une chaîne de chemin d'accès enfant
     * @param parent Correspond au chemin d'accès abstrait parent
     * @param child Correspond à la chaîne de chemin d'accès enfant
     */
    public FileCode(java.io.File parent, String child) {
        super(parent, child);
    }

    /**
     * Crée une nouvelle instance de File en convertissant le fichier donné: URI en un chemin abstrait
     * @param uri Correspond à une URI hiérarchique absolu avec un schéma égal à "fichier", un composant de chemin non vide et des composants d'autorité, de requête et de fragment non définis
     */
    public FileCode(java.net.URI uri) {
        super(uri);
    }

    
    
//METHODES PUBLICS
    /**
     * Renvoie le créateur d'élément de code
     * @return Retourne le créateur d'élément de code
     */
    public synchronized final CodeCreator getCreator() {
        return creator;
    }

    /**
     * Modifie le créateur d'élément de code
     * @param creator Retourne le créateur d'élément de cide
     */
    public synchronized final void setCreator(CodeCreator creator) {
        this.creator = creator;
    }
    
    /**
     * Renvoie la licence de ce fichier de code
     * @return Retourne la licence de ce fichier de code
     */
    public synchronized final String getLicence() {
        return _licence;
    }
    
    /**
     * Modifie la licence de ce fichier de code
     * @param licence Correspond à la nouvelle licence de ce fichier de code
     */
    public synchronized final void setLicence(String licence){
        this._licence = licence;
    }

    /**
     * Renvoie le package de ce fichier de code
     * @return Retourne le package de ce fichier de code
     */
    public synchronized final Package_element getPackage() {
        return _package;
    }

    /**
     * Modifie le package de ce fichier de code
     * @param _package Correspond au nouveau pachage de ce fichier de code
     */
    public synchronized final void setPackage(Package_element _package) {
        this._package = _package;
    }

    /**
     * Renvoie une copie de la liste des imports (attention: il s'agit d'une copie de liste. Donc les modifications réalisées dessus n'auront aucun impact sur le fichier de code)
     * @return Retourne une copie de la liste des imports
     */
    public synchronized final java.util.List<Import_element> getImports() {
        synchronized(_imports){
            java.util.List<Import_element> copy = new java.util.ArrayList<>();
            for(Import_element ic : _imports)
                copy.add(ic);
            return copy;
        }
    }
    
    /**
     * Ajoute un/des imports dans le fichier de code
     * @param imports Correspond au(x) import(s) à ajouter dans le fichier de code
     */
    public synchronized final void addImports(Import_element...imports){
        synchronized(_imports){
            if(imports != null){
                for(Import_element code : imports){
                    if(!_imports.contains(code))
                        _imports.add(code);
                }
            }
        }
    }
    
    /**
     * Enlève un/des imports du fichier de code
     * @param imports Correspond au(x) import(s) à enlever du fichier de code
     */
    public synchronized final void removeImports(Import_element...imports){
        synchronized(_imports){
            if(imports != null){
                for(Import_element code : imports){
                    if(_imports.contains(code))
                        _imports.remove(code);
                }
            }
        }
    }
    
    /**
     * Enlève un import du fichier de code à l'indice donné
     * @param index Correspond à l'indice de l'import à enlever
     */
    public synchronized final void removeImport(int index){
        synchronized(_imports){
            _imports.remove(index);
        }
    }
    
    /**
     * Enlève tous les imports du fichier de code
     */
    public synchronized final void clearImports(){
        synchronized(_imports){
            _imports.clear();
        }
    }
    
    /**
     * Renvoie le nombre d'import compris dans le fichier de code
     * @return Retourne le nombre d'import compris dans le fichier de code
     */
    public synchronized final int countImports(){
        synchronized(_imports){
            return _imports.size();
        }
    }
    
    /**
     * Renvoie l'import à l'indice index, compris dans le fichier de source
     * @param index Correspond à l'indice de l'import à renvoyer
     * @return Retourne l'import à l'indice index, compris dans le fichier de source
     */
    public synchronized final Import_element getImport(int index){
        synchronized(_imports){
            return _imports.get(index);
        }
    }

    /**
     * Renvoie une copie de la liste des [classes | interfaces | énumérations | @annotations] à la source du fichier de code (attention: il s'agit d'une copie de liste. Donc les modifications réalisées dessus n'auront aucun impact sur le fichier de code)
     * @return Retourne une copie de la liste des [classes | interfaces | énumérations | @annotations]
     */
    public synchronized final java.util.List<Type_code> getDocuments() {
        synchronized(_docs){
            java.util.List<Type_code> copy = new java.util.ArrayList<>();
            for (Type_code tc : _docs) {
                copy.add(tc);
            }
            return copy;
        }
    }
    
    /**
     * Ajoute une/des [classe(s) | interface(s) | énumération(s) | @annotation(s)] à la source du fichier de code
     * @param documents Correspond au(x) [classe(s) | interface(s) | énumération(s) | @annotation(s)] à ajouter à la source du fichier de code
     */
    public synchronized final void addDocuments(Type_code...documents){
        synchronized(_docs){
            if(documents != null){
                for(Type_code code : documents){
                    if(!_docs.contains(code))
                        _docs.add(code);
                }
            }
        }
    }
    
    /**
     * Enlève une/des [classe(s) | interface(s) | énumération(s) | @annotation(s)] de la source du fichier de code
     * @param documents Correspond au(x) [classe(s) | interface(s) | énumération(s) | @annotation(s)] à enlever de la source du fichier de code
     */
    public synchronized final void removeDocuments(Type_code...documents){
        synchronized(_docs){
            if(documents != null){
                for(Type_code code : documents){
                    if(_docs.contains(code))
                        _docs.remove(code);
                }
            }
        }
    }
    
    /**
     * Enlève une [classe | interface | énumération | @annotation] de la source du fichier de code à l'indice donné
     * @param index Correspond à l'indice de la [classe | interface | énumération | @annotation] à enlever
     */
    public synchronized final void removeDocument(int index){
        synchronized(_docs){
            _docs.remove(index);
        }
    }
    
    /**
     * Enlève toutes les [classes | interfaces | énumérations | @annotations] de la source du fichier de code
     */
    public synchronized final void clearDocuments(){
        synchronized(_docs){
            _docs.clear();
        }
    }
    
    /**
     * Renvoie le nombre de [classes | interfaces | énumérations | @annotations] à la source du fichier de code
     * @return Retourne le nombre de [classes | interfaces | énumérations | @annotations] à la source du fichier de code
     */
    public synchronized final int countDocuments(){
        synchronized(_docs){
            return _docs.size();
        }
    }
    
    /**
     * Renvoie la [classe | interface | énumération | @annotation] à l'indice index, compris dans le fichier de source
     * @param index Correspond à l'indice de la [classe | interface | énumération | @annotation] à renvoyer
     * @return Retourne la [classe | interface | énumération | @annotation] à l'indice index, compris dans le fichier de source
     */
    public synchronized final Type_code getDocuments(int index){
        synchronized(_docs){
            return _docs.get(index);
        }
    }
    
    /**
     * Analyse le fichier de code
     * @throws java.io.FileNotFoundException Si le fichier n'a pas été trouvé
     * @throws SyntaxCodeException Si le fichier contient des erreurs de syntax java
     */
    public synchronized final void analyse() throws java.io.FileNotFoundException, SyntaxCodeException {
        synchronized(_imports){
            synchronized(_docs){
                if (creator == null) {
                    creator = new CodeCreator();
                }
                ParseResult<CompilationUnit> result = new JavaParser(new ParserConfiguration()).parse(this);
                if (result.isSuccessful()) {
                    _package = null;
                    _imports = new java.util.ArrayList<>();
                    _docs = new java.util.ArrayList<>();

                    java.util.List<Node> nodes = result.getResult().get().getChildNodes();
                    for (ImportDeclaration node : result.getResult().get().getImports()) {
                        _imports.add(creator.createImport(creator, node.getName().toString(), node.isStatic(), node.isAsterisk(), null));
                    }
                    for (Node node : nodes) {
                        if (node instanceof PackageDeclaration) {
                            PackageDeclaration pack = (PackageDeclaration) node;
                            _package = creator.createPackage(creator, pack.getNameAsString(), null);
                        }
                        if (node instanceof ClassOrInterfaceDeclaration) {
                            ClassOrInterfaceDeclaration coid = (ClassOrInterfaceDeclaration) node;
                            if (coid.isInterface()) {
                                _docs.add(creator.createInterface(creator, null, coid));
                            } else {
                                _docs.add(creator.createClass(creator, null, coid));
                            }
                        } else if (node instanceof EnumDeclaration) {
                            _docs.add(creator.createEnum(creator, null, (EnumDeclaration) node));
                        } else if (node instanceof AnnotationDeclaration) {
                            _docs.add(creator.createAnnotation(creator, null, (AnnotationDeclaration) node));
                        }
                    }
                } else {
                    throw new SyntaxCodeException("The file code is not written correctly !");
                }
            }
        }
    }
    
    /**
     * Ecrase le fichier de code par les modifications effectuées de ce fichier de code
     * @throws java.io.IOException Si une exception survient lors de l'écriture ou si aucun contenu est à écrire
     */
    public synchronized final void write() throws java.io.IOException {
        write(this);
    }
    
    /**
     * Ecrit un fichier de code en utilisant pour base les modifications effectuées de ce fichier de code
     * @param target Correspond au nouveau fichier de code qui sera créé
     * @throws java.io.IOException Si une exception survient lors de l'écriture ou si aucun contenu est à écrire
     */
    public synchronized final void write(java.io.File target) throws java.io.IOException {
        if(canWritten()){
            synchronized(_imports){
                synchronized(_docs){
                    try (java.io.BufferedWriter bw = new java.io.BufferedWriter(new java.io.OutputStreamWriter(new java.io.FileOutputStream(target), java.nio.charset.StandardCharsets.UTF_8))) {
                        bw.write(FileCode.this.toString());
                        bw.flush();
                    }
                }
            }
        }else{
            throw new java.io.IOException("Code file " + target + " cannot be written because it does not contain a package or class ...");
        }
    }
    
    /**
     * Renvoie le code de ce fichier de code
     * @return Retourne le code de ce fichier de code
     */
    public synchronized String getCode(){
        synchronized (_imports) {
            synchronized (_docs) {
                StringBuilder sb = new StringBuilder();
                if (_licence != null) {
                    sb.append(_licence).append("\n");
                }
                sb.append(_package).append("\n\n");
                if (!_imports.isEmpty()) {
                    for (Import_element _import : _imports) {
                        sb.append(_import).append("\n");
                    }
                    sb.append("\n");
                }
                if (!_docs.isEmpty()) {
                    for (int i = 0; i < _docs.size(); i++) {
                        Type_code doc = _docs.get(i);
                        if (i > 0) {
                            sb.append("\n\n");
                        }
                        sb.append(doc);
                    }
                }
                return sb.toString();
            }
        }
    }
    
    /**
     * Renvoie le fichier de code sous la forme d'une chaîne de caractères
     * @return Retourne le fichier de code sous la forme d'une chaîne de caractères
     */
    @Override
    public synchronized final String toString(){
        if(canWritten()){
            return getCode();
        }else{
            return super.toString();
        }
    }
    
    
    
//METHODE PRIVATE
    /**
     * Détermine si le fichier de code contient suffisament de code pour pouvoir être affiché ou écrit dans un fichier
     * @return Retourne true si c'est las cas, sinon false
     */
    private synchronized boolean canWritten(){
        synchronized(_docs){
            return (_package != null && !_docs.isEmpty());
        }
    }
    
    
    
}