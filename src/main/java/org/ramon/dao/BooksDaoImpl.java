package org.ramon.dao;

import org.ramon.dao.exceptions.SaveBookException;
import org.ramon.dao.exceptions.UpdateBookException;
import org.ramon.model.Book;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Service
public class BooksDaoImpl implements BooksDao {
    private final List<Book> library;


    public BooksDaoImpl() {
        library = new ArrayList<Book>();
    }

    @Override
    public List<Book> getListByAuthor(String authorName) {

        List<Book> booksByAuthor = new ArrayList<Book>();

        for (Book book : library) {
            if (book.getAuthor().getName().equals(authorName)) {
                booksByAuthor.add(book);
            }
        }

        return booksByAuthor;
    }

    @Override
    public Book deleteBook(String idBook) {
        Book book = null;

        for (Book item : library) {
            if (item.getId().equals(idBook)) {
                book = item;
            }
        }

        library.remove(book);

        return book;
    }

    @Override
    public Book getBook(String idBook) {
        Book book = null;

        for (Book item : library) {
            if (item.getId().equals(idBook)) {
                book = item;
            }
        }

        return book;
    }

    @Override
    public void updateBook(Book bookToUpdate) {
        String bookId = bookToUpdate.getId();
        Book book = getBook(bookId);
        deleteBook(book.getId());
        library.add(bookToUpdate);
    }

    @Override
    public void addBook(Book book) {
            library.add(book);

    }

    @Override
    public List<Book> getAllBooks() {
        return library;
    }

    @Override
    public boolean exist(String idBook) {
        return getBook(idBook) != null;
    }

    @Override
    public boolean notExist(String idBook) {
        return !exist(idBook);
    }

}
