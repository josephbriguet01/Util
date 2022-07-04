/*
 * Copyright (C) JasonPercus Systems, Inc - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by JasonPercus, 07/2021
 */
package com.jasonpercus.util;



import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;



/**
 * Cette annotation personnalisée permet entre autre de structurer le code d'une classe en la réécrivant correctement
 * @author JasonPercus
 * @version 1.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface Showing {
    
    
    
    /**
     * Renvoie si oui ou non la séparation des blocs de codes se fait par un editor-fold ou seulement une ligne de commentaire
     * @return Retourne {@link Bloc#NO_COLLAPSE}, s'il s'agit seulement d'une ligne, sinon {@link Bloc#COLLAPSE} s'il s'agit d'un bloc editor-fold
     */
    Bloc blocs() default Bloc.NO_COLLAPSE;
    
    /**
     * Si {@link #blocs()} renvoie {@link Bloc#COLLAPSE}, alors il reste à détermine si les bloc seront fermé par défaut ou pas
     * @return Retourne {@link State#COLLAPSED}, s'ils le sont, sinon {@link State#UNCOLLAPSED}
     */
    State state() default State.COLLAPSED;
    
    /**
     * Détermine s'il faut afficher les warnings javadoc
     * @return Retourne {@link Javadoc#WARNING}, s'il le faut, sinon {@link Javadoc#NO_WARNING}
     */
    Javadoc javadoc() default Javadoc.WARNING;
    
    
    
//ENUMS
    /**
     * Cette classe d'énumération énumère les deux blocs possibles de code pour un Showing
     * @author JasonPercus
     * @version 1.0
     */
    public enum Bloc {
        
        
        
        /**
         * Les blocs seront délimité par un editor-fold qui empaquettera l'ensemble du bout de code
         */
        COLLAPSE,
        
        /**
         * Les blocs seront délimité par seulement une ligne de commentaire
         */
        NO_COLLAPSE;
        
        
        
    }
    
    /**
     * Cette classe d'énumération énumère si l'editor-fold est replié ou pas par défaut sur lui même (Dans le cas évidement d'un {@link Bloc#COLLAPSE})
     * @author JasonPercus
     * @version 1.0
     */
    public enum State {
        
        
        
        /**
         * Le bloc est replié sur lui même
         */
        COLLAPSED("collapsed"),
        
        /**
         * Le bloc n'est pas replié sur lui même
         */
        UNCOLLAPSED("expanded");
        
        
        
    //ATTRIBUT
        /**
         * Correspond au texte qui correspond à l'affichage d'un état
         */
        private final String str;

        
        
    //CONSTRUCTOR
        /**
         * Crée un état
         * @param str Correspond au texte de l'état
         */
        private State(String str) {
            this.str = str;
        }

        
        
    //METHODE PUBLIC
        /**
         * Renvoie l'état sous la forme d'une chaîne de caractères
         * @return Retourne l'état sous la forme d'une chaîne de caractères
         */
        @Override
        public String toString() {
            return str;
        }
        
        
        
    }
    
    /**
     * Cette classe d'énumération énumère si oui ou non les Warnings de javadoc doivent s'afficher à la compilation
     * @author JasonPercus
     * @version 1.0
     */
    public enum Javadoc {
        
        
        
        /**
         * Les warnings s'afficheront
         */
        WARNING,
        
        /**
         * Les warnings ne s'afficheront pas
         */
        NO_WARNING;
        
        
        
    }
    
    
    
}