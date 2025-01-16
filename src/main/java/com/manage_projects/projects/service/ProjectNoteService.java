package com.manage_projects.projects.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.manage_projects.projects.dto.ProjectNotesDto;
import com.manage_projects.projects.entity.KeyPoints;
import com.manage_projects.projects.entity.ProjectNotes;
import com.manage_projects.projects.repository.KeypointsRepository;
import com.manage_projects.projects.repository.ProjectNotesRepository;

@Service
public class ProjectNoteService {
	
	@Autowired
	private ProjectNotesRepository notesRepo;
	
	@Autowired
	private KeypointsRepository keypointRepo;
	
	public List<ProjectNotes> getNote(String projectid)
	{
		 try {
		        return notesRepo.findByProjectid(projectid);
		    } catch (Exception e) {
		        throw new RuntimeException("Error occurred while fetching milestones: " + e.getMessage());
		    }
	}
	
	public String createProjectNotes(ProjectNotesDto projectNotesDto) {
        if (projectNotesDto == null || projectNotesDto.getKeypoints() == null || projectNotesDto.getKeypoints().isEmpty()) {
            return "No keypoints to create.";
        }

        try {
            List<KeyPoints> keyPointsList = projectNotesDto.getKeypoints().stream()
                    .map(keyPointsDTO -> {
                        KeyPoints keyPoint = new KeyPoints();
                        keyPoint.setId(UUID.randomUUID().toString());
                        keyPoint.setNotesid(projectNotesDto.getNotesid());
                        keyPoint.setProjectid(projectNotesDto.getTag()); // Assuming tag is used as projectid for this example
                        keyPoint.setPoints(keyPointsDTO.getPoints());
                        return keyPoint;
                    })
                    .collect(Collectors.toList());

            keypointRepo.saveAll(keyPointsList);

            return "Successfully created " + keyPointsList.size() + " keypoints.";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error occurred while creating keypoints: " + e.getMessage();
        }
    }

}
