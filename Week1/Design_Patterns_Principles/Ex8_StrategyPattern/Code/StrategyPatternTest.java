public class StrategyPatternTest {
    public static void main(String[] args) {
        PaymentContext context = new PaymentContext();

        System.out.println("Testing Credit Card Payment");
        PaymentStrategy creditCard = new CreditCardPayment("Alice Smith", "1234-5678-9012-3456", "123", "12/28");
        context.setPaymentStrategy(creditCard);
        context.executePayment(250.75);

        System.out.println("Testing PayPal Payment");
        PaymentStrategy paypal = new PayPalPayment("alice@example.com", "mySecurePassword");
        context.setPaymentStrategy(paypal);
        context.executePayment(89.99);
    }
}
