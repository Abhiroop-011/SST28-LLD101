import java.util.*;

public class FakeDb implements StudentRepository {
    private final List<StudentRecord> rows = new ArrayList<>();

    @Override
    public void save(StudentRecord r) { rows.add(r); }
    @Override
    public int count() { return rows.size(); }
    @Override
    public List<StudentRecord> all() { return Collections.unmodifiableList(rows); }
}