package org.ramon.dao;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

import org.ramon.model.Book;
import org.springframework.stereotype.Service;
@Service
public class BooksDaoImpl implements BooksDao {
    @Getter
    @Setter
    private List<Book> mybooks;
    // book;

    public BooksDaoImpl() {
        mybooks = new ArrayList<Book>();
        //book = new Book("1", "100 años de Soledad", "Editorial 1", new Author(
          //      "Gabriel", "Márquez"));
        //mybooks.add(book);
    }

    @Override
    public List<Book> getListByAuthor(String authorName) {

        // Get List of books with the name of the author
        List<Book> booksByAuthor = new ArrayList<Book>();
        for (int i = 0; i < mybooks.size(); i++) {
            if (mybooks.get(i).getAuthor().getName().equals(authorName)) {
                booksByAuthor.add(mybooks.get(i));
            }
        }
        return booksByAuthor;
    }

    @Override
    public Book deleteBook(String idBook) {
        Book bookToDelete=null;
        for (int i = 0; i < mybooks.size(); i++) {
            if (mybooks.get(i).getId().equals(idBook)) {
                bookToDelete=mybooks.get(i);
                mybooks.remove(i);
            }
        }
        return bookToDelete;
    }

    @Override
    public Book getBook(String idBook) {
        Book findBook = null;
        for (int i = 0; i < mybooks.size(); i++) {
            if (mybooks.get(i).getId().equals(idBook)) {
                findBook = mybooks.get(i);
            }
        }
        return findBook;
    }

    @Override
    public void updateBook(Book book) {
        if (exist(book.getId())) {
            Book bookToFind = getBook(book.getId());
            deleteBook(bookToFind.getId());
            mybooks.add(book);
        }
    }

    @Override
    public void addBook(Book b) {
        if (!exist(b.getId())) {
            mybooks.add(b);
        }
    }

    @Override
    public List<Book> getAllBooks() {
        return mybooks;
    }

    @Override
    public boolean exist(String idBook) {
        boolean exist = false;
        for (int i = 0; i < mybooks.size(); i++) {
            if (mybooks.get(i).getId().equals(idBook)) {
                exist = true;
            }
        }
        return exist;
    }

}
