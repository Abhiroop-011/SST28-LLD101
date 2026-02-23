public class CgrRule implements EligibilityRule {
    @Override
    public String evaluate(StudentProfile s, RuleInput config) {
        if (s.cgr < config.minCgr) {
            return "CGR below " + config.minCgr;
        }
        return null;
    }
}