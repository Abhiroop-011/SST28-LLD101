import java.util.*;

public class Menu {
    private final Map<String, MenuItem> items = new LinkedHashMap<>();
    public void add(MenuItem item) { items.put(item.id, item); }
    public MenuItem get(String id) { return items.get(id); }
}