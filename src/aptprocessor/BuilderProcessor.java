/*
 * Copyright (C) JasonPercus Systems, Inc - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by JasonPercus, 06/2021
 */
package aptprocessor;



import com.jasonpercus.util.Builder;
import com.jasonpercus.util.Builder.Mode;
import java.io.File;



/**
 * Cette classe permet au compilateur de générer un Builder pour une classe précise si l'annotation {@link com.jasonpercus.util.Builder} est utilisé pour annoté cette classe
 * @see com.jasonpercus.util.Builder
 * @author JasonPercus
 * @version 1.0
 */
@javax.annotation.processing.SupportedAnnotationTypes(value = {"com.jasonpercus.util.Builder"})
@javax.annotation.processing.SupportedSourceVersion(javax.lang.model.SourceVersion.RELEASE_8)
public class BuilderProcessor extends javax.annotation.processing.AbstractProcessor {

    
    
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
        if(new File(DEFAULT_PATH_NORMAL).exists()){
            PATH = DEFAULT_PATH_NORMAL;
            ANDROID_PROJECT = false;
        } else if(new File(DEFAULT_PATH_ANDROID).exists()){
            PATH = DEFAULT_PATH_ANDROID;
            ANDROID_PROJECT = true;
        } else{
            PATH = DEFAULT_PATH_NORMAL;
            ANDROID_PROJECT = false;
            throw new UnknownError("Your Java project type is unknown ! Therefore, the source analysis cannot be performed.");
        }
    }
    
    
    
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
                
                java.util.Set<javax.lang.model.element.Modifier> modifiers = classe.getModifiers();
                if(modifiers.contains(javax.lang.model.element.Modifier.ABSTRACT)){
                    processingEnv.getMessager().printMessage(javax.tools.Diagnostic.Kind.ERROR, String.format("%s class cannot be abstract if you want to use the @Builder annotation !", classe.getSimpleName()), null);
                }
                if(modifiers.contains(javax.lang.model.element.Modifier.DEFAULT)){
                    processingEnv.getMessager().printMessage(javax.tools.Diagnostic.Kind.ERROR, String.format("%s class cannot contains default modifier if you want to use the @Builder annotation !", classe.getSimpleName()), null);
                }
                if(modifiers.contains(javax.lang.model.element.Modifier.NATIVE)){
                    processingEnv.getMessager().printMessage(javax.tools.Diagnostic.Kind.ERROR, String.format("%s class cannot contains native modifier if you want to use the @Builder annotation !", classe.getSimpleName()), null);
                }
                if(modifiers.contains(javax.lang.model.element.Modifier.PRIVATE)){
                    processingEnv.getMessager().printMessage(javax.tools.Diagnostic.Kind.ERROR, String.format("%s class cannot be private if you want to use the @Builder annotation !", classe.getSimpleName()), null);
                }
                if(modifiers.contains(javax.lang.model.element.Modifier.STRICTFP)){
                    processingEnv.getMessager().printMessage(javax.tools.Diagnostic.Kind.ERROR, String.format("%s class cannot contains strictfp modifier if you want to use the @Builder annotation !", classe.getSimpleName()), null);
                }
                if(modifiers.contains(javax.lang.model.element.Modifier.SYNCHRONIZED)){
                    processingEnv.getMessager().printMessage(javax.tools.Diagnostic.Kind.ERROR, String.format("%s class cannot be synchronized if you want to use the @Builder annotation !", classe.getSimpleName()), null);
                }
                if(modifiers.contains(javax.lang.model.element.Modifier.TRANSIENT)){
                    processingEnv.getMessager().printMessage(javax.tools.Diagnostic.Kind.ERROR, String.format("%s class cannot contains transient modifier if you want to use the @Builder annotation !", classe.getSimpleName()), null);
                }
                if(modifiers.contains(javax.lang.model.element.Modifier.VOLATILE)){
                    processingEnv.getMessager().printMessage(javax.tools.Diagnostic.Kind.ERROR, String.format("%s class cannot be volatile if you want to use the @Builder annotation !", classe.getSimpleName()), null);
                }
                if(!modifiers.contains(javax.lang.model.element.Modifier.ABSTRACT) && 
                        !modifiers.contains(javax.lang.model.element.Modifier.DEFAULT) && 
                        !modifiers.contains(javax.lang.model.element.Modifier.NATIVE) && 
                        !modifiers.contains(javax.lang.model.element.Modifier.PRIVATE) && 
                        !modifiers.contains(javax.lang.model.element.Modifier.STRICTFP) && 
                        !modifiers.contains(javax.lang.model.element.Modifier.SYNCHRONIZED) &&
                        !modifiers.contains(javax.lang.model.element.Modifier.TRANSIENT) && 
                        !modifiers.contains(javax.lang.model.element.Modifier.VOLATILE)){
                    java.util.List<? extends javax.lang.model.element.Element> encloseds = classe.getEnclosedElements();

                    Builder builder = classe.getAnnotation(Builder.class);
                    Mode mode = builder.mode();
                    String code = getCode(classe);
                    java.util.List<String> methodsWrited = new java.util.ArrayList<>();
                    boolean hasConstructor = false;
                    for(javax.lang.model.element.Element enclosed : encloseds){
                        if(enclosed.getKind() == javax.lang.model.element.ElementKind.CONSTRUCTOR){
                            modifiers = enclosed.getModifiers();
                            if(modifiers.contains(javax.lang.model.element.Modifier.PUBLIC) || modifiers.contains(javax.lang.model.element.Modifier.PROTECTED))
                                hasConstructor = true;
                        }
                        if(enclosed.getKind() == javax.lang.model.element.ElementKind.METHOD){
                            modifiers = enclosed.getModifiers();
                            if(!modifiers.contains(javax.lang.model.element.Modifier.DEFAULT) && 
                                    !modifiers.contains(javax.lang.model.element.Modifier.ABSTRACT) && 
                                    !modifiers.contains(javax.lang.model.element.Modifier.NATIVE) && 
                                    !modifiers.contains(javax.lang.model.element.Modifier.PRIVATE) && 
                                    !modifiers.contains(javax.lang.model.element.Modifier.STATIC) &&
                                    !modifiers.contains(javax.lang.model.element.Modifier.STRICTFP) && 
                                    !modifiers.contains(javax.lang.model.element.Modifier.TRANSIENT) && 
                                    !modifiers.contains(javax.lang.model.element.Modifier.VOLATILE)){

                                javax.lang.model.type.ExecutableType typeMirror = (javax.lang.model.type.ExecutableType) enclosed.asType();
                                if(typeMirror.getReturnType().getKind() == javax.lang.model.type.TypeKind.VOID){
                                    String signature = generateMethod(classe, modifiers, typeMirror.getParameterTypes(), enclosed, code);
                                    methodsWrited.add(signature);
                                }

                            }
                        }
                    }
                    
                    java.io.File javaFile = new java.io.File(PATH + java.io.File.separator + ((classe.toString()+"Builder").replace(".", java.io.File.separator)) + ".java");
                    if(mode == Mode.ALWAYS_OVERWRITE || mode == Mode.IF_NOT_EXISTS){
                        if(!methodsWrited.isEmpty()){
                            if(hasConstructor){
                                if(mode == Mode.IF_NOT_EXISTS && javaFile.exists()){
                                    processingEnv.getMessager().printMessage(javax.tools.Diagnostic.Kind.NOTE, String.format("%s file already exists !", classe.toString()+"Builder.java"), null);
                                }else{
                                    String generatedCode = generateFile(classe, methodsWrited);
                                    try {
                                        boolean exists = javaFile.exists();
                                        try (java.io.BufferedWriter bw = new java.io.BufferedWriter(new java.io.FileWriter(javaFile))) {
                                            bw.write(generatedCode);
                                            bw.flush();
                                        }
                                        if(exists)
                                            processingEnv.getMessager().printMessage(javax.tools.Diagnostic.Kind.NOTE, String.format("%s file has been overwritten !", classe.toString()+"Builder.java"), null);
                                        else
                                            processingEnv.getMessager().printMessage(javax.tools.Diagnostic.Kind.NOTE, String.format("%s file has been created !", classe.toString()+"Builder.java"), null);
                                    } catch (java.io.IOException ex) {
                                        java.util.logging.Logger.getLogger(BuilderProcessor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                                    }
                                }
                            }else{
                                processingEnv.getMessager().printMessage(javax.tools.Diagnostic.Kind.ERROR, String.format("%s class does not contain a default constructor: [public | protected] %s(){...}", classe.getSimpleName(), classe.getSimpleName()), null);
                            }
                        }else{
                            processingEnv.getMessager().printMessage(javax.tools.Diagnostic.Kind.WARNING, String.format("%s class does not contain any method used to generate the %s file", classe.getSimpleName(), classe.toString()+"Builder.java"), null);
                        }
                    }else{
                        if(javaFile.exists()){
                            javaFile.delete();
                            processingEnv.getMessager().printMessage(javax.tools.Diagnostic.Kind.NOTE, String.format("%s file deleted !", classe.toString()+"Builder.java"), null);
                        }else{
                            processingEnv.getMessager().printMessage(javax.tools.Diagnostic.Kind.NOTE, String.format("%s not exists !", classe.toString()+"Builder.java"), null);
                        }
                    }
                }
            }
        }
        return false;
    }
    
    
    
//METHODES PRIVATES
    /**
     * Génère le code d'une méthode d'une classe Builder
     * @param classe Correspond à la classe qui possède l'annotation {@link com.jasonpercus.util.Builder}
     * @param modifiers Correspond au modifiers de la méthode (private, public, static...) qui sert de modèle
     * @param parameters Correspond aux paramètres de la méthode qui sert de modèle
     * @param method Correspond à la méthode qui sert de modèle
     * @param code Correspond au code source de l'utilisateur venant du fichier source java qui va générer le Builder
     * @return Retourne le code généré de la méthode
     */
    private String generateMethod(javax.lang.model.element.Element classe, java.util.Set<javax.lang.model.element.Modifier> modifiers, java.util.List<? extends javax.lang.model.type.TypeMirror> parameters, javax.lang.model.element.Element method, String code){
        String[] namesParameters = namesParameters(code, method);
        String signature = "";
        for (javax.lang.model.element.Modifier modifier : modifiers) {
            if (!signature.isEmpty()) {
                signature += " ";
            }
            signature += modifier.toString();
        }

        if (!signature.isEmpty()) {
            signature += " ";
        }
        signature += classe.getSimpleName()+"Builder";

        if (!signature.isEmpty()) {
            signature += " ";
        }
        signature += method.getSimpleName().toString();

        signature += "(";

        for (int i = 0; i < parameters.size(); i++) {
            if (i > 0) {
                signature += ", ";
            }
            signature += parameters.get(i) + " " + namesParameters[i];
        }

        signature += ") {\n        obj." + method.getSimpleName().toString() + "(";

        for (int i = 0; i < parameters.size(); i++) {
            if (i > 0) {
                signature += ", ";
            }
            signature += namesParameters[i];
        }
        signature += ");\n        return this;\n    }";

        return "    " + signature;
    }
    
    /**
     * Génère la chaîne représentant le fichier java qui va être généré
     * @param classe Correspond à la classe qui possède l'annotation {@link com.jasonpercus.util.Builder}
     * @param methods Correspond à la liste des chaînes représentant chaque méthode qui seront ajouté à la chaîne
     * @return Retourne la chaîne généré représentant le fichier java
     */
    private String generateFile(javax.lang.model.element.Element classe, java.util.List<String> methods){
        java.util.Date date = new java.util.Date();
        String file = "package "+classe.getEnclosingElement()+";\n\n\n\n";
        if(!ANDROID_PROJECT){
            file += "@javax.annotation.Generated(\n";
            file += "    value    = \"com.jasonpercus.util.Builder\",\n";
            file += "    comments = \"Correspond au builder de la classe "+classe+"\",\n";
            file += "    date     = \""+new java.text.SimpleDateFormat("dd MMMM yyyy").format(date)+"\"\n";
            file += ")\n";
        }else{
            file += "/*Generated(\n";
            file += "    value    = \"com.jasonpercus.util.Builder\",\n";
            file += "    comments = \"Correspond au builder de la classe "+classe+"\",\n";
            file += "    date     = \""+new java.text.SimpleDateFormat("dd MMMM yyyy").format(date)+"\"\n";
            file += ")*/\n";
        }
        file += "public class "+classe.getSimpleName()+"Builder {\n\n";
        file += "    private final "+classe.getSimpleName()+" obj = new "+classe.getSimpleName()+"();\n\n";
        file += "    public "+classe.getSimpleName()+" build() {\n";
        file += "        return obj;\n";
        file += "    }";
        for(String method : methods){
            file += "\n\n"+method;
        }
        file += "\n}";
        return file;
    }
    
    /**
     * Renvoie la liste des noms des paramètres d'une méthode
     * @param code Correspond au code source de l'utilisateur venant du fichier source java qui va générer le Builder
     * @param method Correspond à une méthode récupérée de la classe qui possède l'annotation {@link com.jasonpercus.util.Builder}
     * @return Retourne le nom des paramètres d'une méthode (exemple: si le code source contient les paramètres (String name, int age), la fonction renverra {"name", "age"}
     */
    private String[] namesParameters(String code, javax.lang.model.element.Element method){
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(String.format("void.*(%s).*\\(.*\\).*\\{", method.getSimpleName().toString()));
        java.util.regex.Matcher m = p.matcher(code);
        while(m.find()) {
            int count = m.groupCount();
            for(int i=0;i<count;i++){
                String group = m.group(i);
                group = group.substring(group.indexOf("(")+1, group.lastIndexOf(")"));
                String[] params = group.split(" *, *");
                javax.lang.model.type.ExecutableType typeMirror = (javax.lang.model.type.ExecutableType) method.asType();
                if(params.length != typeMirror.getParameterTypes().size()) 
                    continue;
                String[] names = new String[params.length];
                for(int j=0;j<params.length;j++){
                    names[j] = name(params[j]);
                }
                return names;
            }
        }
        return new String[0];
    }
    
    /**
     * Renvoie le nom d'un paramètre
     * @param param Correspond au paramètre (exemple: (String name))
     * @return Retourne le nom du paramètre (exemple: "name")
     */
    private String name(String param){
        java.util.regex.Pattern p = java.util.regex.Pattern.compile("^\\w* *(\\w*)$");
        java.util.regex.Matcher m = p.matcher(param);
        while(m.find()) {
            return m.group(1);
        }
        return null;
    }
    
    /**
     * Récupère le code source de la classe modèle
     * @param classe Correspond à la classe qui possède l'annotation {@link com.jasonpercus.util.Builder}
     * @return Retourne le code source de la classe modèle
     */
    private String getCode(javax.lang.model.element.Element classe){
        java.io.File javaFile = new java.io.File(PATH + java.io.File.separator + (classe.toString().replace(".", java.io.File.separator)) + ".java");
        String code = "";
        try {
            try (java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader(javaFile))) {
                String line;
                while ((line = br.readLine()) != null) {
                    if (!code.isEmpty()) {
                        code += "\n";
                    }
                    code += line;
                }
            }
        } catch (java.io.FileNotFoundException ex) {
            java.util.logging.Logger.getLogger(BuilderProcessor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (java.io.IOException ex) {
            java.util.logging.Logger.getLogger(BuilderProcessor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        return code;
    }
    
    
    
}