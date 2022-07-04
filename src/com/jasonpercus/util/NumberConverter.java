/*
 * Copyright (C) JasonPercus Systems, Inc - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by JasonPercus, 05/2021
 */
package com.jasonpercus.util;



/**
 * Cette classe à pour fonction de convertir des nombres en d'autres formats ou objets
 * @author JasonPercus
 * @version 1.0
 */
@SuppressWarnings("ShiftOutOfRange")
public class NumberConverter {

    
    
//CONSTRUCTOR
    /**
     * Correspond au constructeur par défaut
     */
    private NumberConverter() {
    }
    
    
    
//METHODES PUBLICS STATICS
    /**
     * Convertit un long en un tableau de 8 bytes
     * @param l Correspond au long à convertir
     * @return Retourne une tableau de 8 bytes
     */
    public static byte[] longTobytes(long l){
        return new byte[]{
            (byte)((l >> 56) & 0xFF),
            (byte)((l >> 48) & 0xFF),
            (byte)((l >> 40) & 0xFF),
            (byte)((l >> 32) & 0xFF),
            (byte)((l >> 24) & 0xFF),
            (byte)((l >> 16) & 0xFF),
            (byte)((l >> 8) & 0xFF),
            (byte)((l) & 0xFF)
        };
    }
    
    /**
     * Convertit un tableau de 8 bytes en un long
     * @param bytes Correspond au tableau de 8 bytes à convertir
     * @return Retourne un long
     */
    public static long bytesToLong(byte[] bytes){
        return (long)(
            ((bytes[0] & 0xFF) << 56) | 
            ((bytes[1] & 0xFF) << 48) | 
            ((bytes[2] & 0xFF) << 40) | 
            ((bytes[3] & 0xFF) << 32) | 
            ((bytes[4] & 0xFF) << 24) | 
            ((bytes[5] & 0xFF) << 16) | 
            ((bytes[6] & 0xFF) << 8) | 
            ((bytes[7] & 0xFF))
        );
    }
    
    /**
     * Convertit un entier en un tableau de 4 bytes
     * @param i Correspond à l'entier à convertir
     * @return Retourne une tableau de 4 bytes
     */
    public static byte[] intTobytes(int i){
        return new byte[]{
            (byte)((i >> 24) & 0xFF),
            (byte)((i >> 16) & 0xFF),
            (byte)((i >> 8) & 0xFF),
            (byte)((i) & 0xFF)
        };
    }
    
    /**
     * Convertit un tableau de 4 bytes en un entier
     * @param bytes Correspond au tableau de 4 bytes à convertir
     * @return Retourne un entier
     */
    public static long bytesToInt(byte[] bytes){
        return (int)(
            ((bytes[4] & 0xFF) << 24) | 
            ((bytes[5] & 0xFF) << 16) | 
            ((bytes[6] & 0xFF) << 8) | 
            ((bytes[7] & 0xFF))
        );
    }
    
    /**
     * Convertit un short en un tableau de 2 bytes
     * @param i Correspond au short à convertir
     * @return Retourne une tableau de 2 bytes
     */
    public static byte[] shortTobytes(int i){
        return new byte[]{
            (byte)((i >> 8) & 0xFF),
            (byte)((i) & 0xFF)
        };
    }
    
    /**
     * Convertit un tableau de 2 bytes en un short
     * @param bytes Correspond au tableau de 2 bytes à convertir
     * @return Retourne un short
     */
    public static long bytesToShort(byte[] bytes){
        return (short)(
            ((bytes[6] & 0xFF) << 8) | 
            ((bytes[7] & 0xFF))
        );
    }
    
    
    
}