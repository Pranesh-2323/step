import java.util.*;

public class TransactionAnalyzer {

    static class Transaction {
        int id;
        int amount;
        String merchant;

        Transaction(int id, int amount, String merchant) {
            this.id = id;
            this.amount = amount;
            this.merchant = merchant;
        }

        @Override
        public String toString() {
            return "ID:" + id + " Amount:" + amount + " Merchant:" + merchant;
        }
    }

    private List<Transaction> transactions;

    public TransactionAnalyzer() {
        transactions = new ArrayList<>();
    }

    // Add transaction
    public void addTransaction(int id, int amount, String merchant) {
        transactions.add(new Transaction(id, amount, merchant));
        System.out.println("✅ Transaction added.");
    }

    // Classic Two-Sum
    public void findTwoSum(int target) {

        HashMap<Integer, Transaction> map = new HashMap<>();

        for (Transaction t : transactions) {
            int complement = target - t.amount;

            if (map.containsKey(complement)) {
                System.out.println("💰 Two-Sum Found:");
                System.out.println(map.get(complement));
                System.out.println(t);
                return;
            }

            map.put(t.amount, t);
        }

        System.out.println("No Two-Sum pair found.");
    }

    // Duplicate Detection (same amount + merchant)
    public void detectDuplicates() {

        HashMap<String, List<Transaction>> map = new HashMap<>();

        for (Transaction t : transactions) {

            String key = t.amount + "_" + t.merchant;

            map.putIfAbsent(key, new ArrayList<>());
            map.get(key).add(t);
        }

        boolean found = false;

        for (List<Transaction> list : map.values()) {
            if (list.size() > 1) {
                found = true;
                System.out.println("⚠ Duplicate Transactions:");
                for (Transaction t : list) {
                    System.out.println(t);
                }
                System.out.println();
            }
        }

        if (!found) {
            System.out.println("No duplicates found.");
        }
    }

    // ==========================
    // MAIN METHOD
    // ==========================
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        TransactionAnalyzer analyzer = new TransactionAnalyzer();

        System.out.println("=== Transaction Analyzer System ===");

        while (true) {

            System.out.println("\n1. Add Transaction");
            System.out.println("2. Find Two-Sum");
            System.out.println("3. Detect Duplicates");
            System.out.println("4. Exit");
            System.out.print("Choose option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) {

                System.out.print("Enter Transaction ID: ");
                int id = scanner.nextInt();

                System.out.print("Enter Amount: ");
                int amount = scanner.nextInt();
                scanner.nextLine();

                System.out.print("Enter Merchant: ");
                String merchant = scanner.nextLine();

                analyzer.addTransaction(id, amount, merchant);

            } else if (choice == 2) {

                System.out.print("Enter Target Amount: ");
                int target = scanner.nextInt();

                analyzer.findTwoSum(target);

            } else if (choice == 3) {

                analyzer.detectDuplicates();

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