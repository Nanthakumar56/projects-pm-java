package com.manage_projects.projects.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.manage_projects.projects.entity.Documents;

public interface DocumentsRepository extends JpaRepository<Documents, String> {

	@Query("SELECT docs FROM Documents docs WHERE docs.projectid = :projectid")
	List<Documents> findByProjectId(String projectid);

	 @Modifying
	    @Query("DELETE FROM Documents d WHERE d.projectid = :projectId")
	    void deleteByProjectId(@Param("projectId") String projectId);
}
