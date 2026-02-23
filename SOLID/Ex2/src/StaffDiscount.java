public class StaffDiscount implements DiscountStrategy {
    @Override
    public double calculateDiscount(double subtotal, int distinctLines) {
        return distinctLines >= 3 ? 15.0 : 5.0;
    }
}