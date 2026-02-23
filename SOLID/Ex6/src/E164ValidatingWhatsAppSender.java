public class E164ValidatingWhatsAppSender extends NotificationSender {
    private final WhatsAppSender delegate;

    public E164ValidatingWhatsAppSender(WhatsAppSender delegate, AuditLog audit) {
        super(audit);
        this.delegate = delegate;
    }

    @Override
    protected void doSend(Notification n) {
        if (n.phone == null || !n.phone.startsWith("+")) {
            throw new IllegalArgumentException("phone must start with + and country code");
        }
        delegate.doSend(n);
    }

    @Override
    protected String senderId() {
        return "WA";
    }

    @Override
    protected String failureAuditEntry() {
        return "wa failed";
    }
}
