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
import java.util.logging.Handler;
import java.util.logging.LogRecord;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

/**
 * A custom handler for {@link SPLogger}, which logs to a {@link JTextArea}
 *
 * @since 1.0
 * @author 1Rogue
 * @version 1.0
 */
public class SPHandler extends Handler {

    private final JTextArea output;
    private final SimpleProject project;

    public SPHandler(SimpleProject project) {
        this.output = new JTextArea();
        this.output.setEditable(false);
        this.output.setLineWrap(true);
        this.project = project;
    }

    /**
     * Action to take when something is logged. Writes to console {@link JTextArea}
     * 
     * @since 1.0
     * @version 1.0
     * 
     * @param record The LogRecord being written
     */
    @Override
    public void publish(final LogRecord record) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                output.append(record.getMessage() + "\n");
            }
        });
    }

    /**
     * How to clear the log. Sets the console as blank (no text)
     * 
     * @since 1.0
     * @version 1.0
     */
    @Override
    public void flush() {
        this.output.setText("");
    }

    /**
     * Does nothing, simply overrides the abstract method within {@link Handler}
     * 
     * @since 1.0
     * @version 1.0
     * 
     * @throws SecurityException Never actually thrown
     */
    @Override
    public void close() throws SecurityException {
    }
    
    /**
     * Gets the console window
     *
     * @since 1.0
     * @version 1.0
     *
     * @return The console {@link JTextArea}
     */
    public JTextArea getTextArea() {
        return this.output;
    }
}
