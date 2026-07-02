public class FinancialForecasting {
    public static double calculateFutureValue(double pv, double rate, int periods) {
        if (periods <= 0) {
            return pv;
        }
        return calculateFutureValue(pv, rate, periods - 1) * (1 + rate);
    }
}
