package src.parkinglot;
public class AnnualBilling implements BillingStrategy {
    private double ANNUAL_RATE = 0.0;
    private static final AnnualBilling INSTANCE = new AnnualBilling();

    private AnnualBilling() {
        ANNUAL_RATE = 0.0;
    }

    public static AnnualBilling getInstance() {
        return INSTANCE;
    }

    @Override
    public double calculateBill(Ticket ticket) {
        return ANNUAL_RATE;
    }

    @Override
    public boolean setFeeRate(double fee) {
        if (ANNUAL_RATE != fee){
            return false;
        } else {
            return true;
        }
    }
}