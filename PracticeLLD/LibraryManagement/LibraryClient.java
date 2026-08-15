import java.util.ArrayList;
import java.util.List;

class Book {

    private int id;
    private String title;
    private String author;
    private boolean available;

    public Book(int id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.available = true;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public boolean isAvailable() {
        return available;
    }

    public void borrow() {
        if (!available) {
            System.out.println("Book is already borrowed");
            return;
        }

        available = false;
        System.out.println("Book borrowed successfully");
    }

    public void returnBook() {
        if (available) {
            System.out.println("Book is already available");
            return;
        }

        available = true;
        System.out.println("Book returned successfully");
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", available=" + available +
                '}';
    }
}


class Library {

    private List<Book> books = new ArrayList<>();

    // Add a book
    public void addBook(Book book) {
        books.add(book);
        System.out.println("Book added successfully");
    }

    // Remove a book
    public void removeBook(int bookId) {

        for (Book book : books) {

            if (book.getId() == bookId) {
                books.remove(book);
                System.out.println("Book removed successfully");
                return;
            }
        }

        System.out.println("Book not found");
    }

    // Search book by title
    public Book searchBook(String title) {

        for (Book book : books) {

            if (book.getTitle().equalsIgnoreCase(title)) {
                return book;
            }
        }

        System.out.println("Book not found");
        return null;
    }

    // Display all books
    public void displayBooks() {

        if (books.isEmpty()) {
            System.out.println("Library is empty");
            return;
        }

        for (Book book : books) {
            System.out.println(book);
        }
    }

    // Borrow book
    public void borrowBook(int bookId) {

        for (Book book : books) {

            if (book.getId() == bookId) {
                book.borrow();
                return;
            }
        }

        System.out.println("Book not found");
    }

    // Return book
    public void returnBook(int bookId) {

        for (Book book : books) {

            if (book.getId() == bookId) {
                book.returnBook();
                return;
            }
        }

        System.out.println("Book not found");
    }
}


public class LibraryClient {

    public static void main(String[] args) {

        Library library = new Library();

        // Add books
        library.addBook(
                new Book(111, "C++", "Umesh")
        );

        library.addBook(
                new Book(112, "DSA", "Pavan")
        );

        library.addBook(
                new Book(113, "Java", "Einstein")
        );

        System.out.println("\n--- All Books ---");
        library.displayBooks();

        // Search
        System.out.println("\n--- Search Book ---");

        Book book = library.searchBook("DSA");

        if (book != null) {
            System.out.println(book);
        }

        // Borrow
        System.out.println("\n--- Borrow Book 112 ---");
        library.borrowBook(112);

        // Try borrowing again
        System.out.println("\n--- Borrow Book 112 Again ---");
        library.borrowBook(112);

        // Return
        System.out.println("\n--- Return Book 112 ---");
        library.returnBook(112);

        // Try returning again
        System.out.println("\n--- Return Book 112 Again ---");
        library.returnBook(112);

        // Remove
        System.out.println("\n--- Remove Book 111 ---");
        library.removeBook(111);

        // Final display
        System.out.println("\n--- Final Books ---");
        library.displayBooks();
    }
}

// import java.util.*;
// class LibraryManager{
//     Library lb;
//     public void addBook(int id,String title,String author){
//         lb.addBook(id,title,author);
//     }
//     public void removeBook(int id){
//         lb.removeBook(id);
//     }
//     public void display(){
//         lb.display();
//     }
//     public Book searchBook(String title){
//         return lb.searchBook(title);
//     }
//     public void borrow(int id){
//         lb.borrow(id);
//     }
//     public void returnBook(int id){
//         lb.returnBook(id);
//     }

// }
// class Library{
//     private List<Book> books = new ArrayList<>();
//     BorrowManager bm;
//     // Library(BorrowManager bm){
//     //     this.bm= bm;
//     // }
//     public void addBook(int id,String title, String author){
//         Book book = new Book(id,title,author);
//         books.add(book);
//     }
//     public void removeBook(int id){
//         for(Book b:books){
//             if(b.id==id){
//                 books.remove(b);
//                 return;
//             }
//         }
//     }
//     public void display(){
//         for(Book b:books){
//             System.out.println(b.toString());
//         }
//     }
//     public Book searchBook(String title){
//         for(Book b:books){
//             if(b.title.equals(title)){
//                 return b;
//             }
//         }
//         System.out.println("Book not found");
//         return null;

//     }
//     public void borrow(int id){
//         bm.borrow(books, id);
//     }
//     public void returnBook(int id){
//         bm.returnBook(books, id);
//     }


// }
// class BorrowManager{
//     public void borrow(List<Book> books,int id){
//         for(Book b:books){
//             if(b.id==id){
//                 if(b.status==true){
//                     System.out.println("Book borrowed");
//                     b.status=false;
//                     return;
//                 } else System.out.println("Book not available");
//             }
//         }
//         System.out.println("Book not found");
//     }
//     public void returnBook(List<Book> books,int id){
//         for(Book b:books){
//             if(b.id==id){
//                 if(b.status==false){
//                     System.out.println("Book returned");
//                     b.status=true;
//                     return;
//                 } else System.out.println("Book already returned");
//             }
//         }
//         System.out.println("Book not found");
//     }
// }
// class Book{
//     int id;
//     String title;
//     String author;
//     Boolean status;
//     public Book(int id,String title,String author){
//         this.id=id;
//         this.title=title;
//         this.author = author;
//         this.status = true;
//     }
// }


// public class LibraryClient {
//     public static void main(String[] args) {
//         LibraryManager lm = new LibraryManager() ;
//         lm.addBook(111,"C++","Umesh");
//         lm.addBook(112,"DSA","Pavan");
//         lm.addBook(113,"Java","Eistien");
//         lm.removeBook(111);
//         lm.display();
//         lm.searchBook("DSA");
//         lm.borrow(112);
//         lm.returnBook(112);
//     }

// }
