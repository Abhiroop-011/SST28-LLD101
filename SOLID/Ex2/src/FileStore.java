import java.util.*;

public class FileStore implements InvoiceStore {
    private final Map<String, String> files = new HashMap<>();

    @Override
    public void save(String name, String content) { files.put(name, content); }
    @Override
    public int countLines(String name) {
        String c = files.getOrDefault(name, "");
        return c.isEmpty() ? 0 : c.split("\n").length;
    }
}