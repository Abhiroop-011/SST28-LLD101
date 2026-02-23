import java.nio.charset.StandardCharsets;

public class CsvExporter implements Exporter {
    @Override
    public ExportResult export(ExportRequest req) {
        String body = req.body == null ? "" : req.body;
        if (body.contains(",") || body.contains("\n")) {
            body = "\"" + body.replace("\"", "\"\"") + "\""; 
        }
        String csv = "title,body\n" + req.title + "," + body + "\n";
        return new ExportResult("text/csv", csv.getBytes(StandardCharsets.UTF_8));
    }
}