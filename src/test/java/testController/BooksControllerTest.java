package testController;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
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

//import notesdashboard.models.Note;
//import notesdashboard.controllers.NoteController;
//import notesdashboard.dao.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MockServletContext.class)
@WebAppConfiguration
public class BooksControllerTest {
/*
    Gson gson;
    Properties properties;
    Note note1, note2, note3;
    List<Note> entities;
    @Mock
    NoteDao service;
    @InjectMocks
    NoteController noteController;
    private MockMvc mvc;

    @Before
    public void setUp() throws Exception {
        gson = new Gson();
        properties = new Properties();

        note1 = new Note(1, "title test", "description test");
        note2 = new Note(2, "title2", "description2");
        note3 = new Note(3, "title3", "description3");

        entities = new ArrayList<Note>();
        entities.add(note1);
        entities.add(note2);
        entities.add(note3);

        MockitoAnnotations.initMocks(this);

        Mockito.when(service.exist(note1.getId())).thenReturn(true);
        Mockito.when(service.exist(note2.getId())).thenReturn(true);
        Mockito.when(service.exist(note3.getId())).thenReturn(true);

        Mockito.when(service.getById(note1.getId())).thenReturn(note1);
        Mockito.when(service.getById(note2.getId())).thenReturn(note2);
        Mockito.when(service.getById(note3.getId())).thenReturn(note3);

        Mockito.when(service.deleteById(note1.getId())).thenReturn(note1);
        Mockito.when(service.deleteById(note2.getId())).thenReturn(note2);
        Mockito.when(service.deleteById(note3.getId())).thenReturn(note3);
        Mockito.when(service.deleteById(4)).thenReturn(null);

        Mockito.when(service.getAll()).thenReturn(entities);

        mvc = MockMvcBuilders.standaloneSetup(new NoteController()).build();
    }

    // 1) test of get (GET) an empty arraylist note
    @Test
    public void getListZeroNotesAsJson() throws Exception {
        entities = new ArrayList<Note>();
        mvc.perform(
                get("/notesdashboard/api/notes/").accept(
                        MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andExpect(content().string("[]"));
    }

    // 2) test of create (POST) a new note using an arraylist
    @Test
    public void createNote() throws Exception {
        entities = new ArrayList<Note>();
        String json = gson.toJson(note1);
        mvc.perform(
                post("/notesdashboard/api/notes/").contentType(
                        MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title").value(note1.getTitle()))
                .andExpect(
                        jsonPath("$.description").value(note1.getDescription()))
                .andReturn();
    }

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
