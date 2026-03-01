public class WhatsAppSender extends NotificationSender {
    private final NotificationValidator validator = new WhatsAppValidator();

    public WhatsAppSender(AuditLog audit) { 
        super(audit); 
    }

    @Override
    protected void doSend(Notification n) {
        validator.validate(n); 
        
        System.out.println("WA -> to=" + n.phone + " body=" + n.body);
        audit.add("wa sent");
    }

    @Override
    protected String senderId() {
        return "WA";
    }
}