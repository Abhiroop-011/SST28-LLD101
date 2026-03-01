public interface IReportWriter {
    String write(Submission s, int plag, int code);
}