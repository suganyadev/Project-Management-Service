package com.project.management.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.project.management.model.ProjectManagementVO;
import com.project.management.service.ProjectManagementService;
import com.project.management.validator.ProjectValidator;
import com.project.management.vo.UserVO;


@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
public class ProjectMgmtController {


	@Autowired
	ProjectValidator validator; 

	@Autowired
	ProjectManagementService service;

	@InitBinder
	public void InitBinder(WebDataBinder binder) {
		binder.setValidator(validator);
	}

	@GetMapping("/")
	public String info() {
		return "Welcome to Project Management";
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
	public ResponseEntity<?> addTask(@RequestBody @Valid UserVO vo,BindingResult result){
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
	
}
