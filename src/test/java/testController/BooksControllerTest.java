package testController;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Properties;

import net.minidev.json.JSONObject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.ramon.controller.BooksController;
import org.ramon.model.Author;
import org.ramon.model.Book;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.google.gson.Gson;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MockServletContext.class)
@WebAppConfiguration
public class BooksControllerTest {

    Gson gson;
    Properties properties;
    Book book;
    @InjectMocks
    BooksController bookController;
    private MockMvc mvc;

    @Before
    public void setUp() throws Exception {
        gson = new Gson();
        properties = new Properties();
        MockitoAnnotations.initMocks(this);
        book = new Book("1", "Librito", "Editorial 5", new Author("Miguel",
                "Cervantes"));
        mvc = MockMvcBuilders.standaloneSetup(new BooksController()).build();
    }

    // 1) test of get (GET) an empty list of books
    @Test
    public void getListEmptyBooks() throws Exception {
        mvc.perform(
                get("/SpringRestBooks/list").accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("[]"));
    }

    @Test
    public void testListNotEmpty() throws Exception {

        String json = gson.toJson(book);
        JSONObject author = new JSONObject();
        author.put("name", book.getAuthor().getName());
        author.put("lastn", book.getAuthor().getLastn());

        mvc.perform(
                post("/SpringRestBooks/create").contentType(APPLICATION_JSON)
                .content(json))
                .andExpect(jsonPath("$.id").value(book.getId()))
                .andExpect(jsonPath("$.name").value(book.getName()))
                .andExpect(jsonPath("$.editorial").value(book.getEditorial()))
                .andExpect(jsonPath("$.author").value(author))
                .andExpect(status().isCreated()).andReturn();

        mvc.perform(
                get("/SpringRestBooks/list").accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(status().isOk())
                .andExpect(content().string("[{\"id\":\"1\",\"name\":\"Librito\","
                                        + "\"editorial\":\"Editorial 5\",\"author\":{\"name\":\"Miguel\","
                                        + "\"lastn\":\"Cervantes\"}}]"));
    }

    @Test
    public void testListByAuthor() throws Exception {
        String json = gson.toJson(book);
        JSONObject author = new JSONObject();
        author.put("name", book.getAuthor().getName());
        author.put("lastn", book.getAuthor().getLastn());
        mvc.perform(
                post("/SpringRestBooks/create").contentType(
                        MediaType.APPLICATION_JSON).content(json))
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(book.getId()))
                .andExpect(jsonPath("$.name").value(book.getName()))
                .andExpect(jsonPath("$.editorial").value(book.getEditorial()))
                .andExpect(jsonPath("$.author").value(author))
                .andExpect(status().isCreated()).andReturn();

        mvc.perform(
                get("/SpringRestBooks/listbyauthor/{authorName}", "Miguel")
                        .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(
                        content()
                                .string("[{\"id\":\"1\",\"name\":\"Librito\","
                                        + "\"editorial\":\"Editorial 5\",\"author\":{\"name\":\"Miguel\","
                                        + "\"lastn\":\"Cervantes\"}}]"));
    }

    @Test
    public void testEmptyListByAuthor() throws Exception {
        mvc.perform(
                get("/SpringRestBooks/listbyauthor/{authorName}", "Roberto")
                .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("[]"));
    }

    // 2) test of create (POST) a new book
    @Test
    public void testCreateBook() throws Exception {
        String json = gson.toJson(book);
        JSONObject author = new JSONObject();
        author.put("name", book.getAuthor().getName());
        author.put("lastn", book.getAuthor().getLastn());
        mvc.perform(
                post("/SpringRestBooks/create").contentType(APPLICATION_JSON)
                .content(json))
                .andExpect(jsonPath("$.id").value(book.getId()))
                .andExpect(jsonPath("$.name").value(book.getName()))
                .andExpect(jsonPath("$.editorial").value(book.getEditorial()))
                .andExpect(jsonPath("$.author").value(author))
                .andExpect(status().isCreated()).andReturn();
    }

    // 3) test of create (POST) and then get (GET) a book
    @Test
    public void testGetBook() throws Exception {
        String json = gson.toJson(book);
        JSONObject author = new JSONObject();
        author.put("name", book.getAuthor().getName());
        author.put("lastn", book.getAuthor().getLastn());
        mvc.perform(
                post("/SpringRestBooks/create").contentType(APPLICATION_JSON)
                .content(json))
                .andExpect(jsonPath("$.id").value(book.getId()))
                .andExpect(jsonPath("$.name").value(book.getName()))
                .andExpect(jsonPath("$.editorial").value(book.getEditorial()))
                .andExpect(jsonPath("$.author").value(author))
                .andExpect(status().isCreated()).andReturn();

        mvc.perform(
                get("/SpringRestBooks/get/{idBook}", "1").accept(APPLICATION_JSON))
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$id").value(book.getId()))
                .andExpect(jsonPath("$.name").value(book.getName()))
                .andExpect(jsonPath("$.editorial").value(book.getEditorial()))
                .andExpect(jsonPath("$.author").value(author))
                .andExpect(status().isOk())
                .andReturn();

    }
    @Test
    // Delete a Book
    public void testDeleteBook() throws Exception {
        String json = gson.toJson(book);
        JSONObject author = new JSONObject();
        author.put("name", book.getAuthor().getName());
        author.put("lastn", book.getAuthor().getLastn());
        mvc.perform(
                post("/SpringRestBooks/create").contentType(
                        MediaType.APPLICATION_JSON).content(json))
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(book.getId()))
                .andExpect(jsonPath("$.name").value(book.getName()))
                .andExpect(jsonPath("$.editorial").value(book.getEditorial()))
                .andExpect(jsonPath("$.author").value(author))
                .andExpect(status().isCreated()).andReturn();

        mvc.perform(
                 delete("/SpringRestBooks/delete/{idBook}","1")
                 .accept(MediaType.APPLICATION_JSON))
                 .andExpect(content().contentType(APPLICATION_JSON))
                 .andExpect(jsonPath("$.id").value(book.getId()))
                 .andExpect(jsonPath("$.name").value(book.getName()))
                 .andExpect(jsonPath("$.editorial").value(book.getEditorial()))
                 .andExpect(jsonPath("$.author").value(author))
                 .andExpect(status().isOk())
                 .andReturn();

    }
    //DELETE A BOOK WHAT NOT EXIST
    @Test
    public void testDeleteNotExistBook()throws Exception{
        mvc.perform(
                 delete("/SpringRestBooks/delete/{idBook}","2")
                 .contentType(APPLICATION_JSON))
                 .andExpect(status().isNotFound());
    }

    @Test
    public void testUpdateExistBook()throws Exception{
        String json = gson.toJson(book);
        JSONObject author = new JSONObject();
        author.put("name", book.getAuthor().getName());
        author.put("lastn", book.getAuthor().getLastn());
        mvc.perform(
                post("/SpringRestBooks/create").contentType(APPLICATION_JSON)
                .content(json))
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(book.getId()))
                .andExpect(jsonPath("$.name").value(book.getName()))
                .andExpect(jsonPath("$.editorial").value(book.getEditorial()))
                .andExpect(jsonPath("$.author").value(author))
                .andExpect(status().isCreated()).andReturn();

        Book bookUpdate = new Book("1", "Librin", "Editorial 6", new Author("Carlos",
                "Cervantes"));
        String json1 = gson.toJson(bookUpdate);
        mvc.perform(
                put("/SpringRestBooks/update").contentType(APPLICATION_JSON)
                .content(json1))
                .andExpect(status().isOk())
                .andReturn();

    }
    @Test
    public void testUpdateNotExistBook()throws Exception{
        String json = gson.toJson(book);
        JSONObject author = new JSONObject();
        author.put("name", book.getAuthor().getName());
        author.put("lastn", book.getAuthor().getLastn());
        mvc.perform(
                post("/SpringRestBooks/create").contentType(APPLICATION_JSON)
                .content(json))
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(book.getId()))
                .andExpect(jsonPath("$.name").value(book.getName()))
                .andExpect(jsonPath("$.editorial").value(book.getEditorial()))
                .andExpect(jsonPath("$.author").value(author))
                .andExpect(status().isCreated()).andReturn();

        Book bookUpdate = new Book("2", "Librin", "Editorial 6", new Author("Carlos",
                "Cervantes"));
        String json1 = gson.toJson(bookUpdate);
        mvc.perform(
                put("/SpringRestBooks/update").contentType(APPLICATION_JSON)
                .content(json1))
                .andExpect(status().isNotFound());

    }

}