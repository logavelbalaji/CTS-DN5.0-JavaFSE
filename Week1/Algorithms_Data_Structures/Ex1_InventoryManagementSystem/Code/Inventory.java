import java.util.HashMap;
import java.util.Map;
public class Inventory {
    private Map<String, Product> products = new HashMap<>();
    public void addProduct(Product product) {
        if (product == null) {
            return;
        }
        if (products.containsKey(product.getProductId())) {
            return;
        }
        products.put(product.getProductId(), product);
    }
    public void updateProduct(String productId, Product updatedProduct) {
        if (updatedProduct == null || !products.containsKey(productId)) {
            return;
        }
        products.put(productId, updatedProduct);
    }
    public void deleteProduct(String productId) {
        products.remove(productId);
    }
    public Product getProduct(String productId) {
        return products.get(productId);
    }
    public void displayAllProducts() {
        for (Product product : products.values()) {
            System.out.println(product);
        }
    }
}
