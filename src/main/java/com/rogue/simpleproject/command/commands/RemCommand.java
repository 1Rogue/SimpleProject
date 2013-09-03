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
 * Allows the user to add custom user-commands
 *
 * @since 1.0
 * @author 1Rogue
 * @version 1.0
 */
public class RemCommand implements Command {

    public boolean execute(String[] args) {
        if (args.length > 2) {
            String name = args[0];
            StringBuilder sb = new StringBuilder();
            for (int i = 1; i < args.length; i++) {
                sb.append(args[i]).append(' ');
            }
            String rem = sb.toString().trim();
            this.project.getLogger().log(Level.INFO, "Adding rem '" + name + "'...");
            this.project.getDataHandler().addRem(name, rem);
            return true;
        }
        return false;
    }

    public String getName() {
        return "rem";
    }

    public String[] getHelp() {
        return new String[] {
            "/rem <variable-name> <output-text>",
            "Variable name can be anything. You can also allow for arguments to be supplied via numbered brackets, i.e. {0} or {1}",
            "{0} would be the first argument supplied by a rem, so if you ran",
            "$remname sponge",
            "output: Foo is a sponge",
            "original setting command: /rem remname Foo is a {0}"
        };
    }
    
}
