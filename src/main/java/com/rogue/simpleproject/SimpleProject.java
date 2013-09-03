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
package com.rogue.simpleproject;

import com.rogue.simpleproject.command.CommandHandler;
import com.rogue.simpleproject.data.DataHandler;
import com.rogue.simpleproject.gui.GUIManager;
import com.rogue.simpleproject.listener.SPListener;
import com.rogue.simpleproject.logger.SPLogger;
import java.io.File;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import javax.swing.SwingUtilities;

/**
 * Main class
 *
 * @since 1.0
 * @author 1Rogue
 * @version 1.0
 */
public class SimpleProject extends Start {

    private final GUIManager gui;
    private final CommandHandler command;
    private final SPListener listener;
    private final SPLogger logger;
    private final DataHandler data;
    private boolean running = true;

    /**
     * Starts the main instance of the program.
     *
     * @since 1.0
     * @version 1.0
     *
     * @param useGUI Whether to use the {@link GUIManager} or command line
     */
    public SimpleProject(boolean useGUI) {
        
        final long startTime = System.nanoTime();

        this.logger = new SPLogger(this);

        if (useGUI) {
            this.getLogger().log(Level.INFO, "Enabling listener...");
            this.listener = new SPListener(this);

            this.getLogger().log(Level.INFO, "Enabling GUI...");
            this.gui = new GUIManager(this);
            this.gui.init();
        } else {
            this.listener = null;
            this.gui = null;
        }

        this.getLogger().log(Level.INFO, "Enabling Command Handler...");
        this.command = new CommandHandler(this);


        this.getLogger().log(Level.INFO, "Enabling Data Handler...");
        this.data = new DataHandler(this);
        this.data.verify("data" + File.separator);
        
        final SimpleProject proj = this;
        if (useGUI) {
            SwingUtilities.invokeLater(new Runnable() {

                public void run() {
                    proj.getGUI().getWindow().setVisible(true);
                    proj.getLogger().log(Level.INFO, "Enabling input...");
                    proj.getGUI().getWindow().setInputEditable(true);
                    final long stopTime = System.nanoTime();
                    proj.getLogger().log(Level.INFO, "Enabled! " + proj.readableProfile(stopTime - startTime));
                }
            });
        }
    }
    
    /**
     * Makes a long-type time value into a readable string.
     *
     * @since 1.0
     * @version 1.0
     *
     * @param time The time value as a long
     * @return Readable String of the time
     */
    private String readableProfile(long time) {
        int i;
        String[] units = new String[]{"ms", "s", "m", "hr", "day", "week", "mnth", "yr"};
        int[] metric = new int[]{1000, 60, 60, 24, 7, 30, 12};
        long current = TimeUnit.MILLISECONDS.convert(time, TimeUnit.NANOSECONDS);

        for (i = 0; current > metric[i]; i++) {
            current /= metric[i];
        }

        return current + " " + units[i] + ((current > 1 && i > 1) ? "s" : "");
    }

    /**
     * Returns a static instance of the project
     *
     * @since 1.0
     * @version 1.0
     *
     * @return The {@link SimpleProject} instance
     */
    public static SimpleProject getProject() {
        return instance;
    }

    /**
     * Exits the project cleanly, clearing up any running tasks
     *
     * @since 1.0
     * @version 1.0
     *
     * @param exitCode The system exit code to use
     */
    public void exit(int exitCode) {
        this.running = false;
        System.exit(exitCode);
    }

    /**
     * Returns the GUI Manager for the project
     *
     * @since 1.0
     * @version 1.0
     *
     * @return The {@link GUIManager} instance
     */
    public GUIManager getGUI() {
        return this.gui;
    }

    /**
     * Returns the command handler for the project
     *
     * @since 1.0
     * @version 1.0
     *
     * @return The {@link CommandHandler} instance
     */
    public CommandHandler getCommandHandler() {
        return this.command;
    }

    /**
     * Returns the listener for the project
     *
     * @since 1.0
     * @version 1.0
     *
     * @return The {@link SPListener} instance
     */
    public SPListener getListener() {
        return this.listener;
    }

    /**
     * A simple boolean for if the application is running or not
     *
     * @since 1.0
     * @version 1.0
     *
     * @return True if running, false otherwise
     */
    public boolean isRunning() {
        return this.running;
    }

    /**
     * Gets the logger for the project
     *
     * @since 1.0
     * @version 1.0
     *
     * @return The {@link SPLogger} for {@link SimpleProject}
     */
    public final SPLogger getLogger() {
        return this.logger;
    }
    /**
     * Gets the data handler for the project
     *
     * @since 1.0
     * @version 1.0
     *
     * @return The {@link DataHandler} for {@link SimpleProject}
     */
    public DataHandler getDataHandler() {
     return data;
    }
}
