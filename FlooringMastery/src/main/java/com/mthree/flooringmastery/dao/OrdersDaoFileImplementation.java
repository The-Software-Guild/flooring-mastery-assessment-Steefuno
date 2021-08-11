/*
 * @author Steven Nguyen
 * @email: steven.686295@gmail.com
 * @date: 
 */

package com.mthree.flooringmastery.dao;

import com.mthree.flooringmastery.model.Order;
import com.mthree.flooringmastery.model.OrderDetails;
import com.mthree.flooringmastery.model.OrderID;
import com.mthree.flooringmastery.model.ProductType;
import com.mthree.flooringmastery.model.State;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @author Steven
 */
public class OrdersDaoFileImplementation implements OrdersDao {
    final private BackupDao backupDao;
    final private HashMap<LocalDate, HashMap<Integer, Order>> orders;
    final private String path;
    private int nextOrderNumber = 1;
        
    final private static String DATEFORMAT = "MMddyyyy";
    final private static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(DATEFORMAT);
    
    final private static String ORDER_FILE_NAME_PATTERN_STRING = "Orders_(\\d{2})(\\d{2})(\\d{4})";
    final private static Pattern ORDER_FILE_NAME_PATTERN = Pattern.compile(ORDER_FILE_NAME_PATTERN_STRING);
    
    /**
     * Constructs a new OrdersDaoFileImplementation
     * @param backupDao the DAO to handle backing up orders
     * @param path the path to the directory with the files
     * @param productsDao the dao that handles the products to assign product types to orders
     * @param taxesDao the dao that handles states to assign states to orders
     */
    public OrdersDaoFileImplementation(BackupDao backupDao, String path, ProductsDao productsDao, TaxesDao taxesDao) {
        this.backupDao = backupDao;
        this.path = path;
        
        this.orders = new HashMap<>();
        load(productsDao, taxesDao);
    }
    
    /**
     * Gets an order given the date and order number
     * @param orderID the order id
     * @return the order or null
     */
    @Override
    public Order getOrder(OrderID orderID) {
        HashMap<Integer, Order> dateOrders;
        Order order;
        
        dateOrders = orders.get(orderID.getDate());
        if (dateOrders == null) {
            return null;
        }
        
        order = dateOrders.get(orderID.getNumber());
        return order;
    }
    
    /**
     * Gets a hashmap of all the orders on a select date
     * @param date the date
     * @return the hashmap or null
     */
    @Override
    public HashMap<Integer, Order> getOrders(LocalDate date) {
        return orders.get(date);
    }
    
    /**
     * Constructs and adds an order
     * @param orderDetails the details of the new order
     * @return the id of the new order
     */
    @Override
    public OrderID addOrder(OrderDetails orderDetails) {
        HashMap<Integer, Order> dateOrders;
        LocalDate date;
        Order order;
        Integer orderNumber;
        
        date = orderDetails.getDate();
        
        dateOrders = orders.get(date);
        if (dateOrders == null) {
            dateOrders = new HashMap<Integer, Order>();
            orders.put(date, dateOrders);
        }
        
        order = new Order(orderDetails);
        orderNumber = nextOrderNumber++;
        dateOrders.put(orderNumber, order);
        
        return new OrderID(orderNumber, date);
    }
    
    /**
     * Calculates an order given the details, and DOES NOT save the order to memory
     * @param orderDetails the details of the order
     * @return the order
     */
    @Override
    public Order calculateOrder(OrderDetails orderDetails) {
        return new Order(orderDetails);
    }
    
    /**
     * Edits an existing order
     * @param orderID the id of the order
     * @param newOrder the new order to replace the old order
     * @throws com.mthree.flooringmastery.dao.OrderDoesNotExistException
     */
    @Override
    public void editOrder(OrderID orderID, Order newOrder) throws OrderDoesNotExistException {
        HashMap<Integer, Order> dateOrders;
        LocalDate date;
        Integer orderNumber;
        
        date = orderID.getDate();
        
        dateOrders = orders.get(date);
        if (dateOrders == null) {
            throw new OrderDoesNotExistException();
        }
        
        orderNumber = orderID.getNumber();
        if (!dateOrders.containsKey(orderNumber)) {
            throw new OrderDoesNotExistException();
        }
        
        dateOrders.put(orderNumber, newOrder);
    }
    
    /**
     * Removes an order from memory
     * @param orderID the id of the order
     * @throws com.mthree.flooringmastery.dao.OrderDoesNotExistException
     */
    @Override
    public void removeOrder(OrderID orderID) throws OrderDoesNotExistException {
        HashMap<Integer, Order> dateOrders;
        LocalDate date;
        Integer orderNumber;
        
        date = orderID.getDate();
        
        dateOrders = orders.get(date);
        if (dateOrders == null) {
            throw new OrderDoesNotExistException();
        }
        
        orderNumber = orderID.getNumber();
        if (!dateOrders.containsKey(orderNumber)) {
            throw new OrderDoesNotExistException();
        }
        
        dateOrders.remove(orderNumber);
        
        if (dateOrders.isEmpty()) {
            orders.remove(date);
        }
    }
    
    /**
     * Saves all orders in memory to file
     */
    @Override
    public void export() {
        File dir;
        
        dir = new File(path);
        
        // purge the directory
        for (File file: dir.listFiles()) {
            file.delete();
        }
        
        // for each dateOrders
        for (Entry<LocalDate, HashMap<Integer, Order>> ordersEntry: orders.entrySet()) {
            HashMap<Integer, Order> dateOrders;
            LocalDate date;
            PrintWriter out;
            String fileName;
            File dateFile;
            
            dateOrders = ordersEntry.getValue();
            date = ordersEntry.getKey();
            
            fileName = "Orders_" + dateFormatter.format(date);
            dateFile = new File(path, fileName);
            
            // create the file and setup the outputter
            try {
                dateFile.createNewFile();
                out = new PrintWriter(
                    new FileWriter(dateFile)
                );
            } catch (IOException e) {
                System.out.println("Failed to create file to save " + fileName + ".");
                continue;
            }

            // add the header
            out.println(
                String.format(
                    "%s::%s::%s::%s::%s::%s::%s::%s::%s::%s::%s::%s",
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
                    "Total"
                )
            );            
            // for each order, save to file
            for (Entry<Integer, Order> dateOrdersEntry: dateOrders.entrySet()) {
                Order order;
                Integer orderNumber;
                
                order = dateOrdersEntry.getValue();
                orderNumber = dateOrdersEntry.getKey();
                
                saveLine(out, orderNumber, order);
            }
            
            out.flush();
            out.close();
        }
        
        backupDao.save(orders);
    }
    
    /**
     * Loads the orders from a folder of files
     * @param productsDao the dao that handles the products to assign product types to orders
     * @param taxesDao the dao that handles states to assign states to orders
     */
    private void load(ProductsDao productsDao, TaxesDao taxesDao) {
        File dir;
        
        dir = new File(path);
        
        orders.clear();
        
        // check each file in the folder of dateOrders
        for (File file: dir.listFiles()) {
            String name;
            Scanner sc;
            LocalDate date;
            Matcher matcher;
            int month, day, year;
            
            // setup the scanner
            name = file.getName();
            try {
                sc = new Scanner(
                    new BufferedReader(
                        new FileReader(file)
                    )
                );
            } catch (FileNotFoundException e) {
                System.out.println("Failed to read file to load orders " + name + ".");
                System.exit(-1);
                return;
            }
            
            // extract the date from the filename
            matcher = ORDER_FILE_NAME_PATTERN.matcher(name);
            matcher.find();
            try {
                month = Integer.parseInt(
                    matcher.group(1)
                );
                day = Integer.parseInt(
                    matcher.group(2)
                );
                year = Integer.parseInt(
                    matcher.group(3)
                );
            } catch (NumberFormatException e) {
                sc.close();
                System.out.println("Failed to read date from file " + name + ".");
                continue;
            }
            date = LocalDate.of(year, month, day);
            
            orders.put(date, new HashMap<>());
            
            // extract each order from each line in the file
            sc.nextLine(); // ignore the header
            while (sc.hasNextLine()) {
                String line;
                
                line = sc.nextLine();
                loadLine(date, line, productsDao, taxesDao);
            }
            
            sc.close();
        }
    }
    
    /**
     * Loads a line from an orders file
     * @param date the date of the file
     * @param line the line in the file
     * @param productsDao the dao that handles the products to assign product types to orders
     * @param taxesDao the dao that handles states to assign states to orders
     */
    private void loadLine(LocalDate date, String line, ProductsDao productsDao, TaxesDao taxesDao) {
        Order order;
        String[] data;
        Integer orderNumber;
        String customerName;
        State state;
        ProductType productType;
        BigDecimal area;
        
        data = line.split("::");
        if (data.length != 12) {
            System.out.println("Bad Format: " + line);
            return;
        }
        
        // get the order number
        try {
            orderNumber = Integer.parseInt(data[0]);
        } catch(NumberFormatException e) {
            System.out.println("Failed to read order number: " + line);
            return;
        }
        
        // update the dao's highest order number
        if (orderNumber >= nextOrderNumber) {
            nextOrderNumber = orderNumber + 1;
        }
        
        // get the customer name
        customerName = data[1];
        
        // get the state
        state = taxesDao.getState(data[2]);
        if (state == null) {
            System.out.println("Failed to find state: " + line);
            return;
        }
        
        // get the product type
        productType = productsDao.getProductType(data[4]);
        if (productType == null) {
            System.out.println("Failed to find product type: " + line);
            return;
        }
        
        // get the area
        try {
            area = (new BigDecimal(data[5])).setScale(2, RoundingMode.HALF_UP);
        } catch(NumberFormatException e) {
            System.out.println("Failed to read area: " + line);
            return;
        }
        
        order = new Order(
            customerName,
            state,
            productType,
            area
        );
        orders.get(date).put(orderNumber, order);
    }
    
    /**
     * Saves an order into the appropriate file
     * @param out the printwriter to output to the file
     * @param orderNumber the order number
     * @param order the order to save
     */
    private void saveLine(PrintWriter out, Integer orderNumber, Order order) {
        String output;
        State state;
        ProductType productType;
        
        state = order.getState();
        productType = order.getProductType();
        
        output = String.format(
            "%s::%s::%s::%s::%s::%s::%s::%s::%s::%s::%s::%s",
            orderNumber,
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
            order.getTotal()
        );
        out.println(output);
    }
}
