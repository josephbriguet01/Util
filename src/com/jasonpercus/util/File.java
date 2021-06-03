/*
 * Copyright (C) JasonPercus Systems, Inc - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by JasonPercus, May 2021
 */
package com.jasonpercus.util;



/**
 * Correspond à une classe représentant des représentations abstraites de chemins, de fichiers et de répertoires.
 * @see java.io.File
 * @author JasonPercus
 * @version 1.0
 */
public class File extends java.io.File {
    
    
    
    // <editor-fold defaultstate="collapsed" desc="SERIAL_VERSION_UID">
    /**
     * Correspond au numéro de série qui identifie le type de dé/sérialization utilisé pour l'objet
     */
    private static final long serialVersionUID = 1L;
    // </editor-fold>

    
    
//CONSTRUCTORS
    /**
     * Crée une nouvelle instance de File en convertissant la chaîne de chemin d'accès donnée en un chemin d'accès abstrait
     * @param pathname Correspond à une chaîne de chemin
     */
    public File(String pathname) {
        super(pathname);
    }
    
    /**
     * Crée une nouvelle instance de File en convertissant le fichier donné en un chemin d'accès abstrait
     * @param file Correspond au fichier à convertir
     */
    public File(java.io.File file){
        super(path(file));
    }

    /**
     * Crée une nouvelle instance de File à partir d'une chaîne de chemin parent et d'une chaîne de chemin enfant
     * @param parent Correspond à la chaîne de chemin d'accès parent
     * @param child Correspond à la chaîne de chemin d'accès enfant
     */
    public File(String parent, String child) {
        super(parent, child);
    }

    /**
     * Crée une nouvelle instance de fichier à partir d'un chemin d'accès abstrait parent et d'une chaîne de chemin d'accès enfant
     * @param parent Correspond au chemin d'accès abstrait parent
     * @param child Correspond à la chaîne de chemin d'accès enfant
     */
    public File(java.io.File parent, String child) {
        super(parent, child);
    }

    /**
     * Crée une nouvelle instance de File en convertissant le fichier donné: URI en un chemin abstrait
     * @param uri Correspond à une URI hiérarchique absolu avec un schéma égal à "fichier", un composant de chemin non vide et des composants d'autorité, de requête et de fragment non définis
     */
    public File(java.net.URI uri) {
        super(uri);
    }
    
    
    
//METHODE PUBLIC
    /**
     * Renvoie l'extension du fichier
     * @return Retourne l'extension du fichier sans son point
     * @throws java.io.FileNotFoundException Si le fichier n'existe pas
     */
    public String getExtension() throws java.io.FileNotFoundException {
        if (this.exists()){
            if (this.isDirectory()) {
                return "";
            } else {
                String fileName = this.getName();
                int i = fileName.lastIndexOf('.');
                if (i > -1 )
                    return fileName.substring(i).replace(".", "");
                else
                    return "";
            }
        }else throw new java.io.FileNotFoundException(this+" not exists !");
    }

    /**
     * Renvoie une liste de fichiers filtrés par leur extension
     * @param extensions Correspond à la liste des extensions qui seront autorisés à apparaître dans la liste filtré de sortie
     * @return Retourne une liste de fichiers filtrés par leur extension
     */
    public java.io.File[] listFilesByExtension(String...extensions) {
        java.io.File[] files = this.listFiles((java.io.File pathname) -> {
            File f = castTo(pathname);
            try {
                String ext = f.getExtension();
                
                if(extensions == null) return false;
                if(extensions.length == 0) return false;
                
                for(String extension : extensions){
                    if(extension != null && extension.equals(ext)){
                        return true;
                    }
                }
                return false;
                
            } catch (java.io.FileNotFoundException ex) {
                return false;
            }
        });
        return files;
    }

    /**
     * Renvoie une liste de noms de fichiers filtrés par leur extension
     * @param extensions Correspond à la liste des extensions qui seront autorisés à apparaître dans la liste filtré de sortie
     * @return Retourne une liste de noms de fichiers filtrés par leur extension
     */
    public String[] listByExtension(String...extensions) {
        java.io.File[] files = listFilesByExtension(extensions);
        if(files == null) return null;
        String[] strs = new String[files.length];
        for(int i=0;i<files.length;i++){
            strs[i] = files[i].getName();
        }
        return strs;
    }
    
    
    
//METHODE PUBLIC STATIC
    /**
     * Converti un {@link java.io.File} en {@link File} qui est la nouvelle version
     * @param file Correspond au fichier à convertir
     * @return Retourne la nouvelle instance de fichier
     */
    public static File castTo(java.io.File file){
        try {
            return new File(file.getCanonicalFile().getAbsolutePath());
        } catch (java.io.IOException ex) {
            return new File(file.getAbsolutePath());
        }
    }
    
    
    
//METHODE PRIVATE STATIC
    /**
     * Renvoie le chemin du fichier
     * @param file Correspond au fichier dont on veut récupérer le chemin
     * @return Retourne le chemin du fichier
     */
    private static String path(java.io.File file){
        try {
            return file.getCanonicalFile().getAbsolutePath();
        } catch (java.io.IOException ex) {
            return file.getAbsolutePath();
        }
    }
    
    
    
}