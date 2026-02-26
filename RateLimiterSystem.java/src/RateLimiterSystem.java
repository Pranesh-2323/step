import java.util.*;

public class RateLimiterSystem {

    // Token Bucket Class
    static class TokenBucket {
        private int maxTokens;
        private double refillRate; // tokens per second
        private double currentTokens;
        private long lastRefillTime;

        public TokenBucket(int maxTokens, int refillPerMinute) {
            this.maxTokens = maxTokens;
            this.refillRate = refillPerMinute / 60.0; // per second
            this.currentTokens = maxTokens;
            this.lastRefillTime = System.currentTimeMillis();
        }

        // Check and consume token
        public boolean allowRequest() {
            refillTokens();

            if (currentTokens >= 1) {
                currentTokens--;
                return true;
            }
            return false;
        }

        // Refill tokens based on time passed
        private void refillTokens() {
            long now = System.currentTimeMillis();
            double secondsPassed = (now - lastRefillTime) / 1000.0;

            double tokensToAdd = secondsPassed * refillRate;

            if (tokensToAdd > 0) {
                currentTokens = Math.min(maxTokens, currentTokens + tokensToAdd);
                lastRefillTime = now;
            }
        }

        public int getRemainingTokens() {
            refillTokens();
            return (int) currentTokens;
        }
    }

    // clientId -> TokenBucket
    private HashMap<String, TokenBucket> clients;

    public RateLimiterSystem() {
        clients = new HashMap<>();
    }

    public boolean checkRateLimit(String clientId) {

        clients.putIfAbsent(clientId,
                new TokenBucket(10, 10)); // max 10 req/min for demo

        return clients.get(clientId).allowRequest();
    }

    public int getRemaining(String clientId) {
        if (!clients.containsKey(clientId)) return 0;
        return clients.get(clientId).getRemainingTokens();
    }

    // ==========================
    // MAIN METHOD
    // ==========================
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        RateLimiterSystem limiter = new RateLimiterSystem();

        System.out.println("=== API Rate Limiter System ===");

        while (true) {

            System.out.println("\n1. Send Request");
            System.out.println("2. Check Remaining Tokens");
            System.out.println("3. Exit");
            System.out.print("Choose option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) {

                System.out.print("Enter Client ID: ");
                String clientId = scanner.nextLine();

                if (limiter.checkRateLimit(clientId)) {
                    System.out.println("✅ Request Allowed. Remaining: "
                            + limiter.getRemaining(clientId));
                } else {
                    System.out.println("❌ Rate Limit Exceeded. Try later.");
                }

            } else if (choice == 2) {

                System.out.print("Enter Client ID: ");
                String clientId = scanner.nextLine();

                System.out.println("Remaining Tokens: "
                        + limiter.getRemaining(clientId));

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