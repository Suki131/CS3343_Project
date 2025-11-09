package src.parkinglot;
public class Vehicle {
    private String licensePlate;
    private VehicleType vehicleType;
    private Driver ownerDriver;

    public Vehicle(String licensePlate, VehicleType vehicleType, Driver ownerDriver) {
        this.licensePlate = licensePlate;
        this.vehicleType = vehicleType;
        this.ownerDriver = ownerDriver;
        ownerDriver.addVehicle(this);
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public VehicleType getSpec() {
        return vehicleType;
    }

    public Driver getOwnerDriver() {
        return ownerDriver;
    }
}
