import java.util.Arrays;

public class AccountIDLookup {

    // Linear Search - First Occurrence
    public static int linearSearchFirst(String[] logs, String target) {

        int comparisons = 0;

        for (int i = 0; i < logs.length; i++) {

            comparisons++;

            if (logs[i].equals(target)) {

                System.out.println("Linear Search First Occurrence:");
                System.out.println("Index: " + i);
                System.out.println("Comparisons: " + comparisons);
                System.out.println("Time Complexity: O(n)");

                return i;
            }
        }

        System.out.println("Account not found");
        return -1;
    }

    // Linear Search - Last Occurrence
    public static int linearSearchLast(String[] logs, String target) {

        int comparisons = 0;
        int index = -1;

        for (int i = 0; i < logs.length; i++) {

            comparisons++;

            if (logs[i].equals(target)) {
                index = i;
            }
        }

        System.out.println("\nLinear Search Last Occurrence:");
        System.out.println("Index: " + index);
        System.out.println("Comparisons: " + comparisons);
        System.out.println("Time Complexity: O(n)");

        return index;
    }

    // Binary Search - Exact Match
    public static int binarySearch(String[] logs, String target) {

        int low = 0;
        int high = logs.length - 1;
        int comparisons = 0;

        while (low <= high) {

            comparisons++;

            int mid = (low + high) / 2;

            int result = logs[mid].compareTo(target);

            if (result == 0) {

                System.out.println("\nBinary Search Exact Match:");
                System.out.println("Index: " + mid);
                System.out.println("Comparisons: " + comparisons);
                System.out.println("Time Complexity: O(log n)");

                return mid;
            }

            if (result < 0)
                low = mid + 1;
            else
                high = mid - 1;
        }

        System.out.println("Account not found");
        return -1;
    }

    // Count Occurrences using Binary Search
    public static int countOccurrences(String[] logs, String target) {

        int first = findFirst(logs, target);

        if (first == -1)
            return 0;

        int last = findLast(logs, target);

        int count = last - first + 1;

        System.out.println("Total Occurrences: " + count);

        return count;
    }

    // Find First occurrence (Binary)
    public static int findFirst(String[] logs, String target) {

        int low = 0;
        int high = logs.length - 1;
        int result = -1;

        while (low <= high) {

            int mid = (low + high) / 2;

            if (logs[mid].equals(target)) {

                result = mid;
                high = mid - 1;
            }

            else if (logs[mid].compareTo(target) < 0)
                low = mid + 1;

            else
                high = mid - 1;
        }

        return result;
    }

    // Find Last occurrence (Binary)
    public static int findLast(String[] logs, String target) {

        int low = 0;
        int high = logs.length - 1;
        int result = -1;

        while (low <= high) {

            int mid = (low + high) / 2;

            if (logs[mid].equals(target)) {

                result = mid;
                low = mid + 1;
            }

            else if (logs[mid].compareTo(target) < 0)
                low = mid + 1;

            else
                high = mid - 1;
        }

        return result;
    }

    // Main Method
    public static void main(String[] args) {

        String[] logs = {

            "accB",
            "accA",
            "accB",
            "accC",
            "accB",
            "accD"
        };

        System.out.println("Original Logs:");
        System.out.println(Arrays.toString(logs));

        // Sort before binary search
        Arrays.sort(logs);

        System.out.println("\nSorted Logs:");
        System.out.println(Arrays.toString(logs));

        String target = "accB";

        linearSearchFirst(logs, target);

        linearSearchLast(logs, target);

        binarySearch(logs, target);

        countOccurrences(logs, target);
    }
}
