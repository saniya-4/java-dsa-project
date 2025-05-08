public class Main {
    public static void main(String[] args) {
        try {
            System.out.println("=== Vehicle Rental System ===");
            System.out.println("Starting the application...\n");

            // Create and show the admin dashboard
            AdminDashboard dashboard = new AdminDashboard();
            dashboard.showDashboard();

        } catch (Exception e) {
            System.err.println("An unexpected error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }
} 