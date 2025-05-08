public class CustomerDetails {
    private String name;
    private String idNumber;
    private String phone;

    public CustomerDetails(String name, String idNumber, String phone) {
        validateName(name);
        validateIdNumber(idNumber);
        validatePhone(phone);
        
        this.name = name;
        this.idNumber = idNumber;
        this.phone = phone;
    }

    private void validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        if (!name.matches("^[a-zA-Z ]+$")) {
            throw new IllegalArgumentException("Name should only contain letters and spaces");
        }
    }

    private void validateIdNumber(String idNumber) {
        if (idNumber == null || idNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("ID number cannot be empty");
        }
        if (!idNumber.matches("\\d{5}")) {
            throw new IllegalArgumentException("ID number must be exactly 5 digits");
        }
    }

    private void validatePhone(String phone) {
        if (phone == null || phone.trim().isEmpty()) {
            throw new IllegalArgumentException("Phone number cannot be empty");
        }
        if (!phone.matches("\\d{10}")) {
            throw new IllegalArgumentException("Phone number must be exactly 10 digits");
        }
    }

    // Getters
    public String getName() { return name; }
    public String getIdNumber() { return idNumber; }
    public String getPhone() { return phone; }

    @Override
    public String toString() {
        return String.format("Customer: %s (ID: %s, Phone: %s)", name, idNumber, phone);
    }
} 