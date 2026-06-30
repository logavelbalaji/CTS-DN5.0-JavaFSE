public class CreditCardPayment implements PaymentStrategy {
    private String cardHolderName;
    private String cardNumber;
    private String cvv;
    private String expirationDate;

    public CreditCardPayment(String cardHolderName, String cardNumber, String cvv, String expirationDate) {
        this.cardHolderName = cardHolderName;
        this.cardNumber = cardNumber;
        this.cvv = cvv;
        this.expirationDate = expirationDate;
    }

    @Override
    public void pay(double amount) {
        System.out.println("Paid Rs. " + amount + " using Credit Card.");
    }
}
