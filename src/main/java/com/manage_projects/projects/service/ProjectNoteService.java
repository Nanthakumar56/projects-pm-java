package com.manage_projects.projects.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.manage_projects.projects.entity.ProjectNotes;
import com.manage_projects.projects.repository.ProjectNotesRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProjectNoteService {

    @Autowired
    private ProjectNotesRepository notesRepo;

    // Get all notes for a specific project
    public List<ProjectNotes> getNotesByProjectId(String projectid) {
        try {
            return notesRepo.findByProjectid(projectid);
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while fetching project notes: " + e.getMessage());
        }
    }

    // Get a note by ID
    public ProjectNotes getNoteById(String noteId) {
        return notesRepo.findById(noteId).orElse(null);
    }

    // Create a new project note
    public String createProjectNotes(ProjectNotes projectNotes) {
        if (projectNotes == null || projectNotes.getKeypoints() == null || projectNotes.getKeypoints().isEmpty()) {
            return "No keypoints to create.";
        }

        try {
            projectNotes.setNotesid(UUID.randomUUID().toString());
            projectNotes.setCreated_at(LocalDateTime.now());

            notesRepo.save(projectNotes);

            return "Successfully created note";
        } catch (Exception e) {
            return "Error occurred while creating the project note: " + e.getMessage();
        }
    }

    // Bulk create project notes
    public String bulkCreateProjectNotes(List<ProjectNotes> projectNotesList) {
        if (projectNotesList == null || projectNotesList.isEmpty()) {
            return "No project notes to create.";
        }

        try {
            for (ProjectNotes projectNotes : projectNotesList) {
                projectNotes.setNotesid(UUID.randomUUID().toString());
                projectNotes.setCreated_at(LocalDateTime.now());
                projectNotes.setUpdated_at(LocalDateTime.now());
            }

            notesRepo.saveAll(projectNotesList);

            return "Successfully created " + projectNotesList.size() + " notes.";
        } catch (Exception e) {
            return "Error occurred while creating project notes: " + e.getMessage();
        }
    }

    // Update a project note
    public ProjectNotes updateProjectNotes(ProjectNotes projectNotes) {
        Optional<ProjectNotes> existingNoteOpt = notesRepo.findById(projectNotes.getNotesid());
        if (existingNoteOpt.isPresent()) {
            ProjectNotes existingNote = existingNoteOpt.get();

            // Update fields only if they are not null in the payload
            if (projectNotes.getTag() != null) {
                existingNote.setTag(projectNotes.getTag());
            }
            if (projectNotes.getKeypoints() != null) {
                existingNote.setKeypoints(projectNotes.getKeypoints());
            }

            // Update the timestamp for modification
            existingNote.setUpdated_at(LocalDateTime.now());

            notesRepo.save(existingNote);
            return existingNote;
        }
        return null;  
    }


    public String deleteProjectNotes(String noteId) {
        Optional<ProjectNotes> existingNoteOpt = notesRepo.findById(noteId);
        if (existingNoteOpt.isPresent()) {
            notesRepo.delete(existingNoteOpt.get());
            return "Successfully deleted the note.";
        }
        return "Note not found";  
    }
}
