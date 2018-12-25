package com.project.management.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.management.model.Project;
import com.project.management.model.Users;
import com.project.management.repository.ParentTaskRepository;
import com.project.management.repository.ProjectRepository;
import com.project.management.repository.TaskRepository;
import com.project.management.repository.UsersRepository;
import com.project.management.service.ProjectManagementService;
import com.project.management.vo.ManagerVO;
import com.project.management.vo.ProjectVO;
import com.project.management.vo.UserVO;

@Service
public class ProjectManagementServiceImpl implements ProjectManagementService {

	@Autowired
	private UsersRepository userRepository;

	@Autowired
	private ProjectRepository projectRepository;

	@Autowired
	private TaskRepository taskRepository;

	@Autowired
	private ParentTaskRepository parentTaskRepository;

	public void addUser(UserVO vo) {
		Users user = new Users();

		user.setFirstName(vo.getFirstName());
		user.setLastName(vo.getLastName());
		user.setEmployeeId(vo.getEmployeeId());
		user.setStatus(vo.getStatus());
		userRepository.save(user);
	}

	public UserVO getUserById(int userId) {
		Optional<Users> optUser = userRepository.findById(userId);
		UserVO userVO = new UserVO();
		userVO.setFirstName(optUser.get().getFirstName());
		userVO.setLastName(optUser.get().getLastName());
		userVO.setEmployeeId(optUser.get().getEmployeeId());
		userVO.setStatus(optUser.get().getStatus());
		return userVO;
	}

	@Override
	public boolean deleteUser(int userId) throws Exception {
		try {
			UserVO existingUser = getUserById(userId);
			if (existingUser != null) {
				Users user = new Users();
				user.setEmployeeId(existingUser.getEmployeeId());
				user.setFirstName(existingUser.getFirstName());
				user.setLastName(existingUser.getLastName());
				user.setProjectId(0);
				user.setTaskId(0);
				user.setStatus("Deleted");
				userRepository.save(user);
			} else {
				System.out.println("delete user: No user available in the user id : " + userId);
				return false;
			}
		} catch (Exception e) {
			throw new Exception("delete user: No user available in the user id :");
		}
		return true;
	}

	@Override
	public List<UserVO> getAllUsers() {
		List<UserVO> userVOList = new ArrayList<UserVO>();
		List<Users> users = userRepository.findUsers();

		userVOList.addAll(users.stream().map(user -> new UserVO(user.getUserId(), user.getEmployeeId(),
				user.getFirstName(), user.getLastName(), user.getStatus())).distinct().collect(Collectors.toList()));
		/*
		 * for (Users user : users) { UserVO userVO = new UserVO();
		 * userVO.setFirstName(user.getFirstName());user.getEmployeeId()
		 * userVO.setLastName(user.getLastName());
		 * userVO.setEmployeeId(user.getEmployeeId());
		 * userVO.setStatus(user.getStatus()); userVO.setUserId(user.getUserId());
		 * userVOList.add(userVO);
		 * 
		 * } return userVOList;
		 */
		return userVOList;

	}

	@Override
	public List<UserVO> getDistinctUser() {
		List<UserVO> userVOList = new ArrayList<UserVO>();
		List<ManagerVO> managerVOs = userRepository.findAllManagers();

		userVOList.addAll(managerVOs.stream().map(
				managerVO -> new UserVO(managerVO.getEmployeeId(), managerVO.getFirstName(), managerVO.getLastName()))
				.distinct().collect(Collectors.toList()));
		return userVOList;

	}

	@Override
	public void updateUser(UserVO userVO) {
		Users user = new Users();
		user.setEmployeeId(userVO.getEmployeeId());
		user.setFirstName(userVO.getFirstName());
		user.setLastName(userVO.getLastName());
		user.setStatus(userVO.getStatus());
		userRepository.save(user);

	}

	@Override
	public void updateProject(ProjectVO projectVO) {

		Project project = new Project();
		project.setProjectId(projectVO.getProjectId());
		project.setProject(projectVO.getProject());
		// project.setStartDate(projectVO.getStartDate());
		// project.setEndDate(projectVO.getEndDate());
		project.setPriority(projectVO.getPriority());
		// project.setManagerId(Integer.parseInt(projectVO.getEmployeeId()));
		projectRepository.save(project);

	}

	@Override
	public List<ProjectVO> getAllProjects() {

		List<ProjectVO> projectVOList = new ArrayList<ProjectVO>();
		List<Project> projects = projectRepository.findAll();
		for (Project project : projects) {
			ProjectVO prjVO = new ProjectVO();
			prjVO.setProject(project.getProject());
			prjVO.setProjectId(project.getProjectId());
			prjVO.setStartDate(project.getStartDate());
			prjVO.setEndDate(project.getEndDate());
			prjVO.setStatus(project.getStatus());
			prjVO.setPriority(project.getPriority());
			projectVOList.add(prjVO);

		}
		return projectVOList;
	}

	@Override
	public void saveOrUpdateProject(ProjectVO vo) {
		Project project = null;

		if (vo.getProjectId() > 0) {
			project = projectRepository.findById(vo.getProjectId()).get();
		} else {
			project = new Project();
		}
		// setting proj name
		project.setProject(vo.getProject());

		if (vo.isHasSetDefaultDate()) {
			project.setStartDate(vo.getStartDate());
			project.setEndDate(vo.getEndDate());
		} else {
			project.setStartDate(new Date());
			project.setEndDate(new Date(new Date().getTime() + (1000 * 60 * 60 * 24)));
		}

		project.setPriority(vo.getPriority());
		project.setStatus(vo.getStatus());
		project = projectRepository.save(project);
		System.out.println(" addedProject.getProjectId() " + project.getProjectId());

		ManagerVO managerVO = vo.getManagerVO();
		Users newUser = new Users();
		newUser.setProjectId(project.getProjectId());
		/*
		 * newUser.setEmployeeId(managerVO.getEmployeeId());
		 * newUser.setFirstName(managerVO.getFirstName());
		 * newUser.setLastName(managerVO.getLastName());
		 */

		newUser.setEmployeeId(vo.getEmployeeId());
		newUser.setFirstName(vo.getFirstName());
		newUser.setLastName(vo.getLastName());
		newUser.setStatus("Active");

		// newUser.setProject(projectSet);
		// Need to detach existing user to project mapping
		Users existingAllocatedUser = userRepository.findUserByProjectId(project.getProjectId());

		if(null!=existingAllocatedUser) {
		userRepository.deleteById(existingAllocatedUser.getUserId());
		}
		userRepository.save(newUser);

	}

	@Override
	public void suspendProject(@Valid ProjectVO vo) {
		Project project = projectRepository.findById(vo.getProjectId()).get();
		project.setStatus("Completed");
	}

}
