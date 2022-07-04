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
 * Cette classe permet de récupérer la liste de toutes les cartes réseaux.
 * @author Briguet
 * @version 1.0
 */
public class Network {

    
    
    // <editor-fold defaultstate="collapsed" desc="CONSTRUCTOR">
    /**
     * Crée un objet Network par défaut
     */
    public Network() {
    }
    // </editor-fold>
    
    
    
    // <editor-fold defaultstate="collapsed" desc="METHODE PUBLIC">
    /**
     * Renvoie la liste des cartes réseaux
     * @return Retourne la liste des cartes réseaux
     * @throws HardwareNetworkException Correspond à l'exception levée si une erreur survient
     */
    public java.util.List<NetworkCard> getHardwareList() throws HardwareNetworkException {
        java.util.List<NetworkCard> ncs = new java.util.ArrayList<>();
        try {
            
            java.util.Enumeration<java.net.NetworkInterface> nets = java.net.NetworkInterface.getNetworkInterfaces();
            while(nets.hasMoreElements()){
                java.net.NetworkInterface netint = nets.nextElement();
                try {
                    NetworkCard nc;
                    if(netint.getHardwareAddress() == null) nc = new NetworkCard(netint.getDisplayName(), netint.getName(), netint.getIndex());
                    else nc = new NetworkCard(new MACAddress(netint.getHardwareAddress()), netint.getDisplayName(), netint.getName(), netint.getIndex());
                    java.util.List<java.net.InterfaceAddress> ias = netint.getInterfaceAddresses();
                    for(int i=0;i<ias.size();i++){
                        java.net.InterfaceAddress ia = ias.get(i);
                        
                        java.net.InetAddress broadcast = ia.getBroadcast();
                        BroadcastIPv4 broad = null;
                        if(broadcast != null) broad = new BroadcastIPv4(broadcast.toString().replaceAll("/", ""));
                        try{
                            IPCard ipc = new IPCard(new IPv4(ia.getAddress().getAddress()), MaskIPv4.setCIDR("/"+ia.getNetworkPrefixLength()), broad);
                            nc.addIPCard(ipc);
                        }catch(InvalidAddressException e){
                            
                        }
                    }
                    nc.setNetworkInterface(netint);
                    ncs.add(nc);
                } catch (java.net.SocketException ex) {
                    throw new HardwareNetworkException(ex.getMessage());
                }
            }
        } catch (java.net.SocketException ex) {
            throw new HardwareNetworkException(ex.getMessage());
        }
        return ncs;
    }
    // </editor-fold>
    
    
    
    // <editor-fold defaultstate="collapsed" desc="METHODES PUBLICS STATICS">
    /**
     * Renvoie l'adresse IP du réseau
     * @param ipEquipement Correspond à une adresse ip du réseau
     * @param mask Correspond au masque de réseau
     * @return Retourne
     */
    public static IPv4 getIPNetwork(IPv4 ipEquipement, MaskIPv4 mask){
        byte[] i = ipEquipement.toBytes();
        byte[] m = mask.toBytes();
        return new IPv4(new byte[]{(byte)(i[0] & m[0]), (byte)(i[1] & m[1]), (byte)(i[2] & m[2]), (byte)(i[3] & m[3])});
    }
    
    /**
     * Renvoie le broadcast du réseau
     * @param ipNetwork Correspond à l'adresse IP du réseau
     * @param mask Correspond au masque du réseau
     * @return Retourne le broadcast du réseau
     */
    public static BroadcastIPv4 getIPBroadcast(IPv4 ipNetwork, MaskIPv4 mask){
        byte[] i = ipNetwork.toBytes();
        byte[] w = mask.getWildcard().toBytes();
        return new BroadcastIPv4(new byte[]{(byte)(i[0] | w[0]), (byte)(i[1] | w[1]), (byte)(i[2] | w[2]), (byte)(i[3] | w[3])});
    }
    
    /**
     * Renvoie un objet PoolIPv4 permettant de connaitre les adresses utilisables d'un réseau ainsi que leur nombre
     * @param ipNetwork Correspond à l'IP du réseau
     * @param mask Correspond au masque du réseau
     * @return Retourne un objet PoolIPv4
     */
    public static PoolIPv4 getPoolIpv4(IPv4 ipNetwork, MaskIPv4 mask){
        return new PoolIPv4(ipNetwork, mask);
    }
    // </editor-fold>
    
    
    
}