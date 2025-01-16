package com.manage_projects.projects.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="keypoints")
public class KeyPoints {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private String id;
	private String notesid;
	private String projectid;
	private String points;
	
	public KeyPoints() {
		super();
	}

	public KeyPoints(String id, String notesid, String projectid, String points) {
		super();
		this.id = id;
		this.notesid = notesid;
		this.projectid = projectid;
		this.points = points;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNotesid() {
		return notesid;
	}

	public void setNotesid(String notesid) {
		this.notesid = notesid;
	}

	public String getProjectid() {
		return projectid;
	}

	public void setProjectid(String projectid) {
		this.projectid = projectid;
	}

	public String getPoints() {
		return points;
	}

	public void setPoints(String points) {
		this.points = points;
	}
	
}
