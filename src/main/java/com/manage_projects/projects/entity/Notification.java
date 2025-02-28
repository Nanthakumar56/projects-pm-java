package com.manage_projects.projects.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="notifications")
public class Notification {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
	private String notificationid;
	private String userid;
	private String title;
	private String projectid;
	private String taskid;
	private String is_read;
	private String created_at;
	
	public Notification() {
		super();
	}

	public Notification(String notificationid, String userid, String title, String projectid, String taskid,
			String is_read, String created_at) {
		super();
		this.notificationid = notificationid;
		this.userid = userid;
		this.title = title;
		this.projectid = projectid;
		this.taskid = taskid;
		this.is_read = is_read;
		this.created_at = created_at;
	}

	public String getNotificationid() {
		return notificationid;
	}

	public void setNotificationid(String notificationid) {
		this.notificationid = notificationid;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getProjectid() {
		return projectid;
	}

	public void setProjectid(String projectid) {
		this.projectid = projectid;
	}

	public String getTaskid() {
		return taskid;
	}

	public void setTaskid(String taskid) {
		this.taskid = taskid;
	}

	public String getIs_read() {
		return is_read;
	}

	public void setIs_read(String is_read) {
		this.is_read = is_read;
	}

	public String getCreated_at() {
		return created_at;
	}

	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}
	
	
}
