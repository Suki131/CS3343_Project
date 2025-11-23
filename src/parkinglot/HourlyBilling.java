package parkinglot;

import java.time.Duration;
import java.time.LocalDateTime;

public class HourlyBilling implements BillingStrategy {
    private double HOURLY_RATE = 5.0;
    private static final HourlyBilling INSTANCE = new HourlyBilling();

    private HourlyBilling() {
        HOURLY_RATE = 5.0;
    }

    public static HourlyBilling getInstance() {
        return INSTANCE;
    }
    
    @Override
    public double calculateBill(Ticket ticket) {
        LocalDateTime entry = ticket.getEntryTime();
        LocalDateTime exit = ticket.getExitTime();

        Duration duration = Duration.between(entry, exit);
        long minutes = duration.toMinutes();
        
        long hoursParked = (minutes + 59) / 60;

        hoursParked = Math.max(1, hoursParked);

        return hoursParked * HOURLY_RATE;
    }

    @Override
    public void setFeeRate(double fee){
        this.HOURLY_RATE = fee;
    }
    
    @Override
    public double getFeeRate() {
    	return HOURLY_RATE;
    }
}
