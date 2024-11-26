package com.manage_projects.projects.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.manage_projects.projects.dto.ProjectDto;
import com.manage_projects.projects.entity.Projects;
import com.manage_projects.projects.service.ProjectsService;

@RestController
@RequestMapping("/projects")
public class ProjectsController {
	@Autowired
	private ProjectsService projectService;
	
	@PostMapping("/createProject")
	public ResponseEntity<?> createProject (@RequestBody ProjectDto projectDTO)
	{
		try {
            Projects savedProject = projectService.createProject(projectDTO);
            
            return ResponseEntity.ok(savedProject);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body("Error occurred while creating the role: " + e.getMessage());
        }
	}
	@GetMapping("/allProjects")
	public ResponseEntity<List<ProjectDto>> getAllProjects()
	{
        List<ProjectDto> projectDTOList = projectService.getAllProjects();
        return ResponseEntity.status(HttpStatus.OK).body(projectDTOList);
	}
	@GetMapping("/project")
	public ResponseEntity<ProjectDto> getProject(@RequestParam String projectId)
	{
		ProjectDto projectDTO = projectService.getProject(projectId);
        if (projectDTO != null) {
            return ResponseEntity.status(HttpStatus.OK).body(projectDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
	}
	
	@PutMapping("/update")
	public ResponseEntity<?> updateProject(@RequestBody ProjectDto projectdto)
	{
		boolean response = projectService.updateProject(projectdto);
		
		if(response)
		{
		     return ResponseEntity.status(HttpStatus.OK).body("Project updated successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Failed to update project");
        }
	}
	
	@DeleteMapping("/delete")
	public ResponseEntity<?> deleteProject(@RequestParam String projectId) {
	    boolean response = projectService.deleteProject(projectId);
	    
	    if (response) {
	        return ResponseEntity.status(HttpStatus.OK).body("Project deleted successfully");
	    } else {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Failed to delete project or project not found");
	    }
	}


}