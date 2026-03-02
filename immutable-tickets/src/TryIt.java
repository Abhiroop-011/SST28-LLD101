import com.example.tickets.IncidentTicket;
import com.example.tickets.TicketService;
import java.util.List;

public class TryIt {

    public static void main(String[] args) {
        TicketService service = new TicketService();

        IncidentTicket t1 = service.createTicket("TCK-1001", "reporter@example.com", "Payment failing on checkout");
        System.out.println("Original Created  : " + t1);

        IncidentTicket t2 = service.assign(t1, "agent@example.com");
        IncidentTicket t3 = service.escalateToCritical(t2);

        System.out.println("After Assignment  : " + t2);
        System.out.println("After Escalation  : " + t3);
        System.out.println("Verify Original   : " + t1 + " (Should be completely unchanged!)");

        System.out.println("\nAttempting to hack tags array from outside...");
        try {
            List<String> tags = t3.getTags();
            tags.add("HACKED_FROM_OUTSIDE");
        } catch (UnsupportedOperationException e) {
            System.out.println("Blocked! Caught UnsupportedOperationException. Tags are completely immutable.");
        }
    }
}