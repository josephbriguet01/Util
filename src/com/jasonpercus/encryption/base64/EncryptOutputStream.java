/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jasonpercus.encryption.base64;



/**
 * Un objet EncryptOutputStream obtient des octets de sortie chiffrés en base 64
 * @see java.io.OutputStream
 * @author JasonPercus
 * @version 1.0
 */
public class EncryptOutputStream extends com.jasonpercus.encryption.base.EncryptOutputStream {
    
    
    
//CONSTRUCTOR
    /**
     * Crée un flux EncryptOutputStream qui aura pour but de chiffrer un flux en base 64
     * @param output Correspond au flux chiffré
     * @throws java.io.IOException Si une erreur d'E/S se produit
     */
    public EncryptOutputStream(java.io.OutputStream output) throws java.io.IOException {
        super(output, new Base64());
    }
    
    
    
}