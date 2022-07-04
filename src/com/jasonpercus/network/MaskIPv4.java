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
 * Cette classe permet de créer un masque de réseau ou sous-réseau.
 * @author Briguet
 * @version 1.0
 */
public class MaskIPv4 extends IPv4 {
    
    
    
    // <editor-fold defaultstate="collapsed" desc="SERIAL_VERSION_UID">
    /**
     * Correspond au numéro de série qui identifie le type de dé/sérialization utilisé pour l'objet
     */
    private static final long serialVersionUID = 1L;
    // </editor-fold>

    
    
    // <editor-fold defaultstate="collapsed" desc="CONSTRUCTORS">
    /**
     * Crée un masque
     */
    public MaskIPv4() {
        super();
    }
    
    /**
     * Crée un masque
     * @param ipv4 Correspond à l'entier représentant le masque
     * @throws InvalidAddressException Correspond à l'exception levée si l'adresse est invalide
     */
    public MaskIPv4(int ipv4) throws InvalidAddressException {
        super(ipv4);
    }

    /**
     * Crée un masque
     * @param ipv4 Correspond à un tableau de byte
     * @throws InvalidAddressException Correspond à l'exception levée si l'adresse est invalide
     */
    public MaskIPv4(byte[] ipv4) throws InvalidAddressException {
        super(ipv4);
        if(!MaskIPv4.isValid(new IPv4(ipv4).getIpv4())){
            throw new InvalidAddressException("Your mask is invalid !");
        }
    }

    /**
     * Crée un masque
     * @param ipv4 Correspond à une chaîne de caractère représentant l'adresse ip
     * @throws InvalidAddressException Correspond à l'exception levée si l'adresse est invalide
     */
    public MaskIPv4(String ipv4) throws InvalidAddressException {
        super(ipv4);
        if(!MaskIPv4.isValid(ipv4)){
            throw new InvalidAddressException("Your mask is invalid !");
        }
    }
    // </editor-fold>

    
    
    // <editor-fold defaultstate="collapsed" desc="METHODES PUBLICS">
    /**
     * Modifie le masque
     * @param ipv4 Correspond à la nouvelle adresse IP sous forme de chaîne de caractères
     * @throws InvalidAddressException Correspond à l'exception levée si l'adresse est invalide
     */
    @Override
    public void setString(String ipv4) throws InvalidAddressException {
        super.setString(ipv4);
        if(!MaskIPv4.isValid(ipv4)){
            throw new InvalidAddressException("Your mask is invalid !");
        }
    }

    /**
     * Modifie le masque
     * @param ipv4 Correspond à la nouvelle adresse IP sous forme d'un tableau de bytes
     * @throws InvalidAddressException Correspond à l'exception levée si l'adresse est invalide
     */
    @Override
    public void setBytes(byte[] ipv4) throws InvalidAddressException {
        super.setBytes(ipv4);
        if(!MaskIPv4.isValid(new IPv4(ipv4).getIpv4())){
            throw new InvalidAddressException("Your mask is invalid !");
        }
    }
    
    /**
     * Modifie l'adresse IP
     * @param ipv4 Correspond à la nouvelle adresse IP sous forme d'un entier
     * @throws InvalidAddressException Correspond à l'exception levée si l'adresse est invalide
     */
    @Override
    public void setInt(int ipv4) throws InvalidAddressException {
        super.setInt(ipv4);
        if(!MaskIPv4.isValid(new IPv4(ipv4).getIpv4())){
            throw new InvalidAddressException("Your mask is invalid !");
        }
    }

    /**
     * Modifie le masque
     * @param ipv4 Correspond à la nouvelle adresse IP sous forme de chaîne de caractères
     * @throws InvalidAddressException Correspond à l'exception levée si l'adresse est invalide
     */
    @Override
    public void setIpv4(String ipv4) throws InvalidAddressException {
        super.setIpv4(ipv4);
        if(!MaskIPv4.isValid(ipv4)){
            throw new InvalidAddressException("Your mask is invalid !");
        }
    }
    
    /**
     * Renvoie le wildcard du masque (ex: masque = 255.255.255.0, wildcard = 0.0.0.255)
     * @return Retourne le wildcard du masque
     */
    public IPv4 getWildcard(){
        byte[] mask = toBytes();
        return new IPv4(new byte[]{(byte)(mask[0] ^ 0xFF), (byte)(mask[1] ^ 0xFF), (byte)(mask[2] ^ 0xFF), (byte)(mask[3] ^ 0xFF)});
    }
    
    /**
     * Renvoie le nombre d'adresse ip sur la partie hôte du masque
     * @return Retourne le nombre d'adresse ip sur la partie hôte du masque
     */
    public long lengthHost(){
        int c = 32 - Integer.parseInt(getCIDR(this).replaceAll("\\/", ""));
        if(c == 0) return 0;
        return (long) Math.pow(2, c);
    }
    
    /**
     * Renvoie le nombre d'adresse ip sur la partie réseau du masque
     * @return Retourne le nombre d'adresse ip sur la partie réseau du masque
     */
    public long lengthNetwork(){
        int c = Integer.parseInt(getCIDR(this).replaceAll("\\/", ""));
        if(c == 0) return 0;
        return (long) Math.pow(2, c);
    }
    
    /**
     * Renvoie le nombre d'adresse ip utilisable (donc sans l'adresse de réseau et de broadcast) sur la partie hôte du masque
     * @return Retourne le nombre d'adresse ip utilisable (donc sans l'adresse de réseau et de broadcast) sur la partie hôte du masque
     */
    public long countHosts(){
        int c = 32 - Integer.parseInt(getCIDR(this).replaceAll("\\/", ""));
        if(c > 1) return (long) (Math.pow(2, c) - 2);
        else if(c == 1) return 2;
        else return 1;
    }
    
    /**
     * Renvoie le masque sous sa forme simplifiée (ex: /10)
     * @param ipv4 Correspond au masque
     * @return Retourne le masque sous sa forme simplifiée (ex: /10)
     */
    public static String getCIDR(MaskIPv4 ipv4){
        String ip = ipv4.getIpv4();
        java.util.StringTokenizer st = new java.util.StringTokenizer(ip, ".");
        
        if (st.countTokens() != 4) 
            throw new InvalidAddressException("Your IPv4 address must have the format: X.X.X.X");
        
        java.util.List<String> strs = new java.util.ArrayList<>();
        
        while (st.hasMoreTokens()) {
            String temp = st.nextToken();
            int number = Integer.parseInt(temp);
            
            int result = 0;
            int[] base = {128, 64, 32, 16, 8, 4, 2, 1};
            for(int i=0;i<base.length;i++){
                int calc = result+base[i];
                if(calc>number){
                    strs.add("0");
                }else{
                    strs.add("1");
                    result = calc;
                }
            }
        }
        
        int index = strs.indexOf("0");
        if(index > 0){
            java.util.List<String> subString = strs.subList(0, index);
            return "/"+subString.size();
        }else{
            if(index == -1) return "/32";
            return "/0";
        }
    }
    
    /**
     * Renvoie le masque sous sa forme complète (ex: 255.192.0.0)
     * @param cidr Correspond au masque sous sa forme simplifiée (ex: /10)
     * @return Retourne le masque sous sa forme complète (ex: 255.192.0.0) 
     * @throws InvalidCharacterException Correspond à l'exception levée si un caractère invalide apparait dans simplifiedNorm
     */
    public static MaskIPv4 setCIDR(String cidr) throws InvalidCharacterException{
        String simplifiedN = cidr;
        simplifiedN = simplifiedN.replaceAll("/", "");
        try{
            int value = Integer.parseInt(simplifiedN);
            if(0<value && value<=32){
                int[] is = new int[32];
                for(int i=0;i<is.length;i++){
                    if(value<=i) is[i] = 0;
                    else is[i] = 1;
                }
                int[] tab = new int[4];
                for(int j=0;j<4;j++){
                    int start = j*8;
                    int end = start+8;
                    int calc = 0;
                    int[] base = {128, 64, 32, 16, 8, 4, 2, 1};
                    for(int i=start;i<end;i++){
                        int index = i-start;
                        if(is[i] == 1){
                            calc += base[index];
                        }
                    }
                    tab[j] = calc;
                }
                String chain = "";
                for(int i=0;i<tab.length;i++){
                    if(i!=0) chain += ".";
                    chain += ""+tab[i];
                }
                return new MaskIPv4(chain);
            }else{
                throw new InvalidNumberException("Your number of simplifiedNorm is invalid");
            }
        }catch(java.lang.NumberFormatException e){
            throw new InvalidCharacterException("Your chain of characters is invalid");
        }
    }
    
    /**
     * Renvoie l'adresse broadcast pricipale de cet équipement
     * @return Retourne une adresse ip locale
     */
    public static MaskIPv4 getMainLocalMask(){
        Network n = new Network();
        java.util.List<NetworkCard> lnc = n.getHardwareList();
        for(int i=0;i<lnc.size();i++){
            NetworkCard nc = lnc.get(i);
            if(nc.isUp() && !nc.isLoopback()){
                java.util.List<IPCard> lipc = nc.getIpCards();
                if(lipc.size()<=0) return null;
                else{
                    return lipc.get(0).getMask();
                }
            }
        }
        return null;
    }
    // </editor-fold>
    
    
    
    // <editor-fold defaultstate="collapsed" desc="METHODE PRIVATE">
    /**
     * Vérifie si le masque est correct
     * @param chain Correspond à l'ip du masque
     * @return Retourne true, s'il est valide, sinon false
     */
    private static boolean isValid(String chain){
        java.util.StringTokenizer st = new java.util.StringTokenizer(chain, ".");
        
        if (st.countTokens() != 4) 
            throw new InvalidAddressException("Your IPv4 address must have the format: X.X.X.X");
        
        java.util.List<String> strs = new java.util.ArrayList<>();
        
        while (st.hasMoreTokens()) {
            String temp = st.nextToken();
            int number = Integer.parseInt(temp);
            
            int result = 0;
            int[] base = {128, 64, 32, 16, 8, 4, 2, 1};
            for(int i=0;i<base.length;i++){
                int calc = result+base[i];
                if(calc>number){
                    strs.add("0");
                }else{
                    strs.add("1");
                    result = calc;
                }
            }
        }
        
        int index = strs.indexOf("0");
        if(index > 0){
            java.util.List<String> subString = strs.subList(index, strs.size());
            return !subString.contains("1");
        }else{
            return index == -1;
        }
    }
    // </editor-fold>
    
    
    
}