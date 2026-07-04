public class BookService {
    private BookRepository bookRepository;

    public void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public void printBookDetails() {
        if (bookRepository != null) {
            System.out.println(bookRepository.getBookDetails());
        } else {
            System.out.println("BookRepository is not configured yet");
        }
    }
}
