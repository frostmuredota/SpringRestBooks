package testController;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.ramon.dao.BooksDao;
import org.ramon.dao.BooksDaoImpl;
import org.ramon.model.Author;
import org.ramon.model.Book;

public class testBooksDaoImpl {
    
    BooksDao bookDaoImpl;
    Book book;

    @Before
    public void setUp() {
        book = new Book("2", "100 años", "Alcantara", new Author("Jose",
                "Perez"));
        bookDaoImpl = new BooksDaoImpl();
        bookDaoImpl.addBook(book);
    }

    @Test
    public void testAddBook() {
        assertTrue(bookDaoImpl.getAllBooks().size() == 1);
    }

    @Test
    public void testGetBook() {
        assertEquals(bookDaoImpl.getBook("2"), book);
    }

    @Test
    public void testDeleteBook() {
        bookDaoImpl.deleteBook("2");
        assertThat(bookDaoImpl.getAllBooks().size(),is(0));
    }
    @Test
    public void testUpdateBook(){
        Book bookUpdate = new Book("2", "100 años", " 1", new Author("Jose",
                "Perez"));
        bookDaoImpl.updateBook(bookUpdate);
        assertEquals(bookDaoImpl.getBook("2"), bookUpdate);
    }
}
