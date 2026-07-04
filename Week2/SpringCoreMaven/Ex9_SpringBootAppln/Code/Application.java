import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import jakarta.persistence.EntityManager;

@SpringBootConfiguration
@EnableAutoConfiguration
@Import(BookController.class)
@EntityScan(basePackageClasses = Book.class)
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public BookRepository bookRepository(EntityManager entityManager) {
        return new JpaRepositoryFactory(entityManager).getRepository(BookRepository.class);
    }

    @Bean
    public CommandLineRunner initData(BookRepository bookRepository) {
        return args -> {
            Book book1 = new Book();
            book1.setTitle("Ancient India Guide");
            book1.setAuthor("Aarav Sharma");
            book1.setPrice(450.0);
            bookRepository.save(book1);
            Book book2 = new Book();
            book2.setTitle("Modern Indian Economy");
            book2.setAuthor("Bhavna Patel");
            book2.setPrice(550.0);
            bookRepository.save(book2);
        };
    }
}
