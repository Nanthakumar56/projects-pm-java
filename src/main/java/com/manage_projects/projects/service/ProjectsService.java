package com.manage_projects.projects.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.manage_projects.projects.dto.ProjectDto;
import com.manage_projects.projects.dto.UserResponse;
import com.manage_projects.projects.entity.Members;
import com.manage_projects.projects.entity.MembersId;
import com.manage_projects.projects.entity.Projects;
import com.manage_projects.projects.repository.MembersRepository;
import com.manage_projects.projects.repository.ProjectsRepository;


@Service
public class ProjectsService {
	
	@Autowired
	private ProjectsRepository projectsRepo;

	@Autowired
	private MembersRepository membersRepo;
	
	public Projects createProject(ProjectDto projectDTO) {
		
		Projects project = new Projects();	
	    project.setProjectid(UUID.randomUUID().toString());  
	    project.setProjectname(projectDTO.getProjectname());
	    project.setProjectdescription(projectDTO.getProjectdescription());
	    project.setStart_date(projectDTO.getStart_date());
	    project.setEnd_date(projectDTO.getEnd_date());
	    project.setStatus(projectDTO.getStatus());
	    project.setCreated_at(LocalDateTime.now());
	    project.setPrivacy(projectDTO.getPrivacy());
	    project.setPriority(projectDTO.getPriority());
	    project.setProject_manager_id(projectDTO.getproject_manager());
	    project.setClient_id(projectDTO.getClient_id());
	    project.setProgress("0");
	    project.setAttachments(null);
	    project.setIs_archived(false);
	    
	    Projects savedProject = projectsRepo.save(project);
	    List<String> userIds = projectDTO.getUserIds();
        for (String userId : userIds) {
            createMember(savedProject.getProjectid(), userId);
        }
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
	        projectdto.setClient_id(project.getClient_id());
	        projectdto.setProgress(project.getProgress());
	        projectdto.setAttachments(project.getAttachments());
	        projectdto.setIs_archived(project.getIs_archived());
	        
	        String projectManagerId = project.getProject_manager_id();
	        String url = "http://localhost:5656/users/getName?userId=" + projectManagerId;

	        try {
	            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
	            if (response.getStatusCode().is2xxSuccessful()) {
	                String projectManagerName = response.getBody();
	                projectdto.setproject_manager(projectManagerName);  
	            } else {
	                projectdto.setproject_manager("Unknown");  
	            }
	        } catch (Exception e) {
	            projectdto.setproject_manager("Unknown");
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
	        projectdto.setProject_manager_id(actualProject.getProject_manager_id());
	        projectdto.setClient_id(actualProject.getClient_id());
	        projectdto.setProgress(actualProject.getProgress());
	        projectdto.setAttachments(actualProject.getAttachments());
	        projectdto.setIs_archived(actualProject.getIs_archived());
	        
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
	                projectdto.setproject_manager(fullName); 

	                projectdto.setFile(user.getFile());  
	            }
	        } catch (Exception e) {
	            System.err.println("Error fetching project manager details: " + e.getMessage());
	            projectdto.setproject_manager("Unknown");
	        }
	        
	        List<String> userIds = membersRepo.findUserIdsByProjectId(actualProject.getProjectid());
	        projectdto.setUserIds(userIds);
	    }

	    return projectdto;
	}

	
	public boolean updateProject(ProjectDto projectdto) {
	    try {
	        return projectsRepo.findById(projectdto.getProjectid())
	            .map(existProject -> {
	                if (projectdto.getProjectname() != null) {
	                    existProject.setProjectname(projectdto.getProjectname());
	                }
	                if (projectdto.getProjectdescription() != null) {
	                    existProject.setProjectdescription(projectdto.getProjectdescription());
	                }
	                if (projectdto.getStart_date() != null) {
	                    existProject.setStart_date(projectdto.getStart_date());
	                }
	                if (projectdto.getEnd_date() != null) {
	                    existProject.setEnd_date(projectdto.getEnd_date());
	                }
	                if (projectdto.getStatus() != null) {
	                    existProject.setStatus(projectdto.getStatus());
	                }
	                if (projectdto.getCreated_at() != null) {
	                    existProject.setCreated_at(projectdto.getCreated_at());
	                }
	                existProject.setUpdated_at(LocalDateTime.now());

	                if (projectdto.getPriority() != null) {
	                    existProject.setPriority(projectdto.getPriority());
	                }
	                if (projectdto.getproject_manager() != null) {
	                    existProject.setProject_manager_id(projectdto.getproject_manager());
	                }
	                if (projectdto.getClient_id() != null) {
	                    existProject.setClient_id(projectdto.getClient_id());
	                }
	                if (projectdto.getProgress() != null) {
	                    existProject.setProgress(projectdto.getProgress());
	                }
	                if (projectdto.getAttachments() != null) {
	                    existProject.setAttachments(projectdto.getAttachments());
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
	public boolean deleteProject(String projectid) {
	    try {
	        if (projectsRepo.existsById(projectid)) {
	            membersRepo.deleteByProjectId(projectid);
	            
	            projectsRepo.deleteById(projectid);
	            return true;
	        } else {
	            System.err.println("Project not found with ID: " + projectid);
	            return false;
	        }
	    } catch (Exception e) {
	        System.err.println("Error deleting project: " + e.getMessage());
	        return false;
	    }
	}
	
	public Members createMember(String projectid, String userid) {
        MembersId membersId = new MembersId(projectid, userid);
        Members member = new Members(membersId); 
        return membersRepo.save(member); 
}
    // Bulk add members to a project
    public void bulkAddMembers(List<Members> members) {
    	membersRepo.saveAll(members);
    }

    // Remove a single member from a project
    public void removeMember(MembersId membersId) {
    	membersRepo.deleteById(membersId);
    }

    // Get all userIds by projectId
    public List<String> getUserIdsByProjectId(String projectid) {
        return membersRepo.findUserIdsByProjectId(projectid);
    }

    // Remove all members from a project
    public void removeAllMembersByProjectId(String projectid) {
    	membersRepo.deleteByProjectId(projectid);
    }
}
