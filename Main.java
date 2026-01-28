import java.util.*;
import java.time.*;

class Vehicle {
    private String number;
    private String ownerName;
    private LocalDateTime entryTime;

    public Vehicle(String number, String ownerName) {
        this.number = number;
        this.ownerName = ownerName;
        this.entryTime = LocalDateTime.now();
    }

    public String getNumber() {
        return number;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public LocalDateTime getEntryTime() {
        return entryTime;
    }
    public String toString() {
        return String.format("%-10s %-15s %-25s", number, ownerName, entryTime.toString());
    }
}

class ParkingLot {
    private int capacity;
    private Map<Integer, Vehicle> slots;
    private double totalRevenue;
    private double ratePerHour;

    public ParkingLot(int capacity, double ratePerHour) {
        this.capacity = capacity;
        this.ratePerHour = ratePerHour;
        this.slots = new HashMap<>();
        this.totalRevenue = 0;
    }

    public boolean parkVehicle(Vehicle vehicle) {
        if (slots.size() >= capacity) {
            System.out.println(" Parking Lot is Full!");
            return false;
        }
        for (int i = 1; i <= capacity; i++) {
            if (!slots.containsKey(i)) {
                slots.put(i, vehicle);
                System.out.println(" Vehicle parked successfully at slot " + i);
                return true;
            }
        }
        return false;
    }

    public boolean removeVehicle(String number) {
        for (Map.Entry<Integer, Vehicle> entry : slots.entrySet()) {
            if (entry.getValue().getNumber().equalsIgnoreCase(number)) {
                int slot = entry.getKey();
                Vehicle v = entry.getValue();
                LocalDateTime exitTime = LocalDateTime.now();
                long duration = Duration.between(v.getEntryTime(), exitTime).toMinutes();
                double hours = Math.ceil(duration / 60.0);
                double fee = hours * ratePerHour;
                totalRevenue += fee;
                slots.remove(slot);
                System.out.println(" Vehicle " + number + " removed successfully.");
                System.out.println(" Duration Parked: " + duration + " minutes");
                System.out.println(" Parking Fee: ₹" + fee);
                return true;
            }
        }
        System.out.println(" Vehicle not found!");
        return false;
    }

    public void searchVehicle(String number) {
        for (Map.Entry<Integer, Vehicle> entry : slots.entrySet()) {
            if (entry.getValue().getNumber().equalsIgnoreCase(number)) {
                System.out.println(" Vehicle found at slot " + entry.getKey());
                System.out.println(entry.getValue());
                return;
            }
        }
        System.out.println(" Vehicle not found!");
    }

    public void displayStatus() {
        System.out.println("\n========= PARKING LOT STATUS =========");
        for (int i = 1; i <= capacity; i++) {
            if (slots.containsKey(i)) {
                Vehicle v = slots.get(i);
                System.out.println("Slot " + i + " → " + v.getNumber() + " (" + v.getOwnerName() + ")");
            } else {
                System.out.println("Slot " + i + " → [Empty]");
            }
        }
        System.out.println("=====================================");
    }

    public void displayAllVehicles() {
        System.out.println("\n========= PARKED VEHICLES =========");
        if (slots.isEmpty()) {
            System.out.println("No vehicles parked currently!");
        } else {
            System.out.printf("%-10s %-15s %-25s %-10s%n", "Number", "Owner", "Entry Time", "Slot");
            for (Map.Entry<Integer, Vehicle> entry : slots.entrySet()) {
                Vehicle v = entry.getValue();
                System.out.printf("%-10s %-15s %-25s %-10d%n",
                        v.getNumber(), v.getOwnerName(), v.getEntryTime(), entry.getKey());
            }
        }
        System.out.println("===================================");
    }

    public void showAvailableSlots() {
        System.out.println("\nAvailable Slots: " + (capacity - slots.size()));
        System.out.println("Occupied Slots: " + slots.size());
    }

    public void showRevenue() {
        System.out.println("\n Total Revenue Collected: ₹" + totalRevenue);
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println(" Welcome to Parking Lot Management System ");

        System.out.print("Enter Parking Lot Capacity: ");
        int capacity = sc.nextInt();
        System.out.print("Enter Parking Rate (₹ per minute): ");
        double rate = sc.nextDouble();
        sc.nextLine();

        ParkingLot lot = new ParkingLot(capacity, rate);

        while (true) {
            System.out.println("\n========== MAIN MENU ==========");
            System.out.println("1️  Park a Vehicle");
            System.out.println("2️  Remove a Vehicle");
            System.out.println("3️  Search a Vehicle");
            System.out.println("4️  View Parking Lot Status");
            System.out.println("5️  View All Parked Vehicles");
            System.out.println("6️  Show Available Slots");
            System.out.println("7️  Show Total Revenue");
            System.out.println("8️  Exit");
            System.out.println("===============================");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter Vehicle Number: ");
                    String num = sc.nextLine();
                    System.out.print("Enter Owner Name: ");
                    String name = sc.nextLine();
                    lot.parkVehicle(new Vehicle(num, name));
                    break;

                case 2:
                    System.out.print("Enter Vehicle Number to Remove: ");
                    String rem = sc.nextLine();
                    lot.removeVehicle(rem);
                    break;

                case 3:
                    System.out.print("Enter Vehicle Number to Search: ");
                    String search = sc.nextLine();
                    lot.searchVehicle(search);
                    break;

                case 4:
                    lot.displayStatus();
                    break;

                case 5:
                    lot.displayAllVehicles();
                    break;

                case 6:
                    lot.showAvailableSlots();
                    break;

                case 7:
                    lot.showRevenue();
                    break;

                case 8:
                    System.out.println("\nSaving data...");
                    try { Thread.sleep(1000); } catch (InterruptedException e) {}
                    System.out.println(" Thank you for using Parking Lot Management System!");
                    sc.close();
                    return;

                default:
                    System.out.println(" Invalid option! Try again.");
            }
        }
    }
}
