/*
 * Copyright (C) JasonPercus Systems, Inc - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by JasonPercus, 05/2021
 */
package com.jasonpercus.util.process;



import com.jasonpercus.util.InputStreamListener;
import com.jasonpercus.util.InputStream;
import static com.jasonpercus.util.OS.IS_ANDROID;
import static com.jasonpercus.util.OS.IS_LINUX;
import static com.jasonpercus.util.OS.IS_WINDOWS;
import com.jasonpercus.util.OSNotSupportedException;



/**
 * Cette classe permet de créer un sous process Unix ou Windows et de controler ses flux... Attention: Cette bibliothèque fait référence à JNA. Assurez-vous de bien avoir les jars suivants: jna-x.x.x.jar et jna-platform-x.x.x.jar
 * @see IProcessListener
 * @see com.sun.jna.platform.win32.Kernel32
 * @author JasonPercus
 * @version 1.0
 */
public class Process implements Comparable<Process> {
    
    
    
//ATTRIBUTS STATICS
    /**
     * Correspond au compteur d'id des process
     */
    private static int cpt = 0;
    
    
    
//ATTRIBUTS
    /**
     * Correspond à l'id du process
     */
    private final int id;
    
    /**
     * Correspond aux argument de lancement du process
     */
    private final String[] args;
    
    /**
     * Correspond à l'entrée standard du process
     */
    private InputStream messageStream;
    
    /**
     * Correspond à l'entrée erreur du process
     */
    private InputStream errorStream;
    
    /**
     * Correspond au pid du process (-1 si le process a été killé, arrêté ou n'a pas été lancé)
     */
    private long pid;
    
    /**
     * Correspond au listener qui écoute le lancement, l'arrêt ... du process
     */
    private IProcessListener processListener;

    
    
//CONSTRUCTORS
    /**
     * Constructeur par défaut (à ne pas utiliser)
     */
    private Process(){
        this.args = null;
        this.id = -1;
    }
    
    /**
     * Crée un process
     * @param args Correspond aux arguments de lancement du process
     * @throws OSNotSupportedException Si le système d'exploitation ne prend pas en charge l'utilisation de cette variante de processus
     */
    public Process(String... args) throws OSNotSupportedException {
        if(!(IS_WINDOWS || IS_LINUX)) throw new OSNotSupportedException("The use of this process variant is not currently supported for other operating systems other than windows and linux !");
        if(IS_ANDROID) throw new OSNotSupportedException("Android does not support this alternative process !");
        this.args = args;
        this.id = cpt++;
        this.pid = -1;
        disableAccessWarnings();
    }

    
    
//GETTERS
    /**
     * Renvoie les arguments de lancement du process
     * @return Retourne les arguments de lancement du process
     */
    public String[] getArgs() {
        return args;
    }

    /**
     * Renvoie l'entrée standard du process
     * @return Retourne l'entrée standard du process
     */
    public InputStream getMessageStream() {
        return messageStream;
    }

    /**
     * Renvoie l'entrée erreur du process
     * @return Retourne l'entrée erreur du process
     */
    public InputStream getErrorStream() {
        return errorStream;
    }

    /**
     * Renvoie le pid du process (-1 si le process est mort ou pas encore lancé)
     * @return Retourne le pid du process
     */
    public long getPid() {
        return pid;
    }
    
    
    
//METHODES PUBLICS
    /**
     * Démarre le processus
     */
    public void start(){
        start(null);
    }
    
    /**
     * Démarre le processus
     * @param <R> Correspond au type de résultat renvoyé
     * @param listener Correspond au listener qui sera mis au courant des status du processus
     * @return Retourne éventuellement un résultat
     */
    @SuppressWarnings("SleepWhileInLoop")
    public <R> R start(IProcessListener listener) {
        this.processListener = listener;

        final int[] countEnd = new int[]{0};
        try {
            java.lang.Process proc = new ProcessBuilder(this.args).start();
            pid = getProcessID(proc);
            InputStreamListener iolistener = new InputStreamListener() {
                @Override
                public void started() {
                    
                }

                @Override
                public void finished() {
                    countEnd[0] = countEnd[0]+1;
                    if(countEnd[0]>=2){
                        pid = -1;
                        if (processListener != null) {
                            processListener.stopped(Process.this, args);
                        }
                    }
                }
            };
            
            messageStream = new InputStream(proc.getInputStream(), iolistener);
            errorStream = new InputStream(proc.getErrorStream(), iolistener);
            if (processListener != null) {
                return processListener.started(this, args, pid, messageStream, errorStream);
            }
        } catch (java.io.IOException ex) {
            java.util.logging.Logger.getLogger(Process.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        return null;
    }
    
    /**
     * Tue le processus
     * @return Retourne true s'il a bien été tué, sinon false (Attention, il peut retourner également false si le processus se termine un instant avant le kill)
     */
    public boolean kill(){
        if(pid>-1){
            try {
                String[] arguments = new String[]{"kill", "-9", ""+pid};
                if(IS_WINDOWS){
                    arguments = new String[]{"taskkill", "/f", "/pid", ""+pid};
                }
                java.lang.Process proc = new ProcessBuilder(arguments).start();
                java.io.BufferedReader streamMessage = new java.io.BufferedReader(new java.io.InputStreamReader(proc.getInputStream()));
                java.io.BufferedReader streamError = new java.io.BufferedReader(new java.io.InputStreamReader(proc.getErrorStream()));
                
                String message = "";
                String error = "";
                String lineMessage;
                String lineError;
                int cptLineMessage = 0;
                int cptLineError = 0;
                
                while((lineMessage = streamMessage.readLine()) != null){
                    if(cptLineMessage > 0) message += "\n";
                    message += lineMessage;
                    cptLineMessage++;
                }
                
                while((lineError = streamError.readLine()) != null){
                    if(cptLineError > 0) error += "\n";
                    error += lineError;
                    cptLineError++;
                }
                boolean result = (message.isEmpty() && error.isEmpty()) || ((message.contains("No such process") || error.contains("No such process")));
                if(IS_WINDOWS)
                    result = (message.isEmpty() && error.isEmpty()) || (!(message.toLowerCase().contains("err") || error.toLowerCase().contains("err")));
                if(result && processListener != null){
                    processListener.killed(this, args);
                }
                return result;
            } catch (java.io.IOException ex) {
                return false;
            }
        }else{
            return false;
        }
    }

    /**
     * Renvoie le hashCode du process
     * @return Retourne le hashCode du Process
     */
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + this.id;
        return hash;
    }

    /**
     * Compare deux process entres-eux
     * @param o Correspond au second process à comparer au courant
     * @return Retourne le résultat de la comparaison
     */
    @Override
    public int compareTo(Process o) {
        if(id < o.id) return -1;
        else if(id > o.id) return 1;
        else return 0;
    }

    /**
     * Renvoie si oui ou non deux Process sont identiques
     * @param obj Correspond au second process à comparer au premier
     * @return Retourne true s'ils sont identiques, sinon false
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
        final Process other = (Process) obj;
        return this.id == other.id;
    }
    
    
    
//METHODES PRIVATES
    /**
     * Renvoie le pid d'un processus
     * @param p Correspond au processus dont on cherche à connaitre le PID
     * @return Retourne le PID ou -1 s'il y a une exception
     */
    @SuppressWarnings("UseSpecificCatch")
    private static long getProcessID(java.lang.Process p){
        long result;
        try {
            if (IS_WINDOWS) {
                java.lang.reflect.Field f = p.getClass().getDeclaredField("handle");
                f.setAccessible(true);              
                long handl = f.getLong(p);
                com.sun.jna.platform.win32.Kernel32 kernel = com.sun.jna.platform.win32.Kernel32.INSTANCE;
                com.sun.jna.platform.win32.WinNT.HANDLE hand = new com.sun.jna.platform.win32.WinNT.HANDLE();
                hand.setPointer(com.sun.jna.Pointer.createConstant(handl));
                result = kernel.GetProcessId(hand);
                f.setAccessible(false);
            }else{
                java.lang.reflect.Field f = p.getClass().getDeclaredField("pid");
                f.setAccessible(true);
                result = f.getLong(p);
                f.setAccessible(false);
            }
        }catch(Exception ex){
            result = -1;
        }
        return result;
    }
    
    /**
     * Supprime le WARNING: An illegal reflective access operation has occurred
     */
    @SuppressWarnings({"unchecked", "UseSpecificCatch"})
    private static void disableAccessWarnings() {
        try {
            Class unsafeClass = Class.forName("sun.misc.Unsafe");
            java.lang.reflect.Field field = unsafeClass.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            Object unsafe = field.get(null);

            java.lang.reflect.Method putObjectVolatile = unsafeClass.getDeclaredMethod("putObjectVolatile", Object.class, long.class, Object.class);
            java.lang.reflect.Method staticFieldOffset = unsafeClass.getDeclaredMethod("staticFieldOffset", java.lang.reflect.Field.class);

            Class loggerClass = Class.forName("jdk.internal.module.IllegalAccessLogger");
            java.lang.reflect.Field loggerField = loggerClass.getDeclaredField("logger");
            Long offset = (Long) staticFieldOffset.invoke(unsafe, loggerField);
            putObjectVolatile.invoke(unsafe, loggerClass, offset, null);
        } catch (Exception ignored) {
        }
    }
    
    
    
}