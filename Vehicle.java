public class Vehicle {
    private String type;
    private String make;
    private String model;
    private double price;
    private String location;
    private boolean isAvailable;

    public Vehicle(String type, String make, String model, double price, String location) {
        this.type = type;
        this.make = make;
        this.model = model;
        this.price = price;
        this.location = location;
        this.isAvailable = true;
    }

    // Getters
    public String getType() { return type; }
    public String getMake() { return make; }
    public String getModel() { return model; }
    public double getPrice() { return price; }
    public String getLocation() { return location; }
    public boolean isAvailable() { return isAvailable; }

    // Setters
    public void setType(String type) { this.type = type; }
    public void setMake(String make) { this.make = make; }
    public void setModel(String model) { this.model = model; }
    public void setPrice(double price) { this.price = price; }
    public void setLocation(String location) { this.location = location; }
    public void setAvailable(boolean available) { isAvailable = available; }

    @Override
    public String toString() {
        return String.format("Vehicle{type='%s', make='%s', model='%s', price=%.2f, location='%s', available=%s}",
                type, make, model, price, location, isAvailable);
    }
} 