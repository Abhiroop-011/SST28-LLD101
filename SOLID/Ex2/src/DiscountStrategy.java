public interface DiscountStrategy {
    double calculateDiscount(double subtotal, int distinctLines);
}