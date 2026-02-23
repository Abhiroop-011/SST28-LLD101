import java.util.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Cafeteria Billing ===");

        Menu menu = new Menu();
        menu.add(new MenuItem("M1", "Veg Thali", 80.00));
        menu.add(new MenuItem("C1", "Coffee", 30.00));
        menu.add(new MenuItem("S1", "Sandwich", 60.00));

        Map<String, TaxStrategy> taxes = new HashMap<>();
        taxes.put("student", new StudentTax());
        taxes.put("staff", new StaffTax());

        Map<String, DiscountStrategy> discounts = new HashMap<>();
        discounts.put("student", new StudentDiscount());
        discounts.put("staff", new StaffDiscount());

        CafeteriaSystem sys = new CafeteriaSystem(
            new BillingCalculator(menu), new FileStore(), new InvoiceFormatter(), new CafeteriaPrinter(), taxes, discounts
        );

        List<OrderLine> order1 = List.of(new OrderLine("M1", 2), new OrderLine("C1", 1));
        sys.checkout("student", order1);
        System.out.println();

        //Staff-checkout testcase
        List<OrderLine> order2 = List.of(new OrderLine("S1", 1), new OrderLine("C1", 1), new OrderLine("M1", 1));
        sys.checkout("staff", order2);
    }
}