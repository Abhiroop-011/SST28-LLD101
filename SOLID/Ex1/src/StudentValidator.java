import java.util.*;

public class StudentValidator {
    private final List<String> allowedPrograms;

    public StudentValidator(List<String> allowedPrograms) {
        this.allowedPrograms = allowedPrograms;
    }

    public List<String> validate(ParsedInput input) {
        List<String> errors = new ArrayList<>();
        if (input.name.isBlank()) errors.add("name is required");
        if (input.email.isBlank() || !input.email.contains("@")) errors.add("email is invalid");
        if (input.phone.isBlank() || !input.phone.chars().allMatch(Character::isDigit)) errors.add("phone is invalid");
        
        if (!allowedPrograms.contains(input.program)) {
            errors.add("program is invalid");
        }
        
        return errors;
    }
}