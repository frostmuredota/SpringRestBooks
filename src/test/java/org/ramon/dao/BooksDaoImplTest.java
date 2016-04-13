package org.ramon.dao;

import org.junit.Before;
import org.junit.Test;
import org.ramon.builder.BookBuilder;
import org.ramon.dao.exceptions.SaveBookException;
import org.ramon.dao.exceptions.UpdateBookException;
import org.ramon.model.Book;

import static org.junit.Assert.*;

public class BooksDaoImplTest {
    
    private static final String EDITORIAL = "Editorial 1";
    private static final String BOOKNAME = "Libro 1";
    private static final String ID = "1";
    private static final String AUTHORNAME = "Jose";
    private static final String AUTHORLASTNAME = "Perez";
    BooksDaoImpl dao;
    
    @Before
    public void setUp() throws Exception{
       dao = new BooksDaoImpl(); 
       
    }

    @Test
    public void testGetListByAuthor() {
        Book book1 =  new BookBuilder()
            .withAuthor()
            .withName(AUTHORNAME)
            .done()
        .build();
        Book book = book1;
        
        assertEquals(0,dao.getAllBooks().size());
        
        dao.addBook(book);
        
        assertEquals(1,dao.getListByAuthor(AUTHORNAME).size());
    }
    
    @Test
    public void testGetListByAuthorNameNoExist() {
        Book book1 =  new BookBuilder()
            .withAuthor()
            .withName(AUTHORNAME)
            .done()
        .build();
        Book book = book1;
        
        assertEquals(0,dao.getAllBooks().size());
        
        dao.addBook(book);
        
        assertEquals(0,dao.getListByAuthor("Claudio").size());
    }

    @Test
    public void testDeleteBook() {
        Book book =  new BookBuilder()
        .withId(ID)
        .build();
        
        assertEquals(0,dao.getAllBooks().size());

        dao.addBook(book);
        
        assertEquals(1,dao.getAllBooks().size());
        
        dao.deleteBook(dao.getAllBooks().get(0).getId());
        
        assertEquals(0,dao.getAllBooks().size());
        
    }
    
    @Test
    public void testDeleteBookIdNotExist() {
        Book book =  new BookBuilder()
        .withId(ID)
        .build();
        
        assertEquals(0,dao.getAllBooks().size());

        dao.addBook(book);
        
        assertEquals(1,dao.getAllBooks().size());
        
        dao.deleteBook("4");
        
        assertEquals(1,dao.getAllBooks().size());
        
    }

    @Test
    public void testGetBookById() {
        Book expectedBook =  new BookBuilder()
        .withId(ID)
        .withName(BOOKNAME)
        .withEditorial(EDITORIAL)
        .withAuthor()
            .withName(AUTHORNAME)
            .withLastName(AUTHORLASTNAME)
            .done()
        .build();
       
        
        assertEquals(0,dao.getAllBooks().size());
        
        dao.addBook(expectedBook);
        
        assertEquals(1,dao.getAllBooks().size());
        
        Book book = dao.getAllBooks().get(0);
        
        assertNotNull(dao.getBook(book.getId()));
        assertEquals(ID,book.getId());
        assertEquals(BOOKNAME,book.getName());
        assertEquals(EDITORIAL,book.getEditorial());
        assertEquals(AUTHORNAME,book.getAuthor().getName());
        assertEquals(AUTHORLASTNAME,book.getAuthor().getLastName());
    }

    @Test
    public void testUpdateBook() {
        Book originalBook =  new BookBuilder()
        .withId(ID)
        .withName(BOOKNAME)
        .build();
        
        assertEquals(0,dao.getAllBooks().size());
        
        dao.addBook(originalBook);
        
        String newBookName = "Libro 2";
        
        Book book_update = new BookBuilder()
        .withId(ID)
        .withName(newBookName)
        .build();
        
        dao.updateBook(book_update);
        
        assertEquals(1,dao.getAllBooks().size());
        Book book = dao.getAllBooks().get(0);
        assertEquals(ID,book.getId());
        assertEquals(newBookName,book.getName());
        
    }
    
    @Test(expected=UpdateBookException.class)
    public void testFailUpdateBookIdNotExistIsNotBlank() {
        Book book =  new BookBuilder()
        .withId(ID)
        .build();
        
        dao.addBook(book);
        Book book_update = new BookBuilder()
        .withId("2")
        .build();
        dao.updateBook(book_update);
        
    }
    
    @Test(expected=UpdateBookException.class)
    public void testFailUpdateBookNotExistIdBlank() {
        Book book =  new BookBuilder()
        .withId(ID)
        .build();
        
        dao.addBook(book);
        Book book_update = new BookBuilder()
        .withId("")
        .build();
        dao.updateBook(book_update);
        
    }
    
    @Test(expected=UpdateBookException.class)
    public void testFailUpdateBookIdNull() {
        Book book =  new BookBuilder()
        .withId(ID)
        .build();
        
        dao.addBook(book);
        Book book_update = new BookBuilder()
        .withId(null)
        .build();
        dao.updateBook(book_update);
        
    }
    
    @Test
    public void testAddBook() {
        Book expectedBook =  new BookBuilder()
        .withId(ID)
        .withName(BOOKNAME)
        .withEditorial(EDITORIAL)
        .withAuthor()
            .withName(AUTHORNAME)
            .withLastName(AUTHORLASTNAME)
            .done()
        .build();
        
        assertEquals(0,dao.getAllBooks().size());
        
        dao.addBook(expectedBook);
        
        assertEquals(1,dao.getAllBooks().size());
        Book book = dao.getAllBooks().get(0);
        assertEquals(expectedBook,book);
        assertEquals(ID,book.getId());
        assertEquals(BOOKNAME,book.getName());
        assertEquals(EDITORIAL,book.getEditorial());
        assertEquals(AUTHORNAME,book.getAuthor().getName());
        assertEquals(AUTHORLASTNAME,book.getAuthor().getLastName());
        
        
    }
    @Test
    public void testAddTwoBooks() {
        Book book1 =  new BookBuilder()
        .withId(ID)
        .build();
        Book book2 =  new BookBuilder()
        .withId(ID+"2")
        .build();
        
        assertEquals(0,dao.getAllBooks().size());
        
        dao.addBook(book1);
        dao.addBook(book2);
        
        assertEquals(2,dao.getAllBooks().size());
    
    }
    
    @Test(expected=SaveBookException.class)
    public void testAddTwoEqualsIdBooks() {
        Book book =  new BookBuilder()
        .withId(ID)
        .build();

        assertEquals(0,dao.getAllBooks().size());
        
        dao.addBook(book);
        dao.addBook(book);
        
    
    }
    
    @Test(expected=SaveBookException.class)
    public void testFailAddBook(){
        Book book =  new BookBuilder()
        .withId("")
        .build();
        
        dao.addBook(book);
    }
    @Test
    public void testGetAllBooks() {
        Book book =  new BookBuilder()
        .build();
        
        assertEquals(0,dao.getAllBooks().size());
        
        dao.addBook(book);
        
        assertEquals(1,dao.getAllBooks().size());
       
    }

    @Test
    public void testExistBook() {
        Book book =  new BookBuilder()
        .withId(ID)
        .build();
        
        assertEquals(0,dao.getAllBooks().size());
        
        dao.addBook(book);
        
        assertTrue(dao.exist(dao.getAllBooks().get(0).getId()));
    }
    @Test
    public void testNotExistBook() {
        Book book =  new BookBuilder()
        .withId(ID)
        .build();
        
        assertEquals(0,dao.getAllBooks().size());
        
        dao.addBook(book);
        
        assertTrue(dao.notExist("4"));
    }

}
