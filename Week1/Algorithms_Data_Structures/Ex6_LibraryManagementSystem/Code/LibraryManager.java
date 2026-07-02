public class LibraryManager {
    public static Book linearSearch(Book[] books, String title) {
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                return book;
            }
        }
        return null;
    }

    public static Book binarySearch(Book[] books, String title) {
        int low = 0;
        int high = books.length - 1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            int comp = books[mid].getTitle().compareToIgnoreCase(title);
            if (comp == 0) {
                return books[mid];
            } else if (comp < 0) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return null;
    }
}
