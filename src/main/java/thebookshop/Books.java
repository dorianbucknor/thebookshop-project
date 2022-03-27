package thebookshop;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Books extends RedBlackTree {

    /**
     * Initialize book storage
     */
    public Books (){
        super();
        addBooks();
    };

    /**
     * Searches storage for a book
     * @param title Title of book
     * @param author Author's full name
     * @param publishedYear Year book was published
     * @return The book that was found or null
     * @see Book Book
     */
    public Book findBook(String title, String author, String publishedYear) {
        String key = title.toLowerCase() + "-" + author.toLowerCase() + "-" + publishedYear.toLowerCase();
        Node result = searchForNode(key);
        if (result != null) {
            return result.book;
        } else {
            return null;
        }
    }

    /**
     * Searches for a book and "rents" it
     * @param book The book to be rented
     * @return The book that was rented or null if book not found
     * @see Book Book
     */
    public Book rentBook(Book book) {
        try {
            String key = book.getTitle().toLowerCase() + "-" + book.getAuthor().toLowerCase() + "-" + book.getPublishedYear().toLowerCase();
            Node result = deleteNode(key);
            if (result != null) {
                return result.book;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Return rented book
     * @param book Book to return
     * @see Book Book
     */
    public void returnBook(Book book) {
        try {
            insertNode(book);
        } catch (IllegalArgumentException e){
            e.printStackTrace();
        }

    }

    /**
     * Add books to storage
     *  @see Book Book
     */
    private void addBooks() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            List<Map<String, Object>> jsonMap =
                    objectMapper.readValue(getClass().getClassLoader().getResourceAsStream("books.json"),
                    new TypeReference<List<Map<String, Object>>>() {
                    });

            jsonMap.forEach(result -> {
                insertNode(new Book((String) result.get("title"), (String) result.get("author"),
                        (String) result.get("imageLink"), Integer.toString((Integer) result.get("year")),
                        (String) result.get("link"),
                       randomPrice(200, 700) ));
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get random price for dummy data
     * @param min Minimum price
     * @param max Maximum price
     * @return Random price inclusive of min exclusive max
     */
    private float randomPrice(int min, int max){
        Random random = new Random();
        return random.nextFloat(min, max);
    }


}
