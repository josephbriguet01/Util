/*
 * Copyright (C) JasonPercus Systems, Inc - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by JasonPercus, 06/2021
 */
package com.jasonpercus.util.code;



/**
 * Cette classe représente un bout de code
 * @author JasonPercus
 * @version 1.0
 */
public abstract class Code implements java.io.Serializable, Comparable<Code>, Cloneable {

    
    
//ATTRIBUTS
    /**
     * Correspond au nom du bout de code. Exemple, si le code est une méthode, name sera égale au nom de la méthode, si le code est énumération, name sera égale au nom de l'énumération...
     */
    private String name;
    
    /**
     * Correspond au créateur de bout de code
     */
    private transient CodeCreator creator;
    
    /**
     * Détermine si le bout de code est en commentaire
     */
    private boolean asComment;
    
    /**
     * Correspond au parent de cet élément
     */
    private Code parent;
    
    
    
//CONSTRUCTORS
    /**
     * Crée un bout de code par défaut
     * @deprecated <div style="color: #D45B5B; font-style: italic">Ne pas utiliser - Il n'a de l'intérêt que pour la dé/sérialization</div>
     */
    public Code() {
    }

    /**
     * Crée un bout de code
     * @param creator Correspond au créateur de bout de code
     * @param name Correspond au nom du bout de code
     * @param parent Correspond au parent de cet élément
     */
    protected Code(CodeCreator creator, String name, Code parent) {
        this.creator    = creator;
        this.name       = name;
        this.parent     = parent;
    }


    
//GETTERS & SETTERS
    /**
     * Renvoie le créateur de bout de code
     * @return Retourne le créateur de bout de code
     */
    public synchronized final CodeCreator getCreator() {
        return creator;
    }
    
    /**
     * Modifie le créateur de bout de code
     * @param creator Correspond au nouveau créateur de bout de code
     */
    public synchronized final void setCreator(CodeCreator creator) {
        this.creator = creator;
    }

    /**
     * Renvoie le nom du bout de code
     * @return Retourne le nom du bout de code
     */
    public synchronized final String getName() {
        return name;
    }

    /**
     * Modifie le nom du bout de code
     * @param name Correspond au nouveau nom du bout de code
     */
    public synchronized final void setName(String name) {
        this.name = name;
    }
    
    /**
     * Détermine si l'élément est un élément commenté
     * @return Retourne true s'il est commenté, sinon false
     */
    public synchronized final boolean asComment(){
        return this.asComment;
    }
    
    /**
     * Modifie si oui ou non l'élément doit être commenté ou pas
     * @param asComment True s'il doit l'être
     */
    public synchronized final void asComment(boolean asComment){
        this.asComment = asComment;
    }
    
    /**
     * Renvoie le bloc parent de ce bout de code
     * @return Retourne le bloc parent de ce bout de code
     */
    public synchronized final Code getParent() {
        return parent;
    }

    /**
     * Modifie le bloc parent de ce bout de code
     * @param parent Correspond au nouveau bloc parent de ce bout de code
     */
    public synchronized void setParent(Code parent) {
        this.parent = parent;
    }
    
    
    
//METHODES PUBLICS
    /**
     * Renvoie le nombre de parents au dessus de ce bout de code
     * @return Retourne le nombre de parents au dessus de ce bout de code, ou 0 s'il n'a pas de parent
     */
    public synchronized final int countParent(){
        Code dc = this;
        int count = 0;
        while(dc.getParent() != null){
            count++;
            dc = dc.getParent();
        }
        return count;
    }
    
    /**
     * Renvoie si oui ou non ce bout de code à un parent
     * @return Retourne si oui ou non ce bout de code à un parent
     */
    public synchronized final boolean hasParent(){
        return parent != null;
    }
    
    /**
     * Renvoie le hash du bout de code renvoyé par la méthode {@link #toString()}
     * @return Retourne le hash du bout de code renvoyé par la méthode {@link #toString()}
     */
    public synchronized final String hash(){
        try {
            return md5(toString());
        } catch (java.security.NoSuchAlgorithmException ex) {
            java.util.logging.Logger.getLogger(Code.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        return null;
    }
    
    /**
     * Renvoie la signature du bout de code (À définir)
     * @return Retourne la signature du bout de code
     */
    public abstract String getSignature();

    /**
     * Renvoie le bout de code en entier sous la forme d'une chaîne de caractères (À définir). Il doit contenir la javadoc du bout de code (s'il y en a une), les annotations du bout de code (s'il y en a), la signature du bout de code avec le code associé (s'il y en a un)
     * @return Retourne le bout de code en entier sous la forme d'une chaîne de caractères
     */
    @Override
    public abstract String toString();

    /**
     * Renvoie le hashCode du bout de code
     * @return Retourne le hashCode du bout de code
     */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + java.util.Objects.hashCode(this.name);
        return hash;
    }

    /**
     * Détermine si deux bouts de code sont identiques ou pas
     * @param obj Correspond au second objet à comparer au courant
     * @return Retourne true, s'ils sont identiques, sinon false
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Code other = (Code) obj;
        return java.util.Objects.equals(this.name, other.name);
    }

    /**
     * Compare deux bouts de code
     * @param o Correspond au second objet à comparer au courant
     * @return Retourne le résultat de la comparaison
     */
    @Override
    public int compareTo(Code o) {
        return this.name.compareToIgnoreCase(o.name);
    }

    /**
     * Clone un bout de code
     * @return Retourne la copie du bout de code cloné
     * @throws CloneNotSupportedException Si le clone n'est pas supporté
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
    
    
    
//METHODE PRIVATE STATIC
    /**
     * Renvoie la valeur MD5 d'un texte
     * @param text Correspond au texte dont on veut connaitre la valeur MD5
     * @return Retourne la valeur MD5 du texte
     * @throws NoSuchAlgorithmException S'il y a la moindre exception
     */
    private static String md5(final String text) throws java.security.NoSuchAlgorithmException {
        // Create MD5 Hash
        java.security.MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
        digest.update(text.getBytes());
        byte messageDigest[] = digest.digest();

        // Create Hex String
        StringBuilder hexString = new StringBuilder();
        for (byte aMessageDigest : messageDigest) {
            String h = Integer.toHexString(0xFF & aMessageDigest);
            while (h.length() < 2)
                h = "0" + h;
            hexString.append(h);
        }
        return hexString.toString().toUpperCase();
    }
    
    
    
}