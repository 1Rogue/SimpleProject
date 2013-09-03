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
import com.rogue.simpleproject.data.Text;
import java.util.Map;
import java.util.logging.Level;

/**
 * Lists the current REMs
 *
 * @since 1.0
 * @author 1Rogue
 * @version 1.0
 */
public class ListRemsCommand implements Command {

    public boolean execute(String[] args) {
        Text text = new Text(this.project);
        final Map<String, String> rems;
        synchronized (rems = text.getRems()) {
            StringBuilder sb = new StringBuilder();
            for (String rem : rems.keySet()) {
                sb.append(rem).append(", ");
            }
            this.project.getLogger().log(Level.INFO, "Current REMs:");
            this.project.getLogger().log(Level.INFO, sb.substring(0, sb.length() - 2));
        }
        return true;
    }

    public String getName() {
        return "listrems";
    }

    public String[] getHelp() {
        return new String[] {
            "Command usage: /listrems",
            "Lists all the current registered REMs"
        };
    }
}
