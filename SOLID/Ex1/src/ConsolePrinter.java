import java.util.List;

public class ConsolePrinter {
    public void printInput(String raw) {
        System.out.println("INPUT: " + raw);
    }

    public void printSuccess(StudentRecord rec, int totalStudents) {
        System.out.println("OK: created student " + rec.id);
        System.out.println("Saved. Total students: " + totalStudents);
        System.out.println("CONFIRMATION:");
        System.out.println(rec);
    }

    public void printErrors(List<String> errors) {
        System.out.println("ERROR: cannot register");
        for (String e : errors) {
            System.out.println("- " + e);
        }
    }
}