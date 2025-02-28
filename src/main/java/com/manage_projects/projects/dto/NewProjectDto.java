package com.manage_projects.projects.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.manage_projects.projects.entity.MileStones;

public class NewProjectDto {
	private String projectid;
    private String title;
    private String description;
    private LocalDateTime startdate;
    private LocalDateTime duedate;
    private String status;
    private String reason;
    private String project_manager;
    private String priority;
    private String task_supervisor;
    private String pjtmain_id;
    private List<String> userIds; 
    private List<MilestoneDto> milestones;
	
    public NewProjectDto() {
		super();
	}

	public NewProjectDto(String projectid,String title, String description, LocalDateTime startdate, LocalDateTime duedate,
			String status,String reason, String project_manager, String priority, String task_supervisor, String pjtmain_id,
			List<String> userIds, List<MilestoneDto> milestones) {
		super();
		this.projectid = projectid;
		this.title = title;
		this.description = description;
		this.startdate = startdate;
		this.duedate = duedate;
		this.status = status;
		this.reason = reason;
		this.project_manager = project_manager;
		this.priority = priority;
		this.task_supervisor = task_supervisor;
		this.pjtmain_id = pjtmain_id;
		this.userIds = userIds;
		this.milestones = milestones;
	}

	public String getProjectid() {
		return projectid;
	}

	public void setProjectid(String projectid) {
		this.projectid = projectid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDateTime getStartdate() {
		return startdate;
	}

	public void setStartdate(LocalDateTime startdate) {
		this.startdate = startdate;
	}

	public LocalDateTime getDuedate() {
		return duedate;
	}

	public void setDuedate(LocalDateTime duedate) {
		this.duedate = duedate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getProject_manager() {
		return project_manager;
	}

	public void setProject_manager(String project_manager) {
		this.project_manager = project_manager;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getTask_supervisor() {
		return task_supervisor;
	}

	public void setTask_supervisor(String task_supervisor) {
		this.task_supervisor = task_supervisor;
	}

	public String getPjtmain_id() {
		return pjtmain_id;
	}

	public void setPjtmain_id(String pjtmain_id) {
		this.pjtmain_id = pjtmain_id;
	}

	public List<String> getUserIds() {
		return userIds;
	}

	public void setUserIds(List<String> userIds) {
		this.userIds = userIds;
	}

	public List<MilestoneDto> getMilestones() {
		return milestones;
	}

	public void setMilestones(List<MilestoneDto> milestones) {
		this.milestones = milestones;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
    
}
