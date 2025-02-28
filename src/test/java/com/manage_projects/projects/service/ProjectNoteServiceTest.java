package com.manage_projects.projects.service;

import com.manage_projects.projects.entity.ProjectNotes;
import com.manage_projects.projects.repository.ProjectNotesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProjectNoteServiceTest {

    @Mock
    private ProjectNotesRepository notesRepo;

    @InjectMocks
    private ProjectNoteService projectNoteService;

    private ProjectNotes projectNote;
    
    @BeforeEach
    void setUp() {
        projectNote = new ProjectNotes();
        projectNote.setNotesid(UUID.randomUUID().toString());
        projectNote.setProjectid("project123");
        projectNote.setKeypoints("Initial key points");
        projectNote.setTag("Important");
        projectNote.setCreated_at(LocalDateTime.now());
    }

    @Test
    void testGetNotesByProjectId() {
        when(notesRepo.findByProjectid("project123")).thenReturn(Arrays.asList(projectNote));
        List<ProjectNotes> notes = projectNoteService.getNotesByProjectId("project123");
        assertFalse(notes.isEmpty());
        assertEquals(1, notes.size());
        assertEquals("project123", notes.get(0).getProjectid());
    }

    @Test
    void testGetNoteById() {
        when(notesRepo.findById(projectNote.getNotesid())).thenReturn(Optional.of(projectNote));
        ProjectNotes note = projectNoteService.getNoteById(projectNote.getNotesid());
        assertNotNull(note);
        assertEquals(projectNote.getNotesid(), note.getNotesid());
    }

    @Test
    void testCreateProjectNotes() {
        when(notesRepo.save(any(ProjectNotes.class))).thenReturn(projectNote);
        String result = projectNoteService.createProjectNotes(projectNote);
        assertEquals("Successfully created note", result);
    }

    @Test
    void testBulkCreateProjectNotes() {
        List<ProjectNotes> notesList = Arrays.asList(projectNote, new ProjectNotes());
        when(notesRepo.saveAll(anyList())).thenReturn(notesList);
        String result = projectNoteService.bulkCreateProjectNotes(notesList);
        assertEquals("Successfully created 2 notes.", result);
    }

    @Test
    void testUpdateProjectNotes() {
        when(notesRepo.findById(projectNote.getNotesid())).thenReturn(Optional.of(projectNote));
        when(notesRepo.save(any(ProjectNotes.class))).thenReturn(projectNote);
        
        projectNote.setKeypoints("Updated key points");
        ProjectNotes updatedNote = projectNoteService.updateProjectNotes(projectNote);
        
        assertNotNull(updatedNote);
        assertEquals("Updated key points", updatedNote.getKeypoints());
    }

    @Test
    void testDeleteProjectNotes() {
        when(notesRepo.findById(projectNote.getNotesid())).thenReturn(Optional.of(projectNote));
        doNothing().when(notesRepo).delete(any(ProjectNotes.class));
        String result = projectNoteService.deleteProjectNotes(projectNote.getNotesid());
        assertEquals("Successfully deleted the note.", result);
    }
}
