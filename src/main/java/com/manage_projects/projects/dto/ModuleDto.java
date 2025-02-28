package com.manage_projects.projects.dto;

import java.util.List;

import com.manage_projects.projects.entity.ModuleTasks;

public class ModuleDto 
{
	private String moduleid;
	private String modulename;
	private String description;
	private String milestoneid;
	private String status;
	List<ModuleTasks> moduletasks;
	
	public ModuleDto() {
		super();
	}

	public ModuleDto(String moduleid, String modulename, String description, String milestoneid, String status,
			List<ModuleTasks> moduletasks) {
		super();
		this.moduleid = moduleid;
		this.modulename = modulename;
		this.description = description;
		this.milestoneid = milestoneid;
		this.status = status;
		this.moduletasks = moduletasks;
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

	public List<ModuleTasks> getModuletasks() {
		return moduletasks;
	}

	public void setModuletasks(List<ModuleTasks> moduletasks) {
		this.moduletasks = moduletasks;
	}
	
}
