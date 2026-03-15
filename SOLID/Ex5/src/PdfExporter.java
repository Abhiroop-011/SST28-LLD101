import java.nio.charset.StandardCharsets;
public class PdfExporter extends Exporter {

    private final ExportValidator validator = new PdfValidator();

    @Override
    protected ExportResult doExport(ExportRequest req) {
        
        validator.validate(req); 
        
        String fakePdf = "PDF(" + req.title + "):" + req.body;
        return new ExportResult("application/pdf", fakePdf.getBytes(StandardCharsets.UTF_8));
    }
}