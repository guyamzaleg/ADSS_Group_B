//package dev.Inventory.Test;
//
//import dev.Inventory.Classes.Discount;
//import dev.Inventory.Classes.Item;
//import dev.Inventory.Classes.Product;
//import dev.Inventory.Enums.E_Item_Place;
//import dev.Inventory.Enums.E_Item_Status;
//import dev.Inventory.Enums.E_Product_Status;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.time.LocalDate;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class InventoryTest {
//
//    Product sampleProduct;
//    Item availableWarehouseItem1, availableWarehouseItem2;
//    Discount productDiscount, newDiscount;
//
//    @BeforeEach
//    void setUp() {
//        // Initialize test data
//        availableWarehouseItem1 = new Item("Item1", 10, 20, "Manufacturer1", "Category1", "SubCategory1", 10, null, E_Item_Status.Available, E_Item_Place.Warehouse);
//        availableWarehouseItem2 = new Item("Item1", 10, 20, "Manufacturer1", "Category1", "SubCategory1", 10, null, E_Item_Status.Available, E_Item_Place.Warehouse);
//
//        // Initialize product and discount
//        sampleProduct = new Product("Item1", "Category1", "SubCategory1", 10, 2, null);
//        productDiscount = new Discount(50, LocalDate.ofYearDay(2024, 20), LocalDate.ofYearDay(2024, 300));
//        newDiscount = new Discount(30, LocalDate.ofYearDay(2024, 50), LocalDate.ofYearDay(2024, 300));
//    }
//
//    // Test 1: Add an item to the product
//    @Test
//    void testAddItem() {
//        sampleProduct.addItem(availableWarehouseItem1);
//        assertTrue(sampleProduct.getItems().containsKey(availableWarehouseItem1.getId()), "Item should be added.");
//    }
//
//    // Test 2: Remove an item from the product
//    @Test
//    void testRemoveItem() {
//        sampleProduct.addItem(availableWarehouseItem1);
//        sampleProduct.removeItem(availableWarehouseItem1);
//        assertFalse(sampleProduct.getItems().containsKey(availableWarehouseItem1.getId()), "Item should be removed.");
//    }
//
//    // Test 3: Apply discount and verify price update
//    @Test
//    void testApplyDiscount() {
//        sampleProduct.addItem(availableWarehouseItem1);
//        sampleProduct.setDiscount(productDiscount);
//
//        double expectedDiscountedPrice = availableWarehouseItem1.getSelling_price() * (1 - productDiscount.getDiscountRate() / 100);
//        assertEquals(expectedDiscountedPrice, availableWarehouseItem1.getPrice_after_discount(), 0.01, "Discount should be applied to item's selling price.");
//    }
//
//    // Test 4: Remove discount from product
//    @Test
//    void testRemoveDiscount() {
//        sampleProduct.addItem(availableWarehouseItem1);
//        sampleProduct.setDiscount(productDiscount);
//
//        // Remove the discount
//        sampleProduct.setDiscount(null);
//
//        // Check if price returned to original price
//        assertEquals(availableWarehouseItem1.getSelling_price(), availableWarehouseItem1.getPrice_after_discount(), 0.01, "Price should return to original after removing discount.");
//    }
//
//    // Test 5: Update product status based on quantity in store/warehouse
//    @Test
//    void testUpdateProductStatus() {
//        sampleProduct.addItem(availableWarehouseItem1);
//        sampleProduct.addItem(availableWarehouseItem2);
//
//        assertEquals(E_Product_Status.Available, sampleProduct.getStatus(), "Product should be available after adding items.");
//
//        // Remove all items to check status change
//        sampleProduct.removeItem(availableWarehouseItem1);
//        sampleProduct.removeItem(availableWarehouseItem2);
//
//        assertEquals(E_Product_Status.Out_of_stock, sampleProduct.getStatus(), "Product should be out of stock after removing all items.");
//    }
//
//    // Test 6: Test moving an item between places
//    @Test
//    void testMoveItem() {
//        sampleProduct.addItem(availableWarehouseItem1);
//        sampleProduct.moveItemTo(availableWarehouseItem1, E_Item_Place.Store);
//        assertEquals(E_Item_Place.Store, availableWarehouseItem1.getPlace(), "Item should be moved to Store.");
//    }
//
//    // Test 7: Apply a new discount and verify the update
//    @Test
//    void testApplyNewDiscount() {
//        sampleProduct.addItem(availableWarehouseItem1);
//        sampleProduct.setDiscount(productDiscount);
//        double expectedPrice = availableWarehouseItem1.getSelling_price() * (1 - productDiscount.getDiscountRate() / 100);
//
//        sampleProduct.setDiscount(newDiscount);  // Apply new discount
//        double newExpectedPrice = availableWarehouseItem1.getSelling_price() * (1 - newDiscount.getDiscountRate() / 100);
//
//        assertEquals(newExpectedPrice, availableWarehouseItem1.getPrice_after_discount(), 0.01, "New discount should overwrite the previous one.");
//    }
//
//    // Test 8: Check adding duplicate items
//    @Test
//    void testAddDuplicateItems() {
//        sampleProduct.addItem(availableWarehouseItem1);
//        sampleProduct.addItem(availableWarehouseItem1);  // Adding the same item again
//
//        // Verify that duplicate items are not added
//        assertEquals(1, sampleProduct.getItems().size(), "Duplicate items should not be added.");
//    }
//
//    // Test 9: Test product minimum quantity enforcement
//    @Test
//    void testProductMinimumQuantity() {
//        sampleProduct.addItem(availableWarehouseItem1);
//        assertTrue(sampleProduct.getItems().size() >= sampleProduct.getMin_quantity(), "Product should meet minimum quantity requirement.");
//    }
//
//    // Test 10: Check item status after applying discount
//    @Test
//    void testItemStatusAfterDiscount() {
//        sampleProduct.addItem(availableWarehouseItem1);
//        sampleProduct.setDiscount(productDiscount);
//
//        assertEquals(E_Item_Status.Available, availableWarehouseItem1.getStatus(), "Item status should remain available after discount.");
//    }
//}