package com.manage_projects.projects.dto;

import java.util.List;

public class ProjectMembersDto {
	private List<String> createUser;
	private List<String> removeUser;
	public ProjectMembersDto() {
		super();
	}
	public ProjectMembersDto(List<String> createUser, List<String> removeUser) {
		super();
		this.createUser = createUser;
		this.removeUser = removeUser;
	}
	public List<String> getCreateUser() {
		return createUser;
	}
	public void setCreateUser(List<String> createUser) {
		this.createUser = createUser;
	}
	public List<String> getRemoveUser() {
		return removeUser;
	}
	public void setRemoveUser(List<String> removeUser) {
		this.removeUser = removeUser;
	}
	

}
