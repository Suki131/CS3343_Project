package parkinglot;
public class MonthlyBilling implements BillingStrategy {
    private double MONTHLY_RATE = 0.0;
    private static final MonthlyBilling INSTANCE = new MonthlyBilling();

    private MonthlyBilling() {
        MONTHLY_RATE = 0.0;
    }

    public static MonthlyBilling getInstance() {
        return INSTANCE;
    }

    @Override
    public double calculateBill(Ticket ticket) {
        return MONTHLY_RATE;
    }

    @Override
    public void setFeeRate(double fee){
    	this.MONTHLY_RATE = fee;
    }
    
    @Override
    public double getFeeRate() {
    	return MONTHLY_RATE;
    }
}
