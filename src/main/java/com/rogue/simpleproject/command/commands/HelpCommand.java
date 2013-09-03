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
package com.rogue.simpleproject.command.commands;

import com.rogue.simpleproject.command.Command;
import java.util.logging.Level;

/**
 * Help menu for commands
 *
 * @since 1.0
 * @author 1Rogue
 * @version 1.0
 */
public class HelpCommand implements Command {

    public boolean execute(String[] args) {
        if (args.length == 1) {
            args[0] = args[0].toLowerCase();
            Command cmd = this.project.getCommandHandler().getCommandMap().get(args[0]);
            if (cmd == null) {
                this.project.getLogger().log(Level.WARNING, "Unknown command.");
                this.project.getLogger().log(Level.INFO, this.getHelp()[1]);
            } else {
                this.project.getLogger().log(Level.INFO, "USAGE FOR: /" + args[0]);
                for (String help : cmd.getHelp()) {
                    this.project.getLogger().log(Level.INFO, help);
                }
            }
            return true;
        }
        return false;
    }

    public String getName() {
        return "help";
    }

    public String[] getHelp() {
        StringBuilder sb = new StringBuilder();
        for (String cmd : this.project.getCommandHandler().getCommandMap().keySet()) {
            sb.append(cmd).append(", ");
        }
        return new String[] {
            "Command usage: /help <command-name>",
            "Available commands: " + sb.substring(0, sb.length() - 2)
        };
    }

}
