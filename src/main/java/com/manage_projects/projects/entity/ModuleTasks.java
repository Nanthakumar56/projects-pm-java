package com.manage_projects.projects.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="moduletasks")
public class ModuleTasks 
{
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
	private String mtasksid;
	private String taskname;
	private String status;
	private String moduleid;
	private boolean added;
	public ModuleTasks() {
		super();
	}
	public ModuleTasks(String mtasksid, String taskname, String status, String moduleid, boolean added) {
		super();
		this.mtasksid = mtasksid;
		this.taskname = taskname;
		this.status = status;
		this.moduleid = moduleid;
		this.added = added;
	}
	public String getMtasksid() {
		return mtasksid;
	}
	public void setMtasksid(String mtasksid) {
		this.mtasksid = mtasksid;
	}
	public String getTaskname() {
		return taskname;
	}
	public void setTaskname(String taskname) {
		this.taskname = taskname;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getModuleid() {
		return moduleid;
	}
	public void setModuleid(String moduleid) {
		this.moduleid = moduleid;
	}
	public boolean isAdded() {
		return added;
	}
	public void setAdded(boolean added) {
		this.added = added;
	}
	
}
