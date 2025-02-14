package com.manage_projects.projects.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.manage_projects.projects.entity.ProjectNotes;

@Repository
public interface ProjectNotesRepository extends JpaRepository<ProjectNotes, String> {

	List<ProjectNotes> findByProjectid(String projectid);
	
	@Modifying
    @Query("DELETE FROM ProjectNotes n WHERE n.projectid = :projectId")
    void deleteByProjectId(@Param("projectId") String projectId);

}
