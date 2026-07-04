import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class LibraryManagementApplication {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        BookService serviceConstructor = (BookService) context.getBean("bookServiceConstructor");
        BookService serviceSetter = (BookService) context.getBean("bookServiceSetter");
        System.out.println("Constructor Injection details:");
        serviceConstructor.printBookDetails();
        System.out.println("Setter Injection details:");
        serviceSetter.printBookDetails();
    }
}
