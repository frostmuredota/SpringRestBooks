package org.ramon.builder;

import org.junit.Test;
import org.ramon.model.Book;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class BookBuilderTest {

    private static final String AUTHORLASTNAME = "Perez";
    private static final String AUTHORNAME = "Jose";
    private static final String EDITORIAL = "Editorial 1";
    private static final String BOOKNAME = "Libro 1";
    private static final String ID = "1";

    @Test
    public void testWithId() {
        Book book = new BookBuilder()
        .withId(ID)
        .build();
       
        assertEquals(ID,book.getId());
    }

    @Test
    public void testWithName() {
        Book book = new BookBuilder()
        .withName(BOOKNAME)
        .build();
        
        assertEquals(BOOKNAME,book.getName());
    }

    @Test
    public void testWithEditorial() {
        Book book = new BookBuilder()
        .withEditorial(EDITORIAL)
        .build();
        
        assertEquals(EDITORIAL,book.getEditorial());
    }

    @Test
    public void testWithAuthor() {
        Book book = new BookBuilder()
        .withAuthor()
            .withName(AUTHORNAME)
            .withLastName(AUTHORLASTNAME)
            .done()
        .build();
        
        assertNotNull(book.getAuthor());
        assertEquals(AUTHORNAME,book.getAuthor().getName());
        assertEquals(AUTHORLASTNAME,book.getAuthor().getLastName());
    }

}
