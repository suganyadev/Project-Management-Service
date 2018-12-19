package com.project.management.service;

import java.util.List;

import com.project.management.vo.ProjectVO;
import com.project.management.vo.UserVO;

public interface ProjectManagementService {
	
	public void addUser(UserVO user);
	
	public void addProject(ProjectVO projectMgmt);

	public boolean deleteUser(int userId) throws Exception;

	List<UserVO> getAllUsers();

	void updateUser(UserVO userVO);

	void updateProject(ProjectVO projectVO);



}
