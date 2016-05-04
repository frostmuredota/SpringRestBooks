package org.ramon.service;

import org.ramon.dao.BooksDao;
import org.ramon.dao.exceptions.DeleteBookException;
import org.ramon.dao.exceptions.SaveBookException;
import org.ramon.dao.exceptions.UpdateBookException;
import org.ramon.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    @Autowired
    private BooksDao bookDao;

    public void createBook(Book book) {
        try {
            this.bookDao.addBook(book);
        } catch (SaveBookException e) {
            throw new CreationErrorException("can not create the book with id: " + book.getId());
        }
    }

    public void updateBook(Book book) {
        try {
            this.bookDao.updateBook(book);
        } catch (UpdateBookException e) {
            throw new UpdateErrorException("can not update the book with id: " + book.getId());
        }
    }

    public Book deleteBook(String idBook) {
        try {
            return bookDao.deleteBook(idBook);
        } catch (DeleteBookException e) {
            throw new DeleteErrorException("can not delete the book with id: " + idBook);
        }
    }

    public List<Book> getAllBooks() {
        List<Book> bookList = bookDao.getAllBooks();
        if (bookList.isEmpty()) {
            throw new EmptyListException("List of books is empty");
        } else {
            return bookList;
        }
    }

    public List<Book> getListByAuthor(String authorName) {
        return bookDao.getListByAuthor(authorName);
    }

    public Book getBook(String idBook) {
        Book book = this.bookDao.getBook(idBook);
        if (book != null) {
            return book;
        } else {
            throw new ReadErrorException("can not acces to the book with id: " + idBook);
        }
    }

    public class CreationErrorException extends RuntimeException {
        public CreationErrorException(String message) {
            super(message);
        }
    }

    public class UpdateErrorException extends RuntimeException {
        public UpdateErrorException(String message) {
            super(message);
        }
    }

    public class DeleteErrorException extends RuntimeException {
        public DeleteErrorException(String message) {
            super(message);
        }
    }

    public class ReadErrorException extends RuntimeException {
        public ReadErrorException(String message) {
            super(message);
        }
    }

    public class EmptyListException extends RuntimeException {
        public EmptyListException(String message) { super(message); }
    }
}
