/*
 * @author Steven Nguyen
 * @email: steven.686295@gmail.com
 * @date: 12 Aug 2021
 */
package com.mthree.flooringmastery.dao;

import com.mthree.flooringmastery.model.Order;
import com.mthree.flooringmastery.model.OrderDetails;
import com.mthree.flooringmastery.model.OrderID;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Steven
 */
public class OrdersDaoFileImplementationTest {
    final private static String DATA_PATH = "./src/main/resources/TestFileData/";
    final private static String DATEFORMAT = "MMddyyyy";
    final private static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(DATEFORMAT);
    final private static LocalDate TODAY = LocalDate.now();
    
    private OrdersDaoFileImplementation ordersDao;
    final private static BackupDao backupDao = new BackupDaoStubImplementation();
    final private static ProductsDao productsDao = new ProductsDaoFileImplementation(DATA_PATH + "Data/Products");
    final private static TaxesDao taxesDao = new TaxesDaoFileImplementation(DATA_PATH + "Data/Taxes");
    
    public OrdersDaoFileImplementationTest() {
        ordersDao = null;
    }
    
    @BeforeAll
    public static void setUpClass() {
        File dir;
        
        dir = new File(DATA_PATH + "Orders/");
        
        // purge the directory
        for (var file: dir.listFiles()) {
            file.delete();
        }
    }

    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() throws FileNotFoundException, IOException {
        File file;
        PrintWriter out;
        LocalDate date;
        
        date = TODAY;
        file = new File(DATA_PATH + "Orders/", "Orders_" + dateFormatter.format(date));
        file.createNewFile();
        out = new PrintWriter(
            new FileWriter(file)
        );
        
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
        out.println(
            String.format(
                "%s::%s::%s::%s::%s::%s::%s::%s::%s::%s::%s::%s",
                "2", "C1", "1", "_", "P1", "100.00", 
                "_", "_", "_", "_", "_", "_"
            )
        );
        out.println(
            String.format(
                "%s::%s::%s::%s::%s::%s::%s::%s::%s::%s::%s::%s",
                "3", "C2", "2", "_", "P1", "100.00", 
                "_", "_", "_", "_", "_", "_"
            )
        );
        out.flush();
        out.close();
        
        date = TODAY.plusDays(5);
        file = new File(DATA_PATH + "Orders/", "Orders_" + dateFormatter.format(date));
        file.createNewFile();
        out = new PrintWriter(
            new FileWriter(file)
        );
        
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
        out.println(
            String.format(
                "%s::%s::%s::%s::%s::%s::%s::%s::%s::%s::%s::%s",
                "4", "C3", "1", "_", "P2", "100.00", 
                "_", "_", "_", "_", "_", "_"
            )
        );
        out.flush();
        out.close();
        
        ordersDao = new OrdersDaoFileImplementation(backupDao, DATA_PATH + "Orders/", productsDao, taxesDao);
    }
    
    @AfterEach
    public void tearDown() {
        File dir;
        
        dir = new File(DATA_PATH + "Orders/");
        
        // purge the directory
        for (var file: dir.listFiles()) {
            file.delete();
        }
    }
    
    /**
     * Tests loading from file and the getOrders method
     */
    @Test
    public void Should_Initialize_With_Presets() {
        HashMap<Integer, Order> orders;
        Order order;
        
        // Gets the orders from TODAY and checks the hashmap's existence and size
        orders = ordersDao.getOrders(TODAY);
        assertNotNull(
            orders,
            "We should have 2 orders listed under TODAY."
        );
        assertEquals(
            orders.size(),
            2,
            "We should have 2 orders listed under TODAY."
        );
        
        // Tests if order number 2 has loaded properly
        order = orders.get(2);
        assertNotNull(
            order,
            "Order 2 should exist in the orders listed under TODAY."
        );
        assertEquals(
            order.getCustomerName(),
            "C1",
            "Order 2 should have the customer name, C1."
        );
        assertEquals(
            order.getState(),
            taxesDao.getState("1"),
            "Order 2 should have the state, S1."
        );
        assertEquals(
            order.getProductType(),
            productsDao.getProductType("P1"),
            "Order 2 should have the product type, P1."
        );
        assertEquals(
            order.getArea(),
            (new BigDecimal("100.00")).setScale(2, RoundingMode.HALF_UP),
            "Order 2 should have area, 100.00 sq ft."
        );
        
        // Gets the orders from TODAY + 5 days and checks the hashmap's existence and size
        orders = ordersDao.getOrders(TODAY.plusDays(5));
        assertNotNull(
            orders,
            "We should have 1 order listed under TODAY + 5 days."
        );
        assertEquals(
            orders.size(),
            1,
            "We should have 1 order listed under TODAY + 5 days."
        );
    }
    
    /**
     * Tests getOrder to get null if order does not exist
     */
    @Test
    public void GetOrder_Should_Get_Null_If_DNE() {
        Order order;
        OrderID orderID;
        
        // Tests if null on a date that does not have orders
        orderID = new OrderID(
            2,
            TODAY.minusDays(1)
        );
        order = ordersDao.getOrder(orderID);
        assertNull(
            order,
            "Order 2 should not exist on TODAY - 1 days."
        );
        
        // Tests if null on a date that exists, but on an order that does not exist
        orderID = new OrderID(
            1,
            TODAY
        );
        order = ordersDao.getOrder(orderID);
        assertNull(
            order,
            "Order 1 should not exist."
        );
    }
    
    /**
     * Tests if addOrder works in normal conditions
     */
    @Test
    public void Should_Add_Order_Normally() {
        OrderDetails orderDetails;
        OrderID orderID;
        int orderNumber;
        
        // Should add order 4 on TODAY + 5 days, a day that already exists
        orderDetails = new OrderDetails(
            TODAY.plusDays(5),
            "C4",
            taxesDao.getState("1"),
            productsDao.getProductType("P1"),
            ((new BigDecimal("100.00")).setScale(2, RoundingMode.HALF_UP))
        );
        ordersDao.addOrder(orderDetails);
        
        orderID = new OrderID(
            4,
            TODAY.plusDays(5)
        );
        assertNotNull(
            ordersDao.getOrder(orderID),
            "Order 4 should exist."
        );
        
        // Should add order 5 on TODAY + 6 days, a day that does not exist yet
        orderDetails = new OrderDetails(
            TODAY.plusDays(6),
            "C5",
            taxesDao.getState("1"),
            productsDao.getProductType("P1"),
            (new BigDecimal("100.00")).setScale(2, RoundingMode.HALF_UP)
        );
        orderNumber = ordersDao.addOrder(orderDetails).getNumber();
        
        orderID = new OrderID(
            orderNumber,
            TODAY.plusDays(6)
        );
        assertNotNull(
            ordersDao.getOrder(orderID),
            "Order " + orderNumber + " should exist."
        );
    }
    
    /**
     * Tests if calculateOrder makes the appropriate order
     */
    @Test
    public void Should_Calculate_Normally() {
        Order order;
        OrderDetails orderDetails;
        
        orderDetails = new OrderDetails(
            TODAY,
            "C",
            taxesDao.getState("1"),
            productsDao.getProductType("P1"),
            (new BigDecimal("100.00")).setScale(2, RoundingMode.HALF_UP)
        );
        order = ordersDao.calculateOrder(orderDetails);
        
        assertNotNull(
            order,
            "Order should exist."
        );
        assertEquals(
            order.getCustomerName(),
            "C",
            "Order should have customer name, C."
        );
        assertEquals(
            order.getState(),
            taxesDao.getState("1"),
            "Order should have the state, S1."
        );
        assertEquals(
            order.getProductType(),
            productsDao.getProductType("P1"),
            "Order should have the product type, P1."
        );
    }
    
    /**
     * Tests if editOrder works in normal conditions
     */
    @Test
    public void EditOrder_Should_Edit_Properly() {
        OrderID orderID;
        Order order;
        
        orderID = new OrderID(
            2,
            TODAY
        );
        order = new Order(
            "C",
            taxesDao.getState("2"),
            productsDao.getProductType("P2"),
            (new BigDecimal("1.00")).setScale(2, RoundingMode.HALF_UP)
        );
        
        try {
            ordersDao.editOrder(orderID, order);
        } catch (OrderDoesNotExistException e) {
            fail("Order 2 should exist.");
        }
        
        order = ordersDao.getOrder(orderID);
        assertNotNull(
            order,
            "Order 2 should exist in the order."
        );
        assertEquals(
            order.getCustomerName(),
            "C",
            "Order 2 should have the customer name, C."
        );
        assertEquals(
            order.getState(),
            taxesDao.getState("2"),
            "Order 2 should have the state, S2."
        );
        assertEquals(
            order.getProductType(),
            productsDao.getProductType("P2"),
            "Order 2 should have the product type, P2."
        );
        assertEquals(
            order.getArea(),
            (new BigDecimal("1.00")).setScale(2, RoundingMode.HALF_UP),
            "Order 2 should have area, 1.00 sq ft."
        );
    }
    
    /**
     * Tests if editOrder fails when the order does not exist
     */
    @Test
    public void EditOrder_Should_Fail_If_DNE() {
        OrderID orderID;
        Order order;
        
        orderID = new OrderID(
            1,
            TODAY
        );
        order = new Order(
            "C",
            taxesDao.getState("1"),
            productsDao.getProductType("P1"),
            (new BigDecimal("100.00")).setScale(2, RoundingMode.HALF_UP)
        );
        
        try {
            ordersDao.editOrder(orderID, order);
            fail("Order 2 should not exist.");
        } catch (OrderDoesNotExistException e) {
            // do nothing
        }
    }
    
    /**
     * Tests if removeOrder works in normal conditions
     */
    @Test
    public void RemoveOrder_Should_Remove_Normally() {
        OrderID orderID;
        Order order;
        
        orderID = new OrderID(
            2,
            TODAY
        );
        
        try {
            ordersDao.removeOrder(orderID);
        } catch (OrderDoesNotExistException e) {
            fail("Order 2 should exist.");
        }
        order = ordersDao.getOrder(orderID);
        
        assertNull(
            order,
            "Order 2 should not exist."
        );
    }
    
    /**
     * Tests if removeOrder fails when the order does not exist
     */
    @Test
    public void RemoveOrder_Should_Fail_If_DNE() {
        OrderID orderID;
        Order order;
        
        orderID = new OrderID(
            1,
            TODAY
        );
        
        try {
            ordersDao.removeOrder(orderID);
            fail("Order 1 should not exist.");
        } catch (OrderDoesNotExistException e) {
            // do nothing
        }
        order = ordersDao.getOrder(orderID);
        
        assertNull(
            order,
            "Order 1 should not exist."
        );
    }
    
    /**
     * Tests if export works in normal conditions
     */
    @Test
    public void Export_Should_Export_Normally() {
        OrderID orderID;
        Order order;
        
        // Edit order 2
        orderID = new OrderID(
            2,
            TODAY
        );
        order = new Order(
            "C",
            taxesDao.getState("2"),
            productsDao.getProductType("P2"),
            (new BigDecimal("1.00")).setScale(2, RoundingMode.HALF_UP)
        );
        
        try {
            ordersDao.editOrder(orderID, order);
        } catch (OrderDoesNotExistException e) {
            fail("Order 2 should exist.");
        }
        
        // Restart OrdersDao and check if Order 2 is still edited
        ordersDao.export();
        ordersDao = new OrdersDaoFileImplementation(backupDao, DATA_PATH + "Orders/", productsDao, taxesDao);
        
        // Test if the order is still edited
        order = ordersDao.getOrder(orderID);
        assertNotNull(
            order,
            "Order 2 should exist in the order."
        );
        assertEquals(
            order.getCustomerName(),
            "C",
            "Order 2 should have the customer name, C."
        );
        assertEquals(
            order.getState(),
            taxesDao.getState("2"),
            "Order 2 should have the state, S2."
        );
        assertEquals(
            order.getProductType(),
            productsDao.getProductType("P2"),
            "Order 2 should have the product type, P2."
        );
        assertEquals(
            order.getArea(),
            (new BigDecimal("1.00")).setScale(2, RoundingMode.HALF_UP),
            "Order 2 should have area, 1.00 sq ft."
        );
    }
}
