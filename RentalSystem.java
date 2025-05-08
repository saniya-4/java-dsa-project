import java.util.Scanner;
import java.util.List;

public class RentalSystem {
    private Scanner scanner;
    private VehicleDatabase vehicleDatabase;

    public RentalSystem() {
        this.scanner = new Scanner(System.in);
        this.vehicleDatabase = VehicleDatabase.getInstance();
    }

    public void startRentalProcess() {
        try {
            System.out.println("\n=== Process Rental ===");
            
            // Get customer details
            CustomerDetails customer = getCustomerDetails();
            
            // Show available vehicles
            List<Vehicle> availableVehicles = vehicleDatabase.getAllVehicles();
            if (availableVehicles.isEmpty()) {
                System.out.println("No vehicles available for rent.");
                return;
            }

            System.out.println("\nAvailable Vehicles:");
            for (int i = 0; i < availableVehicles.size(); i++) {
                System.out.printf("%d. %s%n", i + 1, availableVehicles.get(i));
            }

            // Get vehicle selection
            System.out.print("\nEnter the number of the vehicle to rent: ");
            int vehicleIndex = Integer.parseInt(scanner.nextLine().trim()) - 1;
            
            if (vehicleIndex < 0 || vehicleIndex >= availableVehicles.size()) {
                System.out.println("Invalid vehicle selection.");
                return;
            }

            Vehicle selectedVehicle = availableVehicles.get(vehicleIndex);
            
            // Create and process rental
            Rental rental = new Rental(customer, selectedVehicle);
            boolean success = vehicleDatabase.processRental(rental);
            
            if (success) {
                System.out.println("\nRental processed successfully!");
                System.out.println("Rental details: " + rental);
            } else {
                System.out.println("\nFailed to process rental. Please try again.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number.");
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    private CustomerDetails getCustomerDetails() {
        System.out.println("\nPlease enter customer details:");
        
        String name = getValidName();
        String idNumber = getValidIdNumber();
        String phone = getValidPhone();
        
        return new CustomerDetails(name, idNumber, phone);
    }

    private String getValidName() {
        while (true) {
            try {
                System.out.println("\nName Format:");
                System.out.println("- Must contain only letters and spaces");
                System.out.println("- Cannot be empty");
                System.out.print("Enter customer name: ");
                String name = scanner.nextLine().trim();
                
                if (name.matches("^[a-zA-Z ]+$") && !name.isEmpty()) {
                    return name;
                }
                System.out.println("Invalid name. Please use only letters and spaces.");
            } catch (Exception e) {
                System.out.println("An error occurred. Please try again.");
            }
        }
    }

    private String getValidIdNumber() {
        while (true) {
            try {
                System.out.println("\nID Number Format:");
                System.out.println("- Must be exactly 5 digits");
                System.out.println("- Cannot be empty");
                System.out.print("Enter customer ID number: ");
                String idNumber = scanner.nextLine().trim();
                
                if (idNumber.matches("^\\d{5}$")) {
                    return idNumber;
                }
                System.out.println("Invalid ID number. Please enter exactly 5 digits.");
            } catch (Exception e) {
                System.out.println("An error occurred. Please try again.");
            }
        }
    }

    private String getValidPhone() {
        while (true) {
            try {
                System.out.println("\nPhone Number Format:");
                System.out.println("- Must be exactly 10 digits");
                System.out.println("- Cannot be empty");
                System.out.print("Enter customer phone number: ");
                String phone = scanner.nextLine().trim();
                
                if (phone.matches("^\\d{10}$")) {
                    return phone;
                }
                System.out.println("Invalid phone number. Please enter exactly 10 digits.");
            } catch (Exception e) {
                System.out.println("An error occurred. Please try again.");
            }
        }
    }
} 