package models;

import java.util.List;

public class Member {
    private int memberId;
    private String name;
    private List<BorrowRecord> borrowedBooks;

    public Member(String member_name){
        this.name = member_name;
    }

    public String getName() {
        return name;
    }

    public List<BorrowRecord> getBorrowedBooks() {
        return borrowedBooks;
    }
}
