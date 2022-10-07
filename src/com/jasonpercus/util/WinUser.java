/*
 * Copyright (C) JasonPercus Systems, Inc - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by JasonPercus, 06/2021
 */
package com.jasonpercus.util;



import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.platform.win32.WinDef.PVOID;
import com.sun.jna.platform.win32.WinDef.UINT;
import com.sun.jna.platform.win32.WinUser.WINDOWPLACEMENT;
import com.sun.jna.platform.win32.WinUser.WNDENUMPROC;
import com.sun.jna.win32.StdCallLibrary;



/**
 * Cette classe permet d'appeler des méthodes de la librairie c++ User32.dll (WinUser). Attention: Cette classe est compatible seulement Windows
 * @see Native Utilise JNA
 * @author JasonPercus
 * @version 1.0
 */
public class WinUser {
    
    
    
//CONSTRUCTOR
    /**
     * Constructeur par défaut
     * @deprecated NE PAS UTILISER
     */
    private WinUser(){
        
    }
    
    
    
//METHODES PUBLICS STATICS
    /**
     * Renvoie la liste de toutes les fenêtres Windows ouvertes (cela prend en compte le systray + toutes les fenêtres invisibles que l'utilisateur ne voit pas)
     * @return Retourne la liste de toutes les fenêtre Windows ouvertes
     * @throws OSNotSupportedException Si le système d'exploitation qui exécute cette méthode n'est pas un système Windows
     */
    public static Window[] listOpenedWindows() throws OSNotSupportedException {
        if(!OS.IS_WINDOWS) 
            throw new OSNotSupportedException(WinUser.class.getSimpleName()+" class only works for Windows systems !");
        
        java.util.List<Window> windows = new java.util.ArrayList<>();
        final User32 user32 = User32.INSTANCE;
        user32.EnumWindows(new WNDENUMPROC() {
            int count = 0;

            @Override
            public boolean callback(HWND hWnd, Pointer arg1) {
                byte[] windowText = new byte[512];
                int c = user32.GetWindowTextA(hWnd, windowText, 512);
                String wText = Native.toString(windowText);

                // débarrassez-vous de ce bloc si vous voulez que toutes les fenêtres aient ou non du texte
                if (wText.isEmpty())
                    return true;

                //System.out.println("Found window with text " + hWnd + ", total " + (++count) + " Text: " + wText+" - CountChars["+c+"]");
                
                windows.add(new Window(hWnd, wText));
                return true;
            }
        }, null);
        Window[] ws = new Window[windows.size()];
        for(int i=0;i<windows.size();i++){
            ws[i] = windows.get(i);
        }
        return ws;
    }
    
    /**
     * Renvoie si oui ou non une fenêtre possédant le titre en paramètre, est ouverte
     * @param title Correspond au titre de la fenêtre dont on cherche à déterminer si elle est ouverte
     * @return Retourne true, si elle est ouverte, sinon false
     * @throws OSNotSupportedException Si le système d'exploitation qui exécute cette méthode n'est pas un système Windows
     */
    public static boolean isOpenedWindow(String title) throws OSNotSupportedException {
        Window[] windows = listOpenedWindows();
        for(Window window : windows){
            if(window.name.equals(title))
                return true;
        }
        return false;
    }
    
    /**
     * Renvoie si oui ou non une fenêtre en paramètre, est ouverte
     * @param window Correspond à la fenêtre dont on cherche à déterminer si elle est ouverte
     * @return Retourne true, si elle est ouverte, sinon false
     * @throws OSNotSupportedException Si le système d'exploitation qui exécute cette méthode n'est pas un système Windows
     */
    public static boolean isOpenedWindow(Window window) throws OSNotSupportedException {
        Window[] windows = listOpenedWindows();
        for(Window w : windows){
            if(w.equals(window))
                return true;
        }
        return false;
    }
    
    /**
     * Place la fenêtre spécifiée en haut de l'ordre Z. Si la fenêtre est une fenêtre de niveau supérieur, elle est activée. Si la fenêtre est une fenêtre enfant, la fenêtre parent de niveau supérieur associée à la fenêtre enfant est activée
     * @param window Correspond à la fenêtre à placer en haut de l'ordre
     * @return Retourne true, si la fonction réussit, sinon false
     * @throws OSNotSupportedException Si le système d'exploitation qui exécute cette méthode n'est pas un système Windows
     */
    public static boolean bringWindowToTop(Window window) throws OSNotSupportedException {
        if(!OS.IS_WINDOWS) 
            throw new OSNotSupportedException(WinUser.class.getSimpleName()+" class only works for Windows systems !");
        
        final User32 user32 = User32.INSTANCE;
        return user32.BringWindowToTop(window.windowsPointer);
    }
    
    /**
     * Détruit la fenêtre spécifiée. La fonction envoie des messages WM_DESTROY et WM_NCDESTROY à la fenêtre pour la désactiver et en retirer le focus clavier. La fonction détruit également le menu de la fenêtre, vide la file d'attente des messages du thread, détruit les minuteurs, supprime la propriété du presse-papiers et interrompt la chaîne de visualisation du presse-papiers (si la fenêtre est au sommet de la chaîne de visualisation). Si la fenêtre spécifiée est une fenêtre parent ou propriétaire, DestroyWindow détruit automatiquement la fenêtre enfant ou propriétaire associée lorsqu'il détruit la fenêtre parent ou propriétaire. La fonction détruit d'abord les fenêtres enfant ou propriétaire, puis elle détruit la fenêtre parent ou propriétaire. DestroyWindow détruit également les boîtes de dialogue non modales créées par la fonction CreateDialog
     * @param window Correspond à la fenêtre à détruire
     * @return Retourne true, si la fonction réussit, sinon false
     * @throws OSNotSupportedException Si le système d'exploitation qui exécute cette méthode n'est pas un système Windows
     */
    public static boolean destroyWindow(Window window) throws OSNotSupportedException {
        if(!OS.IS_WINDOWS) 
            throw new OSNotSupportedException(WinUser.class.getSimpleName()+" class only works for Windows systems !");
        
        final User32 user32 = User32.INSTANCE;
        return user32.DestroyWindow(window.windowsPointer);
    }
    
    /**
     * Récupère la fenêtre de bureau
     * @return Retourne la fenêtre de bureau
     * @throws OSNotSupportedException Si le système d'exploitation qui exécute cette méthode n'est pas un système Windows
     */
    public static Window getDesktopWindow() throws OSNotSupportedException {
        if(!OS.IS_WINDOWS) 
            throw new OSNotSupportedException(WinUser.class.getSimpleName()+" class only works for Windows systems !");
        
        final User32 user32 = User32.INSTANCE;
        return new Window(user32.GetDesktopWindow(), null);
    }
    
    /**
     * Récupère la fenêtre de premier plan (la fenêtre avec laquelle l'utilisateur travaille actuellement). Le système attribue une priorité légèrement plus élevée au thread qui crée la fenêtre de premier plan qu'aux autres threads.
     * @return Retourne la fenêtre de premier plan (la fenêtre avec laquelle l'utilisateur travaille actuellement). Le système attribue une priorité légèrement plus élevée au thread qui crée la fenêtre de premier plan qu'aux autres threads.
     * @throws OSNotSupportedException Si le système d'exploitation qui exécute cette méthode n'est pas un système Windows
     */
    public static Window getForegroundWindow() throws OSNotSupportedException {
        if(!OS.IS_WINDOWS) 
            throw new OSNotSupportedException(WinUser.class.getSimpleName()+" class only works for Windows systems !");
        
        final User32 user32 = User32.INSTANCE;
        HWND window = user32.GetForegroundWindow();
        Window[] list = listOpenedWindows();
        for(Window w : list){
            if(w.windowsPointer.equals(window))
                return w;
        }
        return new Window(window, null);
    }
    
    /**
     * Récupère la fenêtre parente d'une fenêtre
     * @param window Correspond à la fenêtre dont on cherche la fenêtre parente
     * @return Retourne la fenêtre parente d'une fenêtre
     * @throws OSNotSupportedException Si le système d'exploitation qui exécute cette méthode n'est pas un système Windows
     */
    public static Window getParentWindow(Window window) throws OSNotSupportedException {
        if(!OS.IS_WINDOWS) 
            throw new OSNotSupportedException(WinUser.class.getSimpleName()+" class only works for Windows systems !");
        
        final User32 user32 = User32.INSTANCE;
        HWND hWnd = user32.GetParent(window.windowsPointer);
        Window[] list = listOpenedWindows();
        for(Window w : list){
            if(w.windowsPointer.equals(hWnd))
                return w;
        }
        return new Window(hWnd, null);
    }
    
    /**
     * Récupère la fenêtre du bureau du Shell
     * @return Retourne la fenêtre du bureau du Shell .Si aucun processus Shell n'est présent, la valeur de retour est NULL
     * @throws OSNotSupportedException Si le système d'exploitation qui exécute cette méthode n'est pas un système Windows
     */
    public static Window getShellWindow() throws OSNotSupportedException {
        if(!OS.IS_WINDOWS) 
            throw new OSNotSupportedException(WinUser.class.getSimpleName()+" class only works for Windows systems !");
        
        final User32 user32 = User32.INSTANCE;
        HWND hWnd = user32.GetShellWindow();
        Window[] list = listOpenedWindows();
        for(Window w : list){
            if(w.windowsPointer.equals(hWnd))
                return w;
        }
        return new Window(hWnd, null);
    }
    
    /**
     * Récupère la couleur actuelle de l'élément d'affichage spécifié. Les éléments d'affichage sont les parties d'une fenêtre et l'affichage qui apparaissent sur l'écran d'affichage du système
     * @param nIndex Correspond à l'élément d'affichage dont la couleur doit être récupérée
     * @return Retourne la couleur actuelle de l'élément d'affichage spécifié
     * @throws OSNotSupportedException Si le système d'exploitation qui exécute cette méthode n'est pas un système Windows
     */
    public static java.awt.Color getSysColor(SysColor nIndex) throws OSNotSupportedException {
        if(!OS.IS_WINDOWS) 
            throw new OSNotSupportedException(WinUser.class.getSimpleName()+" class only works for Windows systems !");
        
        final User32 user32 = User32.INSTANCE;
        WinDef.DWORD dword = user32.GetSysColor(nIndex.nIndex);
        if(dword == null) return null;
        return new java.awt.Color(dword.intValue());
    }
    
    /**
     * Récupère la métrique système ou le paramètre de configuration système spécifié. Notez que toutes les dimensions récupérées par GetSystemMetrics sont en pixels
     * @param nIndex La métrique système ou le paramètre de configuration à récupérer. Ce paramètre peut être l'une des valeurs suivantes. Notez que toutes les valeurs SM_CX* sont des largeurs et toutes les valeurs SM_CY* sont des hauteurs. Notez également que tous les paramètres conçus pour renvoyer des données booléennes représentent TRUE comme toute valeur différente de zéro et FALSE comme valeur zéro
     * @return Retourne la métrique système ou le paramètre de configuration système spécifié
     * @throws OSNotSupportedException Si le système d'exploitation qui exécute cette méthode n'est pas un système Windows
     */
    public static int getSystemMetrics(SystemMetrics nIndex) throws OSNotSupportedException {
        if(!OS.IS_WINDOWS) 
            throw new OSNotSupportedException(WinUser.class.getSimpleName()+" class only works for Windows systems !");
        
        final User32 user32 = User32.INSTANCE;
        return user32.GetSystemMetrics(nIndex.nIndex);
    }
    
    /**
     * Examine l'ordre Z des fenêtres enfants associées à la fenêtre parent spécifiée et récupère la fenêtre enfant en haut de l'ordre Z
     * @param window Correspond à la fenêtre parent dont les fenêtres enfants doivent être examinées. Si ce paramètre est NULL, la fonction renvoie la fenêtre en haut de l'ordre Z
     * @return Si la fonction réussit, la valeur de retour est la fenêtre enfant en haut de l'ordre Z. Si la fenêtre spécifiée n'a pas de fenêtre enfant, la valeur de retour est NULL . Pour obtenir des informations d'erreur étendues, utilisez la fonction GetLastError
     * @throws OSNotSupportedException Si le système d'exploitation qui exécute cette méthode n'est pas un système Windows
     */
    public static Window getTopWindow(Window window) throws OSNotSupportedException {
        if(!OS.IS_WINDOWS) 
            throw new OSNotSupportedException(WinUser.class.getSimpleName()+" class only works for Windows systems !");
        
        final User32 user32 = User32.INSTANCE;
        HWND hWnd = user32.GetTopWindow(window.windowsPointer);
        Window[] list = listOpenedWindows();
        for(Window w : list){
            if(w.windowsPointer.equals(hWnd))
                return w;
        }
        return new Window(hWnd, null);
    }
    
    /**
     * Récupère une fenêtre qui a la relation spécifiée (Ordre Z ou propriétaire) avec la fenêtre spécifiée
     * @param window Une fenêtre. La fenêtre récupéré est relative à cette fenêtre, basé sur la valeur du paramètre uCmd
     * @param uCmd Correspond à la relation entre la fenêtre spécifiée et la fenêtre qui doit être récupérée
     * @return Si la fonction réussit, la valeur de retour est une fenêtre. Si aucune fenêtre n'existe avec la relation spécifiée avec la fenêtre spécifiée, la valeur de retour est NULL
     * @throws OSNotSupportedException Si le système d'exploitation qui exécute cette méthode n'est pas un système Windows
     */
    public static Window getWindow(Window window, EGetWindow uCmd) throws OSNotSupportedException {
        if(!OS.IS_WINDOWS) 
            throw new OSNotSupportedException(WinUser.class.getSimpleName()+" class only works for Windows systems !");
        
        final User32 user32 = User32.INSTANCE;
        HWND hWnd = user32.GetWindow(window.windowsPointer, uCmd.uCmd);
        Window[] list = listOpenedWindows();
        for(Window w : list){
            if(w.windowsPointer.equals(hWnd))
                return w;
        }
        return new Window(hWnd, null);
    }
    
    /**
     * Récupère l'état d'affichage et les positions restaurées, réduites et agrandies de la fenêtre spécifiée. Si la fonction réussi, l'objet window contiendra en interne le résultat
     * @param window Correspond à la fenêtre dont on veut connaitre l'état d'affichage
     * @return Si la fonction réussit, la valeur de retour est différente de zéro. Si la fonction échoue, la valeur de retour est zéro. Pour obtenir des informations d'erreur étendues, appelez GetLastError
     * @throws OSNotSupportedException Si le système d'exploitation qui exécute cette méthode n'est pas un système Windows
     */
    public static boolean getWindowPlacement(Window window) throws OSNotSupportedException {
        if(!OS.IS_WINDOWS) 
            throw new OSNotSupportedException(WinUser.class.getSimpleName()+" class only works for Windows systems !");
        
        final User32 user32 = User32.INSTANCE;
        WINDOWPLACEMENT lpwndpl = new WINDOWPLACEMENT();
        boolean b = user32.GetWindowPlacement(window.windowsPointer, lpwndpl);
        window.flags = lpwndpl.flags;
        window.length = lpwndpl.length;
        window.ptMaxPosition = new java.awt.Point(lpwndpl.ptMaxPosition.x, lpwndpl.ptMaxPosition.y);
        window.ptMinPosition = new java.awt.Point(lpwndpl.ptMinPosition.x, lpwndpl.ptMinPosition.y);
        window.rcNormalPosition = lpwndpl.rcNormalPosition.toRectangle();
        window.showCmd = lpwndpl.showCmd;
        return b;
    }
    
    /**
     * Récupère les dimensions du rectangle de délimitation de la fenêtre spécifiée. Les dimensions sont données en coordonnées d'écran qui sont relatives au coin supérieur gauche de l'écran
     * @param window Correspond à la fenêtre dont on cherche les déléminitations
     * @return Retourne les dimensions du rectangle de délimitation de la fenêtre spécifiée
     * @throws OSNotSupportedException Si le système d'exploitation qui exécute cette méthode n'est pas un système Windows
     */
    public static LPRECT getWindowRect(Window window) throws OSNotSupportedException {
        if(!OS.IS_WINDOWS) 
            throw new OSNotSupportedException(WinUser.class.getSimpleName()+" class only works for Windows systems !");
        
        final User32 user32 = User32.INSTANCE;
        LPRECT lpRect = new LPRECT();
        boolean b = user32.GetWindowRect(window.windowsPointer, lpRect);
        return lpRect;
    }
    
    /**
     * Renvoie le titre d'une fenêtre
     * @param window Correspond à la fenêtre dont on cherche à récupérer le titre
     * @return Retourne le titre d'une fenêtre
     * @throws OSNotSupportedException Si le système d'exploitation qui exécute cette méthode n'est pas un système Windows
     */
    public static String getWindowText(Window window) throws OSNotSupportedException {
        if(!OS.IS_WINDOWS) 
            throw new OSNotSupportedException(WinUser.class.getSimpleName()+" class only works for Windows systems !");
        
        final User32 user32 = User32.INSTANCE;
        byte[] windowText = new byte[512];
        int c = user32.GetWindowTextA(window.windowsPointer, windowText, 512);
        return Native.toString(windowText);
    }
    
    /**
     * Détermine si la fenêtre window est l'un des enfants de la fenêtre parent
     * @param parent Correspond à la fenêtre parent dont on cherche à prouver la paternité de la fenêtre window
     * @param window Correspond à la fenêtre dont ont cherche à prouver qu'elle est fille de parent
     * @return Retourne true si la fenêtre est une fenêtre enfant ou descendante de la fenêtre parent spécifiée. Sinon false
     * @throws OSNotSupportedException Si le système d'exploitation qui exécute cette méthode n'est pas un système Windows
     */
    public static boolean isChild(Window parent, Window window) throws OSNotSupportedException {
        if(!OS.IS_WINDOWS) 
            throw new OSNotSupportedException(WinUser.class.getSimpleName()+" class only works for Windows systems !");
        
        final User32 user32 = User32.INSTANCE;
        return user32.IsChild(parent.windowsPointer, window.windowsPointer);
    }
    
    /**
     * Détermine si le thread appelant est déjà un thread GUI. Il peut également éventuellement convertir le thread en un thread GUI
     * @param bConvert Si true et que le thread n'est pas un thread GUI, converti au passage le thread en thread GUI
     * @return Retourne true si le thread est déjà un thread GUI ou que le thread a bien été converti en thread GUI, sinon false
     * @throws OSNotSupportedException Si le système d'exploitation qui exécute cette méthode n'est pas un système Windows
     */
    public static boolean isGUIThread(boolean bConvert) throws OSNotSupportedException {
        if(!OS.IS_WINDOWS) 
            throw new OSNotSupportedException(WinUser.class.getSimpleName()+" class only works for Windows systems !");
        
        final User32 user32 = User32.INSTANCE;
        return user32.IsGUIThread(bConvert);
    }
    
    /**
     * Détermine si la fenêtre spécifiée est réduite (iconique)
     * @param window Correspond à la fenêtre à tester
     * @return Retourne true si la fenêtre est réduite, sinon false
     * @throws OSNotSupportedException Si le système d'exploitation qui exécute cette méthode n'est pas un système Windows
     */
    public static boolean isIconic(Window window) throws OSNotSupportedException {
        if(!OS.IS_WINDOWS) 
            throw new OSNotSupportedException(WinUser.class.getSimpleName()+" class only works for Windows systems !");
        
        final User32 user32 = User32.INSTANCE;
        return user32.IsIconic(window.windowsPointer);
    }
        
    /**
     * Détermine si la fenêtre spécifiée existe
     * @param window Correspond à la fenêtre à tester
     * @return Retourne true si elle existe, sinon false
     * @throws OSNotSupportedException Si le système d'exploitation qui exécute cette méthode n'est pas un système Windows
     */
    public static boolean existsWindow(Window window) throws OSNotSupportedException {
        if(!OS.IS_WINDOWS) 
            throw new OSNotSupportedException(WinUser.class.getSimpleName()+" class only works for Windows systems !");
        
        final User32 user32 = User32.INSTANCE;
        return user32.IsWindow(window.windowsPointer);
    }
        
    /**
     * Détermine si la fenêtre spécifiée est une fenêtre Unicode native
     * @param window Correspond à la fenêtre à tester
     * @return Retourne true si la fenêtre est une fenêtre Unicode native, sinon false (il s'agit d'une fenêtre ANSI native)
     * @throws OSNotSupportedException Si le système d'exploitation qui exécute cette méthode n'est pas un système Windows
     */
    public static boolean isWindowUnicode(Window window) throws OSNotSupportedException {
        if(!OS.IS_WINDOWS) 
            throw new OSNotSupportedException(WinUser.class.getSimpleName()+" class only works for Windows systems !");
        
        final User32 user32 = User32.INSTANCE;
        return user32.IsWindowUnicode(window.windowsPointer);
    }

    /**
     * Détermine l'état de visibilité de la fenêtre spécifiée
     * @param window Correspond à la fenêtre à tester
     * @return Si la fenêtre spécifiée, sa fenêtre parent, la fenêtre parent de son parent, et ainsi de suite, ont le style WS_VISIBLE, alors retourne true, sinon false
     * @throws OSNotSupportedException Si le système d'exploitation qui exécute cette méthode n'est pas un système Windows
     */
    public static boolean isWindowVisible(Window window) throws OSNotSupportedException {
        if(!OS.IS_WINDOWS) 
            throw new OSNotSupportedException(WinUser.class.getSimpleName()+" class only works for Windows systems !");
        
        final User32 user32 = User32.INSTANCE;
        return user32.IsWindowVisible(window.windowsPointer);
    }

    /**
     * Détermine si une fenêtre est agrandie
     * @param window Correspond à la fenêtre à tester
     * @return Retourne true si la fenêtre est agrandie, sinon false
     * @throws OSNotSupportedException Si le système d'exploitation qui exécute cette méthode n'est pas un système Windows
     */
    public static boolean isZoomed(Window window) throws OSNotSupportedException {
        if(!OS.IS_WINDOWS) 
            throw new OSNotSupportedException(WinUser.class.getSimpleName()+" class only works for Windows systems !");
        
        final User32 user32 = User32.INSTANCE;
        return user32.IsZoomed(window.windowsPointer);
    }
    
    /**
     * Le processus de premier plan peut appeler la fonction LockSetForegroundWindow pour désactiver les appels à la fonction setForegroundWindow()
     * @param lock Correspond à true si l'on souhaite bloquer l'utilisation de setForegroundWindow(), sinon false
     * @return Retourne true si le changement a bien fonctionné, sinon false
     * @throws OSNotSupportedException Si le système d'exploitation qui exécute cette méthode n'est pas un système Windows
     */
    public static boolean lockSetForegroundWindow(boolean lock) throws OSNotSupportedException {
        if(!OS.IS_WINDOWS) 
            throw new OSNotSupportedException(WinUser.class.getSimpleName()+" class only works for Windows systems !");
        
        final User32 user32 = User32.INSTANCE;
        return user32.LockSetForegroundWindow(new UINT((lock) ? 1 : 2));
    }
        
    /**
     * Modifie la position et les dimensions de la fenêtre spécifiée. Pour une fenêtre de niveau supérieur, la position et les dimensions sont relatives au coin supérieur gauche de l'écran. Pour une fenêtre enfant, ils sont relatifs au coin supérieur gauche de la zone cliente de la fenêtre parent
     * @param window Correspond à la fenêtre à modifier
     * @param x La nouvelle position du côté gauche de la fenêtre
     * @param y La nouvelle position du haut de la fenêtre
     * @param width La nouvelle largeur de la fenêtre
     * @param height La nouvelle hauteur de la fenêtre
     * @param repaint Indique si la fenêtre doit être repeinte. Si ce paramètre est TRUE , la fenêtre reçoit un message. Si le paramètre est FALSE , aucun repeint d'aucune sorte ne se produit. Cela s'applique à la zone cliente, à la zone non cliente (y compris la barre de titre et les barres de défilement) et à toute partie de la fenêtre parent découverte suite au déplacement d'une fenêtre enfant
     * @return Retourne true, si la fonction réussit, sinon false
     * @throws OSNotSupportedException Si le système d'exploitation qui exécute cette méthode n'est pas un système Windows
     */
    public static boolean moveWindow(Window window, int x, int y, int width, int height, boolean repaint) throws OSNotSupportedException {
        if(!OS.IS_WINDOWS) 
            throw new OSNotSupportedException(WinUser.class.getSimpleName()+" class only works for Windows systems !");
        
        final User32 user32 = User32.INSTANCE;
        return user32.MoveWindow(window.windowsPointer, x, y, width, height, repaint);
    }
        
    /**
     * Restaure une fenêtre réduite (iconique) à sa taille et à sa position précédentes ; il active alors la fenêtre
     * @param window Correspond à la fenêtre à restaurer
     * @return Retourne true si la fonction réussit
     * @throws OSNotSupportedException Si le système d'exploitation qui exécute cette méthode n'est pas un système Windows
     */
    public static boolean openIcon(Window window) throws OSNotSupportedException {
        if(!OS.IS_WINDOWS) 
            throw new OSNotSupportedException(WinUser.class.getSimpleName()+" class only works for Windows systems !");
        
        final User32 user32 = User32.INSTANCE;
        return user32.OpenIcon(window.windowsPointer);
    }
        
    /**
     * Amène le thread qui a créé la fenêtre spécifiée au premier plan et active la fenêtre. La saisie au clavier est dirigée vers la fenêtre et divers repères visuels sont modifiés pour l'utilisateur. Le système attribue une priorité légèrement plus élevée au thread qui a créé la fenêtre de premier plan qu'aux autres threads
     * @param window Correspond à la fenêtre qui doit être activée et amenée au premier plan
     * @return Retourne true si la fenêtre a été mise au premier plan, sinon false
     * @throws OSNotSupportedException Si le système d'exploitation qui exécute cette méthode n'est pas un système Windows
     */
    public static boolean setForegroundWindow(Window window) throws OSNotSupportedException {
        if(!OS.IS_WINDOWS) 
            throw new OSNotSupportedException(WinUser.class.getSimpleName()+" class only works for Windows systems !");
        
        final User32 user32 = User32.INSTANCE;
        return user32.SetForegroundWindow(window.windowsPointer);
    }
        
    /**
     * Définit l'état d'affichage de la fenêtre spécifiée
     * @param window Correspond à la fenêtre dont il faut modifier l'état
     * @param nCmdShow Contrôle la façon dont la fenêtre doit être affichée. Ce paramètre est ignoré la première fois qu'une application appelle ShowWindow , si le programme qui a lancé l'application fournit une structure STARTUPINFO . Sinon, la première fois que ShowWindow est appelé, la valeur doit être la valeur obtenue par la fonction WinMain dans son paramètre nCmdShow
     * @return Retourne true, si la fenêtre était précédemment visible. Retourne false si la fenêtre était précédemment masquée.
     * @throws OSNotSupportedException Si le système d'exploitation qui exécute cette méthode n'est pas un système Windows
     */
    public static boolean showWindow(Window window, CmdShow nCmdShow) throws OSNotSupportedException {
        if(!OS.IS_WINDOWS) 
            throw new OSNotSupportedException(WinUser.class.getSimpleName()+" class only works for Windows systems !");
        
        final User32 user32 = User32.INSTANCE;
        return user32.ShowWindow(window.windowsPointer, nCmdShow.nCmdShow);
    }
        
    /**
     * Définit l'état d'affichage de la fenêtre spécifiée sans attendre la fin de l'opération
     * @param window Correspond à la fenêtre dont il faut modifier l'état
     * @param nCmdShow Contrôle la façon dont la fenêtre doit être affichée. Ce paramètre est ignoré la première fois qu'une application appelle ShowWindow , si le programme qui a lancé l'application fournit une structure STARTUPINFO . Sinon, la première fois que ShowWindow est appelé, la valeur doit être la valeur obtenue par la fonction WinMain dans son paramètre nCmdShow
     * @return Retourne true, si la fenêtre était précédemment visible. Retourne false si la fenêtre était précédemment masquée.
     * @throws OSNotSupportedException Si le système d'exploitation qui exécute cette méthode n'est pas un système Windows
     */
    public static boolean showWindowAsync(Window window, CmdShow nCmdShow) throws OSNotSupportedException {
        if(!OS.IS_WINDOWS) 
            throw new OSNotSupportedException(WinUser.class.getSimpleName()+" class only works for Windows systems !");
        
        final User32 user32 = User32.INSTANCE;
        return user32.ShowWindowAsync(window.windowsPointer, nCmdShow.nCmdShow);
    }
        
    /**
     * Déclenche un signal visuel pour indiquer qu'un son est joué
     * @return Retourne true si le signal visuel était ou sera affiché correctement, sinon false, une erreur a empêché l'affichage du signal
     * @throws OSNotSupportedException Si le système d'exploitation qui exécute cette méthode n'est pas un système Windows
     */
    public static boolean soundSentry() throws OSNotSupportedException {
        if(!OS.IS_WINDOWS) 
            throw new OSNotSupportedException(WinUser.class.getSimpleName()+" class only works for Windows systems !");
        
        final User32 user32 = User32.INSTANCE;
        return user32.SoundSentry();
    }
        
    /**
     * Récupère ou définit la valeur de l'un des paramètres à l'échelle du système. Cette fonction permet également de mettre à jour le profil utilisateur lors du paramétrage d'un paramètre
     * @param uiAction Paramètre à l'échelle du système à récupérer ou à définir
     * @param uiParam Paramètre dont l'utilisation et le format dépendent du paramètre système interrogé ou défini. Pour plus d'informations sur les paramètres à l'échelle du système, consultez le paramètre uiAction . Sauf indication contraire, vous devez spécifier zéro pour ce paramètre
     * @param pvParam Paramètre dont l'utilisation et le format dépendent du paramètre système interrogé ou défini. Pour plus d'informations sur les paramètres à l'échelle du système, consultez le paramètre uiAction . Sauf indication contraire, vous devez spécifier NULL pour ce paramètre
     * @param fWinIni Si un paramètre système est défini, spécifie si le profil utilisateur doit être mis à jour et, dans l'affirmative, si le message WM_SETTINGCHANGE doit être diffusé à toutes les fenêtres de niveau supérieur pour les informer du changement. paramètre peut être zéro si vous ne souhaitez pas mettre à jour le profil utilisateur ou diffuser le message WM_SETTINGCHANGE , ou il peut être une ou plusieurs des valeurs suivantes
     * @return Retourne true si la fonction réussit
     * @throws OSNotSupportedException Si le système d'exploitation qui exécute cette méthode n'est pas un système Windows
     */
    public static boolean systemParametersInfo(UiAction uiAction, UINT uiParam, PVOID pvParam, UINT fWinIni) throws OSNotSupportedException {
        if(!OS.IS_WINDOWS) 
            throw new OSNotSupportedException(WinUser.class.getSimpleName()+" class only works for Windows systems !");
        
        final User32 user32 = User32.INSTANCE;
        return user32.SystemParametersInfoA(uiAction.uiAction, uiParam, pvParam, fWinIni);
    }
    
    
    
//CLASS
    /**
     * Cette classe permet de manimuler des fenêtres windows grâce à l'intermédiaire JNA. Attention: Cette classe est compatible seulement windows
     * @see Native Utilise JNA
     * @author JasonPercus
     * @version 1.0
     */
    public static class Window{
        
        
        
    //ATTRIBUTS
        /**
         * Correspond au pointeur de la fenêtre windows
         */
        private final HWND windowsPointer;

        /**
         * Correspond au nom de la fenêtre windows
         */
        private final String name;
        
        /**
         * Correspond aux indicateurs qui contrôlent la position de la fenêtre réduite et la méthode par laquelle la fenêtre est restaurée
         */
        private int flags;
        
        /**
         * Correspond à la longueur de la structure, en octets
         */
        private int length;
        
        /**
         * Correspond à la coordonnée du coin supérieur droit de la fenêtre lorsqu'elle est agrandie
         */
        private java.awt.Point ptMaxPosition;
        
        /**
         * Correspond à la position virtuelle du coin supérieur gauche de la fenêtre lorsqu'elle est réduite
         */
        private java.awt.Point ptMinPosition;
        
        /**
         * Correspond aux coordonnées de la fenêtre lorsque la fenêtre est dans la position restaurée
         */
        private java.awt.Rectangle rcNormalPosition;
        
        /**
         * Correspond à l'état d'affichage actuel de la fenêtre
         */
        private int showCmd;



    //CONSTRUCTORS
        /**
         * Crée une fenêtre Windows
         * @deprecated Aucun intérêt
         */
        private Window(){
            this(null, null);
        }

        /**
         * Crée une fenêtre Windows
         * @param windowsPointer Correspond au pointeur de la fenêtre Windows
         * @param name Correspond au nom de la fenêtre Windows
         * @deprecated Ne pas utiliser
         */
        private Window(HWND windowsPointer, String name) {
            this.windowsPointer = windowsPointer;
            this.name = name;
        }



    //METHODES PUBLICS
        /**
         * Renvoie le nom de la fenêtre
         * @return Retourne le nom de la fenêtre
         */
        public String getName() {
            return name;
        }

        /**
         * Renvoie les indicateurs qui contrôlent la position de la fenêtre réduite et la méthode par laquelle la fenêtre est restaurée
         * @return Retourne les indicateurs qui contrôlent la position de la fenêtre réduite et la méthode par laquelle la fenêtre est restaurée
         */
        public int getFlags() {
            return flags;
        }

        /**
         * Renvoie la longueur de la structure, en octets
         * @return Retourne la longueur de la structure, en octets
         */
        public int getLength() {
            return length;
        }

        /**
         * Renvoie la coordonnée du coin supérieur droit de la fenêtre lorsqu'elle est agrandie
         * @return Retourne la coordonnée du coin supérieur droit de la fenêtre lorsqu'elle est agrandie
         */
        public java.awt.Point getPtMaxPosition() {
            return ptMaxPosition;
        }

        /**
         * Renvoie la position virtuelle du coin supérieur gauche de la fenêtre lorsqu'elle est réduite
         * @return Retourne la position virtuelle du coin supérieur gauche de la fenêtre lorsqu'elle est réduite
         */
        public java.awt.Point getPtMinPosition() {
            return ptMinPosition;
        }

        /**
         * Renvoie les coordonnées de la fenêtre lorsque la fenêtre est dans la position restaurée
         * @return Retourne les coordonnées de la fenêtre lorsque la fenêtre est dans la position restaurée
         */
        public java.awt.Rectangle getRcNormalPosition() {
            return rcNormalPosition;
        }

        /**
         * Renvoie l'état d'affichage actuel de la fenêtre
         * @return Retourne l'état d'affichage actuel de la fenêtre
         */
        public int getShowCmd() {
            return showCmd;
        }

        /**
         * Détermine si la fenêtre existe
         * @return Retourne true si elle existe, sinon false
         * @throws OSNotSupportedException Si le système d'exploitation qui exécute cette méthode n'est pas un système Windows
         */
        public boolean exists() throws OSNotSupportedException {
            return WinUser.existsWindow(this);
        }
        
        /**
         * Renvoie le titre de la fenêtre
         * @return Retourne le titre de la fenêtre
         * @throws OSNotSupportedException Si le système d'exploitation qui exécute cette méthode n'est pas un système Windows
         */
        public String getWindowText() throws OSNotSupportedException {
            return WinUser.getWindowText(this);
        }

        /**
         * Récupère la fenêtre parente de cette fenêtre
         * @return Retourne la fenêtre parente de cette fenêtre
         * @throws OSNotSupportedException Si le système d'exploitation qui exécute cette méthode n'est pas un système Windows
         */
        public Window getParent() throws OSNotSupportedException {
            return WinUser.getParentWindow(this);
        }
        
        /**
         * Examine l'ordre Z des fenêtres enfants associées à cette fenêtre parent spécifiée et récupère la fenêtre enfant en haut de l'ordre Z
         * @return Si la fonction réussit, la valeur de retour est la fenêtre enfant en haut de l'ordre Z. Si cette fenêtre spécifiée n'a pas de fenêtre enfant, la valeur de retour est NULL . Pour obtenir des informations d'erreur étendues, utilisez la fonction GetLastError
         * @throws OSNotSupportedException Si le système d'exploitation qui exécute cette méthode n'est pas un système Windows
         */
        public Window getTop() throws OSNotSupportedException {
            return WinUser.getTopWindow(this);
        }
        
        /**
         * Récupère une fenêtre qui a la relation spécifiée (Ordre Z ou propriétaire) avec cette fenêtre
         * @param uCmd Correspond à la relation entre cette fenêtre et la fenêtre qui doit être récupérée
         * @return Si la fonction réussit, la valeur de retour est une fenêtre. Si aucune fenêtre n'existe avec la relation spécifiée avec cette fenêtre, la valeur de retour est NULL
         * @throws OSNotSupportedException Si le système d'exploitation qui exécute cette méthode n'est pas un système Windows
         */
        public Window getWindow(EGetWindow uCmd) throws OSNotSupportedException {
            return WinUser.getWindow(this, uCmd);
        }

        /**
         * Renvoie si oui ou non cette fenêtre, est ouverte
         * @return Retourne true, si elle est ouverte, sinon false
         * @throws OSNotSupportedException Si le système d'exploitation qui exécute cette méthode n'est pas un système Windows
         */
        public boolean isOpened() throws OSNotSupportedException {
            return WinUser.isOpenedWindow(this);
        }

        /**
         * Place cette fenêtre en haut de l'ordre Z. Si la fenêtre est une fenêtre de niveau supérieur, elle est activée. Si la fenêtre est une fenêtre enfant, la fenêtre parent de niveau supérieur associée à la fenêtre enfant est activée
         * @return Retourne true, si la fonction réussit, sinon false
         * @throws OSNotSupportedException Si le système d'exploitation qui exécute cette méthode n'est pas un système Windows
         */
        public boolean bringToTop() throws OSNotSupportedException {
            return WinUser.bringWindowToTop(this);
        }

        /**
         * Détruit la fenêtre. La fonction envoie des messages WM_DESTROY et WM_NCDESTROY à la fenêtre pour la désactiver et en retirer le focus clavier. La fonction détruit également le menu de la fenêtre, vide la file d'attente des messages du thread, détruit les minuteurs, supprime la propriété du presse-papiers et interrompt la chaîne de visualisation du presse-papiers (si la fenêtre est au sommet de la chaîne de visualisation). Si la fenêtre spécifiée est une fenêtre parent ou propriétaire, DestroyWindow détruit automatiquement la fenêtre enfant ou propriétaire associée lorsqu'il détruit la fenêtre parent ou propriétaire. La fonction détruit d'abord les fenêtres enfant ou propriétaire, puis elle détruit la fenêtre parent ou propriétaire. DestroyWindow détruit également les boîtes de dialogue non modales créées par la fonction CreateDialog
         * @return Retourne true, si la fonction réussit, sinon false
         * @throws OSNotSupportedException Si le système d'exploitation qui exécute cette méthode n'est pas un système Windows
         */
        public boolean destroy() throws OSNotSupportedException {
            return WinUser.destroyWindow(this);
        }

        /**
         * Renvoie si cette fenêtre est au premier plan (la fenêtre avec laquelle l'utilisateur travaille actuellement)
         * @return Retourne true si c'est le cas, sinon false
         * @throws OSNotSupportedException Si le système d'exploitation qui exécute cette méthode n'est pas un système Windows
         */
        public boolean isForeground() throws OSNotSupportedException {
            return WinUser.getForegroundWindow().equals(this);
        }

        /**
         * Renvoie si oui ou non cette fenêtre est la fenêtre de bureau
         * @return Retourne true si elle l'est sinon false
         * @throws OSNotSupportedException Si le système d'exploitation qui exécute cette méthode n'est pas un système Windows
         */
        public boolean isDesktopWindow() throws OSNotSupportedException {
            return WinUser.getDesktopWindow().equals(this);
        }

        /**
         * Renvoie si oui ou non cette fenêtre est la fenêtre du bureau du shell
         * @return Retourne true si elle l'est sinon false
         * @throws OSNotSupportedException Si le système d'exploitation qui exécute cette méthode n'est pas un système Windows
         */
        public boolean isShellWindow() throws OSNotSupportedException {
            return WinUser.getShellWindow().equals(this);
        }
        
        /**
         * Détermine si cette fenêtre est l'un des enfants de la fenêtre parent
         * @param parent Correspond à la fenêtre parent dont on cherche à prouver la paternité de cette fenêtre
         * @return Retourne true si cette fenêtre est une fenêtre enfant ou descendante de la fenêtre parent spécifiée. Sinon false
         * @throws OSNotSupportedException Si le système d'exploitation qui exécute cette méthode n'est pas un système Windows
         */
        public boolean isChild(Window parent) throws OSNotSupportedException {
            return WinUser.isChild(parent, this);
        }
        
        /**
         * Détermine si cette fenêtre est réduite (iconique)
         * @return Retourne true si la fenêtre est réduite, sinon false
         * @throws OSNotSupportedException Si le système d'exploitation qui exécute cette méthode n'est pas un système Windows
         */
        public boolean isIconic() throws OSNotSupportedException {
            return WinUser.isIconic(this);
        }
        
        /**
         * Détermine si la fenêtre est une fenêtre Unicode native
         * @return Retourne true si la fenêtre est une fenêtre Unicode native, sinon false (il s'agit d'une fenêtre ANSI native)
         * @throws OSNotSupportedException Si le système d'exploitation qui exécute cette méthode n'est pas un système Windows
         */
        public boolean isWindowUnicode() throws OSNotSupportedException {
            return WinUser.isWindowUnicode(this);
        }

        /**
         * Détermine l'état de visibilité de la fenêtre
         * @return Si la fenêtre, sa fenêtre parent, la fenêtre parent de son parent, et ainsi de suite, ont le style WS_VISIBLE, alors retourne true, sinon false
         * @throws OSNotSupportedException Si le système d'exploitation qui exécute cette méthode n'est pas un système Windows
         */
        public boolean isVisible() throws OSNotSupportedException {
            return WinUser.isWindowVisible(this);
        }

        /**
         * Détermine si la fenêtre est agrandie
         * @return Retourne true si elle l'est, sinon false
         * @throws OSNotSupportedException Si le système d'exploitation qui exécute cette méthode n'est pas un système Windows
         */
        public boolean isZoomed() throws OSNotSupportedException {
            return WinUser.isZoomed(this);
        }
        
        /**
         * Récupère l'état d'affichage et les positions restaurées, réduites et agrandies de cette fenêtre. Si la fonction réussi, l'objet window contiendra en interne le résultat
         * @return Si la fonction réussit, la valeur de retour est différente de zéro. Si la fonction échoue, la valeur de retour est zéro. Pour obtenir des informations d'erreur étendues, appelez GetLastError
         * @throws OSNotSupportedException Si le système d'exploitation qui exécute cette méthode n'est pas un système Windows
         */
        public boolean getPlacement(){
            return WinUser.getWindowPlacement(this);
        }
        
        /**
         * Le processus de premier plan peut appeler la fonction LockSetForegroundWindow pour désactiver les appels à la fonction setForegroundWindow()
         * @param lock Correspond à true si l'on souhaite bloquer l'utilisation de setForegroundWindow(), sinon false
         * @return Retourne true si le changement a bien fonctionné, sinon false
         * @throws OSNotSupportedException Si le système d'exploitation qui exécute cette méthode n'est pas un système Windows
         */
        public static boolean lockSetForeground(boolean lock) throws OSNotSupportedException {
            if(!OS.IS_WINDOWS) 
                throw new OSNotSupportedException(WinUser.class.getSimpleName()+" class only works for Windows systems !");

            final User32 user32 = User32.INSTANCE;
            return user32.LockSetForegroundWindow(new UINT((lock) ? 1 : 2));
        }
        
        /**
         * Modifie la position et les dimensions de la fenêtre. Pour une fenêtre de niveau supérieur, la position et les dimensions sont relatives au coin supérieur gauche de l'écran. Pour une fenêtre enfant, ils sont relatifs au coin supérieur gauche de la zone cliente de la fenêtre parent
         * @param x La nouvelle position du côté gauche de la fenêtre
         * @param y La nouvelle position du haut de la fenêtre
         * @param width La nouvelle largeur de la fenêtre
         * @param height La nouvelle hauteur de la fenêtre
         * @param repaint Indique si la fenêtre doit être repeinte. Si ce paramètre est TRUE , la fenêtre reçoit un message. Si le paramètre est FALSE , aucun repeint d'aucune sorte ne se produit. Cela s'applique à la zone cliente, à la zone non cliente (y compris la barre de titre et les barres de défilement) et à toute partie de la fenêtre parent découverte suite au déplacement d'une fenêtre enfant
         * @return Retourne true, si la fonction réussit, sinon false
         */
        public boolean moveWindow(int x, int y, int width, int height, boolean repaint) throws OSNotSupportedException {
            return WinUser.moveWindow(this, x, y, width, height, repaint);
        }
        
        /**
         * Amène le thread qui a créé la fenêtre au premier plan et active la fenêtre. La saisie au clavier est dirigée vers la fenêtre et divers repères visuels sont modifiés pour l'utilisateur. Le système attribue une priorité légèrement plus élevée au thread qui a créé la fenêtre de premier plan qu'aux autres threads
         * @return Retourne true si la fenêtre a été mise au premier plan, sinon false
         * @throws OSNotSupportedException Si le système d'exploitation qui exécute cette méthode n'est pas un système Windows
         */
        public boolean setForegroundWindow() throws OSNotSupportedException {
            return WinUser.setForegroundWindow(this);
        }
        
        /**
         * Restaure la fenêtre réduite (iconique) à sa taille et à sa position précédentes ; il active alors la fenêtre
         * @return Retourne true si la fonction réussit
         */
        public boolean openIcon() throws OSNotSupportedException {
            return WinUser.openIcon(this);
        }
        
        /**
         * Définit l'état d'affichage de la fenêtre
         * @param nCmdShow Contrôle la façon dont la fenêtre doit être affichée. Ce paramètre est ignoré la première fois qu'une application appelle ShowWindow , si le programme qui a lancé l'application fournit une structure STARTUPINFO . Sinon, la première fois que ShowWindow est appelé, la valeur doit être la valeur obtenue par la fonction WinMain dans son paramètre nCmdShow
         * @return Retourne true, si la fenêtre était précédemment visible. Retourne false si la fenêtre était précédemment masquée.
         */
        public boolean show(CmdShow nCmdShow) throws OSNotSupportedException {
            return WinUser.showWindow(this, nCmdShow);
        }
        
        /**
         * Définit l'état d'affichage de la fenêtre sans attendre la fin de l'opération
         * @param nCmdShow Contrôle la façon dont la fenêtre doit être affichée. Ce paramètre est ignoré la première fois qu'une application appelle ShowWindow , si le programme qui a lancé l'application fournit une structure STARTUPINFO . Sinon, la première fois que ShowWindow est appelé, la valeur doit être la valeur obtenue par la fonction WinMain dans son paramètre nCmdShow
         * @return Retourne true, si la fenêtre était précédemment visible. Retourne false si la fenêtre était précédemment masquée.
         */
        public boolean showAsync(CmdShow nCmdShow) throws OSNotSupportedException {
            return WinUser.showWindowAsync(this, nCmdShow);
        }

        /**
         * Renvoie le hashCode de la fenêtre
         * @return Retourne le hashCode de la fenêtre
         */
        @Override
        public int hashCode() {
            int hash = 5;
            hash = 41 * hash + java.util.Objects.hashCode(this.windowsPointer);
            hash = 41 * hash + java.util.Objects.hashCode(this.name);
            return hash;
        }

        /**
         * Détermine si deux fenêtres Windows sont identiques
         * @param obj Correspond à la seconde fenêtre à comparer à la courante
         * @return Retourne true si elles sont identiques, sinon false
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
            final Window other = (Window) obj;
            if (!java.util.Objects.equals(this.name, other.name)) {
                return false;
            }
            return java.util.Objects.equals(this.windowsPointer, other.windowsPointer);
        }

        /**
         * Renvoie la fenêtre sous la forme d'une chaîne de caractères
         * @return Retourne la fenêtre sous la forme d'une chaîne de caractères
         */
        @Override
        public String toString() {
            return "Window{"+name+'}';
        }
        
        
        
    }
    
    /**
     * Cette classe représente la structure RECT qui définit un rectangle par les coordonnées de ses coins supérieur gauche et inférieur droit
     * @see Native Utilise JNA
     * @author JasonPercus
     * @version 1.0
     */
    @Structure.FieldOrder(value = {"left", "top", "right", "bottom"})
    public static class LPRECT extends Structure {
        
        
        
    //ATTRIBUTS
        /**
         * Spécifie la coordonnée x du coin supérieur gauche du rectangle
         */
        public long left;
        
        /**
         * Spécifie la coordonnée y du coin supérieur gauche du rectangle
         */
        public long top;
        
        /**
         * Spécifie la coordonnée x du coin inférieur droit du rectangle
         */
        public long right;
        
        /**
         * Spécifie la coordonnée y du coin inférieur droit du rectangle
         */
        public long bottom;
        
        
        
    }
    
    
    
//ENUMS
    /**
     * Cette classe d'énumération contient les valeurs possibles d'affichage dont la couleur doit être récupérée
     * @see WinUser#getSysColor(com.jasonpercus.util.WinUser.SysColor)
     * @author JasonPercus
     * @version 1.0
     */
    public enum SysColor {
        
        /**
         * Ombre sombre pour les éléments d'affichage en trois dimensions
         */
        COLOR_3DDKSHADOW(21),
        
        /**
         * Couleur de la face pour les éléments d'affichage en trois dimensions et pour les arrière-plans des boîtes de dialogue
         */
        COLOR_3DFACE(15),
        
        /**
         * Couleur de surbrillance pour les éléments d'affichage en trois dimensions (pour les bords faisant face à la source lumineuse)
         */
        COLOR_3DHIGHLIGHT(20),
        
        /**
         * Couleur de surbrillance pour les éléments d'affichage en trois dimensions (pour les bords faisant face à la source lumineuse)
         */
        COLOR_3DHILIGHT(20),
        
        /**
         * Couleur de la lumière pour les éléments d'affichage tridimensionnels (pour les bords faisant face à la source lumineuse)
         */
        COLOR_3DLIGHT(22),
        
        /**
         * Couleur d'ombre pour les éléments d'affichage en trois dimensions (pour les bords opposés à la source lumineuse)
         */
        COLOR_3DSHADOW(16),
        
        /**
         * Bordure de fenêtre active
         */
        COLOR_ACTIVEBORDER(10),
        
        /**
         * Barre de titre de la fenêtre active. La couleur de premier plan associée est COLOR_CAPTIONTEXT. Spécifie la couleur du côté gauche du dégradé de couleurs de la barre de titre d'une fenêtre active si l'effet de dégradé est activé
         */
        COLOR_ACTIVECAPTION(2),
        
        /**
         * Couleur d'arrière-plan des applications d'interface de documents multiples (MDI)
         */
        COLOR_APPWORKSPACE(12),
        
        /**
         * Bureau
         */
        COLOR_BACKGROUND(1),
        
        /**
         * Couleur de face pour les éléments d'affichage en trois dimensions et pour les arrière-plans des boîtes de dialogue. La couleur de premier plan associée est COLOR_BTNTEXT
         */
        COLOR_BTNFACE(15),
        
        /**
         * Couleur de surbrillance pour les éléments d'affichage en trois dimensions (pour les bords faisant face à la source lumineuse)
         */
        COLOR_BTNHIGHLIGHT(20),
        
        /**
         * Couleur de surbrillance pour les éléments d'affichage en trois dimensions (pour les bords faisant face à la source lumineuse)
         */
        COLOR_BTNHILIGHT(20),
        
        /**
         * Couleur d'ombre pour les éléments d'affichage en trois dimensions (pour les bords opposés à la source lumineuse)
         */
        COLOR_BTNSHADOW(16),
        
        /**
         * Texte sur les boutons poussoirs. La couleur d'arrière-plan associée est COLOR_BTNFACE
         */
        COLOR_BTNTEXT(18),
        
        /**
         * Texte dans la légende, la zone de taille et la zone de flèche de la barre de défilement. La couleur d'arrière-plan associée est COLOR_ACTIVECAPTION
         */
        COLOR_CAPTIONTEXT(9),
        
        /**
         * Bureau
         */
        COLOR_DESKTOP(1),
        
        /**
         * Couleur du côté droit dans le dégradé de couleurs de la barre de titre d'une fenêtre active. COLOR_ACTIVECAPTION spécifie la couleur du côté gauche. Utilisez SPI_GETGRADIENTCAPTIONS avec la fonction SystemParametersInfo pour déterminer si l'effet de dégradé est activé
         */
        COLOR_GRADIENTACTIVECAPTION(27),
        
        /**
         * Couleur du côté droit dans le dégradé de couleurs de la barre de titre d'une fenêtre inactive. COLOR_INACTIVECAPTION spécifie la couleur du côté gauche
         */
        COLOR_GRADIENTINACTIVECAPTION(28),
        
        /**
         * Texte grisé (désactivé). Cette couleur est définie sur 0 si le pilote d'affichage actuel ne prend pas en charge une couleur grise unie
         */
        COLOR_GRAYTEXT(17),
        
        /**
         * Élément(s) sélectionné(s) dans un champ. La couleur de premier plan associée est COLOR_HIGHLIGHTTEXT
         */
        COLOR_HIGHLIGHT(13),
        
        /**
         * Texte du ou des éléments sélectionnés dans un champ. La couleur d'arrière-plan associée est COLOR_HIGHLIGHT
         */
        COLOR_HIGHLIGHTTEXT(14),
        
        /**
         * Couleur d'un lien hypertexte ou d'un élément suivi à chaud. La couleur d'arrière-plan associée est COLOR_WINDOW
         */
        COLOR_HOTLIGHT(26),
        
        /**
         * Bordure de fenêtre inactive
         */
        COLOR_INACTIVEBORDER(11),
        
        /**
         * Légende de la fenêtre inactive. La couleur de premier plan associée est COLOR_INACTIVECAPTIONTEXT. Spécifie la couleur du côté gauche du dégradé de couleurs de la barre de titre d'une fenêtre inactive si l'effet de dégradé est activé
         */
        COLOR_INACTIVECAPTION(3),
        
        /**
         * Couleur du texte dans une légende inactive. La couleur d'arrière-plan associée est COLOR_INACTIVECAPTION
         */
        COLOR_INACTIVECAPTIONTEXT(19),
        
        /**
         * Couleur d'arrière-plan des contrôles d'info-bulle. La couleur de premier plan associée est COLOR_INFOTEXT
         */
        COLOR_INFOBK(24),
        
        /**
         * Couleur du texte pour les contrôles d'info-bulle. La couleur d'arrière-plan associée est COLOR_INFOBK
         */
        COLOR_INFOTEXT(23),
        
        /**
         * Arrière-plan des menus. La couleur de premier plan associée est COLOR_MENUTEXT
         */
        COLOR_MENU(4),
        
        /**
         * La couleur utilisée pour mettre en surbrillance les éléments de menu lorsque le menu apparaît sous forme de menu plat (voir SystemParametersInfo ). L'élément de menu en surbrillance est entouré de COLOR_HIGHLIGHT
         */
        COLOR_MENUHILIGHT(29),
        
        /**
         * La couleur d'arrière-plan de la barre de menus lorsque les menus apparaissent sous forme de menus plats (voir SystemParametersInfo ). Cependant, COLOR_MENU continue de spécifier la couleur d'arrière-plan du menu contextuel
         */
        COLOR_MENUBAR(30),
        
        /**
         * Texte dans les menus. La couleur d'arrière-plan associée est COLOR_MENU
         */
        COLOR_MENUTEXT(7),
        
        /**
         * Zone grise de la barre de défilement
         */
        COLOR_SCROLLBAR(0),
        
        /**
         * Fond de fenêtre. Les couleurs de premier plan associées sont COLOR_WINDOWTEXT et COLOR_HOTLITE
         */
        COLOR_WINDOW(5),
        
        /**
         * Cadre de fenêtre
         */
        COLOR_WINDOWFRAME(6),
        
        /**
         * Texte dans les fenêtres. La couleur d'arrière-plan associée est COLOR_WINDOW
         */
        COLOR_WINDOWTEXT(8);
        
        
        
    //ATTRIBUT
        /**
         * Correspond à l'élément d'affichage dont la couleur doit être récupérée
         */
        protected final int nIndex;

        
        
    //CONSTRUCTOR
        /**
         * Crée un SysColor
         * @param nIndex Correspond à l'élément d'affichage dont la couleur doit être récupérée
         */
        private SysColor(int nIndex) {
            this.nIndex = nIndex;
        }
        
        
        
    }
    
    /**
     * Cette classe d'énumération contient les valeurs possibles de récupération de métrique système ou paramètre de configuration à récupérer. Notez que toutes les valeurs SM_CX* sont des largeurs et toutes les valeurs SM_CY* sont des hauteurs. Notez également que tous les paramètres conçus pour renvoyer des données booléennes représentent TRUE comme toute valeur différente de zéro et FALSE comme valeur zéro
     * @see #getSystemMetrics(com.jasonpercus.util.WinUser.SystemMetrics) 
     * @author JasonPercus
     * @version 1.0
     */
    public enum SystemMetrics {
        
        /**
         * Les drapeaux qui spécifient comment le système a organisé les fenêtres réduites
         */
        SM_ARRANGE(56),
        
        /**
         * La valeur qui spécifie comment le système est démarré : 0 = Démarrage normal, 1 = démarrage à sécurité intégrée, 2 = Fail-safe avec démarrage réseau. Un démarrage sans échec (également appelé SafeBoot, Safe Mode ou Clean Boot) contourne les fichiers de démarrage de l'utilisateur
         */
        SM_CLEANBOOT(67),
        
        /**
         * Le nombre de moniteurs d'affichage sur un bureau. Pour plus d'informations, consultez la section Remarques dans cette rubrique
         */
        SM_CMONITORS(80),
        
        /**
         * Le nombre de boutons sur une souris, ou zéro si aucune souris n'est installée
         */
        SM_CMOUSEBUTTONS(43),
        
        /**
         * Reflète l'état de l'ordinateur portable ou du mode ardoise, 0 pour le mode ardoise et différent de zéro sinon. Lorsque cette métrique système change, le système envoie un message de diffusion via WM_SETTINGCHANGE avec « ConvertibleSlateMode » dans le LPARAM. Notez que cette métrique système ne s'applique pas aux ordinateurs de bureau. Dans ce cas, utilisez GetAutoRotationState
         */
        SM_CONVERTIBLESLATEMODE(0x2003),
        
        /**
         * La largeur d'une bordure de fenêtre, en pixels. Ceci est équivalent à la valeur SM_CXEDGE pour les fenêtres avec le look 3-D
         */
        SM_CXBORDER(5),
        
        /**
         * La largeur d'un curseur, en pixels. Le système ne peut pas créer de curseurs d'autres tailles
         */
        SM_CXCURSOR(13),
        
        /**
         * Cette valeur est la même que SM_CXFIXEDFRAME
         */
        SM_CXDLGFRAME(7),
        
        /**
         * La largeur du rectangle autour de l'emplacement d'un premier clic dans une séquence de double-clic, en pixels. Le deuxième clic doit se produire dans le rectangle défini par SM_CXDOUBLECLK et SM_CYDOUBLECLK pour que le système considère les deux clics comme un double-clic. Les deux clics doivent également se produire dans un délai spécifié. Pour définir la largeur du rectangle de double-clic, appelez SystemParametersInfo avec SPI_SETDOUBLECLKWIDTH
         */
        SM_CXDOUBLECLK(36),
        
        /**
         * Le nombre de pixels de chaque côté d'un point de souris enfoncé que le pointeur de la souris peut déplacer avant qu'une opération de glissement ne commence. Cela permet à l'utilisateur de cliquer et de relâcher facilement le bouton de la souris sans lancer involontairement une opération de glissement. Si cette valeur est négative, elle est soustraite à gauche du point bas de la souris et ajoutée à droite de celui-ci
         */
        SM_CXDRAG(68),
        
        /**
         * La largeur d'une bordure 3D, en pixels. Cette métrique est la contrepartie 3-D de SM_CXBORDER
         */
        SM_CXEDGE(45),
        
        /**
         * L'épaisseur du cadre autour du périmètre d'une fenêtre qui a une légende mais n'est pas dimensionnable, en pixels. SM_CXFIXEDFRAME est la hauteur de la bordure horizontale et SM_CYFIXEDFRAME est la largeur de la bordure verticale. Cette valeur est la même que SM_CXDLGFRAME
         */
        SM_CXFIXEDFRAME(7),
        
        /**
         * La largeur des bords gauche et droit du rectangle de focus que DrawFocusRect dessine. Cette valeur est en pixels. Windows 2000: cette valeur n'est pas prise en charge
         */
        SM_CXFOCUSBORDER(83),
        
        /**
         * Cette valeur est la même que SM_CXSIZEFRAME
         */
        SM_CXFRAME(32),
        
        /**
         * La largeur de la zone client pour une fenêtre plein écran sur le moniteur d'affichage principal, en pixels. Pour obtenir les coordonnées de la partie de l'écran qui n'est pas masquée par la barre des tâches système ou par les barres d'outils du bureau des applications, appelez la fonction SystemParametersInfo avec la valeur SPI_GETWORKAREA
         */
        SM_CXFULLSCREEN(16),
        
        /**
         * La largeur de la bitmap de flèche sur une barre de défilement horizontale, en pixels
         */
        SM_CXHSCROLL(21),
        
        /**
         * La largeur de la zone de pouce dans une barre de défilement horizontale, en pixels
         */
        SM_CXHTHUMB(10),
        
        /**
         * La largeur par défaut d'une icône, en pixels. La fonction LoadIcon ne peut charger que des icônes avec les dimensions spécifiées par SM_CXICON et SM_CYICON
         */
        SM_CXICON(11),
        
        /**
         * La largeur d'une cellule de grille pour les éléments en mode grande icône, en pixels. Chaque élément s'insère dans un rectangle de taille SM_CXICONSPACING par SM_CYICONSPACING lorsqu'il est disposé. Cette valeur est toujours supérieure ou égale à SM_CXICON
         */
        SM_CXICONSPACING(38),
        
        /**
         * La largeur par défaut, en pixels, d'une fenêtre de niveau supérieur agrandie sur le moniteur d'affichage principal
         */
        SM_CXMAXIMIZED(61),
        
        /**
         * La largeur maximale par défaut d'une fenêtre qui a une légende et des bordures de dimensionnement, en pixels. Cette métrique fait référence à l'ensemble du bureau. L'utilisateur ne peut pas faire glisser le cadre de la fenêtre à une taille supérieure à ces dimensions. Une fenêtre peut remplacer cette valeur en traitant le message WM_GETMINMAXINFO
         */
        SM_CXMAXTRACK(59),
        
        /**
         * La largeur du bitmap de coche de menu par défaut, en pixels
         */
        SM_CXMENUCHECK(71),
        
        /**
         * La largeur des boutons de la barre de menus, tels que le bouton de fermeture de la fenêtre enfant utilisé dans l'interface multi-documents, en pixels
         */
        SM_CXMENUSIZE(54),
        
        /**
         * La largeur minimale d'une fenêtre, en pixels
         */
        SM_CXMIN(28),
        
        /**
         * La largeur d'une fenêtre réduite, en pixels
         */
        SM_CXMINIMIZED(57),
        
        /**
         * La largeur d'une cellule de grille pour une fenêtre réduite, en pixels. Chaque fenêtre réduite s'insère dans un rectangle de cette taille lorsqu'elle est disposée. Cette valeur est toujours supérieure ou égale à SM_CXMINIMIZED
         */
        SM_CXMINSPACING(47),
        
        /**
         * La largeur de suivi minimale d'une fenêtre, en pixels. L'utilisateur ne peut pas faire glisser le cadre de la fenêtre à une taille inférieure à ces dimensions. Une fenêtre peut remplacer cette valeur en traitant le message WM_GETMINMAXINFO
         */
        SM_CXMINTRACK(34),
        
        /**
         * La quantité de marge intérieure pour les fenêtres sous-titrées, en pixels. Windows XP/2000: cette valeur n'est pas prise en charge
         */
        SM_CXPADDEDBORDER(92),
        
        /**
         * La largeur de l'écran du moniteur d'affichage principal, en pixels. Il s'agit de la même valeur obtenue en appelant GetDeviceCaps comme suit : GetDeviceCaps( hdcPrimaryMonitor, HORZRES)
         */
        SM_CXSCREEN(0),
        
        /**
         * La largeur d'un bouton dans une légende de fenêtre ou une barre de titre, en pixels
         */
        SM_CXSIZE(30),
        
        /**
         * L'épaisseur de la bordure de dimensionnement autour du périmètre d'une fenêtre qui peut être redimensionnée, en pixels. SM_CXSIZEFRAME est la largeur de la bordure horizontale et SM_CYSIZEFRAME est la hauteur de la bordure verticale. Cette valeur est la même que SM_CXFRAME
         */
        SM_CXSIZEFRAME(32),
        
        /**
         * La largeur recommandée d'une petite icône, en pixels. Les petites icônes apparaissent généralement dans les légendes des fenêtres et dans l'affichage des petites icônes
         */
        SM_CXSMICON(49),
        
        /**
         * La largeur des petits boutons de légende, en pixels
         */
        SM_CXSMSIZE(52),
        
        /**
         * La largeur de l'écran virtuel, en pixels. L'écran virtuel est le rectangle englobant de tous les moniteurs d'affichage. La métrique SM_XVIRTUALSCREEN correspond aux coordonnées du côté gauche de l'écran virtuel
         */
        SM_CXVIRTUALSCREEN(78),
        
        /**
         * La largeur d'une barre de défilement verticale, en pixels
         */
        SM_CXVSCROLL(2),
        
        /**
         * La hauteur d'une bordure de fenêtre, en pixels. Ceci est équivalent à la valeur SM_CYEDGE pour les fenêtres avec le look 3-D
         */
        SM_CYBORDER(6),
        
        /**
         * La hauteur d'une zone de légende, en pixels
         */
        SM_CYCAPTION(4),
        
        /**
         * La hauteur d'un curseur, en pixels. Le système ne peut pas créer de curseurs d'autres tailles
         */
        SM_CYCURSOR(14),
        
        /**
         * Cette valeur est la même que SM_CYFIXEDFRAME
         */
        SM_CYDLGFRAME(8),
        
        /**
         * La hauteur du rectangle autour de l'emplacement d'un premier clic dans une séquence de double-clic, en pixels. Le deuxième clic doit se produire dans le rectangle défini par SM_CXDOUBLECLK et SM_CYDOUBLECLK pour que le système considère les deux clics comme un double-clic. Les deux clics doivent également se produire dans un délai spécifié. Pour définir la hauteur du rectangle de double-clic, appelez SystemParametersInfo avec SPI_SETDOUBLECLKHEIGHT
         */
        SM_CYDOUBLECLK(37),
        
        /**
         * Le nombre de pixels au-dessus et au-dessous d'un point bas de la souris que le pointeur de la souris peut déplacer avant qu'une opération de glissement ne commence. Cela permet à l'utilisateur de cliquer et de relâcher facilement le bouton de la souris sans lancer involontairement une opération de glissement. Si cette valeur est négative, elle est soustraite au-dessus du point bas de la souris et ajoutée en dessous
         */
        SM_CYDRAG(69),
        
        /**
         * La hauteur d'une bordure 3D, en pixels. C'est la contrepartie 3-D de SM_CYBORDER
         */
        SM_CYEDGE(46),
        
        /**
         * L'épaisseur du cadre autour du périmètre d'une fenêtre qui a une légende mais n'est pas dimensionnable, en pixels. SM_CXFIXEDFRAME est la hauteur de la bordure horizontale et SM_CYFIXEDFRAME est la largeur de la bordure verticale. Cette valeur est la même que SM_CYDLGFRAME
         */
        SM_CYFIXEDFRAME(8),
        
        /**
         * La hauteur des bords supérieur et inférieur du rectangle de focus dessiné par DrawFocusRect. Cette valeur est en pixels. Windows 2000: cette valeur n'est pas prise en charge
         */
        SM_CYFOCUSBORDER(84),
        
        /**
         * Cette valeur est la même que SM_CYSIZEFRAME
         */
        SM_CYFRAME(33),
        
        /**
         * Hauteur de la zone client pour une fenêtre plein écran sur le moniteur d'affichage principal, en pixels. Pour obtenir les coordonnées de la partie de l'écran non masquée par la barre des tâches système ou par les barres d'outils du bureau des applications, appelez la fonction SystemParametersInfo avec la valeur SPI_GETWORKAREA
         */
        SM_CYFULLSCREEN(17),
        
        /**
         * La hauteur d'une barre de défilement horizontale, en pixels
         */
        SM_CYHSCROLL(3),
        
        /**
         * La hauteur par défaut d'une icône, en pixels. La fonction LoadIcon ne peut charger que des icônes avec les dimensions SM_CXICON et SM_CYICON
         */
        SM_CYICON(12),
        
        /**
         * La hauteur d'une cellule de grille pour les éléments en grande icône, en pixels. Chaque élément s'insère dans un rectangle de taille SM_CXICONSPACING par SM_CYICONSPACING lorsqu'il est disposé. Cette valeur est toujours supérieure ou égale à SM_CYICON
         */
        SM_CYICONSPACING(39),
        
        /**
         * Pour les versions à jeu de caractères à double octet du système, il s'agit de la hauteur de la fenêtre Kanji en bas de l'écran, en pixels
         */
        SM_CYKANJIWINDOW(18),
        
        /**
         * La hauteur par défaut, en pixels, d'une fenêtre de niveau supérieur agrandie sur le moniteur d'affichage principal
         */
        SM_CYMAXIMIZED(62),
        
        /**
         * La hauteur maximale par défaut d'une fenêtre qui a une légende et des bordures de dimensionnement, en pixels. Cette métrique fait référence à l'ensemble du bureau. L'utilisateur ne peut pas faire glisser le cadre de la fenêtre à une taille supérieure à ces dimensions. Une fenêtre peut remplacer cette valeur en traitant le message WM_GETMINMAXINFO
         */
        SM_CYMAXTRACK(60),
        
        /**
         * La hauteur d'une barre de menus sur une seule ligne, en pixels
         */
        SM_CYMENU(15),
        
        /**
         * La hauteur du bitmap de coche de menu par défaut, en pixels
         */
        SM_CYMENUCHECK(72),
        
        /**
         * Hauteur des boutons de la barre de menus, tels que le bouton de fermeture de la fenêtre enfant utilisé dans l'interface multi-documents, en pixels
         */
        SM_CYMENUSIZE(55),
        
        /**
         * La hauteur minimale d'une fenêtre, en pixels
         */
        SM_CYMIN(29),
        
        /**
         * La hauteur d'une fenêtre réduite, en pixels
         */
        SM_CYMINIMIZED(58),
        
        /**
         * La hauteur d'une cellule de grille pour une fenêtre réduite, en pixels. Chaque fenêtre réduite s'insère dans un rectangle de cette taille lorsqu'elle est disposée. Cette valeur est toujours supérieure ou égale à SM_CYMINIMIZED
         */
        SM_CYMINSPACING(48),
        
        /**
         * La hauteur de suivi minimale d'une fenêtre, en pixels. L'utilisateur ne peut pas faire glisser le cadre de la fenêtre à une taille inférieure à ces dimensions. Une fenêtre peut remplacer cette valeur en traitant le message WM_GETMINMAXINFO
         */
        SM_CYMINTRACK(35),
        
        /**
         * La hauteur de l'écran du moniteur d'affichage principal, en pixels. Il s'agit de la même valeur obtenue en appelant GetDeviceCaps comme suit : GetDeviceCaps( hdcPrimaryMonitor, VERTRES)
         */
        SM_CYSCREEN(1),
        
        /**
         * La hauteur d'un bouton dans une légende de fenêtre ou une barre de titre, en pixels
         */
        SM_CYSIZE(31),
        
        /**
         * L'épaisseur de la bordure de dimensionnement autour du périmètre d'une fenêtre qui peut être redimensionnée, en pixels. SM_CXSIZEFRAME est la largeur de la bordure horizontale et SM_CYSIZEFRAME est la hauteur de la bordure verticale. Cette valeur est la même que SM_CYFRAME
         */
        SM_CYSIZEFRAME(33),
        
        /**
         * La hauteur d'une petite légende, en pixels
         */
        SM_CYSMCAPTION(51),
        
        /**
         * La hauteur recommandée d'une petite icône, en pixels. Les petites icônes apparaissent généralement dans les légendes des fenêtres et dans l'affichage des petites icônes
         */
        SM_CYSMICON(50),
        
        /**
         * La hauteur des petits boutons de légende, en pixels
         */
        SM_CYSMSIZE(53),
        
        /**
         * La hauteur de l'écran virtuel, en pixels. L'écran virtuel est le rectangle englobant de tous les moniteurs d'affichage. La métrique SM_YVIRTUALSCREEN correspond aux coordonnées du haut de l'écran virtuel
         */
        SM_CYVIRTUALSCREEN(79),
        
        /**
         * La hauteur de la flèche bitmap sur une barre de défilement verticale, en pixels
         */
        SM_CYVSCROLL(20),
        
        /**
         * La hauteur de la zone de pouce dans une barre de défilement verticale, en pixels
         */
        SM_CYVTHUMB(9),
        
        /**
         * Différent de zéro si User32.dll prend en charge DBCS ; sinon, 0
         */
        SM_DBCSENABLED(42),
        
        /**
         * Différent de zéro si la version de débogage de User.exe est installée ; sinon, 0
         */
        SM_DEBUG(22),
        
        /**
         * Différent de zéro si le système d'exploitation actuel est Windows 7 ou Windows Server 2008 R2 et que le service Tablet PC Input est démarré ; sinon, 0. La valeur de retour est un masque de bits qui spécifie le type d'entrée de numériseur pris en charge par le périphérique. Windows Server 2008, Windows Vista et Windows XP/2000: cette valeur n'est pas prise en charge
         */
        SM_DIGITIZER(94),
        
        /**
         * Différent de zéro si les fonctionnalités Input Method Manager/Input Method Editor sont activées ; sinon, 0. SM_IMMENABLED indique si le système est prêt à utiliser un IME basé sur Unicode sur une application Unicode. Pour vous assurer qu'un IME dépendant de la langue fonctionne, vérifiez SM_DBCSENABLED et la page de codes ANSI du système. Sinon, la conversion ANSI en Unicode risque de ne pas s'effectuer correctement ou certains composants tels que les polices ou les paramètres de registre peuvent ne pas être présent
         */
        SM_IMMENABLED(82),
        
        /**
         * Différent de zéro s'il y a des numériseurs dans le système ; sinon, 0. SM_MAXIMUMTOUCHES renvoie le maximum agrégé du nombre maximum de contacts pris en charge par chaque numériseur du système. Si le système n'a que des numériseurs à touche unique, la valeur de retour est 1. Si le système a des numériseurs à plusieurs touches, la valeur de retour est le nombre de contacts simultanés que le matériel peut fournir. Windows Server 2008, Windows Vista et Windows XP/2000: cette valeur n'est pas prise en charge
         */
        SM_MAXIMUMTOUCHES(95),
        
        /**
         * Différent de zéro si le système d'exploitation actuel est Windows XP, Media Center Edition, 0 sinon
         */
        SM_MEDIACENTER(87),
        
        /**
         * Différent de zéro si les menus déroulants sont alignés à droite avec l'élément de barre de menu correspondant ; 0 si les menus sont alignés à gauche
         */
        SM_MENUDROPALIGNMENT(40),
        
        /**
         * Différent de zéro si le système est activé pour les langues hébraïque et arabe, 0 sinon
         */
        SM_MIDEASTENABLED(74),
        
        /**
         * Différent de zéro si une souris est installée ; sinon, 0. Cette valeur est rarement nulle, en raison de la prise en charge des souris virtuelles et parce que certains systèmes détectent la présence du port au lieu de la présence d'une souris
         */
        SM_MOUSEPRESENT(19),
        
        /**
         * Différent de zéro si une souris avec une molette de défilement horizontale est installée ; sinon 0
         */
        SM_MOUSEHORIZONTALWHEELPRESENT(91),
        
        /**
         * Différent de zéro si une souris avec une molette de défilement verticale est installée ; sinon 0
         */
        SM_MOUSEWHEELPRESENT(75),
        
        /**
         * Le bit le moins significatif est défini si un réseau est présent ; sinon, il est effacé. Les autres bits sont réservés pour une utilisation future
         */
        SM_NETWORK(63),
        
        /**
         * Différent de zéro si les extensions informatiques Microsoft Windows pour Pen sont installées ; zéro sinon
         */
        SM_PENWINDOWS(41),
        
        /**
         * Cette métrique système est utilisée dans un environnement de services Terminal Server pour déterminer si la session Terminal Server en cours est contrôlée à distance. Sa valeur est non nulle si la session en cours est contrôlée à distance; sinon, 0. Vous pouvez utiliser des outils de gestion des services Terminal Server tels que Terminal Services Manager (tsadmin.msc) et shadow.exe pour contrôler une session à distance. Lorsqu'une session est contrôlée à distance, un autre utilisateur peut afficher le contenu de cette session et potentiellement interagir avec elle
         */
        SM_REMOTECONTROL(0x2001),
        
        /**
         * Cette métrique système est utilisée dans un environnement de services Terminal Server. Si le processus appelant est associé à une session cliente des services Terminal Server, la valeur de retour est différente de zéro. Si le processus appelant est associé à la session de console des services Terminal Server, la valeur de retour est 0. Windows Server 2003 et Windows XP: la session de console n'est pas nécessairement la console physique. Pour plus d'informations, consultez WTSGetActiveConsoleSessionId
         */
        SM_REMOTESESSION(0x1000),
        
        /**
         * Différent de zéro si tous les moniteurs d'affichage ont le même format de couleur, sinon 0. Deux affichages peuvent avoir la même résolution en bits, mais des formats de couleur différents. Par exemple, les pixels rouges, verts et bleus peuvent être codés avec différents nombres de bits, ou ces bits peuvent être situés à différents endroits dans une valeur de couleur de pixel
         */
        SM_SAMEDISPLAYFORMAT(81),
        
        /**
         * Le numéro de build si le système est Windows Server 2003 R2 ; sinon, 0
         */
        SM_SERVERR2(89),
        
        /**
         * Différent de zéro si l'utilisateur a besoin d'une application pour présenter des informations visuellement dans des situations où il ne présenterait autrement les informations que sous forme audible ; sinon, 0
         */
        SM_SHOWSOUNDS(70),
        
        /**
         * Différent de zéro si la session en cours se ferme ; sinon, 0. Windows 2000 :   cette valeur n'est pas prise en charge
         */
        SM_SHUTTINGDOWN(0x2000),
        
        /**
         * Différent de zéro si l'ordinateur dispose d'un processeur bas de gamme (lent) ; sinon, 0
         */
        SM_SLOWMACHINE(73),
        
        /**
         * Différent de zéro si le système d'exploitation actuel est Windows 7 Starter Edition, Windows Vista Starter ou Windows XP Starter Edition ; sinon, 0
         */
        SM_STARTER(88),
        
        /**
         * Différent de zéro si les significations des boutons gauche et droit de la souris sont inversées ; sinon, 0
         */
        SM_SWAPBUTTON(23),
        
        /**
         * Reflète l'état du mode d'amarrage, 0 pour le mode Undocked et différent de zéro sinon. Lorsque cette métrique système change, le système envoie un message de diffusion via WM_SETTINGCHANGE avec "SystemDockMode" dans le LPARAM
         */
        SM_SYSTEMDOCKED(0x2004),
        
        /**
         * Différent de zéro si le système d'exploitation actuel est l'édition Windows XP Tablet PC ou si le système d'exploitation actuel est Windows Vista ou Windows 7 et que le service Tablet PC Input est démarré ; sinon, 0. Le paramètre SM_DIGITIZER indique le type d'entrée de numériseur pris en charge par un périphérique exécutant Windows 7 ou Windows Server 2008 R2
         */
        SM_TABLETPC(86),
        
        /**
         * Les coordonnées du côté gauche de l'écran virtuel. L'écran virtuel est le rectangle englobant tous les moniteurs d'affichage. La métrique SM_CXVIRTUALSCREEN est la largeur de l'écran virtuel
         */
        SM_XVIRTUALSCREEN(76),
        
        /**
         * Les coordonnées du haut de l'écran virtuel. L'écran virtuel est le rectangle englobant tous les moniteurs d'affichage. La métrique SM_CYVIRTUALSCREEN est la hauteur de l'écran virtuel
         */
        SM_YVIRTUALSCREEN(77);
        
        
        
    //ATTRIBUT
        /**
         * Correspond au paramètre ou métrique système de configuration à récupérer
         */
        protected final int nIndex;

        
        
    //CONSTRUCTOR
        /**
         * Crée un SystemMetrics
         * @param nIndex Correspond au paramètre ou métrique système de configuration à récupérer
         */
        private SystemMetrics(int nIndex) {
            this.nIndex = nIndex;
        }
        
        
        
    }
    
    /**
     * Cette classe d'énumération contient les valeurs possibles de relations entre la fenêtre spécifiée et la fenêtre dont le handle doit être récupéré
     * @see #getWindow(com.jasonpercus.util.WinUser.Window, com.jasonpercus.util.WinUser.EGetWindow) 
     * @author JasonPercus
     * @version 1.0
     */
    public enum EGetWindow {
        
        /**
         * Le handle récupéré identifie la fenêtre enfant en haut de l'ordre Z, si la fenêtre spécifiée est une fenêtre parente ; sinon, le handle récupéré est NULL . La fonction examine uniquement les fenêtres enfants de la fenêtre spécifiée. Il n'examine pas les fenêtres descendantes
         */
        GW_CHILD(5),
        
        /**
         * Le descripteur récupéré identifie la fenêtre contextuelle activée appartenant à la fenêtre spécifiée (la recherche utilise la première fenêtre de ce type trouvée à l'aide de GW_HWNDNEXT ); sinon, s'il n'y a pas de fenêtres popup activées, le handle récupéré est celui de la fenêtre spécifiée
         */
        GW_ENABLEDPOPUP(6),
        
        /**
         * Le handle récupéré identifie la fenêtre du même type qui est la plus élevée dans l'ordre Z. Si la fenêtre spécifiée est une fenêtre la plus haute, la poignée identifie une fenêtre la plus haute. Si la fenêtre spécifiée est une fenêtre de niveau supérieur, le handle identifie une fenêtre de niveau supérieur. Si la fenêtre spécifiée est une fenêtre enfant, le handle identifie une fenêtre sœur
         */
        GW_HWNDFIRST(0),
        
        /**
         * Le descripteur récupéré identifie la fenêtre du même type qui est la plus basse dans l'ordre Z. Si la fenêtre spécifiée est une fenêtre la plus haute, la poignée identifie une fenêtre la plus haute. Si la fenêtre spécifiée est une fenêtre de niveau supérieur, le handle identifie une fenêtre de niveau supérieur. Si la fenêtre spécifiée est une fenêtre enfant, le handle identifie une fenêtre sœur
         */
        GW_HWNDLAST(1),
        
        /**
         * Le descripteur récupéré identifie la fenêtre sous la fenêtre spécifiée dans l'ordre Z. Si la fenêtre spécifiée est une fenêtre la plus haute, la poignée identifie une fenêtre la plus haute. Si la fenêtre spécifiée est une fenêtre de niveau supérieur, le handle identifie une fenêtre de niveau supérieur. Si la fenêtre spécifiée est une fenêtre enfant, le handle identifie une fenêtre sœur
         */
        GW_HWNDNEXT(2),
        
        /**
         * Le descripteur récupéré identifie la fenêtre au-dessus de la fenêtre spécifiée dans l'ordre Z. Si la fenêtre spécifiée est une fenêtre la plus haute, la poignée identifie une fenêtre la plus haute. Si la fenêtre spécifiée est une fenêtre de niveau supérieur, le handle identifie une fenêtre de niveau supérieur. Si la fenêtre spécifiée est une fenêtre enfant, le handle identifie une fenêtre sœur
         */
        GW_HWNDPREV(3),
        
        /**
         * Le descripteur récupéré identifie la fenêtre propriétaire de la fenêtre spécifiée, le cas échéant. Pour plus d'informations, consultez Fenêtres détenues
         */
        GW_OWNER(4);
        
        
        
    //ATTRIBUT
        /**
         * Correspond à la relation entre la fenêtre spécifiée et la fenêtre dont le handle doit être récupéré
         */
        protected final UINT uCmd;

        
        
    //CONSTRUCTOR
        /**
         * Crée un EGetWindow
         * @param uCmd Correspond à la relation entre la fenêtre spécifiée et la fenêtre dont le handle doit être récupéré
         */
        private EGetWindow(int uCmd) {
            this.uCmd = new UINT(uCmd);
        }
        
        
        
    }
    
    /**
     * Cette classe d'énumération contient les valeurs possibles de façon dont la fenêtre doit être affichée
     * @see #showWindow(com.jasonpercus.util.WinUser.Window, com.jasonpercus.util.WinUser.CmdShow) 
     * @see #showWindowAsync(com.jasonpercus.util.WinUser.Window, com.jasonpercus.util.WinUser.CmdShow) 
     * @author JasonPercus
     * @version 1.0
     */
    public enum CmdShow {
        
        /**
         * Masque la fenêtre et active une autre fenêtre
         */
        SW_HIDE(0),
        
        /**
         * Active et affiche une fenêtre. Si la fenêtre est réduite ou agrandie, le système la restaure à sa taille et à sa position d'origine. Une application doit spécifier ce drapeau lors de l'affichage de la fenêtre pour la première fois
         */
        SW_SHOWNORMAL(1),
        
        /**
         * Active et affiche une fenêtre. Si la fenêtre est réduite ou agrandie, le système la restaure à sa taille et à sa position d'origine. Une application doit spécifier ce drapeau lors de l'affichage de la fenêtre pour la première fois
         */
        SW_NORMAL(1),
        
        /**
         * Active la fenêtre et l'affiche sous forme de fenêtre réduite
         */
        SW_SHOWMINIMIZED(2),
        
        /**
         * Active la fenêtre et l'affiche sous forme de fenêtre agrandie
         */
        SW_SHOWMAXIMIZED(3),
        
        /**
         * Active la fenêtre et l'affiche sous forme de fenêtre agrandie
         */
        SW_MAXIMIZE(3),
        
        /**
         * Affiche une fenêtre dans sa taille et sa position les plus récentes. Cette valeur est similaire à SW_SHOWNORMAL , sauf que la fenêtre n'est pas activée
         */
        SW_SHOWNOACTIVATE(4),
        
        /**
         * Active la fenêtre et l'affiche dans sa taille et sa position actuelles
         */
        SW_SHOW(5),
        
        /**
         * Réduit la fenêtre spécifiée et active la fenêtre de niveau supérieur suivante dans l'ordre Z
         */
        SW_MINIMIZE(6),
        
        /**
         * Affiche la fenêtre sous forme de fenêtre réduite. Cette valeur est similaire à SW_SHOWMINIMIZED , sauf que la fenêtre n'est pas activée
         */
        SW_SHOWMINNOACTIVE(7),
        
        /**
         * Affiche la fenêtre dans sa taille et sa position actuelles. Cette valeur est similaire à SW_SHOW , sauf que la fenêtre n'est pas activée
         */
        SW_SHOWNA(8),
        
        /**
         * Active et affiche la fenêtre. Si la fenêtre est réduite ou agrandie, le système la restaure à sa taille et à sa position d'origine. Une application doit spécifier cet indicateur lors de la restauration d'une fenêtre réduite
         */
        SW_RESTORE(9),
        
        /**
         * Définit l'état d' affichage en fonction de la valeur SW_ spécifiée dans la structure STARTUPINFO transmise à la fonction CreateProcess par le programme qui a démarré l'application
         */
        SW_SHOWDEFAULT(10),
        
        /**
         * Réduit une fenêtre, même si le thread propriétaire de la fenêtre ne répond pas. Cet indicateur ne doit être utilisé que lors de la réduction des fenêtres d'un thread différent
         */
        SW_FORCEMINIMIZE(11);
        
        
        
    //ATTRIBUT
        /**
         * Contrôle la façon dont la fenêtre doit être affichée
         */
        protected final int nCmdShow;

        
        
    //CONSTRUCTOR
        /**
         * Crée un CmdShow
         * @param uCmd Contrôle la façon dont la fenêtre doit être affichée
         */
        private CmdShow(int nCmdShow) {
            this.nCmdShow = nCmdShow;
        }
        
        
        
    }
    
    /**
     * Cette classe d'énumération contient les valeurs possibles de paramètres à l'échelle du système à récupérer ou à définir
     * @see #systemParametersInfo(com.jasonpercus.util.WinUser.UiAction, com.sun.jna.platform.win32.WinDef.UINT, com.sun.jna.platform.win32.WinDef.PVOID, com.sun.jna.platform.win32.WinDef.UINT) 
     * @author JasonPercus
     * @version 1.0
     */
    public enum UiAction {
        
        
        
    //paramètres d'accessibilité
        /**
         * Récupère des informations sur le délai d'expiration associé aux fonctionnalités d'accessibilité. Le paramètre pvParam doit pointer vers une structure ACCESSTIMEOUT qui reçoit les informations. Définissez le membre cbSize de cette structure et le paramètre uiParam sur sizeof(ACCESSTIMEOUT)
         */
        SPI_GETACCESSTIMEOUT(0x003C),
        
        /**
         * Détermine si les descriptions audio sont activées ou désactivées. Le paramètre pvParam est un pointeur vers une structure AUDIODESCRIPTION . Définissez le membre cbSize de cette structure et le paramètre uiParam sur sizeof(AUDIODESCRIPTION). Bien qu'il soit possible pour les utilisateurs malvoyants d'entendre l'audio dans le contenu vidéo, il y a beaucoup d'action dans la vidéo qui n'a pas d'audio correspondant. Une description audio spécifique de ce qui se passe dans une vidéo aide ces utilisateurs à mieux comprendre le contenu. Ce drapeau vous permet de déterminer si les descriptions audio ont été activées et dans quelle langue
         */
        SPI_GETAUDIODESCRIPTION(0x0074),
        
        /**
         * Détermine si les animations sont activées ou désactivées. Le paramètre pvParam doit pointer vers une variable BOOL qui reçoit TRUE si les animations sont activées, ou FALSE sinon. Les fonctionnalités d'affichage telles que le clignotement, le clignotement, le scintillement et le contenu en mouvement peuvent provoquer des crises chez les utilisateurs atteints d'épilepsie photosensible. Ce drapeau vous permet de déterminer si de telles animations ont été désactivées dans l'espace client
         */
        SPI_GETCLIENTAREAANIMATION(0x1042),
        
        /**
         * Détermine si le contenu superposé est activé ou désactivé. Le paramètre pvParam doit pointer vers une variable BOOL qui reçoit TRUE si elle est activée, ou FALSE sinon. Les fonctionnalités d'affichage telles que les images d'arrière-plan, les arrière-plans texturés, les filigranes sur les documents, la fusion alpha et la transparence peuvent réduire le contraste entre le premier plan et l'arrière-plan, ce qui rend plus difficile pour les utilisateurs malvoyants de voir les objets à l'écran. Cet indicateur vous permet de déterminer si ce contenu superposé a été désactivé
         */
        SPI_GETDISABLEOVERLAPPEDCONTENT(0x1040),
        
        /**
         * Récupère des informations sur la fonctionnalité d'accessibilité FilterKeys. Le paramètre pvParam doit pointer vers une structure FILTERKEYS qui reçoit les informations. Définissez le membre cbSize de cette structure et le paramètre uiParam sur sizeof(FILTERKEYS)
         */
        SPI_GETFILTERKEYS(0x0032),
        
        /**
         * Récupère la hauteur, en pixels, des bords supérieur et inférieur du rectangle de focus dessiné avec DrawFocusRect . Le paramètre pvParam doit pointer vers une valeur UINT
         */
        SPI_GETFOCUSBORDERHEIGHT(0x2010),
        
        /**
         * Récupère la largeur, en pixels, des bords gauche et droit du rectangle de focus dessiné avec DrawFocusRect . Le paramètre pvParam doit pointer vers un UINT
         */
        SPI_GETFOCUSBORDERWIDTH(0x200E),
        
        /**
         * Récupère des informations sur la fonctionnalité d'accessibilité HighContrast. Le paramètre pvParam doit pointer vers une structure HIGHCONTRAST qui reçoit les informations. Définissez le membre cbSize de cette structure et le paramètre uiParam sur sizeof(HIGHCONTRAST)
         */
        SPI_GETHIGHCONTRAST(0x0042),
        
        /**
         * Récupère la durée d'affichage des fenêtres contextuelles de notification, en secondes. Le paramètre pvParam doit pointer vers un ULONG qui reçoit la durée du message. Les utilisateurs ayant une déficience visuelle ou des troubles cognitifs tels que le TDAH et la dyslexie peuvent avoir besoin de plus de temps pour lire le texte dans les messages de notification. Ce drapeau permet de récupérer la durée du message
         */
        SPI_GETMESSAGEDURATION(0x2016),
        
        /**
         * Récupère l'état de la fonction Mouse ClickLock. Le paramètre pvParam doit pointer vers une variable BOOL qui reçoit TRUE si elle est activée, ou FALSE sinon
         */
        SPI_GETMOUSECLICKLOCK(0x101E),
        
        /**
         * Récupère le délai avant que le bouton principal de la souris ne soit verrouillé. Le paramètre pvParam doit pointer vers DWORD qui reçoit le délai, en millisecondes. Ceci n'est activé que si SPI_SETMOUSECLICKLOCK est défini sur TRUE
         */
        SPI_GETMOUSECLICKLOCKTIME(0x2008),
        
        /**
         * Récupère des informations sur la fonctionnalité d'accessibilité MouseKeys. Le paramètre pvParam doit pointer vers une structure MOUSEKEYS qui reçoit les informations. Définissez le membre cbSize de cette structure et le paramètre uiParam sur sizeof(MOUSEKEYS)
         */
        SPI_GETMOUSEKEYS(0x0036),
        
        /**
         * Récupère l'état de la fonction Mouse Sonar. Le paramètre pvParam doit pointer vers une variable BOOL qui reçoit TRUE si elle est activée ou FALSE sinon
         */
        SPI_GETMOUSESONAR(0x101C),
        
        /**
         * Récupère l'état de la fonctionnalité Mouse Vanish. Le paramètre pvParam doit pointer vers une variable BOOL qui reçoit TRUE si elle est activée ou FALSE sinon
         */
        SPI_GETMOUSEVANISH(0x1020),
        
        /**
         * Détermine si un utilitaire de révision d'écran est en cours d'exécution. Un utilitaire de révision d'écran dirige les informations textuelles vers un périphérique de sortie, tel qu'un synthétiseur vocal ou un afficheur braille. Lorsque cet indicateur est défini, une application doit fournir des informations textuelles dans des situations où elle présenterait autrement les informations sous forme graphique. Le paramètre pvParam est un pointeur vers une variable BOOL qui reçoit TRUE si un utilitaire de révision d' écran est en cours d'exécution, ou FALSE sinon
         */
        SPI_GETSCREENREADER(0x0046),
        
        /**
         * Détermine si l'indicateur d'accessibilité Afficher les sons est activé ou désactivé. S'il est activé, l'utilisateur a besoin d'une application pour présenter des informations visuellement dans des situations où il ne présenterait autrement les informations que sous une forme audible. Le paramètre pvParam doit pointer vers une variable BOOL qui reçoit TRUE si la fonctionnalité est activée , ou FALSE si elle est désactivée. L'utilisation de cette valeur équivaut à appeler GetSystemMetrics avec SM_SHOWSOUNDS . C'est l'appel recommandé
         */
        SPI_GETSHOWSOUNDS(0x0038),
        
        /**
         * Récupère des informations sur la fonction d'accessibilité SoundSentry. Le paramètre pvParam doit pointer vers une structure SOUNDSENTRY qui reçoit les informations. Définissez le membre cbSize de cette structure et le paramètre uiParam sur sizeof(SOUNDSENTRY)
         */
        SPI_GETSOUNDSENTRY(0x0040),
        
        /**
         * Récupère des informations sur la fonction d'accessibilité StickyKeys. Le paramètre pvParam doit pointer vers une structure STICKYKEYS qui reçoit les informations. Définissez le membre cbSize de cette structure et le paramètre uiParam sur sizeof(STICKYKEYS)
         */
        SPI_GETSTICKYKEYS(0x003A),
        
        /**
         * Récupère des informations sur la fonctionnalité d'accessibilité ToggleKeys. Le paramètre pvParam doit pointer vers une structure TOGGLEKEYS qui reçoit les informations. Définissez le membre cbSize de cette structure et le paramètre uiParam sur sizeof(TOGGLEKEYS)
         */
        SPI_GETTOGGLEKEYS(0x0034),
        
        /**
         * Définit le délai d'expiration associé aux fonctionnalités d'accessibilité. Le paramètre pvParam doit pointer vers une structure ACCESSTIMEOUT qui contient les nouveaux paramètres. Définissez le membre cbSize de cette structure et le paramètre uiParam sur sizeof(ACCESSTIMEOUT)
         */
        SPI_SETACCESSTIMEOUT(0x003D),
        
        /**
         * Active ou désactive la fonction de descriptions audio. Le paramètre pvParam est un pointeur vers une structure AUDIODESCRIPTION. Bien qu'il soit possible pour les utilisateurs malvoyants d'entendre l'audio dans le contenu vidéo, il y a beaucoup d'action dans la vidéo qui n'a pas d'audio correspondant. Une description audio spécifique de ce qui se passe dans une vidéo aide ces utilisateurs à mieux comprendre le contenu. Ce drapeau vous permet d'activer ou de désactiver les descriptions audio dans les langues dans lesquelles elles sont fournies
         */
        SPI_SETAUDIODESCRIPTION(0x0075),
        
        /**
         * Active ou désactive les animations de la zone client. Le paramètre pvParam est une variable BOOL . Définissez pvParam sur TRUE pour activer les animations et autres effets transitoires dans la zone client, ou FALSE pour les désactiver. Les fonctionnalités d'affichage telles que le clignotement, le clignotement, le scintillement et le contenu en mouvement peuvent provoquer des crises chez les utilisateurs atteints d'épilepsie photosensible. Ce drapeau vous permet d'activer ou de désactiver toutes ces animations
         */
        SPI_SETCLIENTAREAANIMATION(0x1043),
        
        /**
         * Active ou désactive le contenu superposé (comme les images d'arrière-plan et les filigranes). Le paramètre pvParam est une variable BOOL . Définissez pvParam sur TRUE pour désactiver le contenu superposé, ou FALSE pour activer le contenu superposé. Les fonctionnalités d'affichage telles que les images d'arrière-plan, les arrière-plans texturés, les filigranes sur les documents, la fusion alpha et la transparence peuvent réduire le contraste entre le premier plan et l'arrière-plan, ce qui rend plus difficile pour les utilisateurs malvoyants de voir les objets à l'écran. Cet indicateur vous permet d'activer ou de désactiver tout ce contenu superposé
         */
        SPI_SETDISABLEOVERLAPPEDCONTENT(0x1041),
        
        /**
         * Définit les paramètres de la fonctionnalité d'accessibilité FilterKeys. Le paramètre pvParam doit pointer vers une structure FILTERKEYS qui contient les nouveaux paramètres. Définissez le membre cbSize de cette structure et le paramètre uiParam sur sizeof(FILTERKEYS)
         */
        SPI_SETFILTERKEYS(0x0033),
        
        /**
         * Définit la hauteur des bords supérieur et inférieur du rectangle de focus dessiné avec DrawFocusRect à la valeur du paramètre pvParam
         */
        SPI_SETFOCUSBORDERHEIGHT(0x2011),
        
        /**
         * Définit la hauteur des bords gauche et droit du rectangle de focus dessiné avec DrawFocusRect à la valeur du paramètre pvParam
         */
        SPI_SETFOCUSBORDERWIDTH(0x200F),
        
        /**
         * Définit les paramètres de la fonction d'accessibilité HighContrast. Le paramètre pvParam doit pointer vers une structure HIGHCONTRAST qui contient les nouveaux paramètres. Définissez le membre cbSize de cette structure et le paramètre uiParam sur sizeof(HIGHCONTRAST)
         */
        SPI_SETHIGHCONTRAST(0x0043),
        
        /**
         * Définit la durée d'affichage des fenêtres contextuelles de notification, en secondes. Le paramètre pvParam spécifie la durée du message. Les utilisateurs ayant une déficience visuelle ou des troubles cognitifs tels que le TDAH et la dyslexie peuvent avoir besoin de plus de temps pour lire le texte dans les messages de notification. Ce drapeau vous permet de définir la durée du message
         */
        SPI_SETMESSAGEDURATION(0x2017),
        
        /**
         * Active ou désactive la fonction d'accessibilité Mouse ClickLock. Cette fonctionnalité verrouille temporairement le bouton principal de la souris lorsque ce bouton est cliqué et maintenu enfoncé pendant la durée spécifiée par SPI_SETMOUSECLICKLOCKTIME . Le paramètre pvParam spécifie TRUE pour on ou FALSE pour off. La valeur par défaut est désactivée. Pour plus d'informations, consultez Remarques et AboutMouse Input
         */
        SPI_SETMOUSECLICKLOCK(0x101F),
        
        /**
         * Ajuste le délai avant que le bouton principal de la souris ne soit verrouillé. Le paramètre uiParam doit être défini sur 0. Le paramètre pvParam pointe vers un DWORD qui spécifie le délai en millisecondes. Par exemple, spécifiez 1000 pour un délai de 1 seconde. La valeur par défaut est 1200. Pour plus d'informations, consultez À propos de la saisie à la souris
         */
        SPI_SETMOUSECLICKLOCKTIME(0x2009),
        
        /**
         * Définit les paramètres de la fonction d'accessibilité MouseKeys. Le paramètre pvParam doit pointer vers une structure MOUSEKEYS qui contient les nouveaux paramètres. Définissez le membre cbSize de cette structure et le paramètre uiParam sur sizeof(MOUSEKEYS)
         */
        SPI_SETMOUSEKEYS(0x0037),
        
        /**
         * Active ou désactive la fonction d'accessibilité du sondeur. Cette fonctionnalité affiche brièvement plusieurs cercles concentriques autour du pointeur de la souris lorsque l'utilisateur appuie et relâche la touche CTRL. Le paramètre pvParam spécifie TRUE pour on et FALSE pour off. La valeur par défaut est désactivée
         */
        SPI_SETMOUSESONAR(0x101D),
        
        /**
         * Active ou désactive la fonction Disparition. Cette fonctionnalité masque le pointeur de la souris lorsque l'utilisateur tape ; le pointeur réapparaît lorsque l'utilisateur déplace la souris. Le paramètre pvParam spécifie TRUE pour on et FALSE pour off. La valeur par défaut est désactivée
         */
        SPI_SETMOUSEVANISH(0x1021),
        
        /**
         * Détermine si un utilitaire de révision d'écran est en cours d'exécution. Le paramètre uiParam spécifie TRUE pour on ou FALSE pour off
         */
        SPI_SETSCREENREADER(0x0047),
        
        /**
         * Active ou désactive la fonction d'accessibilité ShowSounds. Le paramètre uiParam spécifie TRUE pour on ou FALSE pour off
         */
        SPI_SETSHOWSOUNDS(0x0039),
        
        /**
         * Définit les paramètres de la fonction d' accessibilité SoundSentry . Le paramètre pvParam doit pointer vers une structure SOUNDSENTRY qui contient les nouveaux paramètres. Définissez le membre cbSize de cette structure et le paramètre uiParam sur sizeof(SOUNDSENTRY)
         */
        SPI_SETSOUNDSENTRY(0x0041),
        
        /**
         * Définit les paramètres de la fonction d'accessibilité StickyKeys. Le paramètre pvParam doit pointer vers une structure STICKYKEYS qui contient les nouveaux paramètres. Définissez le membre cbSize de cette structure et le paramètre uiParam sur sizeof(STICKYKEYS)
         */
        SPI_SETSTICKYKEYS(0x003B),
        
        /**
         * Définit les paramètres de la fonctionnalité d'accessibilité ToggleKeys. Le paramètre pvParam doit pointer vers une structure TOGGLEKEYS qui contient les nouveaux paramètres. Définissez le membre cbSize de cette structure et le paramètre uiParam sur sizeof(TOGGLEKEYS)
         */
        SPI_SETTOGGLEKEYS(0x0035),
        

        
    //paramètres du bureau
        /**
         * Détermine si ClearType est activé. Le paramètre pvParam doit pointer vers une variable BOOL qui reçoit TRUE si ClearType est activé, ou FALSE sinon. ClearType est une technologie logicielle qui améliore la lisibilité du texte sur les écrans à cristaux liquides (LCD)
         */
        SPI_GETCLEARTYPE(0x1048),
        
        /**
         * Récupère le chemin complet du fichier bitmap pour le papier peint du bureau. Le paramètre pvParam doit pointer vers une mémoire tampon pour recevoir la chaîne de chemin terminée par NULL. Définissez le paramètre uiParam sur la taille, en caractères, du tampon pvParam . La chaîne renvoyée ne dépassera pas MAX_PATH caractères. S'il n'y a pas de fond d'écran, la chaîne renvoyée est vide
         */
        SPI_GETDESKWALLPAPER(0x0073),
        
        /**
         * Détermine si l'effet d'ombre portée est activé. Le paramètre pvParam doit pointer vers une variable BOOL qui renvoie TRUE si activé ou FALSE si désactivé
         */
        SPI_GETDROPSHADOW(0x1024),
        
        /**
         * Détermine si les menus utilisateur natifs ont une apparence de menu plat. Le paramètre pvParam doit pointer vers une variable BOOL qui renvoie TRUE si l'apparence du menu plat est définie, ou FALSE sinon
         */
        SPI_GETFLATMENU(0x1022),
        
        /**
         * Détermine si la fonction de lissage des polices est activée. Cette fonctionnalité utilise l'anticrénelage des polices pour rendre les courbes de police plus lisses en peignant les pixels à différents niveaux de gris. Le paramètre pvParam doit pointer vers une variable BOOL qui reçoit TRUE si la fonctionnalité est activée, ou FALSE si elle ne l'est pas
         */
        SPI_GETFONTSMOOTHING(0x004A),
        
        /**
         * Récupère une valeur de contraste utilisée dans le lissage ClearType . Le paramètre pvParam doit pointer vers un UINT qui reçoit les informations. Les valeurs de contraste valides sont comprises entre 1000 et 2200. La valeur par défaut est 1400
         */
        SPI_GETFONTSMOOTHINGCONTRAST(0x200C),
        
        /**
         * Récupère l'orientation de lissage de la police. Le paramètre pvParam doit pointer vers un UINT qui reçoit les informations. Les valeurs possibles sont FE_FONTSMOOTHINGORIENTATIONBGR (bleu-vert-rouge) et FE_FONTSMOOTHINGORIENTATIONRGB (rouge-vert-bleu)
         */
        SPI_GETFONTSMOOTHINGORIENTATION(0x2012),
        
        /**
         * Récupère le type de lissage des polices. Le paramètre pvParam doit pointer vers un UINT qui reçoit les informations. Les valeurs possibles sont FE_FONTSMOOTHINGSTANDARD et FE_FONTSMOOTHINGCLEARTYPE
         */
        SPI_GETFONTSMOOTHINGTYPE(0x200A),
        
        /**
         * Récupère la taille de la zone de travail sur le moniteur d'affichage principal. La zone de travail est la partie de l'écran non masquée par la barre des tâches du système ou par les barres d'outils du bureau des applications. Le paramètre pvParam doit pointer vers une structure RECT qui reçoit les coordonnées de la zone de travail, exprimées en taille de pixel physique. Tout mode de virtualisation DPI de l'appelant n'a aucun effet sur cette sortie. Pour obtenir la zone de travail d'un moniteur autre que le moniteur d'affichage principal, appelez la fonction GetMonitorInfo
         */
        SPI_GETWORKAREA(0x0030),
        
        /**
         * Active ou désactive ClearType. Le paramètre pvParam est une variable BOOL . Définissez pvParam sur TRUE pour activer ClearType ou FALSE pour le désactiver. ClearType est une technologie logicielle qui améliore la lisibilité du texte sur les moniteurs LCD
         */
        SPI_SETCLEARTYPE(0x1049),
        
        /**
         * Recharge les curseurs système. Définissez le paramètre uiParam sur zéro et le paramètre pvParam sur NULL
         */
        SPI_SETCURSORS(0x0057),
        
        /**
         * Définit le modèle de bureau actuel en obligeant Windows à lire le paramètre Pattern= à partir du fichier WIN.INI
         */
        SPI_SETDESKPATTERN(0x0015),
        
        /**
         * Remarque: Lorsque l'indicateur SPI_SETDESKWALLPAPER est utilisé, SystemParametersInfo renvoie TRUE sauf en cas d'erreur (comme lorsque le fichier spécifié n'existe pas)
         */
        SPI_SETDESKWALLPAPER(0x0014),
        
        /**
         * Active ou désactive l'effet d'ombre portée. Définissez pvParam sur TRUE pour activer l'effet d'ombre portée ou FALSE pour le désactiver. Vous devez également avoir CS_DROPSHADOW dans le style de classe de fenêtre
         */
        SPI_SETDROPSHADOW(0x1025),
        
        /**
         * Active ou désactive l'apparence du menu plat pour les menus utilisateur natifs. Définissez pvParam sur TRUE pour activer l'apparence du menu plat ou FALSE pour le désactiver. Lorsqu'elle est activée, la barre de menus utilise COLOR_MENUBAR pour l'arrière-plan de la barre de menus, COLOR_MENU pour l'arrière-plan du menu contextuel, COLOR_MENUHILIGHT pour le remplissage de la sélection de menu actuelle et COLOR_HILIGHT pour le contour de la sélection de menu actuelle. Si cette option est désactivée, les menus sont dessinés en utilisant les mêmes mesures et couleurs que dans Windows 2000
         */
        SPI_SETFLATMENU(0x1023),
        
        /**
         * Active ou désactive la fonction de lissage des polices, qui utilise l'anticrénelage des polices pour rendre les courbes de police plus lisses en peignant les pixels à différents niveaux de gris. Pour activer la fonctionnalité, définissez le paramètre uiParam sur TRUE . Pour désactiver la fonctionnalité, définissez uiParam sur FALSE
         */
        SPI_SETFONTSMOOTHING(0x004B),
        
        /**
         * Définit la valeur de contraste utilisée dans le lissage ClearType . Le paramètre pvParam est la valeur de contraste. Les valeurs de contraste valides sont comprises entre 1000 et 2200. La valeur par défaut est 1400. SPI_SETFONTSMOOTHINGTYPE doit également être défini sur FE_FONTSMOOTHINGCLEARTYPE
         */
        SPI_SETFONTSMOOTHINGCONTRAST(0x200D),
        
        /**
         * Définit l'orientation du lissage de la police. Le paramètre pvParam est soit FE_FONTSMOOTHINGORIENTATIONBGR (bleu-vert-rouge) soit FE_FONTSMOOTHINGORIENTATIONRGB (rouge-vert-bleu)
         */
        SPI_SETFONTSMOOTHINGORIENTATION(0x2013),
        
        /**
         * Définit le type de lissage de la police. Le paramètre pvParam est soit FE_FONTSMOOTHINGSTANDARD , si l'anticrénelage standard est utilisé, soit FE_FONTSMOOTHINGCLEARTYPE , si ClearType est utilisé. La valeur par défaut est FE_FONTSMOOTHINGSTANDARD. SPI_SETFONTSMOOTHING doit également être défini
         */
        SPI_SETFONTSMOOTHINGTYPE(0x200B),
        
        /**
         * Définit la taille de la zone de travail. La zone de travail est la partie de l'écran non masquée par la barre des tâches du système ou par les barres d'outils du bureau des applications. Le paramètre pvParam est un pointeur vers une structure RECT qui spécifie le nouveau rectangle de la zone de travail, exprimé en coordonnées d'écran virtuel. Dans un système avec plusieurs moniteurs d'affichage, la fonction définit la zone de travail du moniteur qui contient le rectangle spécifié
         */
        SPI_SETWORKAREA(0x002F),
        
        
        
    //paramètres de l'icône
        /**
         * Récupère les métriques associées aux icônes. Le paramètre pvParam doit pointer vers une structure ICONMETRICS qui reçoit les informations. Définissez le membre cbSize de cette structure et le paramètre uiParam sur sizeof(ICONMETRICS)
         */
        SPI_GETICONMETRICS(0x002D),
        
        /**
         * Récupère les informations de police logique pour la police actuelle du titre de l'icône. Le paramètre uiParam spécifie la taille d'une structure LOGFONT et le paramètre pvParam doit pointer vers la structure LOGFONT à remplir
         */
        SPI_GETICONTITLELOGFONT(0x001F),
        
        /**
         * Détermine si l'habillage icône-titre est activé. Le paramètre pvParam doit pointer vers une variable BOOL qui reçoit TRUE si elle est activée, ou FALSE sinon
         */
        SPI_GETICONTITLEWRAP(0x0019),
        
        /**
         * Définit ou récupère la largeur, en pixels, d'une cellule d'icône. Le système utilise ce rectangle pour organiser les icônes dans une grande vue d'icônes. Pour définir cette valeur, définissez uiParam sur la nouvelle valeur et définissez pvParam sur NULL . Vous ne pouvez pas définir cette valeur sur une valeur inférieure à SM_CXICON. Pour récupérer cette valeur, pvParam doit pointer sur un entier qui reçoit la valeur actuelle
         */
        SPI_ICONHORIZONTALSSPACEMENT(0x000D),
        
        /**
         * Définit ou récupère la hauteur, en pixels, d'une cellule d'icône. Pour définir cette valeur, définissez uiParam sur la nouvelle valeur et définissez pvParam sur NULL . Vous ne pouvez pas définir cette valeur sur une valeur inférieure à SM_CYICON . Pour récupérer cette valeur, pvParam doit pointer sur un entier qui reçoit la valeur actuelle
         */
        SPI_ICONVERTICALSPACING(0x0018),
        
        /**
         * Définit les métriques associées aux icônes. Le paramètre pvParam doit pointer vers une structure ICONMETRICS qui contient les nouveaux paramètres. Définissez le membre cbSize de cette structure et le paramètre uiParam sur sizeof(ICONMETRICS)
         */
        SPI_SETICONMETRICS(0x002E),
        
        /**
         * Recharge les icônes du système. Définissez le paramètre uiParam sur zéro et le paramètre pvParam sur NULL
         */
        SPI_SETICONS(0x0058),
        
        /**
         * Définit la police utilisée pour les titres des icônes. Le paramètre uiParam spécifie la taille d'une structure LOGFONT et le paramètre pvParam doit pointer vers une structure LOGFONT
         */
        SPI_SETICONTITLELOGFONT(0x0022),
        
        /**
         * Active ou désactive l'habillage du titre de l'icône. Le paramètre uiParam spécifie TRUE pour on ou FALSE pour off
         */
        SPI_SETICONTITLEWRAP(0x001A),
        
        
        
    //paramètres d'entrée. Ils incluent des paramètres liés au clavier, à la souris, au stylet, à la langue de saisie et au bip d'avertissement
        /**
         * Détermine si le bip d'avertissement est activé. Le paramètre pvParam doit pointer vers une variable BOOL qui reçoit TRUE si le beeper est activé, ou FALSE s'il est désactivé
         */
        SPI_GETBEEP(0x0001),
        
        /**
         * Récupère un BOOL indiquant si une application peut réinitialiser le minuteur de l'économiseur d'écran en appelant la fonction SendInput pour simuler une entrée au clavier ou à la souris. Le paramètre pvParam doit pointer vers une variable BOOL qui reçoit TRUE si l'entrée simulée sera bloquée, ou FALSE sinon
         */
        SPI_GETBLOCKSENDINPUTRESETS(0x1026),
        
        /**
         * Récupère le paramètre de visualisation de contact actuel. Le paramètre pvParam doit pointer vers une variable ULONG qui reçoit le paramètre
         */
        SPI_GETCONTACTVISUALIZATION(0x2018),
        
        /**
         * Récupère l'identifiant des paramètres régionaux d'entrée pour la langue d'entrée par défaut du système. Le paramètre pvParam doit pointer vers une variable HKL qui reçoit cette valeur
         */
        SPI_GETDEFAULTINPUTLANG(0x0059),
        
        /**
         * Récupère le paramètre de visualisation des gestes actuel. Le paramètre pvParam doit pointer vers une variable ULONG qui reçoit le paramètre
         */
        SPI_GETGESTUREVISUALIZATION(0x201A),
        
        /**
         * Détermine si les touches d'accès au menu sont toujours soulignées. Le paramètre pvParam doit pointer sur une variable BOOL qui reçoit TRUE si les touches d'accès au menu sont toujours soulignées, et FALSE si elles ne sont soulignées que lorsque le menu est activé par le clavier
         */
        SPI_GETKEYBOARDCUES(0x100A),
        
        /**
         * Récupère le paramètre de délai de répétition du clavier, qui est une valeur comprise entre 0 (délai d'environ 250 ms) et 3 (délai d'environ 1 seconde). Le délai réel associé à chaque valeur peut varier en fonction du matériel. Le paramètre pvParam doit pointer vers une variable entière qui reçoit le paramètre
         */
        SPI_GETKEYBOARDDELAY(0x0016),
        
        /**
         * Détermine si l'utilisateur s'appuie sur le clavier au lieu de la souris et souhaite que les applications affichent des interfaces de clavier qui seraient autrement masquées. Le paramètre pvParam doit pointer vers une variable BOOL qui reçoit TRUE si l'utilisateur s'appuie sur le clavier ; ou FAUX sinon
         */
        SPI_GETKEYBOARDPREF(0x0044),
        
        /**
         * Récupère le paramètre de vitesse de répétition du clavier, qui est une valeur comprise entre 0 (environ 2,5 répétitions par seconde) et 31 (environ 30 répétitions par seconde). Les taux de répétition réels dépendent du matériel et peuvent varier d'une échelle linéaire jusqu'à 20 %. Le paramètre pvParam doit pointer vers une variable DWORD qui reçoit le paramètre
         */
        SPI_GETKEYBOARDSPEED(0x000A),
        
        /**
         * Récupère les deux valeurs de seuil de la souris et l'accélération de la souris. Le paramètre pvParam doit pointer vers un tableau de trois entiers qui reçoit ces valeurs. Voir mouse_event pour plus d'informations
         */
        SPI_GETMOUSE(0x0003),
        
        /**
         * Récupère la hauteur, en pixels, du rectangle dans lequel le pointeur de la souris doit rester pour que TrackMouseEvent génère un message WM_MOUSEHOVER . Le paramètre pvParam doit pointer vers une variable UINT qui reçoit la hauteur
         */
        SPI_GETMOUSEHOVERHEIGHT(0x0064),
        
        /**
         * Récupère la durée, en millisecondes, pendant laquelle le pointeur de la souris doit rester dans le rectangle de survol pour que TrackMouseEvent génère un message WM_MOUSEHOVER . Le paramètre pvParam doit pointer vers une variable UINT qui reçoit l'heure
         */
        SPI_GETMOUSEHOVERTIME(0x0066),
        
        /**
         * Récupère la largeur, en pixels, du rectangle dans lequel le pointeur de la souris doit rester pour que TrackMouseEvent génère un message WM_MOUSEHOVER . Le paramètre pvParam doit pointer vers une variable UINT qui reçoit la largeur
         */
        SPI_GETMOUSEHOVERWIDTH(0x0062),
        
        /**
         * Récupère la vitesse actuelle de la souris. La vitesse de la souris détermine la distance parcourue par le pointeur en fonction de la distance parcourue par la souris. Le paramètre pvParam doit pointer sur un entier qui reçoit une valeur comprise entre 1 (le plus lent) et 20 (le plus rapide). Une valeur de 10 est la valeur par défaut. La valeur peut être définie par un utilisateur final à l'aide de l'application du panneau de commande de la souris ou par une application à l'aide de SPI_SETMOUSESPEED
         */
        SPI_GETMOUSESPEED(0x0070),
        
        /**
         * Détermine si la fonction Mouse Trails est activée. Cette fonctionnalité améliore la visibilité des mouvements du curseur de la souris en affichant brièvement une traînée de curseurs et en les effaçant rapidement. Le paramètre pvParam doit pointer vers une variable entière qui reçoit une valeur. si la valeur est zéro ou 1, la fonction est désactivée. Si la valeur est supérieure à 1, la fonctionnalité est activée et la valeur indique le nombre de curseurs dessinés dans le tracé. Le paramètre uiParam n'est pas utilisé
         */
        SPI_GETMOUSETRAILS(0x005E),
        
        /**
         * Récupère le paramètre de routage pour l'entrée du bouton de la molette. Le paramètre de routage détermine si l'entrée du bouton de la molette est envoyée à l'application avec le focus (au premier plan) ou à l'application sous le curseur de la souris. Le paramètre pvParam doit pointer vers une variable DWORD qui reçoit l'option de routage. Si la valeur est zéro ou MOUSEWHEEL_ROUTING_FOCUS, l'entrée de la molette de la souris est transmise à l'application avec le focus. Si la valeur est 1 ou MOUSEWHEEL_ROUTING_HYBRID (par défaut), l'entrée de la molette de la souris est transmise à l'application avec le focus (applications de bureau) ou à l'application sous le curseur de la souris (applications Windows Store). Le paramètre uiParam n'est pas utilisé
         */
        SPI_GETMOUSEWHEELROUTING(0x201C),
        
        /**
         * Récupère le paramètre actuel de visualisation des gestes du stylet. Le paramètre pvParam doit pointer vers une variable ULONG qui reçoit le paramètre
         */
        SPI_GETPENVISUALIZATION(0x201E),
        
        /**
         * Détermine si la fonctionnalité snap-to-default-button est activée. Si cette option est activée, le curseur de la souris se déplace automatiquement sur le bouton par défaut, tel que OK ou Appliquer , d'une boîte de dialogue. Le paramètre pvParam doit pointer vers une variable BOOL qui reçoit TRUE si la fonctionnalité est activée , ou FALSE si elle est désactivée
         */
        SPI_GETSNAPTODEFBUTTON(0x005F),
        
        /**
         * À partir de Windows 8 : détermine si la barre de langue du système est activée ou désactivée. Le paramètre pvParam doit pointer vers une variable BOOL qui reçoit TRUE si la barre de langue est activée, ou FALSE sinon
         */
        SPI_GETSYSTEMLANGUAGEBAR(0x1050),
        
        /**
         * À partir de Windows 8 : détermine si les paramètres d'entrée actifs ont une portée Local (par thread, TRUE ) ou Global (session, FALSE ). Le paramètre pvParam doit pointer vers une variable BOOL
         */
        SPI_GETTHREADLOCALINPUTSETTINGS(0x104E),
        
        /**
         * Récupère le nombre de caractères à faire défiler lorsque la molette horizontale de la souris est déplacée. Le paramètre pvParam doit pointer vers une variable UINT qui reçoit le nombre de lignes. La valeur par défaut est 3
         */
        SPI_GETWHEELSCROLLCHARS(0x006C),
        
        /**
         * Récupère le nombre de lignes à faire défiler lorsque la molette verticale de la souris est déplacée. Le paramètre pvParam doit pointer vers une variable UINT qui reçoit le nombre de lignes. La valeur par défaut est 3
         */
        SPI_GETWHEELSCROLLLINES(0x0068),
        
        /**
         * Active ou désactive le bip d'avertissement. Le paramètre uiParam spécifie TRUE pour on ou FALSE pour off
         */
        SPI_SETBEEP(0x0002),
        
        /**
         * Détermine si une application peut réinitialiser le minuteur de l'économiseur d'écran en appelant la fonction SendInput pour simuler une entrée au clavier ou à la souris. Le paramètre uiParam spécifie TRUE si l'économiseur d'écran ne sera pas désactivé par une entrée simulée, ou FALSE si l'économiseur d'écran sera désactivé par une entrée simulée
         */
        SPI_SETBLOCKSENDINPUTRESETS(0x1027),
        
        /**
         * Définit le paramètre de visualisation de contact actuel. Le paramètre pvParam doit pointer vers une variable ULONG qui identifie le paramètre
         */
        SPI_SETCONTACTVISUALIZATION(0x2019),
        
        /**
         * Définit la langue d'entrée par défaut pour le shell du système et les applications. La langue spécifiée doit pouvoir être affichée à l'aide du jeu de caractères système actuel. Le paramètre pvParam doit pointer vers une variable HKL qui contient l'identifiant des paramètres régionaux d'entrée pour la langue par défaut
         */
        SPI_SETDEFAULTINPUTLANG(0x005A),
        
        /**
         * Définit le temps de double-clic de la souris sur la valeur du paramètre uiParam . Si la valeur uiParam est supérieure à 5 000 millisecondes, le système définit le temps de double-clic sur 5 000 millisecondes. Le temps de double-clic est le nombre maximal de millisecondes qui peut se produire entre le premier et le deuxième clic d'un double-clic. Vous pouvez également appeler la fonction SetDoubleClickTime pour définir l'heure du double-clic. Pour obtenir l'heure actuelle du double-clic, appelez la fonction GetDoubleClickTime
         */
        SPI_SETDOUBLECLICKTIME(0x0020),
        
        /**
         * Définit la hauteur du rectangle de double-clic sur la valeur du paramètre uiParam . Le rectangle de double-clic est le rectangle dans lequel doit se situer le deuxième clic d'un double-clic pour qu'il soit enregistré comme un double-clic. Pour récupérer la hauteur du rectangle de double-clic, appelez GetSystemMetrics avec l' indicateur SM_CYDOUBLECLK
         */
        SPI_SETDOUBLECLKHEIGHT(0x001E),
        
        /**
         * Définit la largeur du rectangle de double-clic sur la valeur du paramètre uiParam . Le rectangle de double-clic est le rectangle dans lequel doit se situer le deuxième clic d'un double-clic pour qu'il soit enregistré comme un double-clic. Pour récupérer la largeur du rectangle de double-clic, appelez GetSystemMetrics avec l' indicateur SM_CXDOUBLECLK
         */
        SPI_SETDOUBLECLKWIDTH(0x001D),
        
        /**
         * Définit le paramètre actuel de visualisation des gestes. Le paramètre pvParam doit pointer vers une variable ULONG qui identifie le paramètre
         */
        SPI_SETGESTUREVISUALIZATION(0x201B),
        
        /**
         * Définit le soulignement des lettres des touches d'accès au menu. Le paramètre pvParam est une variable BOOL . Définissez pvParam sur TRUE pour toujours souligner les touches d'accès au menu, ou FALSE pour souligner les touches d'accès au menu uniquement lorsque le menu est activé à partir du clavier
         */
        SPI_SETKEYBOARDCUES(0x100B),
        
        /**
         * Définit le paramètre de délai de répétition du clavier. Le paramètre uiParam doit spécifier 0, 1, 2 ou 3, où zéro définit le délai le plus court d'environ 250 ms) et 3 définit le délai le plus long (environ 1 seconde). Le délai réel associé à chaque valeur peut varier en fonction du matériel
         */
        SPI_SETKEYBOARDDELAY(0x0017),
        
        /**
         * Définit la préférence du clavier. Le paramètre uiParam spécifie TRUE si l'utilisateur s'appuie sur le clavier au lieu de la souris et souhaite que les applications affichent des interfaces clavier qui seraient autrement masquées ; uiParam est FAUX sinon
         */
        SPI_SETKEYBOARDPREF(0x0045),
        
        /**
         * Définit le paramètre de vitesse de répétition du clavier. Le paramètre uiParam doit spécifier une valeur comprise entre 0 (environ 2,5 répétitions par seconde) et 31 (environ 30 répétitions par seconde). Les taux de répétition réels dépendent du matériel et peuvent varier d'une échelle linéaire jusqu'à 20 %. Si uiParam est supérieur à 31, le paramètre est défini sur 31
         */
        SPI_SETKEYBOARDSPEED(0x000B),
        
        /**
         * Définit le jeu de touches de raccourci pour basculer entre les langues de saisie. Les paramètres uiParam et pvParam ne sont pas utilisés. La valeur définit les touches de raccourci dans les feuilles de propriétés du clavier en relisant le Registre. Le registre doit être défini avant que cet indicateur ne soit utilisé. le chemin dans le registre est HKEY_CURRENT_USER\Keyboard Layout\Toggle. Les valeurs valides sont "1" = ALT+MAJ, "2" = CTRL+MAJ et "3" = aucun
         */
        SPI_SETLANGTOGGLE(0x005B),
        
        /**
         * Définit les deux valeurs de seuil de la souris et l'accélération de la souris. Le paramètre pvParam doit pointer vers un tableau de trois entiers qui spécifie ces valeurs. Voir mouse_event pour plus d'informations
         */
        SPI_SETMOUSE(0x0004),
        
        /**
         * Échange ou restaure la signification des boutons gauche et droit de la souris. Le paramètre uiParam spécifie TRUE pour échanger les significations des boutons, ou FALSE pour restaurer leurs significations d'origine. Pour récupérer le paramètre actuel, appelez GetSystemMetrics avec l' indicateur SM_SWAPBUTTON
         */
        SPI_SETMOUSEBUTTONSWAP(0x0021),
        
        /**
         * Définit la hauteur, en pixels, du rectangle dans lequel le pointeur de la souris doit rester pour que TrackMouseEvent génère un message WM_MOUSEHOVER . Définissez le paramètre uiParam sur la nouvelle hauteur
         */
        SPI_SETMOUSEHOVERHEIGHT(0x0065),
        
        /**
         * Définit la durée, en millisecondes, pendant laquelle le pointeur de la souris doit rester dans le rectangle de survol pour que TrackMouseEvent génère un message WM_MOUSEHOVER . Ceci est utilisé uniquement si vous passez HOVER_DEFAULT dans le paramètre dwHoverTime dans l'appel à TrackMouseEvent . Définissez le paramètre uiParam sur la nouvelle heure. L'heure spécifiée doit être comprise entre USER_TIMER_MAXIMUM et USER_TIMER_MINIMUM . Si uiParam est inférieur à USER_TIMER_MINIMUM , la fonction utilisera USER_TIMER_MINIMUM . Si uiParam est supérieur à USER_TIMER_MAXIMUM , la fonction sera USER_TIMER_MAXIMUM . Windows Server 2003 et Windows XP :   Le système d'exploitation n'impose pas l'utilisation de USER_TIMER_MAXIMUM et USER_TIMER_MINIMUM jusqu'à Windows Server 2003 avec SP1 et Windows XP avec SP2
         */
        SPI_SETMOUSEHOVERTIME(0x0067),
        
        /**
         * Définit la largeur, en pixels, du rectangle dans lequel le pointeur de la souris doit rester pour que TrackMouseEvent génère un message WM_MOUSEHOVER . Définissez le paramètre uiParam sur la nouvelle largeur
         */
        SPI_SETMOUSEHOVERWIDTH(0x0063),
        
        /**
         * Définit la vitesse actuelle de la souris. Le paramètre pvParam est un entier compris entre 1 (le plus lent) et 20 (le plus rapide). Une valeur de 10 est la valeur par défaut. Cette valeur est généralement définie à l'aide de l'application du panneau de commande de la souris
         */
        SPI_SETMOUSESPEED(0x0071),
        
        /**
         * Active ou désactive la fonction Pistes de souris, qui améliore la visibilité des mouvements du curseur de la souris en affichant brièvement une piste de curseurs et en les effaçant rapidement. Pour désactiver la fonctionnalité, définissez le paramètre uiParam sur zéro ou 1. Pour activer la fonctionnalité, définissez uiParam sur une valeur supérieure à 1 pour indiquer le nombre de curseurs dessinés dans le parcours
         */
        SPI_SETMOUSETRAILS(0x005D),
        
        /**
         * Définit le paramètre de routage pour l'entrée du bouton de roue. Le paramètre de routage détermine si l'entrée du bouton de la molette est envoyée à l'application avec le focus (au premier plan) ou à l'application sous le curseur de la souris. Le paramètre pvParam doit pointer vers une variable DWORD qui reçoit l'option de routage. Si la valeur est zéro ou MOUSEWHEEL_ROUTING_FOCUS, l'entrée de la molette de la souris est transmise à l'application avec le focus. Si la valeur est 1 ou MOUSEWHEEL_ROUTING_HYBRID (par défaut), l'entrée de la molette de la souris est transmise à l'application avec le focus (applications de bureau) ou à l'application sous le curseur de la souris (applications Windows Store). Définissez le paramètre uiParam sur zéro
         */
        SPI_SETMOUSEWHEELROUTING(0x201D),
        
        /**
         * Définit le paramètre actuel de visualisation des gestes du stylet. Le paramètre pvParam doit pointer vers une variable ULONG qui identifie le paramètre
         */
        SPI_SETPENVISUALIZATION(0x201F),
        
        /**
         * Active ou désactive la fonction d'accrochage au bouton par défaut. Si cette option est activée, le curseur de la souris se déplace automatiquement sur le bouton par défaut, tel que OK ou Appliquer , d'une boîte de dialogue. Définissez le paramètre uiParam sur TRUE pour activer la fonctionnalité ou sur FALSE pour la désactiver. Les applications doivent utiliser la fonction ShowWindow lors de l'affichage d'une boîte de dialogue afin que le gestionnaire de dialogue puisse positionner le curseur de la souris
         */
        SPI_SETSNAPTODEFBUTTON(0x0060),
        
        /**
         * À partir de Windows 8 : active ou désactive la fonction de barre de langue héritée. Le paramètre pvParam est un pointeur vers une variable BOOL . Définissez pvParam sur TRUE pour activer la barre de langue héritée ou sur FALSE pour la désactiver. L'indicateur est pris en charge sur Windows 8 où la barre de langue héritée est remplacée par Input Switcher et donc désactivée par défaut. L'activation de la barre de langue héritée est fournie pour des raisons de compatibilité et n'a aucun effet sur le sélecteur d'entrée
         */
        SPI_SETSYSTEMLANGUAGEBAR(0x1051),
        
        /**
         * À partir de Windows 8 : détermine si les paramètres d'entrée actifs ont une portée Local (par thread, TRUE ) ou Global (session, FALSE ). Le paramètre pvParam doit être une variable BOOL , transtypée par PVOID
         */
        SPI_SETTHREADLOCALINPUTSETTINGS(0x104F),
        
        /**
         * Définit le nombre de caractères à faire défiler lorsque la molette horizontale de la souris est déplacée. Le nombre de caractères est défini à partir du paramètre uiParam
         */
        SPI_SETWHEELSCROLLCHARS(0x006D),
        
        /**
         * Définit le nombre de lignes à faire défiler lorsque la molette verticale de la souris est déplacée. Le nombre de lignes est défini à partir du paramètre uiParam . Le nombre de lignes est le nombre suggéré de lignes à faire défiler lorsque la molette de la souris est lancée sans utiliser les touches de modification. Si le nombre est 0, aucun défilement ne devrait se produire. Si le nombre de lignes à faire défiler est supérieur au nombre de lignes affichables, et en particulier s'il s'agit de WHEEL_PAGESCROLL ( #defined as UINT_MAX ), l'opération de défilement doit être interprétée comme un clic dans les régions page suivante ou page précédente du défilement bar
         */
        SPI_SETWHEELSCROLLLINES(0x0069),
        
        
        
    //paramètres du menu
        /**
         * Détermine si les menus contextuels sont alignés à gauche ou à droite, par rapport à l'élément de barre de menus correspondant. Le paramètre pvParam doit pointer vers une variable BOOL qui reçoit TRUE si elle est alignée à droite, ou FALSE sinon
         */
        SPI_GETMENUDROPALIGNMENT(0x001B),
        
        /**
         * Détermine si l'animation de fondu de menu est activée. Le paramètre pvParam doit pointer vers une variable BOOL qui reçoit TRUE lorsque l'animation de fondu est activée et FALSE lorsqu'elle est désactivée. Si l'animation de fondu est désactivée, les menus utilisent l'animation de diapositive. Ce drapeau est ignoré sauf si l'animation de menu est activée, ce que vous pouvez faire en utilisant le drapeau SPI_SETMENUANIMATION
         */
        SPI_GETMENUFADE(0x1012),
        
        /**
         * Récupère le temps, en millisecondes, pendant lequel le système attend avant d'afficher un menu contextuel lorsque le curseur de la souris se trouve sur un élément de sous-menu. Le paramètre pvParam doit pointer vers une variable DWORD qui reçoit l'heure du délai
         */
        SPI_GETMENUSHOWDELAY(0x006A),
        
        /**
         * Définit la valeur d'alignement des menus contextuels. Le paramètre uiParam spécifie TRUE pour un alignement à droite ou FALSE pour un alignement à gauche
         */
        SPI_SETMENUDROPALIGNMENT(0x001C),
        
        /**
         * Active ou désactive l'animation de fondu de menu. Réglez pvParam sur TRUE pour activer l'effet de fondu de menu ou FALSE pour le désactiver. Si l'animation de fondu est désactivée, les menus utilisent l'animation de diapositive. L'effet de fondu de menu n'est possible que si le système a une profondeur de couleur de plus de 256 couleurs. Cet indicateur est ignoré sauf si SPI_MENUANIMATION est également défini
         */
        SPI_SETMENUFADE(0x1013),
        
        /**
         * Définit uiParam au temps, en millisecondes, que le système attend avant d'afficher un menu contextuel lorsque le curseur de la souris est sur un élément de sous-menu
         */
        SPI_SETMENUSHOWDELAY(0x006B),
        
                
        
    //paramètres de puissance
        /**
         * Windows Server 2003 et Windows XP/2000 :   détermine si la phase de faible consommation d'écran est activée. Le paramètre pvParam doit pointer vers une variable BOOL qui reçoit TRUE si activé, ou FALSE si désactivé. Cet indicateur n'est pris en charge que pour les applications 32 bits
         */
        SPI_GETLOWPOWERACTIVE(0x0053),
        
        /**
         * Windows Server 2003 et Windows XP/2000 :   Récupère la valeur du délai d'attente pour la phase de faible consommation d'énergie de l'enregistrement d'écran. Le paramètre pvParam doit pointer vers une variable entière qui reçoit la valeur. Cet indicateur n'est pris en charge que pour les applications 32 bits
         */
        SPI_GETLOWPOWERTIMEOUT(0x004F),
        
        /**
         * Ce paramètre n'est pas pris en charge. Lorsque la phase de mise hors tension de l'économie d'écran est activée, le paramètre d' alimentation GUID_VIDEO_POWERDOWN_TIMEOUT est supérieur à zéro. Windows Server 2003 et Windows XP/2000 :   détermine si la phase de mise hors tension de l'enregistrement d'écran est activée. Le paramètre pvParam doit pointer vers une variable BOOL qui reçoit TRUE si activé, ou FALSE si désactivé. Cet indicateur n'est pris en charge que pour les applications 32 bits.
         */
        SPI_GETPOWEROFFACTIVE(0x0054),
        
        /**
         * Ce paramètre n'est pas pris en charge. À la place, vérifiez le paramètre d' alimentation GUID_VIDEO_POWERDOWN_TIMEOUT. Windows Server 2003 et Windows XP/2000 :   Récupère la valeur du délai d'attente pour la phase de mise hors tension de l'enregistrement d'écran. Le paramètre pvParam doit pointer vers une variable entière qui reçoit la valeur. Cet indicateur n'est pris en charge que pour les applications 32 bits
         */
        SPI_GETPOWEROFFTIMEOUT(0x0050),
        
        /**
         * Windows Server 2003 et Windows XP/2000 :   Active ou désactive la phase basse consommation de l'économie d'écran. Réglez uiParam sur 1 pour activer ou sur zéro pour désactiver. Le paramètre pvParam doit être NULL . Cet indicateur n'est pris en charge que pour les applications 32 bits
         */
        SPI_SETLOWPOWERACTIVE(0x0055),
        
        /**
         * Windows Server 2003 et Windows XP/2000 :   définit la valeur du délai d'attente, en secondes, pour la phase de faible consommation d'énergie de l'enregistrement d'écran. Le paramètre uiParam spécifie la nouvelle valeur. Le paramètre pvParam doit être NULL . Cet indicateur n'est pris en charge que pour les applications 32 bits
         */
        SPI_SETLOWPOWERTIMEOUT(0x0051),
        
        /**
         * Ce paramètre n'est pas pris en charge. Au lieu de cela, définissez le paramètre d' alimentation GUID_VIDEO_POWERDOWN_TIMEOUT. Windows Server 2003 et Windows XP/2000 :   Active ou désactive la phase de mise hors tension de l'enregistrement d'écran. Réglez uiParam sur 1 pour activer ou sur zéro pour désactiver. Le paramètre pvParam doit être NULL . Cet indicateur n'est pris en charge que pour les applications 32 bits
         */
        SPI_SETPOWEROFFACTIVE(0x0056),
        
        /**
         * Ce paramètre n'est pas pris en charge. Au lieu de cela, définissez le paramètre d' alimentation GUID_VIDEO_POWERDOWN_TIMEOUT sur une valeur de délai d' attente. Windows Server 2003 et Windows XP/2000 :   définit la valeur du délai d'attente, en secondes, pour la phase de mise hors tension de l'enregistrement d'écran. Le paramètre uiParam spécifie la nouvelle valeur. Le paramètre pvParam doit être NULL . Cet indicateur n'est pris en charge que pour les applications 32 bits
         */
        SPI_SETPOWEROFFTIMEOUT(0x0052),
        
        
        
    //paramètres de l'économiseur d'écran
        /**
         * Détermine si l'économie d'écran est activée. Le paramètre pvParam doit pointer vers une variable BOOL qui reçoit TRUE si la sauvegarde d'écran est activée, ou FALSE sinon. Windows 7, Windows Server 2008 R2 et Windows 2000 :   la fonction renvoie VRAI même lorsque l'enregistrement d'écran n'est pas activé
         */
        SPI_GETSCREENSAVEACTIVE(0x0010),
        
        /**
         * Détermine si un économiseur d'écran est en cours d'exécution sur la station Windows du processus appelant. Le paramètre pvParam doit pointer vers une variable BOOL qui reçoit TRUE si un économiseur d'écran est en cours d'exécution, ou FALSE sinon. Notez que seule la station Windows interactive, WinSta0, peut avoir un économiseur d'écran en cours d'exécution
         */
        SPI_GETSCREENSAVERRUNNING(0x0072),
        
        /**
         * Détermine si l'économiseur d'écran nécessite un mot de passe pour afficher le bureau Windows. Le paramètre pvParam doit pointer vers une variable BOOL qui reçoit TRUE si l'économiseur d'écran requiert un mot de passe, ou FALSE sinon. Le paramètre uiParam est ignoré
         */
        SPI_GETSCREENSAVESECURE(0x0076),
        
        /**
         * Récupère la valeur du délai d'attente de l'économiseur d'écran, en secondes. Le paramètre pvParam doit pointer vers une variable entière qui reçoit la valeur
         */
        SPI_GETSCREENSAVETIMEOUT(0x000E),
        
        /**
         * Définit l'état de l'économiseur d'écran. Le paramètre uiParam spécifie TRUE pour activer la sauvegarde d'écran ou FALSE pour la désactiver. Si la machine est entrée en mode d'économie d'énergie ou en état de verrouillage du système, une exception ERROR_OPERATION_IN_PROGRESS se produit
         */
        SPI_SETSCREENSAVEACTIVE(0x0011),
        
        /**
         * Définit si l'économiseur d'écran demande à l'utilisateur de saisir un mot de passe pour afficher le bureau Windows. Le paramètre uiParam est une variable BOOL . Le paramètre pvParam est ignoré. Définissez uiParam sur TRUE pour exiger un mot de passe, ou FALSE pour ne pas exiger de mot de passe. Si la machine est entrée en mode d'économie d'énergie ou en état de verrouillage du système, une exception ERROR_OPERATION_IN_PROGRESS se produit. Windows Server 2003 et Windows XP/2000 :   ce paramètre n'est pas pris en charge
         */
        SPI_SETSCREENSAVESECURE(0x0077),
        
        /**
         * Définit la valeur du délai d'attente de l'économiseur d'écran sur la valeur du paramètre uiParam . Cette valeur est la durée, en secondes, pendant laquelle le système doit être inactif avant l'activation de l'économiseur d'écran. Si la machine est entrée en mode d'économie d'énergie ou en état de verrouillage du système, une exception ERROR_OPERATION_IN_PROGRESS se produit
         */
        SPI_SETSCREENSAVETIMEOUT(0x000F),
        
        
        
    //paramètres de délai d'attente pour les applications et les services
        /**
         * Récupère le nombre de millisecondes qu'un thread peut passer sans envoyer de message avant que le système ne le considère comme ne répondant pas. Le paramètre pvParam doit pointer vers une variable entière qui reçoit la valeur. Windows Server 2008, Windows Vista, Windows Server 2003 et Windows XP/2000 :   ce paramètre n'est pas pris en charge
         */
        SPI_GETHUNGAPPTIMEOUT(0x0078),
        
        /**
         * Récupère le nombre de millisecondes que le système attend avant de mettre fin à une application qui ne répond pas à une demande d'arrêt. Le paramètre pvParam doit pointer vers une variable entière qui reçoit la valeur. Windows Server 2008, Windows Vista, Windows Server 2003 et Windows XP/2000 :   ce paramètre n'est pas pris en charge
         */
        SPI_GETWAITTOKILLTIMEOUT(0x007A),
        
        /**
         * Récupère le nombre de millisecondes que le gestionnaire de contrôle de service attend avant de mettre fin à un service qui ne répond pas à une demande d'arrêt. Le paramètre pvParam doit pointer vers une variable entière qui reçoit la valeur. Windows Server 2008, Windows Vista, Windows Server 2003 et Windows XP/2000 :   ce paramètre n'est pas pris en charge
         */
        SPI_GETWAITTOKILLSERVICETIMEOUT(0x007C),
        
        /**
         * Définit le délai d' expiration de l'application bloquée à la valeur du paramètre uiParam . Cette valeur est le nombre de millisecondes qu'un thread peut passer sans envoyer de message avant que le système ne le considère comme ne répondant pas. Windows Server 2008, Windows Vista, Windows Server 2003 et Windows XP/2000 :   ce paramètre n'est pas pris en charge
         */
        SPI_SETHUNGAPPTIMEOUT(0x0079),
        
        /**
         * Définit le délai d'expiration de la demande d'arrêt de l'application sur la valeur du paramètre uiParam . Cette valeur correspond au nombre de millisecondes que le système attend avant de mettre fin à une application qui ne répond pas à une demande d'arrêt. Windows Server 2008, Windows Vista, Windows Server 2003 et Windows XP/2000 :   ce paramètre n'est pas pris en charge
         */
        SPI_SETWAITTOKILLTIMEOUT(0x007B),
        
        /**
         * Définit le délai d'expiration de la demande d'arrêt du service sur la valeur du paramètre uiParam . Cette valeur correspond au nombre de millisecondes que le système attend avant de mettre fin à un service qui ne répond pas à une demande d'arrêt. Windows Server 2008, Windows Vista, Windows Server 2003 et Windows XP/2000 :   ce paramètre n'est pas pris en charge
         */
        SPI_SETWAITTOKILLSERVICETIMEOUT(0x007D),
        
        
        
    //les effets de l'interface utilisateur
        /**
         * Détermine si l'effet d'ouverture coulissante pour les zones de liste déroulante est activé. Le paramètre pvParam doit pointer vers une variable BOOL qui reçoit TRUE pour activé ou FALSE pour désactivé
         */
        SPI_GETCOMBOBOXANIMATION(0x1004),
        
        /**
         * Détermine si le curseur est entouré d'une ombre. Le paramètre pvParam doit pointer vers une variable BOOL qui reçoit TRUE si le shadow est activé, FALSE s'il est désactivé. Cet effet n'apparaît que si le système a une profondeur de couleur de plus de 256 couleurs
         */
        SPI_GETCURSORSHADOW(0x101A),
        
        /**
         * Détermine si l'effet de dégradé pour les barres de titre des fenêtres est activé. Le paramètre pvParam doit pointer vers une variable BOOL qui reçoit TRUE pour activé ou FALSE pour désactivé
         */
        SPI_GETGRADIENTCAPTIONS(0x1008),
        
        /**
         * Détermine si le suivi à chaud des éléments de l'interface utilisateur, tels que les noms de menu dans les barres de menu, est activé. Le paramètre pvParam doit pointer vers une variable BOOL qui reçoit TRUE pour activé ou FALSE pour désactivé. Le suivi à chaud signifie que lorsque le curseur se déplace sur un élément, il est mis en surbrillance mais pas sélectionné. Vous pouvez interroger cette valeur pour décider d'utiliser ou non le suivi à chaud dans l'interface utilisateur de votre application
         */
        SPI_GETHOTTRACKING(0x100E),
        
        /**
         * Détermine si l'effet de défilement régulier pour les zones de liste est activé. Le paramètre pvParam doit pointer vers une variable BOOL qui reçoit TRUE pour activé ou FALSE pour désactivé
         */
        SPI_GETLISTBOXSMOOTHSCROLLING(0x1006),
        
        /**
         * Détermine si la fonction d'animation de menu est activée. Cet interrupteur principal doit être activé pour activer les effets d'animation de menu. Le paramètre pvParam doit pointer vers une variable BOOL qui reçoit TRUE si l'animation est activée et FALSE si elle est désactivée. Si l'animation est activée, SPI_GETMENUFADE indique si les menus utilisent une animation de fondu ou de diapositive
         */
        SPI_GETMENUANIMATION(0x1002),
        
        /**
         * Identique à SPI_GETKEYBOARDCUES
         */
        SPI_GETMENUUNDERLINES(0x100A),
        
        /**
         * Détermine si l'effet de fondu de sélection est activé. Le paramètre pvParam doit pointer vers une variable BOOL qui reçoit TRUE si activé ou FALSE si désactivé. L'effet de fondu de sélection fait que l'élément de menu sélectionné par l'utilisateur reste brièvement sur l'écran tout en s'estompant après la fermeture du menu
         */
        SPI_GETSELECTIONFADE(0x1014),
        
        /**
         * Détermine si l'animation d'info-bulle est activée. Le paramètre pvParam doit pointer vers une variable BOOL qui reçoit TRUE si activé ou FALSE si désactivé. Si l'animation d'info-bulle est activée, SPI_GETTOOLTIPFADE indique si les info-bulles utilisent une animation de fondu ou de diapositive
         */
        SPI_GETTOOLTIPANIMATION(0x1016),
        
        /**
         * Si SPI_SETTOOLTIPANIMATION est activé, SPI_GETTOOLTIPFADE indique si l'animation ToolTip utilise un effet de fondu ou un effet de diapositive. Le paramètre pvParam doit pointer vers une variable BOOL qui reçoit TRUE pour l'animation de fondu ou FALSE pour l'animation de diapositive
         */
        SPI_GETTOOLTIPFADE(0x1018),
        
        /**
         * Détermine si les effets d'interface utilisateur sont activés ou désactivés. Le paramètre pvParam doit pointer vers une variable BOOL qui reçoit TRUE si tous les effets d'interface utilisateur sont activés, ou FALSE s'ils sont désactivés
         */
        SPI_GETUIEFFECTS(0x103E),
        
        /**
         * Active ou désactive l'effet d'ouverture coulissante pour les zones de liste déroulante. Définissez le paramètre pvParam sur TRUE pour activer l'effet de dégradé ou sur FALSE pour le désactiver
         */
        SPI_SETCOMBOBOXANIMATION(0x1005),
        
        /**
         * Active ou désactive une ombre autour du curseur. Le paramètre pvParam est une variable BOOL . Définissez pvParam sur TRUE pour activer l'ombre ou FALSE pour désactiver l'ombre. Cet effet n'apparaît que si le système a une profondeur de couleur de plus de 256 couleurs
         */
        SPI_SETCURSORSHADOW(0x101B),
        
        /**
         * Active ou désactive l'effet de dégradé pour les barres de titre des fenêtres. Définissez le paramètre pvParam sur TRUE pour l'activer ou sur FALSE pour le désactiver. L'effet de dégradé n'est possible que si le système a une profondeur de couleur de plus de 256 couleurs
         */
        SPI_SETGRADIENTCAPTIONS(0x1009),
        
        /**
         * Active ou désactive le suivi à chaud des éléments de l'interface utilisateur tels que les noms de menu dans les barres de menu. Définissez le paramètre pvParam sur TRUE pour l'activer ou sur FALSE pour le désactiver. Le suivi à chaud signifie que lorsque le curseur se déplace sur un élément, il est mis en surbrillance mais pas sélectionné
         */
        SPI_SETHOTTRACKING(0x100F),
        
        /**
         * Active ou désactive l'effet de défilement fluide pour les zones de liste. Définissez le paramètre pvParam sur TRUE pour activer l'effet de défilement fluide ou sur FALSE pour le désactiver
         */
        SPI_SETLISTBOXSMOOTHSCROLLING(0x1007),
        
        /**
         * Active ou désactive l'animation du menu. Cet interrupteur principal doit être activé pour qu'une animation de menu se produise. Le paramètre pvParam est une variable BOOL ; définissez pvParam sur TRUE pour activer l'animation et FALSE pour désactiver l'animation. Si l'animation est activée, SPI_GETMENUFADE indique si les menus utilisent une animation de fondu ou de diapositive
         */
        SPI_SETMENUANIMATION(0x1003),
        
        /**
         * Identique à SPI_SETKEYBOARDCUES
         */
        SPI_SETMENUUNDERLINES(0x100B),
        
        /**
         * Réglez pvParam sur TRUE pour activer l'effet de fondu de sélection ou sur FALSE pour le désactiver. L'effet de fondu de sélection fait que l'élément de menu sélectionné par l'utilisateur reste brièvement sur l'écran tout en s'estompant après la fermeture du menu. L'effet de fondu de sélection n'est possible que si le système a une profondeur de couleur de plus de 256 couleurs
         */
        SPI_SETSELECTIONFADE(0x1015),
        
        /**
         * Définissez pvParam sur TRUE pour activer l'animation ToolTip ou FALSE pour la désactiver. Si activé, vous pouvez utiliser SPI_SETTOOLTIPFADE pour spécifier une animation de fondu ou de diapositive
         */
        SPI_SETTOOLTIPANIMATION(0x1017),
        
        /**
         * Si l' indicateur SPI_SETTOOLTIPANIMATION est activé, utilisez SPI_SETTOOLTIPFADE pour indiquer si l'animation ToolTip utilise un effet de fondu ou un effet de diapositive. Définissez pvParam sur TRUE pour une animation de fondu ou FALSE pour une animation de diapositive. L'effet de fondu de l'info-bulle n'est possible que si le système a une profondeur de couleur de plus de 256 couleurs
         */
        SPI_SETTOOLTIPFADE(0x1019),
        
        /**
         * Active ou désactive les effets de l'interface utilisateur. Définissez le paramètre pvParam sur TRUE pour activer tous les effets d'interface utilisateur ou FALSE pour désactiver tous les effets d'interface utilisateur
         */
        SPI_SETUIEFFECTS(0x103F),
                
                
                
    //paramètres de la fenêtre
        /**
         * Détermine si le suivi de fenêtre active (activation de la fenêtre sur laquelle la souris est activée) est activé ou désactivé. Le paramètre pvParam doit pointer vers une variable BOOL qui reçoit TRUE pour on ou FALSE pour off
         */
        SPI_GETACTIVEWINDOWTRACKING(0x1000),
        
        /**
         * Détermine si les fenêtres activées via le suivi de fenêtre actif seront placées en haut. Le paramètre pvParam doit pointer vers une variable BOOL qui reçoit TRUE pour on ou FALSE pour off
         */
        SPI_GETACTIVEWNDTRKZORDER(0x100C),
        
        /**
         * Récupère le délai de suivi de la fenêtre active, en millisecondes. Le paramètre pvParam doit pointer vers une variable DWORD qui reçoit l'heure
         */
        SPI_GETACTIVEWNDTRKTIMEOUT(0x2002),
        
        /**
         * Récupère les effets d'animation associés aux actions de l'utilisateur. Le paramètre pvParam doit pointer vers une structure ANIMATIONINFO qui reçoit les informations. Définissez le membre cbSize de cette structure et le paramètre uiParam sur sizeof(ANIMATIONINFO)
         */
        SPI_GETANIMATION(0x0048),
        
        /**
         * Récupère le facteur multiplicateur de bordure qui détermine la largeur de la bordure de dimensionnement d'une fenêtre. Le paramètre pvParam doit pointer vers une variable entière qui reçoit cette valeur
         */
        SPI_GETBORDER(0x0005),
        
        /**
         * Récupère la largeur du curseur dans les champs d'édition, en pixels. Le paramètre pvParam doit pointer vers une variable DWORD qui reçoit cette valeur
         */
        SPI_GETCARETWIDTH(0x2006),
        
        /**
         * Détermine si une fenêtre est ancrée lorsqu'elle est déplacée vers les bords supérieur, gauche ou droit d'un moniteur ou d'une matrice de moniteurs. Le paramètre pvParam doit pointer vers une variable BOOL qui reçoit TRUE si elle est activée, ou FALSE sinon. Utilisez SPI_GETWINARRANGEING pour déterminer si ce comportement est activé. Windows Server 2008, Windows Vista, Windows Server 2003 et Windows XP/2000 :   ce paramètre n'est pas pris en charge
         */
        SPI_GETDOCKMOVING(0x0090),
        
        /**
         * Détermine si une fenêtre agrandie est restaurée lorsque sa barre de légende est déplacée. Le paramètre pvParam doit pointer vers une variable BOOL qui reçoit TRUE si elle est activée, ou FALSE sinon. Utilisez SPI_GETWINARRANGEING pour déterminer si ce comportement est activé. Windows Server 2008, Windows Vista, Windows Server 2003 et Windows XP/2000 :   ce paramètre n'est pas pris en charge
         */
        SPI_GETDRAGFROMMAXIMIZE(0x008C),
        
        /**
         * Détermine si le déplacement de fenêtres complètes est activé. Le paramètre pvParam doit pointer vers une variable BOOL qui reçoit TRUE si elle est activée, ou FALSE sinon
         */
        SPI_GETDRAGFULLWINDOWS(0x0026),
        
        /**
         * Récupère le nombre de fois où SetForegroundWindow fait clignoter le bouton de la barre des tâches lors du rejet d'une demande de changement de premier plan. Le paramètre pvParam doit pointer vers une variable DWORD qui reçoit la valeur
         */
        SPI_GETFOREGROUNDFLASHCOUNT(0x2004),
        
        /**
         * Récupère la durée suivant l'entrée de l'utilisateur, en millisecondes, pendant laquelle le système ne permettra pas aux applications de se forcer au premier plan. Le paramètre pvParam doit pointer vers une variable DWORD qui reçoit l'heure
         */
        SPI_GETFOREGROUNDLOCKTIMEOUT(0x2000),
        
        /**
         * Récupère les métriques associées aux fenêtres réduites. Le paramètre pvParam doit pointer vers une structure MINIMIZEDMETRICS qui reçoit les informations. Définissez le membre cbSize de cette structure et le paramètre uiParam sur sizeof(MINIMIZEDMETRICS)
         */
        SPI_GETMINIMIZEDMETRICS(0x002B),
        
        /**
         * Récupère le seuil en pixels où le comportement d'ancrage est déclenché en utilisant une souris pour faire glisser une fenêtre vers le bord d'un moniteur ou d'une matrice de moniteurs. Le seuil par défaut est 1. Le paramètre pvParam doit pointer vers une variable DWORD qui reçoit la valeur. Utilisez SPI_GETWINARRANGEING pour déterminer si ce comportement est activé. Windows Server 2008, Windows Vista, Windows Server 2003 et Windows XP/2000 :   ce paramètre n'est pas pris en charge
         */
        SPI_GETMOUSEDOCKTHRESHOLD(0x007E),
        
        /**
         * Récupère le seuil en pixels où le comportement de désancrage est déclenché en utilisant une souris pour faire glisser une fenêtre du bord d'un moniteur ou d'un réseau de moniteurs vers le centre. Le seuil par défaut est 20. Utilisez SPI_GETWINARRANGEING pour déterminer si ce comportement est activé. Windows Server 2008, Windows Vista, Windows Server 2003 et Windows XP/2000 :   ce paramètre n'est pas pris en charge
         */
        SPI_GETMOUSEDRAGOUTTHRESHOLD(0x0084),
        
        /**
         * Récupère le seuil en pixels du haut d'un moniteur ou d'un réseau de moniteurs où une fenêtre agrandie verticalement est restaurée lorsqu'elle est glissée avec la souris. Le seuil par défaut est 50. Utilisez SPI_GETWINARRANGEING pour déterminer si ce comportement est activé. Windows Server 2008, Windows Vista, Windows Server 2003 et Windows XP/2000 :   ce paramètre n'est pas pris en charge
         */
        SPI_GETMOUSESIDEMOVETHRESHOLD(0x0088),
        
        /**
         * Récupère les métriques associées à la zone non cliente des fenêtres non réduites. Le paramètre pvParam doit pointer vers une structure NONCLIENTMETRICS qui reçoit les informations. Définissez le membre cbSize de cette structure et le paramètre uiParam sur sizeof(NONCLIENTMETRICS)
         */
        SPI_GETNONCLIENTMETRICS(0x0029),
        
        /**
         * Récupère le seuil en pixels où le comportement d'ancrage est déclenché en utilisant un stylet pour faire glisser une fenêtre vers le bord d'un moniteur ou d'une matrice de moniteurs. La valeur par défaut est 30. Utilisez SPI_GETWINARRANGEING pour déterminer si ce comportement est activé. Windows Server 2008, Windows Vista, Windows Server 2003 et Windows XP/2000 :   ce paramètre n'est pas pris en charge
         */
        SPI_GETPENDOCKTHRESHOLD(0x0080),
        
        /**
         * Récupère le seuil en pixels où le comportement de désancrage est déclenché en utilisant un stylet pour faire glisser une fenêtre du bord d'un moniteur ou d'une matrice de moniteurs vers son centre. Le seuil par défaut est 30. Utilisez SPI_GETWINARRANGEING pour déterminer si ce comportement est activé. Windows Server 2008, Windows Vista, Windows Server 2003 et Windows XP/2000 :   ce paramètre n'est pas pris en charge
         */
        SPI_GETPENDRAGOUTTHRESHOLD(0x0086),
        
        /**
         * Récupère le seuil en pixels du haut d'un moniteur ou d'une matrice de moniteurs où une fenêtre agrandie verticalement est restaurée lorsqu'elle est glissée avec la souris. Le seuil par défaut est 50. Utilisez SPI_GETWINARRANGEING pour déterminer si ce comportement est activé. Windows Server 2008, Windows Vista, Windows Server 2003 et Windows XP/2000 :   ce paramètre n'est pas pris en charge
         */
        SPI_GETPENSIDEMOVETHRESHOLD(0x008A),
        
        /**
         * Détermine si la fenêtre d'état IME est visible (par utilisateur). Le paramètre pvParam doit pointer vers une variable BOOL qui reçoit TRUE si la fenêtre d'état est visible, ou FALSE si ce n'est pas le cas
         */
        SPI_GETSHOWIMEUI(0x006E),
        
        /**
         * Détermine si une fenêtre est agrandie verticalement lorsqu'elle est dimensionnée en haut ou en bas d'un moniteur ou d'une matrice de moniteurs. Le paramètre pvParam doit pointer vers une variable BOOL qui reçoit TRUE si elle est activée, ou FALSE sinon. Utilisez SPI_GETWINARRANGEING pour déterminer si ce comportement est activé. Windows Server 2008, Windows Vista, Windows Server 2003 et Windows XP/2000 :   ce paramètre n'est pas pris en charge
         */
        SPI_GETSNAPSIZING(0x008E),
        
        /**
         * Détermine si la disposition des fenêtres est activée. Le paramètre pvParam doit pointer vers une variable BOOL qui reçoit TRUE si elle est activée, ou FALSE sinon. La disposition des fenêtres réduit le nombre d'interactions avec la souris, le stylet ou le toucher nécessaires pour déplacer et dimensionner les fenêtres de niveau supérieur en simplifiant le comportement par défaut d'une fenêtre lorsqu'elle est déplacée ou dimensionnée.
         * Les paramètres suivants récupèrent les paramètres de disposition des fenêtres individuelles :
         * SPI_GETDOCKMOVING
         * SPI_GETMOUSEDOCKTHRESHOLD
         * SPI_GETMOUSEDRAGOUTTHRESHOLD
         * SPI_GETMOUSESIDEMOVETHRESHOLD
         * SPI_GETPENDOCKTHRESHOLD
         * SPI_GETPENDRAGOUTTHRESHOLD
         * SPI_GETPENSIDEMOVETHRESHOLD
         * SPI_GETSNAPSIZING
         * Windows Server 2008, Windows Vista, Windows Server 2003 et Windows XP/2000 :   ce paramètre n'est pas pris en charge
         */
        SPI_GETWINARRANGING(0x0082),
        
        /**
         * Active ou désactive le suivi de fenêtre actif (en activant la fenêtre sur laquelle la souris est activée). Définissez pvParam sur TRUE pour on ou FALSE pour off
         */
        SPI_SETACTIVEWINDOWTRACKING(0x1001),
        
        /**
         * Détermine si les fenêtres activées via le suivi actif des fenêtres doivent être placées en haut. Définissez pvParam sur TRUE pour on ou FALSE pour off
         */
        SPI_SETACTIVEWNDTRKZORDER(0x100D),
        
        /**
         * Définit le délai de suivi de la fenêtre active. Définissez pvParam sur le nombre de millisecondes à attendre avant d'activer la fenêtre sous le pointeur de la souris
         */
        SPI_SETACTIVEWNDTRKTIMEOUT(0x2003),
        
        /**
         * Définit les effets d'animation associés aux actions de l'utilisateur. Le paramètre pvParam doit pointer vers une structure ANIMATIONINFO qui contient les nouveaux paramètres. Définissez le membre cbSize de cette structure et le paramètre uiParam sur sizeof(ANIMATIONINFO)
         */
        SPI_SETANIMATION(0x0049),
        
        /**
         * Définit le facteur multiplicateur de bordure qui détermine la largeur de la bordure de dimensionnement d'une fenêtre. Le paramètre uiParam spécifie la nouvelle valeur
         */
        SPI_SETBORDER(0x0006),
        
        /**
         * Définit la largeur du curseur dans les contrôles d'édition. Définissez pvParam sur la largeur souhaitée, en pixels. La valeur par défaut et minimale est 1
         */
        SPI_SETCARETWIDTH(0x2007),
        
        /**
         * Définit si une fenêtre est ancrée lorsqu'elle est déplacée vers les cibles d'ancrage supérieure, gauche ou droite sur un moniteur ou une matrice de moniteurs. Définissez pvParam sur TRUE pour on ou FALSE pour off. SPI_GETWINARRANGEING doit être TRUE pour activer ce comportement. Windows Server 2008, Windows Vista, Windows Server 2003 et Windows XP/2000 :   ce paramètre n'est pas pris en charge
         */
        SPI_SETDOCKMOVING(0x0091),
        
        /**
         * Définit si une fenêtre agrandie est restaurée lorsque sa barre de légende est déplacée. Définissez pvParam sur TRUE pour on ou FALSE pour off. SPI_GETWINARRANGEING doit être TRUE pour activer ce comportement. Windows Server 2008, Windows Vista, Windows Server 2003 et Windows XP/2000 :   ce paramètre n'est pas pris en charge
         */
        SPI_SETDRAGFROMMAXIMIZE(0x008D),
        
        /**
         * Active ou désactive le glissement de fenêtres complètes. Le paramètre uiParam spécifie TRUE pour on ou FALSE pour off
         */
        SPI_SETDRAGFULLWINDOWS(0x0025),
        
        /**
         * Définit la hauteur, en pixels, du rectangle utilisé pour détecter le début d'une opération de glissement. Définissez uiParam sur la nouvelle valeur. Pour récupérer la hauteur de traînée, appelez GetSystemMetrics avec l' indicateur SM_CYDRAG
         */
        SPI_SETDRAGHEIGHT(0x004D),
        
        /**
         * Définit la largeur, en pixels, du rectangle utilisé pour détecter le début d'une opération de glissement. Définissez uiParam sur la nouvelle valeur. Pour récupérer la largeur de glissement, appelez GetSystemMetrics avec l' indicateur SM_CXDRAG
         */
        SPI_SETDRAGWIDTH(0x004C),
        
        /**
         * Définit le nombre de fois où SetForegroundWindow fera clignoter le bouton de la barre des tâches lors du rejet d'une demande de changement de premier plan. Définissez pvParam sur le nombre de clignotements
         */
        SPI_SETFOREGROUNDFLASHCOUNT(0x2005),
        
        /**
         * Définit la durée suivant l'entrée de l'utilisateur, en millisecondes, pendant laquelle le système ne permet pas aux applications de se forcer au premier plan. Définissez pvParam sur la nouvelle valeur de délai d' attente . Le thread appelant doit pouvoir changer la fenêtre de premier plan, sinon l'appel échoue
         */
        SPI_SETFOREGROUNDLOCKTIMEOUT(0x2001),
        
        /**
         * Définit les métriques associées aux fenêtres réduites. Le paramètre pvParam doit pointer vers une structure MINIMIZEDMETRICS qui contient les nouveaux paramètres. Définissez le membre cbSize de cette structure et le paramètre uiParam sur sizeof(MINIMIZEDMETRICS)
         */
        SPI_SETMINIMIZEDMETRICS(0x002C),
        
        /**
         * Définit le seuil en pixels auquel le comportement d'ancrage est déclenché à l'aide d'une souris pour faire glisser une fenêtre vers le bord d'un moniteur ou d'une matrice de moniteurs. Le seuil par défaut est 1. Le paramètre pvParam doit pointer vers une variable DWORD qui contient la nouvelle valeur. SPI_GETWINARRANGEING doit être TRUE pour activer ce comportement. Windows Server 2008, Windows Vista, Windows Server 2003 et Windows XP/2000 :   ce paramètre n'est pas pris en charge
         */
        SPI_SETMOUSEDOCKTHRESHOLD(0x007F),
        
        /**
         * Définit le seuil en pixels auquel le comportement de désamarrage est déclenché en utilisant une souris pour faire glisser une fenêtre du bord d'un moniteur ou d'une matrice de moniteurs vers son centre. Le seuil par défaut est 20. Le paramètre pvParam doit pointer vers une variable DWORD qui contient la nouvelle valeur. SPI_GETWINARRANGEING doit être TRUE pour activer ce comportement. Windows Server 2008, Windows Vista, Windows Server 2003 et Windows XP/2000 :   ce paramètre n'est pas pris en charge
         */
        SPI_SETMOUSEDRAGOUTTHRESHOLD(0x0085),
        
        /**
         * Définit le seuil en pixels à partir du haut du moniteur où une fenêtre agrandie verticalement est restaurée lorsqu'elle est glissée avec la souris. Le seuil par défaut est 50. Le paramètre pvParam doit pointer vers une variable DWORD qui contient la nouvelle valeur. SPI_GETWINARRANGEING doit être TRUE pour activer ce comportement. Windows Server 2008, Windows Vista, Windows Server 2003 et Windows XP/2000 :   ce paramètre n'est pas pris en charge
         */
        SPI_SETMOUSESIDEMOVETHRESHOLD(0x0089),
        
        /**
         * Définit les métriques associées à la zone non cliente des fenêtres non réduites. Le paramètre pvParam doit pointer vers une structure NONCLIENTMETRICS qui contient les nouveaux paramètres. Définissez le membre cbSize de cette structure et le paramètre uiParam sur sizeof(NONCLIENTMETRICS). En outre, le membre lfHeight de la structure LOGFONT doit être une valeur négative
         */
        SPI_SETNONCLIENTMETRICS(0x002A),
        
        /**
         * Définit le seuil en pixels auquel le comportement d'ancrage est déclenché en utilisant un stylet pour faire glisser une fenêtre vers le bord d'un moniteur ou d'une matrice de moniteurs. Le seuil par défaut est 30. Le paramètre pvParam doit pointer vers une variable DWORD qui contient la nouvelle valeur. SPI_GETWINARRANGEING doit être TRUE pour activer ce comportement. Windows Server 2008, Windows Vista, Windows Server 2003 et Windows XP/2000 :   ce paramètre n'est pas pris en charge
         */
        SPI_SETPENDOCKTHRESHOLD(0x0081),
        
        /**
         * Définit le seuil en pixels auquel le comportement de désancrage est déclenché en utilisant un stylet pour faire glisser une fenêtre du bord d'un moniteur ou d'une matrice de moniteurs vers son centre. Le seuil par défaut est 30. Le paramètre pvParam doit pointer vers une variable DWORD qui contient la nouvelle valeur. SPI_GETWINARRANGEING doit être TRUE pour activer ce comportement. Windows Server 2008, Windows Vista, Windows Server 2003 et Windows XP/2000 :   ce paramètre n'est pas pris en charge
         */
        SPI_SETPENDRAGOUTTHRESHOLD(0x0087),
        
        /**
         * Définit le seuil en pixels à partir du haut du moniteur où une fenêtre agrandie verticalement est restaurée lorsqu'elle est glissée avec un stylet. Le seuil par défaut est 50. Le paramètre pvParam doit pointer vers une variable DWORD qui contient la nouvelle valeur. SPI_GETWINARRANGEING doit être TRUE pour activer ce comportement. Windows Server 2008, Windows Vista, Windows Server 2003 et Windows XP/2000 :   ce paramètre n'est pas pris en charge
         */
        SPI_SETPENSIDEMOVETHRESHOLD(0x008B),
        
        /**
         * Définit si la fenêtre d'état IME est visible ou non par utilisateur. Le paramètre uiParam spécifie TRUE pour on ou FALSE pour off
         */
        SPI_SETSHOWIMEUI(0x006F),
        
        /**
         * Définit si une fenêtre est agrandie verticalement lorsqu'elle est dimensionnée en haut ou en bas du moniteur. Définissez pvParam sur TRUE pour on ou FALSE pour off. SPI_GETWINARRANGEING doit être TRUE pour activer ce comportement. Windows Server 2008, Windows Vista, Windows Server 2003 et Windows XP/2000 :   ce paramètre n'est pas pris en charge
         */
        SPI_SETSNAPSIZING(0x008F),
        
        /**
         * Définit si la disposition des fenêtres est activée. Définissez pvParam sur TRUE pour on ou FALSE pour off. La disposition des fenêtres réduit le nombre d'interactions avec la souris, le stylet ou le toucher nécessaires pour déplacer et dimensionner les fenêtres de niveau supérieur en simplifiant le comportement par défaut d'une fenêtre lorsqu'elle est déplacée ou dimensionnée. Les paramètres suivants définissent les paramètres de disposition des fenêtres individuelles :
         * SPI_SETDOCKMOVING
         * SPI_SETMOUSEDOCKTHRESHOLD
         * SPI_SETMOUSEDRAGOUTTHRESHOLD
         * SPI_SETMOUSESIDEMOVETHRESHOLD
         * SPI_SETPENDOCKTHRESHOLD
         * SPI_SETPENDRAGOUTTHRESHOLD
         * SPI_SETPENSIDEMOVETHRESHOLD
         * SPI_SETSNAPSIZING
         * Windows Server 2008, Windows Vista, Windows Server 2003 et Windows XP/2000 :   ce paramètre n'est pas pris en charge
         */
        SPI_SETWINARRANGING(0x0083);
        
        
        
    //ATTRIBUT
        /**
         * Correspond au paramètre à l'échelle du système à récupérer ou à définir
         */
        protected final UINT uiAction;

        
        
    //CONSTRUCTOR
        /**
         * Crée un UiAction
         * @param uiAction Correspond au paramètre à l'échelle du système à récupérer ou à définir
         */
        private UiAction(int uiAction) {
            this.uiAction = new UINT(uiAction);
        }
        
        
        
    }
    
    
    
//INTERFACE
    /**
     * Cette interface JNA représente la class user32 de windows
     */
    private interface User32 extends StdCallLibrary {
        
        
        
        /**
         * Correspond au lien direct entre cette interface et la classe user32 de windows
         */
        User32 INSTANCE = (User32) Native.loadLibrary("user32", User32.class);
        
        
        
        /**
         * Place la fenêtre spécifiée en haut de l'ordre Z. Si la fenêtre est une fenêtre de niveau supérieur, elle est activée. Si la fenêtre est une fenêtre enfant, la fenêtre parent de niveau supérieur associée à la fenêtre enfant est activée
         * @param hWnd Correspond au pointeur de la fenêtre concernée
         * @return Retourne true, si la fonction réussit, sinon false
         */
        boolean BringWindowToTop(HWND hWnd);
        
        /**
         * Réduit (mais ne détruit pas) la fenêtre spécifiée
         * @param hWnd Correspond au pointeur de la fenêtre concernée
         * @return Retourne true, si la fonction réussit, sinon false
         */
        boolean CloseWindow(HWND hWnd);
        
        /**
         * Détruit la fenêtre spécifiée. La fonction envoie des messages WM_DESTROY et WM_NCDESTROY à la fenêtre pour la désactiver et en retirer le focus clavier. La fonction détruit également le menu de la fenêtre, vide la file d'attente des messages du thread, détruit les minuteurs, supprime la propriété du presse-papiers et interrompt la chaîne de visualisation du presse-papiers (si la fenêtre est au sommet de la chaîne de visualisation). Si la fenêtre spécifiée est une fenêtre parent ou propriétaire, DestroyWindow détruit automatiquement la fenêtre enfant ou propriétaire associée lorsqu'il détruit la fenêtre parent ou propriétaire. La fonction détruit d'abord les fenêtres enfant ou propriétaire, puis elle détruit la fenêtre parent ou propriétaire. DestroyWindow détruit également les boîtes de dialogue non modales créées par la fonction CreateDialog
         * @param hWnd Correspond au pointeur de la fenêtre concernée
         * @return Retourne true, si la fonction réussit, sinon false
         */
        boolean DestroyWindow(HWND hWnd);
        
        /**
         * Récupère le bureau
         * @return Retourne le bureau
         */
        HWND GetDesktopWindow();
        
        /**
         * Récupère un handle vers la fenêtre de premier plan (la fenêtre avec laquelle l'utilisateur travaille actuellement). Le système attribue une priorité légèrement plus élevée au thread qui crée la fenêtre de premier plan qu'aux autres threads.
         * @return Retourne un handle vers la fenêtre de premier plan (la fenêtre avec laquelle l'utilisateur travaille actuellement). Le système attribue une priorité légèrement plus élevée au thread qui crée la fenêtre de premier plan qu'aux autres threads.
         */
        HWND GetForegroundWindow();
        
        /**
         * Récupère un handle vers la fenêtre du bureau du Shell
         * @return La valeur de retour est le handle de la fenêtre de bureau du Shell. Si aucun processus Shell n'est présent, la valeur de retour est NULL
         */
        HWND GetShellWindow();
        
        /**
         * Récupère un handle vers le parent ou le propriétaire de la fenêtre spécifiée. Pour récupérer un handle vers un ancêtre spécifié, utilisez la fonction GetAncestor .
         * @param hWnd Un handle vers la fenêtre dont le handle de fenêtre parent doit être récupéré.
         * @return Retourne un handle vers le parent ou le propriétaire de la fenêtre spécifiée
         */
        HWND GetParent(HWND hWnd);
        
        /**
         * Récupère la couleur actuelle de l'élément d'affichage spécifié. Les éléments d'affichage sont les parties d'une fenêtre et l'affichage qui apparaissent sur l'écran d'affichage du système
         * @param nIndex Correspond à l'élément d'affichage dont la couleur doit être récupérée
         * @return Retourne la couleur actuelle de l'élément d'affichage spécifié
         */
        WinDef.DWORD GetSysColor(int nIndex);
        
        /**
         * Récupère la métrique système ou le paramètre de configuration système spécifié. Notez que toutes les dimensions récupérées par GetSystemMetrics sont en pixels
         * @param nIndex La métrique système ou le paramètre de configuration à récupérer. Ce paramètre peut être l'une des valeurs suivantes. Notez que toutes les valeurs SM_CX* sont des largeurs et toutes les valeurs SM_CY* sont des hauteurs. Notez également que tous les paramètres conçus pour renvoyer des données booléennes représentent TRUE comme toute valeur différente de zéro et FALSE comme valeur zéro
         * @return Retourne la métrique système ou le paramètre de configuration système spécifié
         */
        int GetSystemMetrics(int nIndex);
        
        /**
         * Énumère toutes les fenêtres de niveau supérieur à l'écran en passant le handle de chaque fenêtre, à son tour, à une fonction de rappel définie par l'application. EnumWindows continue jusqu'à ce que la dernière fenêtre de niveau supérieur soit énumérée ou que la fonction de rappel renvoie false.
         * @param lpEnumFunc Un pointeur vers une fonction de rappel définie par l'application. Pour plus d'informations, consultez EnumWindowsProc (https://docs.microsoft.com/en-us/previous-versions/windows/desktop/legacy/ms633498(v=vs.85))
         * @param arg Une valeur définie par l'application à transmettre à la fonction de rappel
         * @return Retourne true si la fonction réussit, sinon false
         */
        boolean EnumWindows(com.sun.jna.platform.win32.WinUser.WNDENUMPROC lpEnumFunc, Pointer arg);
        
        /**
         * Récupère un pointeur vers une fenêtre Windows
         * @param hWnd Correspond au pointeur récupéré
         * @param lpString Correspond au tableau de bytes (voir nMaxCount pour la taille de ce tableau) qui servira de stockage du nom de la fenêtre récupérée
         * @param nMaxCount Correspond à la taille maximum du tableau de byte qui contiendra le nom de la fenêtre
         * @return Retourne le nombre de caractère de la fenêtre windows. Il va donc sans dire que cette taille est inférieur ou égale à nMaxCount
         */
        int GetWindowTextA(HWND hWnd, byte[] lpString, int nMaxCount);
        
        /**
         * Examine l'ordre Z des fenêtres enfants associées à la fenêtre parent spécifiée et récupère un handle vers la fenêtre enfant en haut de l'ordre Z
         * @param hWnd Un handle vers la fenêtre parent dont les fenêtres enfants doivent être examinées. Si ce paramètre est NULL , la fonction renvoie un handle vers la fenêtre en haut de l'ordre Z
         * @return Si la fonction réussit, la valeur de retour est un handle vers la fenêtre enfant en haut de l'ordre Z. Si la fenêtre spécifiée n'a pas de fenêtre enfant, la valeur de retour est NULL . Pour obtenir des informations d'erreur étendues, utilisez la fonction GetLastError
         */
        HWND GetTopWindow(HWND hWnd);
        
        /**
         * Récupère un handle vers une fenêtre qui a la relation spécifiée ( Ordre Z ou propriétaire) avec la fenêtre spécifiée
         * @param hWnd Une poignée à une fenêtre. Le handle de fenêtre récupéré est relatif à cette fenêtre, basé sur la valeur du paramètre uCmd
         * @param uCmd La relation entre la fenêtre spécifiée et la fenêtre dont le handle doit être récupéré
         * @return Si la fonction réussit, la valeur de retour est un handle de fenêtre. Si aucune fenêtre n'existe avec la relation spécifiée avec la fenêtre spécifiée, la valeur de retour est NULL . Pour obtenir des informations d'erreur étendues, appelez GetLastError
         */
        HWND GetWindow(HWND hWnd, UINT uCmd);
        
        /**
         * Récupère l'état d'affichage et les positions restaurées, réduites et agrandies de la fenêtre spécifiée
         * @param hWnd Correspond à une poignée vers la fenêtre
         * @param lpwndpl Un pointeur vers la structure WINDOWPLACEMENT qui reçoit l'état d' affichage et les informations de position. Avant d'appeler GetWindowPlacement , définissez le membre de longueur sur sizeof(WINDOWPLACEMENT). GetWindowPlacement échoue si lpwndpl -> length n'est pas défini correctement
         * @return Si la fonction réussit, la valeur de retour est différente de zéro. Si la fonction échoue, la valeur de retour est zéro. Pour obtenir des informations d'erreur étendues, appelez GetLastError
         */
        boolean GetWindowPlacement(HWND hWnd, com.sun.jna.platform.win32.WinUser.WINDOWPLACEMENT lpwndpl);
        
        /**
         * Récupère les dimensions du rectangle de délimitation de la fenêtre spécifiée. Les dimensions sont données en coordonnées d'écran qui sont relatives au coin supérieur gauche de l'écran
         * @param hWnd Une poignée à la fenêtre
         * @param lpRect Un pointeur vers une structure RECT qui reçoit les coordonnées d'écran des coins supérieur gauche et inférieur droit de la fenêtre
         * @return Si la fonction réussit, la valeur de retour est différente de zéro. Si la fonction échoue, la valeur de retour est zéro. Pour obtenir des informations d'erreur étendues, appelez GetLastError 
         */
        boolean GetWindowRect(HWND hWnd, LPRECT lpRect);
        
        /**
         * Détermine si une fenêtre est une fenêtre enfant ou une fenêtre descendante d'une fenêtre parent spécifiée. Une fenêtre enfant est le descendant direct d'une fenêtre parent spécifiée si cette fenêtre parent se trouve dans la chaîne de fenêtres parent ; la chaîne de fenêtres parent mène de la fenêtre d'origine superposée ou contextuelle à la fenêtre enfant
         * @param hWndParent Un handle vers la fenêtre parent
         * @param hWnd Une poignée pour la fenêtre à tester
         * @return Si la fenêtre est une fenêtre enfant ou descendante de la fenêtre parent spécifiée, la valeur de retour est différente de zéro. Si la fenêtre n'est pas une fenêtre enfant ou descendante de la fenêtre parent spécifiée, la valeur de retour est zéro
         */
        boolean IsChild(HWND hWndParent, HWND hWnd);
        
        /**
         * Détermine si le thread appelant est déjà un thread GUI. Il peut également éventuellement convertir le thread en un thread GUI
         * @param bConvert Si true et que le thread n'est pas un thread GUI, converti au passage le thread en thread GUI
         * @return Retourne true si le thread est déjà un thread GUI ou que le thread a bien été converti en thread GUI, sinon false
         */
        boolean IsGUIThread(boolean bConvert);
        
        /**
         * Détermine si la fenêtre spécifiée est réduite (iconique)
         * @param hWnd Une poignée pour la fenêtre à tester
         * @return Retourne true si la fenêtre est réduite, sinon false
         */
        boolean IsIconic(HWND hWnd);
        
        /**
         * Détermine si la fenêtre spécifiée existe
         * @param hWnd Une poignée pour la fenêtre à tester
         * @return Retourne true si elle existe, sinon false
         */
        boolean IsWindow(HWND hWnd);
        
        /**
         * Détermine si la fenêtre spécifiée est une fenêtre Unicode native
         * @param hWnd Une poignée pour la fenêtre à tester
         * @return Retourne true si la fenêtre est une fenêtre Unicode native, sinon false (il s'agit d'une fenêtre ANSI native)
         */
        boolean IsWindowUnicode(HWND hWnd);
        
        /**
         * Détermine l'état de visibilité de la fenêtre spécifiée
         * @param hWnd Une poignée pour la fenêtre à tester
         * @return Si la fenêtre spécifiée, sa fenêtre parent, la fenêtre parent de son parent, et ainsi de suite, ont le style WS_VISIBLE, la valeur de retour est différente de zéro. Sinon, la valeur de retour est zéro. Étant donné que la valeur de retour spécifie si la fenêtre a le style WS_VISIBLE , elle peut être différente de zéro même si la fenêtre est totalement masquée par d'autres fenêtres
         */
        boolean IsWindowVisible(HWND hWnd);
        
        /**
         * Détermine si une fenêtre est agrandie
         * @param hWnd Une poignée pour la fenêtre à tester
         * @return Si la fenêtre est agrandie, la valeur de retour est différente de zéro. Si la fenêtre n'est pas agrandie, la valeur de retour est zéro
         */
        boolean IsZoomed(HWND hWnd);
        
        /**
         * Le processus de premier plan peut appeler la fonction LockSetForegroundWindow pour désactiver les appels à la fonction SetForegroundWindow
         * @param uLockCode Spécifie s'il faut activer ou désactiver les appels à SetForegroundWindow . Ce paramètre peut être l'une des valeurs suivantes: 1 = Désactive les appels, 2 = Active les appels à SetForegroundWindow
         * @return 
         */
        boolean LockSetForegroundWindow(UINT uLockCode);
        
        /**
         * Modifie la position et les dimensions de la fenêtre spécifiée. Pour une fenêtre de niveau supérieur, la position et les dimensions sont relatives au coin supérieur gauche de l'écran. Pour une fenêtre enfant, ils sont relatifs au coin supérieur gauche de la zone cliente de la fenêtre parent
         * @param hWnd Une poignée à la fenêtre
         * @param X La nouvelle position du côté gauche de la fenêtre
         * @param Y La nouvelle position du haut de la fenêtre
         * @param nWidth La nouvelle largeur de la fenêtre
         * @param nHeight La nouvelle hauteur de la fenêtre
         * @param bRepaint Indique si la fenêtre doit être repeinte. Si ce paramètre est TRUE , la fenêtre reçoit un message. Si le paramètre est FALSE , aucun repeint d'aucune sorte ne se produit. Cela s'applique à la zone cliente, à la zone non cliente (y compris la barre de titre et les barres de défilement) et à toute partie de la fenêtre parent découverte suite au déplacement d'une fenêtre enfant
         * @return Retourne true, si la fonction réussit, sinon false
         */
        boolean MoveWindow(HWND hWnd, int X, int Y, int nWidth, int nHeight, boolean bRepaint);
        
        /**
         * Restaure une fenêtre réduite (iconique) à sa taille et à sa position précédentes ; il active alors la fenêtre
         * @param hWnd Une poignée à la fenêtre à restaurer et à activer
         * @return Retourne true si la fonction réussit
         */
        boolean OpenIcon(HWND hWnd);
        
        /**
         * Amène le thread qui a créé la fenêtre spécifiée au premier plan et active la fenêtre. La saisie au clavier est dirigée vers la fenêtre et divers repères visuels sont modifiés pour l'utilisateur. Le système attribue une priorité légèrement plus élevée au thread qui a créé la fenêtre de premier plan qu'aux autres threads
         * @param hWnd Une poignée vers la fenêtre qui doit être activée et amenée au premier plan
         * @return Retourne true si la fenêtre a été mise au premier plan, sinon false
         */
        boolean SetForegroundWindow(HWND hWnd);
        
        /**
         * Définit l'état d'affichage de la fenêtre spécifiée
         * @param hWnd Une poignée à la fenêtre
         * @param nCmdShow Contrôle la façon dont la fenêtre doit être affichée. Ce paramètre est ignoré la première fois qu'une application appelle ShowWindow , si le programme qui a lancé l'application fournit une structure STARTUPINFO . Sinon, la première fois que ShowWindow est appelé, la valeur doit être la valeur obtenue par la fonction WinMain dans son paramètre nCmdShow
         * @return Retourne true, si la fenêtre était précédemment visible. Retourne false si la fenêtre était précédemment masquée.
         */
        boolean ShowWindow(HWND hWnd, int nCmdShow);
        
        /**
         * Définit l'état d'affichage de la fenêtre spécifiée sans attendre la fin de l'opération
         * @param hWnd Une poignée à la fenêtre
         * @param nCmdShow Contrôle la façon dont la fenêtre doit être affichée. Ce paramètre est ignoré la première fois qu'une application appelle ShowWindow , si le programme qui a lancé l'application fournit une structure STARTUPINFO . Sinon, la première fois que ShowWindow est appelé, la valeur doit être la valeur obtenue par la fonction WinMain dans son paramètre nCmdShow
         * @return Retourne true, si la fenêtre était précédemment visible. Retourne false si la fenêtre était précédemment masquée.
         */
        boolean ShowWindowAsync(HWND hWnd, int nCmdShow);
        
        /**
         * Déclenche un signal visuel pour indiquer qu'un son est joué
         * @return Retourne true si le signal visuel était ou sera affiché correctement, sinon false, une erreur a empêché l'affichage du signal 
         */
        boolean SoundSentry();
        
        /**
         * Récupère ou définit la valeur de l'un des paramètres à l'échelle du système. Cette fonction permet également de mettre à jour le profil utilisateur lors du paramétrage d'un paramètre
         * @param uiAction Paramètre à l'échelle du système à récupérer ou à définir
         * @param uiParam Paramètre dont l'utilisation et le format dépendent du paramètre système interrogé ou défini. Pour plus d'informations sur les paramètres à l'échelle du système, consultez le paramètre uiAction . Sauf indication contraire, vous devez spécifier zéro pour ce paramètre
         * @param pvParam Paramètre dont l'utilisation et le format dépendent du paramètre système interrogé ou défini. Pour plus d'informations sur les paramètres à l'échelle du système, consultez le paramètre uiAction . Sauf indication contraire, vous devez spécifier NULL pour ce paramètre
         * @param fWinIni Si un paramètre système est défini, spécifie si le profil utilisateur doit être mis à jour et, dans l'affirmative, si le message WM_SETTINGCHANGE doit être diffusé à toutes les fenêtres de niveau supérieur pour les informer du changement. paramètre peut être zéro si vous ne souhaitez pas mettre à jour le profil utilisateur ou diffuser le message WM_SETTINGCHANGE , ou il peut être une ou plusieurs des valeurs suivantes
         * @return Retourne true si la fonction réussit
         */
        boolean SystemParametersInfoA(UINT uiAction, UINT uiParam, PVOID pvParam, UINT fWinIni);
        
        
        
    }
    
    
    
}