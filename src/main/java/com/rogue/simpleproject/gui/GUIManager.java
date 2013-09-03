/*
 * Copyright (C) 2013 Spencer Alderman
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.rogue.simpleproject.gui;

import com.rogue.simpleproject.SimpleProject;

/**
 * Manages the GUI for the project
 *
 * @since 1.0
 * @author 1Rogue
 * @version 1.0
 */
public class GUIManager {
    
    private SimpleProject project;
    private SPWindow window;
    
    /**
     * Initializes the GUI Manager. Use GUIManager.start() to create the window
     * 
     * @since 1.0
     * @version 1.0
     * 
     * @param project The {@link SimpleProject} instance
     */
    public GUIManager(SimpleProject project) {
        this.project = project;
    }
    
    /**
     * Builds the GUI window.
     * 
     * @since 1.0
     * @version 1.0
     */
    public void init() {
        this.window = new SPWindow(this.project);
    }
    
    /**
     * Gets the main GUI window
     * 
     * @since 1.0
     * @version 1.0
     * 
     * @return The {@link SPWindow} in use
     */
    public SPWindow getWindow() {
        return this.window;
    }

}
