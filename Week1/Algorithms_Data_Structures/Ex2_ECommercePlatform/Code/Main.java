import java.util.Arrays;
public class Main {
    public static void main(String[] args) {
        Product[] products = {
            new Product("P003", "Wireless Mouse", "Electronics"),
            new Product("P001", "Gaming Laptop", "Electronics"),
            new Product("P004", "Desk Organizer", "Office Supplies"),
            new Product("P002", "Bluetooth Speaker", "Electronics")
        };
        Product result1 = Search.linearSearch(products, "P002");
        System.out.println("Linear Search result for P002: " + result1);
        Arrays.sort(products);
        Product result2 = Search.binarySearch(products, "P002");
        System.out.println("Binary Search result for P002: " + result2);
        Product result3 = Search.binarySearch(products, "P099");
        System.out.println("Binary Search result for P099: " + result3);
    }
}
