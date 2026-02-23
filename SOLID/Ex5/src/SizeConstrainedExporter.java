// SizeConstrainedExporter.java
public class SizeConstrainedExporter implements Exporter {
    private final Exporter delegate;
    private final int maxLength;
    private final String formatName;

    public SizeConstrainedExporter(Exporter delegate, int maxLength, String formatName) {
        this.delegate = delegate;
        this.maxLength = maxLength;
        this.formatName = formatName;
    }

    @Override
    public ExportResult export(ExportRequest req) {
        if (req != null && req.body != null && req.body.length() > maxLength) {
            throw new IllegalArgumentException(formatName + " cannot handle content > " + maxLength + " chars");
        }
        return delegate.export(req);
    }
}