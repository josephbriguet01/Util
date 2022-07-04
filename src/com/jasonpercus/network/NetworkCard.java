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
 * Cette classe permet d'avoir des représentations de cartes réseaux d'un PC
 * @author Briguet
 * @version 1.0
 */
public class NetworkCard implements java.io.Serializable, Comparable<NetworkCard>, Cloneable {
    
    
    
    // <editor-fold defaultstate="collapsed" desc="SERIAL_VERSION_UID">
    /**
     * Correspond au numéro de série qui identifie le type de dé/sérialization utilisé pour l'objet
     */
    private static final long serialVersionUID = 1L;
    // </editor-fold>
    
    
    
    // <editor-fold defaultstate="collapsed" desc="ATTRIBUTS">
    /**
     * Correspond à l'adresse MAC de cette carte
     */
    private MACAddress   macAddress;
    /**
     * Correspond au nom et description de la carte
     */
    private String       nameCard;
    /**
     * Correspond au nom de l'interface de la carte
     */
    private String       nameEth;
    /**
     * Correspond à l'index de la carte
     */
    private int          cardNumber;
    /**
     * Détermine si la carte est en activité ou pas
     */
    private boolean      up;
    /**
     * Détermine si la carte est virtuelle ou pas
     */
    private boolean      virtual;
    /**
     * Détermine si l'interface réseau est une interface de bouclage
     */
    private boolean      loopback;
    /**
     * Détermine si l'interface réseau est une interface point à point
     */
    private boolean      pointToPoint;
    /**
     * Correspond à la liste des adresses IP
     */
    private java.util.List<IPCard> ipCards;
    /**
     * Correspond à une interface réseau
     */
    private transient java.net.NetworkInterface ni;
    // </editor-fold>

    
    
    // <editor-fold defaultstate="collapsed" desc="CONSTRUCTORS">
    /**
     * Crée une carte réseau
     * @param nameCard Correspond au nom et description de la carte
     * @param nameEth Correspond au nom de l'interface
     * @param cardNumber Correspond à l'index de la carte
     */
    public NetworkCard(String nameCard, String nameEth, int cardNumber) {
        this.nameCard = nameCard;
        this.nameEth = nameEth;
        this.cardNumber = cardNumber;
        this.ipCards = new java.util.ArrayList<>();
    }

    /**
     * Crée une carte réseau
     * @param nameCard Correspond au nom et description de la carte
     * @param nameEth Correspond au nom de l'interface
     * @param cardNumber Correspond à l'index de la carte
     * @param ipCards Correspond à la liste des adresses IP
     */
    public NetworkCard(String nameCard, String nameEth, int cardNumber, java.util.List<IPCard> ipCards) {
        this.nameCard = nameCard;
        this.nameEth = nameEth;
        this.cardNumber = cardNumber;
        this.ipCards = ipCards;
    }
    
    /**
     * Crée une carte réseau
     * @param macAddress Correspond à l'adresse MAC de la carte
     * @param nameCard Correspond au nom et description de la carte
     * @param nameEth Correspond au nom de l'interface
     * @param cardNumber Correspond à l'index de la carte
     */
    public NetworkCard(MACAddress macAddress, String nameCard, String nameEth, int cardNumber) {
        this.macAddress = macAddress;
        this.nameCard = nameCard;
        this.nameEth = nameEth;
        this.cardNumber = cardNumber;
        this.ipCards = new java.util.ArrayList<>();
    }
    
    /**
     * Crée une carte réseau
     * @param macAddress Correspond à l'adresse MAC de la carte
     * @param nameCard Correspond au nom et description de la carte
     * @param nameEth Correspond au nom de l'interface
     * @param cardNumber Correspond à l'index de la carte
     * @param ipCards Correspond à la liste des adresses IP
     */
    public NetworkCard(MACAddress macAddress, String nameCard, String nameEth, int cardNumber, java.util.List<IPCard> ipCards) {
        this.macAddress = macAddress;
        this.nameCard = nameCard;
        this.nameEth = nameEth;
        this.cardNumber = cardNumber;
        this.ipCards = ipCards;
    }
    // </editor-fold>
    
    
    
    // <editor-fold defaultstate="collapsed" desc="METHODES PUBLICS">
    /**
     * Ajoute une IPCard
     * @param ipCard Correspond à l'IPCard à ajouter
     */
    public void addIPCard(IPCard ipCard){
        this.ipCards.add(ipCard);
    }

    /**
     * Renvoie l'adresse MAC de la carte réseau
     * @return Retourne l'adresse MAC de la carte réseau
     */
    public MACAddress getMacAddress() {
        return macAddress;
    }

    /**
     * Modifie l'adresse MAC de la carte réseau
     * @param macAddress Correspond à la nouvelle adresse MAC
     */
    public void setMacAddress(MACAddress macAddress) {
        this.macAddress = macAddress;
    }

    /**
     * Renvoie le nom et description de la carte réseau
     * @return Retourne le nom et description de la carte réseau
     */
    public String getNameCard() {
        return nameCard;
    }

    /**
     * Modifie le nom et description de la carte réseau
     * @param nameCard Correspond au nouveau nom de la carte réseau
     */
    public void setNameCard(String nameCard) {
        this.nameCard = nameCard;
    }

    /**
     * Renvoie le nom de l'interface de la carte réseau
     * @return Retourne le nom de l'interface de la carte réseau
     */
    public String getNameEth() {
        return nameEth;
    }

    /**
     * Correspond au nouveau de l'interface de la carte réseau
     * @param nameEth Correspond au nouveau nom de l'interface de la carte réseau
     */
    public void setNameEth(String nameEth) {
        this.nameEth = nameEth;
    }

    /**
     * Renvoie l'index de la carte réseau
     * @return Retourne l'index de la carte réseau
     */
    public int getCardNumber() {
        return cardNumber;
    }

    /**
     * Modifie l'index de la carte réseau
     * @param cardNumber Correspond au nouvel index de la carte réseau
     */
    public void setCardNumber(int cardNumber) {
        this.cardNumber = cardNumber;
    }

    /**
     * Renvoie la liste des ip de la carte réseau
     * @return Retourne la liste des ip de la carte réseau
     */
    public java.util.List<IPCard> getIpCards() {
        return ipCards;
    }

    /**
     * Modifie la liste des ip de la carte réseau
     * @param ipCards Correspond à la nouvelle liste d'ip de la carte réseau
     */
    public void setIpCards(java.util.List<IPCard> ipCards) {
        this.ipCards = ipCards;
    }
    
    /**
     * Renvoie true si la carte est en activité, sinon false (Attention, si l'interface change d'état, il est possible que l'objet n'en ressente pas les effets. Cela se produit généralement si cet objet est sérialisé dans un état précis, puis que l'interface change d'état)
     * @return Retourne true si la carte est en activité, sinon false
     */
    public boolean isUp(){
        if(this.ni != null){
            try {
                up = this.ni.isUp();
            } catch (java.net.SocketException ex) {
                up = false;
            }
        }
        return up;
    }
    
    /**
     * Renvoie si oui ou non la carte est virtuelle (Attention, si l'interface change d'état, il est possible que l'objet n'en ressente pas les effets. Cela se produit généralement si cet objet est sérialisé dans un état précis, puis que l'interface change d'état)
     * @return Retourne true si la carte est virtuelle, sinon false
     */
    public boolean isVirtual(){
        if(this.ni != null){
            virtual = this.ni.isVirtual();
        }
        return virtual;
    }
    
    /**
     * Renvoie si une interface réseau est une interface de bouclage (Attention, si l'interface change d'état, il est possible que l'objet n'en ressente pas les effets. Cela se produit généralement si cet objet est sérialisé dans un état précis, puis que l'interface change d'état)
     * @return Retourne si une interface réseau est une interface de bouclage
     */
    public boolean isLoopback(){
        if(this.ni != null){
            try {
                loopback = this.ni.isLoopback();
            } catch (java.net.SocketException ex) {
                loopback = false;
            }
        }
        return loopback;
    }
    
    /**
     * Renvoie si l'interface réseau est une interface point à point. Une interface point à point typique serait une connexion PPP via un modem (Attention, si l'interface change d'état, il est possible que l'objet n'en ressente pas les effets. Cela se produit généralement si cet objet est sérialisé dans un état précis, puis que l'interface change d'état)
     * @return Retourne si l'interface réseau est une interface point à point.
     */
    public boolean isPointToPoint(){
        if(this.ni != null){
            try {
                pointToPoint = this.ni.isPointToPoint();
            } catch (java.net.SocketException ex) {
                pointToPoint = false;
            }
        }
        return pointToPoint;
    }

    /**
     * Affiche une carte réseau
     * @return Retourne une chaîne de caractère représentant la carte réseau
     */
    @Override
    public String toString() {
        return "NetworkCard(" + "@MAC: " + macAddress + ", name: " + nameCard + ", nameEth: " + nameEth + ", index: " + cardNumber + ", ipCards: " + ipCards + ")";
    }

    /**
     * Renvoie le hashCode de la carte réseau
     * @return Retourne le hashCode de la carte réseau
     */
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + java.util.Objects.hashCode(this.macAddress);
        hash = 53 * hash + this.cardNumber;
        return hash;
    }

    /**
     * Détermine si deux cartes réseaux sont identiques
     * @param obj Correspond à la seconde carte réseau à comparer à la courante
     * @return Retourne true, si elles sont identiques, sinon false
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
        final NetworkCard other = (NetworkCard) obj;
        if (this.cardNumber != other.cardNumber) {
            return false;
        }
        return java.util.Objects.equals(this.macAddress, other.macAddress);
    }

    /**
     * Clone cette NetworkCard
     * @return Retourne le clone de la NetworkCard
     * @throws CloneNotSupportedException Correspond à l'adresse levée s'il y a une erreur de clonage
     */
    @Override
    @SuppressWarnings("CloneDoesntCallSuperClone")
    protected Object clone() throws CloneNotSupportedException {
        NetworkCard nc  = new NetworkCard(macAddress, nameCard, nameEth, cardNumber, ipCards);
        nc.up           = up;
        nc.virtual      = virtual;
        nc.loopback     = loopback;
        nc.pointToPoint = pointToPoint;
        return nc;
    }
    
    /**
     * Renvoie le résultat de la méthode compareTo()
     * @param nc Correspond à l'objet à comparer
     * @return Retourne le résultat de la méthode compareTo()
     */
    @Override
    public int compareTo(NetworkCard nc) {
        if(cardNumber < nc.cardNumber) return -1;
        else if(cardNumber > nc.cardNumber) return 1;
        else return 0;
    }
    // </editor-fold>
    
    
    
    // <editor-fold defaultstate="collapsed" desc="METHODES PROTECTED">
    /**
     * Modifie l'objet NetworkInterface
     * @param ni Correspond à un objet NetworkInterface
     */
    protected void setNetworkInterface(java.net.NetworkInterface ni){
        this.ni = ni;
        isUp();
        isVirtual();
        isLoopback();
        isPointToPoint();
    }
    // </editor-fold>
    
    
    
}