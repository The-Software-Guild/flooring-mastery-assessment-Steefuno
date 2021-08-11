/*
 * @author Steven Nguyen
 * @email: steven.686295@gmail.com
 * @date: 
 */

package com.mthree.flooringmastery.view;

import com.mthree.flooringmastery.model.Order;
import com.mthree.flooringmastery.model.OrderDetails;
import com.mthree.flooringmastery.model.OrderID;
import com.mthree.flooringmastery.model.ProductType;
import com.mthree.flooringmastery.model.State;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Set;

/**
 * 
 * @author Steven
 */
public class View {
    final private UI ui;
    
    /**
     * Constructs a new View given the UI to work with
     * @param ui the user interface
     */
    public View(UI ui) {
        this.ui = ui;
    }
    
    /**
     * Displays an message to the user
     * @param message the message to display
     */
    public void say(String message) {
        ui.say(message);
    }
    
    /**
     * Displays an error to the user
     * @param message the message to display
     */
    public void error(String message) {
        ui.say("ERROR: " + message);
    }
    
    /**
     * Displays an array of orders to the user
     * @param orders the array of orders
     */
    public void displayOrders(HashMap<Integer, Order> orders) {
        Set<Integer> orderNumbers;
        
        orderNumbers = orders.keySet();
        for (var orderNumber: orderNumbers) {
            ui.say("\n======== ORDER " + orderNumber + " ========");
            displayOrder(orders.get(orderNumber));
        }
    }
    
    /**
     * Displays an order to the user
     * @param order the order to display
     */
    public void displayOrder(Order order) {
        ProductType productType;
//        State state;
        
        productType = order.getProductType();
//        state = order.getState();
        
        ui.say(
            "Customer Name: " + order.getCustomerName() + "\n" +
            "State: " + order.getState().getName() + "\n" + 
            "Product Type: " + productType.getName() + "\n" +
            "Area: " + order.getArea()+ " sq ft\n" + 
//            "Material Cost Per Square Foot: $" + productType.getMaterialCostPerSquareFoot() + "\n" +
            "Material Cost: $" + order.getMaterialCost() + "\n" +
//            "Labor Cost Per Square Foot: $" + productType.getLaborCostPerSquareFoot() + "\n" +
            "Labor Cost: $" + order.getLaborCost() + "\n" + 
//            "Tax Rate: " + state.getTaxRate() + "\n" +
            "Tax: $" + order.getTax() + "\n" +
            "Total: $" + order.getTotal() 
        );
    }
    
    /**
     * Prompts the user to select a menu item
     * @return the user's selection
     */
    public String promptMenu() {
        String selection;
        promptMenu: do {
            selection = promptString(
                "\n======== FLOORING PROGRAM MAIN MENU ========\n" + 
                "DISPLAY - Display Orders\n" + 
                "ADD - Add an Order\n" + 
                "EDIT - Edit an Order\n" + 
                "REMOVE - Remove an Order\n" + 
                "EXPORT - Export all data to file\n" + 
                "EXIT - Exit\n" +
                "What would you like to do?"
            );
            
            switch(selection.toLowerCase()) {
                case "display":
                case "add":
                case "edit":
                case "remove":
                case "export":
                case "exit":
                    break promptMenu;
                default:
                    continue;
            }
        } while(true);
        
        return selection;
    }
    
    /**
     * Prompts the user to create a new order
     * @param states the states the user can select
     * @param productTypes the types of products the user can select
     * @param dateFormat the format for dates in prompts
     * @return the order details of the order created by the user
     */
    public OrderDetails promptNewOrder(State[] states, ProductType[] productTypes, String dateFormat) {
        DateTimeFormatter dateFormatter;
        OrderDetails orderDetails;
        LocalDate date;
        String customerName, selection, listOfStates, listOfProductTypes;
        HashMap<String, State> statesMap;
        State state;
        HashMap<String, ProductType> productTypesMap;
        ProductType productType;
        BigDecimal area;
        
        dateFormatter = DateTimeFormatter.ofPattern(dateFormat);
        
        
        // Prompt the date until the date is a future date
        date = promptDate(
            "\nToday's date is " + LocalDate.now().format(dateFormatter) + ".\n" + 
            "Please input a date for the order: (" + dateFormat + ")",
            dateFormat
        );
        while(date.compareTo(LocalDate.now()) <= 0) {
            date = promptDate(
                "Your order's date must be in the future.\n\n" + 
                "Today's date is " + LocalDate.now().format(dateFormatter) + ".\n" +
                "Please input a date for the order: (" + dateFormat + ")",
                dateFormat
            );
        }
        
        
        // Prompt the customer name until it is a valid name
        customerName = promptString("\nPlease input your Customer Name:");
        while(!customerName.matches("[\\w\\., ]+")) {
            customerName = promptString(
                "\nYour name must contain only [a-zA-Z0-9 ,].\n" + 
                "Please input your Customer Name:"
            );
        }
        
        
        // Display all of the available states
        statesMap = new HashMap(states.length);
        listOfStates = "";
        ui.say("======== AVAILABLE STATES ========");
        for (var availableState: states) {
            statesMap.put(availableState.getAbbreviation().toLowerCase(), availableState);
            listOfStates += availableState.getAbbreviation() + ", ";
        }
        listOfStates += "\b\b";
        ui.say(listOfStates);
        
        // Prompt the state until it is a valid state
        selection = promptString("\nPlease select a State to place the order in:").toLowerCase();
        while (!statesMap.containsKey(selection)) {
            selection = promptString(
                "Invalid State.\n\n" + 
                "States: " + listOfStates + "\n" + 
                "Please select a State to place the order in:").toLowerCase();
        }
        state = statesMap.get(selection);

        
        // Display all of the available product types
        productTypesMap = new HashMap(productTypes.length);
        listOfProductTypes = "";
        ui.say("======== AVAILABLE STATES ========");
        for (var availableProductType: productTypes) {
            productTypesMap.put(availableProductType.getName().toLowerCase(), availableProductType);
            listOfProductTypes += availableProductType.getName() + ", ";
        }
        listOfProductTypes += "\b\b";
        ui.say(listOfProductTypes);
        
        // Prompt the product type until it is a valid product type
        selection = promptString("\nPlease select a Product Type to place the order in:").toLowerCase();
        while (!productTypesMap.containsKey(selection)) {
            selection = promptString(
                "Invalid Product Type.\n\n" + 
                "Product Types: " + listOfProductTypes + "\n" + 
                "Please select a Product Type:").toLowerCase();
        }
        productType = productTypesMap.get(selection);
        
        
        // Prompt for the area of the Order
        area = promptNumber("\nPlease input an area in square feet:");
        
        orderDetails = new OrderDetails(
            date, customerName, state, productType, area
        );
        return orderDetails;
    }
    
    /**
     * Prompts the user to edit an order
     * @param order the order before being changed by the user
     * @param states the states the user can select
     * @param productTypes the types of products the user can select
     * @return the new order with the changes
     */
    public Order promptEditOrder(Order order, State[] states, ProductType[] productTypes) {
        Order newOrder;
        String customerName, selection, listOfStates, listOfProductTypes, currentState, currentProductType;
        HashMap<String, State> statesMap;
        HashMap<String, ProductType> productTypesMap;
        BigDecimal area, currentArea;
        
        newOrder = order.copy();
        
        // Prompt the customer name until it is a valid name
        customerName = promptString("\nPlease input your Customer Name: (" + order.getCustomerName() + ")");
        while(
            !(
                (customerName.matches("[\\w\\., ]+")) || 
                (customerName.equals(""))
            )
        ) {
            customerName = promptString(
                "\nYour name must contain only [a-zA-Z0-9 ,].\n" + 
                "Please input your Customer Name: (" + order.getCustomerName() + ")"
            );
        }
        
        if (customerName.equals("")) {
            // do nothing
        } else {
            newOrder.setCustomerName(customerName);
        }
        
        
        // Display all of the available states
        statesMap = new HashMap(states.length);
        listOfStates = "";
        ui.say("======== AVAILABLE STATES ========");
        for (var availableState: states) {
            statesMap.put(availableState.getAbbreviation().toLowerCase(), availableState);
            listOfStates += availableState.getAbbreviation() + ", ";
        }
        listOfStates += "\b\b";
        ui.say(listOfStates);
        
        // Prompt the state until it is a valid state
        currentState = order.getState().getAbbreviation();
        selection = promptString("\nPlease select a State to place the order in: (" + currentState + ")").toLowerCase();
        while (
            !(
                selection.equals("") || 
                statesMap.containsKey(selection)
            )
        ) {
            selection = promptString(
                "\nInvalid State.\n" + 
                "States: " + listOfStates + "\n" + 
                "Please select a State to place the order in: (" + currentState + ")").toLowerCase();
        }
        
        if (selection.equals("")) {
            // do nothing
        } else {
            newOrder.setState(
                statesMap.get(selection)
            );
        }

        
        // Display all of the available product types
        productTypesMap = new HashMap(productTypes.length);
        listOfProductTypes = "";
        ui.say("======== AVAILABLE PRODUCT TYPES ========");
        for (var availableProductType: productTypes) {
            productTypesMap.put(availableProductType.getName().toLowerCase(), availableProductType);
            listOfProductTypes += availableProductType.getName() + ", ";
        }
        listOfProductTypes += "\b\b";
        ui.say(listOfProductTypes);
        
        // Prompt the product type until it is a valid product type
        currentProductType = order.getProductType().getName();
        selection = promptString("\nPlease select a Product Type to place the order in: (" + currentProductType + ")").toLowerCase();
        while (
            !(
                selection.equals("") || 
                productTypesMap.containsKey(selection)
            )
        ) {
            selection = promptString(
                "\nInvalid Product Type.\n" + 
                "Product Types: " + listOfProductTypes + "\n" + 
                "Please select a Product Type to place the order in: (" + currentProductType + ")").toLowerCase();
        }
        
        if (selection.equals("")) {
            // do nothing
        } else {
            newOrder.setProductType(
                productTypesMap.get(selection)
            );
        }
        
        
        // Prompt for the area of the Order
        currentArea = order.getArea();
        do {
            selection = promptString("\nPlease input an area in square feet: (" + currentArea + ")");
            
            if (selection.equals("")) {
                // do nothing
                break;
            }
            
            try {
                newOrder.setArea(
                    (new BigDecimal(selection)).setScale(2, RoundingMode.HALF_UP)
                );
                break;
            } catch (NumberFormatException e) {
                ui.say("Invalid number format.");
                continue;
            }
        } while(true);

        return newOrder;
    }
    
    /**
     * Prompts the user to select a date
     * @param message the message to display to the user
     * @param dateFormat the format for dates in prompts
     * @return the date the user selected
     */
    public LocalDate promptDate(String message, String dateFormat) {
        DateTimeFormatter dateFormatter;
        String selection;
        LocalDate date;
        
        dateFormatter = DateTimeFormatter.ofPattern(dateFormat);
        
        // Prompt the date until it can be parsed correctly
        promptDate: do {
            selection = promptString(message);
            try {
                date = LocalDate.parse(selection, dateFormatter);
                break promptDate;
            } catch (DateTimeParseException e) {
                ui.say("Text cannot be parsed. The date format should be (" + dateFormat + ").");
            }
        } while(true);
        
        return date;
    }
    
    /**
     * Prompts the user to input a String
     * @param message the message to display to the user
     * @return the String the user inputted
     */
    public String promptString(String message) {
        ui.say(message);
        return ui.readString();
    }
    
    /**
     * Prompts the user to select an order id
     * @param dateFormat the format of dates in prompts
     * @param mustBeFuture outputs an error if the inputted date is not in the future
     * @return the orderID the user selected
     */
    public OrderID promptOrderID(String dateFormat, boolean mustBeFuture) {
        DateTimeFormatter dateFormatter;
        LocalDate date;
        int number;
        String selection;
        
        dateFormatter = DateTimeFormatter.ofPattern(dateFormat);
        
        // Prompt the date until the date is a future date
        date = promptDate(
            "\nToday's date is " + LocalDate.now().format(dateFormatter) + ".\n" + 
            "Please input a date for the order: (" + dateFormat + ")",
            dateFormat
        );
        while((mustBeFuture) && (date.compareTo(LocalDate.now()) <= 0)) {
            date = promptDate(
                "Your order's date must be in the future.\n\n" + 
                "Today's date is " + LocalDate.now().format(dateFormatter) + ".\n" +
                "Please input a date for the order: (" + dateFormat + ")",
                dateFormat
            );
        }
        
        // Prompt the order number until it is a positive integer
        do {
            selection = promptString(
                "\nPlease input an order number:"
            );
            
            try {
                number = Integer.parseInt(selection);
            } catch (NumberFormatException e) {
                ui.say("Invalid format.");
                continue;
            }
            
            if (number < 0) {
                ui.say("Order numbers are positive integers.");
                continue;
            } else {
                break;
            }
        } while(true);
        
        return new OrderID(number, date);
    }
    
    /**
     * Prompts the user to input a positive number
     * @param message the message to display to the user
     * @return the BigDecimal the user inputted
     */
    private BigDecimal promptNumber(String message) {
        String selection;
        BigDecimal number;
        
        do {
            ui.say(message);
            selection = ui.readString();
            
            try {
                number = (new BigDecimal(selection)).setScale(2, RoundingMode.HALF_UP);
            } catch (NumberFormatException e) {
                ui.say("Invalid number format.");
                continue;
            }
            
            if (number.compareTo(BigDecimal.ZERO) <= 0) {
                ui.say("Area must be a postive number.");
                continue;
            } else {
                break;
            }
        } while(true);
        
        return number;
    }
}
