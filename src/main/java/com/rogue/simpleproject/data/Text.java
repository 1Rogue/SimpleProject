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
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;

/**
 * A really messy text file loading class written in about 20 minutes. Forgive
 * me for my sins.
 *
 * @since 1.0
 * @author 1Rogue
 * @version 1.0
 */
public class Text {

    private final File dataDirectory = new File("data");
    private final SimpleProject project;
    private static final Map<String, String> rems = new ConcurrentHashMap();
    private final File textFolder = new File(dataDirectory + File.separator + "text");

    public Text(SimpleProject project) {
        this.project = project;
        if (this.rems.isEmpty()) {
            this.loadRems();
        }
    }

    /**
     * Verifies directories are made and loads any rem (text) files that are
     * located within them.
     * 
     * @since 1.0
     * @version 1.0
     */
    private void loadRems() {
        boolean missingDir = false;
        if (!this.dataDirectory.exists()) {
            this.dataDirectory.mkdir();
            missingDir = true;
        }
        if (!this.textFolder.exists()) {
            this.textFolder.mkdir();
            missingDir = true;
        }
        if (!missingDir) {
            File[] textFiles = textFolder.listFiles();
            if (textFiles != null) {
                String name = "";
                String rem = "";
                try {
                    for (File f : textFiles) {
                        name = f.getName();
                        FileInputStream fis = new FileInputStream(f);
                        InputStreamReader isr = new InputStreamReader(fis);
                        if (isr.ready()) {
                            BufferedReader br = new BufferedReader(isr);
                            if (br.ready()) {
                                rem = br.readLine();
                                project.getLogger().log(Level.INFO, "Adding rem '" + name + "' with value of '" + rem + "'!");
                            }
                        }
                        this.rems.put(name, rem);
                    }
                } catch (FileNotFoundException ex) {
                    project.getLogger().log(Level.SEVERE, "File not found!", ex);
                } catch (IOException ex) {
                    project.getLogger().log(Level.SEVERE, "Error opening rem files!", ex);
                }
            }
        }
    }
    
    /**
     * Returns a map of the currently loaded REMs
     * 
     * @since 1.0
     * @version 1.0
     * 
     * @return A {@link ConcurrentHashMap} of the current REMs
     */
    public Map<String, String> getRems() {
        return this.rems;
    }
    
    /**
     * Gets the data directory where REM files are stored
     * 
     * @since 1.0
     * @version 1.0
     * 
     * @return A {@link File} directory of REM locations
     */
    public File getDataDirectory() {
        return this.textFolder;
    }
}