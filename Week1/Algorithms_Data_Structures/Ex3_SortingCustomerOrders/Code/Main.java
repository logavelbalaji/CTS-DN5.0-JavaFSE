import java.util.Random;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== E-Commerce Order Sorting System ===");
        Order[] sampleOrders = {
            new Order("O001", "Aarav Sharma", 250.50),
            new Order("O002", "Bhavna Patel", 120.00),
            new Order("O003", "Chirag Sen", 450.75),
            new Order("O004", "Divya Reddy", 89.99),
            new Order("O005", "Eshwar Iyer", 1250.00),
            new Order("O006", "Farhan Khan", 320.40)
        };
        System.out.println("\n--- Original Sample Orders ---");
        printOrders(sampleOrders);
        Order[] bubbleSorted = cloneOrders(sampleOrders);
        BubbleSort.sort(bubbleSorted);
        System.out.println("\n--- Orders Sorted by Bubble Sort (Ascending) ---");
        printOrders(bubbleSorted);
        Order[] quickSorted = cloneOrders(sampleOrders);
        QuickSort.sort(quickSorted);
        System.out.println("\n--- Orders Sorted by Quick Sort (Ascending) ---");
        printOrders(quickSorted);
        System.out.println("\n--- Performance Comparison (Time Complexity) ---");
        int datasetSize = 10000;
        System.out.println("Generating random dataset of " + datasetSize + " orders...");
        Order[] largeDataset = generateLargeDataset(datasetSize);
        Order[] datasetForBubble = cloneOrders(largeDataset);
        Order[] datasetForQuick = cloneOrders(largeDataset);
        long startTime = System.nanoTime();
        BubbleSort.sort(datasetForBubble);
        long endTime = System.nanoTime();
        double bubbleSortTimeMs = (endTime - startTime) / 1e6;
        System.out.printf("Bubble Sort execution time for %d elements: %.2f ms%n", datasetSize, bubbleSortTimeMs);
        startTime = System.nanoTime();
        QuickSort.sort(datasetForQuick);
        endTime = System.nanoTime();
        double quickSortTimeMs = (endTime - startTime) / 1e6;
        System.out.printf("Quick Sort execution time for %d elements:  %.2f ms%n", datasetSize, quickSortTimeMs);
        System.out.printf("Ratio (Bubble Sort / Quick Sort): %.2fx slower%n", bubbleSortTimeMs / quickSortTimeMs);
    }

    private static void printOrders(Order[] orders) {
        for (Order o : orders) {
            System.out.println("  " + o);
        }
    }

    private static Order[] cloneOrders(Order[] src) {
        Order[] dest = new Order[src.length];
        for (int i = 0; i < src.length; i++) {
            dest[i] = new Order(src[i].getOrderId(), src[i].getCustomerName(), src[i].getTotalPrice());
        }
        return dest;
    }

    private static Order[] generateLargeDataset(int size) {
        Order[] dataset = new Order[size];
        Random rand = new Random(42);
        for (int i = 0; i < size; i++) {
            String orderId = "O" + String.format("%05d", i + 1);
            String customerName = "Customer " + (i + 1);
            double totalPrice = 10.0 + (rand.nextDouble() * 1990.0);
            dataset[i] = new Order(orderId, customerName, totalPrice);
        }
        return dataset;
    }
}
