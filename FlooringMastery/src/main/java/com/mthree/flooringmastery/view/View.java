/*
 * @author Steven Nguyen
 * @email: steven.686295@gmail.com
 * @date: 
 */

package com.mthree.flooringmastery.view;

import com.mthree.flooringmastery.model.Order;
import com.mthree.flooringmastery.model.OrderDetails;
import com.mthree.flooringmastery.model.OrderID;
import com.mthree.flooringmastery.model.ProductType;
import com.mthree.flooringmastery.model.State;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 
 * @author Steven
 */
public class View {
    final private UI ui;
    
    /**
     * Constructs a new View given the UI to work with
     * @param ui the user interface
     */
    public View(UI ui) {
        this.ui = ui;
    }
    
    /**
     * Displays an error to the user
     * @param message the message to display
     */
    public void error(String message) {
        
    }
    
    /**
     * Displays an array of orders to the user
     * @param orders the array of orders
     */
    public void displayOrders(Order[] orders) {
        
    }
    
    /**
     * Displays an order to the user
     * @param order the order to display
     */
    public void displayOrder(Order order) {
        
    }
    
    /**
     * Prompts the user to select a menu item
     * @return the user's selection
     */
    public String promptMenu() {
        
    }
    
    /**
     * Prompts the user to create a new order
     * @param states the states the user can select
     * @param productTypes the types of products the user can select
     * @return the order details of the order created by the user
     */
    public OrderDetails promptNewOrder(State[] states, ProductType[] productTypes) {
        
    }
    
    /**
     * Prompts the user to edit an order
     * @param orderDetails the order details before being changed by the user
     * @param states the states the user can select
     * @param productTypes the types of products the user can select
     * @return the order details of the changes to the order the user wants
     */
    public OrderDetails promptEditOrder(OrderDetails orderDetails, State[] states, ProductType[] productTypes) {
        
    }
    
    /**
     * Prompts the user to select a date
     * @param message the message to display to the user
     * @return the date the user selected
     */
    public LocalDate promptDate(String message) {
        
    }
    
    /**
     * Prompts the user to select an order id
     * @param message the message to display to the user
     * @return the orderID the user selected
     */
    private OrderID promptOrderID(String message) {
        
    }
    
    /**
     * Prompts the user to input a String
     * @param message the message to display to the user
     * @return the String the user inputted
     */
    private String promptString(String message) {
        
    }
    
    /**
     * Prompts the user to select an item from an array
     * Uses the toString method of each object to display the item
     * @param message the message to display to the user before listing all the object
     * @param items the array of objects to display to the user
     * @return 
     */
    private Object promptArrayItem(String message, Object[] items) {
        
    }
    
    /**
     * Prompts the user to input a number
     * @param message the message to display to the user
     * @return the BigDecimal the user inputted
     */
    private BigDecimal promptNumber(String message) {
        
    }
}
