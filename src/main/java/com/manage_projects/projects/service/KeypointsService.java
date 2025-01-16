package com.manage_projects.projects.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.manage_projects.projects.entity.KeyPoints;
import com.manage_projects.projects.repository.KeypointsRepository;

@Service
public class KeypointsService {
	@Autowired
	private KeypointsRepository keypointRepo;
	
	public String createKeypoint(KeyPoints keyPoint) {
        try {
            if (keyPoint.getNotesid() == null || keyPoint.getProjectid() == null) {
                return "Notes ID and Project ID cannot be null.";
            }
            keyPoint.setId(UUID.randomUUID().toString());
            keypointRepo.save(keyPoint);

            return "Keypoint created successfully with ID: " + keyPoint.getId();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error occurred while creating Keypoint: " + e.getMessage();
        }
    }
	
	public String createKeypoints(List<KeyPoints> keyPointsList) {
	    if (keyPointsList == null || keyPointsList.isEmpty()) {
	        return "No keypoints to create.";
	    }

	    try {
	        for (KeyPoints keyPoint : keyPointsList) {
	            if (keyPoint.getNotesid() == null || keyPoint.getProjectid() == null) {
	                return "Notes ID and Project ID cannot be null for any keypoint.";
	            }
	            keyPoint.setId(UUID.randomUUID().toString());
	        }

	        keypointRepo.saveAll(keyPointsList);

	        return "Successfully created " + keyPointsList.size() + " keypoints.";
	    } catch (Exception e) {
	        e.printStackTrace();
	        return "Error occurred while creating keypoints: " + e.getMessage();
	    }
	}
	public List<KeyPoints> getKeypointsByNotesid(String notesid) {
        try {
            return keypointRepo.findByNotesid(notesid);
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while fetching keypoints: " + e.getMessage());
        }
    }
	public String deleteKeypointsByNotesid(String notesid) {
        try {
            keypointRepo.deleteByNotesid(notesid);
            return "All keypoints for notesid " + notesid + " have been successfully deleted.";
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while deleting keypoints: " + e.getMessage());
        }
    }
	
	public String deleteKeypointById(String id) {
        try {
            if (!keypointRepo.existsById(id)) {
                return "KeyPoint with ID " + id + " does not exist.";
            }
            keypointRepo.deleteById(id);
            return "KeyPoint with ID " + id + " has been successfully deleted.";
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while deleting KeyPoint: " + e.getMessage());
        }
    }

}
