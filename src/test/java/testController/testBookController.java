package testController;

import static com.jayway.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.ramon.controller.BooksController;
import org.ramon.dao.BooksDaoImpl;
import org.ramon.model.Author;
import org.ramon.model.Book;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.module.mockmvc.RestAssuredMockMvc;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MockServletContext.class)
@WebAppConfiguration
public class testBookController {
    
    List <Book> myBooks = new ArrayList<Book>();
    List<Book>booksByAuthor = new ArrayList<Book>();
    
    Book book1,book2,book3;
    
    @Mock
    BooksDaoImpl services;
    
    @InjectMocks
    BooksController bookController;
    
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        
        book1= new Book("1", "100 anos de Soledad", "Editorial 1", new Author(
                "Gabriel", "Marquez"));
        book2=new Book("2", "Muerte Anunciada", "Editorial 2", new Author(
                       "Gabriel", "Marquez"));
        book3= new Book("3", "Libro de la Selva", "Editorial 3", new Author(
                       "Pedro", "Marquez"));
        myBooks.add(book1);
        myBooks.add(book2);
        myBooks.add(book3);
        
        booksByAuthor.add(book1);
        booksByAuthor.add(book2);
                
        Mockito.when(services.exist("1")).thenReturn(true);
        Mockito.when(services.exist("2")).thenReturn(true);
        Mockito.when(services.exist("3")).thenReturn(true);
        
        Mockito.when(services.getBook("1")).thenReturn(book1);
        Mockito.when(services.getBook("2")).thenReturn(book2);
        Mockito.when(services.getBook("3")).thenReturn(book3);
        
        Mockito.when(services.deleteBook("1")).thenReturn(book1);
        Mockito.when(services.deleteBook("2")).thenReturn(book2);
        Mockito.when(services.deleteBook("3")).thenReturn(book3);
  
        Mockito.when(services.getAllBooks()).thenReturn(myBooks);
        Mockito.when(services.getListByAuthor("Gabriel")).thenReturn(booksByAuthor);
        
        RestAssuredMockMvc.standaloneSetup(bookController);

    }
    @Test
    public void testGetBook(){
        given().
        when().
            get("/SpringRestBooks/get/{idBook}",1).
        then().
            statusCode(200).
            assertThat().
            body("id",equalTo("1")).
            body("name",equalTo("100 anos de Soledad")).
            body("editorial",equalTo("Editorial 1")).
            body("author.name",equalTo("Gabriel")).
            body("author.lastn",equalTo("Marquez"));
    }
    @Test
    public void testNotFoundBook(){
        given().
        when().
            get("/SpringRestBooks/get/{idBook}",5).
        then().
            statusCode(404);  
    }
    @Test
    public void testCreateBook(){
        given().
            contentType(ContentType.JSON).
            body(new Book("7","El Lobito","Editorial 7",new Author("Carlos","Fuentes"))).
        when().
            post("/SpringRestBooks/create").
        then()
            .statusCode(201);
        
    }
    @Test
    public void testFailCreateBook(){
        given().
            contentType(ContentType.JSON).
            body(new Book("1","100 años","Alpahuara", new Author("Gabriel","García Márquez"))).
        when().
            post("/SpringRestBooks/create").
        then()
            .statusCode(406); 
    }
    @Test
    public void testDeleteBook(){
        given().
        when().
            delete("/SpringRestBooks/delete/{idBook}",3).
        then().
            statusCode(200).
            body("id", equalTo("3")).
            body("name", equalTo("Libro de la Selva")).
            body("editorial", equalTo("Editorial 3")).
            body("author.name", equalTo("Pedro")).
            body("author.lastn", equalTo("Marquez")); ;
    }
    @Test
    public void testUpdateBook(){
        given().
            contentType(ContentType.JSON).
            body(new Book("1","100000 anos","Alpahuara", new Author("Carlos","Garcia"))).
        when().
            put("/SpringRestBooks/update").
        then()
            .statusCode(200).
            body("id", equalTo("1")).
            body("name", equalTo("100000 anos")).
            body("editorial", equalTo("Alpahuara")).
            body("author.name", equalTo("Carlos")).
            body("author.lastn", equalTo("Garcia")); 
    }
    @Test
    public void testFailUpdateBook(){
        given().
            contentType(ContentType.JSON).
            body(new Book("10","100000 años","Alpahuara", new Author("Carlos","García Márquez"))).
        when().
            put("/SpringRestBooks/update").
        then()
            .statusCode(404); 
    }
    @Test
    public void testFailDeleteBook(){
        given().
        when().
            delete("/SpringRestBooks/delete/{idBook}",8).
        then().
            statusCode(404);
    }
    @Test
    public void testGetAllBooks(){
        String response = given().when().get("/SpringRestBooks/list").asString();
        assertThat(response, is(not("[]")));
    }
    @Test
    public void testGetListByAuthor(){
        String response = given().when().get("/SpringRestBooks/listbyauthor/{authorName}","Gabriel").asString();
        assertThat(response, is(not("[]")));
    }
}