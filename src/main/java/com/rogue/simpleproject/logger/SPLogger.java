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
package com.rogue.simpleproject.logger;

import com.rogue.simpleproject.SimpleProject;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

/**
 *
 * @since 1.0
 * @author 1Rogue
 * @version 1.0
 */
public class SPLogger extends Logger {

    private final SimpleProject project;
    private final String prefix = "===> ";
    
    public SPLogger(SimpleProject project) {
        super(project.getClass().getCanonicalName(), null);
        this.project = project;
        this.addHandler(new SPHandler(project));
        this.setLevel(Level.ALL);
    }
    
    @Override
    public void log(LogRecord logRecord) {
        String level = (logRecord.getLevel() == Level.OFF) ? "" : logRecord.getLevel().getName();
        logRecord.setMessage("[" + level + "] " + this.prefix + logRecord.getMessage());
        super.log(logRecord);
    }
    
}
