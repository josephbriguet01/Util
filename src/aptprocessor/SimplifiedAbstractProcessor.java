/*
 * Copyright (C) JasonPercus Systems, Inc - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by JasonPercus, 06/2021
 */
package aptprocessor;



import com.jasonpercus.util.code.CodeCreator;
import com.jasonpercus.util.FileCode;
import com.jasonpercus.util.code.SyntaxCodeException;



/**
 * Cette classe représente un processeur abstrait de compilation
 * @author JasonPercus
 * @version 1.0
 */
@SuppressWarnings("ObsoleteAnnotationSupportedSource")
public abstract class SimplifiedAbstractProcessor extends javax.annotation.processing.AbstractProcessor {

    
    
//CONSTANTES
    /**
     * Correspond au chemin par défaut des sources d'un projet standard
     */
    private final static String DEFAULT_PATH_NORMAL = System.getProperty("user.dir") + java.io.File.separator + "src";
    
    /**
     * Correspond au chemin par défaut des sources d'un projet android
     */
    private final static String DEFAULT_PATH_ANDROID = System.getProperty("user.dir") + java.io.File.separator + "app" + java.io.File.separator + "src" + java.io.File.separator + "main" + java.io.File.separator + "java";
    
    
    
//ATTRIBUTS STATICS
    /**
     * Correspond au chemin où se trouve les sources du projet
     */
    private final static String PATH;
    
    /**
     * Détermine s'il s'agit d'un projet android ou pas
     */
    private final static boolean ANDROID_PROJECT;
    
    static{
        if(new java.io.File(DEFAULT_PATH_NORMAL).exists()){
            PATH = DEFAULT_PATH_NORMAL;
            ANDROID_PROJECT = false;
        } else if(new java.io.File(DEFAULT_PATH_ANDROID).exists()){
            PATH = DEFAULT_PATH_ANDROID;
            ANDROID_PROJECT = true;
        } else{
            PATH = DEFAULT_PATH_NORMAL;
            ANDROID_PROJECT = false;
            throw new UnknownError("Your Java project type is unknown ! Therefore, the source analysis cannot be performed.");
        }
    }
    
    /**
     * Correspond au messager
     */
    protected javax.annotation.processing.Messager messager;
    
    
    
//METHODES PUBLICS
    /**
     * Lorsque le processor s'initialise
     * @param processingEnv Correspond à l'environnement du processor
     */
    @Override
    public synchronized void init(javax.annotation.processing.ProcessingEnvironment processingEnv){
        super.init(processingEnv);
        this.messager = processingEnv.getMessager();
    }
    
    /**
     * Ecrit un message
     * @param type Correspond au type de message
     * @param message Correspond au message à afficher
     */
    public void printMessage(javax.tools.Diagnostic.Kind type, String message){
        this.messager.printMessage(type, message);
    }
    
    /**
     * Ecrit un message
     * @param type Correspond au type de message
     * @param message Correspond au message à afficher
     * @param e Correspond à l'élément concerné par le message
     */
    public void printMessage(javax.tools.Diagnostic.Kind type, String message, javax.lang.model.element.Element e){
        this.messager.printMessage(type, message, e);
    }
    
    /**
     * Renvoie le chemin de la classe dans l'arborescence de fichiers
     * @param classe Correspond à la classe dont on veut connaître le chemin
     * @return Retourne le chemin de la classe
     */
    public final String getPathClass(javax.lang.model.element.Element classe){
        return getPathSrcProject() + java.io.File.separator + classe.getEnclosingElement().toString().replace(".", java.io.File.separator) + java.io.File.separator + classe.getSimpleName();
    }
    
    /**
     * Renvoie le fichier de la classe dans l'arborescence de fichiers
     * @param classe Correspond à la classe dont on veut connaître le fichier
     * @return Retourne le fichier de la classe
     */
    public final java.io.File getFileClass(javax.lang.model.element.Element classe){
        return new java.io.File(getPathClass(classe));
    }

    /**
     * Renvoie le chemin du projet
     * @return Retourne le chemin du projet
     */
    public final String getPathProject() {
        return System.getProperty("user.dir");
    }
    
    /**
     * Renvoie le répertoire du projet
     * @return Retourne le répertoire du projet
     */
    public final java.io.File getFileProject(){
        return new java.io.File(getPathProject());
    }
    
    /**
     * Renvoie le chemin du projet où se trouve le code source qui va être compilé
     * @return Retourne le chemin du projet
     */
    public final String getPathSrcProject(){
        return PATH;
    }
    
    /**
     * Renvoie le répertoire du projet où se trouve le code source qui va être compilé
     * @return Retourne le répertoire du projet
     */
    public final java.io.File getFileSrcProject(){
        return new java.io.File(PATH);
    }
    
    /**
     * Détermine si le projet qui est compilé est un projet android
     * @return Retourne true, si c'est le cas, sinon false
     */
    public final boolean isAndroidProject(){
        return ANDROID_PROJECT;
    }
    
    /**
     * Importe et analyse le code d'un fichier java
     * @param classe Correspond à la classe pointé par l'annotation
     * @return Retourne le filecode importé et analysé
     * @throws java.io.FileNotFoundException Si le fichier source n'existe pas
     * @throws SyntaxCodeException Si le fichier source contient des erreurs de syntax
     */
    public final FileCode importCode(javax.lang.model.element.Element classe) throws java.io.FileNotFoundException, SyntaxCodeException {
        return importCode(null, classe);
    }
    
    /**
     * Importe et analyse le code d'un fichier java
     * @param javaFile Correspond au fichier à charger
     * @return Retourne le filecode importé et analysé
     * @throws java.io.FileNotFoundException Si le fichier source n'existe pas
     * @throws SyntaxCodeException Si le fichier source contient des erreurs de syntax
     */
    public final FileCode importCode(java.io.File javaFile) throws java.io.FileNotFoundException, SyntaxCodeException {
        return importCode(null, javaFile);
    }
    
    /**
     * Importe et analyse le code d'un fichier java
     * @param creator Correspond à l'éventuel createur de sous-bloc d'élément
     * @param classe Correspond à la classe pointé par l'annotation
     * @return Retourne le filecode importé et analysé
     * @throws java.io.FileNotFoundException Si le fichier source n'existe pas
     * @throws SyntaxCodeException Si le fichier source contient des erreurs de syntax
     */
    public final FileCode importCode(CodeCreator creator, javax.lang.model.element.Element classe) throws java.io.FileNotFoundException, SyntaxCodeException {
        return importCode(creator, new java.io.File(PATH + java.io.File.separator + (classe.toString().replace(".", java.io.File.separator)) + ".java"));
    }
    
    /**
     * Importe et analyse le code d'un fichier java
     * @param creator Correspond à l'éventuel createur de sous-bloc d'élément
     * @param javaFile Correspond au fichier à charger
     * @return Retourne le filecode importé et analysé
     * @throws java.io.FileNotFoundException Si le fichier source n'existe pas
     * @throws SyntaxCodeException Si le fichier source contient des erreurs de syntax
     */
    public final FileCode importCode(CodeCreator creator, java.io.File javaFile) throws java.io.FileNotFoundException, SyntaxCodeException {
        FileCode fc = createFileCode(javaFile);
        if(creator != null)
            fc.setCreator(creator);
        fc.analyse();
        return fc;
    }
    
    /**
     * Enregistre le filecode dans le même fichier que point le fileCode
     * @param fileCode Correspond au code à enregistrer et également la cible de l'enregistrement
     * @throws java.io.IOException S'il y a la moindre exception
     */
    public final void exportCode(FileCode fileCode) throws java.io.IOException {
        fileCode.write();
    }
    
    /**
     * Enregistre le filecode dans le fichier cible
     * @param fileCode Correspond au code à enregistrer
     * @param target Correspond à la cible qui contiendra le code
     * @throws java.io.IOException S'il y a la moindre exception
     */
    public final void exportCode(FileCode fileCode, java.io.File target) throws java.io.IOException {
        fileCode.write(target);
    }
    
    
    
//ABSTRACT
    /**
     * Crée un FileCode
     * @param javaFile Correspond au fichier qui sert de base
     * @return Retourne le FileCode créé
     * @throws java.io.FileNotFoundException Si le fichier source n'existe pas
     * @throws SyntaxCodeException Si le fichier source contient des erreurs de syntax
     */
    public abstract FileCode createFileCode(java.io.File javaFile) throws java.io.FileNotFoundException, SyntaxCodeException;
    
    
    
}