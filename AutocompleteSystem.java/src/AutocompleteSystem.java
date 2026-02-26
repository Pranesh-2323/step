import java.util.*;

public class AutocompleteSystem {

    // query -> frequency
    private HashMap<String, Integer> queryFrequency;

    public AutocompleteSystem() {
        queryFrequency = new HashMap<>();

        // Sample data
        queryFrequency.put("java tutorial", 100);
        queryFrequency.put("javascript basics", 80);
        queryFrequency.put("java download", 60);
        queryFrequency.put("python tutorial", 90);
        queryFrequency.put("java interview questions", 70);
    }

    // Search and update frequency
    public void searchQuery(String query) {
        queryFrequency.put(query,
                queryFrequency.getOrDefault(query, 0) + 1);
    }

    // Get top suggestions by prefix
    public List<String> getSuggestions(String prefix) {

        List<Map.Entry<String, Integer>> matches = new ArrayList<>();

        for (Map.Entry<String, Integer> entry : queryFrequency.entrySet()) {
            if (entry.getKey().startsWith(prefix.toLowerCase())) {
                matches.add(entry);
            }
        }

        // Sort by frequency descending
        matches.sort((a, b) -> b.getValue() - a.getValue());

        List<String> results = new ArrayList<>();

        int count = 0;
        for (Map.Entry<String, Integer> entry : matches) {
            results.add(entry.getKey() + " (" + entry.getValue() + ")");
            count++;
            if (count == 10) break;
        }

        return results;
    }

    // ==========================
    // MAIN METHOD
    // ==========================
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        AutocompleteSystem system = new AutocompleteSystem();

        System.out.println("=== Search Autocomplete System ===");

        while (true) {

            System.out.println("\n1. Type Search Prefix");
            System.out.println("2. Add/Search Full Query");
            System.out.println("3. Exit");
            System.out.print("Choose option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) {
                System.out.print("Enter prefix: ");
                String prefix = scanner.nextLine();

                List<String> suggestions = system.getSuggestions(prefix);

                if (suggestions.isEmpty()) {
                    System.out.println("No suggestions found.");
                } else {
                    System.out.println("Top Suggestions:");
                    for (String s : suggestions) {
                        System.out.println(" - " + s);
                    }
                }

            } else if (choice == 2) {
                System.out.print("Enter full query: ");
                String query = scanner.nextLine();

                system.searchQuery(query);
                System.out.println("Query added/updated successfully!");

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