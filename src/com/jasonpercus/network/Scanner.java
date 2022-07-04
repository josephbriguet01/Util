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
 * Cette classe permet de scanner les équipements d'un réseau LAN ainsi que connaitre les ports ouverts ou pas d'un équipement...
 * @author Briguet
 * @version 1.0
 */
public class Scanner {
    
    
    
    // <editor-fold defaultstate="collapsed" desc="CONSTRUCTOR">
    /**
     * Crée un objet Scanner par défaut
     */
    public Scanner() {
        
    }
    // </editor-fold>
    
    
    
    // <editor-fold defaultstate="collapsed" desc="METHODES PUBLICS">
    /**
     * Renvoie la liste des équipements du réseau LAN
     * @param ipNetwork Correspond à l'adresse IP du réseau local avec le masque 255.255.255.0
     * @param options Correspond aux éventuelles options. L'option /a renvoie que les équipements présents sur le réseau. L'option /n renvoie que les équipements qui n'existe pas
     * @return Retourne la liste des équipements
     */
    @SuppressWarnings("SleepWhileInLoop")
    public ResultScan listLanHardware(IPv4 ipNetwork, String... options){
        return listLanHardware(ipNetwork, MaskIPv4.setCIDR("/24"), options);
    }
    
    /**
     * Renvoie la liste des équipements du réseau LAN
     * @param ipNetwork Correspond à l'adresse IP du réseau local
     * @param mask Correspond au masque du réseau local
     * @param options Correspond aux éventuelles options. L'option /a renvoie que les équipements présents sur le réseau. L'option /n renvoie que les équipements qui n'existe pas
     * @return Retourne la liste des équipements
     */
    @SuppressWarnings("SleepWhileInLoop")
    public ResultScan listLanHardware(IPv4 ipNetwork, MaskIPv4 mask, String... options){
        byte[] ip;

        PoolIPv4 pool = Network.getPoolIpv4(ipNetwork, mask);
        int count = (int) pool.count();
        
        Equipment[] si = new Equipment[count];
        java.util.Date d1 = new java.util.Date();
        
        //Boucle sur l'ensemble du masque réseau
        int j = 0;
        for(IPv4 i : pool){
            try{
                ip = i.toBytes();
            }catch(java.lang.NullPointerException e){
                throw new InvalidCombinaisonAddressException("Invalid combinaison between network IP and mask !");
            }
            try {
                java.net.InetAddress addr = java.net.InetAddress.getByAddress(ip);
                
                new Thread(new SearchHardware(j++, addr, si)).start();
                
            } catch (java.net.UnknownHostException e) {
                System.out.println(e.getMessage());
            }
        }
        while(containNull(si)){
            try {
                Thread.sleep(10);
            } catch (InterruptedException ex) {
                java.util.logging.Logger.getLogger(Scanner.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }
        }
        java.util.Date d2 = new java.util.Date();
        
        if(containOption(options, "/a") && containOption(options, "/n")){
            return new ResultScan(si, d1, d2);
        }
        
        if(containOption(options, "/a")){
            java.util.List<Equipment> tsi = new java.util.ArrayList<>();
            for (Equipment si1 : si) {
                if (si1.isAlive()) tsi.add(si1);
            }
            Equipment[] nsi = new Equipment[tsi.size()];
            for(int i=0;i<tsi.size();i++){
                nsi[i] = tsi.get(i);
            }
            return new ResultScan(nsi, d1, d2);
        }
        
        if(containOption(options, "/n")){
            java.util.List<Equipment> tsi = new java.util.ArrayList<>();
            for (Equipment si1 : si) {
                if (!si1.isAlive()) tsi.add(si1);
            }
            Equipment[] nsi = new Equipment[tsi.size()];
            for(int i=0;i<tsi.size();i++){
                nsi[i] = tsi.get(i);
            }
            return new ResultScan(nsi, d1, d2);
        }
        
        return new ResultScan(si, d1, d2);
    }
    
    /**
     * Renvoie si oui ou non un port est ouvert pour cet équipement
     * @param port Correspond au numéro de port dont on veut savoir s'il est ouvert
     * @return Retourne 0 si le port tcp et udp sont fermés, 1 si seul le port tcp est ouvert, 2 si seul le port udp est ouvert et 3 si les deux ports sont ouverts
     */
    public int portIsUsed(int port){
        boolean tcp = portTCPIsUsed(port);
        boolean udp = portUDPIsUsed(port);
        if(tcp && udp) return 3;
        else if(tcp && !udp) return 1;
        else if(!tcp && udp) return 2;
        else return 0;
    }
    
    /**
     * Renvoie si oui ou non un port TCP est ouvert pour cet équipement
     * @param port Correspond au numéro de port TCP dont on veut savoir s'il est ouvert
     * @return Retourne true si le port TCP est ouvert, sinon false
     */
    public boolean portTCPIsUsed(int port){
        java.net.ServerSocket ss = null;
        try {
            ss = new java.net.ServerSocket(port);
            ss.setReuseAddress(true);
            return false;
        } catch (java.io.IOException e) {
        } finally {
            if (ss != null) {
                try {
                    ss.close();
                } catch (java.io.IOException e) {
                }
            }
        }

        return true;
    }
    
    /**
     * Renvoie si oui ou non un port UDP est ouvert pour cet équipement
     * @param port Correspond au numéro de port UDP dont on veut savoir s'il est ouvert
     * @return Retourne true si le port UDP est ouvert, sinon false
     */
    @SuppressWarnings("ConvertToTryWithResources")
    public boolean portUDPIsUsed(int port){
        java.net.DatagramSocket ds = null;
        try {
            ds = new java.net.DatagramSocket(port);
            ds.setReuseAddress(true);
            return false;
        } catch (java.io.IOException e) {
        } finally {
            if (ds != null) {
                ds.close();
            }
        }

        return true;
    }
    
    /**
     * Renvoie la liste des ports ouverts ou fermés entre le port 0 et 1024 par défaut.
     * @param ip Correspond à l'adresse ip de l'équipement dont on veut connaitre la liste des ports
     * @param options Correspond aux éventuelles options. L'option /a renvoie que les ports ouverts. L'option /n renvoie que les ports fermés. L'option /m [int] définit le port de départ. L'option /M [int] définit le port de fin. L'option /udp renvoie que les ports UDP. L'option /tcp renvoie que les ports TCP. Par défaut il est renvoyé les ports TCP et UDP.
     * @return Retourne la liste des ports
     */
    @SuppressWarnings({"SleepWhileInLoop", "SleepWhileHoldingLock"})
    public ResultScan listPort(IPv4 ip, String... options){
        java.util.List<Boolean> sem = new java.util.ArrayList<>();
        
        int portMin = 0;
        int portMax = 1024;
        boolean atcp = true;
        boolean audp = true;
        
        if(containOption(options, "/tcp") && !containOption(options, "/udp")){
            audp = false;
        }
        
        if(!containOption(options, "/tcp") && containOption(options, "/udp")){
            atcp = false;
        }
        
        if(containOption(options, "/m")){
            int val = indexOfOption(options, "/m");
            String option = options[val];
            String opt = option.replace("/m", "").replace(" ", "");
            try{
                portMin = Integer.parseInt(opt);
                if(portMin < 0) portMin = 0;
            }catch(java.lang.NumberFormatException e){}
        }
        
        if(containOption(options, "/M")){
            int val = indexOfOption(options, "/M");
            String option = options[val];
            String opt = option.replace("/M", "").replace(" ", "");
            try{
                portMax = Integer.parseInt(opt)+1;
                if(portMax > 65536) portMax = 1024;
            }catch(java.lang.NumberFormatException e){}
        }
        
        EquipmentPort[] pis = new EquipmentPort[portMax-portMin];
        java.util.Date d1 = new java.util.Date();
        synchronized(sem){
            for(int i=0;i<pis.length;i++){
                while(sem.size()>=5){
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException ex) {
                        java.util.logging.Logger.getLogger(Scanner.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                    }
                }
                sem.add(Boolean.TRUE);
                new Thread(new SearchPort(portMin+i, pis, sem, portMin, atcp, audp)).start();
            }
        }
        while(containNull(pis)){
            try {
                Thread.sleep(10);
            } catch (InterruptedException ex) {
                java.util.logging.Logger.getLogger(Scanner.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }
        }
        java.util.Date d2 = new java.util.Date();
        if(containOption(options, "/a") && containOption(options, "/n")){
            return new ResultScan(pis, d1, d2);
        }else
            
        if (containOption(options, "/a")) {
            java.util.List<EquipmentPort> tpi = new java.util.ArrayList<>();
            for (EquipmentPort pi : pis) {
                if (pi.isAlive()) {
                    tpi.add(pi);
                }
            }
            EquipmentPort[] npi = new EquipmentPort[tpi.size()];
            for (int i = 0; i < tpi.size(); i++) {
                npi[i] = tpi.get(i);
            }
            return new ResultScan(npi, d1, d2);
        } else if (containOption(options, "/n")) {
            java.util.List<EquipmentPort> tpi = new java.util.ArrayList<>();
            for (EquipmentPort pi : pis) {
                if (!pi.isAlive()) {
                    tpi.add(pi);
                }
            }
            EquipmentPort[] npi = new EquipmentPort[tpi.size()];
            for (int i = 0; i < tpi.size(); i++) {
                npi[i] = tpi.get(i);
            }
            return new ResultScan(npi, d1, d2);
        } else {
            return new ResultScan(pis, d1, d2);
        }
    }
    
    /**
     * Renvoie un port TCP de libre à l'adresse IP
     * @return Retourne un port TCP libre
     */
    public int getFreePortTCP(){
        int nombreAleatoire = -1;
        
        do{
            int Min = 1025;
            int Max = 65535;
            nombreAleatoire = Min + (int)(Math.random() * ((Max - Min) + 1));
        }while(portTCPIsUsed(nombreAleatoire));
        return nombreAleatoire;
    }
    
    /**
     * Renvoie un port UDP de libre à l'adresse IP
     * @return Retourne un port UDP libre
     */
    public int getFreePortUDP(){
        int nombreAleatoire = -1;
        
        do{
            int Min = 1025;
            int Max = 65535;
            nombreAleatoire = Min + (int)(Math.random() * ((Max - Min) + 1));
        }while(portUDPIsUsed(nombreAleatoire));
        return nombreAleatoire;
    }
    // </editor-fold>
    
    
    
    // <editor-fold defaultstate="collapsed" desc="METHODES PRIVATES">
    /**
     * Renvoie si oui ou non une option existe dans un tableau d'options
     * @param options Correspond au tableau d'options
     * @param option Correspond à l'option que l'on cherche
     * @return Retourne true si l'option existe
     */
    private boolean containOption(String[] options, String option){
        if(options == null) return false;
        for (String option1 : options) {
            if (option1.indexOf(option) == 0) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Renvoie l'index d'une option dans un tableau d'options
     * @param options Correspond au tableau d'options
     * @param option Correspond à l'option que l'on cherche
     * @return Retourne l'index de l'option
     */
    private int indexOfOption(String[] options, String option){
        if(options == null) return -1;
        
        for(int i=0;i<options.length;i++){
            if(options[i].indexOf(option) == 0) return i;
        }
        return -1;
    }
    
    /**
     * Renvoie si oui ou non le tableau contient un valeur null
     * @param si Correspond au tableau dont on veut faire le test
     * @return Renvoie true, s'il existe une valeur null
     */
    private boolean containNull(Equipment[] si){
        synchronized(si){
            for (Equipment si1 : si) {
                if (si1 == null) {
                    return true;
                }
            }
            return false;
        }
    }
    
    /**
     * Renvoie si oui ou non le tableau contient un valeur null
     * @param pi Correspond au tableau dont on veut faire le test
     * @return Renvoie true, s'il existe une valeur null
     */
    private boolean containNull(EquipmentPort[] pi){
        synchronized(pi){
            for (EquipmentPort si1 : pi) {
                if (si1 == null) {
                    return true;
                }
            }
            return false;
        }
    }
    
    /**
     * Renvoie si l'équipement ayant l'adresse ip en paramètre existe sur le réseau
     * @param Ipv4Adr Correspond à l'adresse ip de l'équipement dont on veut connaitre l'existance
     * @return Retourne true s'il existe, sinon false
     */
    private boolean isAlive(String Ipv4Adr) {
        Process p1;
        boolean reachable = false;
        
        try {
            
            String command = null;
            
            if(isWindows()){
                command = "ping -n 1 " + Ipv4Adr;
            }else if(isUnix()){
                command = "ping -c 1 " + Ipv4Adr;
            }else if(isMac()){
                command = "ping -c 1 " + Ipv4Adr;
            }else if(isSolaris()){
                command = "ping -s " + Ipv4Adr + " 64 1";
            }
            
            if(command == null) return false;
            p1 = java.lang.Runtime.getRuntime().exec(command);
            int returnVal = p1.waitFor();
            reachable = (returnVal == 0);
        } catch (java.io.IOException | InterruptedException ex) {
            java.util.logging.Logger.getLogger(Scanner.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        return reachable;
    }
    
    /**
     * Renvoie si oui ou non le système est un OS Windows
     * @return Retourne true s'il s'agit d'un système Windows
     */
    private static boolean isWindows() {
        return (System.getProperty("os.name").toLowerCase().contains("win"));
    }

    /**
     * Renvoie si oui ou non le système est un OS Mac
     * @return Retourne true s'il s'agit d'un système Mac
     */
    private static boolean isMac() {
        return (System.getProperty("os.name").toLowerCase().contains("mac"));
    }

    /**
     * Renvoie si oui ou non le système est un OS Unix
     * @return Retourne true s'il s'agit d'un système Unix
     */
    private static boolean isUnix() {
        return (System.getProperty("os.name").toLowerCase().contains("nix") || System.getProperty("os.name").toLowerCase().contains("nux") || System.getProperty("os.name").toLowerCase().indexOf("aix") > 0);
    }

    /**
     * Renvoie si oui ou non le système est un OS Solaris
     * @return Retourne true s'il s'agit d'un système Solaris
     */
    private static boolean isSolaris() {
        return (System.getProperty("os.name").toLowerCase().contains("sunos"));
    }
    
    /**
     * Renvoie le nom du port (si celui-ci est connu)
     * @param port Correspond au numéro du port
     * @return Retourne le nom du port
     */
    private String getNamePort(int port){
        switch(port){
            case 7: return "Echo";
            case 9: return "Discard Protocol / Wake-on-LAN";
            case 11: return "systat service";
            case 13: return "Daytime protocol";
            case 15: return "netstat service";
            case 17: return "QOTD";
            case 18: return "MSP";
            case 19: return "CHARGEN";
            case 20: return "FTP (data transfer)";
            case 21: return "FTP (control)";
            case 22: return "SSH";
            case 23: return "Telnet protocol";
            case 25: return "SMTP";
            case 29: return "msg-icp";
            case 31: return "msg-auth";
            case 33: return "DSP";
            case 37: return "Time Protocol";
            case 38: return "RAP";
            case 39: return "RLP";
            case 42: return "Host Name Server Protocol";
            case 43: return "WHOIS protocol";
            case 47: return "ni-ftp";
            case 48: return "Digital Audit Daemon";
            case 49: return "Login Host Protocol";
            case 50: return "Remote Mail Checking Protocol";
            case 52: return "XNS";
            case 53: return "DNS";
            case 54: return "XNS Clearinghouse";
            case 56: return "XNS Authentication";
            case 58: return "XNS Mail";
            case 63: return "whoispp - whois++";
            case 64: return "covia - Communications Integrator (CI)";
            case 67: return "BOOTP server";
            case 68: return "BOOTP client";
            case 69: return "TFTP";
            case 70: return "Gopher";
            case 71: return "NETRJS";
            case 72: return "NETRJS";
            case 73: return "NETRJS";
            case 74: return "NETRJS";
            case 78: return "vetTCP";
            case 79: return "finger";
            case 80: return "HTTP";
            case 81: return "TorPark onion routing";
            case 82: return "TorPark control";
            case 83: return "MIT ML Device";
            case 84: return "Common Trace Facility";
            case 85: return "MIT ML Device";
            case 86: return "Micro Focus Cobol";
            case 88: return "Kerberos";
            case 89: return "SU/MIT Telnet Gateway";
            case 90: return "dnsix";
            case 91: return "MIT Dover Spooler";
            case 92: return "Network Printing Protocol";
            case 93: return "Device Control Protocol";
            case 94: return "Tivoli Object Dispatcher";
            case 95: return "supdup";
            case 96: return "DIXIE Protocol Specification";
            case 97: return "Swift Remote Virtual File Protocol";
            case 98: return "tacnews";
            case 99: return "WIP message protocol";
            case 101: return "NIC host name";
            case 102: return "TSAP";
            case 104: return "DICOM";
            case 105: return "CCSO Nameserver";
            case 107: return "RTelnet";
            case 108: return "SNA";
            case 109: return "POP2";
            case 110: return "POP3";
            case 114: return "Audio News Multicast";
            case 115: return "SFTP";
            case 116: return "ansanotify";
            case 117: return "UUCP Mapping Project";
            case 118: return "SQL";
            case 119: return "NNTP";
            case 120: return "cfdptkt";
            case 121: return "erpc";
            case 122: return "smakynet";
            case 123: return "NTP";
            case 124: return "ansatrader";
            case 125: return "Locus PC-Interface Net Map Server";
            case 127: return "Locus PC-Interface Conn Server";
            case 128: return "GSS X License Verification";
            case 129: return "Password Generator Protocol";
            case 130: return "cisco FNATIVE";
            case 131: return "cisco TNATIVE";
            case 132: return "cisco SYSMAINT";
            case 133: return "statsrv";
            case 136: return "PROFILE Naming System";
            case 137: return "NetBIOS Name Service";
            case 138: return "NetBIOS Datagram Service";
            case 139: return "NetBIOS Session Service";
            case 140: return "EMFIS Data Service";
            case 141: return "EMFIS Control Service";
            case 142: return "Britton-Lee IDM";
            case 143: return "IMAP";
            case 144: return "news";
            case 145: return "uaac";
            case 148: return "cronus";
            case 149: return "AED 512 Emulation Service";
            case 150: return "sql-net";
            case 151: return "hems";
            case 152: return "BFTP";
            case 153: return "SGMP";
            case 154: return "netsc-prod";
            case 155: return "netsc-dev";
            case 156: return "SQL";
            case 157: return "knet-cmp";
            case 158: return "DMSP";
            case 159: return "nss-routing";
            case 160: return "sgmp-traps";
            case 161: return "SNMP";
            case 162: return "SNMPTRAP";
            case 163: return "CMIP/TCP Manager";
            case 164: return "CMIP/TCP Agent";
            case 165: return "xns-courier - Xerox";
            case 166: return "Sirius Systems";
            case 167: return "namp";
            case 168: return "rsvd";
            case 169: return "send";
            case 170: return "Print server";
            case 171: return "Network Innovations Multiplex";
            case 172: return "Network Innocations CL/1";
            case 173: return "Xyplex";
            case 174: return "mailq";
            case 175: return "vmnet";
            case 176: return "genrad-mux";
            case 177: return "XDMCP";
            case 178: return "NeXTSTEP Window Server";
            case 179: return "Border Gateway Protocol";
            case 180: return "Intergraph";
            case 181: return "unify";
            case 182: return "Unisys Audit SITP";
            case 183: return "ocbinder";
            case 184: return "ocserver";
            case 185: return "remote-kis";
            case 186: return "KIS Protocol";
            case 187: return "Application Communication Interface";
            case 188: return "Plus Five's MUMPS";
            case 189: return "Queued File Transport";
            case 190: return "Gateway Access Protocol";
            case 191: return "Prospero Directory Service";
            case 192: return "OSU Network Monitoring System";
            case 193: return "Spider Remote Monitoring Protocol";
            case 194: return "Internet Relay Chat";
            case 195: return "DNSIX Network Level Module Audit";
            case 196: return "DNSIX Session Mgt Module Audit Redir";
            case 197: return "dls - Directory Location Service";
            case 198: return "Directory Location Service Monitor";
            case 199: return "SNMP multiplexing protocol";
            case 200: return "IBM System Resource Controller";
            case 201: return "AppleTalk Routing Maintenance";
            case 202: return "AppleTalk Name Binding";
            case 203: return "AppleTalk Unused";
            case 204: return "AppleTalk Echo";
            case 205: return "AppleTalk Unused";
            case 206: return "AppleTalk Zone Information";
            case 207: return "AppleTalk Unused";
            case 208: return "AppleTalk Unused";
            case 209: return "Quick Mail Transfer Protocol";
            case 211: return "Texas Instruments 914C/G Terminal";
            case 212: return "ATEXSSTR";
            case 213: return "Internetwork Packet Exchange";
            case 214: return "VM PWSCS";
            case 215: return "Insignia Solutions";
            case 216: return "Access Technology License Server";
            case 217: return "dBASE Unix";
            case 218: return "Message posting protocol";
            case 219: return "Unisys ARPs";
            case 220: return "IMAP";
            case 221: return "fln-spx";
            case 222: return "rsh-spx";
            case 223: return "Certificate Distribution Center";
            case 243: return "Surveet Measurement";
            case 246: return "Display Systems Protocol";
            case 259: return "Efficient Short Remote Operations";
            case 262: return "Arcisdms";
            case 264: return "Border Gateway Multicast Protocol";
            case 280: return "http-mgmt";
            case 300: return "ThinLinc Web Access";
            case 308: return "Novastor Online Backup";
            case 318: return "PKIX Time Stamp Protocol";
            case 319: return "Precision Time Protocol (PTP) event messages";
            case 320: return "Precision Time Protocol (PTP) general messages";
            case 345: return "Perf Analysis Workbench";
            case 346: return "Zebra server";
            case 347: return "Fatmen Server";
            case 348: return "Cabletron Management Protocol";
            case 350: return "MATIP type A";
            case 351: return "MATIP type B";
            case 356: return "cloanto-net-1";
            case 366: return "ODMR";
            case 369: return "Rpc2portmap";
            case 370: return "Coda authentication server";
            case 371: return "ClearCase albd";
            case 372: return "Unix Listserv";
            case 373: return "legent-1";
            case 374: return "legent-2";
            case 375: return "hassle";
            case 376: return "Amiga Envoy Network Inquiry Proto";
            case 377: return "tnETOS";
            case 378: return "dsETOS";
            case 379: return "is99c - TIA/EIA/IS-99 modem client";
            case 380: return "is99s - TIA/EIA/IS-99 modem server";
            case 383: return "HP data alarm manager";
            case 384: return "A Remote Network Server System";
            case 385: return "ibm-app";
            case 386: return "ASA Message Router Object Def";
            case 387: return "AURP";
            case 388: return "Unidata LDM Version 4";
            case 389: return "LDAP";
            case 390: return "uis";
            case 391: return "SynOptics SNMP Relay Port";
            case 392: return "SynOptics Port Broker Port";
            case 393: return "Data Interpretation System";
            case 394: return "EMBL Nucleic Data Transfer";
            case 395: return "NETscout Control Protocol";
            case 396: return "netware-ip";
            case 397: return "mptn";
            case 398: return "kryptolan";
            case 399: return "Digital Equipment Corporation DECnet";
            case 400: return "Workstation Solutions";
            case 401: return "UPS";
            case 402: return "Genie Protocol4";
            case 403: return "decap4";
            case 404: return "nced4";
            case 407: return "Timbuktu";
            case 408: return "Prospero Resource Manager Sys. Man4.";
            case 409: return "Prospero Resource Manager Node Man4";
            case 410: return "DECLadebug Remote Debug Protcol4";
            case 411: return "Remote MT Protocol4";
            case 412: return "Trap Convention Port4";
            case 413: return "smsp";
            case 414: return "infoseek4";
            case 415: return "bnet4";
            case 416: return "silverplatter4";
            case 417: return "onmux4";
            case 418: return "hyper-g4";
            case 419: return "ariel14";
            case 420: return "smpte4";
            case 421: return "ariel24";
            case 422: return "ariel34";
            case 423: return "opc-job-start";
            case 424: return "opc-job-track";
            case 425: return "ICAD4";
            case 426: return "smartsdp4";
            case 427: return "Service Location Protocol";
            case 428: return "ocs_cmu4";
            case 429: return "ocs_amu4";
            case 430: return "utmpsd4";
            case 431: return "utmpcd4";
            case 432: return "iasd4";
            case 433: return "NNSP";
            case 435: return "mobileip-mn4";
            case 436: return "dna-cml4";
            case 437: return "comscm4";
            case 438: return "dsfgw4";
            case 439: return "dasp4";
            case 440: return "sgcp4";
            case 441: return "decvms-sysmgt4";
            case 442: return "cvc_hostd4";
            case 443: return "Hypertext Transfer Protocol";
            case 444: return "Simple Network Paging Protocol";
            case 445: return "Microsoft-DS Active Directory";
            case 446: return "ddm-rdb";
            case 447: return "ddm-dfm";
            case 448: return "ddm-byte";
            case 449: return "AS Server Mapper";
            case 464: return "Kerberos Change/Set password";
            case 465: return "Authenticated SMTP";
            case 497: return "Retrospect";
            case 500: return "ISAKMP";
            case 502: return "Modbus Protocol";
            case 504: return "Citadel";
            case 510: return "FCP";
            case 512: return "Remote Process Execution";
            case 513: return "Who";
            case 514: return "Remote Shell";
            case 515: return "Line Printer Daemon";
            case 517: return "Talk";
            case 518: return "NTalk";
            case 520: return "Routing Information Protocol (RIP)";
            case 521: return "Routing Information Protocol Next Generation (RIPng)";
            case 524: return "NetWare Core Protocol";
            case 525: return "Timed";
            case 530: return "Remote procedure call (RPC)";
            case 532: return "netnews";
            case 533: return "netwall";
            case 540: return "Unix-to-Unix Copy Protocol";
            case 542: return "commerce";
            case 543: return "Kerberos login";
            case 544: return "Kerberos Remote shell";
            case 546: return "DHCPv6 client";
            case 547: return "DHCPv6 server";
            case 548: return "Apple Filing Protocol";
            case 554: return "RTSP";
            case 556: return "RFS";
            case 560: return "Remote Monitor";
            case 563: return "NNTP";
            case 585: return "Legacy";
            case 587: return "SMTP";
            case 591: return "FileMaker";
            case 593: return "HTTP RPC Ep Map";
            case 601: return "Reliable Syslog Service";
            case 604: return "TUNNEL profile";
            case 623: return "ASF Remote Management and Control Protocol (ASF-RMCP) & IPMI Remote Management Protocol";
            case 625: return "Open Directory Proxy";
            case 631: return "Common Unix Printing System";
            case 635: return "RLZ DBase";
            case 636: return "Lightweight Directory Access Protocol";
            case 639: return "MSDP";
            case 641: return "SupportSoft Nexus Remote Command (control/listening)";
            case 643: return "SANity";
            case 646: return "Label Distribution Protocol";
            case 647: return "DHCP Failover protocol";
            case 648: return "Registry Registrar Protocol";
            case 651: return "IEEE-MMS";
            case 653: return "SupportSoft Nexus Remote Command (data)";
            case 654: return "Media Management System";
            case 655: return "Tinc VPN daemon";
            case 674: return "Application Configuration Access Protocol";
            case 688: return "ApplianceWare Server Appliance Management Protocol";
            case 690: return "Velneo Application Transfer Protocol";
            case 691: return "MS Exchange Routing";
            case 694: return "Linux-HA high-availability heartbeat";
            case 695: return "IEEE Media Management System";
            case 698: return "Optimized Link State Routing";
            case 700: return "Extensible Provisioning Protocol";
            case 701: return "Link Management Protocol";
            case 702: return "Internet Registry Information Service";
            case 706: return "Secure Internet Live Conferencing";
            case 711: return "Cisco Tag Distribution Protocol";
            case 749: return "Kerberos administration";
            case 750: return "Kerberos version IV";
            case 751: return "kerberos_master, Kerberos authentication";
            case 752: return "Kerberos password (kpasswd) server";
            case 753: return "Reverse Routing Header";
            case 754: return "tell send";
            case 760: return "Kerberos registration";
            case 782: return "Conserver serial-console management server";
            case 783: return "SpamAssassin spamd daemon";
            case 800: return "mdbs-daemon";
            case 829: return "Certificate Management Protocol";
            case 830: return "NETCONF";
            case 831: return "NETCONF";
            case 832: return "NETCONF";
            case 833: return "NETCONF";
            case 843: return "Adobe Flash";
            case 847: return "DHCP Failover protocol";
            case 848: return "Group Domain Of Interpretation";
            case 853: return "DNS";
            case 860: return "iSCSI";
            case 861: return "OWAMP control";
            case 862: return "TWAMP control";
            case 873: return "rsync file synchronization protocol";
            case 897: return "Brocade SMI-S RPC";
            case 898: return "Brocade SMI-S RPC SSL";
            case 902: return "VMware ESXi";
            case 903: return "VMware ESXi";
            case 953: return "RNDC";
            case 989: return "FTPS Protocol (data)";
            case 990: return "FTPS Protocol (control)";
            case 991: return "Netnews Administration System";
            case 992: return "Telnet protocol";
            case 993: return "Internet Message Access Protocol";
            case 994: return "Internet Relay Chat";
            case 995: return "POP3S";
            default: return null;
        }
    }
    // </editor-fold>
    
    
    
    // <editor-fold defaultstate="collapsed" desc="CLASS PRIVATES">
    /**
     * Cette classe permet de chercher en tant que Thread si oui ou non un équipement existe
     */
    private class SearchHardware implements Runnable {

        private final int pc;
        private final java.net.InetAddress addr;
        private final Equipment[] si;

        public SearchHardware(int pc, java.net.InetAddress addr, Equipment[] si) {
            this.pc = pc;
            this.addr = addr;
            this.si = si;
        }
        
        @Override
        public void run() {
            if (isAlive(addr.getHostAddress())) {
                if (!addr.getHostAddress().equals(addr.getHostName())) {
                    //Une adresse est trouvée
                    synchronized(si){
                        si[pc] = new Equipment(new IPv4(addr.getAddress()), addr.getHostName(), true);
                    }
                } else {
                    synchronized(si){
                        si[pc] = new Equipment(new IPv4(addr.getAddress()), null, false);
                    }
                }
            } else {
                synchronized(si){
                    si[pc] = new Equipment(new IPv4(addr.getAddress()), null, false);
                }
            }
        }
        
    }
    
    /**
     * Cette classe permet de chercher en tant que Thread si oui ou non un port est ouvert
     */
    private class SearchPort implements Runnable {

        private final int port;
        private final EquipmentPort[] pi;
        private final java.util.List<Boolean> sem;
        private final int portMin;
        private final boolean atcp;
        private final boolean audp;

        public SearchPort(int port, EquipmentPort[] pi, java.util.List<Boolean> sem, int portMin, boolean atcp, boolean audp) {
            this.port = port;
            this.pi = pi;
            this.sem = sem;
            this.portMin = portMin;
            this.atcp = atcp;
            this.audp = audp;
        }
        
        @Override
        public void run() {
            synchronized(pi){
                boolean isOpen;
                int protocol;
                
                if(atcp && audp){
                    int value = portIsUsed(port);
                    isOpen = value>0;
                    switch (value) {
                        case 1:
                            protocol = EquipmentPort.TYPE_TCP;
                            break;
                        case 2:
                            protocol = EquipmentPort.TYPE_UDP;
                            break;
                        default:
                            protocol = EquipmentPort.TYPE_TCP_UDP;
                            break;
                    }
                }else if (atcp && !audp){
                    isOpen = portTCPIsUsed(port);
                    protocol = EquipmentPort.TYPE_TCP;
                }else if (!atcp && audp){
                    isOpen = portUDPIsUsed(port);
                    protocol = EquipmentPort.TYPE_UDP;
                }else{
                    int value = portIsUsed(port);
                    isOpen = value>0;
                    switch (value) {
                        case 1:
                            protocol = EquipmentPort.TYPE_TCP;
                            break;
                        case 2:
                            protocol = EquipmentPort.TYPE_UDP;
                            break;
                        default:
                            protocol = EquipmentPort.TYPE_TCP_UDP;
                            break;
                    }
                }
                
                pi[port-portMin] = new EquipmentPort(port, isOpen, getNamePort(port), protocol);
            }
            try{
                sem.remove(0);
            }catch(java.lang.IndexOutOfBoundsException e){}
        }
        
    }
    // </editor-fold>
    
    
    
}