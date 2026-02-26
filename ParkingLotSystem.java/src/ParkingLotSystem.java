import java.util.*;

public class ParkingLotSystem {

    static class ParkingSpot {
        String licensePlate;
        long entryTime;

        ParkingSpot(String licensePlate) {
            this.licensePlate = licensePlate;
            this.entryTime = System.currentTimeMillis();
        }
    }

    private ParkingSpot[] table;
    private int capacity;
    private int size;
    private int totalProbes;

    public ParkingLotSystem(int capacity) {
        this.capacity = capacity;
        table = new ParkingSpot[capacity];
        size = 0;
        totalProbes = 0;
    }

    // Hash function
    private int hash(String licensePlate) {
        return Math.abs(licensePlate.hashCode()) % capacity;
    }

    // Park vehicle using Linear Probing
    public void parkVehicle(String licensePlate) {

        if (size == capacity) {
            System.out.println("Parking Lot Full!");
            return;
        }

        int index = hash(licensePlate);
        int probes = 0;

        while (table[index] != null) {
            index = (index + 1) % capacity;
            probes++;
        }

        table[index] = new ParkingSpot(licensePlate);
        size++;
        totalProbes += probes;

        System.out.println("✅ Vehicle parked at spot #" + index +
                " (" + probes + " probes)");
    }

    // Exit vehicle
    public void exitVehicle(String licensePlate) {

        int index = hash(licensePlate);

        while (table[index] != null) {

            if (table[index].licensePlate.equals(licensePlate)) {

                long durationMillis =
                        System.currentTimeMillis() - table[index].entryTime;

                double hours = durationMillis / (1000.0 * 60 * 60);
                double fee = hours * 10; // $10 per hour

                table[index] = null;
                size--;

                System.out.println("🚗 Vehicle exited from spot #" + index);
                System.out.println("Duration: "
                        + String.format("%.2f", hours) + " hours");
                System.out.println("Fee: $" + String.format("%.2f", fee));
                return;
            }

            index = (index + 1) % capacity;
        }

        System.out.println("Vehicle not found.");
    }

    // Show statistics
    public void showStatistics() {

        double occupancy = (size * 100.0) / capacity;
        double avgProbes = (size == 0) ? 0 :
                (totalProbes * 1.0 / size);

        System.out.println("Occupancy: "
                + String.format("%.2f", occupancy) + "%");
        System.out.println("Average Probes: "
                + String.format("%.2f", avgProbes));
    }

    // ==========================
    // MAIN METHOD
    // ==========================
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        ParkingLotSystem parking = new ParkingLotSystem(10);

        System.out.println("=== Smart Parking Lot System ===");

        while (true) {

            System.out.println("\n1. Park Vehicle");
            System.out.println("2. Exit Vehicle");
            System.out.println("3. Show Statistics");
            System.out.println("4. Exit Program");
            System.out.print("Choose option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) {
                System.out.print("Enter License Plate: ");
                String plate = scanner.nextLine();
                parking.parkVehicle(plate);

            } else if (choice == 2) {
                System.out.print("Enter License Plate: ");
                String plate = scanner.nextLine();
                parking.exitVehicle(plate);

            } else if (choice == 3) {
                parking.showStatistics();

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