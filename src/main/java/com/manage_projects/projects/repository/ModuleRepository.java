package com.manage_projects.projects.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.manage_projects.projects.dto.MilestoneDto;
import com.manage_projects.projects.entity.Module;

@Repository
public interface ModuleRepository extends JpaRepository<Module, String> {
	List<Module> findByMilestoneid(String milestoneid);
    void deleteByMilestoneid(String milestoneid);
	Collection<MilestoneDto> findByModuleid(String moduleid);
	
	@Query("SELECT m.milestoneid FROM Module m WHERE m.id = :moduleid")
	String findMilestoneIdByModuleId(@Param("moduleid") String moduleid);

	@Query("SELECT m.status FROM Module m WHERE m.milestoneid = :milestoneid")
	List<String> findModuleStatusesByMilestoneId(@Param("milestoneid") String milestoneid);
	
	@Transactional
	@Modifying
	@Query("UPDATE Module m SET m.status = :status WHERE m.id = :moduleid")
	void updateModuleStatus(@Param("moduleid") String moduleid, @Param("status") String status);

}
