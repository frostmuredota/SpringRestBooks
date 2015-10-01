package org.ramon.dao;

import java.util.List;

import org.ramon.model.Author;
import org.ramon.model.Book;

public interface BooksDao {
    public Book getBook(String idBook);
    public List<Book> getListByAuthor(String nameAuthor);
    public List<Book>getAllBooks();
    public void deleteBook(String idBook);
    public void addBook(Book b); 
    public void updateBook(Book book);
    public boolean exist(String idBook);
    public String sayHello();
}
