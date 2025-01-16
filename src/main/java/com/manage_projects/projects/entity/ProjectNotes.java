package com.manage_projects.projects.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="project_notes")
public class ProjectNotes {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private String notesid;
	private String name;
	private String tag;
	private String keypoints;
	private String projectid;
	private LocalDateTime created_at;
	private LocalDateTime updated_at;
	
	public ProjectNotes() {
		super();
	}
	
	public ProjectNotes(String notesid, String name, String tag, String keypoints, String projectid,
			LocalDateTime created_at, LocalDateTime updated_at) {
		super();
		this.notesid = notesid;
		this.name = name;
		this.tag = tag;
		this.keypoints = keypoints;
		this.projectid = projectid;
		this.created_at = created_at;
		this.updated_at = updated_at;
	}
	
	public String getNotesid() {
		return notesid;
	}
	public void setNotesid(String notesid) {
		this.notesid = notesid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public String getKeypoints() {
		return keypoints;
	}
	public void setKeypoints(String keypoints) {
		this.keypoints = keypoints;
	}
	public String getProjectid() {
		return projectid;
	}
	public void setProjectid(String projectid) {
		this.projectid = projectid;
	}
	public LocalDateTime getCreated_at() {
		return created_at;
	}
	public void setCreated_at(LocalDateTime created_at) {
		this.created_at = created_at;
	}
	public LocalDateTime getUpdated_at() {
		return updated_at;
	}
	public void setUpdated_at(LocalDateTime updated_at) {
		this.updated_at = updated_at;
	}
	
}
