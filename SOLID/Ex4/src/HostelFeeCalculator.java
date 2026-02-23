import java.util.List;

public class HostelFeeCalculator {
    private final List<FeeRule> monthlyRules;
    private final Money depositAmount; 

    public HostelFeeCalculator(List<FeeRule> monthlyRules, Money depositAmount) {
        this.monthlyRules = monthlyRules;
        this.depositAmount = depositAmount;
    }

    public FeeResult calculate(BookingRequest req) {
        Money monthlyTotal = new Money(0.0);
        
        for (FeeRule rule : monthlyRules) {
            monthlyTotal = monthlyTotal.plus(rule.calculate(req));
        }

        return new FeeResult(monthlyTotal, depositAmount);
    }
}