package parkinglot;

import java.time.LocalDate;

public class DailyBilling implements BillingStrategy {
    private double DAILY_RATE = 0.0; 
    private static DailyBilling INSTANCE = new DailyBilling();

    private DailyBilling() {
        DAILY_RATE = 0.0;
    }

    public static DailyBilling getInstance() {
        return INSTANCE;
    }

    @Override
    public double calculateBill(Ticket ticket) {
        LocalDate entryDate = ticket.getEntryTime().toLocalDate();
        LocalDate exitDate = ticket.getExitTime().toLocalDate();

        long daysParked = java.time.temporal.ChronoUnit.DAYS.between(entryDate, exitDate);

        long billableDays = Math.max(1, daysParked);

        return billableDays * DAILY_RATE;
    }

    @Override
    public void setFeeRate(double fee){
    	this.DAILY_RATE = fee;
    }
    
    @Override
    public double getFeeRate() {
    	return DAILY_RATE;
    }
}
