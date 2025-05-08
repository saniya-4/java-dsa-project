import java.util.Scanner;
import java.util.List;

public class AdminDashboard {
    private Scanner scanner;
    private VehicleDatabase vehicleDatabase;
    private RentalSystem rentalSystem;

    public AdminDashboard() {
        this.scanner = new Scanner(System.in);
        this.vehicleDatabase = VehicleDatabase.getInstance();
        this.rentalSystem = new RentalSystem();
    }

    public void showDashboard() {
        boolean exit = false;
        
        while (!exit) {
            try {
                System.out.println("\n=== Admin Dashboard ===");
                System.out.println("1. View all available vehicles");
                System.out.println("2. Add a new vehicle");
                System.out.println("3. Process a rental");
                System.out.println("4. Exit");
                System.out.print("Please choose an option (1-4): ");

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
                        exit = true;
                        System.out.println("Thank you for using the Admin Dashboard. Goodbye!");
                        break;
                    default:
                        System.out.println("Invalid option. Please choose between 1-4.");
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
            List<Vehicle> vehicles = vehicleDatabase.getAllVehicles();
            if (vehicles.isEmpty()) {
                System.out.println("\nNo vehicles available in the database.");
                return;
            }

            System.out.println("\n=== Available Vehicles ===");
            for (int i = 0; i < vehicles.size(); i++) {
                System.out.printf("%d. %s%n", i + 1, vehicles.get(i));
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
            boolean success = vehicleDatabase.addVehicle(newVehicle);

            if (success) {
                System.out.println("\nVehicle added successfully!");
                System.out.println("Vehicle details: " + newVehicle);
            } else {
                System.out.println("\nFailed to add vehicle. Please try again.");
            }
        } catch (Exception e) {
            System.err.println("Error adding vehicle: " + e.getMessage());
        }
    }

    private String getValidVehicleType() {
        while (true) {
            try {
                System.out.println("\nVehicle Type Format:");
                System.out.println("- Must be one of: Car, SUV, Truck, Van, Motorcycle");
                System.out.print("Enter vehicle type: ");
                String type = scanner.nextLine().trim();
                
                if (type.matches("^(Car|SUV|Truck|Van|Motorcycle)$")) {
                    return type;
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
                System.out.print("Enter vehicle make: ");
                String make = scanner.nextLine().trim();
                
                if (make.matches("^[a-zA-Z ]+$") && !make.isEmpty()) {
                    return make;
                }
                System.out.println("Invalid make. Please use only letters and spaces.");
            } catch (Exception e) {
                System.out.println("An error occurred. Please try again.");
            }
        }
    }

    private String getValidModel() {
        while (true) {
            try {
                System.out.println("\nModel Format:");
                System.out.println("- Can contain letters, numbers, and spaces");
                System.out.println("- Cannot be empty");
                System.out.print("Enter vehicle model: ");
                String model = scanner.nextLine().trim();
                
                if (!model.isEmpty()) {
                    return model;
                }
                System.out.println("Model cannot be empty.");
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
} 