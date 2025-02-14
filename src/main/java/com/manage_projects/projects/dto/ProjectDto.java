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
    private String task_approver;
    private String approver_name;
    private String pjtmain_id;
    private Boolean is_archived;
    private LocalDateTime completed_at;
    private LocalDateTime started_on;
    private String progress;
    private String reason;
    private String privacy;
    private byte[] managerFile;
    private byte[] approverFile;
    private List<String> userIds; 
    private String totaltasks;
    private String closedtasks;
    private String pendingtasks;
    
    public ProjectDto() {
        super();
    }

	public ProjectDto(String projectid, String projectname, String projectdescription, LocalDateTime start_date,
			LocalDateTime end_date, String status, LocalDateTime created_at, LocalDateTime updated_at,
			String project_manager_id, String project_manager, String priority, String task_approver,
			String approver_name, String pjtmain_id, Boolean is_archived, LocalDateTime completed_at,
			LocalDateTime started_on, String progress,String reason, String privacy, byte[] managerFile, byte[] approverFile,
			List<String> userIds, String totaltasks, String closedtasks, String pendingtasks) {
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
		this.project_manager = project_manager;
		this.priority = priority;
		this.task_approver = task_approver;
		this.approver_name = approver_name;
		this.pjtmain_id = pjtmain_id;
		this.is_archived = is_archived;
		this.completed_at = completed_at;
		this.started_on = started_on;
		this.progress = progress;
		this.reason = reason;
		this.privacy = privacy;
		this.managerFile = managerFile;
		this.approverFile = approverFile;
		this.userIds = userIds;
		this.totaltasks = totaltasks;
		this.closedtasks = closedtasks;
		this.pendingtasks = pendingtasks;
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

	public String getProject_manager_id() {
		return project_manager_id;
	}

	public void setProject_manager_id(String project_manager_id) {
		this.project_manager_id = project_manager_id;
	}

	public String getProject_manager() {
		return project_manager;
	}

	public void setProject_manager(String project_manager) {
		this.project_manager = project_manager;
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

	public String getApprover_name() {
		return approver_name;
	}

	public void setApprover_name(String approver_name) {
		this.approver_name = approver_name;
	}

	public byte[] getManagerFile() {
		return managerFile;
	}

	public void setManagerFile(byte[] managerFile) {
		this.managerFile = managerFile;
	}

	public byte[] getApproverFile() {
		return approverFile;
	}

	public void setApproverFile(byte[] approverFile) {
		this.approverFile = approverFile;
	}

	public String getTotaltasks() {
		return totaltasks;
	}

	public void setTotaltasks(String totaltasks) {
		this.totaltasks = totaltasks;
	}

	public String getClosedtasks() {
		return closedtasks;
	}

	public void setClosedtasks(String closedtasks) {
		this.closedtasks = closedtasks;
	}

	public String getPendingtasks() {
		return pendingtasks;
	}

	public void setPendingtasks(String pendingtasks) {
		this.pendingtasks = pendingtasks;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
	
}
