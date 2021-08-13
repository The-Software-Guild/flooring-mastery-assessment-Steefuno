/*
 * @author Steven Nguyen
 * @email: steven.686295@gmail.com
 * @date: 
 */

package com.mthree.flooringmastery.dao;

import com.mthree.flooringmastery.model.Order;
import java.time.LocalDate;
import java.util.HashMap;

/**
 * 
 * @author Steven
 */
public class BackupDaoStubImplementation implements BackupDao {
    @Override
    public void save(HashMap<LocalDate, HashMap<Integer, Order>> orders) {
        // do nothing
    }
}
