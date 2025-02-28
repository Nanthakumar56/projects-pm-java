package com.manage_projects.projects.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.manage_projects.projects.dto.MilestoneDto;
import com.manage_projects.projects.dto.ModuleDto;
import com.manage_projects.projects.entity.MileStones;
import com.manage_projects.projects.entity.Module;
import com.manage_projects.projects.entity.ModuleTasks;
import com.manage_projects.projects.repository.MileStoneRepository;
import com.manage_projects.projects.repository.ModuleRepository;
import com.manage_projects.projects.repository.ModuleTasksRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class MilestoneServieTest {

    @Mock
    private MileStoneRepository milestoneRepo;

    @Mock
    private ModuleRepository moduleRepository;

    @Mock
    private ModuleTasksRepository moduleTasksRepository;

    @Spy
    @InjectMocks
    private MilestoneServie milestoneServie;

    private MileStones milestone;
    private Module module;
    private ModuleTasks task;
    private MilestoneDto milestoneDto;
    private ModuleDto moduleDto;

    @BeforeEach
    public void setUp() {
        milestone = new MileStones();
        milestone.setMilestoneid("test-1");
        milestone.setName("Milestone 1");
        milestone.setDescription("Description of Milestone 1");
        milestone.setStart_date(LocalDateTime.now());
        milestone.setTarget_date(LocalDateTime.now().plusDays(10));
        milestone.setDue_date(LocalDateTime.now().plusDays(20));
        milestone.setProjectid("project1");
        milestone.setStatus("In Progress");
        milestone.setCreated_at(LocalDateTime.now());
        milestone.setStep(1);
        milestone.setReason("");

        module = new Module();
        module.setModuleid(UUID.randomUUID().toString());
        module.setModulename("Module 1");
        module.setDescription("Description of Module 1");
        module.setMilestoneid(milestone.getMilestoneid());
        module.setStatus("To Do");

        task = new ModuleTasks();
        task.setMtasksid(UUID.randomUUID().toString());
        task.setTaskname("Task 1");
        task.setStatus("To Do");
        task.setModuleid(module.getModuleid());
        task.setAdded(false);

        moduleDto = new ModuleDto(
                module.getModuleid(),
                module.getModulename(),
                module.getDescription(),
                module.getMilestoneid(),
                module.getStatus(),
                Collections.singletonList(task)
        );

        milestoneDto = new MilestoneDto(
                milestone.getMilestoneid(),
                milestone.getName(),
                milestone.getDescription(),
                milestone.getStart_date(),
                milestone.getTarget_date(),
                milestone.getDue_date(),
                milestone.getProjectid(),
                milestone.getStatus(),
                milestone.getCreated_at(),
                null,
                milestone.getStep(),
                milestone.getReason(),
                Collections.singletonList(moduleDto)
        );
    }

    @Test
    public void testCreateMilestones() {
        when(milestoneRepo.save(any(MileStones.class))).thenReturn(milestone);
        when(moduleRepository.save(any(Module.class))).thenReturn(module);
        when(moduleTasksRepository.save(any(ModuleTasks.class))).thenReturn(task);

        String result = milestoneServie.createMilestones("project1", Collections.singletonList(milestoneDto));

        assertEquals("Successfully created milestones for project project1", result);
        verify(milestoneRepo, times(1)).save(any(MileStones.class));
        verify(moduleRepository, times(1)).save(any(Module.class));
        verify(moduleTasksRepository, times(1)).save(any(ModuleTasks.class));
    }

    @Test
    public void testCreateMilestone() {
        when(milestoneRepo.save(any(MileStones.class))).thenReturn(milestone);
        when(moduleRepository.save(any(Module.class))).thenReturn(module);
        when(moduleTasksRepository.save(any(ModuleTasks.class))).thenReturn(task);

        String result = milestoneServie.createMileStone(milestoneDto);

        verify(milestoneRepo, times(1)).save(any(MileStones.class));
        verify(moduleRepository, times(1)).save(any(Module.class));
        verify(moduleTasksRepository, times(1)).save(any(ModuleTasks.class));
    }

    @Test
    public void testGetAllMilestones() {
        when(milestoneRepo.findByProjectidOrderByStepAsc("project1")).thenReturn(Collections.singletonList(milestone));
        when(moduleRepository.findByMilestoneid(milestone.getMilestoneid())).thenReturn(Collections.singletonList(module));
        when(moduleTasksRepository.findByModuleid(module.getModuleid())).thenReturn(Collections.singletonList(task));

        List<MilestoneDto> result = milestoneServie.getAllMilestones("project1");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(milestone.getMilestoneid(), result.get(0).getMilestoneid());
        verify(milestoneRepo, times(1)).findByProjectidOrderByStepAsc("project1");
        verify(moduleRepository, times(1)).findByMilestoneid(milestone.getMilestoneid());
        verify(moduleTasksRepository, times(1)).findByModuleid(module.getModuleid());
    }

    @Test
    public void testGetMilestoneById() {
        when(milestoneRepo.findById(milestone.getMilestoneid())).thenReturn(Optional.of(milestone));
        when(moduleRepository.findByMilestoneid(milestone.getMilestoneid())).thenReturn(Collections.singletonList(module));
        when(moduleTasksRepository.findByModuleid(module.getModuleid())).thenReturn(Collections.singletonList(task));

        Optional<MilestoneDto> result = milestoneServie.getMilestoneById(milestone.getMilestoneid());

        assertTrue(result.isPresent());
        assertEquals(milestone.getMilestoneid(), result.get().getMilestoneid());
        verify(milestoneRepo, times(1)).findById(milestone.getMilestoneid());
        verify(moduleRepository, times(1)).findByMilestoneid(milestone.getMilestoneid());
        verify(moduleTasksRepository, times(1)).findByModuleid(module.getModuleid());
    }

    @Test
    public void testUpdateMilestones() {
        when(milestoneRepo.findById(milestone.getMilestoneid())).thenReturn(Optional.of(milestone));
        when(milestoneRepo.save(any(MileStones.class))).thenReturn(milestone);

        String result = milestoneServie.updateMilestones(Collections.singletonList(milestoneDto));

        assertEquals("Successfully updated 1 milestones.", result);
        verify(milestoneRepo, times(1)).findById(milestone.getMilestoneid());
        verify(milestoneRepo, times(1)).save(any(MileStones.class));
    }

    @Test
    public void testDeleteMilestone() {
       
        // Mock behavior
        when(milestoneRepo.findById(milestone.getMilestoneid())).thenReturn(Optional.of(milestone));
        doNothing().when(milestoneRepo).deleteById(milestone.getMilestoneid());
        doNothing().when(milestoneRepo).updateStepNumbersForProject(anyInt(), anyString());

        // ❌ DO NOT MOCK deleteModulesByMilestoneId(), so real logic executes
        // doNothing().when(milestoneServie).deleteModulesByMilestoneId(anyString());

        // Call the method under test
        boolean result = milestoneServie.deleteMilestone(milestone.getMilestoneid());

        // Assertions
        assertTrue(result);

        // Verify interactions
        verify(milestoneRepo, times(1)).findById(milestone.getMilestoneid());
        verify(milestoneRepo, times(1)).deleteById(milestone.getMilestoneid());
        verify(milestoneRepo, times(1)).updateStepNumbersForProject(anyInt(), anyString());

        // Debugging
        System.out.println("Checking if moduleTasksRepository.deleteByModuleid() was called");

        // Verify module deletions
        verify(moduleRepository, times(1)).deleteByMilestoneid(milestone.getMilestoneid());
    }


    @Test
    public void testDeleteMilestonesByProjectId() {
        when(milestoneRepo.findByProjectid("project1")).thenReturn(Collections.singletonList(milestone));
        doNothing().when(milestoneRepo).deleteById(milestone.getMilestoneid());
        doNothing().when(milestoneRepo).updateStepNumbersForProject(anyInt(), anyString());
        doNothing().when(moduleRepository).deleteByMilestoneid(anyString());

        boolean result = milestoneServie.deleteMilestonesByProjectId("project1");

        assertTrue(result);
        verify(milestoneRepo, times(1)).findByProjectid("project1");
        verify(milestoneRepo, times(1)).deleteById(milestone.getMilestoneid());
        verify(milestoneRepo, times(1)).updateStepNumbersForProject(anyInt(), anyString());
    }

    @Test
    public void testCreateTask() {
        when(moduleTasksRepository.save(any(ModuleTasks.class))).thenReturn(task);

        ModuleTasks result = milestoneServie.createTask(task);

        assertNotNull(result);
        assertEquals(task.getMtasksid(), result.getMtasksid());
        verify(moduleTasksRepository, times(1)).save(any(ModuleTasks.class));
    }

    @Test
    public void testEditTask() {
        when(moduleTasksRepository.findById(task.getMtasksid())).thenReturn(Optional.of(task));
        when(moduleTasksRepository.save(any(ModuleTasks.class))).thenReturn(task);

        ModuleTasks result = milestoneServie.editTask(task.getMtasksid(), task);

        assertNotNull(result);
        assertEquals(task.getMtasksid(), result.getMtasksid());
        verify(moduleTasksRepository, times(1)).findById(task.getMtasksid());
        verify(moduleTasksRepository, times(1)).save(any(ModuleTasks.class));
    }

    @Test
    public void testDeleteTaskById() {
        when(moduleTasksRepository.existsById(task.getMtasksid())).thenReturn(true);
        doNothing().when(moduleTasksRepository).deleteById(task.getMtasksid());

        milestoneServie.deleteTaskById(task.getMtasksid());

        verify(moduleTasksRepository, times(1)).existsById(task.getMtasksid());
        verify(moduleTasksRepository, times(1)).deleteById(task.getMtasksid());
    }

    @Test
    public void testDeleteTasksByModuleId() {
        doNothing().when(moduleTasksRepository).deleteByModuleid(module.getModuleid());

        milestoneServie.deleteTasksByModuleId(module.getModuleid());

        verify(moduleTasksRepository, times(1)).deleteByModuleid(module.getModuleid());
    }

    @Test
    public void testCreateModule() {
        when(moduleRepository.save(any(Module.class))).thenReturn(module);
        when(moduleTasksRepository.save(any(ModuleTasks.class))).thenReturn(task);

        ModuleDto result = milestoneServie.createModule(moduleDto);

        assertNotNull(result);
        assertEquals(module.getModuleid(), result.getModuleid());
        verify(moduleRepository, times(1)).save(any(Module.class));
        verify(moduleTasksRepository, times(1)).save(any(ModuleTasks.class));
    }

    @Test
    public void testUpdateModule() {
        when(moduleRepository.findById(module.getModuleid())).thenReturn(Optional.of(module));
        when(moduleRepository.save(any(Module.class))).thenReturn(module);

        ModuleDto result = milestoneServie.updateModule(module.getModuleid(), moduleDto);

        assertNotNull(result);
        assertEquals(module.getModuleid(), result.getModuleid());
        verify(moduleRepository, times(1)).findById(module.getModuleid());
        verify(moduleRepository, times(1)).save(any(Module.class));
    }

    @Test
    public void testDeleteModuleById() {
        doNothing().when(moduleTasksRepository).deleteByModuleid(module.getModuleid());
        doNothing().when(moduleRepository).deleteById(module.getModuleid());

        milestoneServie.deleteModuleById(module.getModuleid());

        verify(moduleTasksRepository, times(1)).deleteByModuleid(module.getModuleid());
        verify(moduleRepository, times(1)).deleteById(module.getModuleid());
    }

    @Test
    public void testUpdateAsAdded() {
        when(moduleTasksRepository.findById(task.getMtasksid())).thenReturn(Optional.of(task));
        when(moduleTasksRepository.save(any(ModuleTasks.class))).thenReturn(task);

        boolean result = milestoneServie.updateAsAdded(task.getMtasksid(), "In Progress");

        assertTrue(result);
        verify(moduleTasksRepository, times(1)).save(any(ModuleTasks.class));
        verify(moduleRepository, times(1)).updateModuleStatus(anyString(), anyString());
    }

    @Test
    public void testUpdateAsRemoved() {
        when(moduleTasksRepository.findById(task.getMtasksid())).thenReturn(Optional.of(task));
        when(moduleTasksRepository.save(any(ModuleTasks.class))).thenReturn(task);

        boolean result = milestoneServie.updateAsRemoved(task.getMtasksid());

        assertTrue(result);
        verify(moduleTasksRepository, times(1)).findById(task.getMtasksid());
        verify(moduleTasksRepository, times(1)).save(any(ModuleTasks.class));
    }
}