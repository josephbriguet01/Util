/*
 * Copyright (C) JasonPercus Systems, Inc - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by JasonPercus, 03/2023
 */
package com.jasonpercus.util.tray;



import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;
import com.github.kwhat.jnativehook.mouse.NativeMouseEvent;
import com.github.kwhat.jnativehook.mouse.NativeMouseInputListener;
import com.jasonpercus.util.Foreach;
import com.jasonpercus.util.NativeEvent;



/**
 * A {@code TrayIcon} object represents a tray icon that can be
 * added to the {@link SystemTray system tray}. A
 * {@code TrayIcon} can have a tooltip (text), an image, a popup
 * menu, and a set of listeners associated with it.
 *
 * <p>A {@code TrayIcon} can generate various {@link java.awt.event.MouseEvent
 * MouseEvents} and supports adding corresponding listeners to receive
 * notification of these events.  {@code TrayIcon} processes some
 * of the events by itself.  For example, by default, when the
 * right-mouse click is performed on the {@code TrayIcon} it
 * displays the specified popup menu.  When the mouse hovers
 * over the {@code TrayIcon} the tooltip is displayed (this behaviour is
 * platform dependent).
 *
 * <p><strong>Note:</strong> When the {@code MouseEvent} is
 * dispatched to its registered listeners its {@code component}
 * property will be set to {@code null}.  (See {@link
 * java.awt.event.ComponentEvent#getComponent}) The
 * {@code source} property will be set to this
 * {@code TrayIcon}. (See {@link
 * java.util.EventObject#getSource})
 *
 * <p><b>Note:</b> A well-behaved {@link TrayIcon} implementation
 * will assign different gestures to showing a popup menu and
 * selecting a tray icon.
 *
 * <p>A {@code TrayIcon} can generate an {@link java.awt.event.ActionEvent
 * ActionEvent}.  On some platforms, this occurs when the user selects
 * the tray icon using either the mouse or keyboard.
 *
 * <p>If a SecurityManager is installed, the AWTPermission
 * {@code accessSystemTray} must be granted in order to create
 * a {@code TrayIcon}. Otherwise the constructor will throw a
 * SecurityException.
 *
 * <p> See the {@link SystemTray} class overview for an example on how
 * to use the {@code TrayIcon} API.
 *
 * When the {@systemProperty apple.awt.enableTemplateImages} property is
 * set, all images associated with instances of this class are treated
 * as template images by the native desktop system. This means all color
 * information is discarded, and the image is adapted automatically to
 * be visible when desktop theme and/or colors change. This property
 * only affects MacOSX.
 *
 * @since 1.6
 * @see SystemTray#add
 * @see java.awt.event.ComponentEvent#getComponent
 * @see java.util.EventObject#getSource
 *
 * @author Bino George
 * @author Denis Mikhalkin
 * @author Sharon Zakhour
 * @author Anton Tarasov
 * @author JasonPercus
 * @version 1.0
 */
public class TrayIcon {
    
    
    
//ATTRIBUTS
    /**
     * Correspond au veritable objet représentant le {@link java.awt.TrayIcon}
     */
    final java.awt.TrayIcon trayIcon;
    
    /**
     * Correspond à la popup contenu dans le {@link TrayIcon}
     */
    private javax.swing.JPopupMenu popup;
    
    /**
     * Correspond au décallage sur l'axe des abscisses de la popup
     */
    private int offsetPositionX = 0;
    
    /**
     * Correspond au décallage sur l'axe des ordonnées de la popup
     */
    private int offsetPositionY = 0;
    
    /**
     * Correspond à la liste des {@link java.awt.event.ActionListener}
     */
    private transient final java.util.List<java.awt.event.ActionListener> actionListeners = new java.util.ArrayList<>();
    
    /**
     * Correspond à la liste des {@link java.awt.event.MouseListener}
     */
    private transient final java.util.List<java.awt.event.MouseListener> mouseListeners = new java.util.ArrayList<>();
    
    /**
     * Correspond à la liste des {@link java.awt.event.MouseMotionListener}
     */
    private transient final java.util.List<java.awt.event.MouseMotionListener> mouseMotionListeners = new java.util.ArrayList<>();

    
    
//CONSTRUCTOR
    /**
     * Creates a {@code TrayIcon} with the specified image.
     *
     * @param image the {@code Image} to be used
     * @throws IllegalArgumentException if {@code image} is {@code null}
     * @throws UnsupportedOperationException if the system tray isn't supported by the current platform
     * @throws java.awt.HeadlessException if {@code GraphicsEnvironment.isHeadless()} returns {@code true}
     * @throws SecurityException if {@code accessSystemTray} permission is not granted
     * @see SystemTray#add(TrayIcon)
     * @see TrayIcon#TrayIcon(Image, String, PopupMenu)
     * @see TrayIcon#TrayIcon(Image, String)
     * @see SecurityManager#checkPermission
     * @see java.awt.AWTPermission
     */
    public TrayIcon(java.awt.Image image) {
        this.trayIcon = new java.awt.TrayIcon(image);
        this.init();
    }

    /**
     * Creates a {@code TrayIcon} with the specified image and
     * tooltip text. Tooltip may be not visible on some platforms.
     *
     * @param image the {@code Image} to be used
     * @param tooltip the string to be used as tooltip text; if the
     * value is {@code null} no tooltip is shown
     * @throws IllegalArgumentException if {@code image} is {@code null}
     * @throws UnsupportedOperationException if the system tray isn't supported by the current platform
     * @throws java.awt.HeadlessException if {@code GraphicsEnvironment.isHeadless()} returns {@code true}
     * @throws SecurityException if {@code accessSystemTray} permission is not granted
     * @see SystemTray#add(TrayIcon)
     * @see TrayIcon#TrayIcon(Image)
     * @see TrayIcon#TrayIcon(Image, String, PopupMenu)
     * @see SecurityManager#checkPermission
     * @see java.awt.AWTPermission
     */
    public TrayIcon(java.awt.Image image, String tooltip) {
        this.trayIcon = new java.awt.TrayIcon(image, tooltip);
        this.init();
    }

    /**
     * Creates a {@code TrayIcon} with the specified image,
     * tooltip and popup menu. Tooltip may be not visible on some platforms.
     *
     * @param image the {@code Image} to be used
     * @param tooltip the string to be used as tooltip text; if the value is {@code null} no tooltip is shown
     * @param popup the menu to be used for the tray icon's popup menu; if the value is {@code null} no popup menu is shown
     * @throws IllegalArgumentException if {@code image} is {@code null}
     * @throws UnsupportedOperationException if the system tray isn't supported by the current platform
     * @throws java.awt.HeadlessException if {@code GraphicsEnvironment.isHeadless()} returns {@code true}
     * @throws SecurityException if {@code accessSystemTray} permission is not granted
     * @see SystemTray#add(TrayIcon)
     * @see TrayIcon#TrayIcon(Image, String)
     * @see TrayIcon#TrayIcon(Image)
     * @see javax.swing.JPopupMenu
     * @see java.awt.event.MouseListener
     * @see #addMouseListener(MouseListener)
     * @see SecurityManager#checkPermission
     * @see java.awt.AWTPermission
     */
    public TrayIcon(java.awt.Image image, String tooltip, javax.swing.JPopupMenu popup) {
        this.popup = popup;
        this.trayIcon = new java.awt.TrayIcon(image, tooltip);
        this.init();
    }

    /**
     * Renvoie le décallage sur l'axe des abscisses de la popup
     * @return Retourne le décallage sur l'axe des abscisses de la popup
     */
    public final int getOffsetPositionX() {
        return offsetPositionX;
    }

    /**
     * Modifie le décallage sur l'axe des abscisses de la popup
     * @param offsetPositionX Correspond au nouveau décallage
     */
    public final void setOffsetPositionX(int offsetPositionX) {
        this.offsetPositionX = offsetPositionX;
    }

    /**
     * Renvoie le décallage sur l'axe des ordonnées de la popup
     * @return Retourne le décallage sur l'axe des ordonnées de la popup
     */
    public final int getOffsetPositionY() {
        return offsetPositionY;
    }

    /**
     * Modifie le décallage sur l'axe des ordonnées de la popup
     * @param offsetPositionY Correspond au nouveau décallage
     */
    public final void setOffsetPositionY(int offsetPositionY) {
        this.offsetPositionY = offsetPositionY;
    }
    
    /**
     * Returns the current image used for this {@code TrayIcon}.
     *
     * @return the image
     * @see #setImage(Image)
     * @see java.awt.Image
     */
    public final java.awt.Image getImage() {
        return this.trayIcon.getImage();
    }
    
    /**
     * Sets the image for this {@code TrayIcon}.  The previous
     * tray icon image is discarded without calling the {@link
     * java.awt.Image#flush} method &#8212; you will need to call it
     * manually.
     *
     * <p> If the image represents an animated image, it will be
     * animated automatically.
     *
     * <p> See the {@link #setImageAutoSize(boolean)} property for
     * details on the size of the displayed image.
     *
     * <p> Calling this method with the same image that is currently
     * being used has no effect.
     *
     * @throws NullPointerException if {@code image} is {@code null}
     * @param image the non-null {@code Image} to be used
     * @see #getImage
     * @see java.awt.Image
     * @see SystemTray#add(TrayIcon)
     * @see TrayIcon#TrayIcon(Image, String)
     */
    public final void setImage(java.awt.Image image) {
        this.trayIcon.setImage(image);
    }
    
    /**
     * Returns the popup menu associated with this {@code TrayIcon}.
     *
     * @return the popup menu or {@code null} if none exists
     * @see #setPopupMenu(JPopupMenu)
     */
    public final javax.swing.JPopupMenu getPopupMenu() {
        return this.popup;
    }
    
    /**
     * Sets the popup menu for this {@code TrayIcon}.  If
     * {@code popup} is {@code null}, no popup menu will be
     * associated with this {@code TrayIcon}.
     *
     * <p>Note that this {@code popup} must not be added to any
     * parent before or after it is set on the tray icon.  If you add
     * it to some parent, the {@code popup} may be removed from
     * that parent.
     *
     * <p>The {@code popup} can be set on one {@code TrayIcon} only.
     * Setting the same popup on multiple {@code TrayIcon}s will cause
     * an {@code IllegalArgumentException}.
     *
     * <p><strong>Note:</strong> Some platforms may not support
     * showing the user-specified popup menu component when the user
     * right-clicks the tray icon.  In this situation, either no menu
     * will be displayed or, on some systems, a native version of the
     * menu may be displayed.
     *
     * @throws IllegalArgumentException if the {@code popup} is already
     * set for another {@code TrayIcon}
     * @param popup a {@code JPopupMenu} or {@code null} to
     * remove any popup menu
     * @see #getPopupMenu
     */
    public final void setPopupMenu(javax.swing.JPopupMenu popup){
        this.popup = popup;
    }
    
    /**
     * Returns the tooltip string associated with this
     * {@code TrayIcon}.
     *
     * @return the tooltip string or {@code null} if none exists
     * @see #setToolTip(String)
     */
    public final String getToolTip() {
        return this.trayIcon.getToolTip();
    }
    
    /**
     * Sets the tooltip string for this {@code TrayIcon}. The
     * tooltip is displayed automatically when the mouse hovers over
     * the icon.  Tooltip may be not visible on some platforms.
     * Setting the tooltip to {@code null} removes any tooltip text.
     *
     * When displayed, the tooltip string may be truncated on some platforms;
     * the number of characters that may be displayed is platform-dependent.
     *
     * @param tooltip the string for the tooltip; if the value is
     * {@code null} no tooltip is shown
     * @see #getToolTip
     */
    public final void setToolTip(String tooltip) {
        this.trayIcon.setToolTip(tooltip);
    }
    
    /**
     * Returns the value of the auto-size property.
     *
     * @return {@code true} if the image will be auto-sized,
     * {@code false} otherwise
     * @see #setImageAutoSize(boolean)
     */
    public final boolean isImageAutoSize() {
        return this.trayIcon.isImageAutoSize();
    }
    
    /**
     * Sets the auto-size property.  Auto-size determines whether the
     * tray image is automatically sized to fit the space allocated
     * for the image on the tray.  By default, the auto-size property
     * is set to {@code false}.
     *
     * <p> If auto-size is {@code false}, and the image size
     * doesn't match the tray icon space, the image is painted as-is
     * inside that space &#8212; if larger than the allocated space, it will
     * be cropped.
     *
     * <p> If auto-size is {@code true}, the image is stretched or shrunk to
     * fit the tray icon space.
     *
     * @param autosize {@code true} to auto-size the image,
     * {@code false} otherwise
     * @see #isImageAutoSize
     */
    public final void setImageAutoSize(boolean autosize) {
        this.trayIcon.setImageAutoSize(autosize);
    }
    
    /**
     * Adds the specified mouse listener to receive mouse events from
     * this {@code TrayIcon}.  Calling this method with a
     * {@code null} value has no effect.
     *
     * <p><b>Note</b>: The {@code MouseEvent}'s coordinates (received
     * from the {@code TrayIcon}) are relative to the screen, not the
     * {@code TrayIcon}.
     *
     * <p> <b>Note: </b>The {@code MOUSE_ENTERED} and
     * {@code MOUSE_EXITED} mouse events are not supported.
     * <p>Refer to <a href="doc-files/AWTThreadIssues.html#ListenersThreads"
     * >AWT Threading Issues</a> for details on AWT's threading model.
     *
     * @param    listener the mouse listener
     * @see      java.awt.event.MouseEvent
     * @see      java.awt.event.MouseListener
     * @see      #removeMouseListener(MouseListener)
     * @see      #getMouseListeners
     */
    public final void addMouseListener(java.awt.event.MouseListener listener) {
        if(listener != null && !mouseListeners.contains(listener)){
            this.mouseListeners.add(listener);
            this.trayIcon.addMouseListener(listener);
        }
    }
    
    /**
     * Removes the specified mouse listener.  Calling this method with
     * {@code null} or an invalid value has no effect.
     * <p>Refer to <a href="doc-files/AWTThreadIssues.html#ListenersThreads"
     * >AWT Threading Issues</a> for details on AWT's threading model.
     *
     * @param    listener   the mouse listener
     * @see      java.awt.event.MouseEvent
     * @see      java.awt.event.MouseListener
     * @see      #addMouseListener(MouseListener)
     * @see      #getMouseListeners
     */
    public final void removeMouseListener(java.awt.event.MouseListener listener) {
        if(listener != null && mouseListeners.contains(listener)){
            this.mouseListeners.remove(listener);
        }
    }
    
    /**
     * Returns an array of all the mouse listeners
     * registered on this {@code TrayIcon}.
     *
     * @return all of the {@code MouseListeners} registered on
     * this {@code TrayIcon} or an empty array if no mouse
     * listeners are currently registered
     *
     * @see      #addMouseListener(MouseListener)
     * @see      #removeMouseListener(MouseListener)
     * @see      java.awt.event.MouseListener
     */
    public final java.awt.event.MouseListener[] getMouseListeners() {
        java.awt.event.MouseListener[] listeners = new java.awt.event.MouseListener[mouseListeners.size()];
        Foreach.execute((int i, int length, java.awt.event.MouseListener o) -> {
            listeners[i] = o;
            return true;
        }, mouseListeners);
        return listeners;
    }
    
    /**
     * Adds the specified mouse listener to receive mouse-motion
     * events from this {@code TrayIcon}.  Calling this method
     * with a {@code null} value has no effect.
     *
     * <p><b>Note</b>: The {@code MouseEvent}'s coordinates (received
     * from the {@code TrayIcon}) are relative to the screen, not the
     * {@code TrayIcon}.
     *
     * <p> <b>Note: </b>The {@code MOUSE_DRAGGED} mouse event is not supported.
     * <p>Refer to <a href="doc-files/AWTThreadIssues.html#ListenersThreads"
     * >AWT Threading Issues</a> for details on AWT's threading model.
     *
     * @param    listener   the mouse listener
     * @see      java.awt.event.MouseEvent
     * @see      java.awt.event.MouseMotionListener
     * @see      #removeMouseMotionListener(MouseMotionListener)
     * @see      #getMouseMotionListeners
     */
    public final void addMouseMotionListener(java.awt.event.MouseMotionListener listener) {
        if(listener != null && !mouseMotionListeners.contains(listener)){
            this.mouseMotionListeners.add(listener);
            this.trayIcon.addMouseMotionListener(listener);
        }
    }
    
    /**
     * Removes the specified mouse-motion listener.  Calling this method with
     * {@code null} or an invalid value has no effect.
     * <p>Refer to <a href="doc-files/AWTThreadIssues.html#ListenersThreads"
     * >AWT Threading Issues</a> for details on AWT's threading model.
     *
     * @param    listener   the mouse listener
     * @see      java.awt.event.MouseEvent
     * @see      java.awt.event.MouseMotionListener
     * @see      #addMouseMotionListener(MouseMotionListener)
     * @see      #getMouseMotionListeners
     */
    public final void removeMouseMotionListener(java.awt.event.MouseMotionListener listener) {
        if(listener != null && mouseMotionListeners.contains(listener)){
            this.mouseMotionListeners.remove(listener);
        }
    }
    
    /**
     * Returns an array of all the mouse-motion listeners
     * registered on this {@code TrayIcon}.
     *
     * @return all of the {@code MouseInputListeners} registered on
     * this {@code TrayIcon} or an empty array if no mouse
     * listeners are currently registered
     *
     * @see      #addMouseMotionListener(MouseMotionListener)
     * @see      #removeMouseMotionListener(MouseMotionListener)
     * @see      java.awt.event.MouseMotionListener
     */
    public final java.awt.event.MouseMotionListener[] getMouseMotionListeners() {
        java.awt.event.MouseMotionListener[] listeners = new java.awt.event.MouseMotionListener[mouseMotionListeners.size()];
        Foreach.execute((int i, int length, java.awt.event.MouseMotionListener o) -> {
            listeners[i] = o;
            return true;
        }, mouseMotionListeners);
        return listeners;
    }
    
    /**
     * Adds the specified action listener to receive
     * {@code ActionEvent}s from this {@code TrayIcon}.
     * Action events usually occur when a user selects the tray icon,
     * using either the mouse or keyboard.  The conditions in which
     * action events are generated are platform-dependent.
     *
     * <p>Calling this method with a {@code null} value has no
     * effect.
     * <p>Refer to <a href="doc-files/AWTThreadIssues.html#ListenersThreads"
     * >AWT Threading Issues</a> for details on AWT's threading model.
     *
     * @param         listener the action listener
     * @see           #removeActionListener
     * @see           #getActionListeners
     * @see           java.awt.event.ActionListener
     * @see #setActionCommand(String)
     */
    public final void addActionListener(java.awt.event.ActionListener listener) {
        if(listener != null && !actionListeners.contains(listener)){
            this.actionListeners.add(listener);
            this.trayIcon.addActionListener(listener);
        }
    }
    
    /**
     * Removes the specified action listener.  Calling this method with
     * {@code null} or an invalid value has no effect.
     * <p>Refer to <a href="doc-files/AWTThreadIssues.html#ListenersThreads"
     * >AWT Threading Issues</a> for details on AWT's threading model.
     *
     * @param    listener   the action listener
     * @see      java.awt.event.ActionEvent
     * @see      java.awt.event.ActionListener
     * @see      #addActionListener(ActionListener)
     * @see      #getActionListeners
     * @see #setActionCommand(String)
     */
    public final void removeActionListener(java.awt.event.ActionListener listener) {
        if(listener != null && actionListeners.contains(listener)){
            this.actionListeners.remove(listener);
        }
    }
    
    /**
     * Returns an array of all the action listeners
     * registered on this {@code TrayIcon}.
     *
     * @return all of the {@code ActionListeners} registered on
     * this {@code TrayIcon} or an empty array if no action
     * listeners are currently registered
     *
     * @see      #addActionListener(ActionListener)
     * @see      #removeActionListener(ActionListener)
     * @see      java.awt.event.ActionListener
     */
    public final java.awt.event.ActionListener[] getActionListeners() {
        java.awt.event.ActionListener[] listeners = new java.awt.event.ActionListener[actionListeners.size()];
        Foreach.execute((int i, int length, java.awt.event.ActionListener o) -> {
            listeners[i] = o;
            return true;
        }, actionListeners);
        return listeners;
    }
    
    /**
     * Returns the command name of the action event fired by this tray icon.
     *
     * @return the action command name, or {@code null} if none exists
     * @see #addActionListener(ActionListener)
     * @see #setActionCommand(String)
     */
    public final String getActionCommand() {
        return this.trayIcon.getActionCommand();
    }
    
    /**
     * Sets the command name for the action event fired by this tray
     * icon.  By default, this action command is set to
     * {@code null}.
     *
     * @param command  a string used to set the tray icon's
     *                 action command.
     * @see java.awt.event.ActionEvent
     * @see #addActionListener(ActionListener)
     * @see #getActionCommand
     */
    public final void setActionCommand(String command) {
        this.trayIcon.setActionCommand(command);
    }
    
    /**
     * Displays a popup message near the tray icon.  The message will
     * disappear after a time or if the user clicks on it.  Clicking
     * on the message may trigger an {@code ActionEvent}.
     *
     * <p>Either the caption or the text may be {@code null}, but an
     * {@code NullPointerException} is thrown if both are
     * {@code null}.
     *
     * When displayed, the caption or text strings may be truncated on
     * some platforms; the number of characters that may be displayed is
     * platform-dependent.
     *
     * <p><strong>Note:</strong> Some platforms may not support
     * showing a message.
     *
     * @param caption the caption displayed above the text, usually in
     * bold; may be {@code null}
     * @param text the text displayed for the particular message; may be
     * {@code null}
     * @param messageType an enum indicating the message type
     * @throws NullPointerException if both {@code caption}
     * and {@code text} are {@code null}
     */
    public final void displayMessage(String caption, String text, java.awt.TrayIcon.MessageType messageType) {
        this.trayIcon.displayMessage(caption, text, messageType);
    }
    
    /**
     * Returns the size, in pixels, of the space that the tray icon
     * occupies in the system tray.  For the tray icon that is not yet
     * added to the system tray, the returned size is equal to the
     * result of the {@link SystemTray#getTrayIconSize}.
     *
     * @return the size of the tray icon, in pixels
     * @see TrayIcon#setImageAutoSize(boolean)
     * @see java.awt.Image
     * @see TrayIcon#getSize()
     */
    public final java.awt.Dimension getSize() {
        return this.trayIcon.getSize();
    }
    
    /**
     * A redéfinir au besoin lorsqu'une touche est en train d'être pressée
     * @param nativeEvent Correspond à l'évènement reçu
     */
    protected void evtKeyPressed(NativeKeyEvent nativeEvent){
        
    }
    
    /**
     * A redéfinir au besoin lorsqu'une touche vient d'être relaché
     * @param nativeEvent Correspond à l'évènement reçu
     */
    protected void evtKeyReleased(NativeKeyEvent nativeEvent){
        
    }
    
    /**
     * A redéfinir au besoin lorsqu'une touche vient d'être tapée
     * @param nativeEvent Correspond à l'évènement reçu
     */
    protected void evtKeyTyped(NativeKeyEvent nativeEvent){
        
    }
    
    
    
//METHODE PRIVATE
    /**
     * Termine l'initialisation de l'objet {@linkplain TrayIcon}
     */
    @SuppressWarnings("Convert2Lambda")
    private void init(){
        trayIcon.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseReleased(java.awt.event.MouseEvent e) {
                maybeShowPopup(e);
            }

            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                maybeShowPopup(e);
            }

            private void maybeShowPopup(java.awt.event.MouseEvent e) {
                if (e.isPopupTrigger()) {
                    popup.setLocation(e.getX() + offsetPositionX, e.getY() + offsetPositionY);
                    popup.setInvoker(popup);
                    popup.setVisible(true);
                }
            }
        });
        this.trayIcon.setImageAutoSize(true);
        
        try {
            NativeEvent nativeEvent = new NativeEvent();
            nativeEvent.addMouseListener(new NativeMouseInputListener() {
                @Override
                public void nativeMouseClicked(NativeMouseEvent nativeEvent) {
                    if(popup.isShowing()){
                        int x = popup.getLocationOnScreen().x;
                        int y = popup.getLocationOnScreen().y;
                        int w = popup.getWidth();
                        int h = popup.getHeight();
                        
                        java.awt.Point mousePosition = java.awt.MouseInfo.getPointerInfo().getLocation();
                        
                        if (!(x <= mousePosition.x && mousePosition.x <= x + w && y <= mousePosition.y && mousePosition.y <= y + h))
                            popup.setVisible(false);
                    }
                }
            });
            nativeEvent.addKeyListener(new NativeKeyListener() {
                @Override
                public void nativeKeyPressed(NativeKeyEvent nativeEvent) {
                    evtKeyPressed(nativeEvent);
                }
                
                @Override
                public void nativeKeyReleased(NativeKeyEvent nativeEvent) {
                    evtKeyReleased(nativeEvent);
                }

                @Override
                public void nativeKeyTyped(NativeKeyEvent nativeEvent) {
                    evtKeyTyped(nativeEvent);
                }
            });
        } catch (NativeEvent.NativeEventException ex) {}
    }
    
    
    
}