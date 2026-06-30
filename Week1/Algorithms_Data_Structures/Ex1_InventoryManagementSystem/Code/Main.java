public class Main {
    public static void main(String[] args) {
        Inventory inventory = new Inventory();
        Product p1 = new Product("P001", "Laptop", 10, 999.99);
        Product p2 = new Product("P002", "Smartphone", 25, 499.99);
        Product p3 = new Product("P003", "Headphones", 50, 79.99);
        inventory.addProduct(p1);
        inventory.addProduct(p2);
        inventory.addProduct(p3);
        inventory.displayAllProducts();
        Product p2Updated = new Product("P002", "Smartphone", 30, 479.99);
        inventory.updateProduct("P002", p2Updated);
        inventory.displayAllProducts();
        inventory.deleteProduct("P003");
        inventory.displayAllProducts();
    }
}
