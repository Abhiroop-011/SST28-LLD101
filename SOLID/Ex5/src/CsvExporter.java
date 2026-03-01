import java.nio.charset.StandardCharsets;
public class CsvExporter extends Exporter {
    @Override
    protected ExportResult doExport(ExportRequest req) {
        // wrapping commas and newlines in quotes to avoid CSV parsing issues
        String body = req.body == null ? "" : req.body;
        if (body.contains(",") || body.contains("\n")) {
            body = "\"" + body.replace("\"", "\"\"") + "\""; 
        }
        String csv = "title,body\n" + req.title + "," + body + "\n";
        return new ExportResult("text/csv", csv.getBytes(StandardCharsets.UTF_8));
    }
}
        