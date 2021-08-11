/*
 * @author Steven Nguyen
 * @email: steven.686295@gmail.com
 * @date: 
 */

package com.mthree.flooringmastery.dao;

import com.mthree.flooringmastery.model.ProductType;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Scanner;

/**
 * 
 * @author Steven
 */
public class ProductsDaoFileImplementation implements ProductsDao {
    final private String path;
    final private HashMap<String, ProductType> products;
    
    public ProductsDaoFileImplementation(String path) {
        this.path = path;
        this.products = new HashMap<>();
        
        load();
    }
    
    /**
     * Gets an array of all product types
     * @return the array
     */
    @Override
    public ProductType[] getAllProductTypes() {
        Object[] values;
        ProductType[] productTypes;
        
        values = this.products.values().toArray();
        productTypes = new ProductType[values.length];
        
        for (int i = 0; i < values.length; i++) {
            productTypes[i] = (ProductType) values[i];
        }
        
        return productTypes;
    }
    
    /**
     * Gets a product type given its name
     * Not case sensitive
     * @param productTypeName the name of the product type
     * @return the product type
     */
    @Override
    public ProductType getProductType(String productTypeName) {
        return products.get(productTypeName.toLowerCase());
    }
    
    /**
     * Loads the products list from file
     */
    private void load() {
        Scanner sc;
        
        products.clear();
        
        // setup the scanner
        try {
            sc = new Scanner(
                new BufferedReader(
                    new FileReader(path)
                )
            );
        } catch (FileNotFoundException e) {
            System.out.println("Failed to read file to load products " + path + ".");
            System.exit(-1);
            return;
        }
        
        // extract each product from each line in the file
        sc.nextLine(); // ignore the header
        while (sc.hasNextLine()) {
            String line;

            line = sc.nextLine();
            loadLine(line);
        }
    }
    
    /**
     * Loads a line of the products list
     */
    private void loadLine(String line) {
        ProductType productType;
        String[] data;
        String name;
        BigDecimal materialCostPerSquareFoot, laborCostPerSquareFoot;
        
        data = line.split("::");
        if (data.length != 3) {
            System.out.println("Bad Format: " + line);
            return;
        }
        
        // get the name
        name = data[0];
        
        // get the material cost per square foot
        try {
            materialCostPerSquareFoot = (new BigDecimal(data[1])).setScale(2, RoundingMode.HALF_UP);
        } catch(NumberFormatException e) {
            System.out.println("Failed to read material cost per square foot: " + line);
            return;
        }
        
        // get the labor cost per square foot
        try {
            laborCostPerSquareFoot = (new BigDecimal(data[2])).setScale(2, RoundingMode.HALF_UP);
        } catch(NumberFormatException e) {
            System.out.println("Failed to read labor cost per square foot: " + line);
            return;
        }
        
        productType = new ProductType(
            name,
            materialCostPerSquareFoot,
            laborCostPerSquareFoot
        );
        products.put(name.toLowerCase(), productType);
    }
}
