public class ParkingSpot {
    private final String spotID;
    private boolean isOccupied;
    private final ParkingSpotType type;
    private Vehicle parkedVehicle;

    ParkingSpot(String spotID, ParkingSpotType type) {
        this.spotID = spotID;
        this.isOccupied = false;
        this.type = type;
    }

    public Vehicle getParkedVehicle() {
        return parkedVehicle != null ? parkedVehicle : null;
    }

    public String getSpotID() {
        return spotID;
    }

    public ParkingSpotType getType() {
        return type;
    }

    public void setOccupied(boolean occupied) {
        isOccupied = occupied;
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public boolean assignVehicle(Vehicle vehicle) {
        this.parkedVehicle = vehicle;
        this.isOccupied = vehicle != null;
        return this.isOccupied;
    }

    public boolean removeVehicle() {
        this.parkedVehicle = null;
        this.isOccupied = false;
        return this.isOccupied;
    }
}
