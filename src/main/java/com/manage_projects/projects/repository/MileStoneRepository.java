package com.manage_projects.projects.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.manage_projects.projects.entity.MileStones;

@Repository
public interface MileStoneRepository extends JpaRepository<MileStones, String> {

	List<MileStones> findByProjectidOrderByStepAsc(String projectid);

}
