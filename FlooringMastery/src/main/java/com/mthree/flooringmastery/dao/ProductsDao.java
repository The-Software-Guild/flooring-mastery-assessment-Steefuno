/*
 * @author Steven Nguyen
 * @email: steven.686295@gmail.com
 * @date: 11 Aug 2021
 */
package com.mthree.flooringmastery.dao;

import com.mthree.flooringmastery.model.ProductType;

/**
 *
 * @author Steven
 */
public interface ProductsDao {
    /**
     * Gets an array of all product types
     * @return the array
     */
    public ProductType[] getAllProductTypes();
    
    /**
     * Gets a product type given its name
     * Not case sensitive
     * @param productTypeName the name of the product type
     * @return the product type
     */
    public ProductType getProductType(String productTypeName);
}
