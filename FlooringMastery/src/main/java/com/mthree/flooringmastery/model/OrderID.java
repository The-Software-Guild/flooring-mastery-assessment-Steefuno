/*
 * @author Steven Nguyen
 * @email: steven.686295@gmail.com
 * @date: 09 Aug 2021
 */

package com.mthree.flooringmastery.model;

import java.time.LocalDate;

/**
 * 
 * @author Steven
 */
public class OrderID {
    final private int number;
    final private LocalDate date;
    
    /**
     * Constructs the OrderID given the ID number and date
     * @param number the ID number
     * @param date the date
     */
    public OrderID(int number, LocalDate date) {
        this.number = number;
        this.date = date;
    }
    
    /**
     * Gets the number
     * @return the number
     */
    public int getNumber() {
        return number;
    }
    
    /**
     * Gets the date
     * @return the date
     */
    public LocalDate getDate() {
        return date;
    }
}
