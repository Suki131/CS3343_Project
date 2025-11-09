package src.parkinglot;
public class DailyBilling implements BillingStrategy {
    private double DAILY_RATE = 0.0; 
    private static final DailyBilling INSTANCE = new DailyBilling();

    private DailyBilling() {
        DAILY_RATE = 0.0;
    }

    public static DailyBilling getInstance() {
        return INSTANCE;
    }

    @Override
    public double calculateBill(Ticket ticket) {
        long daysParked = java.time.Duration.between(ticket.getEntryTime(), ticket.getExitTime()).toDays();
        return daysParked * DAILY_RATE;
    }

    @Override
    public boolean setFeeRate(double fee){
        if (DAILY_RATE != fee){
            return false;
        } else {
            return true;
        }
    }
}
