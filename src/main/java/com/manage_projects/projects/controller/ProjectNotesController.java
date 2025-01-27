package com.manage_projects.projects.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.manage_projects.projects.entity.ProjectNotes;
import com.manage_projects.projects.service.ProjectNoteService;

import java.util.List;

@RestController
@RequestMapping("/projectNotes")
public class ProjectNotesController {

    @Autowired
    private ProjectNoteService noteService;

    // Get all notes for a specific project
    @GetMapping("/getNotes")
    public ResponseEntity<?> getProjectNotes(@RequestParam String projectid) {
        try {
            List<ProjectNotes> notes = noteService.getNotesByProjectId(projectid);

            return ResponseEntity.ok(notes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error occurred while fetching the notes: " + e.getMessage());
        }
    }

    @GetMapping("/getNoteById")
    public ResponseEntity<?> getNoteById(@RequestParam String noteId) {
        try {
            ProjectNotes note = noteService.getNoteById(noteId);

            if (note == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Note not found.");
            }

            return ResponseEntity.ok(note);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error occurred while fetching the note: " + e.getMessage());
        }
    }

    // Create a new project note
    @PostMapping("/create")
    public ResponseEntity<?> createProjectNotes(@RequestBody ProjectNotes projectNotes) {
        try {
            String result = noteService.createProjectNotes(projectNotes);
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error occurred while creating the note: " + e.getMessage());
        }
    }
    
    @PostMapping("/bulkCreate")
    public ResponseEntity<?> bulkCreateProjectNotes(@RequestBody List<ProjectNotes> projectNotesList) {
        try {
            String result = noteService.bulkCreateProjectNotes(projectNotesList);
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error occurred while creating project notes: " + e.getMessage());
        }
    }

    // Update an existing project note
    @PutMapping("/update")
    public ResponseEntity<?> updateProjectNotes(@RequestBody ProjectNotes projectNotes) {
        try {
            ProjectNotes updatedNote = noteService.updateProjectNotes(projectNotes);
            if (updatedNote == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Note not found to update.");
            }
            return ResponseEntity.ok(updatedNote);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error occurred while updating the note: " + e.getMessage());
        }
    }

    // Delete a project note by ID
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteProjectNotes(@RequestParam String noteId) {
        try {
            String result = noteService.deleteProjectNotes(noteId);
            if ("Note not found".equals(result)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
            }
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error occurred while deleting the note: " + e.getMessage());
        }
    }
}
