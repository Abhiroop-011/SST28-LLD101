public class SmsSender extends NotificationSender {
    public SmsSender(AuditLog audit) { super(audit); }

    @Override
    protected void doSend(Notification n) {
        //We will preserve the subject by concatenating it!
        String fullMessage = n.body;
        if (n.subject != null && !n.subject.isEmpty()) {
            fullMessage = "[" + n.subject + "] " + n.body;
        }

        System.out.println("SMS -> to=" + n.phone + " body=" + fullMessage);
        audit.add("sms sent");
    }

    @Override
    protected String senderId() { return "SMS"; }
}