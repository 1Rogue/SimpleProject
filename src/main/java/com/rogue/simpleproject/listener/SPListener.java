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
package com.rogue.simpleproject.listener;

import com.rogue.simpleproject.SimpleProject;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;

/**
 * Listener for {@link SimpleProject}.
 *
 * @since 1.0
 * @author 1Rogue
 * @version 1.0
 */
public class SPListener implements ActionListener {

    private final SimpleProject project;

    /**
     * Initializes the listener
     *
     * @since 1.0
     * @version 1.0
     *
     * @param project The {@link SimpleProject} instance
     */
    public SPListener(SimpleProject project) {
        this.project = project;
    }

    /**
     * Fired when an action event is triggered. Currently only linked with the
     * {@link JTextField} used for input, may be linked to buttons later.
     *
     * @since 1.0
     * @version 1.0
     *
     * @param e The {@link ActionEvent} being fired
     */
    public synchronized void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("input")) {
            String text = this.project.getGUI().getWindow().getInputField().getText();
            if (text.isEmpty()) {
                return;
            }
            this.project.getGUI().getWindow().setInputField("");
            project.getLogger().log(Level.OFF, text);
            if (text.startsWith("/")) {
                this.project.getCommandHandler().parseCommand(text);
            }
            if (text.startsWith("$")) {
                String[] args = text.split(" ");
                String rem = args[0].substring(1);
                String back = this.project.getDataHandler().getRem(rem);
                if (back != null) {
                    String[] newArgs = new String[args.length - 1];
                    for (int i = 1; i < args.length; i++) {
                        newArgs[i - 1] = args[i];
                    }
                    back = this.project.getDataHandler().formatRem(rem, newArgs);
                } else {
                    project.getLogger().log(Level.INFO, "back is null!");
                }
                project.getLogger().log(Level.INFO, back);
            }
        }
    }
}
