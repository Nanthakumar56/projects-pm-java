package com.manage_projects.projects.entity;

import java.time.LocalDateTime;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="projects")
public class Projects {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
	private String projectid;
	private String projectname;
	private String projectdescription;
	private LocalDateTime start_date;
	private LocalDateTime end_date;
	private String status;
	private LocalDateTime created_at;
	private LocalDateTime updated_at;
	private String project_manager_id;
	private String priority;
	private String task_approver;
	private String pjtmain_id;
	private Boolean is_archived;
	private LocalDateTime started_on;
	private LocalDateTime completed_at;
	private String type;
	private String privacy;
	private String reason;
	
	public Projects() {
		super();
	}

	public Projects(String projectid, String projectname, String projectdescription, LocalDateTime start_date,
			LocalDateTime end_date, String status, LocalDateTime created_at, LocalDateTime updated_at,
			String project_manager_id, String priority, String task_approver, String pjtmain_id, Boolean is_archived,
			LocalDateTime started_on, LocalDateTime completed_at, String type, String privacy, String reason) {
		super();
		this.projectid = projectid;
		this.projectname = projectname;
		this.projectdescription = projectdescription;
		this.start_date = start_date;
		this.end_date = end_date;
		this.status = status;
		this.created_at = created_at;
		this.updated_at = updated_at;
		this.project_manager_id = project_manager_id;
		this.priority = priority;
		this.task_approver = task_approver;
		this.pjtmain_id = pjtmain_id;
		this.is_archived = is_archived;
		this.started_on = started_on;
		this.completed_at = completed_at;
		this.type = type;
		this.privacy = privacy;
		this.reason = reason;
	}

	public String getProjectid() {
		return projectid;
	}

	public void setProjectid(String projectid) {
		this.projectid = projectid;
	}

	public String getProjectname() {
		return projectname;
	}

	public void setProjectname(String projectname) {
		this.projectname = projectname;
	}

	public String getProjectdescription() {
		return projectdescription;
	}

	public void setProjectdescription(String projectdescription) {
		this.projectdescription = projectdescription;
	}

	public LocalDateTime getStart_date() {
		return start_date;
	}

	public void setStart_date(LocalDateTime start_date) {
		this.start_date = start_date;
	}

	public LocalDateTime getEnd_date() {
		return end_date;
	}

	public void setEnd_date(LocalDateTime end_date) {
		this.end_date = end_date;
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

	public String getProject_manager_id() {
		return project_manager_id;
	}

	public void setProject_manager_id(String project_manager_id) {
		this.project_manager_id = project_manager_id;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public Boolean getIs_archived() {
		return is_archived;
	}

	public void setIs_archived(Boolean is_archived) {
		this.is_archived = is_archived;
	}

	public LocalDateTime getCompleted_at() {
		return completed_at;
	}

	public void setCompleted_at(LocalDateTime completed_at) {
		this.completed_at = completed_at;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPrivacy() {
		return privacy;
	}

	public void setPrivacy(String privacy) {
		this.privacy = privacy;
	}

	public String getTask_approver() {
		return task_approver;
	}

	public void setTask_approver(String task_approver) {
		this.task_approver = task_approver;
	}

	public String getPjtmain_id() {
		return pjtmain_id;
	}

	public void setPjtmain_id(String pjtmain_id) {
		this.pjtmain_id = pjtmain_id;
	}

	public LocalDateTime getStarted_on() {
		return started_on;
	}

	public void setStarted_on(LocalDateTime started_on) {
		this.started_on = started_on;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
	
}
