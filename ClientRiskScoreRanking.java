import java.util.Arrays;

public class ClientRiskScoreRanking {

    // Client class
    static class Client {

        String name;
        int riskScore;
        double accountBalance;

        public Client(String name, int riskScore, double accountBalance) {
            this.name = name;
            this.riskScore = riskScore;
            this.accountBalance = accountBalance;
        }

        @Override
        public String toString() {
            return name + "(" + riskScore + ", Bal=" + accountBalance + ")";
        }
    }

    // Bubble Sort - Ascending Risk Score
    public static void bubbleSortAscending(Client[] clients) {

        int n = clients.length;
        int swaps = 0;

        for (int i = 0; i < n - 1; i++) {

            for (int j = 0; j < n - i - 1; j++) {

                if (clients[j].riskScore > clients[j + 1].riskScore) {

                    // Swap
                    Client temp = clients[j];
                    clients[j] = clients[j + 1];
                    clients[j + 1] = temp;

                    swaps++;
                }
            }
        }

        System.out.println("Bubble Sort (Ascending Risk Score):");
        printClients(clients);

        System.out.println("Total Swaps: " + swaps);
    }

    // Insertion Sort - Descending Risk Score + Account Balance
    public static void insertionSortDescending(Client[] clients) {

        int n = clients.length;

        for (int i = 1; i < n; i++) {

            Client key = clients[i];
            int j = i - 1;

            while (j >= 0 &&
                  (clients[j].riskScore < key.riskScore ||
                  (clients[j].riskScore == key.riskScore &&
                   clients[j].accountBalance < key.accountBalance))) {

                clients[j + 1] = clients[j];
                j--;
            }

            clients[j + 1] = key;
        }

        System.out.println("\nInsertion Sort (Descending Risk Score + Balance):");
        printClients(clients);
    }

    // Print clients
    public static void printClients(Client[] clients) {

        for (Client c : clients) {
            System.out.println(c);
        }
    }

    // Top 10 highest risk clients
    public static void printTopRisks(Client[] clients) {

        System.out.println("\nTop Highest Risk Clients:");

        int limit = Math.min(10, clients.length);

        for (int i = 0; i < limit; i++) {
            System.out.println(
                (i + 1) + ". " +
                clients[i].name +
                " (Risk: " + clients[i].riskScore + ")"
            );
        }
    }

    // Main method
    public static void main(String[] args) {

        Client[] clients = {

            new Client("clientC", 80, 5000),
            new Client("clientA", 20, 10000),
            new Client("clientB", 50, 7000),
            new Client("clientD", 90, 2000),
            new Client("clientE", 60, 8000),
            new Client("clientF", 75, 6000)
        };

        System.out.println("Original Clients:");
        printClients(clients);

        // Bubble Sort Ascending
        bubbleSortAscending(clients);

        // Insertion Sort Descending
        insertionSortDescending(clients);

        // Top Risk Clients
        printTopRisks(clients);
    }
}
