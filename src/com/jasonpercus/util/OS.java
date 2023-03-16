/*
 * Copyright (C) JasonPercus Systems, Inc - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by JasonPercus, 05/2021
 */
package com.jasonpercus.util;



/**
 * Cette classe permet de détermine le type et nom du système d'exploitation
 * @author Briguet
 * @version 1.0
 */
@SuppressWarnings("ConvertToTryWithResources")
public class OS {
    
    
    
//ATTRIBUT PRIVATE STATIC
    /**
     * Correspond à la catégorie et/ou nom de l'os
     */
    private static final String OS = java.util.Objects.requireNonNull(System.getProperty("os.name")).toLowerCase();
    
    
    
//ATTRIBUT PUBLICS STATICS
    /**
     * Détermine si le système d'exploitation est un système Windows
     */
    public static boolean IS_WINDOWS;
    
    /**
     * Détermine si le système d'exploitation est un système Windows XP (Attention: c'est un système Windows)
     */
    public static boolean IS_WINDOWS_XP;
    
    /**
     * Détermine si le système d'exploitation est un système Windows VISTA (Attention: c'est un système Windows)
     */
    public static boolean IS_WINDOWS_VISTA;
    
    /**
     * Détermine si le système d'exploitation est un système Windows 7 (Attention: c'est un système Windows)
     */
    public static boolean IS_WINDOWS_7;
    
    /**
     * Détermine si le système d'exploitation est un système Windows 8 (Attention: c'est un système Windows)
     */
    public static boolean IS_WINDOWS_8;
    
    /**
     * Détermine si le système d'exploitation est un système Windows 8.1 (Attention: c'est un système Windows)
     */
    public static boolean IS_WINDOWS_8_1;
    
    /**
     * Détermine si le système d'exploitation est un système Windows 10 (Attention: c'est un système Windows)
     */
    public static boolean IS_WINDOWS_10;
    
    /**
     * Détermine si le système d'exploitation est un système HP Unix
     */
    public static boolean IS_HP_UNIX;
    
    /**
     * Détermine si le système d'exploitation est un système MAC OS
     */
    public static boolean IS_MAC;
    
    /**
     * Détermine si le système d'exploitation est un système ANDROID (Attention: c'est un système Linux)
     */
    public static boolean IS_ANDROID;
    
    /**
     * Détermine si le système d'exploitation est un système Linux
     */
    public static boolean IS_LINUX;
    
    /**
     * Détermine si le système d'exploitation est un système Ubuntu (Attention: c'est un système Linux)
     */
    public static boolean IS_UBUNTU;
    
    /**
     * Détermine si le système d'exploitation est un système Kali (Attention: c'est un système Linux)
     */
    public static boolean IS_KALI;
    
    /**
     * Détermine si le système d'exploitation est un système Raspbian (Attention: c'est un système Linux)
     */
    public static boolean IS_RASPBIAN;
    
    /**
     * Détermine si le système d'exploitation est un système Sun
     */
    public static boolean IS_SUN;
    
    /**
     * Détermine si le système d'exploitation est un système Solaris
     */
    public static boolean IS_SOLARIS;
    
    /**
     * Détermine si le PC est d'architecture x32 (ne fonctionne pas avec Android)
     */
    public static boolean IS_X32;
    
    /**
     * Détermine si le PC est d'architecture x64 (ne fonctionne pas avec Android)
     */
    public static boolean IS_X64;
    
    
    
//INIT
    /**
     * Initialise les variables
     */
    static{
        try {

            //ARCHITECTURE
            String arch = System.getProperty("sun.arch.data.model");
            if(arch != null) {
                if (arch.equals("32")) IS_X32 = true;
                if (arch.equals("64")) IS_X64 = true;
            }

            //DETECT WINDOWS
            if(OS.contains("win")){
                IS_WINDOWS = true;
                if (OS.contains("windows") && OS.contains("xp"))
                    IS_WINDOWS_XP = true;
                else if (OS.contains("windows") && OS.contains("vista"))
                    IS_WINDOWS_VISTA = true;
                else if (OS.contains("windows") && OS.contains("7"))
                    IS_WINDOWS_7 = true;
                else if (OS.contains("windows") && OS.contains("8"))
                    IS_WINDOWS_8 = true;
                else if (OS.contains("windows") && OS.contains("8.1"))
                    IS_WINDOWS_8_1 = true;
                else if (OS.contains("windows") && OS.contains("10"))
                    IS_WINDOWS_10 = true;
            }

            //DETECT HP UNIX
            IS_HP_UNIX = (OS.contains("hpux"));

            //DETECT MAC OS
            IS_MAC = (OS.contains("mac"));

            //DETECT LINUX
            if(OS.contains("nix") || OS.contains("nux") || OS.indexOf("aix") > 0){
                IS_LINUX = true;
                if(System.getProperty("java.runtime.name").toLowerCase().contains("android")){
                    IS_ANDROID = true;
                }else{
                    java.io.File file = new java.io.File("/etc");
                    @SuppressWarnings("Convert2Lambda")
                    java.io.File[] list = file.listFiles(new java.io.FilenameFilter() {
                        @Override
                        public boolean accept(java.io.File dir, String name) {
                            return name.endsWith("-release");
                        }
                    });
                    String distrib_id = null;
                    String line;
                    for (java.io.File f : list) {
                        java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader(f));
                        while((line = br.readLine()) != null){
                            if(line.contains("ID=")){
                                distrib_id = line.replaceAll("^DISTRIB_ID=", "").replaceAll("^ID=", "").toLowerCase();
                                break;
                            }
                        }
                        br.close();
                        if(distrib_id != null) break;
                    }
                    if(distrib_id != null){
                        if(distrib_id.contains("ubuntu")){
                            IS_UBUNTU = true;
                        }else if(distrib_id.contains("kali")){
                            IS_KALI = true;
                        }else if(distrib_id.contains("raspbian")){
                            IS_RASPBIAN = true;
                        }
                    }
                }
            }

            //DETECT SOLARIS
            IS_SOLARIS = (OS.contains("solaris"));

            //DETECT SUN
            IS_SUN = (OS.contains("sunos"));
            
        } catch (Exception ex) {
            //ex.printStackTrace();
        }
    }
    
    
    
//CONSTRUCTOR
    /**
     * Constructeur par défaut
     * @deprecated Ne pas utiliser
     */
    @Deprecated
    private OS() {
    }
    
    
    
}