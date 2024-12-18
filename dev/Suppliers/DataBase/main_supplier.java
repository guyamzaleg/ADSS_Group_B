package dev.Suppliers.DataBase;

import dev.Suppliers.Domain.*;
import dev.Suppliers.Presentation.UI;
//import dev.Suppliers.Presentation.UI;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;

public class main_supplier {
    public static void main() throws SQLException {


        Connection connection = DatabaseConnection.connect();
        // Initialize the controllers

        SupplierController supplierController = new SupplierController(connection);
        ProductController productController = new ProductController(connection);
        AgreementController agreementController = new AgreementController(connection);
        OrderController orderController = new OrderController(connection);


        // Initialize the ControllersManager
        ControllersManager controllersManager = new ControllersManager(supplierController, productController, agreementController, orderController);
        HashMap<String, Integer> productOrderMap = new HashMap<>();
        productOrderMap.put("banana", 10);
        productOrderMap.put("table", 15);
//        controllersManager.createOrderForShortage(productOrderMap);

        // Initialize the UI
        UI ui = new UI(controllersManager);

        // Then, prompt the user for login and show the corresponding menu based on access level
        ui.displayLoginAndMenu();
    }
}