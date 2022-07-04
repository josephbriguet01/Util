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
 * Cette classe permet d'encapsuler les résultats des scans...
 * @author Briguet
 * @version 1.0
 */
public class ResultScan implements java.io.Serializable, Comparable<ResultScan>, Cloneable {
    
    
    
    // <editor-fold defaultstate="collapsed" desc="SERIAL_VERSION_UID">
    /**
     * Correspond au numéro de série qui identifie le type de dé/sérialization utilisé pour l'objet
     */
    private static final long serialVersionUID = 1L;
    // </editor-fold>
    
    
    
    // <editor-fold defaultstate="collapsed" desc="ATTRIBUTS">
    /**
     * Correspond à un tableau d'équipement sur le réseau LAN
     */
    private Equipment[] equipments;
    /**
     * Correspond à un tableau de port d'un équipement sur le réseau LAN
     */
    private EquipmentPort[] equipmentPorts;
    /**
     * Correspond à la date de départ d'un scanner
     */
    private java.util.Date start;
    /**
     * Correspond à la date de fin d'un scanner
     */
    private java.util.Date end;
    /**
     * Correspond au nombre de ms de traitement d'un scan
     */
    private long ms;
    // </editor-fold>
    
    
    
    // <editor-fold defaultstate="collapsed" desc="CONSTRUTORS">
    /**
     * Crée un objet ResultScan
     */
    private ResultScan(){
    
    }
    
    /**
     * Crée un objet ResultScan
     * @param e Correspond à un tableau d'équipement sur le réseau LAN
     * @param start Correspond à la date de départ d'un scanner
     * @param end Correspond à la date de fin d'un scanner
     */
    protected ResultScan(Equipment[] e, java.util.Date start, java.util.Date end) {
        this.equipments = e;
        this.start = start;
        this.end = end;
        this.ms = end.getTime() - start.getTime();
    }

    /**
     * Crée un objet ResultScan
     * @param ep Correspond à un tableau de port d'un équipement sur le réseau LAN
     * @param start Correspond à la date de départ d'un scanner
     * @param end Correspond à la date de fin d'un scanner
     */
    protected ResultScan(EquipmentPort[] ep, java.util.Date start, java.util.Date end) {
        this.equipmentPorts = ep;
        this.start = start;
        this.end = end;
        this.ms = end.getTime() - start.getTime();
    }
    // </editor-fold>

    
    
    // <editor-fold defaultstate="collapsed" desc="METHODES PUBLICS">
    /**
     * Renvoie la liste des ports d'un équipement réseau
     * @return Retourne la liste des ports d'un équipement réseau
     */
    public EquipmentPort[] getEquipmentPorts() {
        return equipmentPorts;
    }

    /**
     * Renvoie la liste des équipements réseau sur le LAN
     * @return Retourne la liste des équipements réseau sur le LAN
     */
    public Equipment[] getEquipments() {
        return equipments;
    }

    /**
     * Renvoie la date de départ d'un scanner
     * @return Retourne la date de départ d'un scanner
     */
    public java.util.Date getStart() {
        return start;
    }

    /**
     * Renvoie la date de fin d'un scanner
     * @return Retourne la date de fin d'un scanner
     */
    public java.util.Date getEnd() {
        return end;
    }

    /**
     * Renvoie le nombre de ms de traitement d'un scanner
     * @return Retourne le nombre de ms de traitement d'un scanner
     */
    public long getMs() {
        return ms;
    }

    /**
     * Modifie la liste des ports d'un équipement réseau
     * @param equipmentPorts Correspond à une nouvelle liste de port
     */
    public void setEquipmentPorts(EquipmentPort[] equipmentPorts) {
        this.equipmentPorts = equipmentPorts;
    }

    /**
     * Modifie la liste des équipements réseau sur le LAN
     * @param equipments Correspond à la nouvelle liste d'équipements
     */
    public void setEquipments(Equipment[] equipments) {
        this.equipments = equipments;
    }

    /**
     * Modifie la date de départ d'un scan
     * @param start Correspond à la nouvelle date d'un scan
     */
    public void setStart(java.util.Date start) {
        this.start = start;
    }

    /**
     * Modifie la date de fin d'un scan
     * @param end Correspond à la nouvelle date d'un scan
     */
    public void setEnd(java.util.Date end) {
        this.end = end;
    }

    /**
     * Modifie le nombre de ms de traitement d'un scan
     * @param ms Correspond au nouveau nombre de ms d'un scan
     */
    public void setMs(long ms) {
        this.ms = ms;
    }

    /**
     * Renvoie l'objet sous la forme d'une chaîne de caractères
     * @return Retourne l'objet sous la forme d'une chaîne de caractères
     */
    @Override
    public String toString() {
        if(equipments != null){
            return "Scan{" + "scannerInfos=" + java.util.Arrays.toString(equipments) + ", ms=" + ms + '}';
        }else if (equipmentPorts != null){
            return "Scan{" + "portInfos=" + java.util.Arrays.toString(equipmentPorts) + ", ms=" + ms + '}';
        }else{
            return "Scan{ms=" + ms + '}';
        }
    }

    /**
     * Renvoie le résultat de la méthode compareTo()
     * @param rs Correspond à l'objet à comparer
     * @return Retourne le résultat de la méthode compareTo()
     */
    @Override
    public int compareTo(ResultScan rs) {
        if(start.getTime()<rs.start.getTime()) return -1;
        else if(start.getTime()>rs.start.getTime()) return 1;
        else{
            if(end.getTime()<rs.end.getTime()) return -1;
            else if(end.getTime()>rs.end.getTime()) return 1;
            else return 0;
        }
    }

    /**
     * Clone les résultat du scanner
     * @return Retourne le clone des résultats
     * @throws CloneNotSupportedException Correspond à l'adresse levée s'il y a une erreur de clonage
     */
    @Override
    @SuppressWarnings("CloneDoesntCallSuperClone")
    public Object clone() throws CloneNotSupportedException {
        ResultScan rs       = new ResultScan();
        rs.equipments       = (Equipment[]) rs.equipments.clone();
        rs.equipmentPorts   = (EquipmentPort[]) rs.equipmentPorts.clone();
        rs.start            = (java.util.Date) start.clone();
        rs.end              = (java.util.Date) end.clone();
        rs.ms               = ms;
        return rs;
    }

    /**
     * Renvoie le hashCode du résultat
     * @return Retourne le hashCode du résultat
     */
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 47 * hash + java.util.Arrays.deepHashCode(this.equipments);
        hash = 47 * hash + java.util.Arrays.deepHashCode(this.equipmentPorts);
        hash = 47 * hash + java.util.Objects.hashCode(this.start);
        hash = 47 * hash + java.util.Objects.hashCode(this.end);
        return hash;
    }

    /**
     * Détermine si deux résultats de scan sont identiques
     * @param obj Correspond au second résultat de scan à comparer au courant
     * @return Retourne true, s'ils sont identiques, sinon false
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
        final ResultScan other = (ResultScan) obj;
        if (!java.util.Arrays.deepEquals(this.equipments, other.equipments)) {
            return false;
        }
        if (!java.util.Arrays.deepEquals(this.equipmentPorts, other.equipmentPorts)) {
            return false;
        }
        if (!java.util.Objects.equals(this.start, other.start)) {
            return false;
        }
        return java.util.Objects.equals(this.end, other.end);
    }
    // </editor-fold>
    
    
    
}