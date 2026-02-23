public class AttendanceRule implements EligibilityRule {
    @Override
    public String evaluate(StudentProfile s, RuleInput config) {
        if (s.attendancePct < config.minAttendance) {
            return "attendance below " + config.minAttendance;
        }
        return null;
    }
}