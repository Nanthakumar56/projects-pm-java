package com.manage_projects.projects.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.manage_projects.projects.entity.ProjectNotes;
import com.manage_projects.projects.service.ProjectNoteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ProjectNotesControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ProjectNoteService noteService;

    @InjectMocks
    private ProjectNotesController projectNotesController;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(projectNotesController).build();
    }

    @Test
    void testGetProjectNotes() throws Exception {
        List<ProjectNotes> notes = Arrays.asList(new ProjectNotes());
        when(noteService.getNotesByProjectId(anyString())).thenReturn(notes);

        mockMvc.perform(get("/projectNotes/getNotes").param("projectid", "1"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(notes)));

        verify(noteService, times(1)).getNotesByProjectId(anyString());
    }

    @Test
    void testGetNoteById() throws Exception {
        ProjectNotes note = new ProjectNotes();
        when(noteService.getNoteById(anyString())).thenReturn(note);

        mockMvc.perform(get("/projectNotes/getNoteById").param("noteId", "1"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(note)));

        verify(noteService, times(1)).getNoteById(anyString());
    }

    @Test
    void testCreateProjectNotes() throws Exception {
        ProjectNotes note = new ProjectNotes();
        when(noteService.createProjectNotes(any())).thenReturn("Note Created");

        mockMvc.perform(post("/projectNotes/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(note)))
                .andExpect(status().isCreated())
                .andExpect(content().string("Note Created"));

        verify(noteService, times(1)).createProjectNotes(any());
    }

    @Test
    void testBulkCreateProjectNotes() throws Exception {
        List<ProjectNotes> notes = Arrays.asList(new ProjectNotes());
        when(noteService.bulkCreateProjectNotes(any())).thenReturn("Notes Created");

        mockMvc.perform(post("/projectNotes/bulkCreate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(notes)))
                .andExpect(status().isCreated())
                .andExpect(content().string("Notes Created"));

        verify(noteService, times(1)).bulkCreateProjectNotes(any());
    }

    @Test
    void testUpdateProjectNotes() throws Exception {
        ProjectNotes note = new ProjectNotes();
        when(noteService.updateProjectNotes(any())).thenReturn(note);

        mockMvc.perform(put("/projectNotes/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(note)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(note)));

        verify(noteService, times(1)).updateProjectNotes(any());
    }

    @Test
    void testDeleteProjectNotes() throws Exception {
        when(noteService.deleteProjectNotes(anyString())).thenReturn("Note deleted successfully");

        mockMvc.perform(delete("/projectNotes/delete").param("noteId", "1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Note deleted successfully"));

        verify(noteService, times(1)).deleteProjectNotes(anyString());
    }
}
