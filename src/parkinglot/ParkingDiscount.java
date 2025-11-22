package parkinglot;
public class ParkingDiscount implements DiscountStrategy {
    public double applyDiscount(double baseAmount, double discount) {
        return baseAmount - discount;
    }
}
