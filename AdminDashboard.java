import java.sql.*;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class AdminDashboard {
    private Scanner scanner;
    private RentalSystem rentalSystem;

    public AdminDashboard() {
        this.scanner = new Scanner(System.in);
        this.rentalSystem = new RentalSystem();
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
                System.out.println("10. Update vehicle");
                System.out.println("11. Exit");
                System.out.print("Please choose an option (1-11): ");

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
                        updateVehicle();
                        break;
                    case 11:
                        exit = true;
                        System.out.println("Thank you for using the Admin Dashboard. Goodbye!");
                        break;
                    default:
                        System.out.println("Invalid option. Please choose between 1-11.");
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
            List<Vehicle> vehicles = Vehicle.getAllVehicles();
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
            
            String vehicleNumber = getValidVehicleNumber();
            String vehicleName = getValidVehicleName();
            String vehicleType = getValidVehicleType();
            int seatingCapacity = getValidSeatingCapacity();
            double rentPerDay = getValidPrice();

            Vehicle newVehicle = new Vehicle(vehicleNumber, vehicleName, vehicleType, seatingCapacity, rentPerDay);
            Vehicle.addVehicle(newVehicle);

            System.out.println("\nVehicle added successfully!");
            System.out.println("Vehicle details: " + newVehicle);
        } catch (Exception e) {
            System.err.println("Error adding vehicle: " + e.getMessage());
        }
    }

    private String getValidVehicleNumber() {
        while (true) {
            try {
                System.out.println("\nVehicle Number Format:");
                System.out.println("- Must be unique");
                System.out.println("- Can contain letters, numbers, and spaces");
                System.out.println("- Cannot be empty");
                System.out.print("Enter vehicle number: ");
                String number = scanner.nextLine().trim();
                
                if (number.isEmpty()) {
                    System.out.println("Error: Vehicle number is required and cannot be empty.");
                    continue;
                }
                
                return number;
            } catch (Exception e) {
                System.out.println("An error occurred. Please try again.");
            }
        }
    }

    private String getValidVehicleName() {
        while (true) {
            try {
                System.out.println("\nVehicle Name Format:");
                System.out.println("- Must contain only letters, numbers, and spaces");
                System.out.println("- Cannot be empty");
                System.out.print("Enter vehicle name: ");
                String name = scanner.nextLine().trim();
                
                if (name.isEmpty()) {
                    System.out.println("Error: Vehicle name is required and cannot be empty.");
                    continue;
                }
                
                return name;
            } catch (Exception e) {
                System.out.println("An error occurred. Please try again.");
            }
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
                
                if (type.matches("^(?i)(car|suv|truck|van|motorcycle)$")) {
                    return type.substring(0, 1).toUpperCase() + type.substring(1).toLowerCase();
                }
                System.out.println("Invalid vehicle type. Please choose from the list.");
            } catch (Exception e) {
                System.out.println("An error occurred. Please try again.");
            }
        }
    }

    private int getValidSeatingCapacity() {
        while (true) {
            try {
                System.out.println("\nSeating Capacity Format:");
                System.out.println("- Must be a positive number");
                System.out.println("- Maximum 50 seats");
                System.out.print("Enter seating capacity: ");
                String input = scanner.nextLine().trim();
                int capacity = Integer.parseInt(input);
                
                if (capacity > 0 && capacity <= 50) {
                    return capacity;
                }
                System.out.println("Seating capacity must be between 1 and 50. Please try again.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid number format. Please enter a valid number.");
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
                System.out.print("Enter vehicle price per day: ");
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

    private void removeVehicle() {
        try {
            List<Vehicle> vehicles = Vehicle.getAllVehicles();
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
            
            // Delete from database
            String sql = "DELETE FROM vehicles WHERE id = ?";
            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, selectedVehicle.getId());
                pstmt.executeUpdate();
                System.out.println("\nVehicle removed successfully!");
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number.");
        } catch (Exception e) {
            System.err.println("Error removing vehicle: " + e.getMessage());
        }
    }

    private void viewRecentRentals() {
        try {
            String sql = "SELECT r.*, v.vehicle_name, c.name as customer_name " +
                        "FROM rentals r " +
                        "JOIN vehicles v ON r.vehicle_id = v.id " +
                        "JOIN customers c ON r.customer_id = c.id " +
                        "ORDER BY r.rent_date DESC LIMIT 5";
            
            try (Connection conn = DatabaseConnection.getConnection();
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {
                
                if (!rs.next()) {
                    System.out.println("\nNo rentals found.");
                    return;
                }

                System.out.println("\n=== Recent Rentals ===");
                int count = 1;
                do {
                    System.out.printf("%d. Customer: %s, Vehicle: %s, Date: %s, Amount: $%.2f%n",
                        count++,
                        rs.getString("customer_name"),
                        rs.getString("vehicle_name"),
                        rs.getDate("rent_date"),
                        rs.getDouble("total_amount")
                    );
                } while (rs.next() && count <= 5);
            }
        } catch (Exception e) {
            System.err.println("Error viewing recent rentals: " + e.getMessage());
        }
    }

    private void makeVehicleAvailable() {
        try {
            List<Vehicle> vehicles = Vehicle.getAllVehicles();
            List<Vehicle> unavailableVehicles = vehicles.stream()
                .filter(v -> !v.isAvailable())
                .toList();

            if (unavailableVehicles.isEmpty()) {
                System.out.println("\nNo unavailable vehicles found.");
                return;
            }

            System.out.println("\n=== Make Vehicle Available ===");
            System.out.println("Select a vehicle to make available:");
            
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
            
            // Update in database
            String sql = "UPDATE vehicles SET is_available = true WHERE id = ?";
            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, selectedVehicle.getId());
                pstmt.executeUpdate();
                System.out.println("\nVehicle is now available for rent!");
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number.");
        } catch (Exception e) {
            System.err.println("Error making vehicle available: " + e.getMessage());
        }
    }

    private void viewFirstVehicle() {
        try {
            String sql = "SELECT * FROM vehicles ORDER BY id ASC LIMIT 1";
            try (Connection conn = DatabaseConnection.getConnection();
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {
                
                if (rs.next()) {
                    Vehicle vehicle = new Vehicle(
                        rs.getString("vehicle_number"),
                        rs.getString("vehicle_name"),
                        rs.getString("vehicle_type"),
                        rs.getInt("seating_capacity"),
                        rs.getDouble("rent_per_day")
                    );
                    vehicle.setId(rs.getInt("id"));
                    vehicle.setAvailable(rs.getBoolean("is_available"));
                    
                    System.out.println("\n=== First Vehicle in Database ===");
                    System.out.println(vehicle);
                } else {
                    System.out.println("\nNo vehicles in the system.");
                }
            }
        } catch (Exception e) {
            System.err.println("Error viewing first vehicle: " + e.getMessage());
        }
    }

    private void viewLastVehicle() {
        try {
            String sql = "SELECT * FROM vehicles ORDER BY id DESC LIMIT 1";
            try (Connection conn = DatabaseConnection.getConnection();
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {
                
                if (rs.next()) {
                    Vehicle vehicle = new Vehicle(
                        rs.getString("vehicle_number"),
                        rs.getString("vehicle_name"),
                        rs.getString("vehicle_type"),
                        rs.getInt("seating_capacity"),
                        rs.getDouble("rent_per_day")
                    );
                    vehicle.setId(rs.getInt("id"));
                    vehicle.setAvailable(rs.getBoolean("is_available"));
                    
                    System.out.println("\n=== Last Vehicle in Database ===");
                    System.out.println(vehicle);
                } else {
                    System.out.println("\nNo vehicles in the system.");
                }
            }
        } catch (Exception e) {
            System.err.println("Error viewing last vehicle: " + e.getMessage());
        }
    }

    private void moveVehicleToFront() {
        try {
            List<Vehicle> vehicles = Vehicle.getAllVehicles();
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
            
            // Update the vehicle's ID to be the minimum ID in the database
            String sql = "UPDATE vehicles SET id = (SELECT MIN(id) - 1 FROM vehicles) WHERE id = ?";
            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, selectedVehicle.getId());
                pstmt.executeUpdate();
                System.out.println("\nVehicle moved to front successfully!");
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number.");
        } catch (Exception e) {
            System.err.println("Error moving vehicle: " + e.getMessage());
        }
    }

    private void updateVehicle() {
        try {
            List<Vehicle> vehicles = Vehicle.getAllVehicles();
            if (vehicles.isEmpty()) {
                System.out.println("\nNo vehicles available to update.");
                return;
            }

            System.out.println("\n=== Update Vehicle ===");
            System.out.println("Select a vehicle to update:");
            
            for (int i = 0; i < vehicles.size(); i++) {
                Vehicle vehicle = vehicles.get(i);
                String status = vehicle.isAvailable() ? "Available" : "Not Available";
                System.out.printf("%d. %s (Status: %s)%n", i + 1, vehicle, status);
            }

            System.out.print("\nEnter the number of the vehicle to update: ");
            int vehicleIndex = Integer.parseInt(scanner.nextLine().trim()) - 1;
            
            if (vehicleIndex < 0 || vehicleIndex >= vehicles.size()) {
                System.out.println("Invalid vehicle selection.");
                return;
            }

            Vehicle selectedVehicle = vehicles.get(vehicleIndex);
            System.out.println("\nCurrent vehicle details: " + selectedVehicle);
            System.out.println("\nEnter new details (press Enter to keep current value):");

            // Get updated vehicle details
            System.out.print("Vehicle Name [" + selectedVehicle.getVehicleName() + "]: ");
            String newName = scanner.nextLine().trim();
            if (newName.isEmpty()) {
                newName = selectedVehicle.getVehicleName();
            }

            System.out.print("Vehicle Type [" + selectedVehicle.getVehicleType() + "]: ");
            String newType = scanner.nextLine().trim();
            if (newType.isEmpty()) {
                newType = selectedVehicle.getVehicleType();
            } else {
                while (!newType.matches("^(?i)(car|suv|truck|van|motorcycle)$")) {
                    System.out.println("Invalid vehicle type. Please choose from: Car, SUV, Truck, Van, Motorcycle");
                    System.out.print("Vehicle Type [" + selectedVehicle.getVehicleType() + "]: ");
                    newType = scanner.nextLine().trim();
                    if (newType.isEmpty()) {
                        newType = selectedVehicle.getVehicleType();
                        break;
                    }
                }
                newType = newType.substring(0, 1).toUpperCase() + newType.substring(1).toLowerCase();
            }

            System.out.print("Seating Capacity [" + selectedVehicle.getSeatingCapacity() + "]: ");
            String seatingInput = scanner.nextLine().trim();
            int newSeating = selectedVehicle.getSeatingCapacity();
            if (!seatingInput.isEmpty()) {
                try {
                    int capacity = Integer.parseInt(seatingInput);
                    if (capacity > 0 && capacity <= 50) {
                        newSeating = capacity;
                    } else {
                        System.out.println("Invalid seating capacity. Keeping current value.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid number format. Keeping current value.");
                }
            }

            System.out.print("Rent per Day [" + selectedVehicle.getRentPerDay() + "]: ");
            String rentInput = scanner.nextLine().trim();
            double newRent = selectedVehicle.getRentPerDay();
            if (!rentInput.isEmpty()) {
                try {
                    double rent = Double.parseDouble(rentInput);
                    if (rent > 0) {
                        newRent = rent;
                    } else {
                        System.out.println("Invalid rent amount. Keeping current value.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid number format. Keeping current value.");
                }
            }

            // Update the vehicle
            selectedVehicle.setVehicleName(newName);
            selectedVehicle.setVehicleType(newType);
            selectedVehicle.setSeatingCapacity(newSeating);
            selectedVehicle.setRentPerDay(newRent);
            
            // Save the updated vehicle to the database
            Vehicle.updateVehicle(selectedVehicle);
            
            System.out.println("\nVehicle updated successfully!");
            System.out.println("Updated vehicle details: " + selectedVehicle);
            
        } catch (Exception e) {
            System.err.println("Error updating vehicle: " + e.getMessage());
        }
    }
} 