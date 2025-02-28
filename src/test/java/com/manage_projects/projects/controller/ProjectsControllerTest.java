package com.manage_projects.projects.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.manage_projects.projects.dto.NewProjectDto;
import com.manage_projects.projects.dto.ProjectDto;
import com.manage_projects.projects.dto.ProjectMembersDto;
import com.manage_projects.projects.entity.Members;
import com.manage_projects.projects.entity.Projects;
import com.manage_projects.projects.service.ProjectsService;

class ProjectsControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ProjectsService projectService;

    @InjectMocks
    private ProjectsController projectsController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(projectsController).build();
    }

    @Test
    void testCreateProject() throws Exception {
        NewProjectDto projectDto = new NewProjectDto();
        Projects project = new Projects();
        when(projectService.createProject(any())).thenReturn(project);

        mockMvc.perform(post("/projects/createProject")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(projectDto)))
                .andExpect(status().isOk());
    }

    @Test
    void testGetAllProjects() throws Exception {
        List<ProjectDto> projects = Arrays.asList(new ProjectDto(), new ProjectDto());
        when(projectService.getAllProjects()).thenReturn(projects);

        mockMvc.perform(get("/projects/allProjects"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetProjectsByUserId() throws Exception {
        List<ProjectDto> projects = Arrays.asList(new ProjectDto());
        when(projectService.getProjectsByUserId("123")).thenReturn(projects);

        mockMvc.perform(get("/projects/userProjects").param("userId", "123"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetProject() throws Exception {
        ProjectDto project = new ProjectDto();
        when(projectService.getProject("1")).thenReturn(project);

        mockMvc.perform(get("/projects/project").param("projectId", "1"))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdateProject() throws Exception {
        NewProjectDto projectDto = new NewProjectDto();
        when(projectService.updateProject(any())).thenReturn(true);

        mockMvc.perform(put("/projects/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(projectDto)))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteProject() throws Exception {
        when(projectService.deleteProject("1")).thenReturn(true);

        mockMvc.perform(delete("/projects/delete").param("projectId", "1"))
                .andExpect(status().isOk());
    }

    @Test
    void testBulkAddMembers() throws Exception {
        List<Members> members = Arrays.asList(new Members(), new Members());
        doNothing().when(projectService).bulkAddMembers(any());

        mockMvc.perform(post("/projects/bulk-add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(members)))
                .andExpect(status().isOk());
    }

    @Test
    void testRemoveMember() throws Exception {
        doNothing().when(projectService).removeMember(any());

        mockMvc.perform(delete("/projects/remove").param("projectid", "1").param("userid", "123"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetUserIdsByProjectId() throws Exception {
        List<String> userIds = Arrays.asList("123", "456");
        when(projectService.getUserIdsByProjectId("1")).thenReturn(userIds);

        mockMvc.perform(get("/projects/users").param("projectid", "1"))
                .andExpect(status().isOk());
    }

    @Test
    void testRemoveAllMembersByProjectId() throws Exception {
        doNothing().when(projectService).removeAllMembersByProjectId("1");

        mockMvc.perform(delete("/projects/remove-all").param("projectid", "1"))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdateProjectMembers() throws Exception {
        ProjectMembersDto membersDto = new ProjectMembersDto();
        doNothing().when(projectService).updateProjectMembers(anyString(), any());

        mockMvc.perform(post("/projects/update-members")
                .param("projectid", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(membersDto)))
                .andExpect(status().isOk());
    }
}
