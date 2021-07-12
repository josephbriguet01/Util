/*
 * Copyright (C) BRIGUET Systems, Inc - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Briguet, Mai 2020
 */
package com.jasonpercus.json;



/**
 * Cette classe permet de gérer les erreurs graves
 * @author Briguet
 * @version 1.0
 */
public class JSONError extends Error{

    
    
    /**
     * Affiche une erreur grave
     * @param message Affiche le message d'erreur grave
     */
    public JSONError(String message) {
        super(message);
    }
    
    
    
}