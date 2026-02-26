import java.util.*;

public class RealTimeAnalytics {

    // pageUrl -> visit count
    private HashMap<String, Integer> pageViews;

    // pageUrl -> unique visitors
    private HashMap<String, HashSet<String>> uniqueVisitors;

    // traffic source -> count
    private HashMap<String, Integer> trafficSources;

    public RealTimeAnalytics() {
        pageViews = new HashMap<>();
        uniqueVisitors = new HashMap<>();
        trafficSources = new HashMap<>();
    }

    // Process a page view event
    public void processEvent(String url, String userId, String source) {

        // Update page views
        pageViews.put(url, pageViews.getOrDefault(url, 0) + 1);

        // Update unique visitors
        uniqueVisitors.putIfAbsent(url, new HashSet<>());
        uniqueVisitors.get(url).add(userId);

        // Update traffic source
        trafficSources.put(source, trafficSources.getOrDefault(source, 0) + 1);

        System.out.println("✅ Event processed successfully!");
    }

    // Show Top 10 pages
    public void showTopPages() {

        List<Map.Entry<String, Integer>> list =
                new ArrayList<>(pageViews.entrySet());

        list.sort((a, b) -> b.getValue() - a.getValue());

        System.out.println("\n=== Top Pages ===");

        int count = 0;
        for (Map.Entry<String, Integer> entry : list) {

            String url = entry.getKey();
            int views = entry.getValue();
            int unique = uniqueVisitors.get(url).size();

            System.out.println(url + " - " + views +
                    " views (" + unique + " unique)");

            count++;
            if (count == 10) break;
        }
    }

    // Show traffic source stats
    public void showTrafficSources() {

        int total = 0;
        for (int value : trafficSources.values()) {
            total += value;
        }

        System.out.println("\n=== Traffic Sources ===");

        for (Map.Entry<String, Integer> entry : trafficSources.entrySet()) {

            double percent = (entry.getValue() * 100.0) / total;

            System.out.println(entry.getKey() + " - "
                    + String.format("%.2f", percent) + "%");
        }
    }

    // =========================
    // MAIN METHOD
    // =========================
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        RealTimeAnalytics analytics = new RealTimeAnalytics();

        System.out.println("=== Real-Time Website Analytics ===");

        while (true) {

            System.out.println("\n1. Add Page View Event");
            System.out.println("2. Show Top Pages");
            System.out.println("3. Show Traffic Sources");
            System.out.println("4. Exit");
            System.out.print("Choose option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) {
                System.out.print("Enter Page URL: ");
                String url = scanner.nextLine();

                System.out.print("Enter User ID: ");
                String userId = scanner.nextLine();

                System.out.print("Enter Traffic Source (Google/Facebook/Direct): ");
                String source = scanner.nextLine();

                analytics.processEvent(url, userId, source);

            } else if (choice == 2) {
                analytics.showTopPages();

            } else if (choice == 3) {
                analytics.showTrafficSources();

            } else if (choice == 4) {
                System.out.println("Exiting...");
                break;

            } else {
                System.out.println("Invalid option.");
            }
        }

        scanner.close();
    }
}