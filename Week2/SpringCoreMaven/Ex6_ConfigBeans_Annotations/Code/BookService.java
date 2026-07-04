import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    public void printBookDetails() {
        if (bookRepository != null) {
            System.out.println(bookRepository.getBookDetails());
        } else {
            System.out.println("BookRepository is not configured yet");
        }
    }
}
