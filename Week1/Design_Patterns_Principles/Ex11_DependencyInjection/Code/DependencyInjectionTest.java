public class DependencyInjectionTest {
    public static void main(String[] args) {
        CustomerRepository repository = new CustomerRepositoryImpl();

        CustomerService service = new CustomerService(repository);

        System.out.println("Finding customer with ID 1:");
        String customer1 = service.getCustomerName(1);
        System.out.println("Customer Name: " + customer1);

        System.out.println("\nFinding customer with ID 2:");
        String customer2 = service.getCustomerName(2);
        System.out.println("Customer Name: " + customer2);

        System.out.println("\nFinding customer with ID 3:");
        String customer3 = service.getCustomerName(3);
        System.out.println("Customer Name: " + customer3);
    }
}
