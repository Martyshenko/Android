package savern.booklistingapp;

public class Book {
    private String mAuthor;
    private String mTitle;
    private String mDescription;

    public Book(String author, String title, String description) {
        mAuthor = author;
        mTitle = title;
        mDescription = description;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public String getDescription() {
        return mDescription;
    }

}
