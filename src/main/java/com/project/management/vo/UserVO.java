package com.project.management.vo;

public class UserVO {
	private int employeeId;
	private String firstName;
	private String lastName;
	private String status;
	private ProjectVO project;
	private TaskVO task;
	public int getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public ProjectVO getProject() {
		return project;
	}
	public void setProject(ProjectVO project) {
		this.project = project;
	}
	public TaskVO getTask() {
		return task;
	}
	public void setTask(TaskVO task) {
		this.task = task;
	}
	 
	
	
}
