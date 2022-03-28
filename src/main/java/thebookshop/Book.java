package thebookshop;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.html.ImageView;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.Raster;

public class Book {
    private String title;
    private String author;
    private String imageLink;
    private int year;
    private float price;
    protected String indexer;
    private String language;
    private String country;
    private String link;
    private int pages;
    protected ImageIcon bookCoverImage;

    /* /**
      * Creates a book object
      * @param title title of book
      * @param author author of book (fullname)
      * @param imageLink image url of book cover
      * @param year the year book was published
      * @param description description of book
      * @param price rental price of book

     public Book(String title, String author, String imageLink, String year, String description, float price) {
         this.title = title;
         this.author = author;
         this.imageLink = imageLink;
         this.year = year;
         this.description = description;
         this.price = price;

         bgImage =
                 new ImageIcon(Book.class.getResource("/"+ imageLink));

         indexer =
                 title.toLowerCase() + "-" + author.toLowerCase() + "-" + year.toLowerCase();

     }*/
    @JsonIgnoreProperties(ignoreUnknown = true)
    Book() {
        this.title = "";
        this.author = "";
        this.year = 0;
        this.price = 0;
        this.language = "";
        this.link = "";
        this.pages = 0;
        this.country = "";
        this.imageLink = "";
        this.indexer = "";
    }

    Book(String title, String author, int year, float price, String language, String link, int pages, String country,
         String imageLink) {
        this.title = title;
        this.author = author;
        this.year = year;
        this.price = price;
        this.language = language;
        this.link = link;
        this.pages = pages;
        this.country = country;
        this.imageLink = imageLink;
        bookCoverImage = null;
        indexer =
                title.toLowerCase() + "-" + author.toLowerCase() + "-" + year;
    }

    Book(Book book) {
        this.title = book.title;
        this.author = book.author;
        this.year = book.year;
        this.price = book.price;
        this.language = book.language;
        this.link = book.link;
        this.pages = book.pages;
        this.country = book.country;
        this.imageLink = book.imageLink;
        this.indexer = book.indexer;
    }

    /**
     * Creates book cover gui
     *
     * @return a JLabel with the photo of the book cover as background
     */
    public JLabel createCover() {
        JLabel cover = new JLabel();
        cover.setIcon(bookCoverImage);
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

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
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

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public ImageIcon retrieveBookCoverImage() {
        return bookCoverImage;
    }

    public void addBookCoverImage(ImageIcon bookCoverImage) {
        this.bookCoverImage = bookCoverImage;
    }
}
