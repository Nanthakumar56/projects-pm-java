package com.manage_projects.projects.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.manage_projects.projects.entity.MileStones;
import com.manage_projects.projects.repository.MileStoneRepository;

@Service
public class MilestoneServie {

    @Autowired
    private MileStoneRepository milestoneRepo;
    
    public String createMileStones(String projectid,List<MileStones> milestones) {
        if (milestones == null || milestones.isEmpty()) {
            return "No milestones to create.";
        }

        try {
            for (MileStones milestone : milestones) {
                if (milestone.getMilestoneid() == null) {
                    milestone.setMilestoneid(UUID.randomUUID().toString());
                }
                milestone.setProjectid(projectid);
                milestone.setCreated_at(LocalDateTime.now());

                milestoneRepo.save(milestone);
            }
            return "Successfully created " + milestones.size() + " milestones for project " + milestones.get(0).getProjectid() + ".";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error occurred while creating milestones: " + e.getMessage();
        }
    }

    public String createMileStone(MileStones milestone) {
        if (milestone == null) {
            return "Milestone cannot be null.";
        }

        try {
            if (milestone.getMilestoneid() == null) {
                milestone.setMilestoneid(UUID.randomUUID().toString());
            }
            milestone.setCreated_at(LocalDateTime.now());

            // Ensure project ID is set from the milestone object itself
            if (milestone.getProjectid() == null) {
                return "Project ID must be provided in the milestone.";
            }

            int insertStep = milestone.getStep();

            // Increment steps for existing milestones in the same project
            List<MileStones> milestonesToUpdate = milestoneRepo.findByStepGreaterThanEqualAndProjectid(insertStep, milestone.getProjectid());
            for (MileStones m : milestonesToUpdate) {
                m.setStep(m.getStep() + 1);
            }

            milestoneRepo.saveAll(milestonesToUpdate);
            milestoneRepo.save(milestone);

            return "Successfully created milestone with ID: " + milestone.getMilestoneid() + " for project " + milestone.getProjectid() + ".";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error occurred while creating milestone: " + e.getMessage();
        }
    }

    public List<MileStones> getAllMilestones(String projectId) {
        try {
            return milestoneRepo.findByProjectidOrderByStepAsc(projectId);
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while fetching milestones for project " + projectId + ": " + e.getMessage());
        }
    }

    public Optional<MileStones> getMilestoneById(String milestoneId) {
        try {
            return milestoneRepo.findById(milestoneId);
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while fetching the milestone: " + e.getMessage());
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
                    Integer oldStep = existingMilestone.getStep();
                    Integer newStep = milestone.getStep();
                    String projectid = existingMilestone.getProjectid();  // Project ID is retrieved here

                    if (newStep != null && !newStep.equals(oldStep)) {
                        if (newStep > oldStep) {
                            milestoneRepo.incrementStepsForMoveUp(oldStep + 1, newStep, projectid);
                        } else {
                            milestoneRepo.decrementStepsForMoveDown(newStep, oldStep - 1, projectid); 
                        }

                        existingMilestone.setStep(newStep);
                    }

                    // Update milestone fields
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
                    if (milestone.getReason() != null && milestone.getStatus().equals("On Hold")) {
                        existingMilestone.setReason(milestone.getReason());
                    } else {
                        existingMilestone.setReason("");
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

    @Transactional
    public boolean deleteMilestone(String mileId) {
        try {
            Optional<MileStones> milestoneToDelete = milestoneRepo.findById(mileId);
            if (milestoneToDelete.isPresent()) {
                MileStones milestone = milestoneToDelete.get();
                int stepNumberToDelete = milestone.getStep();
                String projectId = milestone.getProjectid();  // Get project ID from the milestone

                milestoneRepo.deleteById(mileId);

                milestoneRepo.updateStepNumbersForProject(stepNumberToDelete, projectId);

                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
