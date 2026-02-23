import java.util.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Hostel Fee Calculator ===");
        BookingRequest req = new BookingRequest(LegacyRoomTypes.DOUBLE, List.of(AddOn.LAUNDRY, AddOn.MESS));

        Map<Integer, Money> roomRates = new HashMap<>();
        roomRates.put(LegacyRoomTypes.SINGLE, new Money(14000.0));
        roomRates.put(LegacyRoomTypes.DOUBLE, new Money(15000.0));
        roomRates.put(LegacyRoomTypes.TRIPLE, new Money(12000.0));
        
        Map<AddOn, Money> addOnRates = new HashMap<>();
        addOnRates.put(AddOn.MESS, new Money(1000.0));
        addOnRates.put(AddOn.LAUNDRY, new Money(500.0));
        addOnRates.put(AddOn.GYM, new Money(300.0));

        List<FeeRule> rules = Arrays.asList(
            new RoomFeeRule(roomRates, new Money(16000.0)),
            new AddOnFeeRule(addOnRates)
        );

        HostelFeeCalculator calc = new HostelFeeCalculator(rules, new Money(5000.0));
        FakeBookingRepo repo = new FakeBookingRepo();

        FeeResult result = calc.calculate(req);
        
        ReceiptPrinter.print(req, result.monthly, result.deposit);
        
        String bookingId = "H-" + (7000 + new Random(1).nextInt(1000));
        repo.save(bookingId, req, result.monthly, result.deposit);
    }
}