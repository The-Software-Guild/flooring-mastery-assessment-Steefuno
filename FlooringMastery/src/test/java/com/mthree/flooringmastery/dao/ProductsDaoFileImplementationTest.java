/*
 * @author Steven Nguyen
 * @email: steven.686295@gmail.com
 * @date: 
 */
package com.mthree.flooringmastery.dao;

import com.mthree.flooringmastery.model.ProductType;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Steven
 */
public class ProductsDaoFileImplementationTest {
    final private static String DATA_PATH = "./src/main/resources/TestFileData/";
    
    ProductsDao productsDao;
    
    public ProductsDaoFileImplementationTest() {
        productsDao = new ProductsDaoFileImplementation(DATA_PATH + "Data/Products");
    }

    /**
     * Test of getAllProductTypes method, of class ProductsDaoFileImplementation.
     */
    @Test
    public void Should_Get_All_Product_Types() {
        HashMap<String, Boolean> foundProductTypes;
        ProductType[] productTypes;
        boolean result;
        
        foundProductTypes = new HashMap(2);
        foundProductTypes.put("P1", Boolean.FALSE);
        foundProductTypes.put("P2", Boolean.FALSE);
        
        productTypes = productsDao.getAllProductTypes();
        for (var productType: productTypes) {
            foundProductTypes.put(productType.getName(), Boolean.TRUE);
        }
        
        result = true;
        for (var isFound: foundProductTypes.values()) {
            result = result && isFound;
        }
        
        assertTrue(
            result,
            "P1 and P2 must be in the list of products."
        );
    }

    /**
     * Tests the getProductType function and loading from file
     */
    @Test
    public void Should_Get_The_Product_Type_Normally() {
        ProductType productType;
        
        productType = productsDao.getProductType("P1");
        
        assertEquals(
            productType.getName(),
            "P1",
            "Should have gotten P1"
        );
        assertEquals(
            productType.getLaborCostPerSquareFoot(),
            (new BigDecimal("10.00")).setScale(2, RoundingMode.HALF_UP),
            "Labor cost per sq ft should be $10.00."
        );
        assertEquals(
            productType.getMaterialCostPerSquareFoot(),
            (new BigDecimal("1.00")).setScale(2, RoundingMode.HALF_UP),
            "Material cost per sq ft should be $1.00."
        );
    }
    
}
