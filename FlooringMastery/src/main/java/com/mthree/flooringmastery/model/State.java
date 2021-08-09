/*
 * @author Steven Nguyen
 * @email: steven.686295@gmail.com
 * @date: 09 Aug 2021
 */

package com.mthree.flooringmastery.model;

import java.math.BigDecimal;

/**
 * 
 * @author Steven
 */
public class State {
    final private String abbreviation;
    final private String name;
    final private BigDecimal taxRate;
    
    /**
     * Constructs a new State given the abbreviation, name, and tax rate
     * @param abbreviation the abbreviation of the state name
     * @param name the state name
     * @param taxRate the tax rate of the state as a percentage
     */
    public State(String abbreviation, String name, BigDecimal taxRate) {
        this.abbreviation = abbreviation;
        this.name = name;
        this.taxRate = taxRate;
    }
    
    /**
     * Gets the abbreviation
     * @return the abbreviation
     */
    public String getAbbreviation() {
        return abbreviation;
    }
    
    /**
     * Gets the name
     * @return the name
     */
    public String getName() {
        return name;
    }
    
    /**
     * Gets the tax rate
     * @return the tax rate
     */
    public BigDecimal getTaxRate() {
        return taxRate;
    }
}
