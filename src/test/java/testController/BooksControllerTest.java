package testController;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import net.minidev.json.JSONObject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import com.google.gson.Gson;

import org.ramon.model.Author;
import org.ramon.model.Book;
import org.ramon.controller.BooksController;
import org.ramon.dao.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MockServletContext.class)
@WebAppConfiguration
public class BooksControllerTest {

    Gson gson;
    Properties properties;
    Book book1, book2, book3;
    Map<String,Book> library;
    @Mock
    BooksDaoImpl daobook;
    @InjectMocks
    BooksController bookController;
    private MockMvc mvc;

    @Before
    public void setUp() throws Exception {
        gson = new Gson();
        properties = new Properties();

        book1 = new Book("1","100 anos de soledad","Ercilla",new Author("Gabriel","Garcia Marquez"));
        book2 = new Book("2","El amor en tiempos de Cólera","Mondadori",new Author("Gabriel","Garcia Marquez"));
        book3 = new Book("3","Crónica de una muerte anunciada","Ercilla",new Author("Edgar","Alan Poe"));

        library = new HashMap<>();
        library.put("1",book1);
        library.put("2",book2);
        library.put("3",book3);

        MockitoAnnotations.initMocks(this);

        Mockito.when(daobook.exist(book1.getId())).thenReturn(true);
        Mockito.when(daobook.exist(book2.getId())).thenReturn(true);
        Mockito.when(daobook.exist(book3.getId())).thenReturn(true);

        Mockito.when(daobook.getBook(book1.getId())).thenReturn(book1);
        Mockito.when(daobook.getBook(book2.getId())).thenReturn(book2);
        Mockito.when(daobook.getBook(book3.getId())).thenReturn(book3);
/*
        Mockito.when(daobook.deleteBook(book1.getId()).;
        Mockito.when(service.deleteById(note2.getId())).thenReturn(note2);
        Mockito.when(service.deleteById(note3.getId())).thenReturn(note3);
        Mockito.when(service.deleteById(4)).thenReturn(null);
*/
        Mockito.when(daobook.getBooks()).thenReturn(library);

        mvc = MockMvcBuilders.standaloneSetup(new BooksController()).build();
    }
    
    @Test
    public void sayHello(){
    	Mockito.when(daobook.sayHello()).thenReturn("Hello");
    }

    // 1) test of get (GET) an empty arraylist books
    @Test
    public void getListBooksAsJson() throws Exception {
        mvc.perform(
                get("/SpringRestBooks/list").accept(
                        MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andExpect(content().string("[]"));
    }

    // 2) test of create (POST) a new book 
    @Test
    public void createBook() throws Exception {
        
        String json = gson.toJson(book1);
        JSONObject author = new JSONObject();
        author.put("name", book1.getAuthor().getName());
        author.put("lastn", book1.getAuthor().getLastn());
        mvc.perform(
                post("/SpringRestBooks/create").contentType(
                        MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(book1.getId()))
                .andExpect(jsonPath("$.name").value(book1.getName()))
                .andExpect(jsonPath("$.editorial").value(book1.getEditorial()))
                .andExpect(jsonPath("$.author").value(author))
                .andReturn();
    }
/*
    // 3) test of create (POST) and then get (GET) a not empty note arraylist
    @Test
    public void createAndGetNoteWithList() throws Exception {
        entities = new ArrayList<Note>();
        String json = gson.toJson(note1);
        mvc.perform(
                post("/notesdashboard/api/notes/").contentType(
                        MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(note1.getId()))
                .andExpect(jsonPath("$.title").value(note1.getTitle()))
                .andExpect(
                        jsonPath("$.description").value(note1.getDescription()));

        mvc.perform(
                get("/notesdashboard/api/notes/").accept(
                        MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(
                        content()
                                .string("[{\"id\":1,\"title\":\"title test\",\"description\":\"description test\"}]"));

    }

    // 4) test of create (POST) and then get (GET) a not empty note
    @Test
    public void createAndGetNote() throws Exception {
        entities = new ArrayList<Note>();
        String json = gson.toJson(note3);
        String id;

        mvc.perform(
                post("/notesdashboard/api/notes/").contentType(
                        MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(note3.getId()))
                .andExpect(jsonPath("$.title").value(note3.getTitle()))
                .andExpect(
                        jsonPath("$.description").value(note3.getDescription()));

        properties = gson.fromJson(json, Properties.class);
        id = properties.getProperty("id");

        mvc.perform(
                get("/notesdashboard/api/notes/" + id).accept(
                        MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(note3.getId()))
                .andExpect(jsonPath("$.title").value(note3.getTitle()))
                .andExpect(
                        jsonPath("$.description").value(note3.getDescription()))
                .andReturn();
    }

    // 5) test of get (GET) a non existing note
    @Test
    public void getEmptyNote() throws Exception {
        int id = 4;
        entities = new ArrayList<Note>();
        mvc.perform(
                get("/notesdashboard/api/notes/" + id).contentType(
                        MediaType.APPLICATION_JSON)).andExpect(
                status().isNotFound());
    }

    // 6) Delete an existing note
    @Test
    public void deleteAnExistNote() throws Exception {
        entities = new ArrayList<Note>();
        String json = gson.toJson(note3);
        String id;

        mvc.perform(
                post("/notesdashboard/api/notes/").contentType(
                        MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(note3.getId()))
                .andExpect(jsonPath("$.title").value(note3.getTitle()))
                .andExpect(
                        jsonPath("$.description").value(note3.getDescription()))
                .andReturn();

        properties = gson.fromJson(json, Properties.class);
        id = properties.getProperty("id");

        mvc.perform(
                delete("/notesdashboard/api/notes/" + id).accept(
                        MediaType.APPLICATION_JSON))
                // To check the response code. We expect 200 (OK)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(note3.getId()))
                .andExpect(jsonPath("$.title").value(note3.getTitle()))
                .andExpect(
                        jsonPath("$.description").value(note3.getDescription()));
    }

    // 7) Delete a non existing note
    @Test
    public void deleteANonExistNote() throws Exception {
        int id = 3;
        mvc.perform(
                delete("/notesdashboard/api/notes/" + id).contentType(
                        MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string(""));
    }

    // 8) Update an existing note
    @Test
    public void updateAnExistNote() throws Exception {
        mvc.perform(put("/notesdashboard/api/notes/").contentType(
                MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));
    }

    // 9) Update a non existing note
    @Test
    public void updateANonExistNote() throws Exception {
        mvc.perform(
                put("/notesdashboard/api/notes/").contentType(
                        MediaType.APPLICATION_JSON)).andExpect(
                status().isBadRequest());
    }
*/
}
