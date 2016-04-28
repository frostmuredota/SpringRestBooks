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
        boolean bookExist = bookDao.exist(book.getId());

        if (bookExist) {
            throw new CreationErrorException();
        }

        this.bookDao.addBook(book);
    }

    public void updateBook(Book book) {
        boolean bookNotExist = bookDao.notExist(book.getId());

        if (bookNotExist) {
           throw new UpdateErrorException();
        }

        this.bookDao.updateBook(book);
    }

    public Book deleteBook(String idBook) {

        boolean bookNotExist = bookDao.notExist(idBook);

        if (bookNotExist) {
            throw new DeleteErrorException();
        }

        bookDao.deleteBook(idBook);
        return bookDao.getBook(idBook);

    }

    public List<Book> getAllBooks() {
        return bookDao.getAllBooks();
    }

    public List<Book> getListByAuthor(String authorName) {
        return bookDao.getListByAuthor(authorName);
    }

    public Book getBook(String idBook) {
        boolean listBooksNotEmpty = !this.bookDao.getAllBooks().isEmpty();

        if (listBooksNotEmpty) {
            if (bookDao.exist(idBook)) {
                return this.bookDao.getBook(idBook);
            } else {
                throw new ReadErrorException();
            }
        } else {
            throw new ReadErrorException();
        }
    }

    public class CreationErrorException extends RuntimeException { }

    public class UpdateErrorException extends RuntimeException { }

    public class DeleteErrorException extends RuntimeException { }

    public class ReadErrorException extends RuntimeException { }
}
