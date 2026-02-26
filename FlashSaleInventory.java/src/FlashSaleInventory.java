import java.util.*;

public class FlashSaleInventory {

    // productId -> stock count
    private HashMap<String, Integer> inventory;

    // productId -> waiting list (FIFO)
    private HashMap<String, Queue<Integer>> waitingList;

    public FlashSaleInventory() {
        inventory = new HashMap<>();
        waitingList = new HashMap<>();
    }

    // Add product with stock
    public void addProduct(String productId, int stock) {
        inventory.put(productId, stock);
        waitingList.put(productId, new LinkedList<>());
    }

    // Check stock
    public int checkStock(String productId) {
        return inventory.getOrDefault(productId, 0);
    }

    // Purchase product
    public void purchaseItem(String productId, int userId) {

        if (!inventory.containsKey(productId)) {
            System.out.println("Product does not exist.");
            return;
        }

        int stock = inventory.get(productId);

        if (stock > 0) {
            inventory.put(productId, stock - 1);
            System.out.println("✅ Purchase successful! Remaining stock: "
                    + (stock - 1));
        } else {
            waitingList.get(productId).add(userId);
            System.out.println("❌ Out of stock. Added to waiting list. Position: "
                    + waitingList.get(productId).size());
        }
    }

    // Show waiting list
    public void showWaitingList(String productId) {
        System.out.println("Waiting List: "
                + waitingList.get(productId));
    }

    // ==========================
    // MAIN METHOD (User Input)
    // ==========================
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        FlashSaleInventory system = new FlashSaleInventory();

        // Add product (Example)
        system.addProduct("IPHONE15_256GB", 5);

        System.out.println("=== Flash Sale Inventory System ===");

        while (true) {
            System.out.println("\n1. Check Stock");
            System.out.println("2. Purchase Item");
            System.out.println("3. Show Waiting List");
            System.out.println("4. Exit");
            System.out.print("Choose option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) {
                System.out.print("Enter Product ID: ");
                String productId = scanner.nextLine();
                System.out.println("Stock Available: "
                        + system.checkStock(productId));

            } else if (choice == 2) {
                System.out.print("Enter Product ID: ");
                String productId = scanner.nextLine();
                System.out.print("Enter User ID: ");
                int userId = scanner.nextInt();
                system.purchaseItem(productId, userId);

            } else if (choice == 3) {
                System.out.print("Enter Product ID: ");
                String productId = scanner.nextLine();
                system.showWaitingList(productId);

            } else if (choice == 4) {
                System.out.println("Exiting...");
                break;

            } else {
                System.out.println("Invalid option.");
            }
        }

        scanner.close();
    }
}