// It's never turned off, so PowerControl isn't required
public class AttendanceScanner implements Scanner {
    @Override public int scanAttendance() { return 3; }
}