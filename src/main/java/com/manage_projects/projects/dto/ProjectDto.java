package com.manage_projects.projects.dto;

import java.time.LocalDateTime;
import java.util.List;

public class ProjectDto {
    private String projectid;
    private String projectname;
    private String projectdescription;
    private LocalDateTime start_date;
    private LocalDateTime end_date;
    private String status;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
    private String project_manager_id;
    private String project_manager;
    private String priority;
    private String client_id;
    private String attachments;
    private Boolean is_archived;
    private LocalDateTime completed_at;
    private String progress;
    private String privacy;
    private byte[] file;
    private List<String> userIds; 

    public ProjectDto() {
        super();
    }

    public ProjectDto(String projectid, String projectname, String projectdescription, LocalDateTime start_date,
                      LocalDateTime end_date, String status, LocalDateTime created_at, LocalDateTime updated_at,
                      String project_manager,String project_manager_id, String priority, String client_id, String attachments,
                      Boolean is_archived, LocalDateTime completed_at, String progress, String privacy,
                      byte[] file, List<String> userIds) { 
        super();
        this.projectid = projectid;
        this.projectname = projectname;
        this.projectdescription = projectdescription;
        this.start_date = start_date;
        this.end_date = end_date;
        this.status = status;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.project_manager = project_manager;
        this.project_manager_id = project_manager_id;
        this.priority = priority;
        this.client_id = client_id;
        this.attachments = attachments;
        this.is_archived = is_archived;
        this.completed_at = completed_at;
        this.privacy = privacy;
        this.progress = progress;
        this.file = file;
        this.userIds = userIds; 
    
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

    public String getproject_manager() {
        return project_manager;
    }

    public void setproject_manager(String project_manager) {
        this.project_manager = project_manager;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String getAttachments() {
        return attachments;
    }

    public void setAttachments(String attachments) {
        this.attachments = attachments;
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

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public String getPrivacy() {
        return privacy;
    }

    public void setPrivacy(String privacy) {
        this.privacy = privacy;
    }

    public List<String> getUserIds() { 
        return userIds;
    }

    public void setUserIds(List<String> userIds) { 
        this.userIds = userIds;
    }


	public byte[] getFile() {
		return file;
	}

	public void setFile(byte[] file) {
		this.file = file;
	}

	public String getProject_manager_id() {
		return project_manager_id;
	}

	public void setProject_manager_id(String project_manager_id) {
		this.project_manager_id = project_manager_id;
	}
	
    
}
