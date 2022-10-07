/*
 * Copyright (C) JasonPercus Systems, Inc - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by JasonPercus, 06/2021
 */
package com.jasonpercus.util.key;



/**
 * Cette interface représente un écouteur sur un objet {@link CombinationCapture}. Cela permet donc de transmettre une combinaison (simple) de touches
 * @see Combination
 * @see CombinationCapture
 * @author JasonPercus
 * @version 1.0
 */
public interface CombinationCaptureListener {
    
    
    
    /**
     * Lorsqu'une combinaison simple de touche(s) est pressée
     * @param combination Correspond à la combinaison de touche(s) pressée
     * @param evt Correspond à l'évênement qui a généré la combinaison
     */
    public void combinationPressed(Combination combination, java.awt.event.KeyEvent evt);
    
    
    
}