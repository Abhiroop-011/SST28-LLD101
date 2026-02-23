public class EmailSender extends NotificationSender {
    public EmailSender(AuditLog audit) { super(audit); }

    @Override
    protected void doSend(Notification n) {
        // Keep meaning intact: no silent truncation.
        System.out.println("EMAIL -> to=" + n.email + " subject=" + n.subject + " body=" + n.body);
        audit.add("email sent");
    }

    @Override
    protected String senderId() {
        return "EMAIL";
    }
}
