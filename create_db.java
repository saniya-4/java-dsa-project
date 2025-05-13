import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class create_db {
    public static void main(String[] args) {
        try {
            String url = "jdbc:mysql://localhost:3306/"; // <--- connect to server only
            String database = "vehiclerentaldb";         // desired DB name
            String user = "root";
            String password = "Root";

            String sql = "CREATE DATABASE " + database;

            // Establish connection
            Connection conn = DriverManager.getConnection(url, user, password);
            Statement stmt = conn.createStatement();

            // Execute SQL
            stmt.executeUpdate(sql);
            JOptionPane.showMessageDialog(null, "Database created successfully!");

            // Close connection
            stmt.close();
            conn.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }
}
   