/*
 * @author Steven Nguyen
 * @email: steven.686295@gmail.com
 * @date: 
 */

package com.mthree.flooringmastery.dao;

import com.mthree.flooringmastery.model.State;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Scanner;

/**
 * 
 * @author Steven
 */
public class TaxesDaoFileImplementation implements TaxesDao {
    final private String path;
    final private HashMap<String, State> states;
    
    public TaxesDaoFileImplementation(String path) {
        this.path = path;
        this.states = new HashMap<>();
        
        load();
    }
    
    /**
     * Gets an array of all states
     * @return the array
     */
    @Override
    public State[] getAllStates() {
        Object[] values;
        State[] availableStates;
        
        values = states.values().toArray();
        availableStates = new State[values.length];
        
        for (int i = 0; i < values.length; i++) {
            availableStates[i] = (State) values[i];
        }
        
        return availableStates;
    }
    
    /**
     * Gets a state from the abbreviation
     * @param abbreviation the abbreviation of the state name
     * @return the State
     */
    @Override
    public State getState(String abbreviation) {
        return states.get(abbreviation.toLowerCase());
    }
    
    /**
     * Loads the states list from file
     */
    private void load() {
        Scanner sc;
        
        states.clear();
        
        // setup the scanner
        try {
            sc = new Scanner(
                new BufferedReader(
                    new FileReader(path)
                )
            );
        } catch (FileNotFoundException e) {
            System.out.println("Failed to read file to load states " + path + ".");
            System.exit(-1);
            return;
        }
        
        // extract each state from each line in the file
        sc.nextLine(); // ignore the header
        while (sc.hasNextLine()) {
            String line;

            line = sc.nextLine();
            loadLine(line);
        }
    }
    
    /**
     * Loads a line of the states list
     */
    private void loadLine(String line) {
        State state;
        String[] data;
        String abbreviation, name;
        BigDecimal taxRate;
        
        data = line.split("::");
        if (data.length != 3) {
            System.out.println("Bad Format: " + line);
            return;
        }
        
        // get the abbreviation
        abbreviation = data[0];
        
        // get the name
        name = data[1];
        
        // get the taxRate
        try {
            taxRate = (new BigDecimal(data[2])).setScale(2, RoundingMode.HALF_UP);
        } catch(NumberFormatException e) {
            System.out.println("Failed to read taxRate: " + line);
            return;
        }
        
        state = new State(
            abbreviation,
            name,
            taxRate
        );
        states.put(abbreviation.toLowerCase(), state);
    }
}
