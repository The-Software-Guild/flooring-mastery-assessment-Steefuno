/*
 * @author Steven Nguyen
 * @email: steven.686295@gmail.com
 * @date: 09 Aug 2021
 */

package com.mthree.flooringmastery.view;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Scanner;


/**
 * 
 * @author Steven
 */
public class UIConsoleImplementation implements UI {
    final private Scanner inputReader;
    
    /**
     * Constructs a new UIConsoleImplementation
     */
    public UIConsoleImplementation() {
        inputReader = new Scanner(System.in);
    }
    
    /**
     * Displays a message to the user
     * @param message the message to display
     */
    @Override
    public void say(String message) {
        System.out.println(message);
    }
    
    /**
     * Reads a string inputted by the user
     * @return the string
     */
    @Override
    public String readString() {
        return inputReader.nextLine();
    }
    
    /**
     * Reads a number inputted by the user
     * 2 decimal places
     * @return the BigNumber
     */
    @Override
    public BigDecimal readNumber() {
        String line;
        BigDecimal number;
        
        line = inputReader.nextLine();
        number = (new BigDecimal(line)).setScale(2, RoundingMode.HALF_UP);
        
        return number;
    }
}
