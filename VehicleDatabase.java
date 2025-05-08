import java.util.ArrayList;
import java.util.List;

public class VehicleDatabase {
    private static VehicleDatabase instance;
    private List<Vehicle> vehicles;
    private List<Rental> rentals;

    private VehicleDatabase() {
        this.vehicles = new ArrayList<>();
        this.rentals = new ArrayList<>();
    }

    public static VehicleDatabase getInstance() {
        if (instance == null) {
            instance = new VehicleDatabase();
        }
        return instance;
    }

    public boolean addVehicle(Vehicle vehicle) {
        try {
            return vehicles.add(vehicle);
        } catch (Exception e) {
            System.err.println("Error adding vehicle: " + e.getMessage());
            return false;
        }
    }

    public List<Vehicle> getAllVehicles() {
        return new ArrayList<>(vehicles);
    }

    public boolean processRental(Rental rental) {
        try {
            Vehicle vehicle = rental.getVehicle();
            if (!vehicle.isAvailable()) {
                System.out.println("Vehicle is not available for rent.");
                return false;
            }
            
            vehicle.setAvailable(false);
            rentals.add(rental);
            return true;
        } catch (Exception e) {
            System.err.println("Error processing rental: " + e.getMessage());
            return false;
        }
    }

    public List<Rental> getAllRentals() {
        return new ArrayList<>(rentals);
    }
} 