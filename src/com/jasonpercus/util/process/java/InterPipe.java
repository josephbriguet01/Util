/*
 * Copyright (C) JasonPercus Systems, Inc - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by JasonPercus, 05/2021
 */
package com.jasonpercus.util.process.java;



import static com.jasonpercus.util.OS.IS_ANDROID;
import static com.jasonpercus.util.OS.IS_LINUX;
import static com.jasonpercus.util.OS.IS_WINDOWS;
import com.jasonpercus.util.OSNotSupportedException;
import com.jasonpercus.util.Strings;



/**
 * Cette classe à pour but de permettre une communication bi-directionnelle entre un processus père java et un processus fils java (*.jar). Attention InterPipe ne fonctionne actuellement que sur Windows et Linux (excepté Android)
 * <p>
 * <b>
 * POUR LE PERE:
 * </b>
 * </p>
 * <p>
 * Le processus père doit lancer le fichier .jar créant ainsi le processus fils avec la méthode {@link InterPipe#createProcessJava(java.lang.String, java.lang.String) createProcessJava()}
 * </p>
 * <p>
 * Puis il recevra les données de son fils après utilisation de la méthode {@link In#read(com.jasonpercus.util.process.java.IReceivedForParents) InterPipe.in.read(IReceivedForParents)} et après implémentation de l'interface {@link IReceivedForParents}
 * </p>
 * <p>
 * Il pourra envoyer un objet à son fils avec la méthode {@link Out#print(java.lang.String, java.io.Serializable) InterPipe.out.print(String, Serializable)}
 * </p>
 * <p>
 * <b>
 * POUR LE FILS:
 * </b>
 * </p>
 * <p>
 * Un processus fils n'a pas à ce soucier de lancer quoi que ce soit. Il recevra les données de son père après utilisation de la méthode {@link In#read(com.jasonpercus.util.process.java.IReceivedForChildren) InterPipe.in.read(IReceivedForChildren)} et après implémentation de l'interface {@link IReceivedForChildren}
 * </p>
 * <p>
 * Il pourra envoyer un objet à son père avec la méthode {@link Out#print(java.io.Serializable) InterPipe.out.print(Serializable)}
 * </p>
 * <p>
 * De même qu'il pourra envoyer un objet d'erreur à son père avec la méthode {@link Err#print(java.io.Serializable) InterPipe.err.print(Serializable)}
 * </p>
 * @see WrongTypeException
 * @see IReceivedForChildren
 * @see IReceivedForParents
 * @author JasonPercus
 * @version 1.0
 */
@SuppressWarnings("StaticNonFinalUsedInInitialization")
public final class InterPipe {

    
    
//ATTRIBUTS STATICS PUBLICS
    /**
     * Le flux de sortie "standard". Ce flux est déjà ouvert (pour un fils) (pour un père après utilisation de la méthode {@link InterPipe#createProcessJava(java.lang.String, java.lang.String) createProcessJava()}) et prêt à accepter les données de sortie.
     * Il est important de ne pas se tromper de méthode lors de son utilisation.
     * <p>
     * {@link Out#print(java.io.Serializable) print(Serializable obj)}: c'est pour qu'un fils communique avec son père
     * </p>
     * <p>
     * {@link Out#print(java.lang.String, java.io.Serializable) print(String processId, Serializable obj)}: C'est pour qu'un père communique avec l'un de ses fils
     * </p>
     */
    public static Out out = new Out();
    
    /**
     * Le flux d'entrée "standard". Ce flux est déjà ouvert (pour un père après utilisation de la méthode {@link InterPipe#createProcessJava(java.lang.String, java.lang.String) createProcessJava()}) (pour un fils uniquement après le placement de la redirection {@link In#read(com.jasonpercus.util.process.java.IReceivedForChildren) InterPipe.in.read()} et prêt à fournir des données d'entrée.
     * Il est important de ne pas se tromper de méthode lors de son utilisation.
     * <p>
     * {@link In#read(com.jasonpercus.util.process.java.IReceivedForChildren) read(IReceivedForChildren received)}: c'est pour rediriger les paquets d'un fils reçus d'un père
     * </p>
     * <p>
     * {@link In#read(com.jasonpercus.util.process.java.IReceivedForParents) read(IReceivedForParents received)}: c'est pour rediriger les paquets standard ou d'erreur d'un père reçus d'un fils
     * </p>
     */
    public static In in = new In();
    
    /**
     * Le flux de sortie d'erreur "standard". Ce flux est déjà ouvert (pour un fils) et prêt à accepter les données de sortie
     * Il est important de noter que contrairement à {@link InterPipe#in InterPipe.in} il n'y a pas de méthode InterPipe.err.print(String processId, Serializable obj). Pour la simple et bonne raison qu'un père ne peut pas envoyer d'erreur à son fils. Seul l'inverse est vrai
     */
    public static Err err = new Err();
    
    
    
//ATTRIBUTS STATICS PRIVATES
    /**
     * Correspond à la liste des sous-processus (pour un processus père)
     */
    private static java.util.List<PacketProcess> packetsProcess = new java.util.ArrayList<>();
    
    /**
     * Correspond à la liste des écouteurs d'analyses de paquets synchrones (au niveau des process fils)
     */
    private static java.util.List<Sync> syncsForChildren = new java.util.ArrayList<>();
    
    /**
     * Correspond à la liste des écouteurs d'analyses de paquets synchrones (au niveau du process père)
     */
    private static java.util.List<Sync> syncsForParent = new java.util.ArrayList<>();
    
    /**
     * Détermine si l'utilisation de cette classe se fait en tant que processus père ou fils
     */
    private static boolean parent;
    
    /**
     * Détermine (dans le cas d'un process fils) si le scanner doit continuer d'écouter les paquets entrants
     */
    private static boolean run;
    
    /**
     * Détermine (dans le cas d'un process fils) si le processus est coupé définitivement de son père (les streams sont fermés)
     */
    private static boolean stopped;
    
    /**
     * Correspond au compteur d'objet;
     */
    private static int cpteur = 0;
    
    /**
     * Correspond au listener (dans le cas d'un process père) qui redirige les paquets standards ou d'erreurs
     */
    private static IReceivedForParents receivedForParents;
    
    /**
     * initialise le system print
     */
    static{
        java.io.PrintStream[] ps = new java.io.PrintStream[]{System.out, System.err};
        for (int i = 0; i < ps.length; i++) {
            ps[i] = new java.io.PrintStream(ps[i]) {
                @Override
                public void println(Object obj) {
                    if (parent) {
                        super.println(obj);
                    } else {
                        print(obj);
                    }
                }

                @Override
                public void println(String s) {
                    if (parent) {
                        super.println(s);
                    } else {
                        print(s);
                    }
                }

                @Override
                public void println(char[] c) {
                    if (parent) {
                        super.println(c);
                    } else {
                        print(c);
                    }
                }

                @Override
                public void println(double d) {
                    if (parent) {
                        super.println(d);
                    } else {
                        print(d);
                    }
                }

                @Override
                public void println(float f) {
                    if (parent) {
                        super.println(f);
                    } else {
                        print(f);
                    }
                }

                @Override
                public void println(long l) {
                    if (parent) {
                        super.println(l);
                    } else {
                        print(l);
                    }
                }

                @Override
                public void println(int i) {
                    if (parent) {
                        super.println(i);
                    } else {
                        print(i);
                    }
                }

                @Override
                public void println(char c) {
                    if (parent) {
                        super.println(c);
                    } else {
                        print(c);
                    }
                }

                @Override
                public void println(boolean b) {
                    if (parent) {
                        super.println(b);
                    } else {
                        print(b);
                    }
                }

                @Override
                public void println() {
                    if (parent) {
                        super.println();
                    } else {
                        print("");
                    }
                }

                @Override
                public void print(Object obj) {
                    if (parent) {
                        super.print(obj);
                    } else if (obj instanceof Packet) {
                        super.print(obj + "\n");
                    } else {
                        super.print(new Packet((java.io.Serializable) obj) + "\n");
                    }
                }

                @Override
                public void print(String s) {
                    if (parent) {
                        super.print(s);
                    } else {
                        super.print(new Packet(s) + "\n");
                    }
                }

                @Override
                public void print(char[] c) {
                    if (parent) {
                        super.print(c);
                    } else {
                        super.print(new Packet(c) + "\n");
                    }
                }

                @Override
                public void print(double d) {
                    if (parent) {
                        super.print(d);
                    } else {
                        super.print(new Packet(d) + "\n");
                    }
                }

                @Override
                public void print(float f) {
                    if (parent) {
                        super.print(f);
                    } else {
                        super.print(new Packet(f) + "\n");
                    }
                }

                @Override
                public void print(long l) {
                    if (parent) {
                        super.print(l);
                    } else {
                        super.print(new Packet(l) + "\n");
                    }
                }

                @Override
                public void print(int i) {
                    if (parent) {
                        super.print(i);
                    } else {
                        super.print(new Packet(i) + "\n");
                    }
                }

                @Override
                public void print(char c) {
                    if (parent) {
                        super.print(c);
                    } else {
                        super.print(new Packet(c) + "\n");
                    }
                }

                @Override
                public void print(boolean b) {
                    if (parent) {
                        super.print(b);
                    } else {
                        super.print(new Packet(b) + "\n");
                    }
                }
            };
        }
        System.setOut(ps[0]);
        System.setErr(ps[1]);
    }

    
    
//CONSTRUCTOR
    /**
     * Correspond au constructeur par défaut
     */
    private InterPipe() {
    }
    
    
    
//METHODES PUBLICS STATICS
    /**
     * Crée et lance un processus fils (à partir d'un jar)
     * @param id Correspond à l'id du processus fils qui identifiera le nouveau processus
     * @param jarFile Correspond au chemin du fichier jar qui sera lancé en tant que processus
     */
    @SuppressWarnings("SynchronizeOnNonFinalField")
    public static void createProcessJava(String id, String jarFile){
        if(IS_WINDOWS || (IS_LINUX && !IS_ANDROID)){
            if(id == null)
                throw new NullPointerException("id is null !");
            else{
                synchronized(packetsProcess){
                    if(!packetsProcess.contains(new PacketProcess(id))){
                        packetsProcess.add(new PacketProcess(id, jarFile));
                        parent = true;
                    }
                }
            }
        }else{
            throw new OSNotSupportedException("InterPipe is only supported by Windows and Linux (except Android) !");
        }
    }
    
    /**
     * Stoppe le processus d'écoute (à utiliser à partir d'un processus fils)
     */
    public static void stop(){
        if(IS_WINDOWS || (IS_LINUX && !IS_ANDROID)){
            if(!parent){
                run = false;
                stopped = true;
                out.print("ergiuberiguberiuz");
                err.print("ergiuberiguberiuz");
            }else
                throw WrongTypeException.isParentTypeStop();
        }else{
            throw new OSNotSupportedException("InterPipe is only supported by Windows and Linux (except Android) !");
        }
    }
    
    /**
     * Stoppe le processus d'écoute (à utiliser à partir d'un processus parent)
     * @param id Correspond à l'id du processus fils dont on va couper la communication
     */
    public static void stop(String id){
        if(IS_WINDOWS || (IS_LINUX && !IS_ANDROID)){
            if(parent)
                out.print(id, "erougneruogbnueor");
            else
                throw WrongTypeException.isChildTypeStop();
        }else{
            throw new OSNotSupportedException("InterPipe is only supported by Windows and Linux (except Android) !");
        }
    }
    
    /**
     * Renvoie la liste des id des processus fils (utilisable seulement si le processus courant est un processus père)
     * @return Retourne la liste des id des processus fils
     */
    @SuppressWarnings("SynchronizeOnNonFinalField")
    public static String[] listIdChildren(){
        if(IS_WINDOWS || (IS_LINUX && !IS_ANDROID)){
            if(parent){
                synchronized(packetsProcess){
                    int size = packetsProcess.size();
                    String[] names = new String[size];
                    for(int i=0;i<size;i++){
                        names[i] = packetsProcess.get(i).name;
                    }
                    return names;
                }
            }else
                throw WrongTypeException.isChildType();
        }else{
            throw new OSNotSupportedException("InterPipe is only supported by Windows and Linux (except Android) !");
        }
    }
    
    /**
     * Demande un objet au processus parent
     * @param obj Correspond à la demande d'objet
     * @param error Détermine si la demande est réalisé sur le bus standard ou sur le bus erreur
     * @return Retourne le résultat de la demande
     */
    @SuppressWarnings({"SynchronizeOnNonFinalField", "SleepWhileInLoop"})
    public static java.io.Serializable getSync(java.io.Serializable obj, boolean error){
        if(IS_WINDOWS || (IS_LINUX && !IS_ANDROID)){
            if(!parent){
                final String idReturn = Strings.generate(20);
                final java.io.Serializable[] returnObj = new java.io.Serializable[1];
                final boolean[] finished = new boolean[]{false};
                Sync sync = new Sync(error) {
                    @Override
                    public void packet(Packet packet) {
                        if(packet.getIdReturn() != null && packet.getIdReturn().equals(idReturn) && packet.getDirection() == -1){
                            returnObj[0] = (java.io.Serializable) packet.getObject();
                            finished[0] = true;
                        }
                    }
                };
                synchronized(syncsForChildren){
                    syncsForChildren.add(sync);
                }
                if(error){
                    System.err.println(new Packet(obj, 1, idReturn));
                }else{
                    System.out.println(new Packet(obj, 1, idReturn));
                }
                while (!finished[0]) {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException ex) {
                        java.util.logging.Logger.getLogger(InterPipe.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                    }
                }
                synchronized (syncsForChildren) {
                    syncsForChildren.remove(sync);
                }
                if(returnObj[0].toString().equals("erognzbgihnenfbn"))
                    return null;
                return returnObj[0];
            }else{
                return WrongTypeException.isChildTypeSync();
            }
        }else{
            throw new OSNotSupportedException("InterPipe is only supported by Windows and Linux (except Android) !");
        }
    }
    
    /**
     * Demande un objet à un processus fils
     * @param id Correspond à l'id du processus fils à qui il faut lui faire sa demande
     * @param obj Correspond à la demande d'objet
     * @return Retourne le résultat de la demande
     */
    @SuppressWarnings({"SynchronizeOnNonFinalField", "SleepWhileInLoop"})
    public static java.io.Serializable getSync(String id, java.io.Serializable obj){
        if(IS_WINDOWS || (IS_LINUX && !IS_ANDROID)){
            if(parent){
                if(id == null)
                    throw new NullPointerException("id is null !");
                final String idReturn = Strings.generate(20);
                final java.io.Serializable[] returnObj = new java.io.Serializable[1];
                final boolean[] finished = new boolean[]{false};
                Sync sync = new Sync() {
                    @Override
                    public void packet(Packet packet) {
                        if(packet.getIdReturn() != null && packet.getIdReturn().equals(idReturn) && packet.getDirection() == -1){
                            returnObj[0] = (java.io.Serializable) packet.getObject();
                            finished[0] = true;
                        }
                    }
                };
                synchronized(syncsForParent){
                    syncsForParent.add(sync);
                }
                PacketProcess packetProcess = getPacketProcess(id);
                if(packetProcess != null){
                    try {
                        packetProcess.bw.write(new Packet(obj, 1, idReturn).toString()+"\n");
                        packetProcess.bw.flush();
                    } catch (java.io.IOException ex) {
                        java.util.logging.Logger.getLogger(InterPipe.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                    }
                    while (!finished[0]) {
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException ex) {
                            java.util.logging.Logger.getLogger(InterPipe.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                        }
                    }
                    synchronized (syncsForParent) {
                        syncsForParent.remove(sync);
                    }
                    if(returnObj[0].toString().equals("erognzbgihnenfbn"))
                        return null;
                    return returnObj[0];
                }else{
                    return null;
                }
            }else{
                return WrongTypeException.isParentTypeSync();
            }
        }else{
            throw new OSNotSupportedException("InterPipe is only supported by Windows and Linux (except Android) !");
        }
    }
    
    
    
//METHODES PRIVATES STATICS
    /**
     * Renvoie le PacketProcess correspondant à son id
     * @param id Correspond à l'id du PacketProcess à trouver
     * @return Retourne le PacketProcess trouvé ou null
     */
    @SuppressWarnings("SynchronizeOnNonFinalField")
    private static PacketProcess getPacketProcess(String id){
        synchronized (packetsProcess) {
            int indexof = packetsProcess.indexOf(new PacketProcess(id));
            if (indexof > -1) {
                return packetsProcess.get(indexof);
            }
        }
        return null;
    }
    
    
    
//CLASS PUBLICS
    /**
     * Cette classe permet d'envoyer un objet d'un processus père à un processus fils et vice-versa
     * @author JasonPercus
     * @version 1.0
     */
    public static class Out {
        
        
        
    //CONSTRUCTOR
        /**
         * Correspond au constructeur par défaut
         */
        private Out(){
            
        }
        
        
        
    //METHODES PUBLICS
        /**
         * Envoie un objet du fils à destination du père
         * @param obj Correspond à l'objet à envoyer
         */
        public void print(java.io.Serializable obj){
            if(IS_WINDOWS || (IS_LINUX && !IS_ANDROID)){
                if(!parent)
                    System.out.println(new Packet(obj));
                else
                    throw WrongTypeException.isParentTypeOut();
            }else{
                throw new OSNotSupportedException("InterPipe is only supported by Windows and Linux (except Android) !");
            }
        }
        
        
        
        /**
         * Envoie un objet du père à destination de l'un de ses fils
         * @param processId Correspond à l'id d'un de ses fils
         * @param obj Correspond à l'objet à envoyer
         */
        @SuppressWarnings("SynchronizeOnNonFinalField")
        public void print(String processId, java.io.Serializable obj){
            if(IS_WINDOWS || (IS_LINUX && !IS_ANDROID)){
                if(parent){
                    synchronized(packetsProcess){
                        int indexof = packetsProcess.indexOf(new PacketProcess(processId));
                        if(indexof>-1){
                            PacketProcess packetProcess = packetsProcess.get(indexof);
                            try {
                                Packet packet = new Packet(obj);
                                packetProcess.bw.write(packet.toString()+"\n");
                                packetProcess.bw.flush();
                            } catch (java.io.IOException ex) {
                                java.util.logging.Logger.getLogger(InterPipe.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                            }
                        }
                    }
                } else
                    throw WrongTypeException.isChildTypeOut();
            }else{
                throw new OSNotSupportedException("InterPipe is only supported by Windows and Linux (except Android) !");
            }
        }
        
        
        
    }
    
    /**
     * Cette classe permet d'écouter un processus père et/ou un processus fils et ainsi de récupérer leurs objets envoyés
     * @author JasonPercus
     * @version 1.0
     */
    public static class In {
        
        
        
    //CONSTRUCTOR
        /**
         * Correspond au constructeur par défaut
         */
        private In(){
            
        }
        
        
        
    //METHODES PUBLICS
        /**
         * Ecoute le bus d'entré du processus fils
         * @param received Permet de rediriger les objets du processus père
         */
        public void read(IReceivedForChildren received){
            if(IS_WINDOWS || (IS_LINUX && !IS_ANDROID)){
                if(!parent){
                    if(!stopped)
                        run = true;
                    @SuppressWarnings("SynchronizeOnNonFinalField")
                    Thread thread = new Thread(() -> {
                        java.util.Scanner scanner = new java.util.Scanner(System.in);
                        try {
                            while (run) {
                                String line = scanner.nextLine();
                                if (line != null) {
                                    Packet packet = Packet.open(line.replace("\n", ""));
                                    Object obj = packet.getObject();
                                    switch (packet.getDirection()) {
                                        case -1:
                                            Thread t1 = new Thread(() -> {
                                                synchronized(syncsForChildren){
                                                    for(Sync sync : syncsForChildren){
                                                        sync.packet(packet);
                                                    }
                                                }
                                            });
                                            t1.setPriority(Thread.MAX_PRIORITY);
                                            t1.start();
                                            break;
                                        case 1:
                                            Thread t2 = new Thread(() -> {
                                                java.io.Serializable objReturn = received.receivedSync(obj);
                                                if(objReturn == null)
                                                    objReturn = "erognzbgihnenfbn";
                                                Packet packetReturn = new Packet(objReturn, -1, packet.getIdReturn());
                                                System.out.println(packetReturn);
                                            });
                                            t2.setPriority(Thread.MAX_PRIORITY);
                                            t2.start();
                                            break;
                                        default:
                                            if(obj != null && obj instanceof String && obj.toString().equals("erougneruogbnueor"))
                                                stop();
                                            else
                                                received.received(obj);
                                            break;
                                    }
                                }
                            }
                        } catch (Exception ex) {

                        }
                    });
                    thread.setPriority(Thread.MAX_PRIORITY);
                    thread.start();
                }else
                    throw WrongTypeException.isParentTypeIn();
            }else{
                throw new OSNotSupportedException("InterPipe is only supported by Windows and Linux (except Android) !");
            }
        }
        
        /**
         * Ecoute les bus d'entrées du processus père
         * @param received Permet de rediriger les objets des processus fils
         */
        public void read(IReceivedForParents received){
            if(IS_WINDOWS || (IS_LINUX && !IS_ANDROID)){
                if(parent)
                    receivedForParents = received;
                else
                    throw WrongTypeException.isChildTypeIn();
            }else{
                throw new OSNotSupportedException("InterPipe is only supported by Windows and Linux (except Android) !");
            }
        }
        
        
        
    }
    
    /**
     * Cette classe permet à un processus fils d'envoyer un objet à son père sur le bus d'erreur
     * @author JasonPercus
     * @version 1.0
     */
    public static class Err {
        
        
        
    //CONSTRUCTOR
        /**
         * Correspond au constructeur par défaut
         */
        private Err(){
            
        }
        
        
        
    //METHODES PUBLICS
        /**
         * Envoie un objet erreur du fils à destination du père
         * @param obj Correspond à l'objet erreur à envoyer
         */
        public void print(java.io.Serializable obj){
            if(IS_WINDOWS || (IS_LINUX && !IS_ANDROID)){
                if(!parent)
                    System.err.println(new Packet(obj));
                else
                    throw WrongTypeException.isParentType();
            }else{
                throw new OSNotSupportedException("InterPipe is only supported by Windows and Linux (except Android) !");
            }
        }
        
        
        
    }
    
    
    
//CLASS PRIVATE
    /**
     * Cette classe permet de créer ou gérer un sous-processus (vu comme fils pour un père)
     * @author JasonPercus
     * @version 1.0
     */
    private final static class PacketProcess{
        
        
        
    //ATTRIBUTS
        /**
         * Correspond au nom ou id du process fils lancé
         */
        private String name;
        
        /**
         * Correspond au process fils lancé
         */
        private Process proc;
        
        /**
         * Correspond au flux de sortie du processus créé (utilisable pour que le père envoie des données au fils)
         */
        private java.io.BufferedWriter bw;
        
        /**
         * Correspond au flux d'entrée du processus créé (utilisable pour que le père reçoive des données du fils)
         */
        private java.io.BufferedReader br;
        
        /**
         * Correspond au flux d'entrée d'erreur du processus créé (utilisable pour que le père reçoive des erreurs du fils)
         */
        private java.io.BufferedReader be;
        
        /**
         * Détermine si les flux sont à l'écoute ou pas
         */
        private boolean run;
        
        /**
         * Correspond au thread qui écoute le flux d'entrée standard
         */
        private Thread threadIN;
        
        /**
         * Correspond au thread qui écoute le flux d'entrée d'erreur
         */
        private Thread threadERR;
        
        
        
    //CONSTRUCTORS
        /**
         * Crée un PacketProcess par défaut (utilisable seulement pour la recherche d'un même élément dans une liste...)
         * @param name Correspond au nom ou id du process
         */
        public PacketProcess(String name) {
            this.name = name;
        }

        /**
         * Crée un PacketProcess
         * @param name Correspond au nom ou id du process
         * @param jarFile Correspond au nom du fichier jar qui sera le process fils
         */
        @SuppressWarnings({"CallToThreadStartDuringObjectConstruction", "CallToPrintStackTrace"})
        public PacketProcess(String name, String jarFile) {
            this.name = name;
                try {
                proc = Runtime.getRuntime().exec("java -jar " + jarFile);
                br = new java.io.BufferedReader(new java.io.InputStreamReader(proc.getInputStream()));
                be = new java.io.BufferedReader(new java.io.InputStreamReader(proc.getErrorStream()));
                bw = new java.io.BufferedWriter(new java.io.OutputStreamWriter(proc.getOutputStream()));
            } catch (java.io.IOException ex) {
                java.util.logging.Logger.getLogger(InterPipe.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }
                
            run = true;
            threadIN = new Thread(() -> {
                try {
                    while(run) {
                        String line = br.readLine();
                        treat(line, false);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
            threadIN.setPriority(Thread.MAX_PRIORITY);
            threadIN.start();
            
            threadERR = new Thread(() -> {
                try {
                    while(run) {
                        String line = be.readLine();
                        treat(line, true);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
            threadERR.setPriority(Thread.MAX_PRIORITY);
            threadERR.start();
        }

        
        
    //METHODES PUBLICS
        /**
         * Renvoie le hashCode du PacketProcess
         * @return Retourne le hashCode du PacketProcess
         */
        @Override
        public int hashCode() {
            int hash = 7;
            hash = 59 * hash + java.util.Objects.hashCode(this.name);
            return hash;
        }

        /**
         * Renvoie si deux PacketProcess sont identiques
         * @param obj Correspond au second PacketProcess à comparer au courant
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
            final PacketProcess other = (PacketProcess) obj;
            return java.util.Objects.equals(this.name, other.name);
        }
        
        
        
    //METHODES PRIVATES
        /**
         * Ferme tous les flux du processus père
         * @throws IOException S'il y a la moindre exception
         */
        @SuppressWarnings("SynchronizeOnNonFinalField")
        private void stop() throws java.io.IOException{
            br.close();
            be.close();
            bw.close();
            synchronized(packetsProcess){
                packetsProcess.remove(this);
            }
        }
        
        /**
         * Traite la réception d'un paquet
         * @param line Correspond à la ligne récupérée
         * @param error Détermine s'il s'agit d'un paquet erreur ou pas
         * @throws IOException S'il y a la moindre exception
         */
        @SuppressWarnings({"SynchronizeOnNonFinalField", "NestedSynchronizedStatement"})
        private synchronized void treat(String line, boolean error) throws java.io.IOException{
            if (line != null) {
                try{
                    Packet packet = Packet.open(line.replace("\n", ""));
                    Object obj = packet.getObject();
                    if(packet.getIdReturn() != null && packet.getDirection() == 1){
                        Thread t = new Thread(() -> {
                            java.io.Serializable objReturn;
                            if (receivedForParents != null) {
                                objReturn = receivedForParents.receivedSync(this.name, obj, error);
                                if(objReturn == null)
                                    objReturn = "erognzbgihnenfbn";
                            }else{
                                objReturn = "erognzbgihnenfbn";
                            }
                            Packet packetReturn = new Packet(objReturn, -1, packet.getIdReturn());
                            try {
                                bw.write(packetReturn.toString()+"\n");
                                bw.flush();
                            } catch (java.io.IOException ex) {
                                java.util.logging.Logger.getLogger(InterPipe.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                            }
                        });
                        t.setPriority(Thread.MAX_PRIORITY);
                        t.start();
                    }else if (packet.getIdReturn() != null && packet.getDirection() == -1){
                        Thread t = new Thread(() -> {
                            synchronized(syncsForParent){
                                for(Sync sync : syncsForParent){
                                    sync.packet(packet);
                                }
                            }
                        });
                        t.setPriority(Thread.MAX_PRIORITY);
                        t.start();
                    }else{
                        if (obj != null && obj instanceof String && obj.toString().equals("ergiuberiguberiuz") && run) {
                            run = false;
                        } else if (obj != null && obj instanceof String && obj.toString().equals("ergiuberiguberiuz") && !run) {
                            stop();
                        } else if (receivedForParents != null) {
                            receivedForParents.received(this.name, obj, error);
                        }
                    }
                }catch(java.lang.IllegalArgumentException | java.lang.NullPointerException e){
                    if (receivedForParents != null) {
                        receivedForParents.received(this.name, line, error);
                    }
                }
            }
        }
        
        
        
    }
    
    /**
     * Cette classe permet de faire de la redirection d'objet synchrone
     * @author JasonPercus
     * @version 1.0
     */
    private static abstract class Sync {
        
        
        
    //ATTRIBUTS
        /**
         * Correspond à l'id unique de l'objet
         */
        private final int id;
        
        /**
         * Correspond au nom du process fils
         */
        private String name;
        
        /**
         * Détermine s'il s'agit d'une erreur ou pas
         */
        private boolean error;

        
        
    //CONSTRUCTORS
        /**
         * Crée un objet Sync par défaut
         */
        public Sync(){
            this.id = cpteur++;
        }
        
        /**
         * Crée un objet Sync
         * @param error Détermine si l'objet récupéré était sur le bus error
         */
        public Sync(boolean error) {
            this.id = cpteur++;
            this.error = error;
        }
        
        
        
    //ABSTRACT
        /**
         * Récupère un paquet à analyser
         * @param packet Correspond au paquet à analyser
         */
        public abstract void packet(Packet packet);
        
        

    //METHODES PUBLICS
        /**
         * Renvoie le hashCode de l'objet
         * @return Retourne le hashCode de l'objet
         */
        @Override
        public int hashCode() {
            int hash = 5;
            hash = 67 * hash + this.id;
            return hash;
        }

        /**
         * Détermine si deux Sync sont égaux
         * @param obj Correspond aux second objet à comparer au courant
         * @return Retourne true s'ils sont égaux, sinon false
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
            final Sync other = (Sync) obj;
            return this.id == other.id;
        }
        
        
        
    }
    
    
    
}