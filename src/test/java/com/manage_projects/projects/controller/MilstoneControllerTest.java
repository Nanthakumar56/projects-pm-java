package com.manage_projects.projects.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.manage_projects.projects.dto.MilestoneDto;
import com.manage_projects.projects.dto.ModuleDto;
import com.manage_projects.projects.entity.ModuleTasks;
import com.manage_projects.projects.service.MilestoneServie;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

class MilstoneControllerTest {

    private MockMvc mockMvc;

    @Mock
    private MilestoneServie milestoneService;

    @InjectMocks
    private MilstoneController milestoneController;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(milestoneController).build();
    }

    @Test
    void testCreateMilestone() throws Exception {
        List<MilestoneDto> milestones = Arrays.asList(new MilestoneDto());
        when(milestoneService.createMilestones(anyString(), any())).thenReturn("Milestones Created");

        mockMvc.perform(post("/milestones/createMilestone")
                .param("projectid", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(milestones)))
                .andExpect(status().isOk())
                .andExpect(content().string("Milestones Created"));

        verify(milestoneService, times(1)).createMilestones(anyString(), any());
    }

    @Test
    void testGetAllMilestones() throws Exception {
        List<MilestoneDto> milestones = Arrays.asList(new MilestoneDto());
        when(milestoneService.getAllMilestones(anyString())).thenReturn(milestones);

        mockMvc.perform(get("/milestones/getMilestones")
                .param("projectid", "1"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(milestones)));

        verify(milestoneService, times(1)).getAllMilestones(anyString());
    }

    @Test
    void testGetMilestoneById() throws Exception {
        MilestoneDto milestoneDto = new MilestoneDto();
        when(milestoneService.getMilestoneById(anyString())).thenReturn(Optional.of(milestoneDto));

        mockMvc.perform(get("/milestones/getPhase")
                .param("milestoneId", "1"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(milestoneDto)));

        verify(milestoneService, times(1)).getMilestoneById(anyString());
    }

    @Test
    void testUpdateMilestones() throws Exception {
        List<MilestoneDto> milestones = Arrays.asList(new MilestoneDto());
        when(milestoneService.updateMilestones(any())).thenReturn("Successfully updated milestones");

        mockMvc.perform(put("/milestones/updateMilestones")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(milestones)))
                .andExpect(status().isOk())
                .andExpect(content().string("Successfully updated milestones"));

        verify(milestoneService, times(1)).updateMilestones(any());
    }

    @Test
    void testDeleteMilestone() throws Exception {
        when(milestoneService.deleteMilestone(anyString())).thenReturn(true);

        mockMvc.perform(delete("/milestones/deleteMilestone")
                .param("mileId", "1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Milestone with ID 1 deleted successfully, and step numbers were updated."));

        verify(milestoneService, times(1)).deleteMilestone(anyString());
    }

    @Test
    void testCreateTask() throws Exception {
        ModuleTasks task = new ModuleTasks();
        when(milestoneService.createTask(any())).thenReturn(task);

        mockMvc.perform(post("/milestones/create-task")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(task)))
                .andExpect(status().isOk());

        verify(milestoneService, times(1)).createTask(any());
    }

    @Test
    void testGetModuleById() throws Exception {
        ModuleDto moduleDto = new ModuleDto();
        when(milestoneService.getModuleById(anyString())).thenReturn(moduleDto);

        mockMvc.perform(get("/milestones/module")
                .param("moduleId", "1"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(moduleDto)));

        verify(milestoneService, times(1)).getModuleById(anyString());
    }

    @Test
    void testDeleteModuleById() throws Exception {
        doNothing().when(milestoneService).deleteModuleById(anyString());

        mockMvc.perform(delete("/milestones/deleteModule")
                .param("moduleId", "1"))
                .andExpect(status().isOk());

        verify(milestoneService, times(1)).deleteModuleById(anyString());
    }
}
