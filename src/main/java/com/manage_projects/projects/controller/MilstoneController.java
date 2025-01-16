package com.manage_projects.projects.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.manage_projects.projects.entity.MileStones;
import com.manage_projects.projects.service.MilestoneServie;

@RestController
@RequestMapping("/milestones")
public class MilstoneController {

	@Autowired
	private MilestoneServie milestoneService;
	
	@PostMapping("/createMilestone")
	public ResponseEntity<?> createMilestone(@RequestBody List<MileStones> milestones)
	{
		try {
			if(!milestones.isEmpty())
			{
				String response = milestoneService.createMileStones(milestones);
				
			     return ResponseEntity.status(HttpStatus.OK).body(response); 
			}
			else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
			            .body("Empty milestones" );
			}
		}
		catch(Exception e)
		{
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body("Error occurred while creating the MileStones: " + e.getMessage());
		}
		
	}
	
	@GetMapping("/getMilestones")
	public ResponseEntity<?> getAllMilestones(@RequestParam String projectid) {
	    try {
	        List<MileStones> milestones = milestoneService.getAllMilestones(projectid);
	        
	        if (milestones.isEmpty()) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                    .body("No milestones found.");
	        }
	        
	        return ResponseEntity.ok(milestones);
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
	                .body("Error occurred while fetching the milestones: " + e.getMessage());
	    }
	}

	@PutMapping("/updateMilestones")
    public ResponseEntity<String> updateMilestones(@RequestBody List<MileStones> milestones) {
        String response = milestoneService.updateMilestones(milestones);

        if (response.startsWith("Successfully updated")) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
	
	@DeleteMapping("/deleteMilestone")
    public ResponseEntity<String> deleteMilestone(@RequestParam String mileId) {
        boolean isDeleted = milestoneService.deleteMilestone(mileId);

        if (isDeleted) {
            return ResponseEntity.ok("Milestone with ID " + mileId + " deleted successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error occurred while deleting the milestone with ID " + mileId);
        }
    }
}
