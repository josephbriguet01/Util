/*
 * Copyright (C) BRIGUET Systems, Inc - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Briguet, May 2021
 */
package com.jasonpercus.util;



/**
 * Correspond à une classe représentant des représentations abstraites de chemins, de fichiers et de répertoires.
 * @see java.io.File
 * @author JasonPercus
 * @version 1.0
 */
public class File extends java.io.File {

    
    
//CONSTRUCTORS
    /**
     * Crée une nouvelle instance de File en convertissant la chaîne de chemin d'accès donnée en un chemin d'accès abstrait
     * @param pathname Correspond à une chaîne de chemin
     */
    public File(String pathname) {
        super(pathname);
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
    
    
    
}