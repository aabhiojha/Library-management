package models;

public class Book {
    private int id;
    private String title;
    private String author;
    private boolean isAvailable;

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public Book(String bookTitle, String bookAuthor, boolean bookIsAvailable) {
        this.title = bookTitle;
        this.author = bookAuthor;
        this.isAvailable = bookIsAvailable;
    }
}
