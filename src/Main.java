import models.Book;
import models.Member;
import services.LibraryService;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // start the connection
        String url = "jdbc:postgresql://localhost:5432/Library";
        String username = "postgres";
        String password = "securepassword";

        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            LibraryService ls = new LibraryService(conn);

            Scanner sc = new Scanner(System.in);
            boolean isRunning = true;
            while (isRunning) {
                System.out.println("1. Add Book\n" +
                        "2. Register Member\n" +
                        "3. Borrow Book\n" +
                        "4. Return Book\n" +
                        "5. View Available Books\n" +
                        "6. Exit\n");
                int choice = Integer.parseInt(sc.nextLine());
                switch (choice) {
                    case 1: {
                        System.out.println("Add book\n");
                        System.out.println("Enter Book title: ");
                        String book_title = sc.nextLine();
                        System.out.println("Enter Book author: ");
                        String book_author = sc.nextLine();
                        System.out.println("Is the book available: ");
                        boolean book_is_available = Boolean.parseBoolean(sc.nextLine());
                        ls.addBook(new Book(book_title, book_author, book_is_available));
                        break;
                    }
                    case 2: {
                        System.out.println("Register member function");
                        System.out.println("Add member\n");
                        System.out.println("Enter member name: ");
                        String member_name = sc.nextLine();
                        ls.registerMember(new Member(member_name));
                        break;
                    }
                    case 3: {
                        System.out.println("borrow Book function");
                        System.out.println("Enter member id: ");
                        int memberId = Integer.parseInt(sc.nextLine());
                        System.out.println("Enter book id: ");
                        int bookId = Integer.parseInt(sc.nextLine());
                        ls.borrowBook(memberId, bookId);
                        break;
                    }
                    case 4: {
                        System.out.println("Return Book function");
                        System.out.println("Enter member id: ");
                        int memberId = Integer.parseInt(sc.nextLine());
                        System.out.println("Enter book id: ");
                        int bookId = Integer.parseInt(sc.nextLine());
                        ls.returnBook(memberId, bookId);
                        break;
                    }
                    case 5: {
                        System.out.println("View available book");
                        ls.viewAvailableBooks();
                        break;
                    }
                    case 6: {
                        System.out.println("exiting");
                        isRunning = false;
                    }
                    default: {
                        System.out.println("enter a valid choice\n");
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}