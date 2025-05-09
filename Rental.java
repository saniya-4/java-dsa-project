import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Rental {
    private String customerName;
    private String customerId;
    private String customerPhone;
    private Vehicle vehicle;
    private String rentalId;
    private String rentalDate;
    private String paymentMode;
    private double amount;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public Rental(String customerName, String customerId, String customerPhone, Vehicle vehicle, String paymentMode, double amount) {
        this.customerName = customerName;
        this.customerId = customerId;
        this.customerPhone = customerPhone;
        this.vehicle = vehicle;
        this.rentalId = generateRentalId();
        this.rentalDate = java.time.LocalDate.now().toString();
        this.paymentMode = paymentMode;
        this.amount = amount;
    }

    private String generateRentalId() {
        return "RENT" + System.currentTimeMillis();
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public String getRentalId() {
        return rentalId;
    }

    public String getRentalDate() {
        return rentalDate;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public double getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return String.format("Customer: %s%nID: %s%nPhone: %s%nVehicle: %s%nPayment Mode: %s%nAmount: $%.2f",
            customerName, customerId, customerPhone, vehicle, paymentMode, amount);
    }
} 