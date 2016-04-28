package org.ramon.controller;


import org.ramon.dao.BooksDao;
import org.ramon.model.Book;
import org.ramon.service.BookService;
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

    @Autowired
    private BookService bookService;


    @RequestMapping(value = "/get/{idBook}", method = GET)
    @ResponseBody
    public ResponseEntity<Book> getBook(@PathVariable("idBook") String idBook) {

        ResponseEntity<Book> response;

        try {
            Book book = bookService.getBook(idBook);
            response = new ResponseEntity<>(book,OK);
        } catch (BookService.ReadErrorException e) {
            response = new ResponseEntity<>(NOT_FOUND);
        }

        return response;
    }


    @RequestMapping(value = "/listByAuthor/{authorName}", method = GET)
    @ResponseBody
    public List<Book> getListByAuthor(@PathVariable("authorName") String authorName) {
        return bookService.getListByAuthor(authorName);
    }


    @RequestMapping(value = "/list", method = GET)
    @ResponseBody
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }


    @RequestMapping(value = "/delete/{idBook}", method = DELETE)
    @ResponseBody
    public ResponseEntity<Book> deleteBook(@PathVariable("idBook") String idBook) {

        ResponseEntity<Book> response;

        try {
            Book book = bookService.deleteBook(idBook);
            response = new ResponseEntity<>(book, OK);
        } catch (BookService.DeleteErrorException e) {
            response = new ResponseEntity<>(NOT_FOUND);
        }

        return response;
    }


    @RequestMapping(value = "/create", method = POST)
    @ResponseBody
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        ResponseEntity<Book> response;

        try {
            bookService.createBook(book);
            response = new ResponseEntity<>(book, CREATED);
        } catch (BookService.CreationErrorException e) {
            response = new ResponseEntity<>(NOT_ACCEPTABLE);
        }

        return response;
    }


    @RequestMapping(value = "/update", method = PUT)
    @ResponseBody
    public ResponseEntity<Book> updateBook(@RequestBody Book book) {
        ResponseEntity<Book> response;

        try {
            bookService.updateBook(book);
            response = new ResponseEntity<>(book, OK);
        } catch (BookService.UpdateErrorException e) {
            response = new ResponseEntity<>(NOT_FOUND);
        }

        return response;
    }

}
