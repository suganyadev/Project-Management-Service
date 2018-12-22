package com.project.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.project.management.model.Task;


/**
 * @author Admin
 *
 */
@Repository
public interface TaskRepository  extends JpaRepository<Task,Integer>{
	/*@Query("SELECT count(t) FROM Task t where t.project.projectId = ?1")
	public int getNoOfTask(int projectId); */
}
