package com.manage_projects.projects.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="module")
public class Module 
{
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
	private String moduleid;
	private String modulename;
	private String description;
	private String milestoneid;
	private String status;
	
	public Module() {
		super();
	}

	public Module(String moduleid, String modulename, String description, String milestoneid, String status) {
		super();
		this.moduleid = moduleid;
		this.modulename = modulename;
		this.description = description;
		this.milestoneid = milestoneid;
		this.status = status;
	}

	public String getModuleid() {
		return moduleid;
	}

	public void setModuleid(String moduleid) {
		this.moduleid = moduleid;
	}

	public String getModulename() {
		return modulename;
	}

	public void setModulename(String modulename) {
		this.modulename = modulename;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getMilestoneid() {
		return milestoneid;
	}

	public void setMilestoneid(String milestoneid) {
		this.milestoneid = milestoneid;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}
