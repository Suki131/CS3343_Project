package src.parkinglot;

public interface BillingStrategy {
    double calculateBill(Ticket ticket);

    boolean setFeeRate(double fee);
}
