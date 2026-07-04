public class SimpleApp {
    public boolean checkLoanEligibility(int age, double salary) {
        if (age > 60) {
            return salary >= 30000.00;
        }
        return salary >= 50000.00;
    }
}
