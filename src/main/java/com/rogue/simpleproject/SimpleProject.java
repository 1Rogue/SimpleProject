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
package com.rogue.simpleproject;

import com.rogue.simpleproject.command.CommandHandler;
import com.rogue.simpleproject.gui.GUIManager;
import com.rogue.simpleproject.listener.SPListener;

/**
 * Main class
 *
 * @since 1.0
 * @author 1Rogue
 * @version 1.0
 */
public class SimpleProject extends Start {
    
    private GUIManager gui;
    private CommandHandler command;
    private SPListener listener;
    private boolean running = true;
    
    /**
     * Starts the main instance of the program.
     * 
     * @since 1.0
     * @version 1.0
     * 
     * @param useGUI Whether to use the {@link GUIManager} or command line
     */
    public SimpleProject(boolean useGUI) {
        
        this.command = new CommandHandler(this);
        
        if (useGUI) {
            this.listener = new SPListener(this);
            
            this.gui = new GUIManager(this);
            this.gui.init();
        } else {
            this.gui = null;
        }
    }
    
    /**
     * Returns a static instance of the project
     * 
     * @since 1.0
     * @version 1.0
     * 
     * @return The {@link SimpleProject} instance
     */
    public static SimpleProject getProject() {
        return instance;
    }
    
    /**
     * Exits the project cleanly, clearing up any running tasks
     * 
     * @since 1.0
     * @version 1.0
     * 
     * @param exitCode The system exit code to use
     */
    public void exit(int exitCode) {
        this.running = false;
        System.exit(exitCode);
    }
    
    /**
     * Returns the GUI Manager for the project
     * 
     * @since 1.0
     * @version 1.0
     * 
     * @return The {@link GUIManager} instance
     */
    public GUIManager getGUI() {
        return this.gui;
    }
    
    /**
     * Returns the command handler for the project
     * 
     * @since 1.0
     * @version 1.0
     * 
     * @return The {@link CommandHandler} instance
     */
    public CommandHandler getCommandHandler() {
        return this.command;
    }
    
    /**
     * Returns the listener for the project
     * 
     * @since 1.0
     * @version 1.0
     * 
     * @return The {@link SPListener} instance 
     */
    public SPListener getListener() {
        return this.listener;
    }
    
    /**
     * A simple boolean for if the application is running or not
     * 
     * @since 1.0
     * @version 1.0
     * 
     * @return True if running, false otherwise
     */
    public boolean isRunning() {
        return this.running;
    }

}
