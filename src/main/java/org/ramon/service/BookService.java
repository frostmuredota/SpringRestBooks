package org.ramon.service;

import org.ramon.dao.BooksDao;
import org.ramon.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Service
public class BookService {

    @Autowired
    private BooksDao bookDao;


    public void createBook(Book book) {
        boolean bookExist = bookDao.exist(book.getId()) && isNotBlank(book.getId());

        if (bookExist) {
            throw new CreationErrorException();
        }

        this.bookDao.addBook(book);
    }

    public void updateBook(Book book) {
        boolean bookNotExist = !(bookDao.exist(book.getId()) && isNotBlank(book.getId()));

        if (bookNotExist) {
            throw new UpdateErrorException();
        }else{
            this.bookDao.updateBook(book);
        }

    }

    public Book deleteBook(String idBook) {

        boolean bookExist = bookDao.exist(idBook);

        if (bookExist) {
            return bookDao.deleteBook(idBook);
        }else{
            throw new DeleteErrorException();
        }

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
            throw new EmptyListException();
        }
    }

    public class CreationErrorException extends RuntimeException { }

    public class UpdateErrorException extends RuntimeException { }

    public class DeleteErrorException extends RuntimeException { }

    public class ReadErrorException extends RuntimeException { }

    public class EmptyListException extends RuntimeException{ }
}
