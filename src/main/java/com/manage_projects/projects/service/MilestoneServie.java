package com.manage_projects.projects.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.manage_projects.projects.dto.MilestoneDto;
import com.manage_projects.projects.dto.ModuleDto;
import com.manage_projects.projects.entity.MileStones;
import com.manage_projects.projects.entity.Module;
import com.manage_projects.projects.entity.ModuleTasks;
import com.manage_projects.projects.repository.MileStoneRepository;
import com.manage_projects.projects.repository.ModuleRepository;
import com.manage_projects.projects.repository.ModuleTasksRepository;

@Service
public class MilestoneServie {

    @Autowired
    private MileStoneRepository milestoneRepo;
    
    @Autowired
    private ModuleRepository moduleRepository;
    
    @Autowired
    private ModuleTasksRepository moduleTasksRepository;
    
    public String createMilestones(String projectid, List<MilestoneDto> milestones) {
        if (milestones == null || milestones.isEmpty()) {
            return "No milestones to create.";
        }
        try {
            for (MilestoneDto milestoneDto : milestones) {
                MileStones milestone = new MileStones();
                milestone.setMilestoneid(UUID.randomUUID().toString());
                milestone.setName(milestoneDto.getName());
                milestone.setDescription(milestoneDto.getDescription());
                milestone.setStart_date(milestoneDto.getStart_date());
                milestone.setTarget_date(milestoneDto.getTarget_date());
                milestone.setDue_date(milestoneDto.getDue_date());
                milestone.setProjectid(projectid);
                milestone.setStatus(milestoneDto.getStatus());
                milestone.setCreated_at(LocalDateTime.now());
                milestone.setStep(milestoneDto.getStep());
                milestone.setReason(milestoneDto.getReason());
                MileStones savedMilestone = milestoneRepo.save(milestone);

                // Save modules
                if (milestoneDto.getModules() != null) {
                    for (ModuleDto moduleDto : milestoneDto.getModules()) {
                        Module module = new Module();
                        module.setModuleid(UUID.randomUUID().toString());
                        module.setModulename(moduleDto.getModulename());
                        module.setDescription(moduleDto.getDescription());
                        module.setMilestoneid(savedMilestone.getMilestoneid());
                        module.setStatus(moduleDto.getStatus());
                        Module savedModule = moduleRepository.save(module);

                        // Save module tasks
                        if (moduleDto.getModuletasks() != null) {
                            for (ModuleTasks taskDto : moduleDto.getModuletasks()) {
                                ModuleTasks task = new ModuleTasks();
                                task.setMtasksid(UUID.randomUUID().toString());
                                task.setTaskname(taskDto.getTaskname());
                                task.setStatus(taskDto.getStatus());
                                task.setModuleid(savedModule.getModuleid());
                                task.setAdded(false);
                                moduleTasksRepository.save(task);
                            }
                        }
                    }
                }
            }
            return "Successfully created milestones for project " + projectid;
        } catch (Exception e) {
            e.printStackTrace();
            return "Error occurred while creating milestones: " + e.getMessage();
        }
    }

    public String createMileStone(MilestoneDto milestoneDto) {
        if (milestoneDto == null) {
            return "Milestone cannot be null.";
        }

        try {
            MileStones milestone = new MileStones();
            milestone.setMilestoneid(UUID.randomUUID().toString());
            milestone.setName(milestoneDto.getName());
            milestone.setDescription(milestoneDto.getDescription());
            milestone.setStart_date(milestoneDto.getStart_date());
            milestone.setTarget_date(milestoneDto.getTarget_date());
            milestone.setDue_date(milestoneDto.getDue_date());
            milestone.setProjectid(milestoneDto.getProjectid());
            milestone.setStatus(milestoneDto.getStatus());
            milestone.setCreated_at(LocalDateTime.now());
            milestone.setStep(milestoneDto.getStep());
            milestone.setReason(milestoneDto.getReason());
            
            int insertStep = milestone.getStep();

            List<MileStones> milestonesToUpdate = milestoneRepo.findByStepGreaterThanEqualAndProjectid(insertStep, milestone.getProjectid());
            for (MileStones m : milestonesToUpdate) {
                m.setStep(m.getStep() + 1);
            }

            milestoneRepo.saveAll(milestonesToUpdate);
            MileStones savedMilestone = milestoneRepo.save(milestone);
            for (ModuleDto moduleDto : milestoneDto.getModules()) {
                Module module = new Module();
                module.setModuleid(UUID.randomUUID().toString());
                module.setModulename(moduleDto.getModulename());
                module.setDescription(moduleDto.getDescription());
                module.setMilestoneid(savedMilestone.getMilestoneid());
                module.setStatus(moduleDto.getStatus());
                Module savedModule = moduleRepository.save(module);

                // Save module tasks
                if (moduleDto.getModuletasks() != null) {
                    for (ModuleTasks taskDto : moduleDto.getModuletasks()) {
                        ModuleTasks task = new ModuleTasks();
                        task.setMtasksid(UUID.randomUUID().toString());
                        task.setTaskname(taskDto.getTaskname());
                        task.setStatus(taskDto.getStatus());
                        task.setModuleid(savedModule.getModuleid());
                        task.setAdded(false);
                        moduleTasksRepository.save(task);
                    }
                }
            }

            return "Successfully created milestone with ID: " + milestone.getMilestoneid();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error occurred while creating milestone: " + e.getMessage();
        }
    }

    public List<MilestoneDto> getAllMilestones(String projectId) {
        try {
            // Fetch all milestones for the project ordered by step
            List<MileStones> milestones = milestoneRepo.findByProjectidOrderByStepAsc(projectId);
            
            return milestones.stream()
                .map(milestone -> {
                    // For each milestone, fetch the associated modules
                    List<ModuleDto> modules = moduleRepository.findByMilestoneid(milestone.getMilestoneid())
                        .stream()
                        .map(module -> {
                            // For each module, fetch the associated tasks
                            List<ModuleTasks> tasks = moduleTasksRepository.findByModuleid(module.getModuleid());
                            return new ModuleDto(
                                    module.getModuleid(),
                                    module.getModulename(),
                                    module.getDescription(),
                                    module.getMilestoneid(),
                                    module.getStatus(),
                                    tasks
                            );
                        })
                        .collect(Collectors.toList());

                    // Map Milestone to MilestoneDto
                    return new MilestoneDto(
                            milestone.getMilestoneid(),
                            milestone.getName(),
                            milestone.getDescription(),
                            milestone.getStart_date(),
                            milestone.getTarget_date(),
                            milestone.getDue_date(),
                            milestone.getProjectid(),
                            milestone.getStatus(),
                            milestone.getCreated_at(),
                            milestone.getUpdated_at(),
                            milestone.getStep(),
                            milestone.getReason(),
                            modules
                    );
                })
                .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while fetching milestones: " + e.getMessage());
        }
    }

    private MilestoneDto convertToDto(MileStones milestone) {
        MilestoneDto dto = new MilestoneDto();
        dto.setMilestoneid(milestone.getMilestoneid());
        dto.setName(milestone.getName());
        dto.setDescription(milestone.getDescription());
        dto.setStart_date(milestone.getStart_date());
        dto.setTarget_date(milestone.getTarget_date());
        dto.setDue_date(milestone.getDue_date());
        dto.setProjectid(milestone.getProjectid());
        dto.setStatus(milestone.getStatus());
        dto.setStep(milestone.getStep());
        dto.setReason(milestone.getReason());
        return dto;
    }

    public Optional<MilestoneDto> getMilestoneById(String milestoneId) {
        try {
            return milestoneRepo.findById(milestoneId).map(milestone -> {
                // Get associated modules for the milestone
                List<ModuleDto> modules = moduleRepository.findByMilestoneid(milestone.getMilestoneid())
                    .stream()
                    .map(module -> {
                        // For each module, get associated tasks
                        List<ModuleTasks> tasks = moduleTasksRepository.findByModuleid(module.getModuleid());
                        return new ModuleDto(
                                module.getModuleid(),
                                module.getModulename(),
                                module.getDescription(),
                                module.getMilestoneid(),
                                module.getStatus(),
                                tasks
                        );
                    })
                    .collect(Collectors.toList());
                
                // Map Milestone to MilestoneDto
                return new MilestoneDto(
                        milestone.getMilestoneid(),
                        milestone.getName(),
                        milestone.getDescription(),
                        milestone.getStart_date(),
                        milestone.getTarget_date(),
                        milestone.getDue_date(),
                        milestone.getProjectid(),
                        milestone.getStatus(),
                        milestone.getCreated_at(),
                        milestone.getUpdated_at(),
                        milestone.getStep(),
                        milestone.getReason(),
                        modules
                );
            });
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while fetching milestone: " + e.getMessage());
        }
    }
    public String updateMilestones(List<MilestoneDto> milestoneDtos) {
        if (milestoneDtos == null || milestoneDtos.isEmpty()) {
            return "No milestones to update.";
        }

        try {
            for (MilestoneDto milestoneDto : milestoneDtos) {
                Optional<MileStones> existingMilestoneOpt = milestoneRepo.findById(milestoneDto.getMilestoneid());

                if (existingMilestoneOpt.isPresent()) {
                    MileStones existingMilestone = existingMilestoneOpt.get();
                    Integer oldStep = existingMilestone.getStep();
                    Integer newStep = milestoneDto.getStep();
                    String projectId = existingMilestone.getProjectid(); // Ensure we update only within the same project

                    if (newStep != null && !newStep.equals(oldStep)) {
                        if (newStep > oldStep) {
                            milestoneRepo.incrementStepsForMoveUp(oldStep + 1, newStep, projectId);
                        } else {
                            milestoneRepo.decrementStepsForMoveDown(newStep, oldStep - 1, projectId);
                        }
                        existingMilestone.setStep(newStep);
                    }

                    if (milestoneDto.getName() != null) {
                        existingMilestone.setName(milestoneDto.getName());
                    }
                    if (milestoneDto.getDescription() != null) {
                        existingMilestone.setDescription(milestoneDto.getDescription());
                    }
                    if (milestoneDto.getStart_date() != null) {
                        existingMilestone.setStart_date(milestoneDto.getStart_date());
                    }
                    if (milestoneDto.getTarget_date() != null) {
                        existingMilestone.setTarget_date(milestoneDto.getTarget_date());
                    }
                    if (milestoneDto.getDue_date() != null) {
                        existingMilestone.setDue_date(milestoneDto.getDue_date());
                    }
                    if (milestoneDto.getStatus() != null) {
                        existingMilestone.setStatus(milestoneDto.getStatus());
                    }
                    if (milestoneDto.getReason() != null && "On Hold".equals(milestoneDto.getStatus())) {
                        existingMilestone.setReason(milestoneDto.getReason());
                    } else {
                        existingMilestone.setReason("");
                    }

                    existingMilestone.setUpdated_at(LocalDateTime.now());
                    milestoneRepo.save(existingMilestone);
                } else {
                    System.out.println("Milestone with ID " + milestoneDto.getMilestoneid() + " not found.");
                }
            }
            return "Successfully updated " + milestoneDtos.size() + " milestones.";
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
                String projectId = milestone.getProjectid();  

                milestoneRepo.deleteById(mileId);

                milestoneRepo.updateStepNumbersForProject(stepNumberToDelete, projectId);
                
                deleteModulesByMilestoneId(mileId);
                
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @Transactional
    public boolean deleteMilestonesByProjectId(String projectId) {
        try {
            List<MileStones> milestones = milestoneRepo.findByProjectid(projectId);

            if (milestones.isEmpty()) {
                return false; 
            }

            for (MileStones milestone : milestones) {
                int stepNumberToDelete = milestone.getStep();
                String milestoneId = milestone.getMilestoneid();

                milestoneRepo.deleteById(milestoneId);

                milestoneRepo.updateStepNumbersForProject(stepNumberToDelete, projectId);

                deleteModulesByMilestoneId(milestoneId);
            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public ModuleTasks createTask(ModuleTasks moduleTask) {
        return moduleTasksRepository.save(moduleTask);
    }

    public List<ModuleTasks> bulkCreateTasks(List<ModuleTasks> tasks) {
        return moduleTasksRepository.saveAll(tasks);
    }

    public ModuleTasks editTask(String mtasksid, ModuleTasks updatedTask) {
        return moduleTasksRepository.findById(mtasksid)
            .map(existingTask -> {
                existingTask.setTaskname(updatedTask.getTaskname());
                existingTask.setStatus(updatedTask.getStatus());
                existingTask.setModuleid(updatedTask.getModuleid());
                return moduleTasksRepository.save(existingTask);
            }).orElseThrow(() -> new RuntimeException("Task not found"));
    }

    public List<ModuleTasks> getAllTasksByModuleId(String moduleid) {
        return moduleTasksRepository.findByModuleid(moduleid);
    }

    public ModuleTasks getTaskById(String mtasksid) {
        return moduleTasksRepository.findById(mtasksid)
            .orElseThrow(() -> new RuntimeException("Task not found"));
    }

    public void deleteTaskById(String mtasksid) {
        if (moduleTasksRepository.existsById(mtasksid)) {
            moduleTasksRepository.deleteById(mtasksid);
        } else {
            throw new RuntimeException("Task not found");
        }
    }

    @Transactional
    public void deleteTasksByModuleId(String moduleid) {
    	System.err.print("Module id" + moduleid);

        moduleTasksRepository.deleteByModuleid(moduleid);
    }

    public ModuleDto createModule(ModuleDto moduleDto) {
        Module module = mapDtoToEntity(moduleDto);
        Module savedModule = moduleRepository.save(module);

        // Save module tasks
        if (moduleDto.getModuletasks() != null) {
            for (ModuleTasks taskDto : moduleDto.getModuletasks()) {
                ModuleTasks task = new ModuleTasks();
                task.setMtasksid(UUID.randomUUID().toString());
                task.setTaskname(taskDto.getTaskname());
                task.setStatus(taskDto.getStatus());
                task.setModuleid(savedModule.getModuleid());
                task.setAdded(false);
                moduleTasksRepository.save(task);
            }
        }
        return moduleDto;
    }

    public List<ModuleDto> bulkCreateModules(List<ModuleDto> moduleDtos) {
    	for (ModuleDto moduleDto : moduleDtos) {
            Module module = new Module();
            module.setModuleid(UUID.randomUUID().toString());
            module.setModulename(moduleDto.getModulename());
            module.setDescription(moduleDto.getDescription());
            module.setMilestoneid(moduleDto.getMilestoneid());
            module.setStatus(moduleDto.getStatus());
            Module savedModule = moduleRepository.save(module);

            // Save module tasks
            if (moduleDto.getModuletasks() != null) {
                for (ModuleTasks taskDto : moduleDto.getModuletasks()) {
                    ModuleTasks task = new ModuleTasks();
                    task.setMtasksid(UUID.randomUUID().toString());
                    task.setTaskname(taskDto.getTaskname());
                    task.setStatus(taskDto.getStatus());
                    task.setModuleid(savedModule.getModuleid());
                    task.setAdded(false);
                    moduleTasksRepository.save(task);
                }
            }
        }

        return moduleDtos;
    }

    @Transactional
    public ModuleDto updateModule(String moduleid, ModuleDto moduleDto) {

        return moduleRepository.findById(moduleid)
            .map(existingModule -> {
                existingModule.setModulename(moduleDto.getModulename());
                existingModule.setDescription(moduleDto.getDescription());
                moduleRepository.save(existingModule);
                return moduleDto;
            }).orElseThrow(() -> new RuntimeException("Module not found"));
    }

    public List<ModuleDto> getModulesByMilestoneId(String milestoneid) {
        return moduleRepository.findByMilestoneid(milestoneid)
            .stream()
            .map(module -> {
                List<ModuleTasks> tasks = moduleTasksRepository.findByModuleid(module.getModuleid());
                return new ModuleDto(
                        module.getModuleid(),
                        module.getModulename(),
                        module.getDescription(),
                        module.getMilestoneid(),
                        module.getStatus(),
                        tasks
                );
            })
            .collect(Collectors.toList());
    }
    
    public ModuleDto getModuleById(String moduleid) {
                return moduleRepository.findById(moduleid)
            .map(module -> {
                List<ModuleTasks> tasks = moduleTasksRepository.findByModuleid(moduleid);
                
                return new ModuleDto(
                        module.getModuleid(),
                        module.getModulename(),
                        module.getDescription(),
                        module.getMilestoneid(),
                        module.getStatus(),
                        tasks
                );
            })
            .orElseThrow(() -> new RuntimeException("Module not found"));
    }

    @Transactional
    public void deleteModuleById(String moduleid) {
        deleteTasksByModuleId(moduleid);
        moduleRepository.deleteById(moduleid);
    }

    @Transactional
    public void deleteModulesByMilestoneId(String milestoneid) {
        List<Module> modules = moduleRepository.findByMilestoneid(milestoneid);
        for (Module module : modules) {
        	System.err.print("Module id" + module.getModuleid());
            deleteTasksByModuleId(module.getModuleid());
        }
        moduleRepository.deleteByMilestoneid(milestoneid);
    }

    private Module mapDtoToEntity(ModuleDto dto) {
        Module module = new Module();
        module.setModuleid(dto.getModuleid());
        module.setModulename(dto.getModulename());
        module.setDescription(dto.getDescription());
        module.setMilestoneid(dto.getMilestoneid());
        module.setStatus(dto.getStatus());
        return module;
    }

    private ModuleDto mapEntityToDto(Module module) {
        ModuleDto dto = new ModuleDto();
        dto.setModuleid(module.getModuleid());
        dto.setModulename(module.getModulename());
        dto.setDescription(module.getDescription());
        dto.setMilestoneid(module.getMilestoneid());
        dto.setStatus(module.getStatus());
        return dto;
    }
    
    public boolean updateAsAdded(String mtaskid, String status) {
        Optional<ModuleTasks> mtaskOptional = moduleTasksRepository.findById(mtaskid);
        if (mtaskOptional.isPresent()) {
            ModuleTasks task = mtaskOptional.get();
            task.setAdded(true);
            task.setStatus(status);
            moduleTasksRepository.save(task);
            boolean response = updateModuleStatus(task.getModuleid());
            if(response)
            {
            	return true;
            }
            else {
            	return false;
            }
        }
        return false;
    }
    
    public boolean updateAsRemoved(String mtaskid) {
        Optional<ModuleTasks> mtaskOptional = moduleTasksRepository.findById(mtaskid);
        if (mtaskOptional.isPresent()) {
            ModuleTasks task = mtaskOptional.get();
            task.setAdded(false);
            task.setStatus("To Do");
            moduleTasksRepository.save(task);
            return true;
        }
        return false;
    }
    
    public List<ModuleTasks> getAllTasksNotStatedByModuleId(String moduleId) {
        return moduleTasksRepository.findByModuleidAndAddedIsFalse(moduleId);
    }
    
    @Transactional
    public boolean updateModuleStatus(String moduleid) {
        List<ModuleTasks> moduleTasksList = moduleTasksRepository.findByModuleid(moduleid);

        if (moduleTasksList.isEmpty()) {
            return false; 
        }

        // Determine module status based on tasks
        boolean allToDo = moduleTasksList.stream().allMatch(task -> "To Do".equals(task.getStatus()));
        boolean allCompleted = moduleTasksList.stream().allMatch(task -> "Completed".equals(task.getStatus()));

        String newModuleStatus;
        if (allToDo) {
            newModuleStatus = "To Do";
        } else if (allCompleted) {
            newModuleStatus = "Completed";
        } else {
            newModuleStatus = "In Progress";
        }

        // Update module status in the database
        moduleRepository.updateModuleStatus(moduleid, newModuleStatus);

        // Check milestone status
        updateMilestoneStatusByModule(moduleid);

        return true;
    }

    /**
     * Update milestone status based on module statuses.
     */
    @Transactional
    public void updateMilestoneStatusByModule(String moduleid) {
        String milestoneId = moduleRepository.findMilestoneIdByModuleId(moduleid);
        
        if (milestoneId == null) {
            return;
        }

        List<String> moduleStatuses = moduleRepository.findModuleStatusesByMilestoneId(milestoneId);

        boolean allToDoOrUpcoming = moduleStatuses.stream().allMatch(status -> "To Do".equals(status) || "Upcoming".equals(status));
        boolean allCompleted = moduleStatuses.stream().allMatch(status -> "Completed".equals(status));

        String newMilestoneStatus;
        if (allToDoOrUpcoming) {
            newMilestoneStatus = "Upcoming";
        } else if (allCompleted) {
            newMilestoneStatus = "Completed";
        } else {
            newMilestoneStatus = "In Progress";
        }

        milestoneRepo.updateMilestoneStatus(milestoneId, newMilestoneStatus);
    }

}
