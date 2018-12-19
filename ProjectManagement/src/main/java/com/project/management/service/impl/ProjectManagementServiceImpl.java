package com.project.management.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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
		userRepository.save(user);
	}

	@Override
	public void addProject(ProjectVO projectMgmt) {
		
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
			user.setStatus("I");
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
		List<Users> users =userRepository.findAll();
		for(Users user: users) {
			UserVO userVO=new UserVO();
			userVO.setFirstName(user.getFirstName());
			userVO.setLastName(user.getLastName());
			userVO.setEmployeeId(user.getEmployeeId());
			userVO.setStatus(user.getStatus());
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
		project.setProject(projectVO.getProjectName());
		//project.setStartDate(projectVO.getStartDate());
		//project.setEndDate(projectVO.getEndDate());
		project.setPriority(Integer.parseInt(projectVO.getPriority()));
		//project.setManagerId(Integer.parseInt(projectVO.getEmployeeId()));
		projectRepository.save(project);
		
	
		
	}
	
}


