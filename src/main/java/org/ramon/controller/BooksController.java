package org.ramon.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import java.util.List;

import lombok.Setter;

import org.ramon.dao.BooksDao;
import org.ramon.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/SpringRestBooks")
public class BooksController {
    @Setter
    @Autowired
    private BooksDao daobook;

    @RequestMapping(value = "/get/{idBook}", method = GET, produces = { APPLICATION_JSON_VALUE })
    @ResponseBody
    public ResponseEntity<Book> getBook(@PathVariable("idBook") String idBook) {
        if (!this.daobook.getAllBooks().isEmpty()) {
            if (daobook.exist(idBook)) {
                return new ResponseEntity<Book>(this.daobook.getBook(idBook),
                        HttpStatus.OK);
            } else {
                return new ResponseEntity<Book>(NOT_FOUND);
            }

        } else {
            return new ResponseEntity<Book>(NOT_FOUND);
        }
    }

    @RequestMapping(value = "/listByAuthor/{authorName}", method = GET, produces = { APPLICATION_JSON_VALUE })
    @ResponseBody
    public List<Book> getListByAuthor(
            @PathVariable("authorName") String authorName) {
        return daobook.getListByAuthor(authorName);

    }

    @RequestMapping(value = "/list", method = GET, produces = { APPLICATION_JSON_VALUE })
    @ResponseBody
    public List<Book> getAllBooks() {
        return daobook.getAllBooks();
    }

    @RequestMapping(value = "/delete/{idBook}", method = DELETE, produces = { APPLICATION_JSON_VALUE })
    @ResponseBody
    public ResponseEntity<Book> deleteBook(@PathVariable("idBook") String idBook) {
            if (daobook.exist(idBook)) {
                Book book = daobook.getBook(idBook);
                daobook.deleteBook(idBook);
                return new ResponseEntity<Book>(book, OK);
            } else {
                return new ResponseEntity<Book>(NOT_FOUND);
            }

    }

    @RequestMapping(value = "/create", method = POST, produces = "application/json", consumes = "application/json")
    @ResponseBody
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        if (!daobook.exist(book.getId())) {
            this.daobook.addBook(book);
            return new ResponseEntity<Book>(book, CREATED);
        } else {
            return new ResponseEntity<Book>(NOT_ACCEPTABLE);
        }
    }

    @RequestMapping(value = "/update", method = PUT, produces = "application/json", consumes = "application/json")
    @ResponseBody
    public ResponseEntity<Book> updateBook(@RequestBody Book book) {
        if (daobook.exist(book.getId())) {
            this.daobook.updateBook(book);
            return new ResponseEntity<Book>(book, OK);
        } else {
            return new ResponseEntity<Book>(NOT_FOUND);
        }
    }
}
