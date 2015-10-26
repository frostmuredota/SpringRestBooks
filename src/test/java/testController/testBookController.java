package testController;

import static com.jayway.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.ramon.controller.BooksController;
import org.ramon.dao.BooksDao;
import org.ramon.model.Author;
import org.ramon.model.Book;

import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.module.mockmvc.RestAssuredMockMvc;
@RunWith(MockitoJUnitRunner.class)
public class testBookController {
    
    List <Book> myBooks = new ArrayList<Book>();
    List<Book>booksByAuthor = new ArrayList<Book>();
    
    Book book1,book2,book3;
    
    @Mock
    BooksDao booksDao;
    
    @InjectMocks
    BooksController bookController;
    
    @Before
    public void setUp() {
        
        book1= new Book("1", "Amor en tiempos de colera", "Editorial 1", new Author(
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
                
        when(booksDao.exist("1")).thenReturn(true);
        when(booksDao.exist("2")).thenReturn(true);
        when(booksDao.exist("3")).thenReturn(true);
        
        when(booksDao.getBook("1")).thenReturn(book1);
        when(booksDao.getBook("2")).thenReturn(book2);
        when(booksDao.getBook("3")).thenReturn(book3);
        
        Mockito.when(booksDao.deleteBook("1")).thenReturn(book1);
        Mockito.when(booksDao.deleteBook("2")).thenReturn(book2);
        Mockito.when(booksDao.deleteBook("3")).thenReturn(book3);
  
        Mockito.when(booksDao.getAllBooks()).thenReturn(myBooks);
        Mockito.when(booksDao.getListByAuthor("Gabriel")).thenReturn(booksByAuthor);
        
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
            body("name",equalTo("Amor en tiempos de colera")).
            body("editorial",equalTo("Editorial 1")).
            body("author.name",equalTo("Gabriel")).
            body("author.lastName",equalTo("Marquez"));
    }
    @Test
    public void testGetBookListEmpty(){
        List <Book> emptyBooks = new ArrayList<Book>();
        Mockito.when(booksDao.getAllBooks()).thenReturn(emptyBooks);
        given().
        when().
            get("/SpringRestBooks/get/{idBook}",1).
        then().
            statusCode(404);
    }
    @Test
    public void testNotFoundBook(){
        given().
        when().
            get("/SpringRestBooks/get/{idBook}",6).
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
            .statusCode(201).
            assertThat().
            body("id",equalTo("7")).
            body("name",equalTo("El Lobito")).
            body("editorial",equalTo("Editorial 7")).
            body("author.name",equalTo("Carlos")).
            body("author.lastName",equalTo("Fuentes"));
        
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
            body("author.lastName", equalTo("Marquez")); ;
    }
    @Test
    public void testUpdateBook(){
        given().
            contentType(ContentType.JSON).
            body(new Book("1","Colmillo Blanco","Alpahuara", new Author("Carlos","Garcia"))).
        when().
            put("/SpringRestBooks/update").
        then()
            .statusCode(200).
            body("id", equalTo("1")).
            body("name", equalTo("Colmillo Blanco")).
            body("editorial", equalTo("Alpahuara")).
            body("author.name", equalTo("Carlos")).
            body("author.lastName", equalTo("Garcia")); 
    }
    @Test
    public void testFailUpdateBook(){
        given().
            contentType(ContentType.JSON).
            body(new Book("10","Doce Cuentos Peregrinos","Alpahuara", new Author("Carlos","García Márquez"))).
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
        given().
        contentType(ContentType.JSON).
    when().
        get("/SpringRestBooks/list").
    then()
        .statusCode(200).
        body("id[0]", equalTo("1")).
        body("name[0]",equalTo("Amor en tiempos de colera")).
        body("editorial[0]",equalTo("Editorial 1")).
        body("author[0].name",equalTo("Gabriel")).
        body("author[0].lastName",equalTo("Marquez"));
    }
    @Test
    public void testGetListByAuthor(){
        given().
        contentType(ContentType.JSON).
    when().
        get("/SpringRestBooks/listByAuthor/{authorName}","Gabriel").
    then()
        .statusCode(200).
        body("id[0]", equalTo("1")).
        body("name[0]",equalTo("Amor en tiempos de colera")).
        body("editorial[0]",equalTo("Editorial 1")).
        body("author[0].name",equalTo("Gabriel")).
        body("author[0].lastName",equalTo("Marquez"));
    }
}