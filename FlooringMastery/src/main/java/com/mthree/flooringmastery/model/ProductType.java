/*
 * @author Steven Nguyen
 * @email: steven.686295@gmail.com
 * @date: 09 Aug 2021
 */

package com.mthree.flooringmastery.model;

import java.math.BigDecimal;

/**
 * 
 * @author Steven
 */
public class ProductType {
    final private String name;
    final private BigDecimal materialCostPerSquareFoot;
    final private BigDecimal laborCostPerSquareFoot;
    
    /**
     * Constructs a new ProductType given the name and costs per square foot
     * @param name the name
     * @param materialCostPerSquareFoot the material cost per square foot
     * @param laborCostPerSquareFoot the labor cost per square foot
     */
    public ProductType(String name, BigDecimal materialCostPerSquareFoot, BigDecimal laborCostPerSquareFoot) {
        this.name = name;
        this.materialCostPerSquareFoot = materialCostPerSquareFoot;
        this.laborCostPerSquareFoot = laborCostPerSquareFoot;
    }
    
    /**
     * Gets the name
     * @return the name
     */
    public String getName() {
        return name;
    }
    
    /**
     * Gets the material cost per square foot
     * @return the material cost per square foot
     */
    public BigDecimal getMaterialCostPerSquareFoot() {
        return materialCostPerSquareFoot;
    }
    
    /**
     * Gets the labor cost per square foot
     * @return the labor cost per square foot
     */
    public BigDecimal getLaborCostPerSquareFoot() {
        return laborCostPerSquareFoot;
    }
}
