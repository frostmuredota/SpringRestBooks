package testCucumber;

import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.Arrays;

import net.minidev.json.JSONArray;

import org.ramon.model.Author;
import org.ramon.model.Book;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class testBooksCucumber {

    ConfigTest configTest = new ConfigTest();
    static String URL_GET;
    static String URL_POST;
    static String URL_DELETE;
    static String URL_PUT;
    static Gson json;
    static String ID;
    static ResponseEntity<Book> response;
    Book book,book1;
    static RestTemplate rest = new RestTemplate();

    public static boolean isJSONValid(String JSON_STRING) {
        Gson gson = new Gson();
        try {
            gson.fromJson(JSON_STRING, Object.class);
            return true;
        } catch (com.google.gson.JsonSyntaxException ex) {
            return false;
        }
    }

    /*---------------Get a Book----------------*/
    @Given("^have a list of books")
    public void given() throws InterruptedException {
        configTest.setUp();

    }

    @And("^the user want get a book with id =\"(.*?)\"$")
    public void a_book_with_id(String idBook) {
        ID = idBook;
    }

    @When("^user send the request Get with \"(.*?)\"$")
    public void user_send_the_request_Get_with(String url) {
        url += ID;
        URL_GET = url;
        // CREATE A BOOK
        book = new Book("1", "Libro de la Selva", "Editorial 1", new Author(
                "Pedro", "Marquez"));
        String urlPost = "http://localhost:8080/book/create";
        rest.postForEntity(urlPost, book, Book.class);

    }

    @Then("^returns a json object:$")
    public void returns_a_json_object(String json) {
        Gson gson = new Gson();
        response = rest.getForEntity(URL_GET, Book.class);
        json = gson.toJson(response.getBody());
        assertEquals(true, isJSONValid(json));
        assertEquals(book.getId(), response.getBody().getId());
        assertEquals(book.getEditorial(), response.getBody().getEditorial());
        assertEquals(book.getAuthor().getName(), response.getBody().getAuthor()
                .getName());
        assertEquals(book.getAuthor().getLastName(), response.getBody()
                .getAuthor().getLastName());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        configTest.setDown();
    }

    /*-------------------Fail Get Book --------------------------------------*/

    @And("^the user want get a book with idBook =\"(.*?)\"$")
    public void the_user_want_get_a_book_with_id(String idBook) {
        ID = idBook;
    }

    @When("^user send request \"(.*?)\"$")
    public void user_send_request(String url) {
        url += ID;
        URL_GET = url;
        // CREATE A BOOK
        book = new Book("1", "Libro de la Selva", "Editorial 1", new Author(
                "Pedro", "Marquez"));
        String urlPost = "http://localhost:8080/book/create";
        rest.postForEntity(urlPost, book, Book.class);
    }

    @Then("^returns a HttpStatus NOT_FOUND$")
    public void returns_a_HttpStatus_NOT_FOUND()
            throws HttpClientErrorException {
        try {
            response = rest.getForEntity(URL_GET,
                    Book.class);
        } catch (HttpClientErrorException e) {
            assertEquals(404, e.getStatusCode().value());
        }
        configTest.setDown();
    }
    
    /*--------------------Create a Book -------------------------------------*/
    @And("^the user want create a book$")
    public void the_user_want_create_a_book(){
        book = new Book("1", "Libro de la Selva", "Editorial 1", new Author(
                "Pedro", "Marquez"));
    }

    @When("^user send request to create a book \"(.*?)\"$")
    public void user_send_request_to_create_a_book(String url){
        String urlPost = "http://localhost:8080/book/create";
        response = rest.postForEntity(urlPost, book, Book.class);
    }

    @Then("^returns a HttpStatus CREATE$")
    public void returns_a_HttpStatus_CREATE(){
        assertEquals(201, response.getStatusCode().value());
        configTest.setDown();
    }
    /*--------------------Fail to Create a Book ------------------------------*/
    @And("^the user wish create a book$")
    public void the_user_wish_create_a_book() {
        book = new Book("1", "Libro de la Selva", "Editorial 1", new Author(
                "Pedro", "Marquez"));
        book1 = new Book("1", "Cronica de una Muerte Anunciada", "Editorial 1", new Author(
                "Gabriel", "Marquez"));
    }

    @When("^user send a request to create a book \"(.*?)\"$")
    public void user_send_a_request_to_create_a_book(String url){
        String urlPost = "http://localhost:8080/book/create";
        URL_POST=url;
        response = rest.postForEntity(urlPost, book, Book.class);
   
    }

    @Then("^returns a HttpStatus NOT_ACCEPTABLE$")
    public void returns_a_HttpStatus_NOT_ACCEPTABLE() throws HttpClientErrorException{
        try{
        response = rest.postForEntity(URL_POST, book1, Book.class);
        }catch(HttpClientErrorException e){
            assertEquals(406, e.getStatusCode().value());
        }
        configTest.setDown();
    }
    /*----------------Delete a Book ----------------------------*/
    
    
    
    
    
    
    
    /*-------------------Get List of Books ----------------------------------*/
    @When("^user send the request \"(.*?)\"$")
    public void user_send_the_request(String url) {
        URL_GET = url;
        // CREATE A BOOK
        book = new Book("1", "Libro de la Selva", "Editorial 1", new Author(
                "Pedro", "Marquez"));
        String urlPost = "http://localhost:8080/book/create";
        rest.postForEntity(urlPost, book, Book.class);
    }

    @Then("^returns a list of books$")
    public void returns_a_list_of_books() {
        ResponseEntity<Book[]> response = rest.getForEntity(URL_GET,Book[].class);
        JSONArray mJSONArray = new JSONArray();
        String json = mJSONArray.toJSONString(Arrays.asList(response.getBody()));
        assertEquals(200, response.getStatusCode().value());
        assertThat("[]", not(json));
        configTest.setDown();
    }

    /*----------------------Get Empty List ------------------------*/
    @Given("^have a list empty of books$")
    public void have_a_list_empty_of_books() throws InterruptedException{
        configTest.setUp();
    }
    @When("^user send the next request \"(.*?)\"$")
    public void user_send_the_next_request(String url){
        URL_GET = url;
    }

    @Then("^returns a list empty of books$")
    public void returns_a_list_empty_of_books(){
        ResponseEntity<Book[]> response = rest.getForEntity(URL_GET,
                Book[].class);
        JSONArray mJSONArray = new JSONArray();
        String json = mJSONArray.toJSONString(Arrays.asList(response.getBody()));
        assertEquals(true, isJSONValid(json));
        assertEquals("[]", json);
        configTest.setDown();
    }

}
