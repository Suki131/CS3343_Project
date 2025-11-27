package parkinglot;

import java.time.LocalDateTime;

public class Ticket {
    private final String ticketID;
    private final Vehicle vehicle;
    private LocalDateTime entryTime;
    private LocalDateTime exitTime;
    private double baseAmount;
    private double totalAmount;
    private TicketStatus status;
    private ParkingSpot spot;
    private DiscountStrategy discountStrategy;
    private BillingStrategy billingStrategy;

    public Ticket(Vehicle vehicle, ParkingSpot spot) {
        this.ticketID = RandomIDGenerator.generateID(6);
        this.vehicle = vehicle;
        this.spot = spot;
        this.entryTime = LocalDateTime.now();
        this.exitTime = LocalDateTime.now().plusHours(1).plusMinutes(1);
        this.status = TicketStatus.ENTERED;
    }

    public boolean changeStatus(TicketStatus newStatus) {
        TicketStatus oldStatus = this.status;
        this.status = newStatus;
        return oldStatus == newStatus;
    }

    public LocalDateTime getEntryTime() {
        return entryTime;
    }

    public LocalDateTime getExitTime() {
        return exitTime;
    }

    public boolean setEntryTime(LocalDateTime entryTime) {
        LocalDateTime oldEntry = this.entryTime;
        this.entryTime = entryTime;
        return oldEntry == entryTime; // both true/false possible
    }

    public boolean setExitTime(LocalDateTime exitTime) {
        LocalDateTime oldExit = this.exitTime;
        this.exitTime = exitTime;
        return oldExit == exitTime; // both true/false possible
    }

    public void setBillingStrategy(BillingStrategy billingStrategy) {
        this.billingStrategy = billingStrategy;
    }

    public TicketStatus getStatus() {
        return status;
    }

    public void setDiscountStrategy(DiscountStrategy discountStrategy) {
        this.discountStrategy = discountStrategy;
    }

    public double applyDiscount(double currentFee, double discount) {
        return discountStrategy != null ? discountStrategy.applyDiscount(currentFee, discount) : 0;
    }

    public MembershipType getMembershipType() {
        return vehicle.getOwnerDriver().getMembershipType();
    }

    public double calDiscount(double discount) {
        return totalAmount = discountStrategy != null
                ? discountStrategy.applyDiscount(calculateBaseAmount(), discount)
                : 0;
    }

    public double calculateBaseAmount() {
        if (billingStrategy != null) {
            this.baseAmount = billingStrategy.calculateBill(this);
        }
        return baseAmount;
    }

    public double getBaseAmount() {
        return baseAmount;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setParkingFee(double newFee) {
        this.totalAmount = newFee;
    }

    public String getTicketID() {
        return ticketID;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }
}
