package thebookshop;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;

public class Book  {
    private String title;
    private String author;
    private String imageUrl;
    private String publishedYear;
    private String description;
    private float price;
    protected String indexer;
    ImageIcon bgImage;

    /**
     * Creates a book object
     * @param title title of book
     * @param author author of book (fullname)
     * @param imageUrl image url of book cover
     * @param publishedYear the year book was published
     * @param description description of book
     * @param price rental price of book
     */
    public Book(String title, String author, String imageUrl, String publishedYear, String description, float price) {
        this.title = title;
        this.author = author;
        this.imageUrl = imageUrl;
        this.publishedYear = publishedYear;
        this.description = description;
        this.price = price;
        bgImage =
                new ImageIcon(Book.class.getResource("/"+imageUrl));

        indexer =
                title.toLowerCase() + "-" + author.toLowerCase() + "-" + publishedYear.toLowerCase();

    }

    /**
     * Creates a copy of a book
     * @param book the book to copy
     */
    public Book(Book book) {
        author = book.author;
        title = book.title;
        imageUrl = book.imageUrl;
        publishedYear = book.publishedYear;
        description = book.description;
        price = book.price;
        indexer = book.indexer;
        bgImage = book.bgImage;
        ;
    }

    /**
     * Creates book cover gui
     * @return a JLabel with the photo of the book cover as background
     */
    public JLabel getCover(){
        JLabel cover = new JLabel();
        cover.setIcon(bgImage);
        return cover;
    }

    //Getters and setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getPublishedYear() {
        return publishedYear;
    }

    public void setPublishedYear(String publishedYear) {
        this.publishedYear = publishedYear;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getIndexer() {
        return indexer;
    }

    public void setIndexer(String indexer) {
        this.indexer = indexer;
    }

}
