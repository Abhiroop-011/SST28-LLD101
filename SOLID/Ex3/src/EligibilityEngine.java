import java.util.*;

public class EligibilityEngine {
    private final List<EligibilityRule> rules;
    private final RuleInput config;

    public EligibilityEngine(List<EligibilityRule> rules, RuleInput config) {
        this.rules = rules;
        this.config = config;
    }

    public EligibilityEngineResult evaluate(StudentProfile s) {
        List<String> reasons = new ArrayList<>();
        String status = "ELIGIBLE";

        for (EligibilityRule rule : rules) {
            String failureReason = rule.evaluate(s, config);
            if (failureReason != null) {
                status = "NOT_ELIGIBLE";
                reasons.add(failureReason);
                break; 
            }
        }

        return new EligibilityEngineResult(status, reasons);
    }
}

class EligibilityEngineResult {
    public final String status;
    public final List<String> reasons;
    
    public EligibilityEngineResult(String status, List<String> reasons) {
        this.status = status;
        this.reasons = reasons;
    }
}