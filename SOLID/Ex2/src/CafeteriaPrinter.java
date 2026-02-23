public class CafeteriaPrinter {
    public void printInvoice(String formattedText, String invId, int savedLines) {
        System.out.print(formattedText);
        System.out.println("Saved invoice: " + invId + " (lines=" + savedLines + ")");
    }
}