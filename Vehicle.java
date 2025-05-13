import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Vehicle {
    private int id;
    private String vehicleNumber;
    private String vehicleName;
    private String vehicleType;
    private int seatingCapacity;
    private double rentPerDay;
    private boolean isAvailable;

    public Vehicle(String vehicleNumber, String vehicleName, String vehicleType, int seatingCapacity, double rentPerDay) {
        this.vehicleNumber = vehicleNumber;
        this.vehicleName = vehicleName;
        this.vehicleType = vehicleType;
        this.seatingCapacity = seatingCapacity;
        this.rentPerDay = rentPerDay;
        this.isAvailable = true;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getVehicleNumber() { return vehicleNumber; }
    public void setVehicleNumber(String vehicleNumber) { this.vehicleNumber = vehicleNumber; }
    public String getVehicleName() { return vehicleName; }
    public void setVehicleName(String vehicleName) { this.vehicleName = vehicleName; }
    public String getVehicleType() { return vehicleType; }
    public void setVehicleType(String vehicleType) { this.vehicleType = vehicleType; }
    public int getSeatingCapacity() { return seatingCapacity; }
    public void setSeatingCapacity(int seatingCapacity) { this.seatingCapacity = seatingCapacity; }
    public double getRentPerDay() { return rentPerDay; }
    public void setRentPerDay(double rentPerDay) { this.rentPerDay = rentPerDay; }
    public boolean isAvailable() { return isAvailable; }
    public void setAvailable(boolean available) { isAvailable = available; }

    // Database Operations
    public static void addVehicle(Vehicle vehicle) {
        String sql = "INSERT INTO vehicles (vehicle_number, vehicle_name, vehicle_type, seating_capacity, rent_per_day, is_available) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, vehicle.getVehicleNumber());
            pstmt.setString(2, vehicle.getVehicleName());
            pstmt.setString(3, vehicle.getVehicleType());
            pstmt.setInt(4, vehicle.getSeatingCapacity());
            pstmt.setDouble(5, vehicle.getRentPerDay());
            pstmt.setBoolean(6, vehicle.isAvailable());
            
            pstmt.executeUpdate();
            System.out.println("Vehicle added successfully!");
            
        } catch (SQLException e) {
            System.err.println("Error adding vehicle: " + e.getMessage());
            e.printStackTrace();
        }
    }
    

    public static List<Vehicle> getAllVehicles() {
        List<Vehicle> vehicles = new ArrayList<>();
        String sql = "SELECT * FROM vehicles";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Vehicle vehicle = new Vehicle(
                    rs.getString("vehicle_number"),
                    rs.getString("vehicle_name"),
                    rs.getString("vehicle_type"),
                    rs.getInt("seating_capacity"),
                    rs.getDouble("rent_per_day")
                );
                vehicle.setId(rs.getInt("id"));
                vehicle.setAvailable(rs.getBoolean("is_available"));
                vehicles.add(vehicle);
            }
            
        } catch (SQLException e) {
            System.err.println("Error retrieving vehicles: " + e.getMessage());
            e.printStackTrace();
        }
        
        return vehicles;
    }

    public static Vehicle getVehicleById(int id) {
        String sql = "SELECT * FROM vehicles WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            
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
                return vehicle;
            }
            
        } catch (SQLException e) {
            System.err.println("Error retrieving vehicle: " + e.getMessage());
            e.printStackTrace();
        }
        
        return null;
    }

    @Override
    public String toString() {
        return String.format("%s %s (ID: %d) - $%.2f/day - Seats: %d - Available: %s",
            vehicleType, vehicleName, id, rentPerDay, seatingCapacity, isAvailable ? "Yes" : "No");
    }

    public static void updateVehicle(Vehicle vehicle) {
        String sql = "UPDATE vehicles SET vehicle_name = ?, vehicle_type = ?, seating_capacity = ?, " +
                    "rent_per_day = ?, is_available = ? WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, vehicle.getVehicleName());
            pstmt.setString(2, vehicle.getVehicleType());
            pstmt.setInt(3, vehicle.getSeatingCapacity());
            pstmt.setDouble(4, vehicle.getRentPerDay());
            pstmt.setBoolean(5, vehicle.isAvailable());
            pstmt.setInt(6, vehicle.getId());
            
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Vehicle updated successfully!");
            } else {
                System.out.println("No vehicle was updated. Vehicle ID may not exist.");
            }
            
        } catch (SQLException e) {
            System.err.println("Error updating vehicle: " + e.getMessage());
            e.printStackTrace();
        }
    }
} 