/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jasonpercus.encryption.base64;



/**
 * Un objet DecryptInputStream obtient des octets d'entrée déchiffrés en base 256
 * @see java.io.InputStream
 * @author JasonPercus
 * @version 1.0
 */
public class DecryptInputStream extends com.jasonpercus.encryption.base.DecryptInputStream {
    
    
    
//CONSTRUCTOR
    /**
     * Crée un flux DecryptInputStream qui aura pour but de déchiffrer un flux en base 256
     * @param input Correspond au flux à déchiffrer
     * @throws java.io.IOException Si une erreur d'E/S se produit
     */
    public DecryptInputStream(java.io.InputStream input) throws java.io.IOException {
        super(input, new Base64());
    }
    
    
    
}