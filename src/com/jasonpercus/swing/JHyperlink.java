/*
 * Copyright (C) JasonPercus Systems, Inc - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by JasonPercus, 07/2022
 */
package com.jasonpercus.swing;



/**
 * Cette classe représente un {@link javax.swing.JLabel} qui a pour but de jouer le rôle de lien hyper-texte
 * @author JasonPercus
 * @version 1.0
 */
public class JHyperlink extends javax.swing.JLabel {
    
    
    
//ATTRIBUTS
    /**
     * Correspond au texte qui s'affiche à la place de l'{@link #url}
     */
    private String replaceText;
    
    /**
     * Correspond à l'url pointée par le lien hyper-texte
     */
    private String url;

    
    
//CONSTRUCTORS
    /**
     * Crée un objet {@link JHyperlink}
     */
    public JHyperlink() {
        super("<html><a href=\"#\">jHyperlink</a></html>");
        this.replaceText = "jHyperlink";
        init();
    }

    /**
     * Crée un objet {@link JHyperlink}
     * @param url Correspond à l'url pointée par le lien hyper-texte
     */
    public JHyperlink(String url) {
        super("<html><a href=\"#\">" + url + "</a></html>");
        this.url = url;
        init();
    }

    /**
     * Crée un objet {@link JHyperlink}
     * @param url Correspond à l'url pointée par le lien hyper-texte
     * @param replaceText Correspond au texte qui s'affiche à la place de l'url
     */
    public JHyperlink(String url, String replaceText) {
        super("<html><a href=\"#\">" + replaceText + "</a></html>");
        this.replaceText = replaceText;
        this.url         = url;
        init();
    }

    
    
//GETTERS & SETTERS
    /**
     * Renvoie le texte qui s'affiche à la place de l'url
     * @return Retourne le texte qui s'affiche à la place de l'url
     */
    public String getReplaceText() {
        return replaceText;
    }
    
    /**
     * Modifie le texte qui s'affiche à la place de l'url
     * @param replaceText Correspond au nouveau texte qui s'affiche à la place de l'url
     */
    public void setReplaceText(String replaceText) {
        this.replaceText = replaceText;
        if(this.replaceText.isEmpty())
            this.replaceText = null;
        changeText();
    }

    /**
     * Renvoie l'url pointée par le lien hyper-texte
     * @return Retourne l'url pointée par le lien hyper-texte
     */
    public String getUrl() {
        return url;
    }

    /**
     * Modifie l'url pointée par le lien hyper-texte
     * @param url Correspond à la nouvelle url pointée par le lien hyper-texte
     */
    public void setUrl(String url) {
        this.url = url;
        if(this.url.isEmpty())
            this.url = null;
        changeText();
    }
    
    
    
//METHODES PRIVATES
    /**
     * Modifie le texte du composant en fonction des attributs {@link #replaceText} et {@link #url}
     */
    private void changeText(){
        if(this.replaceText != null)
            super.setText("<html><a href=\"#\">" + replaceText + "</a></html>");
        else if (this.url != null)
            super.setText("<html><a href=\"#\">" + url + "</a></html>");
        else 
            super.setText("");
    }
    
    /**
     * Initialisise le fonctionnement du lien hyper-texte
     */
    private void init(){
        this.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.HAND_CURSOR));
        this.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if(JHyperlink.this.url != null){
                    try {
                        java.awt.Desktop.getDesktop().browse(new java.net.URI(JHyperlink.this.url));
                    } catch (java.net.URISyntaxException | java.io.IOException ex) {
                        java.util.logging.Logger.getLogger(JHyperlink.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                    }
                }
            }
        });
    }
    
    
    
}