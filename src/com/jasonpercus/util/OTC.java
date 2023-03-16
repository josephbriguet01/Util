/*
 * Copyright (C) JasonPercus Systems, Inc - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by JasonPercus, 05/2021
 */
package com.jasonpercus.util;



/**
 * Cette classe "OptimizeTimeCalcul" permet de calculer les temps d'exécutions d'une méthode ou d'une portion de code
 * @author JasonPercus
 * @version 1.0
 */
public class OTC {
    
    
    
//CONSTANTES
    /**
     * Correspond à la liste des calculs en cours
     */
    private final static java.util.List<Calcul> CALCULS = new java.util.ArrayList<>();
    
    
    
//CONSTRUCTOR
    /**
     * Constructeur par défaut
     * @deprecated Ne pas utiliser
     */
    @Deprecated
    private OTC() {
    }
    
    
    
//METHODES PUBLICS STATICS
    /**
     * Démarre un calcul. Il s'agit du flag de démarrage du calcul. Ainsi le chronomètre est enclenché
     * @see #stop(java.lang.String) Il faudra arrêter le calcul
     * @param id Correspond à l'id du calcul démarré. En effet, il peut y avoir plusieurs calculs de démarré en même temps. Il peuvent être également imbriqués
     */
    @SuppressWarnings("NestedSynchronizedStatement")
    public static synchronized void start(String id){
        Calcul c = new Calcul(id);
        synchronized(CALCULS){
            if(!CALCULS.contains(c)){
                CALCULS.add(c);
            }
        }
    }
    
    /**
     * Stoppe un calcul. Il s'agit du flag de fin du calcul. Ainsi le chronomètre est arrêté et la différence entre la date de fin et de départ peut-être calculée
     * @see #start(java.lang.String) Il faut démarrer le calcul avant de l'arrêter
     * @param id Correspond à l'id du calcul qui doit être stoppé. En effet, il peut y avoir plusieurs calculs de démarré en même temps. Il peuvent être également imbriqués
     * @return Retourne le temps en millisecondes. Il s'agit du temps entre l'utilisation de la méthode {@link #start(java.lang.String)} et cette méthode {@link #stop(java.lang.String)}
     */
    public static synchronized long stop(String id){
        Calcul c = new Calcul(id);
        synchronized(CALCULS){
            int index = CALCULS.indexOf(c);
            if(index > -1){
                c = CALCULS.get(index);
                CALCULS.remove(index);
                return c.end();
            }
        }
        return -1;
    }
    
    /**
     * Calcul le temps d'exécution en ms d'un objet {@link Runnable}
     * @param runnable Correspond au bout de code dont il va falloir calculer le temps d'exécution
     * @return Retourne le temps d'exécution en ms
     */
    public static long duration(Runnable runnable){
        long d1 = new java.util.Date().getTime();
        runnable.run();
        return new java.util.Date().getTime() - d1;
    }
    
    
    
//CLASS
    /**
     * Cette classe représente un calcul de durée d'exécution d'un bout de code
     * @see OTC
     * @author JasonPercus
     * @version 1.0
     */
    private static class Calcul {
        
        
        
        /**
         * Correspond à l'id du calcul. Cela permet entre autre de le différencier des autres de la liste {@link #CALCULS}
         */
        private final String id;
        
        /**
         * Correspond à la date de démarrage du calcul. Autrement dit à la création de cet objet
         */
        private final long date1;
        
        /**
         * Correspond à la durée d'exécution en ms d'un bout de code
         */
        private long duration;

        
        
    //CONSTRUCTOR
        /**
         * Crée un calcul
         * @param id Correspond à l'id du calcul. Cela permet entre autre de le différencier des autres de la liste {@link #CALCULS}
         */
        public Calcul(String id) {
            this.id = id;
            this.date1 = new java.util.Date().getTime();
            this.duration = -1;
        }
        
        
        
    //METHODES PUBLICS
        /**
         * Stoppe le calcul. Calcul la différence entre la date de fin et de début de l'éxécution du bout de code
         * @return Retourne le temps en ms de la différence entre la date de fin et de début
         */
        public long end(){
            return duration = new java.util.Date().getTime() - date1;
        }

        /**
         * Renvoie seulement la différence entre la date de fin et de début. != {@link #end()} Car celle-ci calcule la différence. Ce qui n'est pas le cas de cette méthode qui elle ne fait que renvoyer la différence
         * @return Retourne seulement la différence entre la date de fin et de début
         */
        public long getDuration() {
            return duration;
        }

        /**
         * Renvoie le hashCode du calcul
         * @return Retourne le hashCode du calcul
         */
        @Override
        public int hashCode() {
            int hash = 5;
            hash = 67 * hash + java.util.Objects.hashCode(this.id);
            return hash;
        }

        /**
         * Détermine si deux calculs sont identiques. La comparaison est effectué à partir de l'id de chaque calcul
         * @param obj Correspond au second calcul à comparer au courant
         * @return Retourne true si les deux calculs sont identiques, sinon false
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
            final Calcul other = (Calcul) obj;
            return java.util.Objects.equals(this.id, other.id);
        }
        
        
        
    }
    
    
    
}