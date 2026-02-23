
public interface EligibilityRule {
    String evaluate(StudentProfile profile, RuleInput config);
}