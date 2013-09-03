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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;

/**
 * Instantiable SQLite connector
 *
 * @since 1.0
 * @author 1Rogue
 * @version 1.0
 */
public class SQLite {

    private static int connections = 0;
    private static File dataLocation = null;
    private Connection con = null;
    private SimpleProject project = SimpleProject.getProject();

    /**
     * Opens a connection to the SQLite database. Make sure to call
     * SQLite.close() after you are finished working with the database for your
     * segment of your code.
     *
     * Unfortunately the Computer Science machines don't have any SQLite drivers
     * on the machines, so using SQLite is pretty much out the window after
     * writing this.
     *
     * @since 1.0
     * @version 1.0
     *
     * @return The Connection object
     * @throws SQLException If database access error occurs
     */
    public Connection open() throws SQLException {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException ex) {
            if (project == null) {
                System.out.println("Project is null!");
            }
            project.getLogger().log(Level.SEVERE, "Error loading SQLite connection, aborting!", ex);
            throw new SQLException("SQLite drivers not found!");
        }
        con = DriverManager.getConnection("jdbc:sqlite:" + this.dataLocation);
        project.getLogger().log(Level.INFO, "Open SQLite connections: {0}", ++this.connections);
        return con;
    }

    /**
     * Checks if a table exists within the set database
     *
     * @since 1.0
     * @version 1.0
     *
     * @param tablename Name of the table to check for
     * @return true if exists, false otherwise
     * @throws SQLException If database access error occurs
     */
    public boolean checkTable(String tablename) throws SQLException {
        ResultSet count = query("SELECT COUNT(*) FROM sqlite_master WHERE type='table' AND name='" + tablename + "'");
        int i = 0;
        if (count.next()) {
            i = count.getInt(1);
        }
        count.close();
        return (i == 1) ? true : false;
    }

    /**
     * Executes a query, but does not update any information nor lock the
     * database
     *
     * @since 1.0
     * @version 1.0
     *
     * @param query The string query to execute
     * @return A ResultSet from the query
     * @throws SQLException If database access error occurs or bad query
     */
    public ResultSet query(String query) throws SQLException {
        Statement stmt = con.createStatement();
        return stmt.executeQuery(query);
    }

    /**
     * Executes a query that can change values, and will lock the database for
     * the duration of the query
     *
     * @since 1.0
     * @version 1.0
     *
     * @param query The string query to execute
     * @return 0 for no returned results, or the number of returned rows
     * @throws SQLException If database access error occurs or bad query
     */
    public synchronized int update(String query) throws SQLException {
        Statement stmt = con.createStatement();
        return stmt.executeUpdate(query);
    }

    /**
     * Closes the SQLite connection. Must be open first.
     *
     * @since 1.0
     * @version 1.0
     *
     * @throws SQLException If database access error occurs
     */
    public void close() throws SQLException {
        con.close();
        project.getLogger().log(Level.INFO, "Open SQLite connections: {0}", --this.connections);
    }

    /**
     * Sets the location of the database to use. Must be called before using the
     * SQLite class
     *
     * @since 1.0
     * @version 1.0
     *
     * @param location The location of the database, in {@link File} form
     */
    public void setDatabaseLocation(File location) {
        this.dataLocation = location;
    }
}
