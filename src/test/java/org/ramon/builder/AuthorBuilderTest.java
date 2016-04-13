package org.ramon.builder;

import org.junit.Test;
import org.ramon.model.Author;

import static org.junit.Assert.*;

public class AuthorBuilderTest {
    
    private static final String LASTNAME = "Perez";
    private static final String NAME = "Jose";

    @Test
    public void testWithName() {
        
        Author author = new AuthorBuilder().withName(NAME).withLastName(LASTNAME).build();
        assertEquals(NAME,author.getName());

    }

    @Test
    public void testWithLastName() {
        Author author = new AuthorBuilder().withName(NAME).withLastName(LASTNAME).build();
        assertEquals(LASTNAME,author.getLastName());
    }

    @Test
    public void testDone() {
        assertNull(new AuthorBuilder().done());
        assertNotNull(new AuthorBuilder(new BookBuilder()).done());
    }


}
