package thebookshop;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;

import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Books extends RBT {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private File userFile;
    private ArrayList<Book> books;
    private File booksFile;
    private final int RENT_LIMIT = 5;

    private LinkedList<Book> rentedBooks = new LinkedList<>();

    /**
     * Initialize book store
     */
    public Books() {
        super();

        try {
            String path = Book.class.getResource("/").getFile().split(":")[1];
            Files.createDirectories(Paths.get(path + "user"));

            userFile = new File(path + "user/rentedBooks.json");

            if (!userFile.exists()) {
                userFile.createNewFile();
            }

            if (userFile.length() > 0) {
                rentedBooks =
                        objectMapper.readValue(userFile,
                                new TypeReference<LinkedList<Book>>() {
                                });
                rentedBooks.forEach(book -> book.addBookCoverImage(new ImageIcon(Book.class.getResource("/" + book.getImageLink()))));
            }
            //
            //
            //
            booksFile = new File(path + "app_files/books.json");

            if (!booksFile.exists()) {
                booksFile.createNewFile();
            }

            if (booksFile.length() <= 0) {
                books = new ArrayList<>();
            } else {
                books =
                        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).readValue(booksFile,
                                new TypeReference<List<Book>>() {
                                });
            }
        } catch (MismatchedInputException ex) {
            ex.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        addBooks();
    }

    /**
     * Searches store for a book
     *
     * @param title         Title of book
     * @param author        Author's full name
     * @param publishedYear Year book was published
     * @return The book that was found or null
     * @see Book Book
     */
    public Book findBook(String title, String author, String publishedYear) {
        String key = title.toLowerCase() + "-" + author.toLowerCase() + "-" + publishedYear.toLowerCase();
        Node result = search(key);
        if (result != null) {
            return result.book;
        } else {
            return null;
        }
    }

    /**
     * Searches for a book and "rents" it
     *
     * @param indexer The indexer of the book  to be rented
     * @return true if the book was rented, false otherwise
     * @see Book Book
     */
    int b = 0;
    public boolean rentBook(String indexer) {
        try {
            Node bookToRent = delete(indexer);
            b++;
            System.out.println("d-c: "+b);

            if (bookToRent != null) {
                rentedBooks.add(bookToRent.book);
                books.remove(bookToRent.book);

                FileWriter writer = new FileWriter(userFile, false);
                objectMapper.writeValue(writer, rentedBooks);

                writer = new FileWriter(booksFile, false);
                objectMapper.writeValue(writer, books);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Return rented book to store
     *
     * @param book Book to return
     * @see Book Book
     */
    int t = 0;
    public void returnBook(Book book) {
        try {
            insert(book);
            t++;
            if (rentedBooks.contains(book)){
                rentedBooks.remove(book);
            }
            books.add(book);
            FileWriter writer = new FileWriter(userFile, false);
            objectMapper.writeValue(writer, rentedBooks);

            writer = new FileWriter(booksFile, false);
            objectMapper.writeValue(writer, books);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    /**
     * Load books into store
     *
     * @see Book Book
     */
    private void addBooks() {
        books.forEach(book -> {
            book.setIndexer(book.getTitle().toLowerCase() + "-" + book.getAuthor().toLowerCase() + "-" + book.getYear());
            book.addBookCoverImage(new ImageIcon(Book.class.getResource("/" + book.getImageLink())));
            book.setPrice(randomPrice(200, 500));
            insert(book);
        });
    }

    /**
     * Get random rent price
     *
     * @param min Minimum price
     * @param max Maximum price
     * @return random price inclusive of min exclusive max
     */
    private float randomPrice(int min, int max) {
        Random random = new Random();
        return random.nextFloat(min, max);
    }

    public void inOrder(Node node) {
        if (node != null) {
            inOrder(node.left);
            System.out.println(node.key);
            inOrder(node.right);

        }
    }

    public LinkedList<Book> getRentedBooks() {
        return rentedBooks;
    }

    public boolean atRentLimit() {
        return rentedBooks.size() == RENT_LIMIT;
    }


    void returnAll() {
//        for (int i = 0; i < rentedBooks.size(); i++) {
//           returnBook(rentedBooks.get(i));
//        }

        while (rentedBooks.size() > 0) {
            returnBook(rentedBooks.remove());
        }

    }
}
