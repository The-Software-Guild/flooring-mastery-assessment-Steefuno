/*
 * @author Steven Nguyen
 * @email: steven.686295@gmail.com
 * @date: 
 */
package com.mthree.flooringmastery.dao;

import com.mthree.flooringmastery.model.State;

/**
 *
 * @author Steven
 */
public interface TaxesDao {
    /**
     * Gets an array of all states
     * @return the array
     */
    public State[] getAllStates();
    
    /**
     * Gets a state from the abbreviation
     * @param abbreviation the abbreviation of the state name
     * @return the State
     */
    public State getState(String abbreviation);
}
