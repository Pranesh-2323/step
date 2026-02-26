import java.util.*;

public class DNSCacheSystem {

    // Entry class to store DNS details
    static class DNSEntry {
        String ipAddress;
        long expiryTime;

        DNSEntry(String ipAddress, int ttlSeconds) {
            this.ipAddress = ipAddress;
            this.expiryTime = System.currentTimeMillis() + (ttlSeconds * 1000);
        }

        boolean isExpired() {
            return System.currentTimeMillis() > expiryTime;
        }
    }

    private HashMap<String, DNSEntry> cache;
    private int hits;
    private int misses;

    public DNSCacheSystem() {
        cache = new HashMap<>();
        hits = 0;
        misses = 0;
    }

    // Simulate upstream DNS lookup
    private String queryUpstream(String domain) {
        // Fake IP generator (for demo)
        return "192.168." + new Random().nextInt(255) + "." + new Random().nextInt(255);
    }

    // Resolve domain
    public String resolve(String domain, int ttlSeconds) {

        if (cache.containsKey(domain)) {
            DNSEntry entry = cache.get(domain);

            if (!entry.isExpired()) {
                hits++;
                System.out.println("✅ Cache HIT");
                return entry.ipAddress;
            } else {
                System.out.println("⚠ Cache EXPIRED");
                cache.remove(domain);
            }
        }

        // Cache miss
        misses++;
        System.out.println("❌ Cache MISS - Querying upstream...");

        String ip = queryUpstream(domain);
        cache.put(domain, new DNSEntry(ip, ttlSeconds));

        return ip;
    }

    // Show statistics
    public void getStats() {
        int total = hits + misses;
        double hitRate = (total == 0) ? 0 : (hits * 100.0 / total);

        System.out.println("Cache Hits: " + hits);
        System.out.println("Cache Misses: " + misses);
        System.out.println("Hit Rate: " + String.format("%.2f", hitRate) + "%");
    }

    // ======================
    // MAIN METHOD
    // ======================
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        DNSCacheSystem dns = new DNSCacheSystem();

        System.out.println("=== DNS Cache System ===");

        while (true) {
            System.out.println("\n1. Resolve Domain");
            System.out.println("2. Show Cache Stats");
            System.out.println("3. Exit");
            System.out.print("Choose option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) {
                System.out.print("Enter domain (e.g., google.com): ");
                String domain = scanner.nextLine();

                System.out.print("Enter TTL (seconds): ");
                int ttl = scanner.nextInt();
                scanner.nextLine();

                String ip = dns.resolve(domain, ttl);
                System.out.println("Resolved IP: " + ip);

            } else if (choice == 2) {
                dns.getStats();

            } else if (choice == 3) {
                System.out.println("Exiting...");
                break;

            } else {
                System.out.println("Invalid choice.");
            }
        }

        scanner.close();
    }
}