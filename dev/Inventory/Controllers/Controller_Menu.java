package dev.Inventory.Controllers;
import java.util.List;
import java.util.Scanner;
import java.time.LocalDate;
import dev.Inventory.Classes.Inventory;

//---------------------------------------------------------------------
//To Take the following classes from the Inventory package
import dev.Inventory.Classes.Item;
import dev.Inventory.Classes.Product;
import dev.Inventory.Classes.Discount;
import dev.Inventory.Enums.E_Item_Place;
import dev.Inventory.Enums.E_Item_Status;
import dev.Inventory.Enums.E_Product_Status;
//---------------------------------------------------------------------

import dev.Inventory.Controllers.Controller_Worker;
import dev.Inventory.Controllers.Controller_Manager;



//To do the following:
//1. Let the controller know only the Inventory class(Facade) and the Controller_Manager class
//          No Creation of objects of the classes


public class Controller_Menu {
    private static Controller_Manager managerController = new Controller_Manager();
    private static Scanner scanner = new Scanner(System.in);


    public static void main(String[] args) {
        while (true) {
            System.out.println("===== Inventory Management System Menu =====");
            System.out.println("1. Add Item (Worker)");
            System.out.println("2. Remove Item (Worker)");
            System.out.println("3. Move Item (Worker)");
            System.out.println("4. View Product Details (Worker)");
            System.out.println("5. Generate Inventory Report (Worker)");
            System.out.println("6. Add Product (Manager)");
            System.out.println("7. Set a discount (Manager)");
            System.out.println("9. Exit");
            System.out.println("============================================");
            System.out.print("Select an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline
            switch (choice) {
                case 1:
                    addItem();
                    break;
                case 2:
                    removeItem();
                    break;
                case 3:
                    moveItem();
                    break;
                case 4:
                    viewProductDetails();
                    break;
                case 5:
                    generateInventoryReport();
                    break;
                case 6:
                    addProduct();
                    break;
                case 7:
                    setDiscount();
                    break;
                case 9:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }


    private static void setDiscount() {
        // Get discount details from the user
        System.out.print("Enter discount percentage: ");
        double discountPercentage = scanner.nextDouble();
        System.out.println("Enter the Start date (yyyy-mm-dd): ");
        String startString = scanner.next();
        LocalDate startDay = LocalDate.parse(startString);
        System.out.println("Enter the End date (yyyy-mm-dd): ");
        String endString = scanner.next();
        LocalDate endDay = LocalDate.parse(endString);
        Discount discount = new Discount(discountPercentage, startDay, endDay);
        // Get the target for the discount (Product, Category, or Subcategory)
        System.out.println("Apply discount to: 1. Product  2. Category  3. Subcategory");
        int choice = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter the name: ");
        String name = scanner.nextLine();
        switch (choice) {
            case 1:
                System.out.print("Enter the Category: ");
                String Category = scanner.nextLine();
                System.out.print("Enter the SubCategory: ");
                String SubCategory = scanner.nextLine();
                System.out.print("Enter the Size: ");
                double Size = Double.parseDouble(scanner.nextLine());
                Product product = managerController.inventory.getProduct(name, Category, SubCategory, Size);
                managerController.applyDiscountToProduct(product, discount);
                break;
            case 2:
                managerController.applyDiscountToCategory(name, discount);
                break;

            case 3:
                managerController.applyDiscountToSubCategory(name, discount);
                break;


            default:
                System.out.println("Invalid choice.");
                break; // Keep looping until a valid choice is made

        }
    }

    // Method to add an item (Worker)
    private static void addItem() {
        System.out.print("Enter item name: ");
        String name = scanner.nextLine();
        System.out.print("Enter category: ");
        String category = scanner.next();
        System.out.print("Enter sub-category: ");
        String subCategory = scanner.next();
        System.out.print("Enter size: ");
        double size = scanner.nextDouble();
        System.out.print("Enter cost price: ");
        double costPrice = scanner.nextDouble();
        System.out.print("Enter selling price: ");
        double sellingPrice = scanner.nextDouble();
        System.out.print("Enter manufacturer: ");
        String manufacturer = scanner.next();
        System.out.print("Enter item place (1: Store, 2: Warehouse): ");
        int placeChoice = scanner.nextInt();
        System.out.println("Enter the expiry date (yyyy-mm-dd): ");
        String expiryDate = scanner.next();
        LocalDate expiry = LocalDate.parse(expiryDate);
        E_Item_Place place = placeChoice == 1 ? E_Item_Place.Store : E_Item_Place.Warehouse;
        Item newItem = new Item(name, costPrice, sellingPrice, manufacturer, category, subCategory, size, expiry, E_Item_Status.Available, place);
        managerController.addItem(newItem);
    }

    // Method to add a product (Manager)
    private static void addProduct() {
        System.out.print("Enter product name: ");
        String name = scanner.nextLine();
        System.out.print("Enter category: ");
        String category = scanner.nextLine();
        System.out.print("Enter sub-category: ");
        String subCategory = scanner.nextLine();
        System.out.print("Enter size: ");
        double size = scanner.nextDouble();
        System.out.print("Enter minimum quantity: ");
        int minQuantity = scanner.nextInt();

        Product newProduct = new Product(name, category, subCategory, size, minQuantity, null);
        managerController.add_product(newProduct);
    }

    private static void removeItem() {
        // Get input from the user for category, sub-category, size, and place
        System.out.print("Enter product name: ");
        String name = scanner.nextLine();
        System.out.print("Enter the Category: ");
        String category = scanner.nextLine();
        System.out.print("Enter the Sub-Category: ");
        String subCategory = scanner.nextLine();
        System.out.print("Enter the size: ");
        double size = Double.parseDouble(scanner.nextLine());
        System.out.print("Enter item place (1: Store, 2: Warehouse): ");
        int placeChoice = scanner.nextInt();
        scanner.nextLine();  // Consume newline after nextInt
        // Convert the user's choice into the appropriate enum value for place
        E_Item_Place place = placeChoice == 1 ? E_Item_Place.Store : E_Item_Place.Warehouse;
        Item item_to_remove = managerController.findItem(name, category, subCategory, size, place);
        if (item_to_remove == null) {
            System.out.println("the item dont exists");
            return;
        }
        managerController.removeItem(item_to_remove);
    }

    private static void moveItem() {
        System.out.print("Enter product name: ");
        String name = scanner.nextLine();
        System.out.print("Enter the Category: ");
        String category = scanner.nextLine();
        System.out.print("Enter the Sub-Category: ");
        String subCategory = scanner.nextLine();
        System.out.print("Enter the size: ");
        double size = Double.parseDouble(scanner.nextLine());
        System.out.print("Enter where you want to move the item (1: Warehouse ,2: Store): ");
        int placeChoice = scanner.nextInt();
        E_Item_Place place_item, place_to_move;
        if (placeChoice == 1) {
            place_item = E_Item_Place.Store;
            place_to_move = E_Item_Place.Warehouse;
        } else {
            place_item = E_Item_Place.Warehouse;
            place_to_move = E_Item_Place.Store;
        }
        Item item_to_move = managerController.findItem(name, category, subCategory, size, place_item);
        if (item_to_move == null) {
            System.out.println("the item dont exists");
            return;
        }
        managerController.moveItem(item_to_move, place_to_move);
    }

    private static void viewProductDetails() {
        System.out.print("Enter product name: ");
        String name = scanner.nextLine();
        System.out.print("Enter the Category: ");
        String category = scanner.nextLine();
        System.out.print("Enter the Sub-Category: ");
        String subCategory = scanner.nextLine();
        System.out.print("Enter the size: ");
        double size = Double.parseDouble(scanner.nextLine());
        Product product = managerController.findOrProduct(name, category, subCategory, size);
        if (product == null) {
            System.out.println("the product dont exists");
            return;
        }
        System.out.println(product);

    }

    private static void generateInventoryReport()
    {
        System.out.println("enter filter : ");
        System.out.println("1. Category\n2. Sub-Category\n3.About to Finish\n4.About to Expire\n5. Expired\n6. All");
        int choice = scanner.nextInt();
        switch (choice) {
            case 1:
                System.out.print("Enter category: ");
                String category = scanner.next();
                List<Product> report = managerController.inventory.getProductsByCategory(category);
                System.out.println(report);
                break;
            case 2:
                System.out.print("Enter sub-category: ");
                String subCategory = scanner.next();
                List<Product> report2 = managerController.inventory.getProductsBySubCategory(subCategory);
                System.out.println(report2);
                break;
            case 3:
                List<Product> report3 = managerController.inventory.getProductsByStatus(E_Product_Status.about_to_finish);
                System.out.println(report3);
                break;
            case 4:
                List<Item> report4 = managerController.inventory.getItemsByStatus(E_Item_Status.about_to_expire);
                System.out.println(report4);
                break;
            case 5:
                List<Item> report5 = managerController.inventory.getItemsByStatus(E_Item_Status.EXPIRED);
                System.out.println(report5);
                break;
            case 6:
                System.out.println(managerController.inventory);
                break;
            default:
                System.out.println("Invalid choice.");
                break;
        }
    }
}
