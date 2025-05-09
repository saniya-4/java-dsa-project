import java.util.Scanner;
import java.util.List;

public class RentalSystem {
    private Scanner scanner;
    private CustomLinkedList<Vehicle> vehicles;
    private CustomLinkedList<Rental> rentals;

    public RentalSystem(CustomLinkedList<Vehicle> vehicles, CustomLinkedList<Rental> rentals) {
        this.scanner = new Scanner(System.in);
        this.vehicles = vehicles;
        this.rentals = rentals;
    }

    public void startRentalProcess() {
        try {
            if (vehicles.isEmpty()) {
                System.out.println("\nNo vehicles available for rent.");
                return;
            }

            System.out.println("\n=== Process Rental ===");
            
            // Get customer details with validation
            String customerName = getValidCustomerName();
            String customerId = getValidCustomerId();
            String customerPhone = getValidPhoneNumber();

            // Show available vehicles
            System.out.println("\nAvailable Vehicles:");
            List<Vehicle> availableVehicles = vehicles.toList().stream()
                .filter(Vehicle::isAvailable)
                .toList();

            if (availableVehicles.isEmpty()) {
                System.out.println("No vehicles are currently available.");
                return;
            }

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
            
            // Get payment details
            String paymentMode = getValidPaymentMode();
            double amount = selectedVehicle.getPrice();

            // Create and add rental
            Rental rental = new Rental(customerName, customerId, customerPhone, selectedVehicle, paymentMode, amount);
            rentals.add(rental);
            selectedVehicle.setAvailable(false);

            // Display rental receipt
            System.out.println("\n=== Rental Receipt ===");
            System.out.println(rental);
            System.out.println("\nRental processed successfully!");
            
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number.");
        } catch (Exception e) {
            System.err.println("Error processing rental: " + e.getMessage());
        }
    }

    private String getValidCustomerName() {
        while (true) {
            try {
                System.out.println("\nCustomer Name Format:");
                System.out.println("- Must contain only letters and spaces");
                System.out.println("- First letter of each word must be capital");
                System.out.println("- No numbers or special characters allowed");
                System.out.println("- Cannot be empty");
                System.out.print("Enter customer name: ");
                String name = scanner.nextLine().trim();
                
                if (name.isEmpty()) {
                    System.out.println("Error: Name is required and cannot be empty.");
                    continue;
                }
                
                // Check if name contains only letters and spaces
                if (!name.matches("^[a-zA-Z ]+$")) {
                    System.out.println("Error: Name must contain only letters and spaces.");
                    continue;
                }
                
                // Convert to proper case (first letter of each word capital)
                String[] words = name.split(" ");
                StringBuilder properName = new StringBuilder();
                for (String word : words) {
                    if (!word.isEmpty()) {
                        properName.append(word.substring(0, 1).toUpperCase())
                                .append(word.substring(1).toLowerCase())
                                .append(" ");
                    }
                }
                
                return properName.toString().trim();
            } catch (Exception e) {
                System.out.println("An error occurred. Please try again.");
            }
        }
    }

    private String getValidCustomerId() {
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

    private String getValidPhoneNumber() {
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

    private String getValidPaymentMode() {
        while (true) {
            try {
                System.out.println("\nPayment Mode Options:");
                System.out.println("1. Credit Card");
                System.out.println("2. Debit Card");
                System.out.println("3. Cash");
                System.out.println("4. UPI");
                System.out.print("Select payment mode (1-4): ");
                
                String input = scanner.nextLine().trim();
                int choice = Integer.parseInt(input);
                
                switch (choice) {
                    case 1: return "Credit Card";
                    case 2: return "Debit Card";
                    case 3: return "Cash";
                    case 4: return "UPI";
                    default:
                        System.out.println("Invalid choice. Please select 1-4.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            } catch (Exception e) {
                System.out.println("An error occurred. Please try again.");
            }
        }
    }
} 