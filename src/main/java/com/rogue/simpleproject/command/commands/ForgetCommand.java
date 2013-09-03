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
 * Removes user-commands by name
 *
 * @since
 * @author 1Rogue
 * @version
 */
public class ForgetCommand implements Command {

    public boolean execute(String[] args) {
        if (args.length == 1) {
            String name = args[0];
            this.project.getLogger().log(Level.INFO, "Removing rem '" + name + "'...");
            boolean existed = this.project.getDataHandler().removeRem(name);
            if (!existed) {
                final Map<String, Command> cmds;
                synchronized (cmds = this.project.getCommandHandler().getCommandMap()) {
                    cmds.get("listrems").execute(new String[]{});
                }
            }
            return true;
        }
        return false;
    }

    public String getName() {
        return "rem";
    }

    public String[] getHelp() {
        return new String[]{
            "/forget <rem-name>",
            "Removes a previously set REM by name",
            "If no REM is found, it will list current REMs"
        };
    }
}
