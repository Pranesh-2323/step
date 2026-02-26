import java.util.*;

public class PlagiarismDetector {

    private static final int N = 5; // 5-gram

    // Extract n-grams from text
    public static List<String> extractNGrams(String text) {
        List<String> ngrams = new ArrayList<>();

        String[] words = text.toLowerCase().split("\\s+");

        for (int i = 0; i <= words.length - N; i++) {
            StringBuilder gram = new StringBuilder();
            for (int j = 0; j < N; j++) {
                gram.append(words[i + j]).append(" ");
            }
            ngrams.add(gram.toString().trim());
        }

        return ngrams;
    }

    // Calculate similarity percentage
    public static double calculateSimilarity(String doc1, String doc2) {

        List<String> ngrams1 = extractNGrams(doc1);
        List<String> ngrams2 = extractNGrams(doc2);

        HashSet<String> set1 = new HashSet<>(ngrams1);
        HashSet<String> set2 = new HashSet<>(ngrams2);

        int matchCount = 0;

        for (String gram : set1) {
            if (set2.contains(gram)) {
                matchCount++;
            }
        }

        if (set1.size() == 0) return 0;

        return (matchCount * 100.0) / set1.size();
    }

    // ======================
    // MAIN METHOD
    // ======================
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Plagiarism Detection System ===");

        System.out.println("\nEnter Document 1:");
        String doc1 = scanner.nextLine();

        System.out.println("\nEnter Document 2:");
        String doc2 = scanner.nextLine();

        double similarity = calculateSimilarity(doc1, doc2);

        System.out.println("\nSimilarity: " + String.format("%.2f", similarity) + "%");

        if (similarity > 60) {
            System.out.println("🚨 PLAGIARISM DETECTED!");
        } else if (similarity > 20) {
            System.out.println("⚠ Suspicious similarity.");
        } else {
            System.out.println("✅ Documents are mostly different.");
        }

        scanner.close();
    }
}