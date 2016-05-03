package org.ramon.service;

import org.ramon.dao.BooksDao;
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
        } catch (CreationErrorException e) {
            throw new CreationErrorException("can not create the book with id: " + book.getId());
        }
    }

    public void updateBook(Book book) {
        try {
            this.bookDao.updateBook(book);
        } catch (UpdateErrorException e) {
            throw new UpdateErrorException("can not update the book with id: " + book.getId());
        }

    }

    public Book deleteBook(String idBook) {
        try {
            return bookDao.deleteBook(idBook);
        } catch (DeleteErrorException e) {
            throw new DeleteErrorException("can not delete the book with id: " + idBook);
        }
    }

    public List<Book> getAllBooks() {
        return bookDao.getAllBooks();
    }

    public List<Book> getListByAuthor(String authorName) {
        return bookDao.getListByAuthor(authorName);
    }

    public Book getBook(String idBook) {
        try {
            return this.bookDao.getBook(idBook);
        } catch (ReadErrorException e) {
            throw new ReadErrorException("can not get the book with id: " + idBook);
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

}
