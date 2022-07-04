/*
 * Copyright (C) JasonPercus Systems, Inc - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by JasonPercus, 05/2021
 */
package com.jasonpercus.util.process.java;



/**
 * Cette classe représente un objet permettant d'empaqueter un objet à envoyer entre deux processus {@link InterPipe}. Cette classe ne doit pas être utilisé par le développeur
 * @see InterPipe
 * @author JasonPercus
 * @version 1.0
 */
public final class Packet implements java.io.Serializable {
    
    
    
    // <editor-fold defaultstate="collapsed" desc="SERIAL_VERSION_UID">
    /**
     * Correspond au numéro de série qui identifie le type de dé/sérialization utilisé pour l'objet
     */
    private static final long serialVersionUID = 1L;
    // </editor-fold>
    
    
    
//ATTRIBUTS
    /**
     * Correspond à l'id du paquet
     */
    private String id;
    
    /**
     * Correspond à l'objet contenu dans le paquet sous la forme d'une chaîne de caractères
     */
    private java.io.Serializable obj;
    
    /**
     * Correspond à l'id qui précise que ce paquet attend une réponse synchrone
     */
    private String idReturn;
    
    /**
     * Correspond à la direction du paquet (par défaut 0 = aucun retour, 1 = le paquet vient d'arriver et nécessite une réponse, -1 = le paquet est la réponse de retour)
     */
    private int direction;
    
    
    
//CONSTRUCTORS
    /**
     * Crée un paquet par défaut (sans rien à l'intérieur)
     */
    public Packet(){
    }

    /**
     * Crée un paquet
     * @param object Correspond à l'objet à fournir dans le paquet
     */
    protected Packet(java.io.Serializable object) {
        this(object, 0, null);
    }

    /**
     * Crée un paquet
     * @param object Correspond à l'objet à fournir dans le paquet
     * @param direction Correspond à la direction du paquet (par défaut 0 = aucun retour, 1 = le paquet vient d'arriver et nécessite une réponse, -1 = le paquet est la réponse de retour)
     * @param idReturn Correspond à l'id de la demande
     */
    protected Packet(java.io.Serializable object, int direction, String idReturn) {
        this.id = generateChain(20);
        this.obj = object;
        this.direction = direction;
        if(direction != 0)
            this.idReturn = idReturn;
    }
    
    
    
//METHODES PUBLICS & PROTECTEDS
    /**
     * Renvoie l'objet contenu dans le paquet
     * @return Retourne l'objet contenu dans le paquet
     */
    protected java.io.Serializable getObject(){
        return this.obj;
    }

    /**
     * Renvoie l'id de retour du paquet
     * @return Retourne l'id de retour du paquet
     */
    protected String getIdReturn() {
        return idReturn;
    }

    /**
     * Renvoie la direction du paquet
     * @return Retourne la direction du paquet (par défaut 0 = aucun retour, 1 = le paquet vient d'arriver et nécessite une réponse, -1 = le paquet est la réponse de retour)
     */
    protected int getDirection() {
        return direction;
    }
    
    /**
     * Renvoie un paquet à partir d'une chaîne de caractères
     * @param str Correspond à la chaîne de caractère à convertir
     * @return Retourne le paquet envoyé
     */
    protected static Packet open(String str){
        return (Packet) datasToObj(base64ToDatas(str));
    }

    /**
     * Renvoie le hashCode du paquet
     * @return Retourne le hashCode du paquet
     */
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 83 * hash + java.util.Objects.hashCode(this.id);
        return hash;
    }

    /**
     * Détermine si deux paquets sont identiques ou pas
     * @param obj Correspond au second paquet à comparer au courant
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
        final Packet other = (Packet) obj;
        return java.util.Objects.equals(this.id, other.id);
    }

    /**
     * Affiche un paquet sous la forme d'une chaîne de caractères
     * @return Retourne un paquet sous la forme d'une chaîne de caractères
     */
    @Override
    public String toString() {
        return datasToBase64(objToDatas(this));
    }
    
    
    
//METHODES PRIVATES STATICS
    /**
     * Cette méthode génère une chaine aléatoire de length caractère
     * @param length Correspond à la taille de la chaine voulue
     * @return Retourne la chaine de caractère
     */
    private static String generateChain(int length) {
        String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890"; // Tu supprimes les lettres dont tu ne veux pas
        String pass = "";
        for (int x = 0; x < length; x++) {
            int i = (int) Math.floor(Math.random() * chars.length());
            pass += chars.charAt(i);
        }
        return pass;
    }
    
    /**
     * Converti un objet en tableau de bytes
     * @param obj Correspond à l'objet à convertir
     * @return Retourne un tableau de bytes
     */
    private static byte[] objToDatas(java.io.Serializable obj){
        java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
        try {
            byte[] yourBytes;
            try (java.io.ObjectOutput out = new java.io.ObjectOutputStream(baos)) {
                out.flush();
                out.writeObject(obj);
                out.flush();
                yourBytes = baos.toByteArray();
            }
            baos.close();
            return yourBytes;
        } catch (java.io.IOException ex) {
            java.util.logging.Logger.getLogger(Packet.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        return null;
    }
    
    /**
     * Converti un tableau de bytes en objet
     * @param datas Correspond au tableau de bytes à convertir
     * @return Retourne un objet
     */
    private static java.io.Serializable datasToObj(byte[] datas){
        try{
            Object o;
            try (java.io.ByteArrayInputStream bis = new java.io.ByteArrayInputStream(datas); java.io.ObjectInput in = new java.io.ObjectInputStream(bis)) {
                o = in.readObject();
            }
            return (java.io.Serializable) o;
        }catch(java.io.IOException | ClassNotFoundException e){
            return null;
        }
    }
    
    /**
     * Converti une chaîne base64 en tableau de bytes
     * @param base64 Correspond à la chaîne base64 à convertir
     * @return Retourne un tableau de bytes
     */
    private static byte[] base64ToDatas(String base64){
        return java.util.Base64.getDecoder().decode(base64.getBytes());
    }
    
    /**
     * Converti un tableau de bytes en chaîne base64
     * @param datas Correspond au tableau de bytes à convertir
     * @return Retourne une chaîne base64
     */
    private static String datasToBase64(byte[] datas){
        return java.util.Base64.getEncoder().encodeToString(datas);
    }
    
    
    
}