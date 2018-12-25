package com.project.management.service;

import java.util.List;

import javax.validation.Valid;

import com.project.management.vo.ProjectVO;
import com.project.management.vo.UserVO;

public interface ProjectManagementService {

	void addUser(UserVO user);

	boolean deleteUser(int userId) throws Exception;

	List<UserVO> getAllUsers();

	void updateUser(UserVO userVO);

	void updateProject(ProjectVO projectVO);

	List<ProjectVO> getAllProjects();

	void saveOrUpdateProject(ProjectVO projectMgmt);

	List<UserVO> getDistinctUser();

	void suspendProject(@Valid ProjectVO vo);

}
