public class SizeConstrainedExporter extends Exporter {
    private final Exporter delegate;
    private final int maxLength;

    public SizeConstrainedExporter(Exporter delegate, int maxLength) {
        this.delegate = delegate;
        this.maxLength = maxLength;
    }

    @Override
    protected ExportResult doExport(ExportRequest req) {
        if (req.body != null && req.body.length() > maxLength) {
            throw new IllegalArgumentException("PDF cannot handle content > " + maxLength + " chars");
        }
        return delegate.export(req);
    }
}