/*
 * Copyright (C) JasonPercus Systems, Inc - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by JasonPercus, 10/2018
 */
package com.jasonpercus.util;



/**
 * Cette classe permet de renvoyer des objets (par le constructeur par défaut) instancié par un plugin extérieur au programme
 * @author JasonPercus
 * @version 1.0
 */
public class LoaderPlugin {
    
    
    
    // <editor-fold defaultstate="collapsed" desc="ATTRIBUTS">
    /**
     * Correspond au dossier où se trouvent les plugins
     */
    private final java.io.File folderPlugin;
    /**
     * Correspond aux plugins contenus dans le dossier de plugins
     */
    private final java.io.File[] filesPlugin;
    /**
     * Correpond à la position du plugin
     */
    private int position = 0;
    // </editor-fold>

    
    
    // <editor-fold defaultstate="collapsed" desc="CONSTRUCTORS">
    /**
     * Charge un dossier de plugins (sans regard sur les extensions)
     * @param folderPlugin Correspond au dossier des plugins
     */
    public LoaderPlugin(java.io.File folderPlugin) {
        this.folderPlugin = folderPlugin;
        this.filesPlugin = this.folderPlugin.listFiles();
    }

    /**
     * Charge un dossier de plugins (avec regard sur les extensions)
     * @param folderPlugin Correspond au dossier des plugins
     * @param extension Correspond à l'extension autorisée des plugins
     */
    public LoaderPlugin(java.io.File folderPlugin, String extension) {
        this.folderPlugin = folderPlugin;
        this.filesPlugin = this.folderPlugin.listFiles((java.io.File file, String fileName) -> (fileName.lastIndexOf('.') > 0 && fileName.substring(fileName.lastIndexOf('.')+1).equals(extension)));
    }
    // </editor-fold>
    
    
    
    // <editor-fold defaultstate="collapsed" desc="METHODES PUBLICS">
    /**
     * Change la position de récupération d'un plugin
     * @param position Correspond à la nouvelle position d'un plugin
     */
    public void setPosition(int position){
        if(position<-1)
            throw new java.lang.ArrayIndexOutOfBoundsException("position ("+position+") is negative");
        else if(position >= filesPlugin.length)
            throw new java.lang.ArrayIndexOutOfBoundsException("position ("+position+") > size()");
        else
            this.position = position;
    }
    
    /**
     * Renvoie le nombre de plugins du dossier de plugins
     * @return Retourne le nombre de plugins du dossier de plugins
     */
    public int size(){
        return filesPlugin.length;
    }
    
    /**
     * Renvoie le plugin pointé par setPosition()
     * @return Retourne le plugin pointé par setPosition()
     */
    public java.io.File getFilePlugin(){
        if(position<-1)
            throw new java.lang.ArrayIndexOutOfBoundsException("position ("+position+") is negative");
        else if(position >= filesPlugin.length)
            throw new java.lang.ArrayIndexOutOfBoundsException("position ("+position+") > size()");
        else{
            return filesPlugin[position];
        }
    }
    
    /**
     * Renvoie la liste des objets instanciés du plugin pointé par setPosition()
     * @param classOrInterfaceSearched Seules les classes qui étendent et implémentes classOrInterfaceSearched seront instancié par le constructeur par défaut
     * @return Retournes un tableau d'objets instanciés du pugin pointé par setPosition()
     */
    public Object[] getObject(Class... classOrInterfaceSearched){
        if(position<-1)
            throw new java.lang.ArrayIndexOutOfBoundsException("position ("+position+") is negative");
        else if(position >= filesPlugin.length)
            throw new java.lang.ArrayIndexOutOfBoundsException("position ("+position+") > size()");
        else{
            try {
                java.util.List<Object> list = new java.util.ArrayList<>();
                java.net.URL u = filesPlugin[position].toURL();
                java.net.URLClassLoader loader = new java.net.URLClassLoader(new java.net.URL[]{u});
                java.util.jar.JarFile jar = new java.util.jar.JarFile(filesPlugin[position].getAbsolutePath());
                //On récupére le contenu du jar
                java.util.Enumeration enumeration = jar.entries();
                while (enumeration.hasMoreElements()) {
                    String tmp = enumeration.nextElement().toString();
                    //On vérifie que le fichier courant est un .class (et pas un fichier d'informations du jar)
                    if (tmp.length() > 6 && tmp.substring(tmp.length() - 6).compareTo(".class") == 0) {
                        tmp = tmp.substring(0, tmp.length() - 6);
                        tmp = tmp.replaceAll("/", ".");
                        Class tmpClass = Class.forName(tmp , true, loader);
                        if (isExtendsOrImplements(classOrInterfaceSearched, tmpClass)) {
                            list.add(tmpClass.newInstance());
                        }
                    }
                }
                
                Object[] objs = new Object[list.size()];
                
                for(int i=0;i<list.size();i++)
                    objs[i] = list.get(i);
                
                return objs;
            } catch (java.net.MalformedURLException ex) {
                java.util.logging.Logger.getLogger(LoaderPlugin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            } catch (java.io.IOException | ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
                java.util.logging.Logger.getLogger(LoaderPlugin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }
        }
        return null;
    }
    // </editor-fold>
    
    
    
    // <editor-fold defaultstate="collapsed" desc="METHODES PRIVATES">
    /**
     * Vérifie que subClass étend et/ou implémente toutes la liste de superClass
     * @param superClass Correspond à la liste de classes de référence
     * @param subClass Correspond à la classe dont ont veut vérifier qu'elle étend/implémente bien la liste superClass
     * @return Retourne true si subClass étend/implémentes toutes les classes de superClass, sinon false
     */
    private boolean isExtendsOrImplements(Class[] superClass, Class subClass){
        for (Class superClas : superClass) {
            if (!((superClas.isAssignableFrom(subClass)) && (!superClas.getName().equals(subClass.getName()))))
                return false;
        }
        return true;
    }
    // </editor-fold>
    
    
    
}