package org.ramon.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import org.ramon.model.Author;
import org.ramon.model.Book;

public class BooksDaoImpl implements BooksDao {
	@Getter
	@Setter
	private Map<String, Book> books;

	public BooksDaoImpl(){
		//books=new HashMap<String,Book>();
		//books.put("1", new Book("1","100 años de soledad","Ercilla",new Author("Gabriel","Garcia Marquez")));
		//books.put("2", new Book("2","El amor en tiempos de Cólera","Mondadori",new Author("Gabriel","Garcia Marquez")));
		//books.put("3", new Book("3","Crónica de una muerte anunciada","Ercilla",new Author("Edgar","Alan Poe")));
		//books.put("4", new Book("4","Colmillo Blanco","Mondadori",new Author("Jack","London")));
	}
	@Override
	public List<Book> getListByAuthor(String nameAuthor) {

		Map<Object, Book> aux = new HashMap<>();
		//Map<String, Book> aux1 = new HashMap<>();
		
		//Get List of books with the name of the author
		
		for (String key : this.getBooks().keySet()) {
			if (this.getBooks().get(key).getAuthor().getName().equals(nameAuthor)) {
				aux.put(key, this.getBooks().get(key));

			}
		}

		List<Book> list = new ArrayList<Book>(aux.values());
		
		/*
		//Get List with the name to author who start to the variable
		int size1=nameAuthor.length();
		System.out.println(nameAuthor.length());
		for (String key : this.getBooks().keySet()) {
			if (this.getBooks().get(key).getAuthor().getName().substring(0,size1).equals(nameAuthor)) {
				aux1.put(key, this.getBooks().get(key));

			}
		}
		List<Book> list = new ArrayList<Book>(aux1.values());
		*/
		return list;
	}

	@Override
	public void deleteBook(String idBook) {
		for (String key : this.getBooks().keySet()) {
			if (this.getBooks().get(key).getId().equals(idBook)) {
				books.remove(key,this.getBooks().get(key));
			}
		}
		
	}


	@Override
	public Book getBook(String idBook) {
		Book aux=null;
		for (String key : this.getBooks().keySet()) {
			if (this.getBooks().get(key).getId().equals(idBook)) {
				aux=this.getBooks().get(key);
			}
		}
		return aux;
	}

	@Override
	public void updateBook(Book book) {
		String id= this.findKeyBook(book);
		if(id!=null){
			books.replace(id, book);
		}
	}

	@Override
	public void addBook(Book b) {
		//b.setAuthor(a);
		boolean aux = true;
		while(aux){
			String key = String.valueOf((int)(Math.random()*90));
			if(books.containsKey(key)){
				aux=true;
			}else{
				books.put(key, b);
				aux=false;
			}
		}
	}

	@Override
	public List<Book> getAllBooks() {
		List<Book> list = new ArrayList<Book>(books.values());
		return list;
	}
	
	public String findKeyBook(Book b){
		String id="";
		for (String key : this.getBooks().keySet()) {
			if (this.getBooks().get(key).getId().equals(b.getId())) {
				id=key;	

			}
		}
        return id;
	}



}
