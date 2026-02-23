import java.util.List;

public class OnboardingService {
    private final StudentRepository db;
    private final InputParser parser;
    private final StudentValidator validator;
    private final ConsolePrinter printer;

    public OnboardingService(StudentRepository db, InputParser parser, StudentValidator validator, ConsolePrinter printer) { 
        this.db = db; 
        this.parser = parser;
        this.validator = validator;
        this.printer = printer;
    }

    public void registerFromRawInput(String raw) {
        printer.printInput(raw);

        ParsedInput input = parser.parse(raw);
        List<String> errors = validator.validate(input);

        if (!errors.isEmpty()) {
            printer.printErrors(errors);
            return; 
        }

        String id = IdUtil.nextStudentId(db.count());
        StudentRecord rec = new StudentRecord(id, input.name, input.email, input.phone, input.program);
        
        db.save(rec);
        
        printer.printSuccess(rec, db.count());
    }
}