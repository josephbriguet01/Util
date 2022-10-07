/*
 * Copyright (C) JasonPercus Systems, Inc - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by JasonPercus, 08/2022
 */
package com.jasonpercus.util;



/**
 * Cette classe permet de copier/coller/... du texte dans le presse papier
 * @author JasonPercus
 * @version 1.0
 */
public class Clipboard {
    
    
    
//CONSTRUCTOR
    /**
     * Correspond au constructeur par défaut
     */
    private Clipboard(){
        
    }
    
    
    
//METHODES PUBLICS STATICS
    /**
     * Copie du texte dans le presse papier
     * @param text Correspond au texte à copier dans le presse papier
     */
    public static void copy(String text)
    {
        java.awt.datatransfer.Clipboard clipboard = getSystemClipboard();
        clipboard.setContents(new java.awt.datatransfer.StringSelection(text), null);
    }

    /**
     * Colle le texte du presse papier
     * @throws java.awt.AWTException S'il y a une erreur lors du collage
     */
    public static void paste() throws java.awt.AWTException
    {
        java.awt.Robot robot = new java.awt.Robot();

        int controlKey = OS.IS_MAC ? java.awt.event.KeyEvent.VK_META : java.awt.event.KeyEvent.VK_CONTROL;
        
        robot.keyPress(controlKey);
        robot.keyPress(java.awt.event.KeyEvent.VK_V);
        robot.keyRelease(controlKey);
        robot.keyRelease(java.awt.event.KeyEvent.VK_V);
    }

    /**
     * Récupère le contenu du presse papier
     * @return Retourne le contenu du presse papier ou null s'il ne s'agit pas du texte d'enregistré ou s'il n'y a rien dans le presse papier
     */
    @SuppressWarnings("UseSpecificCatch")
    public static String get()
    {
        java.awt.datatransfer.Clipboard systemClipboard = getSystemClipboard();
        java.awt.datatransfer.DataFlavor dataFlavor     = java.awt.datatransfer.DataFlavor.stringFlavor;

        if (systemClipboard.isDataFlavorAvailable(dataFlavor))
        {
            try {
                return (String) systemClipboard.getData(dataFlavor);
            } catch (Exception ex) {
                return null;
            }
        }

        return null;
    }

    
    
//METHODE PRIVATE STATIC
    /**
     * Renvoie le presse papier AWT
     * @return Retourne le presse papier AWT
     */
    private static java.awt.datatransfer.Clipboard getSystemClipboard()
    {
        java.awt.Toolkit defaultToolkit = java.awt.Toolkit.getDefaultToolkit();
        return defaultToolkit.getSystemClipboard();
    }
    
    
    
}