import java.util.Map;

public class RoomFeeRule implements FeeRule {
    private final Map<Integer, Money> roomPrices;
    private final Money defaultPrice;

    public RoomFeeRule(Map<Integer, Money> roomPrices, Money defaultPrice) {
        this.roomPrices = roomPrices;
        this.defaultPrice = defaultPrice;
    }

    @Override
    public Money calculate(BookingRequest req) {
        return roomPrices.getOrDefault(req.roomType, defaultPrice);
    }
}