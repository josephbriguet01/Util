/*
 * Copyright (C) JasonPercus Systems, Inc - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by JasonPercus, 05/2021
 */
package com.jasonpercus.util.mousearea;



import com.jasonpercus.util.OS;



/**
 * Cette classe permet de déterminer précisément si la souris se trouve dans une ou des zones précises d'un composant (Ne fonctionne pas sur Android). Pour cela le composant à besoin d'une image carbone négative. Celle-ci est créé par les objets {@link Zone}. Ce type d'objet permet de dessiner comme avec un objet {@link java.awt.Graphics2D}, avec quelques méthodes en moins. Le dessin réalisé délimitera des zones complètes. Il suffira juste à partir des coordonnées de la souris d'interroger la zone pour déterminer, s'il s'agit d'une zone active ou passive. Exemple d'utilisation:
 * <pre>
 * {@code
 * - Imaginons que nous avons un composant carré avec des coins arrondis et dont le composant est troué en son centre par un cercle. Nous voulons que lorque la souris se trouve
 * sur le carré la souris change de curseur et lorsqu'elle est en dehors du carré le curseur de la souris redevient celui par défaut.
 * 
 * - Ajoutons la condition que lorsque la souris se trouve dans le cercle du carré, le curseur de la souris redevient celui par défaut également.
 * L'idée basique serait de calculer les coordonnées du rectangle englobant le carré et les coordonnées du rectangle englobant le cercle. Puis de déterminer si la souris se trouve 
 * dans un des deux rectangles.
 * 
 * Malheureusement, avec cette approche, on ne calcule pas précisément. Car rappelons-nous, le carré a des coins arrondis. Et nous voulons que lorsque la souris se trouve 
 * dans ses coins la zone ne soit pas détectée. De même pour le cercle intérieur.
 * 
 * Nous savons donc qu'avec cette approche, les composants ne peuvent avoir qu'une taille rectangulaire. D'où l'inutilité de cette approche et d'où l'utilité de cette classe.
 * Cette classe va créer un carbone négatif (ou template, ou plan) du dessin du composant. Ainsi si le composant est arrondi, la détection suivra cette contrainte
 * }</pre>
 * 
 * Maintenant créons notre exemple. Partons du principe que nous avons un composant swing (MyComponent my_component) héritant de {@link javax.swing.JPanel} de taille 100x100
 * <pre>
 * <code>
 * // Vérifions d'abord que cette classe est bien supportée par l'OS (Android n'est pas supportée)
 * boolean isSupported = {@link #isSupportedOS() Zone.isSupportedOS()};
 * 
 * // Notre zone de recherche fera exactement 100 pixels par 100. Attention: Cette zone de recherche doit être égale à la taille de notre composant swing.
 * Zone zone = new {@link Zone#Zone(int, int, com.jasonpercus.util.mousearea.ModeArea) Zone(100, 100, ModeArea.ADDITIVE)};
 * 
 * // Par défaut notre zone renverra toujours false car la zone en mode Additive, car il n'y a aucun dessin d'imprimé sur le carbone négatif.
 * // Exemple soit x et y deux valeurs comprises entre [0; 100] représentant la position que nous voulons contrôler dans la zone
 * // Quelque soit la valeur de x ou de y le système affiche pour le moment false, car la zone ne détecte rien
 * System.out.println(zone.{@link #isColored(int, int) isColored(x, y)}); // "false"
 * 
 * // Ajoutons notre carré dont les coins sont arrondis
 * zone.{@link #fillRoundRect(int, int, int, int, int, int) fillRoundRect(0, 0, 100, 100, 20, 20)};
 * 
 * // Maintenant si x et y sont égales à 2, le système affiche false, car la zone de détecte rien
 * System.out.println(zone.{@link #isColored(int, int) isColored(2, 2)}); // "false"
 * 
 * // Mais si x et y sont égales à 50, le système affiche true, car le carré au coins arrondis est détecté
 * System.out.println(zone.{@link #isColored(int, int) isColored(50, 50)}); // "true"
 * 
 * // Mais notre position 50; 50 est le centre de notre composant où l'on est sensé avoir un cecle qui lui ne doit pas être détecté
 * // Il faut changer de mode en soustractif (en gros enlever une zone {@link ModeArea#SUBSTRACTIVE})
 * zone.{@link #setModeArea(com.jasonpercus.util.mousearea.ModeArea) setModeArea(ModeArea.SUBSTRACTIVE)};
 * 
 * // Puis dessiner notre cercle (disons par exemple de diamètre 50). Comme notre composant fait 100 de largeur et de hauteur, pour le centrer, il faut placer le cercle en 25; 25
 * zone.{@link #fillOval(int, int, int, int) fillOval(25, 25, 50, 50)};
 * 
 * // Maintenant si x et y sont égales à 50, le système affiche false, car la zone de détecte rien (on se trouve dans le cercle)
 * System.out.println(zone.{@link #isColored(int, int) isColored(50, 50)}); // "false"
 * 
 * // Mais si x et y sont égales à 25, le système affiche true, car le carré au coins arrondis est détecté
 * System.out.println(zone.{@link #isColored(int, int) isColored(25, 25)}); // "true"
 * </code>
 * </pre>
 * Si l'on avait voulu détecter au contraire la zone en dehors du carré et la zone comprise dans le cercle, il aurais fallu créer notre zone en {@link ModeArea#SUBSTRACTIVE}.
 * <pre>
 * <code>
 * Zone zone = new {@link Zone#Zone(int, int, com.jasonpercus.util.mousearea.ModeArea) Zone(100, 100, ModeArea.SUBSTRACTIVE)};
 * </code>
 * Au lieu de dessiner, il est possible de fournir un {@link java.awt.image.BufferedImage} représentant notre carbone négatif
 * Il suffit d'utiliser la méthode {@link #setNegativeCarbon(java.awt.image.BufferedImage)}. A savoir que l'image ne doit contenir strictement que deux couleurs du noir et du blanc,
 * pas de niveau de gris, ni de couleur Alpha. L'image doit faire la taille de la zone
 * 
 * Remarque: Pour ne pas confondre entre un carbon et un negative carbon:
 *  - Carbon: Il faut le comparer au carbone d'une machine à écrire. Celui-ci contenait l'inverse de la feuille imprimée
 *  - Negative Carbon: Même comparaison si ce n'est que c'est l'inverse du carbone (Autrement dit la feuille imprimée). D'où le fait que l'on raisonne toujours avec le Negative Carbon.
 * </pre>
 * @see ModeArea
 * @see java.awt.Graphics2D
 * @author JasonPercus
 * @version 1.0
 */
public final class Zone {
    
    
    
//ATTRIBUTS
    /**
     * Correspond au carbone negatif (autrement dit l'image original et non inversée. C'est le carbone qui est inversée par rapport au carbone négatif)
     */
    private java.awt.image.BufferedImage image;
    
    /**
     * Correspond au graphic du carbone négatif (voir {@link #image})
     */
    private java.awt.Graphics2D g;
    
    /**
     * Correspond au mode de dessin. Si le mode est {@link ModeArea#ADDITIVE} (par défaut), alors les zones dessinées seront ajoutés au carbone négatif. À l'inverse, si le mode est {@link ModeArea#SUBSTRACTIVE}, alors les zones dessinées seront supprimées du carbone négatif
     */
    private ModeArea mode;
    
    /**
     * Correspond aux 3 composantes RGB de la couleur de fond
     */
    private int[] background;
    
    /**
     * Correspond aux 3 composantes RGB de la couleur de premier plan
     */
    private int[] foreground;
    
    /**
     * Correspond à la largeur de la zone
     */
    private int width;
    
    /**
     * Correspond à la hauteur de la zone
     */
    private int height;
    
    
    
//CONSTRUCTORS
    /**
     * Crée une zone de souris à contrôler
     * @param width Correspond à la largeur de la zone à contrôler
     * @param height Correspond à la hauteur de la zone à contrôler
     */
    public Zone(int width, int height){
        this(width, height, ModeArea.ADDITIVE);
    }

    /**
     * Crée une zone de souris à contrôler
     * @param width Correspond à la largeur de la zone à contrôler
     * @param height Correspond à la hauteur de la zone à contrôler
     * @param mode Correspond au mode de dessin. Si le mode est {@link ModeArea#ADDITIVE}, alors la zone renverra false à moins d'ajouter des zones de détection. À l'inverse si le mode est {@link ModeArea#SUBSTRACTIVE}, alors la zone reverra true à moins de supprimer des zones de détection
     */
    public Zone(int width, int height, ModeArea mode) {
        this.mode = mode;
        setModeArea(mode);
        this.width = width;
        this.height = height;
        this.image = new java.awt.image.BufferedImage(width, height, java.awt.image.BufferedImage.TYPE_INT_RGB);
        this.g = image.createGraphics();
        prepareBackground();
        this.g.fillRect(0, 0, width, height);
    }
    
    /**
     * Crée une zone de souris à contrôler
     * @param negativeCarbon Correspond au carbone négatif de la zone à contrôler. L'image doit posséder seulement la couleur noir et la couleur blanche (Attention: pas de gris, ni d'alpha, ni de couleur). Si par inadvertance, une couleur autre (voir avec de l'alpha) venait à apparaitre sur le carbone négatif, alors une modification du carbone serait effectué pour supprimer les anomalies en fonction du mode {@link ModeArea#ADDITIVE}
     */
    public Zone(java.awt.image.BufferedImage negativeCarbon){
        this(negativeCarbon, ModeArea.ADDITIVE);
    }
    
    /**
     * Crée une zone de souris à contrôler
     * @param negativeCarbon Correspond au carbone négatif de la zone à contrôler. L'image doit posséder seulement la couleur noir et la couleur blanche (Attention: pas de gris, ni d'alpha, ni de couleur). Si par inadvertance, une couleur autre (voir avec de l'alpha) venait à apparaitre sur le carbone négatif, alors une modification du carbone serait effectué pour supprimer les anomalies en fonction du mode choisi en paramètre
     * @param mode Correspond au mode de dessin. Si le mode est {@link ModeArea#ADDITIVE}, alors la zone renverra false à moins d'ajouter des zones de détection. À l'inverse si le mode est {@link ModeArea#SUBSTRACTIVE}, alors la zone reverra true à moins de supprimer des zones de détection
     */
    public Zone(java.awt.image.BufferedImage negativeCarbon, ModeArea mode){
        this(negativeCarbon, false, mode);
    }
    
    /**
     * Crée une zone de souris à contrôler
     * @param carbonOrNegativeCarbon Correspond au carbone ou carbone négatif de la zone à contrôler. L'image doit posséder seulement la couleur noir et la couleur blanche (Attention: pas de gris, ni d'alpha, ni de couleur). Si par inadvertance, une couleur autre (voir avec de l'alpha) venait à apparaitre sur le carbone ou carbone négatif, alors une modification du carbone serait effectué pour supprimer les anomalies en fonction du mode {@link ModeArea#ADDITIVE}
     * @param carbon True s'il s'agit d'un carbone (l'inverse de la zone dessinée), false s'il s'agit d'un carbone négatif
     */
    public Zone(java.awt.image.BufferedImage carbonOrNegativeCarbon, boolean carbon){
        this(carbonOrNegativeCarbon, carbon, ModeArea.ADDITIVE);
    }
    
    /**
     * Crée une zone de souris à contrôler
     * @param carbonOrNegativeCarbon Correspond au carbone ou carbone négatif de la zone à contrôler. L'image doit posséder seulement la couleur noir et la couleur blanche (Attention: pas de gris, ni d'alpha, ni de couleur). Si par inadvertance, une couleur autre (voir avec de l'alpha) venait à apparaitre sur le carbone ou carbone négatif, alors une modification du carbone serait effectué pour supprimer les anomalies en fonction du mode choisi en paramètre
     * @param carbon True s'il s'agit d'un carbone (l'inverse de la zone dessinée), false s'il s'agit d'un carbone négatif
     * @param mode Correspond au mode de dessin. Si le mode est {@link ModeArea#ADDITIVE}, alors la zone renverra false à moins d'ajouter des zones de détection. À l'inverse si le mode est {@link ModeArea#SUBSTRACTIVE}, alors la zone reverra true à moins de supprimer des zones de détection
     */
    public Zone(java.awt.image.BufferedImage carbonOrNegativeCarbon, boolean carbon, ModeArea mode){
        this(carbonOrNegativeCarbon.getWidth(), carbonOrNegativeCarbon.getHeight(), mode);
        if(carbon)
            setCarbon(carbonOrNegativeCarbon);
        else
            setNegativeCarbon(carbonOrNegativeCarbon);
    }
    
    
    
//METHODE PUBLIC STATIC
    /**
     * Renvoie si le système d'exploitation supporte cette classe
     * @return Retourne true si le système est supporté, sinon false
     */
    public static boolean isSupportedOS(){
        return !OS.IS_ANDROID;
    }
    
    
    
//GETTERS & SETTERS
    /**
     * Renvoie le mode de dessin du carbone négatif. Si le mode est {@link ModeArea#ADDITIVE} (par défaut), alors les zones dessinées seront ajoutés au carbone négatif. À l'inverse, si le mode est {@link ModeArea#SUBSTRACTIVE}, alors les zones dessinées seront supprimées du carbone négatif
     * @return Retourne le mode de dessin du carbone négatif
     */
    public ModeArea getModeArea(){
        return this.mode;
    }
    
    /**
     * Définie le nouveau mode de dessin du carbone négatif. Si le mode est {@link ModeArea#ADDITIVE} (par défaut), alors les zones dessinées seront ajoutés au carbone négatif. À l'inverse, si le mode est {@link ModeArea#SUBSTRACTIVE}, alors les zones dessinées seront supprimées du carbone négatif
     * @param mode Correspond au nouveau mode de dessin
     */
    public void setModeArea(ModeArea mode){
        this.mode = mode;
        if(mode == ModeArea.ADDITIVE){
            this.background = new int[]{0xFF, 0xFF, 0xFF};
            this.foreground = new int[]{0x00, 0x00, 0x00};
        }else{
            this.background = new int[]{0x00, 0x00, 0x00};
            this.foreground = new int[]{0xFF, 0xFF, 0xFF};
        }
    }

    /**
     * Renvoie la largeur de la zone à détecter
     * @return Retourne la largeur de la zone à détecter
     */
    public int getWidth() {
        return width;
    }

    /**
     * Renvoie la hauteur de la zone à détecter
     * @return Retourne la hauteur de la zone à détecter
     */
    public int getHeight() {
        return height;
    }
    
    /**
     * Renvoie si un point de la zone du carbone négatif est actif ou pas (si le carbone négatif contient un pixel noir alors true est renvoyé, s'il est blanc, alors false est renvoyé)
     * @param x Correspond à la position x du pixel dont on cherche à vérifier sa coloration
     * @param y Correspond à la position y du pixel dont on cherche à vérifier sa coloration
     * @return Retourne true si le carbone négatif contient un pixel noir, s'il est blanc, alors false est renvoyé
     */
    public boolean isColored(int x, int y){
        java.awt.Color toSearch = (this.mode == ModeArea.ADDITIVE) ? new java.awt.Color(foreground[0], foreground[1], foreground[2]) : new java.awt.Color(background[0], background[1], background[2]);
        int color = image.getRGB(x, y);
        return color == toSearch.getRGB();
    }
    
    /**
     * Renvoie l'image carbone du carbone négatif. Le carbone négatif étant l'original, le carbone représente la même image mais avec la couleur noire et blanche inversée
     * @return Retourne l'image du carbone
     */
    public java.awt.image.BufferedImage getCarbon(){
        return reverseImageToBinaryImg(this.image);
    }
    
    /**
     * Renvoie l'image carbone négatif. Le carbone négatif est l'original, alors que le carbone représente la même image mais avec la couleur noire et blanche inversée
     * @return Retourne l'image du carbone négatif
     */
    public java.awt.image.BufferedImage getNegativeCarbon(){
        return this.image;
    }
    
    /**
     * Modifie l'image carbone du carbone négatif. Les couleurs seront inversées pour obtenir le carbone négatif. Si l'image carbone est plus grande que la zone définie au constructeur, alors le carbone sera coupé, s'il est plus petit, alors le carbone sera complété par la couleur {@link ModeArea#ADDITIVE} ou {@link ModeArea#SUBSTRACTIVE} en fonction du mode de dessin
     * @param carbon Correspond à l'image carbone. Le carbone négatif étant l'original, le carbone représente la même image mais avec la couleur noire et blanche inversée
     * @return Retourne true, si l'image carbone ne représentait pas un carbone standard (c'est à dire avec la couleur noire et blanche seulement et sans alpha), sinon false
     */
    public boolean setCarbon(java.awt.image.BufferedImage carbon){
        return this.loadCarbon(carbon);
    }
    
    /**
     * Modifie l'image du carbone négatif. Si l'image du carbone négatif est plus grande que la zone définie au constructeur, alors le carbone négatif sera coupé, s'il est plus petit, alors le carbone négatif sera complété par la couleur {@link ModeArea#ADDITIVE} ou {@link ModeArea#SUBSTRACTIVE} en fonction du mode de dessin
     * @param negativeCarbon Correspond à l'image du carbone négatif. Le carbone négatif est l'original, alors que le carbone représente la même image mais avec la couleur noire et blanche inversée
     * @return Retourne true, si l'image du carbone négatif ne représentait pas un carbone négatif standard (c'est à dire avec la couleur noire et blanche seulement et sans alpha), sinon false
     */
    public boolean setNegativeCarbon(java.awt.image.BufferedImage negativeCarbon){
        return this.loadNegativeCarbon(negativeCarbon);
    }

    
    
//METHODES PUBLICS
    /**
     * Translates the origin of the graphics context to the point
     * (<i>x</i>,&nbsp;<i>y</i>) in the current coordinate system.
     * Modifies this graphics context so that its new origin corresponds
     * to the point (<i>x</i>,&nbsp;<i>y</i>) in this graphics context's
     * original coordinate system.  All coordinates used in subsequent
     * rendering operations on this graphics context will be relative
     * to this new origin.
     * @param  x   the <i>x</i> coordinate.
     * @param  y   the <i>y</i> coordinate.
     */
    public void translate(int x, int y){
        this.g.translate(x, y);
    }

    /**
     * Concatenates the current
     * <code>Graphics2D</code> <code>Transform</code>
     * with a translation transform.
     * Subsequent rendering is translated by the specified
     * distance relative to the previous position.
     * This is equivalent to calling transform(T), where T is an
     * <code>AffineTransform</code> represented by the following matrix:
     * <pre>
     *          [   1    0    tx  ]
     *          [   0    1    ty  ]
     *          [   0    0    1   ]
     * </pre>
     * @param tx the distance to translate along the x-axis
     * @param ty the distance to translate along the y-axis
     */
    public void translate(double tx, double ty){
        this.g.translate(tx, ty);
    }

    /**
     * Gets the current font.
     * @return    this graphics context's current font.
     * @see       java.awt.Font
     * @see       java.awt.Graphics#setFont(Font)
     */
    public java.awt.Font getFont(){
        return this.g.getFont();
    }

    /**
     * Sets this graphics context's font to the specified font.
     * All subsequent text operations using this graphics context
     * use this font. A null argument is silently ignored.
     * @param  font   the font.
     * @see     java.awt.Graphics#getFont
     * @see     java.awt.Graphics#drawString(java.lang.String, int, int)
     * @see     java.awt.Graphics#drawBytes(byte[], int, int, int, int)
     * @see     java.awt.Graphics#drawChars(char[], int, int, int, int)
    */
    public void setFont(java.awt.Font font){
        this.g.setFont(font);
    }

    /**
     * Gets the font metrics of the current font.
     * @return    the font metrics of this graphics
     *                    context's current font.
     * @see       java.awt.Graphics#getFont
     * @see       java.awt.FontMetrics
     * @see       java.awt.Graphics#getFontMetrics(Font)
     */
    public java.awt.FontMetrics getFontMetrics() {
        return this.g.getFontMetrics();
    }

    /**
     * Gets the font metrics for the specified font.
     * @return    the font metrics for the specified font.
     * @param     f the specified font
     * @see       java.awt.Graphics#getFont
     * @see       java.awt.FontMetrics
     * @see       java.awt.Graphics#getFontMetrics()
     */
    public java.awt.FontMetrics getFontMetrics(java.awt.Font f){
        return this.g.getFontMetrics(f);
    }
    
    /**
     * Get the rendering context of the <code>Font</code> within this
     * <code>Graphics2D</code> context.
     * The {@link java.awt.font.FontRenderContext}
     * encapsulates application hints such as anti-aliasing and
     * fractional metrics, as well as target device specific information
     * such as dots-per-inch.  This information should be provided by the
     * application when using objects that perform typographical
     * formatting, such as <code>Font</code> and
     * <code>TextLayout</code>.  This information should also be provided
     * by applications that perform their own layout and need accurate
     * measurements of various characteristics of glyphs such as advance
     * and line height when various rendering hints have been applied to
     * the text rendering.
     *
     * @return a reference to an instance of FontRenderContext.
     * @see java.awt.font.FontRenderContext
     * @see java.awt.Font#createGlyphVector
     * @see java.awt.font.TextLayout
     * @since     1.2
     */
    public java.awt.font.FontRenderContext getFontRenderContext(){
        return this.g.getFontRenderContext();
    }

    /**
     * Copies an area of the component by a distance specified by
     * <code>dx</code> and <code>dy</code>. From the point specified
     * by <code>x</code> and <code>y</code>, this method
     * copies downwards and to the right.  To copy an area of the
     * component to the left or upwards, specify a negative value for
     * <code>dx</code> or <code>dy</code>.
     * If a portion of the source rectangle lies outside the bounds
     * of the component, or is obscured by another window or component,
     * <code>copyArea</code> will be unable to copy the associated
     * pixels. The area that is omitted can be refreshed by calling
     * the component's <code>paint</code> method.
     * @param       x the <i>x</i> coordinate of the source rectangle.
     * @param       y the <i>y</i> coordinate of the source rectangle.
     * @param       width the width of the source rectangle.
     * @param       height the height of the source rectangle.
     * @param       dx the horizontal distance to copy the pixels.
     * @param       dy the vertical distance to copy the pixels.
     */
    public void copyArea(int x, int y, int width, int height,
                                  int dx, int dy){
        this.g.copyArea(x, y, width, height, dx, dy);
    }

    /**
     * Draws a line, using the current color, between the points
     * <code>(x1,&nbsp;y1)</code> and <code>(x2,&nbsp;y2)</code>
     * in this graphics context's coordinate system.
     * @param   x1  the first point's <i>x</i> coordinate.
     * @param   y1  the first point's <i>y</i> coordinate.
     * @param   x2  the second point's <i>x</i> coordinate.
     * @param   y2  the second point's <i>y</i> coordinate.
     */
    public void drawLine(int x1, int y1, int x2, int y2){
        prepareForeground();
        this.g.drawLine(x1, y1, x2, y2);
    }

    /**
     * Fills the specified rectangle.
     * The left and right edges of the rectangle are at
     * <code>x</code> and <code>x&nbsp;+&nbsp;width&nbsp;-&nbsp;1</code>.
     * The top and bottom edges are at
     * <code>y</code> and <code>y&nbsp;+&nbsp;height&nbsp;-&nbsp;1</code>.
     * The resulting rectangle covers an area
     * <code>width</code> pixels wide by
     * <code>height</code> pixels tall.
     * The rectangle is filled using the graphics context's current color.
     * @param         x   the <i>x</i> coordinate
     *                         of the rectangle to be filled.
     * @param         y   the <i>y</i> coordinate
     *                         of the rectangle to be filled.
     * @param         width   the width of the rectangle to be filled.
     * @param         height   the height of the rectangle to be filled.
     * @see           java.awt.Graphics#clearRect
     * @see           java.awt.Graphics#drawRect
     */
    public void fillRect(int x, int y, int width, int height){
        prepareForeground();
        this.g.fillRect(x, y, width, height);
    }

    /**
     * Draws the outline of the specified rectangle.
     * The left and right edges of the rectangle are at
     * <code>x</code> and <code>x&nbsp;+&nbsp;width</code>.
     * The top and bottom edges are at
     * <code>y</code> and <code>y&nbsp;+&nbsp;height</code>.
     * The rectangle is drawn using the graphics context's current color.
     * @param         x   the <i>x</i> coordinate
     *                         of the rectangle to be drawn.
     * @param         y   the <i>y</i> coordinate
     *                         of the rectangle to be drawn.
     * @param         width   the width of the rectangle to be drawn.
     * @param         height   the height of the rectangle to be drawn.
     * @see          java.awt.Graphics#fillRect
     * @see          java.awt.Graphics#clearRect
     */
    public void drawRect(int x, int y, int width, int height) {
        prepareForeground();
        this.g.drawRect(x, y, width, height);
    }

    /**
     * Clears the specified rectangle by filling it with the background
     * color of the current drawing surface. This operation does not
     * use the current paint mode.
     * <p>
     * Beginning with Java&nbsp;1.1, the background color
     * of offscreen images may be system dependent. Applications should
     * use <code>setColor</code> followed by <code>fillRect</code> to
     * ensure that an offscreen image is cleared to a specific color.
     * @param       x the <i>x</i> coordinate of the rectangle to clear.
     * @param       y the <i>y</i> coordinate of the rectangle to clear.
     * @param       width the width of the rectangle to clear.
     * @param       height the height of the rectangle to clear.
     * @see         java.awt.Graphics#fillRect(int, int, int, int)
     * @see         java.awt.Graphics#drawRect
     * @see         java.awt.Graphics#setColor(java.awt.Color)
     * @see         java.awt.Graphics#setPaintMode
     * @see         java.awt.Graphics#setXORMode(java.awt.Color)
     */
    public void clearRect(int x, int y, int width, int height){
        prepareBackground();
        this.g.fillRect(x, y, width, height);
    }

    /**
     * Draws an outlined round-cornered rectangle using this graphics
     * context's current color. The left and right edges of the rectangle
     * are at <code>x</code> and <code>x&nbsp;+&nbsp;width</code>,
     * respectively. The top and bottom edges of the rectangle are at
     * <code>y</code> and <code>y&nbsp;+&nbsp;height</code>.
     * @param      x the <i>x</i> coordinate of the rectangle to be drawn.
     * @param      y the <i>y</i> coordinate of the rectangle to be drawn.
     * @param      width the width of the rectangle to be drawn.
     * @param      height the height of the rectangle to be drawn.
     * @param      arcWidth the horizontal diameter of the arc
     *                    at the four corners.
     * @param      arcHeight the vertical diameter of the arc
     *                    at the four corners.
     * @see        java.awt.Graphics#fillRoundRect
     */
    public void drawRoundRect(int x, int y, int width, int height,
                                       int arcWidth, int arcHeight){
        prepareForeground();
        this.g.drawRoundRect(x, y, width, height, arcWidth, arcHeight);
    }

    /**
     * Fills the specified rounded corner rectangle with the current color.
     * The left and right edges of the rectangle
     * are at <code>x</code> and <code>x&nbsp;+&nbsp;width&nbsp;-&nbsp;1</code>,
     * respectively. The top and bottom edges of the rectangle are at
     * <code>y</code> and <code>y&nbsp;+&nbsp;height&nbsp;-&nbsp;1</code>.
     * @param       x the <i>x</i> coordinate of the rectangle to be filled.
     * @param       y the <i>y</i> coordinate of the rectangle to be filled.
     * @param       width the width of the rectangle to be filled.
     * @param       height the height of the rectangle to be filled.
     * @param       arcWidth the horizontal diameter
     *                     of the arc at the four corners.
     * @param       arcHeight the vertical diameter
     *                     of the arc at the four corners.
     * @see         java.awt.Graphics#drawRoundRect
     */
    public void fillRoundRect(int x, int y, int width, int height,
                                       int arcWidth, int arcHeight){
        prepareForeground();
        this.g.fillRoundRect(x, y, width, height, arcWidth, arcHeight);
    }

    /**
     * Draws the outline of an oval.
     * The result is a circle or ellipse that fits within the
     * rectangle specified by the <code>x</code>, <code>y</code>,
     * <code>width</code>, and <code>height</code> arguments.
     * <p>
     * The oval covers an area that is
     * <code>width&nbsp;+&nbsp;1</code> pixels wide
     * and <code>height&nbsp;+&nbsp;1</code> pixels tall.
     * @param       x the <i>x</i> coordinate of the upper left
     *                     corner of the oval to be drawn.
     * @param       y the <i>y</i> coordinate of the upper left
     *                     corner of the oval to be drawn.
     * @param       width the width of the oval to be drawn.
     * @param       height the height of the oval to be drawn.
     * @see         java.awt.Graphics#fillOval
     */
    public void drawOval(int x, int y, int width, int height){
        prepareForeground();
        this.g.drawOval(x, y, width, height);
    }

    /**
     * Fills an oval bounded by the specified rectangle with the
     * current color.
     * @param       x the <i>x</i> coordinate of the upper left corner
     *                     of the oval to be filled.
     * @param       y the <i>y</i> coordinate of the upper left corner
     *                     of the oval to be filled.
     * @param       width the width of the oval to be filled.
     * @param       height the height of the oval to be filled.
     * @see         java.awt.Graphics#drawOval
     */
    public void fillOval(int x, int y, int width, int height){
        prepareForeground();
        this.g.fillOval(x, y, width, height);
    }

    /**
     * Draws the outline of a circular or elliptical arc
     * covering the specified rectangle.
     * <p>
     * The resulting arc begins at <code>startAngle</code> and extends
     * for <code>arcAngle</code> degrees, using the current color.
     * Angles are interpreted such that 0&nbsp;degrees
     * is at the 3&nbsp;o'clock position.
     * A positive value indicates a counter-clockwise rotation
     * while a negative value indicates a clockwise rotation.
     * <p>
     * The center of the arc is the center of the rectangle whose origin
     * is (<i>x</i>,&nbsp;<i>y</i>) and whose size is specified by the
     * <code>width</code> and <code>height</code> arguments.
     * <p>
     * The resulting arc covers an area
     * <code>width&nbsp;+&nbsp;1</code> pixels wide
     * by <code>height&nbsp;+&nbsp;1</code> pixels tall.
     * <p>
     * The angles are specified relative to the non-square extents of
     * the bounding rectangle such that 45 degrees always falls on the
     * line from the center of the ellipse to the upper right corner of
     * the bounding rectangle. As a result, if the bounding rectangle is
     * noticeably longer in one axis than the other, the angles to the
     * start and end of the arc segment will be skewed farther along the
     * longer axis of the bounds.
     * @param        x the <i>x</i> coordinate of the
     *                    upper-left corner of the arc to be drawn.
     * @param        y the <i>y</i>  coordinate of the
     *                    upper-left corner of the arc to be drawn.
     * @param        width the width of the arc to be drawn.
     * @param        height the height of the arc to be drawn.
     * @param        startAngle the beginning angle.
     * @param        arcAngle the angular extent of the arc,
     *                    relative to the start angle.
     * @see         java.awt.Graphics#fillArc
     */
    public void drawArc(int x, int y, int width, int height,
                                 int startAngle, int arcAngle){
        prepareForeground();
        this.g.drawArc(x, y, width, height, startAngle, arcAngle);
    }

    /**
     * Fills a circular or elliptical arc covering the specified rectangle.
     * <p>
     * The resulting arc begins at <code>startAngle</code> and extends
     * for <code>arcAngle</code> degrees.
     * Angles are interpreted such that 0&nbsp;degrees
     * is at the 3&nbsp;o'clock position.
     * A positive value indicates a counter-clockwise rotation
     * while a negative value indicates a clockwise rotation.
     * <p>
     * The center of the arc is the center of the rectangle whose origin
     * is (<i>x</i>,&nbsp;<i>y</i>) and whose size is specified by the
     * <code>width</code> and <code>height</code> arguments.
     * <p>
     * The resulting arc covers an area
     * <code>width&nbsp;+&nbsp;1</code> pixels wide
     * by <code>height&nbsp;+&nbsp;1</code> pixels tall.
     * <p>
     * The angles are specified relative to the non-square extents of
     * the bounding rectangle such that 45 degrees always falls on the
     * line from the center of the ellipse to the upper right corner of
     * the bounding rectangle. As a result, if the bounding rectangle is
     * noticeably longer in one axis than the other, the angles to the
     * start and end of the arc segment will be skewed farther along the
     * longer axis of the bounds.
     * @param        x the <i>x</i> coordinate of the
     *                    upper-left corner of the arc to be filled.
     * @param        y the <i>y</i>  coordinate of the
     *                    upper-left corner of the arc to be filled.
     * @param        width the width of the arc to be filled.
     * @param        height the height of the arc to be filled.
     * @param        startAngle the beginning angle.
     * @param        arcAngle the angular extent of the arc,
     *                    relative to the start angle.
     * @see         java.awt.Graphics#drawArc
     */
    public void fillArc(int x, int y, int width, int height,
                                 int startAngle, int arcAngle){
        prepareForeground();
        this.g.fillArc(x, y, width, height, startAngle, arcAngle);
    }

    /**
     * Draws a sequence of connected lines defined by
     * arrays of <i>x</i> and <i>y</i> coordinates.
     * Each pair of (<i>x</i>,&nbsp;<i>y</i>) coordinates defines a point.
     * The figure is not closed if the first point
     * differs from the last point.
     * @param       xPoints an array of <i>x</i> points
     * @param       yPoints an array of <i>y</i> points
     * @param       nPoints the total number of points
     * @see         java.awt.Graphics#drawPolygon(int[], int[], int)
     * @since       JDK1.1
     */
    public void drawPolyline(int xPoints[], int yPoints[],
                                      int nPoints){
        prepareForeground();
        this.g.drawPolyline(xPoints, yPoints, nPoints);
    }

    /**
     * Draws a closed polygon defined by
     * arrays of <i>x</i> and <i>y</i> coordinates.
     * Each pair of (<i>x</i>,&nbsp;<i>y</i>) coordinates defines a point.
     * <p>
     * This method draws the polygon defined by <code>nPoint</code> line
     * segments, where the first <code>nPoint&nbsp;-&nbsp;1</code>
     * line segments are line segments from
     * <code>(xPoints[i&nbsp;-&nbsp;1],&nbsp;yPoints[i&nbsp;-&nbsp;1])</code>
     * to <code>(xPoints[i],&nbsp;yPoints[i])</code>, for
     * 1&nbsp;&le;&nbsp;<i>i</i>&nbsp;&le;&nbsp;<code>nPoints</code>.
     * The figure is automatically closed by drawing a line connecting
     * the final point to the first point, if those points are different.
     * @param        xPoints   a an array of <code>x</code> coordinates.
     * @param        yPoints   a an array of <code>y</code> coordinates.
     * @param        nPoints   a the total number of points.
     * @see          java.awt.Graphics#fillPolygon
     * @see          java.awt.Graphics#drawPolyline
     */
    public void drawPolygon(int xPoints[], int yPoints[],
                                     int nPoints){
        prepareForeground();
        this.g.drawPolygon(xPoints, yPoints, nPoints);
    }

    /**
     * Draws the outline of a polygon defined by the specified
     * <code>Polygon</code> object.
     * @param        p the polygon to draw.
     * @see          java.awt.Graphics#fillPolygon
     * @see          java.awt.Graphics#drawPolyline
     */
    public void drawPolygon(java.awt.Polygon p) {
        prepareForeground();
        this.g.drawPolygon(p);
    }

    /**
     * Fills a closed polygon defined by
     * arrays of <i>x</i> and <i>y</i> coordinates.
     * <p>
     * This method draws the polygon defined by <code>nPoint</code> line
     * segments, where the first <code>nPoint&nbsp;-&nbsp;1</code>
     * line segments are line segments from
     * <code>(xPoints[i&nbsp;-&nbsp;1],&nbsp;yPoints[i&nbsp;-&nbsp;1])</code>
     * to <code>(xPoints[i],&nbsp;yPoints[i])</code>, for
     * 1&nbsp;&le;&nbsp;<i>i</i>&nbsp;&le;&nbsp;<code>nPoints</code>.
     * The figure is automatically closed by drawing a line connecting
     * the final point to the first point, if those points are different.
     * <p>
     * The area inside the polygon is defined using an
     * even-odd fill rule, also known as the alternating rule.
     * @param        xPoints   a an array of <code>x</code> coordinates.
     * @param        yPoints   a an array of <code>y</code> coordinates.
     * @param        nPoints   a the total number of points.
     * @see          java.awt.Graphics#drawPolygon(int[], int[], int)
     */
    public void fillPolygon(int xPoints[], int yPoints[],
                                     int nPoints){
        prepareForeground();
        this.g.fillPolygon(xPoints, yPoints, nPoints);
    }

    /**
     * Fills the polygon defined by the specified Polygon object with
     * the graphics context's current color.
     * <p>
     * The area inside the polygon is defined using an
     * even-odd fill rule, also known as the alternating rule.
     * @param        p the polygon to fill.
     * @see          java.awt.Graphics#drawPolygon(int[], int[], int)
     */
    public void fillPolygon(java.awt.Polygon p) {
        prepareForeground();
        this.g.fillPolygon(p);
    }

    /**
     * Draws the text given by the specified string, using this
     * graphics context's current font and color. The baseline of the
     * leftmost character is at position (<i>x</i>,&nbsp;<i>y</i>) in this
     * graphics context's coordinate system.
     * @param       str      the string to be drawn.
     * @param       x        the <i>x</i> coordinate.
     * @param       y        the <i>y</i> coordinate.
     * @throws NullPointerException if <code>str</code> is <code>null</code>.
     * @see         java.awt.Graphics#drawBytes
     * @see         java.awt.Graphics#drawChars
     */
    public void drawString(String str, int x, int y){
        prepareForeground();
        this.g.drawString(str, x, y);
    }

    /**
     * Renders the text specified by the specified <code>String</code>,
     * using the current text attribute state in the <code>Graphics2D</code> context.
     * The baseline of the first character is at position
     * (<i>x</i>,&nbsp;<i>y</i>) in the User Space.
     * The rendering attributes applied include the <code>Clip</code>,
     * <code>Transform</code>, <code>Paint</code>, <code>Font</code> and
     * <code>Composite</code> attributes. For characters in script systems
     * such as Hebrew and Arabic, the glyphs can be rendered from right to
     * left, in which case the coordinate supplied is the location of the
     * leftmost character on the baseline.
     * @param str the <code>String</code> to be rendered
     * @param x the x coordinate of the location where the
     * <code>String</code> should be rendered
     * @param y the y coordinate of the location where the
     * <code>String</code> should be rendered
     * @throws NullPointerException if <code>str</code> is
     *         <code>null</code>
     * @see #setPaint
     * @see java.awt.Graphics#setColor
     * @see java.awt.Graphics#setFont
     * @see #setTransform
     * @see #setComposite
     */
    public void drawString(String str, float x, float y){
        prepareForeground();
        this.g.drawString(str, x, y);
    }

    /**
     * Renders the text of the specified iterator applying its attributes
     * in accordance with the specification of the
     * {@link java.awt.font.TextAttribute TextAttribute} class.
     * <p>
     * The baseline of the leftmost character is at position
     * (<i>x</i>,&nbsp;<i>y</i>) in this graphics context's coordinate system.
     * @param       iterator the iterator whose text is to be drawn
     * @param       x        the <i>x</i> coordinate.
     * @param       y        the <i>y</i> coordinate.
     * @throws NullPointerException if <code>iterator</code> is
     * <code>null</code>.
     * @see         java.awt.Graphics#drawBytes
     * @see         java.awt.Graphics#drawChars
     */
    public void drawString(java.text.AttributedCharacterIterator iterator,
                                    int x, int y){
        prepareForeground();
        this.g.drawString(iterator, x, y);
    }

    /**
     * Renders the text of the specified iterator applying its attributes
     * in accordance with the specification of the {@link java.awt.font.TextAttribute} class.
     * <p>
     * The baseline of the first character is at position
     * (<i>x</i>,&nbsp;<i>y</i>) in User Space.
     * For characters in script systems such as Hebrew and Arabic,
     * the glyphs can be rendered from right to left, in which case the
     * coordinate supplied is the location of the leftmost character
     * on the baseline.
     * @param iterator the iterator whose text is to be rendered
     * @param x the x coordinate where the iterator's text is to be
     * rendered
     * @param y the y coordinate where the iterator's text is to be
     * rendered
     * @throws NullPointerException if <code>iterator</code> is
     *         <code>null</code>
     * @see #setPaint
     * @see java.awt.Graphics#setColor
     * @see #setTransform
     * @see #setComposite
     */
    public void drawString(java.text.AttributedCharacterIterator iterator,
                                    float x, float y){
        prepareForeground();
        this.g.drawString(iterator, x, y);
    }

    /**
     * Renders the text of the specified
     * {@link java.awt.font.GlyphVector} using
     * the <code>Graphics2D</code> context's rendering attributes.
     * The rendering attributes applied include the <code>Clip</code>,
     * <code>Transform</code>, <code>Paint</code>, and
     * <code>Composite</code> attributes.  The <code>GlyphVector</code>
     * specifies individual glyphs from a {@link java.awt.Font}.
     * The <code>GlyphVector</code> can also contain the glyph positions.
     * This is the fastest way to render a set of characters to the
     * screen.
     * @param g the <code>GlyphVector</code> to be rendered
     * @param x the x position in User Space where the glyphs should
     * be rendered
     * @param y the y position in User Space where the glyphs should
     * be rendered
     * @throws NullPointerException if <code>g</code> is <code>null</code>.
     *
     * @see java.awt.Font#createGlyphVector
     * @see java.awt.font.GlyphVector
     * @see #setPaint
     * @see java.awt.Graphics#setColor
     * @see #setTransform
     * @see #setComposite
     */
    public void drawGlyphVector(java.awt.font.GlyphVector g, float x, float y){
        prepareForeground();
        this.g.drawGlyphVector(g, x, y);
    }

    /**
     * Draws the text given by the specified character array, using this
     * graphics context's current font and color. The baseline of the
     * first character is at position (<i>x</i>,&nbsp;<i>y</i>) in this
     * graphics context's coordinate system.
     * @param data the array of characters to be drawn
     * @param offset the start offset in the data
     * @param length the number of characters to be drawn
     * @param x the <i>x</i> coordinate of the baseline of the text
     * @param y the <i>y</i> coordinate of the baseline of the text
     * @throws NullPointerException if <code>data</code> is <code>null</code>.
     * @throws IndexOutOfBoundsException if <code>offset</code> or
     * <code>length</code>is less than zero, or
     * <code>offset+length</code> is greater than the length of the
     * <code>data</code> array.
     * @see         java.awt.Graphics#drawBytes
     * @see         java.awt.Graphics#drawString
     */
    public void drawChars(char data[], int offset, int length, int x, int y) {
        prepareForeground();
        this.g.drawChars(data, offset, length, x, y);
    }

    /**
     * Draws the text given by the specified byte array, using this
     * graphics context's current font and color. The baseline of the
     * first character is at position (<i>x</i>,&nbsp;<i>y</i>) in this
     * graphics context's coordinate system.
     * <p>
     * Use of this method is not recommended as each byte is interpreted
     * as a Unicode code point in the range 0 to 255, and so can only be
     * used to draw Latin characters in that range.
     * @param data the data to be drawn
     * @param offset the start offset in the data
     * @param length the number of bytes that are drawn
     * @param x the <i>x</i> coordinate of the baseline of the text
     * @param y the <i>y</i> coordinate of the baseline of the text
     * @throws NullPointerException if <code>data</code> is <code>null</code>.
     * @throws IndexOutOfBoundsException if <code>offset</code> or
     * <code>length</code>is less than zero, or <code>offset+length</code>
     * is greater than the length of the <code>data</code> array.
     * @see         java.awt.Graphics#drawChars
     * @see         java.awt.Graphics#drawString
     */
    public void drawBytes(byte data[], int offset, int length, int x, int y) {
        prepareForeground();
        this.g.drawBytes(data, offset, length, x, y);
    }

    /**
     * Draws as much of the specified image as is currently available.
     * The image is drawn with its top-left corner at
     * (<i>x</i>,&nbsp;<i>y</i>) in this graphics context's coordinate
     * space. Transparent pixels in the image do not affect whatever
     * pixels are already there.
     * <p>
     * This method returns immediately in all cases, even if the
     * complete image has not yet been loaded, and it has not been dithered
     * and converted for the current output device.
     * <p>
     * If the image has completely loaded and its pixels are
     * no longer being changed, then
     * <code>drawImage</code> returns <code>true</code>.
     * Otherwise, <code>drawImage</code> returns <code>false</code>
     * and as more of
     * the image becomes available
     * or it is time to draw another frame of animation,
     * the process that loads the image notifies
     * the specified image observer.
     * @param    img the specified image to be drawn. This method does
     *               nothing if <code>img</code> is null.
     * @param    x   the <i>x</i> coordinate.
     * @param    y   the <i>y</i> coordinate.
     * @param    observer    object to be notified as more of
     *                          the image is converted.
     * @return   <code>false</code> if the image pixels are still changing;
     *           <code>true</code> otherwise.
     * @see      java.awt.Image
     * @see      java.awt.image.ImageObserver
     * @see      java.awt.image.ImageObserver#imageUpdate(java.awt.Image, int, int, int, int, int)
     */
    public boolean drawImage(java.awt.Image img, int x, int y,
                                      java.awt.image.ImageObserver observer){
        prepareForeground();
        img = transformImageToBinaryImg(img);
        return this.g.drawImage(img, x, y, observer);
    }

    /**
     * Draws as much of the specified image as has already been scaled
     * to fit inside the specified rectangle.
     * <p>
     * The image is drawn inside the specified rectangle of this
     * graphics context's coordinate space, and is scaled if
     * necessary. Transparent pixels do not affect whatever pixels
     * are already there.
     * <p>
     * This method returns immediately in all cases, even if the
     * entire image has not yet been scaled, dithered, and converted
     * for the current output device.
     * If the current output representation is not yet complete, then
     * <code>drawImage</code> returns <code>false</code>. As more of
     * the image becomes available, the process that loads the image notifies
     * the image observer by calling its <code>imageUpdate</code> method.
     * <p>
     * A scaled version of an image will not necessarily be
     * available immediately just because an unscaled version of the
     * image has been constructed for this output device.  Each size of
     * the image may be cached separately and generated from the original
     * data in a separate image production sequence.
     * @param    img    the specified image to be drawn. This method does
     *                  nothing if <code>img</code> is null.
     * @param    x      the <i>x</i> coordinate.
     * @param    y      the <i>y</i> coordinate.
     * @param    width  the width of the rectangle.
     * @param    height the height of the rectangle.
     * @param    observer    object to be notified as more of
     *                          the image is converted.
     * @return   <code>false</code> if the image pixels are still changing;
     *           <code>true</code> otherwise.
     * @see      java.awt.Image
     * @see      java.awt.image.ImageObserver
     * @see      java.awt.image.ImageObserver#imageUpdate(java.awt.Image, int, int, int, int, int)
     */
    public boolean drawImage(java.awt.Image img, int x, int y,
                                      int width, int height,
                                      java.awt.image.ImageObserver observer){
        prepareForeground();
        img = transformImageToBinaryImg(img);
        return this.g.drawImage(img, x, y, width, height, observer);
    }

    /**
     * Draws as much of the specified image as is currently available.
     * The image is drawn with its top-left corner at
     * (<i>x</i>,&nbsp;<i>y</i>) in this graphics context's coordinate
     * space.  Transparent pixels are drawn in the specified
     * background color.
     * <p>
     * This operation is equivalent to filling a rectangle of the
     * width and height of the specified image with the given color and then
     * drawing the image on top of it, but possibly more efficient.
     * <p>
     * This method returns immediately in all cases, even if the
     * complete image has not yet been loaded, and it has not been dithered
     * and converted for the current output device.
     * <p>
     * If the image has completely loaded and its pixels are
     * no longer being changed, then
     * <code>drawImage</code> returns <code>true</code>.
     * Otherwise, <code>drawImage</code> returns <code>false</code>
     * and as more of
     * the image becomes available
     * or it is time to draw another frame of animation,
     * the process that loads the image notifies
     * the specified image observer.
     * @param    img the specified image to be drawn. This method does
     *               nothing if <code>img</code> is null.
     * @param    x      the <i>x</i> coordinate.
     * @param    y      the <i>y</i> coordinate.
     * @param    bgcolor the background color to paint under the
     *                         non-opaque portions of the image.
     * @param    observer    object to be notified as more of
     *                          the image is converted.
     * @return   <code>false</code> if the image pixels are still changing;
     *           <code>true</code> otherwise.
     * @see      java.awt.Image
     * @see      java.awt.image.ImageObserver
     * @see      java.awt.image.ImageObserver#imageUpdate(java.awt.Image, int, int, int, int, int)
     */
    public boolean drawImage(java.awt.Image img, int x, int y,
                                      java.awt.Color bgcolor,
                                      java.awt.image.ImageObserver observer){
        prepareForeground();
        img = transformImageToBinaryImg(img);
        return this.g.drawImage(img, x, y, bgcolor, observer);
    }

    /**
     * Draws as much of the specified image as has already been scaled
     * to fit inside the specified rectangle.
     * <p>
     * The image is drawn inside the specified rectangle of this
     * graphics context's coordinate space, and is scaled if
     * necessary. Transparent pixels are drawn in the specified
     * background color.
     * This operation is equivalent to filling a rectangle of the
     * width and height of the specified image with the given color and then
     * drawing the image on top of it, but possibly more efficient.
     * <p>
     * This method returns immediately in all cases, even if the
     * entire image has not yet been scaled, dithered, and converted
     * for the current output device.
     * If the current output representation is not yet complete then
     * <code>drawImage</code> returns <code>false</code>. As more of
     * the image becomes available, the process that loads the image notifies
     * the specified image observer.
     * <p>
     * A scaled version of an image will not necessarily be
     * available immediately just because an unscaled version of the
     * image has been constructed for this output device.  Each size of
     * the image may be cached separately and generated from the original
     * data in a separate image production sequence.
     * @param    img       the specified image to be drawn. This method does
     *                     nothing if <code>img</code> is null.
     * @param    x         the <i>x</i> coordinate.
     * @param    y         the <i>y</i> coordinate.
     * @param    width     the width of the rectangle.
     * @param    height    the height of the rectangle.
     * @param    bgcolor   the background color to paint under the
     *                         non-opaque portions of the image.
     * @param    observer    object to be notified as more of
     *                          the image is converted.
     * @return   <code>false</code> if the image pixels are still changing;
     *           <code>true</code> otherwise.
     * @see      java.awt.Image
     * @see      java.awt.image.ImageObserver
     * @see      java.awt.image.ImageObserver#imageUpdate(java.awt.Image, int, int, int, int, int)
     */
    public boolean drawImage(java.awt.Image img, int x, int y,
                                      int width, int height,
                                      java.awt.Color bgcolor,
                                      java.awt.image.ImageObserver observer){
        prepareForeground();
        img = transformImageToBinaryImg(img);
        return this.g.drawImage(img, x, y, width, height, bgcolor, observer);
    }

    /**
     * Draws as much of the specified area of the specified image as is
     * currently available, scaling it on the fly to fit inside the
     * specified area of the destination drawable surface. Transparent pixels
     * do not affect whatever pixels are already there.
     * <p>
     * This method returns immediately in all cases, even if the
     * image area to be drawn has not yet been scaled, dithered, and converted
     * for the current output device.
     * If the current output representation is not yet complete then
     * <code>drawImage</code> returns <code>false</code>. As more of
     * the image becomes available, the process that loads the image notifies
     * the specified image observer.
     * <p>
     * This method always uses the unscaled version of the image
     * to render the scaled rectangle and performs the required
     * scaling on the fly. It does not use a cached, scaled version
     * of the image for this operation. Scaling of the image from source
     * to destination is performed such that the first coordinate
     * of the source rectangle is mapped to the first coordinate of
     * the destination rectangle, and the second source coordinate is
     * mapped to the second destination coordinate. The subimage is
     * scaled and flipped as needed to preserve those mappings.
     * @param       img the specified image to be drawn. This method does
     *                  nothing if <code>img</code> is null.
     * @param       dx1 the <i>x</i> coordinate of the first corner of the
     *                    destination rectangle.
     * @param       dy1 the <i>y</i> coordinate of the first corner of the
     *                    destination rectangle.
     * @param       dx2 the <i>x</i> coordinate of the second corner of the
     *                    destination rectangle.
     * @param       dy2 the <i>y</i> coordinate of the second corner of the
     *                    destination rectangle.
     * @param       sx1 the <i>x</i> coordinate of the first corner of the
     *                    source rectangle.
     * @param       sy1 the <i>y</i> coordinate of the first corner of the
     *                    source rectangle.
     * @param       sx2 the <i>x</i> coordinate of the second corner of the
     *                    source rectangle.
     * @param       sy2 the <i>y</i> coordinate of the second corner of the
     *                    source rectangle.
     * @param       observer object to be notified as more of the image is
     *                    scaled and converted.
     * @return   <code>false</code> if the image pixels are still changing;
     *           <code>true</code> otherwise.
     * @see         java.awt.Image
     * @see         java.awt.image.ImageObserver
     * @see         java.awt.image.ImageObserver#imageUpdate(java.awt.Image, int, int, int, int, int)
     * @since       JDK1.1
     */
    public boolean drawImage(java.awt.Image img,
                                      int dx1, int dy1, int dx2, int dy2,
                                      int sx1, int sy1, int sx2, int sy2,
                                      java.awt.image.ImageObserver observer){
        prepareForeground();
        img = transformImageToBinaryImg(img);
        return this.g.drawImage(img, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, observer);
    }

    /**
     * Draws as much of the specified area of the specified image as is
     * currently available, scaling it on the fly to fit inside the
     * specified area of the destination drawable surface.
     * <p>
     * Transparent pixels are drawn in the specified background color.
     * This operation is equivalent to filling a rectangle of the
     * width and height of the specified image with the given color and then
     * drawing the image on top of it, but possibly more efficient.
     * <p>
     * This method returns immediately in all cases, even if the
     * image area to be drawn has not yet been scaled, dithered, and converted
     * for the current output device.
     * If the current output representation is not yet complete then
     * <code>drawImage</code> returns <code>false</code>. As more of
     * the image becomes available, the process that loads the image notifies
     * the specified image observer.
     * <p>
     * This method always uses the unscaled version of the image
     * to render the scaled rectangle and performs the required
     * scaling on the fly. It does not use a cached, scaled version
     * of the image for this operation. Scaling of the image from source
     * to destination is performed such that the first coordinate
     * of the source rectangle is mapped to the first coordinate of
     * the destination rectangle, and the second source coordinate is
     * mapped to the second destination coordinate. The subimage is
     * scaled and flipped as needed to preserve those mappings.
     * @param       img the specified image to be drawn. This method does
     *                  nothing if <code>img</code> is null.
     * @param       dx1 the <i>x</i> coordinate of the first corner of the
     *                    destination rectangle.
     * @param       dy1 the <i>y</i> coordinate of the first corner of the
     *                    destination rectangle.
     * @param       dx2 the <i>x</i> coordinate of the second corner of the
     *                    destination rectangle.
     * @param       dy2 the <i>y</i> coordinate of the second corner of the
     *                    destination rectangle.
     * @param       sx1 the <i>x</i> coordinate of the first corner of the
     *                    source rectangle.
     * @param       sy1 the <i>y</i> coordinate of the first corner of the
     *                    source rectangle.
     * @param       sx2 the <i>x</i> coordinate of the second corner of the
     *                    source rectangle.
     * @param       sy2 the <i>y</i> coordinate of the second corner of the
     *                    source rectangle.
     * @param       bgcolor the background color to paint under the
     *                    non-opaque portions of the image.
     * @param       observer object to be notified as more of the image is
     *                    scaled and converted.
     * @return   <code>false</code> if the image pixels are still changing;
     *           <code>true</code> otherwise.
     * @see         java.awt.Image
     * @see         java.awt.image.ImageObserver
     * @see         java.awt.image.ImageObserver#imageUpdate(java.awt.Image, int, int, int, int, int)
     * @since       JDK1.1
     */
    public boolean drawImage(java.awt.Image img,
                                      int dx1, int dy1, int dx2, int dy2,
                                      int sx1, int sy1, int sx2, int sy2,
                                      java.awt.Color bgcolor,
                                      java.awt.image.ImageObserver observer){
        prepareForeground();
        img = transformImageToBinaryImg(img);
        return this.g.drawImage(img, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, bgcolor, observer);
    }

    /**
     * Returns the device configuration associated with this
     * <code>Graphics2D</code>.
     * @return the device configuration of this <code>Graphics2D</code>.
     */
    public java.awt.GraphicsConfiguration getDeviceConfiguration(){
        return this.g.getDeviceConfiguration();
    }

    /**
     * Sets the <code>Composite</code> for the <code>Graphics2D</code> context.
     * The <code>Composite</code> is used in all drawing methods such as
     * <code>drawImage</code>, <code>drawString</code>, <code>draw</code>,
     * and <code>fill</code>.  It specifies how new pixels are to be combined
     * with the existing pixels on the graphics device during the rendering
     * process.
     * <p>If this <code>Graphics2D</code> context is drawing to a
     * <code>Component</code> on the display screen and the
     * <code>Composite</code> is a custom object rather than an
     * instance of the <code>AlphaComposite</code> class, and if
     * there is a security manager, its <code>checkPermission</code>
     * method is called with an <code>AWTPermission("readDisplayPixels")</code>
     * permission.
     * @throws SecurityException
     *         if a custom <code>Composite</code> object is being
     *         used to render to the screen and a security manager
     *         is set and its <code>checkPermission</code> method
     *         does not allow the operation.
     * @param comp the <code>Composite</code> object to be used for rendering
     * @see java.awt.Graphics#setXORMode
     * @see java.awt.Graphics#setPaintMode
     * @see #getComposite
     * @see java.awt.AlphaComposite
     * @see SecurityManager#checkPermission
     * @see java.awt.AWTPermission
     */
    public void setComposite(java.awt.Composite comp){
        prepareForeground();
        this.g.setComposite(comp);
    }

    /**
     * Sets the <code>Paint</code> attribute for the
     * <code>Graphics2D</code> context.  Calling this method
     * with a <code>null</code> <code>Paint</code> object does
     * not have any effect on the current <code>Paint</code> attribute
     * of this <code>Graphics2D</code>.
     * @param paint the <code>Paint</code> object to be used to generate
     * color during the rendering process, or <code>null</code>
     * @see java.awt.Graphics#setColor
     * @see #getPaint
     * @see java.awt.GradientPaint
     * @see java.awt.TexturePaint
     */
    public void setPaint(java.awt.Paint paint){
        prepareForeground();
        this.g.setPaint(paint);
    }

    /**
     * Sets the <code>Stroke</code> for the <code>Graphics2D</code> context.
     * @param s the <code>Stroke</code> object to be used to stroke a
     * <code>Shape</code> during the rendering process
     * @see java.awt.BasicStroke
     * @see #getStroke
     */
    public void setStroke(java.awt.Stroke s){
        prepareForeground();
        this.g.setStroke(s);
    }

    /**
     * Sets the value of a single preference for the rendering algorithms.
     * Hint categories include controls for rendering quality and overall
     * time/quality trade-off in the rendering process.  Refer to the
     * <code>RenderingHints</code> class for definitions of some common
     * keys and values.
     * @param hintKey the key of the hint to be set.
     * @param hintValue the value indicating preferences for the specified
     * hint category.
     * @see #getRenderingHint(RenderingHints.Key)
     * @see java.awt.RenderingHints
     */
    public void setRenderingHint(java.awt.RenderingHints.Key hintKey, Object hintValue){
        prepareForeground();
        this.g.setRenderingHint(hintKey, hintValue);
    }

    /**
     * Returns the value of a single preference for the rendering algorithms.
     * Hint categories include controls for rendering quality and overall
     * time/quality trade-off in the rendering process.  Refer to the
     * <code>RenderingHints</code> class for definitions of some common
     * keys and values.
     * @param hintKey the key corresponding to the hint to get.
     * @return an object representing the value for the specified hint key.
     * Some of the keys and their associated values are defined in the
     * <code>RenderingHints</code> class.
     * @see java.awt.RenderingHints
     * @see #setRenderingHint(RenderingHints.Key, Object)
     */
    public Object getRenderingHint(java.awt.RenderingHints.Key hintKey){
        return this.g.getRenderingHint(hintKey);
    }

    /**
     * Replaces the values of all preferences for the rendering
     * algorithms with the specified <code>hints</code>.
     * The existing values for all rendering hints are discarded and
     * the new set of known hints and values are initialized from the
     * specified {@link java.util.Map} object.
     * Hint categories include controls for rendering quality and
     * overall time/quality trade-off in the rendering process.
     * Refer to the <code>RenderingHints</code> class for definitions of
     * some common keys and values.
     * @param hints the rendering hints to be set
     * @see #getRenderingHints
     * @see java.awt.RenderingHints
     */
    public void setRenderingHints(java.util.Map<?,?> hints){
        prepareForeground();
        this.g.setRenderingHints(hints);
    }

    /**
     * Sets the values of an arbitrary number of preferences for the
     * rendering algorithms.
     * Only values for the rendering hints that are present in the
     * specified <code>Map</code> object are modified.
     * All other preferences not present in the specified
     * object are left unmodified.
     * Hint categories include controls for rendering quality and
     * overall time/quality trade-off in the rendering process.
     * Refer to the <code>RenderingHints</code> class for definitions of
     * some common keys and values.
     * @param hints the rendering hints to be set
     * @see java.awt.RenderingHints
     */
    public void addRenderingHints(java.util.Map<?,?> hints){
        prepareForeground();
        this.g.addRenderingHints(hints);
    }

    /**
     * Gets the preferences for the rendering algorithms.  Hint categories
     * include controls for rendering quality and overall time/quality
     * trade-off in the rendering process.
     * Returns all of the hint key/value pairs that were ever specified in
     * one operation.  Refer to the
     * <code>RenderingHints</code> class for definitions of some common
     * keys and values.
     * @return a reference to an instance of <code>RenderingHints</code>
     * that contains the current preferences.
     * @see java.awt.RenderingHints
     * @see #setRenderingHints(Map)
     */
    public java.awt.RenderingHints getRenderingHints(){
        return this.g.getRenderingHints();
    }

    /**
     * Concatenates the current <code>Graphics2D</code>
     * <code>Transform</code> with a rotation transform.
     * Subsequent rendering is rotated by the specified radians relative
     * to the previous origin.
     * This is equivalent to calling <code>transform(R)</code>, where R is an
     * <code>AffineTransform</code> represented by the following matrix:
     * <pre>
     *          [   cos(theta)    -sin(theta)    0   ]
     *          [   sin(theta)     cos(theta)    0   ]
     *          [       0              0         1   ]
     * </pre>
     * Rotating with a positive angle theta rotates points on the positive
     * x axis toward the positive y axis.
     * @param theta the angle of rotation in radians
     */
    public void rotate(double theta){
        prepareForeground();
        this.g.rotate(theta);
    }

    /**
     * Concatenates the current <code>Graphics2D</code>
     * <code>Transform</code> with a translated rotation
     * transform.  Subsequent rendering is transformed by a transform
     * which is constructed by translating to the specified location,
     * rotating by the specified radians, and translating back by the same
     * amount as the original translation.  This is equivalent to the
     * following sequence of calls:
     * <pre>
     *          translate(x, y);
     *          rotate(theta);
     *          translate(-x, -y);
     * </pre>
     * Rotating with a positive angle theta rotates points on the positive
     * x axis toward the positive y axis.
     * @param theta the angle of rotation in radians
     * @param x the x coordinate of the origin of the rotation
     * @param y the y coordinate of the origin of the rotation
     */
    public void rotate(double theta, double x, double y){
        prepareForeground();
        this.g.rotate(theta, x, y);
    }

    /**
     * Composes an <code>AffineTransform</code> object with the
     * <code>Transform</code> in this <code>Graphics2D</code> according
     * to the rule last-specified-first-applied.  If the current
     * <code>Transform</code> is Cx, the result of composition
     * with Tx is a new <code>Transform</code> Cx'.  Cx' becomes the
     * current <code>Transform</code> for this <code>Graphics2D</code>.
     * Transforming a point p by the updated <code>Transform</code> Cx' is
     * equivalent to first transforming p by Tx and then transforming
     * the result by the original <code>Transform</code> Cx.  In other
     * words, Cx'(p) = Cx(Tx(p)).  A copy of the Tx is made, if necessary,
     * so further modifications to Tx do not affect rendering.
     * @param Tx the <code>AffineTransform</code> object to be composed with
     * the current <code>Transform</code>
     * @see #setTransform
     * @see java.awt.geom.AffineTransform
     */
    public void transform(java.awt.geom.AffineTransform Tx){
        prepareForeground();
        this.g.transform(Tx);
    }

    /**
     * Overwrites the Transform in the <code>Graphics2D</code> context.
     * WARNING: This method should <b>never</b> be used to apply a new
     * coordinate transform on top of an existing transform because the
     * <code>Graphics2D</code> might already have a transform that is
     * needed for other purposes, such as rendering Swing
     * components or applying a scaling transformation to adjust for the
     * resolution of a printer.
     * <p>To add a coordinate transform, use the
     * <code>transform</code>, <code>rotate</code>, <code>scale</code>,
     * or <code>shear</code> methods.  The <code>setTransform</code>
     * method is intended only for restoring the original
     * <code>Graphics2D</code> transform after rendering, as shown in this
     * example:
     * <pre>
     * // Get the current transform
     * AffineTransform saveAT = g2.getTransform();
     * // Perform transformation
     * g2d.transform(...);
     * // Render
     * g2d.draw(...);
     * // Restore original transform
     * g2d.setTransform(saveAT);
     * </pre>
     *
     * @param Tx the <code>AffineTransform</code> that was retrieved
     *           from the <code>getTransform</code> method
     * @see #transform
     * @see #getTransform
     * @see java.awt.geom.AffineTransform
     */
    public void setTransform(java.awt.geom.AffineTransform Tx){
        prepareForeground();
        this.g.setTransform(Tx);
    }

    /**
     * Returns a copy of the current <code>Transform</code> in the
     * <code>Graphics2D</code> context.
     * @return the current <code>AffineTransform</code> in the
     *             <code>Graphics2D</code> context.
     * @see #transform
     * @see #setTransform
     */
    public java.awt.geom.AffineTransform getTransform(){
        return this.g.getTransform();
    }

    /**
     * Returns the current <code>Paint</code> of the
     * <code>Graphics2D</code> context.
     * @return the current <code>Graphics2D</code> <code>Paint</code>,
     * which defines a color or pattern.
     * @see #setPaint
     * @see java.awt.Graphics#setColor
     */
    public java.awt.Paint getPaint(){
        return this.g.getPaint();
    }

    /**
     * Returns the current <code>Composite</code> in the
     * <code>Graphics2D</code> context.
     * @return the current <code>Graphics2D</code> <code>Composite</code>,
     *              which defines a compositing style.
     * @see #setComposite
     */
    public java.awt.Composite getComposite(){
        return this.g.getComposite();
    }

    /**
     * Returns the current <code>Stroke</code> in the
     * <code>Graphics2D</code> context.
     * @return the current <code>Graphics2D</code> <code>Stroke</code>,
     *                 which defines the line style.
     * @see #setStroke
     */
    public java.awt.Stroke getStroke(){
        return this.g.getStroke();
    }

    /**
     * Disposes of this graphics context and releases
     * any system resources that it is using.
     * A <code>Graphics</code> object cannot be used after
     * <code>dispose</code>has been called.
     * <p>
     * When a Java program runs, a large number of <code>Graphics</code>
     * objects can be created within a short time frame.
     * Although the finalization process of the garbage collector
     * also disposes of the same system resources, it is preferable
     * to manually free the associated resources by calling this
     * method rather than to rely on a finalization process which
     * may not run to completion for a long period of time.
     * <p>
     * Graphics objects which are provided as arguments to the
     * <code>paint</code> and <code>update</code> methods
     * of components are automatically released by the system when
     * those methods return. For efficiency, programmers should
     * call <code>dispose</code> when finished using
     * a <code>Graphics</code> object only if it was created
     * directly from a component or another <code>Graphics</code> object.
     * @see         java.awt.Graphics#finalize
     * @see         java.awt.Component#paint
     * @see         java.awt.Component#update
     * @see         java.awt.Component#getGraphics
     * @see         java.awt.Graphics#create
     */
    public void dispose(){
        this.g.dispose();
    }

    /**
     * Disposes of this graphics context once it is no longer referenced.
     * @see #dispose
     */
    @Override
    @SuppressWarnings({"FinalizeDeclaration", "FinalizeDoesntCallSuperFinalize"})
    public void finalize() {
        dispose();
    }
    
    
    
//METHODES PRIVATES
    /**
     * Donne un BufferedImage d'une image
     * @param img correspond à l'image à traduire en BufferedImage
     * @return Retourne un BufferedImage
     */
    private java.awt.image.BufferedImage toBufferedImage(java.awt.Image img){
        if(img!=null){
            if (img instanceof java.awt.image.BufferedImage) return (java.awt.image.BufferedImage) img;

            // Create a buffered image with transparency
            java.awt.image.BufferedImage bimage = new java.awt.image.BufferedImage(img.getWidth(null), img.getHeight(null), java.awt.image.BufferedImage.TYPE_INT_ARGB);

            // Draw the image on to the buffered image
            java.awt.Graphics2D bGr = bimage.createGraphics();
            bGr.drawImage(img, 0, 0, null);
            bGr.dispose();

            // Return the buffered image
            return bimage;
        }else return null;
    }
    
    /**
     * Modifie les couleurs d'une image. Il n'y aura plus que deux couleurs
     * @param img Correspond à l'image à recoloriser
     * @return Retourne l'image recolorisée
     */
    private java.awt.Image transformImageToBinaryImg(java.awt.Image img){
        java.awt.image.BufferedImage bi = toBufferedImage(img);
        int c;
        if(this.mode == ModeArea.ADDITIVE)
            c = new java.awt.Color(foreground[0], foreground[1], foreground[2]).getRGB();
        else
            c = new java.awt.Color(background[0], background[1], background[2]).getRGB();
        int a = getColorAlpha();
        for (int i = 0; i < bi.getWidth(); i++) {
            for (int j = 0; j < bi.getHeight(); j++) {
                java.awt.Color color = new java.awt.Color(bi.getRGB(i, j), true);
                if(this.mode == ModeArea.ADDITIVE){
                    if(color.getAlpha() == 0x00)
                        bi.setRGB(i, j, a);
                    else
                        bi.setRGB(i, j, c);
                }else{
                    if(color.getAlpha() == 0x00)
                        bi.setRGB(i, j, c);
                    else
                        bi.setRGB(i, j, a);
                }
            }
        }
        return bi;
    }
    
    /**
     * Crée une copie d'un BufferedImage
     * @param bi Correspond au BufferedImage à copier
     * @return Retourne la copie du BufferedImage
     */
    private java.awt.image.BufferedImage copy(java.awt.image.BufferedImage bi) {
        java.awt.image.ColorModel cm = bi.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        java.awt.image.WritableRaster raster = bi.copyData(null);
        return new java.awt.image.BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }
    
    /**
     * Inverse les couleurs d'une image passé dans une méthode de recolorisation telle que {@link #transformImageToBinaryImg(java.awt.Image)}
     * @param img Correspond à l'image qui ne possède que deux couleurs et dont celles-ci vont être inversée
     * @return Retourne l'image avec les couleurs inversée
     */
    private java.awt.image.BufferedImage reverseImageToBinaryImg(java.awt.image.BufferedImage img){
        java.awt.image.BufferedImage bi = copy(img);
        java.awt.Color f = new java.awt.Color(this.foreground[0], this.foreground[1], this.foreground[2]);
        java.awt.Color b = new java.awt.Color(this.background[0], this.background[1], this.background[2]);
        int rgbForeground = f.getRGB();
        int rgbBackground = b.getRGB();
        for (int i = 0; i < bi.getWidth(); i++) {
            for (int j = 0; j < bi.getHeight(); j++) {
                java.awt.Color color = new java.awt.Color(bi.getRGB(i, j));
                if(color.equals(f))
                    bi.setRGB(i, j, rgbBackground);
                else
                    bi.setRGB(i, j, rgbForeground);
            }
        }
        return bi;
    }
    
    /**
     * Charge une zone complète à partir d'une image représentant le carbone négatif
     * @param bi Correspond au carbone négatif
     * @return Retourne true si lors du chargement, des transformations ont du avoir lieu (si par exemple la couleur alpha a été supprimée...) sinon false
     */
    private boolean loadNegativeCarbon(java.awt.image.BufferedImage bi){
        bi = copy(bi);
        java.awt.Color c1 = new java.awt.Color(foreground[0], foreground[1], foreground[2]); // BLACK
        java.awt.Color c2 = new java.awt.Color(background[0], background[1], background[2]); // WHITE
        int c1rgb = c1.getRGB();
        int c2rgb = c2.getRGB();
        boolean changed = false;
        for (int i = 0; i < bi.getWidth(); i++) {
            for (int j = 0; j < bi.getHeight(); j++) {
                java.awt.Color color = new java.awt.Color(bi.getRGB(i, j), true);
                if(color.getAlpha() == 0x00){
                    if(this.mode == ModeArea.ADDITIVE)
                        bi.setRGB(i, j, c2rgb);
                    else
                        bi.setRGB(i, j, c1rgb);
                    changed = true;
                }else{
                    if(color.equals(c1) || color.equals(c2)){
                        continue;
                    }else{
                        if(this.mode == ModeArea.ADDITIVE)
                            bi.setRGB(i, j, c1rgb);
                        else
                            bi.setRGB(i, j, c2rgb);
                        changed = true;
                    }
                }
            }
        }
        if(bi.getWidth() != this.width && bi.getHeight() != this.height)
            bi = resize(bi, this.width, this.height);
        this.image = bi;
        this.g = this.image.createGraphics();
        return changed;
    }
    
    /**
     * Charge une zone complète à partir d'une image représentant le carbone
     * @param bi Correspond au carbone
     * @return Retourne true si lors du chargement, des transformations ont du avoir lieu (si par exemple la couleur alpha a été supprimée...) sinon false
     */
    private boolean loadCarbon(java.awt.image.BufferedImage bi){
        bi = copy(bi);
        java.awt.Color c1 = new java.awt.Color(foreground[0], foreground[1], foreground[2]); // BLACK
        java.awt.Color c2 = new java.awt.Color(background[0], background[1], background[2]); // WHITE
        int c1rgb = c1.getRGB();
        int c2rgb = c2.getRGB();
        boolean changed = false;
        for (int i = 0; i < bi.getWidth(); i++) {
            for (int j = 0; j < bi.getHeight(); j++) {
                java.awt.Color color = new java.awt.Color(bi.getRGB(i, j), true);
                if(color.getAlpha() == 0x00){
                    if(this.mode == ModeArea.ADDITIVE)
                        bi.setRGB(i, j, c2rgb);
                    else
                        bi.setRGB(i, j, c1rgb);
                    changed = true;
                }else{
                    if(color.equals(c1) || color.equals(c2)){
                        continue;
                    }else{
                        if(this.mode == ModeArea.ADDITIVE)
                            bi.setRGB(i, j, c1rgb);
                        else
                            bi.setRGB(i, j, c2rgb);
                        changed = true;
                    }
                }
            }
        }
        if(bi.getWidth() != this.width && bi.getHeight() != this.height)
            bi = resize(bi, this.width, this.height);
        this.image = reverseImageToBinaryImg(bi);
        this.g = this.image.createGraphics();
        return changed;
    }
    
    /**
     * Retaille une image
     * @param img Correspond à l'image à retailler
     * @param newW Correspond à la nouvelle largeur de l'image
     * @param newH Correspond à la nouvelle hauteur de l'image
     * return l'image retaillée
     */
    private java.awt.image.BufferedImage resize(java.awt.image.BufferedImage img, int newW, int newH) {
        if(img != null){
            int w = img.getWidth();  
            int h = img.getHeight();  
            java.awt.image.BufferedImage dimg = new java.awt.image.BufferedImage(newW, newH, img.getType());  
            java.awt.Graphics2D graphic = dimg.createGraphics();  
            graphic.setRenderingHint(java.awt.RenderingHints.KEY_INTERPOLATION, java.awt.RenderingHints.VALUE_INTERPOLATION_BILINEAR);  
            graphic.drawImage(img, 0, 0, newW, newH, 0, 0, w, h, null);  
            graphic.dispose();  
            return dimg;
        }else return null;
    }
    
    /**
     * Renvoie la couleur avec Alpha qui doit être affichée dans le carbone negatif
     * @return Retourne la couleur avec Alpha
     */
    @SuppressWarnings("PointlessBitwiseExpression")
    private int getColorAlpha(){
        return (0x00 << 24) | (background[0] << 16) | (background[1] << 8) | background[2];
    }
    
    /**
     * Détermine quelle est la couleur de fond du negatif en fonction du {@link ModeArea}
     */
    private void prepareBackground(){
        this.g.setColor(new java.awt.Color(background[0], background[1], background[2]));
    }

    /**
     * Détermine quelle est la couleur de premier plan du negatif en fonction du {@link ModeArea}
     */
    private void prepareForeground(){
        this.g.setColor(new java.awt.Color(foreground[0], foreground[1], foreground[2]));
    }
    
    
    
}