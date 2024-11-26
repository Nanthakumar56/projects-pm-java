package com.manage_projects.projects.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.manage_projects.projects.entity.Projects;

@Repository
public interface ProjectsRepository extends JpaRepository<Projects, String> {

}
