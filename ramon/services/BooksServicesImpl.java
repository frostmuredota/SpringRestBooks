package org.ramon.services;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

import org.ramon.model.Book;
import org.springframework.stereotype.Service;

@Service
public class BooksServicesImpl implements BooksServices {
    @Getter
    @Setter
    private List<Book> mybooks;

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
    public List<Book> getListByAuthor(String nameAuthor) {
        // Get List of books with the name of the author
        List<Book> booksByAuthor = new ArrayList<Book>();
        for (int i = 0; i < mybooks.size(); i++) {
            if (mybooks.get(i).getAuthor().getName().equals(nameAuthor)) {
                booksByAuthor.add(mybooks.get(i));
            }
        }

        return booksByAuthor;
    }


    @Override
    public List<Book> getAllBooks() {
        return mybooks;
    }


    @Override
    public void deleteBook(String idBook) {
        for (int i = 0; i < mybooks.size(); i++) {
            if(mybooks.get(i).getId().equals(idBook)){
                mybooks.remove(i);
            }
        }
    }


    @Override
    public void addBook(Book b) {
        if(!exist(b.getId())){
            mybooks.add(b);
        }

    }


    @Override
    public void updateBook(Book book) {
        if(exist(book.getId())){
            Book bookToFind = getBook(book.getId());
            deleteBook(bookToFind.getId());
            mybooks.add(book);
        }

    }


    @Override
    public boolean exist(String idBook) {
        boolean exist = false;
        for (int i = 0; i < mybooks.size(); i++) {
            if(mybooks.get(i).getId().equals(idBook)){
                exist=true;
            }
        }
        return exist;
    }
}
