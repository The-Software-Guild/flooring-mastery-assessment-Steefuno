/*
 * @author Steven Nguyen
 * @email: steven.686295@gmail.com
 * @date: 09 Aug 2021
 */

package com.mthree.flooringmastery.controller;

import com.mthree.flooringmastery.service.DaoService;
import com.mthree.flooringmastery.view.View;

/**
 * 
 * @author Steven
 */
public class Controller {
    final private View view;
    final private DaoService daoService;
    
    /**
     * Constructs a new Controller given the view and dao service
     * @param view the object that handles the UI
     * @param daoService the object that handles accessing and storing data
     */
    public Controller(View view, DaoService daoService) {
        this.view = view;
        this.daoService = daoService;
    }
    
    /**
     * Runs the controller
     */
    public void run() {
        
    }
    
    /**
     * Prompts the user to select a menu option
     * @return the user's selection
     */
    private String promptMenu() {
        
    }
    
    /**
     * Prompts the user to select an order, then displays the order's details
     */
    private void displayOrders() {
        
    }
    
    /**
     * Prompts the user for details in adding an order, displays the order's details,
     * prompts the user if they want to add this order, then adds the order
     */
    private void addOrder() {
        
    }
    
    /**
     * Prompts the user to select an order, then input new order details, displays the order's details,
     * prompts the user if they want to keep the changes, and then store the edits in memory
     */
    private void editOrder() {
        
    }
    
    /**
     * Prompts the user to select an order, displays the order's details,
     * prompts the user if they want to delete the order, and delete the order if so
     */
    private void removeOrder() {
        
    }
    
    /**
     * Store all data from memory into file
     */
    private void export() {
        
    }
}
