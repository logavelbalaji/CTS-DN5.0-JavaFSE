public class Main {
    public static void main(String[] args) {
        System.out.println("=== Task Management System ===");
        TaskLinkedList list = new TaskLinkedList();
        list.add(new Task("T001", "Design Database for Aarav", "Pending"));
        list.add(new Task("T002", "Code Review with Bhavna", "In Progress"));
        list.add(new Task("T003", "Budget Allocation of Rs. 20000", "Completed"));
        list.add(new Task("T004", "Deploy Application with Divya", "Pending"));
        System.out.println("\nAll Tasks (Initial Traversal):");
        list.traverse();
        System.out.println("\nSearching for Task with ID T002:");
        Task found = list.search("T002");
        if (found != null) {
            System.out.println("Found: " + found);
        } else {
            System.out.println("Task T002 not found.");
        }
        System.out.println("\nDeleting Task with ID T003:");
        boolean deleted = list.delete("T003");
        System.out.println("Deletion Status: " + (deleted ? "Success" : "Failed"));
        System.out.println("\nAll Tasks (Post Deletion Traversal):");
        list.traverse();
        System.out.println("\nSearching for Deleted Task with ID T003:");
        Task searchDeleted = list.search("T003");
        if (searchDeleted != null) {
            System.out.println("Found: " + searchDeleted);
        } else {
            System.out.println("Task T003 not found.");
        }
        System.out.println("\n=== Conceptual & Complexity Analysis ===");
        System.out.println("1. Singly vs. Doubly Linked Lists:");
        System.out.println("   - Singly Linked List: Each node contains a single pointer to the next node. Traversal is unidirectional.");
        System.out.println("   - Doubly Linked List: Each node contains pointers to both the next and the previous nodes. Allows bidirectional traversal.");
        System.out.println("\n2. Time Complexity Analysis:");
        System.out.println("   - Add (at end): O(n) as we traverse to the tail, or O(1) if keeping a tail pointer.");
        System.out.println("   - Search (by ID): O(n) in worst/average case to traverse the nodes.");
        System.out.println("   - Traverse: O(n) to visit every node.");
        System.out.println("   - Delete (by ID): O(n) since we need to locate the target node and its predecessor.");
        System.out.println("\n3. Advantages of Linked Lists over Arrays for Dynamic Data:");
        System.out.println("   - Dynamic Size: No fixed capacity limits; grows and shrinks dynamically as needed.");
        System.out.println("   - Fast Insertion/Deletion: Nodes can be added/removed without shifting elements, requiring only O(1) pointer updates.");
        System.out.println("   - Efficient Memory Utilization: No pre-allocated array memory blocks, avoiding memory underutilization.");
    }
}
