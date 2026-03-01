public interface IPaymentGateway {
    String charge(String studentId, double amount);
}