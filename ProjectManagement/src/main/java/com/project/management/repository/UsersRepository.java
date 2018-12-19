package com.project.management.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.management.model.Users;
import com.project.management.vo.UserVO;

/**
 * @author Admin
 *
 */
@Repository
public interface UsersRepository  extends JpaRepository<Users,Integer>{


	 
}
