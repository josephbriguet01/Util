/*
 * Stop.java
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
 * Created on September 19, 2004, 1:56 AM
 */

package com.kitfox.svg;

/**
 * Holds title textual information within tree
 *
 * @author Mark McKay
 * @author <a href="mailto:mark@kitfox.com">Mark McKay</a>
 */
public class Desc extends SVGElement {

    StringBuffer text = new StringBuffer();

    /** Creates a new instance of Stop */
    public Desc() {
    }

    /**
     * Called during load process to add text scanned within a tag
     */
    public void loaderAddText(SVGLoaderHelper helper, String text)
    {
        this.text.append(text);
    }

    public String getText() { return text.toString(); }
    
    public boolean updateTime(double curTime)
    {
        return false;
    }
}
