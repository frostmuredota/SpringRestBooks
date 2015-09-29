package org.ramon.controller;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

import org.ramon.dao.BooksDaoImpl;
import org.ramon.model.Book;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;



@RestController
@RequestMapping("/SpringRestBooks")
public class BooksController {
    //ApplicationContext context;
	//@Autowired
	@Getter
	@Setter
    private BooksDaoImpl daobook;
    
    public BooksController(){
    	//context = new ClassPathXmlApplicationContext("org/ramon/xmlfiles/mapping.xml");
    	daobook= new BooksDaoImpl();
    }
    @RequestMapping("/")
    public String index() {
    	return "Greetings from Spring Boot!";
    }
    @RequestMapping(value = "/get/{idBook}", method = RequestMethod.GET,produces = {MediaType.APPLICATION_JSON_VALUE })
    @ResponseBody
    public ResponseEntity<Book> getBook(@PathVariable("idBook") String idBook) {
    	if (this.daobook.getAllBooks().size()>0) {
    		if(daobook.getBooks().containsKey(idBook)){
    			return new ResponseEntity<Book>(this.daobook.getBook(idBook), HttpStatus.OK);
    		}else{
    			  return new ResponseEntity<Book>(HttpStatus.NOT_FOUND);
    		}
            
        } else {
            return new ResponseEntity<Book>(HttpStatus.NOT_FOUND);
        }
    }
    @RequestMapping(value = "/listbyauthor/{nameAuthor}", method = RequestMethod.GET, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    @ResponseBody
    public List<Book> getListByAuthor(@PathVariable("nameAuthor") String nameAuthor) {
        return daobook.getListByAuthor(nameAuthor);
        
    }
    @RequestMapping(value = "/list", method = RequestMethod.GET, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    @ResponseBody
    public List<Book> getAllBooks() {
        return daobook.getAllBooks();
        
    }
    @RequestMapping(value = "/delete/{idBook}", method = RequestMethod.DELETE, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    @ResponseBody
    public ResponseEntity<Book> deleteBook(@PathVariable("idBook") String idBook) {
         if (this.daobook.getAllBooks().size()>0) {
     		if(daobook.getBooks().containsKey(idBook)){
     			Book book = daobook.getBook(idBook);
     	        daobook.deleteBook(idBook);
     			return new ResponseEntity<Book>(book, HttpStatus.OK);
     		}else{
     			  return new ResponseEntity<Book>(HttpStatus.NOT_FOUND);
     		}
             
         } else {
             return new ResponseEntity<Book>(HttpStatus.NOT_FOUND);
         }
     }

    @RequestMapping(value = "/create", method = RequestMethod.POST,produces = "application/json", consumes = "application/json")
    @ResponseBody
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        if (!daobook.getBooks().containsValue(book)) {
            this.daobook.addBook(book);
            return new ResponseEntity<Book>(book, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<Book>(HttpStatus.NOT_ACCEPTABLE);
        }
    }
    
    @RequestMapping(value = "/update", method = RequestMethod.POST,produces = "application/json", consumes = "application/json")
    @ResponseBody
    public ResponseEntity<Book> updateComputer(@RequestBody Book book) {
        if (this.getAllBooks().size()>0) {
            this.daobook.updateBook(book);
            return new ResponseEntity<Book>(book, HttpStatus.OK);
        } else {
            return new ResponseEntity<Book>(HttpStatus.NOT_ACCEPTABLE);
        }
    }
    
    
}
