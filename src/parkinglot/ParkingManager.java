package parkinglot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.EnumMap;

public class ParkingManager {
    private static final HashMap<ParkingSpotType, List<ParkingSpot>> spotsByType = new HashMap<>();
    private static final EnumMap<VehicleType, ParkingSpotType> vehicleToSpotMap = new EnumMap<>(VehicleType.class);
    private static final EnumMap<ParkingSpotType, String> spotTypeNames = new EnumMap<>(ParkingSpotType.class);

    static {
        initializeSpots(10);
        vehicleToSpotMap.put(VehicleType.PRIVATE, ParkingSpotType.PRIVATE_SPOT);
        vehicleToSpotMap.put(VehicleType.TRUCK_3_5T, ParkingSpotType.TRUCK_3_5T_SPOT);
        vehicleToSpotMap.put(VehicleType.TRUCK_5_5T, ParkingSpotType.TRUCK_5_5T_SPOT);
        vehicleToSpotMap.put(VehicleType.VAN, ParkingSpotType.VAN_SPOT);

        spotTypeNames.put(ParkingSpotType.PRIVATE_SPOT, "Normal Spot");
        spotTypeNames.put(ParkingSpotType.TRUCK_3_5T_SPOT, "3.5T Truck Spot");
        spotTypeNames.put(ParkingSpotType.TRUCK_5_5T_SPOT, "5.5T Truck Spot");
        spotTypeNames.put(ParkingSpotType.VAN_SPOT, "Van Spot");
    }

    // ---------- Testability helpers (no println changes) ----------
    public static void initializeSpots(int spotsPerType) {
        spotsByType.clear();
        for (ParkingSpotType type : ParkingSpotType.values()) {
            List<ParkingSpot> spots = new ArrayList<>();
            for (int i = 1; i <= spotsPerType; i++) {
                String spotID = type.name() + "_" + i;
                spots.add(new ParkingSpot(spotID, type));
            }
            spotsByType.put(type, spots);
        }
    }

    public static void mapVehicleType(VehicleType type, ParkingSpotType spotType) {
        vehicleToSpotMap.put(type, spotType);
    }

    public static void removeVehicleMapping(VehicleType type) {
        vehicleToSpotMap.remove(type);
    }
    // --------------------------------------------------------------

    public static HashMap<ParkingSpotType, List<ParkingSpot>> getAllSpots() {
        return spotsByType;
    }

    public static List<ParkingSpot> getSpotsByType(ParkingSpotType type) {
        return spotsByType.get(type);
    }

    public static String addParkingSpot(ParkingSpotType type) {
        List<ParkingSpot> spots = spotsByType.get(type);
        if (spots == null) {
            spots = new ArrayList<>();
            spotsByType.put(type, spots);
        }
        String spotID = type.name() + "_" + (spots.size() + 1);
        ParkingSpot newSpot = new ParkingSpot(spotID, type);
        spots.add(newSpot);
        System.out.println("New Spot:" + spotID);
        return spotID;
    }

    public static String removeParkingSpot(ParkingSpotType type) {
        List<ParkingSpot> spots = spotsByType.get(type);
        if (spots != null && !spots.isEmpty()) {
            ParkingSpot removed = spots.remove(spots.size() - 1);
            System.out.println("Spot removed:" + removed.getSpotID());
            return removed.getSpotID();
        } else {
            System.out.println("No " + type + " spots to remove");
            return null;
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
        } else {
            spots = new ArrayList<>();
            spotsByType.put(type, spots);
        }
        String spotID = type.name() + "_" + (spots.size() + 1);
        ParkingSpot newSpot = new ParkingSpot(spotID, type);
        spots.add(newSpot);
        System.out.println("Allocated additional spot: " + spotID);
        return newSpot;
    }

    public static ParkingSpot findParkingSpotByVehicle(Vehicle vehicle) {
        if (vehicle == null) return null;
        for (List<ParkingSpot> spots : spotsByType.values()) {
            for (ParkingSpot spot : spots) {
                if (spot.isOccupied() &&
                    spot.getParkedVehicle().getLicensePlate().equals(vehicle.getLicensePlate())) {
                    return spot;
                }
            }
        }
        return null;
    }

    public static ParkingSpot convertVehicleTypeToSpot(VehicleType vehicleType) {
        if (vehicleType == null) return null;
        ParkingSpotType spotType = vehicleToSpotMap.get(vehicleType);
        if (spotType == null) return null;
        return getAvailableSpot(spotType);
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
            System.out.println("│ Spot ID │ Status │");
            System.out.println("├────────────────────────┼────────────────────────────────────┤");
            for (ParkingSpot spot : spots) {
                String status = spot.isOccupied()
                        ? "Occupied - " + spot.getParkedVehicle().getLicensePlate()
                        : "Available";
                System.out.printf("│ %-22s │ %-34s │%n", spot.getSpotID(), status);
            }
            System.out.println("└────────────────────────┴────────────────────────────────────┘\n");
            System.out.println("=========================================================================================================");
        }
    }

    private static String formatSpotTypeName(ParkingSpotType type) {
        return spotTypeNames.getOrDefault(type, type.name());
    }
}
