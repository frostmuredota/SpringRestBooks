package org.ramon.service;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.ramon.dao.BooksDao;
import org.ramon.dao.exceptions.DeleteBookException;
import org.ramon.dao.exceptions.SaveBookException;
import org.ramon.dao.exceptions.UpdateBookException;
import org.ramon.model.Author;
import org.ramon.model.Book;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class BookServiceTest {

    @Mock
    private BooksDao booksDao;

    @InjectMocks
    private BookService bookService;


    @Test(expected = BookService.CreationErrorException.class)
    public void shouldThrowExceptionWhenBookAlreadyExists() throws Exception {
        Book book = new Book("1","juan","plaza", new Author("Andres","Caceres"));
        doThrow(new SaveBookException("")).when(booksDao).addBook(book);
        bookService.createBook(book);
    }

    @Test
    public void shouldReturnAListOfBooksWhenGetAllBooksIsCalled() throws Exception {
        Book book1 = new Book("1","juan","plaza", new Author("Andres","Caceres"));
        Book book2 = new Book("2","perro","negro", new Author("Diego","Montenegro"));
        List<Book> books = new ArrayList<Book>();
        books.add(book1);
        books.add(book2);
        when(booksDao.getAllBooks()).thenReturn(books);
        bookService.getAllBooks();
    }


    @Test
    public void shouldCreateBookWhenCreateMethodIsCalled() throws Exception {
        Author nullAuthor = null;
        Book book = new Book("1","juan","plaza", nullAuthor);
        bookService.createBook(book);
        verify(booksDao).addBook(book);
    }

    @Test(expected = BookService.DeleteErrorException.class)
    public void shouldThrowExceptionWhenBookAlreadyNotExistsToDelete() throws Exception {
        when(booksDao.deleteBook("1")).thenThrow(new DeleteBookException(""));
        bookService.deleteBook("1");
    }

    @Test
    public void shouldReturnBookWhenDeleteMethodIsCalled() throws Exception {
        Book book = new Book("1","juan","plaza", new Author("Andres","Caceres"));
        when(booksDao.deleteBook("1")).thenReturn(book);
        bookService.deleteBook("1");
    }


    @Test(expected = BookService.UpdateErrorException.class)
    public void shouldThrowExceptionWhenBookAlreadyNotExistsToUpdate() throws Exception {
        Book book = new Book("1","juan","plaza", new Author("Andres","Caceres"));
        doThrow(new UpdateBookException("")).when(booksDao).updateBook(book);
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

    @Test(expected = BookService.EmptyListException.class)
    public void shouldThrowReadExceptionWhenBookListIsEmpty() throws Exception{
        List <Book> books = new ArrayList<>();
        when(booksDao.getAllBooks()).thenReturn(books);
        bookService.getAllBooks();
    }

    @Test
    public void shouldReturnBookWhenGetBookIsCalled() throws Exception {
        Book book = new Book("1","juan","plaza", new Author("Andres","Caceres"));
        when(booksDao.getBook("1")).thenReturn(book);
        bookService.getBook("1");
    }

    @Test(expected = BookService.ReadErrorException.class)
    public void shouldThrowExceptionWhenBookNotExists() throws Exception {
        when(booksDao.getBook("1")).thenReturn(null);
        bookService.getBook("1");
    }

    @Test
    public void shouldGetBookByAuthorList() throws Exception{
        Book book1 = new Book("1","juan","plaza", new Author("Andres","Caceres"));
        Book book2 = new Book("2","perro","negro", new Author("Andres","Caceres"));
        List<Book> books = new ArrayList<Book>();
        books.add(book1);
        books.add(book2);
        when(booksDao.getListByAuthor("Andres")).thenReturn(books);
        bookService.getListByAuthor("Andres");
    }









































}