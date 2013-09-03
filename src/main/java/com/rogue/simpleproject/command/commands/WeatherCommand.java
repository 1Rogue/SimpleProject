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
import static com.rogue.simpleproject.command.Command.project;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;

/**
 * Allows checking the weather based on various information
 *
 * @since 1.0
 * @author 1Rogue
 * @version 1.0
 */
public class WeatherCommand implements Command {

    /**
     * This is an API key for the website used. I'm not exactly worried about
     * cryptography or hiding this key in a database or any secrecy, it's a
     * simple key that I will probably only ever use once.
     */
    private final String weatherKey = "hza4uacyx6r3qfsaajq7vzk7";
    private final String link = "http://api.worldweatheronline.com/free/v1/weather.ashx?key=hza4uacyx6r3qfsaajq7vzk7&num_of_days=1&format=csv&q=";

    public boolean execute(String[] args) {
        if (args.length < 1) {
            return false;
        }
        final String base = "Current Conditions for %s: %sC/%sF Max, %sC/%sF Min, Winds out of the %s at %sMPH/%sKPH, Currently %s with %smm precipitation";
        StringBuilder sb = new StringBuilder();
        for (String arg : args) {
            sb.append(arg).append(" ");
        }
        String argument = sb.toString().trim();
        argument = argument.replace(' ', '+');
        try {
            URL url = new URL(link + argument);
            InputStream stream = url.openStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            String raw = "";
            if (reader.ready()) {
                String line = null, temp;
                while ((temp = reader.readLine()) != null) {
                    line = temp;
                }
                raw = line;
            }
            String[] weatherBits = raw.split(",");
            project.getLogger().log(Level.INFO, String.format(
                    base,
                    argument.replace('+', ' '),
                    weatherBits[1],
                    weatherBits[2],
                    weatherBits[3],
                    weatherBits[4],
                    weatherBits[8],
                    weatherBits[5],
                    weatherBits[6],
                    weatherBits[11],
                    weatherBits[12]));
        } catch (MalformedURLException ex) {
            project.getLogger().log(Level.SEVERE, "Bad weather URL!", ex);
        } catch (IOException ex) {
            project.getLogger().log(Level.SEVERE, "Error retrieving weather info!", ex);
        }
        return true;
    }

    public String getName() {
        return "weather";
    }

    public String[] getHelp() {
        return new String[]{
            "Command usage: /weather <identifying-info>",
            "The command can take many forms of information. The types currently supported:",
            "US Zipcode, UK Postcode, Canada Postalcode, IP address, Latitude/Longitude (decimal degree) or city name"
        };
    }
}
