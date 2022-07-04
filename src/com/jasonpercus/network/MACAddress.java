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
 * Cette classe permet de créer et gérer des adresses Mac.
 * @author Briguet
 * @version 1.0
 */
public class MACAddress implements java.io.Serializable, Comparable<MACAddress>, Cloneable, CharSequence {
    
    
    
    // <editor-fold defaultstate="collapsed" desc="SERIAL_VERSION_UID">
    /**
     * Correspond au numéro de série qui identifie le type de dé/sérialization utilisé pour l'objet
     */
    private static final long serialVersionUID = 1L;
    // </editor-fold>
    
    
    
    // <editor-fold defaultstate="collapsed" desc="ATTRIBUT">
    /**
     * Correspond à l'adresse Mac en chaine de caractère
     */
    private String macAddress;
    // </editor-fold>

    
    
    // <editor-fold defaultstate="collapsed" desc="CONSTRUCTORS">
    /**
     * Crée par défaut une MACAddress
     */
    public MACAddress() {
        this.macAddress = "00-00-00-00-00-00";
    }
    
    /**
     * Crée une MACAddress
     * @param macAddress Correspond à un tableau de bytes
     * @throws InvalidAddressException Correspond à l'exception levée si le format de l'adresse n'est pas correct
     */
    public MACAddress(byte[] macAddress) throws InvalidAddressException {
        this.macAddress = "00-00-00-00-00-00";
        MACAddress.verifyFormat(convertToString(macAddress));
        this.macAddress = convertToString(macAddress);
    }
    
    /**
     * Crée une MACAddress
     * @param macAddress Correspond à une chaine de caractères
     * @throws InvalidAddressException Correspond à l'exception levée si le format de l'adresse n'est pas correct
     */
    public MACAddress(String macAddress) throws InvalidAddressException {
        this.macAddress = "00-00-00-00-00-00";
        MACAddress.verifyFormat(macAddress);
        this.macAddress = convertToString(convertToBytes(macAddress));
    }
    // </editor-fold>

    
    
    // <editor-fold defaultstate="collapsed" desc="METHODES PUBLICS">
    /**
     * Renvoie la MACAddress
     * @return Retourne la MACAddress
     */
    public String getMacAddress() {
        return macAddress;
    }

    /**
     * Modifie la MACAddress
     * @param macAddress Correspond à la nouvelle chaîne de caractères représentant la MACAddress
     * @throws InvalidAddressException Correspond à l'exception levée si le format de l'adresse n'est pas correct
     */
    public void setMacAddress(String macAddress) throws InvalidAddressException {
        this.macAddress = "00-00-00-00-00-00";
        MACAddress.verifyFormat(macAddress);
        this.macAddress = convertToString(convertToBytes(macAddress));
    }
    
    /**
     * Modifie la MACAddress
     * @param macAddress Correspond à la nouvelle MACAddress
     * @throws InvalidAddressException Correspond à l'exception levée si le format de l'adresse n'est pas correct
     */
    public void setBytes(byte[] macAddress) throws InvalidAddressException {
        this.macAddress = convertToString(macAddress);
    }
    
    /**
     * Modifie la MACAddress
     * @param macAddress Correspond à la nouvelle MACAddress
     * @throws InvalidAddressException Correspond à l'exception levée si le format de l'adresse n'est pas correct
     */
    public void setString(String macAddress) throws InvalidAddressException {
        setMacAddress(macAddress);
    }
    
    /**
     * Renvoie la MACAddress sous forme de tableau de bytes
     * @return Retourne la MACAddress sous forme de tableau de bytes
     */
    public byte[] toBytes(){
        return MACAddress.convertToBytes(this.macAddress);
    }
    
    /**
     * Renvoie la MACAddress sous forme de chaîne de caractères
     * @return Retourne la MACAddress sous forme de chaîne de caractères
     */
    @Override
    public String toString(){
        return this.macAddress;
    }
    
    /**
     * Renvoie la MACAddress sous forme de chaîne de caractères
     * @param separator Correspond au séparateur de chaque élément de la MACAddress
     * @return Retourne la MACAddress sous forme de chaîne de caractères
     */
    public String toString(String separator){
        return toString().replaceAll("-", separator);
    }

    /**
     * Renvoie la longueur de la chaîne de caractères représentant l'adresse MAC
     * @return Retourne la longueur de la chaîne de caractères
     */
    @Override
    public int length() {
        return macAddress.length();
    }

    /**
     * Renvoie le nième caractère de la chaîne représentant l'adresse MAC
     * @param index Correspond à l'indice du caractère à récupérer
     * @return Retourne le caractère à la position i
     */
    @Override
    public char charAt(int index) {
        return macAddress.charAt(index);
    }

    /**
     * Renvoie une sous-chaîne de caractères de l'adresse MAC compris entre start et end
     * @param start Correspond à l'indice de départ de la sous-chaîne
     * @param end Correspond à l'indice de fin de la sous-chaîne
     * @return Retourne une chaîne de caractères
     */
    @Override
    public CharSequence subSequence(int start, int end) {
        return macAddress.subSequence(start, end);
    }
    
    /**
     * Renvoie l'adresse MAC principale d'un équipement
     * @return Retourne l'adresse MAC principale d'un équipement
     */
    public static MACAddress getMainLocalMACAddress(){
        Network n = new Network();
        java.util.List<NetworkCard> lnc = n.getHardwareList();
        for(int i=0;i<lnc.size();i++){
            NetworkCard nc = lnc.get(i);
            if(nc.isUp() && !nc.isLoopback()){
                return nc.getMacAddress();
            }
        }
        return null;
    }
    
    /**
     * Clone une MACAddress
     * @return Retourne le clone de la MACAddress courante
     */
    @Override
    @SuppressWarnings({"CloneDoesntCallSuperClone", "CloneDeclaresCloneNotSupported"})
    public Object clone(){
        return new MACAddress(macAddress);
    }

    /**
     * Renvoie le résultat de la méthode compareTo()
     * @param mac Correspond à l'objet à tester
     * @return Retourne le résultat de la méthode compareTo()
     */
    @Override
    public int compareTo(MACAddress mac) {
        return toString().compareTo(mac.toString());
    }

    /**
     * Renvoie le résultat de la méthode hashCode()
     * @return Retourne le résultat de la méthode hashCode()
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 73 * hash + java.util.Objects.hashCode(this.macAddress);
        return hash;
    }

    /**
     * Renvoie le résultat de la méthode equals()
     * @param obj Correspond à l'objet à comparer
     * @return Retourne le résultat de la méthode equals()
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final MACAddress other = (MACAddress) obj;
        return java.util.Objects.equals(this.macAddress, other.macAddress);
    }
    // </editor-fold>
    
    
    
    // <editor-fold defaultstate="collapsed" desc="METHODE PRIVATE">
    /**
     * Vérifie si le format de la MACAddress est correct
     * @param macAddress Correspond à la chaîne de caractère représentant la MACAddress
     * @throws InvalidAddressException Correspond à l'exception levée si le format de l'adresse n'est pas correct
     */
    private static void verifyFormat(String macAddress) throws InvalidAddressException {
        java.util.StringTokenizer st;
        if(macAddress.contains(":"))
            st = new java.util.StringTokenizer(macAddress, ":");
        else
            st = new java.util.StringTokenizer(macAddress, "-");
        
        if (st.countTokens() != 6 && st.countTokens() != 8) 
            throw new InvalidAddressException("Your MAC address must have the format: XX-XX-XX-XX-XX-XX or XX-XX-XX-XX-XX-XX-XX-XX");
        
        while (st.hasMoreTokens()) {
            String temp = st.nextToken();
            if (temp.length() != 2) throw new InvalidAddressException("Your MAC address must have the format: XX-XX-XX-XX-XX-XX or XX-XX-XX-XX-XX-XX-XX-XX");
            java.util.regex.Pattern p = java.util.regex.Pattern.compile("[0-9a-fA-F]{2}");
            java.util.regex.Matcher matcher = p.matcher(temp);
            if(!matcher.find()) throw new InvalidAddressException("Character not authorized in your MAC address");
        }
    }
    
    /**
     * Converti un tableau de bytes en chaîne de caractères
     * @param macAddress Correspond à la MACAddress sous forme de tableau de bytes
     * @return Retourne une MACAddress en chaîne de caractères
     */
    private static String convertToString(byte[] macAddress){
        StringBuilder sb = new StringBuilder(18);
        for (byte b : macAddress) {
            if (sb.length() > 0) sb.append('-');
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
    
    /**
     * Converti une chaîne de caractères en tableau de bytes
     * @param macAddress Correspond à la MACAddress sous forme de chaîne de caractères
     * @return Retourne une MACAddress en tableau de bytes
     */
    private static byte[] convertToBytes(String macAddress) {
        String[] bytes;
        if(macAddress.contains(":"))
            bytes = macAddress.split(":");
        else
            bytes = macAddress.split("-");
        
        byte[] parsed = new byte[bytes.length];

        for (int x = 0; x < bytes.length; x++) {
            java.math.BigInteger temp = new java.math.BigInteger(bytes[x], 16);
            byte[] raw = temp.toByteArray();
            parsed[x] = raw[raw.length - 1];
        }
        return parsed;
    }
    // </editor-fold>
    
    
    
}