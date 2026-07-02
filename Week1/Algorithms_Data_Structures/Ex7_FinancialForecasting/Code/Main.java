public class Main {
    public static void main(String[] args) {
        System.out.println("=== Financial Forecasting Tool ===");
        double presentValue = 100000.00;
        double growthRate = 0.05;
        int periods = 10;
        System.out.printf("Present Value: Rs.%.2f%n", presentValue);
        System.out.printf("Annual Growth Rate: %.2f%%%n", growthRate * 100);
        System.out.printf("Forecasting Period: %d years%n", periods);
        double futureValue = FinancialForecasting.calculateFutureValue(presentValue, growthRate, periods);
        System.out.printf("Predicted Future Value: Rs.%.2f%n", futureValue);
        System.out.println("\n=== Conceptual & Complexity Analysis ===");
        System.out.println("1. Concept of Recursion:");
        System.out.println("   - Recursion is a technique where a method calls itself to solve a smaller instance of the same problem.");
        System.out.println("   - It simplifies problems by breaking them down into base cases and recursive steps, avoiding complex loop tracking.");
        System.out.println("\n2. Time and Space Complexity:");
        System.out.println("   - Time Complexity: O(n) where n is the number of periods (growth calculations).");
        System.out.println("   - Space Complexity: O(n) auxiliary space due to the recursive call stack frames.");
        System.out.println("\n3. Optimization to Avoid Excessive Computation:");
        System.out.println("   - Memoization: Store intermediate results to avoid redundant calculations in overlapping subproblems.");
        System.out.println("   - Iteration: Convert the recursive function to an iterative loop to achieve O(1) space complexity and eliminate stack overflow risk.");
    }
}
