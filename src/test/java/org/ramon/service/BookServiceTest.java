package org.ramon.service;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.ramon.dao.BooksDao;
import org.ramon.model.Author;
import org.ramon.model.Book;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
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

    @Test(expected = BookService.DeleteErrorException.class)
    public void shouldThrowExceptionWhenBookAlreadyNotExistsToDelete() throws Exception {
        when(booksDao.exist("1")).thenReturn(true);

        bookService.deleteBook("1");
    }

    @Test(expected = BookService.UpdateErrorException.class)
    public void shouldThrowExceptionWhenBookAlreadyNotExistsToUpdate() throws Exception {
        when(booksDao.exist("1")).thenReturn(true);

        Author nullAuthor = null;

        Book book = new Book("5","juan","plaza", nullAuthor);

        bookService.updateBook(book);
    }


    @Test(expected = BookService.ReadErrorException.class)
    public void shouldThrowReadExceptionWhenBookListExistsButNoBookFound() throws Exception {
        ArrayList<Book> books = new ArrayList<>();
        books.add(new Book());

        when(booksDao.getAllBooks()).thenReturn(books);

        when(booksDao.exist("42")).thenReturn(false);

        bookService.getBook("42");
    }







































}