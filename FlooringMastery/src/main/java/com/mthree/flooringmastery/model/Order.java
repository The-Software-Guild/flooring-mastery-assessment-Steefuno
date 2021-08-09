/*
 * @author Steven Nguyen
 * @email: steven.686295@gmail.com
 * @date: 
 */

package com.mthree.flooringmastery.model;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * 
 * @author Steven
 */
public class Order {
    private String customerName;
    private State state;
    private ProductType productType;
    private BigDecimal area;
    
    /**
     * Constructs a new Order given the customer's name; and the order's state, productType, and area
     * @param customerName the customer's name
     * @param state the order's state
     * @param productType the order's product type
     * @param area the order's area in square feet
     */
    public Order(String customerName, State state, ProductType productType, BigDecimal area) {
        this.customerName = customerName;
        this.state = state;
        this.productType = productType;
        this.area = area;
    }
    
    /**
     * Constructs a new Order given an OrderDetails
     * @param orderDetails the details of the order
     */
    public Order(OrderDetails orderDetails) {
        this(
            orderDetails.getCustomerName(),
            orderDetails.getState(),
            orderDetails.getProductType(),
            orderDetails.getArea()
        );
    }
    
    /**
     * Gets the customer's name
     * @return the customer's name
     */
    public String getCustomerName() {
        return customerName;
    }
    
    /**
     * Sets the customer's name
     * @param customerName the customer's name
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    
    /**
     * Gets the state
     * @return the state
     */
    public State getState() {
        return state;
    }
    
    /**
     * Sets the state
     * @param state the state
     */
    public void setState(State state) {
        this.state = state;
    }
    
    /**
     * Gets the product type
     * @return the product type
     */
    public ProductType getProductType() {
        return productType;
    }
    
    /**
     * Sets the product type
     * @param productType the product type
     */
    public void setProductType(ProductType productType) {
        this.productType = productType;
    }
    
    /**
     * Gets the area
     * @return the area in square feet
     */
    public BigDecimal getArea() {
        return area;
    }
    
    /**
     * Sets the area
     * @param area the area in square feet
     */
    public void setArea(BigDecimal area) {
        this.area = area;
    }
    
    /**
     * Gets the material cost
     * area * the material cost per square foot of the product
     * @return the material cost
     */
    public BigDecimal getMaterialCost() {
        return
            area.multiply(
                productType.getMaterialCostPerSquareFoot()
            ).setScale(2)
        ;
    }
    
    /**
     * Gets the labor cost
     * area * the labor cost per square foot of the product
     * @return the labor cost
     */
    public BigDecimal getLaborCost() {
        return
            area.multiply(
                productType.getLaborCostPerSquareFoot()
            ).setScale(2)
        ;
    }
    
    /**
     * Gets the tax
     * (material cost + labor cost) * tax%
     * @return the tax
     */
    public BigDecimal getTax() {
        return
            this.getMaterialCost().add(
                this.getLaborCost()
            ).multiply(
                state.getTaxRate().multiply(
                    new BigDecimal(0.01)
                )
            ).setScale(2)
        ;
    }
    
    /**
     * Gets the total
     * material cost + labor cost + tax
     * @return the total
     */
    public BigDecimal getTotal() {
        return
            this.getMaterialCost().add(
                this.getLaborCost()
            ).multiply(
                state.getTaxRate().multiply(
                    new BigDecimal(0.01)
                ).add(
                    new BigDecimal(1)
                )
            ).setScale(2)
        ;
    }
    
    /**
     * Gets if this Order is equal to another object
     * @param obj the object to compare to this order
     * @return true if equals, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        Order otherOrder;
        
        if ((obj == null) || (obj.getClass() != Order.class)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        
        otherOrder = (Order) obj;
        return (
            (this.customerName.equals(otherOrder.getCustomerName())) && 
            (this.state.equals(otherOrder.getState())) && 
            (this.productType.equals(otherOrder.getProductType())) &&
            (this.area.equals(otherOrder.getArea()))
        );
    }
}
