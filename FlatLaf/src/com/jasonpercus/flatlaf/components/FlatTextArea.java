/*
 * Copyright 2020 FormDev Software GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jasonpercus.flatlaf.components;

import static com.formdev.flatlaf.FlatClientProperties.*;
import javax.swing.JTextArea;

/**
 * Subclass of {@link JTextArea} that provides easy access to FlatLaf specific client properties.
 *
 * @author Karl Tauber
 */
public class FlatTextArea
	extends JTextArea
	implements FlatComponentExtension, FlatStyleableComponent
{
	/**
	 * Returns minimum width of a component.
	 */
	public int getMinimumWidth() {
		return getClientPropertyInt( MINIMUM_WIDTH, "Component.minimumWidth" );
	}

	/**
	 * Specifies minimum width of a component.
	 */
	public void setMinimumWidth( int minimumWidth ) {
		putClientProperty( MINIMUM_WIDTH, (minimumWidth >= 0) ? minimumWidth : null );
	}
}
