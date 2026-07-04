public class LibraryManagementApplication {
    public static void main(String[] args) {
        BookRepository repo = new BookRepository();
        BookService service = new BookService();
        service.setBookRepository(repo);
        service.printBookDetails();
    }
}
