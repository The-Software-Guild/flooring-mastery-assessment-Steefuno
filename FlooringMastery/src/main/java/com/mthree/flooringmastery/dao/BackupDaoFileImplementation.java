/*
 * @author Steven Nguyen
 * @email: steven.686295@gmail.com
 * @date: 
 */

package com.mthree.flooringmastery.dao;

import com.mthree.flooringmastery.model.Order;
import com.mthree.flooringmastery.model.OrderID;
import com.mthree.flooringmastery.model.ProductType;
import com.mthree.flooringmastery.model.State;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map.Entry;

/**
 * 
 * @author Steven
 */
public class BackupDaoFileImplementation implements BackupDao {
    final private String path;
    
    final private static String DATEFORMAT = "dd-MM-yyyy";
    final private static DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATEFORMAT);
    
    /**
     * Constructs a new BackupDaoFileImplementation
     * @param path the path to the backup file
     */
    public BackupDaoFileImplementation(String path) {
        this.path = path;
    }
    
    /**
     * Saves all orders to one backup
     * @param orders a hashmap of all the orders
     */
    @Override
    public void save(HashMap<LocalDate, HashMap<Integer, Order>> orders) {
        PrintWriter out;
        
        try {
            out = new PrintWriter(
                new FileWriter(path)
            );
        } catch (IOException e) {
            System.out.println("Failed to open file to write backup: " + path + ".");
            System.exit(-1);
            return;
        }
        
        // add the header
        out.println(
            String.format(
                "%s::%s::%s::%s::%s::%s::%s::%s::%s::%s::%s::%s::%s",
                "Order Number",
                "Customer Name",
                "State",
                "Tax Rate",
                "Product Type",
                "Area in Square Feet",
                "Material Cost per sq ft",
                "Labor Cost per sq ft",
                "Material Cost",
                "Labor Cost",
                "Tax",
                "Total",
                "Date"
            )
        );   
        
        // for each dateOrder
        orders.entrySet().forEach(ordersEntry -> {
            LocalDate date;
            HashMap<Integer, Order> dateOrders;
            
            date = ordersEntry.getKey();
            dateOrders = ordersEntry.getValue();
            
            // for each order, save
            dateOrders.entrySet().forEach(dateOrdersEntry -> {
                Integer orderNumber;
                Order order;
                OrderID orderID;
                
                orderNumber = dateOrdersEntry.getKey();
                order = dateOrdersEntry.getValue();
                orderID = new OrderID(orderNumber, date);
                
                saveLine(out, orderID, order);
            });
        });
        
        out.flush();
        out.close();
    }
    
    /**
     * Saves an order into the appropriate file
     * @param out the printwriter to output to the file
     * @param orderID the date and order number
     * @param order the order to save
     */
    private void saveLine(PrintWriter out, OrderID orderID, Order order) {
        String output;
        State state;
        ProductType productType;
        
        state = order.getState();
        productType = order.getProductType();
        
        output = String.format(
            "%s::%s::%s::%s::%s::%s::%s::%s::%s::%s::%s::%s::%s",
            orderID.getNumber(),
            order.getCustomerName(),
            state.getAbbreviation(),
            state.getTaxRate(),
            productType.getName(),
            order.getArea().toPlainString(),
            productType.getMaterialCostPerSquareFoot(),
            productType.getLaborCostPerSquareFoot(),
            order.getMaterialCost(),
            order.getLaborCost(),
            order.getTax(),
            order.getTotal(),
            orderID.getDate().format(DATE_FORMATTER)
        );
        out.println(output);
    }
}
