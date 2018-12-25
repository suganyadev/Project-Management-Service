/**
 * 
 */
package com.project.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.project.management.model.Project;

/**
 * @author Admin
 *
 */
@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer>{

	
	
}
