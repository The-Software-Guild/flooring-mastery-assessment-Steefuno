/*
 * @author Steven Nguyen
 * @email: steven.686295@gmail.com
 * @date: 
 */
package com.mthree.flooringmastery.dao;

import com.mthree.flooringmastery.model.State;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Steven
 */
public class TaxesDaoFileImplementationTest {
    final private static String DATA_PATH = "./src/main/resources/TestFileData/";
    
    TaxesDao taxesDao;
    
    public TaxesDaoFileImplementationTest() {
        taxesDao = new TaxesDaoFileImplementation(DATA_PATH + "Data/Taxes");
    }

    /**
     * Test of getAllStates method
     */
    @Test
    public void Should_Get_All_States() {
        HashMap<String, Boolean> foundStates;
        State[] states;
        boolean result;
        
        foundStates = new HashMap(2);
        foundStates.put("1", Boolean.FALSE);
        foundStates.put("2", Boolean.FALSE);
        
        states = taxesDao.getAllStates();
        for (var state: states) {
            foundStates.put(state.getAbbreviation(), Boolean.TRUE);
        }
        
        result = true;
        for (var isFound: foundStates.values()) {
            result = result && isFound;
        }
        
        assertTrue(
            result,
            "S1 and S2 must be in the list of products."
        );
    }

    /**
     * Tests the getState function
     */
    @Test
    public void Should_Get_The_Product_Type_Normally() {
        State state;
        
        state = taxesDao.getState("1");
        
        assertEquals(
            state.getName(),
            "S1",
            "Should have gotten S1"
        );
        assertEquals(
            state.getTaxRate(),
            (new BigDecimal("5.00")).setScale(2, RoundingMode.HALF_UP),
            "Tax rate should be $5.00."
        );
    }
}
