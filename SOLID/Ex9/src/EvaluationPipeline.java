public class EvaluationPipeline {
    private final IPlagiarismChecker pc;
    private final ICodeGrader grader;
    private final IReportWriter writer;
    private final Rubric rubric;

    /// Dependency Injection
    public EvaluationPipeline(IPlagiarismChecker pc, ICodeGrader grader, IReportWriter writer, Rubric rubric) {
        this.pc = pc;
        this.grader = grader;
        this.writer = writer;
        this.rubric = rubric;
    }

    public void evaluate(Submission sub) {
        int plag = pc.check(sub);
        System.out.println("PlagiarismScore=" + plag);

        int code = grader.grade(sub, rubric);
        System.out.println("CodeScore=" + code);

        String reportName = writer.write(sub, plag, code);
        System.out.println("Report written: " + reportName);

        int total = plag + code;
        String result = (total >= 90) ? "PASS" : "FAIL";
        System.out.println("FINAL: " + result + " (total=" + total + ")");
    }
}