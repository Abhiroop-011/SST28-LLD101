public class CreditsRule implements EligibilityRule {
    @Override
    public String evaluate(StudentProfile s, RuleInput config) {
        if (s.earnedCredits < config.minCredits) {
            return "credits below " + config.minCredits;
        }
        return null;
    }
}