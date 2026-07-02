public class Main {
    public static void main(String[] args) {
        System.out.println("=== Employee Management System ===");
        EmployeeManager manager = new EmployeeManager(5);
        manager.add(new Employee("E001", "Aarav Sharma", "Software Engineer", 80000.00));
        manager.add(new Employee("E002", "Bhavna Patel", "Project Manager", 120000.00));
        manager.add(new Employee("E003", "Chirag Sen", "QA Analyst", 65000.00));
        manager.add(new Employee("E004", "Divya Reddy", "HR Specialist", 75000.00));
        System.out.println("\nAll Employees (Initial Traversal):");
        manager.traverse();
        System.out.println("\nSearching for Employee with ID E002:");
        Employee found = manager.search("E002");
        if (found != null) {
            System.out.println("Found: " + found);
        } else {
            System.out.println("Employee E002 not found.");
        }
        System.out.println("\nDeleting Employee with ID E003 (Chirag Sen):");
        boolean deleted = manager.delete("E003");
        System.out.println("Deletion Status: " + (deleted ? "Success" : "Failed"));
        System.out.println("\nAll Employees (Post Deletion Traversal):");
        manager.traverse();
        System.out.println("\nSearching for Deleted Employee with ID E003:");
        Employee searchDeleted = manager.search("E003");
        if (searchDeleted != null) {
            System.out.println("Found: " + searchDeleted);
        } else {
            System.out.println("Employee E003 not found.");
        }
        System.out.println("\n=== Conceptual & Complexity Analysis ===");
        System.out.println("1. Memory Representation of Arrays:");
        System.out.println("   - Represented as a contiguous block of memory where each element is of the same data type.");
        System.out.println("   - The memory address of any element at index 'i' is computed in O(1) time using:");
        System.out.println("     Address = Base Address + (i * Element Size)");
        System.out.println("   - Advantages: Fast O(1) random access, minimal memory overhead, and cache-friendly contiguous layout.");
        System.out.println("\n2. Time Complexity Analysis:");
        System.out.println("   - Add (at end): O(1) when capacity is available.");
        System.out.println("   - Search (by attribute): O(n) linear search, since elements are unsorted.");
        System.out.println("   - Traverse: O(n) to visit and print every element.");
        System.out.println("   - Delete (by ID): O(n) since elements must be shifted to keep the array contiguous.");
        System.out.println("\n3. Limitations of Arrays:");
        System.out.println("   - Fixed Size: Cannot grow or shrink dynamically once initialized.");
        System.out.println("   - Costly Insertions/Deletions: Requires shifting elements to maintain index order.");
        System.out.println("   - Pre-allocated Memory: Can result in wasted memory if capacity is underutilized.");
        System.out.println("\n4. When to Use Arrays:");
        System.out.println("   - When the size of the dataset is predetermined and fixed.");
        System.out.println("   - When memory footprint needs to be minimized.");
        System.out.println("   - When frequent random access is required.");
    }
}
