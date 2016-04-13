package org.ramon.controller;

import org.ramon.dao.BooksDao;
import org.ramon.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
@RequestMapping("/book")
public class BooksController {
    @Autowired
    private BooksDao bookDao;

    @RequestMapping(value = "/get/{idBook}", method = GET)
    @ResponseBody
    public ResponseEntity<Book> getBook(@PathVariable("idBook") String idBook) {
        boolean listBooksNotEmpty = !this.bookDao.getAllBooks().isEmpty();

        if (listBooksNotEmpty) {
            if (bookDao.exist(idBook)) {
                return new ResponseEntity<Book>(this.bookDao.getBook(idBook),OK);
            } else {
                return new ResponseEntity<Book>(NOT_FOUND);
            }
        } else {
            return new ResponseEntity<Book>(NOT_FOUND);
        }
    }

    @RequestMapping(value = "/listByAuthor/{authorName}", method = GET)
    @ResponseBody
    public List<Book> getListByAuthor(
            @PathVariable("authorName") String authorName) {
        return bookDao.getListByAuthor(authorName);

    }

    @RequestMapping(value = "/list", method = GET)
    @ResponseBody
    public List<Book> getAllBooks() {
        return bookDao.getAllBooks();
    }

    @RequestMapping(value = "/delete/{idBook}", method = DELETE)
    @ResponseBody
    public ResponseEntity<Book> deleteBook(@PathVariable("idBook") String idBook) {
        boolean bookExist = bookDao.exist(idBook);
        
        if (bookExist) {
            
            Book book = bookDao.getBook(idBook);
            bookDao.deleteBook(idBook);
            return new ResponseEntity<Book>(book, OK);
        } else {
            
            return new ResponseEntity<Book>(NOT_FOUND);
        }

    }

    @RequestMapping(value = "/create", method = POST)
    @ResponseBody
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        boolean bookNotExist = !bookDao.exist(book.getId());
        
        if (bookNotExist) {
            this.bookDao.addBook(book);
            
            return new ResponseEntity<Book>(book, CREATED);
            
        } else {
            
            return new ResponseEntity<Book>(NOT_ACCEPTABLE);
        }
    }

    @RequestMapping(value = "/update", method = PUT)
    @ResponseBody
    public ResponseEntity<Book> updateBook(@RequestBody Book book) {
        boolean bookExist = bookDao.exist(book.getId());
        
        if (bookExist) {
            this.bookDao.updateBook(book);
            
            return new ResponseEntity<Book>(book, OK);
        } else {
            
            return new ResponseEntity<Book>(NOT_FOUND);
        }
    }

    public void setBookDao(BooksDao bookDao) {
        this.bookDao = bookDao;
    }
}
