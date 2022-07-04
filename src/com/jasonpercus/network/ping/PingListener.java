/*
 * Copyright (C) JasonPercus Systems, Inc - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by JasonPercus, 05/2021
 */
package com.jasonpercus.network.ping;



/**
 * Cette interface permet à un processus de ping d'envoyer les résultats des pings en cours
 * @author JasonPercus
 * @version 1.0
 */
public interface PingListener {
    
    
    
    /**
     * Lorsqu'un nouveau résultat de ping est récupéré
     * @param hostOrIp Correspond à l'url ou l'ip de l'équipement pingé
     * @param result Correspond au résultat du ping
     */
    public void newPingResult(String hostOrIp, ResultPing result);
    
    
    
}