public class CustomerRepositoryImpl implements CustomerRepository {
    @Override
    public String findCustomerById(int id) {
        if (id == 1) {
            return "John Doe";
        } else if (id == 2) {
            return "Jane Smith";
        }
        return "Customer Not Found";
    }
}
