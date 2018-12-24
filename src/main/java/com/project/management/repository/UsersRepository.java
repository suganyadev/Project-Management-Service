package com.project.management.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.project.management.model.Users;
import com.project.management.vo.UserVO;

/**
 * @author Admin
 *
 */
@Repository
public interface UsersRepository  extends JpaRepository<Users,Integer>{
	@Query("select u.userId, u.firstName,u.lastName,u.employeeId,u.status from Users u where u.employeeId=:empId")
	public Users findUserByEmployeeId(@Param("empId") String empId);

	 
}
