import java.sql.Connection;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class create_tables {
    public static void main(String[] args) {
        try {
            Connection conn = DatabaseConnection.getConnection();
            Statement stmt = conn.createStatement();

            // Create vehicles table
            String createVehiclesTable = "CREATE TABLE IF NOT EXISTS vehicles (" +
                "id INT PRIMARY KEY AUTO_INCREMENT," +
                "vehicle_number VARCHAR(20) UNIQUE," +
                "vehicle_name VARCHAR(100)," +
                "vehicle_type VARCHAR(50)," +
                "seating_capacity INT," +
                "rent_per_day DECIMAL(10,2)," +
                "is_available BOOLEAN DEFAULT true" +
                ")";
            
            // Create customers table
            String createCustomersTable = "CREATE TABLE IF NOT EXISTS customers (" +
                "id INT PRIMARY KEY AUTO_INCREMENT," +
                "name VARCHAR(100)," +
                "phone VARCHAR(20)," +
                "email VARCHAR(100)," +
                "address TEXT," +
                "license_number VARCHAR(50) UNIQUE" +
                ")";
            
            // Create rentals table
            String createRentalsTable = "CREATE TABLE IF NOT EXISTS rentals (" +
                "id INT PRIMARY KEY AUTO_INCREMENT," +
                "vehicle_id INT," +
                "customer_id INT," +
                "rent_date DATE," +
                "return_date DATE," +
                "total_amount DECIMAL(10,2)," +
                "status VARCHAR(20)," +
                "FOREIGN KEY (vehicle_id) REFERENCES vehicles(id)," +
                "FOREIGN KEY (customer_id) REFERENCES customers(id)" +
                ")";

            // Execute the SQL statements
            stmt.executeUpdate(createVehiclesTable);
            stmt.executeUpdate(createCustomersTable);
            stmt.executeUpdate(createRentalsTable);

            JOptionPane.showMessageDialog(null, "Tables created successfully!");

            // Close resources
            stmt.close();
            DatabaseConnection.closeConnection();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error creating tables: " + e.getMessage());
            e.printStackTrace();
        }
    }
} 