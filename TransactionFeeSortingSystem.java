import java.util.ArrayList;
import java.time.LocalTime;

public class TransactionFeeSortingSystem {

    // Transaction class
    static class Transaction {

        String id;
        double fee;
        LocalTime timestamp;

        public Transaction(String id, double fee, String ts) {
            this.id = id;
            this.fee = fee;
            this.timestamp = LocalTime.parse(ts);
        }

        @Override
        public String toString() {
            return id + ", fee=" + fee + ", ts=" + timestamp;
        }
    }

    // Bubble Sort (≤100 transactions)
    public static void bubbleSortByFee(ArrayList<Transaction> list) {

        int passes = 0;
        int swaps = 0;

        for (int i = 0; i < list.size() - 1; i++) {

            boolean swapped = false;
            passes++;

            for (int j = 0; j < list.size() - i - 1; j++) {

                if (list.get(j).fee > list.get(j + 1).fee) {

                    // swap
                    Transaction temp = list.get(j);
                    list.set(j, list.get(j + 1));
                    list.set(j + 1, temp);

                    swaps++;
                    swapped = true;
                }
            }

            if (!swapped) {
                break;
            }
        }

        System.out.println("\nBubble Sort Result (Sorted by Fee):");
        printTransactions(list);

        System.out.println("Passes: " + passes);
        System.out.println("Swaps: " + swaps);

        flagHighFeeOutliers(list);
    }

    // Insertion Sort (100–1000 transactions)
    public static void insertionSortByFeeAndTime(ArrayList<Transaction> list) {

        for (int i = 1; i < list.size(); i++) {

            Transaction key = list.get(i);
            int j = i - 1;

            while (j >= 0 &&
                  (list.get(j).fee > key.fee ||
                  (list.get(j).fee == key.fee &&
                   list.get(j).timestamp.isAfter(key.timestamp)))) {

                list.set(j + 1, list.get(j));
                j--;
            }

            list.set(j + 1, key);
        }

        System.out.println("\nInsertion Sort Result (Sorted by Fee + Timestamp):");
        printTransactions(list);

        flagHighFeeOutliers(list);
    }

    // Detect high-fee outliers (>50)
    public static void flagHighFeeOutliers(ArrayList<Transaction> list) {

        System.out.println("\nHigh-fee outliers (> 50):");

        boolean found = false;

        for (Transaction t : list) {

            if (t.fee > 50) {
                System.out.println(t);
                found = true;
            }
        }

        if (!found) {
            System.out.println("None");
        }
    }

    // Print transactions
    public static void printTransactions(ArrayList<Transaction> list) {

        for (Transaction t : list) {
            System.out.println(t);
        }
    }

    // Main method
    public static void main(String[] args) {

        ArrayList<Transaction> transactions = new ArrayList<>();

        // Sample input
        transactions.add(new Transaction("id1", 10.5, "10:00"));
        transactions.add(new Transaction("id2", 25.0, "09:30"));
        transactions.add(new Transaction("id3", 5.0, "10:15"));
        transactions.add(new Transaction("id4", 60.0, "11:00")); // Outlier

        int size = transactions.size();

        System.out.println("Original Transactions:");
        printTransactions(transactions);

        if (size <= 100) {

            bubbleSortByFee(transactions);

        } else if (size <= 1000) {

            insertionSortByFeeAndTime(transactions);

        } else {

            System.out.println("Large dataset detected.");
            System.out.println("Use Merge Sort or Quick Sort.");
        }
    }
}
