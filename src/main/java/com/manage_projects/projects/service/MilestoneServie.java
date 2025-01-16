package com.manage_projects.projects.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.manage_projects.projects.entity.MileStones;
import com.manage_projects.projects.repository.MileStoneRepository;

@Service
public class MilestoneServie {

	@Autowired
	private MileStoneRepository milestoneRepo;
	
	public String createMileStones(List<MileStones> milestones) {
	    if (milestones == null || milestones.isEmpty()) {
	        return "No milestones to create.";
	    }

	    try {
	        for (MileStones milestone : milestones) {
	            if (milestone.getMilestoneid() == null) {
	                milestone.setMilestoneid(UUID.randomUUID().toString());
	            }

	            milestone.setCreated_at(LocalDateTime.now());
	            
	            milestoneRepo.save(milestone);
	        }
	        return "Successfully created " + milestones.size() + " milestones.";
	    } catch (Exception e) {
	        e.printStackTrace();
	        return "Error occurred while creating milestones: " + e.getMessage();
	    }
	}

	
	public List<MileStones> getAllMilestones(String projectid) {
	    try {
	        // Fetching milestones by projectId and sorting by step in ascending order
	        return milestoneRepo.findByProjectidOrderByStepAsc(projectid);
	    } catch (Exception e) {
	        throw new RuntimeException("Error occurred while fetching milestones: " + e.getMessage());
	    }
	}


	public String updateMilestones(List<MileStones> milestones) {
	    if (milestones == null || milestones.isEmpty()) {
	        return "No milestones to update.";
	    }

	    try {
	        for (MileStones milestone : milestones) {
	            Optional<MileStones> existingMilestoneOpt = milestoneRepo.findById(milestone.getMilestoneid());
	            
	            if (existingMilestoneOpt.isPresent()) {
	                MileStones existingMilestone = existingMilestoneOpt.get();
	                
	                if (milestone.getName() != null) {
	                    existingMilestone.setName(milestone.getName());
	                }
	                if (milestone.getStart_date() != null) {
	                    existingMilestone.setStart_date(milestone.getStart_date());
	                }
	                if (milestone.getTarget_date() != null) {
	                    existingMilestone.setTarget_date(milestone.getTarget_date());
	                }
	                if (milestone.getStatus() != null) {
	                    existingMilestone.setStatus(milestone.getStatus());
	                }
	                
	                existingMilestone.setUpdated_at(LocalDateTime.now());

	                milestoneRepo.save(existingMilestone);
	            } else {
	                System.out.println("Milestone with ID " + milestone.getMilestoneid() + " not found.");
	            }
	        }
	        return "Successfully updated " + milestones.size() + " milestones.";
	    } catch (Exception e) {
	        e.printStackTrace();
	        return "Error occurred while updating milestones: " + e.getMessage();
	    }
	}
	
	public boolean deleteMilestone(String mileId)
	{
		try {
			milestoneRepo.deleteById(mileId);
			
			return true;
		}
		catch(Exception e)
		{
			return false;
		}
	}

}
