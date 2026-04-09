public class HistoricalTradeVolumeAnalysis {

    // Trade class
    static class Trade {

        String id;
        int volume;

        public Trade(String id, int volume) {
            this.id = id;
            this.volume = volume;
        }

        @Override
        public String toString() {
            return id + ":" + volume;
        }
    }

    // ---------------- MERGE SORT (Ascending) ----------------

    public static void mergeSort(Trade[] trades, int left, int right) {

        if (left < right) {

            int mid = (left + right) / 2;

            mergeSort(trades, left, mid);
            mergeSort(trades, mid + 1, right);

            merge(trades, left, mid, right);
        }
    }

    public static void merge(Trade[] trades, int left, int mid, int right) {

        int n1 = mid - left + 1;
        int n2 = right - mid;

        Trade[] leftArr = new Trade[n1];
        Trade[] rightArr = new Trade[n2];

        for (int i = 0; i < n1; i++)
            leftArr[i] = trades[left + i];

        for (int j = 0; j < n2; j++)
            rightArr[j] = trades[mid + 1 + j];

        int i = 0, j = 0, k = left;

        while (i < n1 && j < n2) {

            if (leftArr[i].volume <= rightArr[j].volume) {
                trades[k] = leftArr[i];
                i++;
            } else {
                trades[k] = rightArr[j];
                j++;
            }

            k++;
        }

        while (i < n1) {
            trades[k] = leftArr[i];
            i++;
            k++;
        }

        while (j < n2) {
            trades[k] = rightArr[j];
            j++;
            k++;
        }
    }

    // ---------------- QUICK SORT (Descending) ----------------

    public static void quickSortDesc(Trade[] trades, int low, int high) {

        if (low < high) {

            int pivotIndex = partition(trades, low, high);

            quickSortDesc(trades, low, pivotIndex - 1);
            quickSortDesc(trades, pivotIndex + 1, high);
        }
    }

    public static int partition(Trade[] trades, int low, int high) {

        int pivot = trades[high].volume;

        int i = low - 1;

        for (int j = low; j < high; j++) {

            if (trades[j].volume > pivot) {

                i++;

                Trade temp = trades[i];
                trades[i] = trades[j];
                trades[j] = temp;
            }
        }

        Trade temp = trades[i + 1];
        trades[i + 1] = trades[high];
        trades[high] = temp;

        return i + 1;
    }

    // ---------------- MERGE TWO SORTED LISTS ----------------

    public static Trade[] mergeTwoSortedLists(Trade[] list1, Trade[] list2) {

        int n1 = list1.length;
        int n2 = list2.length;

        Trade[] merged = new Trade[n1 + n2];

        int i = 0, j = 0, k = 0;

        while (i < n1 && j < n2) {

            if (list1[i].volume <= list2[j].volume) {
                merged[k++] = list1[i++];
            } else {
                merged[k++] = list2[j++];
            }
        }

        while (i < n1)
            merged[k++] = list1[i++];

        while (j < n2)
            merged[k++] = list2[j++];

        return merged;
    }

    // ---------------- TOTAL VOLUME ----------------

    public static int computeTotalVolume(Trade[] trades) {

        int total = 0;

        for (Trade t : trades) {
            total += t.volume;
        }

        return total;
    }

    // ---------------- PRINT ----------------

    public static void printTrades(Trade[] trades) {

        for (Trade t : trades) {
            System.out.println(t);
        }
    }

    // ---------------- MAIN ----------------

    public static void main(String[] args) {

        Trade[] trades = {
                new Trade("trade3", 500),
                new Trade("trade1", 100),
                new Trade("trade2", 300)
        };

        System.out.println("Original Trades:");
        printTrades(trades);

        // Merge Sort (Ascending)
        mergeSort(trades, 0, trades.length - 1);

        System.out.println("\nMerge Sort (Ascending Volume):");
        printTrades(trades);

        // Quick Sort (Descending)
        quickSortDesc(trades, 0, trades.length - 1);

        System.out.println("\nQuick Sort (Descending Volume):");
        printTrades(trades);

        // Example: Merge morning & afternoon sessions
        Trade[] morning = {
                new Trade("m1", 100),
                new Trade("m2", 200)
        };

        Trade[] afternoon = {
                new Trade("a1", 300),
                new Trade("a2", 400)
        };

        Trade[] mergedTrades = mergeTwoSortedLists(morning, afternoon);

        System.out.println("\nMerged Morning + Afternoon Trades:");
        printTrades(mergedTrades);

        int totalVolume = computeTotalVolume(mergedTrades);

        System.out.println("\nTotal Volume: " + totalVolume);
    }
}
