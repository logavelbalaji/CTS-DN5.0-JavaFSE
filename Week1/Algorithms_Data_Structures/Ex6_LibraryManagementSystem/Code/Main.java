import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Library Management System ===");
        Book[] books = {
            new Book("B001", "The Guide", "R. K. Narayan"),
            new Book("B002", "Gitanjali", "Rabindranath Tagore"),
            new Book("B003", "Midnight's Children", "Salman Rushdie"),
            new Book("B004", "Train to Pakistan", "Khushwant Singh"),
            new Book("B005", "The White Tiger", "Aravind Adiga")
        };
        System.out.println("\nAll Books (Unsorted):");
        for (Book b : books) {
            System.out.println(b);
        }
        System.out.println("\nLinear Search for 'Gitanjali':");
        Book foundLinear = LibraryManager.linearSearch(books, "Gitanjali");
        System.out.println(foundLinear != null ? "Found: " + foundLinear : "Book not found.");
        Arrays.sort(books);
        System.out.println("\nAll Books (Sorted by Title for Binary Search):");
        for (Book b : books) {
            System.out.println(b);
        }
        System.out.println("\nBinary Search for 'The White Tiger':");
        Book foundBinary = LibraryManager.binarySearch(books, "The White Tiger");
        System.out.println(foundBinary != null ? "Found: " + foundBinary : "Book not found.");
        System.out.println("\n=== Conceptual & Complexity Analysis ===");
        System.out.println("1. Linear Search vs. Binary Search:");
        System.out.println("   - Linear Search: Scans each element sequentially until a match is found. Works on unsorted data.");
        System.out.println("   - Binary Search: Divide-and-conquer algorithm. Requires the dataset to be sorted. Repeatedly halves the search interval.");
        System.out.println("\n2. Time Complexity Analysis:");
        System.out.println("   - Linear Search: Best Case O(1), Worst Case O(n), Average Case O(n).");
        System.out.println("   - Binary Search: Best Case O(1), Worst Case O(log n), Average Case O(log n).");
        System.out.println("\n3. When to Use Each Algorithm:");
        System.out.println("   - Linear Search: Best for small, unsorted datasets, or when data is frequently modified and sorting overhead is too high.");
        System.out.println("   - Binary Search: Best for large, static datasets that are searched frequently. The cost of sorting is amortized over multiple searches.");
    }
}
