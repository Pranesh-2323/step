import java.util.*;

public class UsernameChecker {

    // Stores username -> userId
    private HashMap<String, Integer> users;

    // Stores username -> search attempts
    private HashMap<String, Integer> attemptFrequency;

    public UsernameChecker() {
        users = new HashMap<>();
        attemptFrequency = new HashMap<>();
    }

    // Register new user
    public boolean registerUser(String username, int userId) {
        if (users.containsKey(username)) {
            return false; // Username already taken
        }
        users.put(username, userId);
        return true;
    }

    // Check username availability
    public boolean checkAvailability(String username) {

        // Track how many times it was searched
        attemptFrequency.put(username,
                attemptFrequency.getOrDefault(username, 0) + 1);

        return !users.containsKey(username);
    }

    // Suggest alternative usernames
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

    // Get most searched username
    public String getMostAttempted() {

        if (attemptFrequency.isEmpty()) {
            return "No searches yet.";
        }

        String mostAttempted = null;
        int max = 0;

        for (Map.Entry<String, Integer> entry : attemptFrequency.entrySet()) {
            if (entry.getValue() > max) {
                max = entry.getValue();
                mostAttempted = entry.getKey();
            }
        }

        return mostAttempted + " (" + max + " searches)";
    }

    // ==========================
    // MAIN METHOD
    // ==========================
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        UsernameChecker checker = new UsernameChecker();

        // Pre-registered usernames
        checker.registerUser("john_doe", 1);
        checker.registerUser("admin", 2);

        System.out.println("=== Username Availability Checker ===");

        while (true) {

            System.out.println("\n1. Check Username");
            System.out.println("2. Show Most Searched Username");
            System.out.println("3. Exit");
            System.out.print("Choose option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) {

                System.out.print("Enter username: ");
                String username = scanner.nextLine();

                if (checker.checkAvailability(username)) {
                    System.out.println("✅ Username is available!");
                } else {
                    System.out.println("❌ Username is taken.");
                    System.out.println("Suggestions: "
                            + checker.suggestAlternatives(username));
                }

            } else if (choice == 2) {

                System.out.println("Most Searched: "
                        + checker.getMostAttempted());

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