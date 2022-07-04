/*
 * Copyright (C) JasonPercus Systems, Inc - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by JasonPercus, 06/2018
 */
package com.jasonpercus.network;



import com.jasonpercus.network.ping.Ping;
import com.jasonpercus.network.ping.ResultPing;
import com.jasonpercus.network.ping.SuccessPing;



/**
 * Crée une adresse IP
 * @author Briguet
 * @version 1.0
 */
public class IPv4 extends Number implements Comparable<IPv4>, Cloneable, CharSequence {
    
    
    
    // <editor-fold defaultstate="collapsed" desc="SERIAL_VERSION_UID">
    /**
     * Correspond au numéro de série qui identifie le type de dé/sérialization utilisé pour l'objet
     */
    private static final long serialVersionUID = 1L;
    // </editor-fold>
    
    
    
    // <editor-fold defaultstate="collapsed" desc="ATTRIBUT">
    /**
     * Correspond à l'adresse IP
     */
    private String ipv4;
    // </editor-fold>

    
    
    // <editor-fold defaultstate="collapsed" desc="CONSTRUCTORS">
    /**
     * Crée une adresse IP
     */
    public IPv4() {
        this.ipv4 = "0.0.0.0";
    }

    /**
     * Crée une adresse IP
     * @param ipv4 Correspond à un tableau de byte
     * @throws InvalidAddressException Correspond à l'exception levée si l'adresse est invalide
     */
    public IPv4(byte[] ipv4) throws InvalidAddressException {
        this.ipv4 = "0.0.0.0";
        IPv4.verifyFormat(convertToString(ipv4));
        this.ipv4 = convertToString(ipv4);
    }
    
    /**
     * Crée une adresse IP
     * @param ipv4 Correspond à l'entier représentant l'adresse IP
     * @throws InvalidAddressException Correspond à l'exception levée si l'adresse est invalide
     */
    public IPv4(int ipv4) throws InvalidAddressException {
        this((byte[]) new byte[]{(byte)((0xFF000000 & ipv4) >>> 24), (byte)((0x00FF0000 & ipv4) >>> 16), (byte)((0x0000FF00 & ipv4) >>> 8), (byte)(0x000000FF & ipv4)});
    }
    
    /**
     * Crée une adresse IP
     * @param ipv4 Correspond à une chaîne de caractère représentant l'adresse ip
     * @throws InvalidAddressException Correspond à l'exception levée si l'adresse est invalide
     */
    public IPv4(String ipv4) throws InvalidAddressException {
        this.ipv4 = "0.0.0.0";
        IPv4.verifyFormat(ipv4);
        this.ipv4 = convertToString(convertToBytes(ipv4));
    }
    // </editor-fold>

    
    
    // <editor-fold defaultstate="collapsed" desc="METHODES PUBLICS">
    /**
     * Renvoie l'adresse IP sous forme de chaîne de caractères
     * @return Retourne l'adresse IP sous forme de chaîne de caractères
     */
    public String getIpv4() {
        return ipv4;
    }

    /**
     * Modifie l'adresse IP
     * @param ipv4 Correspond à la nouvelle adresse IP sous forme de chaîne de caractères
     * @throws InvalidAddressException Correspond à l'exception levée si l'adresse est invalide
     */
    public void setIpv4(String ipv4) throws InvalidAddressException {
        this.ipv4 = "0.0.0.0";
        IPv4.verifyFormat(ipv4);
        this.ipv4 = convertToString(convertToBytes(ipv4));
    }
    
    /**
     * Renvoie le number bloc de l'adresse IP. Exemple: Pour l'adresse 192.168.1.35, getBloc(3) renvoie 35
     * @param number Correspond à la position du nombre que l'on veut récupérer
     * @return Retourne le bloc de l'adresse IP
     */
    public int getBloc(int number){
        if(number>3 || number<0) return -1;
        String split[] = ipv4.split("\\.");
        return Integer.parseInt(split[number]);
    }
    
    /**
     * Envoie un ping à destination de cette adresse IP
     * @return Retourne le résultat de l'adresse IP
     * @throws Exception S'il y a la moindre exception
     */
    public ResultPing ping() throws Exception {
        return Ping.execute(this);
    }
    
    /**
     * Renvoie si oui ou non l'adresse ip est accessible sur le réseau (cette méthode envoie un ping à destination de l'adresse IP)
     * @return Retourne true si l'adresse IP est accessible sur le réseau, sinon false
     */
    public boolean isAccessible() {
        try {
            ResultPing result = ping();
            return result instanceof SuccessPing;
        } catch (Exception ex) {
            return false;
        }
    }
    
    /**
     * Modifie l'adresse IP
     * @param ipv4 Correspond à la nouvelle adresse IP sous forme d'un tableau de bytes
     * @throws InvalidAddressException Correspond à l'exception levée si l'adresse est invalide
     */
    public void setBytes(byte[] ipv4) throws InvalidAddressException {
        this.ipv4 = convertToString(ipv4);
    }
    
    /**
     * Modifie l'adresse IP
     * @param ipv4 Correspond à la nouvelle adresse IP sous forme d'un entier
     * @throws InvalidAddressException Correspond à l'exception levée si l'adresse est invalide
     */
    public void setInt(int ipv4) throws InvalidAddressException {
        setBytes(new byte[]{(byte)((0xFF000000 & ipv4) >>> 24), (byte)((0x00FF0000 & ipv4) >>> 16), (byte)((0x0000FF00 & ipv4) >>> 8), (byte)(0x000000FF & ipv4)});
    }
    
    /**
     * Modifie l'adresse IP
     * @param ipv4 Correspond à la nouvelle adresse IP sous forme de chaîne de caractères
     * @throws InvalidAddressException Correspond à l'exception levée si l'adresse est invalide
     */
    public void setString(String ipv4) throws InvalidAddressException {
        setIpv4(ipv4);
    }
    
    /**
     * Renvoie l'adresse IP sous la forme d'un tableau de bytes
     * @return Retourne l'adresse IP sous la forme d'un tableau de bytes
     */
    public byte[] toBytes(){
        return IPv4.convertToBytes(ipv4);
    }
    
    /**
     * Renvoie l'adresse IP sous la forme d'un entier (ex: 192.168.1.1 = -1062731519)
     * @return Retourne l'adresse IP sous la forme d'un entier
     */
    public int toInt(){
        byte[] bs = toBytes();
        return ((bs[0] & 0xff) << 24) + ((bs[1] & 0xff) << 16) + ((bs[2] & 0xff) << 8) + (bs[3] & 0xff);
    }

    /**
     * Renvoie l'ip sous la forme d'un entier
     * @return Retourne l'ip sous la forme d'un entier
     */
    @Override
    public int intValue() {
        return toInt();
    }

    /**
     * Renvoie l'ip sous la forme d'un long
     * @return Retourne l'ip sous la forme d'un long
     */
    @Override
    public long longValue() {
        return toInt();
    }

    /**
     * Renvoie l'ip sous la forme d'un float
     * @return Retourne l'ip sous la forme d'un float
     */
    @Override
    public float floatValue() {
        return toInt();
    }

    /**
     * Renvoie l'ip sous la forme d'un double
     * @return Retourne l'ip sous la forme d'un double
     */
    @Override
    public double doubleValue() {
        return toInt();
    }
    
    /**
     * Renvoie la longueur de la chaîne de caractères représentant l'adresse ip
     * @return Retourne la longueur de la chaîne de caractères
     */
    @Override
    public int length() {
        return ipv4.length();
    }

    /**
     * Renvoie le nième caractère de la chaîne représentant l'adresse ip
     * @param index Correspond à l'indice du caractère à récupérer
     * @return Retourne le caractère à la position i
     */
    @Override
    public char charAt(int index) {
        return ipv4.charAt(index);
    }

    /**
     * Renvoie une sous-chaîne de caractères de l'adresse ip compris entre start et end
     * @param start Correspond à l'indice de départ de la sous-chaîne
     * @param end Correspond à l'indice de fin de la sous-chaîne
     * @return Retourne une chaîne de caractères
     */
    @Override
    public CharSequence subSequence(int start, int end) {
        return ipv4.subSequence(start, end);
    }

    /**
     * Renvoie l'adresse IP sous forme de chaîne de caractères
     * @return Retourne l'adresse IP sous forme de chaîne de caractères
     */
    @Override
    public String toString() {
        return ipv4;
    }

    /**
     * Crée un clone de l'objet courant
     * @return Retourne le clone
     * @throws CloneNotSupportedException Correspond à l'exception levée s'il y a une erreur de clonage
     */
    @Override
    @SuppressWarnings("CloneDoesntCallSuperClone")
    public Object clone() throws CloneNotSupportedException {
        return new IPv4(this.ipv4);
    }

    /**
     * Renvoie le résultat de la méthode compareTo()
     * @param ip Correspond à l'objet à comparer
     * @return Retourne le résultat de la méthode compareTo()
     */
    @Override
    public int compareTo(IPv4 ip) {
        return toString().compareTo(ip.toString());
    }

    /**
     * Renvoie le résultat de la méthode hashCode()
     * @return Retourne le résultat de la méthode hashCode()
     */
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + java.util.Objects.hashCode(this.ipv4);
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
        final IPv4 other = (IPv4) obj;
        return java.util.Objects.equals(this.ipv4, other.ipv4);
    }
    
    /**
     * Renvoie l'adresse IP publique du réseau local
     * @return Retourne l'adresse IP publique du réseau local
     */
    public static IPv4 getPublicIP(){
        try {
            java.net.URL whatismyip     = new java.net.URL("http://checkip.amazonaws.com");
            java.io.BufferedReader in   = new java.io.BufferedReader(new java.io.InputStreamReader(whatismyip.openStream()));
            
            String ip = in.readLine(); //you get the IP as a String
            return new IPv4(ip);
        } catch (java.net.UnknownHostException ex) {
            return null;
        } catch (java.net.MalformedURLException ex) {
            java.util.logging.Logger.getLogger(IPv4.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (java.io.IOException ex) {
            java.util.logging.Logger.getLogger(IPv4.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        return null;
    }
    
    /**
     * Renvoie l'adresse ip pricipale de cet équipement
     * @return Retourne une adresse ip locale
     */
    public static IPv4 getMainLocalIPv4(){
        Network n = new Network();
        java.util.List<NetworkCard> lnc = n.getHardwareList();
        for(int i=0;i<lnc.size();i++){
            NetworkCard nc = lnc.get(i);
            if(nc.isUp() && !nc.isLoopback() && nc.getIpCards() != null && !nc.getIpCards().isEmpty()){
                java.util.List<IPCard> lipc = nc.getIpCards();
                if(lipc.size()<=0) return null;
                else{
                    return lipc.get(0).getIp();
                }
            }
        }
        return null;
    }
    // </editor-fold>
    
    
    
    // <editor-fold defaultstate="collapsed" desc="METHODE PRIVATE">
    /**
     * Vérifie le format de l'adresse IP.
     * @param ipv4 Correspond à l'adresse ip à tester
     * @throws InvalidAddressException Correspond à l'exception levée si l'adresse est invalide
     */
    private static void verifyFormat(String ipv4) throws InvalidAddressException {
        java.util.StringTokenizer st = new java.util.StringTokenizer(ipv4, ".");
        
        if (st.countTokens() != 4) 
            throw new InvalidAddressException("Your IPv4 address must have the format: X.X.X.X");
        
        while (st.hasMoreTokens()) {
            String temp = st.nextToken();
            if (temp.length() < 1 || temp.length() > 3) throw new InvalidAddressException("Your IPv4 address must have the format: X.X.X.X with (X -> [0-255])");
            
            for(int i=0;i<temp.length();i++){
                java.util.regex.Pattern p = java.util.regex.Pattern.compile("[0-9]{1}");
                java.util.regex.Matcher matcher = p.matcher(""+temp.charAt(i));
                if(!matcher.find()) throw new InvalidAddressException("Character not authorized in your IPv4 address");
            }
            
            int number = Integer.parseInt(temp);
            if(number < 0 || 255 < number){
                throw new InvalidAddressException("Your IPv4 address must have the format: X.X.X.X (X -> [0-255])");
            }
        }
    }
    
    /**
     * Renvoie l'adresse IP converti en chaine de caractères
     * @param ipv4 Correspond à l'adresse IP sous forme d'un tableau de bytes
     * @return Retourne l'adresse IP converti en chaine de caractères
     */
    private static String convertToString(byte[] ipv4) {
        if (!(ipv4 instanceof byte[])) {
            throw new InvalidAddressException("Not a byte[]");
        }
        byte[] a = (byte[]) ipv4;
        if (a.length != 4) {
            throw new InvalidAddressException("Length != 4");
        }
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            int b = a[i];
            if (b < 0) {
                b += 256;
            }
            builder.append(String.valueOf(b));
            if (i < 3) {
                builder.append('.');
            }
        }
        return builder.toString();
    }
    
    /**
     * Renvoie l'adresse IP converti en tableau de bytes
     * @param ipv4 Correspond à l'adresse IP sous forme d'une chaîne de caractères
     * @return Retourne l'adresse IP converti en tableau de bytes
     */
    private static byte[] convertToBytes(String ipv4) {
        java.util.StringTokenizer tokenizer = new java.util.StringTokenizer(ipv4, ".");
        byte[] a = new byte[4];
        for (int i = 0; i < 4; i++) {
            int b = 0;
            if (!tokenizer.hasMoreTokens()) {
                throw new InvalidAddressException("Too few bytes");
            }
            try {
                b = Integer.parseInt(tokenizer.nextToken());
            } catch (NumberFormatException e) {
                throw new InvalidAddressException("Not an integer");
            }
            if (b < 0 || b >= 256) {
                throw new InvalidAddressException("Byte out of range");
            }
            a[i] = (byte) b;
        }
        if (tokenizer.hasMoreTokens()) {
            throw new InvalidAddressException("Too many bytes");
        }
        return a;
    }
    // </editor-fold>
    
    
    
}