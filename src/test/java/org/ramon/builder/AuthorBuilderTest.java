package org.ramon.builder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.ramon.model.Author;

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
