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
 * Calls you a foo, foo!
 *
 * @since 1.0
 * @author 1Rogue
 * @version 1.0
 */
public class FooCommand implements Command {

    public boolean execute(String[] args) {
        project.getLogger().log(Level.INFO, "You a foo, foo!");
        return true;
    }

    public String getName() {
        return "foo";
    }

    public String[] getHelp() {
        return new String[] {
            "Command usage: /foo",
            "Calls you a foo, foo!",
            "This help is not actually possible to reach!"
        };
    }

}
