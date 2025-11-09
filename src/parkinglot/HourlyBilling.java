package src.parkinglot;
public class HourlyBilling implements BillingStrategy {
    private static double HOURLY_RATE = 5.0;
    private static final HourlyBilling INSTANCE = new HourlyBilling();

    private HourlyBilling() {
        HOURLY_RATE = 5.0;
    }

    public static HourlyBilling getInstance() {
        return INSTANCE;
    }

    @Override
    public double calculateBill(Ticket ticket) {
        long hoursParked = java.time.Duration.between(ticket.getEntryTime(), ticket.getExitTime()).toHours();
        System.out.println("Hours parked: " + hoursParked);
        System.out.println("Hourly rate: " + HOURLY_RATE);
        return hoursParked * HOURLY_RATE;
    }

    @Override
    public boolean setFeeRate(double fee){
        HOURLY_RATE = fee;
        if (HOURLY_RATE != fee){
            return false;
        } else {
            return true;
        }
    }
}
