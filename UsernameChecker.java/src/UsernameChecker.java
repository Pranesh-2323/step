import java.util.*;

public class UsernameChecker {

    private HashMap<String, Integer> users;
    private HashMap<String, Integer> attemptFrequency;

    public UsernameChecker() {
        users = new HashMap<>();
        attemptFrequency = new HashMap<>();
    }

    public boolean registerUser(String username, int userId) {
        if (users.containsKey(username)) {
            return false;
        }
        users.put(username, userId);
        return true;
    }

    public boolean checkAvailability(String username) {
        attemptFrequency.put(username,
                attemptFrequency.getOrDefault(username, 0) + 1);

        return !users.containsKey(username);
    }

    public List<String> suggestAlternatives(String username) {
        List<String> suggestions = new ArrayList<>();

        for (int i = 1; i <= 5; i++) {
            String newUsername = username + i;
            if (!users.containsKey(newUsername)) {
                suggestions.add(newUsername);
            }
        }

        if (username.contains("_")) {
            String dotVersion = username.replace("_", ".");
            if (!users.containsKey(dotVersion)) {
                suggestions.add(dotVersion);
            }
        }

        return suggestions;
    }

    public String getMostAttempted() {
        if (attemptFrequency.isEmpty()) {
            return "No attempts yet.";
        }

        String mostAttempted = null;
        int maxAttempts = 0;

        for (Map.Entry<String, Integer> entry : attemptFrequency.entrySet()) {
            if (entry.getValue() > maxAttempts) {
                maxAttempts = entry.getValue();
                mostAttempted = entry.getKey();
            }
        }

        return mostAttempted + " (" + maxAttempts + " attempts)";
    }

    // ==========================
    // MAIN METHOD (User Input)
    // ==========================
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        UsernameChecker checker = new UsernameChecker();

        // Pre-register some users
        checker.registerUser("john_doe", 101);
        checker.registerUser("admin", 1);

        System.out.println("=== Username Availability Checker ===");

        while (true) {
            System.out.println("\n1. Check Username");
            System.out.println("2. Show Most Attempted Username");
            System.out.println("3. Exit");
            System.out.print("Choose option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // clear buffer

            if (choice == 1) {
                System.out.print("Enter username: ");
                String username = scanner.nextLine();

                boolean available = checker.checkAvailability(username);

                if (available) {
                    System.out.println("✅ Username is available!");
                } else {
                    System.out.println("❌ Username is taken.");
                    System.out.println("Suggestions: "
                            + checker.suggestAlternatives(username));
                }

            } else if (choice == 2) {
                System.out.println("Most Attempted: "
                        + checker.getMostAttempted());

            } else if (choice == 3) {
                System.out.println("Exiting program...");
                break;

            } else {
                System.out.println("Invalid choice. Try again.");
            }
        }

        scanner.close();
    }
}