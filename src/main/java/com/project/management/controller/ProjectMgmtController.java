package com.project.management.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.project.management.service.ProjectManagementService;
import com.project.management.validator.ProjectValidator;
import com.project.management.validator.UserValidator;
import com.project.management.vo.ParentTaskVO;
import com.project.management.vo.ProjectVO;
import com.project.management.vo.TaskVO;
import com.project.management.vo.UserVO;


@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
public class ProjectMgmtController {


	@Autowired
	ProjectValidator projectValidator; 
	
	@Autowired
	UserValidator userValidator; 

	@Autowired
	ProjectManagementService service;

	@InitBinder("projectValidator")
	public void InitProjectBinder(WebDataBinder binder) {
		binder.setValidator(projectValidator);
	}
	
	@InitBinder("userValidator")
	public void InitUserBinder(WebDataBinder binder) {
		binder.setValidator(userValidator);
	}


	@GetMapping("/")
	public String info() {
		return "Welcome to Project Management";
	}
	
	@GetMapping("/getParentTask")
	public List<ParentTaskVO> getParentTasks() {
		List<ParentTaskVO> tasks =service.getAllParentTasks();
		return tasks;
	}
	
	@GetMapping("/tasks")
	public List<TaskVO> getTasks() {
		List<TaskVO> tasks =service.getAllTasks();
		return tasks;
	}
	
	@GetMapping("/tasksByProject/{projectId}")
	public List<TaskVO> getTasksByProject(@PathVariable(name="projectId") int projectId) {
		List<TaskVO> tasks =service.getTasksByProject(projectId);
		return tasks;
	}
	
	@GetMapping("/tasks/{taskId}")
	public TaskVO getTasksById(@PathVariable(name="taskId") String taskId) {
		TaskVO task = service.getTask(taskId); 
		return task;
	}
	
	@PostMapping(path = "/task", consumes = "application/json", produces = "application/json")
	public boolean saveTask(@RequestBody TaskVO task) {
		try {
			
			service.saveTask(task);
		}catch(Exception e)
		{
			System.out.println("Save Task Failed : " + e.getMessage());
			return false;
		}
		return true;
	}	
	
	@PostMapping("/editTask")
	public boolean updateTask(@RequestBody TaskVO task) {
		try {
			TaskVO taskExists = service.getTask(String.valueOf(task.getTaskId()));
			if(taskExists != null) {
				service.updateTask(task);
			}else {
				System.out.println("update Task: No task available in the task id : " + task.getTaskId());
				return false;
			}
		}catch(Exception e)
		{
			System.out.println("Update Task Failed : " + e.getMessage());
			return false;
		}
		return true;
	}
	
	@DeleteMapping("/tasks/{taskId}")
	public boolean deleteTask(@PathVariable(name="taskId") String taskId) {
		try {
			TaskVO taskRetrived = service.getTask(taskId);
			if(taskRetrived != null) {
				taskRetrived.setStatus("I");
				service.updateTask(taskRetrived);
			}else {
				System.out.println("deleteTask: No task available in the task id : " + taskId);
				return false;
			}
		}catch(Exception e)
		{
			System.out.println("Delete Task Failed : " + e.getMessage());
			return false;
		}
		return true;
	}
	
	
	@PostMapping(path = "/users", consumes = "application/json", produces = "application/json")
	public boolean saveUser(@RequestBody UserVO userVO) {
		try {
			
			//projectManagerService.saveUser(userVO);
		}catch(Exception e)
		{
			System.out.println("Save Project Failed : " + e.getMessage());
			return false;
		}
		return true;
	}

	@PostMapping("/addUser")
	public ResponseEntity<?> addUser(@RequestBody @Valid UserVO vo,BindingResult result){
		try {
			Map<String, Set<String>> errors = new HashMap<>();
			/*for (FieldError fieldError : result.getFieldErrors()) {
				String code = fieldError.getCode();
				String field = fieldError.getField();
				if (code.equals("NotBlank") || code.equals("NotNull")) {
					errors.computeIfAbsent(field, key -> new HashSet<>()).add("required");
				}
				if (field.equals("task")) {
					errors.computeIfAbsent(field, key -> new HashSet<>()).add("Task is Invalid");
				}

				if (field.equals("parentTask")) {
					errors.computeIfAbsent(field, key -> new HashSet<>()).add("Invalid ParentTask");
				}
			} */ 
			if(errors.size()>0)
				return new ResponseEntity<>(errors, HttpStatus.OK);

			vo.setStatus("Active");
			service.addUser(vo);
			
		}catch(Exception e) {
			return ResponseEntity.ok().body("Exception in Adding task");
		}
		return ResponseEntity.ok().body("User Created Successfully");
	}
	
	@GetMapping("/getAllUsers")
	public ResponseEntity<List<UserVO>> getAllUsers() {
		return ResponseEntity.ok().body(service.getAllUsers());
	}
	
	@GetMapping("/getAllEmployees")
	public ResponseEntity<List<UserVO>> getAllEmployees() {
		return ResponseEntity.ok().body(service.getDistinctUser());
	}
	
	@DeleteMapping("/users/{userId}")
	public boolean deleteUser(@PathVariable(name="userId") int userId) {
		try {
			service.deleteUser(userId);
		}catch(Exception e)
		{
			System.out.println("Delete user Failed : " + e.getMessage());
			return false;
		}
		return true;
	}
	
	
	@GetMapping("/getAllProjects")
	public ResponseEntity<List<ProjectVO>> getAllProjects() {
		return ResponseEntity.ok().body(service.getAllProjects());
	}
	
	@PostMapping("/createProject")
	public ResponseEntity<?> saveOrUpdateProject(@RequestBody @Valid ProjectVO vo,BindingResult result){
		try {
			Map<String, Set<String>> errors = new HashMap<>();
			/*for (FieldError fieldError : result.getFieldErrors()) {
				String code = fieldError.getCode();
				String field = fieldError.getField();
				if (code.equals("NotBlank") || code.equals("NotNull")) {
					errors.computeIfAbsent(field, key -> new HashSet<>()).add("required");
				}
				if (field.equals("task")) {
					errors.computeIfAbsent(field, key -> new HashSet<>()).add("Task is Invalid");
				}

				if (field.equals("parentTask")) {
					errors.computeIfAbsent(field, key -> new HashSet<>()).add("Invalid ParentTask");
				}
			} */ 
			if(errors.size()>0)
				return new ResponseEntity<>(errors, HttpStatus.OK);

		//	vo.setStatus("Open");
			
			if(null != vo.getStatus() && vo.getStatus().equalsIgnoreCase("Completed")) {
				service.suspendProject(vo);
			}else {
				service.saveOrUpdateProject(vo);
			}
			
		}catch(Exception e) {
			return ResponseEntity.ok().body("Exception in Adding task");
		}
		return ResponseEntity.ok().body("User Created Successfully");
	}
	
}
