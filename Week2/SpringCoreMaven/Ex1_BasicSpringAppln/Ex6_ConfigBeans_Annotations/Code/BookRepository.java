import org.springframework.stereotype.Repository;

@Repository
public class BookRepository {
    public String getBookDetails() {
        return "Book: Ancient India History, Price: Rs. 450";
    }
}
