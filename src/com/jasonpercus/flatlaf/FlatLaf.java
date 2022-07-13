/*
 * Copyright (C) BRIGUET Systems, Inc - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Briguet, 07/2022
 */
package com.jasonpercus.flatlaf;



import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.FlatPropertiesLaf;
import com.formdev.flatlaf.IntelliJTheme;
import com.formdev.flatlaf.util.LoggingFacade;
import com.formdev.flatlaf.FlatSystemProperties;
import com.formdev.flatlaf.util.Animator;
import java.awt.Color;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIDefaults;
import javax.swing.UIManager;



/**
 * Cette classe permet la gestion de manière plus facile certaines options de la librairie flatlaf
 * @author JasonPercus
 * @version 1.0
 */
public class FlatLaf {

    
    
//CONSTRUCTOR
    /**
     * Crée un objet FlatLaf
     * @deprecated Ne sert à rien
     */
    @Deprecated
    private FlatLaf() {}
    
    
    
//METHODES PUBLICS STATICS
    /**
     * Affiche l'inspecteur du FlatLaf en cours
     */
    public static void showInspector(){
        java.awt.EventQueue.invokeLater(() -> {
            FlatUIDefaultsInspector.show();
        });
    }
    
    /**
     * Modifie si oui ou non on souhaite utiliser les décorations natives de windows
     * @param useDecorations True si on le souhaite, sinon false
     */
    public static void setUseNativeWindowDecorations(boolean useDecorations){
        java.awt.EventQueue.invokeLater(() -> {
            com.formdev.flatlaf.FlatLaf.setUseNativeWindowDecorations(useDecorations);
        });
    }
    
    /**
     * Modifie si oui ou non on souhaite que la menubar soit imbriquée avec la décoration windows. Attention: cela marche seulement si l'on utilise pas les décorations natives de windows (cd: {@link #setUseNativeWindowDecorations(boolean)})
     * @param embedded True si on le souhaite, sinon false
     */
    public static void setMenuBarEmbedded(boolean embedded) {
        java.awt.EventQueue.invokeLater(() -> {
            javax.swing.UIManager.put("TitlePane.menuBarEmbedded", embedded);
            com.formdev.flatlaf.FlatLaf.revalidateAndRepaintAllFramesAndDialogs();
        });
    }

    /**
     * Modifie si oui ou non on souhaite que la barre de menu soit fondu avec le reste de l'écran ou si l'on souhaite avoir une séparation nette entre les deux
     * @param unified True si on souhaite que la barre de menu soit fondu, sinon false
     */
    public static void setUnifiedTitleBar(boolean unified) {
        java.awt.EventQueue.invokeLater(() -> {
            javax.swing.UIManager.put("TitlePane.unifiedBackground", unified);
            com.formdev.flatlaf.FlatLaf.repaintAllFramesAndDialogs();
        });
    }

    /**
     * Modifie si oui ou non on souhaite que l'icône de la barre de titre s'affiche ou pas
     * @param frame Correspond à la fenêtre où l'on souhaite afficher ou pas l'icône de la barre de titre
     * @param showIcon True si on souhaite afficher l'icône, sinon false
     */
    public static void showTitleBarIcon(javax.swing.JFrame frame, boolean showIcon) {
        java.awt.EventQueue.invokeLater(() -> {
            frame.getRootPane().putClientProperty(com.formdev.flatlaf.FlatClientProperties.TITLE_BAR_SHOW_ICON, showIcon);
            javax.swing.UIManager.put("TitlePane.showIcon", showIcon);
        });
    }
    
    /**
     * Modifie la couleur d'accentuation des {@link javax.swing.JComponent}
     * @param color Correspond à la nouvelle couleur d'accentuation
     */
    public static void setAccentColor(java.awt.Color color){
        setProperty("@accentColor", color);
    }
    
    /**
     * Renvoie une propriété du look and feel. Il peut être utile de s'aider de l'inspecteur pour déterminer quelle propriété renvoyer
     * @param key Correspond à la clef de la propriété à renvoyer
     * @return Retourne la valeur de la propriété ou null
     */
    public static Object getProperty(String key){
        if(key == null || key.isEmpty())
            return null;
        
        UIDefaults defaults = UIManager.getDefaults();
        Set<Map.Entry<Object, Object>> defaultsSet = defaults.entrySet();
        for (Map.Entry<Object, Object> e : defaultsSet) {
            Object k = e.getKey();

            if (!(k instanceof String) || !k.equals(key))
                continue;
            
            Object value = defaults.get(k);
            
            if(value instanceof javax.swing.plaf.ColorUIResource){
                javax.swing.plaf.ColorUIResource str = (javax.swing.plaf.ColorUIResource) value;
                return new Color(str.getRed(), str.getGreen(), str.getBlue(), str.getAlpha());
            }
            
            return value;
        }
        
        return null;
    }
    
    /**
     * Modifie une propriété du look and feel. Il peut être utile de s'aider de l'inspecteur pour déterminer quelle propriété modifier
     * @param key Correspond à la clef de la propriété à modifier
     * @param value Correspond à la nouvelle valeur de la propriété visée
     */
    public static void setProperty(String key, Object value){
        java.awt.EventQueue.invokeLater(() -> {
            if(key != null){
                Class<? extends javax.swing.LookAndFeel> lafClass = javax.swing.UIManager.getLookAndFeel().getClass();
                boolean isAccentColorSupported = 
                        lafClass == FlatLightLaf.class || 
                        lafClass == FlatDarkLaf.class ||
                        lafClass == FlatIntelliJLaf.class ||
                        lafClass == FlatDarculaLaf.class;

                if(isAccentColorSupported){
                    String str = null;
                    if(value instanceof java.awt.Color)
                        str = toString((java.awt.Color)value);

                    com.formdev.flatlaf.FlatLaf.setGlobalExtraDefaults(java.util.Collections.singletonMap(key, str));
                    try {
                        com.formdev.flatlaf.FlatLaf.setup(lafClass.newInstance());
                        com.formdev.flatlaf.FlatLaf.updateUI();
                    } catch (InstantiationException | IllegalAccessException ex) {
                        LoggingFacade.INSTANCE.logSevere(null, ex);
                    }
                } else {
                    throw new FlatLafException(key + " is not supported by " + lafClass.getCanonicalName());
                }
            }
        });
    }
    
    /**
     * Charge le Look And Feel FlatLight
     */
    public static void loadFlatLight(){
        loadFlatLight(false);
    }
    
    /**
     * Charge le Look And Feel FlatLight
     * @param animation Détermine si l'on souhaite avoir une animation au changement du Look And Feel courant (l'ancien), au nouveau Look And Feel (celui-ci)
     */
    public static void loadFlatLight(boolean animation){
        java.awt.EventQueue.invokeLater(() -> {
            changeLaf(new FlatLightLaf(), animation);
        });
    }
    
    /**
     * Charge le Look And Feel FlatDark
     */
    public static void loadFlatDark(){
        loadFlatDark(false);
    }
    
    /**
     * Charge le Look And Feel FlatDark
     * @param animation Détermine si l'on souhaite avoir une animation au changement du Look And Feel courant (l'ancien), au nouveau Look And Feel (celui-ci)
     */
    public static void loadFlatDark(boolean animation){
        java.awt.EventQueue.invokeLater(() -> {
            changeLaf(new FlatDarkLaf(), animation);
        });
    }
    
    /**
     * Charge le Look And Feel FlatIntelliJ
     */
    public static void loadFlatIntelliJ(){
        loadFlatIntelliJ(false);
    }
    
    /**
     * Charge le Look And Feel FlatIntelliJ
     * @param animation Détermine si l'on souhaite avoir une animation au changement du Look And Feel courant (l'ancien), au nouveau Look And Feel (celui-ci)
     */
    public static void loadFlatIntelliJ(boolean animation){
        java.awt.EventQueue.invokeLater(() -> {
            changeLaf(new FlatIntelliJLaf(), animation);
        });
    }
    
    /**
     * Charge le Look And Feel FlatDarcula
     */
    public static void loadFlatDarcula(){
        loadFlatDarcula(false);
    }
    
    /**
     * Charge le Look And Feel FlatDarcula
     * @param animation Détermine si l'on souhaite avoir une animation au changement du Look And Feel courant (l'ancien), au nouveau Look And Feel (celui-ci)
     */
    public static void loadFlatDarcula(boolean animation){
        java.awt.EventQueue.invokeLater(() -> {
            changeLaf(new FlatDarculaLaf(), animation);
        });
    }
    
    /**
     * Charge un Flat Look And Feel ou Thème personnalisé.
     * @param name Correspond au nom du Look And Feel ou au nom du thème
     * @throws java.io.IOException S'il y a une erreur lors du chargement du Look And Feel ou du Thème
     */
    public static void loadPersonnalized(String name) throws java.io.IOException {
        loadPersonnalized(name, false);
    }
    
    /**
     * Charge un Flat Look And Feel ou Thème personnalisé.
     * @param name Correspond au nom du Look And Feel ou au nom du thème
     * @param animation Détermine si l'on souhaite avoir une animation au changement du Look And Feel courant (l'ancien), au nouveau Look And Feel (celui-ci)
     * @throws java.io.IOException S'il y a une erreur lors du chargement du Look And Feel ou du Thème
     */
    public static void loadPersonnalized(String name, boolean animation) throws java.io.IOException {
        java.io.InputStream is1 = FlatLaf.class.getResourceAsStream("/themes/" + name + ".properties");
        if (is1 != null){
            changeLaf(new FlatPropertiesLaf(name, is1), animation);
        }

        java.io.InputStream is2 = FlatLaf.class.getResourceAsStream("/themes/" + name + ".theme.json");
        if (is2 != null){
            if(animation){
                FlatAnimatedLafChange.execute(() -> {
                    try {
                        com.formdev.flatlaf.FlatLaf.setup(IntelliJTheme.createLaf(is2));
                        com.formdev.flatlaf.FlatLaf.updateUI();
                    } catch (java.io.IOException ex) {
                        java.util.logging.Logger.getLogger(FlatLaf.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                    }
                }, 400);
            }else{
                com.formdev.flatlaf.FlatLaf.setup(IntelliJTheme.createLaf(is2));
                com.formdev.flatlaf.FlatLaf.updateUI();
            }
        }
    }
    
    /**
     * Renvoie la liste des noms des Look And Feel et Thème
     * @return Retourne la liste des noms des Look And Feel et Thème
     * @throws java.io.IOException Si l'obtention de la liste des noms semble compromise
     */
    public static String[] listLookAndFeel() throws java.io.IOException {
        java.security.CodeSource src = FlatLaf.class.getProtectionDomain().getCodeSource();
        if (src != null) {
            if(src.getLocation().getFile().endsWith(".jar") || src.getLocation().getFile().endsWith(".exe")){
                java.net.URL jar = src.getLocation();
                java.util.zip.ZipInputStream zip = new java.util.zip.ZipInputStream(jar.openStream());
                java.util.List<String> files = new java.util.ArrayList<>();
                while (zip.available() > 0) {
                    java.util.zip.ZipEntry e = zip.getNextEntry();
                    if (e != null) {
                        String name = e.getName();
                        if (name.startsWith("themes/") && name.endsWith(".properties") || name.endsWith(".theme.json"))
                            files.add(name.substring("themes/".length()).replace(".properties", "").replace(".theme.json", ""));
                    }
                }
                String[] results = new String[files.size()];
                for(int i=0;i<results.length;i++)
                    results[i] = files.get(i);
                return results;
            }else{
                java.io.File folder = new java.io.File("build/classes/themes");
                if(folder.exists()){
                    java.io.File[] files = folder.listFiles((java.io.File pathname) -> {
                        return pathname.getName().endsWith(".properties") || pathname.getName().endsWith(".theme.json");
                    });
                    String[] results = new String[files.length];
                    for(int i=0;i<results.length;i++){
                        results[i] = files[i].getName().replace(".properties", "").replace(".theme.json", "");
                    }
                    return results;
                }
            }
        } else {
            /* Fail... */
        }
        return new String[0];
    }
    
    
    
//METHODES PRIVATES STATICS
    /**
     * Modifie le Look And Feel courant
     * @param laf Correspond au nouveau Look And Feel à installer
     * @param animation Détermine si l'on souhaite avoir une animation au changement du Look And Feel courant (l'ancien), au nouveau Look And Feel (celui-ci en paramètre)
     */
    private static void changeLaf(javax.swing.LookAndFeel laf, boolean animation){
        if(animation){
            FlatAnimatedLafChange.execute(() -> {
                try {
                    javax.swing.UIManager.setLookAndFeel(laf);
                    com.formdev.flatlaf.FlatLaf.updateUI();
                } catch (javax.swing.UnsupportedLookAndFeelException ex) {
                    java.util.logging.Logger.getLogger(FlatLaf.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                }
            }, 400);
        }else{
            try {
                javax.swing.UIManager.setLookAndFeel(laf);
                com.formdev.flatlaf.FlatLaf.updateUI();
            } catch (javax.swing.UnsupportedLookAndFeelException ex) {
                java.util.logging.Logger.getLogger(FlatLaf.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }
        }
    }
    
    /**
     * Renvoie une couleur sous sa forme hexadécimale
     * @param color Correspond à la couleur à transformer
     * @return Retourne une couleur sous sa forme hexadécimale
     */
    private static String toString(java.awt.Color color){
        return String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue());
    }
    
    
    
//CLASS
    /**
     * Cette classe représente une exception personnalisée du Look And Feel FlatLaf
     * @author JasonPercus
     * @version 1.0
     */
    public static class FlatLafException extends RuntimeException {

        
        
    //CONSTRUCTOR
        /**
         * Crée une exception
         * @param message Correspond au message de l'exception
         */
        public FlatLafException(String message) {
            super(message);
        }
        
        
        
    }
    
    
    
}



class FlatAnimatedLafChange {

    /**
     * The resolution of the animation in milliseconds. Default is 40 ms.
     */
    public static int resolution = 40;

    private static Animator animator;
    private static final java.util.Map<javax.swing.JLayeredPane, javax.swing.JComponent> oldUIsnapshots = new java.util.WeakHashMap<>();
    private static final java.util.Map<javax.swing.JLayeredPane, javax.swing.JComponent> newUIsnapshots = new java.util.WeakHashMap<>();
    private static float alpha;
    private static boolean inShowSnapshot;

    /**
     * Create a snapshot of the old UI and shows it on top of the UI. Invoke
     * before setting new look and feel.
     */
    public static void showSnapshot() {
        if (!FlatSystemProperties.getBoolean("flatlaf.animatedLafChange", true)) {
            return;
        }

        // stop already running animation
        if (animator != null) {
            animator.stop();
        }

        alpha = 1;

        // show snapshot of old UI
        showSnapshot(true, oldUIsnapshots);
    }

    private static void showSnapshot(boolean useAlpha, java.util.Map<javax.swing.JLayeredPane, javax.swing.JComponent> map) {
        inShowSnapshot = true;

        // create snapshots for all shown windows
        java.awt.Window[] windows = java.awt.Window.getWindows();
        for (java.awt.Window window : windows) {
            if (!(window instanceof javax.swing.RootPaneContainer) || !window.isShowing()) {
                continue;
            }

            // create snapshot image
            // (using volatile image to have correct sub-pixel text rendering on Java 9+)
            java.awt.image.VolatileImage snapshot = window.createVolatileImage(window.getWidth(), window.getHeight());
            if (snapshot == null) {
                continue;
            }

            // paint window to snapshot image
            javax.swing.JLayeredPane layeredPane = ((javax.swing.RootPaneContainer) window).getLayeredPane();
            layeredPane.paint(snapshot.getGraphics());

            // create snapshot layer, which is added to layered pane and paints
            // snapshot with animated alpha
            javax.swing.JComponent snapshotLayer = new javax.swing.JComponent() {
                @Override
                public void paint(java.awt.Graphics g) {
                    if (inShowSnapshot || snapshot.contentsLost()) {
                        return;
                    }

                    if (useAlpha) {
                        ((java.awt.Graphics2D) g).setComposite(java.awt.AlphaComposite.getInstance(java.awt.AlphaComposite.SRC_OVER, alpha));
                    }
                    g.drawImage(snapshot, 0, 0, null);
                }

                @Override
                public void removeNotify() {
                    super.removeNotify();

                    // release system resources used by volatile image
                    snapshot.flush();
                }
            };
            if (!useAlpha) {
                snapshotLayer.setOpaque(true);
            }
            snapshotLayer.setSize(layeredPane.getSize());

            // add image layer to layered pane
            layeredPane.add(snapshotLayer, Integer.valueOf(javax.swing.JLayeredPane.DRAG_LAYER + (useAlpha ? 2 : 1)));
            map.put(layeredPane, snapshotLayer);
        }

        inShowSnapshot = false;
    }

    /**
     * Starts an animation that shows the snapshot (created by
     * {@link #showSnapshot()} with an decreasing alpha. At the end, the
     * snapshot is removed and the new UI is shown. Invoke after updating UI.
     */
    public static void hideSnapshotWithAnimation() {
        hideSnapshotWithAnimation(160);
    }
    
    /**
     * Starts an animation that shows the snapshot (created by
     * {@link #showSnapshot()} with an decreasing alpha. At the end, the
     * snapshot is removed and the new UI is shown. Invoke after updating UI.
     * @param duration The duration of the animation in milliseconds. Default is 160 ms.
     */
    public static void hideSnapshotWithAnimation(int duration) {
        if (!FlatSystemProperties.getBoolean("flatlaf.animatedLafChange", true)) {
            return;
        }

        if (oldUIsnapshots.isEmpty()) {
            return;
        }

        // show snapshot of new UI
        showSnapshot(false, newUIsnapshots);

        // create animator
        animator = new Animator(duration, fraction -> {
            if (fraction < 0.1 || fraction > 0.9) {
                return; // ignore initial and last events
            }
            alpha = 1f - fraction;

            // repaint snapshots
            for (java.util.Map.Entry<javax.swing.JLayeredPane, javax.swing.JComponent> e : oldUIsnapshots.entrySet()) {
                if (e.getKey().isShowing()) {
                    e.getValue().repaint();
                }
            }
        }, () -> {
            hideSnapshot();
            animator = null;
        });

        animator.setResolution(resolution);
        animator.start();
    }

    private static void hideSnapshot() {
        hideSnapshot(oldUIsnapshots);
        hideSnapshot(newUIsnapshots);
    }

    private static void hideSnapshot(java.util.Map<javax.swing.JLayeredPane, javax.swing.JComponent> map) {
        // remove snapshots
        for (java.util.Map.Entry<javax.swing.JLayeredPane, javax.swing.JComponent> e : map.entrySet()) {
            e.getKey().remove(e.getValue());
            e.getKey().repaint();
        }

        map.clear();
    }

    /**
     * Stops a running animation (if any) and hides the snapshot.
     */
    public static void stop() {
        if (animator != null) {
            animator.stop();
        } else {
            hideSnapshot();
        }
    }
    
    public static void execute(Runnable runnable){
        java.awt.EventQueue.invokeLater(() -> {
            showSnapshot();
            runnable.run();
            hideSnapshotWithAnimation(160);
        });
    }
    
    public static void execute(Runnable runnable, int duration){
        java.awt.EventQueue.invokeLater(() -> {
            showSnapshot();
            runnable.run();
            hideSnapshotWithAnimation(duration);
        });
    }
    
    
    
}