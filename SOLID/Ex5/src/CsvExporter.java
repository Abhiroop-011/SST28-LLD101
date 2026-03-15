import java.nio.charset.StandardCharsets;
public class CsvExporter extends Exporter {
    @Override
    protected ExportResult doExport(ExportRequest req) {
        String safeTitle = escapeCsv(req.title);
        String safeBody = escapeCsv(req.body);
        String csv = "title,body\n" + safeTitle + "," + safeBody + "\n";
        return new ExportResult("text/csv", csv.getBytes(StandardCharsets.UTF_8));
    }

    private String escapeCsv(String val) {
        if (val == null) return "";
        if (val.contains("\"") || val.contains(",") || val.contains("\n")) {
            return "\"" + val.replace("\"", "\"\"") + "\"";
        }
        return val;
    }
}