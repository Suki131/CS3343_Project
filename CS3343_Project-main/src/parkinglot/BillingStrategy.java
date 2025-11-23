package parkinglot;

public interface BillingStrategy {
    double calculateBill(Ticket ticket);

    void setFeeRate(double fee);
    
    double getFeeRate();
}
