import java.nio.charset.StandardCharsets;

public class PdfExporter implements Exporter {
    @Override
    public ExportResult export(ExportRequest req) {
        String body = req.body == null ? "" : req.body;
        String fakePdf = "PDF(" + req.title + "):" + body;
        return new ExportResult("application/pdf", fakePdf.getBytes(StandardCharsets.UTF_8));
    }
}