import java.util.Random;

public class PortfolioReturnSorting {

    // Asset class
    static class Asset {

        String name;
        double returnRate;
        double volatility;

        public Asset(String name, double returnRate, double volatility) {
            this.name = name;
            this.returnRate = returnRate;
            this.volatility = volatility;
        }

        @Override
        public String toString() {
            return name + " (Return=" + returnRate + "%, Vol=" + volatility + ")";
        }
    }

    // ---------------- MERGE SORT (Stable Ascending Return) ----------------

    public static void mergeSort(Asset[] assets, int left, int right) {

        if (left < right) {

            int mid = (left + right) / 2;

            mergeSort(assets, left, mid);
            mergeSort(assets, mid + 1, right);

            merge(assets, left, mid, right);
        }
    }

    public static void merge(Asset[] assets, int left, int mid, int right) {

        int n1 = mid - left + 1;
        int n2 = right - mid;

        Asset[] leftArr = new Asset[n1];
        Asset[] rightArr = new Asset[n2];

        for (int i = 0; i < n1; i++)
            leftArr[i] = assets[left + i];

        for (int j = 0; j < n2; j++)
            rightArr[j] = assets[mid + 1 + j];

        int i = 0, j = 0, k = left;

        while (i < n1 && j < n2) {

            // Stable comparison
            if (leftArr[i].returnRate <= rightArr[j].returnRate) {
                assets[k++] = leftArr[i++];
            } else {
                assets[k++] = rightArr[j++];
            }
        }

        while (i < n1)
            assets[k++] = leftArr[i++];

        while (j < n2)
            assets[k++] = rightArr[j++];
    }

    // ---------------- QUICK SORT ----------------

    public static void quickSort(Asset[] assets, int low, int high) {

        // Hybrid: use insertion sort for small partitions
        if (high - low < 10) {
            insertionSort(assets, low, high);
            return;
        }

        if (low < high) {

            int pivotIndex = medianOfThree(assets, low, high);

            int partitionIndex = partition(assets, low, high, pivotIndex);

            quickSort(assets, low, partitionIndex - 1);
            quickSort(assets, partitionIndex + 1, high);
        }
    }

    // Partition logic
    public static int partition(Asset[] assets, int low, int high, int pivotIndex) {

        Asset pivot = assets[pivotIndex];

        // Move pivot to end
        Asset temp = assets[pivotIndex];
        assets[pivotIndex] = assets[high];
        assets[high] = temp;

        int i = low - 1;

        for (int j = low; j < high; j++) {

            if (assets[j].returnRate > pivot.returnRate ||
               (assets[j].returnRate == pivot.returnRate &&
                assets[j].volatility < pivot.volatility)) {

                i++;

                Asset swap = assets[i];
                assets[i] = assets[j];
                assets[j] = swap;
            }
        }

        Asset swap = assets[i + 1];
        assets[i + 1] = assets[high];
        assets[high] = swap;

        return i + 1;
    }

    // ---------------- MEDIAN OF 3 PIVOT ----------------

    public static int medianOfThree(Asset[] assets, int low, int high) {

        int mid = (low + high) / 2;

        if (assets[low].returnRate > assets[mid].returnRate)
            swap(assets, low, mid);

        if (assets[low].returnRate > assets[high].returnRate)
            swap(assets, low, high);

        if (assets[mid].returnRate > assets[high].returnRate)
            swap(assets, mid, high);

        return mid;
    }

    // ---------------- RANDOM PIVOT ----------------

    public static int randomPivot(int low, int high) {

        Random rand = new Random();

        return rand.nextInt(high - low + 1) + low;
    }

    // ---------------- INSERTION SORT (Hybrid) ----------------

    public static void insertionSort(Asset[] assets, int low, int high) {

        for (int i = low + 1; i <= high; i++) {

            Asset key = assets[i];
            int j = i - 1;

            while (j >= low &&
                  (assets[j].returnRate < key.returnRate ||
                  (assets[j].returnRate == key.returnRate &&
                   assets[j].volatility > key.volatility))) {

                assets[j + 1] = assets[j];
                j--;
            }

            assets[j + 1] = key;
        }
    }

    // Swap helper
    public static void swap(Asset[] assets, int i, int j) {

        Asset temp = assets[i];
        assets[i] = assets[j];
        assets[j] = temp;
    }

    // Print
    public static void printAssets(Asset[] assets) {

        for (Asset a : assets) {
            System.out.println(a);
        }
    }

    // ---------------- MAIN ----------------

    public static void main(String[] args) {

        Asset[] assets = {

            new Asset("AAPL", 12, 5),
            new Asset("TSLA", 8, 7),
            new Asset("GOOG", 15, 4),
            new Asset("MSFT", 12, 3)
        };

        System.out.println("Original Assets:");
        printAssets(assets);

        // Merge Sort
        mergeSort(assets, 0, assets.length - 1);

        System.out.println("\nMerge Sort (Ascending Return):");
        printAssets(assets);

        // Quick Sort
        quickSort(assets, 0, assets.length - 1);

        System.out.println("\nQuick Sort (Return DESC + Volatility ASC):");
        printAssets(assets);
    }
}
