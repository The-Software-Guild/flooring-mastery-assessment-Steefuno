/*
 * @author Steven Nguyen
 * @email: steven.686295@gmail.com
 * @date: 09 Aug 2021
 */

package com.mthree.flooringmastery.model;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 
 * @author Steven
 */
public class OrderDetails {
    final private LocalDate date;
    final private String customerName;
    final private State state;
    final private ProductType productType;
    final private BigDecimal area;
    
    /**
     * Constructs a new OrderDetails given the details
     * @param date the date of the order
     * @param customerName the name of the customer
     * @param state the state of the order
     * @param productType the product type of the order
     * @param area the size of the order
     */
    public OrderDetails(LocalDate date, String customerName, State state, ProductType productType, BigDecimal area) {
        this.date = date;
        this.customerName = customerName;
        this.state = state;
        this.productType = productType;
        this.area = area;
    }
    
    /**
     * Gets the date
     * @return the date
     */
    public LocalDate getDate() {
        return date;
    }
    
    /**
     * Gets the customer name
     * @return the customer name
     */
    public String getCustomerName() {
        return customerName;
    }
    
    /**
     * Gets the state
     * @return the state
     */
    public State getState() {
        return state;
    }
    
    /**
     * Gets the product type
     * @return the product type
     */
    public ProductType getProductType() {
        return productType;
    }
    
    /**
     * Gets the area
     * @return the area
     */
    public BigDecimal getArea() {
        return area;
    }
}
