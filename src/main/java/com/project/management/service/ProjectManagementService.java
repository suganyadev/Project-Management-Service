package com.project.management.service;

import java.util.List;

import javax.validation.Valid;

import com.project.management.vo.ProjectVO;
import com.project.management.vo.UserVO;

public interface ProjectManagementService {
	
	public void addUser(UserVO user);
	

	public boolean deleteUser(int userId) throws Exception;

	List<UserVO> getAllUsers();

	void updateUser(UserVO userVO);

	void updateProject(ProjectVO projectVO);

	public List<ProjectVO> getAllProjects();

	public void createProject(ProjectVO projectMgmt);



}
