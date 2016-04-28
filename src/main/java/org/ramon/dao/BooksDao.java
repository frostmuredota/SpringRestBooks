package org.ramon.dao;

import org.ramon.model.Book;

import java.util.List;

public interface BooksDao {
    public Book getBook(String idBook);
    public List<Book> getListByAuthor(String nameAuthor);
    public List<Book>getAllBooks();
    public Book deleteBook(String idBook);
    public void addBook(Book b); 
    public void updateBook(Book book);
    public boolean exist(String idBook);
    public boolean notExist(String idBook);
}
