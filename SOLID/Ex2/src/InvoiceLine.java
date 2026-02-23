public class InvoiceLine {
    public final String itemName;
    public final int qty;
    public final double lineTotal;

    public InvoiceLine(String itemName, int qty, double lineTotal) {
        this.itemName = itemName; this.qty = qty; this.lineTotal = lineTotal;
    }
}