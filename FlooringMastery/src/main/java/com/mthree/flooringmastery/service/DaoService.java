/*
 * @author Steven Nguyen
 * @email: steven.686295@gmail.com
 * @date: 10 Aug 2021
 */

package com.mthree.flooringmastery.service;

import com.mthree.flooringmastery.dao.OrdersDao;
import com.mthree.flooringmastery.dao.ProductsDao;
import com.mthree.flooringmastery.dao.TaxesDao;
import com.mthree.flooringmastery.model.Order;
import com.mthree.flooringmastery.model.OrderDetails;
import com.mthree.flooringmastery.model.OrderID;
import com.mthree.flooringmastery.model.ProductType;
import com.mthree.flooringmastery.model.State;
import java.time.LocalDate;

/**
 * 
 * @author Steven
 */
public class DaoService {
    final private OrdersDao ordersDao;
    final private ProductsDao productsDao;
    final private TaxesDao taxesDao;
    
    /**
     * Constructs a new DaoService given the orders DAO, products DAO, and Taxes DAO
     * @param ordersDao the DAO for orders
     * @param productsDao the DAO for products
     * @param taxesDao the DAO for state taxes
     */
    public DaoService(OrdersDao ordersDao, ProductsDao productsDao, TaxesDao taxesDao) {
        this.ordersDao = ordersDao;
        this.productsDao = productsDao;
        this.taxesDao = taxesDao;
    }
    
    /**
     * Gets an order given the id
     * @param orderID the id of the order
     * @return the order
     */
    public Order getOrder(OrderID orderID) {
        return ordersDao.getOrder(orderID);
    }
    
    /**
     * Gets all orders of a given date
     * @param date the date to find the orders of
     * @return the array of orders
     */
    public Order[] getOrders(LocalDate date) {
        return ordersDao.getOrders(date);
    }
    
    /**
     * Constructs a new Order and adds to the collection in memory
     * @param orderDetails the details of the order to create
     * @return the ID of the new order
     */
    public OrderID addOrder(OrderDetails orderDetails) {
        return ordersDao.addOrder(orderDetails);
    }
    
    /**
     * Constructs a new Order
     * @param orderDetails the details of the order to create
     * @return the order
     */
    public Order calculatOrder(OrderDetails orderDetails) {
        return ordersDao.calculateOrder(orderDetails);
    }
    
    /**
     * Replaces an Order in memory with a new order
     * @param orderID the ID of the order to replace
     * @param order the new order data
     */
    public void editOrder(OrderID orderID, Order order) {
        ordersDao.editOrder(orderID, order);
    }
    
    /**
     * Removes an order from memory
     * @param orderID the ID of the order
     */
    public void removeOrder(OrderID orderID) {
        ordersDao.removeORder(orderID);
    }
    
    /**
     * Gets all product types
     * @return an array of product types
     */
    public ProductType[] getAllProductTypes() {
        return productsDao.getAllProductTypes();
    }
    
    public State[] getAllStates() {
        return taxesDao.getAllStates();
    }
    
    public void export() {
        ordersDao.export();
    }
}
