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

/**
 *
 * @since 1.0
 * @author 1Rogue
 * @version 1.0
 */
public class SPListener implements ActionListener {
    
    private final SimpleProject project;

    public SPListener(SimpleProject project) {
        this.project = project;
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("input")) {
            String text = this.project.getGUI().getWindow().getInputField().getText();
            if (text.startsWith("/")) {
                this.project.getCommandHandler().parseCommand(text);
            } else {
                System.out.println("[]> " + text);
            }
            this.project.getGUI().getWindow().setInputField("");
        }
    }
}
