public class PdfValidator implements ExportValidator {
    @Override
    public void validate(ExportRequest req) {
        if (req.body != null && req.body.length() > 20) {
            throw new IllegalArgumentException("PDF cannot handle content > 20 chars");
        }
    }
}