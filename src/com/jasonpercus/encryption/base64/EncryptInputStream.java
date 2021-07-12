/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jasonpercus.encryption.base64;



/**
 * Un objet EncryptInputStream obtient des octets d'entrée chiffrés en base 64
 * @see java.io.InputStream
 * @author JasonPercus
 * @version 1.0
 */
public class EncryptInputStream extends com.jasonpercus.encryption.base.EncryptInputStream {
    
    
    
//CONSTRUCTOR
    /**
     * Crée un flux EncryptInputStream qui aura pour but de chiffrer un flux en base 64
     * @param input Correspond au flux à chiffrer
     * @throws java.io.IOException Si une erreur d'E/S se produit
     */
    public EncryptInputStream(java.io.InputStream input) throws java.io.IOException {
        super(input, new Base64());
    }
    
    
    
}