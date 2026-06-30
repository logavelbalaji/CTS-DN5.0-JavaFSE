public class AdapterPatternTest {
    public static void main(String[] args) {
        PaypalGateway paypalGateway = new PaypalGateway();
        PaymentProcessor paypalProcessor = new PaypalAdapter(paypalGateway);
        paypalProcessor.processPayment(150.00);

        StripeGateway stripeGateway = new StripeGateway();
        PaymentProcessor stripeProcessor = new StripeAdapter(stripeGateway);
        stripeProcessor.processPayment(275.50);
    }
}
