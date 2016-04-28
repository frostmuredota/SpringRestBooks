package org.ramon.service;


import org.ramon.dao.BooksDao;
import org.ramon.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class BookService {

    @Autowired
    private BooksDao bookDao;


    public void createBook(Book book) {
        boolean bookNotExist = !bookDao.exist(book.getId());

        if (bookNotExist) {
            this.bookDao.addBook(book);
        } else {
            throw new CreationErrorException();
        }
    }


    public class CreationErrorException extends RuntimeException {

    }
}
