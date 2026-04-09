import java.util.Arrays;

public class RiskThresholdBinaryLookup {

    // Linear Search (Unsorted)
    public static int linearSearch(int[] risks, int target) {

        int comparisons = 0;

        for (int i = 0; i < risks.length; i++) {

            comparisons++;

            if (risks[i] == target) {

                System.out.println("Linear Search:");
                System.out.println("Found at index: " + i);
                System.out.println("Comparisons: " + comparisons);
                return i;
            }
        }

        System.out.println("Linear Search:");
        System.out.println("Threshold not found");
        System.out.println("Comparisons: " + comparisons);

        return -1;
    }

    // Binary Search Insertion Point
    public static int findInsertionPoint(int[] risks, int target) {

        int low = 0;
        int high = risks.length - 1;

        while (low <= high) {

            int mid = (low + high) / 2;

            if (risks[mid] == target)
                return mid;

            if (risks[mid] < target)
                low = mid + 1;
            else
                high = mid - 1;
        }

        return low; // insertion index
    }

    // Floor Value (largest <= target)
    public static int findFloor(int[] risks, int target) {

        int low = 0;
        int high = risks.length - 1;
        int floor = -1;

        while (low <= high) {

            int mid = (low + high) / 2;

            if (risks[mid] == target)
                return risks[mid];

            if (risks[mid] < target) {

                floor = risks[mid];
                low = mid + 1;
            }

            else
                high = mid - 1;
        }

        return floor;
    }

    // Ceiling Value (smallest >= target)
    public static int findCeiling(int[] risks, int target) {

        int low = 0;
        int high = risks.length - 1;
        int ceiling = -1;

        while (low <= high) {

            int mid = (low + high) / 2;

            if (risks[mid] == target)
                return risks[mid];

            if (risks[mid] > target) {

                ceiling = risks[mid];
                high = mid - 1;
            }

            else
                low = mid + 1;
        }

        return ceiling;
    }

    // Main Method
    public static void main(String[] args) {

        int[] risks = { 10, 25, 50, 100 };

        int target = 30;

        System.out.println("Risk Bands:");
        System.out.println(Arrays.toString(risks));

        // Linear Search
        linearSearch(risks, target);

        // Ensure array is sorted
        Arrays.sort(risks);

        // Binary Insertion Point
        int insertionIndex = findInsertionPoint(risks, target);

        System.out.println("\nBinary Search Insertion Point:");
        System.out.println("Insert " + target + " at index: " + insertionIndex);

        // Floor
        int floor = findFloor(risks, target);

        System.out.println("\nFloor Value:");
        System.out.println("Largest <= " + target + ": " + floor);

        // Ceiling
        int ceiling = findCeiling(risks, target);

        System.out.println("\nCeiling Value:");
        System.out.println("Smallest >= " + target + ": " + ceiling);
    }
}
