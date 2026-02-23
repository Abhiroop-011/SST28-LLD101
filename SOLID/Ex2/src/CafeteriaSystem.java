import java.util.*;
public class CafeteriaSystem {
    private final BillingCalculator billingCalculator;
    private final InvoiceStore store;
    private final InvoiceFormatter formatter;
    private final CafeteriaPrinter printer;
    
    private final Map<String, TaxStrategy> taxes;
    private final Map<String, DiscountStrategy> discounts;
    
    private int invoiceSeq = 1000;

    public CafeteriaSystem(BillingCalculator billingCalculator, InvoiceStore store, InvoiceFormatter formatter, CafeteriaPrinter printer,
                           Map<String, TaxStrategy> taxes, Map<String, DiscountStrategy> discounts) {
        this.billingCalculator = billingCalculator; 
        this.store = store; 
        this.formatter = formatter; 
        this.printer = printer;
        this.taxes = taxes; 
        this.discounts = discounts;
    }

    public void checkout(String customerType, List<OrderLine> orderLines) {
        String invId = "INV-" + (++invoiceSeq);
        
        TaxStrategy taxRule = taxes.getOrDefault(customerType, () -> 8.0);
        DiscountStrategy discountRule = discounts.getOrDefault(customerType, (s, d) -> 0.0);

        Invoice invoice = billingCalculator.calculateBilling(invId, orderLines, taxRule, discountRule);

        String printable = formatter.format(invoice);

        store.save(invId, printable);
        printer.printInvoice(printable, invId, store.countLines(invId));
    }
}