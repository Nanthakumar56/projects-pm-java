package com.manage_projects.projects.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.manage_projects.projects.dto.AllTaskDto;
import com.manage_projects.projects.dto.NewProjectDto;
import com.manage_projects.projects.dto.ProjectDto;
import com.manage_projects.projects.dto.ProjectMembersDto;
import com.manage_projects.projects.dto.UserResponse;
import com.manage_projects.projects.entity.Members;
import com.manage_projects.projects.entity.MembersId;
import com.manage_projects.projects.entity.Projects;
import com.manage_projects.projects.repository.DocumentsRepository;
import com.manage_projects.projects.repository.MembersRepository;
import com.manage_projects.projects.repository.MileStoneRepository;
import com.manage_projects.projects.repository.ProjectNotesRepository;
import com.manage_projects.projects.repository.ProjectsRepository;


@Service
public class ProjectsService {
	
	@Autowired
	private ProjectsRepository projectsRepo;

	@Autowired
	private MembersRepository membersRepo;
	
	@Autowired
	private MileStoneRepository milestoneRepo;
	
	@Autowired
	private DocumentsRepository documenentsRepo;
	
	@Autowired
	private ProjectNotesRepository notesRepo;
	
	@Autowired
	private MilestoneServie milestoneService;
	
	public Projects createProject(NewProjectDto projectDTO) {
		
		Projects project = new Projects();	
	    project.setProjectid(UUID.randomUUID().toString());  
	    project.setProjectname(projectDTO.getTitle());
	    project.setProjectdescription(projectDTO.getDescription());
	    project.setStart_date(projectDTO.getStartdate());
	    project.setEnd_date(projectDTO.getDuedate());
	    project.setStatus(projectDTO.getStatus());
	    project.setCreated_at(LocalDateTime.now());
	    project.setPriority(projectDTO.getPriority());
	    project.setProject_manager_id(projectDTO.getProject_manager());
	    project.setTask_approver(projectDTO.getTask_supervisor());
	    project.setPjtmain_id(projectDTO.getPjtmain_id());
	    if(projectDTO.getStatus().equals("On Hold"))
	    {
	    	project.setReason(projectDTO.getReason());
	    }
	   
	    Projects savedProject = projectsRepo.save(project);
	    
	    List<String> userIds = projectDTO.getUserIds();
        for (String userId : userIds) {
            createMember(savedProject.getProjectid(), userId);
        }
        
        milestoneService.createMileStones(savedProject.getProjectid(),projectDTO.getMilestones());
        
		return savedProject;
	}
	

	public List<ProjectDto> getAllProjects() {
	    List<Projects> projectList = projectsRepo.findAll();
	    List<ProjectDto> projectDTOList = new ArrayList<>();
	    
	    RestTemplate restTemplate = new RestTemplate();
	    
	    for (Projects project : projectList) {
	        ProjectDto projectdto = new ProjectDto();
	        projectdto.setProjectid(project.getProjectid());
	        projectdto.setProjectname(project.getProjectname());
	        projectdto.setProjectdescription(project.getProjectdescription());
	        projectdto.setStart_date(project.getStart_date());
	        projectdto.setEnd_date(project.getEnd_date());
	        projectdto.setCompleted_at(project.getCompleted_at());
	        projectdto.setStatus(project.getStatus());
	        projectdto.setCreated_at(project.getCreated_at());
	        projectdto.setUpdated_at(project.getUpdated_at());
	        projectdto.setPriority(project.getPriority());
	        projectdto.setProject_manager_id(project.getProject_manager_id());
	        projectdto.setTask_approver(project.getTask_approver());
	        projectdto.setPjtmain_id(project.getPjtmain_id());
	        projectdto.setIs_archived(project.getIs_archived());
	        projectdto.setStarted_on(project.getStarted_on());
	        
	        String projectManagerId = project.getProject_manager_id();
	       
	        String url = "http://localhost:5656/users/getName?userId=" + projectManagerId;

	        try {
	            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
	            if (response.getStatusCode().is2xxSuccessful()) {
	                String projectManagerName = response.getBody();
	                projectdto.setProject_manager(projectManagerName);  
	            } else {
	                projectdto.setProject_manager("Unknown");  
	            }
	        } catch (Exception e) {
	            projectdto.setProject_manager("Unknown");
	        }
	        
	        String approverId = project.getTask_approver();
		       
	        String url1 = "http://localhost:5656/users/getName?userId=" + approverId;

	        try {
	            ResponseEntity<String> response = restTemplate.getForEntity(url1, String.class);
	            if (response.getStatusCode().is2xxSuccessful()) {
	                String Approvername = response.getBody();
	                projectdto.setApprover_name(Approvername);  
	            } else {
	                projectdto.setApprover_name("Unknown");  
	            }
	        } catch (Exception e) {
	            projectdto.setApprover_name("Unknown");
	        }


	        List<String> userIds = membersRepo.findUserIdsByProjectId(project.getProjectid());
	        projectdto.setUserIds(userIds);

	        projectDTOList.add(projectdto);
	    }
	    
	    return projectDTOList;
	}


	public ProjectDto getProject(String projectid) {
	    Optional<Projects> project = projectsRepo.findById(projectid);
	    ProjectDto projectdto = new ProjectDto();

	    if (project.isPresent()) {
	        Projects actualProject = project.get(); 
	        projectdto.setProjectid(actualProject.getProjectid());
	        projectdto.setProjectname(actualProject.getProjectname());
	        projectdto.setProjectdescription(actualProject.getProjectdescription());
	        projectdto.setStart_date(actualProject.getStart_date());
	        projectdto.setEnd_date(actualProject.getEnd_date());
	        projectdto.setCompleted_at(actualProject.getCompleted_at());
	        projectdto.setStatus(actualProject.getStatus());
	        projectdto.setCreated_at(actualProject.getCreated_at());
	        projectdto.setUpdated_at(actualProject.getUpdated_at());
	        projectdto.setPriority(actualProject.getPriority());
	        projectdto.setReason(actualProject.getReason());
	        projectdto.setProject_manager_id(actualProject.getProject_manager_id());
	        projectdto.setTask_approver(actualProject.getTask_approver());
	        projectdto.setPjtmain_id(actualProject.getPjtmain_id());
	        projectdto.setIs_archived(actualProject.getIs_archived());
	        projectdto.setStarted_on(actualProject.getStarted_on());
	        String projectManagerId = actualProject.getProject_manager_id();
	        
	        RestTemplate restTemplate = new RestTemplate();
	        String url = "http://localhost:5656/users/getUser?userId=" + projectManagerId;
	        
	        try {
	            ResponseEntity<UserResponse> response = restTemplate.getForEntity(url, UserResponse.class);
	            UserResponse user = response.getBody();
	            
	            if (user != null) {
	                String fullName = user.getFirst_name();
	                if (user.getLast_name() != null && !user.getLast_name().isEmpty()) {
	                    fullName += " " + user.getLast_name();
	                }
	                projectdto.setProject_manager(fullName); 
	                projectdto.setManagerFile(user.getFile());  
	            }
	        } catch (Exception e) {
	            System.err.println("Error fetching project manager details: " + e.getMessage());
	            projectdto.setProject_manager("Unknown");
	        }
	        
	        String taskApproverId = actualProject.getTask_approver();

	        RestTemplate restTemplate1 = new RestTemplate();
	        String url1 = "http://localhost:5656/users/getUser?userId=" + taskApproverId;
	        
	        try {
	            ResponseEntity<UserResponse> response = restTemplate1.getForEntity(url1, UserResponse.class);
	            UserResponse user = response.getBody();
	            
	            if (user != null) {
	                String fullName = user.getFirst_name();
	                if (user.getLast_name() != null && !user.getLast_name().isEmpty()) {
	                    fullName += " " + user.getLast_name();
	                }
	                projectdto.setApprover_name(fullName); 
	                projectdto.setApproverFile(user.getFile());  
	            }
	        } catch (Exception e) {
	            System.err.println("Error fetching task approver details: " + e.getMessage());
	            projectdto.setApprover_name("Unknown");
	        }

	        String tasksUrl = "http://localhost:6262/tasks/getTasksByProject?projectId=" + projectid;

	        try {
	            // Use ParameterizedTypeReference to specify the response type
	            ResponseEntity<List<AllTaskDto>> tasksResponse = restTemplate.exchange(
	                tasksUrl, HttpMethod.GET, null, 
	                new ParameterizedTypeReference<List<AllTaskDto>>() {}
	            );
	            List<AllTaskDto> tasks = tasksResponse.getBody();
	            System.err.println(tasks);
	            
	            if (tasks != null) {
	                int totalTasks = tasks.size();
	                int completedTasks = 0;
	                int pendingTasks = 0;
	                
	                for (AllTaskDto task : tasks) {
	                    if ("completed".equalsIgnoreCase(task.getStatus())) {
	                        completedTasks++;
	                    } else {
	                        pendingTasks++;
	                    }
	                }

	                projectdto.setTotaltasks(String.valueOf(totalTasks));
	                projectdto.setClosedtasks(String.valueOf(completedTasks));
	                projectdto.setPendingtasks(String.valueOf(pendingTasks));
	            }
	        } catch (Exception e) {
	            System.err.println("Error fetching tasks details: " + e.getMessage());
	        }

	        List<String> userIds = membersRepo.findUserIdsByProjectId(actualProject.getProjectid());
	        projectdto.setUserIds(userIds);
	    }

	    return projectdto;
	}

	
	public boolean updateProject(NewProjectDto projectdto) {
	    try {
	        return projectsRepo.findById(projectdto.getProjectid())
	            .map(existProject -> {
	                if (projectdto.getTitle() != null) {
	                    existProject.setProjectname(projectdto.getTitle());
	                }
	                if (projectdto.getDescription() != null) {
	                    existProject.setProjectdescription(projectdto.getDescription());
	                }
	                if (projectdto.getStartdate() != null) {
	                    existProject.setStart_date(projectdto.getStartdate());
	                }
	                if (projectdto.getDuedate() != null) {
	                    existProject.setEnd_date(projectdto.getDuedate());
	                }
	                if (projectdto.getStatus() != null) {
	                	if(existProject.getStatus().equals("Upcoming") && projectdto.getStatus().equals("In Progress"))
	                	{
	                		existProject.setStarted_on(LocalDateTime.now());
	                	}
	                	if(projectdto.getStatus().equals("On Hold"))
	                	{
	                		existProject.setReason(projectdto.getReason());
	                	}
	                	else {
	                		existProject.setReason("");
	                	}
	                	if(projectdto.getStatus().equals("Completed"))
	                	{
	                		existProject.setCompleted_at(LocalDateTime.now());
	                	}              	
	                		
	                		existProject.setStatus(projectdto.getStatus());
	                	
	                }

	                existProject.setUpdated_at(LocalDateTime.now());

	                if (projectdto.getPriority() != null) {
	                    existProject.setPriority(projectdto.getPriority());
	                }
	                if (projectdto.getProject_manager() != null) {
	                    existProject.setProject_manager_id(projectdto.getProject_manager());
	                }
	                if (projectdto.getTask_supervisor() != null) {
	                    existProject.setTask_approver(projectdto.getTask_supervisor());
	                }
	                
	                // Save the updated project details
	                projectsRepo.save(existProject);

	                // Handle userIds (project members) updates
	                List<String> newUserIds = projectdto.getUserIds();
	                List<String> currentUserIds = membersRepo.findUserIdsByProjectId(existProject.getProjectid());

	                for (String userId : newUserIds) {
	                    if (!currentUserIds.contains(userId)) {
	                        MembersId newMemberId = new MembersId(existProject.getProjectid(),userId);
	                        Members newMember = new Members(newMemberId);
	                        membersRepo.save(newMember); 
	                    }
	                }

	                for (String userId : currentUserIds) {
	                    if (!newUserIds.contains(userId)) {
	                        MembersId memberIdToRemove = new MembersId(existProject.getProjectid(),userId);
	                        membersRepo.deleteById(memberIdToRemove);  
	                    }
	                }

	                return true;
	            })
	            .orElse(false);
	    } catch (Exception e) {
	        System.err.println("Error updating project: " + e.getMessage());
	        return false;
	    }
	}
	@Transactional
	public boolean deleteProject(String projectId) {
	    try {
	        // Call the external tasks deletion API
	        RestTemplate restTemplate = new RestTemplate();
	        String taskServiceUrl = "http://localhost:6262/tasks/deleteByProject?projectid=" + projectId;
	        ResponseEntity<String> response = restTemplate.exchange(taskServiceUrl, HttpMethod.DELETE, null, String.class);

	        if (!response.getStatusCode().is2xxSuccessful()) {
	            return false; // If task deletion fails, stop the process
	        }

	        // Proceed with deleting project-related entities
	        membersRepo.deleteByProjectId(projectId);
	        documenentsRepo.deleteByProjectId(projectId);
	        notesRepo.deleteByProjectId(projectId);
	        milestoneRepo.deleteByProjectId(projectId);
	        projectsRepo.deleteById(projectId);

	        return true;
	    } catch (Exception e) {
	        return false;
	    }
	}

	public Members createMember(String projectid, String userid) {
        MembersId membersId = new MembersId(projectid, userid);
        Members member = new Members(membersId); 
        return membersRepo.save(member); 
}
    public void bulkAddMembers(List<Members> members) {
    	membersRepo.saveAll(members);
    }

    public void removeMember(MembersId membersId) {
    	membersRepo.deleteById(membersId);
    }

    public List<String> getUserIdsByProjectId(String projectid) {
        return membersRepo.findUserIdsByProjectId(projectid);
    }

    public void removeAllMembersByProjectId(String projectid) {
    	membersRepo.deleteByProjectId(projectid);
    }
    
    public void updateProjectMembers(String projectid, ProjectMembersDto members) {
        if (members.getCreateUser() != null) {
            for (String userid : members.getCreateUser()) {
                createMember(projectid, userid);
            }
        }

        if (members.getRemoveUser() != null) {
            for (String userid : members.getRemoveUser()) {
                MembersId membersId = new MembersId(projectid, userid);
                removeMember(membersId);
            }
        }
    }
} 
