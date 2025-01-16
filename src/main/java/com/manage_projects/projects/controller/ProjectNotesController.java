package com.manage_projects.projects.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.manage_projects.projects.dto.ProjectNotesDto;
import com.manage_projects.projects.entity.ProjectNotes;
import com.manage_projects.projects.service.ProjectNoteService;

@RestController
@RequestMapping("/projectNotes")
public class ProjectNotesController {

	@Autowired
	private ProjectNoteService noteService;
	
	@GetMapping("/getNotes")
	public ResponseEntity<?> getProjectNotes(@RequestParam String projectid)
	{
		try {
	        List<ProjectNotes> notes = noteService.getNote(projectid);
	        
	        if (notes.isEmpty()) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                    .body("No notes found.");
	        }
	        
	        return ResponseEntity.ok(notes);
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
	                .body("Error occurred while fetching the notes: " + e.getMessage());
	    }
	}
	
	 @PostMapping("/create")
	    public String createProjectNotes(@RequestBody ProjectNotesDto projectNotesDto) {
	        return noteService.createProjectNotes(projectNotesDto);
	    }
}
