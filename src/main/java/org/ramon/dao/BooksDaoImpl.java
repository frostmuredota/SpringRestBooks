package org.ramon.dao;

import java.util.ArrayList;
import java.util.List;

import org.ramon.model.Book;
import org.springframework.stereotype.Service;

@Service
public class BooksDaoImpl implements BooksDao {
    private final List<Book> library;
    Book auxBook=null;
    
    public BooksDaoImpl() {
        library = new ArrayList<Book>();
    }

    @Override
    public List<Book> getListByAuthor(String authorName) {
        
        List<Book> booksByAuthor = new ArrayList<Book>();
        
        for (Book book : library) {
           if(book.getAuthor().getName().equals(authorName)){
               booksByAuthor.add(book);
           }
        }

        return booksByAuthor;
    }

    @Override
    public Book deleteBook(String idBook) {

        for (Book book : library) {
            if(book.getId().equals(idBook)){
                auxBook = book;
            }
        }
        
        library.remove(auxBook);
       
        return auxBook;
    }

    @Override
    public Book getBook(String idBook) {
       
        
        for (Book book : library) {
            if(book.getId().equals(idBook)){
                auxBook = book;
            }
        }
        
        return auxBook;
    }

    @Override
    public void updateBook(Book book) {
        
        if (exist(book.getId())) {
            auxBook = getBook(book.getId());
            deleteBook(auxBook.getId());
            library.add(book);
        }
    }

    @Override
    public void addBook(Book book) {
        
        if (!exist(book.getId())) {
            library.add(book);
        }
    }

    @Override
    public List<Book> getAllBooks() {
        return library;
    }

    @Override
    public boolean exist(String idBook) {
        return getBook(idBook)!=null;
    }

}
