package com.project.management.service;

import java.util.List;

import javax.validation.Valid;

import com.project.management.vo.ParentTaskVO;
import com.project.management.vo.ProjectVO;
import com.project.management.vo.TaskVO;
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

	List<ParentTaskVO> getAllParentTasks();

	void saveTask(TaskVO task);

	List<TaskVO> getAllTasks();

	TaskVO getTask(String taskId);

	void updateTask(TaskVO task);

	List<TaskVO> getTasksByProject(int projectId);

}
