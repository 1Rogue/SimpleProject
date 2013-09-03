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
package com.rogue.simpleproject.data;

import com.rogue.simpleproject.SimpleProject;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Handles saved data requests. Namely SQLite and text
 * 
 * @since 1.0
 * @author 1Rogue
 * @version 1.0
 */
public class DataHandler {

    private final SimpleProject project;
    /**
     * This hard-coded value was a little dirty and last minute, I had to create
     * an alternative to SQLite
     */
    private final String datatype = "text";

    public DataHandler(SimpleProject project) {
        this.project = project;
    }

    /**
     * Verifies any data format in use
     *
     * @since 1.0
     * @version 1.0
     *
     * @param location The data directory, in {@link String} form
     */
    public void verify(String location) {
        if (this.datatype.equals("sqlite")) {
            SQLite db = new SQLite();
            db.setDatabaseLocation(new File(location + "rems.db"));
            try {
                db.open();
                if (!db.checkTable("rems")) {
                    db.update("CREATE TABLE rems ( id INTEGER NOT NULL PRIMARY KEY, rem VARCHAR(32) NOT NULL UNIQUE, value TEXT NOT NULL");
                }
                db.close();
            } catch (SQLException ex) {
                this.project.getLogger().log(Level.SEVERE, "Error initializing DB!", ex);
            }
        } else {
            Text text = new Text(this.project);
        }
    }

    /**
     * Gets a REM from whatever data storage is in use
     *
     * @since 1.0
     * @version 1.0
     *
     * @param remName The name of the REM
     * @return The REM in String form, null if no REM exists
     */
    public String getRem(String remName) {
        if (this.datatype.equals("sqlite")) {
            SQLite db = new SQLite();
            String rem = null;
            try {
                db.open();
                ResultSet result = db.query("SELECT `value` from `rems` WHERE `name`='" + remName + "'");
                if (result.next()) {
                    rem = result.getString(1);
                }
                db.close();
            } catch (SQLException ex) {
                this.project.getLogger().log(Level.SEVERE, "Error retrieving rem from DB!", ex);
            }
            return rem;
        } else {
            Text text = new Text(this.project);
            final Map<String, String> rems;
            synchronized (rems = text.getRems()) {
                return rems.get(remName);
            }
        }
    }

    /**
     * Formats a REM with variables (i.e. {0} or {1}) in the string
     *
     * @since 1.0
     * @version 1.0
     *
     * @param rem The REM in raw form
     * @param args The arguments supplied to replace within the string
     * @return The formatted REM
     */
    public String formatRem(String rem, String... args) {
        for (int i = 0; i < args.length; i++) {
            rem = rem.replace("{" + i + "}", args[i]);
        }
        return rem;
    }

    /**
     * Adds a REM to the known REMs, and enables it for use
     *
     * @since 1.0
     * @version 1.0
     *
     * @param name The name of the REM
     * @param rem The REM in raw string format
     */
    public void addRem(String name, String rem) {
        if (this.datatype.equals("sqlite")) {
            SQLite db = new SQLite();
            try {
                db.open();
                db.update("INSERT INTO `rems` VALUES ('" + name + "', '" + rem + "')");
                db.close();
            } catch (SQLException ex) {
                this.project.getLogger().log(Level.SEVERE, "Rem already exists!");
            }
        } else {
            Text text = new Text(this.project);
            File newRem = new File(text.getDataDirectory() + File.separator + name);
            if (!newRem.exists()) {
                try {
                    newRem.createNewFile();
                    FileWriter fw = new FileWriter(newRem);
                    fw.write(rem);
                } catch (IOException ex) {
                    Logger.getLogger(DataHandler.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                this.project.getLogger().log(Level.SEVERE, "Rem already exists!");
            }
            final Map<String, String> rems;
            synchronized (rems = text.getRems()) {
                rems.put(name, rem);
            }
        }
    }

    /**
     * Removes a REM. Does nothing if the REM doesn't exist.
     *
     * @since 1.0
     * @version 1.0
     *
     * @param name The name of the REM to remove.
     */
    public void removeRem(String name) {
        if (this.datatype.equals("sqlite")) {
            // sqlite
        } else {
            Text text = new Text(this.project);
            final Map<String, String> rems;
            synchronized (rems = text.getRems()) {
                String oldValue = rems.remove(name);
                if (oldValue == null) {
                    this.project.getLogger().log(Level.SEVERE, "Rem does not exist!");
                } else {
                    File rem = new File(text.getDataDirectory() + File.separator + name);
                    rem.delete();
                }
            }
        }
    }
}
