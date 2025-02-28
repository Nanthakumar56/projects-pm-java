package com.manage_projects.projects.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.manage_projects.projects.dto.NewProjectDto;
import com.manage_projects.projects.dto.ProjectDto;
import com.manage_projects.projects.entity.Projects;
import com.manage_projects.projects.repository.DocumentsRepository;
import com.manage_projects.projects.repository.MembersRepository;
import com.manage_projects.projects.repository.ProjectNotesRepository;
import com.manage_projects.projects.repository.ProjectsRepository;

import java.time.LocalDateTime;
import java.util.*;

@ExtendWith(MockitoExtension.class)
class ProjectsServiceTest {

    @Mock
    private ProjectsRepository projectsRepo;

    @Mock
    private MembersRepository membersRepo;

    @Mock
    private DocumentsRepository documentsRepo;

    @Mock
    private ProjectNotesRepository notesRepo;

    @Mock
    private MilestoneServie milestoneService;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private ProjectsService projectsService;

    private Projects mockProject;
    private ProjectDto projectDto;
    private NewProjectDto newProjectDto;

    @BeforeEach
    void setUp() {
        mockProject = new Projects();
        mockProject.setProjectid("P123");
        mockProject.setProjectname("Test Project");
        mockProject.setProject_manager_id("M123");
        mockProject.setTask_approver("A123");

        projectDto = new ProjectDto();
        projectDto.setProjectid("P123");
        projectDto.setProjectname("Updated Project");
        projectDto.setProject_manager_id("M123");
        projectDto.setTask_approver("A123");
        projectDto.setUserIds(Arrays.asList("U1", "U2"));

        newProjectDto = new NewProjectDto();
        newProjectDto.setProjectid("P123");
        newProjectDto.setTitle("Updated Project");
        newProjectDto.setProject_manager("M123");
        newProjectDto.setStatus("Upcoming");
        newProjectDto.setTask_supervisor("A123");
        newProjectDto.setUserIds(Arrays.asList("U1", "U2"));
    }

    @Test
    void testGetAllProjects() {
        // Arrange
        List<Projects> projectsList = List.of(mockProject);
        when(projectsRepo.findAll()).thenReturn(projectsList);
       
        List<ProjectDto> result = projectsService.getAllProjects();

        // Assert
        assertEquals(1, result.size());
        assertEquals("Test Project", result.get(0).getProjectname());
        assertEquals("M123", result.get(0).getProject_manager_id());
        verify(projectsRepo, times(1)).findAll();
    }

    @Test
    void testCreateProject() {
        // Arrange
        when(projectsRepo.save(any(Projects.class))).thenReturn(mockProject);

        // Act
        Projects createdProject = projectsService.createProject(newProjectDto);

        // Assert
        assertNotNull(createdProject);
        assertEquals("P123", createdProject.getProjectid());
        verify(projectsRepo, times(1)).save(any(Projects.class));
        verify(membersRepo, times(2)).save(any());
    }
    
    @Test
    void testUpdateProject_Success() {
        // Arrange
        String projectId = "P123";
        NewProjectDto newProjectDto = new NewProjectDto();
        newProjectDto.setProjectid(projectId);
        newProjectDto.setTitle("Updated Project");
        newProjectDto.setDescription("Updated Description");
        newProjectDto.setStartdate(LocalDateTime.now());
        newProjectDto.setDuedate(LocalDateTime.now().plusDays(10));
        newProjectDto.setStatus("In Progress");
        newProjectDto.setPriority("High");
        newProjectDto.setProject_manager("U1");
        newProjectDto.setTask_supervisor("U2");
        newProjectDto.setUserIds(List.of("U1", "U3")); // Updated user list

        Projects existingProject = new Projects();
        existingProject.setProjectid(projectId);
        existingProject.setStatus("Upcoming"); // Ensuring transition to "In Progress"

        // Mock project retrieval
        when(projectsRepo.findById(projectId)).thenReturn(Optional.of(existingProject));

        // Mock saving project
        when(projectsRepo.save(any(Projects.class))).thenReturn(existingProject);

        // Mock existing members in the project
        when(membersRepo.findUserIdsByProjectId(projectId)).thenReturn(List.of("U1", "U2"));

        // Correct mock setup for saving new members (returns void)
        when(membersRepo.save(any())).thenReturn(null);

        // Correct mock setup for deleting members (doNothing() for void method)
        doNothing().when(membersRepo).deleteById(any());

        // Act
        boolean isUpdated = projectsService.updateProject(newProjectDto);

        // Assert
        assertTrue(isUpdated, "Project update should return true");

        verify(projectsRepo, times(1)).findById(projectId);
        verify(projectsRepo, times(1)).save(any(Projects.class));

        verify(membersRepo, times(1)).findUserIdsByProjectId(projectId);
        verify(membersRepo, times(1)).save(any());  // Adding new members
        verify(membersRepo, times(1)).deleteById(any());  // Removing old members
    }


    @Test
    void testUpdateProject_ProjectNotFound() {
        when(projectsRepo.findById("P123")).thenReturn(Optional.empty());

        boolean isUpdated = projectsService.updateProject(newProjectDto);

        assertFalse(isUpdated);
        verify(projectsRepo, times(1)).findById("P123");
        verify(projectsRepo, never()).save(any(Projects.class));
        verify(membersRepo, never()).save(any());
    }

    @Test
    void testDeleteProject_Success() {
        // Arrange
        when(projectsRepo.existsById("P123")).thenReturn(true); // Mock existsById to return true
        doNothing().when(membersRepo).deleteByProjectId("P123"); // Mock void methods
        doNothing().when(documentsRepo).deleteByProjectId("P123");
        doNothing().when(notesRepo).deleteByProjectId("P123");
        when(milestoneService.deleteMilestonesByProjectId("P123")).thenReturn(true); // Mock boolean method
        doNothing().when(projectsRepo).deleteById("P123");

        // Mock the external task service call
        String taskServiceUrl = "http://localhost:6262/tasks/deleteByProject?projectid=P123";
        when(restTemplate.exchange(eq(taskServiceUrl), eq(HttpMethod.DELETE), isNull(), eq(String.class)))
            .thenReturn(ResponseEntity.ok("Deleted"));

        // Act
        boolean isDeleted = projectsService.deleteProject("P123");

        // Assert
        assertTrue(isDeleted); // Ensure the method returns true
        verify(projectsRepo, times(1)).existsById("P123"); // Verify existsById is called
        verify(membersRepo, times(1)).deleteByProjectId("P123"); // Verify related entities are deleted
        verify(documentsRepo, times(1)).deleteByProjectId("P123");
        verify(notesRepo, times(1)).deleteByProjectId("P123");
        verify(milestoneService, times(1)).deleteMilestonesByProjectId("P123"); // Verify milestone deletion
        verify(projectsRepo, times(1)).deleteById("P123"); // Verify project is deleted
        verify(restTemplate, times(1)).exchange(eq(taskServiceUrl), eq(HttpMethod.DELETE), isNull(), eq(String.class)); // Verify external call
    }
    
    @Test
    void testDeleteProject_ProjectNotFound() {
        // Arrange
        when(projectsRepo.existsById("P123")).thenReturn(false); // Mock existsById to return false

        // Act
        boolean isDeleted = projectsService.deleteProject("P123");

        // Assert
        assertFalse(isDeleted); // Ensure the method returns false
        verify(projectsRepo, times(1)).existsById("P123"); // Verify existsById is called
        verify(projectsRepo, never()).deleteById(anyString()); // Ensure deleteById is NOT called
    }

    @Test
    void testDeleteProject_ExceptionThrown() {
        // Arrange
        String projectId = "P123";

        when(projectsRepo.existsById(projectId)).thenReturn(true);
        
        // Ensure task service returns 2xx so deletion proceeds
        when(restTemplate.exchange(anyString(), eq(HttpMethod.DELETE), any(), eq(String.class)))
            .thenReturn(new ResponseEntity<>("Deleted", HttpStatus.OK));

        // Mock an exception when deleting the project
        doThrow(new RuntimeException("Database error")).when(projectsRepo).deleteById(projectId);

        // Act
        boolean isDeleted = projectsService.deleteProject(projectId);

        // Assert
        assertFalse(isDeleted);
        verify(projectsRepo).existsById(projectId);
        verify(restTemplate).exchange(anyString(), eq(HttpMethod.DELETE), any(), eq(String.class));
        verify(membersRepo).deleteByProjectId(projectId);
        verify(notesRepo).deleteByProjectId(projectId);
        verify(milestoneService).deleteMilestonesByProjectId(projectId);
        verify(projectsRepo).deleteById(projectId);
    }


}