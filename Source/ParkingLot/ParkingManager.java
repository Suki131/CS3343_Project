import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ParkingManager {
    private static final HashMap<ParkingSpotType, List<ParkingSpot>> spotsByType = new HashMap<>();

    static {
        int spotsPerType = 10;
        for (ParkingSpotType type : ParkingSpotType.values()) {
            spotsByType.put(type, new ArrayList<>());
            for (int i = 1; i <= spotsPerType; i++) {
                String spotID = type.name() + "_" + i;
                ParkingSpot spot = new ParkingSpot(spotID, type);
                spotsByType.get(type).add(spot);
            }
        }
    }

    public static HashMap<ParkingSpotType, List<ParkingSpot>> getAllSpots() {
        return spotsByType;
    }

    public static List<ParkingSpot> getSpotsByType(ParkingSpotType type) {
        return spotsByType.get(type);
    }

    public static void addParkingSpot(ParkingSpotType type) {
        List<ParkingSpot> spots = spotsByType.get(type);
        if (spots == null) {
            spots = new ArrayList<>();
            spotsByType.put(type, spots);
        }
        String spotID = type.name() + "_" + (spots.size() + 1);
        ParkingSpot newSpot = new ParkingSpot(spotID, type);
        spots.add(newSpot);
        System.out.println("New Spot:" + spotID);
    }

    public static void removeParkingSpot(ParkingSpotType type) {
        List<ParkingSpot> spots = spotsByType.get(type);
        if (spots != null && !spots.isEmpty()) {
            ParkingSpot removed = spots.remove(spots.size() - 1);
            System.out.println("Spot removed:" + removed.getSpotID());
        } else {
            System.out.println("No " + type + " spots to remove");
        }
    }

    public static Vehicle findVehicle(String licensePlate) {
        for (List<ParkingSpot> spots : spotsByType.values()) {
            for (ParkingSpot spot : spots) {
                if (spot.isOccupied() && spot.getParkedVehicle().getLicensePlate().equals(licensePlate)) {
                    return spot.getParkedVehicle();
                }
            }
        }
        return null;
    }

    public static ParkingSpot getAvailableSpot(ParkingSpotType type) {
        List<ParkingSpot> spots = spotsByType.get(type);
        if (spots != null) {
            for (ParkingSpot spot : spots) {
                if (!spot.isOccupied()) {
                    return spot;
                }
            }
        }
        return null;
    }

    public static ParkingSpot findParkingSpotByVehicle(Vehicle vehicle) {
        for (List<ParkingSpot> spots : spotsByType.values()) {
            for (ParkingSpot spot : spots) {    
                if (spot.isOccupied() && spot.getParkedVehicle().equals(vehicle)) {
                    return spot;
                }
            }
        }
        return null;
    }

    public static ParkingSpot convertVehicleTypeToSpot(VehicleType vehicleType) {
        switch (vehicleType) {
            case PRIVATE:
                return getAvailableSpot(ParkingSpotType.PRIVATE_SPOT);
            case TRUCK_3_5T:
                return getAvailableSpot(ParkingSpotType.TRUCK_3_5T_SPOT);
            case TRUCK_5_5T:
                return getAvailableSpot(ParkingSpotType.TRUCK_5_5T_SPOT);
            case VAN:
                return getAvailableSpot(ParkingSpotType.VAN_SPOT);
            default:
                return null;
        }
    }

        public static void displayParkingStatus() {
        System.out.println("\n========================================= Parking Lot Overview =========================================\n");

        for (ParkingSpotType type : ParkingSpotType.values()) {
            List<ParkingSpot> spots = spotsByType.get(type);
            if (spots == null) continue;
            
            long occupied = spots.stream().filter(ParkingSpot::isOccupied).count();
            System.out.println("【" + formatSpotTypeName(type) + "】");
            System.out.println("Total Spots: " + spots.size());
            System.out.println("Occupied: " + occupied);
            System.out.println("Available: " + (spots.size() - occupied));

            System.out.println("\nSpot Details:");
            System.out.println("┌────────────────────────┬────────────────────────────────────┐");
            System.out.println("│        Spot ID         │             Status                 │");
            System.out.println("├────────────────────────┼────────────────────────────────────┤");
            
            for (ParkingSpot spot : spots) {
                String status = spot.isOccupied() ?
                    "Occupied - " + spot.getParkedVehicle().getLicensePlate() :
                    "Available";
                System.out.printf("│ %-22s │ %-34s │%n",
                    spot.getSpotID(), status);
            }
            System.out.println("└────────────────────────┴────────────────────────────────────┘\n");
            System.out.println("=========================================================================================================");
        }
    }

    private static String formatSpotTypeName(ParkingSpotType type) {
        switch (type) {
            case PRIVATE_SPOT: return "Normal Spot";
            case TRUCK_3_5T_SPOT: return "3.5T Truck Spot";
            case TRUCK_5_5T_SPOT: return "5.5T Truck Spot";
            case VAN_SPOT: return "Van Spot";
            default: return type.name();
        }
    }
}
