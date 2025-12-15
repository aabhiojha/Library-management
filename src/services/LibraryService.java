package services;

import models.Book;
import models.Member;

import java.sql.*;
import java.time.LocalDate;

public class LibraryService {
    private Connection conn;

    public LibraryService(Connection conn) {
        this.conn = conn;
    }

    public void addBook(Book book) {
        try (PreparedStatement pst = conn.prepareStatement("insert into book (title, author, is_available) values (?, ?, ?)")) {
            pst.setString(1, book.getTitle());
            pst.setString(2, book.getAuthor());
            pst.setBoolean(3, book.isAvailable());
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void registerMember(Member member) {
        try (PreparedStatement pst = conn.prepareStatement("insert into member (name) values (?)")) {
            pst.setString(1, member.getName());
            int rows_affected = pst.executeUpdate();
            System.out.println(rows_affected + " rows affected");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void borrowBook(int memberId, int bookId) {
        try (PreparedStatement pstt = conn.prepareStatement("select * from borrow_record where book_id = ? and member_id = ? and return_date is null")) {
            pstt.setInt(1, bookId);
            pstt.setInt(2, memberId);
            ResultSet rs = pstt.executeQuery();
            int i = 0;
            while (rs.next()) {
                i++;
            }
            if (i > 0) {
                System.out.println("Pending Book return. cant take it!!");
            } else {
                try (PreparedStatement pst = conn.prepareStatement("insert into borrow_record (member_id, book_id, borrow_date) values(?,?,?)")) {
                    pst.setInt(1, memberId);
                    pst.setInt(2, bookId);
                    pst.setDate(3, Date.valueOf(LocalDate.now()));
                    int rows_affected = pst.executeUpdate();
                    System.out.println(rows_affected + " rows affected");
                    if (rows_affected > 0) {
                        try (PreparedStatement ps = conn.prepareStatement("update book set is_available=false where id = ?")) {
                            ps.setInt(1, bookId);
                            int update = ps.executeUpdate();
                            System.out.println("Book availability status set to false");
                        }
                    }
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void returnBook(int memberId, int bookId) {
        try (PreparedStatement pst = conn.prepareStatement("update borrow_record set return_date = ? where member_id = ? and book_id = ?")) {
            pst.setDate(1, Date.valueOf(LocalDate.now()));
            pst.setInt(2, memberId);
            pst.setInt(3, bookId);
            int rows_affected = pst.executeUpdate();
            System.out.println(rows_affected + " rows affected");
            if (rows_affected > 0) {
                try (PreparedStatement ps = conn.prepareStatement("update book set is_available=true where id = ?")) {
                    ps.setInt(1, bookId);
                    int update = ps.executeUpdate();
                    System.out.println("Book availability status set to true");
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void viewAvailableBooks() {
        try (PreparedStatement pst = conn.prepareStatement(("Select * from book where is_available=true"))) {
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String author = rs.getString("author");
                System.out.println("Book Id: " + id + " Title: " + title + " author: " + author);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
