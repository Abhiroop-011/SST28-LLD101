import java.util.List;

public class RegistrationResponse {
    public final boolean success;
    public final List<String> errors;
    public final StudentRecord record;

    public RegistrationResponse(boolean success, List<String> errors, StudentRecord record) {
        this.success = success; 
        this.errors = errors; 
        this.record = record;
    }
}