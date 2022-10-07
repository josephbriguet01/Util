/*
 * Copyright (C) JasonPercus Systems, Inc - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by JasonPercus, 12/2021
 */
package com.jasonpercus.util;



import com.sun.jna.Native;
import com.sun.jna.Structure;
import com.sun.jna.win32.StdCallLibrary;



/**
 * Cette classe permet d'appeler des méthodes de la librairie c++ Kernel32.dll. Attention: Cette classe est compatible seulement Windows
 * @see Native Utilise JNA
 * @author JasonPercus
 * @version 1.0
 */
public class WinBatteryInfo implements java.io.Serializable, Comparable<WinBatteryInfo> {



//ATTRIBUTS
    /**
     * Détermine si la batterie est branchée au secteur ou pas
     */
    private final ACLineStatus status;

    /**
     * Correspond au status de la batterie
     */
    private final BatteryFlag flag;

    /**
     * Correspond au pourcentage de la batterie
     */
    private final int percent;

    /**
     * Correspond au nombre de secondes restantes avant que la batterie soit déchargée
     */
    private final long lifeTime;

    /**
     * Correspond au nombre de secondes restantes avant que la batterie soit déchargée lorsque la batterie est pleine
     */
    private final long fullLifeTime;



//CONSTRUCTOR
    /**
     * Crée un objet {@link WinBatteryInfo}
     * @param status Détermine si la batterie set branchée au secteur ou pas
     * @param flag Correspond au status de la batterie
     * @param percent Correspond au pourcentage de la batterie
     * @param lifeTime Correpsond au nombre de secondes restantes avant que la batterie soit déchargée
     * @param fullLifeTime Correpsond au nombre de secondes restantes avant que la batterie soit déchargée lorsque la batterie est pleine
     */
    private WinBatteryInfo(byte status, byte flag, int percent, long lifeTime, long fullLifeTime) {
        switch (status) {
            case (0):
                this.status      = ACLineStatus.OFFLINE;
                break;
            case (1):
                this.status      = ACLineStatus.ONLINE;
                break;
            default:
                this.status      = ACLineStatus.UNKNOWN;
                break;
        }
        switch (flag) {
            case (1):
                this.flag        = BatteryFlag.HIGH;
                break;
            case (2):
                this.flag        = BatteryFlag.LOW;
                break;
            case (4):
                this.flag        = BatteryFlag.CRITICAL;
                break;
            case (8):
                this.flag        = BatteryFlag.CHARGING;
                break;
            case ((byte) 128):
                this.flag        = BatteryFlag.NO_SYSTEM_BATTERY;
                break;
            default:
                this.flag        = BatteryFlag.UNKNOWN;
                break;
        }
        this.percent             = (percent == (byte) 255) ? -1 : percent;
        this.lifeTime            = lifeTime;
        this.fullLifeTime        = fullLifeTime;
    }

    
    
//GETTERS
    /**
     * Renvoie si oui ou non la batterie est branchée au secteur ou pas
     * @return Retourne {@link ACLineStatus#ONLINE} si c'est le cas, {@link ACLineStatus#OFFLINE} si ce n'est pas le cas
     */
    public ACLineStatus getStatus() {
        return status;
    }

    /**
     * Renvoie le status de la batterie
     * @return Retourne le status de la batterie
     */
    public BatteryFlag getFlag() {
        return flag;
    }

    /**
     * Renvoie le pourcentage de la batterie (ou -1 si inconnu)
     * @return Retourne le pourcentage de la batterie
     */
    public int getPercent() {
        return percent;
    }

    /**
     * Renvoie le nombre de secondes restantes avant que la batterie soit déchargée (ou -1 si inconnu)
     * @return Retourne le nombre de secondes restantes avant que la batterie soit déchargée
     */
    public long getLifeTime() {
        return lifeTime;
    }

    /**
     * Renvoie le nombre de secondes restantes avant que la batterie soit déchargée lorsque la batterie est pleine (ou -1 si inconnu)
     * @return Retourne le nombre de secondes restantes avant que la batterie soit déchargée lorsque la batterie est pleine
     */
    public long getFullLifeTime() {
        return fullLifeTime;
    }

    
    
//METHODES PUBLICS
    /**
     * Renvoie le hashCode de l'objet {@link WinBatteryInfo}
     * @return Retourne le hashCode de l'objet {@link WinBatteryInfo}
     */
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + java.util.Objects.hashCode(this.status);
        hash = 97 * hash + java.util.Objects.hashCode(this.flag);
        hash = 97 * hash + this.percent;
        hash = 97 * hash + (int) (this.lifeTime ^ (this.lifeTime >>> 32));
        hash = 97 * hash + (int) (this.fullLifeTime ^ (this.fullLifeTime >>> 32));
        return hash;
    }

    /**
     * Détermine si deux objets {@link WinBatteryInfo} sont identiques
     * @param obj Correspond au second objet {@link WinBatteryInfo} à comparer au courant
     * @return Retourne true s'ils sont identiques, sinon false
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final WinBatteryInfo other = (WinBatteryInfo) obj;
        if (this.status != other.status) {
            return false;
        }
        if (this.flag != other.flag) {
            return false;
        }
        if (this.percent != other.percent) {
            return false;
        }
        if (this.lifeTime != other.lifeTime) {
            return false;
        }
        return this.fullLifeTime == other.fullLifeTime;
    }
    
    /**
     * Renvoie l'objet {@link WinBatteryInfo} sous la forme d'une chaîne de caractères
     * @return Retourne l'objet {@link WinBatteryInfo} sous la forme d'une chaîne de caractères
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName()).append("{");
        sb.append("ACLineStatus: ").append(status).append(", ");
        sb.append("Battery Flag: ").append(flag).append(", ");
        sb.append("Battery Life: ").append(percent).append(", ");
        sb.append("Battery Left: ").append(lifeTime).append(", ");
        sb.append("Battery Full: ").append(fullLifeTime).append("}");
        return sb.toString();
    }

    /**
     * Compare deux objets {@link WinBatteryInfo} entre-eux
     * @param o Correspond au second objet à comparer au courant
     * @return Retourne le résultat de la comparaison
     */
    @Override
    public int compareTo(WinBatteryInfo o) {
        if(o == null) return -1;
        else{
            if(this.percent == o.percent) return 0;
            else if(this.percent == -1 && o.percent > -1) return 1;
            else if(this.percent > -1 && o.percent == -1) return -1;
            else if(this.percent > o.percent) return 1;
            else return -1;
        }
    }

    
    
//METHODE PUBLIC STATIC
    /**
     * Renvoie un objet {@link WinBatteryInfo} qui représente l'état de la batterie windows à cet instant
     * @return Retourne un objet {@link WinBatteryInfo} qui représente l'état de la batterie windows à cet instant
     */
    public static WinBatteryInfo get(){
        if(!OS.IS_WINDOWS) 
            throw new OSNotSupportedException(WinBase.class.getSimpleName()+" class only works for Windows systems !");
        
        SYSTEM_POWER_STATUS sps = new SYSTEM_POWER_STATUS();
        if(WinBase.INSTANCE.GetSystemPowerStatus(sps))
            return new WinBatteryInfo(sps.ACLineStatus, sps.BatteryFlag, sps.BatteryLifePercent, sps.BatteryLifeTime, sps.BatteryFullLifeTime);
        else
            return null;
    }

    
    
//ENUM
    /**
     * Cette énumération, énumère si oui ou non la batterie est branchée au secteur
     * @author JasonPercus
     * @version 1.0
     */
    public enum ACLineStatus {
        
        
        
    //CONSTANTES
        /**
         * La batterie n'est pas branchée au secteur (elle se décharge)
         */
        OFFLINE((byte)0),
        
        /**
         * La batterie est branchée au secteur (elle se charge)
         */
        ONLINE((byte)1),
        
        /**
         * Statut inconnu
         */
        UNKNOWN((byte)255);

        
        
    //ATTRIBUT
        /**
         * Correspond à la valeur qui détermine si la batterie est branchée au secteur
         */
        private final byte status;

        
        
    //CONSTRUCTOR
        /**
         * Crée un objet ACLineStatus
         * @param status Correspond à la valeur qui détermine si la batterie est branchée au secteur
         */
        private ACLineStatus(byte status) {
            this.status = status;
        }
        
        
        
    }

    /**
     * Cette énumération, énumère l'état de la batterie
     * @author JasonPercus
     * @version 1.0
     */
    public enum BatteryFlag {
        
        
        
    //CONSTANTES
        /**
         * Élevé : la capacité de la batterie est supérieure à 66 %
         */
        HIGH((byte)1),
        
        /**
         * Faible : la capacité de la batterie est inférieure à 33 %
         */
        LOW((byte)2),
        
        /**
         * Critique : la capacité de la batterie est inférieure à 5 %
         */
        CRITICAL((byte)4),
        
        /**
         * Mise en charge
         */
        CHARGING((byte)8),
        
        /**
         * Pas de batterie système
         */
        NO_SYSTEM_BATTERY((byte)128),
        
        /**
         * État inconnu : impossible de lire les informations sur l'indicateur de batterie
         */
        UNKNOWN((byte)255);

        
        
    //ATTRIBUT
        /**
         * Correspond à l'état de la batterie
         */
        private final byte flag;

        
        
        /**
         * Crée un objet BatteryFlag
         * @param flag Correspond à l'état de la batterie
         */
        private BatteryFlag(byte flag) {
            this.flag = flag;
        }
        
        
        
    }


    
//CLASS
    /**
     * Cette classe permet de stocker les données bruts reçu de Kernel32
     * @author JasonPercus
     * @version 1.0
     */
    protected static class SYSTEM_POWER_STATUS extends Structure {
        
        
        
    //ATTRIBUTS
        /**
         * Détermine si la batterie est branchée au secteur ou pas
         */
        public byte ACLineStatus;
        
        /**
         * Correspond au status de la batterie
         */
        public byte BatteryFlag;
        
        /**
         * Correspond au pourcentage de la batterie
         */
        public byte BatteryLifePercent;
        
        /**
         * Aucune données
         */
        public byte Reserved1;
        
        /**
         * Correspond au nombre de secondes restantes avant que la batterie soit déchargée
         */
        public int BatteryLifeTime;
        
        /**
         * Correspond au nombre de secondes restantes avant que la batterie soit déchargée lorsque la batterie est pleine
         */
        public int BatteryFullLifeTime;

        
        
    //METHODE PROTECTED
        /**
         * Définit l'ordre des attributs de cette classe
         * @return Retourne l'ordre des attributs de cette classe
         */
        @Override
        protected java.util.List<String> getFieldOrder() {
            java.util.List<String> fields = new java.util.ArrayList<>();
            fields.add("ACLineStatus");
            fields.add("BatteryFlag");
            fields.add("BatteryLifePercent");
            fields.add("Reserved1");
            fields.add("BatteryLifeTime");
            fields.add("BatteryFullLifeTime");
            return fields;
        }
        
        
        
    }
    
    
    
//INTERFACE
    /**
     * Cette interface permet de faire le lien direct entre la dll Kernel32 et Java. Grâce à JNA
     * @author JasonPercus
     * @version 1.0
     */
    private interface WinBase extends StdCallLibrary {
        
        
        
    //ATTRIBUT
        /**
         * Correspond au lien direct entre cette interface et la classe WinBase de windows
         */
        WinBase INSTANCE = (WinBase) Native.loadLibrary("Kernel32", WinBase.class);

        
        
    //METHODE
        /**
         * Récupère les infos batterie
         * @param p Correspond à l'objet qui contiendra les infos batterie
         * @return Retourne true si les infos ont été récupérés, sinon false
         */
        boolean GetSystemPowerStatus(SYSTEM_POWER_STATUS p);
        
        
        
    }
    
    
    
}