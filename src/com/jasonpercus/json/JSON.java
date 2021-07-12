/*
 * Copyright (C) BRIGUET Systems, Inc - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Briguet, Mai 2020
 */
package com.jasonpercus.json;



import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;



/**
 * Cette classe permet de créer une chaîne JSON à partir d'un objet et vice-versa
 * @author Briguet
 * @version 1.0
 * @param <O> Correspond au type connu de l'objet racine sérialisé/désérialisé du JSON
 */
public class JSON <O> {
    
    
    
//ATTRIBUTS
    /**
     * Détermine s'il s'agit d'un tableau ou d'un objet
     */
    private final ETypeJson type;
    /**
     * S'il s'agit d'un tableau alors c'est cette variable qui prend le tableau
     */
    private final java.util.List<O> list;
    /**
     * Sinon s'il s'agit d'un objet, alors c'est cette variable qui prend l'objet
     */
    private final O obj;

    
    
//CONSTRUCTOR
    /**
     * Crée un objet JSON
     * @param type Détermine s'il s'agit d'un tableau ou d'un objet
     * @param array Correspond au tableau (s'il s'agit d'un tableau, sinon null)
     * @param obj Correspond à l'objet (s'il s'agit d'un objet, sinon null)
     */
    private JSON(ETypeJson type, java.util.List<O> array, O obj) {
        this.type = type;
        this.list = array;
        this.obj = obj;
    }

    
    
//METHODES PUBLICS
    /**
     * Renvoie le type d'objet déserialisé
     * @return Retourne OBJECT s'il s'agit d'un objet sinon LIST
     */
    public String getType() {
        if(isObject()) return "OBJECT";
        else if(isArray()) return "LIST";
        else return null;
    }
    
    /**
     * Renvoie si oui ou non l'objet déserialisé est un objet et non une liste
     * @return Retourne true si l'objet est un objet
     */
    public boolean isObject(){
        return (type.equals(ETypeJson.OBJECT));
    }
    
    /**
     * Renvoie si oui ou non l'objet déserialisé est une liste et non un objet
     * @return Retourne true si l'objet est une liste
     */
    public boolean isArray(){
        return (type.equals(ETypeJson.ARRAY));
    }

    /**
     * Renvoie la liste (si l'objet est bien une liste, sinon renvoie null)
     * @return Retourne la liste
     */
    public java.util.List<O> getList() {
        return list;
    }

    /**
     * Renvoie l'objet (si l'objet est bien un objet, sinon renvoie null)
     * @return Retourne l'objet
     */
    public O getObj() {
        return obj;
    }
    
    
    
//METHODES PUBLICS STATICS
    /**
     * Renvoie une chaîne de caractères au format json
     * @param obj Correspond à l'objet à sérialiser
     * @return Retourne une chaîne de caractères au format json
     */
    public static String serialize(Object obj){
        checkGsonInstalled();
        final GsonBuilder builder = new GsonBuilder();
        final Gson gson = builder.create();
        return gson.toJson(obj);
    }
    
    /**
     * Renvoie un objet JSON contenant un objet ou une liste désérialisée d'une chaîne de caractère au format json
     * @param <O> Correspond au type d'objet (à la racine du json) qui sera désérialisé
     * @param type Correspond au type d'objet (à la racine du json) qui sera désérialisé. Attention, si l'objet racine est une liste, prendre le type contenu dans la liste. Ex List&lsaquo;MonObjet&rsaquo; -&rsaquo; type = MonObjet)
     * @param json Correspond à la chaîne de caractère au format json représentant l'objet ou la liste
     * @return Retourne un objet JSON
     */
    @SuppressWarnings({"Convert2Diamond", "unchecked"})
    public static <O> JSON deserialize(Class<O> type, String json){
        checkGsonInstalled();
        try{
            O obj = getObject(type, json);
            return new JSON(ETypeJson.OBJECT, null, obj);
        }catch(Exception e){}
        try{
            java.util.List<O> list = (java.util.List<O>) JSON.getList(type, json);
            return new JSON<O>(ETypeJson.ARRAY, list, null);
        }catch(Exception e){}
        return null;
    }
    
    
    
//METHODES PRIVATES STATICS
    /**
     * Renvoie l'objet déserialisé
     * @param <O> Correspond au type d'objet (à la racine du json) qui sera désérialisé
     * @param type Correspond au type d'objet (à la racine du json) qui sera désérialisé
     * @param json Correspond à la chaîne de caractère au format json représentant l'objet ou la liste
     * @return Retourne un objet déserialisé
     */
    private static <O> O getObject(Class<O> type, String json){
        java.lang.reflect.Type t = TypeToken.get(type).getType();
        final GsonBuilder builder = new GsonBuilder();
        final Gson gson = builder.create();
        return gson.fromJson(json, t);
    }
    
    /**
     * Renvoie la liste déserialisée
     * @param <O> Correspond au type d'objet (à la racine du json) qui sera désérialisé
     * @param type Correspond au type d'objet (à la racine du json) qui sera désérialisé. Attention, si l'objet racine est une liste, prendre le type contenu dans la liste. Ex List&lsaquo;MonObjet&rsaquo; -&rsaquo; type = MonObjet)
     * @param json Correspond à la chaîne de caractère au format json représentant l'objet ou la liste
     * @return Retourne une liste déserialisée
     */
    private static <O> java.util.List getList(Class<O> type, String json){
        java.lang.reflect.Type t = TypeToken.getParameterized(java.util.ArrayList.class, type).getType();
        final GsonBuilder builder = new GsonBuilder();
        final Gson gson = builder.create();
        return gson.fromJson(json, t);
    }
    
    /**
     * Génère une erreur si la librairie Gson n'est pas installée
     */
    private static void checkGsonInstalled(){
        try {
            Class.forName("com.google.gson.Gson");
        } catch (ClassNotFoundException ex) {
            throw new JSONError("No \"gson-2.8.2.jar\" installed. You can download it here: https://jar-download.com/artifacts/com.google.code.gson/gson/2.8.2/source-code");
        }
    }
    
    
    
//CLASS
    /**
     * Cette classe énumère le type d'objet déserialisé
     */
    private enum ETypeJson {
    
        ARRAY,
        OBJECT,

    }
    
    
    
}