/*
 * Copyright (C) JasonPercus Systems, Inc - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by JasonPercus, 03/2023
 */
package com.jasonpercus.util.tray;



import com.jasonpercus.util.Foreach;



/**
 * The {@code SystemTray} class represents the system tray for a
 * desktop. On Microsoft Windows it is referred to as the "Taskbar
 * Status Area", on Gnome it is referred to as the "Notification
 * Area", on KDE it is referred to as the "System Tray".  The system
 * tray is shared by all applications running on the desktop.
 *
 * <p> On some platforms the system tray may not be present or may not
 * be supported, in this case {@link SystemTray#getSystemTray()}
 * throws {@link UnsupportedOperationException}.  To detect whether the
 * system tray is supported, use {@link SystemTray#isSupported}.
 *
 * <p>The {@code SystemTray} may contain one or more {@link
 * TrayIcon TrayIcons}, which are added to the tray using the {@link
 * #add} method, and removed when no longer needed, using the
 * {@link #remove}.  {@code TrayIcon} consists of an
 * image, a jpopup menu and a set of associated listeners.  Please see
 * the {@link TrayIcon} class for details.
 *
 * <p>Every Java application has a single {@code SystemTray}
 * instance that allows the app to interface with the system tray of
 * the desktop while the app is running.  The {@code SystemTray}
 * instance can be obtained from the {@link #getSystemTray} method.
 * An application may not create its own instance of
 * {@code SystemTray}.
 *
 * <p>The following code snippet demonstrates how to access
 * and customize the system tray:
 * <pre>
 * <code>
 *     {@link TrayIcon} trayIcon = null;
 *     if (SystemTray.isSupported()) {
 *         // get the SystemTray instance
 *         SystemTray tray = SystemTray.{@link #getSystemTray};
 *         // load an image
 *         {@link java.awt.Image} image = {@link java.awt.Toolkit#getImage(String) Toolkit.getDefaultToolkit().getImage}(...);
 *         // create a action listener to listen for default action executed on the tray icon
 *         {@link java.awt.event.ActionListener} listener = new {@link java.awt.event.ActionListener ActionListener}() {
 *             public void {@link java.awt.event.ActionListener#actionPerformed actionPerformed}({@link java.awt.event.ActionEvent} e) {
 *                 // execute default action of the application
 *                 // ...
 *             }
 *         };
 *         // create a popup menu
 *         {@link javax.swing.JPopupMenu} popup = new {@link javax.swing.JPopupMenu#JPopupMenu JPopupMenu}();
 *         // create menu item for the default action
 *         JMenuItem defaultItem = new JMenuItem(...);
 *         defaultItem.addActionListener(listener);
 *         popup.add(defaultItem);
 *         /// ... add other items
 *         // construct a TrayIcon
 *         trayIcon = new {@link TrayIcon#TrayIcon(java.awt.Image, String, javax.swing.JPopupMenu) TrayIcon}(image, "Tray Demo", popup);
 *         // set the TrayIcon properties
 *         trayIcon.{@link TrayIcon#addActionListener(java.awt.event.ActionListener) addActionListener}(listener);
 *         // ...
 *         // add the tray image
 *         try {
 *             tray.{@link SystemTray#add(TrayIcon) add}(trayIcon);
 *         } catch (AWTException e) {
 *             System.err.println(e);
 *         }
 *         // ...
 *     } else {
 *         // disable tray option in your application or
 *         // perform other actions
 *         ...
 *     }
 *     // ...
 *     // some time later
 *     // the application state has changed - update the image
 *     if (trayIcon != null) {
 *         trayIcon.{@link TrayIcon#setImage(java.awt.Image) setImage}(updatedImage);
 *     }
 *     // ...
 * </code>
 * </pre>
 *
 * @since 1.6
 * @see TrayIcon
 *
 * @author Bino George
 * @author Denis Mikhalkin
 * @author Sharon Zakhour
 * @author Anton Tarasov
 * @author JasonPercus
 * @version 1.0
 */
public class SystemTray {
    
    
    
//ATTRIBUTS
    /**
     * Correspond au veritable objet représentant le {@link java.awt.SystemTray}
     */
    private final transient java.awt.SystemTray systemTray;
    
    /**
     * Correspond à la liste des {@link TrayIcon} contenus dans ce {@linkplain SystemTray}
     */
    private final transient java.util.List<TrayIcon> trayIcons = new java.util.ArrayList<>();
    
    
    
//CONSTRUCTOR
    /**
     * Constructeur par défaut privé
     * @throws UnsupportedOperationException if the system tray isn't supported by the current platform
     * @throws java.awt.HeadlessException if {@code GraphicsEnvironment.isHeadless()} returns {@code true}
     * @throws SecurityException if {@code accessSystemTray} permission is not granted
     */
    private SystemTray() throws UnsupportedOperationException, java.awt.HeadlessException, SecurityException {
        this.systemTray = java.awt.SystemTray.getSystemTray();
    }
    
    
    
//METHODES PUBLICS
    /**
     * Gets the {@code SystemTray} instance that represents the
     * desktop's tray area.  This always returns the same instance per
     * application.  On some platforms the system tray may not be
     * supported.  You may use the {@link #isSupported} method to
     * check if the system tray is supported.
     *
     * <p>If a SecurityManager is installed, the AWTPermission
     * {@code accessSystemTray} must be granted in order to get the
     * {@code SystemTray} instance. Otherwise this method will throw a
     * SecurityException.
     *
     * @return the {@code SystemTray} instance that represents the desktop's tray area
     * @throws UnsupportedOperationException if the system tray isn't supported by the current platform
     * @throws java.awt.HeadlessException if {@code GraphicsEnvironment.isHeadless()} returns {@code true}
     * @throws SecurityException if {@code accessSystemTray} permission is not granted
     * @see #add(TrayIcon)
     * @see TrayIcon
     * @see #isSupported
     * @see SecurityManager#checkPermission
     * @see java.awt.AWTPermission
     */
    public static SystemTray getSystemTray() throws UnsupportedOperationException, java.awt.HeadlessException, SecurityException {
        return new SystemTray();
    }
    
    /**
     * Returns whether the system tray is supported on the current
     * platform.  In addition to displaying the tray icon, minimal
     * system tray support includes either a popup menu (see {@link
     * TrayIcon#setPopupMenu(javax.swing.JPopupMenu)}) or an action event (see 
     * {@link TrayIcon#addActionListener(ActionListener)}).
     *
     * <p>Developers should not assume that all of the system tray
     * functionality is supported.  To guarantee that the tray icon's
     * default action is always accessible, add the default action to
     * both the action listener and the popup menu.  See the {@link
     * SystemTray example} for an example of how to do this.
     *
     * <p><b>Note</b>: When implementing {@code SystemTray} and
     * {@code TrayIcon} it is <em>strongly recommended</em> that
     * you assign different gestures to the popup menu and an action
     * event.  Overloading a gesture for both purposes is confusing
     * and may prevent the user from accessing one or the other.
     *
     * @see #getSystemTray
     * @return {@code false} if no system tray access is supported; this
     * method returns {@code true} if the minimal system tray access is
     * supported but does not guarantee that all system tray
     * functionality is supported for the current platform
     */
    public static boolean isSupported() {
        return java.awt.SystemTray.isSupported();
    }
    
    /**
     * Adds a {@code TrayIcon} to the {@code SystemTray}.
     * The tray icon becomes visible in the system tray once it is
     * added.  The order in which icons are displayed in a tray is not
     * specified - it is platform and implementation-dependent.
     *
     * <p> All icons added by the application are automatically
     * removed from the {@code SystemTray} upon application exit
     * and also when the desktop system tray becomes unavailable.
     *
     * @param trayIcon the {@code TrayIcon} to be added
     * @throws NullPointerException if {@code trayIcon} is
     * {@code null}
     * @throws IllegalArgumentException if the same instance of
     * a {@code TrayIcon} is added more than once
     * @throws java.awt.AWTException if the desktop system tray is missing
     * @see #remove(TrayIcon)
     * @see #getSystemTray
     * @see TrayIcon
     * @see java.awt.Image
     */
    public void add(TrayIcon trayIcon) throws NullPointerException, IllegalArgumentException, java.awt.AWTException{
        this.systemTray.add(trayIcon.trayIcon);
        this.trayIcons.add(trayIcon);
    }
    
    /**
     * Removes the specified {@code TrayIcon} from the
     * {@code SystemTray}.
     *
     * <p> All icons added by the application are automatically
     * removed from the {@code SystemTray} upon application exit
     * and also when the desktop system tray becomes unavailable.
     *
     * <p> If {@code trayIcon} is {@code null} or was not
     * added to the system tray, no exception is thrown and no action
     * is performed.
     *
     * @param trayIcon the {@code TrayIcon} to be removed
     * @see #add(TrayIcon)
     * @see TrayIcon
     */
    public void remove(TrayIcon trayIcon) {
        this.systemTray.remove(trayIcon.trayIcon);
        this.trayIcons.remove(trayIcon);
    }
    
    /**
     * Returns an array of all icons added to the tray by this
     * application.  You can't access the icons added by another
     * application.  Some browsers partition applets in different
     * code bases into separate contexts, and establish walls between
     * these contexts.  In such a scenario, only the tray icons added
     * from this context will be returned.
     *
     * <p> The returned array is a copy of the actual array and may be
     * modified in any way without affecting the system tray.  To
     * remove a {@code TrayIcon} from the
     * {@code SystemTray}, use the {@link
     * #remove(TrayIcon)} method.
     *
     * @return an array of all tray icons added to this tray, or an
     * empty array if none has been added
     * @see #add(TrayIcon)
     * @see TrayIcon
     */
    public TrayIcon[] getTrayIcons() {
        TrayIcon[] listeners = new TrayIcon[trayIcons.size()];
        Foreach.execute((int i, int length, TrayIcon o) -> {
            listeners[i] = o;
            return true;
        }, trayIcons);
        return listeners;
    }
    
    /**
     * Returns the size, in pixels, of the space that a tray icon will
     * occupy in the system tray.  Developers may use this methods to
     * acquire the preferred size for the image property of a tray icon
     * before it is created.  For convenience, there is a similar
     * method {@link TrayIcon#getSize} in the {@code TrayIcon} class.
     *
     * @return the default size of a tray icon, in pixels
     * @see TrayIcon#setImageAutoSize(boolean)
     * @see java.awt.Image
     * @see TrayIcon#getSize()
     */
    public java.awt.Dimension getTrayIconSize() {
        return this.systemTray.getTrayIconSize();
    }
    
    /**
     * Adds a {@code PropertyChangeListener} to the list of listeners for the
     * specific property. The following properties are currently supported:
     *
     * <table class="striped">
     * <caption>SystemTray properties</caption>
     * <thead>
     *   <tr>
     *     <th scope="col">Property
     *     <th scope="col">Description
     * </thead>
     * <tbody>
     *   <tr>
     *     <th scope="row">{@code trayIcons}
     *     <td>The {@code SystemTray}'s array of {@code TrayIcon} objects. The
     *     array is accessed via the {@link #getTrayIcons} method. This property
     *     is changed when a tray icon is added to (or removed from) the system
     *     tray. For example, this property is changed when the system tray
     *     becomes unavailable on the desktop and the tray icons are
     *     automatically removed.
     *   <tr>
     *     <th scope="row">{@code systemTray}
     *     <td>This property contains {@code SystemTray} instance when the
     *     system tray is available or {@code null} otherwise. This property is
     *     changed when the system tray becomes available or unavailable on the
     *     desktop. The property is accessed by the {@link #getSystemTray}
     *     method.
     * </tbody>
     * </table>
     * <p>
     * The {@code listener} listens to property changes only in this context.
     * <p>
     * If {@code listener} is {@code null}, no exception is thrown
     * and no action is performed.
     *
     * @param propertyName the specified property
     * @param listener the property change listener to be added
     *
     * @see #removePropertyChangeListener
     * @see #getPropertyChangeListeners
     */
    public void addPropertyChangeListener(String propertyName, java.beans.PropertyChangeListener listener){
        this.systemTray.addPropertyChangeListener(propertyName, listener);
    }
    
    /**
     * Removes a {@code PropertyChangeListener} from the listener list
     * for a specific property.
     * <p>
     * The {@code PropertyChangeListener} must be from this context.
     * <p>
     * If {@code propertyName} or {@code listener} is {@code null} or invalid,
     * no exception is thrown and no action is taken.
     *
     * @param propertyName the specified property
     * @param listener the PropertyChangeListener to be removed
     *
     * @see #addPropertyChangeListener
     * @see #getPropertyChangeListeners
     */
    public void removePropertyChangeListener(String propertyName, java.beans.PropertyChangeListener listener) {
        this.systemTray.removePropertyChangeListener(propertyName, listener);
    }
    
    /**
     * Returns an array of all the listeners that have been associated
     * with the named property.
     * <p>
     * Only the listeners in this context are returned.
     *
     * @param propertyName the specified property
     * @return all of the {@code PropertyChangeListener}s associated with
     *         the named property; if no such listeners have been added or
     *         if {@code propertyName} is {@code null} or invalid, an empty
     *         array is returned
     *
     * @see #addPropertyChangeListener
     * @see #removePropertyChangeListener
     */
    public java.beans.PropertyChangeListener[] getPropertyChangeListeners(String propertyName){
        return this.systemTray.getPropertyChangeListeners(propertyName);
    }
    
    
    
}