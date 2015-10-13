package org.ramon.dao;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

import org.ramon.model.Book;

public class BooksDaoImpl implements BooksDao {
	@Getter
	@Setter
	private List<Book> mybooks;

	public BooksDaoImpl() {
		mybooks = new ArrayList<Book>();
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
	public void deleteBook(String idBook) {
		for (int i = 0; i < mybooks.size(); i++) {
            if(mybooks.get(i).getId().equals(idBook)){
            	mybooks.remove(i);
            }
		}

	}

	@Override
	public Book getBook(String idBook) {
		Book findBook = null;
		for (int i = 0; i < mybooks.size(); i++) {
			if (mybooks.get(i).getId().equals(idBook)) {
				findBook=mybooks.get(i);
			}
		}
		return findBook;
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
	public void addBook(Book b) {
		if(!exist(b.getId())){
			mybooks.add(b);
		}
	}

	@Override
	public List<Book> getAllBooks() {
		return mybooks;
	}

	public String findKeyBook(Book b) {
		String id = "";
		return id;
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

	@Override
	public String sayHello() {
		return "Hello";
	}

}
