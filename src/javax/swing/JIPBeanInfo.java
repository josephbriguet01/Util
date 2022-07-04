/*
 * Copyright (C) JasonPercus Systems, Inc - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by JasonPercus, 05/2021
 */
package javax.swing;



import java.beans.*;



/**
 * Cette classe représente les informations du Bean {@link JIP}
 * @see JIP
 * @author JasonPercus
 * @version 1.0
 */
public class JIPBeanInfo extends SimpleBeanInfo {

    
    
    /**
     * Correspond au constructeur par défaut
     */
    public JIPBeanInfo() {
    }
    
    // Bean descriptor//GEN-FIRST:BeanDescriptor
    /*lazy BeanDescriptor*/
    private static BeanDescriptor getBdescriptor(){
        BeanDescriptor beanDescriptor = new BeanDescriptor  ( javax.swing.JIP.class , null ); // NOI18N
        beanDescriptor.setDisplayName ( "IP" );
        beanDescriptor.setShortDescription ( "Un composant léger qui permet l'édition d'une adresse IP" );//GEN-HEADEREND:BeanDescriptor
        // Here you can add code for customizing the BeanDescriptor.

        return beanDescriptor;     }//GEN-LAST:BeanDescriptor


    // Property identifiers//GEN-FIRST:Properties
    private static final int PROPERTY_actionMap = 0;
    private static final int PROPERTY_alignmentX = 1;
    private static final int PROPERTY_alignmentY = 2;
    private static final int PROPERTY_background = 3;
    private static final int PROPERTY_bloc = 4;
    private static final int PROPERTY_border = 5;
    private static final int PROPERTY_bounds = 6;
    private static final int PROPERTY_caret = 7;
    private static final int PROPERTY_caretColor = 8;
    private static final int PROPERTY_component = 9;
    private static final int PROPERTY_componentCount = 10;
    private static final int PROPERTY_componentOrientation = 11;
    private static final int PROPERTY_cursor = 12;
    private static final int PROPERTY_debugGraphicsOptions = 13;
    private static final int PROPERTY_disabledTextColor = 14;
    private static final int PROPERTY_dot = 15;
    private static final int PROPERTY_editable = 16;
    private static final int PROPERTY_enabled = 17;
    private static final int PROPERTY_focusable = 18;
    private static final int PROPERTY_focusTraversalKeys = 19;
    private static final int PROPERTY_font = 20;
    private static final int PROPERTY_foreground = 21;
    private static final int PROPERTY_height = 22;
    private static final int PROPERTY_highlighter = 23;
    private static final int PROPERTY_ignoreRepaint = 24;
    private static final int PROPERTY_inheritsPopupMenu = 25;
    private static final int PROPERTY_insets = 26;
    private static final int PROPERTY_layout = 27;
    private static final int PROPERTY_locale = 28;
    private static final int PROPERTY_location = 29;
    private static final int PROPERTY_locationOnScreen = 30;
    private static final int PROPERTY_maximumSize = 31;
    private static final int PROPERTY_minimumSize = 32;
    private static final int PROPERTY_name = 33;
    private static final int PROPERTY_nextFocusableComponent = 34;
    private static final int PROPERTY_opaque = 35;
    private static final int PROPERTY_preferredSize = 36;
    private static final int PROPERTY_requestFocusEnabled = 37;
    private static final int PROPERTY_selectedTextColor = 38;
    private static final int PROPERTY_selectionColor = 39;
    private static final int PROPERTY_showing = 40;
    private static final int PROPERTY_size = 41;
    private static final int PROPERTY_spacePoints = 42;
    private static final int PROPERTY_text = 43;
    private static final int PROPERTY_toolTipText = 44;
    private static final int PROPERTY_treeLock = 45;
    private static final int PROPERTY_verifyInputWhenFocusTarget = 46;
    private static final int PROPERTY_visible = 47;
    private static final int PROPERTY_wheelMouseChangeIp = 48;
    private static final int PROPERTY_width = 49;
    private static final int PROPERTY_x = 50;
    private static final int PROPERTY_y = 51;

    // Property array 
    /*lazy PropertyDescriptor*/
    private static PropertyDescriptor[] getPdescriptor(){
        PropertyDescriptor[] properties = new PropertyDescriptor[52];
    
        try {
            properties[PROPERTY_actionMap] = new PropertyDescriptor ( "actionMap", javax.swing.JIP.class, "getActionMap", "setActionMap" ); // NOI18N
            properties[PROPERTY_alignmentX] = new PropertyDescriptor ( "alignmentX", javax.swing.JIP.class, "getAlignmentX", "setAlignmentX" ); // NOI18N
            properties[PROPERTY_alignmentY] = new PropertyDescriptor ( "alignmentY", javax.swing.JIP.class, "getAlignmentY", "setAlignmentY" ); // NOI18N
            properties[PROPERTY_background] = new PropertyDescriptor ( "background", javax.swing.JIP.class, "getBackground", "setBackground" ); // NOI18N
            properties[PROPERTY_bloc] = new IndexedPropertyDescriptor ( "bloc", javax.swing.JIP.class, null, null, "getBloc", null ); // NOI18N
            properties[PROPERTY_border] = new PropertyDescriptor ( "border", javax.swing.JIP.class, "getBorder", "setBorder" ); // NOI18N
            properties[PROPERTY_bounds] = new PropertyDescriptor ( "bounds", javax.swing.JIP.class, "getBounds", "setBounds" ); // NOI18N
            properties[PROPERTY_caret] = new PropertyDescriptor ( "caret", javax.swing.JIP.class, "getCaret", "setCaret" ); // NOI18N
            properties[PROPERTY_caretColor] = new PropertyDescriptor ( "caretColor", javax.swing.JIP.class, "getCaretColor", "setCaretColor" ); // NOI18N
            properties[PROPERTY_component] = new IndexedPropertyDescriptor ( "component", javax.swing.JIP.class, null, null, "getComponent", null ); // NOI18N
            properties[PROPERTY_componentCount] = new PropertyDescriptor ( "componentCount", javax.swing.JIP.class, "getComponentCount", null ); // NOI18N
            properties[PROPERTY_componentOrientation] = new PropertyDescriptor ( "componentOrientation", javax.swing.JIP.class, "getComponentOrientation", "setComponentOrientation" ); // NOI18N
            properties[PROPERTY_cursor] = new PropertyDescriptor ( "cursor", javax.swing.JIP.class, "getCursor", "setCursor" ); // NOI18N
            properties[PROPERTY_debugGraphicsOptions] = new PropertyDescriptor ( "debugGraphicsOptions", javax.swing.JIP.class, "getDebugGraphicsOptions", "setDebugGraphicsOptions" ); // NOI18N
            properties[PROPERTY_disabledTextColor] = new PropertyDescriptor ( "disabledTextColor", javax.swing.JIP.class, "getDisabledTextColor", "setDisabledTextColor" ); // NOI18N
            properties[PROPERTY_dot] = new IndexedPropertyDescriptor ( "dot", javax.swing.JIP.class, null, null, "getDot", null ); // NOI18N
            properties[PROPERTY_editable] = new PropertyDescriptor ( "editable", javax.swing.JIP.class, "isEditable", "setEditable" ); // NOI18N
            properties[PROPERTY_enabled] = new PropertyDescriptor ( "enabled", javax.swing.JIP.class, "isEnabled", "setEnabled" ); // NOI18N
            properties[PROPERTY_focusable] = new PropertyDescriptor ( "focusable", javax.swing.JIP.class, "isFocusable", "setFocusable" ); // NOI18N
            properties[PROPERTY_focusTraversalKeys] = new IndexedPropertyDescriptor ( "focusTraversalKeys", javax.swing.JIP.class, null, null, null, "setFocusTraversalKeys" ); // NOI18N
            properties[PROPERTY_font] = new PropertyDescriptor ( "font", javax.swing.JIP.class, "getFont", "setFont" ); // NOI18N
            properties[PROPERTY_foreground] = new PropertyDescriptor ( "foreground", javax.swing.JIP.class, "getForeground", "setForeground" ); // NOI18N
            properties[PROPERTY_height] = new PropertyDescriptor ( "height", javax.swing.JIP.class, "getHeight", null ); // NOI18N
            properties[PROPERTY_highlighter] = new PropertyDescriptor ( "highlighter", javax.swing.JIP.class, "getHighlighter", "setHighlighter" ); // NOI18N
            properties[PROPERTY_ignoreRepaint] = new PropertyDescriptor ( "ignoreRepaint", javax.swing.JIP.class, "getIgnoreRepaint", "setIgnoreRepaint" ); // NOI18N
            properties[PROPERTY_inheritsPopupMenu] = new PropertyDescriptor ( "inheritsPopupMenu", javax.swing.JIP.class, "getInheritsPopupMenu", "setInheritsPopupMenu" ); // NOI18N
            properties[PROPERTY_insets] = new PropertyDescriptor ( "insets", javax.swing.JIP.class, "getInsets", null ); // NOI18N
            properties[PROPERTY_layout] = new PropertyDescriptor ( "layout", javax.swing.JIP.class, "getLayout", "setLayout" ); // NOI18N
            properties[PROPERTY_locale] = new PropertyDescriptor ( "locale", javax.swing.JIP.class, "getLocale", "setLocale" ); // NOI18N
            properties[PROPERTY_location] = new PropertyDescriptor ( "location", javax.swing.JIP.class, "getLocation", "setLocation" ); // NOI18N
            properties[PROPERTY_locationOnScreen] = new PropertyDescriptor ( "locationOnScreen", javax.swing.JIP.class, "getLocationOnScreen", null ); // NOI18N
            properties[PROPERTY_maximumSize] = new PropertyDescriptor ( "maximumSize", javax.swing.JIP.class, "getMaximumSize", "setMaximumSize" ); // NOI18N
            properties[PROPERTY_minimumSize] = new PropertyDescriptor ( "minimumSize", javax.swing.JIP.class, "getMinimumSize", "setMinimumSize" ); // NOI18N
            properties[PROPERTY_name] = new PropertyDescriptor ( "name", javax.swing.JIP.class, "getName", "setName" ); // NOI18N
            properties[PROPERTY_nextFocusableComponent] = new PropertyDescriptor ( "nextFocusableComponent", javax.swing.JIP.class, "getNextFocusableComponent", "setNextFocusableComponent" ); // NOI18N
            properties[PROPERTY_opaque] = new PropertyDescriptor ( "opaque", javax.swing.JIP.class, "isOpaque", "setOpaque" ); // NOI18N
            properties[PROPERTY_preferredSize] = new PropertyDescriptor ( "preferredSize", javax.swing.JIP.class, "getPreferredSize", "setPreferredSize" ); // NOI18N
            properties[PROPERTY_requestFocusEnabled] = new PropertyDescriptor ( "requestFocusEnabled", javax.swing.JIP.class, "isRequestFocusEnabled", "setRequestFocusEnabled" ); // NOI18N
            properties[PROPERTY_selectedTextColor] = new PropertyDescriptor ( "selectedTextColor", javax.swing.JIP.class, "getSelectedTextColor", "setSelectedTextColor" ); // NOI18N
            properties[PROPERTY_selectionColor] = new PropertyDescriptor ( "selectionColor", javax.swing.JIP.class, "getSelectionColor", "setSelectionColor" ); // NOI18N
            properties[PROPERTY_showing] = new PropertyDescriptor ( "showing", javax.swing.JIP.class, "isShowing", null ); // NOI18N
            properties[PROPERTY_size] = new PropertyDescriptor ( "size", javax.swing.JIP.class, "getSize", "setSize" ); // NOI18N
            properties[PROPERTY_spacePoints] = new PropertyDescriptor ( "spacePoints", javax.swing.JIP.class, "getSpacePoints", null ); // NOI18N
            properties[PROPERTY_text] = new PropertyDescriptor ( "text", javax.swing.JIP.class, "getText", "setText" ); // NOI18N
            properties[PROPERTY_toolTipText] = new PropertyDescriptor ( "toolTipText", javax.swing.JIP.class, "getToolTipText", "setToolTipText" ); // NOI18N
            properties[PROPERTY_treeLock] = new PropertyDescriptor ( "treeLock", javax.swing.JIP.class, "getTreeLock", null ); // NOI18N
            properties[PROPERTY_verifyInputWhenFocusTarget] = new PropertyDescriptor ( "verifyInputWhenFocusTarget", javax.swing.JIP.class, "getVerifyInputWhenFocusTarget", "setVerifyInputWhenFocusTarget" ); // NOI18N
            properties[PROPERTY_visible] = new PropertyDescriptor ( "visible", javax.swing.JIP.class, "isVisible", "setVisible" ); // NOI18N
            properties[PROPERTY_wheelMouseChangeIp] = new PropertyDescriptor ( "wheelMouseChangeIp", javax.swing.JIP.class, "isWheelMouseChangeIp", "setWheelMouseChangeIp" ); // NOI18N
            properties[PROPERTY_width] = new PropertyDescriptor ( "width", javax.swing.JIP.class, "getWidth", null ); // NOI18N
            properties[PROPERTY_x] = new PropertyDescriptor ( "x", javax.swing.JIP.class, "getX", null ); // NOI18N
            properties[PROPERTY_y] = new PropertyDescriptor ( "y", javax.swing.JIP.class, "getY", null ); // NOI18N
        }
        catch(IntrospectionException e) {
            e.printStackTrace();
        }//GEN-HEADEREND:Properties
        // Here you can add code for customizing the properties array.

        return properties;     }//GEN-LAST:Properties

    // EventSet identifiers//GEN-FIRST:Events
    private static final int EVENT_actionListener = 0;
    private static final int EVENT_ancestorListener = 1;
    private static final int EVENT_caretListener = 2;
    private static final int EVENT_componentListener = 3;
    private static final int EVENT_focusListener = 4;
    private static final int EVENT_hierarchyBoundsListener = 5;
    private static final int EVENT_hierarchyListener = 6;
    private static final int EVENT_keyListener = 7;
    private static final int EVENT_mouseListener = 8;
    private static final int EVENT_mouseMotionListener = 9;
    private static final int EVENT_mouseWheelListener = 10;
    private static final int EVENT_propertyChangeListener = 11;
    private static final int EVENT_vetoableChangeListener = 12;

    // EventSet array
    /*lazy EventSetDescriptor*/
    private static EventSetDescriptor[] getEdescriptor(){
        EventSetDescriptor[] eventSets = new EventSetDescriptor[13];
    
        try {
            eventSets[EVENT_actionListener] = new EventSetDescriptor ( javax.swing.JIP.class, "actionListener", java.awt.event.ActionListener.class, new String[] {"actionPerformed"}, "addActionListener", "removeActionListener" ); // NOI18N
            eventSets[EVENT_ancestorListener] = new EventSetDescriptor ( javax.swing.JIP.class, "ancestorListener", javax.swing.event.AncestorListener.class, new String[] {"ancestorAdded", "ancestorRemoved", "ancestorMoved"}, "addAncestorListener", "removeAncestorListener" ); // NOI18N
            eventSets[EVENT_caretListener] = new EventSetDescriptor ( javax.swing.JIP.class, "caretListener", javax.swing.event.CaretListener.class, new String[] {"caretUpdate"}, "addCaretListener", "removeCaretListener" ); // NOI18N
            eventSets[EVENT_componentListener] = new EventSetDescriptor ( javax.swing.JIP.class, "componentListener", java.awt.event.ComponentListener.class, new String[] {"componentResized", "componentMoved", "componentShown", "componentHidden"}, "addComponentListener", "removeComponentListener" ); // NOI18N
            eventSets[EVENT_focusListener] = new EventSetDescriptor ( javax.swing.JIP.class, "focusListener", java.awt.event.FocusListener.class, new String[] {"focusGained", "focusLost"}, "addFocusListener", "removeFocusListener" ); // NOI18N
            eventSets[EVENT_hierarchyBoundsListener] = new EventSetDescriptor ( javax.swing.JIP.class, "hierarchyBoundsListener", java.awt.event.HierarchyBoundsListener.class, new String[] {"ancestorMoved", "ancestorResized"}, "addHierarchyBoundsListener", "removeHierarchyBoundsListener" ); // NOI18N
            eventSets[EVENT_hierarchyListener] = new EventSetDescriptor ( javax.swing.JIP.class, "hierarchyListener", java.awt.event.HierarchyListener.class, new String[] {"hierarchyChanged"}, "addHierarchyListener", "removeHierarchyListener" ); // NOI18N
            eventSets[EVENT_keyListener] = new EventSetDescriptor ( javax.swing.JIP.class, "keyListener", java.awt.event.KeyListener.class, new String[] {"keyTyped", "keyPressed", "keyReleased"}, "addKeyListener", "removeKeyListener" ); // NOI18N
            eventSets[EVENT_mouseListener] = new EventSetDescriptor ( javax.swing.JIP.class, "mouseListener", java.awt.event.MouseListener.class, new String[] {"mouseClicked", "mousePressed", "mouseReleased", "mouseEntered", "mouseExited"}, "addMouseListener", "removeMouseListener" ); // NOI18N
            eventSets[EVENT_mouseMotionListener] = new EventSetDescriptor ( javax.swing.JIP.class, "mouseMotionListener", java.awt.event.MouseMotionListener.class, new String[] {"mouseDragged", "mouseMoved"}, "addMouseMotionListener", "removeMouseMotionListener" ); // NOI18N
            eventSets[EVENT_mouseWheelListener] = new EventSetDescriptor ( javax.swing.JIP.class, "mouseWheelListener", java.awt.event.MouseWheelListener.class, new String[] {"mouseWheelMoved"}, "addMouseWheelListener", "removeMouseWheelListener" ); // NOI18N
            eventSets[EVENT_propertyChangeListener] = new EventSetDescriptor ( javax.swing.JIP.class, "propertyChangeListener", java.beans.PropertyChangeListener.class, new String[] {"propertyChange"}, "addPropertyChangeListener", "removePropertyChangeListener" ); // NOI18N
            eventSets[EVENT_vetoableChangeListener] = new EventSetDescriptor ( javax.swing.JIP.class, "vetoableChangeListener", java.beans.VetoableChangeListener.class, new String[] {"vetoableChange"}, "addVetoableChangeListener", "removeVetoableChangeListener" ); // NOI18N
        }
        catch(IntrospectionException e) {
            e.printStackTrace();
        }//GEN-HEADEREND:Events
        // Here you can add code for customizing the event sets array.

        return eventSets;     }//GEN-LAST:Events

    // Method identifiers//GEN-FIRST:Methods
    private static final int METHOD_action0 = 0;
    private static final int METHOD_add1 = 1;
    private static final int METHOD_add2 = 2;
    private static final int METHOD_add3 = 3;
    private static final int METHOD_add4 = 4;
    private static final int METHOD_add5 = 5;
    private static final int METHOD_add6 = 6;
    private static final int METHOD_addNotify7 = 7;
    private static final int METHOD_addPropertyChangeListener8 = 8;
    private static final int METHOD_applyComponentOrientation9 = 9;
    private static final int METHOD_areFocusTraversalKeysSet10 = 10;
    private static final int METHOD_bounds11 = 11;
    private static final int METHOD_checkImage12 = 12;
    private static final int METHOD_checkImage13 = 13;
    private static final int METHOD_computeVisibleRect14 = 14;
    private static final int METHOD_contains15 = 15;
    private static final int METHOD_contains16 = 16;
    private static final int METHOD_countComponents17 = 17;
    private static final int METHOD_createImage18 = 18;
    private static final int METHOD_createImage19 = 19;
    private static final int METHOD_createToolTip20 = 20;
    private static final int METHOD_createVolatileImage21 = 21;
    private static final int METHOD_createVolatileImage22 = 22;
    private static final int METHOD_deliverEvent23 = 23;
    private static final int METHOD_disable24 = 24;
    private static final int METHOD_discreaseSpacePoints25 = 25;
    private static final int METHOD_dispatchEvent26 = 26;
    private static final int METHOD_doLayout27 = 27;
    private static final int METHOD_enable28 = 28;
    private static final int METHOD_enable29 = 29;
    private static final int METHOD_enableInputMethods30 = 30;
    private static final int METHOD_findComponentAt31 = 31;
    private static final int METHOD_findComponentAt32 = 32;
    private static final int METHOD_firePropertyChange33 = 33;
    private static final int METHOD_firePropertyChange34 = 34;
    private static final int METHOD_firePropertyChange35 = 35;
    private static final int METHOD_firePropertyChange36 = 36;
    private static final int METHOD_firePropertyChange37 = 37;
    private static final int METHOD_firePropertyChange38 = 38;
    private static final int METHOD_firePropertyChange39 = 39;
    private static final int METHOD_firePropertyChange40 = 40;
    private static final int METHOD_getActionForKeyStroke41 = 41;
    private static final int METHOD_getBaseline42 = 42;
    private static final int METHOD_getBounds43 = 43;
    private static final int METHOD_getClientProperty44 = 44;
    private static final int METHOD_getComponentAt45 = 45;
    private static final int METHOD_getComponentAt46 = 46;
    private static final int METHOD_getComponentZOrder47 = 47;
    private static final int METHOD_getConditionForKeyStroke48 = 48;
    private static final int METHOD_getDefaultLocale49 = 49;
    private static final int METHOD_getFocusTraversalKeys50 = 50;
    private static final int METHOD_getFontMetrics51 = 51;
    private static final int METHOD_getInsets52 = 52;
    private static final int METHOD_getListeners53 = 53;
    private static final int METHOD_getLocation54 = 54;
    private static final int METHOD_getMousePosition55 = 55;
    private static final int METHOD_getPopupLocation56 = 56;
    private static final int METHOD_getPropertyChangeListeners57 = 57;
    private static final int METHOD_getSize58 = 58;
    private static final int METHOD_getText59 = 59;
    private static final int METHOD_getToolTipLocation60 = 60;
    private static final int METHOD_getToolTipText61 = 61;
    private static final int METHOD_gotFocus62 = 62;
    private static final int METHOD_grabFocus63 = 63;
    private static final int METHOD_handleEvent64 = 64;
    private static final int METHOD_hasFocus65 = 65;
    private static final int METHOD_hide66 = 66;
    private static final int METHOD_imageUpdate67 = 67;
    private static final int METHOD_increaseSpacePoints68 = 68;
    private static final int METHOD_insets69 = 69;
    private static final int METHOD_inside70 = 70;
    private static final int METHOD_invalidate71 = 71;
    private static final int METHOD_isAncestorOf72 = 72;
    private static final int METHOD_isFocusCycleRoot73 = 73;
    private static final int METHOD_isLightweightComponent74 = 74;
    private static final int METHOD_keyDown75 = 75;
    private static final int METHOD_keyUp76 = 76;
    private static final int METHOD_layout77 = 77;
    private static final int METHOD_list78 = 78;
    private static final int METHOD_list79 = 79;
    private static final int METHOD_list80 = 80;
    private static final int METHOD_list81 = 81;
    private static final int METHOD_list82 = 82;
    private static final int METHOD_locate83 = 83;
    private static final int METHOD_location84 = 84;
    private static final int METHOD_lostFocus85 = 85;
    private static final int METHOD_minimumSize86 = 86;
    private static final int METHOD_mouseDown87 = 87;
    private static final int METHOD_mouseDrag88 = 88;
    private static final int METHOD_mouseEnter89 = 89;
    private static final int METHOD_mouseExit90 = 90;
    private static final int METHOD_mouseMove91 = 91;
    private static final int METHOD_mouseUp92 = 92;
    private static final int METHOD_move93 = 93;
    private static final int METHOD_nextFocus94 = 94;
    private static final int METHOD_paint95 = 95;
    private static final int METHOD_paintAll96 = 96;
    private static final int METHOD_paintComponents97 = 97;
    private static final int METHOD_paintImmediately98 = 98;
    private static final int METHOD_paintImmediately99 = 99;
    private static final int METHOD_postEvent100 = 100;
    private static final int METHOD_preferredSize101 = 101;
    private static final int METHOD_prepareImage102 = 102;
    private static final int METHOD_prepareImage103 = 103;
    private static final int METHOD_print104 = 104;
    private static final int METHOD_printAll105 = 105;
    private static final int METHOD_printComponents106 = 106;
    private static final int METHOD_putClientProperty107 = 107;
    private static final int METHOD_registerKeyboardAction108 = 108;
    private static final int METHOD_registerKeyboardAction109 = 109;
    private static final int METHOD_remove110 = 110;
    private static final int METHOD_remove111 = 111;
    private static final int METHOD_remove112 = 112;
    private static final int METHOD_removeAll113 = 113;
    private static final int METHOD_removeNotify114 = 114;
    private static final int METHOD_removePropertyChangeListener115 = 115;
    private static final int METHOD_repaint116 = 116;
    private static final int METHOD_repaint117 = 117;
    private static final int METHOD_repaint118 = 118;
    private static final int METHOD_repaint119 = 119;
    private static final int METHOD_repaint120 = 120;
    private static final int METHOD_requestDefaultFocus121 = 121;
    private static final int METHOD_requestFocus122 = 122;
    private static final int METHOD_requestFocus123 = 123;
    private static final int METHOD_requestFocusInWindow124 = 124;
    private static final int METHOD_resetKeyboardActions125 = 125;
    private static final int METHOD_reshape126 = 126;
    private static final int METHOD_resize127 = 127;
    private static final int METHOD_resize128 = 128;
    private static final int METHOD_revalidate129 = 129;
    private static final int METHOD_scrollRectToVisible130 = 130;
    private static final int METHOD_setBounds131 = 131;
    private static final int METHOD_setComponentZOrder132 = 132;
    private static final int METHOD_setDefaultLocale133 = 133;
    private static final int METHOD_show134 = 134;
    private static final int METHOD_show135 = 135;
    private static final int METHOD_size136 = 136;
    private static final int METHOD_toString137 = 137;
    private static final int METHOD_transferFocus138 = 138;
    private static final int METHOD_transferFocusBackward139 = 139;
    private static final int METHOD_transferFocusDownCycle140 = 140;
    private static final int METHOD_transferFocusUpCycle141 = 141;
    private static final int METHOD_unregisterKeyboardAction142 = 142;
    private static final int METHOD_update143 = 143;
    private static final int METHOD_updateUI144 = 144;
    private static final int METHOD_validate145 = 145;

    // Method array 
    /*lazy MethodDescriptor*/
    private static MethodDescriptor[] getMdescriptor(){
        MethodDescriptor[] methods = new MethodDescriptor[146];
    
        try {
            methods[METHOD_action0] = new MethodDescriptor(java.awt.Component.class.getMethod("action", new Class[] {java.awt.Event.class, java.lang.Object.class})); // NOI18N
            methods[METHOD_action0].setDisplayName ( "" );
            methods[METHOD_add1] = new MethodDescriptor(java.awt.Component.class.getMethod("add", new Class[] {java.awt.PopupMenu.class})); // NOI18N
            methods[METHOD_add1].setDisplayName ( "" );
            methods[METHOD_add2] = new MethodDescriptor(java.awt.Container.class.getMethod("add", new Class[] {java.awt.Component.class})); // NOI18N
            methods[METHOD_add2].setDisplayName ( "" );
            methods[METHOD_add3] = new MethodDescriptor(java.awt.Container.class.getMethod("add", new Class[] {java.lang.String.class, java.awt.Component.class})); // NOI18N
            methods[METHOD_add3].setDisplayName ( "" );
            methods[METHOD_add4] = new MethodDescriptor(java.awt.Container.class.getMethod("add", new Class[] {java.awt.Component.class, int.class})); // NOI18N
            methods[METHOD_add4].setDisplayName ( "" );
            methods[METHOD_add5] = new MethodDescriptor(java.awt.Container.class.getMethod("add", new Class[] {java.awt.Component.class, java.lang.Object.class})); // NOI18N
            methods[METHOD_add5].setDisplayName ( "" );
            methods[METHOD_add6] = new MethodDescriptor(java.awt.Container.class.getMethod("add", new Class[] {java.awt.Component.class, java.lang.Object.class, int.class})); // NOI18N
            methods[METHOD_add6].setDisplayName ( "" );
            methods[METHOD_addNotify7] = new MethodDescriptor(javax.swing.JComponent.class.getMethod("addNotify", new Class[] {})); // NOI18N
            methods[METHOD_addNotify7].setDisplayName ( "" );
            methods[METHOD_addPropertyChangeListener8] = new MethodDescriptor(java.awt.Container.class.getMethod("addPropertyChangeListener", new Class[] {java.lang.String.class, java.beans.PropertyChangeListener.class})); // NOI18N
            methods[METHOD_addPropertyChangeListener8].setDisplayName ( "" );
            methods[METHOD_applyComponentOrientation9] = new MethodDescriptor(java.awt.Container.class.getMethod("applyComponentOrientation", new Class[] {java.awt.ComponentOrientation.class})); // NOI18N
            methods[METHOD_applyComponentOrientation9].setDisplayName ( "" );
            methods[METHOD_areFocusTraversalKeysSet10] = new MethodDescriptor(java.awt.Container.class.getMethod("areFocusTraversalKeysSet", new Class[] {int.class})); // NOI18N
            methods[METHOD_areFocusTraversalKeysSet10].setDisplayName ( "" );
            methods[METHOD_bounds11] = new MethodDescriptor(java.awt.Component.class.getMethod("bounds", new Class[] {})); // NOI18N
            methods[METHOD_bounds11].setDisplayName ( "" );
            methods[METHOD_checkImage12] = new MethodDescriptor(java.awt.Component.class.getMethod("checkImage", new Class[] {java.awt.Image.class, java.awt.image.ImageObserver.class})); // NOI18N
            methods[METHOD_checkImage12].setDisplayName ( "" );
            methods[METHOD_checkImage13] = new MethodDescriptor(java.awt.Component.class.getMethod("checkImage", new Class[] {java.awt.Image.class, int.class, int.class, java.awt.image.ImageObserver.class})); // NOI18N
            methods[METHOD_checkImage13].setDisplayName ( "" );
            methods[METHOD_computeVisibleRect14] = new MethodDescriptor(javax.swing.JComponent.class.getMethod("computeVisibleRect", new Class[] {java.awt.Rectangle.class})); // NOI18N
            methods[METHOD_computeVisibleRect14].setDisplayName ( "" );
            methods[METHOD_contains15] = new MethodDescriptor(java.awt.Component.class.getMethod("contains", new Class[] {java.awt.Point.class})); // NOI18N
            methods[METHOD_contains15].setDisplayName ( "" );
            methods[METHOD_contains16] = new MethodDescriptor(javax.swing.JComponent.class.getMethod("contains", new Class[] {int.class, int.class})); // NOI18N
            methods[METHOD_contains16].setDisplayName ( "" );
            methods[METHOD_countComponents17] = new MethodDescriptor(java.awt.Container.class.getMethod("countComponents", new Class[] {})); // NOI18N
            methods[METHOD_countComponents17].setDisplayName ( "" );
            methods[METHOD_createImage18] = new MethodDescriptor(java.awt.Component.class.getMethod("createImage", new Class[] {java.awt.image.ImageProducer.class})); // NOI18N
            methods[METHOD_createImage18].setDisplayName ( "" );
            methods[METHOD_createImage19] = new MethodDescriptor(java.awt.Component.class.getMethod("createImage", new Class[] {int.class, int.class})); // NOI18N
            methods[METHOD_createImage19].setDisplayName ( "" );
            methods[METHOD_createToolTip20] = new MethodDescriptor(javax.swing.JComponent.class.getMethod("createToolTip", new Class[] {})); // NOI18N
            methods[METHOD_createToolTip20].setDisplayName ( "" );
            methods[METHOD_createVolatileImage21] = new MethodDescriptor(java.awt.Component.class.getMethod("createVolatileImage", new Class[] {int.class, int.class})); // NOI18N
            methods[METHOD_createVolatileImage21].setDisplayName ( "" );
            methods[METHOD_createVolatileImage22] = new MethodDescriptor(java.awt.Component.class.getMethod("createVolatileImage", new Class[] {int.class, int.class, java.awt.ImageCapabilities.class})); // NOI18N
            methods[METHOD_createVolatileImage22].setDisplayName ( "" );
            methods[METHOD_deliverEvent23] = new MethodDescriptor(java.awt.Container.class.getMethod("deliverEvent", new Class[] {java.awt.Event.class})); // NOI18N
            methods[METHOD_deliverEvent23].setDisplayName ( "" );
            methods[METHOD_disable24] = new MethodDescriptor(javax.swing.JComponent.class.getMethod("disable", new Class[] {})); // NOI18N
            methods[METHOD_disable24].setDisplayName ( "" );
            methods[METHOD_discreaseSpacePoints25] = new MethodDescriptor(javax.swing.JIP.class.getMethod("discreaseSpacePoints", new Class[] {})); // NOI18N
            methods[METHOD_discreaseSpacePoints25].setDisplayName ( "" );
            methods[METHOD_dispatchEvent26] = new MethodDescriptor(java.awt.Component.class.getMethod("dispatchEvent", new Class[] {java.awt.AWTEvent.class})); // NOI18N
            methods[METHOD_dispatchEvent26].setDisplayName ( "" );
            methods[METHOD_doLayout27] = new MethodDescriptor(java.awt.Container.class.getMethod("doLayout", new Class[] {})); // NOI18N
            methods[METHOD_doLayout27].setDisplayName ( "" );
            methods[METHOD_enable28] = new MethodDescriptor(java.awt.Component.class.getMethod("enable", new Class[] {boolean.class})); // NOI18N
            methods[METHOD_enable28].setDisplayName ( "" );
            methods[METHOD_enable29] = new MethodDescriptor(javax.swing.JComponent.class.getMethod("enable", new Class[] {})); // NOI18N
            methods[METHOD_enable29].setDisplayName ( "" );
            methods[METHOD_enableInputMethods30] = new MethodDescriptor(java.awt.Component.class.getMethod("enableInputMethods", new Class[] {boolean.class})); // NOI18N
            methods[METHOD_enableInputMethods30].setDisplayName ( "" );
            methods[METHOD_findComponentAt31] = new MethodDescriptor(java.awt.Container.class.getMethod("findComponentAt", new Class[] {int.class, int.class})); // NOI18N
            methods[METHOD_findComponentAt31].setDisplayName ( "" );
            methods[METHOD_findComponentAt32] = new MethodDescriptor(java.awt.Container.class.getMethod("findComponentAt", new Class[] {java.awt.Point.class})); // NOI18N
            methods[METHOD_findComponentAt32].setDisplayName ( "" );
            methods[METHOD_firePropertyChange33] = new MethodDescriptor(java.awt.Component.class.getMethod("firePropertyChange", new Class[] {java.lang.String.class, byte.class, byte.class})); // NOI18N
            methods[METHOD_firePropertyChange33].setDisplayName ( "" );
            methods[METHOD_firePropertyChange34] = new MethodDescriptor(java.awt.Component.class.getMethod("firePropertyChange", new Class[] {java.lang.String.class, short.class, short.class})); // NOI18N
            methods[METHOD_firePropertyChange34].setDisplayName ( "" );
            methods[METHOD_firePropertyChange35] = new MethodDescriptor(java.awt.Component.class.getMethod("firePropertyChange", new Class[] {java.lang.String.class, long.class, long.class})); // NOI18N
            methods[METHOD_firePropertyChange35].setDisplayName ( "" );
            methods[METHOD_firePropertyChange36] = new MethodDescriptor(java.awt.Component.class.getMethod("firePropertyChange", new Class[] {java.lang.String.class, float.class, float.class})); // NOI18N
            methods[METHOD_firePropertyChange36].setDisplayName ( "" );
            methods[METHOD_firePropertyChange37] = new MethodDescriptor(java.awt.Component.class.getMethod("firePropertyChange", new Class[] {java.lang.String.class, double.class, double.class})); // NOI18N
            methods[METHOD_firePropertyChange37].setDisplayName ( "" );
            methods[METHOD_firePropertyChange38] = new MethodDescriptor(javax.swing.JComponent.class.getMethod("firePropertyChange", new Class[] {java.lang.String.class, boolean.class, boolean.class})); // NOI18N
            methods[METHOD_firePropertyChange38].setDisplayName ( "" );
            methods[METHOD_firePropertyChange39] = new MethodDescriptor(javax.swing.JComponent.class.getMethod("firePropertyChange", new Class[] {java.lang.String.class, int.class, int.class})); // NOI18N
            methods[METHOD_firePropertyChange39].setDisplayName ( "" );
            methods[METHOD_firePropertyChange40] = new MethodDescriptor(javax.swing.JComponent.class.getMethod("firePropertyChange", new Class[] {java.lang.String.class, char.class, char.class})); // NOI18N
            methods[METHOD_firePropertyChange40].setDisplayName ( "" );
            methods[METHOD_getActionForKeyStroke41] = new MethodDescriptor(javax.swing.JComponent.class.getMethod("getActionForKeyStroke", new Class[] {javax.swing.KeyStroke.class})); // NOI18N
            methods[METHOD_getActionForKeyStroke41].setDisplayName ( "" );
            methods[METHOD_getBaseline42] = new MethodDescriptor(javax.swing.JComponent.class.getMethod("getBaseline", new Class[] {int.class, int.class})); // NOI18N
            methods[METHOD_getBaseline42].setDisplayName ( "" );
            methods[METHOD_getBounds43] = new MethodDescriptor(javax.swing.JComponent.class.getMethod("getBounds", new Class[] {java.awt.Rectangle.class})); // NOI18N
            methods[METHOD_getBounds43].setDisplayName ( "" );
            methods[METHOD_getClientProperty44] = new MethodDescriptor(javax.swing.JComponent.class.getMethod("getClientProperty", new Class[] {java.lang.Object.class})); // NOI18N
            methods[METHOD_getClientProperty44].setDisplayName ( "" );
            methods[METHOD_getComponentAt45] = new MethodDescriptor(java.awt.Container.class.getMethod("getComponentAt", new Class[] {int.class, int.class})); // NOI18N
            methods[METHOD_getComponentAt45].setDisplayName ( "" );
            methods[METHOD_getComponentAt46] = new MethodDescriptor(java.awt.Container.class.getMethod("getComponentAt", new Class[] {java.awt.Point.class})); // NOI18N
            methods[METHOD_getComponentAt46].setDisplayName ( "" );
            methods[METHOD_getComponentZOrder47] = new MethodDescriptor(java.awt.Container.class.getMethod("getComponentZOrder", new Class[] {java.awt.Component.class})); // NOI18N
            methods[METHOD_getComponentZOrder47].setDisplayName ( "" );
            methods[METHOD_getConditionForKeyStroke48] = new MethodDescriptor(javax.swing.JComponent.class.getMethod("getConditionForKeyStroke", new Class[] {javax.swing.KeyStroke.class})); // NOI18N
            methods[METHOD_getConditionForKeyStroke48].setDisplayName ( "" );
            methods[METHOD_getDefaultLocale49] = new MethodDescriptor(javax.swing.JComponent.class.getMethod("getDefaultLocale", new Class[] {})); // NOI18N
            methods[METHOD_getDefaultLocale49].setDisplayName ( "" );
            methods[METHOD_getFocusTraversalKeys50] = new MethodDescriptor(java.awt.Container.class.getMethod("getFocusTraversalKeys", new Class[] {int.class})); // NOI18N
            methods[METHOD_getFocusTraversalKeys50].setDisplayName ( "" );
            methods[METHOD_getFontMetrics51] = new MethodDescriptor(javax.swing.JComponent.class.getMethod("getFontMetrics", new Class[] {java.awt.Font.class})); // NOI18N
            methods[METHOD_getFontMetrics51].setDisplayName ( "" );
            methods[METHOD_getInsets52] = new MethodDescriptor(javax.swing.JComponent.class.getMethod("getInsets", new Class[] {java.awt.Insets.class})); // NOI18N
            methods[METHOD_getInsets52].setDisplayName ( "" );
            methods[METHOD_getListeners53] = new MethodDescriptor(javax.swing.JComponent.class.getMethod("getListeners", new Class[] {java.lang.Class.class})); // NOI18N
            methods[METHOD_getListeners53].setDisplayName ( "" );
            methods[METHOD_getLocation54] = new MethodDescriptor(javax.swing.JComponent.class.getMethod("getLocation", new Class[] {java.awt.Point.class})); // NOI18N
            methods[METHOD_getLocation54].setDisplayName ( "" );
            methods[METHOD_getMousePosition55] = new MethodDescriptor(java.awt.Container.class.getMethod("getMousePosition", new Class[] {boolean.class})); // NOI18N
            methods[METHOD_getMousePosition55].setDisplayName ( "" );
            methods[METHOD_getPopupLocation56] = new MethodDescriptor(javax.swing.JComponent.class.getMethod("getPopupLocation", new Class[] {java.awt.event.MouseEvent.class})); // NOI18N
            methods[METHOD_getPopupLocation56].setDisplayName ( "" );
            methods[METHOD_getPropertyChangeListeners57] = new MethodDescriptor(java.awt.Component.class.getMethod("getPropertyChangeListeners", new Class[] {java.lang.String.class})); // NOI18N
            methods[METHOD_getPropertyChangeListeners57].setDisplayName ( "" );
            methods[METHOD_getSize58] = new MethodDescriptor(javax.swing.JComponent.class.getMethod("getSize", new Class[] {java.awt.Dimension.class})); // NOI18N
            methods[METHOD_getSize58].setDisplayName ( "" );
            methods[METHOD_getText59] = new MethodDescriptor(javax.swing.JIP.class.getMethod("getText", new Class[] {int.class, int.class})); // NOI18N
            methods[METHOD_getText59].setDisplayName ( "" );
            methods[METHOD_getToolTipLocation60] = new MethodDescriptor(javax.swing.JComponent.class.getMethod("getToolTipLocation", new Class[] {java.awt.event.MouseEvent.class})); // NOI18N
            methods[METHOD_getToolTipLocation60].setDisplayName ( "" );
            methods[METHOD_getToolTipText61] = new MethodDescriptor(javax.swing.JComponent.class.getMethod("getToolTipText", new Class[] {java.awt.event.MouseEvent.class})); // NOI18N
            methods[METHOD_getToolTipText61].setDisplayName ( "" );
            methods[METHOD_gotFocus62] = new MethodDescriptor(java.awt.Component.class.getMethod("gotFocus", new Class[] {java.awt.Event.class, java.lang.Object.class})); // NOI18N
            methods[METHOD_gotFocus62].setDisplayName ( "" );
            methods[METHOD_grabFocus63] = new MethodDescriptor(javax.swing.JComponent.class.getMethod("grabFocus", new Class[] {})); // NOI18N
            methods[METHOD_grabFocus63].setDisplayName ( "" );
            methods[METHOD_handleEvent64] = new MethodDescriptor(java.awt.Component.class.getMethod("handleEvent", new Class[] {java.awt.Event.class})); // NOI18N
            methods[METHOD_handleEvent64].setDisplayName ( "" );
            methods[METHOD_hasFocus65] = new MethodDescriptor(javax.swing.JIP.class.getMethod("hasFocus", new Class[] {})); // NOI18N
            methods[METHOD_hasFocus65].setDisplayName ( "" );
            methods[METHOD_hide66] = new MethodDescriptor(javax.swing.JComponent.class.getMethod("hide", new Class[] {})); // NOI18N
            methods[METHOD_hide66].setDisplayName ( "" );
            methods[METHOD_imageUpdate67] = new MethodDescriptor(java.awt.Component.class.getMethod("imageUpdate", new Class[] {java.awt.Image.class, int.class, int.class, int.class, int.class, int.class})); // NOI18N
            methods[METHOD_imageUpdate67].setDisplayName ( "" );
            methods[METHOD_increaseSpacePoints68] = new MethodDescriptor(javax.swing.JIP.class.getMethod("increaseSpacePoints", new Class[] {})); // NOI18N
            methods[METHOD_increaseSpacePoints68].setDisplayName ( "" );
            methods[METHOD_insets69] = new MethodDescriptor(java.awt.Container.class.getMethod("insets", new Class[] {})); // NOI18N
            methods[METHOD_insets69].setDisplayName ( "" );
            methods[METHOD_inside70] = new MethodDescriptor(java.awt.Component.class.getMethod("inside", new Class[] {int.class, int.class})); // NOI18N
            methods[METHOD_inside70].setDisplayName ( "" );
            methods[METHOD_invalidate71] = new MethodDescriptor(java.awt.Container.class.getMethod("invalidate", new Class[] {})); // NOI18N
            methods[METHOD_invalidate71].setDisplayName ( "" );
            methods[METHOD_isAncestorOf72] = new MethodDescriptor(java.awt.Container.class.getMethod("isAncestorOf", new Class[] {java.awt.Component.class})); // NOI18N
            methods[METHOD_isAncestorOf72].setDisplayName ( "" );
            methods[METHOD_isFocusCycleRoot73] = new MethodDescriptor(java.awt.Container.class.getMethod("isFocusCycleRoot", new Class[] {java.awt.Container.class})); // NOI18N
            methods[METHOD_isFocusCycleRoot73].setDisplayName ( "" );
            methods[METHOD_isLightweightComponent74] = new MethodDescriptor(javax.swing.JComponent.class.getMethod("isLightweightComponent", new Class[] {java.awt.Component.class})); // NOI18N
            methods[METHOD_isLightweightComponent74].setDisplayName ( "" );
            methods[METHOD_keyDown75] = new MethodDescriptor(java.awt.Component.class.getMethod("keyDown", new Class[] {java.awt.Event.class, int.class})); // NOI18N
            methods[METHOD_keyDown75].setDisplayName ( "" );
            methods[METHOD_keyUp76] = new MethodDescriptor(java.awt.Component.class.getMethod("keyUp", new Class[] {java.awt.Event.class, int.class})); // NOI18N
            methods[METHOD_keyUp76].setDisplayName ( "" );
            methods[METHOD_layout77] = new MethodDescriptor(java.awt.Container.class.getMethod("layout", new Class[] {})); // NOI18N
            methods[METHOD_layout77].setDisplayName ( "" );
            methods[METHOD_list78] = new MethodDescriptor(java.awt.Component.class.getMethod("list", new Class[] {})); // NOI18N
            methods[METHOD_list78].setDisplayName ( "" );
            methods[METHOD_list79] = new MethodDescriptor(java.awt.Component.class.getMethod("list", new Class[] {java.io.PrintStream.class})); // NOI18N
            methods[METHOD_list79].setDisplayName ( "" );
            methods[METHOD_list80] = new MethodDescriptor(java.awt.Component.class.getMethod("list", new Class[] {java.io.PrintWriter.class})); // NOI18N
            methods[METHOD_list80].setDisplayName ( "" );
            methods[METHOD_list81] = new MethodDescriptor(java.awt.Container.class.getMethod("list", new Class[] {java.io.PrintStream.class, int.class})); // NOI18N
            methods[METHOD_list81].setDisplayName ( "" );
            methods[METHOD_list82] = new MethodDescriptor(java.awt.Container.class.getMethod("list", new Class[] {java.io.PrintWriter.class, int.class})); // NOI18N
            methods[METHOD_list82].setDisplayName ( "" );
            methods[METHOD_locate83] = new MethodDescriptor(java.awt.Container.class.getMethod("locate", new Class[] {int.class, int.class})); // NOI18N
            methods[METHOD_locate83].setDisplayName ( "" );
            methods[METHOD_location84] = new MethodDescriptor(java.awt.Component.class.getMethod("location", new Class[] {})); // NOI18N
            methods[METHOD_location84].setDisplayName ( "" );
            methods[METHOD_lostFocus85] = new MethodDescriptor(java.awt.Component.class.getMethod("lostFocus", new Class[] {java.awt.Event.class, java.lang.Object.class})); // NOI18N
            methods[METHOD_lostFocus85].setDisplayName ( "" );
            methods[METHOD_minimumSize86] = new MethodDescriptor(java.awt.Container.class.getMethod("minimumSize", new Class[] {})); // NOI18N
            methods[METHOD_minimumSize86].setDisplayName ( "" );
            methods[METHOD_mouseDown87] = new MethodDescriptor(java.awt.Component.class.getMethod("mouseDown", new Class[] {java.awt.Event.class, int.class, int.class})); // NOI18N
            methods[METHOD_mouseDown87].setDisplayName ( "" );
            methods[METHOD_mouseDrag88] = new MethodDescriptor(java.awt.Component.class.getMethod("mouseDrag", new Class[] {java.awt.Event.class, int.class, int.class})); // NOI18N
            methods[METHOD_mouseDrag88].setDisplayName ( "" );
            methods[METHOD_mouseEnter89] = new MethodDescriptor(java.awt.Component.class.getMethod("mouseEnter", new Class[] {java.awt.Event.class, int.class, int.class})); // NOI18N
            methods[METHOD_mouseEnter89].setDisplayName ( "" );
            methods[METHOD_mouseExit90] = new MethodDescriptor(java.awt.Component.class.getMethod("mouseExit", new Class[] {java.awt.Event.class, int.class, int.class})); // NOI18N
            methods[METHOD_mouseExit90].setDisplayName ( "" );
            methods[METHOD_mouseMove91] = new MethodDescriptor(java.awt.Component.class.getMethod("mouseMove", new Class[] {java.awt.Event.class, int.class, int.class})); // NOI18N
            methods[METHOD_mouseMove91].setDisplayName ( "" );
            methods[METHOD_mouseUp92] = new MethodDescriptor(java.awt.Component.class.getMethod("mouseUp", new Class[] {java.awt.Event.class, int.class, int.class})); // NOI18N
            methods[METHOD_mouseUp92].setDisplayName ( "" );
            methods[METHOD_move93] = new MethodDescriptor(java.awt.Component.class.getMethod("move", new Class[] {int.class, int.class})); // NOI18N
            methods[METHOD_move93].setDisplayName ( "" );
            methods[METHOD_nextFocus94] = new MethodDescriptor(java.awt.Component.class.getMethod("nextFocus", new Class[] {})); // NOI18N
            methods[METHOD_nextFocus94].setDisplayName ( "" );
            methods[METHOD_paint95] = new MethodDescriptor(javax.swing.JComponent.class.getMethod("paint", new Class[] {java.awt.Graphics.class})); // NOI18N
            methods[METHOD_paint95].setDisplayName ( "" );
            methods[METHOD_paintAll96] = new MethodDescriptor(java.awt.Component.class.getMethod("paintAll", new Class[] {java.awt.Graphics.class})); // NOI18N
            methods[METHOD_paintAll96].setDisplayName ( "" );
            methods[METHOD_paintComponents97] = new MethodDescriptor(java.awt.Container.class.getMethod("paintComponents", new Class[] {java.awt.Graphics.class})); // NOI18N
            methods[METHOD_paintComponents97].setDisplayName ( "" );
            methods[METHOD_paintImmediately98] = new MethodDescriptor(javax.swing.JComponent.class.getMethod("paintImmediately", new Class[] {int.class, int.class, int.class, int.class})); // NOI18N
            methods[METHOD_paintImmediately98].setDisplayName ( "" );
            methods[METHOD_paintImmediately99] = new MethodDescriptor(javax.swing.JComponent.class.getMethod("paintImmediately", new Class[] {java.awt.Rectangle.class})); // NOI18N
            methods[METHOD_paintImmediately99].setDisplayName ( "" );
            methods[METHOD_postEvent100] = new MethodDescriptor(java.awt.Component.class.getMethod("postEvent", new Class[] {java.awt.Event.class})); // NOI18N
            methods[METHOD_postEvent100].setDisplayName ( "" );
            methods[METHOD_preferredSize101] = new MethodDescriptor(java.awt.Container.class.getMethod("preferredSize", new Class[] {})); // NOI18N
            methods[METHOD_preferredSize101].setDisplayName ( "" );
            methods[METHOD_prepareImage102] = new MethodDescriptor(java.awt.Component.class.getMethod("prepareImage", new Class[] {java.awt.Image.class, java.awt.image.ImageObserver.class})); // NOI18N
            methods[METHOD_prepareImage102].setDisplayName ( "" );
            methods[METHOD_prepareImage103] = new MethodDescriptor(java.awt.Component.class.getMethod("prepareImage", new Class[] {java.awt.Image.class, int.class, int.class, java.awt.image.ImageObserver.class})); // NOI18N
            methods[METHOD_prepareImage103].setDisplayName ( "" );
            methods[METHOD_print104] = new MethodDescriptor(javax.swing.JComponent.class.getMethod("print", new Class[] {java.awt.Graphics.class})); // NOI18N
            methods[METHOD_print104].setDisplayName ( "" );
            methods[METHOD_printAll105] = new MethodDescriptor(javax.swing.JComponent.class.getMethod("printAll", new Class[] {java.awt.Graphics.class})); // NOI18N
            methods[METHOD_printAll105].setDisplayName ( "" );
            methods[METHOD_printComponents106] = new MethodDescriptor(java.awt.Container.class.getMethod("printComponents", new Class[] {java.awt.Graphics.class})); // NOI18N
            methods[METHOD_printComponents106].setDisplayName ( "" );
            methods[METHOD_putClientProperty107] = new MethodDescriptor(javax.swing.JComponent.class.getMethod("putClientProperty", new Class[] {java.lang.Object.class, java.lang.Object.class})); // NOI18N
            methods[METHOD_putClientProperty107].setDisplayName ( "" );
            methods[METHOD_registerKeyboardAction108] = new MethodDescriptor(javax.swing.JComponent.class.getMethod("registerKeyboardAction", new Class[] {java.awt.event.ActionListener.class, java.lang.String.class, javax.swing.KeyStroke.class, int.class})); // NOI18N
            methods[METHOD_registerKeyboardAction108].setDisplayName ( "" );
            methods[METHOD_registerKeyboardAction109] = new MethodDescriptor(javax.swing.JComponent.class.getMethod("registerKeyboardAction", new Class[] {java.awt.event.ActionListener.class, javax.swing.KeyStroke.class, int.class})); // NOI18N
            methods[METHOD_registerKeyboardAction109].setDisplayName ( "" );
            methods[METHOD_remove110] = new MethodDescriptor(java.awt.Component.class.getMethod("remove", new Class[] {java.awt.MenuComponent.class})); // NOI18N
            methods[METHOD_remove110].setDisplayName ( "" );
            methods[METHOD_remove111] = new MethodDescriptor(java.awt.Container.class.getMethod("remove", new Class[] {int.class})); // NOI18N
            methods[METHOD_remove111].setDisplayName ( "" );
            methods[METHOD_remove112] = new MethodDescriptor(java.awt.Container.class.getMethod("remove", new Class[] {java.awt.Component.class})); // NOI18N
            methods[METHOD_remove112].setDisplayName ( "" );
            methods[METHOD_removeAll113] = new MethodDescriptor(java.awt.Container.class.getMethod("removeAll", new Class[] {})); // NOI18N
            methods[METHOD_removeAll113].setDisplayName ( "" );
            methods[METHOD_removeNotify114] = new MethodDescriptor(javax.swing.JComponent.class.getMethod("removeNotify", new Class[] {})); // NOI18N
            methods[METHOD_removeNotify114].setDisplayName ( "" );
            methods[METHOD_removePropertyChangeListener115] = new MethodDescriptor(java.awt.Component.class.getMethod("removePropertyChangeListener", new Class[] {java.lang.String.class, java.beans.PropertyChangeListener.class})); // NOI18N
            methods[METHOD_removePropertyChangeListener115].setDisplayName ( "" );
            methods[METHOD_repaint116] = new MethodDescriptor(java.awt.Component.class.getMethod("repaint", new Class[] {})); // NOI18N
            methods[METHOD_repaint116].setDisplayName ( "" );
            methods[METHOD_repaint117] = new MethodDescriptor(java.awt.Component.class.getMethod("repaint", new Class[] {long.class})); // NOI18N
            methods[METHOD_repaint117].setDisplayName ( "" );
            methods[METHOD_repaint118] = new MethodDescriptor(java.awt.Component.class.getMethod("repaint", new Class[] {int.class, int.class, int.class, int.class})); // NOI18N
            methods[METHOD_repaint118].setDisplayName ( "" );
            methods[METHOD_repaint119] = new MethodDescriptor(javax.swing.JComponent.class.getMethod("repaint", new Class[] {long.class, int.class, int.class, int.class, int.class})); // NOI18N
            methods[METHOD_repaint119].setDisplayName ( "" );
            methods[METHOD_repaint120] = new MethodDescriptor(javax.swing.JComponent.class.getMethod("repaint", new Class[] {java.awt.Rectangle.class})); // NOI18N
            methods[METHOD_repaint120].setDisplayName ( "" );
            methods[METHOD_requestDefaultFocus121] = new MethodDescriptor(javax.swing.JComponent.class.getMethod("requestDefaultFocus", new Class[] {})); // NOI18N
            methods[METHOD_requestDefaultFocus121].setDisplayName ( "" );
            methods[METHOD_requestFocus122] = new MethodDescriptor(javax.swing.JIP.class.getMethod("requestFocus", new Class[] {boolean.class})); // NOI18N
            methods[METHOD_requestFocus122].setDisplayName ( "" );
            methods[METHOD_requestFocus123] = new MethodDescriptor(javax.swing.JIP.class.getMethod("requestFocus", new Class[] {})); // NOI18N
            methods[METHOD_requestFocus123].setDisplayName ( "" );
            methods[METHOD_requestFocusInWindow124] = new MethodDescriptor(javax.swing.JIP.class.getMethod("requestFocusInWindow", new Class[] {})); // NOI18N
            methods[METHOD_requestFocusInWindow124].setDisplayName ( "" );
            methods[METHOD_resetKeyboardActions125] = new MethodDescriptor(javax.swing.JComponent.class.getMethod("resetKeyboardActions", new Class[] {})); // NOI18N
            methods[METHOD_resetKeyboardActions125].setDisplayName ( "" );
            methods[METHOD_reshape126] = new MethodDescriptor(javax.swing.JComponent.class.getMethod("reshape", new Class[] {int.class, int.class, int.class, int.class})); // NOI18N
            methods[METHOD_reshape126].setDisplayName ( "" );
            methods[METHOD_resize127] = new MethodDescriptor(java.awt.Component.class.getMethod("resize", new Class[] {int.class, int.class})); // NOI18N
            methods[METHOD_resize127].setDisplayName ( "" );
            methods[METHOD_resize128] = new MethodDescriptor(java.awt.Component.class.getMethod("resize", new Class[] {java.awt.Dimension.class})); // NOI18N
            methods[METHOD_resize128].setDisplayName ( "" );
            methods[METHOD_revalidate129] = new MethodDescriptor(javax.swing.JComponent.class.getMethod("revalidate", new Class[] {})); // NOI18N
            methods[METHOD_revalidate129].setDisplayName ( "" );
            methods[METHOD_scrollRectToVisible130] = new MethodDescriptor(javax.swing.JComponent.class.getMethod("scrollRectToVisible", new Class[] {java.awt.Rectangle.class})); // NOI18N
            methods[METHOD_scrollRectToVisible130].setDisplayName ( "" );
            methods[METHOD_setBounds131] = new MethodDescriptor(java.awt.Component.class.getMethod("setBounds", new Class[] {int.class, int.class, int.class, int.class})); // NOI18N
            methods[METHOD_setBounds131].setDisplayName ( "" );
            methods[METHOD_setComponentZOrder132] = new MethodDescriptor(java.awt.Container.class.getMethod("setComponentZOrder", new Class[] {java.awt.Component.class, int.class})); // NOI18N
            methods[METHOD_setComponentZOrder132].setDisplayName ( "" );
            methods[METHOD_setDefaultLocale133] = new MethodDescriptor(javax.swing.JComponent.class.getMethod("setDefaultLocale", new Class[] {java.util.Locale.class})); // NOI18N
            methods[METHOD_setDefaultLocale133].setDisplayName ( "" );
            methods[METHOD_show134] = new MethodDescriptor(java.awt.Component.class.getMethod("show", new Class[] {})); // NOI18N
            methods[METHOD_show134].setDisplayName ( "" );
            methods[METHOD_show135] = new MethodDescriptor(java.awt.Component.class.getMethod("show", new Class[] {boolean.class})); // NOI18N
            methods[METHOD_show135].setDisplayName ( "" );
            methods[METHOD_size136] = new MethodDescriptor(java.awt.Component.class.getMethod("size", new Class[] {})); // NOI18N
            methods[METHOD_size136].setDisplayName ( "" );
            methods[METHOD_toString137] = new MethodDescriptor(java.awt.Component.class.getMethod("toString", new Class[] {})); // NOI18N
            methods[METHOD_toString137].setDisplayName ( "" );
            methods[METHOD_transferFocus138] = new MethodDescriptor(java.awt.Component.class.getMethod("transferFocus", new Class[] {})); // NOI18N
            methods[METHOD_transferFocus138].setDisplayName ( "" );
            methods[METHOD_transferFocusBackward139] = new MethodDescriptor(java.awt.Component.class.getMethod("transferFocusBackward", new Class[] {})); // NOI18N
            methods[METHOD_transferFocusBackward139].setDisplayName ( "" );
            methods[METHOD_transferFocusDownCycle140] = new MethodDescriptor(java.awt.Container.class.getMethod("transferFocusDownCycle", new Class[] {})); // NOI18N
            methods[METHOD_transferFocusDownCycle140].setDisplayName ( "" );
            methods[METHOD_transferFocusUpCycle141] = new MethodDescriptor(java.awt.Component.class.getMethod("transferFocusUpCycle", new Class[] {})); // NOI18N
            methods[METHOD_transferFocusUpCycle141].setDisplayName ( "" );
            methods[METHOD_unregisterKeyboardAction142] = new MethodDescriptor(javax.swing.JComponent.class.getMethod("unregisterKeyboardAction", new Class[] {javax.swing.KeyStroke.class})); // NOI18N
            methods[METHOD_unregisterKeyboardAction142].setDisplayName ( "" );
            methods[METHOD_update143] = new MethodDescriptor(javax.swing.JComponent.class.getMethod("update", new Class[] {java.awt.Graphics.class})); // NOI18N
            methods[METHOD_update143].setDisplayName ( "" );
            methods[METHOD_updateUI144] = new MethodDescriptor(javax.swing.JPanel.class.getMethod("updateUI", new Class[] {})); // NOI18N
            methods[METHOD_updateUI144].setDisplayName ( "" );
            methods[METHOD_validate145] = new MethodDescriptor(java.awt.Container.class.getMethod("validate", new Class[] {})); // NOI18N
            methods[METHOD_validate145].setDisplayName ( "" );
        }
        catch( Exception e) {}//GEN-HEADEREND:Methods
        // Here you can add code for customizing the methods array.

        return methods;     }//GEN-LAST:Methods

    private static java.awt.Image iconColor16 = null;//GEN-BEGIN:IconsDef
    private static java.awt.Image iconColor32 = null;
    private static java.awt.Image iconMono16 = null;
    private static java.awt.Image iconMono32 = null;//GEN-END:IconsDef
    private static String iconNameC16 = "/javax/swing/JIP_c16.png";//GEN-BEGIN:Icons
    private static String iconNameC32 = "/javax/swing/JIP_c32.png";
    private static String iconNameM16 = "/javax/swing/JIP_m16.png";
    private static String iconNameM32 = "/javax/swing/JIP_m32.png";//GEN-END:Icons

    private static final int defaultPropertyIndex = -1;//GEN-BEGIN:Idx
    private static final int defaultEventIndex = -1;//GEN-END:Idx


//GEN-FIRST:Superclass
    // Here you can add code for customizing the Superclass BeanInfo.

//GEN-LAST:Superclass
    /**
     * Gets the bean's <code>BeanDescriptor</code>s.
     *
     * @return BeanDescriptor describing the editable properties of this bean.
     * May return null if the information should be obtained by automatic
     * analysis.
     */
    @Override
    public BeanDescriptor getBeanDescriptor() {
        return getBdescriptor();
    }

    /**
     * Gets the bean's <code>PropertyDescriptor</code>s.
     *
     * @return An array of PropertyDescriptors describing the editable
     * properties supported by this bean. May return null if the information
     * should be obtained by automatic analysis.
     * <p>
     * If a property is indexed, then its entry in the result array will belong
     * to the IndexedPropertyDescriptor subclass of PropertyDescriptor. A client
     * of getPropertyDescriptors can use "instanceof" to check if a given
     * PropertyDescriptor is an IndexedPropertyDescriptor.
     */
    @Override
    public PropertyDescriptor[] getPropertyDescriptors() {
        return getPdescriptor();
    }

    /**
     * Gets the bean's <code>EventSetDescriptor</code>s.
     *
     * @return An array of EventSetDescriptors describing the kinds of events
     * fired by this bean. May return null if the information should be obtained
     * by automatic analysis.
     */
    @Override
    public EventSetDescriptor[] getEventSetDescriptors() {
        return getEdescriptor();
    }

    /**
     * Gets the bean's <code>MethodDescriptor</code>s.
     *
     * @return An array of MethodDescriptors describing the methods implemented
     * by this bean. May return null if the information should be obtained by
     * automatic analysis.
     */
    @Override
    public MethodDescriptor[] getMethodDescriptors() {
        return getMdescriptor();
    }

    /**
     * A bean may have a "default" property that is the property that will
     * mostly commonly be initially chosen for update by human's who are
     * customizing the bean.
     *
     * @return Index of default property in the PropertyDescriptor array
     * returned by getPropertyDescriptors.
     * <P>
     * Returns -1 if there is no default property.
     */
    @Override
    public int getDefaultPropertyIndex() {
        return defaultPropertyIndex;
    }

    /**
     * A bean may have a "default" event that is the event that will mostly
     * commonly be used by human's when using the bean.
     *
     * @return Index of default event in the EventSetDescriptor array returned
     * by getEventSetDescriptors.
     * <P>
     * Returns -1 if there is no default event.
     */
    @Override
    public int getDefaultEventIndex() {
        return defaultEventIndex;
    }

    /**
     * This method returns an image object that can be used to represent the
     * bean in toolboxes, toolbars, etc. Icon images will typically be GIFs, but
     * may in future include other formats.
     * <p>
     * Beans aren't required to provide icons and may return null from this
     * method.
     * <p>
     * There are four possible flavors of icons (16x16 color, 32x32 color, 16x16
     * mono, 32x32 mono). If a bean choses to only support a single icon we
     * recommend supporting 16x16 color.
     * <p>
     * We recommend that icons have a "transparent" background so they can be
     * rendered onto an existing background.
     *
     * @param iconKind The kind of icon requested. This should be one of the
     * constant values ICON_COLOR_16x16, ICON_COLOR_32x32, ICON_MONO_16x16, or
     * ICON_MONO_32x32.
     * @return An image object representing the requested icon. May return null
     * if no suitable icon is available.
     */
    @Override
    public java.awt.Image getIcon(int iconKind) {
        switch (iconKind) {
            case ICON_COLOR_16x16:
                if (iconNameC16 == null) {
                    return null;
                } else {
                    if (iconColor16 == null) {
                        iconColor16 = loadImage(iconNameC16);
                    }
                    return iconColor16;
                }
            case ICON_COLOR_32x32:
                if (iconNameC32 == null) {
                    return null;
                } else {
                    if (iconColor32 == null) {
                        iconColor32 = loadImage(iconNameC32);
                    }
                    return iconColor32;
                }
            case ICON_MONO_16x16:
                if (iconNameM16 == null) {
                    return null;
                } else {
                    if (iconMono16 == null) {
                        iconMono16 = loadImage(iconNameM16);
                    }
                    return iconMono16;
                }
            case ICON_MONO_32x32:
                if (iconNameM32 == null) {
                    return null;
                } else {
                    if (iconMono32 == null) {
                        iconMono32 = loadImage(iconNameM32);
                    }
                    return iconMono32;
                }
            default:
                return null;
        }
    }
    
}
