package parkinglot;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class DriverManager {
    private static DriverManager instance = new DriverManager();
    private final ArrayList<Driver> driverList = new ArrayList<>();

private DriverManager() {
        Driver driver = new Driver("1234", "John Doe", "12345678", MembershipType.NONE, null, new ArrayList<>());
        driver.addVehicle(new Vehicle("AB123", VehicleType.PRIVATE, driver));
        driver.addVehicle(new Vehicle("XY789", VehicleType.VAN, driver));
        this.addDriver(driver);
        driver = new Driver("1111", "Chris Wong", "87654321", MembershipType.DAILY, LocalDateTime.now().plusDays(10), new ArrayList<>());
        driver.addVehicle(new Vehicle("CD123", VehicleType.TRUCK_3_5T, driver));
        driver.addVehicle(new Vehicle("ST789", VehicleType.VAN, driver));
        this.addDriver(driver);
        driver = new Driver("2222", "Eric Chan", "12121212", MembershipType.MONTHLY, LocalDateTime.now().plusMonths(10), new ArrayList<>());
        driver.addVehicle(new Vehicle("EF123", VehicleType.TRUCK_5_5T, driver));
        driver.addVehicle(new Vehicle("PQ789", VehicleType.VAN, driver));
        this.addDriver(driver);
        driver = new Driver("3333", "Kevin Man", "33333333", MembershipType.ANNUALLY, LocalDateTime.now().plusYears(1), new ArrayList<>());
        driver.addVehicle(new Vehicle("GH123", VehicleType.TRUCK_5_5T, driver));
        driver.addVehicle(new Vehicle("JK789", VehicleType.VAN, driver));
        this.addDriver(driver);
    }

    public static DriverManager getInstance() {
        return instance;
    }

    public void addDriver(Driver driver) {
        driverList.add(driver);
    }

    public void removeDriver(Driver driver) {
        driverList.remove(driver);
    }

    public ArrayList<Driver> getDrivers() {
        return driverList;
    }

    public Driver retrieveDriver(String driverID, String phone) {
        for (Driver driver : getInstance().getDrivers()) {
            if (driver.getDriverID().equals(driverID) && driver.getContactInfo().equals(phone)) {
                return driver;
            }
        }
        return null;
    }

    public Driver retrieveDriverbyID(String driverID) {
        for (Driver driver : getInstance().getDrivers()) {
            if (driver.getDriverID().equals(driverID)) {
                return driver;
            }
        }
        return null;
    }

    public Vehicle findVehicleByLicense(String licensePlate) {
        if (licensePlate == null || licensePlate.trim().isEmpty()) {
            return null;
        }

        for (Driver driver : driverList) {
            Vehicle vehicle = driver.retrieveVehicle(licensePlate);
            if (vehicle != null) {
                return vehicle;
            }
        }
        return null;
    }
}
