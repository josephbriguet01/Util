/*
 * Copyright (C) JasonPercus Systems, Inc - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by JasonPercus, 05/2021
 */
package com.jasonpercus.util.thread;



/**
 * Cette classe permet de lancer plusieurs sous-threads exécutant une tâche et renvoyant un éventuel résultat. Un objet résultant de cette classe attendra tous les résultats avant de finir son exécution.
 * <pre>
 * {@code
 * Ordre d'appel des méthodes:
 *  1: OnCodeProcess.start()
 *  2: OnCodeProcess.before()
 *   #3: During.run() [exécuté dans un autre thread ce qui fait que le point 4 est appelé quasiment instantanément sur le thread courant]
 *  4: OnCodeProcess.after() [qui ne connait pas le/les résultat(s) du point 3 puisqu'ils s'exécutent sur deux threads différents]
 *  5: OnCodeProcess.whileWaiting() [qui boucle tant que le point 3 n'est pas terminé. Ce point bloque le thread courant]
 *  6: OnCodeProcess.end() [le point 5 est débloqué et tous les résultats sont connus et transmis]
 * }</pre>
 * 
 * Exemple simplifié:
 * <pre>
 * <code>
 * //Calcul la factorielle
 * During&lsaquo;Integer, Void, Void, Integer&rsaquo; first = new During&lsaquo;Integer, Void, Void, Integer&rsaquo;() {
 *      {@literal @}Override
 *      public Integer run(Integer in, Void resultStart, Void resultBefore) {
 *          int result = in;
 *          for(int i=1;i&lsaquo;=10;i++){
 *              result *= i;
 *          }
 *          return result;
 *      }
 * };
 * 
 * //Même chose mais avec une addition
 * During&lsaquo;Integer, Void, Void, Integer&rsaquo; second = new During&lsaquo;Integer, Void, Void, Integer&rsaquo;() {
 *      {@literal @}Override
 *      public Integer run(Integer in, Void resultStart, Void resultBefore) {
 *          int result = in;
 *          for(int i=1;i&lsaquo;=10;i++){
 *              result += i;
 *          }
 *          return result;
 *      }
 * };
 * 
 * //Création d'un listener
 * OnCodeProcessAdapter&lsaquo;Integer, Void, Void, Void, Void&rsaquo; listener = new OnCodeProcessAdapter&lsaquo;Integer, Void, Void, Void, Void&rsaquo;(){
 * 
 *      {@literal @}Override
 *      public Void start(Integer in) {
 *          System.out.println(String.format("Les opérations du nombre %d vont commencer", in));
 *          return null;
 *      }
 * 
 *      {@literal @}Override
 *      public void end(Integer in, Void resultStart, Void resultBefore, List&lsaquo;Void&rsaquo; resultDuring, Void resultAfter, long durationProcess) {
 *          System.out.println(String.format("Les résultats ont été obtenus en %d ms", durationProcess));
 *      }
 * 
 * };
 * 
 * //Crée un processus puis le lance
 * CodeProcessing&lsaquo;Integer, Integer&rsaquo; codeProcessing = new CodeProcessing&lsaquo;&rsaquo;();
 * List&lsaquo;Integer&rsaquo; resultats = codeProcessing.start(listener, 2, first, second);
 * 
 * //Affiche les résultats
 * System.out.println(resultats);
 * </code>
 * </pre>
 * 
 * La console affiche:
 * <pre>
 * {@code 
 * Les opérations du nombre 2 vont commencer
 * Les résultats ont été obtenus en 157 ms
 * [7257600, 57]
 * }</pre>
 * @param <In> Correspond à une éventuelle valeur donnée permettant aux méthodes: {@link OnCodeProcess#start(java.lang.Object) OnCodeProcess.start()}, {@link OnCodeProcess#before(java.lang.Object, java.lang.Object) OnCodeProcess.before()}, {@link During#run(java.lang.Object, java.lang.Object, java.lang.Object) During.run()}, {@link OnCodeProcess#after(java.lang.Object, java.lang.Object, java.lang.Object) OnCodeProcess.after()} et {@link OnCodeProcess#end(java.lang.Object, java.lang.Object, java.lang.Object, java.util.List, java.lang.Object, long) OnCodeProcess.end()} de s'éxécuter
 * @param <Out> Correspond au type des résultats attendues des sous-threads
 * @see OnCodeProcess
 * @see During
 * @author JasonPercus
 * @version 1.0
 */
public class CodeProcessing <In, Out> {

    
    
//ATTRIBUTS
    /**
     * Correspond au temps que mettra chaque passage dans la boucle bloquante d'attente des résultats (voir: {@link #start(com.jasonpercus.util.thread.OnCodeProcess, java.lang.Object, java.util.List) start()})
     */
    private final long msWaiting = 100;
    
    /**
     * Détermine si le processus est en cours ou pas (si c'est le cas, la boucle bloquante est en marche elle aussi)
     */
    private boolean run = false;
    
    
    
//METHODES PUBLICS
    /**
     * Démarre le processus. Exécutent les sous-threads (cf: {@link During}) et renvoie les éventuels résultats de ces sous-exécutions
     * @param listener Correspond à l'éventuel écouteur qui aura pour but de suivre l'évolution du code de la méthode
     * @param inStart Correspond à une éventuelle valeur à transmettre aux méthodes de la classe {@link OnCodeProcess} et {@link During}
     * @param process Correspond à/aux éventuels sous-threads a exécuter en parallèle de ce thread courant
     * @return Retourne la liste des résultats de/des méthode(s) {@link During#run(java.lang.Object, java.lang.Object, java.lang.Object) During.run()} exécutée(s). Si deux objets {@link During} ont été fourni à cette méthode, alors la liste contiendra deux résultats. Ces résultats sont placés dans la liste dans l'ordre d'arrivé des objets {@link During}
     */
    public java.util.List<Out> start(final OnCodeProcess listener, final In inStart, final During...process){
        java.util.List<During> list = new java.util.ArrayList<>();
        list.addAll(java.util.Arrays.asList(process));
        return start(listener, inStart, list);
    }
    
    /**
     * Démarre le processus. Exécutent les sous-threads (cf: {@link During}) et renvoie les éventuels résultats de ces sous-exécutions
     * @param listener Correspond à l'éventuel écouteur qui aura pour but de suivre l'évolution du code de la méthode
     * @param inStart Correspond à une éventuelle valeur à transmettre aux méthodes de la classe {@link OnCodeProcess} et {@link During}
     * @param process Correspond à/aux éventuels sous-threads a exécuter en parallèle de ce thread courant
     * @return Retourne la liste des résultats de/des méthode(s) {@link During#run(java.lang.Object, java.lang.Object, java.lang.Object) During.run()} exécutée(s). Si deux objets {@link During} ont été fourni à cette méthode, alors la liste contiendra deux résultats. Ces résultats sont placés dans la liste dans l'ordre d'arrivé des objets {@link During}
     */
    public java.util.List<Out> start(final OnCodeProcess listener, final In inStart, final java.util.List<During> process){
        final int size = process.size();
        final Object[] od = new Object[size];
        
        if(!run){
            run = true;
            long timeSpent = 0;
            long start = new java.util.Date().getTime();
            
            final Object[] os = new Object[]{null};
            if(listener != null)
                os[0] = listener.start(inStart);
            
            Object[] ob = new Object[]{null};
            if(listener != null)
                ob[0] = listener.before(inStart, os[0]);
            final int[] wait = new int[]{0};
            
            for(int i=0;i<size;i++){
                final int num = i;
                During during = process.get(i);
                Thread thread = new Thread(() -> {
                    od[num] = during.run(inStart, os[0], ob[0]);
                    wait[0]++;
                });
                thread.start();
            }
            
            Object oa = null;
            if(listener != null)
                oa = listener.after(inStart, os[0], ob[0]);
            
            while(wait[0]<size && run){
                sleep(msWaiting);
                timeSpent += msWaiting;
                
                if(listener != null)
                    listener.whileWaiting(this, timeSpent);
            }
            long end = new java.util.Date().getTime();
            java.util.List<Object> odList = new java.util.ArrayList<>();
            odList.addAll(java.util.Arrays.asList(od));
            
            if(listener != null)
                listener.end(inStart, os[0], ob[0], odList, oa, end-start);
        }
        
        java.util.List odList = new java.util.ArrayList();
        odList.addAll(java.util.Arrays.asList(od));
        return odList;
    }
    
    /**
     * Stoppe le processus ainsi que ses sous-threads
     * Attention: Néanmoins si un seul sous-thread ou plutôt {@link During} contient un {@link Thread#sleep(long) Thread.sleep()} de longue durée, le processus ne pourra pas se fermer tant que le sommeil du sous-thread ne sera pas terminé.
     * Pour palier à ce problème il est préférable de créer une boucle qui exécute plusieurs {@link Thread#sleep(long) Thread.sleep()} de petites durées et entre chacune d'elles vérifier que le processus est toujours actif avec la méthode {@link #isRunning() isRunning()}. Si le processus n'est plus actif, casser la boucle et renvoyer un résultat null.
     */
    public void stop(){
        if(run)
            run = false;
    }
    
    /**
     * Renvoie si oui ou non les processus est actif ou pas
     * @return Retourne true s'il l'est, sinon false
     */
    public boolean isRunning(){
        return this.run;
    }
    
    
    
//METHODE PRIVATE
    /**
     * Endors le thread courant
     * @param ms Correspond au nombre de millisecondes que le thread attend avant de se réveiller
     */
    private void sleep(long ms){
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ex) {
            java.util.logging.Logger.getLogger(CodeProcessing.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }
    
    
    
}