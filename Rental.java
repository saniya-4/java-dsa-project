import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Rental {
    private CustomerDetails customer;
    private Vehicle vehicle;
    private String rentalId;
    private String rentalDate;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public Rental(CustomerDetails customer, Vehicle vehicle) {
        this.customer = customer;
        this.vehicle = vehicle;
        this.rentalId = generateRentalId();
        this.rentalDate = java.time.LocalDate.now().toString();
    }

    private String generateRentalId() {
        return "RENT" + System.currentTimeMillis();
    }

    public CustomerDetails getCustomer() {
        return customer;
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

    @Override
    public String toString() {
        return String.format("Rental ID: %s%nDate: %s%nCustomer: %s%nVehicle: %s",
            rentalId, rentalDate, customer, vehicle);
    }
} 