import java.util.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Placement Eligibility ===");
        StudentProfile s = new StudentProfile("23BCS1001", "Ayaan", 8.10, 72, 18, LegacyFlags.NONE);

        RuleInput config = new RuleInput();

        List<EligibilityRule> rules = Arrays.asList(
            new DisciplinaryFlagRule(),
            new CgrRule(),
            new AttendanceRule(),
            new CreditsRule()
        );

        EligibilityEngine engine = new EligibilityEngine(rules, config);
        FakeEligibilityStore store = new FakeEligibilityStore();
        ReportPrinter printer = new ReportPrinter();

        EligibilityEngineResult result = engine.evaluate(s);
        
        printer.print(s, result);
        store.save(s.rollNo, result.status);
    }
}