package com.manage_projects.projects.controller;

import java.util.List;
import java.util.Optional;

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

import com.manage_projects.projects.dto.MilestoneDto;
import com.manage_projects.projects.dto.ModuleDto;
import com.manage_projects.projects.entity.MileStones;
import com.manage_projects.projects.entity.ModuleTasks;
import com.manage_projects.projects.service.MilestoneServie;

@RestController
@RequestMapping("/milestones")
public class MilstoneController {

	@Autowired
	private MilestoneServie milestoneService;
	
	@PostMapping("/createMilestone")
	public ResponseEntity<?> createMilestone(@RequestParam String projectid,@RequestBody List<MilestoneDto> milestones)
	{
		try {
			if(!milestones.isEmpty())
			{
				String response = milestoneService.createMilestones(projectid,milestones);
				
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
	
	@PostMapping("/createPhase")
	public ResponseEntity<?> createMilestone(@RequestBody MilestoneDto milestone) {
		
	    try {
	        if (milestone != null) {
	            if (milestone.getStep() <= 0) {
	                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
	                        .body("Valid milestone step is required.");
	            }

	            String response = milestoneService.createMileStone(milestone);
	            return ResponseEntity.status(HttpStatus.OK).body(response);
	        } else {
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
	                    .body("Milestone data is required.");
	        }
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
	                .body("Error occurred while creating milestone: " + e.getMessage());
	    }
	}
	
	@GetMapping("/getMilestones")
	public ResponseEntity<?> getAllMilestones(@RequestParam String projectid) {
	    try {
	        List<MilestoneDto> milestones = milestoneService.getAllMilestones(projectid);

	        if (milestones.isEmpty()) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                    .body("No milestones found.");
	        }

	        return ResponseEntity.ok(milestones);
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
	                .body("Error occurred while fetching milestones: " + e.getMessage());
	    }
	}

	@GetMapping("/getPhase")
	public ResponseEntity<?> getMilestoneById(@RequestParam String milestoneId) {
	    try {
	        Optional<MilestoneDto> milestone = milestoneService.getMilestoneById(milestoneId);

	        if (milestone.isEmpty()) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                    .body("Milestone not found with ID: " + milestoneId);
	        }

	        return ResponseEntity.ok(milestone.get());
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
	                .body("Error occurred while fetching milestone: " + e.getMessage());
	    }
	}

	
	@PutMapping("/updateMilestones")
	public ResponseEntity<String> updateMilestones(@RequestBody List<MilestoneDto> milestones) {
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
	        return ResponseEntity.ok("Milestone with ID " + mileId + " deleted successfully, and step numbers were updated.");
	    } else {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
	                .body("Error occurred while deleting the milestone with ID " + mileId);
	    }
	}
	
	@DeleteMapping("/deleteMilestonesByProject")
	public ResponseEntity<String> deleteMilestonesByProject(@RequestParam String projectId) {
	    boolean isDeleted = milestoneService.deleteMilestonesByProjectId(projectId);

	    if (isDeleted) {
	        return ResponseEntity.ok("Milestones for project ID " + projectId + " deleted successfully, and step numbers were updated.");
	    } else {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
	                .body("No milestones found for project ID " + projectId);
	    }
	}


	@PostMapping("/create-task")
    public ResponseEntity<ModuleTasks> createTask(@RequestBody ModuleTasks moduleTask) {
        return ResponseEntity.ok(milestoneService.createTask(moduleTask));
    }

    @PostMapping("/bulk-create")
    public ResponseEntity<List<ModuleTasks>> bulkCreateTasks(@RequestBody List<ModuleTasks> tasks) {
        return ResponseEntity.ok(milestoneService.bulkCreateTasks(tasks));
    }

    @PutMapping("/editmtask")
    public ResponseEntity<ModuleTasks> editTask(@RequestParam String mtasksid, @RequestBody ModuleTasks updatedTask) {
        return ResponseEntity.ok(milestoneService.editTask(mtasksid, updatedTask));
    }

    @GetMapping("/moduleTasks")
    public ResponseEntity<List<ModuleTasks>> getAllTasksByModuleId(@RequestParam String moduleid) {
        return ResponseEntity.ok(milestoneService.getAllTasksByModuleId(moduleid));
    }

    @GetMapping("/getMtask")
    public ResponseEntity<ModuleTasks> getTaskById(@RequestParam String mtasksid) {
        return ResponseEntity.ok(milestoneService.getTaskById(mtasksid));
    }

    @DeleteMapping("/deletemtask")
    public ResponseEntity<String> deleteTaskById(@RequestParam String mtasksid) {
    	milestoneService.deleteTaskById(mtasksid);
        return ResponseEntity.ok("Task deleted successfully.");
    }

    @DeleteMapping("/deleteTasksByModule")
    public ResponseEntity<String> deleteTasksByModuleId(@RequestParam String moduleid) {
    	milestoneService.deleteTasksByModuleId(moduleid);
        return ResponseEntity.ok("Tasks deleted successfully for moduleId: " + moduleid);
    }
    
    @PostMapping("/create-module")
    public ResponseEntity<ModuleDto> createModule(@RequestBody ModuleDto moduleDto) {
        try {
            ModuleDto createdModule = milestoneService.createModule(moduleDto);
            return new ResponseEntity<>(createdModule, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Bulk create modules
    @PostMapping("/bulkModule")
    public ResponseEntity<List<ModuleDto>> bulkCreateModules(@RequestBody List<ModuleDto> moduleDtos) {
        try {
            List<ModuleDto> createdModules = milestoneService.bulkCreateModules(moduleDtos);
            return new ResponseEntity<>(createdModules, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Update an existing module
    @PutMapping("/updateModule")
    public ResponseEntity<ModuleDto> updateModule(@RequestParam String moduleId, @RequestBody ModuleDto moduleDto) {
        try {
            ModuleDto updatedModule = milestoneService.updateModule(moduleId, moduleDto);
            return new ResponseEntity<>(updatedModule, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // Get modules by milestone ID
    @GetMapping("/milestone-module")
    public ResponseEntity<List<ModuleDto>> getModulesByMilestoneId(@RequestParam String milestoneId) {
        try {
            List<ModuleDto> modules = milestoneService.getModulesByMilestoneId(milestoneId);
            return new ResponseEntity<>(modules, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get module by ID
    @GetMapping("/module")
    public ResponseEntity<ModuleDto> getModuleById(@RequestParam String moduleId) {
        try {
            ModuleDto module = milestoneService.getModuleById(moduleId);
            return new ResponseEntity<>(module, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // Delete a module by ID
    @DeleteMapping("/deleteModule")
    public ResponseEntity<HttpStatus> deleteModuleById(@RequestParam String moduleId) {
        try {
        	milestoneService.deleteModuleById(moduleId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
        	System.err.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Delete modules by milestone ID
    @DeleteMapping("/deleteByMilestone")
    public ResponseEntity<HttpStatus> deleteModulesByMilestoneId(@RequestParam String milestoneId) {
        try {
        	milestoneService.deleteModulesByMilestoneId(milestoneId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PutMapping("/addedTasks")
    public ResponseEntity<?> updateAsAdded(@RequestParam String mtaskid, String status) {
        boolean response = milestoneService.updateAsAdded(mtaskid, status);
        if (response) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not found");
        }
    }
    @PutMapping("/removedTasks")
    public ResponseEntity<?> updateAsRemoved(@RequestParam String mtaskid) {
        boolean response = milestoneService.updateAsRemoved(mtaskid);
        if (response) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not found");
        }
    }

    @GetMapping("/getTasksNotStarted")
    public ResponseEntity<List<ModuleTasks>> getAllTasksNotStartedByModuleId(@RequestParam String moduleid) {
        return ResponseEntity.ok(milestoneService.getAllTasksNotStatedByModuleId(moduleid));
    }
}
