package parkinglot;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TicketManager {
    private Map<Vehicle, Ticket> ticketManager;
    private Map<Vehicle, List<Ticket>> ticketHistory = new HashMap<>();
    private static TicketManager instance = new TicketManager();

    private TicketManager() {
        ticketManager = new HashMap<>();
    }

    public static TicketManager getInstance() {
        return instance;
    }

    public Ticket getEnteredTicket(Vehicle vehicle) {
        Ticket t = ticketManager.get(vehicle);
        if (t != null && t.getStatus() == TicketStatus.ENTERED) {
            return t;
        }
        return null;
    }

    public void printVehicleTicketHistory(Vehicle vehicle) {
        if (vehicle == null) {
            System.out.println("invalid vehicle.");
            return;
        }



        List<Ticket> history = ticketHistory.get(vehicle);
        if (history == null) {
            System.out.println("No ticket history found for vehicle: " + vehicle.getLicensePlate());
            return;
        }

        System.out.println("\n=============== Vehicle Ticket History ===============");
        System.out.println("License Plate: " + vehicle.getLicensePlate());
        System.out.println("Total Tickets: " + history.size());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        for (int i = 0; i < history.size(); i++) {
            Ticket ticket = history.get(i);

            System.out.println("\n【Ticket " + (i + 1) + "/" + history.size() + "】");
            System.out.println("┌────────────────────────────────┬────────────────────────────────────────────────┐");
            System.out.println("│ Ticket ID                      │ " + String.format("%-46s", ticket.getTicketID()) + " │");
            System.out.println("├────────────────────────────────┼────────────────────────────────────────────────┤");
            System.out.println("│ Entry Time                     │ " + String.format("%-46s", ticket.getEntryTime().format(formatter)) + " │");
            System.out.println("│ Exit Time                      │ " + String.format("%-46s", ticket.getExitTime().format(formatter)) + " │");
            System.out.println("│ Ticket Status                  │ " + String.format("%-46s", ticket.getStatus()) + " │");
            System.out.println("│ Total Amount                   │ " + String.format("%-46.2f", ticket.getTotalAmount()) + " │");
            System.out.println("└────────────────────────────────┴────────────────────────────────────────────────┘");
        }
    }

    public void addTicket(Vehicle vehicle, Ticket ticket) {
        if (vehicle == null || ticket == null) return;
        
        ticketManager.put(vehicle, ticket);
        
        ticketHistory.computeIfAbsent(vehicle, k -> new ArrayList<>()).add(ticket);
    }   

}