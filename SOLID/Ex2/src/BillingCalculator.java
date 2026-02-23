import java.util.*;
public class BillingCalculator {
    private final Menu menu;

    public BillingCalculator(Menu menu) {
        this.menu = menu;
    }

    public Invoice calculateBilling(String invId, List<OrderLine> orderLines, TaxStrategy taxRule, DiscountStrategy discountRule) {
        double subtotal = 0.0;
        List<InvoiceLine> lines = new ArrayList<>();
    
        for (OrderLine ol : orderLines) {
            MenuItem item = menu.get(ol.itemId);
            double lineTotal = item.price * ol.qty;
            subtotal += lineTotal;
            lines.add(new InvoiceLine(item.name, ol.qty, lineTotal));
        }

        double taxPct = taxRule.getTaxPercentage();
        double taxAmount = subtotal * (taxPct / 100.0);

        double discountAmount = discountRule.calculateDiscount(subtotal, lines.size());

        double total = subtotal + taxAmount - discountAmount;

        return new Invoice(invId, lines, subtotal, taxPct, taxAmount, discountAmount, total);
    }
}