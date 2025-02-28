package com.manage_projects.projects.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.manage_projects.projects.entity.ModuleTasks;

@Repository
public interface ModuleTasksRepository extends JpaRepository<ModuleTasks, String> {
	List<ModuleTasks> findByModuleid(String moduleid);
    
	void deleteByModuleid(String moduleid);
    
	List<ModuleTasks> findByModuleidAndAddedIsFalse(String moduleId);

}
