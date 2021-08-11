/*
 * @author Steven Nguyen
 * @email: steven.686295@gmail.com
 * @date: 
 */

package com.mthree.flooringmastery.dao;

import com.mthree.flooringmastery.model.Order;
import java.time.LocalDate;
import java.util.HashMap;

/**
 * 
 * @author Steven
 */
public class BackupDaoFileImplementation {
    final private String path;
    
    /**
     * Constructs a new BackupDaoFileImplementation
     * @param path the path to the backup file
     */
    public BackupDaoFileImplementation(String path) {
        this.path = path;
    }
    
    /**
     * Saves all orders to one backup
     * @param orders a hashmap of all the orders
     */
    public void save(HashMap<LocalDate, HashMap<Integer, Order>> orders) {
        
    }
}
