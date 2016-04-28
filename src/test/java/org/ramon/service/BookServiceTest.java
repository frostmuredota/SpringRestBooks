package org.ramon.service;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.ramon.dao.BooksDao;
import org.ramon.model.Author;
import org.ramon.model.Book;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class BookServiceTest {

    @Mock
    private BooksDao booksDao;

    @InjectMocks
    private BookService bookService;


    @Test(expected = BookService.CreationErrorException.class)
    public void shouldThrowExceptionWhenBookAlreadyExists() throws Exception {
        when(booksDao.exist("1")).thenReturn(true);

        Author nullAuthor = null;

        Book book = new Book("1","juan","plaza", nullAuthor);

        bookService.createBook(book);
    }
}