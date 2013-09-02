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
    
    public static SimpleProject getProject() {
        return instance;
    }
    
    public void exit(int exitCode) {
        this.running = false;
        System.exit(exitCode);
    }
    
    public GUIManager getGUI() {
        return this.gui;
    }
    
    public CommandHandler getCommandHandler() {
        return this.command;
    }
    
    public SPListener getListener() {
        return this.listener;
    }
    
    public boolean isRunning() {
        return this.running;
    }

}
