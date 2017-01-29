package savern.booklistingapp;

public class Book {
    private String mAuthor;
    private String mTitle;

    public Book(String author, String title){
        mAuthor = author;
        mTitle =title;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getAuthor() {
        return mAuthor;
    }

}
