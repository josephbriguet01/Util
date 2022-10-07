/*
 * Copyright (C) JasonPercus Systems, Inc - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by JasonPercus, 06/2021
 */
package com.jasonpercus.util;



import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;



/**
 * Cette annotation personnalisée permet de créer une classe à la volée (de la classe contenant l'annotation) avec des setters utilisables à la chaîne
 * @author JasonPercus
 * @version 1.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface Builder {
    
    
    
    /**
     * Correspond au mode d'écriture de la nouvelle classe
     * @return Retourne le mode d'écriture de la nouvelle classe
     */
    Mode mode() default Mode.IF_NOT_EXISTS;
    
    
    
//ENUM
    /**
     * Cette classe d'énumération, énumère les différents modes d'écriture
     * @author JasonPercus
     * @version 1.0
     */
    public enum Mode {
        
        /**
         * Lorsque l'on souhaite toujours écraser la nouvelle classe (si modification)
         */
        ALWAYS_OVERWRITE,
        
        /**
         * Si l'on ne souhaite pas écraser la nouvelle classe (si modification)
         */
        IF_NOT_EXISTS,
        
        /**
         * Si l'on souhaite supprimer la nouvelle classe (si modification)
         */
        DELETE;
        
    }
    
    
    
}