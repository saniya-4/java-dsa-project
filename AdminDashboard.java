import java.util.Scanner;
import java.util.List;

public class AdminDashboard {
    private Scanner scanner;
    private CustomLinkedList<Vehicle> vehicles;
    private CustomLinkedList<Rental> rentals;
    private RentalSystem rentalSystem;

    public AdminDashboard() {
        this.scanner = new Scanner(System.in);
        this.vehicles = new CustomLinkedList<>();
        this.rentals = new CustomLinkedList<>();
        this.rentalSystem = new RentalSystem(vehicles, rentals);
    }

    public void showDashboard() {
        boolean exit = false;
        
        while (!exit) {
            try {
                System.out.println("\n=== Admin Dashboard ===");
                System.out.println("1. View all vehicles");
                System.out.println("2. Add a new vehicle");
                System.out.println("3. Process a rental");
                System.out.println("4. Make vehicle available");
                System.out.println("5. View recent rentals");
                System.out.println("6. Remove vehicle");
                System.out.println("7. View first vehicle");
                System.out.println("8. View last vehicle");
                System.out.println("9. Move vehicle to front");
                System.out.println("10. Exit");
                System.out.print("Please choose an option (1-10): ");

                String input = scanner.nextLine().trim();
                int choice = Integer.parseInt(input);
                
                switch (choice) {
                    case 1:
                        viewAllVehicles();
                        break;
                    case 2:
                        addNewVehicle();
                        break;
                    case 3:
                        rentalSystem.startRentalProcess();
                        break;
                    case 4:
                        makeVehicleAvailable();
                        break;
                    case 5:
                        viewRecentRentals();
                        break;
                    case 6:
                        removeVehicle();
                        break;
                    case 7:
                        viewFirstVehicle();
                        break;
                    case 8:
                        viewLastVehicle();
                        break;
                    case 9:
                        moveVehicleToFront();
                        break;
                    case 10:
                        exit = true;
                        System.out.println("Thank you for using the Admin Dashboard. Goodbye!");
                        break;
                    default:
                        System.out.println("Invalid option. Please choose between 1-10.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            } catch (Exception e) {
                System.out.println("An unexpected error occurred: " + e.getMessage());
            }
        }
        scanner.close();
    }

    private void viewAllVehicles() {
        try {
            if (vehicles.isEmpty()) {
                System.out.println("\nNo vehicles available.");
                return;
            }

            System.out.println("\n=== All Vehicles ===");
            for (int i = 0; i < vehicles.size(); i++) {
                Vehicle vehicle = vehicles.get(i);
                String status = vehicle.isAvailable() ? "Available" : "Not Available";
                System.out.printf("%d. %s (Status: %s)%n", i + 1, vehicle, status);
            }
        } catch (Exception e) {
            System.err.println("Error viewing vehicles: " + e.getMessage());
        }
    }

    private void addNewVehicle() {
        try {
            System.out.println("\n=== Add New Vehicle ===");
            System.out.println("Please follow the format instructions for each field.");
            
            String type = getValidVehicleType();
            String make = getValidMake();
            String model = getValidModel();
            double price = getValidPrice();
            String location = getValidLocation();

            Vehicle newVehicle = new Vehicle(type, make, model, price, location);
            
            vehicles.addFirst(newVehicle);

            System.out.println("\nVehicle added successfully!");
            System.out.println("Vehicle details: " + newVehicle);
        } catch (Exception e) {
            System.err.println("Error adding vehicle: " + e.getMessage());
        }
    }

    private String getValidVehicleType() {
        while (true) {
            try {
                System.out.println("\nVehicle Type Format:");
                System.out.println("- Must be one of: Car, SUV, Truck, Van, Motorcycle");
                System.out.println("- Case insensitive (e.g., 'car' or 'CAR' is same as 'Car')");
                System.out.print("Enter vehicle type: ");
                String type = scanner.nextLine().trim();
                
                // Convert to proper case (first letter capital, rest lowercase)
                if (type.matches("^(?i)(car|suv|truck|van|motorcycle)$")) {
                    // Convert to proper case
                    return type.substring(0, 1).toUpperCase() + type.substring(1).toLowerCase();
                }
                System.out.println("Invalid vehicle type. Please choose from the list.");
            } catch (Exception e) {
                System.out.println("An error occurred. Please try again.");
            }
        }
    }

    private String getValidMake() {
        while (true) {
            try {
                System.out.println("\nMake Format:");
                System.out.println("- Must contain only letters and spaces");
                System.out.println("- Cannot be empty");
                System.out.println("- No numbers or special characters allowed");
                System.out.print("Enter vehicle make: ");
                String make = scanner.nextLine().trim();
                
                if (make.isEmpty()) {
                    System.out.println("Error: Make is required and cannot be empty.");
                    continue;
                }
                
                if (!make.matches("^[a-zA-Z ]+$")) {
                    System.out.println("Error: Make must contain only letters and spaces.");
                    continue;
                }
                
                return make;
            } catch (Exception e) {
                System.out.println("An error occurred. Please try again.");
            }
        }
    }

    private String getValidModel() {
        while (true) {
            try {
                System.out.println("\nModel Format:");
                System.out.println("- Must contain only letters, numbers, and spaces");
                System.out.println("- Cannot be empty");
                System.out.println("- No special characters allowed");
                System.out.print("Enter vehicle model: ");
                String model = scanner.nextLine().trim();
                
                if (model.isEmpty()) {
                    System.out.println("Error: Model is required and cannot be empty.");
                    continue;
                }
                
                if (!model.matches("^[a-zA-Z0-9 ]+$")) {
                    System.out.println("Error: Model must contain only letters, numbers, and spaces.");
                    continue;
                }
                
                return model;
            } catch (Exception e) {
                System.out.println("An error occurred. Please try again.");
            }
        }
    }

    private double getValidPrice() {
        while (true) {
            try {
                System.out.println("\nPrice Format:");
                System.out.println("- Must be a positive number");
                System.out.println("- Can include up to 2 decimal places");
                System.out.print("Enter vehicle price: ");
                String input = scanner.nextLine().trim();
                double price = Double.parseDouble(input);
                
                if (price > 0) {
                    return price;
                }
                System.out.println("Price must be positive. Please try again.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid price format. Please enter a valid number.");
            } catch (Exception e) {
                System.out.println("An error occurred. Please try again.");
            }
        }
    }

    private String getValidLocation() {
        while (true) {
            try {
                System.out.println("\nLocation Format:");
                System.out.println("- Must contain only letters, numbers, and spaces");
                System.out.println("- Cannot be empty");
                System.out.print("Enter vehicle location: ");
                String location = scanner.nextLine().trim();
                
                if (!location.isEmpty()) {
                    return location;
                }
                System.out.println("Location cannot be empty.");
            } catch (Exception e) {
                System.out.println("An error occurred. Please try again.");
            }
        }
    }

    private void removeVehicle() {
        try {
            if (vehicles.isEmpty()) {
                System.out.println("\nNo vehicles in the system.");
                return;
            }

            System.out.println("\n=== Remove Vehicle ===");
            System.out.println("Select a vehicle to remove:");
            
            for (int i = 0; i < vehicles.size(); i++) {
                System.out.printf("%d. %s%n", i + 1, vehicles.get(i));
            }

            System.out.print("\nEnter the number of the vehicle to remove: ");
            int vehicleIndex = Integer.parseInt(scanner.nextLine().trim()) - 1;
            
            if (vehicleIndex < 0 || vehicleIndex >= vehicles.size()) {
                System.out.println("Invalid vehicle selection.");
                return;
            }

            Vehicle selectedVehicle = vehicles.get(vehicleIndex);
            if (vehicles.remove(selectedVehicle)) {
                System.out.println("\nVehicle removed successfully!");
            } else {
                System.out.println("\nFailed to remove vehicle.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number.");
        } catch (Exception e) {
            System.err.println("Error removing vehicle: " + e.getMessage());
        }
    }

    private void viewRecentRentals() {
        try {
            if (rentals.isEmpty()) {
                System.out.println("\nNo rentals found.");
                return;
            }

            System.out.println("\n=== Recent Rentals ===");
            int count = Math.min(5, rentals.size());
            for (int i = 0; i < count; i++) {
                Rental rental = rentals.get(rentals.size() - 1 - i);
                System.out.printf("%d. %s%n", i + 1, rental);
            }
        } catch (Exception e) {
            System.err.println("Error viewing recent rentals: " + e.getMessage());
        }
    }

    private void makeVehicleAvailable() {
        try {
            if (vehicles.isEmpty()) {
                System.out.println("\nNo vehicles in the system.");
                return;
            }

            System.out.println("\n=== Make Vehicle Available ===");
            System.out.println("Select a vehicle to make available:");
            
            List<Vehicle> unavailableVehicles = vehicles.toList().stream()
                .filter(v -> !v.isAvailable())
                .toList();

            if (unavailableVehicles.isEmpty()) {
                System.out.println("No unavailable vehicles found.");
                return;
            }

            for (int i = 0; i < unavailableVehicles.size(); i++) {
                System.out.printf("%d. %s%n", i + 1, unavailableVehicles.get(i));
            }

            System.out.print("\nEnter the number of the vehicle to make available: ");
            int vehicleIndex = Integer.parseInt(scanner.nextLine().trim()) - 1;
            
            if (vehicleIndex < 0 || vehicleIndex >= unavailableVehicles.size()) {
                System.out.println("Invalid vehicle selection.");
                return;
            }

            Vehicle selectedVehicle = unavailableVehicles.get(vehicleIndex);
            selectedVehicle.setAvailable(true);
            System.out.println("\nVehicle is now available for rent!");
            
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number.");
        } catch (Exception e) {
            System.err.println("Error making vehicle available: " + e.getMessage());
        }
    }

    private void viewFirstVehicle() {
        try {
            if (vehicles.isEmpty()) {
                System.out.println("\nNo vehicles in the system.");
                return;
            }

            Vehicle firstVehicle = vehicles.getFirst();
            System.out.println("\n=== First Vehicle in List ===");
            System.out.println(firstVehicle);
        } catch (Exception e) {
            System.err.println("Error viewing first vehicle: " + e.getMessage());
        }
    }

    private void viewLastVehicle() {
        try {
            if (vehicles.isEmpty()) {
                System.out.println("\nNo vehicles in the system.");
                return;
            }

            Vehicle lastVehicle = vehicles.getLast();
            System.out.println("\n=== Last Vehicle in List ===");
            System.out.println(lastVehicle);
        } catch (Exception e) {
            System.err.println("Error viewing last vehicle: " + e.getMessage());
        }
    }

    private void moveVehicleToFront() {
        try {
            if (vehicles.isEmpty()) {
                System.out.println("\nNo vehicles in the system.");
                return;
            }

            System.out.println("\n=== Move Vehicle to Front ===");
            System.out.println("Select a vehicle to move to front:");
            
            for (int i = 0; i < vehicles.size(); i++) {
                System.out.printf("%d. %s%n", i + 1, vehicles.get(i));
            }

            System.out.print("\nEnter the number of the vehicle to move: ");
            int vehicleIndex = Integer.parseInt(scanner.nextLine().trim()) - 1;
            
            if (vehicleIndex < 0 || vehicleIndex >= vehicles.size()) {
                System.out.println("Invalid vehicle selection.");
                return;
            }

            Vehicle selectedVehicle = vehicles.get(vehicleIndex);
            vehicles.remove(selectedVehicle);
            vehicles.addFirst(selectedVehicle);
            System.out.println("\nVehicle moved to front successfully!");
            
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number.");
        } catch (Exception e) {
            System.err.println("Error moving vehicle: " + e.getMessage());
        }
    }
} 