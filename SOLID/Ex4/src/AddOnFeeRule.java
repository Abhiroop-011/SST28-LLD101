import java.util.Map;

public class AddOnFeeRule implements FeeRule {
    private final Map<AddOn, Money> addOnPrices;

    public AddOnFeeRule(Map<AddOn, Money> addOnPrices) {
        this.addOnPrices = addOnPrices;
    }

    @Override
    public Money calculate(BookingRequest req) {
        Money total = new Money(0.0);
        for (AddOn a : req.addOns) {
            Money price = addOnPrices.get(a);
            if (price != null) {
                total = total.plus(price);
            }
        }
        return total;
    }
}