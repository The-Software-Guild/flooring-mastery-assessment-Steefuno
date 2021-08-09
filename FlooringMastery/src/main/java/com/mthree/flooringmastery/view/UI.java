/*
 * @author Steven Nguyen
 * @email: steven.686295@gmail.com
 * @date: 09 Aug 2021
 */
package com.mthree.flooringmastery.view;

import java.math.BigDecimal;

/**
 *
 * @author Steven
 */
public interface UI {
    /**
     * Displays a message to the user
     * @param message the message to display
     */
    public void say(String message);
    
    /**
     * Reads a string inputted by the user
     * @return the string
     */
    public String readString();
    
    /**
     * Reads a number inputted by the user
     * 2 decimal places
     * @return the BigNumber
     */
    public BigDecimal readNumber();
}
