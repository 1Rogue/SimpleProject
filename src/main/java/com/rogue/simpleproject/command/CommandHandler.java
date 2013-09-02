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
import java.util.List;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 *
 * @since 1.0
 * @author 1Rogue
 * @version 1.0
 */
public class CommandHandler {
    
    private final SimpleProject project;
    private final Map<String, Command> commands = new HashMap();
    
    public CommandHandler(SimpleProject project) {
        this.project = project;
    }
    
    public boolean parseCommand(String rawCommand) {
        int splitPoint = rawCommand.charAt(' ');
        String rawArguments = rawCommand.substring(splitPoint, rawCommand.length() - 1);
        String command = rawCommand.substring(1, splitPoint);
        
        // Break the arguments by spaces, but preserve ones enclosed in quotes
        //TODO: Make this cleaner
        String[] arguments;
        if (rawArguments.contains("\"")) {
            char[] broken = rawArguments.toCharArray();
            StringBuilder sb = new StringBuilder();
            List<String> newargs = new LinkedList();
            boolean inquotes = false;
            for (int i = 0; i < broken.length; i++) {
                if (broken[i] == '"') {
                    inquotes = !inquotes;
                } else if (broken[i] == ' ' && inquotes == false) {
                    newargs.add(sb.toString());
                    sb = new StringBuilder();
                } else {
                    sb.append(broken[i]);
                }
            }

            arguments = newargs.toArray(new String[newargs.size()]);
        } else {
            arguments = rawArguments.split(" ");
        }
        
        return onCommand(command, arguments);
    }
    
    private boolean onCommand(String command, String[] arguments) {
        if (commands.get(command) != null) {
            return commands.get(command).execute(arguments);
        }
        return false;
    }

}
