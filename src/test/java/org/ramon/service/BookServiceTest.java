package org.ramon.service;


import org.apache.commons.lang.StringUtils;
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

import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
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

    @Test
    public void shouldReturnBookWhenCreateMethodIsCalled() throws Exception {
        when(booksDao.exist("1")).thenReturn(false);

        Author nullAuthor = null;

        Book book = new Book("1","juan","plaza", nullAuthor);

        bookService.createBook(book);

        verify(booksDao).addBook(book);
    }

    @Test(expected = BookService.DeleteErrorException.class)
    public void shouldThrowExceptionWhenBookAlreadyNotExistsToDelete() throws Exception {
        when(booksDao.exist("1")).thenReturn(true);

        bookService.deleteBook("2");
    }

    @Test
    public void shouldReturnBookWhenDeleteMethodIsCalled() throws Exception {
        when(booksDao.exist("1")).thenReturn(true);

        bookService.deleteBook("1");

        verify(booksDao).deleteBook("1");
    }


    @Test(expected = BookService.UpdateErrorException.class)
    public void shouldThrowExceptionWhenBookAlreadyNotExistsToUpdate() throws Exception {
        when(booksDao.exist("1")).thenReturn(true);

        Author nullAuthor = null;

        Book book = new Book("5","juan","plaza", nullAuthor);

        bookService.updateBook(book);
    }

    @Test
    public void shouldReturnBookWhenUpdateMethodIsCalled() throws Exception {
        when(booksDao.exist("1")).thenReturn(true);

        Author nullAuthor = null;

        Book book = new Book("1","juan","plaza", nullAuthor);

        bookService.updateBook(book);

        verify(booksDao).updateBook(book);
    }


    @Test(expected = BookService.ReadErrorException.class)
    public void shouldThrowReadExceptionWhenBookListExistsButNoBookFound() throws Exception {
        ArrayList<Book> books = new ArrayList<>();
        books.add(new Book());

        when(booksDao.getAllBooks()).thenReturn(books);

        when(booksDao.exist("42")).thenReturn(false);

        bookService.getBook("42");
    }

    @Test(expected = BookService.EmptyListException.class)
    public void shouldThrowReadExceptionWhenBookListIsEmpty() throws Exception{
        ArrayList<Book> books = new ArrayList<>();

        when(booksDao.getAllBooks()).thenReturn(books);

        bookService.getBook("73");

    }

    @Test
    public void shouldReturnBookWhenGetIsCalled() throws Exception {
        ArrayList<Book> books = new ArrayList<>();
        books.add(new Book());

        when(booksDao.getAllBooks()).thenReturn(books);

        when(booksDao.exist("73")).thenReturn(true);

        bookService.getBook("73");

        verify(booksDao).getBook("73");
    }


    @Test
    public void shouldGetBookList() throws Exception{

        bookService.getAllBooks();

        verify(booksDao).getAllBooks();

    }

    @Test
    public void shouldGetBookByAuthorList() throws Exception{

        bookService.getListByAuthor("Miguel");

        verify(booksDao).getListByAuthor("Miguel");

    }









































}