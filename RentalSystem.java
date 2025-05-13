import java.sql.*;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class RentalSystem {
    private Scanner scanner;

    public RentalSystem() {
        this.scanner = new Scanner(System.in);
    }

    public void startRentalProcess() {
        try {
            List<Vehicle> availableVehicles = Vehicle.getAllVehicles().stream()
                .filter(Vehicle::isAvailable)
                .toList();

            if (availableVehicles.isEmpty()) {
                System.out.println("\nNo vehicles are currently available for rent.");
                return;
            }

            // Show available vehicles
            System.out.println("\nAvailable Vehicles:");
            for (int i = 0; i < availableVehicles.size(); i++) {
                System.out.printf("%d. %s%n", i + 1, availableVehicles.get(i));
            }

            // Get vehicle selection
            System.out.print("\nEnter the number of the vehicle you want to rent: ");
            int vehicleIndex = Integer.parseInt(scanner.nextLine().trim()) - 1;
            
            if (vehicleIndex < 0 || vehicleIndex >= availableVehicles.size()) {
                System.out.println("Invalid vehicle selection.");
                return;
            }

            Vehicle selectedVehicle = availableVehicles.get(vehicleIndex);

            // Get customer details
            String customerName = getValidCustomerName();
            String customerPhone = getValidCustomerPhone();
            String customerEmail = getValidCustomerEmail();
            String customerAddress = getValidCustomerAddress();
            String licenseNumber = getValidLicenseNumber();

            // Get rental details
            String paymentMode = getValidPaymentMode();
            double amount = selectedVehicle.getRentPerDay();

            // Create rental record in database
            String sql = "INSERT INTO rentals (vehicle_id, customer_id, rent_date, return_date, total_amount, status) " +
                        "VALUES (?, ?, CURDATE(), DATE_ADD(CURDATE(), INTERVAL 1 DAY), ?, 'ACTIVE')";
            
            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                
                // First, insert customer
                String customerSql = "INSERT INTO customers (name, phone, email, address, license_number) VALUES (?, ?, ?, ?, ?)";
                int customerId;
                try (PreparedStatement customerStmt = conn.prepareStatement(customerSql, Statement.RETURN_GENERATED_KEYS)) {
                    customerStmt.setString(1, customerName);
                    customerStmt.setString(2, customerPhone);
                    customerStmt.setString(3, customerEmail);
                    customerStmt.setString(4, customerAddress);
                    customerStmt.setString(5, licenseNumber);
                    customerStmt.executeUpdate();
                    
                    try (ResultSet rs = customerStmt.getGeneratedKeys()) {
                        if (rs.next()) {
                            customerId = rs.getInt(1);
                        } else {
                            throw new SQLException("Failed to get customer ID");
                        }
                    }
                }

                // Then, insert rental
                pstmt.setInt(1, selectedVehicle.getId());
                pstmt.setInt(2, customerId);
                pstmt.setDouble(3, amount);
                pstmt.executeUpdate();

                // Update vehicle availability
                String updateVehicleSql = "UPDATE vehicles SET is_available = false WHERE id = ?";
                try (PreparedStatement updateStmt = conn.prepareStatement(updateVehicleSql)) {
                    updateStmt.setInt(1, selectedVehicle.getId());
                    updateStmt.executeUpdate();
                }

                System.out.println("\nRental processed successfully!");
                System.out.println("Rental details:");
                System.out.println("Customer: " + customerName);
                System.out.println("Vehicle: " + selectedVehicle.getVehicleName());
                System.out.println("Amount: $" + amount);
                System.out.println("Payment Mode: " + paymentMode);
                
            } catch (SQLException e) {
                System.err.println("Error processing rental: " + e.getMessage());
                e.printStackTrace();
            }
            
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
                
                if (!name.matches("^[a-zA-Z ]+$")) {
                    System.out.println("Error: Name must contain only letters and spaces.");
                    continue;
                }
                
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

    private String getValidCustomerPhone() {
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

    private String getValidCustomerEmail() {
        while (true) {
            try {
                System.out.println("\nEmail Format:");
                System.out.println("- Must be a valid email address");
                System.out.println("- Cannot be empty");
                System.out.print("Enter customer email: ");
                String email = scanner.nextLine().trim();
                
                if (email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
                    return email;
                }
                System.out.println("Invalid email format. Please enter a valid email address.");
            } catch (Exception e) {
                System.out.println("An error occurred. Please try again.");
            }
        }
    }

    private String getValidCustomerAddress() {
        while (true) {
            try {
                System.out.println("\nAddress Format:");
                System.out.println("- Can contain letters, numbers, spaces, and common punctuation");
                System.out.println("- Cannot be empty");
                System.out.print("Enter customer address: ");
                String address = scanner.nextLine().trim();
                
                if (!address.isEmpty()) {
                    return address;
                }
                System.out.println("Address cannot be empty.");
            } catch (Exception e) {
                System.out.println("An error occurred. Please try again.");
            }
        }
    }

    private String getValidLicenseNumber() {
        while (true) {
            try {
                System.out.println("\nLicense Number Format:");
                System.out.println("- Must be unique");
                System.out.println("- Can contain letters, numbers, and spaces");
                System.out.println("- Cannot be empty");
                System.out.print("Enter license number: ");
                String license = scanner.nextLine().trim();
                
                if (!license.isEmpty()) {
                    return license;
                }
                System.out.println("License number cannot be empty.");
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