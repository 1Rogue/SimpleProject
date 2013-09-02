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
import com.rogue.simpleproject.command.commands.*;
import java.util.List;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Handles command for the project
 *
 * @since 1.0
 * @author 1Rogue
 * @version 1.0
 */
public final class CommandHandler {

    private final SimpleProject project;
    private final Map<String, Command> commands = new HashMap();

    /**
     * Initializes the Command Handler and adds the commands to the command map
     * 
     * @since 1.0
     * @version 1.0
     * 
     * @param project The {@link SimpleProject} instance
     */
    public CommandHandler(SimpleProject project) {
        this.project = project;

        FooCommand foo = new FooCommand();
        commands.put(foo.getName(), foo);
    }

    /**
     * Parses a raw command from the text field and executes it. This text
     * should still have the command prefix ("/") within the raw string.
     *
     * Ex. "/weather Rutland, VT"
     *
     * @since 1.0
     * @version 1.0
     *
     * @param rawCommand The raw string command to parse
     */
    public void parseCommand(String rawCommand) {
        String command;
        String[] arguments = null;
        // Break the arguments by spaces, but preserve ones enclosed in quotes
        if (rawCommand.contains(" ")) {
            int splitPoint = rawCommand.indexOf(" ");
            command = rawCommand.substring(1, splitPoint);
            String rawArguments =
                    rawCommand.substring(splitPoint, rawCommand.length());
            //TODO: Make this cleaner
            if (rawArguments.contains("\"")) 
            {
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
        } else {
            command = rawCommand.substring(1, rawCommand.length());
        }
        
        boolean exec = onCommand(command, arguments);
        if (!exec) {
            System.out.println("Unknown command. Type /help for help");
        }
    }

    /**
     * Attempts to execute a command with provided arugments
     *
     * @since 1.0
     * @vversion 1.0
     *
     * @param command The string name for the command to execute
     * @param arguments
     * @return True if command exists, false otherwise
     */
    private boolean onCommand(String command, String[] arguments) {
        if (commands.get(command) != null) {
            Command cmd = commands.get(command);
            boolean needsHelp = !cmd.execute(arguments);
            if (needsHelp) {
                System.out.println("USAGE FOR: /" + command.toLowerCase());
                for (String help : cmd.getHelp()) {
                    System.out.println(help);
                }
            }
            return true;
        }
        return false;
    }
}
