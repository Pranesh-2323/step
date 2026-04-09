import java.util.ArrayList;
import java.time.LocalTime;

public class TransactionFeeSortingSystem {

    // Transaction Class
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

    // Bubble Sort (for small batches ≤ 100)
    public static void bubbleSortByFee(ArrayList<Transaction> list) {

        int passes = 0;
        int swaps = 0;

        for (int i = 0; i < list.size() - 1; i++) {

            boolean swapped = false;
            passes++;

            for (int j = 0; j < list.size() - i - 1; j++) {

                if (list.get(j).fee > list.get(j + 1).fee) {

                    // Swap
                    Transaction temp = list.get(j);
                    list.set(j, list.get(j + 1));
                    list.set(j + 1, temp);

                    swaps++;
                    swapped = true;
                }
            }

            // Early termination
            if (!swapped) {
                break;
            }
        }

        System.out.println("\nBubble Sort Result (Sorted by Fee):");
        printTransactions(list);

        System.out.println("Total Passes: " + passes);
        System.out.println("Total Swaps: " + swaps);

        flagHighFeeOutliers(list);
    }

    // Insertion Sort (for medium batches 100–1000)
    public static void insertionSortByFeeAndTime(ArrayList<Transaction> list) {

        for (int i = 1; i < list.size(); i++) {

            Transaction key = list.get(i);
            int j = i - 1;

            // Sort by fee, then timestamp
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

    // Detect high-fee transactions (> 50)
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

    // Main Method
    public static void main(String[] args) {

        ArrayList<Transaction> transactions = new ArrayList<>();

        // Sample Input
        transactions.add(new Transaction("id1", 10.5, "10:00"));
        transactions.add(new Transaction("id2", 25.0, "09:30"));
        transactions.add(new Transaction("id3", 5.0, "10:15"));
        transactions.add(new Transaction("id4", 60.0, "11:00")); // Outlier

        int size = transactions.size();

        System.out.println("Original Transactions:");
        printTransactions(transactions);

        // Choose sorting algorithm
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
