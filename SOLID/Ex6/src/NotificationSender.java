public abstract class NotificationSender {
    protected final AuditLog audit;
    protected NotificationSender(AuditLog audit) { this.audit = audit; }

   
    public final void send(Notification n) {
        try {
            doSend(n);
        } catch (RuntimeException ex) {
            System.out.println(senderId() + " ERROR: " + ex.getMessage());
            audit.add(failureAuditEntry());
        }
    }

    protected abstract void doSend(Notification n);

    protected abstract String senderId();

    protected String failureAuditEntry() {
        return senderId() + " failed";
    }
}
