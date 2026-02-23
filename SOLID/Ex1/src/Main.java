import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Student Onboarding ===");
        
        StudentRepository db = new FakeDb();
        InputParser parser = new InputParser();
        StudentValidator validator = new StudentValidator(Arrays.asList("CSE", "AI", "SWE")); 
        ConsolePrinter printer = new ConsolePrinter();
        
        OnboardingService svc = new OnboardingService(db, parser, validator, printer);

        String raw1 = "name=Riya;email=riya@sst.edu;phone=9876543210;program=CSE";
        svc.registerFromRawInput(raw1);
        System.out.println();
        
        //Added-testcase (to check incase of failing)
        String raw2 = "name=;email=bademail;phone=abc;program=ART";
        svc.registerFromRawInput(raw2);

        System.out.println("\n-- DB DUMP --");
        System.out.print(TextTable.render3(db));
    }
}