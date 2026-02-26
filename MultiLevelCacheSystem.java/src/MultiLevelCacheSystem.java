import java.util.*;

public class MultiLevelCacheSystem {

    // LRU Cache using LinkedHashMap
    static class LRUCache<K, V> extends LinkedHashMap<K, V> {
        private int capacity;

        public LRUCache(int capacity) {
            super(capacity, 0.75f, true); // access-order
            this.capacity = capacity;
        }

        @Override
        protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
            return size() > capacity;
        }
    }

    private LRUCache<String, String> L1; // Fast memory
    private LRUCache<String, String> L2; // SSD
    private HashMap<String, String> L3;  // Database

    private int l1Hits = 0;
    private int l2Hits = 0;
    private int l3Hits = 0;

    public MultiLevelCacheSystem() {
        L1 = new LRUCache<>(3); // small for demo
        L2 = new LRUCache<>(5);
        L3 = new HashMap<>();

        // Simulated database
        L3.put("video1", "Video Data 1");
        L3.put("video2", "Video Data 2");
        L3.put("video3", "Video Data 3");
        L3.put("video4", "Video Data 4");
        L3.put("video5", "Video Data 5");
    }

    public String getVideo(String videoId) {

        // Check L1
        if (L1.containsKey(videoId)) {
            l1Hits++;
            System.out.println("✅ L1 Cache HIT (Fast)");
            return L1.get(videoId);
        }

        // Check L2
        if (L2.containsKey(videoId)) {
            l2Hits++;
            System.out.println("⚡ L2 Cache HIT (Medium)");

            String data = L2.get(videoId);
            L1.put(videoId, data); // promote to L1
            return data;
        }

        // Check L3 (Database)
        if (L3.containsKey(videoId)) {
            l3Hits++;
            System.out.println("🐢 L3 Database HIT (Slow)");

            String data = L3.get(videoId);
            L2.put(videoId, data); // add to L2
            return data;
        }

        return "Video not found.";
    }

    public void showStats() {

        int total = l1Hits + l2Hits + l3Hits;

        System.out.println("\n=== Cache Statistics ===");

        if (total == 0) {
            System.out.println("No requests yet.");
            return;
        }

        System.out.println("L1 Hits: " + l1Hits);
        System.out.println("L2 Hits: " + l2Hits);
        System.out.println("L3 Hits: " + l3Hits);

        System.out.println("Overall Hit Rate: "
                + String.format("%.2f",
                (total * 100.0) / total) + "%");
    }

    // ==========================
    // MAIN METHOD
    // ==========================
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        MultiLevelCacheSystem cache = new MultiLevelCacheSystem();

        System.out.println("=== Multi-Level Cache System ===");

        while (true) {

            System.out.println("\n1. Get Video");
            System.out.println("2. Show Cache Stats");
            System.out.println("3. Exit");
            System.out.print("Choose option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) {

                System.out.print("Enter Video ID (video1-video5): ");
                String id = scanner.nextLine();

                String result = cache.getVideo(id);
                System.out.println("Result: " + result);

            } else if (choice == 2) {

                cache.showStats();

            } else if (choice == 3) {
                System.out.println("Exiting...");
                break;
            } else {
                System.out.println("Invalid option.");
            }
        }

        scanner.close();
    }
}