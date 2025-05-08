import java.util.Scanner;

public class AdminLogin {
    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "admin123";
    private Scanner scanner;

    public AdminLogin() {
        this.scanner = new Scanner(System.in);
    }

    public void login() {
        System.out.println("\n=== Admin Login ===");
        
        System.out.print("Username: ");
        String username = scanner.nextLine().trim();
        
        System.out.print("Password: ");
        String password = scanner.nextLine().trim();
        
        if (username.equals(ADMIN_USERNAME) && password.equals(ADMIN_PASSWORD)) {
            System.out.println("Login successful!");
            // Create and show admin dashboard
            AdminDashboard dashboard = new AdminDashboard();
            dashboard.showDashboard();
        } else {
            System.out.println("Invalid username or password!");
        }
    }
} 