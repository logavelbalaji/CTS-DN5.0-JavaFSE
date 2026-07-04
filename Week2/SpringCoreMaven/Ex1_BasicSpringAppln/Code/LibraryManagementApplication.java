import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class LibraryManagementApplication {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        BookService service = (BookService) context.getBean("bookService");
        BookRepository repository = (BookRepository) context.getBean("bookRepository");
        System.out.println("BookService instance: " + service);
        System.out.println("BookRepository instance: " + repository);
        service.printBookDetails();
    }
}
