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
package com.rogue.simpleproject.command;

import com.rogue.simpleproject.SimpleProject;

/**
 * Command Template
 *
 * @since 1.0
 * @author 1Rogue
 * @version 1.0
 */
public interface Command {
    
    /**
     * The main project instance, kind of gross
     * 
     * @since 1.0
     * @version 1.0
     * 
     * @deprecated 
     */
    public SimpleProject project = SimpleProject.getProject();
    
    /**
     * The code to execute upon calling the command.
     * 
     * @since 1.0
     * @version 1.0
     * 
     * @param args The command arguments
     * @return Success of the command (if it worked as intended)
     */
    public abstract boolean execute(String[] args);
    
    /**
     * Gets the name of the command. This is used for keeping track of the
     * command usage as well as finding the appropriate class to execute.
     * 
     * @since 1.0
     * @version 1.0
     * 
     * @return The name of the command
     */
    public abstract String getName();
    
    /**
     * Returns help output for use when a command does not execute correctly
     * 
     * @since 1.0
     * @version 1.0
     * 
     * @return Command Help 
     */
    public abstract String[] getHelp();

}
