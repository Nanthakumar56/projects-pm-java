package com.manage_projects.projects.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.manage_projects.projects.entity.Members;
import com.manage_projects.projects.entity.MembersId;

@Repository
public interface MembersRepository extends JpaRepository<Members, MembersId> {

	@Query("SELECT m.id.userid FROM Members m WHERE m.id.projectid = :projectid")
	List<String> findUserIdsByProjectId(String projectid);
	
	 @Modifying
	 @Transactional
	    @Query("DELETE FROM Members m WHERE m.id.projectid = :projectid")
	    void deleteByProjectId(@Param("projectid") String projectid);

	 @Query("SELECT m.id.projectid FROM Members m WHERE m.id.userid = :userId")
	    List<String> findProjectIdsByUserId(@Param("userId") String userId);
}
