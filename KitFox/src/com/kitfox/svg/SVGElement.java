/*
 * SVGElement.java
 *
 *
 *  The Salamander Project - 2D and 3D graphics libraries in Java
 *  Copyright (C) 2004 Mark McKay
 *
 *  This library is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU Lesser General Public
 *  License as published by the Free Software Foundation; either
 *  version 2.1 of the License, or (at your option) any later version.
 *
 *  This library is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 *  Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public
 *  License along with this library; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 *
 *  Mark McKay can be contacted at mark@kitfox.com.  Salamander and other
 *  projects can be found at http://www.kitfox.com
 *
 * Created on January 26, 2004, 1:59 AM
 */

package com.kitfox.svg;

import java.util.*;
import java.util.regex.*;
import java.net.*;
import java.awt.geom.*;

import org.xml.sax.*;
import com.kitfox.svg.animation.*;
import com.kitfox.svg.pathcmd.*;
import com.kitfox.svg.xml.*;
import java.io.Serializable;

/**
 * @author Mark McKay
 * @author <a href="mailto:mark@kitfox.com">Mark McKay</a>
 */
abstract public class SVGElement implements Serializable
{
    public static final long serialVersionUID = 0;
    
    public static final String SVG_NS = "http://www.w3.org/2000/svg";
    
    protected SVGElement parent = null;
    
    protected final ArrayList children = new ArrayList();
    
    protected String id = null;
    /**
     * CSS class.  Used for applying style sheet information.
     */
    protected String cssClass = null;
    
    /**
     * Styles defined for this elemnt via the <b>style</b> attribute.
     */
    protected final HashMap inlineStyles = new HashMap();
    
    /**
     * Presentation attributes set for this element.  Ie, any attribute other
     * than the <b>style</b> attribute.
     */
    protected final HashMap presAttribs = new HashMap();
    
    /**
     * A list of presentation attributes to not include in the presentation
     * attribute set.
     */
    protected static final Set ignorePresAttrib;
    static
    {
        HashSet set = new HashSet();
//        set.add("id");
//        set.add("class");
//        set.add("style");
//        set.add("xml:base");
        
        ignorePresAttrib = Collections.unmodifiableSet(set);
    }
    
    /**
     * This element may override the URI we resolve against with an
     * xml:base attribute.  If so, a copy is placed here.  Otherwise, we defer
     * to our parent for the reolution base
     */
    protected URI xmlBase = null;
    
    /**
     * The diagram this element belongs to
     */
    protected SVGDiagram diagram;
    /**
     * Link to the universe we reside in
     */
//    protected SVGUniverse universe;
    
    protected final TrackManager trackManager = new TrackManager();

    boolean dirty = true;

//    public static final Matcher adobeId = Pattern.compile("(.*)_1_").matcher("");
//    static final String fpNumRe = "[-+]?((\\d+)|(\\d*\\.\\d+))([-+]?[eE]\\d+)?";
    
    /** Creates a new instance of SVGElement */
    public SVGElement()
    {
        this(null, null, null);
    }
    
    public SVGElement(String id, SVGElement parent)
    {
        this(id, null, parent);
    }
    
    public SVGElement(String id, String cssClass, SVGElement parent)
    {
        this.id = id;
        this.cssClass = cssClass;
        this.parent = parent;
    }
    
    public SVGElement getParent()
    {
        return parent;
    }
    
    void setParent(SVGElement parent)
    {
        this.parent = parent;
    }
    
    /**
     * @return an ordered list of nodes from the root of the tree to this node
     */
    public List getPath(List retVec)
    {
        if (retVec == null) retVec = new ArrayList();
        
        if (parent != null)
        {
            parent.getPath(retVec);
        }
        retVec.add(this);
        
        return retVec;
    }
    
    /**
     * @param retVec - A list to add all children to.  If null, a new list is
     * created and children of this group are added.
     *
     * @return The list containing the children of this group
     */
    public List getChildren(List retVec)
    {
        if (retVec == null) retVec = new ArrayList();
        
        retVec.addAll(children);
        
        return retVec;
    }
    
    /**
     * @param id - Id of svg element to return
     * @return the child of the given id, or null if no such child exists.
     */
    public SVGElement getChild(String id)
    {
        for (Iterator it = children.iterator(); it.hasNext();)
        {
            SVGElement ele = (SVGElement)it.next();
            String eleId = ele.getId();
            if (eleId != null && eleId.equals(id)) return ele;
        }
        
        return null;
    }
    
    /**
     * Searches children for given element.  If found, returns index of child.
     * Otherwise returns -1.
     */
    public int indexOfChild(SVGElement child)
    {
        return children.indexOf(child);
    }
    
    /**
     * Swaps 2 elements in children.
     * @param i index of first
     * @param j index of second
     */
    public void swapChildren(int i, int j) throws SVGException
    {
        if ((children == null) || (i < 0) || (i >= children.size()) || (j < 0) || (j >= children.size()))
        {
            return;
        }
        
        Object temp = children.get(i);
        children.set(i, children.get(j));
        children.set(j, temp);
        build();
    }
    
    /**
     * Called during SAX load process to notify that this tag has begun the process
     * of being loaded
     * @param attrs - Attributes of this tag
     * @param helper - An object passed to all SVG elements involved in this build
     * process to aid in sharing information.
     */
    public void loaderStartElement(SVGLoaderHelper helper, Attributes attrs, SVGElement parent) throws SAXException
    {
        //Set identification info
        this.parent = parent;
        this.diagram = helper.diagram;
        
        this.id = attrs.getValue("id");
        if (this.id != null && !this.id.equals(""))
        {
            diagram.setElement(this.id, this);
        }
        
        String className = attrs.getValue("class");
        this.cssClass = (className == null || className.equals("")) ? null : className;
        //docRoot = helper.docRoot;
        //universe = helper.universe;
        
        //Parse style string, if any
        String style = attrs.getValue("style");
        if (style != null)
        {
            HashMap map = XMLParseUtil.parseStyle(style, inlineStyles);
        }
        
        String base = attrs.getValue("xml:base");
        if (base != null && !base.equals(""))
        {
            try
            {
                xmlBase = new URI(base);
            }
            catch (Exception e)
            {
                throw new SAXException(e);
            }
        }
        
        //Place all other attributes into the presentation attribute list
        int numAttrs = attrs.getLength();
        for (int i = 0; i < numAttrs; i++)
        {
            String name = attrs.getQName(i);
            if (ignorePresAttrib.contains(name)) continue;
            String value = attrs.getValue(i);
            
            presAttribs.put(name, new StyleAttribute(name, value));
        }
    }

    public void removeAttribute(String name, int attribType)
    {
        switch (attribType)
        {
            case AnimationElement.AT_CSS:
                inlineStyles.remove(name);
                return;
            case AnimationElement.AT_XML:
                presAttribs.remove(name);
                return;
        }
    }

    public void addAttribute(String name, int attribType, String value) throws SVGElementException
    {
        if (hasAttribute(name, attribType)) throw new SVGElementException(this, "Attribute " + name + "(" + AnimationElement.animationElementToString(attribType) + ") already exists");
        
        //Alter layout for id attribute
        if ("id".equals(name))
        {
            if (diagram != null)
            {
                diagram.removeElement(id);
                diagram.setElement(value, this);
            }
            this.id = value;
        }
        
        switch (attribType)
        {
            case AnimationElement.AT_CSS:
                inlineStyles.put(name, new StyleAttribute(name, value));
                return;
            case AnimationElement.AT_XML:
                presAttribs.put(name, new StyleAttribute(name, value));
                return;
        }
        
        throw new SVGElementException(this, "Invalid attribute type " + attribType);
    }
    
    public boolean hasAttribute(String name, int attribType) throws SVGElementException
    {
        switch (attribType)
        {
            case AnimationElement.AT_CSS:
                return inlineStyles.containsKey(name);
            case AnimationElement.AT_XML:
                return presAttribs.containsKey(name);
            case AnimationElement.AT_AUTO:
                return inlineStyles.containsKey(name) || presAttribs.containsKey(name);
        }
        
        throw new SVGElementException(this, "Invalid attribute type " + attribType);
    }
    
    /**
     * @return a set of Strings that corespond to CSS attributes on this element
     */
    public Set getInlineAttributes()
    {
        return inlineStyles.keySet();
    }
    
    /**
     * @return a set of Strings that corespond to XML attributes on this element
     */
    public Set getPresentationAttributes()
    {
        return presAttribs.keySet();
    }
    
    /**
     * Called after the start element but before the end element to indicate
     * each child tag that has been processed
     */
    public void loaderAddChild(SVGLoaderHelper helper, SVGElement child) throws SVGElementException
    {
        children.add(child);
        child.parent = this;
        child.setDiagram(diagram);
        
        //Add info to track if we've scanned animation element
        if (child instanceof AnimationElement)
        {
            trackManager.addTrackElement((AnimationElement)child);
        }
    }
    
    protected void setDiagram(SVGDiagram diagram)
    {
        this.diagram = diagram;
        diagram.setElement(id, this);
        for (Iterator it = children.iterator(); it.hasNext();)
        {
            SVGElement ele = (SVGElement)it.next();
            ele.setDiagram(diagram);
        }
    }
    
    public void removeChild(SVGElement child) throws SVGElementException
    {
        if (!children.contains(child))
        {
            throw new SVGElementException(this, "Element does not contain child " + child);
        }
        
        children.remove(child);
    }
    
    /**
     * Called during load process to add text scanned within a tag
     */
    public void loaderAddText(SVGLoaderHelper helper, String text)
    {
    }
    
    /**
     * Called to indicate that this tag and the tags it contains have been completely
     * processed, and that it should finish any load processes.
     */
    public void loaderEndElement(SVGLoaderHelper helper) throws SVGParseException
    {
        try
        {
            build();
        }
        catch (SVGException se)
        {
            throw new SVGParseException(se);
        }
    }
    
    /**
     * Called by internal processes to rebuild the geometry of this node
     * from it's presentation attributes, style attributes and animated tracks.
     */
    protected void build() throws SVGException
    {
        StyleAttribute sty = new StyleAttribute();
        
        if (getPres(sty.setName("id")))
        {
            String newId = sty.getStringValue();
            if (!newId.equals(id))
            {
                diagram.removeElement(id);
                id = newId;
                diagram.setElement(this.id, this);
            }
        }
        if (getPres(sty.setName("class"))) cssClass = sty.getStringValue();
        if (getPres(sty.setName("xml:base"))) xmlBase = sty.getURIValue();
    }
    
    public URI getXMLBase()
    {
        return xmlBase != null ? xmlBase :
            (parent != null ? parent.getXMLBase() : diagram.getXMLBase());
    }

    /**
     * @return the id assigned to this node.  Null if no id explicitly set.
     */
    public String getId()
    {
        return id; 
    }
    
    
    LinkedList contexts = new LinkedList();
    
    /**
     * Hack to allow nodes to temporarily change their parents.  The Use tag will
     * need this so it can alter the attributes that a particular node uses.
     */
    protected void pushParentContext(SVGElement context)
    {
        contexts.addLast(context);
    }

    protected SVGElement popParentContext()
    {
        return (SVGElement)contexts.removeLast();
    }
    
    protected SVGElement getParentContext()
    {
        return contexts.isEmpty() ? null : (SVGElement)contexts.getLast();
    }
    
    /*
     * Returns the named style attribute.  Checks for inline styles first, then
     * internal and extranal style sheets, and finally checks for presentation
     * attributes.
     * @param styleName - Name of attribute to return
     * @param recursive - If true and this object does not contain the
     * named style attribute, checks attributes of parents abck to root until
     * one found.
     */
    public boolean getStyle(StyleAttribute attrib) throws SVGException
    {
        return getStyle(attrib, true);
    }
    
    
    public void setAttribute(String name, int attribType, String value) throws SVGElementException
    {
        StyleAttribute styAttr;
        
        
        switch (attribType)
        {
            case AnimationElement.AT_CSS:
            {
                styAttr = (StyleAttribute)inlineStyles.get(name);
                break;
            }
            case AnimationElement.AT_XML:
            {
                styAttr = (StyleAttribute)presAttribs.get(name);
                break;
            }
            case AnimationElement.AT_AUTO:
            {
                styAttr = (StyleAttribute)inlineStyles.get(name);
                
                if (styAttr == null)
                {
                    styAttr = (StyleAttribute)presAttribs.get(name);
                }
                break;
            }
            default:
                throw new SVGElementException(this, "Invalid attribute type " + attribType);
        }
        
        if (styAttr == null)
        {
            throw new SVGElementException(this, "Could not find attribute " + name + "(" + AnimationElement.animationElementToString(attribType) + ").  Make sure to create attribute before setting it.");
        }
        
        //Alter layout for relevant attributes
        if ("id".equals(styAttr.getName()))
        {
            if (diagram != null)
            {
                diagram.removeElement(this.id);
                diagram.setElement(value, this);
            }
            this.id = value;
        }
        
        styAttr.setStringValue(value);
    }
    
    /**
     * Copies the current style into the passed style attribute.  Checks for
     * inline styles first, then internal and extranal style sheets, and
     * finally checks for presentation attributes.  Recursively checks parents.
     * @param attrib - Attribute to write style data to.  Must have it's name
     * set to the name of the style being queried.
     * @param recursive - If true and this object does not contain the
     * named style attribute, checks attributes of parents abck to root until
     * one found.
     */
    public boolean getStyle(StyleAttribute attrib, boolean recursive) throws SVGException
    {
        String styName = attrib.getName();
        
        //Check for local inline styles
        StyleAttribute styAttr = (StyleAttribute)inlineStyles.get(styName);
        
        attrib.setStringValue(styAttr == null ? "" : styAttr.getStringValue());
        
        //Evalutate coresponding track, if one exists
        TrackBase track = trackManager.getTrack(styName, AnimationElement.AT_CSS);
        if (track != null)
        {
            track.getValue(attrib, diagram.getUniverse().getCurTime());
            return true;
        }
        
        //Return if we've found a non animated style
        if (styAttr != null) return true;
        
        
        
        //Implement style sheet lookup later
        
        //Check for presentation attribute
        StyleAttribute presAttr = (StyleAttribute)presAttribs.get(styName);
        
        attrib.setStringValue(presAttr == null ? "" : presAttr.getStringValue());
        
        //Evalutate coresponding track, if one exists
        track = trackManager.getTrack(styName, AnimationElement.AT_XML);
        if (track != null)
        {
            track.getValue(attrib, diagram.getUniverse().getCurTime());
            return true;
        }
        
        //Return if we've found a presentation attribute instead
        if (presAttr != null) return true;
        
        
        //If we're recursive, check parents
        if (recursive)
        {
            SVGElement parentContext = getParentContext();
            if (parentContext != null) return parentContext.getStyle(attrib, true);
            if (parent != null) return parent.getStyle(attrib, true);
        }
        
        //Unsuccessful reading style attribute
        return false;
    }
    
    /**
     * @return the raw style value of this attribute.  Does not take the 
     * presentation value or animation into consideration.  Used by animations 
     * to determine the base to animate from.
     */
    public StyleAttribute getStyleAbsolute(String styName)
    {
        //Check for local inline styles
        return (StyleAttribute)inlineStyles.get(styName);
    }
    
    /**
     * Copies the presentation attribute into the passed one.
     * @return - True if attribute was read successfully
     */
    public boolean getPres(StyleAttribute attrib) throws SVGException
    {
        String presName = attrib.getName();
        
        //Make sure we have a coresponding presentation attribute
        StyleAttribute presAttr = (StyleAttribute)presAttribs.get(presName);
        
        //Copy presentation value directly
        attrib.setStringValue(presAttr == null ? "" : presAttr.getStringValue());
        
        //Evalutate coresponding track, if one exists
        TrackBase track = trackManager.getTrack(presName, AnimationElement.AT_XML);
        if (track != null)
        {
            track.getValue(attrib, diagram.getUniverse().getCurTime());
            return true;
        }
        
        //Return if we found presentation attribute
        if (presAttr != null) return true;
        
        return false;
    }
    
    /**
     * @return the raw presentation value of this attribute.  Ignores any 
     * modifications applied by style attributes or animation.  Used by 
     * animations to determine the starting point to animate from
     */
    public StyleAttribute getPresAbsolute(String styName)
    {
        //Check for local inline styles
        return (StyleAttribute)presAttribs.get(styName);
    }
    
    static protected AffineTransform parseTransform(String val) throws SVGException
    {
        final Matcher matchExpression = Pattern.compile("\\w+\\([^)]*\\)").matcher("");
        
        AffineTransform retXform = new AffineTransform();
        
        matchExpression.reset(val);
        while (matchExpression.find())
        {
            retXform.concatenate(parseSingleTransform(matchExpression.group()));
        }
        
        return retXform;
    }
    
    static public AffineTransform parseSingleTransform(String val) throws SVGException
    {
        final Matcher matchWord = Pattern.compile("[-.\\w]+").matcher("");
        
        AffineTransform retXform = new AffineTransform();
        
        matchWord.reset(val);
        if (!matchWord.find())
        {
            //Return identity transformation if no data present (eg, empty string)
            return retXform;
        }
        
        String function = matchWord.group().toLowerCase();
        
        LinkedList termList = new LinkedList();
        while (matchWord.find())
        {
            termList.add(matchWord.group());
        }
        
        
        double[] terms = new double[termList.size()];
        Iterator it = termList.iterator();
        int count = 0;
        while (it.hasNext())
        {
            terms[count++] = XMLParseUtil.parseDouble((String)it.next());
        }
        
        //Calculate transformation
        if (function.equals("matrix"))
        {
            retXform.setTransform(terms[0], terms[1], terms[2], terms[3], terms[4], terms[5]);
        }
        else if (function.equals("translate"))
        {
            retXform.setToTranslation(terms[0], terms[1]);
        }
        else if (function.equals("scale"))
        {
            if (terms.length > 1)
                retXform.setToScale(terms[0], terms[1]);
            else
                retXform.setToScale(terms[0], terms[0]);
        }
        else if (function.equals("rotate"))
        {
            if (terms.length > 2)
                retXform.setToRotation(Math.toRadians(terms[0]), terms[1], terms[2]);
            else
                retXform.setToRotation(Math.toRadians(terms[0]));
        }
        else if (function.equals("skewx"))
        {
            retXform.setToShear(Math.toRadians(terms[0]), 0.0);
        }
        else if (function.equals("skewy"))
        {
            retXform.setToShear(0.0, Math.toRadians(terms[0]));
        }
        else
        {
            throw new SVGException("Unknown transform type");
        }
        
        return retXform;
    }
    
    static protected float nextFloat(LinkedList l)
    {
        String s = (String)l.removeFirst();
        return Float.parseFloat(s);
    }
    
    static protected PathCommand[] parsePathList(String list)
    {
        final Matcher matchPathCmd = Pattern.compile("([MmLlHhVvAaQqTtCcSsZz])|([-+]?((\\d*\\.\\d+)|(\\d+))([eE][-+]?\\d+)?)").matcher(list);
        
        //Tokenize
        LinkedList tokens = new LinkedList();
        while (matchPathCmd.find())
        {
            tokens.addLast(matchPathCmd.group());
        }
        
        
        boolean defaultRelative = false;
        LinkedList cmdList = new LinkedList();
        char curCmd = 'Z';
        while (tokens.size() != 0)
        {
            String curToken = (String)tokens.removeFirst();
            char initChar = curToken.charAt(0);
            if ((initChar >= 'A' && initChar <='Z') || (initChar >= 'a' && initChar <='z'))
            {
                curCmd = initChar;
            }
            else
            {
                tokens.addFirst(curToken);
            }
            
            PathCommand cmd = null;
            
            switch (curCmd)
            {
                case 'M':
                    cmd = new MoveTo(false, nextFloat(tokens), nextFloat(tokens));
                    curCmd = 'L';
                    break;
                case 'm':
                    cmd = new MoveTo(true, nextFloat(tokens), nextFloat(tokens));
                    curCmd = 'l';
                    break;
                case 'L':
                    cmd = new LineTo(false, nextFloat(tokens), nextFloat(tokens));
                    break;
                case 'l':
                    cmd = new LineTo(true, nextFloat(tokens), nextFloat(tokens));
                    break;
                case 'H':
                    cmd = new Horizontal(false, nextFloat(tokens));
                    break;
                case 'h':
                    cmd = new Horizontal(true, nextFloat(tokens));
                    break;
                case 'V':
                    cmd = new Vertical(false, nextFloat(tokens));
                    break;
                case 'v':
                    cmd = new Vertical(true, nextFloat(tokens));
                    break;
                case 'A':
                    cmd = new Arc(false, nextFloat(tokens), nextFloat(tokens),
                            nextFloat(tokens),
                            nextFloat(tokens) == 1f, nextFloat(tokens) == 1f,
                            nextFloat(tokens), nextFloat(tokens));
                    break;
                case 'a':
                    cmd = new Arc(true, nextFloat(tokens), nextFloat(tokens),
                            nextFloat(tokens),
                            nextFloat(tokens) == 1f, nextFloat(tokens) == 1f,
                            nextFloat(tokens), nextFloat(tokens));
                    break;
                case 'Q':
                    cmd = new Quadratic(false, nextFloat(tokens), nextFloat(tokens),
                            nextFloat(tokens), nextFloat(tokens));
                    break;
                case 'q':
                    cmd = new Quadratic(true, nextFloat(tokens), nextFloat(tokens),
                            nextFloat(tokens), nextFloat(tokens));
                    break;
                case 'T':
                    cmd = new QuadraticSmooth(false, nextFloat(tokens), nextFloat(tokens));
                    break;
                case 't':
                    cmd = new QuadraticSmooth(true, nextFloat(tokens), nextFloat(tokens));
                    break;
                case 'C':
                    cmd = new Cubic(false, nextFloat(tokens), nextFloat(tokens),
                            nextFloat(tokens), nextFloat(tokens),
                            nextFloat(tokens), nextFloat(tokens));
                    break;
                case 'c':
                    cmd = new Cubic(true, nextFloat(tokens), nextFloat(tokens),
                            nextFloat(tokens), nextFloat(tokens),
                            nextFloat(tokens), nextFloat(tokens));
                    break;
                case 'S':
                    cmd = new CubicSmooth(false, nextFloat(tokens), nextFloat(tokens),
                            nextFloat(tokens), nextFloat(tokens));
                    break;
                case 's':
                    cmd = new CubicSmooth(true, nextFloat(tokens), nextFloat(tokens),
                            nextFloat(tokens), nextFloat(tokens));
                    break;
                case 'Z':
                case 'z':
                    cmd = new Terminal();
                    break;
                default:
                    throw new RuntimeException("Invalid path element");
            }
            
            cmdList.add(cmd);
            defaultRelative = cmd.isRelative;
        }
        
        PathCommand[] retArr = new PathCommand[cmdList.size()];
        cmdList.toArray(retArr);
        return retArr;
    }
    
    static protected GeneralPath buildPath(String text, int windingRule)
    {
        PathCommand[] commands = parsePathList(text);
        
        int numKnots = 2;
        for (int i = 0; i < commands.length; i++)
        {
            numKnots += commands[i].getNumKnotsAdded();
        }
        
        
        GeneralPath path = new GeneralPath(windingRule, numKnots);
        
        BuildHistory hist = new BuildHistory();
        
        for (int i = 0; i < commands.length; i++)
        {
            PathCommand cmd = commands[i];
            cmd.appendPath(path, hist);
        }
        
        return path;
    }
    
    
    
    /**
     * Updates all attributes in this diagram associated with a time event.
     * Ie, all attributes with track information.
     * @return - true if this node has changed state as a result of the time
     * update
     */
    abstract public boolean updateTime(double curTime) throws SVGException;

    public int getNumChildren()
    {
        return children.size();
    }

    public SVGElement getChild(int i)
    {
        return (SVGElement)children.get(i);
    }
    
}
