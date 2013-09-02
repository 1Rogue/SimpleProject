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
package com.rogue.simpleproject.gui;

import com.rogue.simpleproject.SimpleProject;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @since 1.0
 * @author 1Rogue
 * @version 1.0
 */
public class SPWindow extends JFrame {

    private SimpleProject project;
    private JTextArea textbox;
    private JTextField input;

    public SPWindow(SimpleProject project) {
        this.project = project;
        this.openWindow();
    }

    private void openWindow() {
        this.setTitle("SimpleProject");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setLayout(new GridBagLayout());
        this.setSize(750, 475);
        this.setupTextBoxes();
        this.setupTabbedPane();
        this.setVisible(true);
    }

    private void setupTextBoxes() {

        // Initialize variables
        this.textbox = new JTextArea();
        this.input = new JTextField();

        // Set listeners
        this.input.setActionCommand("input");
        this.input.addActionListener(project.getListener());

        // Put console in a scroll pane
        JScrollPane output = new JScrollPane(textbox);
        output.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        output.setPreferredSize(new Dimension(450, 350));
        output.setMinimumSize(new Dimension(10, 10));

        // Add console and input into a split pane
        JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT, output, input);
        split.setOneTouchExpandable(false);
        split.setResizeWeight(0.3);

        // Overall GridBagConstraints
        GridBagConstraints overall = new GridBagConstraints();
        overall.ipady = 50;
        overall.ipadx = 50;
        overall.fill = GridBagConstraints.BOTH;
        overall.gridx = 0;
        overall.gridy = 0;
        overall.weightx = 1.0;
        overall.weighty = 1.0;

        // Add SplitPane to the main content pane
        this.getContentPane().add(split, overall);

    }

    private void setupTabbedPane() {

        // Initialize variables
        JTabbedPane settings = new JTabbedPane();
        JPanel menu = new JPanel();
        JPanel menu2 = new JPanel();

        // Menu GridBagConstraints
        GridBagConstraints inMenu = new GridBagConstraints();
        inMenu.fill = GridBagConstraints.CENTER;
        inMenu.gridx = 1;
        inMenu.gridy = 1;

        // Overall GridBagConstraints
        GridBagConstraints overall = new GridBagConstraints();
        overall.ipady = 50;
        overall.ipadx = 50;
        overall.fill = GridBagConstraints.BOTH;
        overall.gridx = 1;
        overall.gridy = 0;
        overall.weightx = 1.0;
        overall.weighty = 1.0;
        overall.anchor = GridBagConstraints.EAST;

        // Set menu layouts and add an example button
        menu.setLayout(new GridBagLayout());
        menu.add(new JButton("Example"), inMenu);
        menu2.setLayout(new GridBagLayout());
        menu2.add(new JButton("Example Two"), inMenu);

        // Add menus to tabbed pane
        settings.add("Manu One", menu);
        settings.add("Menu Two", menu2);

        // Add tabbed pane to main content pane
        this.getContentPane().add(settings, overall);
    }

    public JTextArea getTextArea() {
        return this.textbox;
    }

    public JTextField getInputField() {
        return this.input;
    }
    
    public JTextField setInputField(String input) {
        this.input.setText("");
        return this.input;
    }

    public void pipeOutput() {
        //TODO Replace using the logger class
        try {
            PipedOutputStream pOut = new PipedOutputStream();
            System.setOut(new PrintStream(pOut));
            PipedInputStream pIn = new PipedInputStream(pOut);
            BufferedReader reader = new BufferedReader(new InputStreamReader(pIn));
            while (this.project.isRunning()) {
                try {
                    String line = reader.readLine();
                    if (line != null) {
                        this.textbox.append(line);
                        this.textbox.append("\n");
                    }
                } catch (IOException ex) {
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(SPWindow.class.getName()).log(Level.SEVERE, "Error piping output to console, exiting!", ex);
            this.project.exit(1);
        }
    }
}
