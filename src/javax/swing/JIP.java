/*
 * Copyright (C) JasonPercus Systems, Inc - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by JasonPercus, 05/2021
 */
package javax.swing;



import com.jasonpercus.network.IPv4;



/**
 * Cette classe représente un composant Swing permettant à l'utilisateur de saisir une adresse {@link IPv4}
 * @see IPv4
 * @author JasonPercus
 * @version 1.0
 */
public class JIP extends javax.swing.JPanel {
    
    
    
//ATTRIBUTS PRIVATES
    /**
     * Correspond au premier bloc de l'adresse IP
     */
    private javax.swing.JTextField bloc1;
    
    /**
     * Correspond au second bloc de l'adresse IP
     */
    private javax.swing.JTextField bloc2;
    
    /**
     * Correspond au troisième bloc de l'adresse IP
     */
    private javax.swing.JTextField bloc3;
    
    /**
     * Correspond au quatrième bloc de l'adresse IP
     */
    private javax.swing.JTextField bloc4;
    
    /**
     * Correspond au premier point de l'adresse IP
     */
    private javax.swing.JLabel point1;
    
    /**
     * Correspond au second point de l'adresse IP
     */
    private javax.swing.JLabel point2;
    
    /**
     * Correspond au troisième point de l'adresse IP
     */
    private javax.swing.JLabel point3;
    
    /**
     * Correspond à la liste des ActionListeners
     */
    private final java.util.List<java.awt.event.ActionListener> actionListeners;
    
    /**
     * Correspond à la liste des CaretListeners
     */
    private final java.util.List<javax.swing.event.CaretListener> caretListeners;
    
    /**
     * Correspond à la liste des FocusListeners
     */
    private final java.util.List<java.awt.event.FocusListener> focusListeners;
    
    /**
     * Correspond à la liste des KeyListeners
     */
    private final java.util.List<java.awt.event.KeyListener> keyListeners;
    
    /**
     * Correspond à la liste des MouseListeners
     */
    private final java.util.List<java.awt.event.MouseListener> mouseListeners;
    
    /**
     * Correspond à la liste des MouseMotionListeners
     */
    private final java.util.List<java.awt.event.MouseMotionListener> mouseMotionListeners;
    
    /**
     * Correspond à la liste des MouseWheelListeners
     */
    private final java.util.List<java.awt.event.MouseWheelListener> mouseMouseWheelListeners;
    
    /**
     * Correspond à la liste des PropertyChangeListeners
     */
    private final java.util.List<java.beans.PropertyChangeListener> propertyChangeListeners;
    
    /**
     * Correspond à la liste des VetoableChangeListeners
     */
    private final java.util.List<java.beans.VetoableChangeListener> vetoableChangeListeners;
    
    /**
     * Correspond à la liste des blocs IP du JIP
     */
    private javax.swing.JTextField[] blocs;
    
    /**
     * Correspond à la liste des points entre les blocs IP du JIP
     */
    private javax.swing.JLabel[] points;
    
    /**
     * Correspond à la liste des composants du JIP
     */
    private javax.swing.JComponent[] components;
    
    /**
     * Correspond au tooltiptext du JIP
     */
    private String toolTipText;
    
    /**
     * Correspond au nouvel objet qui a le focus
     */
    private Object newFocus;
    
    /**
     * Détermine si le composant qui vient de perdre le focus, c'est ce JIP (autrement dit l'un de ses sous-composants)
     */
    private boolean oldFocusIsMe = false;
    
    /**
     * Détermine si la souris se trouve à l'intérieur du composant
     */
    private boolean entered = false;
    
    /**
     * Détermine si le roulette de la souris à le droit de modifier les valeurs des blocs ip
     */
    private boolean wheelMouseChangeIp = false;
    
    /**
     * Cette variable (temporairement mis à true) permet de placer le caret à une position donnée sans que lors du focus sur un sous-composant du JIP, son texte se sélectionne complètement
     */
    private boolean focusNotSelect = false;
    
    /**
     * Correspond à la position où se trouvera le caret lors du focus sur le sous-composant (cette variable va de pair avec {@link #focusNotSelect}
     */
    private int newCaretPosition = 0;
    
    
    
//CONSTRUCTORS
    /**
     * Crée un JIP (l'adresse ip du base du JIP est 0.0.0.0)
     */
    public JIP() {
        this(new IPv4("0.0.0.0"));
    }
    
    /**
     * Crée un JIP
     * @param ip Correspond à l'adresse ip de base du JIP
     */
    public JIP(String ip){
        this(new IPv4(ip));
    }
    
    /**
     * Crée un JIP
     * @param ip Correspond à l'adresse ip de base du JIP
     */
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public JIP(IPv4 ip){
        super();
        this.actionListeners = new java.util.ArrayList<>();
        this.caretListeners = new java.util.ArrayList<>();
        this.focusListeners = new java.util.ArrayList<>();
        this.keyListeners = new java.util.ArrayList<>();
        this.mouseListeners = new java.util.ArrayList<>();
        this.mouseMotionListeners = new java.util.ArrayList<>();
        this.mouseMouseWheelListeners = new java.util.ArrayList<>();
        this.propertyChangeListeners = new java.util.ArrayList<>();
        this.vetoableChangeListeners = new java.util.ArrayList<>();
        init();
        this.setIP(ip);
    }

    
    
//METHODES PUBLICS
    /**
     * Détermine si la roulette de la souris peut modifier les valeurs des blocs de l'adresse IP
     * @return Retourne true, si elle le peut, sinon false
     */
    public boolean isWheelMouseChangeIp() {
        return wheelMouseChangeIp;
    }

    /**
     * Détermine si la roulette de la souris peut modifier les valeurs des blocs de l'adresse IP
     * @param wheelMouseChangeIp True = La roulette pourra modifier les valeurs. False = La roulette ne pourra pas modifier les valeurs
     */
    public void setWheelMouseChangeIp(boolean wheelMouseChangeIp) {
        this.wheelMouseChangeIp = wheelMouseChangeIp;
    }
    
    /**
     * Renvoie un composant bloc de l'adresse IP
     * @param index Correspond à l'indice du bloc à récupérer [0; 3] (car il y a 4 nombres compris entre 0 et 255 dans une adresse IP)
     * @return Retourne le composant bloc de l'adresse IP
     */
    public javax.swing.JTextField getBloc(int index){
        switch(index){
            case 0:
                return bloc1;
            case 1:
                return bloc2;
            case 2:
                return bloc3;
            case 3:
                return bloc4;
            default:
                return null;
        }
    }
    
    /**
     * Renvoie un composant point de l'adresse IP
     * @param index Correspond à l'indice du point à récupérer [0; 2] (car il y a 3 points dans une adresse IP)
     * @return Retourne le composant point de l'adresse IP
     */
    public javax.swing.JLabel getDot(int index){
        switch(index){
            case 0:
                return point1;
            case 1:
                return point2;
            case 2:
                return point3;
            default:
                return null;
        }
    }
    
    /**
     * Renvoie le nombre total n d'espaces (n/2[à gauche].n/2[à droite]) autour des points
     * @return Retourne le nombre total d'espaces
     */
    public int getSpacePoints(){
        int left = point1.getText().length()/2;
        return left * 2;
    }
    
    /**
     * Augmente d'un espace de chaque côté de chaque points
     */
    public void increaseSpacePoints(){
        setSpacePoints(getSpacePoints()+2);
    }
    
    /**
     * Diminue d'un espace de chaque côté de chaque points
     */
    public void discreaseSpacePoints(){
        int count = getSpacePoints();
        if(count<=1){
            point1.setText(".");
            point2.setText(".");
            point3.setText(".");
        }else{
            setSpacePoints(count-2);
        }
    }
    
    /**
     * Renvoie le texte du JIP
     * @return Retourne le texte du JIP
     */
    public String getText(){
        return getIP().toString();
    }

    /**
     * Renvoie un sous-texte du JIP
     * @param offs Correspond à l'index de départ du sous-texte
     * @param len Correspond à la longueur du sous-texte
     * @return Retourne le sous-texte du JIP
     * @throws javax.swing.text.BadLocationException Si offs ou len n'est pas valide
     */
    public String getText(int offs, int len) throws javax.swing.text.BadLocationException {
        if(offs<len && offs>-1 && len>-1){
            return getText().substring(offs, offs+len);
        }else{
            throw new javax.swing.text.BadLocationException("offs or len is incorrect !", offs);
        }
    }
    
    /**
     * Modifie le texte du JIP
     * @param text Correspond au nouveau texte du JIP
     */
    public void setText(String text){
        setIP(new IPv4(text));
    }
    
    /**
     * Renvoie l'adresse IP du JIP
     * @return Retourne l'adresse IP du JIP
     */
    public IPv4 getIP(){
        if(bloc1.getText().isEmpty())
            bloc1.setText("0");
        if(bloc2.getText().isEmpty())
            bloc2.setText("0");
        if(bloc3.getText().isEmpty())
            bloc3.setText("0");
        if(bloc4.getText().isEmpty())
            bloc4.setText("0");
        return new IPv4(String.format("%s.%s.%s.%s", bloc1.getText(), bloc2.getText(), bloc3.getText(), bloc4.getText()));
    }
    
    /**
     * Modifie l'adresse IP du JIP
     * @param ip Correspond à la nouvelle adresse IP du JIP
     */
    public void setIP(IPv4 ip){
        this.bloc1.setText(""+ip.getBloc(0));
        this.bloc2.setText(""+ip.getBloc(1));
        this.bloc3.setText(""+ip.getBloc(2));
        this.bloc4.setText(""+ip.getBloc(3));
    }
    
    /**
     * Renvoie la police de caractères du JIP
     * @return Retourne la police de caractères du JIP
     */
    @Override
    public java.awt.Font getFont(){
        if(bloc1 == null)
            return super.getFont();
        else
            return bloc1.getFont();
    }
    
    /**
     * Modifie la police de caractères du JIP
     * @param f Correspond à la nouvelle police de caractères
     */
    @Override
    public void setFont(java.awt.Font f) {
        super.setFont(f);
        if(bloc1 != null)
            bloc1.setFont(f);
        if(bloc2 != null)
            bloc2.setFont(f);
        if(bloc3 != null)
            bloc3.setFont(f);
        if(bloc4 != null)
            bloc4.setFont(f);
        if(point1 != null)
            point1.setFont(f);
        if(point2 != null)
            point2.setFont(f);
        if(point3 != null)
            point3.setFont(f);
    }
    
    /**
     * Renvoie le caret du JIP
     * @return Retourne le caret du JIP
     */
    public javax.swing.text.Caret getCaret() {
        return bloc1.getCaret();
    }
    
    /**
     * Modifie le caret du JIP
     * @param c Correspond au nouveau caret du JIP
     */
    public void setCaret(javax.swing.text.Caret c) {
        bloc1.setCaret(c);
        bloc2.setCaret(c);
        bloc3.setCaret(c);
        bloc4.setCaret(c);
    }
    
    /**
     * Renvoie l'objet qui permet de marquer le fond avec des zones colorées
     * @return Retourne l'objet qui permet de marquer le fond avec des zones colorées
     */
    public javax.swing.text.Highlighter getHighlighter() {
        return bloc1.getHighlighter();
    }
    
    /**
     * Modifie l'objet qui permet de marquer le fond avec des zones colorées
     * @param h Correspond au nouvel objet qui permet de marquer le fond avec des zones colorées
     */
    public void setHighlighter(javax.swing.text.Highlighter h) {
        bloc1.setHighlighter(h);
        bloc2.setHighlighter(h);
        bloc3.setHighlighter(h);
        bloc4.setHighlighter(h);
    }
    
    /**
     * Renvoie la couleur du caret
     * @return Retourne la couleur du caret
     */
    public java.awt.Color getCaretColor() {
        return bloc1.getCaretColor();
    }

    /**
     * Modifie la couleur du caret
     * @param c Correspond à la nouvelle couleur du caret
     */
    public void setCaretColor(java.awt.Color c) {
        bloc1.setCaretColor(c);
        bloc2.setCaretColor(c);
        bloc3.setCaretColor(c);
        bloc4.setCaretColor(c);
    }

    /**
     * Renvoie la couleur de la sélection
     * @return Retourne la couleur de la sélection
     */
    public java.awt.Color getSelectionColor() {
        return bloc1.getSelectionColor();
    }

    /**
     * Modifie la couleur de la sélection
     * @param c Correspond à la nouvelle couleur de la sélection
     */
    public void setSelectionColor(java.awt.Color c) {
        bloc1.setSelectionColor(c);
        bloc2.setSelectionColor(c);
        bloc3.setSelectionColor(c);
        bloc4.setSelectionColor(c);
    }

    /**
     * Renvoie la couleur du texte sélectionnée
     * @return Renvoie la couleur du texte sélectionnée
     */
    public java.awt.Color getSelectedTextColor() {
        return bloc1.getSelectedTextColor();
    }

    /**
     * Modifie la couleur du texte sélectionnée
     * @param c Correspond à la nouvelle couleur du texte sélectionnée
     */
    public void setSelectedTextColor(java.awt.Color c) {
        bloc1.setSelectedTextColor(c);
        bloc2.setSelectedTextColor(c);
        bloc3.setSelectedTextColor(c);
        bloc4.setSelectedTextColor(c);
    }

    /**
     * Renvoie la couleur du texte lorsque celui-ci est désactivé
     * @return Retourne la couleur du texte lorsque celui-ci est désactivé
     */
    public java.awt.Color getDisabledTextColor() {
        return bloc1.getDisabledTextColor();
    }

    /**
     * Modifie la couleur du texte lorsque celui-ci est désactivé
     * @param c Correspond à la nouvelle couleur du texte lorsque celui-ci est désactivé
     */
    public void setDisabledTextColor(java.awt.Color c) {
        bloc1.setDisabledTextColor(c);
        bloc2.setDisabledTextColor(c);
        bloc3.setDisabledTextColor(c);
        bloc4.setDisabledTextColor(c);
    }
    
    /**
     * renvoie le texte sélectionné
     * @return Retourne le texte sélectionné
     */
    public String getSelectedText() {
        if(bloc1.hasFocus())
            return bloc1.getSelectedText();
        if(bloc2.hasFocus())
            return bloc2.getSelectedText();
        if(bloc3.hasFocus())
            return bloc3.getSelectedText();
        if(bloc4.hasFocus())
            return bloc4.getSelectedText();
        return null;
    }
    
    /**
     * Renvoie si oui ou non le JIP est éditable ou pas
     * @return Retourne true s'il l'est, sinon false
     */
    public boolean isEditable() {
        return bloc1.isEditable();
    }
    
    /**
     * Modifie le JIP pour qu'il soit éditable ou pas
     * @param b True = il doit devenir éditable. False, il ne doit pas devenir éditable
     */
    public void setEditable(boolean b) {
        bloc1.setEditable(b);
        bloc2.setEditable(b);
        bloc3.setEditable(b);
        bloc4.setEditable(b);
    }

    /**
     * Renvoie le tooltiptext du JIP
     * @return Retourne le tooltiptext du JIP
     */
    @Override
    public String getToolTipText() {
        return toolTipText;
    }

    /**
     * Modifie le tooltiptext du JIP
     * @param text Correspond au nouveau texte du tooltiptext
     */
    @Override
    public void setToolTipText(String text) {
        super.setToolTipText(text);
        this.toolTipText = text;
        if(bloc1 != null)
            bloc1.setToolTipText(text);
        if(bloc2 != null)
            bloc2.setToolTipText(text);
        if(bloc3 != null)
            bloc3.setToolTipText(text);
        if(bloc4 != null)
            bloc4.setToolTipText(text);
        if(point1 != null)
            point1.setToolTipText(text);
        if(point2 != null)
            point2.setToolTipText(text);
        if(point3 != null)
            point3.setToolTipText(text);
    }

    /**
     * Modifie la couleur du background du JIP
     * @param bg Correspond à la nouvelle couleur du background
     */
    @Override
    public void setBackground(java.awt.Color bg) {
        super.setBackground(bg);
        if(bloc1 != null)
            bloc1.setBackground(bg);
        if(bloc2 != null)
            bloc2.setBackground(bg);
        if(bloc3 != null)
            bloc3.setBackground(bg);
        if(bloc4 != null)
            bloc4.setBackground(bg);
        if(point1 != null)
            point1.setBackground(bg);
        if(point2 != null)
            point2.setBackground(bg);
        if(point3 != null)
            point3.setBackground(bg);
    }
    
    /**
     * Renvoie la couleur du texte du JIP
     * @return Retourne la couleur du texte du JIP
     */
    @Override
    public java.awt.Color getForeground() {
        return super.getForeground();
    }

    /**
     * Modifie la couleur du texte du JIP
     * @param fg Correspond à la nouvelle couleur du texte
     */
    @Override
    public void setForeground(java.awt.Color fg) {
        super.setForeground(fg);
        if(bloc1 != null)
            bloc1.setForeground(fg);
        if(bloc2 != null)
            bloc2.setForeground(fg);
        if(bloc3 != null)
            bloc3.setForeground(fg);
        if(bloc4 != null)
            bloc4.setForeground(fg);
        if(point1 != null)
            point1.setForeground(fg);
        if(point2 != null)
            point2.setForeground(fg);
        if(point3 != null)
            point3.setForeground(fg);
    }

    /**
     * Active ou désactive le JIP
     * @param enabled True = active. False = désactive
     */
    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        if(bloc1 != null)
            bloc1.setEnabled(enabled);
        if(bloc2 != null)
            bloc2.setEnabled(enabled);
        if(bloc3 != null)
            bloc3.setEnabled(enabled);
        if(bloc4 != null)
            bloc4.setEnabled(enabled);
        if(point1 != null)
            point1.setEnabled(enabled);
        if(point2 != null)
            point2.setEnabled(enabled);
        if(point3 != null)
            point3.setEnabled(enabled);
    }
    
    /**
     * Détermine si le JIP à la focus
     * @return Retourne true s'il a le focus, sinon false
     */
    @Override
    public boolean hasFocus(){
        if(bloc1 != null && bloc2 != null && bloc3 != null && bloc4 != null){
            return bloc1.hasFocus() || bloc2.hasFocus() || bloc3.hasFocus() || bloc4.hasFocus();
        }else
            return super.hasFocus();
    }

    /**
     * Requests that this <code>Component</code> gets the input focus.
     * Refer to {@link java.awt.Component#requestFocusInWindow()
     * Component.requestFocusInWindow()} for a complete description of
     * this method.
     * <p>
     * If you would like more information on focus, see
     * <a href="https://docs.oracle.com/javase/tutorial/uiswing/misc/focus.html">
     * How to Use the Focus Subsystem</a>,
     * a section in <em>The Java Tutorial</em>.
     *
     * @return <code>false</code> if the focus change request is guaranteed to
     *         fail; <code>true</code> if it is likely to succeed
     * @see java.awt.Component#requestFocusInWindow()
     * @see java.awt.Component#requestFocusInWindow(boolean)
     * @since 1.4
     */
    @Override
    public boolean requestFocusInWindow() {
        return bloc1.requestFocusInWindow(); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Requests that this <code>Component</code> gets the input focus.
     * Refer to {@link java.awt.Component#requestFocus(boolean)
     * Component.requestFocus(boolean)} for a complete description of
     * this method.
     * <p>
     * Note that the use of this method is discouraged because
     * its behavior is platform dependent. Instead we recommend the
     * use of {@link #requestFocusInWindow(boolean)
     * requestFocusInWindow(boolean)}.
     * If you would like more information on focus, see
     * <a href="https://docs.oracle.com/javase/tutorial/uiswing/misc/focus.html">
     * How to Use the Focus Subsystem</a>,
     * a section in <em>The Java Tutorial</em>.
     *
     * @param temporary boolean indicating if the focus change is temporary
     * @return <code>false</code> if the focus change request is guaranteed to
     *         fail; <code>true</code> if it is likely to succeed
     * @see java.awt.Component#requestFocusInWindow()
     * @see java.awt.Component#requestFocusInWindow(boolean)
     * @since 1.4
     */
    @Override
    public boolean requestFocus(boolean temporary) {
        return bloc1.requestFocus(temporary); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Requests that this <code>Component</code> gets the input focus.
     * Refer to {@link java.awt.Component#requestFocus()
     * Component.requestFocus()} for a complete description of
     * this method.
     * <p>
     * Note that the use of this method is discouraged because
     * its behavior is platform dependent. Instead we recommend the
     * use of {@link #requestFocusInWindow() requestFocusInWindow()}.
     * If you would like more information on focus, see
     * <a href="https://docs.oracle.com/javase/tutorial/uiswing/misc/focus.html">
     * How to Use the Focus Subsystem</a>,
     * a section in <em>The Java Tutorial</em>.
     *
     * @see java.awt.Component#requestFocusInWindow()
     * @see java.awt.Component#requestFocusInWindow(boolean)
     * @since 1.4
     */
    @Override
    public void requestFocus() {
        if(!hasFocus())
            bloc1.requestFocus(); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Renvoie le curseur du JIP
     * @return Retourne le curseur du JIP
     */
    @Override
    public java.awt.Cursor getCursor() {
        return bloc1.getCursor();
    }

    /**
     * Modifie le curseur du JIP
     * @param cursor Correspond au nouveau curseur du JIP
     */
    @Override
    public void setCursor(java.awt.Cursor cursor) {
        super.setCursor(cursor);
        if(bloc1 != null)
            bloc1.setCursor(cursor);
        if(bloc2 != null)
            bloc2.setCursor(cursor);
        if(bloc3 != null)
            bloc3.setCursor(cursor);
        if(bloc4 != null)
            bloc4.setCursor(cursor);
    }

    /**
     * Ajoute un ActionListener au JIP
     * @param listener Correspond à l'ActionListener à ajouter
     */
    public synchronized void addActionListener(java.awt.event.ActionListener listener) {
        if(listener != null && actionListeners != null && !actionListeners.contains(listener)){
            actionListeners.add(listener);
        }
    }

    /**
     * Supprime un ActionListener du JIP
     * @param listener Correspond à l'ActionListener à supprimer
     */
    public synchronized void removeActionListener(java.awt.event.ActionListener listener) {
        if(listener != null && actionListeners != null && actionListeners.contains(listener)){
            actionListeners.remove(listener);
        }
    }

    /**
     * Renvoie un tableau des ActionListener du JIP
     * @return Retourne un tableau des ActionListener du JIP
     */
    public synchronized java.awt.event.ActionListener[] getActionListeners() {
        if(actionListeners != null){
            java.awt.event.ActionListener[] actions = new java.awt.event.ActionListener[actionListeners.size()];
            for(int i=0;i<actions.length;i++){
                actions[i] = actionListeners.get(i);
            }
            return actions;
        } else return new java.awt.event.ActionListener[0];
    }

    /**
     * Ajoute un CaretListener au JIP
     * @param listener Correspond au CaretListener à ajouter
     */
    public synchronized void addCaretListener(javax.swing.event.CaretListener listener) {
        if(listener != null && caretListeners != null && !caretListeners.contains(listener)){
            caretListeners.add(listener);
        }
    }

    /**
     * Supprime un CaretListener du JIP
     * @param listener Correspond au CaretListener à supprimer
     */
    public synchronized void removeCaretListener(javax.swing.event.CaretListener listener) {
        if(listener != null && caretListeners != null && caretListeners.contains(listener)){
            caretListeners.remove(listener);
        }
    }

    /**
     * Renvoie un tableau des CaretListener du JIP
     * @return Retourne un tableau des CaretListener du JIP
     */
    public synchronized javax.swing.event.CaretListener[] getCaretListeners() {
        if(caretListeners != null){
            javax.swing.event.CaretListener[] actions = new javax.swing.event.CaretListener[caretListeners.size()];
            for(int i=0;i<actions.length;i++){
                actions[i] = caretListeners.get(i);
            }
            return actions;
        }else return new javax.swing.event.CaretListener[0];
    }
    
    /**
     * Ajoute un ContainerListener au JIP
     * @param listener Correspond au ContainerListener à ajouter
     * @deprecated <div style="color: #D45B5B; font-style: italic">NE PAS UTILISER</div>
     */
    @Override
    public synchronized final void addContainerListener(java.awt.event.ContainerListener listener){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    /**
     * Supprime un ContainerListener du JIP
     * @param listener Correspond au ContainerListener à supprimer
     * @deprecated <div style="color: #D45B5B; font-style: italic">NE PAS UTILISER</div>
     */
    @Override
    public synchronized final void removeContainerListener(java.awt.event.ContainerListener listener){
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Renvoie un tableau des ContainerListener du JIP
     * @return Retourne un tableau des ContainerListener du JIP
     * @deprecated <div style="color: #D45B5B; font-style: italic">NE PAS UTILISER</div>
     */
    @Override
    public synchronized java.awt.event.ContainerListener[] getContainerListeners() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Ajoute un FocusListener au JIP
     * @param listener Correspond au FocusListener à ajouter
     */
    @Override
    public synchronized void addFocusListener(java.awt.event.FocusListener listener) {
        if(listener != null && focusListeners != null && !focusListeners.contains(listener)){
            focusListeners.add(listener);
        }
    }

    /**
     * Supprime un FocusListener du JIP
     * @param listener Correspond au FocusListener à supprimer
     */
    @Override
    public synchronized void removeFocusListener(java.awt.event.FocusListener listener) {
        if(listener != null && focusListeners != null && focusListeners.contains(listener)){
            focusListeners.remove(listener);
        }
    }

    /**
     * Renvoie un tableau des CaretListener du JIP
     * @return Retourne un tableau des CaretListener du JIP
     */
    @Override
    public synchronized java.awt.event.FocusListener[] getFocusListeners() {
        if(focusListeners != null){
            java.awt.event.FocusListener[] focus = new java.awt.event.FocusListener[focusListeners.size()];
            for(int i=0;i<focus.length;i++){
                focus[i] = focusListeners.get(i);
            }
            return focus;
        }else return new java.awt.event.FocusListener[0];
    }

    /**
     * Ajoute un InputMethodListener au JIP
     * @param listener Correspond au InputMethodListener à ajouter
     * @deprecated <div style="color: #D45B5B; font-style: italic">NE PAS UTILISER</div>
     */
    @Override
    public synchronized void addInputMethodListener(java.awt.event.InputMethodListener listener) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Supprime un InputMethodListener du JIP
     * @param listener Correspond au InputMethodListener à supprimer
     * @deprecated <div style="color: #D45B5B; font-style: italic">NE PAS UTILISER</div>
     */
    @Override
    public synchronized void removeInputMethodListener(java.awt.event.InputMethodListener listener) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Renvoie un tableau des InputMethodListener du JIP
     * @return Retourne un tableau des InputMethodListener du JIP
     * @deprecated <div style="color: #D45B5B; font-style: italic">NE PAS UTILISER</div>
     */
    @Override
    public synchronized java.awt.event.InputMethodListener[] getInputMethodListeners() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Ajoute un KeyListener au JIP
     * @param listener Correspond au KeyListener à ajouter
     */
    @Override
    public synchronized void addKeyListener(java.awt.event.KeyListener listener) {
        if(listener != null && keyListeners != null && !keyListeners.contains(listener)){
            keyListeners.add(listener);
        }
    }

    /**
     * Supprime un KeyListener du JIP
     * @param listener Correspond au KeyListener à supprimer
     */
    @Override
    public synchronized void removeKeyListener(java.awt.event.KeyListener listener) {
        if(listener != null && keyListeners != null && keyListeners.contains(listener)){
            keyListeners.remove(listener);
        }
    }

    /**
     * Renvoie un tableau des KeyListener du JIP
     * @return Retourne un tableau des KeyListener du JIP
     */
    @Override
    public synchronized java.awt.event.KeyListener[] getKeyListeners() {
        if(keyListeners != null){
            java.awt.event.KeyListener[] key = new java.awt.event.KeyListener[keyListeners.size()];
            for(int i=0;i<key.length;i++){
                key[i] = keyListeners.get(i);
            }
            return key;
        }else return new java.awt.event.KeyListener[0];
    }

    /**
     * Ajoute un MouseListener au JIP
     * @param listener Correspond au MouseListener à ajouter
     */
    @Override
    public synchronized void addMouseListener(java.awt.event.MouseListener listener) {
        if(listener != null && mouseListeners != null && !mouseListeners.contains(listener)){
            mouseListeners.add(listener);
        }
    }

    /**
     * Supprime un MouseListener du JIP
     * @param listener Correspond au MouseListener à supprimer
     */
    @Override
    public synchronized void removeMouseListener(java.awt.event.MouseListener listener) {
        if(listener != null && mouseListeners != null && mouseListeners.contains(listener)){
            mouseListeners.remove(listener);
        }
    }

    /**
     * Renvoie un tableau des MouseListener du JIP
     * @return Retourne un tableau des MouseListener du JIP
     */
    @Override
    public synchronized java.awt.event.MouseListener[] getMouseListeners() {
        if(mouseListeners != null){
            java.awt.event.MouseListener[] mouse = new java.awt.event.MouseListener[mouseListeners.size()];
            for(int i=0;i<mouse.length;i++){
                mouse[i] = mouseListeners.get(i);
            }
            return mouse;
        }else return new java.awt.event.MouseListener[0];
    }

    /**
     * Ajoute un MouseMotionListener au JIP
     * @param listener Correspond au MouseMotionListener à ajouter
     */
    @Override
    public synchronized void addMouseMotionListener(java.awt.event.MouseMotionListener listener) {
        if(listener != null && mouseMotionListeners != null && !mouseMotionListeners.contains(listener)){
            mouseMotionListeners.add(listener);
        }
    }

    /**
     * Supprime un MouseMotionListener du JIP
     * @param listener Correspond au MouseMotionListener à supprimer
     */
    @Override
    public synchronized void removeMouseMotionListener(java.awt.event.MouseMotionListener listener) {
        if(listener != null && mouseMotionListeners != null && mouseMotionListeners.contains(listener)){
            mouseMotionListeners.remove(listener);
        }
    }

    /**
     * Renvoie un tableau des MouseMotionListener du JIP
     * @return Retourne un tableau des MouseMotionListener du JIP
     */
    @Override
    public synchronized java.awt.event.MouseMotionListener[] getMouseMotionListeners() {
        if(mouseMotionListeners != null){
        java.awt.event.MouseMotionListener[] mouse = new java.awt.event.MouseMotionListener[mouseMotionListeners.size()];
        for(int i=0;i<mouse.length;i++){
            mouse[i] = mouseMotionListeners.get(i);
        }
        return mouse;
        }else return new java.awt.event.MouseMotionListener[0];
    }

    /**
     * Ajoute un MouseWheelListener au JIP
     * @param listener Correspond au MouseWheelListener à ajouter
     */
    @Override
    public synchronized void addMouseWheelListener(java.awt.event.MouseWheelListener listener) {
        if(listener != null && mouseMouseWheelListeners != null && !mouseMouseWheelListeners.contains(listener)){
            mouseMouseWheelListeners.add(listener);
        }
    }

    /**
     * Supprime un MouseWheelListener du JIP
     * @param listener Correspond au MouseWheelListener à supprimer
     */
    @Override
    public synchronized void removeMouseWheelListener(java.awt.event.MouseWheelListener listener) {
        if(listener != null && mouseMouseWheelListeners != null && mouseMouseWheelListeners.contains(listener)){
            mouseMouseWheelListeners.remove(listener);
        }
    }

    /**
     * Renvoie un tableau des MouseWheelListener du JIP
     * @return Retourne un tableau des MouseWheelListener du JIP
     */
    @Override
    public synchronized java.awt.event.MouseWheelListener[] getMouseWheelListeners() {
        if(mouseMouseWheelListeners != null){
            java.awt.event.MouseWheelListener[] mouse = new java.awt.event.MouseWheelListener[mouseMouseWheelListeners.size()];
            for(int i=0;i<mouse.length;i++){
                mouse[i] = mouseMouseWheelListeners.get(i);
            }
            return mouse;
        }else return new java.awt.event.MouseWheelListener[0];
    }

    /**
     * Ajoute un PropertyChangeListener au JIP
     * @param listener Correspond au PropertyChangeListener à ajouter
     */
    @Override
    public synchronized void addPropertyChangeListener(java.beans.PropertyChangeListener listener) {
        if(listener != null && propertyChangeListeners != null && !propertyChangeListeners.contains(listener)){
            propertyChangeListeners.add(listener);
        }
    }

    /**
     * Supprime un PropertyChangeListener du JIP
     * @param listener Correspond au PropertyChangeListener à supprimer
     */
    @Override
    public synchronized void removePropertyChangeListener(java.beans.PropertyChangeListener listener) {
        if(listener != null && propertyChangeListeners != null && propertyChangeListeners.contains(listener)){
            propertyChangeListeners.remove(listener);
        }
    }

    /**
     * Renvoie un tableau des PropertyChangeListener du JIP
     * @return Retourne un tableau des PropertyChangeListener du JIP
     */
    @Override
    public synchronized java.beans.PropertyChangeListener[] getPropertyChangeListeners() {
        if(propertyChangeListeners != null){
            java.beans.PropertyChangeListener[] property = new java.beans.PropertyChangeListener[propertyChangeListeners.size()];
            for(int i=0;i<property.length;i++){
                property[i] = propertyChangeListeners.get(i);
            }
            return property;
        }else return new java.beans.PropertyChangeListener[0];
    }

    /**
     * Ajoute un VetoableChangeListener au JIP
     * @param listener Correspond au VetoableChangeListener à ajouter
     */
    @Override
    public synchronized void addVetoableChangeListener(java.beans.VetoableChangeListener listener) {
        if(listener != null && vetoableChangeListeners != null && !vetoableChangeListeners.contains(listener)){
            vetoableChangeListeners.add(listener);
        }
    }

    /**
     * Supprime un VetoableChangeListener du JIP
     * @param listener Correspond au VetoableChangeListener à supprimer
     */
    @Override
    public synchronized void removeVetoableChangeListener(java.beans.VetoableChangeListener listener) {
        if(listener != null && vetoableChangeListeners != null && vetoableChangeListeners.contains(listener)){
            vetoableChangeListeners.remove(listener);
        }
    }

    /**
     * Renvoie un tableau des VetoableChangeListener du JIP
     * @return Retourne un tableau des VetoableChangeListener du JIP
     */
    @Override
    public synchronized java.beans.VetoableChangeListener[] getVetoableChangeListeners() {
        if(vetoableChangeListeners != null){
            java.beans.VetoableChangeListener[] veto = new java.beans.VetoableChangeListener[vetoableChangeListeners.size()];
            for(int i=0;i<veto.length;i++){
                veto[i] = vetoableChangeListeners.get(i);
            }
            return veto;
        }else return new java.beans.VetoableChangeListener[0];
    }
    
    
    
//METHODES PRIVATES
    /**
     * Initialise le composant
     */
    @SuppressWarnings({"unchecked", "Convert2Lambda"})
    private void init() {
        //CREATING
        this.bloc1      = new javax.swing.JTextField();
        this.point1     = new javax.swing.JLabel();
        this.bloc2      = new javax.swing.JTextField();
        this.point2     = new javax.swing.JLabel();
        this.bloc3      = new javax.swing.JTextField();
        this.point3     = new javax.swing.JLabel();
        this.bloc4      = new javax.swing.JTextField();
        this.blocs      = new javax.swing.JTextField[]{this.bloc1, this.bloc2, this.bloc3, this.bloc4};
        this.points     = new javax.swing.JLabel[]{this.point1, this.point2, this.point3};
        this.components = new javax.swing.JComponent[]{this.bloc1, this.point1, this.bloc2, this.point2, this.bloc3, this.point3, this.bloc4};
        
        //THIS
        setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.LINE_AXIS));
        setBorder(this.bloc1.getBorder());
        setBackground(this.bloc1.getBackground());

        //PREPARE BLOCS
        for(javax.swing.JTextField component : this.blocs){
            component.setColumns(3);
            component.setHorizontalAlignment(javax.swing.JTextField.CENTER);
            component.setText("0");
            component.setBorder(null);
            component.setTransferHandler(null);
        }
        
        //PREAPRE POINTS
        for(javax.swing.JLabel component : this.points){
            component.setText(" . ");
            component.setMaximumSize(new java.awt.Dimension(65535, 65535));
        }
        
        //CARET LISTENER
        javax.swing.event.CaretListener caretListener = new javax.swing.event.CaretListener() {
            @Override
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                javax.swing.JTextField tf = (javax.swing.JTextField) evt.getSource();
                for(javax.swing.event.CaretListener listener : caretListeners){
                    listener.caretUpdate(new IPCaretEvent(this, tf, numBloc(tf)));
                }
            }
        };
        for(javax.swing.JTextField component : this.blocs)
            component.addCaretListener(caretListener);
        
        //ACTION LISTENER
        java.awt.event.ActionListener actionListener = new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                for(java.awt.event.ActionListener listener : actionListeners){
                    listener.actionPerformed(new java.awt.event.ActionEvent(this, evt.getID(), evt.getActionCommand()));
                }
            }
        };
        for(javax.swing.JTextField component : this.blocs)
            component.addActionListener(actionListener);
        
        //FOCUS LISTENER
        java.awt.event.FocusListener focusListener = new java.awt.event.FocusListener() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                if(!focusNotSelect)
                    ((javax.swing.JTextField)evt.getSource()).selectAll();
                else{
                    focusNotSelect = false;
                    ((javax.swing.JTextField)evt.getSource()).setCaretPosition(newCaretPosition);
                    newCaretPosition = 0;
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                if(((javax.swing.JTextField)evt.getSource()).getText().isEmpty())
                    ((javax.swing.JTextField)evt.getSource()).setText("0");
            }
        };
        for(javax.swing.JTextField component : this.blocs)
            component.addFocusListener(focusListener);
        
        //FOCUS LISTENER
        java.awt.event.KeyListener keyListener = new java.awt.event.KeyListener() {
            @Override
            public void keyTyped(java.awt.event.KeyEvent evt) {
                char c = evt.getKeyChar();
                javax.swing.JTextField thisBloc = (javax.swing.JTextField) evt.getSource();
                if(!authorizationText(thisBloc, c)){
                    evt.consume();
                    javax.swing.JTextField next;
                    try{
                        next = blocs[numBloc(thisBloc)+1];
                    }catch(IndexOutOfBoundsException e){
                        next = null;
                    }
                    if(next != null && c == '.'){
                        next.requestFocusInWindow();
                    }
                }
            }

            @Override
            public void keyPressed(java.awt.event.KeyEvent evt) {
                for(java.awt.event.KeyListener listener : keyListeners){
                    listener.keyPressed(new java.awt.event.KeyEvent(JIP.this, evt.getID(), evt.getWhen(), evt.getModifiers(), evt.getKeyCode(), evt.getKeyChar(), evt.getKeyLocation()));
                }
            }

            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                for(java.awt.event.KeyListener listener : keyListeners){
                    listener.keyReleased(new java.awt.event.KeyEvent(JIP.this, evt.getID(), evt.getWhen(), evt.getModifiers(), evt.getKeyCode(), evt.getKeyChar(), evt.getKeyLocation()));
                }
            }
        };
        for(javax.swing.JTextField component : this.blocs)
            component.addKeyListener(keyListener);
        
        //MOUSE MOTION LISTENER
        java.awt.event.MouseMotionListener mouseMotionListener = new java.awt.event.MouseMotionListener() {
            
            /**
             * Calcule la coordonnée relative x au JIP et non aux sous-composants du JIP
             * @param evt Correspond à l'évênement d'un sous-composant
             * @return Retourne la coordonnée realtive x
             */
            private int nx(java.awt.event.MouseEvent evt){
                int nx = getXMouseEvent(evt);
                if (nx < 0) nx = 0;
                if (nx >= getWidth()) nx = getWidth() - 1;
                return nx;
            }
            
            /**
             * Calcule la coordonnée relative y au JIP et non aux sous-composants du JIP
             * @param evt Correspond à l'évênement d'un sous-composant
             * @return Retourne la coordonnée realtive y
             */
            private int ny(java.awt.event.MouseEvent evt){
                int ny = evt.getY();
                if (ny < 0) ny = 0;
                if (ny >= getHeight()) ny = getHeight() - 1;
                return ny;
            }
            
            @Override
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                for (java.awt.event.MouseMotionListener listener : mouseMotionListeners) {
                    if (listener != null)
                        listener.mouseDragged(new java.awt.event.MouseEvent(JIP.this, evt.getID(), evt.getWhen(), evt.getModifiers(), nx(evt), ny(evt), evt.getClickCount(), evt.isPopupTrigger(), evt.getButton()));
                }
            }

            @Override
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                for (java.awt.event.MouseMotionListener listener : mouseMotionListeners) {
                    if (listener != null)
                        listener.mouseMoved(new java.awt.event.MouseEvent(JIP.this, evt.getID(), evt.getWhen(), evt.getModifiers(), nx(evt), ny(evt), evt.getClickCount(), evt.isPopupTrigger(), evt.getButton()));
                }
            }
        };
        for(javax.swing.JComponent component : this.components)
            component.addMouseMotionListener(mouseMotionListener);
        
        //MOUSE WHEEL LISTENER
        java.awt.event.MouseWheelListener mouseWheelListener = new java.awt.event.MouseWheelListener() {
            @Override
            public void mouseWheelMoved(java.awt.event.MouseWheelEvent evt) {
                if(evt.getSource() instanceof javax.swing.JTextField && wheelMouseChangeIp && (evt.getSource().equals(bloc1) || evt.getSource().equals(bloc2) || evt.getSource().equals(bloc3) || evt.getSource().equals(bloc4))){
                    javax.swing.JTextField tf = (javax.swing.JTextField) evt.getSource();
                    int value, nValue = 0;
                    try{
                        value = Integer.parseInt(tf.getText());
                    }catch(NumberFormatException e){
                        value = 0;
                    }
                    if(evt.getWheelRotation() > 0){
                        nValue = value - 1;
                        if(nValue < 0) nValue = 0;
                    }
                    if(evt.getWheelRotation() < 0){
                        nValue = value + 1;
                        if(nValue > 255) nValue = 255;
                    }
                    if(nValue != value)
                        tf.setText("" + nValue);
                }
                
                for (java.awt.event.MouseWheelListener listener : mouseMouseWheelListeners) {
                    if (listener != null) {
                        int nx = getXMouseEvent(evt);
                        if (nx < 0) nx = 0;
                        if (nx >= getWidth()) nx = getWidth() - 1;
                        int ny = evt.getY();
                        if (ny < 0) ny = 0;
                        if (ny >= getHeight()) ny = getHeight() - 1;
                        listener.mouseWheelMoved(new java.awt.event.MouseWheelEvent(JIP.this, evt.getID(), evt.getWhen(), evt.getModifiers(), nx, ny, evt.getClickCount(), evt.isPopupTrigger(), evt.getScrollType(), evt.getScrollAmount(), evt.getWheelRotation()));
                    }
                }
            }
        };
        for(javax.swing.JComponent component : this.components)
            component.addMouseWheelListener(mouseWheelListener);
        
        //MOUSE LISTENER
        java.awt.event.MouseListener mouseListener = new java.awt.event.MouseListener() {
            
            /**
             * Calcule la coordonnée relative x au JIP et non aux sous-composants du JIP
             * @param evt Correspond à l'évênement d'un sous-composant
             * @return Retourne la coordonnée realtive x
             */
            private int nx(java.awt.event.MouseEvent evt){
                int nx = getXMouseEvent(evt);
                if (nx < 0) nx = 0;
                if (nx >= getWidth()) nx = getWidth() - 1;
                return nx;
            }
            
            /**
             * Calcule la coordonnée relative y au JIP et non aux sous-composants du JIP
             * @param evt Correspond à l'évênement d'un sous-composant
             * @return Retourne la coordonnée realtive y
             */
            private int ny(java.awt.event.MouseEvent evt){
                int ny = evt.getY();
                if (ny < 0) ny = 0;
                if (ny >= getHeight()) ny = getHeight() - 1;
                return ny;
            }
            
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                for(java.awt.event.MouseListener listener : mouseListeners){
                    if(listener != null)
                        listener.mouseClicked(new java.awt.event.MouseEvent(JIP.this, evt.getID(), evt.getWhen(), evt.getModifiers(), nx(evt), ny(evt), evt.getClickCount(), evt.isPopupTrigger(), evt.getButton()));
                }
            }

            @Override
            public void mousePressed(java.awt.event.MouseEvent evt) {
                for(java.awt.event.MouseListener listener : mouseListeners){
                    if(listener != null)
                        listener.mousePressed(new java.awt.event.MouseEvent(JIP.this, evt.getID(), evt.getWhen(), evt.getModifiers(), nx(evt), ny(evt), evt.getClickCount(), evt.isPopupTrigger(), evt.getButton()));
                }
            }

            @Override
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                for(java.awt.event.MouseListener listener : mouseListeners){
                    if(listener != null)
                        listener.mouseReleased(new java.awt.event.MouseEvent(JIP.this, evt.getID(), evt.getWhen(), evt.getModifiers(), nx(evt), ny(evt), evt.getClickCount(), evt.isPopupTrigger(), evt.getButton()));
                }
            }

            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if(!entered){
                    entered = true;
                    for(java.awt.event.MouseListener listener : mouseListeners){
                        if(listener != null)
                            listener.mouseEntered(new java.awt.event.MouseEvent(JIP.this, evt.getID(), evt.getWhen(), evt.getModifiers(), nx(evt), ny(evt), evt.getClickCount(), evt.isPopupTrigger(), evt.getButton()));
                    }
                }
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                java.awt.Point mouse = java.awt.MouseInfo.getPointerInfo().getLocation();
                int x = mouse.x;
                int y = mouse.y;

                java.awt.Point compo = getLocationOnScreen();
                int x1 = compo.x;
                int y1 = compo.y;

                boolean in = true;
                int x2 = x - x1;
                int y2 = y - y1;
                if(x <= x1 || y <= y1 || (x2+1+getMargePX()) >= getWidth() || (y2+1) >= getHeight()) in = false;
                if(!in){
                    entered = false;
                    for(java.awt.event.MouseListener listener : mouseListeners){
                        if(listener != null)
                            listener.mouseExited(new java.awt.event.MouseEvent(JIP.this, evt.getID(), evt.getWhen(), evt.getModifiers(), nx(evt), ny(evt), evt.getClickCount(), evt.isPopupTrigger(), evt.getButton()));
                    }
                }
            }
        };
        for(javax.swing.JComponent component : this.components)
            component.addMouseListener(mouseListener);
        
        //PROPERTY CHANGE LISTENER
        java.beans.PropertyChangeListener propertyChangeListener = new java.beans.PropertyChangeListener() {
            @Override
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                for(java.beans.PropertyChangeListener listener : propertyChangeListeners){
                    if(listener != null)
                        listener.propertyChange(new java.beans.PropertyChangeEvent(this, evt.getPropertyName(), evt.getOldValue(), evt.getNewValue()));
                }
            }
        };
        for(javax.swing.JComponent component : this.components)
            component.addPropertyChangeListener(propertyChangeListener);
        
        //VETOABLE CHANGE LISTENER
        java.beans.VetoableChangeListener vetoableChangeListener = new java.beans.VetoableChangeListener() {
            @Override
            public void vetoableChange(java.beans.PropertyChangeEvent evt) throws java.beans.PropertyVetoException {
                for(java.beans.VetoableChangeListener listener : vetoableChangeListeners){
                    if(listener != null){
                        listener.vetoableChange(new java.beans.PropertyChangeEvent(this, evt.getPropertyName(), evt.getOldValue(), evt.getNewValue()));
                    }
                }
            }
        };
        for(javax.swing.JComponent component : this.components)
            component.addVetoableChangeListener(vetoableChangeListener);
        
        //ADD
        for(javax.swing.JComponent component : this.components)
            add(component);
        
        //MAIN FOCUS
        java.awt.KeyboardFocusManager.getCurrentKeyboardFocusManager().addPropertyChangeListener("focusOwner", new java.beans.PropertyChangeListener() {
            @Override
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                if(evt != null){
                    newFocus = evt.getNewValue();
                    if(newFocus != null){
                        if(oldFocusIsMe){
                            if(!hasJIP(newFocus)){
                                for (java.awt.event.FocusListener listener : focusListeners) {
                                    listener.focusLost(new java.awt.event.FocusEvent(JIP.this, Integer.MIN_VALUE + (int)(Math.random() * ((Integer.MAX_VALUE - Integer.MIN_VALUE) + 1))));
                                }
                            }
                        }else{
                            if(hasJIP(newFocus)){
                                for (java.awt.event.FocusListener listener : focusListeners) {
                                    listener.focusGained(new java.awt.event.FocusEvent(JIP.this, Integer.MIN_VALUE + (int)(Math.random() * ((Integer.MAX_VALUE - Integer.MIN_VALUE) + 1))));
                                }
                            }
                        }
                        oldFocusIsMe = hasJIP(newFocus);
                    }
                }
            }
        });
    }
    
    /**
     * Renvoie le texte tapé sans interprétation (c'est à dire que les caractères autorisé sont toujours compris entre [0; 9] mais chaque bloc d'une adresse ip n'excède pas 255. Ce n'est pas le cas ici. On autorise cette erreur
     * @param tf Correspond au TextField qui est en cours d'écriture
     * @param c Correspond au caractère qui vient d'être tapé
     * @return Retourne le texte sans interprétation
     */
    private String getTextWithoutInterpretation(javax.swing.JTextField tf, char c){
        String text = tf.getText();
        int posStart = tf.getSelectionStart();
        int posStop = tf.getSelectionEnd();
        if(posStart == posStop){
            int position = tf.getCaretPosition();
            String startSub = text.substring(0, position);
            String endSub = text.substring(position);
            return startSub + c + endSub;
        } else {
            return text.substring(0, posStart) + c + text.substring(posStop);
        }
    }
    
    /**
     * Détermine si le caractère tapé est autorisé à être ajouté au TextField
     * @param tf Correspond au TextField en cours d'écriture
     * @param c Correspond au caractère qui doit normalement être ajouté au TextField
     * @return Retourne true, si le caractère est autorisé, sinon false
     */
    private boolean authorizationText(javax.swing.JTextField tf, char c){
        if(c >= '0' && c <= '9'){
            String textSet  = getTextWithoutInterpretation(tf, c);
            if(textSet.length() >= 2 && textSet.charAt(0) == '0')
                return false;
        
            int value = Integer.parseInt(textSet);
            return !(value < 0 || value > 255);
        
        } else return false;
    }
    
    /**
     * Détermine si un objet (ou sous-composant) est considéré comme faisant partie de ce JIP ou est le JIP
     * @param obj Correspond à l'objet à tester
     * @return Retourne true, si l'objet est considéré comme étant le JIP
     */
    private boolean hasJIP(Object obj){
        if(obj == null) return false;
        else return obj.equals(this) || obj.equals(bloc1) || obj.equals(bloc2) || obj.equals(bloc3) || obj.equals(bloc4);
    }
    
    /**
     * Renvoie la position x de la souris d'un évênement (en effet chaque sous composant repart de la position 0, ce que nous ne voulons pas, nous voulons la position x absolue du composant JIP en entier)
     * @param evt Correspond à l'évênement d'un sous composant
     * @return Retourne la position x absolue du JIP d'un évènement
     */
    private int getXMouseEvent(java.awt.event.MouseEvent evt){
        int x = 0;
        if(evt.getSource().equals(bloc1))   x = evt.getX();
        if(evt.getSource().equals(point1))  x = bloc1.getWidth() + evt.getX();
        if(evt.getSource().equals(bloc2))   x = bloc1.getWidth() + point1.getWidth() + evt.getX();
        if(evt.getSource().equals(point2))  x = bloc1.getWidth() + point1.getWidth() + bloc2.getWidth() + evt.getX();
        if(evt.getSource().equals(bloc3))   x = bloc1.getWidth() + point1.getWidth() + bloc2.getWidth() + point2.getWidth() + evt.getX();
        if(evt.getSource().equals(point3))  x = bloc1.getWidth() + point1.getWidth() + bloc2.getWidth() + point2.getWidth() + bloc3.getWidth() + evt.getX();
        if(evt.getSource().equals(bloc4))   x = bloc1.getWidth() + point1.getWidth() + bloc2.getWidth() + point2.getWidth() + bloc3.getWidth() + point3.getWidth() + evt.getX();
        return x;
    }
    
    /**
     * Du fait de l'utilisation du BoxLayout, chaque TextField a la même taille, chaque Label aussi. Ce peut laisser une petite marge de pixel entre le dernier sous-composant et la fin du container. Cette méthode renvoie cette marge
     * @return Retourne la marge comprise entre le dernier sous-composant (bloc4) et la fin du container
     */
    private int getMargePX(){
        return getWidth() - (bloc1.getWidth() + point1.getWidth() + bloc2.getWidth() + point2.getWidth() + bloc3.getWidth() + point3.getWidth() + bloc4.getWidth());
    }
    
    /**
     * Modifie le nombre d'espace total autour des points (le nombre total sera répartie en 2 blocs égaux)
     * @param n Correspond au nombre d'espace total
     */
    private void setSpacePoints(int n){
        String space = "";
        int count = n/2;
        for(int i=0;i<count;i++){
            space += " ";
        }
        point1.setText(space+"."+space);
        point2.setText(space+"."+space);
        point3.setText(space+"."+space);
    }
    
    /**
     * Renvoie le numéro d'un bloc [0; 3]
     * @param aBloc Correspond au bloc dont ont cherche à connaître le numéro
     * @return Retourne le numéro d'un bloc ou -1. Si le numéro n'a pas été trouvé
     */
    private int numBloc(javax.swing.JTextField aBloc){
        for(int i=0;i<blocs.length;i++){
            javax.swing.JTextField b = blocs[i];
            if(b.equals(aBloc)) return i;
        }
        return -1;
    }

    
    
//CLASS
    /**
     * Cette classe représente les IPCaretEvent. Lorsque la position du caret est mise à jour dans un JIP, un évênement IPCaretEvent est envoyé au CaretListeners
     * @see JIP
     * @author JasonPercus
     * @version 1.0
     */
    public static class IPCaretEvent extends javax.swing.event.CaretEvent {

        
        
    //ATTRIBUTS
        /**
         * Correspond au bloc ip qui a généré l'évênement
         */
        private javax.swing.JTextField bloc;
        
        /**
         * Correspond à l'index du bloc ip qui a généré l'évênement [0; 3]
         */
        private int index;
        
        
        
    //CONSTRUCTORS
        /**
         * Crée un évênement IPCaretEvent
         * @param source Correspond au JIP qui a généré l'évênement
         */
        public IPCaretEvent(Object source) {
            super(source);
        }
        
        /**
         * Crée un évênement IPCaretEvent
         * @param source Correspond au JIP qui a généré l'évênement
         * @param bloc Correspond au bloc du JIP qui a généré l'évênement
         * @param index Correspond à l'index du bloc du JIP qui a généré l'évênement
         */
        public IPCaretEvent(Object source, javax.swing.JTextField bloc, int index) {
            this(source);
            this.bloc  = bloc;
            this.index = index;
        }
        
        
        
    //METHODES PUBLICS
        /**
         * Renvoie l'index du bloc du JIP qui a généré l'évênement
         * @return Retourne l'index du bloc du JIP qui a généré l'évênement
         */
        public int getIndexBloc(){
            return this.index;
        }
        
        /**
         * Renvoie le bloc IP du JIP qui a généré l'évênement
         * @return Retourne le bloc IP du JIP qui a généré l'évênement
         */
        public javax.swing.JTextField getBloc(){
            return this.bloc;
        }
        
        /**
         * Renvoie la nouvelle position du caret
         * @return Retourne la nouvelle position du caret
         */
        @Override
        public int getDot() {
            if (bloc != null && bloc.hasFocus()) {
                return bloc.getCaretPosition();
            }
            return -1;
        }

        /**
         * Renvoie lors d'une sélection de texte l'autre position du caret. Celle qui est jointe à {@link #getDot()}
         * @return Retourne l'autre position du caret
         */
        @Override
        public int getMark() {
            if (bloc != null && bloc.hasFocus()) {
                int start = bloc.getSelectionStart();
                int end = bloc.getSelectionEnd();
                int position = getDot();
                if (start != end) {
                    if (start == position) {
                        return end;
                    } else {
                        return start;
                    }
                } else {
                    return position;
                }
            }
            return -1;
        }

        /**
         * Renvoie l'évênement sous la forme d'une chaîne de caractères
         * @return Retourne l'évênement sous la forme d'une chaîne de caractères
         */
        @Override
        public String toString() {
            return "IPCaretListener{" + "bloc=" + bloc + ", index=" + index + '}';
        }
    
        
        
    }

    

}