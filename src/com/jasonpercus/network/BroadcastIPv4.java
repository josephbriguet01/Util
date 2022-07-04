/*
 * Copyright (C) JasonPercus Systems, Inc - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by JasonPercus, 06/2018
 */
package com.jasonpercus.network;



/**
 * Cette classe permet de créer une adresse IP de broadcast.
 * @author Briguet
 * @version 1.0
 */
public class BroadcastIPv4 extends IPv4 {
    
    
    
    // <editor-fold defaultstate="collapsed" desc="SERIAL_VERSION_UID">
    /**
     * Correspond au numéro de série qui identifie le type de dé/sérialization utilisé pour l'objet
     */
    private static final long serialVersionUID = 1L;
    // </editor-fold>

    
    
    // <editor-fold defaultstate="collapsed" desc="CONSTRUCTORS">
    /**
     * Crée une adresse IP broadcast
     */
    public BroadcastIPv4() {
    }

    /**
     * Crée une adresse IP broadcast
     * @param ipv4 Correspond à un tableau de byte
     * @throws InvalidAddressException Correspond à l'exception levée si l'adresse est invalide
     */
    public BroadcastIPv4(byte[] ipv4) throws InvalidAddressException {
        super(ipv4);
    }
    
    /**
     * Crée une adresse IP broadcast
     * @param ipv4 Correspond à l'entier représentant l'adresse IP
     * @throws InvalidAddressException Correspond à l'exception levée si l'adresse est invalide
     */
    public BroadcastIPv4(int ipv4) throws InvalidAddressException {
        super(ipv4);
    }

    /**
     * Crée une adresse IP broadcast
     * @param ipv4 Correspond à une chaîne de caractère représentant l'adresse ip
     * @throws InvalidAddressException Correspond à l'exception levée si l'adresse est invalide
     */
    public BroadcastIPv4(String ipv4) throws InvalidAddressException {
        super(ipv4);
    }
    // </editor-fold>
    
    
    
    // <editor-fold defaultstate="collapsed" desc="METHODES PUBLICS">
    /**
     * Renvoie l'adresse broadcast pricipale de cet équipement
     * @return Retourne une adresse ip locale
     */
    public static BroadcastIPv4 getMainLocalBroadcast(){
        Network n = new Network();
        java.util.List<NetworkCard> lnc = n.getHardwareList();
        for(int i=0;i<lnc.size();i++){
            NetworkCard nc = lnc.get(i);
            if(nc.isUp() && !nc.isLoopback()){
                java.util.List<IPCard> lipc = nc.getIpCards();
                if(lipc.size()<=0) return null;
                else{
                    return lipc.get(0).getBroadcast();
                }
            }
        }
        return null;
    }
    // </editor-fold>
    
    
    
}