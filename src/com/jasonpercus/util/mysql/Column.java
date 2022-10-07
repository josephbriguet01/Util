/*
 * Copyright (C) BRIGUET Systems, Inc - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Briguet, 05/2020
 */
package com.jasonpercus.util.mysql;



/**
 * Cette classe permet d'annoter des attributs d'une classe d'objet. Cette classe permettra entre-autre de récupérer les valeurs des entrées SQL et de les convertir en objet java
 * 
 * En attribuant cette annotation à certains (voir tous) attributs d'un objet, les méthodes (get...()) peuvent faire le lien entre le nom réel d'une colonne des résultats sql avec le nom d'un attribut.
 * Ainsi si l'on obtient un tableau sql de 2 colonnes (integer id, varchar name) et que l'on crée par exemple une class MaClasse dans laquelle on crée les attributs (int a, String b)
 * Et que l'on ajoute les annotations @Column(name="id") pour l'attribut a et @Column(name="name") pour l'attribut b, alors les méthodes créerons automatiquement pour chaque entrée SQL récupérée,
 * un objet MaClasse dans laquelle chaque attribut annoté contiendra la valeur de sa propre cellule des entrées sql.
 * 
 * @author JasonPercus
 * @version 1.0
 */
@java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@java.lang.annotation.Target(java.lang.annotation.ElementType.FIELD)
public @interface Column {
    
    
    
    /**
     * Correspond au nom de la colonne d'un tableau de résultats
     * 
     * En attribuant cette annotation à certains (voir tous) attributs d'un objet, les méthodes (get...()) peuvent faire le lien entre le nom réel d'une colonne des résultats sql avec le nom d'un attribut.
     * Ainsi si l'on obtient un tableau sql de 2 colonnes (integer id, varchar name) et que l'on crée par exemple une class MaClasse dans laquelle on crée les attributs (int a, String b)
     * Et que l'on ajoute les annotations @Column(name="id") pour l'attribut a et @Column(name="name") pour l'attribut b, alors les méthodes créerons automatiquement pour chaque entrée SQL récupérée,
     * un objet MaClasse dans laquelle chaque attribut annoté contiendra la valeur de sa propre cellule des entrées sql.
     * 
     * @return Retourne le nom de la colonne
     */
    public String name();
    
    
    
}