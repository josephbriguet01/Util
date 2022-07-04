/*
 * Copyright (C) JasonPercus Systems, Inc - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by JasonPercus, 05/2021
 */
package com.jasonpercus.network.ping;



import com.jasonpercus.network.IPv4;
import static com.jasonpercus.util.OS.IS_ANDROID;
import static com.jasonpercus.util.OS.IS_LINUX;



/**
 * Cette classe permet de créer des pings vers des équipements ou des urls
 * @author JasonPercus
 * @version 1.0
 */
public final class Ping {



//ATTRIBUTS
    /**
     * Correspond à la liste des écouteurs
     */
    private final java.util.List<PingListener> listeners;

    /**
     * Renvoie l'host du ping
     */
    private final String host;

    /**
     * Correspond au nombre de ping (-1 = infini)
     */
    private final int count;

    /**
     * Détermine si le processus de ping est en cours ou pas
     */
    private boolean running;

    /**
     * Correspond au process qui executeCmd le ping
     */
    private Process process;



//CONSTRUCTORS
    /**
     * Crée un ping infini
     * @param ip Correspond à l'ip pointant l'équipement que l'on veut pinger
     */
    public Ping(IPv4 ip) {
        this(ip.getIpv4());
    }

    /**
     * Crée un ping infini
     * @param host Correspond à l'hote ou l'url pointant l'équipement que l'on veut pinger
     */
    public Ping(String host) {
        this(host, -1);
    }

    /**
     * Crée un ping
     * @param ip Correspond à l'ip pointant l'équipement que l'on veut pinger
     * @param count Correspond au nombre de ping (-1 = infini)
     */
    public Ping(IPv4 ip, int count) {
        this(ip.getIpv4(), count);
    }

    /**
     * Crée un ping
     * @param host Correspond à l'hote ou l'url pointant l'équipement que l'on veut pinger
     * @param count Correspond au nombre de ping (-1 = infini)
     */
    public Ping(String host, int count) {
        this.listeners = new java.util.ArrayList<>();
        this.host = host;
        this.count = (count == 0) ? -1 : count;
    }



//LISTENERS
    /**
     * Ajoute un listener sur le ping
     * @param listener Correspond au listener à ajouter
     */
    public void addPingListener(PingListener listener) {
        if(listener != null && !listeners.contains(listener))
            listeners.add(listener);
    }

    /**
     * Supprime le listener du ping
     * @param listener Correspond au listener à supprimer
     */
    public void removePingListener(PingListener listener) {
        if(listener != null && listeners.contains(listener))
            listeners.add(listener);
    }



//METHODES PUBLICS
    /**
     * Execute le ping
     * @throws Exception Correspond à une éventuelle exception levée
     */
    public void execute() throws  Exception {
        String cmd;
        if(IS_LINUX){
            if(IS_ANDROID){
                if(count>0){
                    cmd = "/system/bin/ping -c "+count+" "+host;
                }else{
                    cmd = "/system/bin/ping "+host;
                }
            }else{
                if(count>0){
                    cmd = "ping "+host+" -c "+count;
                }else{
                    cmd = "ping "+host;
                }
            }
        }else{
            if(count>0){
                cmd = "ping "+host+" -n "+count;
            }else{
                cmd = "ping "+host+" -t";
            }
        }
        executeCmd(cmd);
    }

    /**
     * Stoppe le ping en cours
     */
    public void kill(){
        if(process != null){
            process.destroy();
            process = null;
        }
    }

    /**
     * Renvoie true si le ping est en cours, sinon false
     * @return Retourne true si le ping est en cours, sinon false
     */
    public boolean isRunning(){
        return this.running;
    }



//METHODES PUBLICS STATICS
    /**
     * Execute un seul ping et renvoie son résultat
     * @param ip Correspond à l'ip pointant l'équipement que l'on veut pinger
     * @return Retourne le résultat du ping
     * @throws Exception S'il y a un problème quelconque lors de l'exécution
     */
    public static ResultPing execute(IPv4 ip) throws Exception {
        return execute(ip.getIpv4());
    }

    /**
     * Execute un seul ping et renvoie son résultat
     * @param host Correspond à l'hote ou l'url pointant l'équipement que l'on veut pinger
     * @return Retourne le résultat du ping
     * @throws Exception S'il y a un problème quelconque lors de l'exécution
     */
    public static ResultPing execute(String host) throws Exception {
        ResultPing result = null;
        String cmd;
        if(IS_LINUX){
            if(IS_ANDROID)
                cmd = "/system/bin/ping -c 1 "+host;
            else
                cmd = "ping "+host+" -c 1";
        }else{
            cmd = "ping "+host+" -n 1";
        }
        Process process = Runtime.getRuntime().exec(cmd);
        try (java.io.BufferedReader inputStream = new java.io.BufferedReader(new java.io.InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = inputStream.readLine()) != null) {
                result = analyseResult(line, host, null);
                if(result != null)
                    break;
            }
        }
        if(result == null)
            result = new FailedPing(new IPv4(host));
        return result;
    }

    /**
     * Execute des pings à l'infini (stopper avec kill)
     * @param ip Correspond à l'ip pointant l'équipement que l'on veut pinger
     * @param listener Correspond au listener à ajouter sur le ping
     * @return Retourne le ping en activité sur un autre thread
     */
    public static Ping execute(IPv4 ip, PingListener...listener) {
        return execute(ip.getIpv4(), -1, listener);
    }

    /**
     * Execute des pings à l'infini (stopper avec kill)
     * @param host Correspond à l'hote ou l'url pointant l'équipement que l'on veut pinger
     * @param listener Correspond au listener à ajouter sur le ping
     * @return Retourne le ping en activité sur un autre thread
     */
    public static Ping execute(String host, PingListener...listener) {
        return execute(host, -1, listener);
    }

    /**
     * Execute n pings (possibilité de stopper avec kill)
     * @param ip Correspond à l'ip pointant l'équipement que l'on veut pinger
     * @param count Correspond au n pings qui seront effectués (-1 = infini)
     * @param listener Correspond au listener à ajouter sur le ping
     * @return Retourne le ping en activité sur un autre thread
     */
    public static Ping execute(IPv4 ip, int count, PingListener...listener) {
        return execute(ip.getIpv4(), count, listener);
    }

    /**
     * Execute n pings (possibilité de stopper avec kill)
     * @param host Correspond à l'hote ou l'url pointant l'équipement que l'on veut pinger
     * @param count Correspond au n pings qui seront effectués (-1 = infini)
     * @param listener Correspond au listener à ajouter sur le ping
     * @return Retourne le ping en activité sur un autre thread
     */
    @SuppressWarnings("Convert2Lambda")
    public static Ping execute(String host, int count, PingListener...listener) {
        if (count == 0) count = -1;
        final Ping ping = new Ping(host, count);
        if (listener != null) {
            for (PingListener l : listener) {
                ping.addPingListener(l);
            }
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ping.execute();
                } catch (Exception ex) {
                    java.util.logging.Logger.getLogger(Ping.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                }
            }
        }).start();

        return ping;
    }



//METHODE PRIVATE
    /**
     * Execute la commande native en shell et l'envoie à analyser (cf: analyseResult)
     * @param cmd Correspond à la commande windows ou unix à executer
     * @throws Exception S'il y a un problème quelconque lors de l'exécution
     */
    private void executeCmd(String cmd) throws Exception {
        running = true;
        int n = this.count;
        try{
            process = Runtime.getRuntime().exec(cmd);
            try (java.io.BufferedReader inputStream = new java.io.BufferedReader(new java.io.InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = inputStream.readLine()) != null) {
                    ResultPing result = analyseResult(line, this.host, this.listeners);
                    if(result != null) n--;
                    if(n==0) break;
                }
            }
            running = false;
            process = null;
        }catch(Exception ex){
            running = false;
            process = null;
            throw ex;
        }
    }



//METHODES PRIVATES STATICS
    /**
     * Analyse une ligne récupéré de la console shell et l'analyse en fonction de l'os (cf: analyseResultUnix ou analyseResultWindows)
     * @param result Correspond à la ligne récupérée
     * @param host Correspond à l'hote ou l'url pointant l'équipement que l'on veut pinger
     * @param listeners Correspond au listener à ajouter sur le ping
     * @return Retourne le résultat du ping ou null si la ligne ne correspond pas à un résultat probant
     */
    private static ResultPing analyseResult(String result, String host, java.util.List<PingListener> listeners){
        if(IS_LINUX){
            return analyseResultUnix(result, host, listeners);
        }else{
            return analyseResultWindows(result, host, listeners);
        }
    }

    /**
     * Analyse la ligne
     * @param result Correspond à la ligne à analyser
     * @param host Correspond à l'hote ou l'url pointant l'équipement que l'on veut pinger
     * @param listeners Correspond au listener à ajouter sur le ping
     * @return Retourne le résultat du ping ou null si la ligne ne correspond pas à un résultat probant
     */
    private static ResultPing analyseResultUnix(String result, String host, java.util.List<PingListener> listeners){
        java.util.regex.Pattern patternBase = java.util.regex.Pattern.compile("([a-zA-Zâàéêè]*=[0-9]* [a-zA-Zâàéêè]*=[0-9]* [a-zA-Zâàéêè]*(<=|<|=|>|>=)[0-9\\.]*[ ]?ms)");
        java.util.regex.Matcher matcherBase = patternBase.matcher(result);
        if (matcherBase.find()) {
            return successUnix(matcherBase, result, host, listeners);
        }else{
            if(result.indexOf("PING") != 0 && result.indexOf("---") != 0 && !result.isEmpty()){
                ResultPing resultPing = new FailedPing(new IPv4(host));
                sendResult(resultPing, host, listeners);
                return resultPing;
            }
        }
        return null;
    }

    /**
     * Analyse la ligne
     * @param result Correspond à la ligne à analyser
     * @param host Correspond à l'hote ou l'url pointant l'équipement que l'on veut pinger
     * @param listeners Correspond au listener à ajouter sur le ping
     * @return Retourne le résultat du ping ou null si la ligne ne correspond pas à un résultat probant
     */
    private static ResultPing analyseResultWindows(String result, String host, java.util.List<PingListener> listeners){
        java.util.regex.Pattern patternBase = java.util.regex.Pattern.compile("([a-zA-Zâàéêè]*=[0-9]* [a-zA-Zâàéêè]*(<=|<|=|>|>=)[0-9\\.]*[ ]?ms [a-zA-Zâàéêè]*=[0-9]*)");
        java.util.regex.Matcher matcherBase = patternBase.matcher(result);
        if (matcherBase.find()) {
            return successWindows(matcherBase, result, host, listeners);
        }else{
            if(!result.contains("'ping'") && !result.contains("'Ping'") && !result.contains("Pinging") && !result.contains("statistics") && !result.contains("Statistiques") && !result.isEmpty()){
                ResultPing resultPing = new FailedPing(new IPv4(host));
                sendResult(resultPing, host, listeners);
                return resultPing;
            }
        }
        return null;
    }

    /**
     * Si un ping réussi à atteindre sa destination
     * @param matcherBase Correspond à la séquence analysé par un regex
     * @param result Correspond à la ligne analysé
     * @param host Correspond à l'équipement analysé
     * @param listeners Correspond au listener à ajouter sur le ping
     * @return Retourne le résultat du ping ou null si la ligne ne correspond pas à un résultat probant
     */
    private static ResultPing successUnix(java.util.regex.Matcher matcherBase, String result, String host, java.util.List<PingListener> listeners){
        String resultBase = matcherBase.group();
        java.util.regex.Pattern patternCut = java.util.regex.Pattern.compile("([a-zA-Zâàéêè]*(<=|<|=|>|>=)[0-9]*)");
        java.util.regex.Matcher matcherCut = patternCut.matcher(resultBase);

        int octets = 0;
        int temps = 0;
        int ttl = 0;
        int cpt = 0;

        while (matcherCut.find()) {
            int number = number(matcherCut.group());
            switch (cpt) {
                case 0:
                    octets = Integer.parseInt(result.split(" ")[0]);
                    break;
                case 1:
                    ttl = number;
                    break;
                case 2:
                    temps = number;
                    break;
            }
            cpt++;
        }
        java.util.regex.Pattern patternIP = java.util.regex.Pattern.compile("([0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3})");
        java.util.regex.Matcher matcherIP = patternIP.matcher(result);
        if (matcherIP.find()) {
            ResultPing resultPing = new SuccessPing(new IPv4(matcherIP.group()), octets, temps, ttl);
            sendResult(resultPing, host, listeners);
            return resultPing;
        }
        return null;
    }

    /**
     * Si un ping réussi à atteindre sa destination
     * @param matcherBase Correspond à la séquence analysé par un regex
     * @param result Correspond à la ligne analysé
     * @param host Correspond à l'équipement analysé
     * @param listeners Correspond au listener à ajouter sur le ping
     * @return Retourne le résultat du ping ou null si la ligne ne correspond pas à un résultat probant
     */
    private static ResultPing successWindows(java.util.regex.Matcher matcherBase, String result, String host, java.util.List<PingListener> listeners){
        String resultBase = matcherBase.group();
        java.util.regex.Pattern patternCut = java.util.regex.Pattern.compile("([a-zA-Zâàéêè]*(<=|<|=|>|>=)[0-9]*)");
        java.util.regex.Matcher matcherCut = patternCut.matcher(resultBase);

        int octets = 0;
        int temps = 0;
        int ttl = 0;
        int cpt = 0;

        while (matcherCut.find()) {
            int number = number(matcherCut.group());
            switch (cpt) {
                case 0:
                    octets = number;
                    break;
                case 1:
                    temps = number;
                    break;
                case 2:
                    ttl = number;
                    break;
            }
            cpt++;
        }
        java.util.regex.Pattern patternIP = java.util.regex.Pattern.compile("([0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3})");
        java.util.regex.Matcher matcherIP = patternIP.matcher(result);
        if (matcherIP.find()) {
            ResultPing resultPing = new SuccessPing(new IPv4(matcherIP.group()), octets, temps, ttl);
            sendResult(resultPing, host, listeners);
            return resultPing;
        }
        return null;
    }

    /**
     * Envoie un résultat à la liste des listeners
     * @param result Correspond au résultat du ping à envoyer
     * @param host Correspond à l'adresse de l'équipement pingé
     * @param listeners Correspond à la liste des listeners où envoyer le résultat
     */
    private static void sendResult(ResultPing result, String host, java.util.List<PingListener> listeners){
        if(listeners != null){
            for(PingListener listener : listeners){
                if(listener != null)
                    listener.newPingResult(host, result);
            }
        }
    }

    /**
     * Renvoie l'entier d'un groupe de séquence regex (ex: time=15 renvoie 15)
     * @param group Correspond au groupe récupéré par l'analyse de pings
     * @return Retourne l'entier d'un groupe de séquence regex
     */
    private static int number(String group){
        int entier = -1;
        boolean inf = false;
        boolean sup = false;
        String[] split = null;
        if (group.contains("<=")) {
            split = group.split("<=");
        } else if (group.contains(">=")) {
            split = group.split(">=");
        } else if (group.contains("=")) {
            split = group.split("=");
        } else if (group.contains("<")) {
            split = group.split("<");
            inf = true;
        } else if (group.contains(">")) {
            split = group.split(">");
            sup = true;
        }
        if (split != null && split.length == 2) {
            try {
                entier = Integer.parseInt(split[1]);
                if(inf && entier>0){
                    entier--;
                }
                if(sup){
                    entier++;
                }
            } catch (java.lang.NumberFormatException e) {
            }
        }
        return entier;
    }



}