package com.manage_projects.projects.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.manage_projects.projects.entity.KeyPoints;

@Repository
public interface KeypointsRepository extends JpaRepository<KeyPoints, String> {

	List<KeyPoints> findByNotesid(String notesid);

	@Transactional
    @Modifying
    void deleteByNotesid(String notesid);

}
