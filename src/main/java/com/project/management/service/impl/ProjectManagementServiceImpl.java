package com.project.management.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import com.project.management.model.Project;
import com.project.management.model.Users;
import com.project.management.repository.ParentTaskRepository;
import com.project.management.repository.ProjectRepository;
import com.project.management.repository.TaskRepository;
import com.project.management.repository.UsersRepository;
import com.project.management.service.ProjectManagementService;
import com.project.management.vo.ProjectVO;
import com.project.management.vo.UserVO;

@Service
public class ProjectManagementServiceImpl implements ProjectManagementService{

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
		UserVO userVO=new UserVO();
		userVO.setFirstName(optUser.get().getFirstName());
		userVO.setLastName(optUser.get().getLastName());
		userVO.setEmployeeId(optUser.get().getEmployeeId());
		userVO.setStatus(optUser.get().getStatus());
		return userVO;
	}
	
	@Override
	public boolean deleteUser(int userId) throws Exception{
		try {
		UserVO existingUser = getUserById(userId);
		if(existingUser != null) {
			Users user=new Users();
			user.setEmployeeId(existingUser.getEmployeeId());
			user.setFirstName(existingUser.getFirstName());
			user.setLastName(existingUser.getLastName());
			user.setProjectId(0);
			user.setTaskId(0);
			user.setStatus("InActive");
			userRepository.save(user);
		}else {
			System.out.println("delete user: No user available in the user id : " + userId);
			return false;
		}
		}catch(Exception e) {
			throw new Exception("delete user: No user available in the user id :");
		}
		return true;
	}
	
	@Override
	public List<UserVO> getAllUsers() {
		List<UserVO> userVOList = new ArrayList<UserVO>();
		List<Users> users = userRepository.findAll();
		for(Users user: users) {
			UserVO userVO=new UserVO();
			userVO.setFirstName(user.getFirstName());
			userVO.setLastName(user.getLastName());
			userVO.setEmployeeId(user.getEmployeeId());
			userVO.setStatus(user.getStatus());
			userVO.setUserId(user.getUserId());
			userVOList.add(userVO);
			
		}
		return userVOList;
	
	}

	@Override
	public void updateUser(UserVO userVO) {
		Users user=new Users();
		user.setEmployeeId(userVO.getEmployeeId());
		user.setFirstName(userVO.getFirstName());
		user.setLastName(userVO.getLastName());
		user.setStatus(userVO.getStatus());
		userRepository.save(user);
		
	}

	
	@Override
	public void updateProject(ProjectVO projectVO) {

		Project project=new Project();
		project.setProjectId(projectVO.getProjectId());
		project.setProject(projectVO.getProject());
		//project.setStartDate(projectVO.getStartDate());
		//project.setEndDate(projectVO.getEndDate());
		project.setPriority(projectVO.getPriority());
		//project.setManagerId(Integer.parseInt(projectVO.getEmployeeId()));
		projectRepository.save(project);
		
	
		
	}

	@Override
	public List<ProjectVO> getAllProjects() {
		
		List<ProjectVO> projectVOList = new ArrayList<ProjectVO>();
		List<Project> projects = projectRepository.findAll();
		for(Project project: projects) {
			ProjectVO userVO = new ProjectVO();
			project.setProject(project.getProject());
			project.setProjectId(project.getProjectId());
			project.setStartDate(project.getStartDate());
			project.setEndDate(project.getEndDate());
			project.setStatus(project.getStatus());
			projectVOList.add(userVO);
			
		}
		return projectVOList;
	}

	@Override
	public void createProject(ProjectVO vo) {
		Project project = new Project();
		project.setProject(vo.getProject());
		project.setStartDate(vo.getStartDate());
		project.setEndDate(vo.getEndDate());
		project.setPriority(vo.getPriority());
		project.setStatus(vo.getStatus());
		project = projectRepository.save(project);
		System.out.println(" addedProject.getProjectId() "+project.getProjectId());
		
		Users user = userRepository.findById(vo.getUserId()).get();
		Users newUser = new Users();
		newUser.setProjectId(project.getProjectId());
		newUser.setEmployeeId(vo.getEmployeeId());
		newUser.setFirstName(user.getFirstName());
		newUser.setLastName(user.getLastName());
		newUser.setStatus("Open");
	
		Set<Project> projectSet = new HashSet<Project>();
		projectSet.add(project);
		//newUser.setProject(projectSet);
		
		userRepository.save(newUser);
	}
	
}


