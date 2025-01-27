package com.manage_projects.projects.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="milestones")
public class MileStones {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private String milestoneid;
	private String name;
	private String description;
	private LocalDateTime start_date;
	private LocalDateTime target_date;
	private String projectid;
	private String status;
	private LocalDateTime created_at;
	private LocalDateTime updated_at;
	private int step;
	private String reason;
	
	public MileStones() {
		super();
	}
	public MileStones(String milestoneid, String name, String description, LocalDateTime start_date,
			LocalDateTime target_date, String projectid, String status, LocalDateTime created_at,
			LocalDateTime updated_at, int step, String reason) {
		super();
		this.milestoneid = milestoneid;
		this.name = name;
		this.description = description;
		this.start_date = start_date;
		this.target_date = target_date;
		this.projectid = projectid;
		this.status = status;
		this.created_at = created_at;
		this.updated_at = updated_at;
		this.step = step;
		this.reason = reason;
				}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getMilestoneid() {
		return milestoneid;
	}
	public void setMilestoneid(String milestoneid) {
		this.milestoneid = milestoneid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public LocalDateTime getStart_date() {
		return start_date;
	}
	public void setStart_date(LocalDateTime start_date) {
		this.start_date = start_date;
	}
	public LocalDateTime getTarget_date() {
		return target_date;
	}
	public void setTarget_date(LocalDateTime target_date) {
		this.target_date = target_date;
	}
	public String getProjectid() {
		return projectid;
	}
	public void setProjectid(String projectid) {
		this.projectid = projectid;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	public int getStep() {
		return step;
	}
	public void setStep(int step) {
		this.step = step;
	}
	
}
