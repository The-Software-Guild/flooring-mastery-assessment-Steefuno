/*
 * @author Steven Nguyen
 * @email: steven.686295@gmail.com
 * @date: 
 */
package com.mthree.flooringmastery.dao;

import com.mthree.flooringmastery.model.Order;
import com.mthree.flooringmastery.model.OrderDetails;
import com.mthree.flooringmastery.model.OrderID;
import java.time.LocalDate;
import java.util.HashMap;

/**
 *
 * @author Steven
 */
public interface OrdersDao {
    /**
     * Gets an order given the date and order number
     * @param orderID the order id
     * @return the order or null
     */
    public Order getOrder(OrderID orderID);
    
    /**
     * Gets a hashmap of all the orders on a select date
     * @param date the date
     * @return the hashmap
     */
    public HashMap<Integer, Order> getOrders(LocalDate date);
    
    /**
     * Constructs and adds an order
     * @param orderDetails the details of the new order
     * @return the id of the new order
     */
    public OrderID addOrder(OrderDetails orderDetails);
    
    /**
     * Calculates an order given the details, and DOES NOT save the order to memory
     * @param orderDetails the details of the order
     * @return the order
     */
    public Order calculateOrder(OrderDetails orderDetails);
    
    /**
     * Edits an existing order
     * @param orderID the id of the order
     * @param newOrder the new order to replace the old order
     */
    public void editOrder(OrderID orderID, Order newOrder) throws OrderDoesNotExistException;
    
    /**
     * Removes an order from memory
     * @param orderID the id of the order
     */
    public void removeOrder(OrderID orderID) throws OrderDoesNotExistException;
    
    /**
     * Saves all orders in memory to file
     */
    public void export();
}
