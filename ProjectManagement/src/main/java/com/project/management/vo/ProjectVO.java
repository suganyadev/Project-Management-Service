/**
 * 
 */
package com.project.management.vo;

/**
 * @author Admin
 *
 */
public class ProjectVO {

	/**
	 * 
	 */
	private int projectId;
	private String projectName;
	private String startDate;
	private String endDate;
	private String priority;
	private String status;
	private String employeeId;
	private int noOfTask;
	public int getProjectId() {
		return projectId;
	}
	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	public int getNoOfTask() {
		return noOfTask;
	}
	public void setNoOfTask(int noOfTask) {
		this.noOfTask = noOfTask;
	}

}
