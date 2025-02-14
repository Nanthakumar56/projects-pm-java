package com.manage_projects.projects.dto;

import java.time.LocalDateTime;
import java.util.List;

public class AllTaskDto 
{
	private String id;
	private String tskid;
	private String title;
	private String description;
	private String status;
	private String reason;
	private String priority;
	private String projectId;
	private LocalDateTime startDate;
	private LocalDateTime dueDate;
	private String order;
	private String order_over;
	private List<TaskMembersDto> usersAssigned;
	
	public AllTaskDto() {
		super();
	}

	public AllTaskDto(String id, String tskid, String title, String description, String status, String reason,
			String priority, String projectId, LocalDateTime startDate, LocalDateTime dueDate, String order,
			String order_over, List<TaskMembersDto> usersAssigned) {
		super();
		this.id = id;
		this.tskid = tskid;
		this.title = title;
		this.description = description;
		this.status = status;
		this.reason = reason;
		this.priority = priority;
		this.projectId = projectId;
		this.startDate = startDate;
		this.dueDate = dueDate;
		this.order = order;
		this.order_over = order_over;
		this.usersAssigned = usersAssigned;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTskid() {
		return tskid;
	}

	public void setTskid(String tskid) {
		this.tskid = tskid;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public LocalDateTime getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}

	public LocalDateTime getDueDate() {
		return dueDate;
	}

	public void setDueDate(LocalDateTime dueDate) {
		this.dueDate = dueDate;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}
	
	public String getOrder_over() {
		return order_over;
	}

	public void setOrder_over(String order_over) {
		this.order_over = order_over;
	}

	public List<TaskMembersDto> getUsersAssigned() {
		return usersAssigned;
	}

	public void setUsersAssigned(List<TaskMembersDto> usersAssigned) {
		this.usersAssigned = usersAssigned;
	}


}
