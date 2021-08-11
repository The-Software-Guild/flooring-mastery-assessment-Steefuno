/*
 * @author Steven Nguyen
 * @email: steven.686295@gmail.com
 * @date: 09 Aug 2021
 */

package com.mthree.flooringmastery.controller;

import com.mthree.flooringmastery.dao.OrderDoesNotExistException;
import com.mthree.flooringmastery.model.Order;
import com.mthree.flooringmastery.model.OrderDetails;
import com.mthree.flooringmastery.model.OrderID;
import com.mthree.flooringmastery.model.ProductType;
import com.mthree.flooringmastery.model.State;
import com.mthree.flooringmastery.service.DaoService;
import com.mthree.flooringmastery.view.View;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

/**
 * 
 * @author Steven
 */
public class Controller {
    final private View view;
    final private DaoService daoService;
    final private static String DATEFORMAT = "dd/MM/yyyy";
    final private static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(DATEFORMAT);
    
    /**
     * Constructs a new Controller given the view and dao service
     * @param view the object that handles the UI
     * @param daoService the object that handles accessing and storing data
     */
    public Controller(View view, DaoService daoService) {
        this.view = view;
        this.daoService = daoService;
    }
    
    /**
     * Runs the controller
     */
    public void run() {
        String mainMenuSelection;
        
        mainMenu: do {
            mainMenuSelection = promptMenu();
            
            switch(mainMenuSelection.toLowerCase()) {
                case "display":
                    displayOrders();
                    break;
                case "add":
                    addOrder();
                    break;
                case "edit":
                    editOrder();
                    break;
                case "remove":
                    removeOrder();
                    break;
                case "export":
                    export();
                    break;
                case "exit":
                    break mainMenu;
                default:
                    continue;
            }
        } while(true);
    }
    
    /**
     * Prompts the user to select a menu option
     * @return the user's selection
     */
    private String promptMenu() {
        return view.promptMenu();
    }
    
    /**
     * Prompts the user to select an order, then displays the order's details
     */
    private void displayOrders() {
        LocalDate date;
        HashMap<Integer, Order> orders;
        
        date = view.promptDate("\nWhat date should we check for orders? (" + DATEFORMAT + "):", DATEFORMAT);
        orders = daoService.getOrders(date);
        
        if (orders == null) {
            view.error("No orders exist on this date.");
            return;
        }
        
        view.displayOrders(orders);
    }
    
    /**
     * Prompts the user for details in adding an order, displays the order's details,
     * prompts the user if they want to add this order, then adds the order
     */
    private void addOrder() {
        OrderDetails orderDetails;
        State[] states;
        ProductType[] productTypes;
        Order order;
        String response;
        OrderID orderID;
        LocalDate date;
        
        states = daoService.getAllStates();
        productTypes = daoService.getAllProductTypes();
        orderDetails = view.promptNewOrder(states, productTypes, DATEFORMAT);
        
        order = daoService.calculateOrder(orderDetails);
        view.say("\n======== ORDER ========");
        view.displayOrder(order);
        
        getResponse: do {
            response = view.promptString("\nAre you sure you want to save this order? (Yes / No)");
            switch(response.toLowerCase()) {
                case "yes":
                    break getResponse;
                case "no":
                    return;
                default:
                    view.error("Invalid response.");
                    continue;
            }
        } while(true);
        
        orderID = daoService.addOrder(orderDetails);
        date = orderID.getDate();
        view.say("Added Order number, " + orderID.getNumber() + ", for date, " + date.format(dateFormatter) + ".");
    }
    
    /**
     * Prompts the user to select an order, then input new order details, displays the order's details,
     * prompts the user if they want to keep the changes, and then store the edits in memory
     */
    private void editOrder() {
        OrderID orderID;
        Order order, newOrder;
        State[] states;
        ProductType[] productTypes;
        String response;
        
        orderID = view.promptOrderID(DATEFORMAT, false);
        order = daoService.getOrder(orderID);
        
        if (order == null) {
            view.error("That Order does not exist.");
            return;
        }
        
        states = daoService.getAllStates();
        productTypes = daoService.getAllProductTypes();
        newOrder = view.promptEditOrder(order, states, productTypes);
        
        view.displayOrder(newOrder);
        
        getResponse: do {
            response = view.promptString("\nAre you sure you want to edit this order? (Yes / No)");
            switch(response.toLowerCase()) {
                case "yes":
                    break getResponse;
                case "no":
                    return;
                default:
                    view.error("Invalid response.");
                    continue;
            }
        } while(true);
        
        try {
            daoService.editOrder(orderID, newOrder);
        } catch (OrderDoesNotExistException e) {
            view.error("That Order does not exist.");
            return;
        }
        
        view.say("Updated Order number, " + orderID.getNumber() + ".");
    }
    
    /**
     * Prompts the user to select an order, displays the order's details,
     * prompts the user if they want to delete the order, and delete the order if so
     */
    private void removeOrder() {
        OrderID orderID;
        Order order;
        String response;
        
        orderID = view.promptOrderID(DATEFORMAT, false);
        order = daoService.getOrder(orderID);
        
        if (order == null) {
            view.error("That Order does not exist.");
            return;
        }
        
        view.displayOrder(order);
        
        getResponse: do {
            response = view.promptString("\nAre you sure you want to delete this order? (Yes / No)");
            switch(response.toLowerCase()) {
                case "yes":
                    break getResponse;
                case "no":
                    return;
                default:
                    view.error("Invalid response.");
                    continue;
            }
        } while(true);
        
        try {
            daoService.removeOrder(orderID);
        } catch(OrderDoesNotExistException e) {
            view.error("That Order does not exist.");
            return;
        }
        
        view.say("Removed Order number, " + orderID.getNumber() + ".");
    }
    
    /**
     * Store all data from memory into file
     */
    private void export() {
        daoService.export();
        view.say("Exported.");
    }
}
